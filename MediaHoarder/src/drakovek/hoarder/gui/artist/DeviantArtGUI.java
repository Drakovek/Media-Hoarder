package drakovek.hoarder.gui.artist;

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
		super(settings, DefaultLanguage.DEVIANTART_MODE);
		
	}//CONSTRUCTOR
	
}//CLASS
