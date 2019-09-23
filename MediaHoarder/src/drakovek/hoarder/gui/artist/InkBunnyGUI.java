package drakovek.hoarder.gui.artist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.file.dmf.DMF;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.ArtistValues;
import drakovek.hoarder.file.language.ModeValues;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
import drakovek.hoarder.processing.ExtensionMethods;
import drakovek.hoarder.web.Downloader;

/**
 * Creates GUI for downloading files from Inkbunny.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class InkBunnyGUI extends ArtistHostingGUI
{ 
	/**
	 * String Array of available months in Inkbunny format.
	 */
	private static final String[] MONTHS = {"jan", "feb", "mar", "apr",   //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$//$NON-NLS-4$
											"may", "jun", "jul", "aug", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
											"sep", "oct", "nov", "dec"};  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$//$NON-NLS-4$
	
	/**
	 * Prefix for a DMF ID that indicates that the DMF is sourced from Inkbunny.net
	 */
	private static final String ID_PREFIX = "INK"; //$NON-NLS-1$
	
	/**
	 * Section of Inkbunny page URL that shows it is part of a media gallery
	 */
	private static final String GALLERY_URL = "/s/"; //$NON-NLS-1$
	
	/**
	 * Section of Inkbunny page URL that shows it is part of a journal gallery
	 */
	private static final String JOURNAL_URL = "/j/"; //$NON-NLS-1$
	
	/**
	 * ArrayList of modified Inkbunny IDs to check if media has already been downloaded
	 */
	private ArrayList<String> idStrings;
	
	/**
	 * Initializes InkBunnyGUI class.
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHandler
	 */
	public InkBunnyGUI(DSettings settings, DmfHandler dmfHandler)
	{
		super(settings, dmfHandler, new LoginGUI(settings, ArtistValues.INKBUNNY_LOGIN, false), ModeValues.INKBUNNY_MODE, ArtistValues.CHOOSE_INKBUNNY_FOLDER);
		idStrings = new ArrayList<>();
		getDownloader().setTimeout(1000);
		
	}//CONSTRUCTOR

	@Override
	protected void setDirectory(final File directory)
	{
		getSettings().setInkbunnyDirectory(directory);
		
	}//METHOD

	@Override
	protected File getDirectory()
	{
		return getSettings().getInkbunnyDirectory();
		
	}//METHOD
	
	@Override
	protected String getTitle()
	{
		return '[' + getSettings().getLanguageText(ModeValues.INKBUNNY_MODE) + ']';
		
	}//METHOD
	
	@Override
	protected String getGalleryUrlFragment()
	{
		return GALLERY_URL;
		
	}//METHOD

	@Override
	protected String getMainURL()
	{
		return "inkbunny.net"; //$NON-NLS-1$
		
	}//METHOD
	
	@Override
	public void setNewClient()
	{
		getDownloader().setNewClient();
		getDownloader().getClient().getOptions().setCssEnabled(false);
		getDownloader().getClient().getOptions().setJavaScriptEnabled(false);
		getDownloader().getClient().getOptions().setThrowExceptionOnScriptError(false);
		getDownloader().getClient().setJavaScriptTimeout(10000);
		getDownloader().getClient().setAjaxController(new NicelyResynchronizingAjaxController());
		getDownloader().getClient().getOptions().setTimeout(10000);
		
	}//METHOD

	@Override
	protected void getIdStrings()
	{
		idStrings = new ArrayList<>();
		int size = getDmfHandler().getSize();
		int prefixLength = ID_PREFIX.length();
		for(int i = 0; i < size; i ++)
		{
			String id = getDmfHandler().getID(i);
			if(id.length() > prefixLength && id.startsWith(ID_PREFIX))
			{
				id = id.substring(prefixLength);
				if(id.contains(Character.toString('-')) && !id.endsWith(JOURNAL_SUFFIX))
				{
					idStrings.add(id.substring(0, id.lastIndexOf('-')));
					
				}//IF
				else
				{
					idStrings.add(id);
					
				}//ELSE
				
			}//IF
			
		}//FOR
		
	}//METHOD
	
	@Override
	public void login(String username, String password, String captcha)
	{
		setNewClient();
		setPage("https://inkbunny.net/login.php"); //$NON-NLS-1$
		if(getDownloader().getPage() != null)
		{
			//FILL USERNAME
			List<HtmlInput> usernameText = getDownloader().getPage().getByXPath("//input[@name='username']"); //$NON-NLS-1$
			if(usernameText.size() > 0)
			{
				usernameText.get(0).setValueAttribute(username);
				
			}//IF
			
			//FILL PASSWORD
			List<HtmlInput> passwordText = getDownloader().getPage().getByXPath("//input[@name='password']"); //$NON-NLS-1$
			if(passwordText.size() > 0)
			{
				passwordText.get(0).setValueAttribute(password);
				
			}//IF
			
			//PRESS LOGIN BUTTON
			List<HtmlInput> loginButton = getDownloader().getPage().getByXPath("//div[@class='content']//input[@value='Login']"); //$NON-NLS-1$
			if(loginButton.size() > 0)
			{
				try
				{
					getDownloader().setPage((HtmlPage)loginButton.get(0).click());
				
				}//TRY
				catch (IOException e){}
				
			}//IF
			
		}//IF
		
	}//METHOD

	@Override
	public boolean isLoggedIn()
	{
		if(getDownloader().getPage() != null)
		{
			List<DomElement> myUsername = getDownloader().getPage().getByXPath("//table[@class='loggedin_userdetails']"); //$NON-NLS-1$
			return myUsername.size() > 0;
		
		}//IF
		
		return false;
		
	}//METHOD
	
	@Override
	protected String getUrlArtist(String artist)
	{
		return artist.toLowerCase();
		
	}//METHOD

	@Override
	public File getCaptcha()
	{
		return null;
		
	}//METHOD

	@Override
	protected ArrayList<String> getMediaPages(DProgressInfoDialog progressDialog, final String artist, final boolean checkAll, final boolean scraps)
	{
		//CREATE BASE URL FOR EITHER SCRAPS OR GALLERY
		String baseURL;
		if(scraps)
		{
			baseURL = "https://inkbunny.net/scraps/" + artist + Character.toString('/'); //$NON-NLS-1$
		
		}//IF
		else
		{
			baseURL = "https://inkbunny.net/gallery/" + artist + Character.toString('/'); //$NON-NLS-1$
					
		}//ELSE
		
		//GETS PAGES
		ArrayList<String> allPages = new ArrayList<>();
		ArrayList<String> pages = new ArrayList<>();
		int pageNum = 1;
		int size = -1;
		int allPagesSize = -1;
		List<DomAttr> links;
		while(!progressDialog.isCancelled() && pages.size() != size)
		{
			size = pages.size();
			allPagesSize = allPages.size();
			progressDialog.setDetailLabel(getSettings().getLanguageText(ArtistValues.PAGE) + ' ' + pageNum, false);
			setPage(baseURL + Integer.toString(pageNum) + '/');
			if(isLoggedIn())
			{
				links = getDownloader().getPage().getByXPath("//div[@class='widget_imageFromSubmission ']//a/@href"); //$NON-NLS-1$
				for(int i = 0; !progressDialog.isCancelled() && i < links.size(); i++)
				{
					String linkString = "https://inkbunny.net" + Downloader.getAttribute(links.get(i)); //$NON-NLS-1$
					if(!allPages.contains(linkString))
					{
						allPages.add(linkString);
						
					}//IF
					
					if(!isDownloaded(linkString) && !pages.contains(linkString))
					{
						pages.add(linkString);
						
					}//IF
					
				}//FOR
				
				if(checkAll && allPages.size() != allPagesSize)
				{
					size = -1;
					
				}//IF
				
			}//IF
			
			pageNum++;
			
		}//WHILE
		
		return pages;
		
	}//METHOD

	@Override
	protected ArrayList<String> getJournalPages(DProgressInfoDialog progressDialog, String artist, boolean checkAll)
	{
		//CREATE BASE URL FOR EITHER SCRAPS OR GALLERY
		String baseURL;
		baseURL = "https://inkbunny.net/journals/" + artist + Character.toString('/'); //$NON-NLS-1$
		
		//GETS PAGES
		ArrayList<String> allPages = new ArrayList<>();
		ArrayList<String> pages = new ArrayList<>();
		int pageNum = 1;
		int size = -1;
		int allPagesSize = -1;
		List<DomAttr> links;
		while(!progressDialog.isCancelled() && pages.size() != size)
		{
			size = pages.size();
			allPagesSize = allPages.size();
			progressDialog.setDetailLabel(getSettings().getLanguageText(ArtistValues.PAGE) + ' ' + pageNum, false);
			setPage(baseURL + Integer.toString(pageNum) + '/');
			if(isLoggedIn())
			{
				links = getDownloader().getPage().getByXPath("//div[@class='content']//a/@href"); //$NON-NLS-1$
				for(int i = 0; !progressDialog.isCancelled() && i < links.size(); i++)
				{
					String linkString = "https://inkbunny.net" + Downloader.getAttribute(links.get(i)); //$NON-NLS-1$
					if(linkString.contains(JOURNAL_URL) && !allPages.contains(linkString))
					{
						allPages.add(linkString);
						
					}//IF
					
					if(linkString.contains(JOURNAL_URL) && !isDownloaded(linkString) && !pages.contains(linkString))
					{
						pages.add(linkString);
						
					}//IF
					
				}//FOR
				
				if(checkAll && allPages.size() != allPagesSize)
				{
					size = -1;
					
				}//IF
				
			}//IF
			
			pageNum++;
			
		}//WHILE
		
		return pages;
		
	}//METHOD

	@Override
	protected String downloadMediaPage(final File baseFolder, final String URL) throws Exception
	{
		setPage(URL);
		if(!isLoggedIn())
		{
			throw new Exception("Logged Out Before Process"); //$NON-NLS-1$
			
		}//IF
		
		//GET TITLE
		List<DomElement> titleElement = getDownloader().getPage().getByXPath("//table[@class='pooltable']//h1"); //$NON-NLS-1$
		final String title = Downloader.getElement(titleElement.get(0));
		
		//GET ARTIST
		List<DomElement> artistElement = getDownloader().getPage().getByXPath("//div[@class='elephant elephant_555753']//div[@style='float: left;']//a"); //$NON-NLS-1$
		final String artist = Downloader.getElement(artistElement.get(0));
		
		//GET TIME
		int start = 0;
		int end = 0;
		final List<DomElement> timeElement = getDownloader().getPage().getByXPath("//span[@id='submittime_exact']"); //$NON-NLS-1$
		String timeString = Downloader.getElement(timeElement.get(0)).toLowerCase();
		
		int month;
		for(month = 0; !timeString.contains(MONTHS[month]); month++);
		month++;
		end = timeString.indexOf(' ');
		String day = timeString.substring(0, end);
		start = timeString.indexOf(' ', end + 1) + 1;
		end = timeString.indexOf(' ', start);
		String year = timeString.substring(start, end);
		start = end + 1;
		end = timeString.indexOf(':', start);
		String hour = timeString.substring(start, end);
		start = end + 1;
		end = timeString.indexOf(' ', start);
		String minute = timeString.substring(start, end);

		//GET TAGS
		ArrayList<String> tags = new ArrayList<>();
		
		//GET RATING
		final List<DomElement> ratingElements = getDownloader().getPage().getByXPath("//div[@class='elephant elephant_bottom elephant_white']//div[@class='content']//div"); //$NON-NLS-1$
		String ratingText = null;
		for(int ratingNum = 0; ratingNum < ratingElements.size(); ratingNum++)
		{
			ratingText = Downloader.getElement(ratingElements.get(ratingNum)).toLowerCase();
			if(ratingText.contains(">rating:<")) //$NON-NLS-1$
			{
				if(ratingText.contains("general")) //$NON-NLS-1$
				{
					tags.add(GENERAL_RATING);
					break;
					
				}//IF
				else if(ratingText.contains("mature")) //$NON-NLS-1$
				{
					tags.add(MATURE_RATING);
					break;
					
				}//ELSE IF
				else if(ratingText.contains("adult")) //$NON-NLS-1$
				{
					tags.add(ADULT_RATING);
					break;
					
				}//ELSE IF
				
			}//IF
			
			ratingText = null;
			
		}//FOR
		
		if(ratingText == null)
		{
			throw new Exception("Couldn't find rating tag"); //$NON-NLS-1$
			
		}//IF
		
		//GET MEDIA TYPE
		final List<DomElement> types = getDownloader().getPage().getByXPath("//div[@class='elephant elephant_bottom elephant_white']//div[@class='content']//div"); //$NON-NLS-1$
		String typeText = null;
		for(int typeNum = 0; typeNum < types.size(); typeNum++)
		{
			typeText = Downloader.getElement(types.get(typeNum), true, true).replaceAll("\t", new String()); //$NON-NLS-1$
			if(typeText.contains(">Type:<")) //$NON-NLS-1$
			{
				start = typeText.indexOf('>', typeText.indexOf(">Type:<") + 1) + 1; //$NON-NLS-1$
				end = typeText.indexOf('<', start);
				if(end == -1)
				{
					end = typeText.length();
					
				}//IF
				
				tags.add(typeText.substring(start, end));
				break;
				
			}//IF
			
			typeText = null;
			
		}//FOR
		
		if(typeText == null)
		{
			throw new Exception("Couldn't find type tag"); //$NON-NLS-1$
			
		}//IF
		
		//pool
		final List<DomElement> pool = getDownloader().getPage().getByXPath("//table[@class='pooltable ']//div/a"); //$NON-NLS-1$
		if(pool.size() > 0)
		{
			String poolString = null;
			for(int poolNum = 0; poolNum < pool.size(); poolNum++)
			{
				poolString = Downloader.getElement(pool.get(poolNum), false, false);
				if(poolString.contains("href=\"poolview_process.php")) //$NON-NLS-1$
				{
					tags.add(Downloader.getElement(pool.get(poolNum)));
					break;
				
				}//IF
				
				poolString = null;
			}//FOR
			
			if(poolString == null)
			{
				throw new Exception("Couldn't find pool tag"); //$NON-NLS-1$
				
			}//IF
			
		}//IF
		
		//GET MAIN TAGS
		final List<DomElement> tagElement = getDownloader().getPage().getByXPath("//div[@class='elephant elephant_bottom elephant_white']//div[@class='content']//div//div//a//span"); //$NON-NLS-1$
		for(int i = 0; i < tagElement.size(); i++)
		{
			tags.add(Downloader.getElement(tagElement.get(i)));
			
		}//FOR
		
		//getDescription
		String description = new String();
		final List<DomElement> descriptionElement = getDownloader().getPage().getByXPath("//div[@class='elephant elephant_bottom elephant_white']//div[@class='content']//span[@style='word-wrap: break-word;']"); //$NON-NLS-1$
		if(descriptionElement.size() > 0)
		{
			description = Downloader.getElement(descriptionElement.get(0));
			
		}//IF
		
		//GET PAGES IN SEQUENCE
		ArrayList<String> pageURLs = new ArrayList<>();
		final List<DomAttr> sequenceAttribute = getDownloader().getPage().getByXPath("//div[@class='elephant elephant_white']//div[@class='content']//a/@href"); //$NON-NLS-1$
		String idNum = URL;
		while(URL.endsWith(Character.toString('/')))
		{
			idNum = idNum.substring(0, idNum.length() - 1);
			
		}//WHILE
		start = URL.lastIndexOf('/');
		end = URL.indexOf('-', start);
		if(end == -1)
		{
			end = URL.length();
			
		}//IF
		
		idNum = idNum.substring(start, end);
		
		for(int i = 0; i < sequenceAttribute.size(); i++)
		{
			String pageURL = "https://inkbunny.net" + Downloader.getAttribute(sequenceAttribute.get(i)); //$NON-NLS-1$
			if(!pageURLs.contains(pageURL) && pageURL.contains("#pictop") && pageURL.contains(idNum)) //$NON-NLS-1$
			{
				pageURLs.add(pageURL);
				
			}//IF
			
		}//FOR
		
		if(pageURLs.size() == 0)
		{
			pageURLs.add(URL);
			
		}//IF
		
		//GET MEDIA PAGES
		for(int i = 0; i < pageURLs.size(); i++)
		{
			//SET TITLE
			String currentTitle = title;
			if(pageURLs.size() > 1)
			{
				int pageNum = 1;
				end = pageURLs.get(i).lastIndexOf('-');
				if(end != -1)
				{
					start = pageURLs.get(i).lastIndexOf('p', end) + 1;
					pageNum = Integer.parseInt(pageURLs.get(i).substring(start, end));
					
				}//IF
				
				currentTitle = title + ' ' + '[' + Integer.toString(pageNum) + '/' + Integer.toString(pageURLs.size()) + ']';
			
			}//IF
			
			if(!pageURLs.get(i).equals(URL))
			{
				setPage(pageURLs.get(i));
				
				if(!isLoggedIn())
				{
					throw new Exception("Logged Out Before Process"); //$NON-NLS-1$
					
				}//IF
				
			}//IF
			
			//SET MEDIA URL
			String mediaURL = null;
			List<DomAttr> downloadAttribute = getDownloader().getPage().getByXPath("//div[@id='size_container']//a[@target='_blank']/@href"); //$NON-NLS-1$
			if(downloadAttribute.size() > 0)
			{
				mediaURL = Downloader.getAttribute(downloadAttribute.get(0));
				
			}//IF
			else
			{
				downloadAttribute = getDownloader().getPage().getByXPath("//div[@class='content magicboxParent']//a[@target='_blank']/@href"); //$NON-NLS-1$
				if(downloadAttribute.size() > 0)
				{
					mediaURL = Downloader.getAttribute(downloadAttribute.get(0));
					
				}//IF
				else
				{
					downloadAttribute = getDownloader().getPage().getByXPath("//div[@class='content magicboxParent']//div[@class='widget_imageFromSubmission ']//a/@href"); //$NON-NLS-1$
					mediaURL = Downloader.getAttribute(downloadAttribute.get(0));
					
				}//ELSE
				
			}//ELSE
			
			//SET SECONDARY URL
			String extension = ExtensionMethods.getExtension(mediaURL);
			String secondaryURL = null;
			if(!extension.equals(".jpg") && !extension.equals(".jpeg") && !extension.equals(".png") && !extension.equals(".gif"))   //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$//$NON-NLS-4$
			{
				List<DomAttr> imageAttribute = getDownloader().getPage().getByXPath("//div[@class='content magicboxParent']//div[@class='widget_imageFromSubmission ']//img[@class='shadowedimage']/@src"); //$NON-NLS-1$
				if(imageAttribute.size() > 0)
				{
					secondaryURL = Downloader.getAttribute(imageAttribute.get(0));
					
				}//IF
				
			}//IF
			
			//GET ID
			start = pageURLs.get(i).lastIndexOf('/') + 1;
			end = pageURLs.get(i).indexOf('#', start);
			if(end == -1)
			{
				end = pageURLs.get(i).length();
				
			}//IF
			
			String id = ID_PREFIX + pageURLs.get(i).substring(start, end).toUpperCase();
			
			while(id.endsWith(Character.toString('-')))
			{
				id = id.substring(0, id.length() - 1);
				
			}//WHILE
			
			//SET DMF
			DMF dmf = new DMF();
			dmf.setID(id);
			dmf.setTitle(currentTitle);
			dmf.setArtist(artist);
			dmf.setTime(year, Integer.toString(month), day, hour, minute);
			dmf.setWebTags(tags);
			dmf.setDescription(description);
			dmf.setPageURL(pageURLs.get(i));
			dmf.setMediaURL(mediaURL);
			dmf.setSecondaryURL(secondaryURL);
			
			//DOWNLOAD FILE
			File mediaFile = new File(baseFolder, dmf.getDefaultFileName() + ExtensionMethods.getExtension(dmf.getMediaURL()));
			dmf.setMediaFile(mediaFile);
			getDownloader().downloadFile(dmf.getMediaURL(), mediaFile);
			
			if(dmf.getSecondaryURL() != null)
			{
				File secondaryFile = new File(baseFolder, dmf.getDefaultFileName() + ExtensionMethods.getExtension(dmf.getSecondaryURL()));
				dmf.setSecondaryFile(secondaryFile);
				getDownloader().downloadFile(dmf.getSecondaryURL(), secondaryFile);
				
			}//IF
			
			File dmfFile = new File(baseFolder, dmf.getDefaultFileName() + DMF.DMF_EXTENSION);
			dmf.setDmfFile(dmfFile);
			dmf.writeDMF();
			if(dmf.getDmfFile().exists())
			{
				getDmfHandler().addDMF(dmf);
				
			}//IF
			else
			{
				throw new Exception("Writing DMF Failed"); //$NON-NLS-1$
			
			}//ELSE
			
		}//FOR
		
		return title;
		
	}//METHOD

	@Override
	protected String downloadJournalPage(File baseFolder, String URL) throws Exception
	{
		setPage(URL);
		if(!isLoggedIn())
		{
			throw new Exception("Logged Out Before Process"); //$NON-NLS-1$
			
		}//IF
		
		DMF dmf = new DMF();
		
		//GET ID
		int start = URL.lastIndexOf('/') + 1;
		int end = URL.indexOf('-', start);
		if(end == -1)
		{
			end = URL.length();
			
		}//IF
		
		dmf.setID(ID_PREFIX + URL.substring(start, end).toUpperCase() + JOURNAL_SUFFIX);
		
		//GET TITLE
		List<DomElement> titleElement = getDownloader().getPage().getByXPath("//div[@class='content']//table//h1"); //$NON-NLS-1$
		dmf.setTitle(Downloader.getElement(titleElement.get(0)));
		
		//GET ARTIST
		List<DomElement> artistElement = getDownloader().getPage().getByXPath("//div[@class='elephant elephant_555753']//div[@class='content']//table//span[@class='widget_userNameSmall ']//a"); //$NON-NLS-1$
		dmf.setArtist(Downloader.getElement(artistElement.get(0)));
		
		//GET TIME
		final List<DomElement> timeElement = getDownloader().getPage().getByXPath("//span[@id='submittime_exact']"); //$NON-NLS-1$
		String timeString = Downloader.getElement(timeElement.get(0)).toLowerCase();
		
		int month;
		for(month = 0; !timeString.contains(MONTHS[month]); month++);
		month++;
		end = timeString.indexOf(' ');
		String day = timeString.substring(0, end);
		start = timeString.indexOf(' ', end + 1) + 1;
		end = timeString.indexOf(' ', start);
		String year = timeString.substring(start, end);
		start = end + 1;
		end = timeString.indexOf(':', start);
		String hour = timeString.substring(start, end);
		start = end + 1;
		end = timeString.indexOf(' ', start);
		String minute = timeString.substring(start, end);
		
		dmf.setTime(year, Integer.toString(month), day, hour, minute);
		
		//SET TAGS
		ArrayList<String> tags = new ArrayList<>();
		tags.add(JOURNAL_TAG);
		dmf.setWebTags(tags);
		
		//SET DESCRIPTION
		List<DomElement> description = getDownloader().getPage().getByXPath("//div[@class='elephant elephant_bottom elephant_white']//div[@class='content']//span[@style='word-wrap: break-word;']"); //$NON-NLS-1$
		dmf.setDescription(Downloader.getElement(description.get(0)));
		
		//SET URLS
		dmf.setPageURL(URL);
		dmf.setMediaURL(URL);
		
		//DOWNLOAD DMF
		File mediaFile = new File(baseFolder, dmf.getDefaultFileName() + ".html"); //$NON-NLS-1$
		ArrayList<String> contents = new ArrayList<>();
		contents.add("<!DOCTYPE html>"); //$NON-NLS-1$
		contents.add("<html>"); //$NON-NLS-1$
		contents.add(dmf.getDescription());
		contents.add("</html>"); //$NON-NLS-1$
				
		dmf.setMediaFile(mediaFile);
		DWriter.writeToFile(mediaFile, contents);
			
		File dmfFile = new File(baseFolder, dmf.getDefaultFileName() + DMF.DMF_EXTENSION);
		dmf.setDmfFile(dmfFile);
		dmf.writeDMF();
		if(dmf.getDmfFile().exists())
		{
			getDmfHandler().getDatabase().addDMF(dmf);
							
		}//IF
		else
		{
			throw new Exception("Writing DMF Failed"); //$NON-NLS-1$
					
		}//ELSE
		
		return dmf.getTitle();
		
	}//METHOD

	@Override
	protected boolean isDownloaded(String URL)
	{
		String page = URL.toUpperCase();
		while(page.length() > 0 && page.charAt(page.length() - 1) == '/')
		{
			page = page.substring(0, page.length() - 1);
			
		}//WHILE
		
		int charNum;
		for(charNum = page.length() - 1; charNum > -1 && page.charAt(charNum) != '/'; charNum--); charNum++;
		page = page.substring(charNum);
		if(page.contains(Character.toString('-')))
		{
			page = page.substring(0, page.indexOf('-'));
			
		}//IF
		
		if(URL.contains(GALLERY_URL))
		{	
			return idStrings.contains(page);
			
		}//IF
		else if(URL.contains(JOURNAL_URL))
		{
			page = page + JOURNAL_SUFFIX;
			return idStrings.contains(page);
			
		}//IF
		
		return true;
		
	}//METHOD

}//CLASS
