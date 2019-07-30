package drakovek.hoarder.gui.artist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
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
		progressDialog.appendLog(null);
		String urlArtist = artist.replaceAll(Character.toString('_'), new String());
		
		//GETS PAGES
		progressDialog.appendLog(artist + DProgressInfoDialog.SPACER + getSettings().getLanguageText(DefaultLanguage.GETTING_GALLERY_PAGES));
		ArrayList<String> pages = getMediaPages(progressDialog, urlArtist, checkAll, false);
		progressDialog.appendLog(artist + DProgressInfoDialog.SPACER + getSettings().getLanguageText(DefaultLanguage.GETTING_SCRAP_PAGES));
		pages.addAll(getMediaPages(progressDialog, urlArtist, checkAll, true));
		
		if(checkJournals)
		{
			progressDialog.appendLog(artist + DProgressInfoDialog.SPACER + getSettings().getLanguageText(DefaultLanguage.GETTING_JOURNAL_PAGES));
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
		for(int i = 0; !progressDialog.isCancelled() && i < pages.size(); i++)
		{
			progressDialog.setProcessLabel(DefaultLanguage.LOADING_PAGE);
			progressDialog.setDetailLabel(pages.get(i).substring(pages.get(i).lastIndexOf('/', pages.get(i).length() - 2)), false);
			progressDialog.setProgressBar(false, true, pages.size(), i);
			progressDialog.appendLog(getSettings().getLanguageText(DefaultLanguage.LOADING_PAGE) + pages.get(i));
			
		}//FOR
		
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
