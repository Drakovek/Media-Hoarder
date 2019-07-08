package drakovek.hoarder.gui.artist;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.Start;
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
import drakovek.hoarder.gui.swing.compound.DFileChooser;
import drakovek.hoarder.gui.swing.compound.DTextDialog;
import drakovek.hoarder.processing.StringMethods;

/**
 * Creates GUI for downloading files from artist hosting websites.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public abstract class ArtistHostingGUI extends FrameGUI
{
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
	 * Object for handling the author/creator list
	 * 
	 * @since 2.0
	 */
	private AuthorHandler authorHandler;
	
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
	 * Button to add creators from the creator list.
	 * 
	 * @since 2.0
	 */
	private DButton addButton;
	
	/**
	 * Button to remove creators from the creator list.
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
	 * Checkbox to determine whether to save favorites pages.
	 * 
	 * @since 2.0
	 */
	private DCheckBox favoriteCheck;
	
	/**
	 * List of creators to download from
	 * 
	 * @since 2.0
	 */
	private DList creatorList;
	
	/**
	 * Initializes the ArtistHostingGUI
	 * 
	 * @param settings Program Settings
	 * @param subtitleID ID for the sub-title of the frame
	 * @param openID Language ID for the "open" menu item
	 * @since 2.0
	 */
	public ArtistHostingGUI(DSettings settings, final String subtitleID, final String openID)
	{
		super(settings, subtitleID);
		fileChooser = new DFileChooser(settings);
		authorHandler = new AuthorHandler(settings, subtitleID);
		
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
		
		creatorList = new DList(this, true, DefaultLanguage.CREATORS);
		DScrollPane creatorScroll = new DScrollPane(settings, creatorList);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		centerPanel.add(actionPanel);
		centerPanel.add(creatorScroll);
		
		//LABEL PANEL
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		labelPanel.add(new DLabel(this, null, DefaultLanguage.ACTIONS));
		labelPanel.add(new DLabel(this, creatorList, DefaultLanguage.CREATORS));
		
		//ADD PANEL
		JPanel addPanel = new JPanel();
		addPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		addButton = new DButton(this, DefaultLanguage.ADD);
		removeButton = new DButton(this, DefaultLanguage.REMOVE);
		addPanel.add(addButton);
		addPanel.add(removeButton);
		
		//OPTIONS PANEL
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		journalCheck = new DCheckBox(this, false, DefaultLanguage.SAVE_JOURNALS);
		favoriteCheck = new DCheckBox(this, false, DefaultLanguage.SAVE_FAVORITES);
		optionsPanel.add(journalCheck);
		optionsPanel.add(favoriteCheck);
		
		//BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		bottomPanel.add(optionsPanel);
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
		getFrame().pack();
		getFrame().setMinimumSize(getFrame().getSize());
		getFrame().setLocationRelativeTo(null);
		getFrame().setVisible(true);
		creatorList.setListData(StringMethods.arrayListToArray(authorHandler.getAuthors()));
		
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
	
	@Override
	public void enableAll()
	{
		settingsBar.enableAll();
		newButton.setEnabled(true);
		allButton.setEnabled(true);
		singleButton.setEnabled(true);
		creatorList.setEnabled(true);
		addButton.setEnabled(true);
		removeButton.setEnabled(true);
		journalCheck.setEnabled(true);
		favoriteCheck.setEnabled(true);
		
	}//METHOD

	@Override
	public void disableAll()
	{
		settingsBar.disableAll();
		newButton.setEnabled(false);
		allButton.setEnabled(false);
		singleButton.setEnabled(false);
		creatorList.setEnabled(false);
		addButton.setEnabled(false);
		removeButton.setEnabled(false);
		journalCheck.setEnabled(false);
		favoriteCheck.setEnabled(false);
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.OPEN:
			{
				File file = fileChooser.getFileOpen(getFrame(), getDirectory());
			
				if(file != null && file.isDirectory())
				{
					setDirectory(file);
					settingsBar.setLabel(getDirectory());
				
				}//IF
				
				break;
				
			}//CASE
			case DefaultLanguage.ADD:
			{
				DTextDialog textDialog = new DTextDialog(getSettings());
				String author = textDialog.openTextDialog(getFrame(), DefaultLanguage.ADD_CREATOR, DefaultLanguage.NAME_OF_CREATOR, null);
				
				if(author != null)
				{
					authorHandler.addAuthor(author);
					creatorList.setListData(StringMethods.arrayListToArray(authorHandler.getAuthors()));
					authorHandler.saveAuthors();
					
				}//IF
				
				break;
				
			}//CASE
			case DefaultLanguage.RESTART_PROGRAM:
			{
				Start.startGUI(getSettings());
				
			}//CASE
			case DefaultLanguage.EXIT:
			{
				dispose();
				break;
				
			}//CASE
			
		}//SWITCH
		
	}//METHOD
	
}//CLASS
