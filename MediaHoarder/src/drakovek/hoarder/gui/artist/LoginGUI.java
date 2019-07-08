package drakovek.hoarder.gui.artist;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DPasswordField;
import drakovek.hoarder.gui.swing.components.DTextField;

/**
 * GUI for remotely logging into a website.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class LoginGUI extends BaseGUI
{
	/**
	 * Main dialog for showing the login GUI
	 * 
	 * @since 2.0
	 */
	private DDialog dialog;
	
	/**
	 * Language ID for the title for the login dialog
	 * 
	 * @since 2.0
	 */
	private String titleID;
	
	/**
	 * Text field for the user to input their username
	 * 
	 * @since 2.0
	 */
	private DTextField usernameText;
	
	/**
	 * Password field for the user to input their password
	 * 
	 * @since 2.0
	 */
	private DPasswordField passwordText;
	
	/**
	 * Initializes the LoginGUI class
	 * 
	 * @param settings Program Settings
	 * @param titleID Language ID for the title for the login dialog
	 * @since 2.0
	 */
	public LoginGUI(DSettings settings, final String titleID)
	{
		super(settings);
		this.titleID = titleID;
		usernameText = new DTextField(this, DefaultLanguage.USERNAME);
		passwordText = new DPasswordField(this, DefaultLanguage.PASSWORD);
		
	}//CONSTRUCTOR
	
	/**
	 * Opens the login dialog.
	 * 
	 * @param owner DFrame linked to the dialog
	 * @since 2.0
	 */
	public void openLoginDialog(DFrame owner)
	{
		owner.setAllowExit(false);
		dialog = new DDialog(owner, getLoginPanel(), titleID, getSettings().getFontSize() * getSettings().getFrameWidth(), 0);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		owner.setAllowExit(true);
		
	}//METHOD
	
	/**
	 * Returns the main panel for the login dialog
	 * 
	 * @return Login Panel
	 * @since 2.0
	 */
	public JPanel getLoginPanel()
	{
		//TILE LABEL
		DLabel titleLabel = new DLabel(this, null, titleID);
		titleLabel.setFontLarge();
		
		//TEXT PANEL
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(2, 1, 0, getSettings().getSpaceSize()));
		textPanel.add(usernameText);
		textPanel.add(passwordText);
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(2, 1, 0, getSettings().getSpaceSize()));
		labelPanel.add(new DLabel(this, usernameText, DefaultLanguage.USERNAME));
		labelPanel.add(new DLabel(this, passwordText, DefaultLanguage.PASSWORD));
		
		//LOGIN PANEL
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new GridBagLayout());
		GridBagConstraints loginCST = new GridBagConstraints();
		loginCST.gridx = 0;			loginCST.gridy = 0;
		loginCST.gridwidth = 1;		loginCST.gridheight = 1;
		loginCST.weightx = 0;		loginCST.weighty = 0;
		loginCST.fill = GridBagConstraints.BOTH;
		loginPanel.add(labelPanel, loginCST);
		loginCST.gridy = 1;
		loginPanel.add(getVerticalSpace(), loginCST);
		loginCST.gridx = 1;			loginCST.gridy = 0;
		loginPanel.add(getHorizontalSpace(), loginCST);
		loginCST.gridx = 2;			loginCST.weightx = 1;
		loginPanel.add(textPanel, loginCST);
		loginCST.gridx = 0;			loginCST.gridy = 2;
		loginCST.gridwidth = 3;
		loginPanel.add(new DButton(this, DefaultLanguage.LOGIN), loginCST);
		
		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new BorderLayout());
		fullPanel.add(getSpacedPanel(titleLabel, 0, 0, true, false, true, true), BorderLayout.NORTH);
		fullPanel.add(this.getSpacedPanel(loginPanel, 1, 0, true, true, true, true), BorderLayout.CENTER);
		
		return fullPanel;
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{

	}//METHOD
	
}//CLASS
