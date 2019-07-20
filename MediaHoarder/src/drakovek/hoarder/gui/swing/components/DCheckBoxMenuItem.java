package drakovek.hoarder.gui.swing.components;

import javax.swing.JCheckBoxMenuItem;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.listeners.DCheckBoxListener;

/**
 * Default CheckBoxMenuItem for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DCheckBoxMenuItem extends JCheckBoxMenuItem
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
	public DCheckBoxMenuItem(BaseGUI baseGUI, final boolean selected, final String id)
	{
		super(baseGUI.getSettings().getLanuageText(id));
		this.setMnemonic(baseGUI.getSettings().getLanguageMnemonic(id));
		this.setFont(baseGUI.getFont());
		this.setSelected(selected);
		this.addItemListener(new DCheckBoxListener(baseGUI, id));
		
	}//CONSTRUCTOR
	
}//CLASS