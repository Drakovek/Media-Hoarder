package drakovek.hoarder.gui.modes;

import drakovek.hoarder.file.dmf.DMF;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.compound.DButtonDialog;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * GUI for running processes related to reformatting DMFs.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ReformatModeGUI extends ModeBaseGUI implements DWorker
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
	 * Initializes ReformatModeGUI class.
	 * 
	 * @param frameGUI FrameGUI this mode GUI is contained within.
	 * @since 2.0
	 */
	public ReformatModeGUI(FrameGUI frameGUI)
	{
		super(frameGUI);
		progressDialog = new DProgressDialog(getSettings());
		progressInfoDialog = new DProgressInfoDialog(getSettings());
		
		String[] backIDs = {DefaultLanguage.MODE_BACK, DefaultLanguage.MODE_START, DefaultLanguage.MANAGE_MODE};
		String[] modeIDs = {DefaultLanguage.REFORMAT_DMFS,
							DefaultLanguage.RENAME_FILES,
							DefaultLanguage.DELETE_SEQUENCES};
		
		setContentPanel(backIDs, modeIDs);
		
	}//CONSTRUCTOR
	
	/**
	 * Starts reformatting process by asking if the user wants to continue and loading DMFs.
	 * 
	 * @since 2.0
	 */
	private void startProcess()
	{
		boolean run = true;
		String[] messageIDs = new String[2];
		switch(mode)
		{
			case DefaultLanguage.REFORMAT_DMFS:
				run = false;
				messageIDs[0] = DefaultLanguage.REFORMAT_MESSAGE;
				messageIDs[1] = DefaultLanguage.CONTINUE_MESSAGE;
				break;
				
		}//METHOD
		
		if(!run)
		{
			DButtonDialog buttonDialog = new DButtonDialog(getSettings());
			String[] buttonIDs = {DefaultLanguage.YES, DefaultLanguage.NO};
			String response = buttonDialog.openButtonDialog(getParentGUI().getFrame(), DefaultLanguage.SURE_TITLE, messageIDs, buttonIDs);
			run = response.equals(DefaultLanguage.YES);
			
		}//METHOD
		
		if(run)
		{
			progressDialog.setCancelled(false);
			getParentGUI().getFrame().setProcessRunning(true);
			progressDialog.startProgressDialog(getParentGUI().getFrame(), DefaultLanguage.LOADING_DMFS_TITLE);
			(new DSwingWorker(this, DefaultLanguage.LOADING_DMFS)).execute();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Loads DMFs from the given DMF directories if they are not already loaded.
	 * 
	 * @since 2.0
	 */
	private void loadDMFs()
	{
		if(!getParentGUI().getDmfHandler().isLoaded())
		{
			getParentGUI().getDmfHandler().loadDMFs(getSettings().getDmfDirectories(), progressDialog, getSettings().getUseIndexes(), getSettings().getUseIndexes(), true);
		
		}//IF
		
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
	 * Reformats DMFs to fit the current format for DMF files.
	 * 
	 * @since 2.0
	 */
	private void reformatDMFs()
	{
		int size = getParentGUI().getDmfHandler().getSize();
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + mode.toUpperCase() + ']', false);
		String artist = new String();
		
		for(int i = 0; i < size; i++)
		{
			String artistCheck = getParentGUI().getDmfHandler().getArtists(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setProgressBar(false, true, size, i);
				progressInfoDialog.appendLog(artist, true);
				
			}//IF
			
			DMF dmf = new DMF(getParentGUI().getDmfHandler().getDmfFile(i));
			dmf.writeDMF();
			
		}//FOR
		
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
			case DefaultLanguage.REFORMAT_DMFS:
				reformatDMFs();
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

