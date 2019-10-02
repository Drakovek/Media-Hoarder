package drakovek.hoarder.file.language;

import java.util.ArrayList;

import drakovek.hoarder.processing.ParseINI;

/**
 * Class containing language values for the DMF editing modes of operation.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class EditingValues
{

	/**
	 * Language variable for the button to skip entries in the sequencing GUI
	 */
	public static final String SKIP = "skip"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to add name data to above entries in the sequencing GUI
	 */
	public static final String ABOVE = "above"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to add name data to below entries in the sequencing GUI
	 */
	public static final String BELOW = "below"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to add name data to a single entry in the sequencing GUI
	 */
	public static final String SINGLE = "single"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to add name data to all entries in the sequencing GUI
	 */
	public static final String ALL = "all"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Section Name" label in the sequencing GUI
	 */
	public static final String SECTION_NAME = "section_name"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Branch Name" label in the sequencing GUI
	 */
	public static final String BRANCH_NAME = "branch_name"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to search for DMFs in the sequencing GUI
	 */
	public static final String SEARCH = "search"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to clear all entries in the sequencing GUI
	 */
	public static final String CLEAR = "clear"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Add Branch" button in the sequencing GUI
	 */
	public static final String ADD_BRANCH = "add_branch"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Add Sequence" button in the sequencing GUI
	 */
	public static final String ADD_SEQUENCE = "add_sequence"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to move entries upward in the sequencing GUI
	 */
	public static final String UP = "up"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to move entries downward in the sequencing GUI
	 */
	public static final String DOWN = "down"; //$NON-NLS-1$
	
	/**
	 * Language variable for the title of progress dialog showing that the program is searching for DMFs without sequence data
	 */
	public static final String FINDING_UNSEQUENCED_TITLE = "finding_unsequenced_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the message of progress dialog showing that the program is searching for DMFs without sequence data
	 */
	public static final String FINDING_UNSEQUENCED_MESSAGE = "finding_unsequenced_message"; //$NON-NLS-1$
	
	/**
	 * Language variable for dialog showing that there are no more DMFs of which to add sequence data
	 */
	public static final String SEQUENCING_FINISHED = "sequencing_finished"; //$NON-NLS-1$
	
	/**
	 * Language variable for title of dialog for adding DMFs to a sequence
	 */
	public static final String ADD_DMFS = "add_dmfs"; //$NON-NLS-1$
	
	/**
	 * Language variable for button to add all shown indexes in a list selection dialog
	 */
	public static final String ADD_ALL = "add_all"; //$NON-NLS-1$
	
	/**
	 * Language variable for button to add selected indexes in a list selection dialog
	 */
	public static final String ADD_SELECTED = "add_selected"; //$NON-NLS-1$
	
	/**
	 * Returns an ArrayList of .ini formatted language values.
	 * 
	 * @return Language Values
	 */
	public static ArrayList<String> getValues()
	{
		ArrayList<String> values = new ArrayList<>();
		
		//SEQUENCING
		values.add(new String());
		values.add("[SEQUENCING]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SKIP, "S^kip")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ABOVE, "^Above")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(BELOW, "^Below")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SINGLE, "S^ingle")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ALL, "A^ll")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SECTION_NAME, "Section ^Name:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(BRANCH_NAME, "Branch ^Name:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SEARCH, "Sea^rch")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CLEAR, "^Clear")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ADD_BRANCH, "Add Branc^h")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ADD_SEQUENCE, "Add Se^quence")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(UP, "^Up")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DOWN, "^Down")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FINDING_UNSEQUENCED_TITLE, "Finding Unsequenced Files")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FINDING_UNSEQUENCED_MESSAGE, "Finding files without sequence data")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SEQUENCING_FINISHED, "Finished Adding Sequence Data")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ADD_DMFS, "Add DMFs")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ADD_ALL, "^Add All")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ADD_SELECTED, "Add ^Selected")); //$NON-NLS-1$
		return values;
		
	}//METHOD
	
}//CLASS
