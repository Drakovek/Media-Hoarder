package drakovek.hoarder.gui.modes;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Contains methods for creating the main GUI to switch between operation modes.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ModeContainerGUI
{
	/**
	 * Initializes the ModeContainerGUI class
	 * 
	 * @since 2.0
	 */
	public ModeContainerGUI()
	{
		JFrame modeFrame = new JFrame("Media Hoarder");
		
		modeFrame.pack();
		modeFrame.setSize(new Dimension(100, 100));
		modeFrame.setVisible(true);

	}//CONSTRUCTOR
	
}//CLASS
