package drakovek.hoarder.gui;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.dmf.DmfHandler;
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
	 * Program's DmfHandler
	 */
	private DmfHandler dmfHandler;
	
	/**
	 * Initializes the FrameGUI Object
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHanlder
	 * @param subtitleID ID of the subtitle. If null, just uses the default title
	 */
	public FrameGUI(DSettings settings, DmfHandler dmfHandler, final String subtitleID)
	{
		super(settings);
		frame = new DFrame(this, getTitle(subtitleID));
		this.dmfHandler = dmfHandler;
		
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
	 * Returns the DmfHandler.
	 * 
	 * @return DmfHandler
	 */
	public DmfHandler getDmfHandler()
	{
		return dmfHandler;
		
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
