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
	
	/**
	 * Initializes the DmfDatabase class to be empty.
	 * 
	 * @param settings Program's settings
	 * @since 2.0
	 */
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
		//DMF
		dmfFiles = new ArrayList<>();
		ids = new ArrayList<>();
		
		//INFO
		titles = new ArrayList<>();
		authors = new ArrayList<>();
		times = new ArrayList<>();
		webTags = new ArrayList<>();
		descriptions = new ArrayList<>();

		//WEB
		pageURLs = new ArrayList<>();
		mediaURLs = new ArrayList<>();
		
		//FILE
		mediaFiles = new ArrayList<>();
		lastIDs = new ArrayList<>();
		nextIDs = new ArrayList<>();
		isFirst = new ArrayList<>();
		isLast = new ArrayList<>();

		//USER
		sequenceTitles = new ArrayList<>();
		sectionTitles = new ArrayList<>();
		branchTitles = new ArrayList<>();
		ratings = new ArrayList<>();
		userTags = new ArrayList<>();
		
	}//METHOD
	
	/**
	 * Loads the information from all DMFs in a given folder and in its sub-directories.
	 * 
	 * @param dmfFolder Input Folder
	 * @param useIndexes Whether to use index files to load DmfDirectory object
	 * @param saveIndexes Whether to save DmfDirectory objects to index files
	 * @since 2.0
	 */
	public void loadDMFs(File dmfFolder, final boolean useIndexes, final boolean saveIndexes)
	{
		ArrayList<File> dmfFolders = getDmfFolders(dmfFolder);
		DmfIndexing indexing = new DmfIndexing();
		
		for(File folder: dmfFolders)
		{
			DmfDirectory dmfDirectory = indexing.loadDMFs(folder, useIndexes);
			
			if(saveIndexes)
			{
				indexing.saveIndex(dmfDirectory);
				
			}//IF
			
			addDMFs(dmfDirectory);
			
		}//FOR
		
		indexing.close();
		
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
		//DMF
		dmfFiles.addAll(dmfDirectory.getDmfFiles());
		ids.addAll(dmfDirectory.getIDs());
		
		//INFO
		titles.addAll(dmfDirectory.getTitles());
		authors.addAll(dmfDirectory.getAuthors());
		times.addAll(dmfDirectory.getTimes());
		webTags.addAll(dmfDirectory.getWebTags());
		descriptions.addAll(dmfDirectory.getDescriptions());

		//WEB
		pageURLs.addAll(dmfDirectory.getPageURLs());
		mediaURLs.addAll(dmfDirectory.getMediaURLs());
		
		//FILE
		mediaFiles.addAll(dmfDirectory.getMediaFiles());
		lastIDs.addAll(dmfDirectory.getLastIDs());
		nextIDs.addAll(dmfDirectory.getNextIDs());
		isFirst.addAll(dmfDirectory.getIsFirst());
		isLast.addAll(dmfDirectory.getIsLast());

		//USER
		sequenceTitles.addAll(dmfDirectory.getSequenceTitles());
		sectionTitles.addAll(dmfDirectory.getSectionTitles());
		branchTitles.addAll(dmfDirectory.getBranchTitles());
		ratings.addAll(dmfDirectory.getRatings());
		userTags.addAll(dmfDirectory.getUserTags());
		
	}//METHOD
	
	/**
	 * Returns the number of DMFs loaded.
	 * 
	 * @return Number of DMFs loaded
	 * @since 2.0
	 */
	public int getSize()
	{
		return dmfFiles.size();
		
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
		return dmfFiles.get(index);
		
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
		return ids.get(index);
		
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
		return titles.get(index);
		
	}//METHOD
	
	/**
	 * Gets the Authors from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Authors
	 * @since 2.0
	 */
	public String[] getAuthors(final int index)
	{
		return authors.get(index);
		
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
		return times.get(index).longValue();
		
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
		return webTags.get(index);
		
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
		return descriptions.get(index);
		
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
		return pageURLs.get(index);
		
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
		return mediaURLs.get(index);
		
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
		return mediaFiles.get(index);
		
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
		return lastIDs.get(index);
		
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
		return nextIDs.get(index);
	
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
		return isFirst.get(index).booleanValue();
		
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
		return isLast.get(index).booleanValue();
		
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
		return sequenceTitles.get(index);
		
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
		return sectionTitles.get(index);
		
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
		return branchTitles.get(index);
		
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
		return ratings.get(index).intValue();
		
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
		return userTags.get(index);
		
	}//METHOD
	
}//CLASS
