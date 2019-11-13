package drakovek.hoarder.gui.settings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.Start;
import drakovek.hoarder.file.dvk.DvkHandler;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.SettingsValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.DButton;
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
 */
public class SettingsGUI extends FrameGUI
{
	/**
	 * List of action/language IDs for the possible settings modes the user can choose.
	 */
	private static final String[] SETTINGS_EVENTS = {SettingsValues.LANGUAGE, SettingsValues.DVK_DIRECTORIES, SettingsValues.THEME, SettingsValues.FONT};
	
	/**
	 * FrameGUI that opened the settings GUI
	 */
	private FrameGUI ownerGUI;
	
	/**
	 * GUI for setting particular settings to include within the main settings GUI
	 */
	private SettingsModeGUI modeGUI;
	
	/**
	 * Panel to contain GUI for changing settings
	 */
	private JPanel contentPanel;
	
	/**
	 * List to select the current settings mode
	 */
	private DList settingsList;
	
	/**
	 * Button for applying the currently edited settings
	 */
	private DButton applyButton;
	
	/**
	 * Button for applying the currently edited settings, then closing the settings GUI
	 */
	private DButton saveButton;
	
	/**
	 * Boolean determining if any settings have changed
	 */
	private boolean changed;
	
	/**
	 * Initializes the SettingsGUI
	 * 
	 * @param settings Program Settings
	 * @param dvkHandler Program's DvkHandler
	 * @param ownerGUI FrameGUI that opened the settings GUI, if applicable
	 */
	public SettingsGUI(DSettings settings, DvkHandler dvkHandler, FrameGUI ownerGUI)
	{
		super(settings, dvkHandler, SettingsValues.SETTINGS);
		this.ownerGUI = ownerGUI;
		if(ownerGUI != null)
		{
			ownerGUI.getFrame().setAllowExit(false);
			
		}//IF
		
		getFrame().setSizeRestrictive(getSettings().getFontSize() * 40, getSettings().getFontSize() * 30);
		getFrame().interceptFrameClose(this);
		modeGUI = null;
		changed = false;
		
		//CREATE BOTTOM PANEL
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3, settings.getSpaceSize(), 0));
		buttonPanel.add(new DButton(this, CommonValues.CLOSE));
		applyButton = new DButton(this, SettingsValues.APPLY);
		buttonPanel.add(applyButton);
		saveButton = new DButton(this, CommonValues.SAVE);
		buttonPanel.add(saveButton);
		
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
		
		//CREATE MODE PANEL
		String[] listData = new String[SETTINGS_EVENTS.length];
		for(int i = 0; i < listData.length; i++)
		{
			listData[i] = settings.getLanguageText(SETTINGS_EVENTS[i]) + StringMethods.extendCharacter(' ', 5);
		
		}//FOR
		
		settingsList = new DList(this, false, SettingsValues.SETTINGS);
		settingsList.setListData(listData);
		DScrollPane settingsScroll = new DScrollPane(settings, settingsList);
		
		JPanel modePanel = new JPanel();
		modePanel.setLayout(new BorderLayout());
		Dimension modeSpace = new Dimension(getSettings().getFontSize() * 8, 1);
		modePanel.add(Box.createRigidArea(modeSpace), BorderLayout.NORTH);
		modePanel.add(settingsScroll, BorderLayout.CENTER);
		modePanel.add(Box.createRigidArea(modeSpace), BorderLayout.SOUTH);
		
		//CREATE CENTER PANEL
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(1, 1));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints centerCST = new GridBagConstraints();
		centerCST.gridx = 0;		centerCST.gridy = 0;
		centerCST.gridwidth = 1;	centerCST.gridheight = 3;
		centerCST.weightx = 0;		centerCST.weighty = 1;
		centerCST.fill = GridBagConstraints.BOTH;
		centerPanel.add(modePanel, centerCST);
		centerCST.gridx = 1;
		centerPanel.add(getHorizontalSpace(), centerCST);
		centerCST.gridx = 2;		centerCST.gridheight = 1;
		centerCST.weightx = 1;
		centerPanel.add(contentPanel, centerCST);
		
		//CREATE TOP PANEL
		DLabel settingsLabel = new DLabel(this, settingsList, SettingsValues.SETTINGS);
		settingsLabel.setFontLarge();
		JPanel topPanel = getVerticalStack(settingsLabel, new JSeparator(SwingConstants.HORIZONTAL));
		
		//FINALIZE FRAME
		getFrame().getContentPane().add(getSpacedPanel(centerPanel), BorderLayout.CENTER);
		getFrame().getContentPane().add(this.getSpacedPanel(topPanel, 1, 0, true, false, true, true), BorderLayout.NORTH);
		getFrame().getContentPane().add(getSpacedPanel(bottomPanel, 1, 0, false, true, true, true), BorderLayout.SOUTH);
		settingsList.setSelectedIndex(0);
		getFrame().packRestricted();
		
		if(ownerGUI != null)
		{
			getFrame().setLocationRelativeTo(ownerGUI.getFrame());
			
		}//IF
		else
		{
			getFrame().setLocationRelativeTo(null);
			
		}//ELSE
		getFrame().setVisible(true);

		
	}//CONSTRUCTOR

	/**
	 * Sets the current setting mode of the settings GUI.
	 * 
	 * @param setting Language ID of the setting mode to select
	 */
	public void setSettingMode(final String setting)
	{
		for(int i = 0; i < SETTINGS_EVENTS.length; i++)
		{
			if(SETTINGS_EVENTS[i].equals(setting))
			{
				settingsList.setSelectedIndex(i);
				break;
				
			}//IF
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Handles the finishing operations of the SettingsGUI, closing the frame, and restarting the program if necessary.
	 */
	private void finish()
	{
		if(getFrame().getAllowExit())
		{
			dispose();
			
			if(changed)
			{
				if(ownerGUI != null)
				{
					ownerGUI.dispose();
					
				}//IF
				
				Start.startGUI(getSettings(), getDvkHandler());
				
			}//IF
			else if(ownerGUI != null)
			{
				ownerGUI.getFrame().setAllowExit(true);
				
			}//ELSE
			
		}//IF
		
	}//METHOD
	
	/**
	 * Called when one of the settings options is selected. Sets main settings panel to GUI for editing the settings specified.
	 */
	private void settingSelected()
	{
		int selected = settingsList.getSelectedIndex();
		if(selected != -1)
		{
			switch(SETTINGS_EVENTS[selected])
			{
				case SettingsValues.LANGUAGE:
					modeGUI = new LanguageSettingsGUI(this);
					break;
				case SettingsValues.DVK_DIRECTORIES:
					modeGUI = new DirectorySettingsGUI(this);
					break;
				case SettingsValues.THEME:
					modeGUI = new ThemeSettingsGUI(this);
					break;
				case SettingsValues.FONT:
					modeGUI = new FontSettings(this);
					break;
				
			}//SWITCH
			
			if(modeGUI != null)
			{
				contentPanel.removeAll();
				contentPanel.add(modeGUI.getPanel());
				contentPanel.revalidate();
				
			}//IF
			
		}//IF
		
	}//METHOD
	
	/**
	 * Enables the "Apply" button.
	 */
	public void applyEnable()
	{
		applyButton.setEnabled(true);
		saveButton.setEnabled(true);
		
	}//METHOD
	
	/**
	 * Disables the "Apply" button.
	 */
	public void applyDisable()
	{
		applyButton.setEnabled(false);
		saveButton.setEnabled(false);
		
	}//METHOD
	
	/**
	 * Applies the currently edited settings.
	 */
	private void apply()
	{
		if(modeGUI != null)
		{
			modeGUI.apply();
			applyDisable();
			changed = true;
			
		}//IF
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case SettingsValues.SETTINGS:
				settingSelected();
				break;
			case SettingsValues.APPLY:
				apply();
				break;
			case CommonValues.SAVE:
				apply();
			case DCloseListener.FRAME_CLOSE_EVENT:
			case CommonValues.CLOSE:
				finish();
				break;
		
		}//SWITCH
		
	}//METHOD

	@Override
	public void enableAll(){}

	@Override
	public void disableAll(){}
	
}//CLASS
