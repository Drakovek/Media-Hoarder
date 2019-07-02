package drakovek.hoarder.gui.swing.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import drakovek.hoarder.gui.swing.components.DList;

/**
 * Deals with the user double or triple clicking an item in a DList.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DListClickListener extends MouseAdapter
{
	/**
	 * DEvent to call if an item has been clicked.
	 * 
	 * @since 2.0
	 */
	private DEvent event;
	
	/**
	 * List from which to check for clicks
	 * 
	 * @since 2.0
	 */
	private DList list;
	
	/**
	 * Action ID
	 * 
	 * @since 2.0
	 */
	private String id;
	
	/**
	 * Initializes the DListClickListener class.
	 * 
	 * @param event DEvent to call if an item has been clicked.
	 * @param list List from which to check for clicks
	 * @param id Action ID
	 * @since 2.0
	 */
	public DListClickListener(DEvent event, DList list, final String id)
	{
		this.event = event;
		this.list = list;
		this.id = id;
		
	}//CONSTRUCTOR
	
	@Override
	public void mouseClicked(MouseEvent mEvent)
	{
		if(mEvent.getClickCount() == 2 || mEvent.getClickCount() == 3)
		{
			int index = list.locationToIndex(mEvent.getPoint());
			
			if(index != -1)
            {
				event.event(id, index);

            }//IF
			
		}//IF
		
	}//METHOD
	
}//CLASS
