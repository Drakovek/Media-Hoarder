package drakovek.hoarder.file.dmf;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import drakovek.hoarder.file.ExtensionFilter;

/**
 * Contains all DMF information for a single directory (no sub-directories)
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DmfDirectory implements Serializable
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = 7765584630775373828L;

	/**
	 * Directory from which DMF info for the object is loaded
	 * 
	 * @since 2.0
	 */
	private File directory;
	
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
	 * Initializes the DmfDirectory class to start with no DMF information.
	 * 
	 * @since 2.0
	 */
	public DmfDirectory()
	{
		clearDMFs();
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes the DmfDirectory to contain the information for a given directory.
	 * 
	 * @param dmfFolder Directory from which to search for DMFs
	 * @since 2.0
	 */
	public DmfDirectory(final File dmfFolder)
	{
		loadDMFs(dmfFolder);
		
	}//CONSTRUCTOR
	
	/**
	 * Clears all the DMF information from the object.
	 * 
	 * @since 2.0
	 */
	private void clearDMFs()
	{
		directory = null;
		
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
	 * Loads the information from all the DMFs in a given directory.
	 * 
	 * @param dmfFolder Directory from which to search for DMFs
	 * @since 2.0
	 */
	public void loadDMFs(final File dmfFolder)
	{
		clearDMFs();
		
		if(dmfFolder != null && dmfFolder.isDirectory())
		{
			directory = dmfFolder;
			String[] extension = {DMF.DMF_EXTENSION};
			File[] dmfs = dmfFolder.listFiles(new ExtensionFilter(extension, false));
			
			for(File dmf: dmfs)
			{
				addDMF(dmf);
				
			}//FOR
			
		}//IF
		
	}//METHOD
	
	/**
	 * Adds the information from a single DMF to the object's list of DMF information
	 * 
	 * @param dmfFile DMF File
	 * @since 2.0
	 */
	private void addDMF(final File dmfFile)
	{
		DMF dmf = new DMF(dmfFile);
		addDMF(dmf);
		
	}//METHOD
	
	/**
	 * Adds the information from a single DMF to the object's list of DMF information
	 * 
	 * @param dmf Given DMF
	 * @since 2.0
	 */
	public void addDMF(DMF dmf)
	{
		if(dmf.isValid())
		{
			//DMF
			dmfFiles.add(dmf.getDmfFile());
			ids.add(dmf.getID());
			
			//INFO
			titles.add(dmf.getTitle());
			artists.add(dmf.getArtists());
			if(dmf.getTime() == 0L)
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
		
		}//IF
		
	}//METHOD
	
	/**
	 * Checks whether the current DmfDirectory object is valid. Mainly to check if object has correctly been loaded from an index.
	 * 
	 * @param intendedDirectory Directory the current object should represent
	 * @return Whether the object is valid
	 * @since 2.0
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
		int size = getDmfFiles().size();
		if(valid && (
		   size != getTitles().size() ||
		   size != getIDs().size() ||
		   size != getArtists().size() ||
		   size != getTimes().size() ||
		   size != getWebTags().size() ||
		   size != getDescriptions().size() ||
		   size != getPageURLs().size() ||
		   size != getMediaURLs().size() ||
		   size != getSecondaryURLs().size() ||
		   size != getMediaFiles().size() ||
		   size != getSecondaryFiles().size() ||
		   size != getLastIDs().size() ||
		   size != getNextIDs().size() ||
		   size != getIsFirst().size() ||
		   size != getIsLast().size() ||
		   size != getSequenceTitles().size() ||
		   size != getSectionTitles().size() ||
		   size != getBranchTitles().size() ||
		   size != getRatings().size() ||
		   size != getUserTags().size() ))
		{
			valid = false;
			
		}//IF
		
		//CHECK LISTS CONTAIN THE CORRECT OBJECTS
		if(valid &&
		   getDmfFiles().size() > 0 && (
		   !(dmfFiles.get(0) instanceof java.io.File) ||
		   !(ids.get(0) instanceof java.lang.String) ||
		   !(titles.get(0) instanceof java.lang.String) ||
		   !(artists.get(0) instanceof java.lang.String[]) ||
		   !(times.get(0) instanceof java.lang.Long) ||
		   !(descriptions.get(0) instanceof java.lang.String) ||
		   !(pageURLs.get(0) instanceof java.lang.String) ||
		   !(mediaURLs.get(0) instanceof java.lang.String) ||
		   !(secondaryURLs.get(0) instanceof java.lang.String) ||
		   !(mediaFiles.get(0) instanceof java.io.File) ||
		   !(secondaryFiles.get(0) instanceof java.io.File) ||
		   !(lastIDs.get(0) instanceof java.lang.String[]) ||
		   !(nextIDs.get(0) instanceof java.lang.String[]) ||
		   !(isFirst.get(0) instanceof java.lang.Boolean) ||
		   !(isLast.get(0) instanceof java.lang.Boolean) ||
		   !(sequenceTitles.get(0) instanceof java.lang.String) ||
		   !(sectionTitles.get(0) instanceof java.lang.String) ||
		   !(ratings.get(0) instanceof java.lang.Integer) ||
		   !(userTags.get(0) instanceof java.lang.String[])))
		{
			valid = false;
			
		}//IF
		
		return valid;
		
	}//METHOD
	
	/**
	 * Updates the current DMF info to reflect any changes in the DMFs of the loaded directory.
	 * 
	 * @param lastModified Time and date the index file for this directory was last modified
	 * @since 2.0
	 */
	public void updateDirectory(final long lastModified)
	{
		for(int i = 0; i < dmfFiles.size(); i++)
		{
			if(dmfFiles.get(i) == null || !dmfFiles.get(i).exists())
			{
				//REMOVE DMFS THAT NO LONGER EXIST
				
				//DMF
				dmfFiles.remove(i);
				ids.remove(i);
				
				//INFO
				titles.remove(i);
				artists.remove(i);
				times.remove(i);
				webTags.remove(i);
				descriptions.remove(i);

				//WEB
				pageURLs.remove(i);
				mediaURLs.remove(i);
				secondaryURLs.remove(i);
				
				//FILE
				mediaFiles.remove(i);
				secondaryFiles.remove(i);
				lastIDs.remove(i);
				nextIDs.remove(i);
				isFirst.remove(i);
				isLast.remove(i);

				//USER
				sequenceTitles.remove(i);
				sectionTitles.remove(i);
				branchTitles.remove(i);
				ratings.remove(i);
				userTags.remove(i);
				i--;
				
			}//IF
			else if(lastModified < dmfFiles.get(i).lastModified())
			{
				//UPDATE DMFS THAT HAVE BEEN EDITED
				
				DMF dmf = new DMF(dmfFiles.get(i));
				
				//DMF
				dmfFiles.set(i, dmf.getDmfFile());
				ids.set(i, dmf.getID());
				
				//INFO
				titles.set(i, dmf.getTitle());
				artists.set(i, dmf.getArtists());
				times.set(i, Long.valueOf(dmf.getTime()));
				webTags.set(i, dmf.getWebTags());
				descriptions.set(i, dmf.getDescription());

				//WEB
				pageURLs.set(i, dmf.getPageURL());
				mediaURLs.set(i, dmf.getMediaURL());
				secondaryURLs.set(i, dmf.getSecondaryURL());
				
				//FILE
				mediaFiles.set(i, dmf.getMediaFile());
				secondaryFiles.set(i, dmf.getSecondaryFile());
				lastIDs.set(i, dmf.getLastIDs());
				nextIDs.set(i, dmf.getNextIDs());
				isFirst.set(i, Boolean.valueOf(dmf.isFirstInSection()));
				isLast.set(i, Boolean.valueOf(dmf.isLastInSection()));

				//USER
				sequenceTitles.set(i, dmf.getSequenceTitle());
				sectionTitles.set(i, dmf.getSectionTitle());
				branchTitles.set(i, dmf.getBranchTitles());
				ratings.set(i, Integer.valueOf(dmf.getRating()));
				userTags.set(i, dmf.getUserTags());
				
				dmf = null;
				
			}//ELSE IF
			
		}//FOR
		
		//ADDS NEW DMFS
		String[] extension = {DMF.DMF_EXTENSION};
		File[] allDmfFiles = getDirectory().listFiles(new ExtensionFilter(extension, false));
		for(File dmfFile: allDmfFiles)
		{
			if(!dmfFiles.contains(dmfFile))
			{
				DMF dmf = new DMF(dmfFile);
				if(!ids.contains(dmf.getID()))
				{
					addDMF(dmf);
					
				}//IF
				
			}//IF
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Returns the currently loaded DMF directory
	 * 
	 * @return DMF Directory
	 * @since 2.0
	 */
	public File getDirectory()
	{
		return directory;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of DMF Files
	 * 
	 * @return DMF Files
	 * @since 2.0
	 */
	public ArrayList<File> getDmfFiles()
	{
		return dmfFiles;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of DMF IDs
	 * 
	 * @return DMF IDs
	 * @since 2.0
	 */
	public ArrayList<String> getIDs()
	{
		return ids;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of titles
	 * 
	 * @return Titles
	 * @since 2.0
	 */
	public ArrayList<String> getTitles()
	{
		return titles;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of Artist String Arrays
	 * 
	 * @return Artists
	 * @since 2.0
	 */
	public ArrayList<String[]> getArtists()
	{
		return artists;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of times of publishing for the currently loaded DMFs
	 * 
	 * @return Publishing Times
	 * @since 2.0
	 */
	public ArrayList<Long> getTimes()
	{
		return times;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of web tag String arrays.
	 * 
	 * @return Web Tags
	 * @since 2.0
	 */
	public ArrayList<String[]> getWebTags()
	{
		return webTags;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of DMF descriptions.
	 * 
	 * @return Descriptions
	 * @since 2.0
	 */
	public ArrayList<String> getDescriptions()
	{
		return descriptions;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of page URLs
	 * 
	 * @return Page URLs
	 * @since 2.0
	 */
	public ArrayList<String> getPageURLs()
	{
		return pageURLs;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of media URLs
	 * 
	 * @return Media URLs
	 * @since 2.0
	 */
	public ArrayList<String> getMediaURLs()
	{
		return mediaURLs;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of secondary media URLs.
	 * 
	 * @return Secondary URLs
	 * @since 2.0
	 */
	public ArrayList<String> getSecondaryURLs()
	{
		return secondaryURLs;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of media files.
	 * 
	 * @return Media Files
	 * @since 2.0
	 */
	public ArrayList<File> getMediaFiles()
	{
		return mediaFiles;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of secondary media files.
	 * 
	 * @return Secondary Files
	 * @since 2.0
	 */
	public ArrayList<File> getSecondaryFiles()
	{
		return secondaryFiles;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of String arrays containing previous IDs of a DMF sequence.
	 * 
	 * @return Last IDs
	 * @since 2.0
	 */
	public ArrayList<String[]> getLastIDs()
	{
		return lastIDs;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of String arrays containing next IDs of a DMF sequence.
	 * 
	 * @return Next IDs
	 * @since 2.0
	 */
	public ArrayList<String[]> getNextIDs()
	{
		return nextIDs;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of booleans indicating whether the given DMF is the first of a section.
	 * 
	 * @return Whether DMFs are the first in a section.
	 * @since 2.0
	 */
	public ArrayList<Boolean> getIsFirst()
	{
		return isFirst;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of booleans indicating whether the given DMF is the last of a section.
	 * 
	 * @return Whether DMFs are the last in a section.
	 * @since 2.0
	 */
	public ArrayList<Boolean> getIsLast()
	{
		return isLast;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of sequence titles.
	 * 
	 * @return Sequence Titles
	 * @since 2.0
	 */
	public ArrayList<String> getSequenceTitles()
	{
		return sequenceTitles;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of section titles.
	 * 
	 * @return Section Titles
	 * @since 2.0
	 */
	public ArrayList<String> getSectionTitles()
	{
		return sectionTitles;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of branch title String arrays.
	 * 
	 * @return Branch Titles
	 * @since 2.0
	 */
	public ArrayList<String[]> getBranchTitles()
	{
		return branchTitles;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of DMF ratings.
	 * 
	 * @return Ratings
	 * @since 2.0
	 */
	public ArrayList<Integer> getRatings()
	{
		return ratings;
		
	}//METHOD
	
	/**
	 * Returns ArrayList of user tags.
	 * 
	 * @return User Tags
	 * @since 2.0
	 */
	public ArrayList<String[]> getUserTags()
	{
		return userTags;
		
	}//METHOD
	
}//CLASS
