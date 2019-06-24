package drakovek.hoarder.gui.modes;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DScrollPane;

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
		JPanel backButtonPanel = new JPanel();
		backButtonPanel.setLayout(new GridLayout(1, backIDs.length, getSettings().getSpaceSize(), 1));
		for(String backID: backIDs)
		{
			DButton backButton = new DButton(this, backID);
			backButtonPanel.add(backButton);
			
		}//FOR
		
		JPanel backPanel = new JPanel();
		backPanel.setLayout(new GridBagLayout());
		GridBagConstraints backCST = new GridBagConstraints();
		backCST.gridx = 0;		backCST.gridy = 0;
		backCST.gridwidth = 1;	backCST.gridheight = 1;
		backCST.weightx = 0;	backCST.weighty = 0;
		backCST.fill = GridBagConstraints.BOTH;
		backPanel.add(backButtonPanel, backCST);
		backCST.gridy = 1;
		backPanel.add(getVerticalSpace(), backCST);
		backCST.gridx = 2;		backCST.gridy = 2;
		backPanel.add(getHorizontalSpace(), backCST);
		backCST.gridx = 1;		backCST.gridy = 0;
		backCST.weightx = 1;
		backPanel.add(getVerticalSpace(), backCST);
		backCST.gridx = 0;		backCST.gridy = 2;
		backCST.gridwidth = 2;
		backPanel.add(new JSeparator(SwingConstants.HORIZONTAL), backCST);
		
		//MODE PANEL
		JPanel modePanel = new JPanel();
		modePanel.setLayout(new GridLayout(modeIDs.length, 1, 1, getSettings().getSpaceSize()));
		for(String modeID: modeIDs)
		{
			DButton modeButton = new DButton(this, modeID);
			modeButton.setFontLarge();
			modePanel.add(modeButton);
		
		}//FOR
		
		//FULL PANEL
		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new BorderLayout());
		DScrollPane modeScroll = new DScrollPane(getSettings(), getSpacedPanel(modePanel, 1, 0, true, true, true, true));
		fullPanel.add(getSpacedPanel(backPanel, 1, 0, true, true, true, false), BorderLayout.NORTH);
		fullPanel.add(getSpacedPanel(modeScroll, 1, 1, false, false, true, true), BorderLayout.CENTER);
		
		contentPanel.add(fullPanel);
		contentPanel.revalidate();
		
	}//METHOD
	
}//CLASS
