package drakovek.hoarder.gui.artist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
import drakovek.hoarder.web.Downloader;

/**
 * Creates GUI for downloading files from DeviantArt.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DeviantArtGUI extends ArtistHostingGUI
{
	/**
	 * ArrayList of modified DeviantArt IDs to check if media has already been downloaded
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> idStrings;
	
	
	/**
	 * Prefix for a DMF ID that indicates that the DMF is sourced from FurAffinity.net
	 * 
	 * @since 2.0
	 */
	private static final String ID_PREFIX = "DVA"; //$NON-NLS-1$
	
	/**
	 * Section of DeviantArt page URL that indicates page is to a comment section.
	 * 
	 * @since 2.0
	 */
	private static final String COMMENT_URL = "#comments"; //$NON-NLS-1$
	
	/**
	 * Section of DeviantArt page URL that shows it is part of a media gallery
	 * 
	 * @since 2.0
	 */
	private static final String GALLERY_URL = "/art/"; //$NON-NLS-1$
	
	/**
	 * Section of DeviantArt page URL that shows it is part of a journal gallery
	 * 
	 * @since 2.0
	 */
	private static final String JOURNAL_URL = "/journal/"; //$NON-NLS-1$
	
	/**
	 * Whether the user started in DeviantArt eclipse theme.
	 * 
	 * @since 2.0
	 */
	private boolean eclipse;
	
	/**
	 * Initializes DeviantArtGUI class.
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHandler
	 * @since 2.0
	 */
	public DeviantArtGUI(DSettings settings, DmfHandler dmfHandler)
	{
		super(settings, dmfHandler, new LoginGUI(settings, DefaultLanguage.DEVIANTART_LOGIN, false), DefaultLanguage.DEVIANTART_MODE, DefaultLanguage.CHOOSE_DEVIANTART_FOLDER);
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
		return getSettings().getLanguageText(DefaultLanguage.DEVIANTART_PROGRESS_TITLE);
		
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
		
		eclipse = setEclipseValue(false);
		
	}//METHOD
	
	/**
	 * Sets DeviantArt to either use the Eclipse or the classic theme.
	 * 
	 * @param setToEclipse Whether to set to eclipse theme. If false, sets to classic theme.
	 * @return Whether DeviantArt was already set to the Eclipse theme
	 * @since 2.0
	 */
	private boolean setEclipseValue(final boolean setToEclipse)
	{
		boolean usesEclipse = false;
		List<DomElement> eclipseTest = getDownloader().getPage().getByXPath("//iframe[@id='eclipse-notifications-iframe']"); //$NON-NLS-1$
		if(eclipseTest.size() > 0)
		{
			usesEclipse = true;
			
		}//IF
		
		if((usesEclipse && !setToEclipse) || (!usesEclipse && setToEclipse))
		{
			this.setPage("https://www.deviantart.com/features/switch_version/https://www.deviantart.com/"); //$NON-NLS-1$
			
		}//IF
		
		return usesEclipse;
		
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
			progressDialog.setDetailLabel(getSettings().getLanguageText(DefaultLanguage.PAGE) + ' ' + pageNum, false);
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
		
		if(eclipse)
		{
			setEclipseValue(true);
			
		}//iF
		
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
			progressDialog.setDetailLabel(getSettings().getLanguageText(DefaultLanguage.PAGE) + ' ' + pageNum, false);
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
							
					if(!isDownloaded(linkString) && linkString.contains(JOURNAL_URL) && !linkString.contains(COMMENT_URL) && !pages.contains(linkString))
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
				
		if(eclipse)
		{
			setEclipseValue(true);
			
		}//iF
		
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
	protected String getUrlArtist(String artist)
	{
		return artist;
		
	}//METHOD
	
	@Override
	protected void downloadSinglePage(DProgressInfoDialog pid, String pageURL, File baseDirectory)
	{
	}//METHOD

	@Override
	protected void downloadPages(DProgressInfoDialog pid, String artist, ArrayList<String> pages)
	{
		
	}//METHOD

	@Override
	protected String downloadMediaPage(File baseFolder, String URL) throws Exception
	{
		return new String();
		
	}//METHOD

	@Override
	protected String downloadJournalPage(File baseFolder, String URL) throws Exception
	{
		return new String();
		
	}//METHOD

	@Override
	protected String getGalleryUrlFragment()
	{
		return GALLERY_URL;
		
	}//METHOD
	
}//CLASS
