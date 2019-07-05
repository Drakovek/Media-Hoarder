package drakovek.hoarder.file.dmf;

import java.io.File;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DSettings;

/**
 * Contains methods for loading DmfDirectories from index files.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DmfIndexing
{
	/**
	 * Name of the folder that holds DMF Directory index files
	 * 
	 * @since 2.0
	 */
	private static final String INDEX_FOLDER = "index"; //$NON-NLS-1$
	
	/**
	 * Folder containing DMF Directory index files
	 * 
	 * @since 2.0
	 */
	private File indexFolder;
	
	/**
	 * Initializes the DmfIndexing class.
	 * 
	 * @param settings Program settings
	 * @since 2.0
	 */
	public DmfIndexing(DSettings settings)
	{
		indexFolder = DReader.getDirectory(settings.getDataFolder(), INDEX_FOLDER);
		
	}//CONSTRUCTOR
	
	/**
	 * Loads DMFs from a directory, either directly or from an index file as specified.
	 * 
	 * @param dmfDirectory DmfDirectory object to store DMF info within
	 * @param directory Directory from which to load DMFs
	 * @since 2.0
	 */
	public void loadDMFs(DmfDirectory dmfDirectory, final File directory)
	{
		dmfDirectory.loadDMFs(directory);
		
	}//METHOD
	
}//CLASS
