package drakovek.hoarder.gui.swing.components;

import java.awt.Component;

import javax.swing.JLabel;

import drakovek.hoarder.gui.BaseGUI;

/**
 * Default label Object for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DLabel extends JLabel
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = -6159974118944918348L;
	
	/**
	 * BaseGUI linked to the DLabel to get Swing information.
	 * 
	 * @since 2.0
	 */
	private BaseGUI baseGUI;
	
	/**
	 * Initializes the DLabel class with text, font, mnemonic, and connected component.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param component Component Linked to the label for mnemonics (Mnemonic not used if null)
	 * @param id Language ID
	 */
	public DLabel(BaseGUI baseGUI, Component component, final String id)
	{
		super(baseGUI.getSettings().getLanuageText(id));
		this.setFont(baseGUI.getFont());
		
		if(component != null)
		{
			this.setDisplayedMnemonic(baseGUI.getSettings().getLanguageMnemonic(id));
			this.setLabelFor(component);
			
		}//IF
		
		this.baseGUI = baseGUI;
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the text for the label based on a Language ID.
	 * 
	 * @param id Language ID
	 * @param useMnemonic Whether to use the mnemonic for the label.
	 * @since 2.0
	 */
	public void setTextID(final String id, final boolean useMnemonic)
	{
		this.setText(baseGUI.getSettings().getLanuageText(id));
		
		if(useMnemonic)
		{
			this.setDisplayedMnemonic(baseGUI.getSettings().getLanguageMnemonic(id));
	
		}//IF
		
	}//METHOD
	
}//CLASS
