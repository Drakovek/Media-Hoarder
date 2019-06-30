package drakovek.hoarder.gui.swing.components;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * Extension of JPanel that can be used in a scroll pane while keeping the viewport limited to the width and/or height of its container.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DScrollablePanel extends JPanel implements Scrollable
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = 3544103412588453998L;

	/**
	 * Whether to limit the width to the container's width
	 * 
	 * @since 2.0
	 */
	private boolean fitWidth;
	
	/**
	 * Whether to limit the height to the container's height
	 * 
	 * @since 2.0
	 */
	private boolean fitHeight;
	
	/**
	 * The preferred size of the scroll pane viewport
	 * 
	 * @since 2.0
	 */
	private Dimension preferredViewPortSize;
	
	/**
	 * Initializes the DScrollablePanel class.
	 * 
	 * @param panel Panel to contain, which will fill the Scrollable panel.
	 * @param fitWidth Whether to limit the width to the container's width
	 * @param fitHeight Whether to limit the height to the container's height
	 * @since 2.0
	 */
	public DScrollablePanel(JPanel panel, final boolean fitWidth, final boolean fitHeight)
	{
		super();
		this.fitWidth = fitWidth;
		this.fitHeight = fitHeight;
		this.preferredViewPortSize = panel.getPreferredSize();
		this.setLayout(new GridLayout(1, 1));
		this.add(panel);
		
	}//CONSTRUCTOR
	
	@Override
	public Dimension getPreferredScrollableViewportSize()
	{
		return preferredViewPortSize;
		
	}//METHOD

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2)
	{
		return 0;
		
	}//METHOD

	@Override
	public boolean getScrollableTracksViewportHeight()
	{
		return fitHeight;
		
	}//METHOD

	@Override
	public boolean getScrollableTracksViewportWidth()
	{
		return fitWidth;
		
	}//METHOD

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2)
	{
		return 0;
		
	}//METHOD

}//CLASS
