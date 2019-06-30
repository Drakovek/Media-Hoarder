package drakovek.hoarder.gui.swing.components;

import java.awt.Font;

import javax.swing.JTextArea;

import drakovek.hoarder.gui.BaseGUI;

/**
 * Default Text Area for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DTextArea extends JTextArea
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 4376430617768687635L;

	/**
	 * Initializes the DTextArea class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @since 2.0
	 */
	public DTextArea(BaseGUI baseGUI)
	{
		super();
		this.setFont(baseGUI.getFont());
		this.setEditable(false);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the font for the DTextArea
	 * 
	 * @param fontName Name of the font family.
	 * @param bold Whether the font is bold
	 * @param size Size of the font
	 * @since 2.0
	 */
	public void setFont(final String fontName, final boolean bold, final int size)
	{
		//SET THE FONT
		int fontType = Font.PLAIN;
		if(bold)
		{
			fontType = Font.BOLD;
			
		}//IF
		
		this.setFont(new Font(fontName, fontType, size));

	}//METHOD
	
}//CLASS
