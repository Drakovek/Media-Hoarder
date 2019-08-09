package drakovek.hoarder.processing;

import java.io.File;

/**
 * Contains methods for getting file extension from files and file URLs
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ExtensionMethods
{
	/**
	 * Returns the file extension from a given file.
	 * 
	 * @param file Given File
	 * @return File Extension
	 * @since 2.0
	 */
	public static String getExtension(final File file)
	{
		return getExtension(file.getAbsolutePath());
		
	}//METHOD
	
	/**
	 * Returns the file extension from a given filename.
	 * 
	 * @param filename Given Filename
	 * @return File Extension
	 * @since 2.0
	 */
	public static String getExtension(final String filename)
	{
		int i = filename.lastIndexOf('.');
		
		if(i == -1)
		{
			return new String();
			
		}//IF
		
		return filename.substring(i).toLowerCase();
		
	}//METHOD
	
}//CLASS
