package drakovek.hoarder.gui.swing.components;
import java.awt.Dimension;

import javax.swing.JFrame;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.ScreenDimensions;
import drakovek.hoarder.gui.swing.compound.DButtonDialog;
import drakovek.hoarder.gui.swing.listeners.DCloseListener;

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
	 * Boolean indicating whether a worker process is running.
	 * 
	 * @since 2.0
	 */
	private boolean processRunning;
	
	//TODO replace width/height modifiers with settings value\
	
	/**
	 * TEMPORARY
	 */
	private static final int WIDTH_MULTIPLIER = 20;
	
	/**
	 * TEMPORARY
	 */
	private static final int HEIGHT_MULTIPLIER = 15;
	
	/**
	 * Language variable for title of the "Process Running" error message.
	 * 
	 * @since 2.0
	 */
	private static final String PROCESS_RUNNING = "process_running"; //$NON-NLS-1$
	
	/**
	 * Language variable for message of the "Process Running" error message.
	 * 
	 * @since 2.0
	 */
	private static final String[] PROCESS_RUNNING_MESSAGE = {"cancel_process_message1", "cancel_process_message2"};  //$NON-NLS-1$//$NON-NLS-2$
	
	/**
	 * Initializes the DFrame Class
	 * 
	 * @param settings Program Settings
	 * @param disabler GUI Object implementing ComponentDisabler to call when a process starts/stops running
	 * @param title Title of the Frame
	 * @since 2.0
	 */
	public DFrame(DSettings settings, ComponentDisabler disabler, final String title)
	{
		super(title);
		this.settings = settings;
		this.disabler = disabler;
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new DCloseListener(this));
		
		ScreenDimensions screen = new ScreenDimensions();
		int width = settings.getFontSize() * WIDTH_MULTIPLIER;
		if(width > screen.getMaximumWidth())
		{
			width = screen.getMaximumWidth();
		
		}//IF
		
		int height = settings.getFontSize() * HEIGHT_MULTIPLIER;
		if(height > screen.getMaximumHeight())
		{
			height = screen.getMaximumHeight();
		
		}//IF
		
		this.setMinimumSize(new Dimension(width, height));
		
		processRunning = false;
		
	}//CONSTRUCTOR
    
	/**
	 * Runs when user attempts to close the frame. Executed by DCloseListener.
	 * 
	 * @since 2.0
	 */
	public void closeFrame()
	{
		if(isProcessRunning())
		{
			String[] buttonIDs = {DefaultLanguage.OK};
			DButtonDialog buttonDialog = new DButtonDialog(settings);
			buttonDialog.openButtonDialog(this, PROCESS_RUNNING, PROCESS_RUNNING_MESSAGE, buttonIDs);
			
		}//IF
		else
		{
			settings.writeSettings();
            this.dispose();
            
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
    	
    	if(processRunning)
    	{
    		disabler.disableAll();
    	
    	}//IF
    	else
    	{
    		disabler.enableAll();
    		
    	}//ELSE
    	
    }//METHOD
	
}//CLASS
