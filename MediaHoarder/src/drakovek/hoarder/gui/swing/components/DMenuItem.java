package drakovek.hoarder.gui.swing.components;

import javax.swing.JMenuItem;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.listeners.DActionListener;

/**
 * Default MenuItem for the program.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DMenuItem extends JMenuItem
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 4120141873171027530L;

	/**
	 * BaseGUI linked to the DMenuItem
	 */
	private BaseGUI baseGUI;
	
	/**
	 * Initializes the DMenuItem class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param id Language/Action ID
	 */
	public DMenuItem(BaseGUI baseGUI, final String id)
	{
		super(baseGUI.getSettings().getLanguageText(id));
		this.baseGUI = baseGUI;
		int[] mnemonic = baseGUI.getSettings().getLanguageMnemonic(id);
		this.setMnemonic(mnemonic[0]);
		this.setDisplayedMnemonicIndex(mnemonic[1]);
		this.setFont(baseGUI.getFont());
		this.setMargin(baseGUI.getButtonInsets());
		this.addActionListener(new DActionListener(baseGUI, id));
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the text for the menuItem based on a Language ID.
	 * 
	 * @param id Language ID
	 */
	public void setTextID(final String id)
	{
		this.setText(baseGUI.getSettings().getLanguageText(id));
		int[] mnemonic = baseGUI.getSettings().getLanguageMnemonic(id);
		this.setMnemonic(mnemonic[0]);
		this.setDisplayedMnemonicIndex(mnemonic[1]);
		
	}//METHOD
	
}//CLASS
