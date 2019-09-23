package drakovek.hoarder.gui.settings;

import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import drakovek.hoarder.file.language.SettingsValues;
import drakovek.hoarder.gui.swing.components.DCheckBox;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.components.DTextArea;
import drakovek.hoarder.gui.swing.components.DTextField;
import drakovek.hoarder.processing.BooleanInt;

/**
 * Settings Mode GUI for selecting the program's font.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class FontSettings extends SettingsModeGUI
{
	/**
	 * DList for the user to select a font
	 */
	private DList fontList;
	
	/**
	 * DTextField for the user to input the font size
	 */
	private DTextField sizeText;
	
	/**
	 * DTextArea to show a preview of the selected font
	 */
	private DTextArea previewText;
	
	/**
	 * List of available fonts
	 */
	private String[] fonts;
	
	/**
	 * The currently selected font
	 */
	private String font;
	
	/**
	 * The current setting for whether the font is bold
	 */
	private boolean bold;
	
	/**
	 * The current setting for whether the font is anti-aliased
	 */
	private boolean aa;
	
	/**
	 * The currently edited font size
	 */
	private int size;
	
	/**
	 * Initializes the FontSettings class.
	 * 
	 * @param settingsGUI Parent SettingsGUI
	 */
	public FontSettings(SettingsGUI settingsGUI)
	{
		super(settingsGUI);
		bold = getSettings().getFontBold();
		aa = getSettings().getFontAA();
		size = getSettings().getFontSize();
		font = getSettings().getFontName();
		
		fontList = new DList(this, false, SettingsValues.FONT);
		previewText = new DTextArea(this);
		previewText.setText(getSettings().getLanguageText(SettingsValues.FONT_PREVIEW));
		
		fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontList.setListData(fonts);
		int selected = -1;
		for(int i = 0; i < fonts.length; i++)
		{
			if(fonts[i].equals(font))
			{
				selected = i;
				break;
				
			}//IF
			
		}//FOR
		
		if(selected != -1)
		{
			fontList.setSelectedIndex(selected);
			fontList.ensureIndexIsVisible(selected);
			
		}//IF
		
		//CREATE TEXT OPTIONS PANEL
		sizeText = new DTextField(this, SettingsValues.FONT_SIZE);
		sizeText.setText(Integer.toString(size));
		JPanel sizePanel = new JPanel();
		sizePanel.setLayout(new GridBagLayout());
		GridBagConstraints sizeCST = new GridBagConstraints();
		sizeCST.gridx = 0;		sizeCST.gridy = 0;
		sizeCST.gridwidth = 1;	sizeCST.gridheight = 3;
		sizeCST.weightx = 0;	sizeCST.weighty = 1;
		sizeCST.fill = GridBagConstraints.BOTH;
		sizePanel.add(new DLabel(this, sizeText, SettingsValues.FONT_SIZE), sizeCST);
		sizeCST.gridx = 1;
		sizePanel.add(getHorizontalSpace(), sizeCST);
		sizeCST.gridx = 2;		sizeCST.weightx = 1;
		sizePanel.add(sizeText, sizeCST);
		
		JPanel topTextPanel = new JPanel();
		topTextPanel.setLayout(new GridLayout(1, 2, getSettings().getSpaceSize(), 0));
		topTextPanel.add(new DCheckBox(this, bold, SettingsValues.FONT_BOLD));
		topTextPanel.add(sizePanel);
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(2, 1));
		textPanel.add(topTextPanel);
		textPanel.add(new DCheckBox(this, aa, SettingsValues.FONT_AA));
		
		//CREATE CENTER PANEL
		DScrollPane fontScroll = new DScrollPane(getSettings(), ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, fontList);
		DScrollPane previewScroll = new DScrollPane(getSettings(), ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, previewText);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 2, getSettings().getSpaceSize(), 0));
		centerPanel.add(fontScroll);
		centerPanel.add(previewScroll);
		
		
		//CREATE FINAL FONT GUI
		getPanel().setLayout(new GridBagLayout());
		GridBagConstraints fontCST = new GridBagConstraints();
		fontCST.gridx = 0;		fontCST.gridy = 0;
		fontCST.gridwidth = 3;	fontCST.gridheight = 1;
		fontCST.weightx = 1;	fontCST.weighty = 0;
		fontCST.fill = GridBagConstraints.BOTH;
		getPanel().add(new DLabel(this, fontList, SettingsValues.FONT), fontCST);
		fontCST.gridy = 2;
		getPanel().add(textPanel, fontCST);
		fontCST.gridy = 1;		fontCST.weighty = 1;
		getPanel().add(this.getSpacedPanel(centerPanel, 1, 1, true, true, false, false), fontCST);
		
	}//CONSTRUCTOR

	/**
	 * Updates the font size based on what the user has input in the size text box.
	 */
	private void updateSize()
	{
		try
		{
			int trySize = Integer.parseInt(sizeText.getText());
			size = trySize;
			updatePreview();
			
		}//TRY
		catch(NumberFormatException e)
		{
			sizeText.setText(Integer.toString(size));
			
		}//CATCH
		
	}//METHOD
	
	/**
	 * Updates the font based on which font the user has selected in the font list.
	 */
	private void updateFont()
	{
		int selected = fontList.getSelectedIndex();
		if(selected != -1)
		{
			font = fonts[selected];
			updatePreview();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Updates the text preview to show what the currently selected font will look like.
	 */
	private void updatePreview()
	{
		previewText.setFont(font, bold, size);
		
	}//METHOD
	
	/**
	 * Returns whether any of the font settings have changed from the values saved to disk.
	 * 
	 * @return Whether font settings have changed
	 */
	private boolean checkChanged()
	{
		boolean changed = false;
		
		if(!getSettings().getFontName().equals(font) ||
		   getSettings().getFontBold() != bold ||
		   getSettings().getFontAA() != aa ||
		   getSettings().getFontSize() != size)
		{
			changed = true;
			
		}//IF
		
		
		return changed;
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case SettingsValues.FONT:
				updateFont();
				break;
			case SettingsValues.FONT_AA:
				aa = BooleanInt.getBoolean(value);
				break;
			case SettingsValues.FONT_SIZE:
				updateSize();
				break;
			case SettingsValues.FONT_BOLD:
				bold = BooleanInt.getBoolean(value);
				updatePreview();
				break;
				
		}//SWITCH
		
		if(checkChanged())
		{
			getSettingsGUI().applyEnable();
			
		}//IF
		else
		{
			getSettingsGUI().applyDisable();
			
		}//ELSE
			
	}//METHOD

	@Override
	public void apply()
	{
		updateSize();
		getSettings().setFontName(font);
		getSettings().setFontBold(bold);
		getSettings().setFontAA(aa);
		getSettings().setFontSize(size);
		
	}//METHOD

}//CLASS
