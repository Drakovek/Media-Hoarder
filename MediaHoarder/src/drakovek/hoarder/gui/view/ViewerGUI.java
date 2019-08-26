package drakovek.hoarder.gui.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DMenu;
import drakovek.hoarder.gui.swing.components.DRadioButtonMenuItem;
import drakovek.hoarder.gui.swing.listeners.DCloseListener;
import drakovek.hoarder.media.ImageHandler;
import drakovek.hoarder.media.MediaViewer;
import drakovek.hoarder.processing.BooleanInt;

/**
 * FrameGUI for displaying and navigating through media files.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ViewerGUI extends FrameGUI
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
	private MediaViewer mediaViewer;
	
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
	public ViewerGUI(DSettings settings, DmfHandler dmfHandler, ViewBrowserGUI ownerGUI, final int dmfIndex)
	{
		super(settings, dmfHandler, DefaultLanguage.VIEWER_TITLE);
		this.ownerGUI = ownerGUI;
		this.dmfIndex = dmfIndex;
		
		//CREATE SCALE MENU
		JMenuBar menubar = new JMenuBar();
		DMenu scaleMenu = new DMenu(this, DefaultLanguage.SCALE);
		ButtonGroup scaleGroup = new ButtonGroup();
		DRadioButtonMenuItem scaleFull = new DRadioButtonMenuItem(this, settings.getScaleType() == ImageHandler.SCALE_FULL, DefaultLanguage.SCALE_FULL);
		DRadioButtonMenuItem scale2dFit = new DRadioButtonMenuItem(this, settings.getScaleType() == ImageHandler.SCALE_2D_FIT, DefaultLanguage.SCALE_2D_FIT);
		DRadioButtonMenuItem scale2dStretch = new DRadioButtonMenuItem(this, settings.getScaleType() == ImageHandler.SCALE_2D_STRETCH, DefaultLanguage.SCALE_2D_STRETCH);
		DRadioButtonMenuItem scale1dFit = new DRadioButtonMenuItem(this, settings.getScaleType() == ImageHandler.SCALE_1D_FIT, DefaultLanguage.SCALE_1D_FIT);
		DRadioButtonMenuItem scale1dStretch = new DRadioButtonMenuItem(this, settings.getScaleType() == ImageHandler.SCALE_1D_STRETCH, DefaultLanguage.SCALE_1D_STRETCH);
		DRadioButtonMenuItem scaleDirect = new DRadioButtonMenuItem(this, settings.getScaleType() == ImageHandler.SCALE_DIRECT, DefaultLanguage.SCALE_DIRECT);
		scaleGroup.add(scaleFull);
		scaleGroup.add(scale2dFit);
		scaleGroup.add(scale2dStretch);
		scaleGroup.add(scale1dFit);
		scaleGroup.add(scale1dStretch);
		scaleGroup.add(scaleDirect);
		scaleMenu.add(scaleFull);
		scaleMenu.add(scale2dFit);
		scaleMenu.add(scale2dStretch);
		scaleMenu.add(scale1dFit);
		scaleMenu.add(scale1dStretch);
		scaleMenu.add(scaleDirect);
		menubar.add(scaleMenu);
		
		//CREATE INFO MENU
		DMenu detailMenu = new DMenu(this, DefaultLanguage.DETAILS);
		ButtonGroup detailGroup = new ButtonGroup();
		DRadioButtonMenuItem infoNone = new DRadioButtonMenuItem(this, settings.getDetailLocation() == MediaViewer.NO_DETAILS, DefaultLanguage.NO_DETAILS);
		DRadioButtonMenuItem infoTop = new DRadioButtonMenuItem(this, settings.getDetailLocation() == MediaViewer.TOP_DETAILS, DefaultLanguage.TOP_DETAILS);
		DRadioButtonMenuItem infoBottom = new DRadioButtonMenuItem(this, settings.getDetailLocation() == MediaViewer.BOTTOM_DETAILS, DefaultLanguage.BOTTOM_DETAILS);
		DRadioButtonMenuItem infoLeft = new DRadioButtonMenuItem(this, settings.getDetailLocation() == MediaViewer.LEFT_DETAILS, DefaultLanguage.LEFT_DETAILS);
		DRadioButtonMenuItem infoRight = new DRadioButtonMenuItem(this, settings.getDetailLocation() == MediaViewer.RIGHT_DETAILS, DefaultLanguage.RIGHT_DETAILS);
		detailGroup.add(infoNone);
		detailGroup.add(infoTop);
		detailGroup.add(infoBottom);
		detailGroup.add(infoLeft);
		detailGroup.add(infoRight);
		detailMenu.add(infoNone);
		detailMenu.add(infoTop);
		detailMenu.add(infoBottom);
		detailMenu.add(infoLeft);
		detailMenu.add(infoRight);
		menubar.add(detailMenu);
		
		
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
		mediaViewer = new MediaViewer(this, nextButton);
		getFrame().getContentPane().add(getSpacedPanel(bottomPanel, 1, 0, false, true, true, true), BorderLayout.SOUTH);
		getFrame().getContentPane().add(getSpacedPanel(mediaViewer.getViewerPanel()), BorderLayout.CENTER);
		getFrame().setJMenuBar(menubar);
		
		ownerGUI.getFrame().setAllowExit(false);
		getFrame().interceptFrameClose(this);
		getFrame().setLocationRelativeTo(ownerGUI.getFrame());
		getFrame().packRestricted();
		getFrame().setVisible(true);
		
		mediaViewer.setMedia(dmfIndex);
		
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
			mediaViewer.setMedia(dmfIndex);
		
		}//ELSE
		
	}//METHOD
	
	/**
	 * Updates the GUI to show the DMF details in a new given location.
	 * 
	 * @param location Location passed in from the location radio button
	 * @param isSelected Whether the given location was selected. If true, sets DMF details to given location, otherwise, does nothing.
	 * @since 2.0
	 */
	private void updateDetailLocation(final int location, final boolean isSelected)
	{
		if(isSelected)
		{
			getSettings().setDetailLocation(location);
			mediaViewer.updateDetailLocation();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Changes the current scale type to a new given scale type.
	 * 
	 * @param scaleType Scale type passed in from radio button
	 * @param isSelected Whether the given scale type was selected. If true, sets current scale type to the scale type given, otherwise, does nothing.
	 * @since 2.0
	 */
	private void changeScaleType(final int scaleType, final boolean isSelected)
	{
		if(isSelected)
		{
			getSettings().setScaleType(scaleType);
			mediaViewer.updateMedia();
			
		}//IF
		
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
			case DefaultLanguage.NO_DETAILS:
				updateDetailLocation(MediaViewer.NO_DETAILS, BooleanInt.getBoolean(value));
				break;
			case DefaultLanguage.TOP_DETAILS:
				updateDetailLocation(MediaViewer.TOP_DETAILS, BooleanInt.getBoolean(value));
				break;
			case DefaultLanguage.BOTTOM_DETAILS:
				updateDetailLocation(MediaViewer.BOTTOM_DETAILS, BooleanInt.getBoolean(value));
				break;
			case DefaultLanguage.LEFT_DETAILS:
				updateDetailLocation(MediaViewer.LEFT_DETAILS, BooleanInt.getBoolean(value));
				break;
			case DefaultLanguage.RIGHT_DETAILS:
				updateDetailLocation(MediaViewer.RIGHT_DETAILS, BooleanInt.getBoolean(value));
				break;
			case DefaultLanguage.SCALE_FULL:
				changeScaleType(ImageHandler.SCALE_FULL, BooleanInt.getBoolean(value));
				break;
			case DefaultLanguage.SCALE_1D_FIT:
				changeScaleType(ImageHandler.SCALE_1D_FIT, BooleanInt.getBoolean(value));
				break;	
			case DefaultLanguage.SCALE_1D_STRETCH:
				changeScaleType(ImageHandler.SCALE_1D_STRETCH, BooleanInt.getBoolean(value));
				break;
			case DefaultLanguage.SCALE_2D_FIT:
				changeScaleType(ImageHandler.SCALE_2D_STRETCH, BooleanInt.getBoolean(value));
				break;
			case DefaultLanguage.SCALE_2D_STRETCH:
				changeScaleType(ImageHandler.SCALE_2D_STRETCH, BooleanInt.getBoolean(value));
				break;
			case DefaultLanguage.SCALE_DIRECT:
				changeScaleType(ImageHandler.SCALE_DIRECT, BooleanInt.getBoolean(value));
				break;
				
		}//SWITCH
		
	}//METHOD
	
}//CLASS
