package drakovek.hoarder.gui.swing.compound;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.file.language.CommonValues;
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
 */
public class DProgressInfoDialog extends DProgressDialog
{
	/**
	 * String for spacing out text in the logText
	 */
	public static final String SPACER = " - "; //$NON-NLS-1$
	
	/**
	 * Text area for showing a progress log
	 */
	private DTextArea logText;
	
	/**
	 * Text area for showing the final progress log when process has ended
	 */
	private DTextArea finalLogText;
	
	/**
	 * Scroll pane that holds the progress log
	 */
	private DScrollPane logScroll;
	
	/**
	 * Scroll pane that holds the final progress log
	 */
	private DScrollPane finalLogScroll;
	
	/**
	 * Panel for holding the final progress log
	 */
	private JPanel finalPanel;
	
	/**
	 * Dialog for showing the final progress log
	 */
	private DDialog finalDialog;
	
	/**
	 * File chooser used for saving log text
	 */
	private DFileChooser fileChooser;
	
	/**
	 * Directory for the file chooser to start within
	 */
	private File directory;
	
	/**
	 * Initializes the DProgressInfoDialog class.
	 * 
	 * @param settings Program settings
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
		bottomPanel.add(new DButton(this, CommonValues.CANCEL), BorderLayout.EAST);
		
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
		buttonPanel.add(new DButton(this, CommonValues.CLOSE));
		buttonPanel.add(new DButton(this, CommonValues.SAVE));
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
	 */
	public void resetLog()
	{
		logText.setText(new String());
		
	}//METHOD
	
	/**
	 * Adds a given string of text to the end of the progress log.
	 * 
	 * @param text Text to append
	 * @param addTimestamp Whether to add a time stamp to the appended text
	 */
	public void appendLog(final String text, final boolean addTimestamp)
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
			if(addTimestamp)
			{
				logText.append(TimeMethods.getCurrentTimeString(getSettings(), TimeMethods.DATE_SHORT) + SPACER + text + "\n\r"); //$NON-NLS-1$
			
			}//IF
			else
			{
				logText.append(text + "\n\r"); //$NON-NLS-1$
			
			}//ELSE
			
		}//else
		
		logScroll.resetBottomLeft();
		
	}//METHOD
	
	/**
	 * Closes the dialog for showing progress and opens the dialog for showing the finished progress dialog.
	 * 
	 * @param ownerFrame Frame used as the final progress dialog's owner
	 * @param titleID Title of the final progress dialog
	 * @param startDirectory Directory to start from when saving log.
	 */
	public void showFinalLog(DFrame ownerFrame, final String titleID, final File startDirectory)
	{
		closeProgressDialog();
		finalLogText.setText(logText.getText());
		finalLogScroll.resetTopLeft();
		directory = startDirectory;
		finalDialog = new DDialog(ownerFrame, finalPanel, getSettings().getLanguageText(titleID), true, getSettings().getFrameWidth() * 30, getSettings().getFontSize() * 30);
		finalDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		finalDialog.setVisible(true);
		finalDialog = null;
		
	}//METHOD
	
	/**
	 * Method to save log text to a text file.
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
		logText.setText(new String());
		startProgressDialog(ownerFrame, titleID, getSettings().getFrameWidth() * 30, getSettings().getFontSize() * 30);
		
	}//METHOD
	
	@Override
	public void startProgressDialog(DDialog ownerDialog, final String titleID)
	{
		logText.setText(new String());
		startProgressDialog(ownerDialog, titleID, getSettings().getFrameWidth() * 30, getSettings().getFontSize() * 30);
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case CommonValues.CANCEL:
				setCancelled(true);
				setDetailLabel(CommonValues.CANCELING, true);
				setProgressBar(true, false, 0, 0);
				appendLog(CommonValues.CANCELING, true);
				break;
			case CommonValues.CLOSE:
				finalDialog.dispose();
				break;
			case CommonValues.SAVE:
				saveLog();
				break;
			
		}//SWITCH
		
	}//EVENT
	
}//CLASS
