package drakovek.hoarder.gui.swing.components;

import javax.swing.JPasswordField;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.listeners.DActionListener;

/**
 * Default password field for the project.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DPasswordField extends JPasswordField
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -5240158498525787038L;

	/**
	 * Initializes the DPasswordField class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param id Action ID for the Password Field
	 */
	public DPasswordField(BaseGUI baseGUI, final String id)
	{
		super();
		this.setFont(baseGUI.getFont());
		this.setMargin(baseGUI.getMenuInsets());
		this.addActionListener(new DActionListener(baseGUI, id));
		
	}//CONSTRUCTOR
	
}//CLASS
