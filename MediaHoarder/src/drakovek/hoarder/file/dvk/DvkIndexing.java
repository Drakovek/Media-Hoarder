package drakovek.hoarder.file.dvk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.file.language.DvkLanguageValues;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.processing.ParseINI;

/**
 * Contains methods for loading DvkDirectories from index files.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DvkIndexing
{
	/**
	 * Name of the folder that holds DVK Directory index files
	 */
	private static final String INDEX_FOLDER = "index"; //$NON-NLS-1$
	
	/**
	 * Name of the index list file
	 */
	private static final String INDEX_LIST_FILE = "index" + ParseINI.INI_EXTENSION; //$NON-NLS-1$
	
	/**
	 * Main header for the index list file
	 */
	private static final String INDEX_HEADER = "[INDEX]"; //$NON-NLS-1$
	
	/**
	 * Folder containing DVK Directory index files
	 */
	private File indexFolder;
	
	/**
	 * File for the index list
	 */
	private File indexListFile;
	
	/**
	 * List of directories linked to saved index files
	 */
	private ArrayList<String> indexedDirectories;
	
	/**
	 * FileOutputStream for saving index files
	 */
	private FileOutputStream fileOutputStream;
	
	/**
	 * ObjectOutputStream for saving index files
	 */
	private ObjectOutputStream objectOutputStream;
	
	/**
	 * FileInputStream for reading index files
	 */
	private FileInputStream fileInputStream;
	
	/**
	 * ObjectInputStream for reading index files
	 */
	private ObjectInputStream objectInputStream;
	
	/**
	 * Initializes the DvkIndexing class.
	 * 
	 * @param settings Program settings
	 */
	public DvkIndexing()
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
	 * Loads DVKs from a directory, either directly or from an index file as specified.
	 * 
	 * @param directory Directory from which to load DVKs
	 * @param progressDialog DProgress dialog to show progress of loading DVKs, used here to show when an index file is being loaded.
	 * @param useIndex Whether to use index files to load DV info rather than directly
	 * @param updateIndex Whether to update index file to reflect changes in DVKs
	 * @return DvkDirectory with DVKs loaded from given directory
	 */
	public DvkDirectory loadDVKs(final File directory, DProgressDialog progressDialog, final boolean useIndex, final boolean updateIndex)
	{	
		DvkDirectory dvkDirectory = new DvkDirectory();
		boolean directLoad = true;
		File indexFile = null;
		if(useIndex)
		{
			int index = indexedDirectories.indexOf(directory.getAbsolutePath());
			indexFile = new File(indexFolder, Integer.toString(index));
			
			if(indexFile.exists())
			{
				progressDialog.setProcessLabel(DvkLanguageValues.LOADING_INDEX);
				fileInputStream = null;
				objectInputStream = null;
				
				try
				{
					fileInputStream = new FileInputStream(indexFile);
					objectInputStream = new ObjectInputStream(fileInputStream);
					dvkDirectory = (DvkDirectory)objectInputStream.readObject();
					
					
				}//TRY
				catch(Exception e){}
				finally
				{
					try
					{
						objectInputStream.close();
						fileInputStream.close();
					
					}//TRY
					catch(IOException e){};
					
				}//FINALLY
			
				directLoad = !dvkDirectory.isValid(directory);
				
			}//IF
			
			objectInputStream = null;
			fileInputStream = null;
			
		}//IF
		
		if(directLoad)
		{
			dvkDirectory.loadDVKs(directory);
			
		}//IF
		else if(updateIndex && indexFile != null)
		{
			dvkDirectory.updateDirectory(indexFile.lastModified());
			
		}//ELSE IF
		
		return dvkDirectory;
		
	}//METHOD
	
	/**
	 * Saves a DvkDirectory to an index file, either overwriting existing file or creating a new file.
	 * 
	 * @param dvkDirectory DvkDirectory to save
	 */
	public void saveIndex(DvkDirectory dvkDirectory)
	{
		int index = indexedDirectories.indexOf(dvkDirectory.getDirectory().getAbsolutePath());
		if(index == -1)
		{
			for(index = 0; index < indexedDirectories.size() && indexedDirectories.get(index) != null; index++);
			if(index == indexedDirectories.size())
			{
				indexedDirectories.add(null);
				
			}//IF
			
		}//IF
		
		indexedDirectories.set(index, dvkDirectory.getDirectory().getAbsolutePath());
		
		File indexFile = new File(indexFolder, Integer.toString(index));
		if(indexFile.exists())
		{
			indexFile.delete();
			
		}//IF
		
		fileOutputStream = null;
		objectOutputStream = null;
		
		try
		{
			fileOutputStream = new FileOutputStream(indexFile);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(dvkDirectory);
			
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
			
		}//FINALLY
		
		fileOutputStream = null;
		objectOutputStream = null;
		
	}//METHOD
	
	/**
	 * Cleans up index files and saves an index list file.
	 */
	public void close()
	{
		removeItems();
		deleteFiles();
		saveListFile();
		
	}//METHOD
	
	/**
	 * Removes items from the index list file if linked index files no longer exist or if a given index file hasn't been accessed in several sessions.
	 */
	private void removeItems()
	{
		for(int i = 0; i < indexedDirectories.size(); i++)
		{
			if(indexedDirectories.get(i) != null)
			{
				File indexFile = new File(indexFolder, Integer.toString(i));
				File refDirectory = new File(indexedDirectories.get(i));
				if(!indexFile.exists() || !refDirectory.isDirectory())
				{
					indexedDirectories.set(i, null);
					
				}//IF
				
			}//IF
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Deletes index files that are not referenced in the index list file
	 */
	public void deleteFiles()
	{
		File[] allFiles = indexFolder.listFiles();
		for(File file: allFiles)
		{
			try
			{
				int index = Integer.parseInt(file.getName());
				if(index > indexedDirectories.size() || indexedDirectories.get(index) == null)
				{
					file.delete();
					
				}//IF
				
			}//TRY
			catch(NumberFormatException e){}
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Saves index list file that ties saved index files to the directories they represent.
	 */
	private void saveListFile()
	{
		ArrayList<String> dirList = new ArrayList<>();
		
		dirList.add(INDEX_HEADER);
		
		for(int i = 0; i < indexedDirectories.size(); i++)
		{
			if(indexedDirectories.get(i) != null)
			{	
				dirList.add(ParseINI.getAssignmentString(Integer.toString(i), indexedDirectories.get(i)));
				
			}//IF
			
		}//FOR

		DWriter.writeToFile(indexListFile, dirList);
		
	}//METHOD
	
	/**
	 * Reads index list file that ties saved index files to the directories they represent.
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
		
	}//METHOD
	
}//CLASS
