package drakovek.hoarder.file.dmf;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.ExtensionFilter;

/**
 * Class for handling large amounts of DMF information.
 * 
 * @author Drakovek
 * @version 2.0
 * @since
 */
public class DmfDatabase
{
	/**
	 * ArrayList containing DMF Files from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<File> dmfFiles;
	
	/**
	 * ArrayList containing IDs from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> ids;
	
	/**
	 * ArrayList containing Titles from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> titles;
	
	/**
	 * ArrayList containing Authors from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String[]> authors;

	/**
	 * ArrayList containing Times from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<Long> times;
	
	/**
	 * ArrayList containing Web Tags from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String[]> webTags;
	
	/**
	 * ArrayList containing Descriptions from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> descriptions;
	
	/**
	 * ArrayList containing Page URLs from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> pageURLs;
	
	/**
	 * ArrayList containing Media URLs from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> mediaURLs;
	
	/**
	 * ArrayList containing Media Files from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<File> mediaFiles;
	
	/**
	 * ArrayList containing Last IDs from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String[]> lastIDs;
	
	/**
	 * ArrayList containing Next IDs from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String[]> nextIDs;
	
	/**
	 * ArrayList containing "firstInSection" booleans from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<Boolean> isFirst;
	
	/**
	 * ArrayList containing "lastInSection" booleans from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<Boolean> isLast;
	
	/**
	 * ArrayList containing Sequence Titles from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> sequenceTitles;
	
	/**
	 * ArrayList containing Section Titles from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> sectionTitles;
	
	/**
	 * ArrayList containing Branch Titles from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String[]> branchTitles;
	
	/**
	 * ArrayList containing Ratings from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<Integer> ratings;
	
	/**
	 * ArrayList containing User Tags from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String[]> userTags;
	
	public DmfDatabase()
	{
		clearDMFs();
		
	}//CONSTRUCTOR
	
	/**
	 * Clears all the DMF information from the object.
	 * 
	 * @since 2.0
	 */
	private void clearDMFs()
	{
		dmfFiles = new ArrayList<>();
		
	}//METHOD
	
	/**
	 * Loads the information from all DMFs in a given folder and in its sub-directories.
	 * 
	 * @param dmfFolder Input Folder
	 * @since 2.0
	 */
	public void loadDMFs(File dmfFolder)
	{
		DmfDirectory dmfDirectory = new DmfDirectory();
		ArrayList<File> dmfFolders = getDmfFolders(dmfFolder);

		for(File folder: dmfFolders)
		{
			dmfDirectory.loadDMFs(folder);
			addDMFs(dmfDirectory);
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Returns a list of all the directories and sub-directories within a given folder that contain DMF files.
	 * 
	 * @param inputFolder Given Directory
	 * @return List of Directories containing DMFs
	 * @since 2.0
	 */
	private static ArrayList<File> getDmfFolders(File inputFolder)
	{
		if(inputFolder != null && inputFolder.isDirectory())
		{
			String[] extension = {DMF.DMF_EXTENSION};
			ExtensionFilter filter = new ExtensionFilter(extension);
			ArrayList<File> dmfFolders = new ArrayList<>();
			ArrayList<File> directories = new ArrayList<>();
			directories.add(inputFolder);
			
			while(directories.size() > 0)
			{
				File[] files = directories.get(0).listFiles(filter);
				boolean hasDMF = false;
				
				for(File file: files)
				{
					if(file.isDirectory())
					{
						directories.add(file);
						
					}//IF
					else
					{
						hasDMF = true;
						
					}//ELSE
					
				}//FOR
				
				if(hasDMF)
				{
					dmfFolders.add(directories.get(0));
					
				}//IF
				
				directories.remove(0);
				
			}//WHILE
			
			return dmfFolders;
			
		}//IF
		
		return new ArrayList<>();
		
	}//METHOD
	
	/**
	 * Adds all DMFs from a given folder into the object's list of DMF information
	 *  
	 * @param dmfDirectory Directory from which to load DMFs
	 * @since 2.0
	 */
	private void addDMFs(DmfDirectory dmfDirectory)
	{
		dmfFiles.addAll(dmfDirectory.getDmfFiles());
		
	}//METHOD
	
}//CLASS
