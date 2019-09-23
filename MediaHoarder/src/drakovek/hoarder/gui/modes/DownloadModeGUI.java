package drakovek.hoarder.gui.modes;

import drakovek.hoarder.file.language.ModeValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.artist.DeviantArtGUI;
import drakovek.hoarder.gui.artist.FurAffinityGUI;
import drakovek.hoarder.gui.artist.InkBunnyGUI;

/**
 * Mode GUI for choosing between download modes.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DownloadModeGUI extends ModeBaseGUI
{
	/**
	 * Initializes the ModesGUI object.
	 * 
	 * @param frameGUI FrameGUI this mode GUI is contained within.
	 */
	public DownloadModeGUI(FrameGUI frameGUI)
	{
		super(frameGUI);
		String[] backIDs = {ModeValues.MODE_BACK, ModeValues.MODE_START};
		String[] modeIDs = {ModeValues.DEVIANTART_MODE,
							ModeValues.FUR_AFFINITY_MODE,
							ModeValues.INKBUNNY_MODE,
							ModeValues.TRANSFUR_MODE};
		
		setContentPanel(backIDs, modeIDs);
		
	}//CONSTRUCTOR
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case ModeValues.DEVIANTART_MODE:
				new DeviantArtGUI(getSettings(), getParentGUI().getDmfHandler());
				getParentGUI().dispose();
				break;
			case ModeValues.FUR_AFFINITY_MODE:
				new FurAffinityGUI(getSettings(), getParentGUI().getDmfHandler());
				getParentGUI().dispose();
				break;
			case ModeValues.INKBUNNY_MODE:
				new InkBunnyGUI(getSettings(), getParentGUI().getDmfHandler());
				getParentGUI().dispose();
				break;
			case ModeValues.TRANSFUR_MODE:
				break;
			case ModeValues.MODE_BACK:
			case ModeValues.MODE_START:
				setContentPanel(new ModesGUI(getParentGUI()));
				break;
			
		}//SWITCH
		
	}//METHOD

}//CLASS
