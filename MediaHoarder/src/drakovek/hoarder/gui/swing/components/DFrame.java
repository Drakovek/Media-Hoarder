package drakovek.hoarder.gui.swing.components;
import java.awt.Dimension;

import javax.swing.JFrame;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.ScreenDimensions;
import drakovek.hoarder.gui.swing.compound.DButtonDialog;
import drakovek.hoarder.gui.swing.listeners.DCloseListener;
import drakovek.hoarder.gui.swing.listeners.DEvent;

/**
 * Default Frame object for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DFrame extends JFrame
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = -7020014407451285996L;

	/**
	 * Program Settings
	 * 
	 * @since 2.0
	 */
	private DSettings settings;
	
	/**
	 * GUI Object implementing ComponentDisabler to be called when processRunning boolean changes.
	 * 
	 * @since 2.0
	 */
	private ComponentDisabler disabler;
	
	/**
	 * Called when user attempts to close the frame, if event has been initialized.
	 * 
	 * @since 2.0
	 */
	private DEvent event;
	
	/**
	 * Boolean indicating whether a worker process is running.
	 * 
	 * @since 2.0
	 */
	private boolean processRunning;

	/**
	 * Initializes the DFrame Class
	 * 
	 * @param settings Program Settings
	 * @param disabler GUI Object implementing ComponentDisabler to call when a process starts/stops running
	 * @param title Title of the Frame
	 * @since 2.0
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
	 * @since 2.0
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
	 * 
	 * @since 2.0
	 */
	private void commonInitialize()
	{
		event = null;
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new DCloseListener(this));
		
		ScreenDimensions screen = new ScreenDimensions();
		int width = settings.getFontSize() * settings.getFrameWidth();
		if(width > screen.getMaximumWidth())
		{
			width = screen.getMaximumWidth();
		
		}//IF
		
		int height = settings.getFontSize() * settings.getFrameHeight();
		if(height > screen.getMaximumHeight())
		{
			height = screen.getMaximumHeight();
		
		}//IF
		
		this.setMinimumSize(new Dimension(width, height));
		
		processRunning = false;
	
	}//METHOD
    
	/**
	 * Sets the Frame to call a DEvent when user attempts to close the frame, rather than handling the event internally.
	 * 
	 * @param dEvent DEvent to be called when user attempts to close the frame.
	 * @since 2.0
	 */
	public void interceptFrameClose(DEvent dEvent)
	{
		this.event = dEvent;
		
	}//METOD
	
	/**
	 * Runs when user attempts to close the frame. Executed by DCloseListener.
	 * 
	 * @since 2.0
	 */
	public void closeFrame()
	{
		if(event == null)
		{
			if(isProcessRunning())
			{
				String[] buttonIDs = {DefaultLanguage.OK};
				DButtonDialog buttonDialog = new DButtonDialog(settings);
				buttonDialog.openButtonDialog(this, DefaultLanguage.PROCESS_RUNNING, DefaultLanguage.PROCESS_RUNNING_MESSAGES, buttonIDs);
				
			}//IF
			else
			{
				settings.writeSettings();
	            this.dispose();
	            
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
	 * @since 2.0
	 */
    public boolean isProcessRunning()
    {
    	return processRunning;
    	
    }//METHOD
    
    /**
     * Sets the value of processRunning.
     * 
     * @param processRunning Process Running
     * @since 2.0
     */
    public void setProcessRunning(final boolean processRunning)
    {
    	this.processRunning = processRunning;
    	
    	if(disabler != null)
    	{
        	if(processRunning)
        	{
        		disabler.disableAll();
        	
        	}//IF
        	else
        	{
        		disabler.enableAll();
        		
        	}//ELSE
        	
    	}//IF
    
    }//METHOD
	
}//CLASS
