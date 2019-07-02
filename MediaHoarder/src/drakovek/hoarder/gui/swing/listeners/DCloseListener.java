package drakovek.hoarder.gui.swing.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import drakovek.hoarder.gui.swing.components.DFrame;

/**
 * Contains methods to listen for a DFrame attempting to close.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DCloseListener implements WindowListener
{
	/**
	 * Event id for an attempt at closing the frame.
	 * 
	 * @since 2.0
	 */
	public static final String FRAME_CLOSE_EVENT = "frame_close_event"; //$NON-NLS-1$
	
	/**
	 * DFrame linked to the class, being listened to for closing event.
	 * 
	 * @since 2.0
	 */
	private DFrame frame;
	
	/**
	 * Initializes the DCloseListener class.
	 * 
	 * @param frame Linked DFrame
	 * @since 2.0
	 */
	public DCloseListener(DFrame frame)
	{
		this.frame = frame;
		
	}//CONSTRUCTOR
	
	@Override
	public void windowClosing(WindowEvent arg0)
	{
		frame.closeFrame();
		
	}//METHOD
	
	@Override
	public void windowActivated(WindowEvent arg0){}

	@Override
	public void windowClosed(WindowEvent arg0){}

	@Override
	public void windowDeactivated(WindowEvent arg0){}

	@Override
	public void windowDeiconified(WindowEvent arg0){}

	@Override
	public void windowIconified(WindowEvent arg0){}

	@Override
	public void windowOpened(WindowEvent arg0){}

}//CLASS
