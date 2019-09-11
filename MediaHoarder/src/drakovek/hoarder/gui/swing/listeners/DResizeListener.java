package drakovek.hoarder.gui.swing.listeners;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Deals with a component being resized.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DResizeListener implements ComponentListener
{
	/**
	 * Action ID for the component being resized
	 */
	public static final String RESIZE = "RESIZE"; //$NON-NLS-1$
	
	/**
	 * DEvent to call when component is resized.
	 */
	private DEvent event;
	
	/**
	 * ActionID to add to the RESIZE ID; If null, uses only the RESIZE ID
	 */
	private String id;
	
	/**
	 * Initializes the DResizeListener class.
	 * 
	 * @param event DEvent to call when component is resized.
	 * @param id ActionID to add to the RESIZE ID; If null, uses only the RESIZE ID
	 */
	public DResizeListener(DEvent event, final String id)
	{
		this.event = event;
		
		if(id == null || id.length() == 0)
		{
			this.id = RESIZE;
			
		}//IF
		else
		{
			this.id = id + RESIZE;
			
		}//ELSE
		
	}//CONSTRUCTOR
	
	@Override
	public void componentResized(ComponentEvent compEvent)
	{
		event.event(id, -1);
		
	}//METHOD

	@Override
	public void componentHidden(ComponentEvent compEvent){}

	@Override
	public void componentMoved(ComponentEvent compEvent){}

	@Override
	public void componentShown(ComponentEvent compEvent){}
	
}//CLASS
