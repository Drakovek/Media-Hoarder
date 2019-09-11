package drakovek.hoarder.gui.swing.components;

import javax.swing.JMenu;

import drakovek.hoarder.gui.BaseGUI;

/**
 * Default Menu for the program.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DMenu extends JMenu
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1203234815659039624L;

	/**
	 * Initializes the DMenu class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param id Language ID
	 */
	public DMenu(BaseGUI baseGUI, final String id)
	{
		super(baseGUI.getSettings().getLanguageText(id));
		int[] mnemonic = baseGUI.getSettings().getLanguageMnemonic(id);
		this.setMnemonic(mnemonic[0]);
		this.setDisplayedMnemonicIndex(mnemonic[1]);
		this.setFont(baseGUI.getFont());
		this.setMargin(baseGUI.getMenuInsets());
		
	}//CONSTRUCTOR
	
}//CLASS
