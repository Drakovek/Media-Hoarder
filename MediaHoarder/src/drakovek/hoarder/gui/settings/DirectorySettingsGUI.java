package drakovek.hoarder.gui.settings;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.SettingsValues;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.compound.DFileChooser;
import drakovek.hoarder.processing.sort.FileSort;

/**
 * Settings Mode GUI for selecting the user's DVK Directories
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DirectorySettingsGUI extends SettingsModeGUI
{
	/**
	 * List showing the currently listed DVK Directories
	 */
	DList directoryList;
	
	/**
	 * ArrayList of DVK Directories
	 */
	ArrayList<File> dvkDirectories;
	
	/**
	 * File chooser for choosing DVK directories
	 */
	DFileChooser fileChooser;
	
	/**
	 * Initializes the DirectorySettingsGUI class.
	 * 
	 * @param settingsGUI SettingsGUI Parent settingsGUI
	 */
	public DirectorySettingsGUI(SettingsGUI settingsGUI)
	{
		super(settingsGUI);
		dvkDirectories = getSettings().getDvkDirectories();
		fileChooser = new DFileChooser(getSettings());
		
		//CREATE BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2, getSettings().getSpaceSize(), 0));
		bottomPanel.add(new DButton(this, CommonValues.ADD));
		bottomPanel.add(new DButton(this, CommonValues.REMOVE));
		
		directoryList = new DList(this, true, SettingsValues.DVK_DIRECTORIES);
		setDirectoryList();
		DScrollPane directoryScroll = new DScrollPane(getSettings(), ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, directoryList);
		
		//FINALIZE PANEL
		getPanel().setLayout(new BorderLayout());
		getPanel().add(new DLabel(this, directoryList, SettingsValues.DVK_DIRECTORIES), BorderLayout.NORTH);
		getPanel().add(this.getSpacedPanel(directoryScroll, 1, 1, true, true, false, false));
		getPanel().add(bottomPanel, BorderLayout.SOUTH);
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the displayed directory list based off of the current list of DVK directories while removing invalid entries.
	 */
	private void setDirectoryList()
	{
		//REMOVE NULL AND INVALID FILES
		for(int i = 0; i < dvkDirectories.size(); i++)
		{
			if(dvkDirectories.get(i) == null || !dvkDirectories.get(i).isDirectory())
			{
				dvkDirectories.remove(i);
				i--;
				
			}//IF
			
		}//FOR
		
		//REMOVE DUPLICATES
		for(int i = 0; i < dvkDirectories.size(); i++)
		{
			for(int k = i + 1; k < dvkDirectories.size(); k++)
			{
				if(dvkDirectories.get(i).equals(dvkDirectories.get(k)))
				{
					dvkDirectories.remove(k);
					k--;
					
				}//IF
				
			}//FOR
			
		}//FOR
		
		//CONVERT DIRECTORIES TO STRINGS AND SHOW IN LIST
		dvkDirectories = FileSort.sortFiles(dvkDirectories);
		String[] directoryStrings = new String[dvkDirectories.size()];
		for(int i = 0; i < dvkDirectories.size(); i++)
		{
			directoryStrings[i] = dvkDirectories.get(i).getAbsolutePath();
			
		}//FOR
		
		directoryList.setListData(directoryStrings);
		checkChanged();
		
	}//METHOD

	/**
	 * Checks if any of the DVK directories have changed from those found in the settings file, and if so, allows the user to save changes.
	 */
	private void checkChanged()
	{
		boolean changed = false;
		
		if(dvkDirectories.size() != getSettings().getDvkDirectories().size())
		{
			changed = true;
			
		}//IF
		else
		{
			for(int i = 0; i < dvkDirectories.size(); i++)
			{
				if(!getSettings().getDvkDirectories().get(i).equals(dvkDirectories.get(i)))
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
	 * Adds a directory selected with a file chooser to the list of DVK Directories.
	 */
	private void addDirectory()
	{
		if(dvkDirectories.size() == 0)
		{
			dvkDirectories.add(fileChooser.openDialog(getSettingsGUI().getFrame(), null, null));
		}
		else
		{
			dvkDirectories.add(fileChooser.openDialog(getSettingsGUI().getFrame(), dvkDirectories.get(0), null));
			
		}//ELSE
		setDirectoryList();
		
	}//METHOD
	
	/**
	 * Removes selected entries from the list of DVK directories.
	 */
	private void removeDirectories()
	{
		int[] indexes = directoryList.getSelectedIndices();
		for(int i = 0; i < indexes.length; i++)
		{
			if(indexes[i] < dvkDirectories.size())
			{
				dvkDirectories.set(indexes[i], null);
				
			}//IF
			
		}//FOR
		
		setDirectoryList();
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case CommonValues.ADD:
				addDirectory();
				break;
			case CommonValues.REMOVE:
				removeDirectories();
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void apply()
	{
		getSettings().setDvkDirectories(dvkDirectories);
		getSettingsGUI().getDvkHandler().clearDVKs();
		
	}//METHOD
	
}//CLASS
