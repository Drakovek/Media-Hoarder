package drakovek.hoarder.file.language;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.processing.ParseINI;

/**
 * Class for holding language variable names and their default English values.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DefaultLanguage 
{
	//LANGUAGE
	
	/**
	 * Header text for all language files.
	 * 
	 * @since 2.0
	 */
	public static final String LANGUAGE_HEADER = "[LANGUAGE FILE]"; //$NON-NLS-1$
	
	/**
	 * Variable for the language of a particular language file.
	 * 
	 * @since 2.0
	 */
	public static final String LANGUAGE_VARIABLE = "language_value"; //$NON-NLS-1$
	
	//COMMON
	
	/**
	 * Language variable for the default title for Frames/Dialogs
	 * 
	 * @since 2.0
	 */
	public static final String TITLE_VALUE = "title_value"; //$NON-NLS-1$
	
	/**
	 * Language variable for "OK"
	 * 
	 * @since 2.0
	 */
	public static final String OK = "ok"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Add"
	 * 
	 * @since 2.0
	 */
	public static final String ADD = "add"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Remove"
	 * 
	 * @since 2.0
	 */
	public static final String REMOVE = "remove"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Yes"
	 * 
	 * @since 2.0
	 */
	public static final String YES = "yes"; //$NON-NLS-1$
	
	/**
	 * Language variable for "No"
	 * 
	 * @since 2.0
	 */
	public static final String NO = "no"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Cancel"
	 * 
	 * @since 2.0
	 */
	public static final String CANCEL = "cancel"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Close"
	 */
	public static final String CLOSE = "close"; //$NON-NLS-1$
	
	/**
	 * Language variable for "File"
	 * 
	 * @since 2.0
	 */
	public static final String FILE = "file"; //$NON-NLS-1$
	
	/**
	 * Language variable for "View"
	 * 
	 * @since 2.0
	 */
	public static final String VIEW = "view"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Open"
	 * 
	 * @since 2.0
	 */
	public static final String OPEN = "open"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Exit"
	 * 
	 * @since 2.0
	 */
	public static final String EXIT = "exit"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Save"
	 * 
	 * @since 2.0
	 */
	public static final String SAVE = "save"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Restart Program"
	 * 
	 * @since 2.0
	 */
	public static final String RESTART_PROGRAM = "restart_program"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Downloading"
	 * 
	 * @since 2.0
	 */
	public static final String DOWNLOADING = "downloading"; //$NON-NLS-1$
	
	//FRAME
	
	/**
	 * Language variable for title of the "Process Running" error message.
	 * 
	 * @since 2.0
	 */
	public static final String PROCESS_RUNNING = "process_running"; //$NON-NLS-1$
	
	/**
	 * Language variables for message of the "Process Running" error message.
	 * 
	 * @since 2.0
	 */
	public static final String[] PROCESS_RUNNING_MESSAGES = {"cancel_process_message1", "cancel_process_message2"};  //$NON-NLS-1$//$NON-NLS-2$
	
	//SETTINGS
	
	/**
	 * Language variable for settings button
	 * 
	 * @since 2.0
	 */
	public static final String SETTINGS = "settings"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Apply" button in the settings GUI
	 * 
	 * @since 2.0
	 */
	public static final String APPLY = "apply"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Language" label in the settings GUI
	 * 
	 * @since 2.0
	 */
	public static final String LANGUAGE = "language"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "DMF Directories" label in the settings GUI
	 * 
	 * @since 2.0
	 */
	public static final String DMF_DIRECTORIES = "dmf_directories"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Theme" label in the settings GUI
	 * 
	 * @since 2.0
	 */
	public static final String THEME = "theme"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Font" label in the settings GUI
	 * 
	 * @since 2.0
	 */
	public static final String FONT = "font"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Preview" label in the settings GUI
	 * 
	 * @since 2.0
	 */
	public static final String PREVIEW = "preview"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Bold" check box label in the settings GUI
	 * 
	 * @since 2.0
	 */
	public static final String FONT_BOLD = "font_bold"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Anti-Aliasing" check box label in the settings GUI
	 * 
	 * @since 2.0
	 */
	public static final String FONT_AA = "font_aa"; //$NON-NLS-1$
	
	/**
	 * Language variable for the font "Size" label in the settings GUI
	 * 
	 * @since 2.0
	 */
	public static final String FONT_SIZE = "font_size"; //$NON-NLS-1$
	
	/**
	 * Language variable for the text used for the font preview in the settings GUI
	 * 
	 * @since 2.0
	 */
	public static final String FONT_PREVIEW = "font_preview"; //$NON-NLS-1$
	
	/**
	 * Language variable for the text used to show that no directory is selected in the settings bar.
	 * 
	 * @since 2.0
	 */
	public static final String NO_DIRECTORY = "no_directory"; //$NON-NLS-1$
	
	/**
	 * Language variable for the text used to show that DMF Directories have been properly loaded
	 * 
	 * @since 2.0
	 */
	public static final String DIRECTORIES_LOADED = "directories_loaded"; //$NON-NLS-1$
	
	/**
	 * Language variable for the title of the dialog that shows that no DMF directories have been specified.
	 * 
	 * @since 2.0
	 */
	public static final String NO_DIRECTORIES_TITLE = "no_directories_title"; //$NON-NLS-1$
	
	/**
	 * Language variables for the messages of the dialog that shows that no DMF directories have been specified.
	 * 
	 * @since 2.0
	 */
	public static final String[] NO_DIRECTORIES_MESSAGES = {"no_directories_message_1", "no_directories_message_2"}; //$NON-NLS-1$ //$NON-NLS-2$
	
	/**
	 * Language variable for the title of the progress dialog for loading DMFs
	 * 
	 * @since 2.0
	 */
	public static final String LOADING_DMFS_TITLE = "loading_dmfs_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the DMF loading progress dialog when searching for folders containing DMFs
	 * 
	 * @since 2.0
	 */
	public static final String GETTING_FOLDERS = "getting_folders"; //$NON-NLS-1$
	
	/**
	 * Language variable for the DMF loading progress dialog when loading DMFs
	 * 
	 * @since 2.0
	 */
	public static final String LOADING_DMFS = "loading_dmfs"; //$NON-NLS-1$
	
	/**
	 * Language variable for the DMF loading progress dialog when loading a DMF index file
	 * 
	 * @since 2.0
	 */
	public static final String LOADING_INDEX = "loading_index"; //$NON-NLS-1$
	
	/**
	 * Language variable for the DMF loading progress dialog when saving a DMF index file
	 * 
	 * @since 2.0
	 */
	public static final String SAVING_INDEX = "saving_index"; //$NON-NLS-1$
	
	//MODES
	
	/**
	 * Language variable for the "Back" button for mode GUIs.
	 * 
	 * @since 2.0
	 */
	public static final String MODE_BACK = "mode_back"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Start" button for mode GUIs.
	 * 
	 * @since 2.0
	 */
	public static final String MODE_START = "mode_start"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Download" mode button.
	 * 
	 * @since 2.0
	 */
	public static final String DOWNLOAD_MODE = "download_mode"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Manage" mode button.
	 * 
	 * @since 2.0
	 */
	public static final String MANAGE_MODE = "manage_mode"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "View" mode button
	 * 
	 * @since 2.0
	 */
	public static final String VIEW_MODE = "view_mode"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "DMF Sequencing" mode button
	 * 
	 * @since 2.0
	 */
	public static final String SEQUENCE_MODE = "sequence_mode"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Find Errors" mode button
	 * 
	 * @since 2.0
	 */
	public static final String ERROR_MODE = "error_mode"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Reformat" mode button
	 * 
	 * @since 2.0
	 */
	public static final String REFORMAT_MODE = "reformat_mode"; //$NON-NLS-1$
	
	//DOWNLOAD
	
	/**
	 * Language variable for the "DeviantArt" mode button
	 * 
	 * @since 2.0
	 */
	public static final String DEVIANTART_MODE = "deviantart"; //$NON-NLS-1$
	
	/**
	 * Language variable for the progress info dialog title when loading data from DeviantArt.com
	 * 
	 * @since 2.0
	 */
	public static final String DEVIANTART_PROGRESS_TITLE = "deviantart_progress_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Fur Affinity" mode button
	 * 
	 * @since 2.0
	 */
	public static final String FUR_AFFINITY_MODE = "fur_affinity"; //$NON-NLS-1$
	
	/**
	 * Language variable for the progress info dialog title when loading data from FurAffinity.net
	 * 
	 * @since 2.0
	 */
	public static final String FUR_AFFINITY_PROGRESS_TITLE = "fur_affinity_progress_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Inkbunny" mode button
	 * 
	 * @since 2.0
	 */
	public static final String INKBUNNY_MODE = "inkbunny"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Transfur" mode button
	 * 
	 * @since 2.0
	 */
	public static final String TRANSFUR_MODE = "transfur"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Page:" in the progress info dialog when indicating the program is loading web data from a certain page
	 * 
	 * @since 2.0
	 */
	public static final String PAGE = "page"; //$NON-NLS-1$
	
	/**
	 * Language variable for indicating the program is getting page URLs in the progress info dialog
	 * 
	 * @since 2.0
	 */
	public static final String GETTING_PAGE_URLS = "getting_page_urls"; //$NON-NLS-1$
	
	/**
	 * Language variable for indicating the program is getting gallery pages in the progress info dialog
	 * 
	 * @since 2.0
	 */
	public static final String GETTING_GALLERY_PAGES = "getting_gallery_pages"; //$NON-NLS-1$
	
	/**
	 * Language variable for indicating the program is getting scrap pages in the progress info dialog
	 * 
	 * @since 2.0
	 */
	public static final String GETTING_SCRAP_PAGES = "getting_scrap_pages"; //$NON-NLS-1$
	
	/**
	 * Language variable for indicating the program is getting journal pages in the progress info dialog
	 * 
	 * @since 2.0
	 */
	public static final String GETTING_JOURNAL_PAGES = "getting_journal_pages"; //$NON-NLS-1$
	
	/**
	 * Language variable for indicating the program is loading a page in the progress info dialog
	 * 
	 * @since 2.0
	 */
	public static final String LOADING_PAGE = "loading_page"; //$NON-NLS-1$
	
	//FILE CHOOSER
	
	/**
	 * Language variable for the file chooser dialog title when opening a file.
	 * 
	 * @since 2.0
	 */
	public static final String OPEN_TITLE = "open_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the file chooser dialog title when saving a file.
	 * 
	 * @since 2.0
	 */
	public static final String SAVE_TITLE = "save_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Roots" label in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String ROOTS = "roots"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Parent" button in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String PARENT = "parent"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "New Directory" button in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String NEW_DIRECTORY = "new_directory"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "File Name:" label in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String FILE_NAME = "file_name"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "File Type:" label in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String FILE_TYPE = "file_type"; //$NON-NLS-1$
	
	/**
	 * Language variable for showing that only directories can be opened in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String DIRECTORIES_ONLY = "directories_only"; //$NON-NLS-1$
	
	/**
	 * Language variable for showing that the user can save to any file type in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String ALL_FILES = "all_files"; //$NON-NLS-1$
	
	/**
	 * Language variable for showing all files of any of the listed extensions in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String ALL_ALLOWED_EXTENSIONS = "all_allowed_extensions"; //$NON-NLS-1$
	
	/**
	 * Language variable for the title of dialog showing user that a given file already exists.
	 * 
	 * @since 2.0
	 */
	public static final String FILE_EXISTS = "file_exists"; //$NON-NLS-1$
	
	/**
	 * Language variable for the messages of dialog showing user that a given file already exists.
	 * 
	 * @since 2.0
	 */
	public static final String[] FILE_EXISTS_MESSAGES = {"file_exists_message_1", "file_exists_message_2"}; //$NON-NLS-1$ //$NON-NLS-2$
	
	//ARTIST HOSTING
	
	/**
	 * Language variable for the "Actions" label from the artist hosting GUI
	 * 
	 * @since 2.0
	 */
	public static final String ACTIONS = "actions"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Artists" label from the artist hosting GUI
	 * 
	 * @since 2.0
	 */
	public static final String ARTISTS = "artists"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Check New Pages" button from the artist hosting GUI
	 * 
	 * @since 2.0
	 */
	public static final String CHECK_NEW = "check_new"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Check All Pages" button from the artist hosting GUI
	 * 
	 * @since 2.0
	 */
	public static final String CHECK_ALL = "check_all"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Download Single" button from the artist hosting GUI
	 * 
	 * @since 2.0
	 */
	public static final String DOWNLOAD_SINGLE = "download_single"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Save Journals" check box from the artist hosting GUI
	 * 
	 * @since 2.0
	 */
	public static final String SAVE_JOURNALS = "save_journals"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Add Artist" title for adding artists in the artist hosting GUI
	 * 
	 * @since 2.0
	 */
	public static final String ADD_ARTIST = "add_artist"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "All Artists" item in the artist list of the artist hosting GUI
	 * 
	 * @since 2.0
	 */
	public static final String ALL_ARTISTS = "all_artists"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Name of Artist" message for adding artists in the artist hosting GUI
	 * 
	 * @since 2.0
	 */
	public static final String[] NAME_OF_ARTIST = {"name_of_artist"}; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Are you sure?" title for dialogs.
	 * 
	 * @since 2.0
	 */
	public static final String SURE_TITLE = "sure_title"; //$NON-NLS-1$
	
	/**
	 * Language variables for message shown when removing artists in the Artist Hosting GUI
	 * 
	 * @since 2.0
	 */
	public static final String[] DELETE_ARTIST_MESSAGES = {"delete_artist_message_1", "delete_artist_message_2"};  //$NON-NLS-1$//$NON-NLS-2$
	
	/**
	 * Language variables for message shown if the user has a potentially incorrect folder selected.
	 * 
	 * @since 2.0
	 */
	public static final String[] WRONG_FOLDER_MESSAGES = {"wrong_folder_message_1", "wrong_folder_message_2", "wrong_folder_message_3"};  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
	
	/**
	 * Language variable for the dialog in the artist hosting GUI for showing that no artists are selected.
	 * 
	 * @since 2.0
	 */
	public static final String NO_ARTISTS = "no_artists"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Username" label in the login GUI.
	 * 
	 * @since 2.0
	 */
	public static final String USERNAME = "username"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Password" label in the login GUI.
	 * 
	 * @since 2.0
	 */
	public static final String PASSWORD = "password"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Login" button in the login GUI.
	 * 
	 * @since 2.0
	 */
	public static final String LOGIN = "login"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Captcha" label in the login GUI
	 * 
	 * @since 2.0
	 */
	public static final String CAPTCHA = "captcha"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Refresh Captcha" button in the login GUI
	 * 
	 * @since 2.0
	 */
	public static final String REFRESH_CAPTCHA = "refresh_captcha"; //$NON-NLS-1$
	
	/**
	 * Language variable for the title of the progress dialog when loading image captcha
	 * 
	 * @since 2.0
	 */
	public static final String LOAD_CAPTCHA_TITLE = "load_captcha_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the process label for the dialog when loading image captcha
	 * 
	 * @since 2.0
	 */
	public static final String LOAD_CAPTCHA = "load_captcha"; //$NON-NLS-1$
	
	/**
	 * Language variable for title of the "Attempt Login" progress dialog.
	 * 
	 * @since 2.0
	 */
	public static final String ATTEMPT_LOGIN = "attempt_login"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "login failed" message if attempted login fails.
	 * 
	 * @since 2.0
	 */
	public static final String LOGIN_FAILED = "login_failed"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Canceling" label when canceling a process
	 * 
	 * @since 2.0
	 */
	public static final String CANCELING = "canceling"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Running" label default when a process is running
	 * 
	 * @since 2.0
	 */
	public static final String RUNNING = "running"; //$NON-NLS-1$
	
	/**
	 * Language variable for dialogs showing that no directory is selected
	 * 
	 * @since 2.0
	 */
	public static final String NO_DIRECTORY_DIALOG = "no_directory_dialog"; //$NON-NLS-1$
	
	/**
	 * Language variable for title of dialog asking whether to add directory to DMF directories list
	 * 
	 * @since 2.0
	 */
	public static final String ADD_DIRECTORY_TITLE = "add_directory_title"; //$NON-NLS-1$
	
	/**
	 * Language variables for messages of dialog asking whether to add directory to DMF directories list
	 * 
	 * @since 2.0
	 */
	public static final String[] ADD_DIRECTORY_MESSAGES = {"add_directory_message_1", "add_directory_message_2"};  //$NON-NLS-1$//$NON-NLS-2$
	
	/**
	 * Language variable for a dialog message asking whether to continue.
	 * 
	 * @since 2.0
	 */
	public static final String CONTINUE_MESSAGE = "continue_message"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Downloaded" message
	 * 
	 * @since 2.0
	 */
	public static final String DOWNLOADED = "downloaded"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Finished" message
	 * 
	 * @since 2.0
	 */
	public static final String FINISHED = "finished"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Download Failed" message
	 * 
	 * @since 2.0
	 */
	public static final String DOWNLOAD_FAILED = "download_failed"; //$NON-NLS-1$
	
	/**
	 * Language variable for "AM" in clock time formatting
	 * 
	 * @since 2.0
	 */
	public static final String AM = "am"; //$NON-NLS-1$
	
	/**
	 * Language variable for "PM" in clock time formatting
	 * 
	 * @since 2.0
	 */
	public static final String PM = "pm"; //$NON-NLS-1$
	
	/**
	 * Language variable for the symbol to use when indicating seconds in a clock time
	 * 
	 * @since 2.0
	 */
	public static final String SECONDS_SUFFIX = "seconds_suffix"; //$NON-NLS-1$
	
	/**
	 * Language variable for the title of the login GUI when logging into DeviantArt.
	 * 
	 * @since 2.0
	 */
	public static final String DEVIANTART_LOGIN = "deviantart_login"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Choose DeviantArt Folder" menu item in the DeviantArt GUI
	 * 
	 * @since 2.0
	 */
	public static final String CHOOSE_DEVIANTART_FOLDER = "choose_deviantart_folder"; //$NON-NLS-1$

	/**
	 * Language variable for the title of the login GUI when logging into Fur Affinity.
	 * 
	 * @since 2.0
	 */
	public static final String FUR_AFFINITY_LOGIN = "fur_affinity_login"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Choose Fur Affinity Folder" menu item in the Fur Affinity GUI
	 * 
	 * @since 2.0
	 */
	public static final String CHOOSE_FUR_AFFINITY_FOLDER = "choose_fur_affinity_folder"; //$NON-NLS-1$
	
	//VIEWER
	
	/**
	 * Language variable for the title of the view browser GUI.
	 * 
	 * @since 2.0
	 */
	public static final String VIEWER_TITLE = "viewer_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "previous" button in the viewer GUI.
	 * 
	 * @since 2.0
	 */
	public static final String PREVIOUS = "previous"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "next" button in the viewer GUI.
	 * 
	 * @since 2.0
	 */
	public static final String NEXT = "next"; //$NON-NLS-1$
	
	/**
	 * Language variable for showing an offset value in the viewer GUI
	 * 
	 * @since 2.0
	 */
	public static final String OFFSET = "offset"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Reload DMFs" menu item in the viewer GUI
	 *
	 * @since 2.0
	 */
	public static final String RELOAD_DMFS = "reload_dmfs"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Reload DMFs Without Indexes" menu item in the viewer GUI
	 * 
	 * @since 2.0
	 */
	public static final String RELOAD_WITHOUT_INDEXES = "reload_without_indexes"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Use Thumbnails" menu item in the viewer GUI.
	 * 
	 * @since 2.0
	 */
	public static final String USE_THUMBNAILS = "use_thumbnails"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Sort" menu in the viewer GUI
	 * 
	 * @since 2.0
	 */
	public static final String SORT = "sort"; //$NON-NLS-1$
	
	/**
	 * Language variable for the radio button menu item in the viewer GUI for sorting by time.
	 * 
	 * @since 2.0
	 */
	public static final String SORT_TIME = "sort_time"; //$NON-NLS-1$
	
	/**
	 * Language variable for the radio button menu item in the viewer GUI for sorting alpha-numerically.
	 * 
	 * @since 2.0
	 */
	public static final String SORT_ALPHA = "sort_alpha"; //$NON-NLS-1$
	
	/**
	 * Language variable for the radio button menu item in the viewer GUI for sorting by rating.
	 * 
	 * @since 2.0
	 */
	public static final String SORT_RATING = "sort_rating"; //$NON-NLS-1$
	
	/**
	 * Language variable for the check box menu item in the viewer GUI for grouping artists when sorting.
	 * 
	 * @since 2.0
	 */
	public static final String GROUP_ARTISTS = "group_artists"; //$NON-NLS-1$
	
	/**
	 * Language variable for the check box menu item in the viewer GUI for grouping sequences when sorting.
	 * 
	 * @since 2.0
	 */
	public static final String GROUP_SEQUENCES = "group_sequences"; //$NON-NLS-1$
	
	/**
	 * Language variable for the check box menu item in the viewer GUI for grouping sections when sorting.
	 * 
	 * @since 2.0
	 */
	public static final String GROUP_SECTIONS = "group_sections"; //$NON-NLS-1$
	
	/**
	 * Language variable for viewer GUI's preview loading process title
	 * 
	 * @since 2.0
	 */
	public static final String LOADING_PREVIEWS_TITLE = "loading_previews_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for viewer GUI's preview loading process label
	 * 
	 * @since 2.0
	 */
	public static final String LOADING_PREVIEWS = "loading_previews"; //$NON-NLS-1$
	
	/**
	 * Language variable for the view GUI's DMF sorting process title
	 * 
	 * @since 2.0
	 */
	public static final String SORTING_DMFS_TITLE = "sorting_dmfs_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the view GUI's DMF sorting process label
	 * 
	 * @since 2.0
	 */
	public static final String SORTING_DMFS = "sorting_dmfs"; //$NON-NLS-1$
	
	/**
	 * Language variable for button to start DMF reformatting process
	 * 
	 * @since 2.0
	 */
	public static final String REFORMAT_DMFS = "reformat_dmfs"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to start file renaming process
	 * 
	 * @since 2.0
	 */
	public static final String RENAME_FILES = "rename_files"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to start sequence data deleting process
	 * 
	 * @since 2.0
	 */
	public static final String DELETE_SEQUENCES = "delete_sequeces"; //$NON-NLS-1$
	
	/**
	 * Language variable for message shown to check if user wishes to reformat DMFs
	 * 
	 * @since 2.0
	 */
	public static final String REFORMAT_MESSAGE = "reformat_message"; //$NON-NLS-1$
	
	/**
	 * Language variable for message shown to check if user wishes to rename files
	 * 
	 * @since 2.0
	 */
	public static final String RENAME_MESSAGE = "rename_message"; //$NON-NLS-1$
	
	/**
	 * Language variable for button to search for missing media in the error finding GUI
	 * 
	 * @since 2.0
	 */
	public static final String MISSING_MEDIA = "missing_media"; //$NON-NLS-1$
	
	/**
	 * Language variable for button to search for unlinked files in the error finding GUI
	 * 
	 * @since 2.0
	 */
	public static final String UNLINKED_FILES = "unlinked_files"; //$NON-NLS-1$
	
	/**
	 * Language variable for button to search for DMFs with identical IDs in the error finding GUI
	 * 
	 * @since 2.0
	 */
	public static final String IDENTICAL_IDS = "identical_ids"; //$NON-NLS-1$
	
	/**
	 * Creates a default English language file for if no such file exists, and returns the default language name.
	 * 
	 * @param languageDirectory Directory to save the default language file within.
	 * @return Name of the language, should be "English (USA)"
	 * @since 2.0
	 */
	public static String getDefaultLanguage(File languageDirectory)
	{
		ArrayList<String> languageFile = new ArrayList<>();
		String languageName = "English (USA)"; //$NON-NLS-1$
		
		//LANGUAGE
		languageFile.add(LANGUAGE_HEADER);
		languageFile.add(ParseINI.getAssignmentString(LANGUAGE_VARIABLE, languageName));
		
		//COMMON
		languageFile.add(new String());
		languageFile.add("[COMMON]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(TITLE_VALUE, "Drak's Digital Hoarder")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(OK, "^OK")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CANCEL, "^Cancel")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CLOSE, "^Close")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ADD, "^Add")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(REMOVE, "^Remove")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(YES, "^Yes")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NO, "^No")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SAVE, "^Save")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(RESTART_PROGRAM, "^Restart Program")); //$NON-NLS-1$

		//COMMON MENU
		languageFile.add(new String());
		languageFile.add("[COMMON MENU]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FILE, "^File")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(VIEW, "^View")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(OPEN, "^Open")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(EXIT, "^Exit")); //$NON-NLS-1$
		
		//COMMON DIALOG
		languageFile.add(new String());
		languageFile.add("[COMMON DIALOG]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DOWNLOADING, "Downloading")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CANCELING, "Canceling...")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(RUNNING, "Running...")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NO_DIRECTORY_DIALOG, "No Directory Selected")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ADD_DIRECTORY_TITLE, "Add Directory?")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ADD_DIRECTORY_MESSAGES[0], "Selected directory is not listed as DMF directory.")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ADD_DIRECTORY_MESSAGES[1], "Add to program's DMF directory list?")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CONTINUE_MESSAGE, "Continue?")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DOWNLOADED, "Downloaded")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FINISHED, "Finished")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DOWNLOAD_FAILED, "Download Failed")); //$NON-NLS-1$
		
		
		//TIME
		languageFile.add(new String());
		languageFile.add("[TIME]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(AM, "AM")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PM, "PM")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SECONDS_SUFFIX, "s")); //$NON-NLS-1$
		
		//FRAME
		languageFile.add(new String());
		languageFile.add("[FRAME]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PROCESS_RUNNING, "Process Running")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PROCESS_RUNNING_MESSAGES[0], "A process is running.")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PROCESS_RUNNING_MESSAGES[1], "Cancel process or wait until completion before closing.")); //$NON-NLS-1$
		
		//SETTINGS
		languageFile.add(new String());
		languageFile.add("[SETTINGS]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SETTINGS, "S^ettings")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(APPLY, "^Apply")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LANGUAGE, "^Language")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DMF_DIRECTORIES, "^DMF Directories")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(THEME, "^Theme")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT, "^Font")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PREVIEW, "Preview")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT_BOLD, "^Bold")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT_AA, "^Anti-Aliasing")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT_SIZE, "^Size")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT_PREVIEW, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG?! the quick brown fox jumps over the lazy dog. 0123456789")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NO_DIRECTORY, "(No Directory Selected)")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DIRECTORIES_LOADED, "(DMF Directories Loaded)")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NO_DIRECTORIES_TITLE, "No DMF Directories")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NO_DIRECTORIES_MESSAGES[0], "No DMF directories are specified.")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NO_DIRECTORIES_MESSAGES[1], "Add directories to store/load DMFs.")); //$NON-NLS-1$
		
		//DMF LOADING
		languageFile.add(new String());
		languageFile.add("[DMF LOADING]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LOADING_DMFS_TITLE, "Loading DMFs")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(GETTING_FOLDERS, "Getting Folders:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LOADING_DMFS, "Loading DMFs:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LOADING_INDEX, "Loading DMF Index:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SAVING_INDEX, "Saving DMF Index:")); //$NON-NLS-1$
		
		//MODES
		languageFile.add(new String());
		languageFile.add("[MODES]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(MODE_BACK, "< ^Back")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(MODE_START, "^Start")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DOWNLOAD_MODE, "^Download")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(MANAGE_MODE, "^Manage")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(VIEW_MODE, "^View")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SEQUENCE_MODE, "Se^quence DMFs")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ERROR_MODE, "^Find Errors")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(REFORMAT_MODE, "^Reformat")); //$NON-NLS-1$
		
		//DOWNLOAD
		languageFile.add(new String());
		languageFile.add("[DOWNLOAD]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DEVIANTART_MODE, "^DeviantArt")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DEVIANTART_PROGRESS_TITLE, "[DEVIANT-ART]")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FUR_AFFINITY_MODE, "^Fur Affinity")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FUR_AFFINITY_PROGRESS_TITLE, "[FUR AFFINITY]")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(INKBUNNY_MODE, "^Inkbunny")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(TRANSFUR_MODE, "^Transfur")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PAGE, "Page:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(GETTING_PAGE_URLS, "Getting Page URLs:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(GETTING_GALLERY_PAGES, "Getting Gallery Page URLs")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(GETTING_SCRAP_PAGES, "Getting Scrap Page URLs")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(GETTING_JOURNAL_PAGES, "Getting Journal Page URLs")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LOADING_PAGE, "Loading Page:")); //$NON-NLS-1$
		
		//FILE CHOOSER
		languageFile.add(new String());
		languageFile.add("[FILE CHOOSER]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(OPEN_TITLE, "Open File")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SAVE_TITLE, "Save File")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ROOTS, "^Roots:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PARENT, "^Parent")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NEW_DIRECTORY, "^New Directory")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FILE_NAME, "File ^Name:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FILE_TYPE, "File ^Type:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DIRECTORIES_ONLY, "Directories Only")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ALL_FILES, "All Files")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ALL_ALLOWED_EXTENSIONS, "All Allowed Extensions")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FILE_EXISTS, "File Exists")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FILE_EXISTS_MESSAGES[0], "Selected file already exists.")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FILE_EXISTS_MESSAGES[1], "Replace File?")); //$NON-NLS-1$
		
		//ARTIST HOSTING
		languageFile.add(new String());
		languageFile.add("[ARTIST HOSTING]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ACTIONS, "Actions:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ARTISTS, "Ar^tists:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CHECK_NEW, "Check ^New Pages")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CHECK_ALL, "Check All ^Pages")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DOWNLOAD_SINGLE, "^Download Single Page")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SAVE_JOURNALS, "Save ^Journals")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ADD_ARTIST, "Add Artist")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ALL_ARTISTS, "[ALL ARTISTS]")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NAME_OF_ARTIST[0], "Name of Artist:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SURE_TITLE, "Are You Sure?")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DELETE_ARTIST_MESSAGES[0], "Selected artists will be removed from your creator list.")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DELETE_ARTIST_MESSAGES[1], "Remove artists?")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(WRONG_FOLDER_MESSAGES[0], "Some of the listed artists don't have folders.")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(WRONG_FOLDER_MESSAGES[1], "You may be using the wrong directory.")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(WRONG_FOLDER_MESSAGES[2], "Use this directory?")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NO_ARTISTS, "No Artists Selected")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DEVIANTART_LOGIN, "DeviantArt Login")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CHOOSE_DEVIANTART_FOLDER, "^Choose DeviantArt Folder")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FUR_AFFINITY_LOGIN, "Fur Affinity Login")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CHOOSE_FUR_AFFINITY_FOLDER, "^Choose Fur Affinity Folder")); //$NON-NLS-1$
		
		//LOGIN
		languageFile.add(new String());
		languageFile.add("[LOGIN]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(USERNAME, "^Username:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PASSWORD, "^Password:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LOGIN, "^Login")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CAPTCHA, "^Captcha:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(REFRESH_CAPTCHA, "^Refresh Captcha")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LOAD_CAPTCHA_TITLE, "Loading Captcha")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LOAD_CAPTCHA, "Loading Image Captcha:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ATTEMPT_LOGIN, "Attempting Login:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LOGIN_FAILED, "Login Failed")); //$NON-NLS-1$
		
		//VIEWER
		languageFile.add(new String());
		languageFile.add("[VIEWER]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(VIEWER_TITLE, "Viewer")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PREVIOUS, "< ^Previous")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NEXT, "^Next >")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(OFFSET, " - OFF: ")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(RELOAD_DMFS, "^Reload DMFs")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(RELOAD_WITHOUT_INDEXES, "Reload DMFs ^Without Indexes")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(USE_THUMBNAILS, "^Use Thumbnails")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SORT, "^Sort")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SORT_TIME, "Sort by ^Time Published")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SORT_ALPHA, "Sort A^lphabetically")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SORT_RATING, "Sort by ^Rating")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(GROUP_ARTISTS, "Group ^Artists")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(GROUP_SEQUENCES, "Group Se^quences")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(GROUP_SECTIONS, "Group ^Sections")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LOADING_PREVIEWS_TITLE, "Loading Previews")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LOADING_PREVIEWS, "Loading Previews:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SORTING_DMFS_TITLE, "Sorting DMFs")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SORTING_DMFS, "Sorting DMFs:")); //$NON-NLS-1$
		
		//REFORMAT
		languageFile.add(new String());
		languageFile.add("[REFORMAT]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(REFORMAT_DMFS, "^Reformat DMFs")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(RENAME_FILES, "Re^name Files")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DELETE_SEQUENCES, "^Delete Sequence Data")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(REFORMAT_MESSAGE, "All DMFs will be reformatted.")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(RENAME_MESSAGE, "All DMFs and associated files will be renamed.")); //$NON-NLS-1$
		
		//ERROR FINDING
		languageFile.add(new String());
		languageFile.add("[ERROR FINDING]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(MISSING_MEDIA, "Find Missing ^Media")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(UNLINKED_FILES, "Find ^Unlinked Files")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(IDENTICAL_IDS, "Find ^Identical IDs")); //$NON-NLS-1$
		
		if(languageDirectory != null && languageDirectory.isDirectory())
		{
			File defaultFile = new File(languageDirectory, "00-ENG" + ParseINI.INI_EXTENSION); //$NON-NLS-1$
			DWriter.writeToFile(defaultFile, languageFile);
			
		}//IF
		
		return languageName;
		
	}//METHOD
	
}//CLASS
