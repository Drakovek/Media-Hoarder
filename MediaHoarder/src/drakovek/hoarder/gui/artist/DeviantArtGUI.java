package drakovek.hoarder.gui.artist;

import java.io.File;
import java.util.ArrayList;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;

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
	 * @param dmfHandler Program's DmfHandler
	 * @since 2.0
	 */
	public DeviantArtGUI(DSettings settings, DmfHandler dmfHandler)
	{
		super(settings, dmfHandler, new LoginGUI(settings, DefaultLanguage.DEVIANTART_LOGIN, false), DefaultLanguage.DEVIANTART_MODE, DefaultLanguage.CHOOSE_DEVIANTART_FOLDER);
		
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
		
	}//METHOD

	@Override
	public boolean isLoggedIn()
	{
		return true;
		
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

	@Override
	protected ArrayList<String> getPages(DProgressInfoDialog progressDialog, final String artist, final boolean checkAll, final boolean checkJournals)
	{
		ArrayList<String> pages = new ArrayList<>();
		return pages;
		
	}//METHOD

	@Override
	protected void downloadPages(DProgressInfoDialog progressDialog, final String artist, final ArrayList<String> pages)
	{
		
	}//METHOD

	@Override
	protected void getIdStrings()
	{
	}//METHOD

	@Override
	protected void downloadSinglePage(DProgressInfoDialog pid, String pageURL, File baseDirectory)
	{
	}
	
}//CLASS
