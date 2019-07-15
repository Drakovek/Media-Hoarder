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
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.listeners.DCloseListener;
import drakovek.hoarder.processing.StringMethods;

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
	 * List of action/language IDs for the possible settings modes the user can choose.
	 * 
	 * @since 2.0
	 */
	private static final String[] settingsEvents = {DefaultLanguage.LANGUAGE, DefaultLanguage.THEME, DefaultLanguage.FONT};
	
	/**
	 * FrameGUI that opened the settings GUI
	 * 
	 * @since 2.0
	 */
	private FrameGUI ownerGUI;
	
	/**
	 * The main frame for the settings GUI
	 * 
	 * @since 2.0
	 */
	private DFrame frame;
	
	/**
	 * Panel to contain GUI for changing settings
	 * 
	 * @since 2.0
	 */
	private JPanel contentPanel;
	
	/**
	 * List to select the current settings mode
	 * 
	 * @since 2.0
	 */
	private DList settingsList;
	
	/**
	 * Initializes the SettingsGUI
	 * 
	 * @param ownerGUI FrameGUI that opened the settings GUI
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public SettingsGUI(FrameGUI ownerGUI, DSettings settings)
	{
		super(settings);
		this.ownerGUI = ownerGUI;
		ownerGUI.getFrame().setAllowExit(false);
		frame = new DFrame(settings, DefaultLanguage.SETTINGS);
		frame.interceptFrameClose(this);
		
		//CREATE TOP PANEL
		DLabel settingsLabel = new DLabel(this, null, DefaultLanguage.SETTINGS);
		settingsLabel.setFontLarge();
		JPanel topPanel = getVerticalStack(settingsLabel, new JSeparator(SwingConstants.HORIZONTAL));
		
		//CREATE BOTTOM PANEL
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		buttonPanel.add(new DButton(this, DefaultLanguage.CANCEL));
		buttonPanel.add(new DButton(this, DefaultLanguage.SAVE));
		
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
		bottomCST.gridx = 0;		bottomCST.gridy = 2;
		bottomCST.gridwidth = 2;	bottomCST.weightx = 1;
		bottomPanel.add(getHorizontalSpace(), bottomCST);
		bottomCST.gridy = 0;		bottomCST.gridwidth = 3;
		bottomPanel.add(new JSeparator(SwingConstants.HORIZONTAL), bottomCST);
		
		//CREATE CENTER PANEL
		String[] listData = new String[settingsEvents.length];
		for(int i = 0; i < listData.length; i++)
		{
			listData[i] = settings.getLanuageText(settingsEvents[i]) + StringMethods.extendCharacter(' ', 5);
		
		}//FOR
		
		settingsList = new DList(this, false, DefaultLanguage.SETTINGS);
		settingsList.setListData(listData);
		DScrollPane settingsScroll = new DScrollPane(settings, settingsList);
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(1, 1));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints centerCST = new GridBagConstraints();
		centerCST.gridx = 0;		centerCST.gridy = 0;
		centerCST.gridwidth = 1;	centerCST.gridheight = 3;
		centerCST.weightx = 0;		centerCST.weighty = 1;
		centerCST.fill = GridBagConstraints.BOTH;
		centerPanel.add(settingsScroll, centerCST);
		centerCST.gridx = 1;
		centerPanel.add(getHorizontalSpace(), centerCST);
		centerCST.gridx = 1;		centerCST.weightx = 1;
		centerPanel.add(contentPanel, centerCST);
		
		//FINALIZE FRAME
		frame.getContentPane().add(getSpacedPanel(centerPanel), BorderLayout.CENTER);
		frame.getContentPane().add(this.getSpacedPanel(topPanel, 1, 0, true, false, true, true), BorderLayout.NORTH);
		frame.getContentPane().add(getSpacedPanel(bottomPanel, 1, 0, false, true, true, true), BorderLayout.SOUTH);
		frame.pack();
		frame.setLocationRelativeTo(ownerGUI.getFrame());
		frame.setVisible(true);
		
	}//CONSTRUCTOR
	
	/**
	 * Disposes of the main settings frame.
	 * 
	 * @since 2.0
	 */
	private void dispose()
	{
		frame.dispose();
		frame = null;
		ownerGUI.getFrame().setAllowExit(true);
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DCloseListener.FRAME_CLOSE_EVENT:
			case DefaultLanguage.CANCEL:
			{
				dispose();
				break;
			
			}//CASE
		
		}//SWITCH
		
	}//METHOD
	
}//CLASS
