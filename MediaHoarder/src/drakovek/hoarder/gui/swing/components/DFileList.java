package drakovek.hoarder.gui.swing.components;

import java.io.File;

import javax.swing.JList;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.listeners.DEnterListener;
import drakovek.hoarder.gui.swing.listeners.DListSelectionListener;

/**
 * List object for listing files.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DFileList extends JList<File>
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
	 * @param multipleSelect Whether to allow selecting multiple items in the list.
	 * @param id Event ID for the list.
	 */
	public DFileList(BaseGUI baseGUI, final boolean multipleSelect, final String id)
	{
		super();
		this.setCellRenderer(new DFileCellRenderer(baseGUI));

		if(multipleSelect)
		{
			this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		}//IF
		else
		{
			this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		}//ELSE
		
		cellHeight = baseGUI.getSettings().getFontSize() + (baseGUI.getSettings().getSpaceSize() * 2);
		this.setFixedCellHeight(cellHeight);
		this.setLayoutOrientation(VERTICAL);
		this.setFont(baseGUI.getFont());
		this.addListSelectionListener(new DListSelectionListener(baseGUI, id));

        this.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0, true), "enter"); //$NON-NLS-1$
        this.getActionMap().put("enter", new DEnterListener(baseGUI, id)); //$NON-NLS-1$

	}//CONSTRUCTOR
	
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
