package drakovek.hoarder.gui.swing.components;

import javax.swing.JRadioButtonMenuItem;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.listeners.DCheckBoxListener;

/**
 * Default RadioButtonMenuItem for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DRadioButtonMenuItem extends JRadioButtonMenuItem
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = 8547747358488635526L;

	/**
	 * Initializes the DRadioButtonMenuItem class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param selected Whether the radio button should start selected
	 * @param id Action/Language ID for the object
	 * @since 2.0
	 */
	public DRadioButtonMenuItem(BaseGUI baseGUI, final boolean selected, final String id)
	{
		super(baseGUI.getSettings().getLanguageText(id));
		int[] mnemonic = baseGUI.getSettings().getLanguageMnemonic(id);
		this.setMnemonic(mnemonic[0]);
		this.setDisplayedMnemonicIndex(mnemonic[1]);
		this.setFont(baseGUI.getFont());
		this.setSelected(selected);
		this.addItemListener(new DCheckBoxListener(baseGUI, id));
		
	}//CONSTRUCTOR
	
}//CLASS
