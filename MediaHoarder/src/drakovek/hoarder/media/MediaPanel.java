package drakovek.hoarder.media;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;

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
	 * ImageScrollPane for displaying images.
	 * 
	 * @since 2.0
	 */
	private ImageScrollPane imageScroll;
	
	/**
	 * Initializes the MediaPanel class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public MediaPanel(DSettings settings)
	{
		super();
		this.setLayout(new GridLayout(1, 1));
		imageScroll = new ImageScrollPane(settings);
		this.add(imageScroll);
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the current media file to show.
	 * 
	 * @param mediaFile Media file to show
	 * @param secondaryFile Secondary file to show, if applicable
	 * @since 2.0
	 */
	public void setMedia(final File mediaFile, final File secondaryFile)
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
