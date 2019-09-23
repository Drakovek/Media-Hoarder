package drakovek.hoarder.gui.swing.components;
import java.awt.Dimension;

import javax.swing.JFrame;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.CommonValues;
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
	 * Program Settings
	 */
	private DSettings settings;
	
	/**
	 * GUI Object implementing ComponentDisabler to be called when processRunning boolean changes.
	 */
	private ComponentDisabler disabler;
	
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
	 * @param settings Program Settings
	 * @param disabler GUI Object implementing ComponentDisabler to call when a process starts/stops running
	 * @param title Title of the Frame
	 */
	public DFrame(DSettings settings, final String title)
	{
		super(title);
		this.settings = settings;
		disabler = null;

		commonInitialize();
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes the DFrame Class
	 * 
	 * @param settings Program Settings
	 * @param disabler GUI Object implementing ComponentDisabler to call when a process starts/stops running
	 * @param title Title of the Frame
	 */
	public DFrame(ComponentDisabler disabler, DSettings settings, final String title)
	{
		super(title);
		this.settings = settings;
		this.disabler = disabler;

		commonInitialize();
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes features of the DFrame shared between all of DFrame's constructors.
	 */
	private void commonInitialize()
	{
		event = null;
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new DCloseListener(this));
		
		this.setMinimumSize(getRestrictedDimensions(0, 0));
		
		allowExit = true;
		processRunning = false;
	
	}//METHOD
	
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
		int minWidth = settings.getFrameWidth() * settings.getFontSize();
		int minHeight = settings.getFrameHeight() * settings.getFontSize();
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
				settings.writeSettings();
	            this.dispose();

			}//IF
			else if(isProcessRunning())
			{
				String[] buttonIDs = {CommonValues.OK};
				DButtonDialog buttonDialog = new DButtonDialog(settings);
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
    	
    	if(disabler != null)
    	{
        	if(allowExit)
        	{
        		disabler.enableAll();
        	
        	}//IF
        	else
        	{
        		disabler.disableAll();
        		
        	}//ELSE
        	
    	}//IF
    	
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
