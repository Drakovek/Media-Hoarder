package drakovek.hoarder.gui.modes;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.file.dvk.DVK;
import drakovek.hoarder.file.dvk.DvkHandler;
import drakovek.hoarder.file.dvk.DvkLoader;
import drakovek.hoarder.file.dvk.DvkLoadingMethods;
import drakovek.hoarder.file.language.ArtistValues;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.ManagingValues;
import drakovek.hoarder.file.language.ModeValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.compound.DButtonDialog;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
import drakovek.hoarder.processing.ExtensionMethods;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * GUI for running processes related to reformatting DVKs.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ReformatModeGUI extends ModeBaseGUI implements DWorker, DvkLoadingMethods
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
	 * Initializes ReformatModeGUI class.
	 * 
	 * @param frameGUI FrameGUI this mode GUI is contained within.
	 */
	public ReformatModeGUI(FrameGUI frameGUI)
	{
		super(frameGUI);
		progressInfoDialog = new DProgressInfoDialog(getSettings());
		
		String[] backIDs = {ModeValues.MODE_BACK, ModeValues.MODE_START, ModeValues.MANAGE_MODE};
		String[] modeIDs = {ManagingValues.REFORMAT_DVKS,
							ManagingValues.RENAME_FILES,
							ManagingValues.DELETE_SEQUENCES,
							ManagingValues.REFORMAT_HTMLS};
		
		setContentPanel(backIDs, modeIDs);
		
	}//CONSTRUCTOR
	
	/**
	 * Starts reformatting process by asking if the user wants to continue and loading DVKs.
	 */
	private void startProcess()
	{
		boolean run = true;
		String[] messageIDs = new String[2];
		switch(mode)
		{
			case ManagingValues.REFORMAT_DVKS:
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
			
			if(!getParentGUI().getDvkHandler().isLoaded())
			{
				DvkLoader loader = new DvkLoader(this, getParentGUI());
				loader.loadDVKs(getSettings().getUseIndexes(), getSettings().getUseIndexes(), true);
			
			}//IF
			else
			{
				loadingDVKsDone();
				
			}//ELSE
			
		}//IF
		
	}//METHOD
	
	/**
	 * Reformats DVKs to fit the current format for DVK files.
	 */
	private void reformatDVKs()
	{
		int size = getParentGUI().getDvkHandler().getDirectSize();
		progressInfoDialog.setProcessLabel(ManagingValues.REFORMAT_DVKS);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDvkHandler().getArtistsDirect(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setProgressBar(false, true, size, i);
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.appendLog(artist, true);
				
			}//IF
			
			DVK dvk = new DVK(getParentGUI().getDvkHandler().getDvkFileDirect(i));
			dvk.writeDVK();
			getParentGUI().getDvkHandler().setDvkDirect(dvk, i);
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Reformats HTML files referenced by DVKs by adding HTML escape characters.
	 */
	private void reformatHTMLs()
	{
		int size = getParentGUI().getDvkHandler().getDirectSize();
		progressInfoDialog.setProcessLabel(ManagingValues.REFORMAT_HTMLS);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDvkHandler().getArtistsDirect(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setProgressBar(false, true, size, i);
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.appendLog(artist, true);
				
			}//IF
			
			File mediaFile = getParentGUI().getDvkHandler().getMediaFileDirect(i);
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
			DVK dvk = new DVK(getParentGUI().getDvkHandler().getDvkFileDirect(i));
			dvk.writeDVK();
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Deletes all DVK sequence data.
	 */
	private void deleteSequences()
	{
		int size = getParentGUI().getDvkHandler().getDirectSize();
		progressInfoDialog.setProcessLabel(ManagingValues.DELETE_SEQUENCES);
		progressInfoDialog.setProgressBar(false, true, size, 0);
		progressInfoDialog.appendLog('[' + getSettings().getLanguageText(mode).toUpperCase() + ']', false);
		String artist = new String();
		
		for(int i = 0; !progressInfoDialog.isCancelled() && i < size; i++)
		{
			String artistCheck = getParentGUI().getDvkHandler().getArtistsDirect(i)[0];
			if(artistCheck != null && !artistCheck.equals(artist))
			{
				artist = artistCheck;
				progressInfoDialog.setProgressBar(false, true, size, i);
				progressInfoDialog.setDetailLabel(artist, false);
				progressInfoDialog.appendLog(artist, true);
				
			}//IF
			
			DVK dvk = new DVK(getParentGUI().getDvkHandler().getDvkFileDirect(i));
			dvk.writeDVK();
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Renames files to fit DVK titles
	 */
	private void renameFiles()
	{	
		int size = getParentGUI().getDvkHandler().getDirectSize();
		progressInfoDialog.setProcessLabel(ManagingValues.RENAME_FILES);
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
				progressInfoDialog.appendLog(artist, true);
				
			}//IF
			
			DVK dvk = new DVK(getParentGUI().getDvkHandler().getDvkFileDirect(i));
			dvk.rename(dvk.getDefaultFileName(), null, null);
			getParentGUI().getDvkHandler().setDvkDirect(dvk, i);
			
		}//FOR
		
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
			case ManagingValues.REFORMAT_DVKS:
				reformatDVKs();
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
		infoProcessFinished();
		
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
		if(getParentGUI().getDvkHandler().isLoaded())
		{
			progressInfoDialog.setCancelled(false);
			getParentGUI().getFrame().setProcessRunning(true);
			progressInfoDialog.startProgressDialog(getParentGUI().getFrame(), this.getTitle(mode));
			(new DSwingWorker(this, mode)).execute();
			
		}//IF
		
	}//METHOD

	@Override
	public void filteringDVKsDone() {}
	
}//CLASS

