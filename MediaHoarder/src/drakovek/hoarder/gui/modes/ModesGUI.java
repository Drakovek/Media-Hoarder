package drakovek.hoarder.gui.modes;

import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.view.ViewBrowserGUI;

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
	 * @param frameGUI FrameGUI this mode GUI is contained within.
	 * @since 2.0
	 */
	public ModesGUI(FrameGUI frameGUI)
	{
		super(frameGUI);
		String[] backIDs = {DefaultLanguage.MODE_BACK, DefaultLanguage.MODE_START};
		String[] modeIDs = {DefaultLanguage.DOWNLOAD_MODE, DefaultLanguage.MANAGE_MODE, DefaultLanguage.VIEW_MODE};
		setContentPanel(backIDs, modeIDs);
		
	}//CONSTRUCTOR

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.DOWNLOAD_MODE:
			{
				setContentPanel(new DownloadModeGUI(getParentGUI()));
				break;
				
			}//CASE
			case DefaultLanguage.MANAGE_MODE:
			{
				break;
				
			}//CASE
			case DefaultLanguage.VIEW_MODE:
			{
				new ViewBrowserGUI(getSettings());
				getParentGUI().dispose();
				break;
				
			}//CASE
			
		}//SWITCH
		
	}//METHOD
	
}//CLASS
