package drakovek.hoarder.file.language;

import java.util.ArrayList;

import drakovek.hoarder.processing.ParseINI;

/**
 * Class containing language values for DMF info and DMF actions.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DmfLanguageValues
{
	/**
	 * Language variable for the title of the progress dialog for loading DMFs
	 */
	public static final String LOADING_DMFS_TITLE = "loading_dmfs_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the DMF loading progress dialog when searching for folders containing DMFs
	 */
	public static final String GETTING_FOLDERS = "getting_folders"; //$NON-NLS-1$
	
	/**
	 * Language variable for the DMF loading progress dialog when loading DMFs
	 */
	public static final String LOADING_DMFS = "loading_dmfs"; //$NON-NLS-1$
	
	/**
	 * Language variable for the DMF loading progress dialog when loading a DMF index file
	 */
	public static final String LOADING_INDEX = "loading_index"; //$NON-NLS-1$
	
	/**
	 * Language variable for the DMF loading progress dialog when saving a DMF index file
	 */
	public static final String SAVING_INDEX = "saving_index"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DMF title
	 */
	public static final String TITLE_LABEL = "title_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing DMF artists
	 */
	public static final String ARTISTS_LABEL = "artits_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DMF Description
	 */
	public static final String DESCRIPTION_LABEL = "description_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DMF's web tags
	 */
	public static final String WEB_TAG_LABEL = "web_tag_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DMF's user tags
	 */
	public static final String USER_TAG_LABEL = "user_tag_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DMF page URL
	 */
	public static final String PAGE_URL_LABEL = "page_url_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DMF direct URL
	 */
	public static final String DIRECT_URL_LABEL = "direct_url_label"; //$NON-NLS-1$
	
	/**
	 * Language variable for label showing a DMF secondary URL
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
		
		values.add("[DMF]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOADING_DMFS_TITLE, "Loading DMFs")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(GETTING_FOLDERS, "Getting Folders:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOADING_DMFS, "Loading DMFs:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOADING_INDEX, "Loading DMF Index:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SAVING_INDEX, "Saving DMF Index:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(TITLE_LABEL, "^Title:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ARTISTS_LABEL, "^Artists:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DESCRIPTION_LABEL, "^Description:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(WEB_TAG_LABEL, "^Web Tags:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(USER_TAG_LABEL, "^User Tags:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(PAGE_URL_LABEL, "Page URL:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DIRECT_URL_LABEL, "Direct URL:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SECONDARY_URL_LABEL, "Secondary URL:")); //$NON-NLS-1$
		
		return values;
	}//METHOD
}
