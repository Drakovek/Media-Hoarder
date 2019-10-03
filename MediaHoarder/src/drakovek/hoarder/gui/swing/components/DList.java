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
 */
public class DList extends JList<String>
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -5532350862927679564L;
	
	/**
	 * Height of a single cell.
	 */
	private int cellHeight = 0;
	
	/**
	 * Initializes the DList class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param selectMultiple Whether to allow selecting multiple items in the list.
	 * @param id Event ID for the list.
	 */
	public DList(BaseGUI baseGUI, final boolean selectMultiple, final String id)
	{
		super();
		
		setSelectMultiple(selectMultiple);
		
		cellHeight = baseGUI.getSettings().getFontSize() + (baseGUI.getSettings().getSpaceSize() * 2);
		this.setFixedCellHeight(cellHeight);
		this.setLayoutOrientation(VERTICAL);
		this.setFont(baseGUI.getFont());
		this.addListSelectionListener(new DListSelectionListener(baseGUI, id));

        this.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0, true), "enter"); //$NON-NLS-1$
        this.getActionMap().put("enter", new DEnterListener(baseGUI, id)); //$NON-NLS-1$
		
	}//CONSTRUCTOR
	
	/**
	 * Sets whether to allow selecting multiple items in the list.
	 * 
	 * @param selectMultiple Whether to allow selecting multiple items in the list.
	 */
	public void setSelectMultiple(final boolean selectMultiple)
	{
		if(selectMultiple)
		{
			this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		}//IF
		else
		{
			this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		}//ELSE
		
	}//METHOD
	
	/**
	 * Sets the number of visible rows to fit within the size of the list. ONLY WORKS WHEN LIST IS WITHIN A SCROLL PANE.
	 */
	public void fitRowsToSize()
	{
		int rows = (int)Math.floor((double)getParent().getHeight()/(double)cellHeight);
		if(rows < 1)
		{
			rows = 1;
			
		}//IF
		
		this.setVisibleRowCount(rows);
		
	}//METHOD
	
}//CLASS
