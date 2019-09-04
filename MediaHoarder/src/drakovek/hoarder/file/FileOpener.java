package drakovek.hoarder.file;

import java.awt.Desktop;
import java.io.File;

/**
 * Contains methods for opening files and links.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class FileOpener
{
	/**
	 * Opens a given file in an external program.
	 * 
	 * @param file Given File
	 * @since 2.0
	 */
	public static void openFile(final File file)
	{
		if(file != null && file.exists())
		{
			if(Desktop.isDesktopSupported())
			{
				
				Desktop desktop = Desktop.getDesktop();
				
				try
				{
					desktop.open(file);
					
				}//TRY
				catch(Exception e){}
				
			}//IF
			
		}//IF
		
	}//METHOD
	
}//CLASS
