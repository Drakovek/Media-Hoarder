package drakovek.hoarder.gui.swing.components;

import javax.swing.JComponent;
import javax.swing.JEditorPane;

import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.processing.StringMethods;

/**
 * Default EditorPane for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DEditorPane extends JEditorPane
{
	/**
	 * CSS class to use for elements requiring large text
	 * 
	 * @since 2.0
	 */
	public static final String LARGE_TEXT_CLASS = "drakovek_large_text"; //$NON-NLS-1$
	
	/**
	 * CSS class to use for elements requiring small text
	 * 
	 * @since 2.0
	 */
	public static final String SMALL_TEXT_CLASS = "drakovek_small_text"; //$NON-NLS-1$
	
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = 1156133161692245991L;

	/**
	 * Linked BaseGUI
	 * 
	 * @since 2.0
	 */
	private BaseGUI baseGUI;
	
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
	 * Initializes the DEditorPane class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param colorReference JComponent from which to get color information for CSS
	 * @since 2.0
	 */
	public DEditorPane(BaseGUI baseGUI, JComponent colorReference)
	{
		super();
		this.setEditable(false);
		this.setFont(baseGUI.getFont());
		this.setMargin(baseGUI.getButtonInsets());
		this.baseGUI = baseGUI;
		
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
		
		largeFontSize = (int)((double)baseGUI.getSettings().getFontSize() * ((double)4/(double)3));
		smallFontSize = (int)((double)baseGUI.getSettings().getFontSize() * ((double)3/(double)4));
		if(smallFontSize < 1)
		{
			smallFontSize = 1;
			
		}//IF
		
		borderSize = (int)((double)baseGUI.getSettings().getFontSize() * ((double)1/(double)10));
		if(borderSize < 1)
		{
			borderSize = 1;
		
		}//IF
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the text for the editor pane in HTML format.
	 * 
	 * @param text HTML text
	 * @since 2.0
	 */
	public void setTextHTML(final String text)
	{
		if(text != null)
		{
			String editedText = text;
			
			//REMOVE DOCTYPE
			int start = 0;
			int end = 0;
			while(end != -1)
			{
				end = -1;
				start = editedText.toLowerCase().indexOf("<!doctype"); //$NON-NLS-1$
				
				//IF
				if(start != -1)
				{
					end = editedText.indexOf('>', start);
					
				}//IF
			
				if(end != -1)
				{
					editedText = StringMethods.removeStringSection(editedText, start, end + 1);
				
				}//IF
				
			}//WHILE
			
			//REMOVE HTML ELEMENTS
			end = 0;
			while(end != -1)
			{
				end = -1;
				start = editedText.toLowerCase().indexOf("<html>"); //$NON-NLS-1$
				
				//IF
				if(start != -1)
				{
					end = editedText.indexOf('>', start);
					
				}//IF
			
				if(end != -1)
				{
					editedText = StringMethods.removeStringSection(editedText, start, end + 1);
				
				}//IF
				
			}//WHILE
			
			end = 0;
			while(end != -1)
			{
				end = -1;
				start = editedText.toLowerCase().indexOf("</html>"); //$NON-NLS-1$
				
				//IF
				if(start != -1)
				{
					end = editedText.indexOf('>', start);
					
				}//IF
			
				if(end != -1)
				{
					editedText = StringMethods.removeStringSection(editedText, start, end + 1);
				
				}//IF
				
			}//WHILE
			
			//REMOVE BODY ELEMENTS
			end = 0;
			while(end != -1)
			{
				end = -1;
				start = editedText.toLowerCase().indexOf("<body>"); //$NON-NLS-1$
				
				//IF
				if(start != -1)
				{
					end = editedText.indexOf('>', start);
					
				}//IF
			
				if(end != -1)
				{
					editedText = StringMethods.removeStringSection(editedText, start, end + 1);
				
				}//IF
				
			}//WHILE
			
			end = 0;
			while(end != -1)
			{
				end = -1;
				start = editedText.toLowerCase().indexOf("</body>"); //$NON-NLS-1$
				
				//IF
				if(start != -1)
				{
					end = editedText.indexOf('>', start);
					
				}//IF
			
				if(end != -1)
				{
					editedText = StringMethods.removeStringSection(editedText, start, end + 1);
				
				}//IF
				
			}//WHILE
			
			//REMOVE STYLE ELEMENTS
			end = 0;
			while(end != -1)
			{
				end = -1;
				start = editedText.toLowerCase().indexOf("<style"); //$NON-NLS-1$
				
				//IF
				if(start != -1)
				{
					end = editedText.indexOf("</style>", start); //$NON-NLS-1$
					
				}//IF
			
				if(end != -1)
				{
					end = editedText.indexOf('>', end);
					editedText = StringMethods.removeStringSection(editedText, start, end + 1);
				
				}//IF
				
			}//WHILE
			
			//SET STYLE
			this.setContentType("text/html"); //$NON-NLS-1$
			StringBuilder builder = new StringBuilder();
			builder.append("<!DOCTYPE html><html>"); //$NON-NLS-1$
			builder.append("<style type=\"text/css\">body{text-align:left;font-family:"); //$NON-NLS-1$
			builder.append(baseGUI.getFont().getFamily());
			builder.append(";font-size:"); //$NON-NLS-1$
			builder.append(baseGUI.getSettings().getFontSize());
			builder.append("pt;color:#"); //$NON-NLS-1$
			builder.append(fontColor);
			builder.append(";}a{color:#"); //$NON-NLS-1$
			builder.append(hyperlinkColor);
			builder.append(";}."); //$NON-NLS-1$
			builder.append(LARGE_TEXT_CLASS);
			builder.append("{font-size:"); //$NON-NLS-1$
			builder.append(largeFontSize);
			builder.append("pt}."); //$NON-NLS-1$
			builder.append(SMALL_TEXT_CLASS);
			builder.append("{font-size:"); //$NON-NLS-1$
			builder.append(smallFontSize);
			builder.append("pt}hr{border:"); //$NON-NLS-1$
			builder.append("px solid #"); //$NON-NLS-1$
			builder.append(fontColor);
			builder.append(";}</style><body>"); //$NON-NLS-1$
			
			//SET TEXT
			builder.append(editedText);
			builder.append("</body></html>"); //$NON-NLS-1$
			this.setText(builder.toString());
			
		}//IF
		
	}//METHOD
	
}//CLASS
