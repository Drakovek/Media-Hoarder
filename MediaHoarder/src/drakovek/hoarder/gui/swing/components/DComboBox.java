package drakovek.hoarder.gui.swing.components;

import javax.swing.JComboBox;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.listeners.DActionListener;

/**
 * Default combo box for the program.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DComboBox extends JComboBox<String>
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 2373613947616129301L;

	/**
	 * Initializes the DComboBox class
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param id Action ID for the DComboBox
	 */
	public DComboBox(BaseGUI baseGUI, final String id)
	{
		super();
		this.setFont(baseGUI.getFont());
		this.addActionListener(new DActionListener(baseGUI, id));
		
	}//METHOD
	
	/**
	 * Sets the data contained in the combo box.
	 * 
	 * @param data String array to show in the combo box
	 */
	public void setData(final String[] data)
	{
		this.removeAllItems();
		
		if(data.length > 0)
		{
			for(String line: data)
			{
				this.addItem(line);
			
			}//FOR
		
			this.revalidate();
			this.setSelectedIndex(0);
			
		}//IF
		
	}//METHOD
	
}//CLASS
