package drakovek.hoarder.file;

import java.io.File;
import java.io.FileFilter;

/**
 * FileFilter that returns true if file is a directory or has a given extension.
 *
 * @author Drakovek
 * @version 1.0
 * @since 1.0
 */
public class ExtensionFilter implements FileFilter
{
	/**
	 * Extensions to allow, should start with '.'
	 * 
	 * @since 2.0
	 */
	private String[] extensions;
	
	/**
	 * Initializes ExtensionFilter Class
	 * 
	 * @param extensions Extensions to allow
	 * @since 2.0
	 */
	public ExtensionFilter(final String[] extensions)
	{
		this.extensions = extensions;
		
	}//CONSTRUCTOR
	
	@Override
	public boolean accept(File file)
	{
		if(!file.isHidden() && file.canRead())
		{	
			if(file.isDirectory())
			{
				return true;
				
			}//IF
			
			for(int i = 0; i < extensions.length; i++)
			{
				if(file.getAbsolutePath().endsWith(extensions[i]))
				{
					return true;
					
				}//IF
				
			}//FOR
			
		}//IF

		return false;
		
	}//METHOD

}//CLASS
