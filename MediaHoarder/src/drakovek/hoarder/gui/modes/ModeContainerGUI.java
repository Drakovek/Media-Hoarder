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
	 * Initializes the ModeContainerGUI Class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public ModeContainerGUI(DSettings settings)
	{
		super(settings, settings.getLanuageText(DefaultLanguage.TITLE_VALUE));

		SettingsBarGUI settingsBar = new SettingsBarGUI(this, settings);
		
		getFrame().getContentPane().add((new ModesGUI(settings)).getContentPanel(), BorderLayout.CENTER);
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
	public void enableAll() {}

	@Override
	public void disableAll() {}

}//CLASS
