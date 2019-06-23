package drakovek.hoarder.gui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Deals with component's action events.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DActionListener implements ActionListener
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
	 * Initializes the DActionListener class.
	 * 
	 * @param event DEvent to call when event occurs
	 * @param id ID of the event
	 * @since 2.0
	 */
	public DActionListener(DEvent event, final String id)
	{
		this.event = event;
		this.id = id;
		
	}//CONSTRUCTOR
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		event.event(id, -1);
		
	}//METHOD

}//CLASS
