package drakovek.hoarder.gui.swing.compound;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.components.DTextArea;

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
	 * Text area for showing a progress log
	 * 
	 * @since 2.0
	 */
	private DTextArea logText;
	
	/**
	 * Scroll pane that holds the progress log
	 * 
	 * @since 2.0
	 */
	private DScrollPane logScroll;
	
	/**
	 * Initializes the DProgressInfoDialog class.
	 * 
	 * @param settings Program settings
	 * @since 2.0
	 */
	public DProgressInfoDialog(DSettings settings)
	{
		super(settings);
		
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
		
	}//METHOD
	
	@Override
	public void startProgressDialog(DFrame ownerFrame, final String titleID)
	{
		startProgressDialog(ownerFrame, titleID, getSettings().getFrameWidth() * 30, getSettings().getFontSize() * 30);
		
	}//METHOD
	
}//CLASS
