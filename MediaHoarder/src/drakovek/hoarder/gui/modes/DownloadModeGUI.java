package drakovek.hoarder.gui.modes;

import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.artist.DeviantArtGUI;

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
			{
				new DeviantArtGUI(getSettings());
				getParentGUI().dispose();
				break;
				
			}//CASE
			case DefaultLanguage.FUR_AFFINITY_MODE:
			{
				break;
				
			}//CASE
			case DefaultLanguage.INKBUNNY_MODE:
			{
				break;
				
			}//CASE	
			case DefaultLanguage.TRANSFUR_MODE:
			{
				break;
				
			}//CASE
			case DefaultLanguage.MODE_BACK:
			case DefaultLanguage.MODE_START:
			{
				setContentPanel(new ModesGUI(getParentGUI()));
				break;
				
			}//CASE
			
		}//SWITCH
		
	}//METHOD

}//CLASS
