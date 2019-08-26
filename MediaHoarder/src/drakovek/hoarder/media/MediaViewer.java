package drakovek.hoarder.media;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import drakovek.hoarder.file.dmf.DMF;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.DEditorPane;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.gui.swing.listeners.DResizeListener;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.processing.TimeMethods;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * Panel for showing media and DMF details.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class MediaViewer extends BaseGUI implements DWorker
{
	/**
	 * Value indicating not to show DMF details
	 * 
	 * @since 2.0
	 */
	public static final int NO_DETAILS = 0;
	
	/**
	 * Value indicating to place DMF details on the top of the media panel
	 * 
	 * @since 2.0
	 */
	public static final int TOP_DETAILS = 1;

	/**
	 * Value indicating to place DMF details on the bottom of the media panel
	 * 
	 * @since 2.0
	 */
	public static final int BOTTOM_DETAILS = 2;
	
	/**
	 * Value indicating to place DMF details on the left side of the media panel
	 * 
	 * @since 2.0
	 */
	public static final int LEFT_DETAILS = 3;
	
	/**
	 * Value indicating to place DMF details on the right side of the media panel
	 * 
	 * @since 2.0
	 */
	public static final int RIGHT_DETAILS = 4;

	/**
	 * CSS class to use for elements requiring large text
	 * 
	 * @since 2.0
	 */
	private static final String LARGE_TEXT_CLASS = "drakovek_large_text"; //$NON-NLS-1$
	
	/**
	 * CSS class to use for elements requiring small text
	 * 
	 * @since 2.0
	 */
	private static final String SMALL_TEXT_CLASS = "drakovek_small_text"; //$NON-NLS-1$
	
	/**
	 * Hex color for the main CSS text color
	 * 
	 * @since 2.0
	 */
	private String fontColor;
	
	/**
	 * Hex color for the hyperlink CSS text color
	 * 
	 * @since 2.0
	 */
	private String hyperlinkColor;
	
	/**
	 * Font size used for large CSS text
	 * 
	 * @since 2.0
	 */
	private int largeFontSize;
	
	/**
	 * Font size used for small CSS text
	 * 
	 * @since 2.0
	 */
	private int smallFontSize;
	
	/**
	 * Border size used for CSS hr elements
	 * 
	 * @since 2.0
	 */
	private int borderSize;
	
	/**
	 * Index of the currently shown DMF
	 * 
	 * @since 2.0
	 */
	private int dmfIndex;
	
	/**
	 * Parent GUI for the media viewer panel
	 * 
	 * @since 2.0
	 */
	private FrameGUI ownerGUI;
	
	/**
	 * Main viewer panel used to contain both media and DMF info
	 * 
	 * @since 2.0
	 */
	private JPanel viewerPanel;
	
	/**
	 * Media Panel that contains and handles media viewing
	 * 
	 * @since 2.0
	 */
	private MediaPanel mediaPanel;
	
	/**
	 * Panel for containing the media panel and controlling it's size
	 * 
	 * @since 2.0
	 */
	private JPanel mediaContainerPanel;
	
	/**
	 * Panel for containing the DMF Detail scroll pane and controlling it's size
	 * 
	 * @since 2.0
	 */
	private JPanel detailPanel;
	
	/**
	 * Panels containing vertical spacers to control the size of the media container panel
	 * 
	 * @since 2.0
	 */
	private JPanel[] verticalMediaPanels;
	
	/**
	 * Panels containing horizontal spacers to control the size of the media container panel
	 * 
	 * @since 2.0
	 */
	private JPanel[] horizontalMediaPanels;
	
	/**
	 * Panels containing vertical spacers to control the size of the DMF details panel
	 * 
	 * @since 2.0
	 */
	private JPanel[] verticalDetailPanels;
	
	/**
	 * Panels containing horizontal spacers to control the size of the DMF details panel
	 * 
	 * @since 2.0
	 */
	private JPanel[] horizontalDetailPanels;
	
	/**
	 * DEditorPane for showing DMF details
	 * 
	 * @since 2.0
	 */
	private DEditorPane detailText;
	
	/**
	 * Scroll pane for holding the detail editor pane
	 * 
	 * @since 2.0
	 */
	private DScrollPane detailScroll;
	
	/**
	 * Main progress dialog for showing when media files are being loaded.
	 * 
	 * @since 2.0
	 */
	private DProgressDialog progressDialog;

	/**
	 * Initializes the MediaViewerPanel class.
	 * 
	 * @param ownerGUI Parent GUI for the media viewer panel
	 * @param colorReference JComponent from which to get color information for CSS
	 * @since 2.0
	 */
	public MediaViewer(FrameGUI ownerGUI, JComponent colorReference)
	{
		super(ownerGUI.getSettings());
		this.ownerGUI = ownerGUI;
		
		viewerPanel = new JPanel();
		viewerPanel.setLayout(new GridLayout(1,1));
		progressDialog = new DProgressDialog(getSettings());
		
		//CREATE MEDIA CONTAINER PANEL
		mediaPanel = new MediaPanel(getSettings());
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
		detailText = new DEditorPane(ownerGUI);
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
		
		updateDetailLocation();
		
		//SET CSS VARIABLES
		StringBuilder color = new StringBuilder();
		color.append(StringMethods.extendNumberString(Integer.toHexString(colorReference.getForeground().getRed()), 2));
		color.append(StringMethods.extendNumberString(Integer.toHexString(colorReference.getForeground().getGreen()), 2));
		color.append(StringMethods.extendNumberString(Integer.toHexString(colorReference.getForeground().getBlue()), 2));
		fontColor = color.toString();
		
		color = new StringBuilder();
		color.append(StringMethods.extendNumberString(Integer.toHexString((colorReference.getBackground().getRed() + colorReference.getForeground().getRed()) / 2), 2));
		color.append(StringMethods.extendNumberString(Integer.toHexString((colorReference.getBackground().getGreen() + colorReference.getForeground().getGreen()) / 2), 2));
		color.append(StringMethods.extendNumberString(Integer.toHexString((colorReference.getBackground().getGreen() + colorReference.getForeground().getGreen()) / 2), 2));
		hyperlinkColor = color.toString();
		
		largeFontSize = (int)((double)getSettings().getFontSize() * ((double)4/(double)3));
		smallFontSize = (int)((double)getSettings().getFontSize() * ((double)3/(double)4));
		if(smallFontSize < 1)
		{
			smallFontSize = 1;
			
		}//IF
		
		borderSize = (int)((double)getSettings().getFontSize() * ((double)1/(double)10));
		if(borderSize < 1)
		{
			borderSize = 1;
		
		}//IF
		
	}//CONSTRUCTOR
	
	/**
	 * Returns the main viewer panel.
	 * 
	 * @return Viewer Panel
	 * @since 2.0
	 */
	public JPanel getViewerPanel()
	{
		return viewerPanel;
		
	}//METHOD
	
	/**
	 * Sets the spacers in the media container panel and the DMF detail panel to set their minimum sizes.
	 * 
	 * @since 2.0
	 */
	private void setMinimumSizes()
	{
		int mediaWidth = viewerPanel.getWidth() / 2;
		int mediaHeight = viewerPanel.getHeight() / 2;
		
		if(mediaWidth < 1)
		{
			mediaWidth = 1;
		}//IF
		
		if(mediaHeight < 1)
		{
			mediaHeight = 1;
			
		}//IF
		
		int detailWidth = getSettings().getFontSize() * 20;
		int detailHeight = getSettings().getFontSize() * 15;
		
		if(detailWidth > mediaWidth)
		{
			detailWidth = mediaWidth;
			
		}//IF
		
		if(detailHeight > mediaHeight)
		{
			detailHeight = mediaHeight;
			
		}//IF
		
		verticalMediaPanels[0].removeAll();
		verticalMediaPanels[1].removeAll();
		horizontalMediaPanels[0].removeAll();
		horizontalMediaPanels[1].removeAll();
		verticalDetailPanels[0].removeAll();
		verticalDetailPanels[1].removeAll();
		horizontalDetailPanels[0].removeAll();
		horizontalDetailPanels[1].removeAll();
		
		verticalMediaPanels[0].add(Box.createRigidArea(new Dimension(1, mediaHeight)));
		verticalMediaPanels[1].add(Box.createRigidArea(new Dimension(1, mediaHeight)));
		horizontalMediaPanels[0].add(Box.createRigidArea(new Dimension(mediaWidth, 1)));
		horizontalMediaPanels[1].add(Box.createRigidArea(new Dimension(mediaWidth, 1)));
		verticalDetailPanels[0].add(Box.createRigidArea(new Dimension(1, detailHeight)));
		verticalDetailPanels[1].add(Box.createRigidArea(new Dimension(1, detailHeight)));
		horizontalDetailPanels[0].add(Box.createRigidArea(new Dimension(detailWidth, 1)));
		horizontalDetailPanels[1].add(Box.createRigidArea(new Dimension(detailWidth, 1)));
		
		verticalMediaPanels[0].revalidate();
		verticalMediaPanels[1].revalidate();
		horizontalMediaPanels[0].revalidate();
		horizontalMediaPanels[1].revalidate();
		verticalDetailPanels[0].revalidate();
		verticalDetailPanels[1].revalidate();
		horizontalDetailPanels[0].revalidate();
		horizontalDetailPanels[1].revalidate();
		viewerPanel.revalidate();
		
	}//METHOD
	
	/**
	 * Updates the location of the DMF Detail panel
	 * 
	 * @since 2.0
	 */
	public void updateDetailLocation()
	{
		viewerPanel.removeAll();
		int location = getSettings().getDetailLocation();
		
		switch(location)
		{
			case TOP_DETAILS:
				viewerPanel.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, detailPanel, mediaContainerPanel));
				break;
			case BOTTOM_DETAILS:
				viewerPanel.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, mediaContainerPanel, detailPanel));
				break;
			case LEFT_DETAILS:
				viewerPanel.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, detailPanel, mediaContainerPanel));
				break;
			case RIGHT_DETAILS:
				viewerPanel.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mediaContainerPanel, detailPanel));
				break;	
			default:
				viewerPanel.add(mediaContainerPanel);
				break;
				
		}//SWITCH
		
		viewerPanel.revalidate();
		
	}//METHOD
	
	/**
	 * Sets what media file should be shown.
	 * 
	 * @param dmfIndex Index of the DMF from which to get details
	 * @since 2.0
	 */
	public void setMedia(final int dmfIndex)
	{
		this.dmfIndex = dmfIndex;
		ownerGUI.getFrame().setProcessRunning(true);
		progressDialog.setCancelled(false);
		progressDialog.startProgressDialog(ownerGUI.getFrame(), DefaultLanguage.LOADING_MEDIA_TITLE);
		progressDialog.setProcessLabel(DefaultLanguage.LOADING_MEDIA_MESSAGE);
		progressDialog.setDetailLabel(DefaultLanguage.RUNNING, true);
		progressDialog.setProgressBar(true, false, 0, 0);
		(new DSwingWorker(this, DefaultLanguage.LOADING_MEDIA_MESSAGE)).execute();
		
	}//METHOD
	
	/**
	 * Updates the current media file to reflect the current size of the viewer.
	 * 
	 * @since 2.0
	 */
	public void updateMedia()
	{
		if(!ownerGUI.getFrame().isProcessRunning())
		{
			ownerGUI.getFrame().setProcessRunning(false);
			progressDialog.setCancelled(false);
			progressDialog.startProgressDialog(ownerGUI.getFrame(), DefaultLanguage.LOADING_MEDIA_TITLE);
			progressDialog.setProcessLabel(DefaultLanguage.LOADING_MEDIA_MESSAGE);
			progressDialog.setDetailLabel(DefaultLanguage.RUNNING, true);
			progressDialog.setProgressBar(true, false, 0, 0);
			(new DSwingWorker(this, new String())).execute();
		
		}//IF
		
	}//METHOD
	
	/**
	 * Sets the details editor pane to show the information from a DMF at a given index.
	 * 
	 * @param dmfIndex DMF Index
	 * @since 2.0
	 */
	private void setDetails(final int dmfIndex)
	{
		if(getSettings().getDetailLocation() != NO_DETAILS)
		{
			//SET STYLE
			StringBuilder htmlText = new StringBuilder();
			htmlText.append("<style type=\"text/css\">body{text-align:left;font-family:"); //$NON-NLS-1$
			htmlText.append(ownerGUI.getFont().getFamily());
			htmlText.append(";font-size:"); //$NON-NLS-1$
			htmlText.append(getSettings().getFontSize());
			htmlText.append("pt;color:#"); //$NON-NLS-1$
			htmlText.append(fontColor);
			htmlText.append(";}a{color:#"); //$NON-NLS-1$
			htmlText.append(hyperlinkColor);
			htmlText.append(";}."); //$NON-NLS-1$
			htmlText.append(LARGE_TEXT_CLASS);
			htmlText.append("{font-size:"); //$NON-NLS-1$
			htmlText.append(largeFontSize);
			htmlText.append("pt}."); //$NON-NLS-1$
			htmlText.append(SMALL_TEXT_CLASS);
			htmlText.append("{font-size:"); //$NON-NLS-1$
			htmlText.append(smallFontSize);
			htmlText.append("pt}hr{border:"); //$NON-NLS-1$
			htmlText.append("px solid #"); //$NON-NLS-1$
			htmlText.append(fontColor);
			htmlText.append("}</style><body>"); //$NON-NLS-1$
			
			//SET TITLE AND ARTIST(S)
			htmlText.append("<div class=\"drakovek_title_block\"><span class=\""); //$NON-NLS-1$
			htmlText.append(LARGE_TEXT_CLASS);
			htmlText.append("\"<b>"); //$NON-NLS-1$
			htmlText.append(ownerGUI.getDmfHandler().getTitle(dmfIndex));
			htmlText.append("</b></span><br><i>"); //$NON-NLS-1$
			htmlText.append(StringMethods.arrayToString(ownerGUI.getDmfHandler().getArtists(dmfIndex), true, new String()));
			htmlText.append("</i>"); //$NON-NLS-1$
			
			if(ownerGUI.getDmfHandler().getSequenceTitle(dmfIndex).length() > 0)
			{
				htmlText.append("<br><span class=\""); //$NON-NLS-1$
				htmlText.append(SMALL_TEXT_CLASS);
				htmlText.append("\">"); //$NON-NLS-1$
				htmlText.append(ownerGUI.getDmfHandler().getSequenceTitle(dmfIndex));
				
				if(ownerGUI.getDmfHandler().getSectionTitle(dmfIndex).length() > 0 && !ownerGUI.getDmfHandler().getSectionTitle(dmfIndex).equals(DMF.EMPTY_SECTION))
				{
					htmlText.append(" - "); //$NON-NLS-1$
					htmlText.append(ownerGUI.getDmfHandler().getSectionTitle(dmfIndex));
					
				}//IF
				
				htmlText.append("</span>"); //$NON-NLS-1$
				
			}//IF
			
			//SET DESCRIPTION
			htmlText.append("</div><br><hr><br><div class=\"drakovek_description\">"); //$NON-NLS-1$
			htmlText.append(ownerGUI.getDmfHandler().getDescription(dmfIndex));
			
			//SET TAGS
			htmlText.append("</div><br><hr><br><div class=\"drakovek_info\"><b>"); //$NON-NLS-1$
			htmlText.append(getSettings().getLanguageText(DefaultLanguage.WEB_TAGS));
			htmlText.append("&nbsp; </b>"); //$NON-NLS-1$
			htmlText.append(StringMethods.arrayToString(ownerGUI.getDmfHandler().getWebTags(dmfIndex), true, getSettings().getLanguageText(DefaultLanguage.NON_APPLICABLE)));
			htmlText.append("<br><br><b>"); //$NON-NLS-1$
			htmlText.append(getSettings().getLanguageText(DefaultLanguage.USER_TAGS));
			htmlText.append("&nbsp; </b>"); //$NON-NLS-1$
			htmlText.append(StringMethods.arrayToString(ownerGUI.getDmfHandler().getUserTags(dmfIndex), true, getSettings().getLanguageText(DefaultLanguage.NON_APPLICABLE)));
			
			//SET INFO TABLE
			htmlText.append("</div><br><div class=\""); //$NON-NLS-1$
			htmlText.append(SMALL_TEXT_CLASS);
			htmlText.append("\"><table><tr><td><b>"); //$NON-NLS-1$
			htmlText.append(getSettings().getLanguageText(DefaultLanguage.DATE));
			htmlText.append("</b>&nbsp; "); //$NON-NLS-1$
			htmlText.append(TimeMethods.getDateString(getSettings(), TimeMethods.DATE_LONG, ownerGUI.getDmfHandler().getTime(dmfIndex)));
			htmlText.append("</td><td><a href=\""); //$NON-NLS-1$
			htmlText.append(ownerGUI.getDmfHandler().getPageURL(dmfIndex));
			htmlText.append("\">"); //$NON-NLS-1$
			htmlText.append(getSettings().getLanguageText(DefaultLanguage.PAGE_URL));
			htmlText.append("</a></td></tr><tr><td><b>"); //$NON-NLS-1$
			htmlText.append(getSettings().getLanguageText(DefaultLanguage.TIME));
			htmlText.append("</b>&nbsp; "); //$NON-NLS-1$
			htmlText.append(TimeMethods.getTimeString(getSettings(), ownerGUI.getDmfHandler().getTime(dmfIndex)));
			htmlText.append("</td><td><a href=\""); //$NON-NLS-1$
			htmlText.append(ownerGUI.getDmfHandler().getMediaURL(dmfIndex));
			htmlText.append("\">"); //$NON-NLS-1$
			htmlText.append(getSettings().getLanguageText(DefaultLanguage.DIRECT_URL));
			htmlText.append("</a></td></tr><tr><td><a href=\"file://"); //$NON-NLS-1$
			htmlText.append(ownerGUI.getDmfHandler().getDmfFile(dmfIndex).getAbsolutePath().replaceAll("\\\\", "\\/"));  //$NON-NLS-1$//$NON-NLS-2$
			htmlText.append("\">"); //$NON-NLS-1$
			htmlText.append(getSettings().getLanguageText(DefaultLanguage.DMF));
			htmlText.append("</a></td></tr></table></div></body>"); //$NON-NLS-1$
			
			detailText.setTextHTML(htmlText.toString());
			detailScroll.resetTopLeft();
			
		}//IF
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DResizeListener.RESIZE:
				setMinimumSizes();
				updateMedia();
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void run(String id)
	{
		switch(id)
		{
			case DefaultLanguage.LOADING_MEDIA_MESSAGE:
				setDetails(dmfIndex);
				mediaPanel.setMedia(ownerGUI.getDmfHandler().getMediaFile(dmfIndex), ownerGUI.getDmfHandler().getSecondaryFile(dmfIndex));
				break;
			default:
				mediaPanel.updateMedia();
				break;
				
		}//METHOD
		
	}//METHOD

	@Override
	public void done(String id)
	{
		ownerGUI.getFrame().setProcessRunning(false);
		progressDialog.closeProgressDialog();
		progressDialog.setCancelled(false);
		
	}//METHOD
	
}//CLASS
