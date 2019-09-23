package drakovek.hoarder.gui.artist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
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
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.web.Downloader;

/**
 * Creates GUI for downloading files from DeviantArt.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DeviantArtGUI extends ArtistHostingGUI
{
	/**
	 * ArrayList of modified DeviantArt IDs to check if media has already been downloaded
	 */
	private ArrayList<String> idStrings;
	
	
	/**
	 * Prefix for a DMF ID that indicates that the DMF is sourced from FurAffinity.net
	 */
	private static final String ID_PREFIX = "DVA"; //$NON-NLS-1$
	
	/**
	 * Section of DeviantArt page URL that indicates the page is a poll.
	 */
	private static final String POLL_URL = "/poll/"; //$NON-NLS-1$
	
	/**
	 * Section of DeviantArt page URL that shows it is part of a media gallery
	 */
	private static final String GALLERY_URL = "/art/"; //$NON-NLS-1$
	
	/**
	 * Section of DeviantArt page URL that shows it is part of a journal gallery
	 */
	private static final String JOURNAL_URL = "/journal/"; //$NON-NLS-1$
	
	/**
	 * Initializes DeviantArtGUI class.
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHandler
	 */
	public DeviantArtGUI(DSettings settings, DmfHandler dmfHandler)
	{
		super(settings, dmfHandler, new LoginGUI(settings, ArtistValues.DEVIANTART_LOGIN, false), ModeValues.DEVIANTART_MODE, ArtistValues.CHOOSE_DEVIANTART_FOLDER);
		idStrings = new ArrayList<>();
		getDownloader().setTimeout(1000);
		
	}//CONSTRUCTOR

	@Override
	public void setDirectory(File directory)
	{
		getSettings().setDeviantArtDirectory(directory);
		
	}//METHOD

	@Override
	public File getDirectory()
	{
		return getSettings().getDeviantArtDirectory();
		
	}//METHOD

	@Override
	protected String getTitle()
	{
		return '[' + getSettings().getLanguageText(ModeValues.DEVIANTART_MODE) + ']';
		
	}//METHOD
	
	@Override
	public void login(final String username, final String password, final String captcha)
	{
		setNewClient();
		setPage("https://www.deviantart.com/users/login"); //$NON-NLS-1$
		getDownloader().getClient().waitForBackgroundJavaScript(1000);
		if(getDownloader().getPage() != null)
		{
			//FILL USERNAME AND CHECK IF USING ECLIPSE THEME
			List<HtmlInput> usernameText = getDownloader().getPage().getByXPath("//input[@id='username']"); //$NON-NLS-1$
			if(usernameText.size() > 0)
			{
				//IF USING ECLIPSE LOGIN
				usernameText.get(0).setValueAttribute(username);
				
				//FILL PASSWORD
				List<HtmlInput> passwordText = getDownloader().getPage().getByXPath("//input[@id='password']"); //$NON-NLS-1$
				if(passwordText.size() > 0)
				{
					passwordText.get(0).setValueAttribute(password);
					
				}//IF
				
				//PRESS LOGIN BUTTON
				List<HtmlButton> loginButton = getDownloader().getPage().getByXPath("//button[@id='loginbutton']"); //$NON-NLS-1$
				if(loginButton.size() > 0)
				{
					try
					{
						getDownloader().setPage((HtmlPage)loginButton.get(0).click());
					
					}//TRY
					catch (IOException e){}
					
				}//IF
				
			}//IF
			else
			{
				//NOT USING ECLIPSE
				usernameText = getDownloader().getPage().getByXPath("//input[@id='login_username']"); //$NON-NLS-1$
				if(usernameText.size() > 0)
				{
					usernameText.get(0).setValueAttribute(username);
					
				}//IF
				
				//FILL PASSWORD
				List<HtmlInput> passwordText = getDownloader().getPage().getByXPath("//input[@id='login_password']"); //$NON-NLS-1$
				if(passwordText.size() > 0)
				{
					passwordText.get(0).setValueAttribute(password);
					
				}//IF
			
				//PRESS LOGIN BUTTON
				List<HtmlInput> loginButton = getDownloader().getPage().getByXPath("//input[@class='smbutton smbutton-size-default smbutton-shadow smbutton-blue']"); //$NON-NLS-1$
				if(loginButton.size() > 0)
				{
					try
					{
						getDownloader().setPage((HtmlPage)loginButton.get(0).click());
					
					}//TRY
					catch (IOException e){}
					
				}//IF
				
			}//ELSE
			
		}//IF
		
		setEclipseValue(false);
		
	}//METHOD
	
	/**
	 * Sets DeviantArt to either use the Eclipse or the classic theme.
	 * 
	 * @param setToEclipse Whether to set to eclipse theme. If false, sets to classic theme.
	 */
	private void setEclipseValue(final boolean setToEclipse)
	{
		boolean usesEclipse = false;
		if(getDownloader().getPage() != null)
		{
			List<DomElement> eclipseTest = getDownloader().getPage().getByXPath("//iframe[@id='eclipse-notifications-iframe']"); //$NON-NLS-1$
			if(eclipseTest.size() > 0)
			{
				usesEclipse = true;
				
			}//IF
			
			if((usesEclipse && !setToEclipse) || (!usesEclipse && setToEclipse))
			{
				this.setPage("https://www.deviantart.com/features/switch_version/https://www.deviantart.com/"); //$NON-NLS-1$
				
			}//IF
			
		}//IF
		
	}//METHOD

	@Override
	public boolean isLoggedIn()
	{
		if(getDownloader().getPage() != null)
		{
			List<DomAttr> myUsername = getDownloader().getPage().getByXPath("//span[@class='regular username']"); //$NON-NLS-1$
			return myUsername.size() > 0;
		
		}//IF
		
		return false;
		
	}//METHOD

	@Override
	public File getCaptcha()
	{
		return null;

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
	protected ArrayList<String> getMediaPages(DProgressInfoDialog progressDialog, String artist, boolean checkAll, boolean scraps)
	{
		setEclipseValue(false);
		//CREATE BASE URL FOR EITHER SCRAPS OR GALLERY
		String baseURL;
		if(scraps)
		{
			baseURL = "https://www.deviantart.com/" + artist + "/gallery/?catpath=scraps&offset=";  //$NON-NLS-1$//$NON-NLS-2$
		}
		else
		{
			baseURL = "https://www.deviantart.com/" + artist + "/gallery/?catpath=%2F&offset=";  //$NON-NLS-1$//$NON-NLS-2$
					
		}//ELSE
		
		List<DomAttr> links;
		ArrayList<String> pages = new ArrayList<>();
		boolean hasLink;
		int pageNum = 0;
		int size = -1;
		
		while(!progressDialog.isCancelled() && size != pages.size())
		{
			hasLink = false;
			size = pages.size();
			progressDialog.setDetailLabel(getSettings().getLanguageText(ArtistValues.PAGE) + ' ' + pageNum, false);
			setPage(baseURL + Integer.toString(pageNum * 12));
			if(isLoggedIn())
			{
				links = getDownloader().getPage().getByXPath("//a[@class='torpedo-thumb-link']/@href"); //$NON-NLS-1$
				for(int i = 0; !progressDialog.isCancelled() && i < links.size(); i++)
				{
					String linkString = Downloader.getAttribute(links.get(i));
					if(!hasLink && linkString.contains(GALLERY_URL))
					{
						hasLink = true;
						
					}//IF
					
					if(!isDownloaded(linkString) && linkString.contains(GALLERY_URL) && !pages.contains(linkString))
					{
						pages.add(linkString);
						
					}//IF
					
				}//FOR
				
				
				if(hasLink && checkAll)
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
		setEclipseValue(false);
		//CREATE BASE URL FOR EITHER SCRAPS OR GALLERY
		String baseURL = "https://www.deviantart.com/" + artist + "/journal/?catpath=%2F&offset="; //$NON-NLS-1$ //$NON-NLS-2$

		List<DomAttr> links;
		ArrayList<String> pages = new ArrayList<>();
		boolean hasLink;
		int pageNum = 0;
		int size = -1;
				
		while(!progressDialog.isCancelled() && size != pages.size())
		{
			hasLink = false;
			size = pages.size();
			progressDialog.setDetailLabel(getSettings().getLanguageText(ArtistValues.PAGE) + ' ' + pageNum, false);
			setPage(baseURL + Integer.toString(pageNum * 12));
			if(isLoggedIn())
			{
				links = getDownloader().getPage().getByXPath("//div[@class='deviation-plain-view']//a/@href"); //$NON-NLS-1$
				for(int i = 0; !progressDialog.isCancelled() && i < links.size(); i++)
				{
					String linkString = Downloader.getAttribute(links.get(i));
					if(!hasLink && linkString.contains(GALLERY_URL))
					{
						hasLink = true;
								
					}//IF
							
					if(!isDownloaded(linkString) && linkString.contains(JOURNAL_URL) && !linkString.contains(POLL_URL) && !pages.contains(linkString))
					{
						try
						{
							long testEnd = Long.parseLong(linkString.substring(linkString.lastIndexOf('-') + 1));
							if(testEnd > 0L)
							{
								pages.add(linkString);
								
							}//IF
							
						}//TRY
						catch(Exception f){}
						
					}//IF
							
				}//FOR
						
						
				if(hasLink && checkAll)
				{
					size = -1;
							
				}//IF
						
			}//IF
					
			pageNum++;
					
		}//WHILE
		
		return pages;
		
	}//METHOD
	
	@Override
	protected boolean isDownloaded(final String pageURL)
	{
		String page = pageURL;
		while(page.length() > 0 && page.charAt(page.length() - 1) == '/')
		{
			page = page.substring(0, page.length() - 1);
			
		}//WHILE
		
		int charNum;
		for(charNum = page.length() - 1; charNum > -1 && page.charAt(charNum) != '-'; charNum--); charNum++;
		
		if(page.contains(GALLERY_URL))
		{	
			page = page.substring(charNum);
			return idStrings.contains(page);
			
		}//IF
		else if(page.contains(JOURNAL_URL))
		{
			page = page.substring(charNum) + JOURNAL_SUFFIX;
			return idStrings.contains(page);
			
		}//IF
		
		return true;
		
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
			if(id.length() > prefixLength && id.toUpperCase().startsWith(ID_PREFIX))
			{
				idStrings.add(id.substring(prefixLength));
				
			}//IF
			
		}//FOR
		
	}//METHOD

	@Override
	protected String downloadJournalPage(File baseFolder, String URL) throws Exception
	{
		DMF dmf = new DMF();
		setPage(URL);
		if(!isLoggedIn())
		{
			throw new Exception("Logged Out Before Process"); //$NON-NLS-1$
			
		}//IF
		
		//GET ID
		String id = URL;
		while(id.endsWith(Character.toString('/')) || id.endsWith(Character.toString('#')))
		{
			id = id.substring(0, id.length() - 1);
					
		}//WHILE
		int start = id.lastIndexOf('-') + 1;
		
		if(start == 0)
		{
			start = id.lastIndexOf('/') + 1;
			
		}//IF
		id = ID_PREFIX + id.substring(start) + JOURNAL_SUFFIX;
		dmf.setID(id);
		
		//GET JSON INFO
		List<DomAttr> jsonLinks = getDownloader().getPage().getByXPath("//link[@rel='alternate']/@href"); //$NON-NLS-1$
		JSONObject json = null;
		for(int i = 0; i < jsonLinks.size(); i++)
		{
			String jsonInfo = Downloader.getAttribute(jsonLinks.get(i));
			if(jsonInfo.endsWith("format=json")) //$NON-NLS-1$
			{
				UnexpectedPage myUnexpected = (UnexpectedPage)getDownloader().getClient().getPage(jsonInfo);
				String pageResponse = myUnexpected.getWebResponse().getContentAsString();
				json = new JSONObject(pageResponse);
				break;
				
			}//IF
			
		}//FOR
		
		if(json == null)
		{
			throw new Exception("Failed to read JSON"); //$NON-NLS-1$
			
		}//IF
		
		//GET TITLE
		dmf.setTitle(json.getString("title")); //$NON-NLS-1$
		
		//GET ARTIST
		dmf.setArtist(json.getString("author_name")); //$NON-NLS-1$
		
		//GET TIME
		String time = json.getString("pubdate"); //$NON-NLS-1$
		dmf.setTime(time.substring(0, 4), time.substring(5, 7), time.substring(8, 10), time.substring(11, 13), time.substring(14, 16));
		
		//GET TAGS
		ArrayList<String> tags = new ArrayList<>();
		tags.add(JOURNAL_TAG);
		try
		{
			String rating = json.getString("rating"); //$NON-NLS-1$
			if(rating.equals("adult")) //$NON-NLS-1$
			{
				tags.add(MATURE_RATING);
			}//IF
			else
			{
				tags.add(GENERAL_RATING);
				
			}//ELSE
			
		}//TRY
		catch(Exception f)
		{
			tags.add(GENERAL_RATING);
			
		}//CATCH

		tags.add(json.getString("category")); //$NON-NLS-1$
		dmf.setWebTags(tags);
		
		//SET MEDIA PAGE
		dmf.setPageURL(URL);
		dmf.setMediaURL(URL);
		
		//GET JOURNAL CONTENT
		ArrayList<String> contents = new ArrayList<>();
		contents.add("<!DOCTYPE html>"); //$NON-NLS-1$
		contents.add("<html>"); //$NON-NLS-1$
		List<DomElement> journalText = getDownloader().getPage().getByXPath("//div[@class='gr-body']"); //$NON-NLS-1$
		if(journalText.size() > 0)
		{
			dmf.setDescription(Downloader.getElement(journalText.get(0)));
			
		}//IF
		
		if(dmf.getDescription() == null || dmf.getDescription().length() == 0)
		{
			List<DomElement> journalLarge = getDownloader().getPage().getByXPath("//div[@class='journal-wrapper2']"); //$NON-NLS-1$
			contents.add(StringMethods.addHtmlEscapesToHtml(Downloader.getElement(journalLarge.get(0))));
			
		}//IF
		else
		{
			contents.add(dmf.getDescription());
			
		}//ELSE
		
		contents.add("</html>"); //$NON-NLS-1$
		
		//DOWNLOAD FILES
		File mediaFile = new File(baseFolder, dmf.getDefaultFileName() + ".html"); //$NON-NLS-1$
		dmf.setMediaFile(mediaFile);
		DWriter.writeToFile(mediaFile, contents);
				
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
		
		return dmf.getTitle();
		
	}//METHOD

	@Override
	protected String downloadMediaPage(File baseFolder, String URL) throws Exception
	{
		DMF dmf = new DMF();
		setPage(URL);
		if(!isLoggedIn())
		{
			throw new Exception("Logged Out Before Process"); //$NON-NLS-1$
			
		}//IF
		
		//GET ID
		String id = URL;
		while(id.endsWith(Character.toString('/')) || id.endsWith(Character.toString('#')))
		{
			id = id.substring(0, id.length() - 1);
							
		}//WHILE
		int start = id.lastIndexOf('-') + 1;
				
		if(start == 0)
		{
			start = id.lastIndexOf('/') + 1;
					
		}//IF
		id = ID_PREFIX + id.substring(start);
		dmf.setID(id);
		
		//GET JSON INFO
		List<DomAttr> jsonLinks = getDownloader().getPage().getByXPath("//link[@rel='alternate']/@href"); //$NON-NLS-1$
		JSONObject json = null;
		for(int i = 0; i < jsonLinks.size(); i++)
		{
			String jsonInfo = Downloader.getAttribute(jsonLinks.get(i));
			if(jsonInfo.endsWith("format=json")) //$NON-NLS-1$
			{
				UnexpectedPage myUnexpected = (UnexpectedPage)getDownloader().getClient().getPage(jsonInfo);
				String pageResponse = myUnexpected.getWebResponse().getContentAsString();
				json = new JSONObject(pageResponse);
				break;
				
			}//IF
			
		}//FOR
		
		if(json == null)
		{
			throw new Exception("Failed to read JSON"); //$NON-NLS-1$
			
		}//IF
		
		//GET TITLE
		dmf.setTitle(json.getString("title")); //$NON-NLS-1$
		
		//GET ARTIST
		dmf.setArtist(json.getString("author_name")); //$NON-NLS-1$
		
		//GET TIME
		String time = json.getString("pubdate"); //$NON-NLS-1$
		dmf.setTime(time.substring(0, 4), time.substring(5, 7), time.substring(8, 10), time.substring(11, 13), time.substring(14, 16));
		
		//GET TAGS
		ArrayList<String> tags = new ArrayList<>();
		try
		{
			String rating = json.getString("rating"); //$NON-NLS-1$
			if(rating.equals("adult")) //$NON-NLS-1$
			{
				tags.add(MATURE_RATING);
			}//IF
			else
			{
				tags.add(GENERAL_RATING);
				
			}//ELSE
			
		}//TRY
		catch(Exception f)
		{
			tags.add(GENERAL_RATING);
			
		}//CATCH

		tags.add(json.getString("category")); //$NON-NLS-1$
		
		try
		{
			int end;
			String tagString = json.getString("tags").replaceAll(", ", Character.toString(','));  //$NON-NLS-1$//$NON-NLS-2$
			while(tagString.length() > 0)
			{
				for(end = 0; end < tagString.length() && tagString.charAt(end) != ','; end++);
				tags.add(tagString.substring(0, end));
				end++;
				if(end < tagString.length())
				{
					tagString = tagString.substring(end);
				}//IF
				else
				{
					tagString = new String();
					
				}//ELSE
				
			}//WHILE
			
		}//TRY
		catch(Exception f){}
		
		dmf.setWebTags(tags);
		
		//GET DESCRIPTION
		List<DomElement> description = getDownloader().getPage().getByXPath("//div[@class='dev-view-main-content']//div[@class='text block']"); //$NON-NLS-1$
		if(description.size() > 0)
		{
			dmf.setDescription(Downloader.getElement(description.get(0)));
		
		}//IF
		
		//GET MEDIA URL
		dmf.setPageURL(URL);
		String text = null;
		
		//GET ON-SCREEN IMAGE
		String extension = new String();
		List<DomAttr> normalImage = getDownloader().getPage().getByXPath("//img[@class='dev-content-normal ']/@src"); //$NON-NLS-1$
		if(normalImage.size() > 0)
		{
			dmf.setMediaURL(Downloader.getAttribute(normalImage.get(0)));
			extension = ExtensionMethods.getExtension(dmf.getMediaURL());
			
		}//IF
		List<DomAttr> fullImage = getDownloader().getPage().getByXPath("//img[@class='dev-content-full ']/@src"); //$NON-NLS-1$
		if(normalImage.size() > 0)
		{
			dmf.setMediaURL(Downloader.getAttribute(fullImage.get(0)));
			extension = ExtensionMethods.getExtension(dmf.getMediaURL());
			
		}//IF
		
		//GET DOWNLOAD BUTTON
		List<DomElement> downloadText = getDownloader().getPage().getByXPath("//a[@class='dev-page-button dev-page-button-with-text dev-page-download']//span[@class='text']"); //$NON-NLS-1$
		if(downloadText.size() > 0)
		{
			extension = Downloader.getElement(downloadText.get(0));
			extension = '.' + extension.substring(0, extension.indexOf(' ')).toLowerCase();
			
			List<DomAttr> downloadButton = getDownloader().getPage().getByXPath("//a[@class='dev-page-button dev-page-button-with-text dev-page-download']/@href"); //$NON-NLS-1$
			dmf.setMediaURL(Downloader.getAttribute(downloadButton.get(0)));
			
		}//IF
		
		//GET FLASH FILE
		List<DomAttr> flash = getDownloader().getPage().getByXPath("//iframe[@class='flashtime']/@src"); //$NON-NLS-1$
		if(flash.size() > 0)
		{
			setPage(Downloader.getAttribute(flash.get(0)));
			flash = getDownloader().getPage().getByXPath("//embed[@id='sandboxembed']/@src"); //$NON-NLS-1$
			dmf.setMediaURL(Downloader.getAttribute(flash.get(0)));
		
		}//IF
		
		//GET TEXT
		if(dmf.getMediaURL() == null || dmf.getMediaURL().length() == 0)
		{
			extension = ".html"; //$NON-NLS-1$
			dmf.setMediaURL(null);
			List<DomElement> textElement = getDownloader().getPage().getByXPath("//div[@class='dev-view-deviation']//div[@class='text']"); //$NON-NLS-1$
			text = Downloader.getElement(textElement.get(0));
			if(text.contains("<script")) //$NON-NLS-1$
			{
				text = text.substring(0, text.lastIndexOf("<script")); //$NON-NLS-1$
				
			}//IF
			while(text.endsWith(Character.toString(' ')))
			{
				text = text.substring(0, text.length() - 1);
				
			}//WHILE
			
			text = "<!DOCTYPE html><html>" + StringMethods.addHtmlEscapesToHtml(text) + "</html>"; //$NON-NLS-1$ //$NON-NLS-2$
			
		}//IF
		
		//DOWNLOAD FILES
		File mediaFile = new File(baseFolder, dmf.getDefaultFileName() + extension);
		dmf.setMediaFile(mediaFile);
		if(dmf.getMediaURL() != null)
		{
			getDownloader().downloadFile(dmf.getMediaURL(), mediaFile);
			
		}//IF
		else
		{
			DWriter.writeToFile(mediaFile, text);
			
		}//ELSE
				
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
		
		return dmf.getTitle();
		
	}//METHOD

	@Override
	protected String getGalleryUrlFragment()
	{
		return GALLERY_URL;
		
	}//METHOD
	
	@Override
	protected String getUrlArtist(String artist)
	{
		return artist.toLowerCase();
		
	}//METHOD

	@Override
	protected String getMainURL()
	{
		return "deviantart.com"; //$NON-NLS-1$
		
	}//METHOD
	
}//CLASS
