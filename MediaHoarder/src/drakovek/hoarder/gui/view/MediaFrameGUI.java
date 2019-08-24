package drakovek.hoarder.gui.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.listeners.DCloseListener;
import drakovek.hoarder.media.MediaViewerPanel;

/**
 * FrameGUI for displaying and navigating through media files.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class MediaFrameGUI extends FrameGUI
{
	/**
	 * Index of the currently shown DMF
	 * 
	 * @since 2.0
	 */
	private int dmfIndex;
	
	/**
	 * ViewBrowserGUI Parent
	 * 
	 * @since 2.0
	 */
	private FrameGUI ownerGUI;
	
	/**
	 * Panel for showing the currently selected media
	 * 
	 * @since 2.0
	 */
	private MediaViewerPanel mediaViewerPanel;
	
	/**
	 * Button for displaying the previous DMF
	 * 
	 * @since 2.0
	 */
	private DButton previousButton;
	
	/**
	 * Button for displaying the next DMF
	 * 
	 * @since 2.0
	 */
	private DButton nextButton;
	
	/**
	 * Button for displaying available sequence branches
	 * 
	 * @since 2.0
	 */
	private DButton branchButton;
	
	/**
	 * Initializes the GUI for the MediaFrameGUI class.
	 * 
	 * @param settings Program's Settings
	 * @param dmfHandler Program's DmfHandler
	 * @param ownerGUI ViewBrowserGUI Parent
	 * @param dmfIndex Index of DMF to show when GUI opens
	 * @since 2.0
	 */
	public MediaFrameGUI(DSettings settings, DmfHandler dmfHandler, ViewBrowserGUI ownerGUI, final int dmfIndex)
	{
		super(settings, dmfHandler, DefaultLanguage.VIEWER_TITLE);
		this.ownerGUI = ownerGUI;
		this.dmfIndex = dmfIndex;
		
		//CREATE BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3, getSettings().getSpaceSize(), 0));
		previousButton = new DButton(this, DefaultLanguage.PREVIOUS);
		nextButton = new DButton(this, DefaultLanguage.NEXT);
		branchButton = new DButton(this, DefaultLanguage.BRANCHES);
		bottomPanel.add(previousButton);
		bottomPanel.add(branchButton);
		bottomPanel.add(nextButton);
		
		//INITIALIZE FRAME
		mediaViewerPanel = new MediaViewerPanel(this, nextButton);
		getFrame().getContentPane().add(getSpacedPanel(bottomPanel, 1, 0, false, true, true, true), BorderLayout.SOUTH);
		getFrame().getContentPane().add(getSpacedPanel(mediaViewerPanel), BorderLayout.CENTER);
		
		ownerGUI.getFrame().setAllowExit(false);
		getFrame().interceptFrameClose(this);
		getFrame().setLocationRelativeTo(ownerGUI.getFrame());
		getFrame().setSize(200, 200);
		getFrame().setVisible(true);
		
		mediaViewerPanel.setMedia(dmfIndex);
		
	}//CONSTRUCTOR
	
	/**
	 * Updates the displayed media to reflect the current DMF index, if valid.
	 * 
	 * @since 2.0
	 */
	private void updateMedia()
	{
		if(dmfIndex < 0)
		{
			dmfIndex = 0;
			
		}//IF
		else if(dmfIndex >= getDmfHandler().getSize())
		{
			dmfIndex = getDmfHandler().getSize() - 1;
			
		}//ELSE IF
		else
		{
			mediaViewerPanel.setMedia(dmfIndex);
		
		}//ELSE
		
	}//METHOD

	@Override
	public void enableAll()
	{	
		previousButton.setEnabled(true);
		nextButton.setEnabled(true);
		branchButton.setEnabled(true);
		
	}//METHOD

	@Override
	public void disableAll()
	{
		previousButton.setEnabled(false);
		nextButton.setEnabled(false);
		branchButton.setEnabled(false);
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DCloseListener.FRAME_CLOSE_EVENT:
				dispose();
				ownerGUI.getFrame().setAllowExit(true);
				break;
			case DefaultLanguage.PREVIOUS:
				dmfIndex--;
				updateMedia();
				break;
			case DefaultLanguage.NEXT:
				dmfIndex++;
				updateMedia();
				break;
		}//SWITCH
		
	}//METHOD
	
}//CLASS
