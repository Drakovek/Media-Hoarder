package drakovek.hoarder.file;

import drakovek.hoarder.gui.modes.ModeContainerGUI;

/**
 * Main class for starting the Media Hoarder Program
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class Start 
{
	
	/**
	 * Starts the Media Hoarder Program
	 * 
	 * @param args Not Used
	 * @since 2.0
	 */
	public static void main(String[] args)
	{
		DSettings settings = new DSettings();
		new ModeContainerGUI(settings);

	}//METHOD
	
}//CLASS
