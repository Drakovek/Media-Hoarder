package drakovek.hoarder.gui.modes;

import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.editing.SequencingGUI;

/**
 * Mode GUI for choosing between modes of managing DMFs
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ManageModeGUI extends ModeBaseGUI
{
	/**
	 * Initializes the ManageModeGUI class.
	 * 
	 * @param frameGUI FrameGUI this mode GUI is contained within.
	 * @since 2.0
	 */
	public ManageModeGUI(FrameGUI frameGUI)
	{
		super(frameGUI);
		String[] backIDs = {DefaultLanguage.MODE_BACK, DefaultLanguage.MODE_START};
		String[] modeIDs = {DefaultLanguage.SEQUENCE_MODE,
							DefaultLanguage.ERROR_MODE,
							DefaultLanguage.REFORMAT_MODE};
		
		setContentPanel(backIDs, modeIDs);
		
	}//CONSTRUCTOR

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.SEQUENCE_MODE:
				new SequencingGUI(getSettings(), getParentGUI().getDmfHandler());
				getParentGUI().dispose();
				break;
			case DefaultLanguage.REFORMAT_MODE:
				setContentPanel(new ReformatModeGUI(getParentGUI()));
				break;
			case DefaultLanguage.ERROR_MODE:
				setContentPanel(new ErrorFindingModeGUI(getParentGUI()));
				break;
			case DefaultLanguage.MODE_BACK:
			case DefaultLanguage.MODE_START:
				setContentPanel(new ModesGUI(getParentGUI()));
				break;
				
		}//SWITCH
		
	}//METHOD
	
}//METHOD
