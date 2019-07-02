package drakovek.hoarder.gui.swing.components;

import javax.swing.JList;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.listeners.DEnterListener;
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
		this.setFont(baseGUI.getFont());
		this.addListSelectionListener(new DListSelectionListener(baseGUI, id));

        this.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0, true), "enter"); //$NON-NLS-1$
        this.getActionMap().put("enter", new DEnterListener(baseGUI, id)); //$NON-NLS-1$
		
	}//CONSTRUCTOR
	
}//CLASS
