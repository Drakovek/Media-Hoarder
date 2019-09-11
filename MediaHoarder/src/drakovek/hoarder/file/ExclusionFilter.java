package drakovek.hoarder.file;

import java.io.File;
import java.io.FileFilter;

/**
 * FileFilter that filters out files with given extensions.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ExclusionFilter implements FileFilter
{
	/**
	 * Extensions of files not to return
	 */
	private String[] excludedExtensions;
	
	/**
	 * Whether to allow returning directories
	 */
	private boolean allowDirectories;
	
	/**
	 * Initializes the ExclusionFilter class.
	 * 
	 * @param excludedExtensions Extensions of files not to return
	 * @param allowDirectories Whether to allow returning directories
	 */
	public ExclusionFilter(final String[] excludedExtensions, final boolean allowDirectories)
	{
		this.excludedExtensions = excludedExtensions;
		this.allowDirectories = allowDirectories;
		
	}//CONSTRUCTOR

	@Override
	public boolean accept(File file)
	{
		if(!file.isHidden() && file.canRead())
		{	
			if(file.isDirectory())
			{
				return allowDirectories;
				
			}//IF
			
			for(int i = 0; i < excludedExtensions.length; i++)
			{
				if(file.getAbsolutePath().toLowerCase().endsWith(excludedExtensions[i]))
				{
					return false;
					
				}//IF
				
			}//FOR
			
			return true;
			
		}//IF
		
		return false;
		
	}//METHOD

}//CLASS
