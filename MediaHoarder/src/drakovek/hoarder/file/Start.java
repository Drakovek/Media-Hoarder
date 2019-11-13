package drakovek.hoarder.file;

import javax.swing.UIManager;

import org.apache.commons.logging.LogFactory;

import drakovek.hoarder.file.dvk.DvkHandler;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.SettingsValues;
import drakovek.hoarder.gui.modes.ModeContainerGUI;
import drakovek.hoarder.gui.settings.SettingsGUI;
import drakovek.hoarder.gui.swing.compound.DButtonDialog;

/**
 * Main class for starting the Media Hoarder Program
 * 
 * @author Drakovek
 * @version 2.0
 */
public class Start 
{
	/**
	 * Starts the Media Hoarder Program
	 * 
	 * @param args (Not Used)
	 */
	public static void main(String[] args)
	{
		startGUI();
		
	}//METHOD
	
	/**
	 * Starts the GUI for the program.
	 */
	public static void startGUI()
	{
		DSettings settings = new DSettings();
		DvkHandler dvkHandler = new DvkHandler();
		
		//Turn off HtmlUnit warnings
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog"); //$NON-NLS-1$ //$NON-NLS-2$
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF); //$NON-NLS-1$
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF); //$NON-NLS-1$
	    
		
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
		
		startGUI(settings, dvkHandler);
	
	}//METHOD
	
	/**
	 * Starts the GUI for the program based on the program settings.
	 * 
	 * @param settings Program Settings
	 * @param dvkHandler Program's DvkHandler
	 */
	public static void startGUI(DSettings settings, DvkHandler dvkHandler)
	{
		settings.writeSettings();
		dvkHandler.resetFilterStrings();
		
		//SET LOOK AND FEEL
        try
        {
            UIManager.setLookAndFeel(settings.getTheme());

        }//TRY
        catch(Exception e)
        {
            System.out.println("Failed to set look and feel - OperationGUI.OperationGUI"); //$NON-NLS-1$

        }//CATCH (Exception e)
        
        
        if(settings.getDvkDirectories().size() > 0)
        {
        	new ModeContainerGUI(settings, dvkHandler);
        	
        }//IF
        else
        {
        	//ASKS USER TO ADD DVK DIRECTORIES IF NONE ARE SPECIFIED
        	SettingsGUI settingsGUI = new SettingsGUI(settings, dvkHandler, null);
        	settingsGUI.getFrame().setAllowExit(false);
        	settingsGUI.setSettingMode(SettingsValues.DVK_DIRECTORIES);
        	DButtonDialog buttonDialog = new DButtonDialog(settings);
        	String[] buttonIDs = {CommonValues.OK};
        	buttonDialog.openButtonDialog(settingsGUI.getFrame(), SettingsValues.NO_DIRECTORIES_TITLE, SettingsValues.NO_DIRECTORIES_MESSAGES, buttonIDs);
        	settingsGUI.getFrame().setAllowExit(true);
        	
        }//ELSE
		
	}//METHOD
	
}//CLASS
