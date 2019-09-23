package drakovek.hoarder.gui.modes;

import drakovek.hoarder.file.language.ModeValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.view.ViewBrowserGUI;

/**
 * The starting ModeGUI for choosing between broad operations categories
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ModesGUI extends ModeBaseGUI
{
	/**
	 * Initializes the ModesGUI object.
	 * 
	 * @param frameGUI FrameGUI this mode GUI is contained within.
	 */
	public ModesGUI(FrameGUI frameGUI)
	{
		super(frameGUI);
		String[] backIDs = {ModeValues.MODE_BACK, ModeValues.MODE_START};
		String[] modeIDs = {ModeValues.DOWNLOAD_MODE, ModeValues.MANAGE_MODE, ModeValues.VIEW_MODE};
		setContentPanel(backIDs, modeIDs);
		
	}//CONSTRUCTOR

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case ModeValues.DOWNLOAD_MODE:
				setContentPanel(new DownloadModeGUI(getParentGUI()));
				break;
			case ModeValues.MANAGE_MODE:
				setContentPanel(new ManageModeGUI(getParentGUI()));
				break;
			case ModeValues.VIEW_MODE:
				new ViewBrowserGUI(getSettings(), getParentGUI().getDmfHandler());
				getParentGUI().dispose();
				break;
			
		}//SWITCH
		
	}//METHOD
	
}//CLASS
