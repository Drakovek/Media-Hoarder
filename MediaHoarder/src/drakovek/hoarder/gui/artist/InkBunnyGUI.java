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
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
import drakovek.hoarder.web.Downloader;

/**
 * Creates GUI for downloading files from Inkbunny.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class InkBunnyGUI extends ArtistHostingGUI
{ 
	/**
	 * Prefix for a DMF ID that indicates that the DMF is sourced from Inkbunny.net
	 * 
	 * @since 2.0
	 */
	private static final String ID_PREFIX = "INK"; //$NON-NLS-1$
	
	/**
	 * Section of Inkbunny page URL that shows it is part of a media gallery
	 * 
	 * @since 2.0
	 */
	private static final String GALLERY_URL = "/s/"; //$NON-NLS-1$
	
	/**
	 * Section of Inkbunny page URL that shows it is part of a journal gallery
	 * 
	 * @since 2.0
	 */
	private static final String JOURNAL_URL = "/j/"; //$NON-NLS-1$
	
	/**
	 * ArrayList of modified Inkbunny IDs to check if media has already been downloaded
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> idStrings;
	
	/**
	 * Initializes InkBunnyGUI class.
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHandler
	 * @since 2.0
	 */
	public InkBunnyGUI(DSettings settings, DmfHandler dmfHandler)
	{
		super(settings, dmfHandler, new LoginGUI(settings, DefaultLanguage.INKBUNNY_LOGIN, false), DefaultLanguage.INKBUNNY_MODE, DefaultLanguage.CHOOSE_INKBUNNY_FOLDER);
		idStrings = new ArrayList<>();
		
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
		return getSettings().getLanguageText(DefaultLanguage.INKBUNNY_PROGRESS_TITLE);
		
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
			if(id.length() > prefixLength && id.toUpperCase().startsWith(ID_PREFIX))
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
			progressDialog.setDetailLabel(getSettings().getLanguageText(DefaultLanguage.PAGE) + ' ' + pageNum, false);
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
			progressDialog.setDetailLabel(getSettings().getLanguageText(DefaultLanguage.PAGE) + ' ' + pageNum, false);
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
	protected boolean isDownloaded(String URL)
	{
		String page = URL;
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
		
		if(page.contains(GALLERY_URL))
		{	
			return idStrings.contains(page);
			
		}//IF
		else if(page.contains(JOURNAL_URL))
		{
			page = page + JOURNAL_SUFFIX;
			return idStrings.contains(page);
			
		}//IF
		
		return true;
		
	}//METHOD

}//CLASS
