package drakovek.hoarder.gui.modes;

import java.awt.BorderLayout;
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
	 * @param backIDs IDs for buttons to return to previous Mode GUIs
	 * @param modeIDs IDs for buttons to change the mode of operation
	 * @since 2.0
	 */
	public void setContentPanel(String backIDs[], String[] modeIDs)
	{
		contentPanel.removeAll();
		
		//BACK PANEL
		JPanel backPanel = new JPanel();
		backPanel.setLayout(new GridLayout(1, backIDs.length, getSettings().getSpaceSize(), 1));
		for(String backID: backIDs)
		{
			DButton backButton = new DButton(this, backID);
			backPanel.add(backButton);
			
		}//FOR
		
		//MODE PANEL
		JPanel modePanel = new JPanel();
		modePanel.setLayout(new GridLayout(modeIDs.length, 1, 1, getSettings().getSpaceSize()));
		for(String modeID: modeIDs)
		{
			DButton modeButton = new DButton(this, modeID);
			modePanel.add(modeButton);
		
		}//FOR
		
		//FULL PANEL
		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new BorderLayout());
		fullPanel.add(backPanel, BorderLayout.NORTH);
		fullPanel.add(getSpacedPanel(modePanel, 1, 0, true, false, false, false), BorderLayout.CENTER);
		
		contentPanel.add(fullPanel);
		contentPanel.revalidate();
		
	}//METHOD
	
}//CLASS
