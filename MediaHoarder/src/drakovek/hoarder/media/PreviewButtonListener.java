package drakovek.hoarder.media;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import drakovek.hoarder.gui.swing.listeners.DEvent;

/**
 * Action Listener for a preview button.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class PreviewButtonListener implements ActionListener
{
	/**
	 * Action id for a preview button being pressed
	 * 
	 * @since 2.0
	 */
	public static final String PREVIEW_BUTTON_ACTION = "preview_button"; //$NON-NLS-1$
	
	/**
	 * DEvent to call when preview button is pressed
	 * 
	 * @since 2.0
	 */
	DEvent event;
	
	/**
	 * Index of the preview button
	 * 
	 * @since 2.0
	 */
	int index;
	
	/**
	 * Initializes the PreviewButtonListener class.
	 * 
	 * @param event DEvent to call when preview button is pressed.
	 * @param index Index of the preview button
	 * @since 2.0
	 */
	public PreviewButtonListener(DEvent event, final int index)
	{
		this.event = event;
		this.index = index;
		
	}//CONSTRUCTOR
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		event.event(PREVIEW_BUTTON_ACTION, index);
		
	}//METHOD

}//CLASS
