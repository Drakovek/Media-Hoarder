package drakovek.hoarder.file.dmf;

import java.io.File;
import java.util.ArrayList;

/**
 * Class for other objects to access DMF information.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DmfHandler
{
	/**
	 * ArrayList full of indexes referencing DMFs loaded in DmfDatabase, sorted in a given order.
	 * 
	 * @since 2.0
	 */
	private ArrayList<Integer> sorted;
	
	/**
	 * ArrayList full of indexes referencing DMFs loaded in DmfDatabase, with certain indexes being omitted via a filter.
	 * 
	 * @since 2.0
	 */
	private ArrayList<Integer> filtered;
	
	/**
	 * DMF Database from which to load DMF info
	 * 
	 * @since 2.0
	 */
	private DmfDatabase database;
	
	/**
	 * Current directory loaded by the DmfHandler
	 * 
	 * @since 2.0
	 */
	private File directory;
	
	/**
	 * Initializes DmfHandler class
	 * 
	 * @since 2.0
	 */
	public DmfHandler()
	{
		clearDMFs();
		
	}//CONSTRUCTOR
	
	/**
	 * Resets all DMF information from the object
	 * 
	 * @since 2.0
	 */
	private void clearDMFs()
	{
		directory = null;
		database = new DmfDatabase();
		sorted = new ArrayList<>();
		filtered = new ArrayList<>();
		
	}//METHOD
	
	/**
	 * Loads the information from all DMFs in a given folder and in its sub-directories.
	 * 
	 * @param dmfFolder Input Folder
	 * @param useIndexes Whether to use index files to load DmfDirectory object
	 * @param updateIndexes Whether to update index files to reflect changes in DMFs
	 * @since 2.0
	 */
	public void loadDMFs(final File dmfFolder, final boolean useIndexes, final boolean updateIndexes)
	{
		directory = dmfFolder;
		database.loadDMFs(dmfFolder, useIndexes, updateIndexes);
		resetSorted();
		resetFiltered();
		
	}//METHOD
	
	/**
	 * Resets the sorted list to match the default order of DmfDatabase
	 * 
	 * @since 2.0
	 */
	private void resetSorted()
	{
		sorted = new ArrayList<>();
		int size = database.getSize();
		for(int i = 0; i < size; i++)
		{
			sorted.add(Integer.valueOf(i));
			
		}//FOR
		
	}//METHOD

	/**
	 * Resets the filtered list to match with the sorted list
	 * 
	 * @since 2.0
	 */
	private void resetFiltered()
	{
		filtered = new ArrayList<>();
		int size = sorted.size();
		for(int i = 0; i < size; i++)
		{
			filtered.add(sorted.get(i));
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Returns the currently loaded directory of the DmfHandler
	 * 
	 * @return Current Directory
	 * @since 2.0
	 */
	public File getDirectory()
	{
		return directory;
		
	}//METHOD
	
	/**
	 * Returns whether the given directory has been loaded by the DmfHandler
	 * (Returns true if given directory is equal to the current directory or is a sub-directory of the current directory)
	 * 
	 * @param inputDirectory Given Directory
	 * @return Whether the given directory has been loaded by the dmfHandler
	 * @since 2.0
	 */
	public boolean containsFile(final File inputDirectory)
	{
		if(inputDirectory != null)
		{
			File file = inputDirectory;
			while(file != null && file.isDirectory())
			{
				if(directory.equals(file))
				{
					return true;
				
				}//IF
			
				file = file.getParentFile();
			
			}//WHILE
			
		}//IF
		
		return false;
		
	}//METHOD
	
	/**
	 * Returns the number of DMFs loaded.
	 * 
	 * @return Number of DMFs loaded
	 * @since 2.0
	 */
	public int getSize()
	{
		return filtered.size();
		
	}//METHOD
	
	/**
	 * Gets the DMF File from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return DMF File
	 * @since 2.0
	 */
	public File getDmfFile(final int index)
	{
		return database.getDmfFile(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the ID from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return ID
	 * @since 2.0
	 */
	public String getID(final int index)
	{
		return database.getID(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the Title from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Title
	 * @since 2.0
	 */
	public String getTitle(final int index)
	{
		return database.getTitle(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the Artists from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Artists
	 * @since 2.0
	 */
	public String[] getArtists(final int index)
	{
		return database.getArtists(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the time from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Time
	 * @since 2.0
	 */
	public long getTime(final int index)
	{
		return database.getTime(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the web tags from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Web Tags
	 * @since 2.0
	 */
	public String[] getWebTags(final int index)
	{
		return database.getWebTags(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the description from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Description
	 * @since 2.0
	 */
	public String getDescription(final int index)
	{
		return database.getDescription(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the page URL from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Page URL
	 * @since 2.0
	 */
	public String getPageURL(final int index)
	{
		return database.getPageURL(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the media URL from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Media URL
	 * @since 2.0
	 */
	public String getMediaURL(final int index)
	{
		return database.getMediaURL(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the media file from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Media File
	 * @since 2.0
	 */
	public File getMediaFile(final int index)
	{
		return database.getMediaFile(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the previous IDs from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Last IDs
	 * @since 2.0
	 */
	public String[] getLastIDs(final int index)
	{
		return database.getLastIDs(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the next IDs from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Next IDs
	 * @since 2.0
	 */
	public String[] getNextIDs(final int index)
	{
		return database.getNextIDs(filtered.get(index).intValue());
	
	}//METHOD
	
	/**
	 * Gets the "isFirstInSection" variable from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Whether DMF is first in section
	 * @since 2.0
	 */
	public boolean getIsFirst(final int index)
	{
		return database.getIsFirst(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the "isLastInSection" variable from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Whether DMF is last in section
	 * @since 2.0
	 */
	public boolean getIsLast(final int index)
	{
		return database.getIsLast(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the sequence title from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Sequence Title
	 * @since 2.0
	 */
	public String getSequenceTitle(final int index)
	{
		return database.getSequenceTitle(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the section title from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Section Title
	 * @since 2.0
	 */
	public String getSectionTitle(final int index)
	{
		return database.getSectionTitle(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the branch titles from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Branch Titles
	 * @since 2.0
	 */
	public String[] getBranchTitles(final int index)
	{
		return database.getBranchTitles(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the rating from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Rating
	 * @since 2.0
	 */
	public int getRating(final int index)
	{
		return database.getRating(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the user tags from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return User Tags
	 * @since 2.0
	 */
	public String[] getUserTags(final int index)
	{
		return database.getUserTags(filtered.get(index).intValue());
		
	}//METHOD
	
}//CLASS
