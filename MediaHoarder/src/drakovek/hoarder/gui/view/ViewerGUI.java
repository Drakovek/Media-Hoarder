package drakovek.hoarder.gui.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import drakovek.hoarder.file.language.ViewerValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.listeners.DCloseListener;
import drakovek.hoarder.media.MediaViewer;

/**
 * FrameGUI for displaying and navigating through media files.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ViewerGUI extends FrameGUI
{
	/**
	 * Index of the currently shown DMF
	 */
	private int dmfIndex;
	
	/**
	 * ViewBrowserGUI Parent
	 */
	private ViewBrowserGUI ownerGUI;
	
	/**
	 * Panel for showing the currently selected media
	 */
	private MediaViewer mediaViewer;
	
	/**
	 * Button for displaying the previous DMF
	 */
	private DButton previousButton;
	
	/**
	 * Button for displaying the next DMF
	 */
	private DButton nextButton;
	
	/**
	 * Button for displaying available sequence branches
	 */
	private DButton branchButton;
	
	/**
	 * Initializes the GUI for the MediaFrameGUI class.
	 * 
	 * @param settings Program's Settings
	 * @param dmfHandler Program's DmfHandler
	 * @param ownerGUI ViewBrowserGUI Parent
	 * @param dmfIndex Index of DMF to show when GUI opens
	 * @param updateViews Whether the program should attempt to add a view to the currently shown media after viewing
	 */
	public ViewerGUI(ViewBrowserGUI ownerGUI, final int dmfIndex, final boolean updateViews)
	{
		super(ownerGUI.getSettings(), ownerGUI.getDmfHandler(), ViewerValues.VIEWER_TITLE);
		this.ownerGUI = ownerGUI;
		this.dmfIndex = dmfIndex;
		
		//CREATE BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3, getSettings().getSpaceSize(), 0));
		previousButton = new DButton(this, ViewerValues.PREVIOUS);
		nextButton = new DButton(this, ViewerValues.NEXT);
		branchButton = new DButton(this, ViewerValues.BRANCHES);
		bottomPanel.add(previousButton);
		bottomPanel.add(branchButton);
		bottomPanel.add(nextButton);
		
		//CREATE MENU BAR
		mediaViewer = new MediaViewer(this, nextButton, updateViews);
		JMenuBar menubar = new JMenuBar();
		menubar.add(mediaViewer.getScaleMenu());
		menubar.add(mediaViewer.getDetailMenu());
		menubar.add(mediaViewer.getViewMenu());
		
		//INITIALIZE FRAME
		getFrame().getContentPane().add(getSpacedPanel(bottomPanel, 1, 0, false, true, true, true), BorderLayout.SOUTH);
		getFrame().getContentPane().add(getSpacedPanel(mediaViewer.getViewerPanel()), BorderLayout.CENTER);
		getFrame().setJMenuBar(menubar);
		
		ownerGUI.getFrame().setAllowExit(false);
		getFrame().interceptFrameClose(this);
		
		int minWidth = getSettings().getFrameWidth() * getSettings().getFontSize();
		int minHeight = getSettings().getFrameHeight() * getSettings().getFontSize();
		int width = ownerGUI.getFrame().getWidth() - 100;
		int height = ownerGUI.getFrame().getHeight() - 100;
		if(width < minWidth)
		{
			width = minWidth;
			
		}//IF
		
		if(height < minHeight)
		{
			height = minHeight;
			
		}//IF
		
		getFrame().setSize(width, height);
		getFrame().setLocationRelativeTo(ownerGUI.getFrame());
		getFrame().setVisible(true);
		
		mediaViewer.setMedia(dmfIndex, true);
		
	}//CONSTRUCTOR
	
	/**
	 * Updates the displayed media to reflect the current DMF index, if valid.
	 */
	private void updateMedia()
	{
		if(dmfIndex < 0)
		{
			dmfIndex = 0;
			
		}//IF
		else if(dmfIndex >= getDmfHandler().getFilteredSize())
		{
			dmfIndex = getDmfHandler().getFilteredSize() - 1;
			
		}//ELSE IF
		else
		{
			mediaViewer.setMedia(dmfIndex, true);
		
		}//ELSE
		
	}//METHOD

	@Override
	public void enableAll()
	{	
		if(dmfIndex > 0)
		{
			previousButton.setEnabled(true);
		
		}//IF
		
		if(dmfIndex < (getDmfHandler().getFilteredSize() - 1))
		{
			nextButton.setEnabled(true);
		
		}//IF
		
		if(getDmfHandler().getNextIDsFiltered(dmfIndex).length > 1)
		{
			branchButton.setEnabled(true);
			
		}//IF
		
		mediaViewer.getScaleMenu().setEnabled(true);
		mediaViewer.getDetailMenu().setEnabled(true);
		mediaViewer.getViewMenu().setEnabled(true);
		
	}//METHOD

	@Override
	public void disableAll()
	{
		previousButton.setEnabled(false);
		nextButton.setEnabled(false);
		branchButton.setEnabled(false);
		mediaViewer.getScaleMenu().setEnabled(false);
		mediaViewer.getDetailMenu().setEnabled(false);
		mediaViewer.getViewMenu().setEnabled(false);
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DCloseListener.FRAME_CLOSE_EVENT:
				mediaViewer.incrementViews();
				ownerGUI.getFrame().setAllowExit(true);
				ownerGUI.setOffset(dmfIndex);
				dispose();
				break;
			case ViewerValues.PREVIOUS:
				dmfIndex--;
				updateMedia();
				break;
			case ViewerValues.NEXT:
				dmfIndex++;
				updateMedia();
				break;
				
		}//SWITCH
		
	}//METHOD
	
}//CLASS
