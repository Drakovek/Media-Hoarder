package drakovek.hoarder.gui.settings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.Start;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DCheckBox;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.components.DScrollablePanel;
import drakovek.hoarder.gui.swing.components.DTextArea;
import drakovek.hoarder.gui.swing.components.DTextField;
import drakovek.hoarder.gui.swing.listeners.DCloseListener;
import drakovek.hoarder.processing.BooleanInt;
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
	 * Array containing all the fonts available for Swing to use.
	 * 
	 * @since 2.0
	 */
	private String[] fonts;
	
	/**
	 * Array containing all the "Look and Feel"s for Swing to use.
	 * 
	 * @since 2.0
	 */
	private LookAndFeelInfo[] themes;
	
	/**
	 * Array containing all the languages available for the program to use.
	 * 
	 * @since 2.0
	 */
	private String[] languages;
	
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
	 * List showing available languages for the program
	 * 
	 * @since 2.0
	 */
	private DList languageList;
	
	/**
	 * List showing available Swing "Look and Feel"s for the program
	 * 
	 * @since 2.0
	 */
	private DList themeList;
	
	/**
	 * List showing available fonts for the program
	 * 
	 * @since 2.0
	 */
	private DList fontList;
	
	/**
	 * Text Field for inputting the desired text size.
	 * 
	 * @since 2.0
	 */
	private DTextField sizeText;
	
	/**
	 * Text Area that shows a preview for the chosen font.
	 * 
	 * @since 2.0
	 */
	private DTextArea previewText;
	
	/**
	 * Edited setting for the program's language
	 * 
	 * @since 2.0
	 */
	private String language;
	
	/**
	 * Edited setting for the program's theme
	 * 
	 * @since 2.0
	 */
	private String theme;
	
	/**
	 * Edited setting for the program's default font
	 * 
	 * @since 2.0
	 */
	private String font;
	
	/**
	 * Edited setting for the program's font size
	 * 
	 * @since 2.0
	 */
	private int size;
	
	/**
	 * Edited setting for whether the program's default font should be bold
	 * 
	 * @since 2.0
	 */
	private boolean bold;
	
	/**
	 * Edited setting for whether the program's fonts should be anti-aliased
	 * 
	 * @since 2.0
	 */
	private boolean aa;
	
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
		
		settingsFrame = new DFrame(settings, getTitle(DefaultLanguage.SETTINGS));
		settingsFrame.interceptFrameClose(this);
		
		//LIST PANELS
		languageList = new DList(this, false, DefaultLanguage.LANGUAGE);
		themeList = new DList(this, false, DefaultLanguage.THEME);
		fontList = new DList(this, false, DefaultLanguage.FONT);
		previewText = new DTextArea(this);
		
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
		bold = getSettings().getFontBold();
		aa = getSettings().getFontAA();
		DCheckBox boldCheck = new DCheckBox(this, bold, DefaultLanguage.FONT_BOLD);
		DCheckBox aaCheck = new DCheckBox(this, aa, DefaultLanguage.FONT_AA);
		sizeText = new DTextField(this, DefaultLanguage.FONT_SIZE);
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
		
		DScrollablePanel scrollPanel = new DScrollablePanel(getSpacedPanel(settingsPanel, 1, 0, true, true, true, true), true, false);
		DScrollPane settingsScroll = new DScrollPane(getSettings(), scrollPanel);
		
		//FINALIZE FRAME
		settingsFrame.getContentPane().add(getSpacedPanel(settingsScroll, 1, 1, true, true, true, true), BorderLayout.CENTER);
		settingsFrame.getContentPane().add(getSpacedPanel(bottomPanel, 1, 0, false, true, true, true), BorderLayout.SOUTH);
		settingsFrame.pack();
		settingsFrame.setLocationRelativeTo(ownerGUI.getFrame());
		settingsFrame.setMinimumSize(settingsFrame.getSize());
		ownerGUI.getFrame().setAllowExit(false);
		settingsFrame.setVisible(true);
		
		initializeSettings();
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the Settings GUI to reflect the current settings of the program.
	 * 
	 * @since 2.0
	 */
	private void initializeSettings()
	{
		//SET DEFAULT SETTINGS
		language = getSettings().getLanguageName();
		theme = getSettings().getTheme();
		font = getSettings().getFontName();
		size = getSettings().getFontSize();
		bold = getSettings().getFontBold();
		aa = getSettings().getFontAA();
		
		sizeText.setText(Integer.toString(size));
		
		//LANGUAGES LIST
		languages = StringMethods.arrayListToArray(getSettings().getLanguages());
		languageList.setListData(languages);
		int selection = -1;
		for(int i = 0; i < languages.length; i++)
		{
			if(language.equals(languages[i]))
			{
				selection = i;
				break;
				
			}//IF
			
		}//FOR
		
		if(selection == -1)
		{
			selection = 0;
			
		}//IF
		
		languageList.setSelectedIndex(selection);
		languageList.ensureIndexIsVisible(selection);
				
		//THEMES LIST
		themes = UIManager.getInstalledLookAndFeels();
		String[] themeStrings = new String[themes.length];
		selection = -1;
		for(int i = 0; i < themes.length; i++)
		{
			themeStrings[i] = themes[i].getName();
			
			if(theme.equals(themes[i].getClassName()))
			{
				selection = i;
				
			}//IF
					
		}//FOR
		
		themeList.setListData(themeStrings);
		
		if(selection == -1)
		{
			selection = 0;
		
		}//IF
		
		themeList.setSelectedIndex(selection);
		themeList.ensureIndexIsVisible(selection);
		
		//FONT LIST
		selection = -1;
		fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontList.setListData(fonts);
		for(int i = 0; i < fonts.length; i++)
		{
			if(font.equals(fonts[i]))
			{
				selection = i;
				break;
				
			}//IF
			
		}//FOR
	
		if(selection == -1)
		{
			selection = 0;
			
		}//IF
		
		fontList.setSelectedIndex(selection);
		fontList.ensureIndexIsVisible(selection);
		
		previewText.setText(getSettings().getLanuageText(DefaultLanguage.FONT_PREVIEW));
		updateFontPreview();
		
	}//METHOD
	
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
	 * Resets the font preview to show the currently selected font.
	 * 
	 * @since 2.0
	 */
	private void updateFontPreview()
	{
		previewText.setFont(font, bold, size);
		
	}//METHOD
	
	/**
	 * Attempts to update the font size from a value given by the user in the font size text field.
	 * 
	 * @since 2.0
	 */
	private void updateFontSize()
	{
		try
		{
			int trySize = Integer.parseInt(sizeText.getText());
			size = trySize;
			updateFontPreview();
			
		}//TRY
		catch(NumberFormatException e)
		{
			sizeText.setText(Integer.toString(size));
			
		}//CATCH
		
	}//METHOD
	
	/**
	 * Disposes the settings frame when done with changing settings. Saves settings if called for.
	 * 
	 * @param save Whether to save the edited settings
	 * @since 2.0
	 */
	private void dispose(final boolean save)
	{
		boolean reset = false;
		updateFontSize();
		
		if(save)
		{
			if(!getSettings().getLanguageName().equals(language))
			{
				getSettings().setLanguageName(language);
				reset = true;
				
			}//IF
			
			if(!getSettings().getTheme().equals(theme))
			{
				getSettings().setTheme(theme);
				reset = true;
				
			}//IF
			
			if(!getSettings().getFontName().equals(font))
			{
				getSettings().setFontName(font);
				reset = true;
				
			}//IF
			
			if(getSettings().getFontBold() != bold)
			{
				getSettings().setFontBold(bold);
				reset = true;
				
			}//IF
			
			if(getSettings().getFontAA() != aa)
			{
				getSettings().setFontAA(aa);
				reset = true;
				
			}//IF
			
			if(getSettings().getFontSize() != size)
			{
				getSettings().setFontSize(size);
				reset = true;
				
			}//IF
			
		}//IF
		
		settingsFrame.dispose();
		ownerGUI.getFrame().setAllowExit(true);
		
		if(reset)
		{
			ownerGUI.dispose();
			Start.startGUI(getSettings());
			
		}//IF
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		int selected;
		switch(id)
		{
			case DefaultLanguage.FONT_SIZE:
				updateFontSize();
				break;
			case DefaultLanguage.FONT_BOLD:
				bold = BooleanInt.getBoolean(value);
				updateFontPreview();
				break;
			case DefaultLanguage.FONT_AA:
				aa = BooleanInt.getBoolean(value);
				break;
			case DefaultLanguage.FONT:
				selected = fontList.getSelectedIndex();
				if(selected != -1)
				{
					font = fonts[selected];
					updateFontPreview();
					
				}//IF
				break;
			case DefaultLanguage.THEME:
				selected = themeList.getSelectedIndex();
				if(selected != -1)
				{
					theme = themes[selected].getClassName();
					
				}//IF
				break;
			case DefaultLanguage.LANGUAGE:
				selected = languageList.getSelectedIndex();
				if(selected != -1)
				{
					language = languages[selected];
					
				}//IF
				break;
			case DefaultLanguage.OK:
				dispose(true);
				break;
			case DCloseListener.FRAME_CLOSE_EVENT:
			case DefaultLanguage.CANCEL:
				dispose(false);
				break;
				
		}//SWITCH
		
	}//METHOD
	
}//CLASS
