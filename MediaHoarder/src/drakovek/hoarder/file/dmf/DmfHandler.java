package drakovek.hoarder.file.dmf;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.processing.sort.AlphaNumSort;

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
	 * Int value indicating to sort DMFs by time published.
	 * 
	 * @since 2.0
	 */
	public static final int SORT_TIME = 0;
	
	/**
	 * Int value indicating to sort DMFs Alpha-numerically by title.
	 * 
	 * @since 2.0
	 */
	public static final int SORT_ALPHA = 1;
	
	/**
	 * Int value indicating to sort dmfs by rating.
	 * 
	 * @since 2.0
	 */
	public static final int SORT_RATING = 2;
	
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
	 * Boolean to show if DMFs were loaded properly
	 * 
	 * @since 2.0
	 */
	private boolean loaded;
	
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
	public void clearDMFs()
	{
		loaded = false;
		database = new DmfDatabase();
		sorted = new ArrayList<>();
		filtered = new ArrayList<>();
		
	}//METHOD
	
	/**
	 * Loads the information from all DMFs in a given folder and in its sub-directories.
	 * 
	 * @param dmfDirectories Directories with DMFs to load
	 * @param dmfFolder Input Folder
	 * @param progressDialog DProgress dialog to show progress of loading DMFx
	 * @param useIndexes Whether to use index files to load DmfDirectory object
	 * @param saveIndexes Whether to save DmfDirectories as index files
	 * @param updateIndexes Whether to update index files to reflect changes in DMFs
	 * @since 2.0
	 */
	public void loadDMFs(final ArrayList<File> dmfDirectories, DProgressDialog progressDialog, final boolean useIndexes, final boolean saveIndexes, final boolean updateIndexes)
	{
		loaded = database.loadDMFs(dmfDirectories, progressDialog, useIndexes, saveIndexes, updateIndexes);
		
		resetSorted();
		resetFiltered();
		
	}//METHOD
	
	/**
	 * Returns whether DMFs loaded properly.
	 * 
	 * @return Whether DMFs loaded properly
	 * @since 2.0
	 */
	public boolean isLoaded()
	{
		return loaded;
		
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
	 * Sorts the currently loaded DMFs.
	 * 
	 * @param sortType Sort Type
	 * @param groupArtists Whether to group artists
	 * @param groupSequences Whether to group sequences
	 * @param groupSections Whether to group sections
	 * @since 2.0
	 */
	public void sort(final int sortType, final boolean groupArtists, final boolean groupSequences, final boolean groupSections)
	{
		//POPULATE SORTED ARRAYLIST
		sorted = new ArrayList<>();
		for(int i = 0; i < database.getSize(); i++)
		{
			//Add items to sorted list
			if((groupSequences == false && groupSections == false) ||
			   (groupSequences == false && groupSections == true && ((sortType == SORT_TIME && database.getIsLast(i)) || (sortType != SORT_TIME && database.getIsFirst(i)) || database.isSingle(i))) ||
			   (groupSequences == true && ((sortType == SORT_TIME && database.isLastInSequence(i)) || (sortType != SORT_TIME && database.isFirstInSequence(i)) || database.isSingle(i))))
			{
				sorted.add(Integer.valueOf(i));
			
			}//IF
			
		}//FOR
		
		//SORT LIST
		sorted = sortMerge(sorted, sortType, groupArtists);
		
		//ADD SEQUENCES BACK TO ARRAY, IF NECESSARY
		if(groupSequences || groupSections)
		{
			ArrayList<Integer> newList = new ArrayList<>();
			for(int i = sorted.size() - 1; i > -1; i--)
			{
				if(!database.isSingle(sorted.get(i).intValue()))
				{
					if(!newList.contains(sorted.get(i)))
					{
						if(groupSections && !groupSequences)
						{
							newList.addAll(0, database.getSequenceList(sorted.get(i).intValue(), database.getSectionTitle(sorted.get(i).intValue())));
							
						}//IF
						else
						{
							newList.addAll(0, database.getSequenceList(sorted.get(i).intValue(), null));
					
						}//ELSE
						
					}//IF
				
				}//IF
				else
				{
					newList.add(0, sorted.get(i));
				
				}//ELSE
				
			}//FOR
			
			sorted = new ArrayList<>();
			sorted.addAll(newList);
			newList = null;
			
		}//IF (groupSequences == true || groupSections == true)
		
		resetFiltered();
		
	}//METHOD
	
	/**
	 * Sorts a list of DMF indexes using merge sort.
	 * 
	 * @param startList Given list of DMF indexes to sort.
	 * @param sortType Sort Type
	 * @param groupArtists Whether to group artists together
	 * @return Sorted list of DMF indexes
	 * @since 2.0
	 */
	private ArrayList<Integer> sortMerge(final ArrayList<Integer> startList, final int sortType, final boolean groupArtists)
	{
		if(startList.size() > 1)
		{
			//SPLIT INITIAL ARRAYLIST
			int half = startList.size() / 2;
			ArrayList<Integer> aList = new ArrayList<>();
			ArrayList<Integer> bList = new ArrayList<>();
			
			for(int i = 0; i < half; i++)
			{
				aList.add(startList.get(i));
				
			}//FOR
			
			for(int i = half; i < startList.size(); i++)
			{
				bList.add(startList.get(i));
				
			}//FOR
			
			//SORT SPLIT LISTS
			aList = sortMerge(aList, sortType, groupArtists);
			bList = sortMerge(bList, sortType, groupArtists);
			
			//MERGE LISTS
			ArrayList<Integer> returnList = new ArrayList<>();
			while(aList.size() > 0 && bList.size() > 0)
			{
				if(compareDMFs(aList.get(0).intValue(), bList.get(0).intValue(), sortType, groupArtists) < 0)
                {
                    returnList.add(aList.get(0));
                    aList.remove(0);
                    
                }//IF
                else
                {
                    returnList.add(bList.get(0));
                    bList.remove(0);
                    
                }//ELSE
				
			}//WHILE
			
			returnList.addAll(aList);
			returnList.addAll(bList);
			
			return returnList;
			
		}//IF
		
		return startList;
		
	}//METHOD
	
	/**
	 * Compares two DMFs given their indexes.
	 * 
	 * @param dmfA Index of the first DMF
	 * @param dmfB Index of the second DMF
	 * @param sortType Sort Type, used to determine which criteria to compare DMFs by
	 * @param groupArtists Whether to group artists together
	 * @return int value showing how the DMFs compare
	 * @since 2.0
	 */
	private int compareDMFs(final int dmfA, final int dmfB, final int sortType, final boolean groupArtists)
	{
		int result = 0;
		
		if(groupArtists == true)
		{
			result = AlphaNumSort.compareAlpha(StringMethods.arrayToString(database.getArtists(dmfA)), StringMethods.arrayToString(database.getArtists(dmfB)));
		
		}//IF
		
		if(result == 0 && sortType == SORT_RATING)
		{
			if(database.getRating(dmfA) > database.getRating(dmfB))
			{
				result = -1;
				
			}//IF
			else if(database.getRating(dmfA) < database.getRating(dmfB))
			{
				result = 1;
				
			}//ELSE IF
		
		}//IF
		
		if(result == 0 && (sortType == SORT_TIME || sortType == SORT_RATING))
		{
			if(database.getTime(dmfA) > database.getTime(dmfB))
			{
				result = 1;
				
			}//IF
			else if(database.getTime(dmfA) < database.getTime(dmfB))
			{
				result = -1;
			
			}//ELSE IF
		
		}//IF
		
		if(result == 0)
		{
			result = AlphaNumSort.compareAlpha(database.getTitle(dmfA), database.getTitle(dmfB));
		
		}//IF
		
		return result;
	
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
