package drakovek.hoarder.gui.swing.compound;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.ComponentDisabler;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DLabel;

/**
 * Contains methods for creating a dialog with user given buttons as inputs.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DButtonDialog extends BaseGUI
{
	
	/**
	 * Main Button Dialog
	 * 
	 * @since 2.0
	 */
	private DDialog dialog;
	
	/**
	 * String to return when button is pressed.
	 * 
	 * @since 2.0
	 */
	private String returnString;
	
	/**
	 * Initializes DButtonDialog Class
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public DButtonDialog(DSettings settings)
	{
		super(settings);
		
	}//CONSTRUCTOR

	/**
	 * Creates a dialog with buttons as inputs.
	 * 
	 * @param owner DFrame parent of the button dialog
	 * @param titleID Language ID for the dialog Title
	 * @param messageIDs Language IDs for the dialog message(s)
	 * @param buttonIDs Language IDs for the dialog buttons
	 * @return Language ID from buttonIDs corresponding to the button pressed by the user
	 * @since 2.0
	 */
	public String openButtonDialog(DFrame owner, final String titleID, final String[] messageIDs, final String[] buttonIDs)
	{
		returnString = null;
		owner.setAllowExit(false);
    	dialog = new DDialog(owner, getDialogPanel(messageIDs, buttonIDs), getSettings().getLanuageText(titleID), true, 0, 0);
    	dialog.setVisible(true);
    	dialog = null;
    	owner.setAllowExit(true);
    	return returnString;
	
	}//METHOD
	
	/**
	 * Creates a dialog with buttons as inputs.
	 * 
	 * @param disabler Object with components to disable
	 * @param owner DDialog parent of the button dialog
	 * @param titleID Language ID for the dialog Title
	 * @param messageIDs Language IDs for the dialog message(s)
	 * @param buttonIDs Language IDs for the dialog buttons
	 * @return Language ID from buttonIDs corresponding to the button pressed by the user
	 * @since 2.0
	 */
	public String openButtonDialog(ComponentDisabler disabler, DDialog owner, final String titleID, final String[] messageIDs, final String[] buttonIDs)
	{
		returnString = null;
		disabler.disableAll();
    	dialog = new DDialog(owner, getDialogPanel(messageIDs, buttonIDs), getSettings().getLanuageText(titleID), true, 0, 0);
    	dialog.setVisible(true);
    	dialog = null;
    	disabler.enableAll();
    	return returnString;
	
	}//METHOD
	
    /**
     * Creates and returns the main panel for the button dialog.
     * 
     * @param messageIDs IDs for the dialog's messages
     * @param buttonIDs IDs for the dialog's buttons
     * @return JPanel for the dialog.
     * @since 1.0
     */
    private JPanel getDialogPanel(final String[] messageIDs, final String[] buttonIDs)
    {	
    	//CREATE MESSAGE PANEL
    	JPanel internalMessagePNL = new JPanel();
    	internalMessagePNL.setLayout(new BoxLayout(internalMessagePNL, BoxLayout.Y_AXIS));
    	for(int i = 0; i < messageIDs.length; i++)
    	{
    		DLabel messageLBL = new DLabel(this, null, messageIDs[i]);
    		messageLBL.setAlignmentX(Component.CENTER_ALIGNMENT);
    		
    		if(i > 0)
    		{
    			internalMessagePNL.add(getVerticalSpace());
    		
    		}//IF
    		
    		internalMessagePNL.add(messageLBL);
    		
    	}//FOR
    	
    	JPanel messagePNL = new JPanel();
    	messagePNL.setLayout(new GridBagLayout());
    	GridBagConstraints messageCST = new GridBagConstraints();
    	messageCST.gridx = 1;		messageCST.gridy = 1;
    	messageCST.gridwidth = 1;	messageCST.gridheight = 1;
    	messageCST.weightx = 0;		messageCST.weighty = 0;
    	messagePNL.add(internalMessagePNL, messageCST);
    	messageCST.gridx = 0;		messageCST.weightx = 1;
    	messageCST.fill = GridBagConstraints.BOTH;
    	messagePNL.add(getHorizontalSpace(), messageCST);
    	messageCST.gridx = 2;
    	messagePNL.add(getHorizontalSpace(), messageCST);
    	messageCST.gridx = 1;		messageCST.gridy = 0;
    	messageCST.weightx = 0;		messageCST.weighty = 1;
    	messagePNL.add(getVerticalSpace(), messageCST);
    	messageCST.gridy = 2;
    	messagePNL.add(getVerticalSpace(), messageCST);
    	
    	//CREATE BUTTON PANEL
    	JPanel internalButtonPNL = new JPanel();
		internalButtonPNL.setLayout(new GridLayout(1, buttonIDs.length, getSettings().getSpaceSize(), getSettings().getSpaceSize()));
    	for(int i = 0; i < buttonIDs.length; i++)
    	{
    		DButton currentBTN = new DButton(this, buttonIDs[i]);
    		internalButtonPNL.add(currentBTN);
    	
    	}//FOR
    	
    	JPanel buttonPNL = new JPanel();
    	buttonPNL.setLayout(new GridBagLayout());
    	GridBagConstraints buttonCST = new GridBagConstraints();
    	buttonCST.gridx = 1;		buttonCST.gridy = 2;
    	buttonCST.gridwidth = 1;	buttonCST.gridheight = 1;
    	buttonCST.weightx = 0;		buttonCST.weighty = 0;
    	buttonPNL.add(getVerticalSpace(), buttonCST);
    	buttonCST.gridy = 0;		buttonCST.gridheight = 2;
    	buttonPNL.add(internalButtonPNL, buttonCST);
    	buttonCST.gridx = 0;		buttonCST.weightx = 1;
    	buttonCST.gridheight = 1;
    	buttonCST.fill = GridBagConstraints.BOTH;
    	buttonPNL.add(getHorizontalSpace(), buttonCST);
    	buttonCST.gridx = 2;
    	buttonPNL.add(getHorizontalSpace(), buttonCST);
    	
    	//CREATE FULL PANEL
    	JPanel fullPNL = new JPanel();
    	fullPNL.setLayout(new BorderLayout());
    	fullPNL.add(buttonPNL, BorderLayout.SOUTH);
    	fullPNL.add(messagePNL, BorderLayout.CENTER);
    	return fullPNL;
    
    }//METHOD
	
	@Override
	public void event(String id, int value)
	{
		returnString = id;
		dialog.dispose();
		
	}//METHOD
	
}//CLASS
