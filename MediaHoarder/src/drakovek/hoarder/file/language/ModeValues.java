package drakovek.hoarder.file.language;

import java.util.ArrayList;

import drakovek.hoarder.processing.ParseINI;

/**
 * Class containing language values for modes of operation.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ModeValues
{
	/**
	 * Language variable for the "Back" button for mode GUIs.
	 */
	public static final String MODE_BACK = "mode_back"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Start" button for mode GUIs.
	 */
	public static final String MODE_START = "mode_start"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Download" mode button.
	 */
	public static final String DOWNLOAD_MODE = "download_mode"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Manage" mode button.
	 */
	public static final String MANAGE_MODE = "manage_mode"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "View" mode button
	 */
	public static final String VIEW_MODE = "view_mode"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "DMF Sequencing" mode button
	 */
	public static final String SEQUENCE_MODE = "sequence_mode"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Find Errors" mode button
	 */
	public static final String ERROR_MODE = "error_mode"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Reformat" mode button
	 */
	public static final String REFORMAT_MODE = "reformat_mode"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "DeviantArt" mode button
	 */
	public static final String DEVIANTART_MODE = "deviantart"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Fur Affinity" mode button
	 */
	public static final String FUR_AFFINITY_MODE = "fur_affinity"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Inkbunny" mode button
	 */
	public static final String INKBUNNY_MODE = "inkbunny"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Transfur" mode button
	 */
	public static final String TRANSFUR_MODE = "transfur"; //$NON-NLS-1$
	
	/**
	 * Returns an ArrayList of .ini formatted language values.
	 * 
	 * @return Language Values
	 */
	public static ArrayList<String> getValues()
	{
		ArrayList<String> values = new ArrayList<>();
		
		//MODES
		values.add(new String());
		values.add("[MODES]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MODE_BACK, "< ^Back")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MODE_START, "^Start")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DOWNLOAD_MODE, "^Download")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MANAGE_MODE, "^Manage")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(VIEW_MODE, "^View")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SEQUENCE_MODE, "Se^quence DMFs")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ERROR_MODE, "^Find Errors")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(REFORMAT_MODE, "^Reformat")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DEVIANTART_MODE, "^DeviantArt")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FUR_AFFINITY_MODE, "^Fur Affinity")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(INKBUNNY_MODE, "^Inkbunny")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(TRANSFUR_MODE, "^Transfur")); //$NON-NLS-1$
		
		return values;
	}//METHOD
}//CLASS
