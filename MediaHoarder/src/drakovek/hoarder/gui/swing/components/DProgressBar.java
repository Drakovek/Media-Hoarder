package drakovek.hoarder.gui.swing.components;

import javax.swing.JProgressBar;

import drakovek.hoarder.gui.BaseGUI;

/**
 * Default progress bar for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DProgressBar extends JProgressBar
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = -284022798601497770L;

	/**
	 * Initializes the DProgressBar
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @since 2.0
	 */
	public DProgressBar(BaseGUI baseGUI)
	{
		super();
		this.setFont(baseGUI.getFont());
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the state of the progress bar.
	 * 
	 * @param indeterminate Whether the process is of indeterminate length
	 * @param painted Whether to show a string value on the progress bar.
	 * @param maximum Maximum value for the progress bar (N/A if indeterminate)
	 * @param value Current value of the progress bar (N/A if indeterminate)
	 * @param progressString String to show on the progress bar (N/A if not painted)
	 * @since 2.0
	 */
	public void setProgressBar(final boolean indeterminate, final boolean painted, final int maximum, final int value, final String progressString)
	{
		setIndeterminate(indeterminate);
		setMinimum(0);
		
		if(value > 0 && value <= maximum)
		{
			setMaximum(maximum);
			setValue(value);
		
		}//IF
		else
		{
			setMaximum(0);
			setMinimum(0);
		
		}//ELSE
		
		if(painted && progressString != null && progressString.length() > 0)
		{
			setStringPainted(true);
			setString(progressString);
		
		}//IF
		else
		{
			setStringPainted(false);
		
		}//ELSE
		
	}//METHOD
	
}//CLASS
