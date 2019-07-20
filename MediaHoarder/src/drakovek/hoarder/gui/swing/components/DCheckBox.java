package drakovek.hoarder.gui.swing.components;

import javax.swing.JCheckBox;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.listeners.DCheckBoxListener;

/**
 * Default CheckBox for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DCheckBox extends JCheckBox
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -2574455893303996405L;

	/**
	 * Initializes the DCheckBox class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param selected Whether the CheckBox starts as selected
	 * @param id Action ID for the CheckBox
	 * @since 2.0
	 */
	public DCheckBox(BaseGUI baseGUI, final boolean selected, final String id)
	{
		super(baseGUI.getSettings().getLanuageText(id));
		int[] mnemonic = baseGUI.getSettings().getLanguageMnemonic(id);
		this.setMnemonic(mnemonic[0]);
		this.setDisplayedMnemonicIndex(mnemonic[1]);
		this.setFont(baseGUI.getFont());
		this.setSelected(selected);
		this.addItemListener(new DCheckBoxListener(baseGUI, id));
		
	}//CONSTRUCTOR
	
}//CLASS
