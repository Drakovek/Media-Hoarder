package drakovek.hoarder.gui.swing.compound;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.components.DTextArea;
import drakovek.hoarder.processing.TimeMethods;

/**
 * Contains methods for creating a dialog that shows progress as well as showing a log of progress.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DProgressInfoDialog extends DProgressDialog
{
	/**
	 * String for spacing out text in the logText
	 * 
	 * @since 2.0
	 */
	public static final String SPACER = " - "; //$NON-NLS-1$
	
	/**
	 * Text area for showing a progress log
	 * 
	 * @since 2.0
	 */
	private DTextArea logText;
	
	/**
	 * Text area for showing the final progress log when process has ended
	 * 
	 * @since 2.0
	 */
	private DTextArea finalLogText;
	
	/**
	 * Scroll pane that holds the progress log
	 * 
	 * @since 2.0
	 */
	private DScrollPane logScroll;
	
	/**
	 * Scroll pane that holds the final progress log
	 * 
	 * @since 2.0
	 */
	private DScrollPane finalLogScroll;
	
	/**
	 * Panel for holding the final progress log
	 * 
	 * @since 2.0
	 */
	private JPanel finalPanel;
	
	/**
	 * Dialog for showing the final progress log
	 * 
	 * @since 2.0
	 */
	private DDialog finalDialog;
	
	/**
	 * File chooser used for saving log text
	 * 
	 * @since 2.0
	 */
	private DFileChooser fileChooser;
	
	/**
	 * Directory for the file chooser to start within
	 * 
	 * @since 2.0
	 */
	private File directory;
	
	/**
	 * Initializes the DProgressInfoDialog class.
	 * 
	 * @param settings Program settings
	 * @since 2.0
	 */
	public DProgressInfoDialog(DSettings settings)
	{
		super(settings);
		fileChooser = new DFileChooser(settings);
		
	}//CONSTRUCTOR
	
	@Override
	protected void initializePanel()
	{
		//CREATE TOP PANEL
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(2, 1, 0, getSettings().getSpaceSize()));
		labelPanel.add(getProcessLabel());
		labelPanel.add(getDetailLabel());
		JPanel topPanel = getVerticalStack(labelPanel, getProgressBar());
		
		//CREATE LOG SCROLL
		logText = new DTextArea(this);
		logText.setLineWrap(false);
		logScroll = new DScrollPane(getSettings(), ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, logText);
		
		//CREATE BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(new DButton(this, DefaultLanguage.CANCEL), BorderLayout.EAST);
		
		//CREATE FULL PANEL
		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new BorderLayout());
		fullPanel.add(getSpacedPanel(topPanel, 1, 0, true, false, true, true), BorderLayout.NORTH);
		fullPanel.add(getSpacedPanel(logScroll), BorderLayout.CENTER);
		fullPanel.add(getSpacedPanel(bottomPanel, 1, 0, false, true, true, true), BorderLayout.SOUTH);
		this.setProgressPanel(fullPanel);
		
		//CREATE FINAL BOTTOM PANEL
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, getSettings().getSpaceSize(), 0));
		buttonPanel.add(new DButton(this, DefaultLanguage.CLOSE));
		buttonPanel.add(new DButton(this, DefaultLanguage.SAVE));
		JPanel finalBottomPanel = new JPanel();
		finalBottomPanel.setLayout(new BorderLayout());
		finalBottomPanel.add(buttonPanel, BorderLayout.EAST);
		
		//CREATE FINAL LOG SCROLL
		finalLogText = new DTextArea(this);
		finalLogText.setLineWrap(false);
		finalLogScroll = new DScrollPane(getSettings(), ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, finalLogText);
		
		//CREATE FINAL PANEL
		finalPanel = new JPanel();
		finalPanel.setLayout(new BorderLayout());
		finalPanel.add(getSpacedPanel(finalBottomPanel, 1, 0, false, true, true, true), BorderLayout.SOUTH);
		finalPanel.add(getSpacedPanel(finalLogScroll), BorderLayout.CENTER);
		
	}//METHOD
	
	/**
	 * Resets the progress dialog to contain no text.
	 * 
	 * @since 2.0
	 */
	public void resetLog()
	{
		logText.setText(new String());
		
	}//METHOD
	
	/**
	 * Sets a "title" for the progress log by resetting to only show the given text.
	 * 
	 * @param text Text for the progress log to show
	 * @since 2.0
	 */
	public void setTitle(final String text)
	{
		logText.setText(text);
		logScroll.resetTopLeft();
		
	}//METHOD
	
	/**
	 * Adds a given string of text to the end of the progress log.
	 * 
	 * @param text Text to append
	 * @since 2.0
	 */
	public void appendLog(final String text)
	{
		if(text == null || text.length() == 0)
		{
			if(!logText.getText().endsWith("\n\r\n\r")); //$NON-NLS-1$
			{
				logText.append("\n\r"); //$NON-NLS-1$
				
			}//IF
			
		}//IF
		else
		{
			logText.append(TimeMethods.getCurrentTimeString(getSettings()) + SPACER + text + "\n\r"); //$NON-NLS-1$
			
		}//else
		
		logScroll.resetBottomLeft();
		
	}//METHOD
	
	/**
	 * Closes the dialog for showing progress and opens the dialog for showing the finished progress dialog.
	 * 
	 * @param ownerFrame Frame used as the final progress dialog's owner
	 * @param titleID Title of the final progress dialog
	 * @param startDirectory Directory to start from when saving log.
	 * @since 2.0
	 */
	public void showFinalLog(DFrame ownerFrame, final String titleID, final File startDirectory)
	{
		
		closeProgressDialog();
		finalLogText.setText(logText.getText());
		finalLogScroll.resetTopLeft();
		directory = startDirectory;
		finalDialog = new DDialog(ownerFrame, finalPanel, getSettings().getLanguageText(titleID), true, getSettings().getFrameWidth() * 30, getSettings().getFontSize() * 30);
		finalDialog.setVisible(true);
		
	}//METHOD
	
	/**
	 * Method to save log text to a text file.
	 * 
	 * @since 2.0
	 */
	private void saveLog()
	{
		String[] extension = {".txt"};  //$NON-NLS-1$
		File file = fileChooser.openSaveDialog(finalDialog, directory, extension);
		
		if(file != null)
		{
			DWriter.writeToFile(file, finalLogText.getText());
			
		}//IF
		
	}//METHOD
	
	@Override
	public void startProgressDialog(DFrame ownerFrame, final String titleID)
	{
		startProgressDialog(ownerFrame, titleID, getSettings().getFrameWidth() * 30, getSettings().getFontSize() * 30);
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.CANCEL:
				setCancelled(true);
				setDetailLabel(DefaultLanguage.CANCELING, true);
				setProgressBar(true, false, 0, 0);
				appendLog(DefaultLanguage.CANCELING);
				break;
			case DefaultLanguage.CLOSE:
				finalDialog.dispose();
				finalDialog = null;
				break;
			case DefaultLanguage.SAVE:
				saveLog();
				break;
			
		}//SWITCH
		
	}//EVENT
	
}//CLASS
