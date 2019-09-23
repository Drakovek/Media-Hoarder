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
	 * Language variable for the section name label in the sequencing GUI
	 */
	public static final String SECTION_NAME = "section_name"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to search for DMFs in the sequencing GUI
	 */
	public static final String SEARCH = "search"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to clear all entries in the sequencing GUI
	 */
	public static final String CLEAR = "clear"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to move entries upward in the sequencing GUI
	 */
	public static final String UP = "up"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button to move entries downward in the sequencing GUI
	 */
	public static final String DOWN = "down"; //$NON-NLS-1$
	
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
		values.add(ParseINI.getAssignmentString(SECTION_NAME, "^Section Name:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SEARCH, "Sea^rch")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CLEAR, "^Clear")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(UP, "^Up")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DOWN, "^Down")); //$NON-NLS-1$
			
		return values;
	}
}
