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

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.file.dmf.DMF;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
import drakovek.hoarder.processing.ExtensionMethods;
import drakovek.hoarder.web.Downloader;

/**
 * Creates GUI for downloading files from FurAffinity.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class FurAffinityGUI extends ArtistHostingGUI
{
	/**
	 * String Array of available months in Fur Affinity format.
	 * 
	 * @since 2.0
	 */
	private static final String[] MONTHS = {"jan", "feb", "mar", "apr", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
											"may", "jun", "jul", "aug",  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
											"sep", "oct", "nov", "dec"};  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	
	/**
	 * Prefix for a DMF ID that indicates that the DMF is sourced from FurAffinity.net
	 * 
	 * @since 2.0
	 */
	private static final String ID_PREFIX = "FAF"; //$NON-NLS-1$
	
	/**
	 * ArrayList of modified Fur Affinity IDs to check if media has already been downloaded
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> idStrings;
	
	/**
	 * Initializes FurAffinityGUI class.
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHandler
	 * @since 2.0
	 */
	public FurAffinityGUI(DSettings settings, DmfHandler dmfHandler)
	{
		super(settings, dmfHandler, new LoginGUI(settings, DefaultLanguage.FUR_AFFINITY_LOGIN, true), DefaultLanguage.FUR_AFFINITY_MODE, DefaultLanguage.CHOOSE_FUR_AFFINITY_FOLDER);
		idStrings = new ArrayList<>();
		getDownloader().setTimeout(3000);
		
	}//CONSTRUCTOR

	@Override
	public void setDirectory(File directory)
	{
		getSettings().setFurAffinityDirectory(directory);
		
	}//METHOD

	@Override
	public File getDirectory()
	{
		return getSettings().getFurAffinityDirectory();
		
	}//METHOD

	@Override
	public void login(final String username, final String password, final String captcha)
	{
		if(getDownloader().getPage() != null)
		{
			//FILL USERNAME
			List<HtmlInput> usernameText = getDownloader().getPage().getByXPath("//input[@id='login']"); //$NON-NLS-1$
			if(usernameText.size() > 0)
			{
				usernameText.get(0).setValueAttribute(username);
				
			}//IF
			
			//FILL PASSWORD
			List<HtmlInput> passwordText = getDownloader().getPage().getByXPath("//input[@name='pass']"); //$NON-NLS-1$
			if(passwordText.size() > 0)
			{
				passwordText.get(0).setValueAttribute(password);
				
			}//IF
			
			//FILL CAPTCHA
			List<HtmlInput> captchaText = getDownloader().getPage().getByXPath("//input[@id='captcha']"); //$NON-NLS-1$
			if(captchaText.size() > 0)
			{
				captchaText.get(0).setValueAttribute(captcha);
				
			}//IF
			
			//PRESS LOGIN BUTTON
			List<HtmlInput> loginButton = getDownloader().getPage().getByXPath("//input[@name='login']"); //$NON-NLS-1$
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
			List<DomAttr> myUsername = getDownloader().getPage().getByXPath("//a[@id='my-username']"); //$NON-NLS-1$
			return myUsername.size() > 0;
		
		}//IF
		
		return false;
		
	}//METHOD

	@Override
	public File getCaptcha()
	{
		File file = null;
		File captchaFolder = getCaptchaFolder();
		if(captchaFolder != null && captchaFolder.isDirectory())
		{
			int capNum = 0;
			do
			{
				file = new File(captchaFolder, ID_PREFIX + Integer.toString(capNum) + ".jpg"); //$NON-NLS-1$
				capNum++;
				
			}while (file.exists());
			
		}//IF
		
		setNewClient();
		setPage("https://www.furaffinity.net/login/?mode=imagecaptcha"); //$NON-NLS-1$
		getDownloader().getClient().waitForBackgroundJavaScript(1000);
		if(getDownloader().getPage() != null)
		{
			List<DomAttr> captchaImage = getDownloader().getPage().getByXPath("//img[@id='captcha_img']/@src"); //$NON-NLS-1$
			if(captchaImage.size() > 0)
			{
				getDownloader().downloadFile("https://www.furaffinity.net" + Downloader.getAttribute(captchaImage.get(0)), file); //$NON-NLS-1$
				if(file != null && file.exists())
				{
					return file;
					
				}//IF
				
			}//IF
			
		}//IF
		
		return null;
		
	}//METHOD

	@Override
	public void setNewClient()
	{
		getDownloader().setNewClient();
		getDownloader().getClient().getOptions().setCssEnabled(false);
		getDownloader().getClient().getOptions().setJavaScriptEnabled(true);
		getDownloader().getClient().getOptions().setThrowExceptionOnScriptError(false);
		getDownloader().getClient().setJavaScriptTimeout(10000);
		getDownloader().getClient().setAjaxController(new NicelyResynchronizingAjaxController());
		getDownloader().getClient().getOptions().setTimeout(10000);
		
	}//METHOD

	@Override
	protected ArrayList<String> getPages(DProgressInfoDialog progressDialog, final String artist, final boolean checkAll, final boolean checkJournals)
	{
		progressDialog.setProcessLabel(DefaultLanguage.GETTING_PAGE_URLS);
		progressDialog.setDetailLabel(artist, false);
		progressDialog.setProgressBar(true, false, 0, 0);
		progressDialog.appendLog(null, false);
		String urlArtist = artist.replaceAll(Character.toString('_'), new String());
		
		//GETS PAGES
		progressDialog.appendLog(artist + DProgressInfoDialog.SPACER + getSettings().getLanguageText(DefaultLanguage.GETTING_GALLERY_PAGES), true);
		ArrayList<String> pages = getMediaPages(progressDialog, urlArtist, checkAll, false);
		progressDialog.appendLog(artist + DProgressInfoDialog.SPACER + getSettings().getLanguageText(DefaultLanguage.GETTING_SCRAP_PAGES), true);
		pages.addAll(getMediaPages(progressDialog, urlArtist, checkAll, true));
		
		if(checkJournals)
		{
			progressDialog.appendLog(artist + DProgressInfoDialog.SPACER + getSettings().getLanguageText(DefaultLanguage.GETTING_JOURNAL_PAGES), true);
			pages.addAll(getJournalPages(progressDialog, urlArtist, checkAll));
			
		}//IF
		
		return pages;
		
	}//METHOD
	
	/**
	 * Returns a list of page URLs for a given artist.
	 * 
	 * @param progressDialog DProgressInfoDialog used to show progress
	 * @param artist Fur Affinity Artist
	 * @param checkAll Whether to check all gallery pages
	 * @param scraps Whether to check the scraps gallery
	 * @return List of page URLs
	 * @since 2.0
	 */
	private ArrayList<String> getMediaPages(DProgressInfoDialog progressDialog, final String artist, final boolean checkAll, final boolean scraps)
	{
		//CREATE BASE URL FOR EITHER SCRAPS OR GALLERY
		String baseURL;
		if(scraps)
		{
			baseURL = "https://www.furaffinity.net/scraps/" + artist + Character.toString('/'); //$NON-NLS-1$
		}
		else
		{
			baseURL = "https://www.furaffinity.net/gallery/" + artist + Character.toString('/'); //$NON-NLS-1$
			
		}//ELSE
		
		//GETS PAGES
		ArrayList<String> pages = new ArrayList<>();
		int pageNum = 1;
		int size = -1;
		List<DomAttr> links;
		
		while(!progressDialog.isCancelled() && size != pages.size())
		{
			//LOADS GALLERY PAGE
			size = pages.size();
			progressDialog.setDetailLabel(getSettings().getLanguageText(DefaultLanguage.PAGE) + ' ' + pageNum, false);
			getDownloader().setPage(baseURL + pageNum + "/?perpage=72"); //$NON-NLS-1$
			if(isLoggedIn())
			{
				//GETS LINKS FROM GALLERY PAGE
				links = getDownloader().getPage().getByXPath("//figcaption//a/@href"); //$NON-NLS-1$
				for(int i = 0; !progressDialog.isCancelled() && i < links.size(); i++)
				{
					String linkString = "https://www.furaffinity.net" + Downloader.getAttribute(links.get(i)); //$NON-NLS-1$
					if(!isDownloaded(linkString))
					{
						pages.add(linkString);
						
					}//IF
					
				}//FOR
				
				if(checkAll && links.size() != 0)
				{
					size = -1;
					
				}//IF
				
			}//IF
			
			pageNum++;
			
		}//WHILE
		
		return pages;
		
	}//METHOD
	
	/**
	 * Returns a list of journal page URLs for a given artist.
	 * 
	 * @param progressDialog DProgressInfoDialog used to show progress
	 * @param artist Fur Affinity Artist
	 * @param checkAll Whether to check all gallery pages
	 * @return List of page URLs
	 * @since 2.0
	 */
	private ArrayList<String> getJournalPages(DProgressInfoDialog progressDialog, final String artist, final boolean checkAll)
	{
		//CREATE BASE URL FOR EITHER JOURNAL GALLERY
		String baseURL = "https://www.furaffinity.net/journals/" + artist + Character.toString('/'); //$NON-NLS-1$
		
		//GETS PAGES
		ArrayList<String> pages = new ArrayList<>();
		int pageNum = 1;
		int size = -1;
		List<DomAttr> links;
		
		while(!progressDialog.isCancelled() && size != pages.size())
		{
			//LOADS GALLERY PAGE
			size = pages.size();
			progressDialog.setDetailLabel(getSettings().getLanguageText(DefaultLanguage.PAGE) + ' ' + pageNum, false);
			getDownloader().setPage(baseURL + pageNum);
			if(isLoggedIn())
			{
				//GETS LINKS FROM GALLERY PAGE
				links = getDownloader().getPage().getByXPath("//td[@class='cat']//div[@class='no_overflow']//a/@href"); //$NON-NLS-1$
				for(int i = 0; !progressDialog.isCancelled() && i < links.size(); i++)
				{
					String linkString = "https://www.furaffinity.net" + Downloader.getAttribute(links.get(i)); //$NON-NLS-1$
					if(!isDownloaded(linkString))
					{
						pages.add(linkString);
						
					}//IF
					
				}//FOR
				
				if(checkAll && links.size() != 0)
				{
					size = -1;
					
				}//IF
				
			}//IF
			
			pageNum++;
			
		}//WHILE
		
		return pages;
		
	}//METHOD
	
	/**
	 * Checks whether a given page URL has already been read and downloaded
	 * 
	 * @param pageURL Page URL to download
	 * @return Whether the URL has already downloaded
	 * @since 2.0
	 */
	private boolean isDownloaded(final String pageURL)
	{
		String page = pageURL;
		while(page.length() > 0 && page.charAt(page.length() - 1) == '/')
		{
			page = page.substring(0, page.length() - 1);
			
		}//WHILE
		
		int charNum;
		for(charNum = page.length() - 1; charNum > -1 && page.charAt(charNum) != '/'; charNum--); charNum++;
		
		if(page.contains("/view/")) //$NON-NLS-1$
		{	
			page = page.substring(charNum);
			return idStrings.contains(page);
			
		}//IF
		else if(page.contains("/journal/")) //$NON-NLS-1$
		{
			page = page.substring(charNum) + JOURNAL_SUFFIX;
			return idStrings.contains(page);
			
		}//IF
		
		return true;
		
	}//METHOD

	@Override
	protected void downloadPages(DProgressInfoDialog progressDialog, String artist, ArrayList<String> pages)
	{
		File artistFolder = DReader.getDirectory(getDirectory(), DWriter.getFileFriendlyName(artist));
		for(int i = pages.size() - 1; !progressDialog.isCancelled() && i > -1; i--)
		{
			progressDialog.setProcessLabel(DefaultLanguage.LOADING_PAGE);
			progressDialog.setDetailLabel(pages.get(i).substring(pages.get(i).lastIndexOf('/', pages.get(i).length() - 2)), false);
			progressDialog.setProgressBar(false, true, pages.size(), pages.size() - (i + 1));
			progressDialog.appendLog(getSettings().getLanguageText(DefaultLanguage.LOADING_PAGE) + ' ' + pages.get(i), true);
			
			if(pages.get(i).contains("/view/")) //$NON-NLS-1$
			{
				try
				{
					String title = downloadMediaPage(artistFolder, pages.get(i));
					progressDialog.appendLog(getSettings().getLanguageText(DefaultLanguage.DOWNLOADED) + DProgressInfoDialog.SPACER + title , true);
					
				}//TRY
				catch(Exception e)
				{
					progressDialog.setCancelled(true);
					progressDialog.appendLog(DefaultLanguage.DOWNLOAD_FAILED, true);
					e.printStackTrace();
					
				}//CATCH
				
			}//IF
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Downloads media and creates DMF for a Fur Affinity media page.
	 * 
	 * @param baseFolder Base Folder to save within
	 * @param pageURL PageURL to read
	 * @return Title of the most recently downloaded page
	 * @throws Exception Any problem reading Fur Affinity data
	 * @since 2.0
	 */
	private String downloadMediaPage(final File baseFolder, final String pageURL) throws Exception
	{
		int end;
		
		DMF dmf = new DMF();
		getDownloader().setPage(pageURL);
		if(!isLoggedIn())
		{
			throw new Exception("Logged Out Before Process"); //$NON-NLS-1$
			
		}//IF
		
		//GET ID
		String id = pageURL;
		while(id.endsWith(Character.toString('/')))
		{
			id = id.substring(0, id.length() - 1);
			
		}//WHILE
		id = ID_PREFIX + id.substring(id.lastIndexOf('/') + 1);
		dmf.setID(id);
		
		//GET TITLE
		List<DomElement> title = getDownloader().getPage().getByXPath("//div[@class='classic-submission-title information']/h2"); //$NON-NLS-1$
		dmf.setTitle(Downloader.getElement(title.get(0)));
		
		//GET ARTIST
		List<DomElement> artist = getDownloader().getPage().getByXPath("//div[@class='classic-submission-title information']/a"); //$NON-NLS-1$
		dmf.setArtist(Downloader.getElement(artist.get(0)));
		
		//GET DATE STRING
		String dateString;
		List<DomAttr> dateAttribute = getDownloader().getPage().getByXPath("//td[@class='alt1 stats-container']//span[@class='popup_date']/@title"); //$NON-NLS-1$
		dateString = Downloader.getAttribute(dateAttribute.get(0));
		
		if(dateString.contains("ago")) //$NON-NLS-1$
		{
			List<DomElement> dateElement = getDownloader().getPage().getByXPath("//td[@class='alt1 stats-container']//span[@class='popup_date']"); //$NON-NLS-1$
			dateString = Downloader.getElement(dateElement.get(0));
			
		}//IF
		
		//GET MONTH
		int month;
		for(month = 0; !dateString.toLowerCase().contains(MONTHS[month]); month++);
		month++;
		
		//GET YEAR
		int place = dateString.indexOf(',');
		int year = Integer.parseInt(dateString.substring(place + 2, dateString.indexOf(' ', place + 2)));
		
		//GET DAY
		end = place;
		while(true)
		{
			char myChar = dateString.charAt(end);
			if(myChar == '0' || myChar == '1' || myChar == '2' || myChar == '3' || myChar == '4' || myChar == '5' || myChar == '6' || myChar == '7' || myChar == '8' || myChar == '9')
			{
				break;
				
			}//IF
			
			end--;
			
		}//WHILE
		
		int day = Integer.parseInt(dateString.substring(dateString.indexOf(' ') + 1, end + 1));
		
		//GET MINUTE
		place = dateString.indexOf(':');
		end = dateString.indexOf(' ', place);
		int minute = Integer.parseInt(dateString.substring(place + 1, end));
		
		//GET HOUR
		int hour = Integer.parseInt(dateString.substring(dateString.lastIndexOf(' ', place) + 1, place));
		boolean isAM = dateString.toLowerCase().contains("am"); //$NON-NLS-1$
		if(isAM && hour == 12)
		{
			hour = 0;
			
		}//IF
		
		if(!isAM && hour < 12)
		{
			hour = hour + 12;
			
		}//IF
		
		dmf.setTime(Integer.toString(year), Integer.toString(month), Integer.toString(day), Integer.toString(hour), Integer.toString(minute));
		
		//GET WEB TAGS
		ArrayList<String> tags = new ArrayList<>();
		
		//GET RATING
		List<DomAttr> rating = getDownloader().getPage().getByXPath("//td[@class='alt1 stats-container']//img/@alt"); //$NON-NLS-1$
		String ratingString = Downloader.getAttribute(rating.get(0)).toLowerCase();
		if(ratingString.contains("general")) //$NON-NLS-1$
		{
			tags.add(GENERAL_RATING);
			
		}//IF
		else if(ratingString.contains("mature")) //$NON-NLS-1$
		{
			tags.add(MATURE_RATING);
			
		}//ELSE IF
		else if(ratingString.contains("adult")) //$NON-NLS-1$
		{
			tags.add(ADULT_RATING);
			
		}//ELSE IF
		
		//GET SCRAP/MAIN GALLERY
		String gallery;
		List<DomAttr> galleryAttribute = getDownloader().getPage().getByXPath("//b[@class='minigallery-title']//s//a/@href"); //$NON-NLS-1$
		if(galleryAttribute.size() > 0)
		{
			gallery = Downloader.getAttribute(galleryAttribute.get(0)).toLowerCase();
			
		}//IF
		else
		{
			List<DomElement> galleryElement = getDownloader().getPage().getByXPath("//a[@class='goto-gallery']"); //$NON-NLS-1$
			gallery = Downloader.getElement(galleryElement.get(0)).toLowerCase();
			
		}//ELSE
		
		if(gallery.contains("go to")) //$NON-NLS-1$
		{
			if(gallery.contains("main")) //$NON-NLS-1$
			{
				tags.add(MAIN_GALLERY);
				
			}//IF
			else
			{
				tags.add(SCRAPS_GALLERY);
				
			}//ELSE
			
		}//IF
		else
		{
			if(gallery.contains("/gallery/")) //$NON-NLS-1$
			{
				tags.add(MAIN_GALLERY);
				
			}//IF
			else
			{
				tags.add(SCRAPS_GALLERY);
				
			}//ELSE
			
		}//ELSE
		
		//GET CATEGORIES
		List<DomElement> categories = getDownloader().getPage().getByXPath("//td[@class='alt1 stats-container']"); //$NON-NLS-1$
		String catString = Downloader.getElement(categories.get(0));
		String header = "<b>Category:"; //$NON-NLS-1$
		if(catString.contains(header))
		{
			end = catString.indexOf("<br/>", catString.indexOf(header)); //$NON-NLS-1$
			tags.add("Category - " + catString.substring(catString.lastIndexOf('>', end) + 1, end)); //$NON-NLS-1$
			
		}//IF
		
		header = "<b>Theme:"; //$NON-NLS-1$
		if(catString.contains(header))
		{
			end = catString.indexOf("<br/>", catString.indexOf(header)); //$NON-NLS-1$
			tags.add("Theme - " + catString.substring(catString.lastIndexOf('>', end) + 1, end)); //$NON-NLS-1$
			
		}//IF
		
		header = "<b>Species:"; //$NON-NLS-1$
		if(catString.contains(header))
		{
			end = catString.indexOf("<br/>", catString.indexOf(header)); //$NON-NLS-1$
			tags.add("Species - " + catString.substring(catString.lastIndexOf('>', end) + 1, end)); //$NON-NLS-1$
			
		}//IF
		
		header = "<b>Gender:"; //$NON-NLS-1$
		if(catString.contains(header))
		{
			end = catString.indexOf("<br/>", catString.indexOf(header)); //$NON-NLS-1$
			tags.add("Gender - " + catString.substring(catString.lastIndexOf('>', end) + 1, end)); //$NON-NLS-1$
			
		}//IF

		//GET USER FOLDERS
		List<DomElement> folders = getDownloader().getPage().getByXPath("//div[@class='folder-list-container']//a"); //$NON-NLS-1$
		for(int i = 0; i < folders.size(); i++)
		{
			tags.add(Downloader.getElement(folders.get(i)).replaceAll("<span>", new String()).replaceAll("</span>", new String()).replaceAll("<strong>", new String()).replaceAll("</strong>", new String())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
		}//METHOD
		
		//GET MAIN TAGS
		List<DomElement> tagElements = getDownloader().getPage().getByXPath("//td[@class='alt1 stats-container']//div[@id='keywords']//a"); //$NON-NLS-1$
		for(int i = 0; i < tagElements.size(); i++)
		{
			tags.add(Downloader.getElement(tagElements.get(i)));
			
		}//FOR
		dmf.setWebTags(tags);
		
		//GET DESCRIPTION
		List<DomElement> description = getDownloader().getPage().getByXPath("//table[@class='maintable']//table[@class='maintable']//td[@class='alt1']"); //$NON-NLS-1$
		if(description.size() > 1)
		{
			String descriptionString = Downloader.getElement(description.get(1));
			if(Downloader.getElement(description.get(0)).contains("class=\"alt1 stats-container\"") && !descriptionString.contains("class=\"alt1 stats-container\""))  //$NON-NLS-1$//$NON-NLS-2$
			{
				dmf.setDescription(descriptionString);
				
			}//IF
			
		}//IF
		
		if(dmf.getDescription() == null)
		{
			throw new Exception("Retrieving Description Failed"); //$NON-NLS-1$
			
		}//IF
		
		//GET MEDIA URL
		dmf.setPageURL(pageURL);
		List<DomAttr> mediaURLs = getDownloader().getPage().getByXPath("//div[@class='alt1 actions aligncenter']//a/@href"); //$NON-NLS-1$
		for(int i = 0; i < mediaURLs.size(); i++)
		{
			String mediaURL = "https:" + Downloader.getAttribute(mediaURLs.get(i)); //$NON-NLS-1$
			if(mediaURL.contains("/art/")) //$NON-NLS-1$
			{
				dmf.setMediaURL(mediaURL);
				break;
				
			}//IF
			
		}//FOR
		if(dmf.getMediaURL() == null)
		{
			throw new Exception("Retrieving Media URL Failed"); //$NON-NLS-1$
			
		}//IF
		
		String mainExtension = ExtensionMethods.getExtension(dmf.getMediaURL());
		
		//GET SECONDARY MEDIA URL
		List<DomAttr> secondaryURL = getDownloader().getPage().getByXPath("//img[@id='submissionImg']/@data-fullview-src"); //$NON-NLS-1$
		if(secondaryURL.size() == 0)
		{
			secondaryURL = getDownloader().getPage().getByXPath("//img[@id='submissionImg']/@data-preview-src"); //$NON-NLS-1$
			
		}//IF
		
		String secondaryExtension = null;
		if(secondaryURL.size() > 0)
		{
			String secondary = "https:" + Downloader.getAttribute(secondaryURL.get(0)); //$NON-NLS-1$
			secondaryExtension = ExtensionMethods.getExtension(secondary);
			if(!secondaryExtension.equals(mainExtension))
			{
				dmf.setSecondaryURL(secondary);
				
			}//IF
			
		}//IF
		
		//DOWNLOAD FILES
		String filename = DWriter.getFileFriendlyName(dmf.getTitle()) + Character.toString('_') + dmf.getID();
		File mediaFile = new File(baseFolder, filename + mainExtension);
		dmf.setMediaFile(mediaFile);
		getDownloader().downloadFile(dmf.getMediaURL(), mediaFile);
		
		if(dmf.getSecondaryURL() != null)
		{
			File secondaryFile = new File(baseFolder, filename + secondaryExtension);
			dmf.setSecondaryFile(secondaryFile);
			getDownloader().downloadFile(dmf.getSecondaryURL(), secondaryFile);
			
		}//IF
		
		File dmfFile = new File(baseFolder, filename + DMF.DMF_EXTENSION);
		dmf.setDmfFile(dmfFile);
		dmf.writeDMF();
		if(!dmf.getDmfFile().exists())
		{
			throw new Exception("Writing DMF Failed"); //$NON-NLS-1$
			
		}//IF
		
		return dmf.getTitle();
		
	}//METHOD

	@Override
	protected void getIdStrings()
	{
		idStrings = new ArrayList<>();
		int size = getDmfHandler().getSize();
		for(int i = 0; i < size; i ++)
		{
			String id = getDmfHandler().getID(i);
			if(id.length() > 3 && id.toUpperCase().startsWith(ID_PREFIX))
			{
				idStrings.add(id.substring(3));
				
			}//IF
			
		}//FOR
		
	}//METHOD

	@Override
	protected String getTitle()
	{
		return getSettings().getLanguageText(DefaultLanguage.FUR_AFFINITY_PROGRESS_TITLE);
		
	}//METHOD
	
}//METHOD
