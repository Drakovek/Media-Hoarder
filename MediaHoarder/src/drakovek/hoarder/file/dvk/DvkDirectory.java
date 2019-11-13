package drakovek.hoarder.file.dvk;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import drakovek.hoarder.file.ExtensionFilter;

/**
 * Contains all DVK information for a single directory (no sub-directories)
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DvkDirectory implements Serializable
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -3315692522529459025L;

	/**
	 * Directory from which DVK info for the object is loaded
	 */
	private File directory;
	
	/**
	 * ArrayList containing DVK Files from the DVK class
	 */
	private ArrayList<File> dvkFiles;
	
	/**
	 * ArrayList containing IDs from the Dvk class
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
	 * Initializes the DvkDirectory class to start with no DVK information.
	 */
	public DvkDirectory()
	{
		clearDVKs();
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes the DvkDirectory to contain the information for a given directory.
	 * 
	 * @param dvkFolder Directory from which to search for DVKs
	 */
	public DvkDirectory(final File dvkFolder)
	{
		loadDVKs(dvkFolder);
		
	}//CONSTRUCTOR
	
	/**
	 * Clears all the DVK information from the object.
	 */
	private void clearDVKs()
	{
		directory = null;
		
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
	 * Loads the information from all the DVKs in a given directory.
	 * 
	 * @param dvkFolder Directory from which to search for DVKs
	 */
	public void loadDVKs(final File dvkFolder)
	{
		clearDVKs();
		
		if(dvkFolder != null && dvkFolder.isDirectory())
		{
			directory = dvkFolder;
			String[] extension = {DVK.DVK_EXTENSION};
			File[] dvks = dvkFolder.listFiles(new ExtensionFilter(extension, false));
			
			for(int i = 0; i < dvks.length; i++)
			{
				addDVK(dvks[i]);
				
			}//FOR
			
		}//IF
		
	}//METHOD
	
	/**
	 * Adds the information from a single DVK to the object's list of DVK information
	 * 
	 * @param dvkFile DVK File
	 */
	private void addDVK(final File dvkFile)
	{
		DVK dvk = new DVK(dvkFile);
		addDVK(dvk);
		
	}//METHOD
	
	/**
	 * Adds the information from a single DVK to the object's list of DVK information
	 * 
	 * @param dvk Given DVK
	 */
	public void addDVK(DVK dvk)
	{
		if(dvk.isValidRead())
		{
			//DVK
			dvkFiles.add(dvk.getDvkFile());
			ids.add(dvk.getID());
			
			//INFO
			titles.add(dvk.getTitle());
			artists.add(dvk.getArtists());
			if(dvk.getTime() == 0L)
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
		
		}//IF
		
	}//METHOD
	
	/**
	 * Checks whether the current DvkDirectory object is valid. Mainly to check if object has correctly been loaded from an index.
	 * 
	 * @param intendedDirectory Directory the current object should represent
	 * @return Whether the object is valid
	 */
	public boolean isValid(final File intendedDirectory)
	{
		boolean valid = true;
		
		//CHECK IF DIRECTORY IS CORRECT
		if(getDirectory() == null || !getDirectory().equals(intendedDirectory))
		{
			valid = false;
			
		}//IF
		
		//CHECK IF LISTS ARE EQUAL SIZE
		int size = getDvkFiles().size();
		if(valid && (
		   size != getTitles().size() ||
		   size != getIDs().size() ||
		   size != getArtists().size() ||
		   size != getTimes().size() ||
		   size != getWebTags().size() ||
		   size != getDescriptions().size() ||
		   size != getPageURLs().size() ||
		   size != getDirectURLs().size() ||
		   size != getSecondaryURLs().size() ||
		   size != getMediaFiles().size() ||
		   size != getSecondaryFiles().size()))
		{
			valid = false;
			
		}//IF
		
		return valid;
		
	}//METHOD
	
	/**
	 * Updates the current DVK info to reflect any changes in the DVKs of the loaded directory.
	 * 
	 * @param lastModified Time and date the index file for this directory was last modified
	 */
	public void updateDirectory(final long lastModified)
	{
		for(int i = 0; i < dvkFiles.size(); i++)
		{
			if(dvkFiles.get(i) == null || !dvkFiles.get(i).exists() || mediaFiles.get(i) == null || !mediaFiles.get(i).exists() || (secondaryFiles.get(i) != null && !secondaryFiles.get(i).exists()))
			{
				//REMOVE DVKS THAT NO LONGER EXIST
				
				//DVK
				dvkFiles.remove(i);
				ids.remove(i);
				
				//INFO
				titles.remove(i);
				artists.remove(i);
				times.remove(i);
				webTags.remove(i);
				descriptions.remove(i);

				//WEB
				pageURLs.remove(i);
				directURLs.remove(i);
				secondaryURLs.remove(i);
				
				//FILE
				mediaFiles.remove(i);
				secondaryFiles.remove(i);
				i--;
				
			}//IF
			else if(lastModified < dvkFiles.get(i).lastModified())
			{
				//UPDATE DVKS THAT HAVE BEEN EDITED
				
				DVK dvk = new DVK(dvkFiles.get(i));
				
				//DVK
				dvkFiles.set(i, dvk.getDvkFile());
				ids.set(i, dvk.getID());
				
				//INFO
				titles.set(i, dvk.getTitle());
				artists.set(i, dvk.getArtists());
				times.set(i, Long.valueOf(dvk.getTime()));
				webTags.set(i, dvk.getWebTags());
				descriptions.set(i, dvk.getDescription());

				//WEB
				pageURLs.set(i, dvk.getPageURL());
				directURLs.set(i, dvk.getDirectURL());
				secondaryURLs.set(i, dvk.getSecondaryURL());
				
				//FILE
				mediaFiles.set(i, dvk.getMediaFile());
				secondaryFiles.set(i, dvk.getSecondaryFile());
				dvk = null;
				
			}//ELSE IF
			
		}//FOR
		
		//ADDS NEW DVKS
		String[] extension = {DVK.DVK_EXTENSION};
		File[] allDvkFiles = getDirectory().listFiles(new ExtensionFilter(extension, false));
		for(int i = 0; i < allDvkFiles.length; i++)
		{
			if(!dvkFiles.contains(allDvkFiles[i]))
			{
				DVK dvk = new DVK(allDvkFiles[i]);
				if(!ids.contains(dvk.getID()))
				{
					addDVK(dvk);
					
				}//IF
				
			}//IF
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Returns the currently loaded DVK directory
	 * 
	 * @return DVK Directory
	 */
	public File getDirectory()
	{
		return directory;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of DVK Files
	 * 
	 * @return DVK Files
	 */
	public ArrayList<File> getDvkFiles()
	{
		return dvkFiles;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of DVK IDs
	 * 
	 * @return DVK IDs
	 */
	public ArrayList<String> getIDs()
	{
		return ids;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of titles
	 * 
	 * @return Titles
	 */
	public ArrayList<String> getTitles()
	{
		return titles;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of Artist String Arrays
	 * 
	 * @return Artists
	 */
	public ArrayList<String[]> getArtists()
	{
		return artists;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of times of publishing for the currently loaded DVKs
	 * 
	 * @return Publishing Times
	 */
	public ArrayList<Long> getTimes()
	{
		return times;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of web tag String arrays.
	 * 
	 * @return Web Tags
	 */
	public ArrayList<String[]> getWebTags()
	{
		return webTags;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of DVK descriptions.
	 * 
	 * @return Descriptions
	 */
	public ArrayList<String> getDescriptions()
	{
		return descriptions;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of page URLs
	 * 
	 * @return Page URLs
	 */
	public ArrayList<String> getPageURLs()
	{
		return pageURLs;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of direct URLs
	 * 
	 * @return Media URLs
	 */
	public ArrayList<String> getDirectURLs()
	{
		return directURLs;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of secondary media URLs.
	 * 
	 * @return Secondary URLs
	 */
	public ArrayList<String> getSecondaryURLs()
	{
		return secondaryURLs;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of media files.
	 * 
	 * @return Media Files
	 */
	public ArrayList<File> getMediaFiles()
	{
		return mediaFiles;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of secondary media files.
	 * 
	 * @return Secondary Files
	 */
	public ArrayList<File> getSecondaryFiles()
	{
		return secondaryFiles;
		
	}//METHOD
	
}//CLASS
