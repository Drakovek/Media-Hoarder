package drakovek.hoarder.gui;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * Deals with information regarding the size of the user's screen(s).
 * 
 * @author Drakovek
 * @version 2.0
 */
public class ScreenDimensions 
{
	/**
	 * DisplayMode for the smallest screen size being used.
	 */
	private DisplayMode smallScreen;
	
	/**
	 * DisplayMode for the largest screen size being used.
	 */
	private DisplayMode largeScreen;
	
	/**
	 * Initializes the ScreenDimensions class by getting the largest and smallest monitor sizes.
	 */
	public ScreenDimensions()
	{
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] graphicsDevices = graphicsEnvironment.getScreenDevices();
		
		DisplayMode displayMode = graphicsDevices[0].getDisplayMode();
		long small = displayMode.getWidth() & displayMode.getHeight();
		long large = displayMode.getWidth() & displayMode.getHeight();
		smallScreen = graphicsDevices[0].getDisplayMode();
		largeScreen = graphicsDevices[0].getDisplayMode();
		
		for(int i = 1; i < graphicsDevices.length; i++)
		{
			displayMode = graphicsDevices[i].getDisplayMode();
			
			if((displayMode.getWidth() * displayMode.getHeight()) < small)
			{
				small = displayMode.getWidth() * displayMode.getHeight();
				smallScreen = displayMode;
				
			}//IF
			
			if((displayMode.getWidth() * displayMode.getHeight()) > large)
			{
				large = displayMode.getWidth() * displayMode.getHeight();
				largeScreen = displayMode;
				
			}//IF
			
		}//FOR
		
	}//METHOD
	
    /**
     * Returns dimensions of the smallest screen.
     * 
     * @return Small Screen Dimensions
     */
    public Dimension getSmallScreenDimensions()
    {
    	return new Dimension(smallScreen.getWidth(), smallScreen.getHeight());
    	
    }//METHOD
    
    /**
     * Returns width of the smallest screen.
     * 
     * @return Small Screen Width
     */
    public int getSmallScreenWidth()
    {
    	return smallScreen.getWidth();
    
    }//METHOD
    
    /**
     * Returns height of the smallest screen.
     * 
     * @return Small Screen Height
     */
    public int getSmallScreenHeight()
    {
    	return smallScreen.getHeight();
    
    }//METHOD
    
    /**
     * Returns dimensions of largest screen.
     * 
     * @return Large Screen Dimensions
     */
    public Dimension getLargeScreenDimensions()
    {
    	return new Dimension(largeScreen.getWidth(), largeScreen.getHeight());
    	
    }//METHOD
    
    /**
     * Returns width of the largest screen.
     * 
     * @return Large Screen Width
     */
    public int getLargeScreenWidth()
    {
    	return largeScreen.getWidth();
    
    }//METHOD
    
    /**
     * Returns height of the largest screen.
     * 
     * @return Large Screen Height
     */
    public int getLargeScreenHeight()
    {
    	return largeScreen.getHeight();
    
    }//METHOD
    
    /**
     * Returns maximum dimensions to set a frame.
     * 
     * @return Maximum Dimensions
     */
    public Dimension getMaximumDimensions()
    {
    	int width = getSmallScreenWidth() - 100;
    	if(width < 1)
    	{
    		width = getSmallScreenWidth();
    		
    	}//IF
    	
    	int height = getSmallScreenHeight() - 100;
    	if(height < 1)
    	{
    		height = getSmallScreenHeight();
    	
    	}//IF
    	
    	return new Dimension(width, height);
    	
    }//METHOD
    
    /**
     * Returns maximum width to set a frame.
     * 
     * @return Maximum Width
     */
    public int getMaximumWidth()
    {
    	return (int)getMaximumDimensions().getWidth();
    	
    }//METHOD
    
    /**
     * Returns maximum height to set a frame.
     * 
     * @return Maximum Height
     */
    public int getMaximumHeight()
    {
    	return (int)getMaximumDimensions().getHeight();
    	
    }//METHOD
    
}//CLASS
