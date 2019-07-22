package drakovek.hoarder.gui.settings;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.compound.DFileChooser;
import drakovek.hoarder.processing.sort.FileSort;

/**
 * Settings Mode GUI for selecting the user's DMF Directories
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DirectorySettingsGUI extends SettingsModeGUI
{
	/**
	 * List showing the currently listed DMF Directories
	 * 
	 * @since 2.0
	 */
	DList directoryList;
	
	/**
	 * ArrayList of DMF Directories
	 * 
	 * @since 2.0
	 */
	ArrayList<File> dmfDirectories;
	
	/**
	 * File chooser for choosing DMF directories
	 * 
	 * @since 2.0
	 */
	DFileChooser fileChooser;
	
	/**
	 * Initializes the DirectorySettingsGUI class.
	 * 
	 * @param settingsGUI SettingsGUI Parent settingsGUI
	 * @since 2.0
	 */
	public DirectorySettingsGUI(SettingsGUI settingsGUI)
	{
		super(settingsGUI);
		dmfDirectories = getSettings().getDmfDirectories();
		fileChooser = new DFileChooser(getSettings());
		
		//CREATE BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2, getSettings().getSpaceSize(), 0));
		bottomPanel.add(new DButton(this, DefaultLanguage.ADD));
		bottomPanel.add(new DButton(this, DefaultLanguage.REMOVE));
		
		directoryList = new DList(this, true, DefaultLanguage.DMF_DIRECTORIES);
		setDirectoryList();
		DScrollPane directoryScroll = new DScrollPane(getSettings(), ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, directoryList);
		
		//FINALIZE PANEL
		getPanel().setLayout(new BorderLayout());
		getPanel().add(new DLabel(this, directoryList, DefaultLanguage.DMF_DIRECTORIES), BorderLayout.NORTH);
		getPanel().add(this.getSpacedPanel(directoryScroll, 1, 1, true, true, false, false));
		getPanel().add(bottomPanel, BorderLayout.SOUTH);
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the displayed directory list based off of the current list of DMF directories while removing invalid entries.
	 * 
	 * @since 2.0
	 */
	private void setDirectoryList()
	{
		//REMOVE NULL AND INVALID FILES
		for(int i = 0; i < dmfDirectories.size(); i++)
		{
			if(dmfDirectories.get(i) == null || !dmfDirectories.get(i).isDirectory())
			{
				dmfDirectories.remove(i);
				i--;
				
			}//IF
			
		}//FOR
		
		//REMOVE DUPLICATES
		for(int i = 0; i < dmfDirectories.size(); i++)
		{
			for(int k = i + 1; k < dmfDirectories.size(); k++)
			{
				if(dmfDirectories.get(i).equals(dmfDirectories.get(k)))
				{
					dmfDirectories.remove(k);
					k--;
					
				}//IF
				
			}//FOR
			
		}//FOR
		
		//CONVERT DIRECTORIES TO STRINGS AND SHOW IN LIST
		dmfDirectories = FileSort.sortFiles(dmfDirectories);
		String[] directoryStrings = new String[dmfDirectories.size()];
		for(int i = 0; i < dmfDirectories.size(); i++)
		{
			directoryStrings[i] = dmfDirectories.get(i).getAbsolutePath();
			
		}//FOR
		
		directoryList.setListData(directoryStrings);
		checkChanged();
		
	}//METHOD

	/**
	 * Checks if any of the DMF directories have changed from those found in the settings file, and if so, allows the user to save changes.
	 * 
	 * @since 2.0
	 */
	private void checkChanged()
	{
		boolean changed = false;
		
		if(dmfDirectories.size() != getSettings().getDmfDirectories().size())
		{
			changed = true;
			
		}//IF
		else
		{
			for(int i = 0; i < dmfDirectories.size(); i++)
			{
				if(!getSettings().getDmfDirectories().get(i).equals(dmfDirectories.get(i)))
				{
					changed = true;
					break;
					
				}//IF
				
			}//FOR
			
		}//ELSE
		
		if(changed)
		{
			getSettingsGUI().applyEnable();
			
		}//IF
		else
		{
			getSettingsGUI().applyDisable();
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Adds a directory selected with a file chooser to the list of DMF Directories.
	 * 
	 * @since 2.0
	 */
	private void addDirectory()
	{
		if(dmfDirectories.size() == 0)
		{
			dmfDirectories.add(fileChooser.getFileOpen(getSettingsGUI().getFrame(), null));
		}
		else
		{
			dmfDirectories.add(fileChooser.getFileOpen(getSettingsGUI().getFrame(), dmfDirectories.get(0)));
			
		}//ELSE
		setDirectoryList();
		
	}//METHOD
	
	/**
	 * Removes selected entries from the list of DMF directories.
	 * 
	 * @since 2.0
	 */
	private void removeDirectories()
	{
		int[] indexes = directoryList.getSelectedIndices();
		for(int i = 0; i < indexes.length; i++)
		{
			if(indexes[i] < dmfDirectories.size())
			{
				dmfDirectories.set(indexes[i], null);
				
			}//IF
			
		}//FOR
		
		setDirectoryList();
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.ADD:
				addDirectory();
				break;
			case DefaultLanguage.REMOVE:
				removeDirectories();
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void apply()
	{
		getSettings().setDmfDirectories(dmfDirectories);
		getSettingsGUI().getDmfHandler().clearDMFs();
		
	}//METHOD
	
}//CLASS
