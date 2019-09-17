package drakovek.hoarder.gui.swing.components;

/**
 * Contains methods for enabling/disabling all swing components in a frame. For use when a process starts/stops.
 * 
 * @author Drakovek
 * @version 2.0
 */
public interface ComponentDisabler 
{
	/**
	 * Enables all swing components.
	 */
	public void enableAll();
	
	/**
	 * Disables all swing components.
	 */
	public void disableAll();
	
}//INTERFACE ComponentDisabler
