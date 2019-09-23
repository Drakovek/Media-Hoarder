package drakovek.hoarder.gui.swing.compound;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;

import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.FileOpener;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.ViewerValues;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DLabel;

/**
 * Class used for showing the value of a hyperlink when clicked.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DHyperlinkDialog extends BaseGUI
{
	/**
	 * Main hyperlink dialog
	 */
	DDialog dialog;
	
	/**
	 * The currently displayed URL
	 */
	String currentURL;
	
	/**
	 * The main dialog panel
	 */
	JPanel panel;
	
	/**
	 * Label for displaying the current URL
	 */
	DLabel text;
	
	/**
	 * Initializes the DHyperLinkDialog class.
	 * 
	 * @param settings Program Settings
	 */
	public DHyperlinkDialog(DSettings settings)
	{
		super(settings);
		
		//CREATE BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3, settings.getSpaceSize(), 0));
		bottomPanel.add(new DButton(this, CommonValues.COPY));
		bottomPanel.add(new DButton(this, CommonValues.OPEN));
		bottomPanel.add(new DButton(this, CommonValues.CLOSE));
		
		//CREATE CENTER PANEL
		text = new DLabel(this, null, new String());
		
		//FINALIZE PANEL
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(getSpacedPanel(bottomPanel, 1, 0, false, true, true, true), BorderLayout.SOUTH);
		panel.add(this.getSpacedPanel(text, 1, 0, true, true, true, true), BorderLayout.CENTER);
		
	}//CONSTRUCTOR
	
	/**
	 * Opens the hyperlink dialog.
	 * 
	 * @param owner Parent frame of the dialog
	 * @param url URL to display
	 */
	public void openDialog(DFrame owner, final String url)
	{
		this.currentURL = url;
		text.setText(url);
		owner.setAllowExit(false);
		dialog = new DDialog(owner, panel, getSettings().getLanguageText(ViewerValues.HYPERLINK_TITLE), true, 0, 0);
		dialog.setVisible(true);
		owner.setAllowExit(true);
		
	}//METHOD
	
	/**
	 * Attempts to copy the current URL into the desktop clipboard.
	 */
	private void copyURL()
	{
		try
		{
			StringSelection selection = new StringSelection(currentURL);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, null);
		
		}//TRY
		catch(Exception e)
		{
			System.out.println("Failed to open clipboard - DHyperlinkDialog.copyURL"); //$NON-NLS-1$
		
		}//CATCH (IllegalStateException e)
		
	}//METHOD
	
	/**
	 * Attempts to open the current URL in an external program.
	 */
	private void openURL()
	{
		if(currentURL.toLowerCase().startsWith("file:")) //$NON-NLS-1$
		{
			File file = new File(currentURL.substring(("file:").length())); //$NON-NLS-1$
			FileOpener.openFile(file);
			
		}//IF
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case CommonValues.COPY:
				copyURL();
				break;
			case CommonValues.OPEN:
				openURL();
				break;
			case CommonValues.CLOSE:
				dialog.dispose();
				break;
				
		}//SWITCH
		
	}//METHOD

}//CLASS
