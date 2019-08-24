package drakovek.hoarder.media;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import drakovek.hoarder.file.dmf.DMF;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.DEditorPane;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.processing.TimeMethods;

/**
 * Panel for showing media and DMF info.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class MediaViewerPanel extends JPanel
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = 2605245964359429769L;

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
	 * Parent GUI for the media viewer panel
	 * 
	 * @since 2.0
	 */
	private FrameGUI ownerGUI;
	
	/**
	 * DEditorPane for showing DMF information
	 * 
	 * @since 2.0
	 */
	private DEditorPane infoText;
	
	/**
	 * Scroll pane for holding the info editor pane
	 * 
	 * @since 2.0
	 */
	private DScrollPane infoScroll;

	/**
	 * Initializes the MediaViewerPanel class.
	 * 
	 * @param ownerGUI Parent GUI for the media viewer panel
	 * @param colorReference JComponent from which to get color information for CSS
	 * @since 2.0
	 */
	public MediaViewerPanel(FrameGUI ownerGUI, JComponent colorReference)
	{
		super();
		this.ownerGUI = ownerGUI;
		this.setLayout(new GridLayout(1,1));
		infoText = new DEditorPane(ownerGUI);
		infoScroll = new DScrollPane(ownerGUI.getSettings(), infoText);
		this.add(infoScroll);
		
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
		
		largeFontSize = (int)((double)ownerGUI.getSettings().getFontSize() * ((double)4/(double)3));
		smallFontSize = (int)((double)ownerGUI.getSettings().getFontSize() * ((double)3/(double)4));
		if(smallFontSize < 1)
		{
			smallFontSize = 1;
			
		}//IF
		
		borderSize = (int)((double)ownerGUI.getSettings().getFontSize() * ((double)1/(double)10));
		if(borderSize < 1)
		{
			borderSize = 1;
		
		}//IF
		
	}//CONSTRUCTOR
	
	/**
	 * Sets what media file should be shown.
	 * 
	 * @param dmfIndex Index of the DMF from which to get info
	 * @since 2.0
	 */
	public void setMedia(final int dmfIndex)
	{
		setInfo(dmfIndex);
		
	}//METHOD
	
	/**
	 * Sets the info editor pane to show the information from a DMF at a given index.
	 * 
	 * @param dmfIndex DMF Index
	 * @since 2.0
	 */
	private void setInfo(final int dmfIndex)
	{
		//SET STYLE
		StringBuilder htmlText = new StringBuilder();
		htmlText.append("<style type=\"text/css\">body{text-align:left;font-family:"); //$NON-NLS-1$
		htmlText.append(ownerGUI.getFont().getFamily());
		htmlText.append(";font-size:"); //$NON-NLS-1$
		htmlText.append(ownerGUI.getSettings().getFontSize());
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
		htmlText.append(ownerGUI.getSettings().getLanguageText(DefaultLanguage.WEB_TAGS));
		htmlText.append("&nbsp; </b>"); //$NON-NLS-1$
		htmlText.append(StringMethods.arrayToString(ownerGUI.getDmfHandler().getWebTags(dmfIndex), true, ownerGUI.getSettings().getLanguageText(DefaultLanguage.NON_APPLICABLE)));
		htmlText.append("<br><br><b>"); //$NON-NLS-1$
		htmlText.append(ownerGUI.getSettings().getLanguageText(DefaultLanguage.USER_TAGS));
		htmlText.append("&nbsp; </b>"); //$NON-NLS-1$
		htmlText.append(StringMethods.arrayToString(ownerGUI.getDmfHandler().getUserTags(dmfIndex), true, ownerGUI.getSettings().getLanguageText(DefaultLanguage.NON_APPLICABLE)));
		
		//SET INFO TABLE
		htmlText.append("</div><br><div class=\""); //$NON-NLS-1$
		htmlText.append(SMALL_TEXT_CLASS);
		htmlText.append("\"><table><tr><td><b>"); //$NON-NLS-1$
		htmlText.append(ownerGUI.getSettings().getLanguageText(DefaultLanguage.DATE));
		htmlText.append("</b>&nbsp; "); //$NON-NLS-1$
		htmlText.append(TimeMethods.getDateString(ownerGUI.getSettings(), TimeMethods.DATE_LONG, ownerGUI.getDmfHandler().getTime(dmfIndex)));
		htmlText.append("</td><td><a href=\""); //$NON-NLS-1$
		htmlText.append(ownerGUI.getDmfHandler().getPageURL(dmfIndex));
		htmlText.append("\">"); //$NON-NLS-1$
		htmlText.append(ownerGUI.getSettings().getLanguageText(DefaultLanguage.PAGE_URL));
		htmlText.append("</a></td></tr><tr><td><b>"); //$NON-NLS-1$
		htmlText.append(ownerGUI.getSettings().getLanguageText(DefaultLanguage.TIME));
		htmlText.append("</b>&nbsp; "); //$NON-NLS-1$
		htmlText.append(TimeMethods.getTimeString(ownerGUI.getSettings(), ownerGUI.getDmfHandler().getTime(dmfIndex)));
		htmlText.append("</td><td><a href=\""); //$NON-NLS-1$
		htmlText.append(ownerGUI.getDmfHandler().getMediaURL(dmfIndex));
		htmlText.append("\">"); //$NON-NLS-1$
		htmlText.append(ownerGUI.getSettings().getLanguageText(DefaultLanguage.DIRECT_URL));
		htmlText.append("</a></td></tr><tr><td><a href=\"file://"); //$NON-NLS-1$
		htmlText.append(ownerGUI.getDmfHandler().getDmfFile(dmfIndex).getAbsolutePath().replaceAll("\\\\", "\\/"));  //$NON-NLS-1$//$NON-NLS-2$
		htmlText.append("\">"); //$NON-NLS-1$
		htmlText.append(ownerGUI.getSettings().getLanguageText(DefaultLanguage.DMF));
		htmlText.append("</a></td></tr></table></div></body>"); //$NON-NLS-1$
		
		infoText.setTextHTML(htmlText.toString());
		infoScroll.resetTopLeft();
		
	}//METHOD
	
}//CLASS
