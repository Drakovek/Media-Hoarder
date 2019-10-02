package drakovek.hoarder.gui.swing.components;
import java.awt.Dimension;

import javax.swing.JFrame;

import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.ScreenDimensions;
import drakovek.hoarder.gui.swing.compound.DButtonDialog;
import drakovek.hoarder.gui.swing.listeners.DCloseListener;
import drakovek.hoarder.gui.swing.listeners.DEvent;

/**
 * Default Frame object for the program.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DFrame extends JFrame
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -7020014407451285996L;
	
	/**
	 * Main frame GUI paired with this frame
	 */
	private FrameGUI frameGUI;
	
	/**
	 * Called when user attempts to close the frame, if event has been initialized.
	 */
	private DEvent event;
	
	/**
	 * Boolean indicating whether a worker process is running.
	 */
	private boolean processRunning;
	
	/**
	 * Whether to allow the frame to close
	 */
	private boolean allowExit;
	
	/**
	 * Initializes the DFrame Class
	 * 
	 * @param frameGUI FrameGUI paired to the frame
	 * @param title Title of the Frame
	 */
	public DFrame(FrameGUI frameGUI, final String title)
	{
		super(title);
		this.frameGUI = frameGUI;

		event = null;
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new DCloseListener(this));
		
		this.setMinimumSize(getRestrictedDimensions(0, 0));
		
		allowExit = true;
		processRunning = false;
		
	}//CONSTRUCTOR
	/**
	 * Sets the minimum size of the frame, ensuring it does not exceed the screen dimensions.
	 * 
	 * @param width Frame Width
	 * @param height Frame Height
	 */
	public void setSizeRestrictive(final int width, final int height)
	{
		this.setMinimumSize(getRestrictedDimensions(width, height));
		
	}//METHOD
	
	/**
	 * Packs the frame while ensuring it does not exceed the screen dimensions.
	 */
	public void packRestricted()
	{
		pack();
		setSize(getRestrictedDimensions(getWidth(), getHeight()));
		
	}//METHOD
	
	/**
	 * Returns dimension based on the desired width and height that does not exceed the screen dimensions, and is not smaller than the minimum frame size, if possible.
	 * 
	 * @param width Desired Width
	 * @param height Desired Height
	 * @return Restricted Dimension
	 */
	private Dimension getRestrictedDimensions(final int width, final int height)
	{
		int newWidth = width;
		int newHeight = height;
		
		//MAKE SURE DIMENSIONS AREN'T TOO SMALL
		int minWidth = frameGUI.getSettings().getFrameWidth() * frameGUI.getSettings().getFontSize();
		int minHeight = frameGUI.getSettings().getFrameHeight() * frameGUI.getSettings().getFontSize();
		if(newWidth < minWidth)
		{
			newWidth = minWidth;
			
		}//IF
		
		if(newHeight < minHeight)
		{
			newHeight = minHeight;
			
		}//IF
		
		//MAKE SURE DIMENSIONS AREN'T TOO LARGE
		ScreenDimensions screen = new ScreenDimensions();
		if(newWidth > screen.getMaximumWidth())
		{
			newWidth = screen.getMaximumWidth();
			
		}//IF
		
		if(newHeight > screen.getMaximumHeight())
		{
			newHeight = screen.getMaximumHeight();
			
		}//IF
		
		return new Dimension(newWidth, newHeight);
		
	}//METHOD
    
	/**
	 * Sets the Frame to call a DEvent when user attempts to close the frame, rather than handling the event internally.
	 * 
	 * @param dEvent DEvent to be called when user attempts to close the frame.
	 */
	public void interceptFrameClose(DEvent dEvent)
	{
		this.event = dEvent;
		
	}//METOD
	
	/**
	 * Runs when user attempts to close the frame. Executed by DCloseListener.
	 */
	public void closeFrame()
	{
		if(event == null)
		{
			if(allowExit)
			{
				frameGUI.getSettings().writeSettings();
				frameGUI.dispose();

			}//IF
			else if(isProcessRunning())
			{
				String[] buttonIDs = {CommonValues.OK};
				DButtonDialog buttonDialog = new DButtonDialog(frameGUI.getSettings());
				buttonDialog.openButtonDialog(this, CommonValues.PROCESS_RUNNING, CommonValues.PROCESS_RUNNING_MESSAGES, buttonIDs);
	            
			}//ELSE
			
		}//IF
		else
		{
			event.event(DCloseListener.FRAME_CLOSE_EVENT, -1);
			
		}//ELSE
		
	}//METHOD

	/**
	 * Returns the value of processRunning. Should indicate whether a worker is currently running.
	 * 
	 * @return Process Running
	 */
    public boolean isProcessRunning()
    {
    	return processRunning;
    	
    }//METHOD
    
    /**
     * Sets the value of processRunning.
     * 
     * @param processRunning Process Running
     */
    public void setProcessRunning(final boolean processRunning)
    {
    	this.processRunning = processRunning;
    	setAllowExit(!processRunning);
    
    }//METHOD
    
    /**
     * Sets the value of allowExit
     * 
     * @param allowExit Whether to allow the frame to close
     */
    public void setAllowExit(final boolean allowExit)
    {
    	this.allowExit = allowExit;
    	
    	if(allowExit)
    	{
    		frameGUI.enableAll();
    		
    	}//IF
    	else
    	{
    		frameGUI.disableAll();
    		
    	}//ELSE
    	
    }//METHOD
    
    /**
     * Returns whether the frame is allowed to close.
     * 
     * @return Whether the frame is allowed to close
     */
    public boolean getAllowExit()
    {
    	return allowExit;
    	
    }//METHOD
	
}//CLASS
