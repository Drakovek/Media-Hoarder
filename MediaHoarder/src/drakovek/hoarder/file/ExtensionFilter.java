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
	 * Whether to allow all files of any extension
	 * 
	 * @since 2.0
	 */
	private boolean allowAll;
	
	/**
	 * Initializes ExtensionFilter Class
	 * 
	 * @param extensions Extensions to allow
	 * @param allowAll Whether to allow all files of any extension
	 * @since 2.0
	 */
	public ExtensionFilter(final String[] extensions, final boolean allowAll)
	{
		setExtensions(extensions);
		setAllowAll(allowAll);
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the extensions to be filtered.
	 * 
	 * @param extensions Extensions
	 * @since 2.0
	 */
	public void setExtensions(final String[] extensions)
	{
		this.extensions = extensions;
		
	}//METHOD
	
	/**
	 * Sets whether to allow all files of any extension.
	 * 
	 * @param allowAll Allow All
	 * @since 2.0
	 */
	public void setAllowAll(final boolean allowAll)
	{
		this.allowAll = allowAll;
		
	}//METHOD
	
	@Override
	public boolean accept(File file)
	{
		if(!file.isHidden() && file.canRead())
		{	
			if(allowAll || file.isDirectory())
			{
				return true;
				
			}//IF
			
			if(extensions != null)
			{
				for(int i = 0; i < extensions.length; i++)
				{
					if(file.getAbsolutePath().endsWith(extensions[i]))
					{
						return true;
					
					}//IF
				
				}//FOR
				
			}//IF
			
		}//IF

		return false;
		
	}//METHOD

}//CLASS
