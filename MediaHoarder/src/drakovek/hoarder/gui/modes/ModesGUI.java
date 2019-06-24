package drakovek.hoarder.gui.modes;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;

/**
 * The starting ModeGUI for choosing between broad operations categories
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ModesGUI extends ModeBaseGUI
{
	/**
	 * Initializes the ModesGUI object.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public ModesGUI(DSettings settings)
	{
		super(settings);
		String[] backIDs = {DefaultLanguage.MODE_BACK, DefaultLanguage.MODES};
		String[] modeIDs = {DefaultLanguage.DOWNLOAD_MODE, DefaultLanguage.MANAGE_MODE, DefaultLanguage.VIEW_MODE};
		setContentPanel(backIDs, modeIDs);
		
	}//CONSTRUCTOR

	@Override
	public void event(String id, int value)
	{

	}//METHOD
	
}//CLASS
