package drakovek.hoarder.file.dvk;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.ExtensionFilter;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.DvkLanguageValues;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.processing.sort.FileSort;

/**
 * Class for handling large amounts of DVK information.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DvkDatabase
{
	/**
	 * ArrayList containing DVK Files from the DVK class
	 */
	private ArrayList<File> dvkFiles;
	
	/**
	 * ArrayList containing IDs from the DVK class
	 */
	private ArrayList<String> ids;
	
	/**
	 * ArrayList containing Titles from the DVK class
	 */
	private ArrayList<String> titles;
	
	/**
	 * ArrayList containing Artists from the DVK class
	 */
	private ArrayList<String[]> artists;

	/**
	 * ArrayList containing Times from the DVK class
	 */
	private ArrayList<Long> times;
	
	/**
	 * ArrayList containing Web Tags from the DVK class
	 */
	private ArrayList<String[]> webTags;
	
	/**
	 * ArrayList containing Descriptions from the DVK class
	 */
	private ArrayList<String> descriptions;
	
	/**
	 * ArrayList containing Page URLs from the DVK class
	 */
	private ArrayList<String> pageURLs;
	
	/**
	 * ArrayList containing Direct URLs from the DVK class
	 */
	private ArrayList<String> directURLs;
	
	/**
	 * ArrayList containing Secondary Media URLs from the DVK class
	 */
	private ArrayList<String> secondaryURLs;
	
	/**
	 * ArrayList containing Media Files from the DVK class
	 */
	private ArrayList<File> mediaFiles;
	
	/**
	 * ArrayList containing Secondary Media Files from the DVK class
	 */
	private ArrayList<File> secondaryFiles;
	
	/**
	 * Initializes the DvkDatabase class to be empty.
	 * 
	 * @param settings Program's settings
	 */
	public DvkDatabase()
	{
		clearDVKs();
		
	}//CONSTRUCTOR
	
	/**
	 * Clears all the DVK information from the object.
	 */
	private void clearDVKs()
	{
		//DVK
		dvkFiles = new ArrayList<>();
		ids = new ArrayList<>();
		
		//INFO
		titles = new ArrayList<>();
		artists = new ArrayList<>();
		times = new ArrayList<>();
		webTags = new ArrayList<>();
		descriptions = new ArrayList<>();

		//WEB
		pageURLs = new ArrayList<>();
		directURLs = new ArrayList<>();
		secondaryURLs = new ArrayList<>();
		
		//FILE
		mediaFiles = new ArrayList<>();
		secondaryFiles = new ArrayList<>();
		
	}//METHOD
	
	/**
	 * Loads the information from all DVKs in a given folder and in its sub-directories.
	 * 
	 * @param dvkDirectories Directories with DVKs to load
	 * @param progressDialog DProgress dialog to show progress of loading DVKs
	 * @param useIndexes Whether to use index files to load DvkDirectory object
	 * @param saveIndexes Whether to save DvkDirectories as index files
	 * @param updateIndexes Whether to update index files to reflect changes in DVKs
	 * @return Whether all DVKs were successfully loaded
	 * @version 2.0
	 */
	public boolean loadDVKs(final ArrayList<File> dvkDirectories, DProgressDialog progressDialog, final boolean useIndexes, final boolean saveIndexes, final boolean updateIndexes)
	{
		clearDVKs();
		progressDialog.setProcessLabel(DvkLanguageValues.GETTING_FOLDERS);
		progressDialog.setDetailLabel(CommonValues.RUNNING, true);
		progressDialog.setProgressBar(true, false, 0, 0);
		ArrayList<File> dvkFolders = getDvkFolders(dvkDirectories);
		DvkIndexing indexing = new DvkIndexing();
		
		for(int i = 0; !progressDialog.isCancelled() && i < dvkFolders.size(); i++)
		{
			progressDialog.setProcessLabel(DvkLanguageValues.LOADING_DVKS);
			progressDialog.setDetailLabel(dvkFolders.get(i).getName(), false);
			progressDialog.setProgressBar(false, true, dvkFolders.size(), i);
			DvkDirectory dvkDirectory = indexing.loadDVKs(dvkFolders.get(i), progressDialog, useIndexes, updateIndexes);
			
			if(!progressDialog.isCancelled() && saveIndexes)
			{
				progressDialog.setProcessLabel(DvkLanguageValues.SAVING_INDEX);
				progressDialog.setDetailLabel(dvkFolders.get(i).getName(), false);
				indexing.saveIndex(dvkDirectory);
				
			}//IF
			
			addDVKs(dvkDirectory);
			
		}//FOR
		
		indexing.close();
		
		return !progressDialog.isCancelled();
		
	}//METHOD
	
	/**
	 * Returns a list of all the directories and sub-directories within given folders that contain DVK files.
	 * 
	 * @param dvkDirectories Given Directories
	 * @return List of Directories containing DVKs
	 */
	public static ArrayList<File> getDvkFolders(final ArrayList<File> dvkDirectories)
	{
		ArrayList<File> directories = new ArrayList<>();
		for(File directory: dvkDirectories)
		{
			directories.addAll(getDvkFolders(directory));
			
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
	 * Returns a list of all the directories and sub-directories within a given folder that contain DVK files.
	 * 
	 * @param inputFolder Given Directory
	 * @return List of Directories containing DVKs
	 */
	private static ArrayList<File> getDvkFolders(final File inputFolder)
	{
		if(inputFolder != null && inputFolder.isDirectory())
		{
			String[] extension = {DVK.DVK_EXTENSION};
			ExtensionFilter filter = new ExtensionFilter(extension, false);
			ArrayList<File> dvkFolders = new ArrayList<>();
			ArrayList<File> directories = new ArrayList<>();
			directories.add(inputFolder);
			
			while(directories.size() > 0)
			{
				File[] files = directories.get(0).listFiles(filter);
				boolean hasDVK = false;
				
				for(int i = 0; i < files.length; i++)
				{
					if(files[i].isDirectory())
					{
						directories.add(files[i]);
						
					}//IF
					else
					{
						hasDVK = true;
						
					}//ELSE
					
				}//FOR
				
				if(hasDVK)
				{
					dvkFolders.add(directories.get(0));
					
				}//IF
				
				directories.remove(0);
				
			}//WHILE
			
			return FileSort.sortFiles(dvkFolders);
			
		}//IF
		
		return new ArrayList<>();
		
	}//METHOD
	
	/**
	 * Adds all DVKs from a given folder into the object's list of DVK information
	 *  
	 * @param dvkDirectory Directory from which to load DVKs
	 */
	private void addDVKs(DvkDirectory dvkDirectory)
	{
		//DVK
		dvkFiles.addAll(dvkDirectory.getDvkFiles());
		ids.addAll(dvkDirectory.getIDs());
		
		//INFO
		titles.addAll(dvkDirectory.getTitles());
		artists.addAll(dvkDirectory.getArtists());
		times.addAll(dvkDirectory.getTimes());
		webTags.addAll(dvkDirectory.getWebTags());
		descriptions.addAll(dvkDirectory.getDescriptions());

		//WEB
		pageURLs.addAll(dvkDirectory.getPageURLs());
		directURLs.addAll(dvkDirectory.getDirectURLs());
		secondaryURLs.addAll(dvkDirectory.getSecondaryURLs());
		
		//FILE
		mediaFiles.addAll(dvkDirectory.getMediaFiles());
		secondaryFiles.addAll(dvkDirectory.getSecondaryFiles());
		
	}//METHOD
	
	/**
	 * Returns the number of DVKs loaded.
	 * 
	 * @return Number of DVKs loaded
	 */
	public int getSize()
	{
		return dvkFiles.size();
		
	}//METHOD
	
	/**
	 * Replaces the DVK at a given index with a given DVK.
	 * 
	 * @param dvk Given DVK
	 * @param index Given Index
	 */
	public void setDVK(DVK dvk, final int index)
	{
		//DVK
		dvkFiles.set(index, dvk.getDvkFile());
		ids.set(index, dvk.getID());
				
		//INFO
		titles.set(index, dvk.getTitle());
		artists.set(index, dvk.getArtists());
		if(dvk.getTime() == 0)
		{
			times.set(index, null);
			
		}//IF
		else
		{
			times.set(index, Long.valueOf(dvk.getTime()));
			
		}//ELSE
		webTags.set(index, dvk.getWebTags());
		descriptions.set(index, dvk.getDescription());

		//WEB
		pageURLs.set(index, dvk.getPageURL());
		directURLs.set(index, dvk.getDirectURL());
		secondaryURLs.set(index, dvk.getSecondaryURL());
		
		//FILE
		mediaFiles.set(index, dvk.getMediaFile());
		secondaryFiles.set(index, dvk.getSecondaryFile());
		
	}//METHOD
	
	/**
	 * Adds a given DVK to the database.
	 * 
	 * @param dvk Given DVK
	 * @param index Given Index
	 */
	public void addDVK(DVK dvk)
	{
		//DVK
		dvkFiles.add(dvk.getDvkFile());
		ids.add(dvk.getID());
				
		//INFO
		titles.add(dvk.getTitle());
		artists.add(dvk.getArtists());
		if(dvk.getTime() == 0)
		{
			times.add(null);
			
		}//IF
		else
		{
			times.add(Long.valueOf(dvk.getTime()));
			
		}//ELSE
		webTags.add(dvk.getWebTags());
		descriptions.add(dvk.getDescription());

		//WEB
		pageURLs.add(dvk.getPageURL());
		directURLs.add(dvk.getDirectURL());
		secondaryURLs.add(dvk.getSecondaryURL());
		
		//FILE
		mediaFiles.add(dvk.getMediaFile());
		secondaryFiles.add(dvk.getSecondaryFile());
		
	}//METHOD
	
	/**
	 * Gets the DVK File from the DVK at a given index.
	 * 
	 * @param index Index
	 * @return DVK File
	 */
	public File getDvkFile(final int index)
	{
		return dvkFiles.get(index);
		
	}//METHOD
	
	/**
	 * Gets the ID from the DVK at a given index.
	 * 
	 * @param index Index
	 * @return ID
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
	 * Gets the Title from the DVK at a given index.
	 * 
	 * @param index Index
	 * @return Title
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
	 * Gets the Artists from the DVK at a given index.
	 * 
	 * @param index Index
	 * @return Artists
	 */
	public String[] getArtists(final int index)
	{
		String[] artistArray = artists.get(index);
		
		if(artistArray == null || artistArray.length == 0)
		{
			artistArray = new String[1];
			artistArray[0] = new String();
			
		}//IF

		return artistArray;
		
	}//METHOD
	
	/**
	 * Gets the time from the DVK at a given index.
	 * 
	 * @param index Index
	 * @return Time
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
	 * Gets the web tags from the DVK at a given index.
	 * 
	 * @param index Index
	 * @return Web Tags
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
	 * Gets the description from the DVK at a given index.
	 * 
	 * @param index Index
	 * @return Description
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
	 * Gets the page URL from the DVK at a given index.
	 * 
	 * @param index Index
	 * @return Page URL
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
	 * Gets the direct URL from the DVK at a given index.
	 * 
	 * @param index Index
	 * @return Media URL
	 */
	public String getDirectURL(final int index)
	{
		String directURL = directURLs.get(index);
		
		if(directURL != null)
		{
			return directURL;
			
		}//IF
		
		return new String();
		
	}//METHOD

	/**
	 * Gets the secondary media URL from the DVK at a given index.
	 * 
	 * @param index Index
	 * @return Secondary URL
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
	 * Gets the media file from the DVK at a given index.
	 * 
	 * @param index Index
	 * @return Media File
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
	 */
	public boolean containsMediaFile(final File file)
	{
		return mediaFiles.contains(file);
		
	}//METHOD
	
	/**
	 * Returns the secondary media file from the DVK at a given index.
	 * 
	 * @param index Index
	 * @return Secondary File
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
	 */
	public boolean containsSecondaryFile(final File file)
	{
		return secondaryFiles.contains(file);
		
	}//METHOD
	
}//CLASS
