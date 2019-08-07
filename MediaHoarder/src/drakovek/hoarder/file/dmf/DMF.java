package drakovek.hoarder.file.dmf;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.processing.ParseINI;
import drakovek.hoarder.processing.StringMethods;

/**
 * Contains methods for reading writing and handling a single Drakovek Media File (DMF)
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DMF
{
	/**
	 * Empty ID variable used identify when there are no DMFs either following or preceding the current DMF in a sequence.
	 * 
	 * @since 2.0
	 */
	public static final String EMPTY_ID = "XX"; //$NON-NLS-1$
	
	/**
	 * Extension used for DMF Files
	 * 
	 * @since 2.0
	 */
	public static final String DMF_EXTENSION = ".dmf"; //$NON-NLS-1$
	
	/**
	 * Main header used at the top of DMF Files
	 * 
	 * @since 2.0
	 */
	private static final String DMF_HEADER = "[DMF]"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF ID
	 * 
	 * @since 2.0
	 */
	private static final String ID = "id"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF Title
	 * 
	 * @since 2.0
	 */
	private static final String TITLE = "title"; //$NON-NLS-1$
	
	/**
	 * INI variable for the artist(s) of the DMF media
	 * 
	 * @since 2.0
	 */
	private static final String ARTISTS = "artists"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for DMF artists, used for reading old DMFs
	 * 
	 * @since 2.0
	 */
	private static final String OLD_ARTIST = "artist"; //$NON-NLS-1$
	
	/**
	 * INI variable for the time of publishing for the DMF media
	 * 
	 * @since 2.0
	 */
	private static final String TIME = "time"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the time of publishing, used for reading old DMFs
	 * 
	 * @since 2.0
	 */
	private static final String OLD_DATE = "date"; //$NON-NLS-1$
	
	/**
	 * INI variable for all the original web tags for the DMF media
	 * 
	 * @since 2.0
	 */
	private static final String WEB_TAGS = "web_tags"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for original tags, used for reading old DMFs
	 * 
	 * @since 2.0
	 */
	private static final String OLD_OTAGS = "oTags"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF's description
	 * 
	 * @since 2.0
	 */
	private static final String DESCRIPTION = "description"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the description, used for reading old DMFs
	 * 
	 * @since 2.0
	 */
	private static final String OLD_DESC = "desc"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF's original page URL
	 * 
	 * @since 2.0
	 */
	private static final String PAGE_URL = "page_url"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the page URL, used for reading old DMFs
	 * 
	 * @since 2.0
	 */
	private static final String OLD_PAGE_URL = "pageURL"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF's original media URL
	 * 
	 * @since 2.0
	 */
	private static final String MEDIA_URL = "media_url"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the media URL, used for reading old DMFs
	 * 
	 * @since 2.0
	 */
	private static final String OLD_MEDIA_URL = "mediaURL"; //$NON-NLS-1$
	
	/**
	 * DMF INI variable for the secondary media URL
	 * 
	 * @since 2.0
	 */
	private static final String SECONDARY_URL = "secondary_url"; //$NON-NLS-1$
	
	/**
	 * INI variable for the media file linked to the DMF
	 * 
	 * @since 2.0
	 */
	private static final String MEDIA_FILE = "media_file"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the media file, used for reading old DMFs
	 * 
	 * @since 2.0
	 */
	private static final String OLD_FILENAME = "filename"; //$NON-NLS-1$
	
	/**
	 * DMF INI variable for the secondary media file
	 * 
	 * @since 2.0
	 */
	private static final String SECONDARY_FILE = "secondary_file"; //$NON-NLS-1$
	
	/**
	 * INI variable for the ID(s) of the previous DMF(s)
	 * 
	 * @since 2.0
	 */
	private static final String LAST_IDS = "last_ids"; //$NON-NLS-1$
	
	/**
	 * INI variable for the ID(s) of the next DMF(s)
	 * 
	 * @since 2.0
	 */
	private static final String NEXT_IDS = "next_ids"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether the DMF is the first in a section
	 * 
	 * @since 2.0
	 */
	private static final String FIRST = "first"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether the DMF is the last in a section
	 * 
	 * @since 2.0
	 */
	private static final String LAST = "last"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for all DMF sequence data.
	 * 
	 * @since 2.0
	 */
	private static final String OLD_SEQ_DATA = "seqData"; //$NON-NLS-1$
	
	/**
	 * INI variable for the title of the current sequence
	 * 
	 * @since 2.0
	 */
	private static final String SEQUENCE_TITLE = "sequence_title"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the sequence title, used for reading old DMFs
	 * 
	 * @since 2.0
	 */
	private static final String OLD_SEQ_TITLE = "seqTitle"; //$NON-NLS-1$
	
	/**
	 * INI variable for the title of the current section of the current sequence.
	 * 
	 * @since 2.0
	 */
	private static final String SECTION_TITLE = "section_title"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the section(Sub-sequence) title, used for reading old DMFs
	 * 
	 * @since 2.0
	 */
	private static final String OLD_SUB_SEQ_TITLE = "subSeqTitle"; //$NON-NLS-1$
	
	/**
	 * INI variable for titles of sequence branches coming from this DMF
	 * 
	 * @since 2.0
	 */
	private static final String BRANCH_TITLES = "branch_titles"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF user rating
	 * 
	 * @since 2.0
	 */
	private static final String RATING = "rating"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF's user tags
	 * 
	 * @since 2.0
	 */
	private static final String USER_TAGS = "user_tags"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the user tags, used for reading old DMFs
	 */
	private static final String OLD_UTAGS = "uTags"; //$NON-NLS-1$
	
	//DMF
	
	/**
	 * File for the currently selected DMF
	 * 
	 * @since 2.0
	 */
	private File dmfFile;
	
	/**
	 * Unique ID for the currently selected DMF
	 * 
	 * @since 2.0
	 */
	private String id;
	
	/**
	 * Title of the DMF media
	 * 
	 * @since 2.0
	 */
	private String title;
	
	/**
	 * Artist(s) of the DMF media
	 * 
	 * @since 2.0
	 */
	private String[] artists;
	
	/**
	 * Time of publishing for the DMF media (Structured YYYYMMDDhhmm. Example: April 22, 2001 @ 4:30PM = 200104211630)
	 * 
	 * @since 2.0
	 */
	private long time;
	
	/**
	 * Original web tags for the DMF
	 * 
	 * @since 2.0
	 */
	private String[] webTags;
	
	/**
	 * Description of the DMF media
	 * 
	 * @since 2.0
	 */
	private String description;
	
	/**
	 * The URL for the page that the DMF originates from
	 * 
	 * @since 2.0
	 */
	private String pageURL;
	
	/**
	 * The URL for the direct media download URL that the DMF originates from
	 * 
	 * @since 2.0
	 */
	private String mediaURL;
	
	/**
	 * URL for the direct media download URL for the secondary media file
	 * 
	 * @since 2.0
	 */
	private String secondaryURL;
	
	/**
	 * Media File linked to the DMF
	 * 
	 * @since 2.0
	 */
	private File mediaFile;
	
	/**
	 * Media File linked to the DMF for use as a secondary media file
	 * 
	 * @since 2.0
	 */
	private File secondaryFile;
	
	/**
	 * Array of IDs directly preceding the current DMF in a sequence. If there are multiple IDs, this means the DMF comes directly after multiple branching paths.
	 * 
	 * @since 2.0
	 */
	private String[] lastIDs;
	
	/**
	 * Array of IDs directly proceeding the current DMF in a sequence. If there are multiple IDs, this means the DMF leads to multiple branching paths.
	 * 
	 * @since 2.0
	 */
	private String[] nextIDs;
	
	/**
	 * Whether the current DMF is the first in a sequence section.
	 * 
	 * @since 2.0
	 */
	private boolean first;
	
	/**
	 * Whether the current DMF is the last in a sequence section.
	 * 
	 * @since 2.0
	 */
	private boolean last;
	
	/**
	 * Title for the sequence the current DMF is a part of, if applicable
	 * 
	 * @since 2.0
	 */
	private String sequenceTitle;
	
	/**
	 * Title for the section of a sequence the current DMF is a part of, if applicable
	 * 
	 * @since 2.0
	 */
	private String sectionTitle;
	
	/**
	 * Array of titles for sequence branches coming from this DMF, if applicable.
	 * 
	 * @since 2.0
	 */
	private String[] branchTitles;
	
	/**
	 * User given rating for the DMF, ranging from 1 to 5
	 * 
	 * @since 2.0
	 */
	private int rating;
	
	/**
	 * Tags for the DMF given by the user
	 * 
	 * @since 2.0
	 */
	private String[] userTags;
	
	/**
	 * Initializes DMF to represent an empty DMF file.
	 * 
	 * @since 2.0
	 */
	public DMF()
	{
		dmfFile = null;
		clearDMF();
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes DMF to represent given DMF file.
	 * 
	 * @param dmfFile DMF File to load
	 * @since 2.0
	 */
	public DMF(final File dmfFile)
	{
		this.dmfFile = dmfFile;
		loadDMF();
		
	}//CONSTRUCTOR
	
	/**
	 * Clears all DMF variables so this DMF object represents an empty DMF File.
	 * 
	 * @since 2.0
	 */
	private void clearDMF()
	{
		//DMF
		id = null;
		
		//INFO
		title = null;
		artists = null;
		time = 0L;
		webTags = null;
		description = null;
		
		//WEB
		pageURL = null;
		mediaURL = null;
		secondaryURL = null;
		
		//FILE
		mediaFile = null;
		secondaryURL = null;
		lastIDs = null;
		nextIDs = null;
		first = false;
		last = false;
		
		//USER
		sequenceTitle = null;
		sectionTitle = null;
		branchTitles = null;
		rating = 0;
		userTags = null;
		
	}//METHOD
	
	/**
	 * Loads DMF info from dmfFile so the DMF object represents a given DMF File.
	 * 
	 * @since 2.0
	 */
	public void loadDMF()
	{
		clearDMF();
		if(dmfFile != null && dmfFile.getAbsolutePath().endsWith(DMF_EXTENSION))
		{
			ArrayList<String> contents = DReader.readFile(dmfFile);
			setID(ParseINI.getStringValue(DMF_HEADER, ID, contents, getID()));
			
			//IF ID TAG EXISTS UNDER DMF HEADER, SAFE TO CONTINUE READING
			if(getID() != null && getID().length() > 0)
			{
				//INFO
				setTitle(ParseINI.getStringValue(null, TITLE, contents, null));
				setArtists(ParseINI.getStringListValue(null, ARTISTS, contents, null));
				if(getArtists() == null)
				{
					//IF NEW ARTISTS VARIABLE DOESN'T WORK, USE OLD ARTIST VARIABLE
					setArtists(ParseINI.getStringListValue(null, OLD_ARTIST, contents, null));
					
				}//IF
				
				setTime(ParseINI.getStringValue(null, TIME, contents, new String()));
				if(time == 0L)
				{
					//IF NEW TIME VARIABLE DOESN'T WORK, USE OLD DATE VARIABLE
					setTime(ParseINI.getStringValue(null, OLD_DATE, contents, new String()));
					
				}//IF
				
				setWebTags(ParseINI.getStringListValue(null, WEB_TAGS, contents, null));
				if(getWebTags() == null)
				{
					//IF NEW WEB TAG VARIABLE DOESN'T WORK, USE OLD OTAG VARIABLE
					setWebTags(ParseINI.getStringListValue(null, OLD_OTAGS, contents, null));
					
				}//IF
				
				setDescription(ParseINI.getStringValue(null, DESCRIPTION, contents, null));
				if(getDescription() == null)
				{
					//IF NEW DESCRIPTION VARIABLE DOESN'T WORK, USE OLD DESC VARIABLE
					setDescription(ParseINI.getStringValue(null, OLD_DESC, contents, null));
					
				}//IF
				
				//WEB
				setPageURL(ParseINI.getStringValue(null, PAGE_URL, contents, null));
				if(getPageURL() == null)
				{
					//IF NEW PAGE URL VARIABLE DOESN'T WORK, USE OLD PAGE URL VARIABLE
					setPageURL(ParseINI.getStringValue(null, OLD_PAGE_URL, contents, null));
				
				}//IF
				
				setMediaURL(ParseINI.getStringValue(null, MEDIA_URL, contents, null));
				if(getMediaURL() == null)
				{
					//IF NEW MEDIA URL VARIABLE DOESN'T WORK, USE OLD MEDIA URL VARIABLE
					setMediaURL(ParseINI.getStringValue(null, OLD_MEDIA_URL, contents, null));
					
				}//IF
				
				setSecondaryURL(ParseINI.getStringValue(null, SECONDARY_URL, contents, null));
				
				//FILE
				setMediaFile(ParseINI.getStringValue(null, MEDIA_FILE, contents, null));
				if(getMediaFile() == null || !getMediaFile().exists())
				{
					//IF NEW MEDIA FILE VARIABLE DOESN'T WORK, USE OLD FILENAME VARIABLE
					setMediaFile(ParseINI.getStringValue(null, OLD_FILENAME, contents, null));
					
				}//IF
				
				setSecondaryFile(ParseINI.getStringValue(null, SECONDARY_FILE, contents, null));
				
				setLastIDs(ParseINI.getStringListValue(null, LAST_IDS, contents, null));
				if(getLastIDs() != null)
				{
					//FILE IS USING NEW STANDARD FOR RECORDING SEQUENCES
					setNextIDs(ParseINI.getStringListValue(null, NEXT_IDS, contents, null));
					setFirst(ParseINI.getBooleanValue(null, FIRST, contents, first));
					setLast(ParseINI.getBooleanValue(null, LAST, contents, last));
					
				}//IF
				else
				{
					//IF NEW SEQUENCE DATA VARIABLES DON'T WORK, USE OLD SEQ DATA VARIABLE
					setSequenceData(ParseINI.getStringValue(null, OLD_SEQ_DATA, contents, null));
					
				}//ELSE
				
				//USER
				setSequenceTitle(ParseINI.getStringValue(null, SEQUENCE_TITLE, contents, null));
				if(getSequenceTitle() == null)
				{
					//IF NEW SEQUENCE TITLE VARIABLE DOESN'T WORK, USE OLD SEQ TITLE VARIABLE
					setSequenceTitle(ParseINI.getStringValue(null, OLD_SEQ_TITLE, contents, null));
					
				}//IF
				
				setSectionTitle(ParseINI.getStringValue(null, SECTION_TITLE, contents, null));
				if(getSectionTitle() == null)
				{
					//IF NEW SECTION TITLE VARIABLE DOESN'T WORK, USE OLD SUB SEQ TITLE VARIABLE
					setSectionTitle(ParseINI.getStringValue(null, OLD_SUB_SEQ_TITLE, contents, null));
					
				}//IF
				
				setBranchTitles(ParseINI.getStringListValue(null, BRANCH_TITLES, contents, null));
				setRating(ParseINI.getIntValue(null, RATING, contents, 0));
				
				setUserTags(ParseINI.getStringListValue(null, USER_TAGS, contents, null));
				if(getUserTags() == null)
				{
					//IF NEW USER TAG VARIABLE DOESN'T WORK, USE OLD UTAG VARIABLE
					setUserTags(ParseINI.getStringListValue(null, OLD_UTAGS, contents, null));
					
				}//IF
				
			}//IF
			
		}//IF
		
	}//METHOD
	
	/**
	 * Writes a DMF file to dmfFile
	 *
	 * @return Whether the file was successfully written
	 * @since 2.0
	 */
	public boolean writeDMF()
	{
		if(isValid())
		{
			ArrayList<String> contents = new ArrayList<>();
			contents.add(DMF_HEADER);
			contents.add(ParseINI.getAssignmentString(ID, getID()));
			
			//INFO
			contents.add(new String());
			contents.add("[INFO]"); //$NON-NLS-1$
			
			if(title != null)
			{
				contents.add(ParseINI.getAssignmentString(TITLE, getTitle()));
			
			}//IF
			
			if(getArtists() != null && getArtists().length > 0)
			{
				contents.add(ParseINI.getAssignmentString(ARTISTS, getArtists()));
				
			}//IF
			
			if(getTime() != 0L)
			{
				contents.add(ParseINI.getAssignmentString(TIME, getTimeString()));
				
			}//IF
			
			if(getWebTags() != null && getWebTags().length > 0)
			{
				contents.add(ParseINI.getAssignmentString(WEB_TAGS, getWebTags()));
				
			}//IF
			
			if(getDescription() != null && getDescription().length() > 0)
			{
				contents.add(ParseINI.getAssignmentString(DESCRIPTION, getDescription()));
				
			}//IF
			
			//WEB
			ArrayList<String> web = new ArrayList<>();
			if(getPageURL() != null && getPageURL().length() > 0)
			{
				web.add(ParseINI.getAssignmentString(PAGE_URL, getPageURL()));
				
			}//IF
			
			if(getMediaURL() != null && getMediaURL().length() > 0)
			{
				web.add(ParseINI.getAssignmentString(MEDIA_URL, getMediaURL()));
				
			}//IF
			
			if(getSecondaryURL() != null && getSecondaryURL().length() > 0)
			{
				web.add(ParseINI.getAssignmentString(SECONDARY_URL, getSecondaryURL()));
				
			}//IF
			
			if(web.size() > 0)
			{
				contents.add(new String());
				contents.add("[WEB]"); //$NON-NLS-1$
				contents.addAll(web);
				
			}//IF
			
			//FILE
			contents.add(new String());
			contents.add("[FILE]"); //$NON-NLS-1$
			contents.add(ParseINI.getAssignmentString(MEDIA_FILE, getMediaFile().getName()));
			
			if(getSecondaryFile() != null)
			{
				contents.add(ParseINI.getAssignmentString(SECONDARY_FILE, getSecondaryFile().getName()));
			
			}//IF
			
			if(getLastIDs() != null && getLastIDs().length > 0)
			{
				contents.add(ParseINI.getAssignmentString(LAST_IDS, getLastIDs()));
				
			}//IF
			
			if(getNextIDs() != null && getNextIDs().length > 0)
			{
				contents.add(ParseINI.getAssignmentString(NEXT_IDS, getNextIDs()));
				
			}//IF
			
			if((getLastIDs() != null && getLastIDs().length > 0 && !getLastIDs()[0].equals(EMPTY_ID)) || (getNextIDs() != null && getNextIDs().length > 0 && !getNextIDs()[0].equals(EMPTY_ID)))
			{
				contents.add(ParseINI.getAssignmentString(FIRST, isFirstInSection()));
				contents.add(ParseINI.getAssignmentString(LAST, isLastInSection()));
				
			}//IF
			
			//USER
			ArrayList<String> user = new ArrayList<>();
			if(getSequenceTitle() != null && getSequenceTitle().length() > 0)
			{
				user.add(ParseINI.getAssignmentString(SEQUENCE_TITLE, getSequenceTitle()));
				
			}//IF
			
			if(getSectionTitle() != null && getSectionTitle().length() > 0)
			{
				user.add(ParseINI.getAssignmentString(SECTION_TITLE, getSectionTitle()));
				
			}//IF
			
			if(getBranchTitles() != null && getBranchTitles().length > 0)
			{
				user.add(ParseINI.getAssignmentString(BRANCH_TITLES, getBranchTitles()));
				
			}//IF
			
			if(getRating() > 0)
			{
				user.add(ParseINI.getAssignmentString(RATING, getRating()));
				
			}//IF
			
			if(getUserTags() != null && getUserTags().length > 0)
			{
				user.add(ParseINI.getAssignmentString(USER_TAGS, getUserTags()));
				
			}//IF
			
			if(user.size() > 0)
			{
				contents.add(new String());
				contents.add("[USER]"); //$NON-NLS-1$
				contents.addAll(user);
				
			}//IF
			
			//WRITE TO FILE
			DWriter.writeToFile(dmfFile, contents);
			if(dmfFile.exists())
			{
				return true;
				
			}//IF
			
		}//IF
		
		return false;
		
	}//METHOD
	
	/**
	 * Checks if the current state of the DMF object can be written to make a valid DMF. Must have a valid name for a file, and a non-empty ID and media file.
	 *
	 * @return Whether the current DMF is valid
	 * @since 2.0
	 */
	public boolean isValid()
	{
		boolean valid = true;
		
		if(dmfFile == null || dmfFile.isDirectory() || !dmfFile.getName().endsWith(DMF_EXTENSION))
		{
			valid = false;
			
		}//IF
		
		if(getID() == null || getID().length() == 0)
		{
			valid = false;
			
		}//IF
		
		if(getMediaFile() == null)
		{
			valid = false;
			
		}//IF
		
		return valid;
		
	}//METHOD
	
	//GETTERS AND SETTERS
	
	/**
	 * Returns dmfFile
	 * 
	 * @return dmfFile
	 * @since 2.0
	 */
	public File getDmfFile()
	{
		return dmfFile;
		
	}//METHOD
	
	/**
	 * Sets dmfFile.
	 * 
	 * @param dmfFile dmfFile
	 * @since 2.0
	 */
	public void setDmfFile(final File dmfFile)
	{
		this.dmfFile = dmfFile;
		
	}//METHOD
	
	/**
	 * Returns the DMF ID
	 * 
	 * @return DMF ID
	 * @since 2.0
	 */
	public String getID()
	{
		return id;
		
	}//METHOD
	
	/**
	 * Sets the DMF ID
	 * 
	 * @param id DMF ID
	 * @since 2.0
	 */
	public void setID(final String id)
	{
		this.id = id;
		
	}//METHOD
	
	/**
	 * Returns the DMF Title
	 * 
	 * @return DMF Title
	 * @since 2.0
	 */
	public String getTitle()
	{
		return title;
		
	}//METHOD
	
	/**
	 * Sets the DMF Title
	 * 
	 * @param title DMF Title
	 * @since 2.0
	 */
	public void setTitle(final String title)
	{
		this.title = title;
		
	}//METHOD
	
	/**
	 * Gets the DMF Artist(s)
	 * 
	 * @return DMF Artists
	 * @since 2.0
	 */
	public String[] getArtists()
	{
		return artists;
		
	}//METHOD
	
	/**
	 * Sets the DMF Artists(s)
	 * 
	 * @param artists DMF Artists
	 * @since 2.0
	 */
	public void setArtists(final ArrayList<String> artists)
	{	
		if(artists != null && artists.size() > 0)
		{
			this.artists = StringMethods.arrayListToArray(artists);
			
		}//IF
		else
		{
			this.artists = null;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Sets the DMF Artist variable from a single artist String
	 * 
	 * @param artist DMF Artist
	 * @since 2.0
	 */
	public void setArtist(final String artist)
	{
		if(artist != null && artist.length() > 0)
		{
			artists = new String[1];
			artists[0] = artist;
			
		}//IF
		else
		{
			artists = null;
		
		}//ELSE
		
	}//METHOD
	
	/**
	 * Sets the DMF Time
	 * 
	 * @param time DMF Time
	 * @since 2.0
	 */
	public void setTime(final long time)
	{
		this.time = time;
		
	}//METHOD
	
	/**
	 * Sets the DMF Time from a DMF String
	 * 
	 * @param timeString DMF Time String
	 * @since 2.0
	 */
	private void setTime(final String timeString)
	{
		
		try
		{
			time = Long.parseLong(timeString);
			
		}//TRY
		catch(NumberFormatException e)
		{
			if(timeString.length() == 16)
			{
				setTime(timeString.substring(0, 4), timeString.substring(5, 7), timeString.substring(8, 10), timeString.substring(11, 13), timeString.substring(14));
				
			}//IF
			
		}//CATCH
		
		if(time < 101010000L)
		{
			time = 0L;
			
		}//IF

	}//METHOD
	
	/**
	 * Sets the DMF Time from String values.
	 * 
	 * @param yearString Year String
	 * @param monthString Month String
	 * @param dayString Day String
	 * @param hourString Hour String
	 * @param minuteString Minute String
	 * @since 2.0
	 */
	public void setTime(final String yearString, final String monthString, final String dayString, final String hourString, final String minuteString)
	{
		long returnTime = 0;
		boolean isValid = true;
		try
		{
			int year = Integer.parseInt(yearString);
			int month = Integer.parseInt(monthString);
			int day = Integer.parseInt(dayString);
			int hour = Integer.parseInt(hourString);
			int minute = Integer.parseInt(minuteString);
			
			if(
					(year < 1) ||
					(month < 1 || month > 12) ||
					(day < 1 || day > 31) ||
					(hour < 0 || hour > 23) ||
					(minute < 0 || minute > 59)
			)
			{
				isValid = false;
			}//IF
			
			returnTime = Long.parseLong(StringMethods.extendNumberString(year, 4) + StringMethods.extendNumberString(month, 2) + StringMethods.extendNumberString(day, 2) + StringMethods.extendNumberString(hour, 2) + StringMethods.extendNumberString(minute, 2));
			
		}//TRY
		catch(NumberFormatException e)
		{
			isValid = false;
		
		}//CATCH
		
		if(!isValid)
		{
			returnTime = 0;
			
		}//IF
		
		time = returnTime;
	
	}//METHOD
	
	/**
	 * Returns the DMF Time
	 * 
	 * @return DMF Time
	 * @since 2.0
	 */
	public long getTime()
	{
		return time;
		
	}//METHOD
	
	/**
	 * Gets DMF Time as a String
	 * 
	 * @return String representation of DMF Time
	 * @since 2.0
	 */
	public String getTimeString()
	{
		String longTimeString = StringMethods.extendNumberString(Long.toString(time), 12);
		
		return longTimeString.substring(0, 4) + Character.toString('/') + longTimeString.substring(4, 6) + Character.toString('/') + longTimeString.substring(6, 8) + Character.toString('|') + longTimeString.substring(8, 10) + Character.toString(':') + longTimeString.substring(10);
	
	}//METHOD
	
	/**
	 * Sets the DMF's web tags
	 * 
	 * @param webTags DMF Web Tags
	 * @since 2.0
	 */
	public void setWebTags(final ArrayList<String> webTags)
	{
		if(webTags != null && webTags.size() > 0)
		{
			this.webTags = StringMethods.arrayListToArray(webTags);
		
		}//IF
		else
		{
			this.webTags = null;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Gets the DMF's web tags
	 * 
	 * @return DMF Web Tags
	 * @since 2.0
	 */
	public String[] getWebTags()
	{
		return webTags;
		
	}//METHOD
	
	/**
	 * Sets the description for the current DMF
	 * 
	 * @param description DMF Description
	 * @since 2.0
	 */
	public void setDescription(final String description)
	{
		this.description = description;
		
	}//METHOD
	
	/**
	 * Gets the description from the current DMF
	 * 
	 * @return DMF Description
	 * @since 2.0
	 */
	public String getDescription()
	{
		return description;
		
	}//METHOD
	
	/**
	 * Sets the DMF's Page URL
	 * 
	 * @param pageURL Page URL
	 * @since 2.0
	 */
	public void setPageURL(final String pageURL)
	{
		this.pageURL = pageURL;
		
	}//METHOD
	
	/**
	 * Returns the DMF's Page URL
	 * 
	 * @return Page URL
	 * @since 2.0
	 */
	public String getPageURL()
	{
		return pageURL;
		
	}//METHOD
	
	/**
	 * Sets  the DMF's Media URL
	 * 
	 * @param mediaURL Media URL
	 * @since 2.0
	 */
	public void setMediaURL(final String mediaURL)
	{
		this.mediaURL = mediaURL;
		
	}//METHOD
	
	/**
	 * Returns the DMF's Media URL
	 * 
	 * @return Media URL
	 * @since 2.0
	 */
	public String getMediaURL()
	{
		return mediaURL;
		
	}//METHOD
	
	/**
	 * Sets the secondary URL.
	 * 
	 * @param secondaryURL Secondary URL
	 * @since 2.0
	 */
	public void setSecondaryURL(final String secondaryURL)
	{
		this.secondaryURL = secondaryURL;
		
	}//METHOD
	
	/**
	 * Returns the secondary URL.
	 * 
	 * @return Secondary URL
	 * @since 2.0
	 */
	public String getSecondaryURL()
	{
		return secondaryURL;
		
	}//METHOD
	
	/**
	 * Sets the linked media file.
	 * 
	 * @param mediaFile Media File
	 * @since 2.0
	 */
	public void setMediaFile(final File mediaFile)
	{
		this.mediaFile = mediaFile;
		
	}//METHOD
	
	/**
	 * Sets the linked media file based on a filename. Uses the same directory as the DMF File.
	 * 
	 * @param filename Name of the Media File
	 * @since 2.0
	 */
	public void setMediaFile(final String filename)
	{
		boolean failed = true;
		if(dmfFile != null && filename!= null && filename.length() > 0)
		{
			File parent = dmfFile.getParentFile();
			if(parent != null && parent.isDirectory())
			{
				mediaFile = new File(parent, filename);
				failed = false;
				
			}//IF
			
		}//IF
		
		if(failed)
		{
			mediaFile = null;
		
		}//ELSE
		
	}//METHOD
	
	/**
	 * Gets the linked media file.
	 * 
	 * @return Media File
	 * @since 2.0
	 */
	public File getMediaFile()
	{
		return mediaFile;
		
	}//METHOD
	
	/**
	 * Sets the secondary File.
	 * 
	 * @param secondaryFile Secondary File
	 * @since 2.0
	 */
	public void setSecondaryFile(final File secondaryFile)
	{
		this.secondaryFile = secondaryFile;
		
	}//METHOD
	

	/**
	 * Sets the linked secondary media file based on a filename. Uses the same directory as the DMF File.
	 * 
	 * @param filename Name of the Secondary Media File
	 * @since 2.0
	 */
	public void setSecondaryFile(final String filename)
	{
		boolean failed = true;
		if(dmfFile != null && filename!= null && filename.length() > 0)
		{
			File parent = dmfFile.getParentFile();
			if(parent != null && parent.isDirectory())
			{
				secondaryFile = new File(parent, filename);
				failed = false;
				
			}//IF
			
		}//IF
		
		if(failed)
		{
			secondaryFile = null;
		
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns the secondary file.
	 * 
	 * @return Secondary File
	 * @since 2.0
	 */
	public File getSecondaryFile()
	{
		return secondaryFile;
		
	}//METHOD
	
	/**
	 * Sets the IDs preceding the current DMF
	 * 
	 * @param lastIDs Last IDs
	 * @since 2.0
	 */
	public void setLastIDs(final ArrayList<String> lastIDs)
	{
		if(lastIDs != null && lastIDs.size() > 0)
		{
			this.lastIDs = StringMethods.arrayListToArray(lastIDs);
			
		}//IF
		else
		{
			this.lastIDs = null;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Sets a single ID preceding the current DMF
	 * 
	 * @param lastID Last ID
	 * @since 2.0
	 */
	public void setLastID(final String lastID)
	{
		if(lastID != null && lastID.length() > 0)
		{
			lastIDs = new String[1];
			lastIDs[0] = lastID;
			
		}//IF
		else
		{
			lastIDs = null;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns a list of IDs before the current DMF in a sequence.
	 * 
	 * @return Last IDs
	 * @since 2.0
	 */
	public String[] getLastIDs()
	{
		return lastIDs;
		
	}//METHOD
	
	/**
	 * Sets the IDs after the current DMF
	 * 
	 * @param nextIDs Next IDs
	 * @since 2.0
	 */
	public void setNextIDs(final ArrayList<String> nextIDs)
	{
		if(nextIDs != null && nextIDs.size() > 0)
		{
			this.nextIDs = StringMethods.arrayListToArray(nextIDs);
			
		}//IF
		else
		{
			this.nextIDs = null;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Sets a single ID after the current DMF
	 * 
	 * @param nextID Next ID
	 * @since 2.0
	 */
	public void setNextID(final String nextID)
	{
		if(nextID != null && nextID.length() > 0)
		{
			nextIDs = new String[1];
			nextIDs[0] = nextID;
		
		}//IF
		else
		{
			nextIDs = null;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns a list of IDs after the current DMF in a sequence.
	 * 
	 * @return Next IDs
	 * @since 2.0
	 */
	public String[] getNextIDs()
	{
		return nextIDs;
		
	}//METHOD
	
	/**
	 * Sets if the current DMF is the first in a section.
	 * 
	 * @param first First in Section
	 * @since 2.0
	 */
	public void setFirst(final boolean first)
	{
		this.first = first;
		
	}//METHOD
	
	/**
	 * Returns whether the current DMF is the first in a section.
	 * 
	 * @return First in Section
	 * @since 2.0
	 */
	public boolean isFirstInSection()
	{
		return first;
		
	}//METHOD
	
	/**
	 * Sets if the current DMF is the last in a section.
	 * 
	 * @param last Last in Section
	 * @since 2.0
	 */
	public void setLast(final boolean last)
	{
		this.last = last;
		
	}//METHOD
	
	/**
	 * Returns whether the current DMF is the last in a section.
	 * 
	 * @return Last in Section
	 * @since 2.0
	 */
	public boolean isLastInSection()
	{
		return last;
		
	}//METHOD
	
	/**
	 * Sets all the sequence data based on the old DMF sequence data standards.
	 * 
	 * @param sequenceData Sequence Data String
	 * @since 2.0
	 */
	private void setSequenceData(final String sequenceData)
	{
		try
		{
			int start;
			int end;
			for(start = 0; sequenceData.charAt(start) != ','; start++);
			setLastID(sequenceData.substring(0, start).toUpperCase()); start++;
			for(end = start; end < sequenceData.length() && sequenceData.charAt(end) != ':'; end++);
			setNextID(sequenceData.substring(start, end)); end++;
			if(end < sequenceData.length())
			{
				setFirst(sequenceData.substring(end).contains(Character.toString('F')));
				setLast(sequenceData.substring(end).contains(Character.toString('L')));
				
			}//IF
			else
			{
				setFirst(false);
				setLast(false);
				
			}//ELSE
			
		}//TRY
		catch(Exception e)
		{
			setLastIDs(new ArrayList<String>());
			setNextIDs(new ArrayList<String>());
			setFirst(false);
			setLast(false);
			
		}//CATCH
		
	}//METHOD
	
	/**
	 * Sets the sequence title.
	 * 
	 * @param sequenceTitle Sequence Title
	 * @since 2.0
	 */
	public void setSequenceTitle(final String sequenceTitle)
	{
		this.sequenceTitle = sequenceTitle;
		
	}//METHOD
	
	/**
	 * Returns the sequence title.
	 * 
	 * @return Sequence Title
	 * @since 2.0
	 */
	public String getSequenceTitle()
	{
		return sequenceTitle;
		
	}//METHOD
	
	/**
	 * Sets the section title.
	 * 
	 * @param sectionTitle Section Title
	 * @since 2.0
	 */
	public void setSectionTitle(final String sectionTitle)
	{
		this.sectionTitle = sectionTitle;
		
	}//METHOD
	
	/**
	 * Returns the section title.
	 * 
	 * @return Section Title
	 * @since 2.0
	 */
	public String getSectionTitle()
	{
		return sectionTitle;
		
	}//METHOD
	
	/**
	 * Sets the DMF's branch titles.
	 * 
	 * @param branchTitles Branch Titles
	 * @since 2.0
	 */
	public void setBranchTitles(final ArrayList<String> branchTitles)
	{
		if(branchTitles != null && branchTitles.size() > 0)
		{
			this.branchTitles = StringMethods.arrayListToArray(branchTitles);
		
		}//IF
		else
		{
			this.branchTitles = null;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns the DMF's branch titles.
	 * 
	 * @return Branch Titles
	 * @since 2.0
	 */
	public String[] getBranchTitles()
	{
		return branchTitles;
		
	}//METHOD
	
	/**
	 * Sets the DMF's rating.
	 * 
	 * @param rating DMF Rating
	 * @since 2.0
	 */
	public void setRating(final int rating)
	{
		if(rating > 0 && rating < 6)
		{
			this.rating = rating;
			
		}//IF
		else
		{
			this.rating = 0;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns the DMF's rating.
	 * 
	 * @return DMF Rating
	 * @since 2.0
	 */
	public int getRating()
	{
		return rating;
		
	}//METHOD
	
	/**
	 * Sets the user tags.
	 * 
	 * @param userTags User Tags
	 * @since 2.0
	 */
	public void setUserTags(final ArrayList<String> userTags)
	{
		if(userTags != null && userTags.size() > 0)
		{
			this.userTags = StringMethods.arrayListToArray(userTags);
			
		}//IF
		else
		{
			this.userTags = null;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns the user tags.
	 * 
	 * @return User Tags
	 * @since 2.0
	 */
	public String[] getUserTags()
	{
		return userTags;
		
	}//METHOD
	
}//CLASS
