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
 */
public class ArtistHandler
{	
	/**
	 * INI value for listing artists
	 */
	private static final String ARTIST = "artist"; //$NON-NLS-1$
	
	/**
	 * Main header for the artist list file
	 */
	private String header;
	
	/**
	 * Artist list file this object is handling
	 */
	private File file;
	
	/**
	 * List of artists
	 */
	private ArrayList<String> artists;
	
	/**
	 * Initializes the ArtistHandler class by reading/creating the folder for holding artist list files.
	 * 
	 * @param settings Program Settings
	 * @param service Service for which the artists are linked
	 */
	public ArtistHandler(DSettings settings, final String service)
	{
		header = "[" + service.toUpperCase().replaceAll(Character.toString('_'), Character.toString(' ')) + " ARTISTS]"; //$NON-NLS-1$ //$NON-NLS-2$
		File artistFolder = DReader.getDirectory(settings.getDataFolder(), "artists"); //$NON-NLS-1$
		if(artistFolder != null)
		{
			file = new File(artistFolder, service.toLowerCase().replaceAll(Character.toString('_'), new String()) + ParseINI.INI_EXTENSION);
			
		}//IF
		
		readArtistFile();
		
	}//CONSTRUCTOR
	
	/**
	 * Reads list of artists from an artist list file.
	 */
	private void readArtistFile()
	{
		artists = new ArrayList<>();
		ArrayList<String> contents = DReader.readFile(file);
		artists = ParseINI.getStringValues(header, ARTIST, contents, new ArrayList<String>());
		organizeArtists();
		
	}//METHOD
	
	/**
	 * Returns the currently loaded list of artists.
	 * 
	 * @return Artist List
	 */
	public ArrayList<String> getArtists()
	{
		return artists;
		
	}//METHOD
	
	/**
	 * Returns the artist from the specified indexes.
	 * 
	 * @param indexes Artist indexes
	 * @return Selected Artists
	 */
	public ArrayList<String> getArtists(final int[] indexes)
	{
		
		ArrayList<String> selectedArtists = new ArrayList<>();
		for(int i = 0; i < indexes.length; i++)
		{
			if(indexes[i] == 0)
			{
				return getArtists();
				
			}//IF
			
			if(indexes[i] > 0 && (indexes[i] - 1) < artists.size())
			{
				selectedArtists.add(artists.get(indexes[i] - 1));
				
			}//IF
			
		}//FOR
		
		return selectedArtists;
		
	}//METHOD
	
	/**
	 * Adds an artist to the list of artists.
	 * 
	 * @param artist Artist
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
	 */
	public void deleteArtists(final int[] indexes)
	{
		for(int i = 0; i < indexes.length; i++)
		{
			if(indexes[i] > 0 && (indexes[i] - 1) < artists.size())
			{
				artists.set((indexes[i] - 1), null);
				
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
	 */
	public void saveArtists()
	{
		if(file != null && file.getParentFile().isDirectory())
		{
			ArrayList<String> contents = new ArrayList<>();
			contents.add(header);
			for(int i = 0; i < artists.size(); i++)
			{
				contents.add(ParseINI.getAssignmentString(ARTIST, artists.get(i)));
				
			}//FOR
			
			DWriter.writeToFile(file, contents);
			
		}//IF
		
	}//METHOD
	
}//CLASS
