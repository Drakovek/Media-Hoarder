package drakovek.hoarder.gui.swing.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.work.DRunnable;
import drakovek.hoarder.work.DWorker;

/**
 * Default progress bar for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DProgressBar extends JPanel implements DWorker
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = -284022798601497770L;
	
	/**
	 * Main progress bar
	 * 
	 * @since 2.0
	 */
	private JProgressBar progressBar;
	
	/**
	 * Whether the current process is of indeterminate length
	 * 
	 * @since 2.0
	 */
	private boolean indeterminate;
	
	/**
	 * Whether to show a percentage value on the progress bar
	 * 
	 * @since 2.0
	 */
	private boolean painted;
	
	/**
	 * Maximum value for the progress bar
	 * 
	 * @since 2.0
	 */
	private int maximum;
	
	/**
	 * Current value of the progress bar
	 * 
	 * @since 2.0
	 */
	private int value;

	/**
	 * Initializes the DProgressBar
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @since 2.0
	 */
	public DProgressBar(BaseGUI baseGUI)
	{
		super();
		progressBar = new JProgressBar();
		progressBar.setFont(baseGUI.getFont());

		Dimension space = new Dimension(1, baseGUI.getSettings().getFontSize() + (baseGUI.getSettings().getSpaceSize() * 2));
		this.setLayout(new GridBagLayout());
		GridBagConstraints progressCST = new GridBagConstraints();
		progressCST.gridx = 0;			progressCST.gridy = 0;
		progressCST.gridwidth = 1;		progressCST.gridheight = 3;
		progressCST.weightx = 0;		progressCST.weighty = 0;
		progressCST.fill = GridBagConstraints.BOTH;
		this.add(Box.createRigidArea(space), progressCST);
		progressCST.gridx = 2;
		this.add(Box.createRigidArea(space), progressCST);
		progressCST.gridx = 1;			progressCST.weightx = 1;
		this.add(progressBar, progressCST);
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the state of the progress bar.
	 * 
	 * @param indeterminate Whether the process is of indeterminate length
	 * @param painted Whether to show a percentage value on the progress bar.
	 * @param maximum Maximum value for the progress bar (N/A if indeterminate)
	 * @param value Current value of the progress bar (N/A if indeterminate)
	 * @since 2.0
	 */
	public void setProgressBar(final boolean indeterminate, final boolean painted, final int maximum, final int value)
	{
		this.indeterminate = indeterminate;
		this.painted = painted;
		this.maximum = maximum;
		this.value = value;
		
		SwingUtilities.invokeLater(new DRunnable(this, new String()));
		
	}//METHOD
	
	/**
	 * Sets the state of the progress bar.
	 * 
	 * @since 2.0
	 */
	private void setProgress()
	{
		progressBar.setIndeterminate(indeterminate);
		progressBar.setMinimum(0);
		
		if(value > 0 && value <= maximum)
		{
			progressBar.setMaximum(maximum);
			progressBar.setValue(value);
		
		}//IF
		else
		{
			progressBar.setMaximum(0);
			progressBar.setMinimum(0);
		
		}//ELSE
		
		if(painted)
		{
			progressBar.setStringPainted(true);
			progressBar.setString(Integer.toString((int)(((double)value / (double)maximum) * (double)100)) + Character.toString('%'));
		
		}//IF
		else
		{
			progressBar.setStringPainted(false);
		
		}//ELSE
		
	}//METHOD

	@Override
	public void run(String id)
	{
		setProgress();
		
	}//METHOD

	@Override
	public void done(String id){}
	
}//CLASS
