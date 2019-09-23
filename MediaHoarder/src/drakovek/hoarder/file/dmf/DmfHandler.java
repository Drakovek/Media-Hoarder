package drakovek.hoarder.file.dmf;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.processing.BooleanSearch;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.processing.sort.AlphaNumSort;

/**
 * Class for other objects to access DMF information.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DmfHandler
{
	/**
	 * Int value indicating to sort DMFs by time published.
	 */
	public static final int SORT_TIME = 0;
	
	/**
	 * Int value indicating to sort DMFs Alpha-numerically by title.
	 */
	public static final int SORT_ALPHA = 1;
	
	/**
	 * Int value indicating to sort DMFs by rating.
	 */
	public static final int SORT_RATING = 2;
	
	/**
	 * Int value indicating to sort DMFs by view count
	 */
	public static final int SORT_VIEWS = 3;
	
	/**
	 * Whether to treat filter strings as case sensitive
	 */
	private boolean filterCaseSensitive;
	
	/**
	 * Filter string for DMF titles
	 */
	private String titleFilter;
	
	/**
	 * Filter string for DMF descriptions
	 */
	private String descriptionFilter;
	
	/**
	 * Filter string for DMF web tags
	 */
	private String webTagFilter;
	
	/**
	 * Filter string for DMF user tags
	 */
	private String userTagFilter;
	
	/**
	 * Filter string for DMF artists
	 */
	private String artistFilter;
	
	/**
	 * ArrayList full of indexes referencing DMFs loaded in DmfDatabase, sorted in a given order.
	 */
	private ArrayList<Integer> sorted;
	
	/**
	 * ArrayList full of indexes referencing DMFs loaded in DmfDatabase, with certain indexes being omitted via a filter.
	 */
	private ArrayList<Integer> filtered;
	
	/**
	 * DMF Database from which to load DMF info
	 */
	private DmfDatabase database;
	
	/**
	 * Boolean to show if DMFs were loaded properly
	 */
	private boolean loaded;
	
	/**
	 * Initializes DmfHandler class
	 */
	public DmfHandler()
	{
		clearDMFs();
		resetFilterStrings();
		
	}//CONSTRUCTOR
	
	/**
	 * Resets all DMF information from the object
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
	 */
	public boolean isLoaded()
	{
		return loaded;
		
	}//METHOD
	
	/**
	 * Resets the sorted list to match the default order of DmfDatabase
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
	 * @param reverseOrder Whether to reverse the sorting order
	 */
	public void sort(final int sortType, final boolean groupArtists, final boolean groupSequences, final boolean groupSections, final boolean reverseOrder)
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
		
		//REVERSE ORDER, IF NECESSARY
		if(reverseOrder)
		{
			ArrayList<Integer> reverseList = new ArrayList<>();
			for(int i = sorted.size() - 1; i > -1; i--)
			{
				reverseList.add(sorted.get(i));
				
			}//FOR
			
			sorted = new ArrayList<>();
			sorted.addAll(reverseList);
			reverseList = null;
			
		}//IF
		
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
	 */
	private int compareDMFs(final int dmfA, final int dmfB, final int sortType, final boolean groupArtists)
	{
		int result = 0;
		
		if(groupArtists == true)
		{
			result = AlphaNumSort.compareAlpha(StringMethods.arrayToString(database.getArtists(dmfA)).toLowerCase(), StringMethods.arrayToString(database.getArtists(dmfB)).toLowerCase());
		
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
		
		if(result == 0 && sortType == SORT_VIEWS)
		{
			if(database.getViews(dmfA) > database.getViews(dmfB))
			{
				result = -1;
				
			}//IF
			else if(database.getViews(dmfA) < database.getViews(dmfB))
			{
				result = 1;
				
			}//ELSE IF
			
			
		}//IF
		
		if(result == 0 && (sortType != SORT_ALPHA))
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
	 * Filters the currently loaded DMFs based on the current filter strings.
	 */
	public void filterDMFs()
	{
		resetFiltered();
		filterTitles();
		filterDescriptions();
		filterWebTags();
		filterUserTags();
		filterArtists();
		
	}//METHOD
	
	/**
	 * Filters loaded DMFs based on title.
	 */
	private void filterTitles()
	{
		if(getTitleFilter().length() > 0)
		{
			BooleanSearch booleanSearch = new BooleanSearch();
			booleanSearch.createSearchLogic(getTitleFilter());
			for(int i = 0; i < getSize(); i++)
			{
				if(!booleanSearch.searchText(getTitle(i), getFilterCaseSensitive(), false))
				{
					filtered.remove(i);
					i--;
				
				}//IF
			
			}//FOR
			
		}//IF
		
	}//IF
	
	/**
	 * Filters loaded DMFs based on description.
	 */
	private void filterDescriptions()
	{
		if(getDescriptionFilter().length() > 0)
		{
			BooleanSearch booleanSearch = new BooleanSearch();
			booleanSearch.createSearchLogic(getDescriptionFilter());
			for(int i = 0; i < getSize(); i++)
			{
				if(!booleanSearch.searchText(getDescription(i), getFilterCaseSensitive(), false))
				{
					filtered.remove(i);
					i--;
				
				}//IF
			
			}//FOR
			
		}//IF
		
	}//IF
	
	/**
	 * Filters loaded DMFs based on web tags.
	 */
	private void filterWebTags()
	{
		if(getWebTagFilter().length() > 0)
		{
			BooleanSearch booleanSearch = new BooleanSearch();
			booleanSearch.createSearchLogic(getWebTagFilter());
			for(int i = 0; i < getSize(); i++)
			{
				if(!booleanSearch.searchText(getWebTags(i), getFilterCaseSensitive(), true))
				{
					filtered.remove(i);
					i--;
				
				}//IF
			
			}//FOR
			
		}//IF
		
	}//IF
	
	/**
	 * Filters loaded DMFs based on user tags.
	 */
	private void filterUserTags()
	{
		if(getUserTagFilter().length() > 0)
		{
			BooleanSearch booleanSearch = new BooleanSearch();
			booleanSearch.createSearchLogic(getUserTagFilter());
			for(int i = 0; i < getSize(); i++)
			{
				if(!booleanSearch.searchText(getUserTags(i), getFilterCaseSensitive(), true))
				{
					filtered.remove(i);
					i--;
				
				}//IF
			
			}//FOR
			
		}//IF
		
	}//IF
	
	/**
	 * Filters loaded DMFs based on artist(s).
	 */
	private void filterArtists()
	{
		if(getArtistFilter().length() > 0)
		{
			BooleanSearch booleanSearch = new BooleanSearch();
			booleanSearch.createSearchLogic(getArtistFilter());
			for(int i = 0; i < getSize(); i++)
			{
				if(!booleanSearch.searchText(getArtists(i), getFilterCaseSensitive(), true))
				{
					filtered.remove(i);
					i--;
				
				}//IF
			
			}//FOR
			
		}//IF
		
	}//IF
	
	/**
	 * Returns the number of DMFs loaded.
	 * 
	 * @return Number of DMFs loaded
	 */
	public int getSize()
	{
		return filtered.size();
		
	}//METHOD
	
	/**
	 * Returns the DMF Database linked to the handler.
	 * 
	 * @return DmfDatabase
	 */
	public DmfDatabase getDatabase()
	{
		return database;
		
	}//METHOD
	
	/**
	 * Replaces the DMF at a given index with a given DMF.
	 * 
	 * @param dmf Given DMF
	 * @param index Given Index
	 */
	public void setDMF(DMF dmf, final int index)
	{
		database.setDMF(dmf, filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Adds a given DMF to the database.
	 * 
	 * @param dmf Given DMF
	 * @param index Given Index
	 */
	public void addDMF(DMF dmf)
	{
		database.addDMF(dmf);
		resetSorted();
		resetFiltered();
		
	}//METHOD
	
	/**
	 * Gets the DMF File from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return DMF File
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
	 */
	public String getPageURL(final int index)
	{
		return database.getPageURL(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the direct URL from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Media URL
	 */
	public String getDirectURL(final int index)
	{
		return database.getDirectURL(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the secondary media URL from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Secondary URL
	 */
	public String getSecondaryURL(final int index)
	{
		return database.getSecondaryURL(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the media file from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Media File
	 */
	public File getMediaFile(final int index)
	{
		return database.getMediaFile(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the secondary media file from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Secondary Media File
	 */
	public File getSecondaryFile(final int index)
	{
		return database.getSecondaryFile(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the previous IDs from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Last IDs
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
	 */
	public int getRating(final int index)
	{
		return database.getRating(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the view count from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return Number of Views
	 */
	public int getViews(final int index)
	{
		return database.getViews(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Gets the user tags from the DMF at a given index.
	 * 
	 * @param index Index
	 * @return User Tags
	 */
	public String[] getUserTags(final int index)
	{
		return database.getUserTags(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Resets all the filter strings.
	 */
	public void resetFilterStrings()
	{
		setFilterCaseSensitive(false);
		setTitleFilter(null);
		setDescriptionFilter(null);
		setWebTagFilter(null);
		setUserTagFilter(null);
		setArtistFilter(null);
		resetFiltered();
		
	}//METHOD
	
	/**
	 * Returns whether filter strings are treated as case sensitive.
	 * 
	 * @return Case Sensitive
	 */
	public boolean getFilterCaseSensitive()
	{
		return filterCaseSensitive;
		
	}//METHOD
	
	/**
	 * Sets whether filter strings are treated as case sensitive.
	 * 
	 * @param filterCaseSensitive Case Sensitive
	 */
	public void setFilterCaseSensitive(final boolean filterCaseSensitive)
	{
		this.filterCaseSensitive = filterCaseSensitive;
		
	}//METHOD
	
	/**
	 * Returns the title filter string.
	 * 
	 * @return Title Filter String
	 */
	public String getTitleFilter()
	{
		return titleFilter;
				
	}//METHOD
	
	/**
	 * Sets the title filter string.
	 * 
	 * @param titleFilter Title Filter String
	 */
	public void setTitleFilter(final String titleFilter)
	{
		if(titleFilter != null)
		{
			this.titleFilter = titleFilter;
			
		}//IF
		else
		{
			this.titleFilter = new String();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Returns the description filter string.
	 * 
	 * @return Description Filter
	 */
	public String getDescriptionFilter()
	{
		return descriptionFilter;
		
	}//METHOD
	
	/**
	 * Sets the description filter string.
	 * 
	 * @param descriptionFilter Description Filter
	 */
	public void setDescriptionFilter(final String descriptionFilter)
	{
		if(descriptionFilter == null)
		{
			this.descriptionFilter = new String();
			
		}//IF
		else
		{
			this.descriptionFilter = descriptionFilter;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns the web tag filter string.
	 * 
	 * @return Web Tag Filter
	 */
	public String getWebTagFilter()
	{
		return webTagFilter;
		
	}//METHOD
	
	/**
	 * Sets the web tag filter string.
	 * 
	 * @param webTagFilter Web Tag Filter
	 */
	public void setWebTagFilter(final String webTagFilter)
	{
		if(webTagFilter == null)
		{
			this.webTagFilter = new String();
			
		}//IF
		else
		{
			this.webTagFilter = webTagFilter;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns the user tag filter string.
	 * 
	 * @return User Tag Filter
	 */
	public String getUserTagFilter()
	{
		return userTagFilter;
		
	}//METHOD
	
	/**
	 * Sets the User Tag Filter String.
	 * 
	 * @param userTagFilter User Tag Filter
	 */
	public void setUserTagFilter(final String userTagFilter)
	{
		if(userTagFilter == null)
		{
			this.userTagFilter = new String();
			
		}//IF
		else
		{
			this.userTagFilter = userTagFilter;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns the artist filter string.
	 * 
	 * @return Artist Filter
	 */
	public String getArtistFilter()
	{
		return artistFilter;
		
	}//METHOD
	
	/**
	 * Sets the artist filter string.
	 * 
	 * @param artistFilter Artist Filter
	 */
	public void setArtistFilter(final String artistFilter)
	{
		if(artistFilter == null)
		{
			this.artistFilter = new String();
			
		}//IF
		else
		{
			this.artistFilter = artistFilter;
			
		}//ELSE
		
	}//METHOD
	
}//CLASS
