package drakovek.hoarder.gui.modes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.file.dmf.DMF;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.compound.DButtonDialog;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
import drakovek.hoarder.processing.ExtensionMethods;
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
			case DefaultLanguage.RENAME_FILES:
				run = false;
				messageIDs[0] = DefaultLanguage.RENAME_MESSAGE;
				messageIDs[1] = DefaultLanguage.CONTINUE_MESSAGE;
				break;
			case DefaultLanguage.DELETE_SEQUENCES:
				run = false;
				messageIDs[0] = DefaultLanguage.DELETE_SEQUENCES_MESSAGE;
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
		progressInfoDialog.setProcessLabel(DefaultLanguage.REFORMAT_DMFS);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDmfHandler().getArtists(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setProgressBar(false, true, size, i);
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.appendLog(artist, true);
				
			}//IF
			
			DMF dmf = new DMF(getParentGUI().getDmfHandler().getDmfFile(i));
			dmf.writeDMF();
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Deletes all DMF sequence data.
	 * 
	 * @since 2.0
	 */
	private void deleteSequences()
	{
		int size = getParentGUI().getDmfHandler().getSize();
		progressInfoDialog.setProcessLabel(DefaultLanguage.DELETE_SEQUENCES);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDmfHandler().getArtists(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setProgressBar(false, true, size, i);
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.appendLog(artist, true);
				
			}//IF
			
			DMF dmf = new DMF(getParentGUI().getDmfHandler().getDmfFile(i));
			dmf.setNextID(null);
			dmf.setLastID(null);
			dmf.setFirst(false);
			dmf.setLast(false);
			dmf.setSequenceTitle(null);
			dmf.setSectionTitle(null);
			dmf.writeDMF();
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Renames files to fit DMF titles
	 * 
	 * @since 2.0
	 */
	private void renameFiles()
	{
		int size = getParentGUI().getDmfHandler().getSize();
		progressInfoDialog.setProcessLabel(DefaultLanguage.RENAME_FILES);
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
				progressInfoDialog.appendLog(artist, true);
				
			}//IF
			
			File currentFolder = getParentGUI().getDmfHandler().getDmfFile(i).getParentFile();
			if(currentFolder != null && currentFolder.isDirectory())
			{
				String extension = ExtensionMethods.getExtension(getParentGUI().getDmfHandler().getMediaFile(i));
				String outName = DWriter.getFileFriendlyName(getParentGUI().getDmfHandler().getTitle(i), true) + '_' + getParentGUI().getDmfHandler().getID(i);
				File outFile = new File(currentFolder, outName + extension);
				File tempFile = new File(currentFolder, "xxxTEMPxxx" + getParentGUI().getDmfHandler().getID(i) + extension); //$NON-NLS-1$
				try
				{
					Files.move(getParentGUI().getDmfHandler().getMediaFile(i).toPath(), tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
					Files.move(tempFile.toPath(), outFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
					DMF dmf = new DMF(getParentGUI().getDmfHandler().getDmfFile(i));
					
					if(outFile.exists())
					{
						dmf.getDmfFile().delete();
						dmf.setDmfFile(new File(currentFolder, outName + DMF.DMF_EXTENSION));
						dmf.setMediaFile(outFile);
						dmf.writeDMF();
						getParentGUI().getDmfHandler().setDMF(dmf, i);
						
					}//IF
					
				}//TRY
				catch (IOException e){}
	
			}//IF
			
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
			case DefaultLanguage.RENAME_FILES:
				renameFiles();
				break;
			case DefaultLanguage.DELETE_SEQUENCES:
				deleteSequences();
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

