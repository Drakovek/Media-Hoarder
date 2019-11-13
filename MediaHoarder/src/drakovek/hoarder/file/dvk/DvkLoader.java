package drakovek.hoarder.file.dvk;

import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.DvkLanguageValues;
import drakovek.hoarder.file.language.ViewerValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * Class containing methods for showing progress while loading, sorting, and filtering DVKs.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DvkLoader implements DWorker
{
	/**
	 * Whether to use index files to load DvkDirectory object
	 */
	private boolean useIndexesCV;
	
	/**
	 * Whether to save DvkDirectories as index files
	 */
	private boolean saveIndexesCV;
	
	/**
	 * Whether to update index files to reflect changes in DVKs
	 */
	private boolean updateIndexesCV;
	
	/**
	 * Sort Type
	 */
	private int sortTypeCV;
	
	/**
	 * Whether to group artists when sorting
	 */
	private boolean groupArtistsCV;
	
	/**
	 * Whether to reverse the DVK order when sorting
	 */
	private boolean reverseOrderCV;
	
	/**
	 * Progress dialog for showing progress on DVK loading and processing functions
	 */
	private DProgressDialog progressDialog;
	
	/**
	 * Methods to call when finished with DVK loading and processing functions
	 */
	private DvkLoadingMethods loadingMethods;
	
	/**
	 * Linked Frame GUI
	 */
	private FrameGUI parentGUI;
	
	/**
	 * Initializes the DVK loader class.
	 * 
	 * @param loadingMethods Methods to call when finished with DVK loading and processing functions
	 * @param parentGUI Linked Frame GUI
	 */
	public DvkLoader(DvkLoadingMethods loadingMethods, FrameGUI parentGUI)
	{
		this.loadingMethods = loadingMethods;
		this.parentGUI = parentGUI;
		progressDialog = new DProgressDialog(this.parentGUI.getSettings());
		
		useIndexesCV = false;
		saveIndexesCV = false;
		updateIndexesCV = true;
		
		sortTypeCV = 0;
		groupArtistsCV = false;
		reverseOrderCV = false;
		
	}//METHOD
	
	/**
	 * Starts the process of loading DVKs while showing progress.
	 * 
	 * @param useIndexes Whether to use index files to load DvkDirectory object
	 * @param saveIndexes Whether to save DvkDirectories as index files
	 * @param updateIndexes Whether to update index files to reflect changes in DVKs
	 */
	public void loadDVKs(final boolean useIndexes, final boolean saveIndexes, final boolean updateIndexes)
	{
		this.useIndexesCV = useIndexes;
		this.saveIndexesCV = saveIndexes;
		this.updateIndexesCV = updateIndexes;
		
		parentGUI.getFrame().setProcessRunning(true);
		progressDialog.setCancelled(false);
		progressDialog.startProgressDialog(parentGUI.getFrame(), DvkLanguageValues.LOADING_DVKS_TITLE);
		(new DSwingWorker(this,  DvkLanguageValues.LOADING_DVKS)).execute();
		
	}//METHOD
	
	/**
	 * Sorts DVKs based on the program's current default sorting settings.
	 */
	public void sortDVKsDefault()
	{
		sortDVKs(parentGUI.getSettings().getSortType(), parentGUI.getSettings().getGroupArtists(), parentGUI.getSettings().getReverseOrder());

	}//METHOD
	
	/**
	 * Starts the process of sorting DVKs while showing progress.
	 * 
	 * @param sortType Sort Type
	 * @param groupArtists Whether to group artists
	 * @param reverseOrder Whether to reverse the sorting order
	 */
	public void sortDVKs(final int sortType, final boolean groupArtists, final boolean reverseOrder)
	{
		this.sortTypeCV = sortType;
		this.groupArtistsCV = groupArtists;
		this.reverseOrderCV = reverseOrder;
		
		parentGUI.getFrame().setProcessRunning(true);
		progressDialog.setCancelled(false);
		progressDialog.startProgressDialog(parentGUI.getFrame(), ViewerValues.SORTING_DVKS_TITLE);
		progressDialog.setProcessLabel(ViewerValues.SORTING_DVKS);
		progressDialog.setDetailLabel(CommonValues.RUNNING, true);
		progressDialog.setProgressBar(true, false, 0, 0);
		(new DSwingWorker(this, ViewerValues.SORT)).execute();
		
	}//METHOD
	
	/**
	 * Starts the process of filtering DVKs while showing progress.
	 */
	public void filterDVKs()
	{
		parentGUI.getFrame().setProcessRunning(false);
		progressDialog.setCancelled(false);
		progressDialog.startProgressDialog(parentGUI.getFrame(), ViewerValues.FILTERING_DVKS_TITLE);
		progressDialog.setProcessLabel(ViewerValues.FILTERING_DVKS);
		progressDialog.setDetailLabel(CommonValues.RUNNING, true);
		progressDialog.setProgressBar(true, false, 0, 0);
		(new DSwingWorker(this, ViewerValues.FILTER)).execute();
		
	}//METHOD
	
	/**
	 * Loads DVKs from the program's DVK directories.
	 */
	private void loadDVKsWork()
	{
		parentGUI.getDvkHandler().loadDVKs(parentGUI.getSettings().getDvkDirectories(), progressDialog, useIndexesCV, saveIndexesCV, updateIndexesCV);
	
	}//METHOD
	
	/**
	 * Sorts the loaded DVKs.
	 */
	private void sortDVKsWork()
	{
		parentGUI.getDvkHandler().sort(sortTypeCV, groupArtistsCV, reverseOrderCV);
		
	}//METHOD
	
	/**
	 * Filters the loaded DVKs.
	 */
	private void filterDVKsWork()
	{
		parentGUI.getDvkHandler().filterDVKs();
		
	}//METHOD

	@Override
	public void run(String id)
	{
		switch(id)
		{
			case DvkLanguageValues.LOADING_DVKS:
				loadDVKsWork();
				break;
			case ViewerValues.SORT:
				sortDVKsWork();
				break;
			case ViewerValues.FILTER:
				filterDVKsWork();
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void done(String id)
	{
		progressDialog.setCancelled(false);
		progressDialog.closeProgressDialog();
		parentGUI.getFrame().setProcessRunning(false);
		
		switch(id)
		{
			case DvkLanguageValues.LOADING_DVKS:
				loadingMethods.loadingDVKsDone();
				break;
			case ViewerValues.SORT:
				loadingMethods.sortingDVKsDone();
				break;
			case ViewerValues.FILTER:
				loadingMethods.filteringDVKsDone();
				break;
			
		}//SWITCH
		
	}//METHOD
	
}//CLASS
