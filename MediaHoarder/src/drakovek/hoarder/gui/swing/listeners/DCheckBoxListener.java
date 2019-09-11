package drakovek.hoarder.gui.swing.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import drakovek.hoarder.processing.BooleanInt;

/**
 * Deals with a CheckBox being selected or unselected.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DCheckBoxListener implements ItemListener
{
	/**
	 * DEvent to call when CheckBox is selected or unselected
	 */
	private DEvent event;
	
	/**
	 * Action ID for the CheckBox
	 */
	private String id;
	
	/**
	 * Initializes the DCheckBoxListener class.
	 * 
	 * @param event DEvent to call when CheckBox is selected or unselected
	 * @param id Action ID for the CheckBox
	 */
	public DCheckBoxListener(DEvent event, final String id)
	{
		this.event = event;
		this.id = id;
		
	}//CONSTRUCTOR
	
	@Override
	public void itemStateChanged(ItemEvent itemEvent)
	{
		boolean selected = (itemEvent.getStateChange() == ItemEvent.SELECTED);
		event.event(id, BooleanInt.getInt(selected));
		
	}//METHOD

}//CLASS
