package drakovek.hoarder.gui.artist;

import java.io.File;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;

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
	public void login(String username, String password)
	{
		
	}//METHOD

	@Override
	public boolean isLoggedIn()
	{
		return false;
		
	}//METHOD
	
}//METHOD
