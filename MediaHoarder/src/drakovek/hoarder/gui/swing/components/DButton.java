package drakovek.hoarder.gui.swing.components;

import javax.swing.JButton;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.listeners.DActionListener;

/**
 * Default button Object for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DButton extends JButton
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = 3839715899344861685L;
	
	/**
	 * BaseGUI linked to the DButton to get Swing information and to call events.
	 * 
	 * @since 2.0
	 */
	private BaseGUI baseGUI;
	
	/**
	 * Initializes the DButton class with text, mnemonic, font, margin, and ActionListener.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param id Event/Language ID
	 * @since 2.0
	 */
	public DButton(BaseGUI baseGUI, final String id)
	{
		super(baseGUI.getSettings().getLanguageText(id));
		
		int[] mnemonic = baseGUI.getSettings().getLanguageMnemonic(id);
		this.setMnemonic(mnemonic[0]);
		this.setDisplayedMnemonicIndex(mnemonic[1]);
		this.setFont(baseGUI.getFont());
		this.setMargin(baseGUI.getButtonInsets());
		this.addActionListener(new DActionListener(baseGUI, id));
		
		this.baseGUI = baseGUI;
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the button's font to large.
	 * 
	 * @since 2.0
	 */
	public void setFontLarge()
	{
		this.setFont(baseGUI.getLargeFont());
		
	}//METHOD
	
	/**
	 * Sets the text for the button based on a Language ID.
	 * 
	 * @param id Language ID
	 * @since 2.0
	 */
	public void setTextID(final String id)
	{
		this.setText(baseGUI.getSettings().getLanguageText(id));
		
		int[] mnemonic = baseGUI.getSettings().getLanguageMnemonic(id);
		this.setMnemonic(mnemonic[0]);
		this.setDisplayedMnemonicIndex(mnemonic[1]);
		
	}//METHOD
	
}//CLASS
