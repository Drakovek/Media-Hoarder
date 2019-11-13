package drakovek.hoarder.gui.modes;

import drakovek.hoarder.file.language.ModeValues;
import drakovek.hoarder.gui.FrameGUI;

/**
 * Mode GUI for choosing between modes of managing DVKs
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ManageModeGUI extends ModeBaseGUI
{
	/**
	 * Initializes the ManageModeGUI class.
	 * 
	 * @param frameGUI FrameGUI this mode GUI is contained within.
	 */
	public ManageModeGUI(FrameGUI frameGUI)
	{
		super(frameGUI);
		String[] backIDs = {ModeValues.MODE_BACK, ModeValues.MODE_START};
		String[] modeIDs = {ModeValues.ERROR_MODE,
							ModeValues.REFORMAT_MODE};
		
		setContentPanel(backIDs, modeIDs);
		
	}//CONSTRUCTOR

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case ModeValues.REFORMAT_MODE:
				setContentPanel(new ReformatModeGUI(getParentGUI()));
				break;
			case ModeValues.ERROR_MODE:
				setContentPanel(new ErrorFindingModeGUI(getParentGUI()));
				break;
			case ModeValues.MODE_BACK:
			case ModeValues.MODE_START:
				setContentPanel(new ModesGUI(getParentGUI()));
				break;
				
		}//SWITCH
		
	}//METHOD
	
}//METHOD
