package drakovek.hoarder.file.dmf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.apache.tika.Tika;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.processing.ExtensionMethods;
import drakovek.hoarder.processing.ParseINI;
import drakovek.hoarder.processing.StringMethods;

/**
 * Contains methods for reading writing and handling a single Drakovek Media File (DMF)
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DMF
{
	/**
	 * Empty ID variable used identify when there are no DMFs either following or preceding the current DMF in a sequence.
	 */
	public static final String EMPTY_ID = "XX"; //$NON-NLS-1$
	
	/**
	 * Empty section variable to identify when a section of a sequence has no section title
	 */
	public static final String EMPTY_SECTION = ":"; //$NON-NLS-1$
	
	/**
	 * Extension used for DMF Files
	 */
	public static final String DMF_EXTENSION = ".dmf"; //$NON-NLS-1$
	
	/**
	 * Extension used for DVK files
	 */
	public static final String DVK_EXTENSION = ".dvk"; //$NON-NLS-1$
	
	/**
	 * Array of file types and their corresponding extensions
	 */
	private static final String[][] FILE_TYPES = {{"image/jpeg", ".jpg"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"image/png", ".png"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"image/gif", ".gif"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"image/vnd.adobe.photoshop", ".psd"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"image/bmp", ".bmp"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"text/html", ".html"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"text/plain", ".txt"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"application/pdf", ".pdf"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"application/rtf", ".rtf"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"application/msword", ".doc"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"application/x-shockwave-flash", ".swf"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"video/quicktime", ".mov"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"video/x-ms-wmv", ".wmv"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"video/x-msvideo", ".avi"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"video/mp4", ".mp4"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"video/webm", ".webm"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"audio/mpeg", ".mp3"}};  //$NON-NLS-1$//$NON-NLS-2$
	
	/**
	 * Main header used at the top of DMF Files
	 */
	private static final String DMF_HEADER = "[DMF]"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF ID
	 */
	private static final String ID = "id"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF Title
	 */
	private static final String TITLE = "title"; //$NON-NLS-1$
	
	/**
	 * INI variable for the artist(s) of the DMF media
	 */
	private static final String ARTISTS = "artists"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for DMF artists, used for reading old DMFs
	 */
	private static final String OLD_ARTIST = "artist"; //$NON-NLS-1$
	
	/**
	 * INI variable for the time of publishing for the DMF media
	 */
	private static final String TIME = "time"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the time of publishing, used for reading old DMFs
	 */
	private static final String OLD_DATE = "date"; //$NON-NLS-1$
	
	/**
	 * INI variable for all the original web tags for the DMF media
	 */
	private static final String WEB_TAGS = "web_tags"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for original tags, used for reading old DMFs
	 */
	private static final String OLD_OTAGS = "oTags"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF's description
	 */
	private static final String DESCRIPTION = "description"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the description, used for reading old DMFs
	 */
	private static final String OLD_DESC = "desc"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF's original page URL
	 */
	private static final String PAGE_URL = "page_url"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the page URL, used for reading old DMFs
	 */
	private static final String OLD_PAGE_URL = "pageURL"; //$NON-NLS-1$
	
	/**
	 * DMF INI variable for the direct URL
	 */
	private static final String DIRECT_URL = "direct_url"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the media URL, used for reading second stage old DMFs
	 */
	private static final String MEDIA_URL_2 = "media_url"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the media URL, used for reading old DMFs
	 */
	private static final String OLD_MEDIA_URL = "mediaURL"; //$NON-NLS-1$
	
	/**
	 * DMF INI variable for the secondary media URL
	 */
	private static final String SECONDARY_URL = "secondary_url"; //$NON-NLS-1$
	
	/**
	 * INI variable for the media file linked to the DMF
	 */
	private static final String MEDIA_FILE = "media_file"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the media file, used for reading old DMFs
	 */
	private static final String OLD_FILENAME = "filename"; //$NON-NLS-1$
	
	/**
	 * DMF INI variable for the secondary media file
	 */
	private static final String SECONDARY_FILE = "secondary_file"; //$NON-NLS-1$
	
	/**
	 * INI variable for the ID(s) of the previous DMF(s)
	 */
	private static final String LAST_IDS = "last_ids"; //$NON-NLS-1$
	
	/**
	 * INI variable for the ID(s) of the next DMF(s)
	 */
	private static final String NEXT_IDS = "next_ids"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether the DMF is the first in a section
	 */
	private static final String FIRST = "first"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether the DMF is the last in a section
	 */
	private static final String LAST = "last"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for all DMF sequence data.
	 */
	private static final String OLD_SEQ_DATA = "seqData"; //$NON-NLS-1$
	
	/**
	 * INI variable for the title of the current sequence
	 */
	private static final String SEQUENCE_TITLE = "sequence_title"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the sequence title, used for reading old DMFs
	 */
	private static final String OLD_SEQ_TITLE = "seqTitle"; //$NON-NLS-1$
	
	/**
	 * INI variable for the title of the current section of the current sequence.
	 */
	private static final String SECTION_TITLE = "section_title"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the section(Sub-sequence) title, used for reading old DMFs
	 */
	private static final String OLD_SUB_SEQ_TITLE = "subSeqTitle"; //$NON-NLS-1$
	
	/**
	 * INI variable for titles of sequence branches coming from this DMF
	 */
	private static final String BRANCH_TITLES = "branch_titles"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF user rating
	 */
	private static final String RATING = "rating"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF's view count
	 */
	private static final String VIEWS = "views"; //$NON-NLS-1$
	
	/**
	 * INI variable for the DMF's user tags
	 */
	private static final String USER_TAGS = "user_tags"; //$NON-NLS-1$
	
	/**
	 * Old DMF INI variable for the user tags, used for reading old DMFs
	 */
	private static final String OLD_UTAGS = "uTags"; //$NON-NLS-1$
	
	//DMF
	
	/**
	 * File for the currently selected DMF
	 */
	private File dmfFile;
	
	/**
	 * Unique ID for the currently selected DMF
	 */
	private String id;
	
	/**
	 * Title of the DMF media
	 */
	private String title;
	
	/**
	 * Artist(s) of the DMF media
	 */
	private String[] artists;
	
	/**
	 * Time of publishing for the DMF media (Structured YYYYMMDDhhmm. Example: April 22, 2001 @ 4:30PM = 200104211630)
	 */
	private long time;
	
	/**
	 * Original web tags for the DMF
	 */
	private String[] webTags;
	
	/**
	 * Description of the DMF media
	 */
	private String description;
	
	/**
	 * The URL for the page that the DMF originates from
	 */
	private String pageURL;
	
	/**
	 * The URL for the direct media download URL that the DMF originates from
	 */
	private String directURL;
	
	/**
	 * URL for the direct media download URL for the secondary media file
	 */
	private String secondaryURL;
	
	/**
	 * Media File linked to the DMF
	 */
	private File mediaFile;
	
	/**
	 * Media File linked to the DMF for use as a secondary media file
	 */
	private File secondaryFile;
	
	/**
	 * Array of IDs directly preceding the current DMF in a sequence. If there are multiple IDs, this means the DMF comes directly after multiple branching paths.
	 */
	private String[] lastIDs;
	
	/**
	 * Array of IDs directly proceeding the current DMF in a sequence. If there are multiple IDs, this means the DMF leads to multiple branching paths.
	 */
	private String[] nextIDs;
	
	/**
	 * Whether the current DMF is the first in a sequence section.
	 */
	private boolean first;
	
	/**
	 * Whether the current DMF is the last in a sequence section.
	 */
	private boolean last;
	
	/**
	 * Title for the sequence the current DMF is a part of, if applicable
	 */
	private String sequenceTitle;
	
	/**
	 * Title for the section of a sequence the current DMF is a part of, if applicable
	 */
	private String sectionTitle;
	
	/**
	 * Array of titles for sequence branches coming from this DMF, if applicable.
	 */
	private String[] branchTitles;
	
	/**
	 * User given rating for the DMF, ranging from 1 to 5
	 */
	private int rating;
	
	/**
	 * Number of times the DMF has been viewed
	 */
	private int views;
	
	/**
	 * Tags for the DMF given by the user
	 */
	private String[] userTags;
	
	/**
	 * Initializes DMF to represent an empty DMF file.
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
	 */
	public DMF(final File dmfFile)
	{
		this.dmfFile = dmfFile;
		loadDMF();
		
	}//CONSTRUCTOR
	
	/**
	 * Clears all DMF variables so this DMF object represents an empty DMF File.
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
		directURL = null;
		secondaryURL = null;
		
		//FILE
		mediaFile = null;
		secondaryFile = null;
		lastIDs = null;
		nextIDs = null;
		first = false;
		last = false;
		
		//USER
		sequenceTitle = null;
		sectionTitle = null;
		branchTitles = null;
		rating = 0;
		views = 0;
		userTags = null;
		
	}//METHOD
	
	/**
	 * Loads DMF info from dmfFile so the DMF object represents a given DMF File.
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
				
				setDirectURL(ParseINI.getStringValue(null, DIRECT_URL, contents, null));
				if(getDirectURL() == null)
				{
					//IF NEW DIRECT URL VARIABLE DOESN'T WORK, USE MEDIA URL VARIABLE
					setDirectURL(ParseINI.getStringValue(null, MEDIA_URL_2, contents, null));

					if(getDirectURL() == null)
					{
						//IF NEW MEDIA URL VARIABLE DOESN'T WORK, USE OLD MEDIA URL VARIABLE
						setDirectURL(ParseINI.getStringValue(null, OLD_MEDIA_URL, contents, null));

					}//IF
					
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
				setViews(ParseINI.getIntValue(null, VIEWS, contents, 0));
				
				setUserTags(ParseINI.getStringListValue(null, USER_TAGS, contents, null));
				if(getUserTags() == null)
				{
					//IF NEW USER TAG VARIABLE DOESN'T WORK, USE OLD UTAG VARIABLE
					setUserTags(ParseINI.getStringListValue(null, OLD_UTAGS, contents, null));
					
				}//IF
				
			}//IF
			
		}//IF
		else
		{
			loadDVK();
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Loads DVK info from DVK File so the DMF object represents a given DVK File.
	 */
	public void loadDVK()
	{
		clearDMF();
		if(dmfFile != null && dmfFile.getAbsolutePath().endsWith(DVK_EXTENSION))
		{
			StringBuilder dvkString = new StringBuilder();
			ArrayList<String> json = DReader.readFile(dmfFile);
			for(int i = 0; i < json.size(); i++)
			{
				dvkString.append(json.get(i));
				
			}//FOR
				
			JSONObject dvk = new JSONObject(dvkString.toString());
			try
			{
				setID(dvk.getString("id")); //$NON-NLS-1$
				//IF ID TAG EXISTS AND IS PROPER DVK FILE
				if(getID() != null && getID().length() > 0 && dvk.getString("file_type").equals("dvk"))  //$NON-NLS-1$//$NON-NLS-2$
				{
					JSONObject info = dvk.getJSONObject("info"); //$NON-NLS-1$
					//INFO
					setTitle(info.getString("title")); //$NON-NLS-1$
					JSONArray jsonArray = info.getJSONArray("artists"); //$NON-NLS-1$
					ArrayList<String> artistList = new ArrayList<>();
					for(int i = 0; i < jsonArray.length(); i++)
					{
						artistList.add(jsonArray.getString(i));
						
					}//FOR
					setArtists(artistList);
					
					try
					{
						setTime(info.getString("time")); //$NON-NLS-1$
					}//TRY
					catch(JSONException e)
					{
						setTime(0L);
						
					}//CATCH
					
					try
					{
						jsonArray = info.getJSONArray("web_tags"); //$NON-NLS-1$
						ArrayList<String> tags = new ArrayList<>();
						for(int i = 0; i < jsonArray.length(); i++)
						{
							tags.add(jsonArray.getString(i));
						
						}//FOR
						setWebTags(tags);
					}//TRY
					catch(JSONException e)
					{
						setWebTags(null);
						
					}//CATCH
					
					try
					{
						setDescription(info.getString("description")); //$NON-NLS-1$
						
					}//TRY
					catch(JSONException e)
					{
						setDescription(null);
						
					}//CATCH
					
					//WEB
					JSONObject web = dvk.getJSONObject("web"); //$NON-NLS-1$
					setPageURL(web.getString("page_url")); //$NON-NLS-1$
					
					try
					{
						setDirectURL(web.getString("direct_url")); //$NON-NLS-1$
						
					}//TRY
					catch(JSONException e)
					{
						setDirectURL(null);
						
					}//CATCH
					
					try
					{
						setSecondaryURL(web.getString("secondary_url")); //$NON-NLS-1$
					
					}//TRY
					catch(JSONException e)
					{
						setSecondaryURL(null);
						
					}//CATCH
					
					//FILE
					JSONObject file = dvk.getJSONObject("file"); //$NON-NLS-1$
					setMediaFile(file.getString("media_file")); //$NON-NLS-1$
					
					try
					{
						setSecondaryFile(file.getString("secondary_file"));	 //$NON-NLS-1$
						
					}//TRY
					catch(JSONException e)
					{
						secondaryFile = null;
						
					}//CATCH
					
					
				}//IF
				
			}//TRY
			catch(JSONException e)
			{
				clearDMF();
				
			}//CATCH
			
		}//IF
		
	}//METHOD
	
	/**
	 * Writes a DMF file to dmfFile
	 *
	 * @return Whether the file was successfully written
	 */
	public boolean writeDMF()
	{
		return writeDMF(true);
		
	}//METHOD
	
	/**
	 * Writes a DMF file to dmfFile
	 *
	 * @param checkFileType Whether to check the file file type of linked media files and change extensions accordingly if necessary
	 * @return Whether the file was successfully written
	 */
	public boolean writeDMF(final boolean checkFileType)
	{
		if(isValidWrite())
		{
			JSONObject dvk = new JSONObject();
			dvk.put("file_type", "dvk"); //$NON-NLS-1$ //$NON-NLS-2$
			dvk.put("id", getID()); //$NON-NLS-1$
			
			//INFO
			JSONObject info = new JSONObject();
			
			if(title != null)
			{
				info.put("title", getTitle()); //$NON-NLS-1$
			
			}//IF
			
			if(getArtists() != null && getArtists().length > 0)
			{
				info.put("artists", new JSONArray(getArtists())); //$NON-NLS-1$
				
			}//IF
			
			if(getTime() != 0L)
			{
				info.put("time", getTimeString()); //$NON-NLS-1$
				
			}//IF
			
			if(getWebTags() != null && getWebTags().length > 0)
			{
				info.put("web_tags", new JSONArray(getWebTags())); //$NON-NLS-1$
				
			}//IF
			
			if(getDescription() != null && getDescription().length() > 0)
			{
				info.put("description", getDescription()); //$NON-NLS-1$
				
			}//IF
			
			dvk.put("info", info); //$NON-NLS-1$
			JSONObject web = new JSONObject();
			
			//WEB
			if(getPageURL() != null && getPageURL().length() > 0)
			{
				web.put("page_url", getPageURL()); //$NON-NLS-1$
				
			}//IF
			
			if(getDirectURL() != null && getDirectURL().length() > 0)
			{
				web.put("direct_url", getDirectURL()); //$NON-NLS-1$
				
			}//IF
			
			if(getSecondaryURL() != null && getSecondaryURL().length() > 0)
			{
				web.put("secondary_url", getSecondaryURL()); //$NON-NLS-1$
				
			}//IF
			
			dvk.put("web", web); //$NON-NLS-1$
			
			//FILE
			JSONObject file = new JSONObject();
			file.put("media_file", getMediaFile().getName()); //$NON-NLS-1$
			
			if(getSecondaryFile() != null)
			{
				file.put("secondary_file", getSecondaryFile().getName()); //$NON-NLS-1$
			
			}//IF
			
			dvk.put("file", file); //$NON-NLS-1$
			
			//WRITE TO FILE
			DWriter.writeToFile(dmfFile, dvk.toString());
			if(dmfFile.exists())
			{	
				if(checkFileType)
				{
					Tika tika = new Tika();
					String extension = ExtensionMethods.getExtension(getMediaFile());
					String secondaryExtension = ExtensionMethods.getExtension(getSecondaryFile());
					
					try
					{
						String response = tika.detect(getMediaFile());
						for(int i = 0; i < FILE_TYPES.length; i++)
						{
							if(FILE_TYPES[i][0].equals(response))
							{
								extension = FILE_TYPES[i][1];
								break;
								
							}//IF
							
						}//FOR
						
						if(getSecondaryFile() != null)
						{
							response = tika.detect(getSecondaryFile());
							for(int i = 0; i < FILE_TYPES.length; i++)
							{
								if(FILE_TYPES[i][0].equals(response))
								{
									secondaryExtension = FILE_TYPES[i][1];
									break;
									
								}//IF
								
							}//FOR
							
						}//IF
						
					}catch (IOException e){}
					
					rename(ExtensionMethods.removeExtension(getDmfFile()), extension, secondaryExtension);
					
				}//IF
				
				return true;
				
			}//IF
			
		}//IF
		
		return false;
		
	}//METHOD
	
	/**
	 * Checks if the current state of the DMF object can be written to make a valid DMF. Must have a valid name for a file, and a non-empty ID and media file.
	 *
	 * @return Whether the current DMF is valid to write
	 */
	public boolean isValidWrite()
	{
		boolean valid = true;
		
		if(dmfFile == null || dmfFile.isDirectory() || (!dmfFile.getName().endsWith(DMF_EXTENSION) && !dmfFile.getName().endsWith(DVK_EXTENSION)))
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
	
	/**
	 * Checks whether the current DMF file is valid to read.
	 * 
	 * @return Whether the current DMF file is valid to read
	 */
	public boolean isValidRead()
	{
		boolean valid = true;
		
		if(dmfFile == null || dmfFile.isDirectory() || (!dmfFile.getName().endsWith(DMF_EXTENSION) && !dmfFile.getName().endsWith(DVK_EXTENSION)))
		{
			valid = false;
			
		}//IF
		
		return valid;
		
	}//METHOD
	
	/**
	 * Renames the DMF and its linked media.
	 * 
	 * @param filename Main Filename Body
	 * @param mediaExtension Extension to use for the media file. If null, uses the media file's current extension.
	 * @param secondaryExtension Extension to use for the secondary media file. If null, uses the secondary media file's current extension.
	 */
	public void rename(final String filename, final String mediaExtension, final String secondaryExtension)
	{
		if(isValidWrite())
		{
			File currentFolder = getDmfFile().getParentFile();
			String extension = mediaExtension;
			if(extension == null || extension.length() == 0)
			{
				extension = ExtensionMethods.getExtension(getMediaFile());
				
			}//IF
			
			File outFile = new File(currentFolder, filename + extension);
			File tempFile = new File(currentFolder, "xxxTEMPxxx" + getID()); //$NON-NLS-1$
			try
			{
				Files.move(getMediaFile().toPath(), tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
				Files.move(tempFile.toPath(), outFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
				setMediaFile(outFile);
				
				if(getSecondaryFile() != null)
				{
					extension = secondaryExtension;
					if(extension == null || extension.length() == 0)
					{
						extension = ExtensionMethods.getExtension(getSecondaryFile());
						
					}//IF
					
					outFile = new File(currentFolder, filename + extension);
					Files.move(getSecondaryFile().toPath(), tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
					Files.move(tempFile.toPath(), outFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
					setSecondaryFile(outFile);
					
				}//IF
				
				if(outFile.exists())
				{
					getDmfFile().delete();
					setDmfFile(new File(currentFolder, filename + DVK_EXTENSION));
					writeDMF(false);
					
				}//IF
				
			}//TRY
			catch(IOException e)
			{
				System.out.println("Failed to remame - DMF.rename()"); //$NON-NLS-1$
			
			}//CATCH
			
		}//IF
		
	}//METHOD
	
	//GETTERS AND SETTERS
	
	/**
	 * Returns dmfFile
	 * 
	 * @return dmfFile
	 */
	public File getDmfFile()
	{
		return dmfFile;
		
	}//METHOD
	
	/**
	 * Sets dmfFile.
	 * 
	 * @param dmfFile dmfFile
	 */
	public void setDmfFile(final File dmfFile)
	{
		this.dmfFile = dmfFile;
		
	}//METHOD
	
	/**
	 * Returns the DMF ID
	 * 
	 * @return DMF ID
	 */
	public String getID()
	{
		if(id != null)
		{
			return id.toUpperCase();
			
		}//IF
		
		return null;
		
	}//METHOD
	
	/**
	 * Sets the DMF ID
	 * 
	 * @param id DMF ID
	 */
	public void setID(final String id)
	{
		if(id != null)
		{
			this.id = id.toUpperCase();
			
		}//IF
		else
		{
			this.id = null;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns the default base filename based on DMF info (TITLE_ID)
	 * 
	 * @return Default Filename
	 */
	public String getDefaultFileName()
	{
		String currentTitle = getTitle();
		if(currentTitle == null)
		{
			currentTitle = new String();
			
		}//IF
		
		currentTitle = DWriter.getFileFriendlyName(currentTitle);
		
		String currentID = getID();
		if(currentID == null)
		{
			currentID = new String();
			
		}//IF
		
		return currentTitle + '_' + currentID;
		
	}//METHOD
	
	/**
	 * Returns the DMF Title
	 * 
	 * @return DMF Title
	 */
	public String getTitle()
	{
		if(title == null)
		{
			return null;
			
		}//IF
		
		return StringMethods.replaceHtmlEscapeCharacters(title);
		
	}//METHOD
	
	/**
	 * Sets the DMF Title
	 * 
	 * @param title DMF Title
	 */
	public void setTitle(final String title)
	{
		this.title = title;
		
	}//METHOD
	
	/**
	 * Gets the DMF Artist(s)
	 * 
	 * @return DMF Artists
	 */
	public String[] getArtists()
	{
		return artists;
		
	}//METHOD
	
	/**
	 * Sets the DMF Artists(s)
	 * 
	 * @param artists DMF Artists
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
	 */
	public void setTime(final long time)
	{
		this.time = time;
		
	}//METHOD
	
	/**
	 * Sets the DMF Time from a DMF String
	 * 
	 * @param timeString DMF Time String
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
	 */
	public long getTime()
	{
		return time;
		
	}//METHOD
	
	/**
	 * Gets DMF Time as a String
	 * 
	 * @return String representation of DMF Time
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
	 */
	public String[] getWebTags()
	{
		return webTags;
		
	}//METHOD
	
	/**
	 * Sets the description for the current DMF
	 * 
	 * @param description DMF Description
	 */
	public void setDescription(final String description)
	{
		this.description = description;
		
	}//METHOD
	
	/**
	 * Gets the description from the current DMF
	 * 
	 * @return DMF Description
	 */
	public String getDescription()
	{
		if(description == null)
		{
			return null;
			
		}//IF
		
		return StringMethods.addHtmlEscapesToHtml(description);
		
	}//METHOD
	
	/**
	 * Sets the DMF's Page URL
	 * 
	 * @param pageURL Page URL
	 */
	public void setPageURL(final String pageURL)
	{
		this.pageURL = pageURL;
		
	}//METHOD
	
	/**
	 * Returns the DMF's Page URL
	 * 
	 * @return Page URL
	 */
	public String getPageURL()
	{
		return pageURL;
		
	}//METHOD
	
	/**
	 * Sets the direct URL.
	 * 
	 * @param directURL Direct URL
	 */
	public void setDirectURL(final String directURL)
	{
		this.directURL = directURL;
		
	}//METHOD
	
	/**
	 * Returns the direct URL.
	 * 
	 * @return Direct URL
	 */
	public String getDirectURL()
	{
		return directURL;
		
	}//METHOD
	
	/**
	 * Sets the secondary URL.
	 * 
	 * @param secondaryURL Secondary URL
	 */
	public void setSecondaryURL(final String secondaryURL)
	{
		this.secondaryURL = secondaryURL;
		
	}//METHOD
	
	/**
	 * Returns the secondary URL.
	 * 
	 * @return Secondary URL
	 */
	public String getSecondaryURL()
	{
		return secondaryURL;
		
	}//METHOD
	
	/**
	 * Sets the linked media file.
	 * 
	 * @param mediaFile Media File
	 */
	public void setMediaFile(final File mediaFile)
	{
		this.mediaFile = mediaFile;
		
	}//METHOD
	
	/**
	 * Sets the linked media file based on a filename. Uses the same directory as the DMF File.
	 * 
	 * @param filename Name of the Media File
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
	 */
	public File getMediaFile()
	{
		return mediaFile;
		
	}//METHOD
	
	/**
	 * Sets the secondary File.
	 * 
	 * @param secondaryFile Secondary File
	 */
	public void setSecondaryFile(final File secondaryFile)
	{
		this.secondaryFile = secondaryFile;
		
	}//METHOD
	

	/**
	 * Sets the linked secondary media file based on a filename. Uses the same directory as the DMF File.
	 * 
	 * @param filename Name of the Secondary Media File
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
	 */
	public File getSecondaryFile()
	{
		return secondaryFile;
		
	}//METHOD
	
	/**
	 * Sets the IDs preceding the current DMF
	 * 
	 * @param lastIDs Last IDs
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
	 */
	public String[] getLastIDs()
	{
		return lastIDs;
		
	}//METHOD
	
	/**
	 * Sets the IDs after the current DMF
	 * 
	 * @param nextIDs Next IDs
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
	 */
	public String[] getNextIDs()
	{
		return nextIDs;
		
	}//METHOD
	
	/**
	 * Sets if the current DMF is the first in a section.
	 * 
	 * @param first First in Section
	 */
	public void setFirst(final boolean first)
	{
		this.first = first;
		
	}//METHOD
	
	/**
	 * Returns whether the current DMF is the first in a section.
	 * 
	 * @return First in Section
	 */
	public boolean isFirstInSection()
	{
		return first;
		
	}//METHOD
	
	/**
	 * Sets if the current DMF is the last in a section.
	 * 
	 * @param last Last in Section
	 */
	public void setLast(final boolean last)
	{
		this.last = last;
		
	}//METHOD
	
	/**
	 * Returns whether the current DMF is the last in a section.
	 * 
	 * @return Last in Section
	 */
	public boolean isLastInSection()
	{
		return last;
		
	}//METHOD
	
	/**
	 * Sets all the sequence data based on the old DMF sequence data standards.
	 * 
	 * @param sequenceData Sequence Data String
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
	 */
	public void setSequenceTitle(final String sequenceTitle)
	{
		this.sequenceTitle = sequenceTitle;
		
	}//METHOD
	
	/**
	 * Returns the sequence title.
	 * 
	 * @return Sequence Title
	 */
	public String getSequenceTitle()
	{
		return sequenceTitle;
		
	}//METHOD
	
	/**
	 * Sets the section title.
	 * 
	 * @param sectionTitle Section Title
	 */
	public void setSectionTitle(final String sectionTitle)
	{
		this.sectionTitle = sectionTitle;
		
	}//METHOD
	
	/**
	 * Returns the section title.
	 * 
	 * @return Section Title
	 */
	public String getSectionTitle()
	{
		if(sectionTitle != null && sectionTitle.equals(";;;")) //$NON-NLS-1$
		{
			return EMPTY_SECTION;
			
		}//IF
		
		return sectionTitle;
		
	}//METHOD
	
	/**
	 * Sets the DMF's branch titles.
	 * 
	 * @param branchTitles Branch Titles
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
	 */
	public String[] getBranchTitles()
	{
		return branchTitles;
		
	}//METHOD
	
	/**
	 * Sets the DMF's rating.
	 * 
	 * @param rating DMF Rating
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
	 */
	public int getRating()
	{
		return rating;
		
	}//METHOD
	
	/**
	 * Sets the DMF's view count.
	 * 
	 * @param views Number of Views
	 */
	public void setViews(final int views)
	{
		this.views = views;
		
	}//METHOD
	
	/**
	 * Returns the DMF's view count.
	 * 
	 * @return Number of Views
	 */
	public int getViews()
	{
		return views;
		
	}//METHOD
	
	/**
	 * Sets the user tags.
	 * 
	 * @param userTags User Tags
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
	 */
	public String[] getUserTags()
	{
		return userTags;
		
	}//METHOD
	
}//CLASS
