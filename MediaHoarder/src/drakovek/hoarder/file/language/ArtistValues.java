package drakovek.hoarder.file.language;

import java.util.ArrayList;

import drakovek.hoarder.processing.ParseINI;

/**
 * Class containing language values for artist hosting GUI.
 * @author drakovek
 *
 */
public class ArtistValues
{
	/**
	 * Language variable for the "Actions" label from the artist hosting GUI
	 */
	public static final String ACTIONS = "actions"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Artists" label from the artist hosting GUI
	 */
	public static final String ARTISTS = "artists"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Check New Pages" button from the artist hosting GUI
	 */
	public static final String CHECK_NEW = "check_new"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Check All Pages" button from the artist hosting GUI
	 */
	public static final String CHECK_ALL = "check_all"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Download Single" button from the artist hosting GUI
	 */
	public static final String DOWNLOAD_SINGLE = "download_single"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Save Journals" check box from the artist hosting GUI
	 */
	public static final String SAVE_JOURNALS = "save_journals"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Add Artist" title for adding artists in the artist hosting GUI
	 */
	public static final String ADD_ARTIST = "add_artist"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "All Artists" item in the artist list of the artist hosting GUI
	 */
	public static final String ALL_ARTISTS = "all_artists"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Name of Artist" message for adding artists in the artist hosting GUI
	 */
	public static final String[] NAME_OF_ARTIST = {"name_of_artist"}; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Are you sure?" title for dialogs.
	 */
	public static final String SURE_TITLE = "sure_title"; //$NON-NLS-1$
	
	/**
	 * Language variables for message shown when removing artists in the Artist Hosting GUI
	 */
	public static final String[] DELETE_ARTIST_MESSAGES = {"delete_artist_message_1", "delete_artist_message_2"};  //$NON-NLS-1$//$NON-NLS-2$
	
	/**
	 * Language variables for message shown if the user has a potentially incorrect folder selected.
	 */
	public static final String[] WRONG_FOLDER_MESSAGES = {"wrong_folder_message_1", "wrong_folder_message_2", "wrong_folder_message_3"};  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
	
	/**
	 * Language variable for the dialog in the artist hosting GUI for showing that no artists are selected.
	 */
	public static final String NO_ARTISTS = "no_artists"; //$NON-NLS-1$
	
	/**
	 * Language variable for the title of dialog prompting user to enter a page URL
	 */
	public static final String ENTER_URL_TITLE = "enter_url_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the message of dialog prompting user to enter a page URL
	 */
	public static final String ENTER_URL_MESSAGE = "enter_url_message"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Username" label in the login GUI.
	 */
	public static final String USERNAME = "username"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Password" label in the login GUI.
	 */
	public static final String PASSWORD = "password"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Login" button in the login GUI.
	 */
	public static final String LOGIN = "login"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Captcha" label in the login GUI
	 */
	public static final String CAPTCHA = "captcha"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Refresh Captcha" button in the login GUI

	 */
	public static final String REFRESH_CAPTCHA = "refresh_captcha"; //$NON-NLS-1$
	
	/**
	 * Language variable for the title of the progress dialog when loading image captcha
	 */
	public static final String LOAD_CAPTCHA_TITLE = "load_captcha_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the process label for the dialog when loading image captcha
	 */
	public static final String LOAD_CAPTCHA = "load_captcha"; //$NON-NLS-1$
	
	/**
	 * Language variable for title of the "Attempt Login" progress dialog.
	 */
	public static final String ATTEMPT_LOGIN = "attempt_login"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "login failed" message if attempted login fails.
	 */
	public static final String LOGIN_FAILED = "login_failed"; //$NON-NLS-1$
	
	/**
	 * Language variable for the title of the login GUI when logging into DeviantArt.
	 */
	public static final String DEVIANTART_LOGIN = "deviantart_login"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Choose DeviantArt Folder" menu item in the DeviantArt GUI
	 */
	public static final String CHOOSE_DEVIANTART_FOLDER = "choose_deviantart_folder"; //$NON-NLS-1$

	/**
	 * Language variable for the title of the login GUI when logging into Fur Affinity.
	 */
	public static final String FUR_AFFINITY_LOGIN = "fur_affinity_login"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Choose Fur Affinity Folder" menu item in the Fur Affinity GUI
	 */
	public static final String CHOOSE_FUR_AFFINITY_FOLDER = "choose_fur_affinity_folder"; //$NON-NLS-1$
	
	/**
	 * Language variable for the title of the login GUI when logging into Inkbunny.
	 */
	public static final String INKBUNNY_LOGIN = "inkbunny_login"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Choose Inkbunny Folder" menu item in the Fur Affinity GUI
	 */
	public static final String CHOOSE_INKBUNNY_FOLDER = "choose_inkbunny_folder"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Page:" in the progress info dialog when indicating the program is loading web data from a certain page
	 */
	public static final String PAGE = "page"; //$NON-NLS-1$
	
	/**
	 * Language variable for indicating the program is getting page URLs in the progress info dialog
	 */
	public static final String GETTING_PAGE_URLS = "getting_page_urls"; //$NON-NLS-1$
	
	/**
	 * Language variable for indicating the program is getting gallery pages in the progress info dialog
	 */
	public static final String GETTING_GALLERY_PAGES = "getting_gallery_pages"; //$NON-NLS-1$
	
	/**
	 * Language variable for indicating the program is getting scrap pages in the progress info dialog
	 */
	public static final String GETTING_SCRAP_PAGES = "getting_scrap_pages"; //$NON-NLS-1$
	
	/**
	 * Language variable for indicating the program is getting journal pages in the progress info dialog
	 */
	public static final String GETTING_JOURNAL_PAGES = "getting_journal_pages"; //$NON-NLS-1$
	
	/**
	 * Language variable for indicating the program is loading a page in the progress info dialog
	 */
	public static final String LOADING_PAGE = "loading_page"; //$NON-NLS-1$
	
	/**
	 * Returns an ArrayList of .ini formatted language values.
	 * 
	 * @return Language Values
	 */
	public static final ArrayList<String> getValues()
	{
		ArrayList<String> values = new ArrayList<>();
		
		values.add("[ARTIST HOSTING]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ACTIONS, "Actions:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ARTISTS, "Ar^tists:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CHECK_NEW, "Check ^New Pages")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CHECK_ALL, "Check All ^Pages")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DOWNLOAD_SINGLE, "^Download Single Page")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SAVE_JOURNALS, "Save ^Journals")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ADD_ARTIST, "Add Artist")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ALL_ARTISTS, "[ALL ARTISTS]")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(NAME_OF_ARTIST[0], "Name of Artist:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SURE_TITLE, "Are You Sure?")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DELETE_ARTIST_MESSAGES[0], "Selected artists will be removed from your creator list.")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DELETE_ARTIST_MESSAGES[1], "Remove artists?")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(WRONG_FOLDER_MESSAGES[0], "Some of the listed artists don't have folders.")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(WRONG_FOLDER_MESSAGES[1], "You may be using the wrong directory.")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(WRONG_FOLDER_MESSAGES[2], "Use this directory?")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(NO_ARTISTS, "No Artists Selected")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ENTER_URL_TITLE, "Enter Page URL")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ENTER_URL_MESSAGE, "Enter Page URL:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DEVIANTART_LOGIN, "DeviantArt Login")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CHOOSE_DEVIANTART_FOLDER, "^Choose DeviantArt Folder")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FUR_AFFINITY_LOGIN, "Fur Affinity Login")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CHOOSE_FUR_AFFINITY_FOLDER, "^Choose Fur Affinity Folder")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(INKBUNNY_LOGIN, "Inkbunny Login")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CHOOSE_INKBUNNY_FOLDER, "^Choose Inkbunny Folder")); //$NON-NLS-1$
		
		//LOGIN
		values.add(new String());
		values.add("[LOGIN]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(USERNAME, "^Username:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(PASSWORD, "^Password:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOGIN, "^Login")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CAPTCHA, "^Captcha:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(REFRESH_CAPTCHA, "^Refresh Captcha")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOAD_CAPTCHA_TITLE, "Loading Captcha")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOAD_CAPTCHA, "Loading Image Captcha:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ATTEMPT_LOGIN, "Attempting Login:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOGIN_FAILED, "Login Failed")); //$NON-NLS-1$
		
		values.add(new String());
		values.add("[PAGE LOADING]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(PAGE, "Page:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(GETTING_PAGE_URLS, "Getting Page URLs:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(GETTING_GALLERY_PAGES, "Getting Gallery Page URLs")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(GETTING_SCRAP_PAGES, "Getting Scrap Page URLs")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(GETTING_JOURNAL_PAGES, "Getting Journal Page URLs")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOADING_PAGE, "Loading Page:")); //$NON-NLS-1$
		
		return values;
		
	}//METHOD
	
}//CLASS
