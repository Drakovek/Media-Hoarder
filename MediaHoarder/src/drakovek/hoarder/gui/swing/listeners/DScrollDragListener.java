package drakovek.hoarder.gui.swing.listeners;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JViewport;

import drakovek.hoarder.gui.swing.components.DScrollPane;

/**
 * Deals with the user dragging their mouse on a scroll pane to scroll.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DScrollDragListener extends MouseAdapter
{
	/**
	 * Point where a user started a drag, used as reference against the current mouse position.
	 */
	private Point start;
	
	/**
	 * Linked scroll pane
	 */
	private DScrollPane scrollPane;
	
	/**
	 * Component used in the scroll pane
	 */
	private JComponent view;
	
	/**
	 * Initializes the DScrollDragListener.
	 * 
	 * @param scrollPane Linked Scroll Pane
	 * @param view Component used in the scroll pane
	 */
	public DScrollDragListener(DScrollPane scrollPane, JComponent view)
	{
		this.scrollPane = scrollPane;
		this.view = view;
		
	}//CONSTRUCTOR
	
	@Override
	public void mousePressed(MouseEvent mouseEvent)
	{
		start = new Point(mouseEvent.getPoint());
		
    }//METHOD
	
	@Override
	public void mouseDragged(MouseEvent mouseEvent)
	{
        if (start != null)
        {
            JViewport viewPort = scrollPane.getViewport();
            if (viewPort != null)
            {
                int deltaX = start.x - mouseEvent.getX();
                int deltaY = start.y - mouseEvent.getY();

                Rectangle rectangle = viewPort.getViewRect();
                rectangle.x += deltaX;
                rectangle.y += deltaY;

                view.scrollRectToVisible(rectangle);
                
            }//IF
            
        }//IF
        
    }//METHOD
	
}//CLASS
