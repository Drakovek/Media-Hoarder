package drakovek.hoarder.gui.swing.components;

import javax.swing.JTextField;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.listeners.DActionListener;

/**
 * Default TextField for the program.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DTextField extends JTextField
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 393992044448035563L;

	/**
	 * Initializes the DTextField class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param id Action ID for the Text Field
	 */
	public DTextField(BaseGUI baseGUI, final String id)
	{
		super();
		this.setFont(baseGUI.getFont());
		this.setMargin(baseGUI.getMenuInsets());
		this.addActionListener(new DActionListener(baseGUI, id));
		
	}//CONSTRUCTOR
	
}//CLASS
