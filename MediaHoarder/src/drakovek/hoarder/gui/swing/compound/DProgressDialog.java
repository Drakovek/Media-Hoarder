package drakovek.hoarder.gui.swing.compound;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DProgressBar;

/**
 * Contains methods for creating a dialog to show progress.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DProgressDialog extends BaseGUI
{
	/**
	 * Whether the current process is cancelled
	 * 
	 * @since 2.0
	 */
	private boolean cancelled;
	
	/**
	 * Label for a running process
	 * 
	 * @since 2.0
	 */
	private DLabel processLabel;
	
	/**
	 * Label for the details of a running process
	 * 
	 * @since 2.0
	 */
	private DLabel detailLabel;
	
	/**
	 * Button to cancel current progress.
	 * 
	 * @since 2.0
	 */
	private DButton cancelButton;
	
	/**
	 * Main progress bar for showing progress.
	 * 
	 * @since 2.0
	 */
	private DProgressBar progressBar;
	
	/**
	 * Main dialog for showing progress.
	 * 
	 * @since 2.0
	 */
	private DDialog dialog;
	
	/**
	 * Initializes the DProgressDialog class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public DProgressDialog(DSettings settings)
	{
		super(settings);
		cancelled = false;
		processLabel = new DLabel(this, null, new String());
		detailLabel = new DLabel(this, null, new String());
		progressBar = new DProgressBar(this);
		cancelButton = new DButton(this, DefaultLanguage.CANCEL);
		
	}//CONSTRUCTOR
	
	/**
	 * Opens a progress dialog.
	 * 
	 * @param ownerFrame DFrame used as the dialog's owner.
	 * @param titleID Language ID for the dialog title
	 * @since 2.0
	 */
	public void startProgressDialog(DFrame ownerFrame, final String titleID)
	{
		cancelled = false;
		dialog = new DDialog(ownerFrame, getProgressPanel(), getSettings().getLanuageText(titleID), false, getSettings().getFrameWidth() * getSettings().getFontSize(), 0);
		dialog.setResizable(false);
		dialog.setVisible(true);
		
	}//METHOD
	
	/**
	 * Opens a progress dialog.
	 * 
	 * @param ownerDialog DDialog used as the dialog's owner.
	 * @param titleID Language ID for the dialog title
	 * @since 2.0
	 */
	public void startProgressDialog(DDialog ownerDialog, final String titleID)
	{
		cancelled = false;
		dialog = new DDialog(ownerDialog, getProgressPanel(), getSettings().getLanuageText(titleID), false, getSettings().getFrameWidth() * getSettings().getFontSize(), 0);
		dialog.setResizable(false);
		dialog.setVisible(true);
		
	}//METHOD
	
	/**
	 * Returns panel to use for the progress dialog.
	 * 
	 * @return Progress Panel
	 * @since 2.0
	 */
	public JPanel getProgressPanel()
	{
		//LABEL PANEL
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(3, 1, 0, getSettings().getSpaceSize()));
		centerPanel.add(processLabel);
		centerPanel.add(detailLabel);
		centerPanel.add(progressBar);
		
		JPanel progressPanel = new JPanel();
		progressPanel.setLayout(new BorderLayout());
		progressPanel.add(this.getSpacedPanel(centerPanel, 1, 0, true, false, true, true), BorderLayout.CENTER);
		progressPanel.add(getSpacedPanel(cancelButton, 0, 0, true, true, true, true), BorderLayout.SOUTH);
		
		return progressPanel;
		
	}//METHOD
	
	/**
	 * Closes the current progress dialog.
	 * 
	 * @since 2.0
	 */
	public void closeProgressDialog()
	{
		if(dialog != null)
		{
			dialog.dispose();
			dialog = null;
		
		}//IF
		
	}//METHOD

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
		progressBar.setProgressBar(indeterminate, painted, maximum, value, progressString);
		
	}//METHOD
	
	/**
	 * Sets the text of the process label.
	 * 
	 * @param id Language ID for the process label
	 * @since 2.0
	 */
	public void setProcessLabel(final String id)
	{
		processLabel.setTextID(id, false);
		detailLabel.setTextID(DefaultLanguage.RUNNING, false);
		
	}//METHOD
	
	/**
	 * Sets the text of the detail label.
	 * 
	 * @param text Text for detail label
	 * @since 2.0
	 */
	public void setDetailLabel(final String text)
	{
		detailLabel.setText(text);
		
	}//METHOD
	
	/**
	 * Returns whether the current process has been cancelled.
	 * 
	 * @return Whether the current process has been cancelled
	 * @since 2.0
	 */
	public boolean isCancelled()
	{
		return cancelled;
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.CANCEL:
			{
				cancelled = true;
				setDetailLabel(getSettings().getLanuageText(DefaultLanguage.CANCELING));
				setProgressBar(true, false, 0, 0, null);
				
			}//CASE
			
		}//SWITCH
		
	}//EVENT
	
}//CLASS

