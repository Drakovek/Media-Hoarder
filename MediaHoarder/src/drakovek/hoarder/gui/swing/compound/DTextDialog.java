package drakovek.hoarder.gui.swing.compound;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DTextField;

/**
 * Contains methods to create a dialog that uses a text field as user input.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DTextDialog extends BaseGUI
{
	/**
	 * Main dialog for the GUI
	 * 
	 * @since 2.0
	 */
	private DDialog dialog;
	
	/**
	 * String to return when the dialog is closed. Will either be null or text from the user.
	 * 
	 * @since 2.0
	 */
	private String returnString;
	
	/**
	 * Text field used to get text from the user
	 * 
	 * @since 2.0
	 */
	private DTextField textField;
	
	/**
	 * Initializes the DTextDialog class
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public DTextDialog(DSettings settings)
	{
		super(settings);
		
	}//CONSTRUCTOR
	
	/**
	 * Opens the text dialog GUI.
	 * 
	 * @param owner DFrame linked to the text dialog
	 * @param titleID Language ID for the dialog title
	 * @param messageIDs Language IDs for the message to be shown.
	 * @param fieldText Text to use in the text field (sets as blank if null)
	 * @return String entered by the user
	 * @since 2.0
	 */
	public String openTextDialog(DFrame owner, final String titleID, final String[] messageIDs, final String fieldText)
	{
		owner.setAllowExit(false);
		returnString = null;
		dialog = new DDialog(owner, getDialogPanel(messageIDs, fieldText), getSettings().getLanuageText(titleID), true, getSettings().getFontSize() * getSettings().getFrameWidth(), 0);
		dialog.setVisible(true);
		dialog = null;
		owner.setAllowExit(true);
		return returnString;
		
	}//METHOD
	
	/**
	 * Returns the main panel to be used in the text dialog.
	 * 
	 * @param messageIDs Language IDs for the message to be shown.
	 * @param fieldText Text to use in the text field (sets as blank if null)
	 * @return Panel to use in the text dialog
	 * @since 2.0
	 */
	private JPanel getDialogPanel(final String[] messageIDs, final String fieldText)
	{
		//CREATE CENTER PANEL
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(messageIDs.length + 1, 1, 0, getSettings().getSpaceSize()));
		for(String messageID: messageIDs)
		{
			centerPanel.add(new DLabel(this, null, messageID));
			
		}//FOR
		
		textField = new DTextField(this, DefaultLanguage.OK);
		
		if(fieldText != null)
		{
			textField.setText(fieldText);
			
		}//IF
		
		centerPanel.add(textField);
		
		//CREATE BUTTON PANEL
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, getSettings().getSpaceSize(), 0));
		buttonPanel.add(new DButton(this, DefaultLanguage.CANCEL));
		buttonPanel.add(new DButton(this, DefaultLanguage.OK));
		
		//CREATE DIALOG PANEL
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BorderLayout());
		dialogPanel.add(this.getSpacedPanel(centerPanel, 1, 0, true, true, true, true), BorderLayout.CENTER);
		dialogPanel.add(this.getSpacedPanel(buttonPanel, 0, 0, false, true, true, true), BorderLayout.SOUTH);
		return dialogPanel;
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.OK:
			{
				returnString = textField.getText();
				
			}//CASE
			case DefaultLanguage.CANCEL:
			{
				dialog.dispose();
				break;
				
			}//CASE
			
		}//CASE
		
	}//METHOD
	
}//CLASS
