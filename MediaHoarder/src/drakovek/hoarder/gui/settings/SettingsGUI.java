package drakovek.hoarder.gui.settings;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.ComponentDisabler;
import drakovek.hoarder.gui.swing.components.DFrame;

/**
 * Contains methods for running a GUI for the user to change program settings.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class SettingsGUI extends BaseGUI implements ComponentDisabler
{
	/**
	 * Main frame for containing components to adjust the program settings.
	 * 
	 * @since 2.0
	 */
	private DFrame settingsFrame;
	
	/**
	 * Initializes the SettingsGUI class.
	 * 
	 * @param owner Frame that opened the settings GUI
	 * @param settings Program Settings
	 */
	public SettingsGUI(DFrame owner, DSettings settings)
	{
		super(settings);
		settingsFrame = new DFrame(this, settings, settings.getLanuageText(DefaultLanguage.TITLE_VALUE));
		settingsFrame.pack();
		settingsFrame.setLocationRelativeTo(owner);
		settingsFrame.setVisible(true);
		
	}//CONSTRUCTOR

	@Override
	public void event(String id, int value)
	{
		// TODO Auto-generated method stub
		
	}//METHOD

	@Override
	public void enableAll()
	{
		// TODO Auto-generated method stub
		
	}//METHOD

	@Override
	public void disableAll()
	{
		// TODO Auto-generated method stub
		
	}//METHOD
	
}//CLASS
