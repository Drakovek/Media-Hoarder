package drakovek.hoarder.gui.swing.listeners;

/**
 * Contains methods to run when an event occurs.
 * 
 * @author Drakovek
 * @version 2.0
 */
public interface DEvent
{
	/**
	 * Runs when an event occurs.
	 * 
	 * @param id ID of the event.
	 * @param value Value for the event.
	 */
	public void event(final String id, final int value);
	
}//CLASS
