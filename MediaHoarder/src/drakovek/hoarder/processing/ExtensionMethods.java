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
		if(file == null)
		{
			return null;
			
		}//IF
		
		return getExtension(file.getAbsolutePath());
		
	}//METHOD
	
	/**
	 * Returns the filename of a given file without the extension.
	 * 
	 * @param file Given File
	 * @return Filename Without Extension
	 * @since 2.0
	 */
	public static String removeExtension(final File file)
	{
		if(file == null)
		{
			return null;
			
		}//IF
		
		return removeExtension(file.getName());
		
	}//METHOD
	
	/**
	 * Returns the given filename without its extension.
	 * 
	 * @param filename Given Filename
	 * @return Filename Without Extension
	 * @since 2.0
	 */
	public static String removeExtension(final String filename)
	{
		if(filename == null || filename.length() == 0)
		{
			return new String();
			
		}//IF
		
		int extensionLength = getExtension(filename).length();
		
		return filename.substring(0, filename.length() - extensionLength);
		
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
		if(filename == null || filename.length() == 0)
		{
			return null;
			
		}//IF
		
		int end = filename.length();
		if(filename.contains(Character.toString('?')))
		{
			end = filename.lastIndexOf('?');
			
		}//IF
		
		int start = 0;
		if(end < filename.length())
		{
			start = filename.lastIndexOf('.', end);
			
		}//IF
		else
		{
			start = filename.lastIndexOf('.');
			
		}//ELSE
		
		if(start == -1)
		{
			return new String();
			
		}//IF
		
		return filename.substring(start, end).toLowerCase();
		
	}//METHOD
	
}//CLASS
