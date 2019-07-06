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
	private static final long serialVersionUID = -280406253949987653L;

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
			File[] dmfs = dmfFolder.listFiles(new ExtensionFilter(extension));
			
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
		if(dmf.isValid())
		{
			//DMF
			dmfFiles.add(dmf.getDmfFile());
			ids.add(dmf.getID());
			
			//INFO
			titles.add(dmf.getTitle());
			authors.add(dmf.getAuthors());
			times.add(new Long(dmf.getTime()));
			webTags.add(dmf.getWebTags());
			descriptions.add(dmf.getDescription());

			//WEB
			pageURLs.add(dmf.getPageURL());
			mediaURLs.add(dmf.getMediaURL());
			
			//FILE
			mediaFiles.add(dmf.getMediaFile());
			lastIDs.add(dmf.getLastIDs());
			nextIDs.add(dmf.getNextIDs());
			isFirst.add(new Boolean(dmf.isFirstInSection()));
			isLast.add(new Boolean(dmf.isLastInSection()));

			//USER
			sequenceTitles.add(dmf.getSequenceTitle());
			sectionTitles.add(dmf.getSectionTitle());
			branchTitles.add(dmf.getBranchTitles());
			ratings.add(new Integer(dmf.getRating()));
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
		if(!getDirectory().equals(intendedDirectory))
		{
			valid = false;
			
		}//IF
		
		//CHECK IF LISTS ARE EQUAL SIZE
		int size = getDmfFiles().size();
		if(valid && (
		   size != getTitles().size() ||
		   size != getIDs().size() ||
		   size != getAuthors().size() ||
		   size != getTimes().size() ||
		   size != getWebTags().size() ||
		   size != getDescriptions().size() ||
		   size != getPageURLs().size() ||
		   size != getMediaURLs().size() ||
		   size != getMediaFiles().size() ||
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
		   !(authors.get(0) instanceof java.lang.String[]) ||
		   !(times.get(0) instanceof java.lang.Long) ||
		   !(descriptions.get(0) instanceof java.lang.String) ||
		   !(pageURLs.get(0) instanceof java.lang.String) ||
		   !(mediaURLs.get(0) instanceof java.lang.String) ||
		   !(mediaFiles.get(0) instanceof java.io.File) ||
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
	 * Returns ArrayList of Author String Arrays
	 * 
	 * @return Authors
	 * @since 2.0
	 */
	public ArrayList<String[]> getAuthors()
	{
		return authors;
		
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
