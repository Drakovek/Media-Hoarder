package drakovek.hoarder.media;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import drakovek.hoarder.file.FileOpener;
import drakovek.hoarder.file.dmf.DMF;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.DmfLanguageValues;
import drakovek.hoarder.file.language.ViewerValues;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.DEditorPane;
import drakovek.hoarder.gui.swing.components.DMenu;
import drakovek.hoarder.gui.swing.components.DMenuItem;
import drakovek.hoarder.gui.swing.components.DRadioButtonMenuItem;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.compound.DHyperlinkDialog;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.gui.swing.compound.DTextDialog;
import drakovek.hoarder.gui.swing.listeners.DActionListener;
import drakovek.hoarder.gui.swing.listeners.DHyperlinkListener;
import drakovek.hoarder.gui.swing.listeners.DResizeListener;
import drakovek.hoarder.processing.BooleanInt;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.processing.TimeMethods;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * Panel for showing media and DMF details.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class MediaViewer extends BaseGUI implements DWorker
{
	/**
	 * Value indicating not to show DMF details
	 */
	public static final int NO_DETAILS = 0;
	
	/**
	 * Value indicating to place DMF details on the top of the media panel
	 */
	public static final int TOP_DETAILS = 1;

	/**
	 * Value indicating to place DMF details on the bottom of the media panel
	 */
	public static final int BOTTOM_DETAILS = 2;
	
	/**
	 * Value indicating to place DMF details on the left side of the media panel
	 */
	public static final int LEFT_DETAILS = 3;
	
	/**
	 * Value indicating to place DMF details on the right side of the media panel
	 */
	public static final int RIGHT_DETAILS = 4;

	/**
	 * Index of the currently shown DMF
	 */
	private int dmfIndex;
	
	/**
	 * Whether the program should attempt to add a view to the currently shown media after viewing
	 */
	private boolean updateViews;
	
	/**
	 * Time in milliseconds when the currently shown media was first made visible. Used to determine how long the current media has been open.
	 */
	private long startTime;
	
	/**
	 * String containing HTML/CSS formatted DMF details of the currently shown DMF
	 */
	private String detailString;
	
	/**
	 * FileTypeHandler mainly used to detect if secondary files are image files
	 */
	private FileTypeHandler fileTypeHandler;
	
	/**
	 * Dialog for showing when a hyperlink has been clicked.
	 */
	private DHyperlinkDialog hyperlinkDialog;
	
	/**
	 * Parent GUI for the media viewer panel
	 */
	private FrameGUI ownerGUI;
	
	/**
	 * Menu for selecting between different scale types for images
	 */
	private DMenu scaleMenu;
	
	/**
	 * Menu for selecting how to display DMF info for the currently selected media
	 */
	private DMenu detailMenu;
	
	/**
	 * Menu for viewing files associated with the currently selected DMF
	 */
	private DMenu viewMenu;
	
	/**
	 * Main viewer panel used to contain both media and DMF info
	 */
	private JPanel viewerPanel;
	
	/**
	 * Media Panel that contains and handles media viewing
	 */
	private MediaPanel mediaPanel;
	
	/**
	 * Panel for containing the media panel and controlling it's size
	 */
	private JPanel mediaContainerPanel;
	
	/**
	 * Panel for containing the DMF Detail scroll pane and controlling it's size
	 */
	private JPanel detailPanel;
	
	/**
	 * Panels containing vertical spacers to control the size of the media container panel
	 */
	private JPanel[] verticalMediaPanels;
	
	/**
	 * Panels containing horizontal spacers to control the size of the media container panel
	 */
	private JPanel[] horizontalMediaPanels;
	
	/**
	 * Panels containing vertical spacers to control the size of the DMF details panel
	 */
	private JPanel[] verticalDetailPanels;
	
	/**
	 * Panels containing horizontal spacers to control the size of the DMF details panel
	 */
	private JPanel[] horizontalDetailPanels;
	
	/**
	 * DEditorPane for showing DMF details
	 */
	private DEditorPane detailText;
	
	/**
	 * Scroll pane for holding the detail editor pane
	 */
	private DScrollPane detailScroll;
	
	/**
	 * Main progress dialog for showing when media files are being loaded.
	 */
	private DProgressDialog progressDialog;

	/**
	 * Initializes the MediaViewerPanel class.
	 * 
	 * @param ownerGUI Parent GUI for the media viewer panel
	 * @param colorReference JComponent from which to get color information for CSS
	 * @param updateViews Whether the program should attempt to add a view to the currently shown media after viewing
	 */
	public MediaViewer(FrameGUI ownerGUI, JComponent colorReference, final boolean updateViews)
	{
		super(ownerGUI.getSettings());
		this.ownerGUI = ownerGUI;
		this.updateViews = updateViews;
		this.startTime = 0L;
		
		viewerPanel = new JPanel();
		viewerPanel.setLayout(new GridLayout(1,1));
		progressDialog = new DProgressDialog(getSettings());
		fileTypeHandler = new FileTypeHandler(getSettings());
		hyperlinkDialog = new DHyperlinkDialog(getSettings());
		
		//CREATE SCALE MENU
		scaleMenu = new DMenu(this, ViewerValues.SCALE);
		ButtonGroup scaleGroup = new ButtonGroup();
		DRadioButtonMenuItem scaleFull = new DRadioButtonMenuItem(this, getSettings().getScaleType() == ImageHandler.SCALE_FULL, ViewerValues.SCALE_FULL);
		DRadioButtonMenuItem scale2dFit = new DRadioButtonMenuItem(this, getSettings().getScaleType() == ImageHandler.SCALE_2D_FIT, ViewerValues.SCALE_2D_FIT);
		DRadioButtonMenuItem scale2dStretch = new DRadioButtonMenuItem(this, getSettings().getScaleType() == ImageHandler.SCALE_2D_STRETCH, ViewerValues.SCALE_2D_STRETCH);
		DRadioButtonMenuItem scale1dFit = new DRadioButtonMenuItem(this, getSettings().getScaleType() == ImageHandler.SCALE_1D_FIT, ViewerValues.SCALE_1D_FIT);
		DRadioButtonMenuItem scale1dStretch = new DRadioButtonMenuItem(this, getSettings().getScaleType() == ImageHandler.SCALE_1D_STRETCH, ViewerValues.SCALE_1D_STRETCH);
		DRadioButtonMenuItem scaleDirect = new DRadioButtonMenuItem(this, getSettings().getScaleType() == ImageHandler.SCALE_DIRECT, ViewerValues.SCALE_DIRECT);
		scaleDirect.addActionListener(new DActionListener(this, ViewerValues.SCALE));
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
				
		//CREATE INFO MENU
		detailMenu = new DMenu(this, ViewerValues.DETAILS);
		ButtonGroup detailGroup = new ButtonGroup();
		DRadioButtonMenuItem infoNone = new DRadioButtonMenuItem(this, getSettings().getDetailLocation() == MediaViewer.NO_DETAILS, ViewerValues.NO_DETAILS);
		DRadioButtonMenuItem infoTop = new DRadioButtonMenuItem(this, getSettings().getDetailLocation() == MediaViewer.TOP_DETAILS, ViewerValues.TOP_DETAILS);
		DRadioButtonMenuItem infoBottom = new DRadioButtonMenuItem(this, getSettings().getDetailLocation() == MediaViewer.BOTTOM_DETAILS, ViewerValues.BOTTOM_DETAILS);
		DRadioButtonMenuItem infoLeft = new DRadioButtonMenuItem(this, getSettings().getDetailLocation() == MediaViewer.LEFT_DETAILS, ViewerValues.LEFT_DETAILS);
		DRadioButtonMenuItem infoRight = new DRadioButtonMenuItem(this, getSettings().getDetailLocation() == MediaViewer.RIGHT_DETAILS, ViewerValues.RIGHT_DETAILS);
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
		
		//CREATE VIEW MENU
		viewMenu = new DMenu(this, CommonValues.VIEW);
		viewMenu.add(new DMenuItem(this, ViewerValues.FULLSCREEN));
		viewMenu.addSeparator();
		viewMenu.add(new DMenuItem(this, ViewerValues.OPEN_MEDIA_FILE));
		viewMenu.add(new DMenuItem(this, ViewerValues.OPEN_SECONDARY_FILE));
		viewMenu.add(new DMenuItem(this, ViewerValues.OPEN_DMF));

		//CREATE MEDIA CONTAINER PANEL
		mediaPanel = new MediaPanel(this, colorReference);
		mediaContainerPanel = new JPanel();
		verticalMediaPanels = new JPanel[2];
		verticalMediaPanels[0] = new JPanel();
		verticalMediaPanels[1] = new JPanel();
		horizontalMediaPanels = new JPanel[2];
		horizontalMediaPanels[0] = new JPanel();
		horizontalMediaPanels[1] = new JPanel();
		mediaContainerPanel.setLayout(new BorderLayout());
		verticalMediaPanels[0].setLayout(new GridLayout(1,1));
		verticalMediaPanels[1].setLayout(new GridLayout(1,1));
		horizontalMediaPanels[0].setLayout(new GridLayout(1,1));
		horizontalMediaPanels[1].setLayout(new GridLayout(1,1));
		mediaContainerPanel.add(verticalMediaPanels[0], BorderLayout.WEST);
		mediaContainerPanel.add(verticalMediaPanels[1], BorderLayout.EAST);
		mediaContainerPanel.add(horizontalMediaPanels[0], BorderLayout.NORTH);
		mediaContainerPanel.add(horizontalMediaPanels[1], BorderLayout.SOUTH);
		mediaContainerPanel.add(mediaPanel, BorderLayout.CENTER);
		mediaContainerPanel.addComponentListener(new DResizeListener(this, null));
		
		//CREATE DETAIL PANEL
		detailText = new DEditorPane(ownerGUI, colorReference);
		detailText.addHyperlinkListener(new DHyperlinkListener(this));
		detailScroll = new DScrollPane(getSettings(), detailText);
		detailPanel = new JPanel();
		verticalDetailPanels = new JPanel[2];
		verticalDetailPanels[0] = new JPanel();
		verticalDetailPanels[1] = new JPanel();
		horizontalDetailPanels = new JPanel[2];
		horizontalDetailPanels[0] = new JPanel();
		horizontalDetailPanels[1] = new JPanel();
		detailPanel.setLayout(new BorderLayout());
		verticalDetailPanels[0].setLayout(new GridLayout(1,1));
		verticalDetailPanels[1].setLayout(new GridLayout(1,1));
		horizontalDetailPanels[0].setLayout(new GridLayout(1,1));
		horizontalDetailPanels[1].setLayout(new GridLayout(1,1));
		detailPanel.add(verticalDetailPanels[0], BorderLayout.WEST);
		detailPanel.add(verticalDetailPanels[1], BorderLayout.EAST);
		detailPanel.add(horizontalDetailPanels[0], BorderLayout.NORTH);
		detailPanel.add(horizontalDetailPanels[1], BorderLayout.SOUTH);
		detailPanel.add(detailScroll, BorderLayout.CENTER);
		
		//ADD SPACER
		int width = getSettings().getFontSize() * 20;
		int height = getSettings().getFontSize() * 15;
		
		verticalMediaPanels[0].add(Box.createRigidArea(new Dimension(1, height)));
		verticalMediaPanels[1].add(Box.createRigidArea(new Dimension(1, height)));
		horizontalMediaPanels[0].add(Box.createRigidArea(new Dimension(width, 1)));
		horizontalMediaPanels[1].add(Box.createRigidArea(new Dimension(width, 1)));
		verticalDetailPanels[0].add(Box.createRigidArea(new Dimension(1, height)));
		verticalDetailPanels[1].add(Box.createRigidArea(new Dimension(1, height)));
		horizontalDetailPanels[0].add(Box.createRigidArea(new Dimension(width, 1)));
		horizontalDetailPanels[1].add(Box.createRigidArea(new Dimension(width, 1)));
		
		updateDetailLocation();
		
	}//CONSTRUCTOR
	
	/**
	 * Returns the scale menu.
	 * 
	 * @return Scale Menu
	 */
	public DMenu getScaleMenu()
	{
		return scaleMenu;
		
	}//METHOD
	
	/**
	 * Returns the detail menu.
	 * 
	 * @return Detail Menu
	 */
	public DMenu getDetailMenu()
	{
		return detailMenu;
		
	}//METHOD
	
	/**
	 * Returns the view menu.
	 * 
	 * @return View Menu
	 */
	public DMenu getViewMenu()
	{
		return viewMenu;
		
	}//METHOD
	
	/**
	 * Returns the main viewer panel.
	 * 
	 * @return Viewer Panel
	 */
	public JPanel getViewerPanel()
	{
		return viewerPanel;
		
	}//METHOD
	
	/**
	 * Updates the location of the DMF Detail panel
	 */
	public void updateDetailLocation()
	{
		viewerPanel.removeAll();
		int location = getSettings().getDetailLocation();
		boolean updateText = false;
		
		switch(location)
		{
			case TOP_DETAILS:
				viewerPanel.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, detailPanel, mediaContainerPanel));
				updateText = true;
				break;
			case BOTTOM_DETAILS:
				viewerPanel.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, mediaContainerPanel, detailPanel));
				updateText = true;
				break;
			case LEFT_DETAILS:
				viewerPanel.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, detailPanel, mediaContainerPanel));
				updateText = true;
				break;
			case RIGHT_DETAILS:
				viewerPanel.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mediaContainerPanel, detailPanel));
				updateText = true;
				break;	
			default:
				viewerPanel.add(mediaContainerPanel);
				break;
				
		}//SWITCH
		
		if(updateText)
		{
			detailText.setTextHTML(detailString);
			detailScroll.resetTopLeft();
			
		}//IF
		
		viewerPanel.revalidate();
		
	}//METHOD
	
	/**
	 * Sets what media file should be shown.
	 * 
	 * @param dmfIndex Index of the DMF from which to get details
	 */
	public void setMedia(final int dmfIndex)
	{
		incrementViews();
		this.dmfIndex = dmfIndex;
		ownerGUI.getFrame().setProcessRunning(true);
		progressDialog.setCancelled(false);
		progressDialog.startProgressDialog(ownerGUI.getFrame(), ViewerValues.LOADING_MEDIA_TITLE);
		progressDialog.setProcessLabel(ViewerValues.LOADING_MEDIA_MESSAGE);
		progressDialog.setDetailLabel(CommonValues.RUNNING, true);
		progressDialog.setProgressBar(true, false, 0, 0);
		mediaPanel.setPanelType(ownerGUI.getDmfHandler().getMediaFile(dmfIndex));
		viewerPanel.revalidate();
		(new DSwingWorker(this, ViewerValues.LOADING_MEDIA_MESSAGE)).execute();
		
	}//METHOD
	
	/**
	 * Updates the current media file to reflect the current size of the viewer.
	 */
	public void updateMedia()
	{
		if(!ownerGUI.getFrame().isProcessRunning())
		{
			ownerGUI.getFrame().setProcessRunning(false);
			progressDialog.setCancelled(false);
			progressDialog.startProgressDialog(ownerGUI.getFrame(), ViewerValues.LOADING_MEDIA_TITLE);
			progressDialog.setProcessLabel(ViewerValues.LOADING_MEDIA_MESSAGE);
			progressDialog.setDetailLabel(CommonValues.RUNNING, true);
			progressDialog.setProgressBar(true, false, 0, 0);
			(new DSwingWorker(this, new String())).execute();
		
		}//IF
		
	}//METHOD
	
	/**
	 * Sets the details editor pane to show the information from a DMF at a given index.
	 * 
	 * @param dmfIndex DMF Index
	 */
	private void setDetails(final int dmfIndex)
	{
		//SET STYLE
		StringBuilder htmlText = new StringBuilder();
		
		//SET TITLE AND ARTIST(S)
		htmlText.append("<div class=\"drakovek_title_block\"><span class=\""); //$NON-NLS-1$
		htmlText.append(DEditorPane.LARGE_TEXT_CLASS);
		htmlText.append("\"<b>"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(ownerGUI.getDmfHandler().getTitle(dmfIndex)));
		htmlText.append("</b></span><br><i>"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(StringMethods.arrayToString(ownerGUI.getDmfHandler().getArtists(dmfIndex), true, new String())));
		htmlText.append("</i>"); //$NON-NLS-1$
		
		if(ownerGUI.getDmfHandler().getSequenceTitle(dmfIndex).length() > 0)
		{
			htmlText.append("<br><span class=\""); //$NON-NLS-1$
			htmlText.append(DEditorPane.SMALL_TEXT_CLASS);
			htmlText.append("\">"); //$NON-NLS-1$
			htmlText.append(StringMethods.addHtmlEscapes(ownerGUI.getDmfHandler().getSequenceTitle(dmfIndex)));
			
			if(ownerGUI.getDmfHandler().getSectionTitle(dmfIndex).length() > 0 && !ownerGUI.getDmfHandler().getSectionTitle(dmfIndex).equals(DMF.EMPTY_SECTION))
			{
				htmlText.append(" - "); //$NON-NLS-1$
				htmlText.append(StringMethods.addHtmlEscapes(ownerGUI.getDmfHandler().getSectionTitle(dmfIndex)));
				
			}//IF
			
			htmlText.append("</span>"); //$NON-NLS-1$
			
		}//IF
		
		//SET DESCRIPTION
		htmlText.append("</div><br><hr><br>"); //$NON-NLS-1$
		
		if(ownerGUI.getDmfHandler().getSecondaryFile(dmfIndex) != null && fileTypeHandler.isImageFile(ownerGUI.getDmfHandler().getSecondaryFile(dmfIndex)))
		{
			htmlText.append("<div style=\"text-align:center;\">"); //$NON-NLS-1$
			htmlText.append("<img src=\"file://"); //$NON-NLS-1$
			htmlText.append(ownerGUI.getDmfHandler().getSecondaryFile(dmfIndex).getAbsolutePath().replaceAll("\\\\", "\\/")); //$NON-NLS-1$ //$NON-NLS-2$
			htmlText.append("\" alt=\""); //$NON-NLS-1$
			htmlText.append(StringMethods.addHtmlEscapes(ownerGUI.getDmfHandler().getTitle(dmfIndex)));
			htmlText.append("\"></div><br>"); //$NON-NLS-1$
			
		}//IF
		
		htmlText.append("<div class=\"drakovek_description\">"); //$NON-NLS-1$
		
		htmlText.append(ownerGUI.getDmfHandler().getDescription(dmfIndex));
		
		//SET TAGS
		htmlText.append("</div><br><hr><br><div class=\"drakovek_info\"><b>"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(getSettings().getLanguageText(ViewerValues.VIEWS)));
		htmlText.append("&nbsp;</b>"); //$NON-NLS-1$
		htmlText.append(Integer.toString(ownerGUI.getDmfHandler().getViews(dmfIndex)));
		
		if(ownerGUI.getDmfHandler().getRating(dmfIndex) > 0)
		{
			htmlText.append("&nbsp;&nbsp;&nbsp;&nbsp;<b>"); //$NON-NLS-1$
			htmlText.append(StringMethods.addHtmlEscapes(getSettings().getLanguageText(ViewerValues.RATING)));
			htmlText.append("&nbsp;</b>"); //$NON-NLS-1$
			htmlText.append(StringMethods.addHtmlEscapes(StringMethods.extendCharacter('★', ownerGUI.getDmfHandler().getRating(dmfIndex))));
			
		}//IF
		
		htmlText.append("<br><br><b>"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(getSettings().getLanguageText(DmfLanguageValues.WEB_TAG_LABEL)));
		htmlText.append("&nbsp; </b>"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(StringMethods.arrayToString(ownerGUI.getDmfHandler().getWebTags(dmfIndex), true, getSettings().getLanguageText(CommonValues.NON_APPLICABLE))));
		htmlText.append("<br><br><b>"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(getSettings().getLanguageText(DmfLanguageValues.USER_TAG_LABEL)));
		htmlText.append("&nbsp; </b>"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(StringMethods.arrayToString(ownerGUI.getDmfHandler().getUserTags(dmfIndex), true, getSettings().getLanguageText(CommonValues.NON_APPLICABLE))));
		
		//SET INFO TABLE
		htmlText.append("</div><br><div class=\""); //$NON-NLS-1$
		htmlText.append(DEditorPane.SMALL_TEXT_CLASS);
		htmlText.append("\"><table><tr><td><b>"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(getSettings().getLanguageText(DmfLanguageValues.PAGE_URL_LABEL)));
		htmlText.append("</b>&nbsp; <a href=\""); //$NON-NLS-1$
		htmlText.append(ownerGUI.getDmfHandler().getPageURL(dmfIndex));
		htmlText.append("\">"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(getSettings().getLanguageText(ViewerValues.LINK)));
		htmlText.append("</a></td><td><b>"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(getSettings().getLanguageText(ViewerValues.DATE)));
		htmlText.append("</b>&nbsp; "); //$NON-NLS-1$
		htmlText.append(TimeMethods.getDateString(getSettings(), TimeMethods.DATE_LONG, ownerGUI.getDmfHandler().getTime(dmfIndex)));
		htmlText.append("</td></tr><tr><td><b>"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(getSettings().getLanguageText(DmfLanguageValues.DIRECT_URL_LABEL)));
		htmlText.append("</b>&nbsp; <a href=\""); //$NON-NLS-1$
		htmlText.append(ownerGUI.getDmfHandler().getMediaURL(dmfIndex));
		htmlText.append("\">"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(getSettings().getLanguageText(ViewerValues.LINK)));
		htmlText.append("</a></td><td><b>"); //$NON-NLS-1$
		htmlText.append(StringMethods.addHtmlEscapes(getSettings().getLanguageText(ViewerValues.TIME)));
		htmlText.append("</b>&nbsp; "); //$NON-NLS-1$
		htmlText.append(TimeMethods.getTimeString(getSettings(), ownerGUI.getDmfHandler().getTime(dmfIndex)));
		htmlText.append("</td></tr>"); //$NON-NLS-1$
		
		if(ownerGUI.getDmfHandler().getSecondaryURL(dmfIndex).length() > 0)
		{
			htmlText.append("<tr><td><b>"); //$NON-NLS-1$
			htmlText.append(getSettings().getLanguageText(DmfLanguageValues.SECONDARY_URL_LABEL));
			htmlText.append("</b>&nbsp; <a href=\""); //$NON-NLS-1$
			htmlText.append(ownerGUI.getDmfHandler().getSecondaryURL(dmfIndex));
			htmlText.append("\">"); //$NON-NLS-1$
			htmlText.append(getSettings().getLanguageText(ViewerValues.LINK));
			htmlText.append("</a></tr></td>"); //$NON-NLS-1$
			
		}//IF
		
		htmlText.append("</table></div>"); //$NON-NLS-1$
		
		detailString = htmlText.toString();
	
	}//METHOD
	
	/**
	 * Updates the GUI to show the DMF details in a new given location.
	 * 
	 * @param location Location passed in from the location radio button
	 * @param isSelected Whether the given location was selected. If true, sets DMF details to given location, otherwise, does nothing.
	 */
	private void updateDetailLocation(final int location, final boolean isSelected)
	{
		if(isSelected)
		{
			getSettings().setDetailLocation(location);
			updateDetailLocation();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Changes the current scale type to a new given scale type.
	 * 
	 * @param scaleType Scale type passed in from radio button
	 * @param isSelected Whether the given scale type was selected. If true, sets current scale type to the scale type given, otherwise, does nothing.
	 */
	private void changeScaleType(final int scaleType, final boolean isSelected)
	{
		if(isSelected)
		{
			getSettings().setScaleType(scaleType);
			updateMedia();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Sets the scale if the direct scale option is selected, prompting the use to choose the scaling amount.
	 */
	private void setScaleDirect()
	{
		getSettings().setScaleType(ImageHandler.SCALE_DIRECT);
		
		DTextDialog textDialog = new DTextDialog(getSettings());
		String[] messageIDs = {ViewerValues.DIRECT_SCALE_MESSAGE};
		try
		{
			double result = Double.parseDouble(textDialog.openTextDialog(ownerGUI.getFrame(), ViewerValues.DIRECT_SCALE_TITLE, messageIDs, Double.toString(getSettings().getScaleAmount())));
			if(result < (double)10)
			{
				getSettings().setScaleAmount(result);
				updateMedia();
				
			}//IF
			
		}//TRY
		catch(Exception e){}
		
	}//METHOD
	
	/**
	 * Attempts to add a view to the currently shown DMF.
	 */
	public void incrementViews()
	{
		if(updateViews && startTime != 0L)
		{
			long elapsed = System.currentTimeMillis() - startTime;
			if(elapsed > 5000L)
			{
				DMF dmf = new DMF(ownerGUI.getDmfHandler().getDmfFile(dmfIndex));
				dmf.setViews(dmf.getViews() + 1);
				dmf.writeDMF();
				ownerGUI.getDmfHandler().setDMF(dmf, dmfIndex);
				
			}//IF
			
		}//IF
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DResizeListener.RESIZE:
				updateMedia();
				break;
			case ViewerValues.NO_DETAILS:
				updateDetailLocation(MediaViewer.NO_DETAILS, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.TOP_DETAILS:
				updateDetailLocation(MediaViewer.TOP_DETAILS, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.BOTTOM_DETAILS:
				updateDetailLocation(MediaViewer.BOTTOM_DETAILS, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.LEFT_DETAILS:
				updateDetailLocation(MediaViewer.LEFT_DETAILS, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.RIGHT_DETAILS:
				updateDetailLocation(MediaViewer.RIGHT_DETAILS, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.SCALE_FULL:
				changeScaleType(ImageHandler.SCALE_FULL, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.SCALE_1D_FIT:
				changeScaleType(ImageHandler.SCALE_1D_FIT, BooleanInt.getBoolean(value));
				break;	
			case ViewerValues.SCALE_1D_STRETCH:
				changeScaleType(ImageHandler.SCALE_1D_STRETCH, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.SCALE_2D_FIT:
				changeScaleType(ImageHandler.SCALE_2D_STRETCH, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.SCALE_2D_STRETCH:
				changeScaleType(ImageHandler.SCALE_2D_STRETCH, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.SCALE:
				setScaleDirect();
				break;
			case ViewerValues.FULLSCREEN:
				break;
			case ViewerValues.OPEN_MEDIA_FILE:
				FileOpener.openFile(ownerGUI.getDmfHandler().getMediaFile(dmfIndex));
				break;
			case ViewerValues.OPEN_SECONDARY_FILE:
				FileOpener.openFile(ownerGUI.getDmfHandler().getSecondaryFile(dmfIndex));
				break;
			case ViewerValues.OPEN_DMF:
				FileOpener.openFile(ownerGUI.getDmfHandler().getDmfFile(dmfIndex));
				break;
			default:
				hyperlinkDialog.openDialog(ownerGUI.getFrame(), id);
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void run(String id)
	{
		switch(id)
		{
			case ViewerValues.LOADING_MEDIA_MESSAGE:
				setDetails(dmfIndex);
				mediaPanel.setMedia(ownerGUI.getDmfHandler().getMediaFile(dmfIndex));
				break;
			default:
				mediaPanel.updateMedia();
				break;
				
		}//METHOD
		
	}//METHOD

	@Override
	public void done(String id)
	{
		if(id.equals(ViewerValues.LOADING_MEDIA_MESSAGE) && getSettings().getDetailLocation() != NO_DETAILS)
		{
			detailText.setTextHTML(detailString);
			detailScroll.resetTopLeft();
			
		}//IF
		
		startTime = System.currentTimeMillis();
		
		ownerGUI.getFrame().setProcessRunning(false);
		progressDialog.closeProgressDialog();
		progressDialog.setCancelled(false);
		
	}//METHOD
	
}//CLASS
