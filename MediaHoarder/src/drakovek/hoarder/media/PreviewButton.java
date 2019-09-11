package drakovek.hoarder.media;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JButton;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.gui.swing.listeners.DActionListener;
import drakovek.hoarder.gui.swing.listeners.DEvent;

/**
 * Button for showing preview thumbnails and opening DMFs
 * 
 * @author Drakovek
 * @version 2.0
 */
public class PreviewButton extends JButton
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -7234967063333386274L;
	
	/**
	 * ID of preview button being pressed
	 */
	public static final String PREVIEW_EVENT = "preview_event"; //$NON-NLS-1$

	/**
	 * Image loaded to be used as a preview
	 */
	BufferedImage image;
	
	/**
	 * ImageHandler for scaling and processing preview images
	 */
	ImageHandler imageHandler;
	
	/**
	 * Dimensions of the preview image
	 */
	Dimension imageDimension;
	
	/**
	 * Initializes the PreviewButton object.
	 * 
	 * @param event DEvent to call when the preview button is pressed
	 * @param settings Program settings
	 * @param index Index of the preview button, used as value in DEvent when button is pressed
	 */
	public PreviewButton(DEvent event, DSettings settings, final int index)
	{
		super();
		image = null;
		imageDimension = new Dimension(0,0);
		imageHandler = new ImageHandler(settings);
		this.addActionListener(new DActionListener(event, PREVIEW_EVENT, index));
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the preview image preview image.
	 * 
	 * @param mediaFile Main media file, may contain image
	 * @param secondaryFile Secondary media file in case the main file is not an image
	 * @param useIcon Whether to use an icon rather than a thumbnail
	 */
	public void setImage(final File mediaFile, final File secondaryFile, final boolean useIcon)
	{
		if(mediaFile != null)
		{
			image = imageHandler.getPreview(mediaFile, secondaryFile, useIcon);
			
		}//IF
		else
		{
			image = null;
			
		}//ELSE
		
		if(image != null)
		{
			imageDimension = new Dimension(image.getWidth(), image.getHeight());
			
		}//IF
		
		repaint();
		
	}//METHOD
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//GET IMAGE OFFSET
		int offsetX = 0;
		int offsetY = 0;
		
		if(getWidth() > imageDimension.getWidth())
		{
			offsetX = (int)(((double)getWidth() - imageDimension.getWidth()) / 2);
			
		}//IF
		
		if(getHeight() > imageDimension.getHeight())
		{
			offsetY = (int)(((double)getHeight() - imageDimension.getHeight()) / 2);
			
		}//IF
		
		if(image != null)
		{
			g.drawImage(image, offsetX, offsetY, this);
			
		}//IF
		
	}//METHOD
	
}//CLASS
