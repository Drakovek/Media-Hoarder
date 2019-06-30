package drakovek.hoarder.gui;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.gui.swing.components.ComponentDisabler;
import drakovek.hoarder.gui.swing.components.DFrame;

/**
 * Basic GUI object that has a built-in DFrame.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public abstract class FrameGUI extends BaseGUI implements ComponentDisabler
{
	/**
	 * Main DFrame for the FrameGUI object
	 * 
	 * @since 2.0
	 */
	private DFrame frame;
	
	/**
	 * Initializes the FrameGUI Object
	 * 
	 * @param settings Program Settings
	 * @param frameTitle Title of the main Frame
	 * @since
	 */
	public FrameGUI(DSettings settings, final String frameTitle)
	{
		super(settings);
		frame = new DFrame(this, settings, frameTitle);
		
	}//CONSTRUCTOR
	
	/**
	 * Returns the main DFrame for FrameGUI
	 * 
	 * @return Frame
	 * @since 2.0
	 */
	public DFrame getFrame()
	{
		return frame;
		
	}//METHOD
	
	/**
	 * Disposes of the main DFrame.
	 * 
	 * @since 2.0
	 */
	public void dispose()
	{
		frame.dispose();
		frame = null;
		
	}//METHOD
	
}//CLASS
