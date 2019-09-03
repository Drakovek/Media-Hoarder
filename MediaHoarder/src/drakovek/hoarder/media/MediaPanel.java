package drakovek.hoarder.media;

import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.DEditorPane;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.components.DTextArea;
import drakovek.hoarder.processing.ExtensionMethods;

/**
 * Panel for showing contents of media files.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class MediaPanel extends JPanel
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = 7128125548135572761L;
	
	/**
	 * Value indicating a given file is an image file
	 * 
	 * @since 2.0
	 */
	private static final int IMAGE = 0;
	
	/**
	 * Value indicating a given file is a text file
	 * 
	 * @since 2.0
	 */
	private static final int TEXT = 1;
	
	/**
	 * Value indicating a given file is an HTML file
	 * 
	 * @since 2.0
	 */
	private static final int HTML = 2;
	
	/**
	 * Value of the currently loaded media type
	 * 
	 * @since 2.0
	 */
	private int mediaType;
	
	/**
	 * ImageScrollPane for displaying images.
	 * 
	 * @since 2.0
	 */
	private ImageScrollPane imageScroll;
	
	/**
	 * TextArea for displaying text files
	 * 
	 * @since 2.0
	 */
	private DTextArea textArea;
	
	/**
	 * Scroll pane for holding the main text area
	 * 
	 * @since 2.0
	 */
	private DScrollPane textScroll;
	
	/**
	 * EditorPane for displaying HTML and rich text files
	 * 
	 * @since 2.0
	 */
	private DEditorPane editorPane;
	
	/**
	 * Scroll pane for holding the main editor pane
	 * 
	 * @since 2.0
	 */
	private DScrollPane editorScroll;
	
	/**
	 * Initializes the MediaPanel class.
	 * 
	 * @param baseGUI Linked BaseGUI
	 * @param colorReference JComponent from which to get color information for CSS
	 * @since 2.0
	 */
	public MediaPanel(BaseGUI baseGUI, JComponent colorReference)
	{
		super();
		mediaType = -1;
		this.setLayout(new GridLayout(1, 1));;
		
		imageScroll = new ImageScrollPane(baseGUI.getSettings());
		textArea = new DTextArea(baseGUI);
		textScroll = new DScrollPane(baseGUI.getSettings(), textArea);
		editorPane = new DEditorPane(baseGUI, colorReference);
		editorScroll = new DScrollPane(baseGUI.getSettings(), editorPane);
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the current media file to show.
	 * 
	 * @param mediaFile Media file to show
	 * @since 2.0
	 */
	public void setMedia(final File mediaFile)
	{
		int type = getMediaType(mediaFile);
		switch(type)
		{
			case TEXT:
				setText(mediaFile);
				break;
			case HTML:
				setHTML(mediaFile);
				break;
			default:
				setImage(mediaFile);
				break;
				
		}//METHOD
		
	}//METHOD
	
	/**
	 * Sets the current panel type to match the panel needed for displaying the given media file.
	 * 
	 * @param mediaFile Given Media File
	 * @since 2.0
	 */
	public void setPanelType(final File mediaFile)
	{
		int type = getMediaType(mediaFile);
		if(mediaType != type)
		{
			mediaType = type;
			this.removeAll();
			switch(type)
			{
				case TEXT:	
					this.add(textScroll);
					break;
				case HTML:
					this.add(editorScroll);
					break;
				default:
					this.add(imageScroll);
					break;
				
			}//SWITCH
			
		}//IF
		
	}//METHOD
	
	/**
	 * Returns the int value of the media type for a given file.
	 * 
	 * @param mediaFile Given File
	 * @return Integer Media Type Value
	 * @since 2.0
	 */
	private static int getMediaType(final File mediaFile)
	{
		int type = -1;
		String extension = ExtensionMethods.getExtension(mediaFile);
		switch(extension)
		{
			case ".txt": //$NON-NLS-1$
				type = TEXT;
				break;
			case ".htm": //$NON-NLS-1$
			case ".html": //$NON-NLS-1$
				type = HTML;
				break;
			default:
				type = IMAGE;
				break;
				
		}//SWITCH
		
		return type;
		
	}//METHOD
	
	/**
	 * Displays a given text file.
	 * 
	 * @param mediaFile Given Text File
	 * @since 2.0
	 */
	private void setText(final File mediaFile)
	{
		StringBuilder builder = new StringBuilder();
		ArrayList<String> text = DReader.readFile(mediaFile);
		for(int i = 0; i < text.size(); i++)
		{
			builder.append(text.get(i));
			builder.append('\n');
			builder.append('\r');
			
		}//FOR
		
		textArea.setText(builder.toString());
		textScroll.resetTopLeft();
		
	}//METHOD
	
	/**
	 * Displays a given HTML file.
	 * 
	 * @param mediaFile Given Text File
	 * @since 2.0
	 */
	private void setHTML(final File mediaFile)
	{
		StringBuilder builder = new StringBuilder();
		ArrayList<String> text = DReader.readFile(mediaFile);
		for(int i = 0; i < text.size(); i++)
		{
			builder.append(text.get(i));
			
		}//FOR
		
		editorPane.setTextHTML(builder.toString());
		editorScroll.resetTopLeft();
		
	}//METHOD
	
	/**
	 * Displays a given image file.
	 * 
	 * @param mediaFile Given Text File
	 * @since 2.0
	 */
	private void setImage(final File mediaFile)
	{
		imageScroll.setFile(mediaFile);
		
	}//METHOD
	
	/**
	 * Updates the current media, mainly to adjust for current size of the panel.
	 * 
	 * @since 2.0
	 */
	public void updateMedia()
	{
		imageScroll.scaleImage();
		
	}//METHOD
	
}//CLASS
