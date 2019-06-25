package drakovek.hoarder.gui.settings;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.listeners.DCloseListener;

/**
 * Contains methods for running a GUI for the user to change program settings.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class SettingsGUI extends BaseGUI
{
	/**
	 * Main frame for containing components to adjust the program settings.
	 * 
	 * @since 2.0
	 */
	private DFrame settingsFrame;
	
	/**
	 * FrameGUI that opened the settings GUI
	 * 
	 * @since 2.0
	 */
	private FrameGUI ownerGUI;
	
	/**
	 * Initializes the SettingsGUI class.
	 * 
	 * @param ownerGUI FrameGUI that opened the settings GUI
	 * @param settings Program Settings
	 */
	public SettingsGUI(FrameGUI ownerGUI, DSettings settings)
	{
		super(settings);
		this.ownerGUI = ownerGUI;
		
		settingsFrame = new DFrame(settings, settings.getLanuageText(DefaultLanguage.TITLE_VALUE));
		settingsFrame.interceptFrameClose(this);
		
		//BOTTOM FRAME
		DButton okButton = new DButton(this, DefaultLanguage.OK);
		DButton cancelButton = new DButton(this, DefaultLanguage.CANCEL);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints bottomCST = new GridBagConstraints();
		bottomCST.gridx = 2;		bottomCST.gridy = 2;
		bottomCST.gridwidth = 1;	bottomCST.gridheight = 1;
		bottomCST.weightx = 0;		bottomCST.weighty = 0;
		bottomCST.fill = GridBagConstraints.BOTH;
		bottomPanel.add(buttonPanel, bottomCST);
		bottomCST.gridy = 1;
		bottomPanel.add(getVerticalSpace(), bottomCST);
		bottomCST.gridx = 0;		bottomCST.gridwidth = 2;
		bottomCST.weightx = 1;
		bottomPanel.add(getHorizontalSpace(), bottomCST);
		bottomCST.gridy = 0;		bottomCST.gridwidth = 3;
		bottomPanel.add(new JSeparator(SwingConstants.HORIZONTAL), bottomCST);
		
		
		//FINALIZE FRAME
		settingsFrame.getContentPane().add(getSpacedPanel(bottomPanel, 1, 0, false, true, true, true), BorderLayout.SOUTH);
		settingsFrame.pack();
		settingsFrame.setLocationRelativeTo(ownerGUI.getFrame());
		ownerGUI.getFrame().setProcessRunning(true);
		settingsFrame.setVisible(true);
		
	}//CONSTRUCTOR

	/**
	 * Disposes the settings frame when done with changing settings.
	 * 
	 * @since 2.0
	 */
	private void dispose()
	{
		settingsFrame.dispose();
		ownerGUI.getFrame().setProcessRunning(false);
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DCloseListener.FRAME_CLOSE_EVENT:
			case DefaultLanguage.CANCEL:
				dispose();
				break;
				
		}//SWITCH
		
	}//METHOD
	
}//CLASS
