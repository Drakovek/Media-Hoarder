package drakovek.hoarder.gui.artist;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.gargoylesoftware.htmlunit.CookieManager;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.file.Start;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.dmf.DmfLoader;
import drakovek.hoarder.file.dmf.DmfLoadingMethods;
import drakovek.hoarder.file.language.ArtistValues;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.settings.SettingsBarGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DCheckBox;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DMenu;
import drakovek.hoarder.gui.swing.components.DMenuItem;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.compound.DButtonDialog;
import drakovek.hoarder.gui.swing.compound.DCheckDirectoriesGUI;
import drakovek.hoarder.gui.swing.compound.DFileChooser;
import drakovek.hoarder.gui.swing.compound.DProgressInfoDialog;
import drakovek.hoarder.gui.swing.compound.DTextDialog;
import drakovek.hoarder.processing.BooleanInt;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.web.ClientMethods;
import drakovek.hoarder.web.Downloader;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * Creates GUI for downloading files from artist hosting websites.
 * 
 * @author Drakovek
 * @version 2.0
 */
public abstract class ArtistHostingGUI extends FrameGUI implements ClientMethods, LoginMethods, DWorker, DmfLoadingMethods
{	
	/**
	 * Suffix used at the end of a DMF ID to indicate the DMF is referring to a journal rather than a media file.
	 */
	public static final String JOURNAL_SUFFIX = "-J"; //$NON-NLS-1$
	
	/**
	 * Tag for showing a DMF refers to a journal
	 */
	public static final String JOURNAL_TAG = "Journal"; //$NON-NLS-1$
	
	/**
	 * Tag for General Rating
	 */
	public static final String GENERAL_RATING = "General Rating"; //$NON-NLS-1$
	
	/**
	 * Tag for Mature Rating
	 */
	public static final String MATURE_RATING = "Mature Rating"; //$NON-NLS-1$
	
	/**
	 * Tag for Adult Rating
	 */
	public static final String ADULT_RATING = "Adult Rating"; //$NON-NLS-1$
	
	/**
	 * Tag for Scraps Gallery
	 */
	public static final String SCRAPS_GALLERY = "Scraps Gallery"; //$NON-NLS-1$
	
	/**
	 * Tag for Main Gallery
	 */
	public static final String MAIN_GALLERY = "Main Gallery"; //$NON-NLS-1$
	
	/**
	 * List of currently selected artists
	 */
	private ArrayList<String> artists;
	
	/**
	 * Whether to check all gallery pages or just the latest gallery pages
	 */
	private boolean checkAllPages;
	
	/**
	 * DmfLoader for loading DMFs to check against
	 */
	private DmfLoader loader;
	
	/**
	 * Settings Bar for the GUI
	 */
	private SettingsBarGUI settingsBar;
	
	/**
	 * DFileChooser for the GUI to choose the directory in which to save files.
	 */
	private DFileChooser fileChooser;
	
	/**
	 * GUI for logging into the artist hosting website
	 */
	private LoginGUI loginGUI;
	
	/**
	 * Downloader object for downloading information.
	 */
	private Downloader downloader;
	
	/**
	 * Object for handling the artist list
	 */
	private ArtistHandler artistHandler;
	
	/**
	 * Button to check new pages from which to save info
	 */
	private DButton newButton;
	
	/**
	 * Button to check all pages from which to save info
	 */
	private DButton allButton;
	
	/**
	 * Button to save info from a single page
	 */
	private DButton singleButton;
	
	/**
	 * Button to add artists from the artist list.
	 */
	private DButton addButton;
	
	/**
	 * Button to remove artists from the artist list.
	 */
	private DButton removeButton;
	
	/**
	 * Checkbox to determine whether to save journal pages.
	 */
	private DCheckBox journalCheck;
	
	/**
	 * List of artists to download from
	 */
	private DList artistList;
	
	/**
	 * Progress dialog used for showing log information
	 */
	private DProgressInfoDialog progressInfoDialog;
	
	/**
	 * PageURL given by the user when downloading an individual DMF
	 */
	private String pageURL;
	
	/**
	 * Initializes the ArtistHostingGUI
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHandler
	 * @param loginGUI GUI for logging into the artist hosting website
	 * @param subtitleID ID for the sub-title of the frame
	 * @param openID Language ID for the "open" menu item
	 */
	public ArtistHostingGUI(DSettings settings, DmfHandler dmfHandler, LoginGUI loginGUI, final String subtitleID, final String openID)
	{
		super(settings, dmfHandler, subtitleID);
		loader = new DmfLoader(this, this);
		fileChooser = new DFileChooser(settings);
		artistHandler = new ArtistHandler(settings, subtitleID);
		downloader = new Downloader(this);
		progressInfoDialog = new DProgressInfoDialog(settings);
		this.loginGUI = loginGUI;
		this.loginGUI.setLoginMethods(this);
		checkAllPages = false;
		pageURL = null;
		
		//MENUS
		JMenuBar menubar = new JMenuBar();
		DMenu fileMenu = new DMenu(this, CommonValues.FILE);
		menubar.add(fileMenu);
		DMenuItem openItem = new DMenuItem(this, CommonValues.OPEN);
		openItem.setTextID(openID);
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		fileMenu.add(new DMenuItem(this, CommonValues.RESTART_PROGRAM));
		fileMenu.add(new DMenuItem(this, CommonValues.EXIT));
		
		//TITLE PANEL
		DLabel titleLabel = new DLabel(this, null, subtitleID);
		titleLabel.setFontLarge();
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new GridBagLayout());
		GridBagConstraints titleCST = new GridBagConstraints();
		titleCST.gridx = 0;			titleCST.gridy = 0;
		titleCST.gridwidth = 3;		titleCST.gridheight = 1;
		titleCST.weightx = 1;		titleCST.weighty = 0;
		titleCST.fill = GridBagConstraints.BOTH;
		titlePanel.add(titleLabel, titleCST);
		titleCST.gridy = 1;
		titlePanel.add(getVerticalSpace(), titleCST);
		titleCST.gridy = 2;
		titlePanel.add(new JSeparator(SwingConstants.HORIZONTAL), titleCST);
		
		//CREATE CENTER PANEL
		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new GridLayout(3, 1, 0, settings.getSpaceSize()));
		newButton = new DButton(this, ArtistValues.CHECK_NEW);
		newButton.setFontLarge();
		allButton = new DButton(this, ArtistValues.CHECK_ALL);
		allButton.setFontLarge();
		singleButton = new DButton(this, ArtistValues.DOWNLOAD_SINGLE);
		singleButton.setFontLarge();
		actionPanel.add(newButton);
		actionPanel.add(allButton);
		actionPanel.add(singleButton);
		
		artistList = new DList(this, true, ArtistValues.ARTISTS);
		DScrollPane artistScroll = new DScrollPane(settings, artistList);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		centerPanel.add(actionPanel);
		centerPanel.add(artistScroll);
		
		//LABEL PANEL
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		labelPanel.add(new DLabel(this, null, ArtistValues.ACTIONS));
		labelPanel.add(new DLabel(this, artistList, ArtistValues.ARTISTS));
		
		//ADD PANEL
		JPanel addPanel = new JPanel();
		addPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		addButton = new DButton(this, CommonValues.ADD);
		removeButton = new DButton(this, CommonValues.REMOVE);
		addPanel.add(addButton);
		addPanel.add(removeButton);
		
		//BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		journalCheck = new DCheckBox(this, settings.getSaveJournals(), ArtistValues.SAVE_JOURNALS);
		bottomPanel.add(journalCheck);
		bottomPanel.add(addPanel);
		
		//CREATE FULL PANEL
		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new BorderLayout());
		fullPanel.add(getSpacedPanel(labelPanel, 1, 0, false, false, true, true), BorderLayout.NORTH);
		fullPanel.add(getSpacedPanel(centerPanel, 1, 1, true, true, true, true), BorderLayout.CENTER);
		fullPanel.add(getSpacedPanel(bottomPanel, 1, 0, false, false, true, true), BorderLayout.SOUTH);
		
		//FINALIZE GUI
		settingsBar = new SettingsBarGUI(this);
		settingsBar.setLabel(getDirectory());
		getFrame().setJMenuBar(menubar);
		getFrame().getContentPane().add(this.getSpacedPanel(titlePanel, 1, 0, true, true, true, true), BorderLayout.NORTH);
		getFrame().getContentPane().add(fullPanel, BorderLayout.CENTER);
		getFrame().getContentPane().add(settingsBar.getPanel(), BorderLayout.SOUTH);
		getFrame().packRestricted();
		getFrame().setMinimumSize(getFrame().getSize());
		getFrame().setLocationRelativeTo(null);
		getFrame().setVisible(true);
		updateArtistList();
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the directory from which to save DMFs
	 * 
	 * @param directory Given Directory
	 */
	protected abstract void setDirectory(final File directory);
	
	/**
	 * Returns the directory from which to save DMFs
	 * 
	 * @return Directory
	 */
	protected abstract File getDirectory();
	
	/**
	 * Loads all IDs related to the current service to check if media has already been downloaded.
	 */
	protected abstract void getIdStrings();
	
	/**
	 * Returns the title to use for the progress log.
	 * 
	 * @return Progress Log Title
	 */
	protected abstract String getTitle();
	
	/**
	 * Returns a list of page URLs for a given artist.
	 * 
	 * @param pd DProgressInfoDialog used to show progress
	 * @param artist Fur Affinity Artist
	 * @param checkAll Whether to check all gallery pages
	 * @param scraps Whether to check the scraps gallery
	 * @return List of page URLs
	 */
	protected abstract ArrayList<String> getMediaPages(DProgressInfoDialog pd, final String artist, final boolean checkAll, final boolean scraps);
	
	/**
	 * Returns a list of journal page URLs for a given artist.
	 * 
	 * @param pd DProgressInfoDialog used to show progress
	 * @param artist Fur Affinity Artist
	 * @param checkAll Whether to check all gallery pages
	 * @return List of page URLs
	 */
	protected abstract ArrayList<String> getJournalPages(DProgressInfoDialog pd, final String artist, final boolean checkAll);
	
	/**
	 * Returns a string for a given artist compatible with the URL for the current artist hosting site.
	 * 
	 * @param artist Given Artist
	 * @return Formatted Artist String for use in URL
	 */
	protected abstract String getUrlArtist(final String artist);
	
	/**
	 * Downloads media and creates DMF for a media page.
	 * 
	 * @param baseFolder Base Folder to save within
	 * @param URL PageURL to read
	 * @return Title of the most recently downloaded page
	 * @throws Exception Any problem reading Fur Affinity data
	 */
	protected abstract String downloadMediaPage(final File baseFolder, final String URL) throws Exception; 
	
	/**
	 * Downloads journal and creates DMF for a journal page.
	 * 
	 * @param baseFolder Base Folder to save within
	 * @param URL PageURL to read
	 * @return Title of the most recently downloaded page
	 * @throws Exception Any problem reading Fur Affinity data
	 */
	protected abstract String downloadJournalPage(final File baseFolder, final String URL) throws Exception;
	
	/**
	 * Checks whether a given page URL has already been read and downloaded
	 * 
	 * @param URL Page URL to download
	 * @return Whether the URL has already downloaded
	 */
	protected abstract boolean isDownloaded(final String URL);
	
	/**
	 * Returns section of page URL that shows it is part of a media gallery
	 * 
	 * @return Gallery URL Fragment
	 */
	protected abstract String getGalleryUrlFragment();
	
	/**
	 * Returns the main URL of the artist hosting site, used to check if URL is from the given website.
	 * 
	 * @return Main ArtistHosting URL
	 */
	protected abstract String getMainURL();
	
	/**
	 * Displays the current list of artists.
	 */
	private void updateArtistList()
	{
		ArrayList<String> currentArtists = new ArrayList<>();
		currentArtists.add(getSettings().getLanguageText(ArtistValues.ALL_ARTISTS));
		currentArtists.addAll(artistHandler.getArtists());
		artistList.setListData(StringMethods.arrayListToArray(currentArtists));
		
	}//METHOD
	
	/**
	 * Starts the process to check pages for one or multiple artists.
	 * 
	 * @param checkAll Whether to check all of the pages, if false only checks the new pages.
	 */
	private void checkPages(final boolean checkAll)
	{	
		boolean ready = true;
		checkAllPages = checkAll;
		
		//CHECK IF DIRECTORY IS SELECTED
		if(getDirectory() == null || !getDirectory().isDirectory())
		{
			ready = false;
			DButtonDialog buttonDialog = new DButtonDialog(getSettings());
			String[] messageIDs = {CommonValues.NO_DIRECTORY_DIALOG};
			String[] buttonIDs = {CommonValues.OK};
			buttonDialog.openButtonDialog(getFrame(), CommonValues.NO_DIRECTORY_DIALOG, messageIDs, buttonIDs);
		
		}//IF
		
		//CHECK IF ARTISTS ARE SELECTED
		if(ready)
		{
			artists = artistHandler.getArtists(artistList.getSelectedIndices());
			if(artists.size() == 0)
			{
				ready = false;
				DButtonDialog buttonDialog = new DButtonDialog(getSettings());
				String[] messageIDs = {ArtistValues.NO_ARTISTS};
				String[] buttonIDs = {CommonValues.OK};
				buttonDialog.openButtonDialog(getFrame(), ArtistValues.NO_ARTISTS, messageIDs, buttonIDs);
			
			}//IF
			
		}//IF
		
		//CHECK IF IN THE CORRECT DIRECTORY
		if(ready)
		{
			boolean missingFolders = false;
			for(int i = 0; i < artistHandler.getArtists().size(); i++)
			{
				File artistFolder = new File(getDirectory(), DWriter.getFileFriendlyName(artistHandler.getArtists().get(i)));
				if(!artistFolder.isDirectory())
				{
					missingFolders = true;
					break;
					
				}//IF
				
			}//FOR
			
			if(missingFolders)
			{
				DButtonDialog buttonDialog = new DButtonDialog(getSettings());
				String[] buttonIDs = {CommonValues.YES, CommonValues.NO};
				String result = buttonDialog.openButtonDialog(getFrame(), ArtistValues.SURE_TITLE, ArtistValues.WRONG_FOLDER_MESSAGES, buttonIDs);
			
				if(result.equals(CommonValues.NO))
				{
					ready = false;
					
				}//IF
				
			}//IF
			
		}//IF
		
		//LOGIN
		if(ready)
		{
			new DCheckDirectoriesGUI(getSettings(), getDmfHandler(), getFrame(), getDirectory());
			
			//LOGIN TO WEBSITE
			if(ready && !isLoggedIn())
			{
				loginGUI.openLoginDialog(getFrame());
				loginGUI.clearFields();
				ready = isLoggedIn();
				
			}//IF
			
		}//IF
		
		if(ready)
		{
			if(!getDmfHandler().isLoaded())
			{
				loader.loadDMFs(getSettings().getUseIndexes(), getSettings().getUseIndexes(), true);
				
			}//METHOD
			else
			{
				loadingDMFsDone();
				
			}//ELSE
			
		}//IF
		
	}//METHOD
	
	/**
	 * Returns all the page URLs for a given artist that have not already been downloaded.
	 * 
	 * @param pid DProgressInfoDialog used to show progress
	 * @param artist Given Artist
	 * @param checkAll Whether to check all pages or just new pages.
	 * @param checkJournals Whether to check for the artist's journals
	 * @return Artist's Page URLs
	 */
	protected ArrayList<String> getPages(DProgressInfoDialog pid, final String artist, final boolean checkAll, final boolean checkJournals)
	{
		pid.setProcessLabel(ArtistValues.GETTING_PAGE_URLS);
		pid.setDetailLabel(artist, false);
		pid.setProgressBar(true, false, 0, 0);
		pid.appendLog(null, false);
		String urlArtist = getUrlArtist(artist);
		
		//GETS PAGES
		pid.appendLog(artist + DProgressInfoDialog.SPACER + getSettings().getLanguageText(ArtistValues.GETTING_GALLERY_PAGES), true);
		ArrayList<String> pages = getMediaPages(pid, urlArtist, checkAll, false);
		pid.appendLog(artist + DProgressInfoDialog.SPACER + getSettings().getLanguageText(ArtistValues.GETTING_SCRAP_PAGES), true);
		pages.addAll(getMediaPages(pid, urlArtist, checkAll, true));
		
		if(checkJournals)
		{
			pid.appendLog(artist + DProgressInfoDialog.SPACER + getSettings().getLanguageText(ArtistValues.GETTING_JOURNAL_PAGES), true);
			pages.addAll(getJournalPages(pid, urlArtist, checkAll));
			
		}//IF
		
		return pages;
		
	}//METHOD
	
	/**
	 * Downloads media from list of pageURLs from a given artist.
	 * 
	 * @param pid DProgressInfoDialog used to show progress
	 * @param artist Given Artist
	 * @param pages Page URLs
	 */
	protected void downloadPages(DProgressInfoDialog pid, final String artist, final ArrayList<String> pages)
	{
		File artistFolder = DReader.getDirectory(getDirectory(), DWriter.getFileFriendlyName(artist));
		for(int i = pages.size() - 1; !pid.isCancelled() && i > -1; i--)
		{
			pid.setProcessLabel(ArtistValues.LOADING_PAGE);
			pid.setDetailLabel(pages.get(i), false);
			pid.setProgressBar(false, true, pages.size(), pages.size() - (i + 1));
			pid.appendLog(getSettings().getLanguageText(ArtistValues.LOADING_PAGE) + ' ' + pages.get(i), true);
			
			try
			{
				if(pages.get(i).contains(getGalleryUrlFragment()))
				{
					String title = downloadMediaPage(artistFolder, pages.get(i));
					pid.appendLog(getSettings().getLanguageText(CommonValues.DOWNLOADED) + DProgressInfoDialog.SPACER + title , true);
					
				}//IF
				else
				{
					String title = downloadJournalPage(artistFolder, pages.get(i));
					pid.appendLog(getSettings().getLanguageText(CommonValues.DOWNLOADED) + DProgressInfoDialog.SPACER + title , true);
						
				}//ELSE
				
			}//TRY
			catch(Exception e)
			{
				pid.setCancelled(true);
				pid.appendLog(getSettings().getLanguageText(CommonValues.DOWNLOAD_FAILED), true);
				pid.appendLog(e.getMessage(), false);
				e.printStackTrace();
				
			}//CATCH
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Starts the process of downloading a single media file.
	 */
	private void downloadSingle()
	{
		artists = new ArrayList<>();
		boolean ready = true;
		
		//CHECK IF DIRECTORY IS SELECTED
		if(getDirectory() == null || !getDirectory().isDirectory())
		{
			ready = false;
			DButtonDialog buttonDialog = new DButtonDialog(getSettings());
			String[] messageIDs = {CommonValues.NO_DIRECTORY_DIALOG};
			String[] buttonIDs = {CommonValues.OK};
			buttonDialog.openButtonDialog(getFrame(), CommonValues.NO_DIRECTORY_DIALOG, messageIDs, buttonIDs);
		
		}//IF
		
		//LOGIN
		if(ready)
		{
			//LOGIN TO WEBSITE
			if(ready && !isLoggedIn())
			{
				loginGUI.openLoginDialog(getFrame());
				loginGUI.clearFields();
				ready = isLoggedIn();
				
			}//IF
			
		}//IF
		
		if(ready)
		{
			//GET PAGE URL
			DTextDialog textDialog = new DTextDialog(getSettings());
			String[] messageIDs = {ArtistValues.ENTER_URL_MESSAGE};
			pageURL = textDialog.openTextDialog(getFrame(), ArtistValues.ENTER_URL_TITLE, messageIDs, null);
			
			if(!getDmfHandler().isLoaded())
			{
				loader.loadDMFs(getSettings().getUseIndexes(), getSettings().getUseIndexes(), true);
				
			}//METHOD
			else
			{
				loadingDMFsDone();
				
			}//ELSE
			
		}//IF
		
	}//METHOD
	
	/**
	 * Downloads media from a given URL.
	 * 
	 * @param pid DProgressInfoDialog used to show progress
	 * @param URL Given URL
	 * @param baseDirectory Directory in which to save URL
	 */
	protected void downloadSinglePage(DProgressInfoDialog pid, final String URL, final File baseDirectory)
	{
		pid.setProcessLabel(ArtistValues.LOADING_PAGE);
		pid.setDetailLabel(pageURL, false);
		pid.setProgressBar(true, false, 0, 0);
		pid.appendLog(getSettings().getLanguageText(ArtistValues.LOADING_PAGE) + ' ' + pageURL, true);
		
		if(pageURL != null && pageURL.contains(getMainURL()))
		{
			if(!isDownloaded(pageURL))
			{
				try
				{
					if(pageURL.contains(getGalleryUrlFragment()))
					{
						String title = downloadMediaPage(baseDirectory, pageURL);
						pid.appendLog(getSettings().getLanguageText(CommonValues.DOWNLOADED) + DProgressInfoDialog.SPACER + title , true);
					
					}//IF
					else
					{
						String title = downloadJournalPage(baseDirectory, pageURL);
						pid.appendLog(getSettings().getLanguageText(CommonValues.DOWNLOADED) + DProgressInfoDialog.SPACER + title , true);
						
					}//ELSE
				
				}//TRY
				catch(Exception e)
				{
					pid.setCancelled(true);
					pid.appendLog(getSettings().getLanguageText(CommonValues.DOWNLOAD_FAILED), true);
					pid.appendLog(e.getMessage(), false);
					e.printStackTrace();
				
				}//CATCH
				
			}//IF
			else
			{
				pid.appendLog(getSettings().getLanguageText(CommonValues.ALREADY_DOWNLOADED), true);
				
			}//ELSE
			
		}//IF
		else
		{
			pid.appendLog(getSettings().getLanguageText(CommonValues.INVALID_URL), true);
			
		}//ELSE
		
		
	}//METHOD
	
	/**
	 * Returns the object's Downloader object.
	 * 
	 * @return Downloader
	 */
	public Downloader getDownloader()
	{
		return downloader;
		
	}//METHOD
	
	/**
	 * Returns the directory for holding image captchas.
	 * 
	 * @return Captcha Folder
	 */
	public File getCaptchaFolder()
	{
		return loginGUI.getCaptchaFolder();
		
	}//METHOD

	/**
	 * Selects the main directory for this object.
	 */
	private void selectDirectory()
	{
		File file = fileChooser.openDialog(getFrame(), getDirectory(), null);
		
		if(file != null && file.isDirectory())
		{
			setDirectory(file);
			settingsBar.setLabel(getDirectory());
		
		}//IF
		
	}//METHOD
	
	/**
	 * Adds a given artist to the list of artists.
	 */
	private void addArtist()
	{
		DTextDialog textDialog = new DTextDialog(getSettings());
		String artist = textDialog.openTextDialog(getFrame(), ArtistValues.ADD_ARTIST, ArtistValues.NAME_OF_ARTIST, null);
		
		if(artist != null)
		{
			artistHandler.addArtist(artist);
			updateArtistList();
			artistHandler.saveArtists();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Removes selected artist(s) from the list of artists.
	 */
	private void removeArtists()
	{
		int[] selected = artistList.getSelectedIndices();
		if(selected.length > 0)
		{
			DButtonDialog buttonDialog = new DButtonDialog(getSettings());;
			String[] buttonIDs = {CommonValues.YES, CommonValues.NO};
			String result = buttonDialog.openButtonDialog(getFrame(), ArtistValues.SURE_TITLE, ArtistValues.DELETE_ARTIST_MESSAGES, buttonIDs);
			if(result != null && result.equals(CommonValues.YES))
			{
				artistHandler.deleteArtists(selected);
				updateArtistList();
				artistHandler.saveArtists();
				
			}//IF
			
		}//IF
		
	}//METHOD
	
	/**
	 * Starts the downloading process.
	 */
	private void startDownload()
	{
		if(getDmfHandler().isLoaded() && isLoggedIn())
		{
			if(artists.size() > 0)
			{
				progressInfoDialog.setCancelled(false);
				getFrame().setProcessRunning(true);
				progressInfoDialog.startProgressDialog(getFrame(), this.getTitle(CommonValues.DOWNLOADING));
				(new DSwingWorker(this, ArtistValues.CHECK_ALL)).execute();
				
			}//IF
			else
			{
				progressInfoDialog.setCancelled(false);
				getFrame().setProcessRunning(true);
				progressInfoDialog.startProgressDialog(getFrame(), this.getTitle(CommonValues.DOWNLOADING));
				(new DSwingWorker(this, ArtistValues.DOWNLOAD_SINGLE)).execute();
				
			}//ELSE
			
		}//IF
		
	}//METHOD
	
	/**
	 * Starts process of downloading media from selected artists.
	 */
	private void downloadArtistMedia()
	{
		progressInfoDialog.appendLog(getTitle(), false);
		getIdStrings();
		for(int i = 0; !progressInfoDialog.isCancelled() && i < artists.size(); i++)
		{
			downloadPages(progressInfoDialog, artists.get(i), getPages(progressInfoDialog, artists.get(i), checkAllPages, getSettings().getSaveJournals()));
			
		}//FOR
		progressInfoDialog.appendLog(getSettings().getLanguageText(CommonValues.FINISHED), true);
		
	}//METHOD
	
	/**
	 * Starts process of downloading media from a given page URL
	 */
	private void downloadSingleMedia()
	{
		progressInfoDialog.appendLog(getTitle(), false);
		getIdStrings();
		downloadSinglePage(progressInfoDialog, pageURL, getDirectory());
		progressInfoDialog.appendLog(getSettings().getLanguageText(CommonValues.FINISHED), true);
		
	}//METHOD
	
	/**
	 * Deals with an info process being finished, closing the info progress dialog and allowing input.
	 */
	private void infoProcessFinished()
	{
		progressInfoDialog.setCancelled(false);
		progressInfoDialog.showFinalLog(getFrame(), getTitle(CommonValues.DOWNLOADING), getDirectory());
		getFrame().setProcessRunning(false);
		
	}//METHOD
	
	@Override
	public void enableAll()
	{
		settingsBar.enableAll();
		newButton.setEnabled(true);
		allButton.setEnabled(true);
		singleButton.setEnabled(true);
		artistList.setEnabled(true);
		addButton.setEnabled(true);
		removeButton.setEnabled(true);
		journalCheck.setEnabled(true);
		
	}//METHOD

	@Override
	public void disableAll()
	{
		settingsBar.disableAll();
		newButton.setEnabled(false);
		allButton.setEnabled(false);
		singleButton.setEnabled(false);
		artistList.setEnabled(false);
		addButton.setEnabled(false);
		removeButton.setEnabled(false);
		journalCheck.setEnabled(false);
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case CommonValues.OPEN:
				selectDirectory();
				break;
			case CommonValues.ADD:
				addArtist();
				break;
			case CommonValues.REMOVE:
				removeArtists();
				break;
			case CommonValues.RESTART_PROGRAM:
				Start.startGUI(getSettings(), getDmfHandler());
				dispose();
				break;
			case CommonValues.EXIT:
				dispose();
				break;
			case ArtistValues.CHECK_NEW:
				checkPages(false);
				break;
			case ArtistValues.CHECK_ALL:
				checkPages(true);
				break;
			case ArtistValues.DOWNLOAD_SINGLE:
				downloadSingle();
				break;
			case ArtistValues.SAVE_JOURNALS:
				getSettings().setSaveJournals(BooleanInt.getBoolean(value));
				break;
			
		}//SWITCH
		
	}//METHOD
	
	@Override
	public void setPage(String url)
	{
		CookieManager cookies = getDownloader().getClient().getCookieManager();
		getDownloader().getClient().getCurrentWindow().getJobManager().removeAllJobs();
		getDownloader().getClient().close();
		System.gc();
		setNewClient();
		getDownloader().getClient().setCookieManager(cookies);
		getDownloader().getPage(url);
		
	}//METHOD
	
	@Override
	public void run(final String id)
	{
		switch(id)
		{
			case ArtistValues.DOWNLOAD_SINGLE:
				downloadSingleMedia();
				break;
			case ArtistValues.CHECK_ALL:
				downloadArtistMedia();
				break;
			
		}//SWITCH
		
	}//METHOD

	@Override
	public void done(final String id)
	{
		switch(id)
		{
			case ArtistValues.CHECK_ALL:
			case ArtistValues.DOWNLOAD_SINGLE:
				infoProcessFinished();
				break;
				
		}//SWITCH
		
	}//METHOD
	
	@Override
	public void loadingDMFsDone()
	{
		startDownload();
		
	}//METHOD
	
	@Override
	public void sortingDMFsDone(){}
	
	@Override
	public void filteringDMFsDone(){}
	
}//CLASS
