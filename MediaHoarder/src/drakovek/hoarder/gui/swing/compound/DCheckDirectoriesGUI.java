package drakovek.hoarder.gui.swing.compound;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.dvk.DvkHandler;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.gui.swing.components.DFrame;

/**
 * Class that checks if a given directory is included in the specified DVK directories, and if not, asks the user if it should be added.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DCheckDirectoriesGUI
{
	/**
	 * Initializes the DCheckDirectoriesGUI
	 * 
	 * @param settings Program Settings
	 * @param dvkHandler Program's DvkHandler
	 * @param frame Frame to parent button dialog to, if necessary
	 * @param file Given directory to check if it is included in the DVK directories list
	 */
	public DCheckDirectoriesGUI(DSettings settings, DvkHandler dvkHandler, DFrame frame, final File file)
	{
		if(!isDirectoryListed(settings, file))
		{
			DButtonDialog buttonDialog = new DButtonDialog(settings);
			String[] buttonIDs = {CommonValues.YES, CommonValues.NO};
			String response = buttonDialog.openButtonDialog(frame, CommonValues.ADD_DIRECTORY_TITLE, CommonValues.ADD_DIRECTORY_MESSAGES, buttonIDs);
			
			if(response.equals(CommonValues.YES))
			{
				ArrayList<File> dvkDirectories = settings.getDvkDirectories();
				dvkDirectories.add(file);
				settings.setDvkDirectories(dvkDirectories);
				dvkHandler.clearDVKs();
				
			}//IF 
			
		}//IF
		
	}//CONSTRUCTOR
	
	/**
	 * Returns whether a given file is included in the specified DVK directories.
	 * 
	 * @param settings Program Settings
	 * @param file Given directory to check if it is included in the DVK directories list
	 * @return Whether given directory is included in the DVK directories list
	 */
	private static boolean isDirectoryListed(DSettings settings, final File file)
	{
		boolean isListed = false;
		
		for(int i = 0; !isListed && i < settings.getDvkDirectories().size(); i++)
		{
			File dvkDirectory = settings.getDvkDirectories().get(i);
			File compFile = file;
			
			while(compFile != null && compFile.isDirectory())
			{
				if(dvkDirectory.equals(compFile))
				{
					isListed = true;
					break;
					
				}//IF
				
				compFile = compFile.getParentFile();
				
			}//WHILE
			
		}//FOR
		
		return isListed;
		
	}//METHOD
	
}//CLASS
