package drakovek.hoarder.file.language;

import java.util.ArrayList;

import drakovek.hoarder.processing.ParseINI;

/**
 * Class containing language values for compound swing components.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class CompoundValues
{
	/**
	 * Language variable for the file chooser dialog title when opening a file.
	 */
	public static final String OPEN_TITLE = "open_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the file chooser dialog title when saving a file.
	 */
	public static final String SAVE_TITLE = "save_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Roots" label in the file chooser dialog
	 */
	public static final String ROOTS = "roots"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Parent" button in the file chooser dialog
	 */
	public static final String PARENT = "parent"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "New Directory" button in the file chooser dialog
	 */
	public static final String NEW_DIRECTORY = "new_directory"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "File Name:" label in the file chooser dialog
	 */
	public static final String FILE_NAME = "file_name"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "File Type:" label in the file chooser dialog
	 */
	public static final String FILE_TYPE = "file_type"; //$NON-NLS-1$
	
	/**
	 * Language variable for showing that only directories can be opened in the file chooser dialog
	 */
	public static final String DIRECTORIES_ONLY = "directories_only"; //$NON-NLS-1$
	
	/**
	 * Language variable for showing that the user can save to any file type in the file chooser dialog
	 */
	public static final String ALL_FILES = "all_files"; //$NON-NLS-1$
	
	/**
	 * Language variable for showing all files of any of the listed extensions in the file chooser dialog
	 */
	public static final String ALL_ALLOWED_EXTENSIONS = "all_allowed_extensions"; //$NON-NLS-1$
	
	/**
	 * Language variable for the title of dialog showing user that a given file already exists.
	 */
	public static final String FILE_EXISTS = "file_exists"; //$NON-NLS-1$
	
	/**
	 * Language variable for the messages of dialog showing user that a given file already exists.
	 */
	public static final String[] FILE_EXISTS_MESSAGES = {"file_exists_message_1", "file_exists_message_2"}; //$NON-NLS-1$ //$NON-NLS-2$
	
	/**
	 * Returns an ArrayList of .ini formatted language values.
	 * 
	 * @return Language Values
	 */
	public static ArrayList<String> getValues()
	{
		ArrayList<String> values = new ArrayList<>();
		
		//FILE CHOOSER
		values.add(new String());
		values.add("[FILE CHOOSER]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(OPEN_TITLE, "Open File")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SAVE_TITLE, "Save File")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ROOTS, "^Roots:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(PARENT, "^Parent")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(NEW_DIRECTORY, "^New Directory")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FILE_NAME, "File ^Name:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FILE_TYPE, "File ^Type:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DIRECTORIES_ONLY, "Directories Only")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ALL_FILES, "All Files")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ALL_ALLOWED_EXTENSIONS, "All Allowed Extensions")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FILE_EXISTS, "File Exists")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FILE_EXISTS_MESSAGES[0], "Selected file already exists.")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FILE_EXISTS_MESSAGES[1], "Replace File?")); //$NON-NLS-1$
				
		return values;
		
	}//METHOD
	
}//CLASS
