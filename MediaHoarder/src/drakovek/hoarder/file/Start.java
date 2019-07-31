package drakovek.hoarder.file;

import java.io.File;

import javax.swing.UIManager;

import org.apache.commons.logging.LogFactory;

import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.modes.ModeContainerGUI;
import drakovek.hoarder.gui.settings.SettingsGUI;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.compound.DButtonDialog;
import drakovek.hoarder.gui.swing.compound.DFileChooser;

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
		
		//TEMP CODE FOR FILE CHOOSER TESTING
		DSettings settings = new DSettings();
		DFrame testFrame = new DFrame(settings, "Test Frame"); //$NON-NLS-1$
		testFrame.packRestricted();
		testFrame.setLocationRelativeTo(null);
		testFrame.setVisible(true);
		
		String[] extensions = {".txt", ".dmf"};  //$NON-NLS-1$//$NON-NLS-2$
		DFileChooser fileChooser = new DFileChooser(settings);
		File file = fileChooser.openSaveDialog(testFrame, null, extensions);
				
		if(file != null)
		{
			System.out.println(file.getAbsolutePath());
			
		}//IF
    	
	}//METHOD
	
	/**
	 * Starts the GUI for the program.
	 * 
	 * @since 2.0
	 */
	public static void startGUI()
	{
		DSettings settings = new DSettings();
		DmfHandler dmfHandler = new DmfHandler();
		
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
		
		startGUI(settings, dmfHandler);
	
	}//METHOD
	
	/**
	 * Starts the GUI for the program based on the program settings.
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHandler
	 * @since 2.0
	 */
	public static void startGUI(DSettings settings, DmfHandler dmfHandler)
	{
		settings.writeSettings();
		
		//SET LOOK AND FEEL
        try
        {
            UIManager.setLookAndFeel(settings.getTheme());

        }//TRY
        catch(Exception e)
        {
            System.out.println("Failed to set look and feel - OperationGUI.OperationGUI"); //$NON-NLS-1$

        }//CATCH (Exception e)
        
        
        if(settings.getDmfDirectories().size() > 0)
        {
        	new ModeContainerGUI(settings, dmfHandler);
        	
        }//IF
        else
        {
        	//ASKS USER TO ADD DMF DIRECTORIES IF NONE ARE SPECIFIED
        	SettingsGUI settingsGUI = new SettingsGUI(settings, dmfHandler, null);
        	settingsGUI.getFrame().setAllowExit(false);
        	settingsGUI.setSettingMode(DefaultLanguage.DMF_DIRECTORIES);
        	DButtonDialog buttonDialog = new DButtonDialog(settings);
        	String[] buttonIDs = {DefaultLanguage.OK};
        	buttonDialog.openButtonDialog(settingsGUI.getFrame(), DefaultLanguage.NO_DIRECTORIES_TITLE, DefaultLanguage.NO_DIRECTORIES_MESSAGES, buttonIDs);
        	settingsGUI.getFrame().setAllowExit(true);
        	
        }//ELSE
		
	}//METHOD
	
}//CLASS
