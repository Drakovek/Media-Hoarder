package drakovek.hoarder.gui.modes;

import java.awt.BorderLayout;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.dvk.DvkHandler;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.settings.SettingsBarGUI;

/**
 * Contains methods for creating the main GUI to switch between different modes of operation.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ModeContainerGUI extends FrameGUI
{
	/**
	 * Main settings bar for the ModeContainerGUI
	 */
	private SettingsBarGUI settingsBar;
	
	/**
	 * Main ModeBaseGUI for choosing modes of operation.
	 */
	private ModeBaseGUI modeBaseGUI;
	
	/**
	 * Initializes the ModeContainerGUI Class.
	 * 
	 * @param settings Program Settings
	 * @param dvkHandler Program's DvkHandler
	 */
	public ModeContainerGUI(DSettings settings, DvkHandler dvkHandler)
	{
		super(settings, dvkHandler, null);

		settingsBar = new SettingsBarGUI(this);
		settingsBar.setLabelLoaded(dvkHandler.isLoaded());
		modeBaseGUI = new ModesGUI(this);
		getFrame().getContentPane().add(modeBaseGUI.getContentPanel(), BorderLayout.CENTER);
		getFrame().getContentPane().add(settingsBar.getPanel(), BorderLayout.SOUTH);
		getFrame().packRestricted();
		getFrame().setLocationRelativeTo(null);
		getFrame().setVisible(true);
	
	}//CONSTRUCTOR

	@Override
	public void event(String id, int value){}
	
	@Override
	public void enableAll()
	{
		settingsBar.enableAll();
		modeBaseGUI.enableAll();
		
	}//METHOD

	@Override
	public void disableAll()
	{
		settingsBar.disableAll();
		modeBaseGUI.disableAll();
		
	}//METHOD

}//CLASS
