package drakovek.hoarder.media;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.gui.swing.components.DScrollPane;

/**
 * Scroll Pane for holding an Image Panel
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ImageScrollPane extends DScrollPane
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = -5158625448937053337L;
	
	/**
	 * ImagePanel held within the scroll pane.
	 * 
	 * @since 2.0
	 */
	private ImagePanel imagePanel;
	
	/**
	 * Initializes the ImageScrollPane with no starting image.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public ImageScrollPane(DSettings settings)
	{
		super(settings, new ImagePanel(settings));
		imagePanel = (ImagePanel)getView();
		setSizes();
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes the ImageScrollPane with an image file to use as a starting image.
	 * 
	 * @param settings Program Settings
	 * @param file File used as starting image
	 * @since 2.0
	 */
	public ImageScrollPane(DSettings settings, final File file)
	{
		super(settings, new ImagePanel(settings));
		imagePanel = (ImagePanel)getView();
		setFile(file);
		setSizes();
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the size values relevant to properly fitting an image into frame.
	 * 
	 * @since 2.0
	 */
	private void setSizes()
	{
		JFrame tempFrame = new JFrame();
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(2, 1));
		JPanel gapPanel = new JPanel();
		JScrollPane fullScroll = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		JScrollPane noScroll = new JScrollPane(gapPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		gridPanel.add(fullScroll);
		gridPanel.add(noScroll);
		tempFrame.add(gridPanel, BorderLayout.CENTER);
		tempFrame.setSize(200,200);
		tempFrame.pack();
		int gapWidth = gapPanel.getParent().getParent().getWidth() - gapPanel.getParent().getWidth();
		int gapHeight = gapPanel.getParent().getParent().getHeight() - gapPanel.getParent().getHeight();
		int scrollWidth = fullScroll.getVerticalScrollBar().getWidth();
		int scrollHeight = fullScroll.getHorizontalScrollBar().getHeight();
		imagePanel.setSizes(gapWidth, gapHeight, scrollWidth, scrollHeight);
		tempFrame.dispose();
		tempFrame = null;
		
	}//METHOD
	
	/**
	 * Sets a new image based on a given file.
	 * 
	 * @param file Input File
	 * @since 2.0
	 */
	public void setFile(final File file)
	{
		imagePanel.setFile(file);
		resetScroll();
		
	}//METHOD
	
	/**
	 * Scales the current image to fit in the scroll pane.
	 * 
	 * @since 2.0
	 */
	public void scaleImage()
	{
		imagePanel.scaleImage();
		resetScroll();
		
	}//METHOD
	
	/**
	 * Sets the scaleType and scaleAmount for the class.
	 * 
	 * @param scaleType Int value indicating the type of scaling to use
	 * @param scaleAmount Double value to multiply image size by when scaling directly
	 * @since 2.0
	 */
	public void setScale(final int scaleType, final double scaleAmount)
	{
		imagePanel.setScale(scaleType, scaleAmount);
		
	}//METHOD
	
	/**
	 * Resets the scroll pane to match the scroll bar needs of the current image.
	 * 
	 * @since 2.0
	 */
	private void resetScroll()
	{
		revalidate();
		resetBottomRight();
		resetTopLeft();
		revalidate();
		
	}//PRIVATE
	
	/**
	 * Returns the dimension of the currently displayed image.
	 * 
	 * @return Image Dimensions
	 * @since 2.0
	 */
	public Dimension getImageDimension()
	{
		return imagePanel.getImageDimension();
		
	}//METHOD
	
}//CLASS
