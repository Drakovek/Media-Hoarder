package drakovek.hoarder.gui.swing.compound;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.EditingValues;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DScrollPane;

/**
 * GUI for selecting items from a list.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DListSelection extends BaseGUI
{
	/**
	 * Main list selection dialog
	 */
	private DDialog dialog;
	
	/**
	 * Main list selection panel
	 */
	private JPanel panel;
	
	/**
	 * Panel for holding action buttons
	 */
	private JPanel buttonPanel;
	
	/**
	 * Button panel for dialogs where only a single item can be selected
	 */
	private JPanel singleSelectionPanel;
	
	/**
	 * Button panel for dialogs where multiple items can be selected
	 */
	private JPanel multiSelectionPanel;
	
	/**
	 * List to show selectable contents
	 */
	private DList list;
	
	/**
	 * Array of selected indexes to return
	 */
	private int[] selected;
	
	/**
	 * Initializes the DListSelection class and GUI
	 * 
	 * @param settings Program Settings
	 */
	public DListSelection(DSettings settings)
	{
		super(settings);
		
		//CREATE LIST
		list = new DList(this, true, CommonValues.YES);
		DScrollPane listScroll = new DScrollPane(getSettings(), list);
		
		//CREATE BOTTOM PANEL
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 1));
		
		multiSelectionPanel = new JPanel();
		multiSelectionPanel.setLayout(new GridLayout(1, 3, getSettings().getSpaceSize(), 0));
		multiSelectionPanel.add(new DButton(this, CommonValues.CANCEL));
		multiSelectionPanel.add(new DButton(this, EditingValues.ADD_SELECTED));
		multiSelectionPanel.add(new DButton(this, EditingValues.ADD_ALL));

		singleSelectionPanel = new JPanel();
		singleSelectionPanel.setLayout(new GridLayout(1, 2, getSettings().getSpaceSize(), 0));
		singleSelectionPanel.add(new DButton(this, CommonValues.CANCEL));
		singleSelectionPanel.add(new DButton(this, CommonValues.ADD));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(buttonPanel, BorderLayout.EAST);
		
		panel = getVerticalStack(getSpacedPanel(listScroll), 1, this.getSpacedPanel(bottomPanel, 1, 0, false, true, true, true), 0);
		
	}//CONSTURCTOR
	
	/**
	 * Opens the list selection dialog with ability to select multiple indexes.
	 * 
	 * @param owner DFrame owner of the list selection dialog
	 * @param titleID ID of the selection dialog
	 * @param array String array to display
	 * @return Indexes of user selected Strings
	 */
	public int[] openMultipleSeletionDialog(DFrame owner, final String titleID, final String[] array)
	{
		owner.setAllowExit(false);
		selected = new int[array.length];
		for(int i = 0; i < selected.length; i++)
		{
			selected[i] = i;
			
		}//FOR
		
		buttonPanel.removeAll();
		buttonPanel.add(multiSelectionPanel);
		buttonPanel.revalidate();
		list.setSelectMultiple(true);
		list.setListData(array);
    	dialog = new DDialog(owner, panel, getSettings().getLanguageText(titleID), true, 0, 0);
    	dialog.setVisible(true);
    	dialog = null;
    	owner.setAllowExit(true);
    	
    	return selected;
    	
	}//METHOD
	
	/**
	 * Opens the list selection dialog with only one index being selectable.
	 * 
	 * @param owner DFrame owner of the list selection dialog
	 * @param titleID ID of the selection dialog
	 * @param array String array to display
	 * @return Indexes of user selected Strings
	 */
	public int[] openSingleSeletionDialog(DFrame owner, final String titleID, final String[] array)
	{
		owner.setAllowExit(false);
		selected = new int[0];
		
		buttonPanel.removeAll();
		buttonPanel.add(singleSelectionPanel);
		buttonPanel.revalidate();
		list.setSelectMultiple(false);
		list.setListData(array);
    	dialog = new DDialog(owner, panel, getSettings().getLanguageText(titleID), true, 0, 0);
    	dialog.setVisible(true);
    	dialog = null;
    	owner.setAllowExit(true);
    	
    	return selected;
    	
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case CommonValues.CANCEL:
				selected = new int[0];
				dialog.dispose();
				break;
			case EditingValues.ADD_ALL:
				dialog.dispose();
				break;
			case CommonValues.ADD:
			case EditingValues.ADD_SELECTED:
				selected = list.getSelectedIndices();
				dialog.dispose();
				break;
				
		}//SWITCH
		
	}//METHOD
	
}//CLASS
