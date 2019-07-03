package drakovek.hoarder.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.UIManager;

import drakovek.hoarder.file.dmf.DMF;
import drakovek.hoarder.gui.modes.ModeContainerGUI;
import drakovek.hoarder.processing.StringMethods;

/**
 * Main class for starting the Media Hoarder Program
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class Start 
{
	/**
	 * Starts the Media Hoarder Program
	 * 
	 * @param args Not Used
	 * @since 2.0
	 */
	public static void main(String[] args)
	{
		//startGUI();

		//TODO Use commented out code. Following code is for test purposes only.
        BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(System.in));
        
        while(true)
        {
        	String input = null;
    		File file = null;
    		
    		while(file == null || !file.exists() || file.isDirectory())
    		{
    			System.out.println("Enter DMF file path:"); //$NON-NLS-1$
    			
    	        try
    	        {
    	            input = bufferedReader.readLine();
    	        
    	            if(input != null)
    				{
    					file = new File(input);
    					
    				}//IF
    	            
    	        }
    	        catch (IOException e){}
    			
    		}//WHILE
    		
    		DMF myDMF = new DMF(file);
    		System.out.println();
    		System.out.println("[DMF]"); //$NON-NLS-1$
    		System.out.println("DMF File: " + myDMF.getDmfFile().getAbsolutePath()); //$NON-NLS-1$
    		System.out.println("ID: " + myDMF.getID()); //$NON-NLS-1$
    		
    		System.out.println();
    		System.out.println("[INFO]"); //$NON-NLS-1$
    		System.out.println("Title: " + myDMF.getTitle()); //$NON-NLS-1$
    		System.out.println("Authors: " + StringMethods.arrayToString(myDMF.getAuthors())); //$NON-NLS-1$
    		System.out.println("Time: " + myDMF.getTimeString()); //$NON-NLS-1$
    		System.out.println("Web Tags: " + StringMethods.arrayToString(myDMF.getWebTags())); //$NON-NLS-1$
    		System.out.println("Description: " + myDMF.getDescription()); //$NON-NLS-1$
    		
    		System.out.println();
    		System.out.println("[DMF]"); //$NON-NLS-1$
    		System.out.println("Page URL: " + myDMF.getPageURL()); //$NON-NLS-1$
    		System.out.println("Media URL: " + myDMF.getMediaURL()); //$NON-NLS-1$
    		
    		System.out.println();
    		System.out.println("[FILE]"); //$NON-NLS-1$
    		System.out.println("Media File: " + myDMF.getMediaFile().getAbsolutePath()); //$NON-NLS-1$
    		System.out.println("Last IDs: " + StringMethods.arrayToString(myDMF.getLastIDs())); //$NON-NLS-1$
    		System.out.println("Next IDs: " + StringMethods.arrayToString(myDMF.getNextIDs())); //$NON-NLS-1$
    		System.out.println("First: " + Boolean.toString(myDMF.isFirstInSection())); //$NON-NLS-1$
    		System.out.println("Last: " + Boolean.toString(myDMF.isLastInSection())); //$NON-NLS-1$
    		
    		System.out.println();
    		System.out.println("[USER]"); //$NON-NLS-1$
    		System.out.println("Sequence Title: " + myDMF.getSequenceTitle()); //$NON-NLS-1$
    		System.out.println("Section Title: " + myDMF.getSectionTitle()); //$NON-NLS-1$
    		System.out.println("Branch Titles: " + StringMethods.arrayToString(myDMF.getBranchTitles())); //$NON-NLS-1$
    		System.out.println("Rating: " + Integer.toString(myDMF.getRating())); //$NON-NLS-1$
    		System.out.println("User Tags: " + StringMethods.arrayToString(myDMF.getUserTags())); //$NON-NLS-1$
    		
    		System.out.println();
    		
        }//WHILE
		
	}//METHOD
	
	/**
	 * Starts the GUI for the program.
	 * 
	 * @since 2.0
	 */
	public static void startGUI()
	{
		DSettings settings = new DSettings();
		startGUI(settings);
	
	}//METHOD
	
	/**
	 * Starts the GUI for the program based on the program settings.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public static void startGUI(DSettings settings)
	{
		//SET ANTIALIASING
		if(settings.getFontAA())
		{
			System.setProperty("awt.useSystemAAFontSettings", "on");  //$NON-NLS-1$//$NON-NLS-2$
			System.setProperty("swing.aatext", "true");  //$NON-NLS-1$//$NON-NLS-2$
			
		}//IF
		else
		{
			System.setProperty("awt.useSystemAAFontSettings", "off"); //$NON-NLS-1$ //$NON-NLS-2$
			System.setProperty("swing.aatext", "false"); //$NON-NLS-1$ //$NON-NLS-2$
			
		}//ELSE
		
		//SET LOOK AND FEEL
        try
        {
            UIManager.setLookAndFeel(settings.getTheme());

        }//TRY
        catch(Exception e)
        {
            System.out.println("Failed to set look and feel - OperationGUI.OperationGUI"); //$NON-NLS-1$

        }//CATCH (Exception e)
        
		new ModeContainerGUI(settings);
		
	}//METHOD
	
}//CLASS
