package drakovek.hoarder.gui.modes;

import java.awt.GridLayout;

import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.gui.BaseGUI;

/**
 * The base GUI object for panels that allow the user to switch between modes of operation.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public abstract class ModeBaseGUI extends BaseGUI
{
	/**
	 * Main Panel containing all the content for the Mode GUI.
	 * 
	 * @since 2.0
	 */
	private JPanel contentPanel;
	
	/**
	 * Initializes the ModeBaseGUI class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public ModeBaseGUI(DSettings settings)
	{
		super(settings);
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(1, 1));
		
	}//CONSTRUCTOR
	
	/**
	 * Returns the modeGUI's main content panel.
	 * 
	 * @return Content Panel
	 * @since 2.0
	 */
	public JPanel getContentPanel()
	{
		return contentPanel;
		
	}//METHOD
	
}//CLASS
