package drakovek.hoarder.file.dvk;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.processing.BooleanSearch;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.processing.sort.AlphaNumSort;

/**
 * Class for other objects to access DVK information.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DvkHandler
{
	/**
	 * Int value indicating to sort DVKs by time published.
	 */
	public static final int SORT_TIME = 0;
	
	/**
	 * Int value indicating to sort DVKs Alpha-numerically by title.
	 */
	public static final int SORT_ALPHA = 1;
	
	/**
	 * Whether to treat filter strings as case sensitive
	 */
	private boolean filterCaseSensitive;
	
	/**
	 * Filter string for DVK titles
	 */
	private String titleFilter;
	
	/**
	 * Filter string for DVK descriptions
	 */
	private String descriptionFilter;
	
	/**
	 * Filter string for DVK web tags
	 */
	private String webTagFilter;
	
	/**
	 * Filter string for DVK artists
	 */
	private String artistFilter;
	
	/**
	 * ArrayList full of indexes referencing DVKs loaded in DvkDatabase, sorted in a given order.
	 */
	private ArrayList<Integer> sorted;
	
	/**
	 * ArrayList full of indexes referencing DVKs loaded in DvkDatabase, with certain indexes being omitted via a filter.
	 */
	private ArrayList<Integer> filtered;
	
	/**
	 * DVK Database from which to load DVK info
	 */
	private DvkDatabase database;
	
	/**
	 * Boolean to show if DVKs were loaded properly
	 */
	private boolean loaded;
	
	/**
	 * Initializes DvkHandler class
	 */
	public DvkHandler()
	{
		clearDVKs();
		resetFilterStrings();
		
	}//CONSTRUCTOR
	
	/**
	 * Resets all DVK information from the object
	 */
	public void clearDVKs()
	{
		loaded = false;
		database = new DvkDatabase();
		sorted = new ArrayList<>();
		filtered = new ArrayList<>();
		
	}//METHOD
	
	/**
	 * Loads the information from all DVKs in a given folder and in its sub-directories.
	 * 
	 * @param dvkDirectories Directories with DVKs to load
	 * @param dvkFolder Input Folder
	 * @param progressDialog DProgress dialog to show progress of loading DVK
	 * @param useIndexes Whether to use index files to load DvmDirectory object
	 * @param saveIndexes Whether to save DvkDirectories as index files
	 * @param updateIndexes Whether to update index files to reflect changes in DVKs
	 */
	public void loadDVKs(final ArrayList<File> dvkDirectories, DProgressDialog progressDialog, final boolean useIndexes, final boolean saveIndexes, final boolean updateIndexes)
	{
		loaded = database.loadDVKs(dvkDirectories, progressDialog, useIndexes, saveIndexes, updateIndexes);
		
		resetSorted();
		resetFiltered();
		
	}//METHOD
	
	/**
	 * Returns whether DVKs loaded properly.
	 * 
	 * @return Whether DVKs loaded properly
	 */
	public boolean isLoaded()
	{
		return loaded;
		
	}//METHOD
	
	/**
	 * Resets the sorted list to match the default order of DvkDatabase
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
			filtered.add(Integer.valueOf(i));
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Sorts the currently loaded DVKs.
	 * 
	 * @param sortType Sort Type
	 * @param groupArtists Whether to group artists
	 * @param groupSequences Whether to group sequences
	 * @param groupSections Whether to group sections
	 * @param reverseOrder Whether to reverse the sorting order
	 */
	public void sort(final int sortType, final boolean groupArtists, final boolean reverseOrder)
	{
		//POPULATE SORTED ARRAYLIST
		sorted = new ArrayList<>();
		for(int i = 0; i < database.getSize(); i++)
		{
			sorted.add(Integer.valueOf(i));
			
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
		
		resetFiltered();
		
	}//METHOD
	
	/**
	 * Sorts a list of DVK indexes using merge sort.
	 * 
	 * @param startList Given list of DVK indexes to sort.
	 * @param sortType Sort Type
	 * @param groupArtists Whether to group artists together
	 * @return Sorted list of DVK indexes
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
				if(compareDVKs(aList.get(0).intValue(), bList.get(0).intValue(), sortType, groupArtists) < 0)
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
	 * Compares two DVKs given their indexes.
	 * 
	 * @param dvkA Index of the first DVK
	 * @param dvkB Index of the second DVK
	 * @param sortType Sort Type, used to determine which criteria to compare DVKs by
	 * @param groupArtists Whether to group artists together
	 * @return int value showing how the DVKs compare
	 */
	private int compareDVKs(final int dvkA, final int dvkB, final int sortType, final boolean groupArtists)
	{
		int result = 0;
		
		if(groupArtists == true)
		{
			result = AlphaNumSort.compareAlpha(StringMethods.arrayToString(database.getArtists(dvkA)).toLowerCase(), StringMethods.arrayToString(database.getArtists(dvkB)).toLowerCase());
		
		}//IF
		
		if(result == 0 && (sortType != SORT_ALPHA))
		{
			if(database.getTime(dvkA) > database.getTime(dvkB))
			{
				result = 1;
				
			}//IF
			else if(database.getTime(dvkA) < database.getTime(dvkB))
			{
				result = -1;
			
			}//ELSE IF
		
		}//IF
		
		if(result == 0)
		{
			result = AlphaNumSort.compareAlpha(database.getTitle(dvkA), database.getTitle(dvkB));
		
		}//IF
		
		return result;
	
	}//METHOD
	
	/**
	 * Filters the currently loaded DVKs based on the current filter strings.
	 */
	public void filterDVKs()
	{
		resetFiltered();
		filterTitles();
		filterDescriptions();
		filterWebTags();
		filterArtists();
		
	}//METHOD
	
	/**
	 * Filters loaded DVKs based on title.
	 */
	private void filterTitles()
	{
		if(getTitleFilter().length() > 0)
		{
			BooleanSearch booleanSearch = new BooleanSearch();
			booleanSearch.createSearchLogic(getTitleFilter());
			for(int i = 0; i < getFilteredSize(); i++)
			{
				if(!booleanSearch.searchText(getTitleFiltered(i), getFilterCaseSensitive(), false))
				{
					filtered.remove(i);
					i--;
				
				}//IF
			
			}//FOR
			
		}//IF
		
	}//IF
	
	/**
	 * Filters loaded DVKs based on description.
	 */
	private void filterDescriptions()
	{
		if(getDescriptionFilter().length() > 0)
		{
			BooleanSearch booleanSearch = new BooleanSearch();
			booleanSearch.createSearchLogic(getDescriptionFilter());
			for(int i = 0; i < getFilteredSize(); i++)
			{
				if(!booleanSearch.searchText(getDescriptionFiltered(i), getFilterCaseSensitive(), false))
				{
					filtered.remove(i);
					i--;
				
				}//IF
			
			}//FOR
			
		}//IF
		
	}//IF
	
	/**
	 * Filters loaded DVKs based on web tags.
	 */
	private void filterWebTags()
	{
		if(getWebTagFilter().length() > 0)
		{
			BooleanSearch booleanSearch = new BooleanSearch();
			booleanSearch.createSearchLogic(getWebTagFilter());
			for(int i = 0; i < getFilteredSize(); i++)
			{
				if(!booleanSearch.searchText(getWebTagsFiltered(i), getFilterCaseSensitive(), true))
				{
					filtered.remove(i);
					i--;
				
				}//IF
			
			}//FOR
			
		}//IF
		
	}//IF
	
	/**
	 * Filters loaded DVKs based on artist(s).
	 */
	private void filterArtists()
	{
		if(getArtistFilter().length() > 0)
		{
			BooleanSearch booleanSearch = new BooleanSearch();
			booleanSearch.createSearchLogic(getArtistFilter());
			for(int i = 0; i < getFilteredSize(); i++)
			{
				if(!booleanSearch.searchText(getArtistsFiltered(i), getFilterCaseSensitive(), true))
				{
					filtered.remove(i);
					i--;
				
				}//IF
			
			}//FOR
			
		}//IF
		
	}//IF
	
	/**
	 * Returns the number of DVKs loaded in the filtered list.
	 * 
	 * @return Number of DVKs loaded in the filtered list.
	 */
	public int getFilteredSize()
	{
		return filtered.size();
		
	}//METHOD
	
	/**
	 * Returns the total number of DVKs loaded
	 * 
	 * @return Number of DVKs loaded
	 */
	public int getDirectSize()
	{
		return sorted.size();
		
	}//METHOD
	
	/**
	 * Returns the DVK Database this handler is reading from
	 * 
	 * @return DVK Database
	 */
	public DvkDatabase getDatabase()
	{
		return database;
		
	}//METHOD
	
	/**
	 * Returns the direct index from a given filtered index value.
	 * 
	 * @param filteredIndex Filtered list index value
	 * @return Direct index value
	 */
	public int getDirectIndex(final int filteredIndex)
	{
		return filtered.get(filteredIndex).intValue();
		
	}//METHOD
	
	/**
	 * Sets the DVK at a given index in the filtered list.
	 * 
	 * @param dvk DVK with which to replace listed DVK
	 * @param index Filtered list index value
	 */
	public void setDvkFiltered(DVK dvk, final int index)
	{
		database.setDVK(dvk, sorted.get(filtered.get(index).intValue()).intValue());
		
	}//METHOD
	
	/**
	 * Sets the DVK at a given direct index.
	 * 
	 * @param dvk DVK with which to replace listed DVK
	 * @param index Filtered list index value
	 */
	public void setDvkDirect(DVK dvk, final int index)
	{
		database.setDVK(dvk, sorted.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Adds a given DVK to the database.
	 * 
	 * @param dvk Given DVK
	 * @param index Given Index
	 */
	public void addDVK(DVK dvk)
	{
		database.addDVK(dvk);
		resetSorted();
		resetFiltered();
		
	}//METHOD
	
	/**
	 * Returns the DVK File at a given index in a filtered list.
	 * 
	 * @param index Filtered List Index
	 * @return DVK File
	 */
	public File getDvkFileFiltered(final int index)
	{
		return getDvkFileDirect(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the DVK File at a given direct index.
	 * 
	 * @param index Direct Index
	 * @return DVK File
	 */
	public File getDvkFileDirect(final int index)
	{
		return database.getDvkFile(sorted.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the DVK ID at a given index in a filtered list.
	 * 
	 * @param index Filtered List Index
	 * @return DVK ID
	 */
	public String getIdFiltered(final int index)
	{
		return getIdDirect(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the DVK ID at a given direct index.
	 * 
	 * @param index Direct Index
	 * @return DVK ID
	 */
	public String getIdDirect(final int index)
	{
		return database.getID(sorted.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the DVK title at a given index in a filtered list.
	 * 
	 * @param index Filtered List Index
	 * @return DVK Title
	 */
	public String getTitleFiltered(final int index)
	{
		return getTitleDirect(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the DVK title at a given direct index in a filtered list.
	 * 
	 * @param index Direct Index
	 * @return DVK Title
	 */
	public String getTitleDirect(final int index)
	{
		return database.getTitle(sorted.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the DVK artists at a given index in a filtered list.
	 * 
	 * @param index Filtered List Index
	 * @return DVK Artists
	 */
	public String[] getArtistsFiltered(final int index)
	{
		return getArtistsDirect(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the DVK artists at a given direct index.
	 * 
	 * @param index Filtered List Index
	 * @return DVK Artists
	 */
	public String[] getArtistsDirect(final int index)
	{
		return database.getArtists(sorted.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the time the DVK at a given index in a filtered list was published.
	 * 
	 * @param index Filtered List Index
	 * @return Time of Publication
	 */
	public long getTimeFiltered(final int index)
	{
		return getTimeDirect(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the time the DVK at a given direct index was published.
	 * 
	 * @param index Direct Index
	 * @return Time of Publication
	 */
	public long getTimeDirect(final int index)
	{
		return database.getTime(sorted.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the web tags at a given index in a filtered list.
	 * 
	 * @param index Filtered List Index
	 * @return Web Tags
	 */
	public String[] getWebTagsFiltered(final int index)
	{
		return getWebTagsDirect(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the web tags at a given direct index in a filtered list.
	 * 
	 * @param index Direct Index
	 * @return Web Tags
	 */
	public String[] getWebTagsDirect(final int index)
	{
		return database.getWebTags(sorted.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the DVK description at a given index in a filtered list.
	 * 
	 * @param index Filtered List Index
	 * @return DVK Description
	 */
	public String getDescriptionFiltered(final int index)
	{
		return getDescriptionDirect(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the DVK description at a given direct index.
	 * 
	 * @param index Direct Index
	 * @return DVK Description
	 */
	public String getDescriptionDirect(final int index)
	{
		return database.getDescription(sorted.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the page URL at a given index in a filtered list.
	 * 
	 * @param index Filtered List Index
	 * @return Page URL
	 */
	public String getPageUrlFiltered(final int index)
	{
		return getPageUrlDirect(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the page URL at a given direct index.
	 * 
	 * @param index Direct Index
	 * @return Page URL
	 */
	public String getPageUrlDirect(final int index)
	{
		return database.getPageURL(sorted.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the direct URL at a given index in a filtered list.
	 * 
	 * @param index Filtered List Index
	 * @return Direct URL
	 */
	public String getDirectUrlFiltered(final int index)
	{
		return getDirectUrlDirect(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the direct URL at a given direct index.
	 * 
	 * @param index Direct Index
	 * @return Direct URL
	 */
	public String getDirectUrlDirect(final int index)
	{
		return database.getDirectURL(sorted.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the secondary URL at a given index in a filtered list.
	 * 
	 * @param index Filtered List Index
	 * @return Secondary URL
	 */
	public String getSecondaryUrlFiltered(final int index)
	{
		return getSecondaryUrlDirect(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the secondary URL at a given direct index.
	 * 
	 * @param index Direct Index
	 * @return Secondary URL
	 */
	public String getSecondaryUrlDirect(final int index)
	{
		return database.getSecondaryURL(sorted.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the media file at a given index in a filtered list.
	 * 
	 * @param index Filtered List Index
	 * @return Media File
	 */
	public File getMediaFileFiltered(final int index)
	{
		return getMediaFileDirect(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the media file at a given direct index.
	 * 
	 * @param index Direct Index
	 * @return Media File
	 */
	public File getMediaFileDirect(final int index)
	{
		return database.getMediaFile(sorted.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the secondary file at a given index in a filtered list.
	 * 
	 * @param index Filtered List Index
	 * @return Secondary File
	 */
	public File getSecondaryFileFiltered(final int index)
	{
		return getSecondaryFileDirect(filtered.get(index).intValue());
		
	}//METHOD
	
	/**
	 * Returns the secondary file at a given direct index.
	 * 
	 * @param index Direct Index
	 * @return Secondary File
	 */
	public File getSecondaryFileDirect(final int index)
	{
		return database.getSecondaryFile(sorted.get(index).intValue());
		
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
