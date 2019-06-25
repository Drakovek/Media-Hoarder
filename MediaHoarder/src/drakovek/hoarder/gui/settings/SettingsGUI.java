package drakovek.hoarder.gui.settings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DScrollPane;
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
		
		//LIST PANELS
		JList<String> languageList = new JList<>();
		DLabel languageLabel = new DLabel(this, languageList, DefaultLanguage.LANGUAGE);
		DScrollPane languageScroll = new DScrollPane(settings, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, languageList);
		JPanel languagePanel = new JPanel();
		languagePanel.setLayout(new GridBagLayout());
		
		JList<String> themeList = new JList<>();
		DLabel themeLabel = new DLabel(this, themeList, DefaultLanguage.THEME);
		DScrollPane themeScroll = new DScrollPane(settings, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, themeList);
		JPanel themePanel = new JPanel();
		themePanel.setLayout(new GridBagLayout());
		
		GridBagConstraints listCST = new GridBagConstraints();
		listCST.gridx = 0;			listCST.gridy = 0;
		listCST.gridwidth = 1;		listCST.gridheight = 1;
		listCST.weightx = 0;		listCST.weighty = 0;
		listCST.fill = GridBagConstraints.BOTH;
		languagePanel.add(languageLabel, listCST);
		themePanel.add(themeLabel, listCST);
		listCST.gridy = 1;
		languagePanel.add(getVerticalSpace(), listCST);
		themePanel.add(getVerticalSpace(), listCST);
		listCST.gridy = 2;			listCST.gridwidth = 3;
		listCST.weightx = 1;		listCST.weighty = 1;
		languagePanel.add(languageScroll, listCST);
		themePanel.add(themeScroll, listCST);
		
		//COMBO PANEL
		Dimension sectionHeight = new Dimension(1, settings.getFontSize() * 10);
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new GridBagLayout());
		GridBagConstraints comboCST = new GridBagConstraints();
		comboCST.gridx = 0;			comboCST.gridy = 0;
		comboCST.gridwidth = 1;		comboCST.gridheight = 1;
		comboCST.weightx = 0;		comboCST.weighty = 0;
		comboCST.fill = GridBagConstraints.BOTH;
		comboPanel.add(Box.createRigidArea(sectionHeight), comboCST);
		comboCST.gridy = 2;
		comboPanel.add(Box.createRigidArea(sectionHeight), comboCST);
		comboCST.gridx = 2;			comboCST.gridy = 0;
		comboPanel.add(Box.createRigidArea(sectionHeight), comboCST);
		comboCST.gridy = 2;
		comboPanel.add(Box.createRigidArea(sectionHeight), comboCST);
		comboCST.gridx = 1;			comboCST.weightx = 1;
		comboPanel.add(themePanel, comboCST);
		comboCST.gridy = 0;
		comboPanel.add(languagePanel, comboCST);
		comboCST.gridx = 0;			comboCST.gridy = 1;
		comboCST.gridwidth = 3;
		comboPanel.add(getSpacedSeparatorHorizontal(), comboCST);
		
		
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
		settingsFrame.getContentPane().add(comboPanel, BorderLayout.CENTER);
		settingsFrame.getContentPane().add(getSpacedPanel(bottomPanel, 1, 0, false, true, true, true), BorderLayout.SOUTH);
		settingsFrame.pack();
		settingsFrame.setLocationRelativeTo(ownerGUI.getFrame());
		ownerGUI.getFrame().setAllowExit(false);
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
		ownerGUI.getFrame().setAllowExit(true);
		
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
