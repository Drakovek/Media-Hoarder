package drakovek.hoarder.gui.swing.compound;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.components.DFrame;

/**
 * Class that checks if a given directory is included in the specified DMF directories, and if not, asks the user if it should be added.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DCheckDirectoriesGUI
{
	/**
	 * Initializes the DCheckDirectoriesGUI
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHandler
	 * @param frame Frame to parent button dialog to, if necessary
	 * @param file Given directory to check if it is included in the DMF directories list
	 * @since 2.0
	 */
	public DCheckDirectoriesGUI(DSettings settings, DmfHandler dmfHandler, DFrame frame, final File file)
	{
		if(!isDirectoryListed(settings, file))
		{
			DButtonDialog buttonDialog = new DButtonDialog(settings);
			String[] buttonIDs = {DefaultLanguage.YES, DefaultLanguage.NO};
			String response = buttonDialog.openButtonDialog(frame, DefaultLanguage.ADD_DIRECTORY_TITLE, DefaultLanguage.ADD_DIRECTORY_MESSAGES, buttonIDs);
			
			if(response.equals(DefaultLanguage.YES))
			{
				ArrayList<File> dmfDirectories = settings.getDmfDirectories();
				dmfDirectories.add(file);
				settings.setDmfDirectories(dmfDirectories);
				dmfHandler.clearDMFs();
				
			}//IF 
			
		}//IF
		
	}//CONSTRUCTOR
	
	/**
	 * Returns whether a given file is included in the specified DMF directories.
	 * 
	 * @param settings Program Settings
	 * @param file Given directory to check if it is included in the DMF directories list
	 * @return Whether given directory is included in the DMF directories list
	 * @since 2.0
	 */
	private static boolean isDirectoryListed(DSettings settings, final File file)
	{
		boolean isListed = false;
		
		for(int i = 0; !isListed && i < settings.getDmfDirectories().size(); i++)
		{
			File dmfDirectory = settings.getDmfDirectories().get(i);
			File compFile = file;
			
			while(compFile != null && compFile.isDirectory())
			{
				if(dmfDirectory.equals(compFile))
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
