package drakovek.hoarder.gui.modes;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.ComponentDisabler;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DScrollPane;

/**
 * The base GUI object for panels that allow the user to switch between modes of operation.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public abstract class ModeBaseGUI extends BaseGUI implements ComponentDisabler
{
	/**
	 * Main Panel containing all the content for the Mode GUI.
	 * 
	 * @since 2.0
	 */
	private JPanel contentPanel;
	
	/**
	 * DButtons used to return to previous Mode GUIs
	 * 
	 * @since 2.0
	 */
	private DButton[] backButtons;
	
	/**
	 * DButtons used to change the mode of operation.
	 * 
	 * @since 2.0
	 */
	private DButton[] modeButtons;
	
	/**
	 * ModeBaseGUI to pass commands to if it is opened.
	 * 
	 * @since 2.0
	 */
	private ModeBaseGUI modeBaseGUI;
	
	/**
	 * FrameGUI this mode GUI is contained within.
	 * 
	 * @since 2.0
	 */
	private FrameGUI frameGUI;
	
	/**
	 * Initializes the ModeBaseGUI class.
	 * 
	 * @param frameGUI FrameGUI this mode GUI is contained within.
	 * @since 2.0
	 */
	public ModeBaseGUI(FrameGUI frameGUI)
	{
		super(frameGUI.getSettings());
		
		this.frameGUI = frameGUI;
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(1,1));
		backButtons = new DButton[0];
		modeButtons = new DButton[0];
		modeBaseGUI = null;
		
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
		backButtons = new DButton[backIDs.length];
		for(int i = 0; i < backIDs.length; i++)
		{
			backButtons[i] = new DButton(this, backIDs[i]);
			backButtonPanel.add(backButtons[i]);
			
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
		modeButtons = new DButton[modeIDs.length];
		for(int i = 0; i < modeIDs.length; i++)
		{
			modeButtons[i] = new DButton(this, modeIDs[i]);
			modeButtons[i].setFontLarge();
			modePanel.add(modeButtons[i]);
		
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
	
	/**
	 * Refreshes the content panel with a separate mode GUI
	 * 
	 * @param modeBaseGUI Mode GUI to replace this mode GUI
	 * @since 2.0
	 */
	public void setContentPanel(ModeBaseGUI modeBaseGUI)
	{
		this.modeBaseGUI = modeBaseGUI;
		contentPanel.removeAll();
		contentPanel.add(modeBaseGUI.getContentPanel());
		contentPanel.revalidate();
		
	}//METHOD
	
	@Override
	public void enableAll()
	{
		if(modeBaseGUI == null)
		{
			for(int i = 0; i < backButtons.length; i++)
			{
				backButtons[i].setEnabled(true);
				
			}//FOR
			
			for(int i = 0; i < modeButtons.length; i++)
			{
				modeButtons[i].setEnabled(true);
				
			}//FOR
			
		}//IF
		else
		{
			modeBaseGUI.enableAll();
			
		}//ELSE
		
	}//METHOD

	@Override
	public void disableAll()
	{
		if(modeBaseGUI == null)
		{
			for(int i = 0; i < backButtons.length; i++)
			{
				backButtons[i].setEnabled(false);
				
			}//FOR
			
			for(int i = 0; i < modeButtons.length; i++)
			{
				modeButtons[i].setEnabled(false);
				
			}//FOR
			
		}//IF
		else
		{
			modeBaseGUI.disableAll();
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns the parent FrameGUI
	 * 
	 * @return Parent FrameGUI
	 * @since 2.0
	 */
	public FrameGUI getParentGUI()
	{
		return frameGUI;
		
	}//METHOD
	
}//CLASS
