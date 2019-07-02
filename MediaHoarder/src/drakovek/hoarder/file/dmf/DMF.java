package drakovek.hoarder.file.dmf;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.DReader;
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
	 * INI variable for the author(s) of the DMF media
	 * 
	 * @since 2.0
	 */
	private static final String AUTHORS = "authors"; //$NON-NLS-1$
	
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
	
	//INFO
	
	/**
	 * Title of the DMF media
	 * 
	 * @since 2.0
	 */
	private String title;
	
	/**
	 * Author(s) of the DMF media
	 * 
	 * @since 2.0
	 */
	private String[] authors;
	
	/**
	 * Time of publishing for the DMF media
	 * 
	 * @since 2.0
	 */
	private long time;
	
	/**
	 * Description of the DMF media
	 * 
	 * @since 2.0
	 */
	private String description;
	
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
		id = new String();
		
		//INFO
		title = new String();
		authors = new String[0];
		time = 0L;
		description = new String();
		
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
			
			//IF ID TAG EXISTS UNDER ID HEADER, SAFE TO CONTINUE READING
			if(getID() != null && getID().length() > 0)
			{
				setTitle(ParseINI.getStringValue(null, TITLE, contents, title));
				setAuthors(ParseINI.getStringListValue(null, AUTHORS, contents, new ArrayList<String>()));
				if(getAuthors().length == 0)
				{
					//IF NEW AUTHORS VARIABLE DOESN'T WORK, USE OLD ARTIST VARIABLE
					setAuthors(ParseINI.getStringListValue(null, OLD_ARTIST, contents, new ArrayList<String>()));
					
				}//IF
				
				setTime(ParseINI.getStringValue(null, TIME, contents, new String()));
				if(time == 0L)
				{
					//IF NEW TIME VARIABLE DOESN'T WORK, USE OLD DATE VARIABLE
					setTime(ParseINI.getStringValue(null, OLD_DATE, contents, new String()));
					
				}//IF
				
				setDescription(ParseINI.getStringValue(null, DESCRIPTION, contents, description));
				if(getDescription().length() == 0)
				{
					//IF NEW DESCRIPTION VARIABLE DOESN'T WORK, USE OLD DESC VARIABLE
					setDescription(ParseINI.getStringValue(null, OLD_DESC, contents, description));
					
				}//IF
				
			}//IF
			
		}//IF
		
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
	 * Gets the DMF Author(s)
	 * 
	 * @return DMF Authors
	 * @since 2.0
	 */
	public String[] getAuthors()
	{
		return authors;
		
	}//METHOD
	
	/**
	 * Sets the DMF Author(s)
	 * 
	 * @param authors DMF Authors
	 * @since 2.0
	 */
	public void setAuthors(final ArrayList<String> authors)
	{
		this.authors = StringMethods.arrayListToArray(authors);
		
	}//METHOD
	
	/**
	 * Sets the DMF Author variable from a single author String
	 * 
	 * @param author DMF Author
	 * @since 2.0
	 */
	public void setAuthor(final String author)
	{
		authors = new String[1];
		authors[0] = author;
		
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
	
}//CLASS
