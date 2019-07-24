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
	 * Main panel for showing progress
	 * 
	 * @since 2.0
	 */
	private JPanel progressPanel;
	
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
		progressPanel = new JPanel();
		cancelled = false;
		processLabel = new DLabel(this, null, new String());
		detailLabel = new DLabel(this, null, new String());
		progressBar = new DProgressBar(this);
		
		this.setProcessLabel(DefaultLanguage.RUNNING);
		this.setDetailLabel(DefaultLanguage.RUNNING, true);
		this.setProgressBar(false, true, 2, 1);
		
		initializePanel();
		
	}//CONSTRUCTOR
	
	/**
	 * Creates the initial panel to use for showing progress.
	 * 
	 * @since 2.0
	 */
	protected void initializePanel()
	{
		
		//LABEL PANEL
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(2, 1, 0, getSettings().getSpaceSize()));
		labelPanel.add(processLabel);
		labelPanel.add(detailLabel);
				
		JPanel centerPanel = getVerticalStack(labelPanel, progressBar);
		
		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new BorderLayout());
		fullPanel.add(this.getSpacedPanel(centerPanel), BorderLayout.CENTER);
		fullPanel.add(getSpacedPanel(new DButton(this, DefaultLanguage.CANCEL), 0, 0, false, true, true, true), BorderLayout.SOUTH);
		this.setProgressPanel(fullPanel);
		
	}//METHOD
	
	/**
	 * Sets the main progress panel.
	 * 
	 * @param progressPanel Progress Panel
	 * @since 2.0
	 */
	public void setProgressPanel(JPanel progressPanel)
	{
		this.progressPanel = progressPanel;
		
	}//METHOD
	
	/**
	 * Returns the process label.
	 * 
	 * @return Process Label
	 * @since 2.0
	 */
	public DLabel getProcessLabel()
	{
		return processLabel;
		
	}//METHOD
	
	/**
	 * Returns the detail label.
	 * 
	 * @return Detail Label
	 * @since 2.0
	 */
	public DLabel getDetailLabel()
	{
		return detailLabel;
		
	}//METHOD
	
	/**
	 * Returns the progress bar.
	 * 
	 * @return Progress Bar
	 * @since 2.0
	 */
	public DProgressBar getProgressBar()
	{
		return progressBar;
		
	}//METHOD
	
	/**
	 * Opens a progress dialog.
	 * 
	 * @param ownerFrame DFrame used as the dialog's owner.
	 * @param titleID Language ID for the dialog title
	 * @since 2.0
	 */
	public void startProgressDialog(DFrame ownerFrame, final String titleID)
	{
		startProgressDialog(ownerFrame, titleID, getSettings().getFrameWidth() * getSettings().getFontSize(), 0);
		
	}//METHOD
	
	/**
	 * Opens a progress dialog.
	 * 
	 * @param ownerFrame DFrame used as the dialog's owner.
	 * @param titleID Language ID for the dialog title
	 * @param width Desired dialog width
	 * @param height Desired dialog height
	 * @since 2.0
	 */
	public void startProgressDialog(DFrame ownerFrame, final String titleID, final int width, final int height)
	{
		this.setProgressBar(false, true, 2, 1);
		cancelled = false;
		dialog = new DDialog(ownerFrame, progressPanel, getSettings().getLanguageText(titleID), false, width, height);
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
		startProgressDialog(ownerDialog, titleID, getSettings().getFrameWidth() * getSettings().getFontSize(), 0);
		
	}//METHOD
	
	/**
	 * Opens a progress dialog.
	 * 
	 * @param ownerDialog DDialog used as the dialog's owner.
	 * @param titleID Language ID for the dialog title
	 * @param width Desired dialog width
	 * @param height Desired dialog height
	 * @since 2.0
	 */
	public void startProgressDialog(DDialog ownerDialog, final String titleID, final int width, final int height)
	{
		this.setProgressBar(false, true, 2, 1);
		cancelled = false;
		dialog = new DDialog(ownerDialog, progressPanel, getSettings().getLanguageText(titleID), false, width, height);
		dialog.setVisible(true);
		
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
			setProgressBar(false, true, 1, 1);
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
	public void setProgressBar(final boolean indeterminate, final boolean painted, final int maximum, final int value)
	{
		progressBar.setProgressBar(indeterminate, painted, maximum, value);
		
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
		
	}//METHOD
	
	/**
	 * Sets the text of the detail label.
	 * 
	 * @param text Text for detail label
	 * @param isID Whether the text is a language ID or not
	 * @since 2.0
	 */
	public void setDetailLabel(final String text, final boolean isID)
	{
		if(isID)
		{
			detailLabel.setTextID(text, false);
			
		}//IF
		else
		{
			detailLabel.setText(text);
			
		}//ELSE
		
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
	
	/**
	 * Sets whether the current process is cancelled
	 * 
	 * @param cancelled Whether the current process is cancelled.
	 * @since 2.0
	 */
	public void setCancelled(final boolean cancelled)
	{
		this.cancelled = cancelled;
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.CANCEL:
				cancelled = true;
				setDetailLabel(DefaultLanguage.CANCELING, true);
				setProgressBar(true, false, 0, 0);
			
		}//SWITCH
		
	}//EVENT
	
}//CLASS

