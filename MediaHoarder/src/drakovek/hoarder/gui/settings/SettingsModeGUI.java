package drakovek.hoarder.gui.settings;

import javax.swing.JPanel;

import drakovek.hoarder.gui.BaseGUI;

/**
 * Contains base methods for creating a panel to set settings in the Settings GUI
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public abstract class SettingsModeGUI extends BaseGUI
{
	/**
	 * Main panel for the mode GUI
	 * 
	 * @since 2.0
	 */
	private JPanel panel;
	
	/**
	 * Parent SettingsGUI
	 * 
	 * @since 2.0
	 */
	private SettingsGUI settingsGUI;
	
	/**
	 * Initializes the SettingsModeGUI class.
	 * 
	 * @param settingsGUI Parent SettingsGUI
	 * @since 2.0
	 */
	public SettingsModeGUI(SettingsGUI settingsGUI)
	{
		super(settingsGUI.getSettings());
		this.settingsGUI = settingsGUI;
		settingsGUI.applyDisable();
		panel = new JPanel();
		
	}//CONSTRUCTOR
	
	/**
	 * Returns the parent SettingsGUI
	 * 
	 * @return SettingsGUI
	 * @since 2.0
	 */
	public SettingsGUI getSettingsGUI()
	{
		return settingsGUI;
		
	}//METHOD
	
	/**
	 * Returns the main settings panel
	 * 
	 * @return Settings Panel
	 * @since 2.0
	 */
	public JPanel getPanel()
	{
		return panel;
		
	}//METHOD
	
	/**
	 * Method called when to apply the current settings edited in the settings panel
	 * 
	 * @since 2.0
	 */
	public abstract void apply();

}//CLASS
