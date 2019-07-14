package drakovek.hoarder.gui.artist;

import java.io.File;
import java.util.List;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.html.DomAttr;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
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
	 * Initializes FurAffinityGUI class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public FurAffinityGUI(DSettings settings)
	{
		super(settings, new LoginGUI(settings, DefaultLanguage.FUR_AFFINITY_LOGIN, true), DefaultLanguage.FUR_AFFINITY_MODE, DefaultLanguage.CHOOSE_FUR_AFFINITY_FOLDER);

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
	public void login(final String username, final String password, final String Captcha)
	{
		
	}//METHOD

	@Override
	public boolean isLoggedIn()
	{
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
				file = new File(captchaFolder, "FAF" + Integer.toString(capNum) + ".jpg"); //$NON-NLS-1$ //$NON-NLS-2$
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
	
}//METHOD
