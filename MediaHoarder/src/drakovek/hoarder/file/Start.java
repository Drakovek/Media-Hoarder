package drakovek.hoarder.file;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import drakovek.hoarder.gui.modes.ModeContainerGUI;
import drakovek.hoarder.media.ImagePanel;

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
		//TODO Reinstate commented out code. Following code is for test purposes only.
		
		//GET IMAGE FILE
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		File imageFile = null;
		while(imageFile == null || !imageFile.exists() || imageFile.isDirectory())
		{
			System.out.println("Enter Image File"); //$NON-NLS-1$
			try
			{
				imageFile = null;
				String input = bufferedReader.readLine();
				if(input != null)
				{
					imageFile = new File(input);
					
				}//IF
				
			}//TRY
			catch(IOException e){}
			
		}//WHILE
		
		DSettings settings = new DSettings();
		JFrame testFrame = new JFrame("Test Frame"); //$NON-NLS-1$
		ImagePanel imagePanel = new ImagePanel(settings, imageFile);
		JScrollPane imageScroll = new JScrollPane(imagePanel);
		testFrame.getContentPane().add(imageScroll, BorderLayout.CENTER);
		testFrame.pack();
		testFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		testFrame.setLocationRelativeTo(null);
		testFrame.setVisible(true);
		
		imageFile = null;
		while(imageFile == null || !imageFile.exists() || imageFile.isDirectory())
		{
			System.out.println("Enter Image File"); //$NON-NLS-1$
			try
			{
				imageFile = null;
				String input = bufferedReader.readLine();
				if(input != null)
				{
					imageFile = new File(input);
					
				}//IF
				
			}//TRY
			catch(IOException e){}
			
		}//WHILE
		
		imagePanel.setFile(imageFile);
    	
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
