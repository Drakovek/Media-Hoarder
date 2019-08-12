package drakovek.hoarder.file.dmf;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.ExtensionFilter;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.processing.sort.FileSort;

/**
 * Class for handling large amounts of DMF information.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
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
	 * ArrayList containing Artists from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String[]> artists;

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
	 * ArrayList containing Secondary Media URLs from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> secondaryURLs;
	
	/**
	 * ArrayList containing Media Files from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<File> mediaFiles;
	
	/**
	 * ArrayList containing Secondary Media Files from the DMF class
	 * 
	 * @since 2.0
	 */
	private ArrayList<File> secondaryFiles;
	
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
		artists = new ArrayList<>();
		times = new ArrayList<>();
		webTags = new ArrayList<>();
		descriptions = new ArrayList<>();

		//WEB
		pageURLs = new ArrayList<>();
		mediaURLs = new ArrayList<>();
		secondaryURLs = new ArrayList<>();
		
		//FILE
		mediaFiles = new ArrayList<>();
		secondaryFiles = new ArrayList<>();
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
	 * @param dmfDirectories Directories with DMFs to load
	 * @param progressDialog DProgress dialog to show progress of loading DMFs
	 * @param useIndexes Whether to use index files to load DmfDirectory object
	 * @param saveIndexes Whether to save DmfDirectories as index files
	 * @param updateIndexes Whether to update index files to reflect changes in DMFs
	 * @return Whether all DMFs were successfully loaded
	 * @version 2.0
	 * @since 2.0
	 */
	public boolean loadDMFs(final ArrayList<File> dmfDirectories, DProgressDialog progressDialog, final boolean useIndexes, final boolean saveIndexes, final boolean updateIndexes)
	{
		clearDMFs();
		progressDialog.setProcessLabel(DefaultLanguage.GETTING_FOLDERS);
		progressDialog.setDetailLabel(DefaultLanguage.RUNNING, true);
		progressDialog.setProgressBar(true, false, 0, 0);
		ArrayList<File> dmfFolders = getDmfFolders(dmfDirectories);
		DmfIndexing indexing = new DmfIndexing();
		
		for(int i = 0; !progressDialog.isCancelled() && i < dmfFolders.size(); i++)
		{
			progressDialog.setProcessLabel(DefaultLanguage.LOADING_DMFS);
			progressDialog.setDetailLabel(dmfFolders.get(i).getName(), false);
			progressDialog.setProgressBar(false, true, dmfFolders.size(), i);
			DmfDirectory dmfDirectory = indexing.loadDMFs(dmfFolders.get(i), progressDialog, useIndexes, updateIndexes);
			
			if(!progressDialog.isCancelled() && saveIndexes)
			{
				progressDialog.setProcessLabel(DefaultLanguage.SAVING_INDEX);
				progressDialog.setDetailLabel(dmfFolders.get(i).getName(), false);
				indexing.saveIndex(dmfDirectory);
				
			}//IF
			
			addDMFs(dmfDirectory);
			
		}//FOR
		
		indexing.close();
		
		return !progressDialog.isCancelled();
		
	}//METHOD
	
	/**
	 * Returns a list of all the directories and sub-directories within given folders that contain DMF files.
	 * 
	 * @param dmfDirectories Given Directories
	 * @return List of Directories containing DMFs
	 * @since 2.0
	 */
	public static ArrayList<File> getDmfFolders(final ArrayList<File> dmfDirectories)
	{
		ArrayList<File> directories = new ArrayList<>();
		for(File directory: dmfDirectories)
		{
			directories.addAll(getDmfFolders(directory));
			
		}//FOR
		
		//REMOVE DUPLICATES
		for(int i = 0; i < directories.size(); i++)
		{
			for(int k = i + 1; k < directories.size(); k++)
			{
				if(directories.get(i).equals(directories.get(k)))
				{
					directories.remove(k);
					k--;
					
				}//IF
				
			}//FOR
			
		}//FOR
		
		return FileSort.sortFiles(directories);
		
	}//METHOD
	
	/**
	 * Returns a list of all the directories and sub-directories within a given folder that contain DMF files.
	 * 
	 * @param inputFolder Given Directory
	 * @return List of Directories containing DMFs
	 * @since 2.0
	 */
	private static ArrayList<File> getDmfFolders(final File inputFolder)
	{
		if(inputFolder != null && inputFolder.isDirectory())
		{
			String[] extension = {DMF.DMF_EXTENSION};
			ExtensionFilter filter = new ExtensionFilter(extension, false);
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
			
			return FileSort.sortFiles(dmfFolders);
			
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
		artists.addAll(dmfDirectory.getArtists());
		times.addAll(dmfDirectory.getTimes());
		webTags.addAll(dmfDirectory.getWebTags());
		descriptions.addAll(dmfDirectory.getDescriptions());

		//WEB
		pageURLs.addAll(dmfDirectory.getPageURLs());
		mediaURLs.addAll(dmfDirectory.getMediaURLs());
		secondaryURLs.addAll(dmfDirectory.getSecondaryURLs());
		
		//FILE
		mediaFiles.addAll(dmfDirectory.getMediaFiles());
		secondaryFiles.addAll(dmfDirectory.getSecondaryFiles());
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
	 * Gets an ArrayList of indexes for a sequence given a single index.
	 * 
	 * @param index DMF Index
	 * @param sectionTitle Section Title of the section to include in the list (if null, returns the full sequence)
	 * @return ArrayList<Integer> of indexes in a sequence.
	 * @since 2.0
	 */
	public ArrayList<Integer> getSequenceList(final int index, final String sectionTitle)
	{	
		ArrayList<String> tree = getSequenceIdTree(index);
		ArrayList<Integer> sequenceList = new ArrayList<>();
		
		for(int i = 0; i < tree.size(); i++)
		{
			if(!tree.get(i).contains(Character.toString('(')) && !tree.get(i).contains(Character.toString('*')))
			{
				int currentIndex = getIdIndex(tree.get(i).replaceAll(Character.toString('>'), new String()));
				
				if(currentIndex != -1)
				{
					sequenceList.add(Integer.valueOf(currentIndex));
					
				}//IF
				
			}//IF
			
		}//FOR
		
		if(sectionTitle != null && sectionTitle.length() > 0)
		{
			for(int i = 0; i < sequenceList.size(); i++)
			{
				if(!getSectionTitle(sequenceList.get(i).intValue()).equals(sectionTitle))
				{
					sequenceList.remove(i);
					i--;
					
				}//IF
			
			}//FOR
			
		}//IF
		
		return sequenceList;

	}//METHOD
	
	/**
	 * Gets a sequence ID tree in ArrayList<String> form from a DMF index
	 * 
	 * @param index DMF Index
	 * @return Sequence ID Tree
	 * @since 2.0
	 */
	private ArrayList<String> getSequenceIdTree(final int index)
	{
		ArrayList<Integer> tempIndexes = new ArrayList<>();
		tempIndexes.add(Integer.valueOf(index));
		int currentIndex;
		
		while(true)
		{
			if(getLastIDs(tempIndexes.get(0).intValue()).length > 0)
			{
				currentIndex = getIdIndex(getLastIDs(tempIndexes.get(0).intValue())[0]);
				if(currentIndex == -1 || tempIndexes.contains(Integer.valueOf(currentIndex)))
				{
					break;
				
				}//IF

				tempIndexes.add(0, Integer.valueOf(currentIndex));
			
			}//IF
			else
			{
				break;
			
			}//ELSE
		
		}//WHILE
		
		return getTreeFromIndex(tempIndexes.get(0).intValue(), 1, new ArrayList<String>());
	
	}//METHOD
	
	/**
	 * Recursive Method to get Sequence ID Tree
	 * 
	 * @param index Index to start with.
	 * @param level Level of the Start index
	 * @param currentTree Tree passed in to expand on
	 * @return Sequence ID Tree
	 * @since 2.0
	 */
	private ArrayList<String> getTreeFromIndex(final int index, final int level, final ArrayList<String> currentTree)
	{
		boolean isContained;
		String ID;
		ArrayList<String> tree = new ArrayList<>();
		tree.addAll(currentTree);
		
		isContained = false;
		ID = Character.toString('>') + getID(index);
		for(int i = 0; i < tree.size(); i++)
		{
			if(tree.get(i).endsWith(ID))
			{
				isContained = true;
				break;
				
			}//IF
		
		}//FOR
		
		if(isContained)
		{
			tree.add(StringMethods.extendCharacter('>', level) + getID(index) + Character.toString('*'));

		}//IF
		else
		{
			tree.add(StringMethods.extendCharacter('>', level) + getID(index));
			int currentIndex = index;
			
			while(true)
			{
				if(getNextIDs(currentIndex).length == 0)
				{
					break;
			
				}//IF
				
				if(getNextIDs(currentIndex).length == 1)
				{
					int nextIndex = getIdIndex(getNextIDs(currentIndex)[0]);
					
					if(nextIndex == -1)
					{
						break;
						
					}//IF
					
					isContained = false;
					ID = Character.toString('>') + getID(nextIndex);
					for(int i = 0; i < tree.size(); i++)
					{
						if(tree.get(i).endsWith(ID))
						{
							isContained = true;
							break;
							
						}//IF
					
					}//FOR
					
					if(isContained)
					{
						tree.add(StringMethods.extendCharacter('>', level) + getID(nextIndex) + Character.toString('*'));
						break;
					
					}//IF

					tree.add(StringMethods.extendCharacter('>', level) + getID(nextIndex));
					currentIndex = nextIndex;
					
					
				}//IF
				else
				{
					for(int branchNum = 0; branchNum < getNextIDs(currentIndex).length; branchNum++)
					{
						int nextIndex = getIdIndex(getNextIDs(currentIndex)[branchNum]);
						if(nextIndex != -1)
						{
							String branchName = new String();
							if(branchNum < getBranchTitles(currentIndex).length)
							{
								branchName = getBranchTitles(currentIndex)[branchNum];
					
							}//IF
							
							branchName = Character.toString('(') + branchName + Character.toString(')');
							
							tree.add(StringMethods.extendCharacter('>', level) + branchName);
							tree = getTreeFromIndex(nextIndex, level + 1, tree);
							
						}//IF
						
					}//FOR
					
					break;
					
				}//ELSE
				
			}//WHILE
			
		}//ELSE
		
		return tree;
	
	}//METHOD getTreeFromIndex(final int index, final int level, final ArrayList<String> currentTree)
	
	/**
	 * Gets the index of a given ID
	 * 
	 * @param ID DMF ID
	 * @return DMF Index (-1 if DMF with given ID does not exist)
	 * @since 2.0
	 */
	private int getIdIndex(final String ID)
	{
		if(ID == null || ID.length() == 0 || ID.equals(DMF.EMPTY_ID))
		{
			return -1;
		
		}//IF

		return ids.indexOf(ID);
		
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
	 * Replaces the DMF at a given index with a given DMF.
	 * 
	 * @param dmf Given DMF
	 * @param index Given Index
	 * @since 2.0
	 */
	public void setDMF(DMF dmf, final int index)
	{
		//DMF
		dmfFiles.set(index, dmf.getDmfFile());
		ids.set(index, dmf.getID());
				
		//INFO
		titles.set(index, dmf.getTitle());
		artists.set(index, dmf.getArtists());
		if(dmf.getTime() == 0)
		{
			times.set(index, null);
			
		}//IF
		else
		{
			times.set(index, Long.valueOf(dmf.getTime()));
			
		}//ELSE
		webTags.set(index, dmf.getWebTags());
		descriptions.set(index, dmf.getDescription());

		//WEB
		pageURLs.set(index, dmf.getPageURL());
		mediaURLs.set(index, dmf.getMediaURL());
		secondaryURLs.set(index, dmf.getSecondaryURL());
		
		//FILE
		mediaFiles.set(index, dmf.getMediaFile());
		secondaryFiles.set(index, dmf.getSecondaryFile());
		lastIDs.set(index, dmf.getLastIDs());
		nextIDs.set(index, dmf.getNextIDs());
		isFirst.set(index, Boolean.valueOf(dmf.isFirstInSection()));
		isLast.set(index, Boolean.valueOf(dmf.isLastInSection()));
		
		//USER
		sequenceTitles.set(index, dmf.getSequenceTitle());
		sectionTitles.set(index, dmf.getSectionTitle());
		branchTitles.set(index, dmf.getBranchTitles());
		if(dmf.getRating() == 0)
		{
			ratings.set(index, null);
			
		}//IF
		else
		{
			ratings.set(index, Integer.valueOf(dmf.getRating()));
			
		}//ELSE
		userTags.set(index, dmf.getUserTags());
		
	}//METHOD
	
	/**
	 * Adds a given DMF to the database.
	 * 
	 * @param dmf Given DMF
	 * @param index Given Index
	 * @since 2.0
	 */
	public void addDMF(DMF dmf)
	{
		//DMF
		dmfFiles.add(dmf.getDmfFile());
		ids.add(dmf.getID());
				
		//INFO
		titles.add(dmf.getTitle());
		artists.add(dmf.getArtists());
		if(dmf.getTime() == 0)
		{
			times.add(null);
			
		}//IF
		else
		{
			times.add(Long.valueOf(dmf.getTime()));
			
		}//ELSE
		webTags.add(dmf.getWebTags());
		descriptions.add(dmf.getDescription());

		//WEB
		pageURLs.add(dmf.getPageURL());
		mediaURLs.add(dmf.getMediaURL());
		secondaryURLs.add(dmf.getSecondaryURL());
		
		//FILE
		mediaFiles.add(dmf.getMediaFile());
		secondaryFiles.add(dmf.getSecondaryFile());
		lastIDs.add(dmf.getLastIDs());
		nextIDs.add(dmf.getNextIDs());
		isFirst.add(Boolean.valueOf(dmf.isFirstInSection()));
		isLast.add(Boolean.valueOf(dmf.isLastInSection()));
		
		//USER
		sequenceTitles.add(dmf.getSequenceTitle());
		sectionTitles.add(dmf.getSectionTitle());
		branchTitles.add(dmf.getBranchTitles());
		if(dmf.getRating() == 0)
		{
			ratings.add(null);
			
		}//IF
		else
		{
			ratings.add(Integer.valueOf(dmf.getRating()));
			
		}//ELSE
		userTags.add(dmf.getUserTags());
		
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
	 * Returns if DMF at a given index is a single DMF.
	 * 
	 * @param index DMF Index
	 * @return Whether DMF at a given index is a single DMF
	 * @since 2.0
	 */
	public boolean isSingle(final int index)
	{
		return isFirstInSequence(index) && isLastInSequence(index);
				
	}//METHOD
	
	/**
	 * Returns if DMF at a given index is the first in a sequence.
	 * 
	 * @param index DMF Index
	 * @return Whether DMF at a given index is the first in a sequence
	 * @since 2.0
	 */
	public boolean isFirstInSequence(final int index)
	{
		return getLastIDs(index).length == 0 || getLastIDs(index)[0].equals(DMF.EMPTY_ID);
		
	}//METHOD
	
	/**
	 * Returns if DMF at a given index is the last in a sequence.
	 * 
	 * @param index DMF Index
	 * @return Whether DMF at a given index is the last in a sequence
	 * @since 2.0
	 */
	public boolean isLastInSequence(final int index)
	{
		return getNextIDs(index).length == 0 || getNextIDs(index)[0].equals(DMF.EMPTY_ID);
		
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
		String id = ids.get(index);
		if(id != null)
		{
			return id;
			
		}//IF
		
		return new String();
		
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
		String title = titles.get(index);
		
		if(title != null)
		{
			return title;
			
		}//IF
		
		return new String();
		
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
		String[] artistArray = artists.get(index);
		
		if(artistArray != null)
		{
			return artistArray;
			
		}//IF
		
		return new String[0];
		
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
		Long time = times.get(index);
		
		if(time != null)
		{
			return time.longValue();
			
		}//IF
		
		return 0L;
		
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
		String[] webTagArray = webTags.get(index);
		
		if(webTagArray != null)
		{
			return webTagArray;
			
		}//IF
		
		return new String[0];
		
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
		String description = descriptions.get(index);
		
		if(description != null)
		{
			return description;
			
		}//IF
		
		return new String();
		
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
		String pageURL = pageURLs.get(index);
		
		if(pageURL != null)
		{
			return pageURL;
			
		}//IF
		
		return new String();
		
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
		String mediaURL = mediaURLs.get(index);
		
		if(mediaURL != null)
		{
			return mediaURL;
			
		}//IF
		
		return new String();
		
	}//METHOD

	/**
	 * Gets the secondary media URL from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Secondary URL
	 * @since 2.0
	 */
	public String getSecondaryURL(final int index)
	{
		String secondaryURL = secondaryURLs.get(index);
		
		if(secondaryURL != null)
		{
			return secondaryURL;
			
		}//IF
		
		return new String();
		
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
	 * Returns whether the list of media files contains a given file.
	 * 
	 * @param file Given File
	 * @return Whether file is contained in media file list
	 * @since 2.0
	 */
	public boolean containsMediaFile(final File file)
	{
		return mediaFiles.contains(file);
		
	}//METHOD
	
	/**
	 * Returns the secondary media file from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Secondary File
	 * @since 2.0
	 */
	public File getSecondaryFile(final int index)
	{
		return secondaryFiles.get(index);
		
	}//METHOD
	
	/**
	 * Returns whether the list of secondary files contains a given file.
	 * 
	 * @param file Given File
	 * @return Whether file is contained in secondary file list
	 * @since 2.0
	 */
	public boolean containsSecondaryFile(final File file)
	{
		return secondaryFiles.contains(file);
		
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
		String[] lastIdArray = lastIDs.get(index);
		
		if(lastIdArray != null)
		{
			return lastIdArray;
			
		}//IF
		
		return new String[0];
		
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
		String[] nextIdArray = nextIDs.get(index);
	
		if(nextIdArray != null)
		{
			return nextIdArray;
			
		}//IF
		
		return new String[0];
		
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
		String sequenceTitle = sequenceTitles.get(index);
		
		if(sequenceTitle != null)
		{
			return sequenceTitle;
			
		}//IF
		
		return new String();
		
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
		String sectionTitle = sectionTitles.get(index);
		
		if(sectionTitle != null)
		{
			return sectionTitle;
			
		}//IF
		
		return new String();
		
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
		String[] branchTitleArray = branchTitles.get(index);
		
		if(branchTitleArray != null)
		{
			return branchTitleArray;
			
		}//IF
		
		return new String[0];
		
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
		Integer rating = ratings.get(index);
		
		if(rating != null)
		{
			return rating.intValue();
			
		}//IF
		
		return 0;
		
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
		String[] userTagArray = userTags.get(index);
		
		if(userTagArray != null)
		{
			return userTagArray;
			
		}//IF
		
		return new String[0];
	}//METHOD
	
}//CLASS
