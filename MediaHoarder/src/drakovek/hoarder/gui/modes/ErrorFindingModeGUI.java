package drakovek.hoarder.gui.modes;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.ExclusionFilter;
import drakovek.hoarder.file.dmf.DMF;
import drakovek.hoarder.file.dmf.DmfDatabase;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * GUI for running processes related to finding errors in DMFs.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ErrorFindingModeGUI extends ModeBaseGUI implements DWorker
{
	/**
	 * String containing ID for the current reformat process mode
	 * 
	 * @since 2.0
	 */
	private String mode;
	
	/**
	 * Progress Dialog for showing progress in loading DMFs
	 * 
	 * @since 2.0
	 */
	private DProgressDialog progressDialog;
	
	/**
	 * Progress Info Dialog for showing progress in reformatting processes
	 * 
	 * @since 2.0
	 */
	private DProgressInfoDialog progressInfoDialog;
	
	/**
	 * Initializes the ErrorFindingModeGUI.
	 * 
	 * @param frameGUI FrameGUI this mode GUI is contained within.
	 * @since 2.0
	 */
	public ErrorFindingModeGUI(FrameGUI frameGUI)
	{
		super(frameGUI);
		progressDialog = new DProgressDialog(getSettings());
		progressInfoDialog = new DProgressInfoDialog(getSettings());
		
		String[] backIDs = {DefaultLanguage.MODE_BACK, DefaultLanguage.MODE_START, DefaultLanguage.MANAGE_MODE};
		String[] modeIDs = {DefaultLanguage.MISSING_MEDIA,
							DefaultLanguage.UNLINKED_FILES,
							DefaultLanguage.IDENTICAL_IDS};
		
		setContentPanel(backIDs, modeIDs);
		
	}//CONSTRUCTOR

	/**
	 * Starts reformatting process by asking if the user wants to continue and loading DMFs.
	 * 
	 * @since 2.0
	 */
	private void startProcess()
	{
		progressDialog.setCancelled(false);
		getParentGUI().getFrame().setProcessRunning(true);
		progressDialog.startProgressDialog(getParentGUI().getFrame(), DefaultLanguage.LOADING_DMFS_TITLE);
		(new DSwingWorker(this, DefaultLanguage.LOADING_DMFS)).execute();
		
	}//METHOD
	
	/**
	 * Loads DMFs from the given DMF directories if they are not already loaded.
	 * 
	 * @since 2.0
	 */
	private void loadDMFs()
	{
		getParentGUI().getDmfHandler().loadDMFs(getSettings().getDmfDirectories(), progressDialog, getSettings().getUseIndexes(), getSettings().getUseIndexes(), true);
		getParentGUI().getDmfHandler().sort(DmfHandler.SORT_ALPHA, true, false, false);
		
	}//METHOD
	
	/**
	 * Deals with DMF loading being finished, closing the progress dialog and allowing input.
	 * 
	 * @since 2.0
	 */
	private void loadDmfsFinished()
	{
		progressDialog.setCancelled(false);
		progressDialog.closeProgressDialog();
		getParentGUI().getFrame().setProcessRunning(false);
		
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
	 * 
	 * @since 2.0
	 */
	private void infoProcessFinished()
	{
		progressInfoDialog.setCancelled(false);
		progressInfoDialog.showFinalLog(getParentGUI().getFrame(), getTitle(mode), getSettings().getDmfDirectories().get(0));
		getParentGUI().getFrame().setProcessRunning(false);
		
	}//METHOD
	
	/**
	 * Finds DMFs that are missing their attached media file(s)
	 * 
	 * @since 2.0
	 */
	private void findMissingMedia()
	{
		int size = getParentGUI().getDmfHandler().getSize();
		progressInfoDialog.setProcessLabel(DefaultLanguage.MISSING_MEDIA);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDmfHandler().getArtists(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.setProgressBar(false, true, size, i);
				
			}//IF
			
			if(!getParentGUI().getDmfHandler().getMediaFile(i).exists())
			{
				progressInfoDialog.appendLog(getParentGUI().getDmfHandler().getDmfFile(i).getAbsolutePath(), false);
				
			}//IF
			else
			{
				File secondary = getParentGUI().getDmfHandler().getSecondaryFile(i);
				if(secondary != null && !secondary.exists())
				{
					progressInfoDialog.appendLog(getParentGUI().getDmfHandler().getDmfFile(i).getAbsolutePath(), false);
					
				}//IF
				
			}//ELSE
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Finds media files in DMF folders that are not linked to DMF files.
	 * 
	 * @since 2.0
	 */
	private void findUnlinkedFiles()
	{
		String[] extension = {DMF.DMF_EXTENSION};
		ExclusionFilter filter = new ExclusionFilter(extension, false);
		progressInfoDialog.setProcessLabel(DefaultLanguage.MISSING_MEDIA);
		progressInfoDialog.setDetailLabel(DefaultLanguage.GETTING_FOLDERS, true);
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
	 * 
	 * @since 2.0
	 */
	private void findIdenticalIDs()
	{
		int size = getParentGUI().getDmfHandler().getSize();
		progressInfoDialog.setProcessLabel(DefaultLanguage.IDENTICAL_IDS);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		ArrayList<Integer> identical = new ArrayList<>();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDmfHandler().getArtists(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.setProgressBar(false, true, size, i);
				
			}//IF
			
			String id = getParentGUI().getDmfHandler().getID(i);
			boolean hasID = false;
			for(int k = i + 1; k < size; k++)
			{
				if(!identical.contains(Integer.valueOf(k)) && getParentGUI().getDmfHandler().getID(k).equals(id))
				{
					identical.add(Integer.valueOf(k));
					if(!hasID)
					{
						progressInfoDialog.appendLog(null, false);
						progressInfoDialog.appendLog(getParentGUI().getDmfHandler().getDmfFile(i).getAbsolutePath(), false);
					
					}//IF
					progressInfoDialog.appendLog(Character.toString('\t') + getParentGUI().getDmfHandler().getDmfFile(k).getAbsolutePath(), false);
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
			case DefaultLanguage.MODE_BACK:
			case DefaultLanguage.MANAGE_MODE:
				setContentPanel(new ManageModeGUI(getParentGUI()));
				break;
			case DefaultLanguage.MODE_START:
				setContentPanel(new ModesGUI(getParentGUI()));
				break;
			default:
				mode = id;
				startProcess();
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void run(String id)
	{
		switch(id)
		{
			case DefaultLanguage.LOADING_DMFS:
				loadDMFs();
				break;
			case DefaultLanguage.UNLINKED_FILES:
				findUnlinkedFiles();
				break;
			case DefaultLanguage.IDENTICAL_IDS:
				findIdenticalIDs();
				break;
			case DefaultLanguage.MISSING_MEDIA:
				findMissingMedia();
				break;
			
		}//SWITCH
		
	}//METHOD

	@Override
	public void done(String id)
	{
		switch(id)
		{
			case DefaultLanguage.LOADING_DMFS:
				loadDmfsFinished();
				break;
			default:
				infoProcessFinished();
				break;
			
		}//SWITCH
		
	}//METHOD
	
}//CLASS
