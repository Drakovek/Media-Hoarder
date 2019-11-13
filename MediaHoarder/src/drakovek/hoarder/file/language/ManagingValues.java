package drakovek.hoarder.file.language;

import java.util.ArrayList;

import drakovek.hoarder.processing.ParseINI;

/**
 * Class containing language values for the various DVK managing modes of operation.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ManagingValues
{
	/**
	 * Language variable for button to start DVK reformatting process
	 */
	public static final String REFORMAT_DVKS = "reformat_dvks"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to start file renaming process
	 */
	public static final String RENAME_FILES = "rename_files"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to start sequence data deleting process
	 */
	public static final String DELETE_SEQUENCES = "delete_sequeces"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to start HTML reformatting process
	 */
	public static final String REFORMAT_HTMLS = "reformat htmls"; //$NON-NLS-1$
	
	/**
	 * Language variable for message shown to check if user wishes to reformat DVKs
	 */
	public static final String REFORMAT_MESSAGE = "reformat_message"; //$NON-NLS-1$
	
	/**
	 * Language variable for message shown to check if user wishes to rename files
	 */
	public static final String RENAME_MESSAGE = "rename_message"; //$NON-NLS-1$
	
	/**
	 * Language variable for the message shown to check if user wishes to delete sequence data.
	 */
	public static final String DELETE_SEQUENCES_MESSAGE = "delete_sequences_message"; //$NON-NLS-1$
	
	/**
	 * Language variable for button to search for missing media in the error finding GUI
	 */
	public static final String MISSING_MEDIA = "missing_media"; //$NON-NLS-1$
	
	/**
	 * Language variable for button to search for unlinked files in the error finding GUI
	 */
	public static final String UNLINKED_FILES = "unlinked_files"; //$NON-NLS-1$
	
	/**
	 * Language variable for button to search for DVKs with identical IDs in the error finding GUI
	 */
	public static final String IDENTICAL_IDS = "identical_ids"; //$NON-NLS-1$
	
	/**
	 * Returns an ArrayList of .ini formatted language values.
	 * 
	 * @return Language Values
	 */
	public static ArrayList<String> getValues()
	{
		ArrayList<String> values = new ArrayList<>();
		
		//REFORMAT
		values.add("[REFORMAT]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(REFORMAT_DVKS, "^Reformat DVKs")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(RENAME_FILES, "Re^name Files")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DELETE_SEQUENCES, "^Delete Sequence Data")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(REFORMAT_HTMLS, "Reformat ^HTMLs")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(REFORMAT_MESSAGE, "All DVKs will be reformatted.")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(RENAME_MESSAGE, "All DVKs and associated files will be renamed.")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DELETE_SEQUENCES_MESSAGE, "All sequence data will be deleted.")); //$NON-NLS-1$
		
		//ERROR FINDING
		values.add(new String());
		values.add("[ERROR FINDING]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MISSING_MEDIA, "Find Missing ^Media")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(UNLINKED_FILES, "Find ^Unlinked Files")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(IDENTICAL_IDS, "Find ^Identical IDs")); //$NON-NLS-1$
		
		return values;
		
	}//METHOD
	
}//CLASS
