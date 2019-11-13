package drakovek.hoarder.file.language;

import java.util.ArrayList;

import drakovek.hoarder.processing.ParseINI;

/**
 * Class containing common language values.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class CommonValues
{
	//COMMON
	
	/**
	 * Language variable for the default title for Frames/Dialogs
	 */
	public static final String TITLE_VALUE = "title_value"; //$NON-NLS-1$
	
	/**
	 * Language variable for "OK"
	 */
	public static final String OK = "ok"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Add"
	 */
	public static final String ADD = "add"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Remove"
	 */
	public static final String REMOVE = "remove"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Yes"
	 */
	public static final String YES = "yes"; //$NON-NLS-1$
	
	/**
	 * Language variable for "No"
	 */
	public static final String NO = "no"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Cancel"
	 */
	public static final String CANCEL = "cancel"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Close"
	 */
	public static final String CLOSE = "close"; //$NON-NLS-1$
	
	/**
	 * Language variable for "File"
	 */
	public static final String FILE = "file"; //$NON-NLS-1$
	
	/**
	 * Language variable for "View"
	 */
	public static final String VIEW = "view"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Open"
	 */
	public static final String OPEN = "open"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Exit"
	 */
	public static final String EXIT = "exit"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Save"
	 */
	public static final String SAVE = "save"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Copy"
	 */
	public static final String COPY = "copy"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Reset"
	 */
	public static final String RESET = "reset"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Restart Program"
	 */
	public static final String RESTART_PROGRAM = "restart_program"; //$NON-NLS-1$
	
	/**
	 * Language variable for indicating non-applicable field
	 */
	public static final String NON_APPLICABLE = "non_applicable"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Case Sensitive"
	 */
	public static final String CASE_SENSITIVE = "case_sensitive"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Downloading"
	 */
	public static final String DOWNLOADING = "downloading"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Canceling" label when canceling a process
	 */
	public static final String CANCELING = "canceling"; //$NON-NLS-1$
	
	/**
	 * Language variable for the "Running" label default when a process is running
	 */
	public static final String RUNNING = "running"; //$NON-NLS-1$
	
	/**
	 * Language variable for dialogs showing that no directory is selected
	 */
	public static final String NO_DIRECTORY_DIALOG = "no_directory_dialog"; //$NON-NLS-1$
	
	/**
	 * Language variable for title of dialog asking whether to add directory to DVK directories list
	 */
	public static final String ADD_DIRECTORY_TITLE = "add_directory_title"; //$NON-NLS-1$
	
	/**
	 * Language variables for messages of dialog asking whether to add directory to DVK directories list
	 */
	public static final String[] ADD_DIRECTORY_MESSAGES = {"add_directory_message_1", "add_directory_message_2"};  //$NON-NLS-1$//$NON-NLS-2$
	
	/**
	 * Language variable for a dialog message asking whether to continue.
	 */
	public static final String CONTINUE_MESSAGE = "continue_message"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Downloaded" message
	 */
	public static final String DOWNLOADED = "downloaded"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Finished" message
	 */
	public static final String FINISHED = "finished"; //$NON-NLS-1$
	
	/**
	 * Language variable for "Download Failed" message
	 */
	public static final String DOWNLOAD_FAILED = "download_failed"; //$NON-NLS-1$
	
	/**
	 * Language variable for showing a given file has already been downloaded
	 */
	public static final String ALREADY_DOWNLOADED = "already_downloaded"; //$NON-NLS-1$
	
	/**
	 * Language variable for showing a given URL is invalid
	 */
	public static final String INVALID_URL = "invalid_url"; //$NON-NLS-1$
	
	/**
	 * Language variable for title of the "Process Running" error message.
	 */
	public static final String PROCESS_RUNNING = "process_running"; //$NON-NLS-1$
	
	/**
	 * Language variables for message of the "Process Running" error message.
	 */
	public static final String[] PROCESS_RUNNING_MESSAGES = {"cancel_process_message1", "cancel_process_message2"};  //$NON-NLS-1$//$NON-NLS-2$
	
	
	/**
	 * Language variable for "AM" in clock time formatting
	 */
	public static final String AM = "am"; //$NON-NLS-1$
	
	/**
	 * Language variable for "PM" in clock time formatting
	 */
	public static final String PM = "pm"; //$NON-NLS-1$
	
	/**
	 * Language variable for the symbol to use when indicating seconds in a clock time
	 */
	public static final String SECONDS_SUFFIX = "seconds_suffix"; //$NON-NLS-1$
	
	/**
	 * Language variables for months of the year
	 */
	public static final String[] MONTHS = {"january", "february", "march",  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
										   "april", "may", "june", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
										   "july", "august", "september", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
										   "october", "november", "december"};   //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	
	
	/**
	 * Returns an ArrayList of .ini formatted language values.
	 * 
	 * @return Language Values
	 */
	public static final ArrayList<String> getValues()
	{
		ArrayList<String> values = new ArrayList<>();
		
		//COMMON
		values.add(new String());
		values.add("[COMMON]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(TITLE_VALUE, "Drak's Digital Hoarder")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(OK, "^OK")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CANCEL, "^Cancel")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CLOSE, "^Close")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ADD, "^Add")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(REMOVE, "^Remove")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(YES, "^Yes")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(NO, "^No")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SAVE, "^Save")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(COPY, "Cop^y")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(RESET, "^Reset")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(RESTART_PROGRAM, "^Restart Program")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(NON_APPLICABLE, "N/A")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CASE_SENSITIVE, "Case ^Sensitive")); //$NON-NLS-1$
		
		//COMMON MENU
		values.add(new String());
		values.add("[COMMON MENU]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FILE, "^File")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(VIEW, "^View")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(OPEN, "^Open")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(EXIT, "^Exit")); //$NON-NLS-1$
		
		//COMMON DIALOG
		values.add(new String());
		values.add("[COMMON DIALOG]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DOWNLOADING, "Downloading")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CANCELING, "Canceling...")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(RUNNING, "Running...")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(NO_DIRECTORY_DIALOG, "No Directory Selected")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ADD_DIRECTORY_TITLE, "Add Directory?")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ADD_DIRECTORY_MESSAGES[0], "Selected directory is not listed as DVK directory.")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ADD_DIRECTORY_MESSAGES[1], "Add to program's DVK directory list?")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(CONTINUE_MESSAGE, "Continue?")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DOWNLOADED, "Downloaded")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(FINISHED, "Finished")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(DOWNLOAD_FAILED, "Download Failed")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(ALREADY_DOWNLOADED, "Already Downloaded")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(INVALID_URL, "Invalid URL")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(PROCESS_RUNNING, "Process Running")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(PROCESS_RUNNING_MESSAGES[0], "A process is running.")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(PROCESS_RUNNING_MESSAGES[1], "Cancel process or wait until completion before closing.")); //$NON-NLS-1$
				
				
		//TIME
		values.add(new String());
		values.add("[TIME]"); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(AM, "AM")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(PM, "PM")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(SECONDS_SUFFIX, "s")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MONTHS[0], "January")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MONTHS[1], "February")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MONTHS[2], "March")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MONTHS[3], "April")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MONTHS[4], "May")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MONTHS[5], "June")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MONTHS[6], "July")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MONTHS[7], "August")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MONTHS[8], "September")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MONTHS[9], "October")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MONTHS[10], "November")); //$NON-NLS-1$
		values.add(ParseINI.getAssignmentString(MONTHS[11], "December")); //$NON-NLS-1$
				
		return values;
		
	}//METHOD
	
}//CLASS
