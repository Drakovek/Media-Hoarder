package drakovek.hoarder.gui.swing.components;

import javax.swing.JMenu;

import drakovek.hoarder.gui.BaseGUI;

/**
 * Default Menu for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DMenu extends JMenu
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = 1203234815659039624L;

	/**
	 * Initializes the DMenu class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param id Language ID
	 * @since 2.0
	 */
	public DMenu(BaseGUI baseGUI, final String id)
	{
		super(baseGUI.getSettings().getLanuageText(id));
		this.setMnemonic(baseGUI.getSettings().getLanguageMnemonic(id));
		this.setFont(baseGUI.getFont());
		this.setMargin(baseGUI.getMenuInsets());
		
	}//CONSTRUCTOR
	
}//CLASS
