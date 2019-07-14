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
	 * Program's DmfHandler
	 * 
	 * @since 2.0
	 */
	private DmfHandler dmfHandler;
	
	/**
	 * Initializes the FrameGUI Object
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHanlder
	 * @param subtitleID ID of the subtitle. If null, just uses the default title
	 * @since 2.0
	 */
	public FrameGUI(DSettings settings, DmfHandler dmfHandler, final String subtitleID)
	{
		super(settings);
		frame = new DFrame(this, settings, getTitle(subtitleID));
		this.dmfHandler = dmfHandler;
		
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
	 * Returns the DmfHandler.
	 * 
	 * @return DmfHandler
	 * @since 2.0
	 */
	public DmfHandler getDmfHandler()
	{
		return dmfHandler;
		
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
