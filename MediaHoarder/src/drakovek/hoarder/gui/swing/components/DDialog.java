package drakovek.hoarder.gui.swing.components;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;

import drakovek.hoarder.gui.ScreenDimensions;

/**
 * Default Dialog object for the program.
 * 
 * @author Drakovek
 * @version 1.0
 * @since 1.0
 */
public class DDialog extends JDialog
{
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = 3346226802678631049L;

	/**
	 * Initializes the DDialog class.
	 * 
	 * @param ownerFrame Frame linked to the DDialog
	 * @param dialogPanel Panel contained in the DDialog
	 * @param title Title of the dialog
	 * @param modal Whether the dialog is modal (Disables the ownerFrame)
	 * @param width Desired width of the dialog. If 0, the dialog will use it's default value
	 * @param height Desired height of the dialog. If 0, the dialog will use it's default value
	 * @since 2.0
	 */
	public DDialog(DFrame ownerFrame, JPanel dialogPanel, final String title, final boolean modal, final int width, final int height)
	{
		super(ownerFrame, title, modal);
		initializeDialog(dialogPanel, width, height);
		this.setLocationRelativeTo(ownerFrame);
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes the DDialog class.
	 * 
	 * @param ownerDialog Dialog linked to the DDialog
	 * @param dialogPanel Panel contained in the DDialog
	 * @param title Title of the dialog
	 * @param modal Whether the dialog is modal (Disables the ownerDialog)
	 * @param width Desired width of the dialog. If 0, the dialog will use it's default value
	 * @param height Desired height of the dialog. If 0, the dialog will use it's default value
	 * @since 2.0
	 */
	public DDialog(DDialog ownerDialog, JPanel dialogPanel, final String title, final boolean modal, final int width, final int height)
	{
		super(ownerDialog, title, modal);
		initializeDialog(dialogPanel, width, height);
		this.setLocationRelativeTo(ownerDialog);
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes the dialog.
	 * 
	 * @param dialogPanel Panel contained in the DDialog
	 * @param width Desired width of the dialog. If 0, the dialog will use it's default value
	 * @param height Desired height of the dialog. If 0, the dialog will use it's default value
	 * @since 2.0
	 */
	private void initializeDialog(JPanel dialogPanel, final int width, final int height)
	{
		//ADD PANEL
		this.getContentPane().add(dialogPanel, BorderLayout.CENTER);
				
		//SET MINIMUM DIMENSIONS
		int frameHeight;
		int frameWidth;
		
		this.pack();
		
		if(width == 0)
		{
			frameWidth = this.getWidth() + 5;
			
		}//IF
		else
		{
			frameWidth = width;
			
		}//ELSE
				
		if(height == 0)
		{
			frameHeight = this.getHeight() + 5;
			
		}//IF
		else
		{
			frameHeight = height;
			
		}//ELSE
		
		ScreenDimensions screen = new ScreenDimensions();
		
		if(frameWidth > screen.getMaximumWidth())
		{
			frameWidth = screen.getMaximumWidth();
			
		}//IF
		
		if(frameHeight > screen.getMaximumHeight())
		{
			frameHeight = screen.getMaximumHeight();
			
		}//IF
		
		this.setPreferredSize(new Dimension(frameWidth, frameHeight));
		this.setMinimumSize(new Dimension(frameWidth, frameHeight));
		this.pack();
		
		//FINALIZE
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
	}//METHOD

}//CLASS
