package drakovek.hoarder.gui.artist;

import java.io.File;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;

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
	 * Initializes DeviantArtGUI class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public DeviantArtGUI(DSettings settings)
	{
		super(settings, new LoginGUI(settings, DefaultLanguage.DEVIANTART_LOGIN, false), DefaultLanguage.DEVIANTART_MODE, DefaultLanguage.CHOOSE_DEVIANTART_FOLDER);
		
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
	public void login(String username, String password)
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
	
}//CLASS
