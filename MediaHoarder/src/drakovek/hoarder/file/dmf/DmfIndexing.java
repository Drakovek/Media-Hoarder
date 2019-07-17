package drakovek.hoarder.file.dmf;

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
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
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
	 * FileInputStream for reading index files
	 * 
	 * @since 2.0
	 */
	private FileInputStream fileInputStream;
	
	/**
	 * ObjectInputStream for reading index files
	 * 
	 * @since 2.0
	 */
	private ObjectInputStream objectInputStream;
	
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
	 * @param directory Directory from which to load DMFs
	 * @param progressDialog DProgress dialog to show progress of loading DMFs, used here to show when an index file is being loaded.
	 * @param useIndex Whether to use index files to load DMF info rather than directly
	 * @param updateIndex Whether to update index file to reflect changes in DMFs
	 * @return DmfDirectory with DMFs loaded from given directory
	 * @since 2.0
	 */
	public DmfDirectory loadDMFs(final File directory, DProgressDialog progressDialog, final boolean useIndex, final boolean updateIndex)
	{	
		DmfDirectory dmfDirectory = new DmfDirectory();
		boolean directLoad = true;
		File indexFile = null;
		if(useIndex)
		{
			int index = indexedDirectories.indexOf(directory.getAbsolutePath());
			indexFile = new File(indexFolder, Integer.toString(index));
			
			if(indexFile.exists())
			{
				progressDialog.setProcessLabel(DefaultLanguage.LOADING_INDEX);
				fileInputStream = null;
				objectInputStream = null;
				
				try
				{
					fileInputStream = new FileInputStream(indexFile);
					objectInputStream = new ObjectInputStream(fileInputStream);
					dmfDirectory = (DmfDirectory) objectInputStream.readObject();
					
					
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
			
				directLoad = !dmfDirectory.isValid(directory);
				
			}//IF
			
			objectInputStream = null;
			fileInputStream = null;
			
		}//IF
		
		if(directLoad)
		{
			dmfDirectory.loadDMFs(directory);
			
		}//IF
		else if(updateIndex && indexFile != null)
		{
			dmfDirectory.updateDirectory(indexFile.lastModified());
			
		}//ELSE IF
		
		return dmfDirectory;
		
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
		lastRead.set(index, Integer.valueOf(0));
		
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
			
		}//FINALLY
		
		fileOutputStream = null;
		objectOutputStream = null;
		
	}//METHOD
	
	/**
	 * Cleans up index files and saves an index list file.
	 * 
	 * @since 2.0
	 */
	public void close()
	{
		removeItems();
		deleteFiles();
		saveListFile();
		
	}//METHOD
	
	/**
	 * Removes items from the index list file if linked index files no longer exist or if a given index file hasn't been accessed in several sessions.
	 * 
	 * @since 2.0
	 */
	private void removeItems()
	{
		for(int i = 0; i < indexedDirectories.size(); i++)
		{
			if(indexedDirectories.get(i) != null)
			{
				if(lastRead.get(i) != null && lastRead.get(i).intValue() > 15)
				{
					indexedDirectories.set(i, null);
					lastRead.set(i, null);
					
				}//IF
				else
				{
					File currentFile = new File(indexFolder, Integer.toString(i));
					if(!currentFile.exists())
					{
						indexedDirectories.set(i, null);
						lastRead.set(i, null);
						
					}//IF
					
				}//ELSE
				
			}//IF
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Deletes index files that are not referenced in the index list file
	 * 
	 * @since 2.0
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
					lastRead.set(index, Integer.valueOf(readNum));
					
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
