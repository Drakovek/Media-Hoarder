package drakovek.hoarder.gui.swing.components;

import javax.swing.JEditorPane;

import drakovek.hoarder.gui.BaseGUI;

/**
 * Default EditorPane for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DEditorPane extends JEditorPane
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = 1156133161692245991L;

	/**
	 * Initializes the DEditorPane class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @since 2.0
	 */
	public DEditorPane(BaseGUI baseGUI)
	{
		super();
		this.setEditable(false);
		this.setFont(baseGUI.getFont());
		this.setMargin(baseGUI.getButtonInsets());
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the text for the editor pane in HTML format.
	 * 
	 * @param text HTML text
	 * @since 2.0
	 */
	public void setTextHTML(final String text)
	{
		this.setContentType("text/html"); //$NON-NLS-1$
		StringBuilder builder = new StringBuilder();
		builder.append("<!DOCTYPE html><html>"); //$NON-NLS-1$
		builder.append(text);
		builder.append("</html>"); //$NON-NLS-1$
		this.setText(builder.toString());
		
	}//METHOD
	
}//CLASS
