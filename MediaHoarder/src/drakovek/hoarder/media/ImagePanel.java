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
 */
public class ImagePanel extends JPanel implements Scrollable
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -5627708724281375282L;

	/**
	 * Image used for holding animated GIFs
	 */
	private Image gifImage;
	
	/**
	 * BufferedImage used to hold a scaled version of the image currently stored in memory
	 */
	private BufferedImage scaledImage;
	
	/**
	 * BufferedImage used to hold an image at its original specified resolution.
	 */
	private BufferedImage originalImage;
	
	/**
	 * Dimension of the image to be drawn to the panel
	 */
	private Dimension imageDimension;
	
	/**
	 * Object to handle loading and scaling images
	 */
	private ImageHandler imageHandler;
	
	/**
	 * Width of the gap between the panel and its parent scroll pane
	 */
	private int gapWidth;
	
	/**
	 * Height of the gap between the panel and its parent scroll pane
	 */
	private int gapHeight;
	
	/**
	 * Width of a vertical scroll bar
	 */
	private int scrollWidth;
	
	/**
	 * Height of a horizontal scroll bar
	 */
	private int scrollHeight;
	
	/**
	 * Int value indicating the type of scaling to use
	 */
	private int scaleType;
	
	/**
	 * Double value to multiply image size by when scaling directly
	 */
	private double scaleAmount;
	
	/**
	 * Initializes the ImagePanel to have now image to start with.
	 * 
	 * @param settings Program Settings
	 */
	public ImagePanel(DSettings settings)
	{
		super();
		gifImage = null;
		scaledImage = null;
		originalImage = null;
		imageDimension = new Dimension(0, 0);
		imageHandler = new ImageHandler(settings);	
		gapWidth = 0;
		gapHeight = 0;
		scrollWidth = 0;
		scrollHeight = 0;
		scaleType = -1;
		scaleAmount = -1;
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the size values relevant to properly fitting an image into frame.
	 * 
	 * @param gapWidth Width of the gap between the panel and its parent scroll pane
	 * @param gapHeight Height of the gap between the panel and its parent scroll pane
	 * @param scrollWidth Width of a vertical scroll bar
	 * @param scrollHeight Height of a horizontal scroll bar
	 */
	public void setSizes(final int gapWidth, final int gapHeight, final int scrollWidth, final int scrollHeight)
	{
		this.gapWidth = gapWidth;
		this.gapHeight = gapHeight;
		this.scrollWidth = scrollWidth;
		this.scrollHeight = scrollHeight;
		
	}//METHOD
	
	/**
	 * Sets the scaleType and scaleAmount for the class.
	 * 
	 * @param scaleType Int value indicating the type of scaling to use
	 * @param scaleAmount Double value to multiply image size by when scaling directly
	 */
	public void setScale(final int scaleType, final double scaleAmount)
	{
		this.scaleType = scaleType;
		this.scaleAmount = scaleAmount;
		
	}//METHOD
	
	/**
	 * Sets the image of the ImagePanel based on an image file.
	 * 
	 * @param file Given Image File
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
	 */
	private void setImage(final File file)
	{
		scaledImage = null;
		gifImage = null;
		originalImage = imageHandler.getImage(file, true, false);
		imageDimension = new Dimension(0, 0);
		scaleImage();
		
	}//METHOD
	
	/**
	 * Sets the scaledImage BufferedImage object to be a scaled version of the originalImage object fit to the ImagePanel.
	 */
	public void scaleImage()
	{
		if(originalImage != null)
		{
			Dimension scaledDimension;
			try
			{
				scaledDimension = imageHandler.getScaleDimensions(scaleType, scaleAmount, originalImage.getWidth(), originalImage.getHeight(), getParent().getParent().getWidth() - gapWidth, getParent().getParent().getHeight() - gapHeight, scrollWidth, scrollHeight);
			
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
	
	/**
	 * Returns the dimension of the currently displayed image.
	 * 
	 * @return Image Dimensions
	 */
	public Dimension getImageDimension()
	{
		return imageDimension;
		
	}//METHOD
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//GET IMAGE OFFSET
		int offsetX = 0;
		int offsetY = 0;
		
		if(getParent().getWidth() > imageDimension.getWidth())
		{
			offsetX = (int)(((double)getParent().getWidth() - imageDimension.getWidth()) / 2);
			
		}//IF
		
		if(getParent().getHeight() > imageDimension.getHeight())
		{
			offsetY = (int)(((double)getParent().getHeight() - imageDimension.getHeight()) / 2);
			
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
