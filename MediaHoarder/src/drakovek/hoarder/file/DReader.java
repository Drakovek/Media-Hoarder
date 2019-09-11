package drakovek.hoarder.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Contains methods for reading local data off of a disk.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DReader
{
	/**
	 * BufferedReader used when reading files.
	 */
    private static BufferedReader bufferedReader;
    
	/**
     * Reads a UTF-8 formatted text file
     *
     * @param inputFile Text File to read
     * @return Contents of Text File
     */
    public static ArrayList<String> readFile(final File inputFile)
    {
        ArrayList<String> contents = new ArrayList<>();

		bufferedReader = null;
        
        try
        {
            String inputLine;
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8));
            while((inputLine = bufferedReader.readLine()) != null)
            {
                contents.add(inputLine);

            }//WHILE

        }//TRY
        catch(IOException e)
        {
            System.out.println("Failed Reading " + inputFile.getAbsolutePath() + " - Reader.readFile");  //$NON-NLS-1$ //$NON-NLS-2$
            contents = new ArrayList<>();

        }//CATCH
        finally
        {
        	try 
    		{
				bufferedReader.close();
				
			}//TRY
    		catch (Exception f)
        	{
    			System.out.println("Failed Closing BufferedReader - Reader.readFile"); //$NON-NLS-1$
    			
        	}//CATCH
        	
        }//FINALLY

        return contents;
        
    }//METHOD
	
    /**
     * Gets a directory from a parent folder and a directory name. Creates the directory if it doesn't exist, and returns an empty File if the program can't create the directory.
     * 
     * @param parentFile Parent Directory
     * @param directoryName Name of the Directory
     * @return File for the Directory
     */
    public static File getDirectory(final File parentFile, final String directoryName)
    {
    	File newFile = new File(new String());
    	
    	if(parentFile != null && parentFile.isDirectory())
    	{
    		newFile = new File(parentFile, directoryName);
    		if(!newFile.isDirectory())
    		{
    			if(!newFile.mkdir())
    			{
    				newFile = new File(new String());
    			
    			}//IF
    			
    		}//IF

    	}//IF
    
    	return newFile;
   
    }//METHOD
    
	/**
	 * Returns an ArrayList<File> of all the files with a given extension(s) within a directory.
	 * 
	 * @param directory Directory from which to search for files. 
	 * @param extensions Extensions of the file types to be returned.
	 * @param checkSubs Whether to check sub-directories for files.
	 * @return ArrayList<File> of all files of a given extension(s) within a directory
	 */
	public static ArrayList<File> getFilesOfType(final File directory, final String[] extensions, final boolean checkSubs)
	{
		ArrayList<File> returnFiles = new ArrayList<>();
		
		if(directory != null && directory.isDirectory())
		{
			ArrayList<File> directories = new ArrayList<>();
			directories.add(directory);
			
			while(directories.size() > 0)
			{
				File[] allFiles = directories.get(0).listFiles();
				
				for(File file : allFiles)
				{
					if(file.isDirectory())
					{
						if(checkSubs)
						{
							directories.add(file);
							
						}//IF
						
					}//IF
					else if(extensions.length == 0)
					{
						returnFiles.add(file);
					}//ELSE IF
					else
					{
						for(String extension : extensions)
						{
							if(file.getAbsolutePath().endsWith(extension))
							{
								returnFiles.add(file);
								break;
								
							}//IF
							
						}//FOR
						
					}//ELSE
					
				}//FOR
				
				directories.remove(0);
				
			}//WHILE
		
		}//IF
		
		return returnFiles;
		
	}//METHOD
	
}//CLASS
