package drakovek.hoarder.gui.settings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
		JList<String> themeList = new JList<>();
		JList<String> fontList = new JList<>();
		JTextArea previewText = new JTextArea();
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		textPanel.add(getScrollLabelPanel(fontList, DefaultLanguage.FONT));
		textPanel.add(getScrollLabelPanel(previewText, DefaultLanguage.PREVIEW));
		
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(3, 1, 0, settings.getSpaceSize()));
		listPanel.add(getScrollLabelPanel(languageList, DefaultLanguage.LANGUAGE));
		listPanel.add(getScrollLabelPanel(themeList, DefaultLanguage.THEME));
		listPanel.add(textPanel);
		
		//CREATE TEXT OPTION PANEL
		JCheckBox boldCheck = new JCheckBox(DefaultLanguage.FONT_BOLD);
		JCheckBox aaCheck = new JCheckBox(DefaultLanguage.FONT_AA);
		JTextField sizeText = new JTextField(10);
		DLabel sizeLabel = new DLabel(this, sizeText, DefaultLanguage.FONT_SIZE);
		JPanel sizePanel = new JPanel();
		sizePanel.setLayout(new GridBagLayout());
		GridBagConstraints sizeCST = new GridBagConstraints();
		sizeCST.gridx = 0;			sizeCST.gridy = 0;
		sizeCST.gridwidth = 1;		sizeCST.gridheight = 3;
		sizeCST.weightx = 0;		sizeCST.weighty = 0;
		sizeCST.fill = GridBagConstraints.BOTH;
		sizePanel.add(sizeLabel, sizeCST);
		sizeCST.gridx = 1;
		sizePanel.add(getHorizontalSpace(), sizeCST);
		sizeCST.gridx = 2;			sizeCST.weightx = 1;
		sizePanel.add(sizeText, sizeCST);
		JPanel textTopPanel = new JPanel();
		textTopPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		textTopPanel.add(boldCheck);
		textTopPanel.add(sizePanel);
		JPanel textOptionPanel = new JPanel();
		textOptionPanel.setLayout(new GridLayout(2, 1, 0, settings.getSpaceSize()));
		textOptionPanel.add(textTopPanel);
		textOptionPanel.add(aaCheck);
		
		//CREATE SETTINGS PANEL
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new GridBagLayout());
		GridBagConstraints settingsCST = new GridBagConstraints();
		settingsCST.gridx = 0;			settingsCST.gridy = 0;
		settingsCST.gridwidth = 3;		settingsCST.gridheight = 1;
		settingsCST.weightx = 1;		settingsCST.weighty = 0;
		settingsCST.fill = GridBagConstraints.BOTH;
		settingsPanel.add(listPanel, settingsCST);
		settingsCST.gridy = 1;
		settingsPanel.add(getVerticalSpace(), settingsCST);
		settingsCST.gridy = 2;
		settingsPanel.add(textOptionPanel, settingsCST);
		
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
		settingsFrame.getContentPane().add(this.getSpacedPanel(settingsPanel, 1, 0, true, true, true, true));
		settingsFrame.getContentPane().add(getSpacedPanel(bottomPanel, 1, 0, false, true, true, true), BorderLayout.SOUTH);
		settingsFrame.pack();
		settingsFrame.setLocationRelativeTo(ownerGUI.getFrame());
		ownerGUI.getFrame().setAllowExit(false);
		settingsFrame.setVisible(true);
		
	}//CONSTRUCTOR

	/**
	 * Returns a panel filled with a scaled component in a scroll pane along with a left-aligned label.
	 * 
	 * @param component Component to show in the scroll pane.
	 * @param id ID for the label
	 * @return Scroll Label Panel
	 * @since 2.0
	 */
	private JPanel getScrollLabelPanel(Component component, final String id)
	{
		DScrollPane scroll = new DScrollPane(getSettings(), ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, component);
		DLabel scrollLabel = new DLabel(this, component, id);
		JPanel scrollPanel = new JPanel();
		scrollPanel.setLayout(new GridBagLayout());
	
		GridBagConstraints scrollCST = new GridBagConstraints();
		scrollCST.gridx = 0;			scrollCST.gridy = 0;
		scrollCST.gridwidth = 1;		scrollCST.gridheight = 1;
		scrollCST.weightx = 0;			scrollCST.weighty = 0;
		scrollCST.fill = GridBagConstraints.BOTH;
		scrollPanel.add(scrollLabel, scrollCST);
		scrollCST.gridy = 1;
		scrollPanel.add(getVerticalSpace(), scrollCST);
		scrollCST.gridy = 2;		scrollCST.gridwidth = 3;
		scrollCST.weightx = 1;		scrollCST.weighty = 1;
		scrollPanel.add(scroll, scrollCST);

		return scrollPanel;
		
	}//METHOD
	
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
