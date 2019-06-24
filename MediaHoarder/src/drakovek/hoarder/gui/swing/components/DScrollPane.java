package drakovek.hoarder.gui.swing.components;

import java.awt.Component;

import javax.swing.JScrollPane;

import drakovek.hoarder.file.DSettings;

/**
 * Default scroll pane object for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 1.0
 */
public class DScrollPane extends JScrollPane
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = -3855985632736412421L;

	/**
	 * Initializes the scroll pane
	 * 
	 * @param settings ProjectSettings
	 * @param view Component to show in the viewport.
	 * @since 2.0
	 */
	public DScrollPane(DSettings settings, Component view)
	{
		super(view);

		
	}//METHOD DScrollPane(Component view, UserPreferences userPreferences)
	
}//CLASS
