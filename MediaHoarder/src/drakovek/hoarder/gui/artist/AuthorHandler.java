package drakovek.hoarder.gui.artist;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.processing.ParseINI;
import drakovek.hoarder.processing.sort.AlphaNumSort;

/**
 * Class for handling lists of authors for the Artist Hosting GUI.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class AuthorHandler
{
	/**
	 * Main header for the author list file
	 * 
	 * @since 2.0
	 */
	private String header;
	
	/**
	 * Author list file this object is handling
	 * 
	 * @since 2.0
	 */
	private File file;
	
	/**
	 * List of authors
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> authors;
	
	/**
	 * Initializes the AuthorHandler class by reading/creating the folder for holding author list files.
	 * 
	 * @param settings Program Settings
	 * @param service Service for which the authors are linked
	 * @since 2.0
	 */
	public AuthorHandler(DSettings settings, final String service)
	{
		header = "[" + service.toUpperCase().replaceAll(Character.toString('_'), Character.toString(' ')) + " ARTISTS]"; //$NON-NLS-1$ //$NON-NLS-2$
		File authorFolder = DReader.getDirectory(settings.getDataFolder(), "authors"); //$NON-NLS-1$
		if(authorFolder != null)
		{
			file = new File(authorFolder, service.toLowerCase().replaceAll(Character.toString('_'), new String()) + ".ini"); //$NON-NLS-1$
			
		}//IF
		
		readAuthorFile();
		
	}//CONSTRUCTOR
	
	/**
	 * Reads list of authors from an author list file.
	 * 
	 * @since 2.0
	 */
	private void readAuthorFile()
	{
		authors = new ArrayList<>();
		ArrayList<String> contents = DReader.readFile(file);
		if(ParseINI.getStringValue(header, Integer.toString(0), contents, new String()).length() > 0)
		{
			for(String line: contents)
			{
				int i = line.indexOf('=');
				if(i != -1 && (i+1) < line.length())
				{
					authors.add(line.substring(i + 1));
					
				}//IF
				
			}//FOR
			
			organizeAuthors();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Returns the currently loaded list of authors.
	 * 
	 * @return Author List
	 * @since 2.0
	 */
	public ArrayList<String> getAuthors()
	{
		return authors;
		
	}//METHOD
	
	/**
	 * Adds an author to the list of authors.
	 * 
	 * @param author Author
	 * @since 2.0
	 */
	public void addAuthor(final String author)
	{
		authors.add(author);
		organizeAuthors();
		
	}//METHOD
	
	/**
	 * Adds multiple authors to the list of authors.
	 * 
	 * @param authorList List of Authors
	 * @since 2.0
	 */
	public void addAuthors(final ArrayList<String> authorList)
	{
		this.authors.addAll(authorList);
		organizeAuthors();
		
	}//METHOD
	
	/**
	 * Removes the authors at the specified indexes from the author list.
	 * 
	 * @param indexes Indexes of the authors to remove.
	 * @since 2.0
	 */
	public void deleteAuthors(final int[] indexes)
	{
		for(int i = 0; i < indexes.length; i++)
		{
			if(indexes[i] < authors.size())
			{
				authors.set(indexes[i], null);
				
			}//IF
			
		}//FOR
		
		for(int i = 0; i < authors.size(); i++)
		{
			if(authors.get(i) == null)
			{
				authors.remove(i);
				i--;
				
			}//if
			
		}//FOR
		
		organizeAuthors();
		
	}//METHOD
	
	/**
	 * Makes sure the author list contains no duplicates and are in alpha-numerical order.
	 * 
	 * @since 2.0
	 */
	private void organizeAuthors()
	{
		for(int i = 0; i < authors.size(); i++)
		{
			for(int k = i + 1; k < authors.size(); k++)
			{
				if(authors.get(i).toLowerCase().equals(authors.get(k).toLowerCase()))
				{
					authors.remove(k);
					k--;
					
				}//IF
				
			}//FOR
			
		}//FOR
		
		authors = AlphaNumSort.sort(this.authors);
		
	}//METHOD
	
	/**
	 * Saves the current list of authors to a file.
	 * 
	 * @since 2.0
	 */
	public void saveAuthors()
	{
		if(file != null && file.getParentFile().isDirectory())
		{
			ArrayList<String> contents = new ArrayList<>();
			contents.add(header);
			for(int i = 0; i < authors.size(); i++)
			{
				contents.add(ParseINI.getAssignmentString(Integer.toString(i), authors.get(i)));
				
			}//FOR
			
			DWriter.writeToFile(file, contents);
			
		}//IF
		
	}//METHOD
	
}//CLASS
