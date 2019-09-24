package drakovek.hoarder.file.dmf;

import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.DmfLanguageValues;
import drakovek.hoarder.file.language.ViewerValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * Class containing methods for showing progress while loading, sorting, and filtering DMFs.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DmfLoader implements DWorker
{
	/**
	 * Whether to use index files to load DmfDirectory object
	 */
	private boolean useIndexesCV;
	
	/**
	 * Whether to save DmfDirectories as index files
	 */
	private boolean saveIndexesCV;
	
	/**
	 * Whether to update index files to reflect changes in DMFs
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
	 * Whether to group sequences when sorting
	 */
	private boolean groupSequencesCV;
	
	/**
	 * Whether to group sections when sorting
	 */
	private boolean groupSectionsCV;
	
	/**
	 * Whether to reverse the DMF order when sorting
	 */
	private boolean reverseOrderCV;
	
	/**
	 * Progress dialog for showing progress on DMF loading and processing functions
	 */
	private DProgressDialog progressDialog;
	
	/**
	 * Methods to call when finished with DMF loading and processing functions
	 */
	private DmfLoadingMethods loadingMethods;
	
	/**
	 * Linked Frame GUI
	 */
	private FrameGUI parentGUI;
	
	/**
	 * Initializes the DMF loader class.
	 * 
	 * @param loadingMethods Methods to call when finished with DMF loading and processing functions
	 * @param parentGUI Linked Frame GUI
	 */
	public DmfLoader(DmfLoadingMethods loadingMethods, FrameGUI parentGUI)
	{
		this.loadingMethods = loadingMethods;
		this.parentGUI = parentGUI;
		progressDialog = new DProgressDialog(this.parentGUI.getSettings());
		
		useIndexesCV = false;
		saveIndexesCV = false;
		updateIndexesCV = true;
		
		sortTypeCV = 0;
		groupArtistsCV = false;
		groupSequencesCV = false;
		groupSectionsCV = false;
		reverseOrderCV = false;
		
	}//METHOD
	
	/**
	 * Starts the process of loading DMFs while showing progress.
	 * 
	 * @param useIndexes Whether to use index files to load DmfDirectory object
	 * @param saveIndexes Whether to save DmfDirectories as index files
	 * @param updateIndexes Whether to update index files to reflect changes in DMFs
	 */
	public void loadDMFs(final boolean useIndexes, final boolean saveIndexes, final boolean updateIndexes)
	{
		this.useIndexesCV = useIndexes;
		this.saveIndexesCV = saveIndexes;
		this.updateIndexesCV = updateIndexes;
		
		parentGUI.getFrame().setProcessRunning(true);
		progressDialog.setCancelled(false);
		progressDialog.startProgressDialog(parentGUI.getFrame(), DmfLanguageValues.LOADING_DMFS_TITLE);
		(new DSwingWorker(this,  DmfLanguageValues.LOADING_DMFS)).execute();
		
	}//METHOD
	
	/**
	 * Starts the process of sorting DMFs while showing progress.
	 * 
	 * @param sortType Sort Type
	 * @param groupArtists Whether to group artists
	 * @param groupSequences Whether to group sequences
	 * @param groupSections Whether to group sections
	 * @param reverseOrder Whether to reverse the sorting order
	 */
	public void sortDMFs(final int sortType, final boolean groupArtists, final boolean groupSequences, final boolean groupSections, final boolean reverseOrder)
	{
		this.sortTypeCV = sortType;
		this.groupArtistsCV = groupArtists;
		this.groupSequencesCV = groupSequences;
		this.groupSectionsCV = groupSections;
		this.reverseOrderCV = reverseOrder;
		
		parentGUI.getFrame().setProcessRunning(true);
		progressDialog.setCancelled(false);
		progressDialog.startProgressDialog(parentGUI.getFrame(), ViewerValues.SORTING_DMFS_TITLE);
		progressDialog.setProcessLabel(ViewerValues.SORTING_DMFS);
		progressDialog.setDetailLabel(CommonValues.RUNNING, true);
		progressDialog.setProgressBar(true, false, 0, 0);
		(new DSwingWorker(this, ViewerValues.SORT)).execute();
		
	}//METHOD
	
	/**
	 * Loads DMFs from the program's DMF directories
	 */
	private void loadDMFsWork()
	{
		parentGUI.getDmfHandler().loadDMFs(parentGUI.getSettings().getDmfDirectories(), progressDialog, useIndexesCV, saveIndexesCV, updateIndexesCV);
	
	}//METHOD
	
	/**
	 * Sorts the loaded DMFs
	 */
	private void sortDMFsWork()
	{
		parentGUI.getDmfHandler().sort(sortTypeCV, groupArtistsCV, groupSequencesCV, groupSectionsCV, reverseOrderCV);
		
	}//METHOD

	@Override
	public void run(String id)
	{
		switch(id)
		{
			case DmfLanguageValues.LOADING_DMFS:
				loadDMFsWork();
				break;
			case ViewerValues.SORT:
				sortDMFsWork();
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
			case DmfLanguageValues.LOADING_DMFS:
				loadingMethods.loadingDMFsDone();
				break;
			case ViewerValues.SORT:
				loadingMethods.sortingDMFsDone();
				break;
			
		}//SWITCH
		
	}//METHOD
	
}//CLASS
