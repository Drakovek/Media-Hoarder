package drakovek.hoarder.gui.swing.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

/**
 * Deals with the user double or triple clicking an item in a DList.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DListClickListener extends MouseAdapter
{
	/**
	 * Default action id for when a list element is clicked.
	 */
	public static final String LIST_CLICKED = "list_clicked"; //$NON-NLS-1$
	
	/**
	 * DEvent to call if an item has been clicked.
	 */
	private DEvent event;
	
	/**
	 * List from which to check for clicks
	 */
	@SuppressWarnings("rawtypes")
	private JList list;
	
	/**
	 * Action ID
	 */
	private String id;
	
	/**
	 * Initializes the DListClickListener class.
	 * 
	 * @param event DEvent to call if an item has been clicked.
	 * @param list List from which to check for clicks
	 * @param id Action ID
	 */
	@SuppressWarnings("rawtypes")
	public DListClickListener(DEvent event, JList list, final String id)
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
