package drakovek.hoarder.gui;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.dvk.DvkHandler;
import drakovek.hoarder.gui.swing.components.ComponentDisabler;
import drakovek.hoarder.gui.swing.components.DFrame;

/**
 * Basic GUI object that has a built-in DFrame.
 * 
 * @author Drakovek
 * @version 2.0
 */
public abstract class FrameGUI extends BaseGUI implements ComponentDisabler
{
	/**
	 * Main DFrame for the FrameGUI object
	 */
	private DFrame frame;
	
	/**
	 * Program's DvkHandler
	 */
	private DvkHandler dvkHandler;
	
	/**
	 * Initializes the FrameGUI Object
	 * 
	 * @param settings Program Settings
	 * @param dvkHandler Program's DvkHanlder
	 * @param subtitleID ID of the subtitle. If null, just uses the default title
	 */
	public FrameGUI(DSettings settings, DvkHandler dvkHandler, final String subtitleID)
	{
		super(settings);
		frame = new DFrame(this, getTitle(subtitleID));
		this.dvkHandler = dvkHandler;
		
	}//CONSTRUCTOR
	
	/**
	 * Returns the main DFrame for FrameGUI
	 * 
	 * @return Frame
	 */
	public DFrame getFrame()
	{
		return frame;
		
	}//METHOD
	
	/**
	 * Returns the DvkHandler.
	 * 
	 * @return DvkHandler
	 */
	public DvkHandler getDvkHandler()
	{
		return dvkHandler;
		
	}//METHOD
	
	/**
	 * Disposes of the main DFrame.
	 */
	public void dispose()
	{
		frame.dispose();
		frame = null;
		
	}//METHOD
	
}//CLASS
