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
	 * Header for the "COMMON" section of the language file
	 * 
	 * @since 2.0
	 */
	public static final String COMMON_HEADER = "[COMMON]"; //$NON-NLS-1$
	
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
	 * Header for the "FRAME" section of the language file.
	 * 
	 * @since 2.0
	 */
	private static final String FRAME_HEADER = "[FRAME]"; //$NON-NLS-1$
	
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
	 * Header text for the "SETTINGS" section of the language file.
	 * 
	 * @since 2.0
	 */
	private static final String SETTINGS_HEADER = "[SETTINGS]"; //$NON-NLS-1$
	
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
	
	//MODES
	
	/**
	 * Header text for the "MODES" section of the language file.
	 * 
	 * @since 2.0
	 */
	private static final String MODES_HEADER = "[MODES]"; //$NON-NLS-1$
	
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
	 * Header text for the "DOWNLOAD" section of the language file.
	 * 
	 * @since 2.0
	 */
	private static final String DOWNLOAD_HEADER = "[DOWNLOAD]"; //$NON-NLS-1$
	
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
	
	//VIEW
	
	/**
	 * Header text for the "VIEWER" section of the language file.
	 * 
	 * @since 2.0
	 */
	private static final String VIEWER_HEADER = "[VIEWER]"; //$NON-NLS-1$
	
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
		languageFile.add(COMMON_HEADER);
		languageFile.add(ParseINI.getAssignmentString(TITLE_VALUE, "Drak's Digital Hoarder")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(OK, "^OK")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(CANCEL, "^Cancel")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FILE, "^File")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(OPEN, "^Open")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(EXIT, "^Exit")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(RESTART_PROGRAM, "^Restart Program")); //$NON-NLS-1$
		
		//FRAME
		languageFile.add(new String());
		languageFile.add(FRAME_HEADER);
		languageFile.add(ParseINI.getAssignmentString(PROCESS_RUNNING, "Process Running")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PROCESS_RUNNING_MESSAGES[0], "A process is running.")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PROCESS_RUNNING_MESSAGES[1], "Cancel process or wait until completion before closing.")); //$NON-NLS-1$
		
		//SETTINGS
		languageFile.add(new String());
		languageFile.add(SETTINGS_HEADER);
		languageFile.add(ParseINI.getAssignmentString(SETTINGS, "S^ettings")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(LANGUAGE, "^Language")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(THEME, "^Theme")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT, "^Font")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(PREVIEW, "Preview")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT_BOLD, "^Bold")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT_AA, "^Anti-Aliasing")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT_SIZE, "^Size")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FONT_PREVIEW, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG?! the quick brown fox jumps over the lazy dog. 0123456789")); //$NON-NLS-1$
		
		//MODES
		languageFile.add(new String());
		languageFile.add(MODES_HEADER);
		languageFile.add(ParseINI.getAssignmentString(MODE_BACK, "< ^Back")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(MODE_START, "^Start")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(DOWNLOAD_MODE, "^Download")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(MANAGE_MODE, "^Manage")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(VIEW_MODE, "^View")); //$NON-NLS-1$
		
		//DOWNLOAD
		languageFile.add(new String());
		languageFile.add(DOWNLOAD_HEADER);
		languageFile.add(ParseINI.getAssignmentString(DEVIANTART_MODE, "^DeviantArt")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(FUR_AFFINITY_MODE, "^Fur Affinity")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(INKBUNNY_MODE, "^Inkbunny")); //$NON-NLS-1$
		languageFile.add(ParseINI.getAssignmentString(TRANSFUR_MODE, "^Transfur")); //$NON-NLS-1$
		
		//VIEWER
		languageFile.add(new String());
		languageFile.add(VIEWER_HEADER);
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
