package drakovek.hoarder.gui.modes;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.ExclusionFilter;
import drakovek.hoarder.file.dvk.DVK;
import drakovek.hoarder.file.dvk.DvkDatabase;
import drakovek.hoarder.file.dvk.DvkHandler;
import drakovek.hoarder.file.dvk.DvkLoader;
import drakovek.hoarder.file.dvk.DvkLoadingMethods;
import drakovek.hoarder.file.language.DvkLanguageValues;
import drakovek.hoarder.file.language.ManagingValues;
import drakovek.hoarder.file.language.ModeValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * GUI for running processes related to finding errors in DVKs.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ErrorFindingModeGUI extends ModeBaseGUI implements DWorker, DvkLoadingMethods
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
	 * Starts the process of checking for errors, after DVKs have been loaded.
	 */
	private void startChecking()
	{
		if(getParentGUI().getDvkHandler().isLoaded())
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
		progressInfoDialog.showFinalLog(getParentGUI().getFrame(), getTitle(mode), getSettings().getDvkDirectories().get(0));
		getParentGUI().getFrame().setProcessRunning(false);
		
	}//METHOD
	
	/**
	 * Finds DVKs that are missing their attached media file(s)
	 */
	private void findMissingMedia()
	{
		int size = getParentGUI().getDvkHandler().getDirectSize();
		progressInfoDialog.setProcessLabel(ManagingValues.MISSING_MEDIA);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDvkHandler().getArtistsDirect(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.setProgressBar(false, true, size, i);
				
			}//IF
			
			if(getParentGUI().getDvkHandler().getMediaFileDirect(i) == null || !getParentGUI().getDvkHandler().getMediaFileDirect(i).exists())
			{
				progressInfoDialog.appendLog(getParentGUI().getDvkHandler().getDvkFileDirect(i).getAbsolutePath(), false);
				
			}//IF
			else
			{
				File secondary = getParentGUI().getDvkHandler().getSecondaryFileDirect(i);
				if(secondary != null && !secondary.exists())
				{
					progressInfoDialog.appendLog(getParentGUI().getDvkHandler().getDvkFileDirect(i).getAbsolutePath(), false);
					
				}//IF
				
			}//ELSE
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Finds media files in DVK folders that are not linked to DVK files.
	 */
	private void findUnlinkedFiles()
	{
		String[] extension = {DVK.DVK_EXTENSION};
		ExclusionFilter filter = new ExclusionFilter(extension, false);
		progressInfoDialog.setProcessLabel(ManagingValues.MISSING_MEDIA);
		progressInfoDialog.setDetailLabel(DvkLanguageValues.GETTING_FOLDERS, true);
		progressInfoDialog.setProgressBar(true, false, 0, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		ArrayList<File> dvkFolders = DvkDatabase.getDvkFolders(getSettings().getDvkDirectories());
		for(int i = 0; i < dvkFolders.size(); i++)
		{
			progressInfoDialog.setDetailLabel(dvkFolders.get(i).getName(), false);
			progressInfoDialog.setProgressBar(false, true, dvkFolders.size(), i);
			File[] files = dvkFolders.get(i).listFiles(filter);
			for(int k = 0; k < files.length; k++)
			{
				if(!getParentGUI().getDvkHandler().getDatabase().containsMediaFile(files[k]) && !getParentGUI().getDvkHandler().getDatabase().containsSecondaryFile(files[k]))
				{
					progressInfoDialog.appendLog(files[k].getAbsolutePath(), false);
					
				}//IF
				
			}//FOR
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Find DVKs that share identical IDs
	 */
	private void findIdenticalIDs()
	{
		int size = getParentGUI().getDvkHandler().getDirectSize();
		progressInfoDialog.setProcessLabel(ManagingValues.IDENTICAL_IDS);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		ArrayList<Integer> identical = new ArrayList<>();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDvkHandler().getArtistsDirect(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.setProgressBar(false, true, size, i);
				
			}//IF
			
			String id = getParentGUI().getDvkHandler().getIdDirect(i);
			boolean hasID = false;
			for(int k = i + 1; k < size; k++)
			{
				if(!identical.contains(Integer.valueOf(k)) && getParentGUI().getDvkHandler().getIdDirect(k).equals(id))
				{
					identical.add(Integer.valueOf(k));
					if(!hasID)
					{
						progressInfoDialog.appendLog(null, false);
						progressInfoDialog.appendLog(getParentGUI().getDvkHandler().getDvkFileDirect(i).getAbsolutePath(), false);
					
					}//IF
					progressInfoDialog.appendLog(Character.toString('\t') + getParentGUI().getDvkHandler().getDvkFileDirect(k).getAbsolutePath(), false);
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
				DvkLoader loader = new DvkLoader(this, getParentGUI());
				loader.loadDVKs(getParentGUI().getSettings().getUseIndexes(), getParentGUI().getSettings().getUseIndexes(), true);
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
	public void loadingDVKsDone()
	{
		DvkLoader loader = new DvkLoader(this, getParentGUI());
		loader.sortDVKs(DvkHandler.SORT_ALPHA, true, false);
		
	}//METHOD

	@Override
	public void sortingDVKsDone()
	{
		startChecking();
		
	}//METHOD

	@Override
	public void filteringDVKsDone() {}
	
}//CLASS
