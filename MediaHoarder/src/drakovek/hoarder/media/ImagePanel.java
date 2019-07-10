package drakovek.hoarder.media;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.Scrollable;

import drakovek.hoarder.file.DSettings;

/**
 * Panel object used for displaying images.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ImagePanel extends JPanel implements Scrollable
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = -5627708724281375282L;

	/**
	 * Image used for holding animated GIFs
	 * 
	 * @since 2.0
	 */
	private Image gifImage;
	
	/**
	 * BufferedImage used to hold a scaled version of the image currently stored in memory
	 * 
	 * @since 2.0
	 */
	private BufferedImage scaledImage;
	
	/**
	 * BufferedImage used to hold an image at its original specified resolution.
	 * 
	 * @since 2.0
	 */
	private BufferedImage originalImage;
	
	/**
	 * Dimension of the image to be drawn to the panel
	 * 
	 * @since 2.0
	 */
	private Dimension imageDimension;
	
	/**
	 * Object to handle loading and scaling images
	 * 
	 * @since 2.0
	 */
	private ImageHandler imageHandler;
	
	/**
	 * Initializes the ImagePanel to have now image to start with.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public ImagePanel(DSettings settings)
	{
		super();
		initializeClass(settings);
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes the ImagePanel to start displaying an image from a given file.
	 * 
	 * @param settings Program Settings
	 * @param file Image file to display.
	 * @since 2.0
	 */
	public ImagePanel(DSettings settings, final File file)
	{
		super();
		initializeClass(settings);
		setFile(file);
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes all the instance variables for the class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	private void initializeClass(DSettings settings)
	{
		gifImage = null;
		scaledImage = null;
		originalImage = null;
		imageDimension = new Dimension(0, 0);
		imageHandler = new ImageHandler(settings);
		
	}//METHOD
	
	/**
	 * Sets the image of the ImagePanel based on an image file.
	 * 
	 * @param file Given Image File
	 * @since 2.0
	 */
	public void setFile(final File file)
	{
		if(ImageHandler.isAnimatedGif(file))
		{
			setGifImage(file);
			
		}//IF
		else
		{
			setImage(file);
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Sets the gifImage Image object to contain an animated GIF from a given image file.
	 * 
	 * @param file Animated GIF File
	 * @since 2.0
	 */
	private void setGifImage(final File file)
	{
		originalImage = null;
		scaledImage = null;
		gifImage = ImageHandler.getGifImage(file);
		if(gifImage != null)
		{
			imageDimension = new Dimension(gifImage.getWidth(null), gifImage.getHeight(null));
			
		}//IF
		else
		{
			imageDimension = new Dimension(0, 0);
			
		}//ELSE
		
		repaint();
		
	}//METHOD
	
	/**
	 * Sets the originalImage BufferedImage object based on an given file.
	 * 
	 * @param file Input File
	 * @since 2.0
	 */
	private void setImage(final File file)
	{
		scaledImage = null;
		gifImage = null;
		originalImage = imageHandler.getImage(file, true);
		imageDimension = new Dimension(0, 0);
		scaleImage();
		
	}//METHOD
	
	/**
	 * Sets the scaledImage BufferedImage object to be a scaled version of the originalImage object fit to the ImagePanel.
	 * 
	 * @since 2.0
	 */
	public void scaleImage()
	{
		if(originalImage != null)
		{
			Dimension scaledDimension;
			try
			{
				scaledDimension = ImageHandler.getScaleDimensions(-1, originalImage.getWidth(), originalImage.getHeight(), this.getParent().getWidth(), this.getParent().getHeight());
			
			}//TRY
			catch(NullPointerException e)
			{
				//IF PARENT DOES NOT YET EXIST
				scaledDimension = new Dimension(originalImage.getWidth(), originalImage.getHeight());
				
			}//CATCH
			
			//TEST IF IMAGE NEEDS TO BE RESCALED
			if((int)scaledDimension.getWidth() != (int)imageDimension.getWidth() || (int)scaledDimension.getHeight() != (int)imageDimension.getHeight())
			{
				//TEST IF ORIGINAL IMAGE CAN BE USED
				if((int)scaledDimension.getWidth() == originalImage.getWidth() && (int)scaledDimension.getHeight() == originalImage.getHeight())
				{
					scaledImage = originalImage;
					
				}//IF
				else
				{
					scaledImage = ImageHandler.getScaledImage(originalImage, (int)scaledDimension.getWidth(), (int)scaledDimension.getHeight());
							
				}//ELSE
				
				if(scaledImage != null)
				{
					imageDimension = new Dimension(scaledImage.getWidth(), scaledImage.getHeight());
					
				}//IF
				else
				{
					imageDimension = new Dimension(0, 0);
					
				}//ELSE
				
				repaint();
				
				
			}//IF
			
		}//IF
		
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
		
		if(scaledImage != null)
		{
			g.drawImage(scaledImage, offsetX, offsetY, this);
			
		}//IF
		else if(gifImage != null)
		{
			g.drawImage(gifImage, offsetX, offsetY, this);
			
		}//ELSE IF
		
	}//METHOD

	@Override
	public Dimension getPreferredSize()
	{
		return imageDimension;
		
	}//METHOD
	
	@Override
	public Dimension getMinimumSize()
	{
		return imageDimension;
		
	}//METHOD
	
	@Override
	public Dimension getPreferredScrollableViewportSize()
	{
		return imageDimension;
		
	}//METHOD

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		return 100;
		
	}//METHOD

	@Override
	public boolean getScrollableTracksViewportHeight()
	{
		return getPreferredSize().height <= getParent().getSize().height;
		
	}//METHOD

	@Override
	public boolean getScrollableTracksViewportWidth()
	{
		return getPreferredSize().width <= getParent().getSize().width;
		
	}//METHOD

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		return 100;
	
	}//METHOD
	
}//CLASS
