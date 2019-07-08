package drakovek.hoarder.gui.artist;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.processing.ParseINI;
import drakovek.hoarder.processing.sort.AlphaNumSort;

/**
 * Class for handling lists of artists for the Artist Hosting GUI.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ArtistHandler
{
	/**
	 * Main header for the artist list file
	 * 
	 * @since 2.0
	 */
	private String header;
	
	/**
	 * Artist list file this object is handling
	 * 
	 * @since 2.0
	 */
	private File file;
	
	/**
	 * List of artists
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> artists;
	
	/**
	 * Initializes the ArtistHandler class by reading/creating the folder for holding artist list files.
	 * 
	 * @param settings Program Settings
	 * @param service Service for which the artists are linked
	 * @since 2.0
	 */
	public ArtistHandler(DSettings settings, final String service)
	{
		header = "[" + service.toUpperCase().replaceAll(Character.toString('_'), Character.toString(' ')) + " ARTISTS]"; //$NON-NLS-1$ //$NON-NLS-2$
		File artistFolder = DReader.getDirectory(settings.getDataFolder(), "artists"); //$NON-NLS-1$
		if(artistFolder != null)
		{
			file = new File(artistFolder, service.toLowerCase().replaceAll(Character.toString('_'), new String()) + ".ini"); //$NON-NLS-1$
			
		}//IF
		
		readArtistFile();
		
	}//CONSTRUCTOR
	
	/**
	 * Reads list of artists from an artist list file.
	 * 
	 * @since 2.0
	 */
	private void readArtistFile()
	{
		artists = new ArrayList<>();
		ArrayList<String> contents = DReader.readFile(file);
		if(ParseINI.getStringValue(header, Integer.toString(0), contents, new String()).length() > 0)
		{
			for(String line: contents)
			{
				int i = line.indexOf('=');
				if(i != -1 && (i+1) < line.length())
				{
					artists.add(line.substring(i + 1));
					
				}//IF
				
			}//FOR
			
			organizeArtists();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Returns the currently loaded list of artists.
	 * 
	 * @return Artist List
	 * @since 2.0
	 */
	public ArrayList<String> getArtists()
	{
		return artists;
		
	}//METHOD
	
	/**
	 * Adds an artist to the list of artists.
	 * 
	 * @param artist Artist
	 * @since 2.0
	 */
	public void addArtist(final String artist)
	{
		artists.add(artist);
		organizeArtists();
		
	}//METHOD
	
	/**
	 * Adds multiple artists to the list of artists.
	 * 
	 * @param artistList List of Artists
	 * @since 2.0
	 */
	public void addAartists(final ArrayList<String> artistList)
	{
		this.artists.addAll(artistList);
		organizeArtists();
		
	}//METHOD
	
	/**
	 * Removes the artists at the specified indexes from the artist list.
	 * 
	 * @param indexes Indexes of the artists to remove.
	 * @since 2.0
	 */
	public void deleteArtists(final int[] indexes)
	{
		for(int i = 0; i < indexes.length; i++)
		{
			if(indexes[i] < artists.size())
			{
				artists.set(indexes[i], null);
				
			}//IF
			
		}//FOR
		
		for(int i = 0; i < artists.size(); i++)
		{
			if(artists.get(i) == null)
			{
				artists.remove(i);
				i--;
				
			}//if
			
		}//FOR
		
		organizeArtists();
		
	}//METHOD
	
	/**
	 * Makes sure the artist list contains no duplicates and are in alpha-numerical order.
	 * 
	 * @since 2.0
	 */
	private void organizeArtists()
	{
		for(int i = 0; i < artists.size(); i++)
		{
			for(int k = i + 1; k < artists.size(); k++)
			{
				if(artists.get(i).toLowerCase().equals(artists.get(k).toLowerCase()))
				{
					artists.remove(k);
					k--;
					
				}//IF
				
			}//FOR
			
		}//FOR
		
		artists = AlphaNumSort.sort(this.artists);
		
	}//METHOD
	
	/**
	 * Saves the current list of artists to a file.
	 * 
	 * @since 2.0
	 */
	public void saveArtists()
	{
		if(file != null && file.getParentFile().isDirectory())
		{
			ArrayList<String> contents = new ArrayList<>();
			contents.add(header);
			for(int i = 0; i < artists.size(); i++)
			{
				contents.add(ParseINI.getAssignmentString(Integer.toString(i), artists.get(i)));
				
			}//FOR
			
			DWriter.writeToFile(file, contents);
			
		}//IF
		
	}//METHOD
	
}//CLASS
