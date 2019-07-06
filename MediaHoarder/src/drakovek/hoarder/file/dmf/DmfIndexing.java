package drakovek.hoarder.file.dmf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.processing.ParseINI;

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
	 * Name of the index list file
	 * 
	 * @since 2.0
	 */
	private static final String INDEX_LIST_FILE = "index.ini"; //$NON-NLS-1$
	
	/**
	 * Main header for the index list file
	 * 
	 * @since 2.0
	 */
	private static final String INDEX_HEADER = "[INDEX]"; //$NON-NLS-1$
	
	/**
	 * Read header for the index list file
	 * 
	 * @since 2.0
	 */
	private static final String READ_HEADER = "[READ]"; //$NON-NLS-1$
	
	/**
	 * Folder containing DMF Directory index files
	 * 
	 * @since 2.0
	 */
	private File indexFolder;
	
	/**
	 * File for the index list
	 * 
	 * @since 2.0
	 */
	private File indexListFile;
	
	/**
	 * List of directories linked to saved index files
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> indexedDirectories;
	
	/**
	 * List of times directories were read last
	 * 
	 * @since 2.0
	 */
	private ArrayList<Integer> lastRead;
	
	/**
	 * FileOutputStream for saving index files
	 * 
	 * @since 2.0
	 */
	private FileOutputStream fileOutputStream;
	
	/**
	 * ObjectOutputStream for saving index files
	 * 
	 * @since 2.0
	 */
	private ObjectOutputStream objectOutputStream;
	
	/**
	 * Initializes the DmfIndexing class.
	 * 
	 * @param settings Program settings
	 * @since 2.0
	 */
	public DmfIndexing()
	{
		indexFolder = DReader.getDirectory(new DSettings().getDataFolder(), INDEX_FOLDER);
		indexListFile = null;
		if(indexFolder != null)
		{
			indexListFile = new File(indexFolder, INDEX_LIST_FILE);
			
		}//IF
		
		readListFile();
		
	}//CONSTRUCTOR
	
	/**
	 * Loads DMFs from a directory, either directly or from an index file as specified.
	 * 
	 * @param dmfDirectory DmfDirectory object to store DMF info within
	 * @param directory Directory from which to load DMFs
	 * @param useIndex Whether to use index files to load DMF info rather than directly
	 * @since 2.0
	 */
	public static void loadDMFs(DmfDirectory dmfDirectory, final File directory, final boolean useIndex)
	{	
		dmfDirectory.loadDMFs(directory);
		
	}//METHOD
	
	/**
	 * Saves a DmfDirectory to an index file, either overwriting existing file or creating a new file.
	 * 
	 * @param dmfDirectory DmfDirectory to save
	 * @since 2.0
	 */
	public void saveIndex(DmfDirectory dmfDirectory)
	{
		int index = indexedDirectories.indexOf(dmfDirectory.getDirectory().getAbsolutePath());
		if(index == -1)
		{
			for(index = 0; index < indexedDirectories.size() && indexedDirectories.get(index) != null; index++);
			if(index == indexedDirectories.size())
			{
				indexedDirectories.add(null);
				lastRead.add(null);
				
			}//IF
			
		}//IF
		
		indexedDirectories.set(index, dmfDirectory.getDirectory().getAbsolutePath());
		lastRead.set(index, new Integer(0));
		
		File indexFile = new File(indexFolder, Integer.toString(index));
		if(indexFile.exists())
		{
			indexFile.delete();
			
		}//IF (indexFile.exists())
		
		fileOutputStream = null;
		objectOutputStream = null;
		
		try
		{
			fileOutputStream = new FileOutputStream(indexFile);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(dmfDirectory);
			
		}
		catch(IOException e){}
		finally
		{
			try
			{
				if(objectOutputStream != null)
				{
					objectOutputStream.close();
					
				}//IF
				
				if(fileOutputStream != null)
				{
					fileOutputStream.close();
					
				}//IF
				
			}//try
			catch (IOException e){}
			
			fileOutputStream = null;
			objectOutputStream = null;
			
		}//FINALLY
		
	}//METHOD
	
	/**
	 * Cleans up index files and saves an index list file.
	 * 
	 * @since 2.0
	 */
	public void close()
	{
		saveListFile();
		
	}//METHOD
	
	/**
	 * Saves index list file that ties saved index files to the directories they represent.
	 * 
	 * @since 2.0
	 */
	private void saveListFile()
	{
		ArrayList<String> dirList = new ArrayList<>();
		ArrayList<String> readList = new ArrayList<>();
		
		dirList.add(INDEX_HEADER);
		readList.add(READ_HEADER);
		
		for(int i = 0; i < indexedDirectories.size(); i++)
		{
			if(indexedDirectories.get(i) != null)
			{
				int readNum = 0;
				if(lastRead.get(i) != null)
				{
					readNum = lastRead.get(i).intValue() + 1;
					
				}//IF
				
				dirList.add(ParseINI.getAssignmentString(Integer.toString(i), indexedDirectories.get(i)));
				readList.add(ParseINI.getAssignmentString(Integer.toString(i), readNum));
				
			}//IF
			
		}//FOR
		
		dirList.add(new String());
		dirList.addAll(readList);
		DWriter.writeToFile(indexListFile, dirList);
		
	}//METHOD
	
	/**
	 * Reads index list file that ties saved index files to the directories they represent.
	 * 
	 * @since 2.0
	 */
	private void readListFile()
	{
		indexedDirectories = new ArrayList<>();
		ArrayList<String> directoryContents = ParseINI.getSection(INDEX_HEADER, DReader.readFile(indexListFile));
		
		for(String line: directoryContents)
		{
			int i = line.indexOf('=');
			if(i + 1 < line.length())
			{
				try
				{
					int index = Integer.parseInt(line.substring(0, i));
					while(index >= indexedDirectories.size())
					{
						indexedDirectories.add(null);
						
					}//WHILE
					indexedDirectories.set(index, line.substring(i + 1));
					
				}//TRY
				catch(NumberFormatException e){}
				
			}//IF
			
		}//FOR
		
		lastRead = new ArrayList<>();
		ArrayList<String> readContents = ParseINI.getSection(READ_HEADER, DReader.readFile(indexListFile));
		
		for(String line: readContents)
		{
			int i = line.indexOf('=');
			if(i + 1 < line.length())
			{
				try
				{
					int readNum = Integer.parseInt(line.substring(i + 1));
					int index = Integer.parseInt(line.substring(0, i));
					while(index >= lastRead.size())
					{
						lastRead.add(null);
						
					}//WHILE
					lastRead.set(index, new Integer(readNum));
					
				}//TRY
				catch(NumberFormatException e){}
				
			}//IF
			
		}//FOR
		
		while(lastRead.size() < indexedDirectories.size())
		{
			lastRead.add(null);
			
		}//WHILE
		
		while(lastRead.size() > indexedDirectories.size())
		{
			indexedDirectories.add(null);
			
		}//WHILE
		
	}//METHOD
	
}//CLASS
