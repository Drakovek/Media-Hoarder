package drakovek.hoarder.gui.modes;

import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;

/**
 * Mode GUI for choosing between download modes.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DownloadModeGUI extends ModeBaseGUI
{
	/**
	 * Initializes the ModesGUI object.
	 * 
	 * @param frameGUI FrameGUI this mode GUI is contained within.
	 * @since 2.0
	 */
	public DownloadModeGUI(FrameGUI frameGUI)
	{
		super(frameGUI);
		String[] backIDs = {DefaultLanguage.MODE_BACK, DefaultLanguage.MODE_START};
		String[] modeIDs = {DefaultLanguage.DEVIANTART_MODE,
							DefaultLanguage.FUR_AFFINITY_MODE,
							DefaultLanguage.INKBUNNY_MODE,
							DefaultLanguage.TRANSFUR_MODE};
		
		setContentPanel(backIDs, modeIDs);
		
	}//CONSTRUCTOR
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.DEVIANTART_MODE:
				break;
			case DefaultLanguage.FUR_AFFINITY_MODE:
				break;
			case DefaultLanguage.INKBUNNY_MODE:
				break;
			case DefaultLanguage.TRANSFUR_MODE:
				break;
			case DefaultLanguage.MODE_BACK:
			case DefaultLanguage.MODE_START:
				setContentPanel(new ModesGUI(getParentGUI()));
				break;
		}//SWITCH
		
	}//METHOD

}//CLASS
