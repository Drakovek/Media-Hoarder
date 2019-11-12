package drakovek.hoarder.gui.modes;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.ExclusionFilter;
import drakovek.hoarder.file.dmf.DMF;
import drakovek.hoarder.file.dmf.DmfDatabase;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.dmf.DmfLoader;
import drakovek.hoarder.file.dmf.DmfLoadingMethods;
import drakovek.hoarder.file.language.DmfLanguageValues;
import drakovek.hoarder.file.language.ManagingValues;
import drakovek.hoarder.file.language.ModeValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * GUI for running processes related to finding errors in DMFs.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ErrorFindingModeGUI extends ModeBaseGUI implements DWorker, DmfLoadingMethods
{
	/**
	 * String containing ID for the current reformat process mode
	 */
	private String mode;
	
	/**
	 * Progress Info Dialog for showing progress in reformatting processes
	 */
	private DProgressInfoDialog progressInfoDialog;
	
	/**
	 * Initializes the ErrorFindingModeGUI.
	 * 
	 * @param frameGUI FrameGUI this mode GUI is contained within.
	 */
	public ErrorFindingModeGUI(FrameGUI frameGUI)
	{
		super(frameGUI);
		progressInfoDialog = new DProgressInfoDialog(getSettings());
		
		String[] backIDs = {ModeValues.MODE_BACK, ModeValues.MODE_START, ModeValues.MANAGE_MODE};
		String[] modeIDs = {ManagingValues.MISSING_MEDIA,
							ManagingValues.UNLINKED_FILES,
							ManagingValues.IDENTICAL_IDS};
		
		setContentPanel(backIDs, modeIDs);
		
	}//CONSTRUCTOR
	
	/**
	 * Starts the process of checking for errors, after DMFs have been loaded.
	 */
	private void startChecking()
	{
		if(getParentGUI().getDmfHandler().isLoaded())
		{
			progressInfoDialog.setCancelled(false);
			getParentGUI().getFrame().setProcessRunning(true);
			progressInfoDialog.startProgressDialog(getParentGUI().getFrame(), this.getTitle(mode));
			(new DSwingWorker(this, mode)).execute();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Deals with an info process being finished, closing the info progress dialog and allowing input.
	 */
	private void infoProcessFinished()
	{
		progressInfoDialog.setCancelled(false);
		progressInfoDialog.showFinalLog(getParentGUI().getFrame(), getTitle(mode), getSettings().getDmfDirectories().get(0));
		getParentGUI().getFrame().setProcessRunning(false);
		
	}//METHOD
	
	/**
	 * Finds DMFs that are missing their attached media file(s)
	 */
	private void findMissingMedia()
	{
		int size = getParentGUI().getDmfHandler().getDirectSize();
		progressInfoDialog.setProcessLabel(ManagingValues.MISSING_MEDIA);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDmfHandler().getArtistsDirect(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.setProgressBar(false, true, size, i);
				
			}//IF
			
			if(getParentGUI().getDmfHandler().getMediaFileDirect(i) == null || !getParentGUI().getDmfHandler().getMediaFileDirect(i).exists())
			{
				progressInfoDialog.appendLog(getParentGUI().getDmfHandler().getDmfFileDirect(i).getAbsolutePath(), false);
				
			}//IF
			else
			{
				File secondary = getParentGUI().getDmfHandler().getSecondaryFileDirect(i);
				if(secondary != null && !secondary.exists())
				{
					progressInfoDialog.appendLog(getParentGUI().getDmfHandler().getDmfFileDirect(i).getAbsolutePath(), false);
					
				}//IF
				
			}//ELSE
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Finds media files in DMF folders that are not linked to DMF files.
	 */
	private void findUnlinkedFiles()
	{
		String[] extension = {DMF.DMF_EXTENSION, DMF.DVK_EXTENSION};
		ExclusionFilter filter = new ExclusionFilter(extension, false);
		progressInfoDialog.setProcessLabel(ManagingValues.MISSING_MEDIA);
		progressInfoDialog.setDetailLabel(DmfLanguageValues.GETTING_FOLDERS, true);
		progressInfoDialog.setProgressBar(true, false, 0, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		ArrayList<File> dmfFolders = DmfDatabase.getDmfFolders(getSettings().getDmfDirectories());
		for(int i = 0; i < dmfFolders.size(); i++)
		{
			progressInfoDialog.setDetailLabel(dmfFolders.get(i).getName(), false);
			progressInfoDialog.setProgressBar(false, true, dmfFolders.size(), i);
			File[] files = dmfFolders.get(i).listFiles(filter);
			for(int k = 0; k < files.length; k++)
			{
				if(!getParentGUI().getDmfHandler().getDatabase().containsMediaFile(files[k]) && !getParentGUI().getDmfHandler().getDatabase().containsSecondaryFile(files[k]))
				{
					progressInfoDialog.appendLog(files[k].getAbsolutePath(), false);
					
				}//IF
				
			}//FOR
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Find DMFs that share identical IDs
	 */
	private void findIdenticalIDs()
	{
		int size = getParentGUI().getDmfHandler().getDirectSize();
		progressInfoDialog.setProcessLabel(ManagingValues.IDENTICAL_IDS);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		ArrayList<Integer> identical = new ArrayList<>();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDmfHandler().getArtistsDirect(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.setProgressBar(false, true, size, i);
				
			}//IF
			
			String id = getParentGUI().getDmfHandler().getIdDirect(i);
			boolean hasID = false;
			for(int k = i + 1; k < size; k++)
			{
				if(!identical.contains(Integer.valueOf(k)) && getParentGUI().getDmfHandler().getIdDirect(k).equals(id))
				{
					identical.add(Integer.valueOf(k));
					if(!hasID)
					{
						progressInfoDialog.appendLog(null, false);
						progressInfoDialog.appendLog(getParentGUI().getDmfHandler().getDmfFileDirect(i).getAbsolutePath(), false);
					
					}//IF
					progressInfoDialog.appendLog(Character.toString('\t') + getParentGUI().getDmfHandler().getDmfFileDirect(k).getAbsolutePath(), false);
					hasID = true;
					
				}//IF
				
			}//IF
			
		}//FOR
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case ModeValues.MODE_BACK:
			case ModeValues.MANAGE_MODE:
				setContentPanel(new ManageModeGUI(getParentGUI()));
				break;
			case ModeValues.MODE_START:
				setContentPanel(new ModesGUI(getParentGUI()));
				break;
			default:
				mode = id;
				DmfLoader loader = new DmfLoader(this, getParentGUI());
				loader.loadDMFs(getParentGUI().getSettings().getUseIndexes(), getParentGUI().getSettings().getUseIndexes(), true);
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void run(String id)
	{
		switch(id)
		{
			case ManagingValues.UNLINKED_FILES:
				findUnlinkedFiles();
				break;
			case ManagingValues.IDENTICAL_IDS:
				findIdenticalIDs();
				break;
			case ManagingValues.MISSING_MEDIA:
				findMissingMedia();
				break;
			
		}//SWITCH
		
	}//METHOD

	@Override
	public void done(String id)
	{
		switch(id)
		{
			default:
				infoProcessFinished();
				break;
			
		}//SWITCH
		
	}//METHOD

	@Override
	public void loadingDMFsDone()
	{
		DmfLoader loader = new DmfLoader(this, getParentGUI());
		loader.sortDMFs(DmfHandler.SORT_ALPHA, true, false, false, false);
		
	}//METHOD

	@Override
	public void sortingDMFsDone()
	{
		startChecking();
		
	}//METHOD

	@Override
	public void filteringDMFsDone() {}
	
}//CLASS
