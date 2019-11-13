package drakovek.hoarder.file.language;

import java.util.ArrayList;

import drakovek.hoarder.processing.ParseINI;

/**
 * Class containing language values for DVK info and DVK actions.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DvkLanguageValues
{
	/**
	 * Language variable for the title of the progress dialog for loading DVKs
	 */
	public static final String LOADING_DVKS_TITLE = "loading_dvks_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the DVK loading progress dialog when searching for folders containing DVKs
	 */
	public static final String GETTING_FOLDERS = "getting_folders"; //$NON-NLS-1$
	
	/**
	 * Language variable for the DVK loading progress dialog when loading DVKs
	 */
	public static final String LOADING_DVKS = "loading_dvks"; //$NON-NLS-1$
	
	/**
	 * Language variable for the DVK loading progress dialog when loading a DVK index file
	 */
	public static final String LOADING_INDEX = "loading_index"; //$NON-NLS-1$
	
	/**
	 * Language variable for the DVK loading progress dialog when saving a DVK index file
	 */
	public static final String SAVING_INDEX = "saving_index"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DVK title
	 */
	public static final String TITLE_LABEL = "title_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing DVK artists
	 */
	public static final String ARTISTS_LABEL = "artits_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DVK Description
	 */
	public static final String DESCRIPTION_LABEL = "description_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DVK's web tags
	 */
	public static final String WEB_TAG_LABEL = "web_tag_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DVK page URL
	 */
	public static final String PAGE_URL_LABEL = "page_url_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DVK direct URL
	 */
	public static final String DIRECT_URL_LABEL = "direct_url_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DVK secondary URL
	 */
	public static final String SECONDARY_URL_LABEL = "secondary_url_label"; //$NON-NLS-1$
	
	/**
	 * Returns an ArrayList of .ini formatted language values.
	 * 
	 * @return Language Values
	 */
	public static ArrayList<String> getValues()
	{
		ArrayList<String> values = new ArrayList<>();
		
		values.add("[DVK]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOADING_DVKS_TITLE, "Loading DVKs")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(GETTING_FOLDERS, "Getting Folders:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOADING_DVKS, "Loading DVKs:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOADING_INDEX, "Loading DVK Index:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SAVING_INDEX, "Saving DVK Index:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(TITLE_LABEL, "^Title:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ARTISTS_LABEL, "^Artists:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DESCRIPTION_LABEL, "^Description:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(WEB_TAG_LABEL, "^Web Tags:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(PAGE_URL_LABEL, "Page URL:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DIRECT_URL_LABEL, "Direct URL:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SECONDARY_URL_LABEL, "Secondary URL:")); //$NON-NLS-1$
		
		return values;
		
	}//METHOD
	
}//CLASS
