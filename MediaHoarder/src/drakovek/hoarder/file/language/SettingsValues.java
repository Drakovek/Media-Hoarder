package drakovek.hoarder.file.language;

import java.util.ArrayList;

import drakovek.hoarder.processing.ParseINI;

/**
 * Class containing language values for the settings GUI.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class SettingsValues
{
	/**
	 * Language variable for settings button
	 */
	public static final String SETTINGS = "settings"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Apply" button in the settings GUI
	 */
	public static final String APPLY = "apply"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Language" label in the settings GUI
	 */
	public static final String LANGUAGE = "language"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "DMF Directories" label in the settings GUI
	 */
	public static final String DMF_DIRECTORIES = "dmf_directories"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Theme" label in the settings GUI
	 */
	public static final String THEME = "theme"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Font" label in the settings GUI
	 */
	public static final String FONT = "font"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Preview" label in the settings GUI
	 */
	public static final String PREVIEW = "preview"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Bold" check box label in the settings GUI
	 */
	public static final String FONT_BOLD = "font_bold"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Anti-Aliasing" check box label in the settings GUI
	 */
	public static final String FONT_AA = "font_aa"; //$NON-NLS-1$
	
	/**
	 * Language variable for the font "Size" label in the settings GUI
	 */
	public static final String FONT_SIZE = "font_size"; //$NON-NLS-1$
	
	/**
	 * Language variable for the text used for the font preview in the settings GUI
	 */
	public static final String FONT_PREVIEW = "font_preview"; //$NON-NLS-1$
	
	/**
	 * Language variable for the text used to show that no directory is selected in the settings bar.
	 */
	public static final String NO_DIRECTORY = "no_directory"; //$NON-NLS-1$
	
	/**
	 * Language variable for the text used to show that DMF Directories have been properly loaded
	 */
	public static final String DIRECTORIES_LOADED = "directories_loaded"; //$NON-NLS-1$
	
	/**
	 * Language variable for the title of the dialog that shows that no DMF directories have been specified.
	 */
	public static final String NO_DIRECTORIES_TITLE = "no_directories_title"; //$NON-NLS-1$
	
	/**
	 * Language variables for the messages of the dialog that shows that no DMF directories have been specified.
	 */
	public static final String[] NO_DIRECTORIES_MESSAGES = {"no_directories_message_1", "no_directories_message_2"}; //$NON-NLS-1$ //$NON-NLS-2$
	
	/**
	 * Returns an ArrayList of .ini formatted language values.
	 * 
	 * @return Language Values
	 */
	public static ArrayList<String> getValues()
	{
		ArrayList<String> values = new ArrayList<>();
		
		//SETTINGS
		values.add(new String());
		values.add("[SETTINGS]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SETTINGS, "S^ettings")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(APPLY, "^Apply")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LANGUAGE, "^Language")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DMF_DIRECTORIES, "^DMF Directories")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(THEME, "^Theme")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FONT, "^Font")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(PREVIEW, "Preview")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FONT_BOLD, "^Bold")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FONT_AA, "^Anti-Aliasing")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FONT_SIZE, "^Size")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FONT_PREVIEW, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG?! the quick brown fox jumps over the lazy dog. 0123456789")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(NO_DIRECTORY, "(No Directory Selected)")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DIRECTORIES_LOADED, "(DMF Directories Loaded)")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(NO_DIRECTORIES_TITLE, "No DMF Directories")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(NO_DIRECTORIES_MESSAGES[0], "No DMF directories are specified.")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(NO_DIRECTORIES_MESSAGES[1], "Add directories to store/load DMFs.")); //$NON-NLS-1$
		
		return values;
		
	}//METHOD
	
}//CLASS
