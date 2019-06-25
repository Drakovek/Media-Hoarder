package drakovek.hoarder.gui.settings;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.listeners.DCloseListener;

/**
 * Contains methods for running a GUI for the user to change program settings.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class SettingsGUI extends BaseGUI
{
	/**
	 * Main frame for containing components to adjust the program settings.
	 * 
	 * @since 2.0
	 */
	private DFrame settingsFrame;
	
	/**
	 * FrameGUI that opened the settings GUI
	 * 
	 * @since 2.0
	 */
	private FrameGUI ownerGUI;
	
	/**
	 * Initializes the SettingsGUI class.
	 * 
	 * @param ownerGUI FrameGUI that opened the settings GUI
	 * @param settings Program Settings
	 */
	public SettingsGUI(FrameGUI ownerGUI, DSettings settings)
	{
		super(settings);
		this.ownerGUI = ownerGUI;
		
		settingsFrame = new DFrame(settings, settings.getLanuageText(DefaultLanguage.TITLE_VALUE));
		settingsFrame.interceptFrameClose(this);
		settingsFrame.pack();
		settingsFrame.setLocationRelativeTo(ownerGUI.getFrame());
		ownerGUI.getFrame().setProcessRunning(true);
		settingsFrame.setVisible(true);
		
	}//CONSTRUCTOR

	/**
	 * Disposes the settings frame when done with changing settings.
	 * 
	 * @since 2.0
	 */
	private void dispose()
	{
		settingsFrame.dispose();
		ownerGUI.getFrame().setProcessRunning(false);
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DCloseListener.FRAME_CLOSE_EVENT:
				dispose();
				break;
				
		}//SWITCH
		
	}//METHOD
	
}//CLASS
