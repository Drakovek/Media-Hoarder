package drakovek.hoarder.gui.swing.listeners;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Deals with the enter key being pressed while a component is focused.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DEnterListener extends AbstractAction
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 5006297418196454945L;

	/**
	 * Action ID for when the enter key is pressed; Added to a base ID to indicate that a component had the enter key pressed while focused
	 */
	public static final String ENTER_PRESSED = "ENTER_PRESSED"; //$NON-NLS-1$
	
	/**
	 * DEvent to call when enter pressed.
	 */
	private DEvent event;
	
	/**
	 * Base Action ID for component to listen for
	 */
	private String id;
	
	/**
	 * Initializes the DEnterListener class.
	 * 
	 * @param event DEvent to call when enter pressed.
	 * @param id Base Action ID for component to listen for
	 */
	public DEnterListener(DEvent event, final String id)
	{
		this.event = event;
		this.id = id + ENTER_PRESSED;
		
	}//CONSTRUCTORY
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		event.event(id, -1);
		
	}//METHOD
	
}//CLASS
