package drakovek.hoarder.gui.settings;

import javax.swing.JPanel;

/**
 * Contains methods for creating a settings bar.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class SettingsBarGUI
{
	/**
	 * JPanel for the settingsBar.
	 * 
	 * @since 2.0
	 */
	private JPanel barPanel;
	
	/**
	 * Initializes the SettingsBarGUI class.
	 * 
	 * @since 2.0
	 */
	public SettingsBarGUI()
	{
		barPanel = new JPanel();
		
	}//METHOD
	
	/**
	 * Returns the settings bar.
	 * 
	 * @return Settings Bar
	 * @since 2.0
	 */
	public JPanel getPanel()
	{
		return barPanel;
		
	}//METHOD
	
}//CLASS
