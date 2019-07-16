package drakovek.hoarder.gui.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DScrollPane;

/**
 * Settings Mode GUI for selecting the program's theme (Swing Look and Feel)
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ThemeSettingsGUI extends SettingsModeGUI
{
	/**
	 * DList for selecting the program's theme.
	 * 
	 * @since 2.0
	 */
	private DList themeList;
	
	/**
	 * List of the installed themes (Swing "Look and Feel"s)
	 * 
	 * @since 2.0
	 */
	private LookAndFeelInfo[] themes;
	
	/**
	 * Currently selected theme.
	 * 
	 * @since 2.0
	 */
	private String theme;
	
	/**
	 * Initializes the ThemeSettingsGUI class
	 * 
	 * @param settingsGUI Parent SettingsGUI
	 * @since 2.0
	 */
	public ThemeSettingsGUI(SettingsGUI settingsGUI)
	{
		super(settingsGUI);
		
		//GET LIST OF THEMES
		theme = new String();
		themes = UIManager.getInstalledLookAndFeels();
		String[] themeStrings = new String[themes.length];
		for(int i = 0; i < themes.length; i++)
		{
			themeStrings[i] = themes[i].getName();
			
		}//FOR
		
		//CREATE THEME GUI
		themeList = new DList(this, false, DefaultLanguage.THEME);
		themeList.setListData(themeStrings);
		DScrollPane themeScroll = new DScrollPane(getSettings(), ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, themeList);
		getPanel().setLayout(new GridBagLayout());
		GridBagConstraints panelCST = new GridBagConstraints();
		panelCST.gridx = 0;			panelCST.gridy = 0;
		panelCST.gridwidth = 3;		panelCST.gridheight = 1;
		panelCST.weightx = 1;		panelCST.weighty = 0;
		panelCST.fill = GridBagConstraints.BOTH;
		getPanel().add(new DLabel(this, themeList, DefaultLanguage.THEME), panelCST);
		panelCST.gridy = 1;
		getPanel().add(getVerticalSpace(), panelCST);
		panelCST.gridy = 2;			panelCST.weighty = 1;
		getPanel().add(themeScroll, panelCST);
		
	}//CONSTRUCTOR

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.THEME:
			{
				int selected = themeList.getSelectedIndex();
				if(selected != -1)
				{
					theme = themes[selected].getClassName();
					if(getSettings().getTheme().equals(theme))
					{
						getSettingsGUI().applyDisable();
						
					}//IF
					else
					{
						getSettingsGUI().applyEnable();
						
					}//ELSE
					
				}//IF
				
			}//CASE
			
		}//SWITCH
		
	}//METHOD

	@Override
	public void apply()
	{
		getSettings().setTheme(theme);
		
	}//METHOD
	
}//CLASS
