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
	 * Language variable for "File"
	 * 
	 * @since 2.0
	 */
	public static final String FILE = "file"; //$NON-NLS-1$
	
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
	 * Language variable for "Restart Program"
	 * 
	 * @since 2.0
	 */
	public static final String RESTART_PROGRAM = "restart_program"; //$NON-NLS-1$
	
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
	 * Language variable for the "Language" label in the settings GUI
	 * 
	 * @since 2.0
	 */
	public static final String LANGUAGE = "language"; //$NON-NLS-1$
	
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
	
	//DOWNLOAD
	
	/**
	 * Language variable for the "DeviantArt" mode button
	 * 
	 * @since 2.0
	 */
	public static final String DEVIANTART_MODE = "deviantart"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Fur Affinity" mode button
	 * 
	 * @since 2.0
	 */
	public static final String FUR_AFFINITY_MODE = "fur_affinity"; //$NON-NLS-1$
	
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
	
	//FILE CHOOSER
	
	/**
	 * Language variable for the file chooser dialog title when opening a file.
	 * 
	 * @since 2.0
	 */
	public static final String OPEN_TITLE = "open_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Roots" label in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String ROOTS = "roots"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Files" label in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String FILES = "files"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Back" button in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String BACK = "back"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Parent" button in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String PARENT = "parent"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Name" label in the file chooser dialog
	 * 
	 * @since 2.0
	 */
	public static final String NAME = "name"; //$NON-NLS-1$
	
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
	 * Language variable for the "Save Favorites" check box from the artist hosting GUI
	 * 
	 * @since 2.0
	 */
	public static final String SAVE_FAVORITES = "save_favorites"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Add Artist" title for adding artists in the artist hosting GUI
	 * 
	 * @since 2.0
	 */
	public static final String ADD_ARTIST = "add_artist"; //$NON-NLS-1$
	
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
	 * Language variable for the "Update Indexes" menu option in the view GUI.
	 * 
	 * @since 2.0
	 */
	public static final String UPDATE_INDEXES = "update_indexes"; //$NON-NLS-1$
	
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
		languageFile.add(ParseINI.getAssignmentString(ADD, "^Add")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(REMOVE, "^Remove")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(YES, "^Yes")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NO, "^No")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FILE, "^File")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(OPEN, "^Open")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(EXIT, "^Exit")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(RESTART_PROGRAM, "^Restart Program")); //$NON-NLS-1$
		
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
		languageFile.add(ParseINI.getAssignmentString(LANGUAGE, "^Language")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(THEME, "^Theme")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT, "^Font")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PREVIEW, "Preview")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT_BOLD, "^Bold")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT_AA, "^Anti-Aliasing")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT_SIZE, "^Size")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT_PREVIEW, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG?! the quick brown fox jumps over the lazy dog. 0123456789")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NO_DIRECTORY, "(No Directory Selected)")); //$NON-NLS-1$
		
		//MODES
		languageFile.add(new String());
		languageFile.add("[MODES]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(MODE_BACK, "< ^Back")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(MODE_START, "^Start")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DOWNLOAD_MODE, "^Download")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(MANAGE_MODE, "^Manage")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(VIEW_MODE, "^View")); //$NON-NLS-1$
		
		//DOWNLOAD
		languageFile.add(new String());
		languageFile.add("[DOWNLOAD]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DEVIANTART_MODE, "^DeviantArt")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FUR_AFFINITY_MODE, "^Fur Affinity")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(INKBUNNY_MODE, "^Inkbunny")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(TRANSFUR_MODE, "^Transfur")); //$NON-NLS-1$
		
		//FILE CHOOSER
		languageFile.add(new String());
		languageFile.add("[FILE CHOOSER]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(OPEN_TITLE, "Open Directory")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ROOTS, "^Roots:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FILES, "^Files:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(BACK, "^Back")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PARENT, "^Parent")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NAME, "^Name:")); //$NON-NLS-1$
		
		//ARTIST HOSTING
		languageFile.add(new String());
		languageFile.add("[ARTIST HOSTING]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ACTIONS, "Actions:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ARTISTS, "Ar^tists:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CHECK_NEW, "Check ^New Pages")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CHECK_ALL, "Check All ^Pages")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DOWNLOAD_SINGLE, "^Download Single Page")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SAVE_JOURNALS, "Save ^Journals")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SAVE_FAVORITES, "Save Fa^vorites")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(ADD_ARTIST, "Add Artist")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NAME_OF_ARTIST[0], "Name of Artist:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(SURE_TITLE, "Are You Sure?")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DELETE_ARTIST_MESSAGES[0], "Selected artists will be removed from your creator list.")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DELETE_ARTIST_MESSAGES[1], "Remove artists?")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(USERNAME, "^Username:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PASSWORD, "^Password:")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LOGIN, "^Login")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DEVIANTART_LOGIN, "DeviantArt Login")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CHOOSE_DEVIANTART_FOLDER, "^Choose DeviantArt Folder")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FUR_AFFINITY_LOGIN, "Fur Affinity Login")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CHOOSE_FUR_AFFINITY_FOLDER, "^Choose Fur Affinity Folder")); //$NON-NLS-1$
		
		//VIEWER
		languageFile.add(new String());
		languageFile.add("[VIEWER]"); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(VIEWER_TITLE, "Viewer")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PREVIOUS, "< ^Previous")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(NEXT, "^Next >")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(UPDATE_INDEXES, "^Update Indexes")); //$NON-NLS-1$
		
		
		
		if(languageDirectory != null && languageDirectory.isDirectory())
		{
			File defaultFile = new File(languageDirectory, "00-ENG.ini"); //$NON-NLS-1$
			DWriter.writeToFile(defaultFile, languageFile);
			
		}//IF
		
		return languageName;
		
	}//METHOD
	
}//CLASS
