package drakovek.hoarder.gui.modes;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.file.dmf.DMF;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.ArtistValues;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.DmfLanguageValues;
import drakovek.hoarder.file.language.ManagingValues;
import drakovek.hoarder.file.language.ModeValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.compound.DButtonDialog;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
import drakovek.hoarder.processing.ExtensionMethods;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * GUI for running processes related to reformatting DMFs.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ReformatModeGUI extends ModeBaseGUI implements DWorker
{
	
	/**
	 * String containing ID for the current reformat process mode
	 */
	private String mode;
	
	/**
	 * Progress Dialog for showing progress in loading DMFs
	 */
	private DProgressDialog progressDialog;
	
	/**
	 * Progress Info Dialog for showing progress in reformatting processes
	 */
	private DProgressInfoDialog progressInfoDialog;
	
	/**
	 * Initializes ReformatModeGUI class.
	 * 
	 * @param frameGUI FrameGUI this mode GUI is contained within.
	 */
	public ReformatModeGUI(FrameGUI frameGUI)
	{
		super(frameGUI);
		progressDialog = new DProgressDialog(getSettings());
		progressInfoDialog = new DProgressInfoDialog(getSettings());
		
		String[] backIDs = {ModeValues.MODE_BACK, ModeValues.MODE_START, ModeValues.MANAGE_MODE};
		String[] modeIDs = {ManagingValues.REFORMAT_DMFS,
							ManagingValues.RENAME_FILES,
							ManagingValues.DELETE_SEQUENCES,
							ManagingValues.REFORMAT_HTMLS};
		
		setContentPanel(backIDs, modeIDs);
		
	}//CONSTRUCTOR
	
	/**
	 * Starts reformatting process by asking if the user wants to continue and loading DMFs.
	 */
	private void startProcess()
	{
		boolean run = true;
		String[] messageIDs = new String[2];
		switch(mode)
		{
			case ManagingValues.REFORMAT_DMFS:
				run = false;
				messageIDs[0] = ManagingValues.REFORMAT_MESSAGE;
				messageIDs[1] = CommonValues.CONTINUE_MESSAGE;
				break;
			case ManagingValues.RENAME_FILES:
				run = false;
				messageIDs[0] = ManagingValues.RENAME_MESSAGE;
				messageIDs[1] = CommonValues.CONTINUE_MESSAGE;
				break;
			case ManagingValues.DELETE_SEQUENCES:
				run = false;
				messageIDs[0] = ManagingValues.DELETE_SEQUENCES_MESSAGE;
				messageIDs[1] = CommonValues.CONTINUE_MESSAGE;
				break;
				
		}//METHOD
		
		if(!run)
		{
			DButtonDialog buttonDialog = new DButtonDialog(getSettings());
			String[] buttonIDs = {CommonValues.YES, CommonValues.NO};
			String response = buttonDialog.openButtonDialog(getParentGUI().getFrame(), ArtistValues.SURE_TITLE, messageIDs, buttonIDs);
			run = response.equals(CommonValues.YES);
			
		}//METHOD
		
		if(run)
		{
			progressDialog.setCancelled(false);
			getParentGUI().getFrame().setProcessRunning(true);
			progressDialog.startProgressDialog(getParentGUI().getFrame(), DmfLanguageValues.LOADING_DMFS_TITLE);
			(new DSwingWorker(this, DmfLanguageValues.LOADING_DMFS)).execute();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Loads DMFs from the given DMF directories if they are not already loaded.
	 */
	private void loadDMFs()
	{
		if(!getParentGUI().getDmfHandler().isLoaded())
		{
			getParentGUI().getDmfHandler().loadDMFs(getSettings().getDmfDirectories(), progressDialog, getSettings().getUseIndexes(), getSettings().getUseIndexes(), true);
		
		}//IF
		
		getParentGUI().getDmfHandler().sort(DmfHandler.SORT_ALPHA, true, false, false, false);
		
	}//METHOD
	
	/**
	 * Deals with DMF loading being finished, closing the progress dialog and allowing input.
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
	 */
	private void reformatDMFs()
	{
		int size = getParentGUI().getDmfHandler().getDirectSize();
		progressInfoDialog.setProcessLabel(ManagingValues.REFORMAT_DMFS);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDmfHandler().getArtistsDirect(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setProgressBar(false, true, size, i);
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.appendLog(artist, true);
				
			}//IF
			
			DMF dmf = new DMF(getParentGUI().getDmfHandler().getDmfFileDirect(i));
			dmf.writeDMF();
			getParentGUI().getDmfHandler().setDmfDirect(dmf, i);
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Reformats HTML files referenced by DMFs by adding HTML escape characters.
	 */
	private void reformatHTMLs()
	{
		int size = getParentGUI().getDmfHandler().getDirectSize();
		progressInfoDialog.setProcessLabel(ManagingValues.REFORMAT_HTMLS);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDmfHandler().getArtistsDirect(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setProgressBar(false, true, size, i);
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.appendLog(artist, true);
				
			}//IF
			
			File mediaFile = getParentGUI().getDmfHandler().getMediaFileDirect(i);
			String extension = ExtensionMethods.getExtension(mediaFile);
			if(extension.equals(".html") || extension.equals(".htm")) //$NON-NLS-1$ //$NON-NLS-2$
			{
				ArrayList<String> html = DReader.readFile(mediaFile);
				for(int k = 0; k < html.size(); k++)
				{
					html.set(k, StringMethods.addHtmlEscapesToHtml(html.get(k)));
					
				}//FOR
				
				DWriter.writeToFile(mediaFile, html);
				
			}//IF
			DMF dmf = new DMF(getParentGUI().getDmfHandler().getDmfFileDirect(i));
			dmf.writeDMF();
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Deletes all DMF sequence data.
	 */
	private void deleteSequences()
	{
		int size = getParentGUI().getDmfHandler().getDirectSize();
		progressInfoDialog.setProcessLabel(ManagingValues.DELETE_SEQUENCES);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDmfHandler().getArtistsDirect(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setProgressBar(false, true, size, i);
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.appendLog(artist, true);
				
			}//IF
			
			DMF dmf = new DMF(getParentGUI().getDmfHandler().getDmfFileDirect(i));
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
	 */
	private void renameFiles()
	{	
		int size = getParentGUI().getDmfHandler().getDirectSize();
		progressInfoDialog.setProcessLabel(ManagingValues.RENAME_FILES);
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
				progressInfoDialog.appendLog(artist, true);
				
			}//IF
			
			DMF dmf = new DMF(getParentGUI().getDmfHandler().getDmfFileDirect(i));
			dmf.rename(dmf.getDefaultFileName(), null, null);
			getParentGUI().getDmfHandler().setDmfDirect(dmf, i);
			
		}//FOR
		
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
				startProcess();
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void run(String id)
	{
		switch(id)
		{
			case DmfLanguageValues.LOADING_DMFS:
				loadDMFs();
				break;
			case ManagingValues.REFORMAT_DMFS:
				reformatDMFs();
				break;
			case ManagingValues.RENAME_FILES:
				renameFiles();
				break;
			case ManagingValues.DELETE_SEQUENCES:
				deleteSequences();
				break;
			case ManagingValues.REFORMAT_HTMLS:
				reformatHTMLs();
				break;
			
		}//SWITCH
		
	}//METHOD

	@Override
	public void done(String id)
	{
		switch(id)
		{
			case DmfLanguageValues.LOADING_DMFS:
				loadDmfsFinished();
				break;
			default:
				infoProcessFinished();
				break;
			
		}//SWITCH
		
	}//METHOD
	
}//CLASS

