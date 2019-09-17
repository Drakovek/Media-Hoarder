package drakovek.hoarder.gui.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ScrollPaneConstants;

import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.processing.StringMethods;

/**
 * Settings Mode GUI for selecting the program's language.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class LanguageSettingsGUI extends SettingsModeGUI
{
	/**
	 * Currently selected language.
	 */
	private String language;
	
	/**
	 * List of available languages.
	 */
	private String[] languages;
	
	/**
	 * DList for selecting the program's language.
	 */
	private DList languageList;
	
	/**
	 * Initializes the LanguageSettingsGUI class.
	 * 
	 * @param settingsGUI Parent SettingsGUI
	 */
	public LanguageSettingsGUI(SettingsGUI settingsGUI)
	{
		super(settingsGUI);
		
		//GET LIST OF LANGUAGES
		language = getSettings().getLanguageName();
		languages = StringMethods.arrayListToArray(getSettings().getLanguages());
		int selected = -1;
		for(int i = 0; i < languages.length; i++)
		{
			if(getSettings().getLanguageName().equals(languages[i]))
			{
				selected = i;
				break;
				
			}//IF
			
		}//FOR
		
		//CREATE THEME GUI
		languageList = new DList(this, false, DefaultLanguage.LANGUAGE);
		languageList.setListData(languages);
		if(selected != -1)
		{
			languageList.setSelectedIndex(selected);
			languageList.ensureIndexIsVisible(selected);
			
		}//IF
		
		DScrollPane themeScroll = new DScrollPane(getSettings(), ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, languageList);
		getPanel().setLayout(new GridBagLayout());
		GridBagConstraints panelCST = new GridBagConstraints();
		panelCST.gridx = 0;			panelCST.gridy = 0;
		panelCST.gridwidth = 3;		panelCST.gridheight = 1;
		panelCST.weightx = 1;		panelCST.weighty = 0;
		panelCST.fill = GridBagConstraints.BOTH;
		getPanel().add(new DLabel(this, languageList, DefaultLanguage.LANGUAGE), panelCST);
		panelCST.gridy = 1;
		getPanel().add(getVerticalSpace(), panelCST);
		panelCST.gridy = 2;			panelCST.weighty = 1;
		getPanel().add(themeScroll, panelCST);
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the current language to the language selected by the user.
	 */
	private void setLanguageSelected()
	{
		int selected = languageList.getSelectedIndex();
		if(selected != -1)
		{
			language = languages[selected];
			if(getSettings().getLanguageName().equals(language))
			{
				getSettingsGUI().applyDisable();
				
			}//IF
			else
			{
				getSettingsGUI().applyEnable();
				
			}//ELSE
			
		}//IF
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.LANGUAGE:
				setLanguageSelected();
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void apply()
	{
		getSettings().setLanguageName(language);
		
	}//METHOD

}//CLASS
