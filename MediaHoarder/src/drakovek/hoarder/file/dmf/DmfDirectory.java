package drakovek.hoarder.file.dmf;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.ExtensionFilter;

/**
 * Contains all DMF information for a single directory (no sub-directories)
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DmfDirectory
{
	/**
	 * ArrayList containing DMF Files from DMF
	 * 
	 * @since 2.0
	 */
	private ArrayList<File> dmfFiles;
	
	/**
	 * Initializes the DmfDirectory class to start with no DMF information.
	 * 
	 * @since 2.0
	 */
	public DmfDirectory()
	{
		clearDMFs();
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes the DmfDirectory to contain the information for a given directory.
	 * 
	 * @param dmfFolder Directory from which to search for DMFs
	 * @since 2.0
	 */
	public DmfDirectory(final File dmfFolder)
	{
		loadDMFs(dmfFolder);
		
	}//CONSTRUCTOR
	
	/**
	 * Clears all the DMF information from the object.
	 * 
	 * @since 2.0
	 */
	private void clearDMFs()
	{
		dmfFiles = new ArrayList<>();
		
	}//METHOD
	
	/**
	 * Loads the information from all the DMFs in a given directory.
	 * 
	 * @param dmfFolder Directory from which to search for DMFs
	 * @since 2.0
	 */
	public void loadDMFs(final File dmfFolder)
	{
		clearDMFs();
		
		if(dmfFolder != null && dmfFolder.isDirectory())
		{
			String[] extension = {DMF.DMF_EXTENSION};
			File[] dmfs = dmfFolder.listFiles(new ExtensionFilter(extension));
			
			for(File dmf: dmfs)
			{
				addDMF(dmf);
				
			}//FOR
			
		}//IF
		
	}//METHOD
	
	/**
	 * Adds the information from a single DMF to the objects information variables.
	 * 
	 * @param dmfFile DMF File
	 * @since 2.0
	 */
	public void addDMF(final File dmfFile)
	{
		addDMF(new DMF(dmfFile));
		
	}//METHOD
	
	/**
	 * Adds the information from a single DMF to the objects information variables.
	 * 
	 * @param dmf Single DMF
	 * @since 2.0
	 */
	public void addDMF(DMF dmf)
	{
		if(dmf.isValid())
		{
			//DMF
			dmfFiles.add(dmf.getDmfFile());
			
		}//IF
		
	}//METHOD
	
	/**
	 * Returns the ArrayList of DMF Files
	 * 
	 * @return DMF Files
	 * @since 2.0
	 */
	public ArrayList<File> getDmfFiles()
	{
		return dmfFiles;
		
	}//METHOD
	
}//CLASS
