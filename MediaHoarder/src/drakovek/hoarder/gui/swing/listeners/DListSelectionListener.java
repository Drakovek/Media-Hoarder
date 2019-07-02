package drakovek.hoarder.gui.swing.listeners;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Deals with a DList being selected.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DListSelectionListener implements ListSelectionListener
{
	/**
	 * DEvent to call when event occurs
	 * 
	 * @since 2.0
	 */
	private DEvent event;
	
	/**
	 * ID of the event
	 * 
	 * @since 2.0
	 */
	private String id;
	
	/**
	 * Initializes the DListSelectionListener class.
	 * 
	 * @param event DEvent to call when list item is selected.
	 * @param id ID of the list selection event.
	 * @since 2.0
	 */
	public DListSelectionListener(DEvent event, final String id)
	{
		this.event = event;
		this.id = id;
		
	}//CONSTRUCTOR
	
	@Override
	public void valueChanged(ListSelectionEvent arg0)
	{
		event.event(id, -1);
		
	}//METHOD

}//CLASS
