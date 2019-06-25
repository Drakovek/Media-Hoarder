package drakovek.hoarder.gui.modes;

import java.awt.BorderLayout;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.settings.SettingsBarGUI;

/**
 * Contains methods for creating the main GUI to switch between different modes of operation.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ModeContainerGUI extends FrameGUI
{
	/**
	 * Main settings bar for the ModeContainerGUI
	 * 
	 * @since 2.0
	 */
	private SettingsBarGUI settingsBar;
	
	/**
	 * Main ModeBaseGUI for choosing modes of operation.
	 * 
	 * @since 2.0
	 */
	private ModeBaseGUI modeBaseGUI;
	
	/**
	 * Initializes the ModeContainerGUI Class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public ModeContainerGUI(DSettings settings)
	{
		super(settings, settings.getLanuageText(DefaultLanguage.TITLE_VALUE));

		settingsBar = new SettingsBarGUI(this, settings);
		
		modeBaseGUI = new ModesGUI(settings);
		getFrame().getContentPane().add(modeBaseGUI.getContentPanel(), BorderLayout.CENTER);
		getFrame().getContentPane().add(settingsBar.getPanel(), BorderLayout.SOUTH);
		getFrame().pack();
		getFrame().setLocationRelativeTo(null);
		getFrame().setVisible(true);
	
	}//CONSTRUCTOR

	@Override
	public void event(String id, int value)
	{
		
	}//METHOD
	
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
