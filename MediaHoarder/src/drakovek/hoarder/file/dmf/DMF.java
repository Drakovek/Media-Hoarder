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
	
}//CLASS
