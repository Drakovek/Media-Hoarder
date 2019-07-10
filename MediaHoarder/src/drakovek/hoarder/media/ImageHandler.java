package drakovek.hoarder.media;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DSettings;

/**
 * Contains methods for loading, scaling, and saving images.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ImageHandler
{
	/**
	 * ImageInputStream used for loading animated GIFs.
	 * 
	 * @since 2.0
	 */
	private static ImageInputStream imageInputStream;
	
	/**
	 * Object for determining the file type of given files.
	 * 
	 * @since 2.0
	 */
	private FileTypeHandler fileTypeHandler;
	
	/**
	 * BufferedImage for an icon representing an audio file
	 * 
	 * @since 2.0
	 */
	private BufferedImage audioIcon;
	
	/**
	 * BufferedImage for an icon representing an image file
	 * 
	 * @since 2.0
	 */
	private BufferedImage imageIcon;
	
	/**
	 * BufferedImage for an icon representing a text file
	 * 
	 * @since 2.0
	 */
	private BufferedImage textIcon;
	
	/**
	 * BufferedImage for an icon representing a video file
	 * 
	 * @since 2.0
	 */
	private BufferedImage videoIcon;
	
	/**
	 * BufferedImage for an icon representing a file of an unknown file type
	 * 
	 * @since 2.0
	 */
	private BufferedImage unknownIcon;
	
	/**
	 * Initializes the ImageHandler class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public ImageHandler(DSettings settings)
	{
		fileTypeHandler = new FileTypeHandler(settings);
		
		//GET FILE TYPE ICONS
		audioIcon = null;
		imageIcon = null;
		textIcon = null;
		videoIcon = null;
		unknownIcon = null;
		File iconFolder = DReader.getDirectory(settings.getDataFolder(), "icons"); //$NON-NLS-1$
		if(iconFolder != null && iconFolder.isDirectory())
		{
			audioIcon = getImage(new File(iconFolder, "audio.png"), false); //$NON-NLS-1$
			imageIcon = getImage(new File(iconFolder, "image.png"), false); //$NON-NLS-1$
			textIcon = getImage(new File(iconFolder, "text.png"), false); //$NON-NLS-1$
			videoIcon = getImage(new File(iconFolder, "video.png"), false); //$NON-NLS-1$
			unknownIcon = getImage(new File(iconFolder, "unknown.png"), false); //$NON-NLS-1$
	
		}//IF
		
	}//CONSTRUCTOR

	/**
	 * Returns whether a given file is an animated GIF.
	 * 
	 * @param file Input File
	 * @return Whether inputFile is an Animated GIF
	 * @since 2.0
	 */
	public static boolean isAnimatedGif(final File file)
	{
		if(!file.getAbsolutePath().toLowerCase().endsWith(".gif")) //$NON-NLS-1$
		{
			return false;
			
		}//IF
		
		int numFrames = 0;
		ImageReader imageReader = ImageIO.getImageReadersBySuffix("GIF").next(); //$NON-NLS-1$
		imageInputStream = null;
		
		try
		{
			imageInputStream = ImageIO.createImageInputStream(file);
			imageReader.setInput(imageInputStream);  
			numFrames = imageReader.getNumImages(true);
		
		}//TRY
		catch(IOException e)
		{
			System.out.println("Failed reading file - ImageHandler.isAnimatedGif"); //$NON-NLS-1$
		
		}//CATCH (IOException e)
		finally
		{
			try
			{
				imageInputStream.close();
				
			}//TRY
			catch(IOException e)
			{
				System.out.println("Failed closing input stream - ImageHandler.isAnimatedGif"); //$NON-NLS-1$
			
			}//CATCH (IOException e)
			
		}//FINALLY
		
		return numFrames > 1;
		
	}//METHOD
	
	/**
	 * Returns an image containing an animated GIF loaded from a given file.
	 * 
	 * @param file Input File
	 * @return Image of Animated GIF
	 * @since 2.0
	 */
	public static Image getGifImage(final File file)
	{
		try
		{
			Image gifImage = (new ImageIcon(file.toURI().toURL())).getImage();
			return gifImage;
			
		}//TRY
		catch(Exception e)
		{
			System.out.println("Failed to read gif image - ImageHandler.getGifImage"); //$NON-NLS-1$
			return null;
		
		}//CATCH
		
	}//METHOD
	
	/**
	 * Returns a BufferedImage for a given file, either representing the image for an image file, or giving an icon for a non-image file.
	 * 
	 * @param file Input File
	 * @param useIcon Whether to use a file type icon if the image is not an image file or cannot be loaded
	 * @return BufferedImage representing the file
	 * @since 2.0
	 */
	public BufferedImage getImage(final File file, final boolean useIcon)
	{
		BufferedImage bufferedImage = null;
		
		if(fileTypeHandler.isImageFile(file))
		{
			try
			{
				bufferedImage = ImageIO.read(file);
				if(bufferedImage != null)
				{
					return bufferedImage;
					
				}//IF
				
			}//TRY
			catch(IOException e){}
			
		}//IF
		
		return getIcon(file);
		
	}//METHOD
	
	/**
	 * Gets the dimensions necessary to scale an image to fit inside given dimensions with a given scale type.
	 * 
	 * @param scaleType Int value indicating the type of scaling to use
	 * @param imageWidth Width of the initial image
	 * @param imageHeight Height of the initial image
	 * @param paneWidth Width of the pane to fit image within
	 * @param paneHeight Height of pane to fit image within
	 * @return Dimensions to fit image into given dimensions
	 * @since 2.0
	 */
	public static Dimension getScaleDimensions(final int scaleType, final int imageWidth, final int imageHeight, final int paneWidth, final int paneHeight)
	{
		return new Dimension(imageWidth/2, imageHeight/2);
		
	}//METHOD
	
	/**
	 * Returns an image scaled to given dimensions.
	 * 
	 * @param inputImage Image to scale
	 * @param scaleWidth Width to scale image
	 * @param scaleHeight Height to scale image
	 * @return Scaled BufferedImage
	 */
	public static BufferedImage getScaledImage(final BufferedImage inputImage, final int scaleWidth, final int scaleHeight)
	{
		int currentWidth = inputImage.getWidth();
		int currentHeight = inputImage.getHeight();
		
		if(currentWidth == scaleWidth && currentHeight == scaleHeight)
		{
			return inputImage;
			
		}//IF
		
		int newWidth = scaleWidth;
		int newHeight = scaleHeight;
		
		if(newWidth < 1)
		{
			newWidth = 1;
			
		}//IF (newWidth < 1)
		
		if(newHeight < 1)
		{
			newHeight = 1;
			
		}//IF (newHeight < 1)
		
		BufferedImage mediaImage = inputImage;
		
		while(currentWidth > newWidth * 2)
		{
			currentWidth = (int)((double)currentWidth / 2.0);
			currentHeight = (int)((double)currentHeight / 2.0);
			
			if(currentWidth < newWidth)
			{
				currentWidth = newWidth;
			
			}//IF
			
			if(currentHeight< newHeight)
			{
				currentHeight = newWidth;
				
			}//IF
			
			BufferedImage interImage = new BufferedImage(currentWidth, currentHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D interG2D = (Graphics2D)interImage.createGraphics();
			interG2D.addRenderingHints(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR));
			interG2D.drawImage(mediaImage, 0, 0, currentWidth, currentHeight, null);
			
			mediaImage = interImage;
			
		}//WHILE
		
		BufferedImage finalImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D finalG2D = (Graphics2D)finalImage.createGraphics();
		finalG2D.addRenderingHints(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC));
		finalG2D.drawImage(mediaImage, 0, 0, newWidth, newHeight, null);
		
		return finalImage;
		
	}//METHOD
	
	/**
	 * Returns a BufferedImage for an appropriate icon based on a given file's file type.
	 * 
	 * @param file Input File
	 * @return File Type Icon
	 * @since 2.0
	 */
	public BufferedImage getIcon(final File file)
	{
		if(fileTypeHandler.isAudioFile(file))
		{
			return audioIcon;
			
		}//IF
		
		if(fileTypeHandler.isImageFile(file))
		{
			return imageIcon;
			
		}//IF
		
		if(fileTypeHandler.isTextFile(file))
		{
			return textIcon;
			
		}//IF
		
		if(fileTypeHandler.isVideoFile(file))
		{
			return videoIcon;
			
		}//IF
		
		return unknownIcon;
		
	}//METHOD
	
}//CLASS