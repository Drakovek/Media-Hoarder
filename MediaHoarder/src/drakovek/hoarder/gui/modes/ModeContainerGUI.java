package drakovek.hoarder.gui.modes;

import java.awt.BorderLayout;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.settings.SettingsBarGUI;
import drakovek.hoarder.gui.swing.components.ComponentDisabler;
import drakovek.hoarder.gui.swing.components.DFrame;

/**
 * Contains methods for creating the main GUI to switch between different modes of operation.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ModeContainerGUI extends BaseGUI implements ComponentDisabler
{
	/**
	 * Initializes the ModeContainerGUI Class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public ModeContainerGUI(DSettings settings)
	{
		super(settings);
		
		DFrame containerFrame = new DFrame(this, settings, settings.getLanuageText(DefaultLanguage.TITLE_VALUE));
		SettingsBarGUI settingsBar = new SettingsBarGUI(containerFrame, settings);
		
		containerFrame.getContentPane().add((new ModesGUI(settings)).getContentPanel(), BorderLayout.CENTER);
		containerFrame.getContentPane().add(settingsBar.getPanel(), BorderLayout.SOUTH);
		containerFrame.pack();
		containerFrame.setLocationRelativeTo(null);
		containerFrame.setVisible(true);
	
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
