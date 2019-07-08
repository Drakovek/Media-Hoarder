package drakovek.hoarder.gui.swing.components;

import javax.swing.JMenuItem;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.listeners.DActionListener;

/**
 * Default MenuItem for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DMenuItem extends JMenuItem
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = 4120141873171027530L;

	/**
	 * BaseGUI linked to the DMenuItem
	 * 
	 * @since 2.0
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
		super(baseGUI.getSettings().getLanuageText(id));
		this.baseGUI = baseGUI;
		this.setMnemonic(baseGUI.getSettings().getLanguageMnemonic(id));
		this.setFont(baseGUI.getFont());
		this.setMargin(baseGUI.getButtonInsets());
		this.addActionListener(new DActionListener(baseGUI, id));
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the text for the menuItem based on a Language ID.
	 * 
	 * @param id Language ID
	 * @since 2.0
	 */
	public void setTextID(final String id)
	{
		this.setText(baseGUI.getSettings().getLanuageText(id));
		this.setMnemonic(baseGUI.getSettings().getLanguageMnemonic(id));
		
	}//METHOD
	
}//CLASS
