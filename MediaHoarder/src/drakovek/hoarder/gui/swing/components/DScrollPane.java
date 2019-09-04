package drakovek.hoarder.gui.swing.components;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.gui.swing.listeners.DScrollDragListener;
import drakovek.hoarder.work.DRunnable;
import drakovek.hoarder.work.DWorker;

/**
 * Default scroll pane object for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 1.0
 */
public class DScrollPane extends JScrollPane implements DWorker
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = -3855985632736412421L;
	
	/**
	 * Work ID for scrolling to the top left corner of the scroll pane
	 * 
	 * @since 2.0
	 */
	private static final String TOP_LEFT = "top_left"; //$NON-NLS-1$
	
	/**
	 * Work ID for scrolling to the top right corner of the scroll pane
	 * 
	 * @since 2.0
	 */
	private static final String TOP_RIGHT = "top_right"; //$NON-NLS-1$
	
	/**
	 * Work ID for scrolling to the bottom left corner of the scroll pane
	 * 
	 * @since 2.0
	 */
	private static final String BOTTOM_LEFT = "bottom_left"; //$NON-NLS-1$
	
	/**
	 * Work ID for scrolling to the bottom right corner of the scroll pane
	 * 
	 * @since 2.0
	 */
	private static final String BOTTOM_RIGHT = "bottom_right"; //$NON-NLS-1$

	/**
	 * Program Settings
	 * 
	 * @since 2.0
	 */
	private DSettings settings;
	
	/**
	 * Component used in the scroll pane's viewport.
	 * 
	 * @since 2.0
	 */
	private JComponent view;
	
	/**
	 * Initializes the scroll pane
	 * 
	 * @param settings Program Settings
	 * @param view Component to show in the viewport.
	 * @since 2.0
	 */
	public DScrollPane(DSettings settings, JComponent view)
	{
		super(view);
		this.settings = settings;
		this.view = view;
		setScrollUnit();
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes the scroll pane
	 * 
	 * @param settings Program Settings
	 * @param hsbPolicy Horizontal Scroll Bar Policy
	 * @param vsbPolicy Vertical Scroll Bar Policy
	 * @param view Component to show in the viewport.
	 * @since 2.0
	 */
	public DScrollPane(DSettings settings, int hsbPolicy, int vsbPolicy, JComponent view)
	{
		super(view, vsbPolicy, hsbPolicy);
		this.settings = settings;
		this.view = view;
		setScrollUnit();
		
	}//CONSTRUCTOR
	
	/**
	 * Adds DScrollDragListener so the user can scroll by dragging the mouse
	 * 
	 * @since 2.0
	 */
	public void addDragListener()
	{
		view.setAutoscrolls(true);
		DScrollDragListener dragListener = new DScrollDragListener(this, view);
		view.addMouseListener(dragListener);
		view.addMouseMotionListener(dragListener);
		
	}//METHOD
	
	/**
	 * Sets the scroll increment for this scroll pane.
	 * 
	 * @since 2.0
	 */
	private void setScrollUnit()
	{
		this.getHorizontalScrollBar().setUnitIncrement(settings.getScrollUnit());
		this.getVerticalScrollBar().setUnitIncrement(settings.getScrollUnit());
		
	}//METHOD setScrollUnit()
	
	/**
	 * Returns the component used in the scroll pane's viewport.
	 * 
	 * @return Viewport Component
	 * @since 2.0
	 */
	public Component getView()
	{
		return view;
		
	}//METHOD
	
	/**
	 * Sets the Scroll Pane to the top left.
	 * 
	 * @since 2.0
	 */
	public void resetTopLeft()
	{
		SwingUtilities.invokeLater(new DRunnable(this, TOP_LEFT));
	
	}//METHOD
	
	/**
	 * Sets the Scroll Pane to the top right.
	 * 
	 * @since 2.0
	 */
	public void resetTopRight()
	{
		SwingUtilities.invokeLater(new DRunnable(this, TOP_RIGHT));
	
	}//METHOD
	
	/**
	 * Sets the Scroll Pane to the bottom left.
	 * 
	 * @since 2.0
	 */
	public void resetBottomLeft()
	{
		SwingUtilities.invokeLater(new DRunnable(this, BOTTOM_LEFT));
	
	}//METHOD
	
	/**
	 * Sets the Scroll Pane to the bottom right.
	 * 
	 * @since 2.0
	 */
	public void resetBottomRight()
	{
		SwingUtilities.invokeLater(new DRunnable(this, BOTTOM_RIGHT));
	
	}//METHOD
	
	@Override
	public void run(final String id)
	{
		switch(id)
		{
			case TOP_LEFT:
				getHorizontalScrollBar().setValue(getHorizontalScrollBar().getMinimum());
				getVerticalScrollBar().setValue(getVerticalScrollBar().getMinimum());
				break;
			case TOP_RIGHT:
				getHorizontalScrollBar().setValue(getHorizontalScrollBar().getMaximum());
				getVerticalScrollBar().setValue(getVerticalScrollBar().getMinimum());
				break;
			case BOTTOM_LEFT:
				getHorizontalScrollBar().setValue(getHorizontalScrollBar().getMinimum());
				getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
				break;
			case BOTTOM_RIGHT:
				getHorizontalScrollBar().setValue(getHorizontalScrollBar().getMaximum());
				getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void done(String id){}
	
}//CLASS
