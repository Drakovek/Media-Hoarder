package drakovek.hoarder.gui.modes;

import java.awt.GridLayout;

import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.DButton;

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
		contentPanel.setLayout(new GridLayout(1,1));
		
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
	
	/**
	 * Refreshes the content panel to act as a mode GUI based on given button IDs.
	 * 
	 * @param modeIDs IDs for buttons to change the mode of operation.
	 * @since 2.0
	 */
	public void setContentPanel(String[] modeIDs)
	{
		contentPanel.removeAll();
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(modeIDs.length, 1, getSettings().getSpaceSize(), getSettings().getSpaceSize()));
		for(String modeID: modeIDs)
		{
			DButton modeButton = new DButton(this, modeID);
			buttonPanel.add(modeButton);
		
		}//FOR
		
		
		contentPanel.add(this.getSpacedPanel(buttonPanel, 1, 0, true, false, false, false));
		contentPanel.revalidate();
		
	}//METHOD
	
}//CLASS
