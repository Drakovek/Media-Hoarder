package drakovek.hoarder.gui.artist;

import java.io.File;

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
		super(settings, new LoginGUI(settings, DefaultLanguage.DEVIANTART_LOGIN), DefaultLanguage.DEVIANTART_MODE, DefaultLanguage.CHOOSE_DEVIANTART_FOLDER);
		
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
	
}//CLASS
