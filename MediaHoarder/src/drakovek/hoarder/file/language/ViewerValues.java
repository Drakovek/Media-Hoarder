package drakovek.hoarder.file.language;

import java.util.ArrayList;

import drakovek.hoarder.processing.ParseINI;

/**
 * Class containing language values for the viewer GUI.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ViewerValues
{
	/**
	 * Language variable for the title of the view browser GUI.
	 */
	public static final String VIEWER_TITLE = "viewer_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "previous" button in the viewer GUI.
	 */
	public static final String PREVIOUS = "previous"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "next" button in the viewer GUI.
	 */
	public static final String NEXT = "next"; //$NON-NLS-1$
	
	/**
	 * Language variable for showing an offset value in the viewer GUI
	 */
	public static final String OFFSET = "offset"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Reload DMFs" menu item in the viewer GUI
	 */
	public static final String RELOAD_DMFS = "reload_dmfs"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Reload DMFs Without Indexes" menu item in the viewer GUI
	 */
	public static final String RELOAD_WITHOUT_INDEXES = "reload_without_indexes"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Use Thumbnails" menu item in the viewer GUI.
	 */
	public static final String USE_THUMBNAILS = "use_thumbnails"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Show Artists" menu item in the viewer GUI.
	 */
	public static final String SHOW_ARTISTS = "show_artists"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Show Ratings" menu item in the viewer GUI.
	 */
	public static final String SHOW_RATINGS = "show_ratings"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Show View Counts" menu item in the viewer GUI.
	 */
	public static final String SHOW_VIEWS = "show_views"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Sort" menu in the viewer GUI
	 */
	public static final String SORT = "sort"; //$NON-NLS-1$
	
	/**
	 * Language variable for the radio button menu item in the viewer GUI for sorting by time.
	 */
	public static final String SORT_TIME = "sort_time"; //$NON-NLS-1$
	
	/**
	 * Language variable for the radio button menu item in the viewer GUI for sorting alpha-numerically.
	 */
	public static final String SORT_ALPHA = "sort_alpha"; //$NON-NLS-1$
	
	/**
	 * Language variable for the radio button menu item in the viewer GUI for sorting by rating
	 */
	public static final String SORT_RATING = "sort_rating"; //$NON-NLS-1$
	
	/**
	 * Language variable for the radio button menu item in the viewer GUI for sorting by views
	 */
	public static final String SORT_VIEWS = "sort_views"; //$NON-NLS-1$
	
	/**
	 * Language variable for the check box menu item in the viewer GUI for grouping artists when sorting.
	 */
	public static final String GROUP_ARTISTS = "group_artists"; //$NON-NLS-1$
	
	/**
	 * Language variable for the check box menu item in the viewer GUI for grouping sequences when sorting.
	 */
	public static final String GROUP_SEQUENCES = "group_sequences"; //$NON-NLS-1$
	
	/**
	 * Language variable for the check box menu item in the viewer GUI for grouping sections when sorting.
	 */
	public static final String GROUP_SECTIONS = "group_sections"; //$NON-NLS-1$
	
	/**
	 * Language variable for the check box menu item in the viewer GUI for reversing the DMF sorting order
	 */
	public static final String REVERSE_ORDER = "reverse_order"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Filter" menu in the viewer GUI
	 */
	public static final String FILTER = "filter"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Filter Media" menu item in the viewer GUI
	 */
	public static final String FILTER_MEDIA = "filter_media"; //$NON-NLS-1$
	
	/**
	 * Language variable for viewer GUI's preview loading process title
	 */
	public static final String LOADING_PREVIEWS_TITLE = "loading_previews_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for viewer GUI's preview loading process label
	 */
	public static final String LOADING_PREVIEWS = "loading_previews"; //$NON-NLS-1$
	
	/**
	 * Language variable for the view GUI's DMF sorting process title
	 */
	public static final String SORTING_DMFS_TITLE = "sorting_dmfs_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the view GUI's DMF sorting process label
	 */
	public static final String SORTING_DMFS = "sorting_dmfs"; //$NON-NLS-1$
	
	/**
	 * Language variable for the view GUI's DMF filtering process title
	 */
	public static final String FILTERING_DMFS_TITLE = "filtering_dmfs_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the view GUI's DMF filtering process label
	 */
	public static final String FILTERING_DMFS = "filtering_dmfs"; //$NON-NLS-1$
	
	/**
	 * Language variable for the button used to show available sequence branches.
	 */
	public static final String BRANCHES = "branches"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "views" label in the viewer GUI's DMF info pane
	 */
	public static final String VIEWS = "views"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "rating" label in the viewer GUI's DMF info pane
	 */
	public static final String RATING = "rating"; //$NON-NLS-1$
	
	/**
	 * Language variable for the date label in the viewer GUI's DMF info pane
	 */
	public static final String DATE = "date"; //$NON-NLS-1$
	
	/**
	 * Language variable for the time label in the viewer GUI's DMF info pane
	 */
	public static final String TIME = "time"; //$NON-NLS-1$
	
	/**
	 * Language variable for the links in the viewer GUI's DMF info pane
	 */
	public static final String LINK = "link"; //$NON-NLS-1$
	
	/**
	 * Language variable used for progress dialog title when loading a media file.
	 */
	public static final String LOADING_MEDIA_TITLE = "loading_media_title"; //$NON-NLS-1$
	
	/**
	 * Language variable used for progress dialog message when loading a media file.
	 */
	public static final String LOADING_MEDIA_MESSAGE = "loading_media_message"; //$NON-NLS-1$
	
	/**
	 * Language variable used to show the title of the dialog for showing hyperlink info.
	 */
	public static final String HYPERLINK_TITLE = "hyperlink_title"; //$NON-NLS-1$
	
	/**
	 * Language variable used for the scale menu in the viewer GUI
	 */
	public static final String SCALE = "scale"; //$NON-NLS-1$

	/**
	 * Language variable used for the "Full Scale" scaling menu item in the viewer GUI
	 */
	public static final String SCALE_FULL = "scale_full"; //$NON-NLS-1$
	
	/**
	 * Language variable used for the "2-D Fit" scaling menu item in the viewer GUI
	 */
	public static final String SCALE_2D_FIT = "scale_2d_fit"; //$NON-NLS-1$
	
	/**
	 * Language variable used for the "2-D Stretch" scaling menu item in the viewer GUI
	 */
	public static final String SCALE_2D_STRETCH = "scale_2d_stretch"; //$NON-NLS-1$
	
	/**
	 * Language variable used for the "1-D Fit" scaling menu item in the viewer GUI
	 */
	public static final String SCALE_1D_FIT = "scale_1d_fit"; //$NON-NLS-1$
	
	/**
	 * Language variable used for the "1-D Stretch" scaling menu item in the viewer GUI
	 */
	public static final String SCALE_1D_STRETCH = "scale_1d_stretch"; //$NON-NLS-1$
	
	/**
	 * Language variable used for the "Direct Scale" scaling menu item in the viewer GUI
	 */
	public static final String SCALE_DIRECT = "scale_direct"; //$NON-NLS-1$
	
	/**
	 * Language variable for the title of dialog prompting the user to enter a direct scale value
	 */
	public static final String DIRECT_SCALE_TITLE = "direct_scale_title"; //$NON-NLS-1$
	
	/**
	 * Language variable for the message of dialog prompting the user to enter a direct scale value
	 */
	public static final String DIRECT_SCALE_MESSAGE = "direct_scale_message"; //$NON-NLS-1$
	
	/**
	 * Language variable used for the details menu in the viewer GUI
	 */
	public static final String DETAILS = "details"; //$NON-NLS-1$
	
	/**
	 * Language variable used for the "No Details" detail menu item in the viewer GUI
	 */
	public static final String NO_DETAILS = "no_details"; //$NON-NLS-1$
	
	/**
	 * Language variable used for the "Top Details" detail menu item in the viewer GUI
	 */
	public static final String TOP_DETAILS = "top_details"; //$NON-NLS-1$
	
	/**
	 * Language variable used for the "Bottom Details" detail menu item in the viewer GUI
	 */
	public static final String BOTTOM_DETAILS = "bottom_details"; //$NON-NLS-1$
	
	/**
	 * Language variable used for the "Left Details" detail menu item in the viewer GUI
	 */
	public static final String LEFT_DETAILS = "left_details"; //$NON-NLS-1$
	
	/**
	 * Language variable used for the "Right Details" detail menu item in the viewer GUI
	 */
	public static final String RIGHT_DETAILS = "right_details"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Fullscreen" menu item in the viewer GUI
	 */
	public static final String FULLSCREEN = "fullscreen"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Open Media File" menu item in the viewer GUI
	 */
	public static final String OPEN_MEDIA_FILE = "open_media_file"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Open Secondary File" menu item in the viewer GUI
	 */
	public static final String OPEN_SECONDARY_FILE = "open_secondary_file"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Open DMF" menu item in the viewer GUI
	 */
	public static final String OPEN_DMF = "open_dmf"; //$NON-NLS-1$
	
	/**
	 * Returns an ArrayList of .ini formatted language values.
	 * 
	 * @return Language Values
	 */
	public static ArrayList<String> getValues()
	{
		ArrayList<String> values = new ArrayList<>();
		
		//VIEW BROWSER
		values.add(new String());
		values.add("[VIEW BROWSER]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(VIEWER_TITLE, "Viewer")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(PREVIOUS, "< ^Previous")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(NEXT, "^Next >")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(OFFSET, " - OFF: ")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(RELOAD_DMFS, "^Reload DMFs")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(RELOAD_WITHOUT_INDEXES, "Reload DMFs ^Without Indexes")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(USE_THUMBNAILS, "^Use Thumbnails")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SHOW_ARTISTS, "Show ^Artists")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SHOW_RATINGS, "Show ^Ratings")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SHOW_VIEWS, "Show ^View Counts")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SORT, "^Sort")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SORT_TIME, "Sort by ^Time Published")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SORT_ALPHA, "Sort A^lphabetically")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SORT_RATING, "Sort by ^Rating")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SORT_VIEWS, "Sort by ^View Count")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(GROUP_ARTISTS, "Group ^Artists")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(GROUP_SEQUENCES, "Group Se^quences")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(GROUP_SECTIONS, "Group ^Sections")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(REVERSE_ORDER, "Reverse ^Order")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FILTER, "^Filter")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FILTER_MEDIA, "Filter ^Media")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOADING_PREVIEWS_TITLE, "Loading Previews")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOADING_PREVIEWS, "Loading Previews:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SORTING_DMFS_TITLE, "Sorting DMFs")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SORTING_DMFS, "Sorting DMFs:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FILTERING_DMFS_TITLE, "Filtering DMFs")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FILTERING_DMFS, "Filtering DMFs:")); //$NON-NLS-1$
		
		//VIEWER
		values.add(new String());
		values.add("[VIEWER]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(BRANCHES, "^Branches")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(VIEWS, "Views:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(RATING, "Rating:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DATE, "Date:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(TIME, "Time:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LINK, "Link")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOADING_MEDIA_TITLE, "Loading Media")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LOADING_MEDIA_MESSAGE, "Loading Media:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(HYPERLINK_TITLE, "Hyperlink")); //$NON-NLS-1$
		
		//VIEWER MENUS
		values.add(ParseINI.getAssignmentString(SCALE, "^Scale")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SCALE_FULL, "^Full Size")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SCALE_2D_FIT, "^2-D Fit")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SCALE_2D_STRETCH, "2-D ^Stretch")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SCALE_1D_FIT, "^1-D Fit")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SCALE_1D_STRETCH, "1-D S^tretch")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SCALE_DIRECT, "^Direct Scale")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DIRECT_SCALE_TITLE, "Set Scale Amount")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DIRECT_SCALE_MESSAGE, "Enter direct scale amount:")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DETAILS, "^Details")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(NO_DETAILS, "^No DMF Details")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(TOP_DETAILS, "Details on ^Top")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(BOTTOM_DETAILS, "Details on ^Bottom")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(LEFT_DETAILS, "Details on ^Left Side")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(RIGHT_DETAILS, "Details on ^Right Side")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FULLSCREEN, "^Fullscreen")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(OPEN_MEDIA_FILE, "Open ^Media File")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(OPEN_SECONDARY_FILE, "Open ^Secondary File")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(OPEN_DMF, "Open ^DMF")); //$NON-NLS-1$
		
		return values;
		
	}//METHOD
	
}//CLASS
