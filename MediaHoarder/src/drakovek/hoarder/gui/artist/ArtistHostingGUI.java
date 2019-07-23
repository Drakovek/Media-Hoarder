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

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.Start;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
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
import drakovek.hoarder.gui.swing.compound.DFileChooser;
import drakovek.hoarder.gui.swing.compound.DTextDialog;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.web.ClientMethods;
import drakovek.hoarder.web.Downloader;

/**
 * Creates GUI for downloading files from artist hosting websites.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public abstract class ArtistHostingGUI extends FrameGUI implements ClientMethods, LoginMethods
{
	/**
	 * List of currently selected artists
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> artists;
	
	/**
	 * Settings Bar for the GUI
	 * 
	 * @since 2.0
	 */
	private SettingsBarGUI settingsBar;
	
	/**
	 * DFileChooser for the GUI to choose the directory in which to save files.
	 * 
	 * @since 2.0
	 */
	private DFileChooser fileChooser;
	
	/**
	 * GUI for logging into the artist hosting website
	 * 
	 * @since 2.0
	 */
	private LoginGUI loginGUI;
	
	/**
	 * Downloader object for downloading information.
	 * 
	 * @since 2.0
	 */
	private Downloader downloader;
	
	/**
	 * Object for handling the artist list
	 * 
	 * @since 2.0
	 */
	private ArtistHandler artistHandler;
	
	/**
	 * Button to check new pages from which to save info
	 * 
	 * @since 2.0
	 */
	private DButton newButton;
	
	/**
	 * Button to check all pages from which to save info
	 * 
	 * @since 2.0
	 */
	private DButton allButton;
	
	/**
	 * Button to save info from a single page
	 * 
	 * @since 2.0
	 */
	private DButton singleButton;
	
	/**
	 * Button to add artists from the artist list.
	 * 
	 * @since 2.0
	 */
	private DButton addButton;
	
	/**
	 * Button to remove artists from the artist list.
	 * 
	 * @since 2.0
	 */
	private DButton removeButton;
	
	/**
	 * Checkbox to determine whether to save journal pages.
	 * 
	 * @since 2.0
	 */
	private DCheckBox journalCheck;
	
	/**
	 * List of artists to download from
	 * 
	 * @since 2.0
	 */
	private DList artistList;
	
	/**
	 * Initializes the ArtistHostingGUI
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHandler
	 * @param loginGUI GUI for logging into the artist hosting website
	 * @param subtitleID ID for the sub-title of the frame
	 * @param openID Language ID for the "open" menu item
	 * @since 2.0
	 */
	public ArtistHostingGUI(DSettings settings, DmfHandler dmfHandler, LoginGUI loginGUI, final String subtitleID, final String openID)
	{
		super(settings, dmfHandler, subtitleID);
		fileChooser = new DFileChooser(settings);
		artistHandler = new ArtistHandler(settings, subtitleID);
		downloader = new Downloader(this);
		this.loginGUI = loginGUI;
		this.loginGUI.setLoginMethods(this);
		
		//MENUS
		JMenuBar menubar = new JMenuBar();
		DMenu fileMenu = new DMenu(this, DefaultLanguage.FILE);
		menubar.add(fileMenu);
		DMenuItem openItem = new DMenuItem(this, DefaultLanguage.OPEN);
		openItem.setTextID(openID);
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		fileMenu.add(new DMenuItem(this, DefaultLanguage.RESTART_PROGRAM));
		fileMenu.add(new DMenuItem(this, DefaultLanguage.EXIT));
		
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
		newButton = new DButton(this, DefaultLanguage.CHECK_NEW);
		newButton.setFontLarge();
		allButton = new DButton(this, DefaultLanguage.CHECK_ALL);
		allButton.setFontLarge();
		singleButton = new DButton(this, DefaultLanguage.DOWNLOAD_SINGLE);
		singleButton.setFontLarge();
		actionPanel.add(newButton);
		actionPanel.add(allButton);
		actionPanel.add(singleButton);
		
		artistList = new DList(this, true, DefaultLanguage.ARTISTS);
		DScrollPane artistScroll = new DScrollPane(settings, artistList);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		centerPanel.add(actionPanel);
		centerPanel.add(artistScroll);
		
		//LABEL PANEL
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		labelPanel.add(new DLabel(this, null, DefaultLanguage.ACTIONS));
		labelPanel.add(new DLabel(this, artistList, DefaultLanguage.ARTISTS));
		
		//ADD PANEL
		JPanel addPanel = new JPanel();
		addPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		addButton = new DButton(this, DefaultLanguage.ADD);
		removeButton = new DButton(this, DefaultLanguage.REMOVE);
		addPanel.add(addButton);
		addPanel.add(removeButton);
		
		
		
		//BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		journalCheck = new DCheckBox(this, false, DefaultLanguage.SAVE_JOURNALS);
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
	 * @since 2.0
	 */
	public abstract void setDirectory(final File directory);
	
	/**
	 * Returns the directory from which to save DMFs
	 * 
	 * @return Directory
	 * @since 2.0
	 */
	public abstract File getDirectory();
	
	/**
	 * Displays the current list of artists.
	 * 
	 * @since 2.0
	 */
	private void updateArtistList()
	{
		ArrayList<String> currentArtists = new ArrayList<>();
		currentArtists.add(getSettings().getLanguageText(DefaultLanguage.ALL_ARTISTS));
		currentArtists.addAll(artistHandler.getArtists());
		artistList.setListData(StringMethods.arrayListToArray(currentArtists));
		
	}//METHOD
	
	/**
	 * Starts the process to check pages for one or multiple artists.
	 * 
	 * @param checkAll Whether to check all of the pages, if false only checks the new pages.
	 * @since 2.0
	 */
	private void checkPages(final boolean checkAll)
	{	
		boolean ready = true;
		
		if(getDirectory() == null || !getDirectory().isDirectory())
		{
			ready = false;
			DButtonDialog buttonDialog = new DButtonDialog(getSettings());
			String[] messageIDs = {DefaultLanguage.NO_DIRECTORY_DIALOG};
			String[] buttonIDs = {DefaultLanguage.OK};
			buttonDialog.openButtonDialog(getFrame(), DefaultLanguage.NO_DIRECTORY_DIALOG, messageIDs, buttonIDs);
		
		}//IF
		
		//CHECK IF ARTISTS ARE SELECTED
		if(ready)
		{
			artists = artistHandler.getArtists(artistList.getSelectedIndices());
			if(artists.size() == 0)
			{
				ready = false;
				DButtonDialog buttonDialog = new DButtonDialog(getSettings());
				String[] messageIDs = {DefaultLanguage.NO_ARTISTS};
				String[] buttonIDs = {DefaultLanguage.OK};
				buttonDialog.openButtonDialog(getFrame(), DefaultLanguage.NO_ARTISTS, messageIDs, buttonIDs);
			
			}//IF
			
		}//IF
		
		//CHECK IF IN THE CORRECT DIRECTORY
		if(ready)
		{
			boolean missingFolders = false;
			for(int i = 0; i < artistHandler.getArtists().size(); i++)
			{
				File artistFolder = new File(getDirectory(), artistHandler.getArtists().get(i));
				if(!artistFolder.isDirectory())
				{
					missingFolders = true;
					break;
					
				}//IF
				
			}//FOR
			
			if(missingFolders)
			{
				DButtonDialog buttonDialog = new DButtonDialog(getSettings());
				String[] buttonIDs = {DefaultLanguage.YES, DefaultLanguage.NO};
				String result = buttonDialog.openButtonDialog(getFrame(), DefaultLanguage.SURE_TITLE, DefaultLanguage.WRONG_FOLDER_MESSAGES, buttonIDs);
			
				if(result.equals(DefaultLanguage.NO))
				{
					ready = false;
					
				}//IF
				
			}//IF
			
		}//IF
		
		//INITIALIZE DOWNLOAD PROCESS
		if(ready)
		{
			ready = initializeDownloadProcess();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Starts the process of downloading a single media file.
	 * 
	 * @since 2.0
	 */
	private void downloadSingle()
	{
		artists = new ArrayList<>();
		boolean ready = true;
		
		if(getDirectory() == null || !getDirectory().isDirectory())
		{
			ready = false;
			DButtonDialog buttonDialog = new DButtonDialog(getSettings());
			String[] messageIDs = {DefaultLanguage.NO_DIRECTORY_DIALOG};
			String[] buttonIDs = {DefaultLanguage.OK};
			buttonDialog.openButtonDialog(getFrame(), DefaultLanguage.NO_DIRECTORY_DIALOG, messageIDs, buttonIDs);
		
		}//IF
		
		if(ready)
		{
			ready = initializeDownloadProcess();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Sets up the object for starting a download process
	 * 
	 * @return Whether to continue the downloading process
	 * @since 2.0
	 */
	private boolean initializeDownloadProcess()
	{
		boolean ready = true;
		
		//LOGIN TO WEBSITE
		if(ready && !isLoggedIn())
		{
			loginGUI.openLoginDialog(getFrame());
			ready = isLoggedIn();
			
		}//IF
		
		return ready;
		
	}//METHOD
	
	/**
	 * Returns the object's Downloader object.
	 * 
	 * @return Downloader
	 * @since 2.0
	 */
	public Downloader getDownloader()
	{
		return downloader;
		
	}//METHOD
	
	/**
	 * Returns the directory for holding image captchas.
	 * 
	 * @return Captcha Folder
	 * @since 2.0
	 */
	public File getCaptchaFolder()
	{
		return loginGUI.getCaptchaFolder();
		
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

	/**
	 * Selects the main directory for this object.
	 * 
	 * @since 2.0
	 */
	private void selectDirectory()
	{
		File file = fileChooser.getFileOpen(getFrame(), getDirectory());
		
		if(file != null && file.isDirectory())
		{
			setDirectory(file);
			settingsBar.setLabel(getDirectory());
		
		}//IF
		
	}//METHOD
	
	/**
	 * Adds a given artist to the list of artists.
	 * 
	 * @since 2.0
	 */
	private void addArtist()
	{
		DTextDialog textDialog = new DTextDialog(getSettings());
		String artist = textDialog.openTextDialog(getFrame(), DefaultLanguage.ADD_ARTIST, DefaultLanguage.NAME_OF_ARTIST, null);
		
		if(artist != null)
		{
			artistHandler.addArtist(artist);
			updateArtistList();
			artistHandler.saveArtists();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Removes selected artist(s) from the list of artists.
	 * 
	 * @since 2.0
	 */
	private void removeArtists()
	{
		int[] selected = artistList.getSelectedIndices();
		if(selected.length > 0)
		{
			DButtonDialog buttonDialog = new DButtonDialog(getSettings());;
			String[] buttonIDs = {DefaultLanguage.YES, DefaultLanguage.NO};
			String result = buttonDialog.openButtonDialog(getFrame(), DefaultLanguage.SURE_TITLE, DefaultLanguage.DELETE_ARTIST_MESSAGES, buttonIDs);
			if(result != null && result.equals(DefaultLanguage.YES))
			{
				artistHandler.deleteArtists(selected);
				updateArtistList();
				artistHandler.saveArtists();
				
			}//IF
			
		}//IF
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.OPEN:
				selectDirectory();
				break;
			case DefaultLanguage.ADD:
				addArtist();
				break;
			case DefaultLanguage.REMOVE:
				removeArtists();
				break;
			case DefaultLanguage.RESTART_PROGRAM:
				Start.startGUI(getSettings(), getDmfHandler());
			case DefaultLanguage.EXIT:
				dispose();
				break;
			case DefaultLanguage.CHECK_NEW:
				checkPages(false);
				break;
			case DefaultLanguage.CHECK_ALL:
				checkPages(true);
				break;
			case DefaultLanguage.DOWNLOAD_SINGLE:
				downloadSingle();
				break;
			
		}//SWITCH
		
	}//METHOD
	
	@Override
	public void setPage(String url)
	{
		CookieManager cookies = getDownloader().getClient().getCookieManager();
		setNewClient();
		getDownloader().getClient().setCookieManager(cookies);
		getDownloader().setPage(url);
		
	}//METHOD
	
}//CLASS
