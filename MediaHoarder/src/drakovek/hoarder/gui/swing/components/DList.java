package drakovek.hoarder.gui.swing.components;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.listeners.DListSelectionListener;

/**
 * Default List object for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DList extends JList<String>
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = -5532350862927679564L;

	/**
	 * Initializes the DList class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param multipleSelect Whether to allow selecting multiple items in the list.
	 * @param id Event ID for the list.
	 */
	public DList(BaseGUI baseGUI, final boolean multipleSelect, final String id)
	{
		super();
		
		if(multipleSelect)
		{
			this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		}//IF
		else
		{
			this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		}//ELSE
		
		this.setLayoutOrientation(VERTICAL);
		this.addListSelectionListener(new DListSelectionListener(baseGUI, id));
		this.setFont(baseGUI.getFont());
		
	}//CONSTRUCTOR
	
}//CLASS
