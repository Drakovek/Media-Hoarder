package drakovek.hoarder.gui.artist;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
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
	 * Object to deal with attempting a user login.
	 * 
	 * @since 2.0
	 */
	private LoginMethods loginMethods;
	
	/**
	 * Language ID for the title for the login dialog
	 * 
	 * @since 2.0
	 */
	private String titleID;
	
	/**
	 * Boolean that determines whether to use the captcha GUI when creating the login GUI.
	 * 
	 * @since 2.0
	 */
	private boolean useCaptcha;
	
	/**
	 * Text field for the user to input the contents of a captcha.
	 * 
	 * @since 2.0
	 */
	private DTextField captchaText;
	
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
	 * @param useCaptcha Boolean that determines whether to use the captcha GUI when creating the login GUI.
	 * @since 2.0
	 */
	public LoginGUI(DSettings settings, final String titleID, final boolean useCaptcha)
	{
		super(settings);
		this.titleID = titleID;
		this.useCaptcha = useCaptcha;
		usernameText = new DTextField(this, DefaultLanguage.USERNAME);
		passwordText = new DPasswordField(this, DefaultLanguage.PASSWORD);
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the LoginMethods object for the class.
	 * 
	 * @param loginMethods LoginMethods object
	 * @since 2.0
	 */
	public void setLoginMethods(LoginMethods loginMethods)
	{
		this.loginMethods = loginMethods;
		
	}//METHOD
	
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
		
		//CREATE CAPTCHA PANEL
		JPanel captchaBottomPanel = new JPanel();
		captchaText = new DTextField(this, DefaultLanguage.CAPTCHA);
		captchaBottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints captchaCST = new GridBagConstraints();
		captchaCST.gridx = 0;		captchaCST.gridy = 0;
		captchaCST.gridwidth = 1;	captchaCST.gridheight = 1;
		captchaCST.weightx = 0;		captchaCST.weighty = 0;
		captchaCST.fill = GridBagConstraints.BOTH;
		captchaBottomPanel.add(new DLabel(this, captchaText, DefaultLanguage.CAPTCHA), captchaCST);
		captchaCST.gridy = 1;
		captchaBottomPanel.add(getVerticalSpace(), captchaCST);
		captchaCST.gridx = 1;		captchaCST.gridy = 0;
		captchaBottomPanel.add(getHorizontalSpace(), captchaCST);
		captchaCST.gridx = 2;		captchaCST.weightx = 1;
		captchaBottomPanel.add(captchaText, captchaCST);
		captchaCST.gridx = 0;		captchaCST.gridy = 2;
		captchaCST.gridwidth = 3;
		captchaBottomPanel.add(new DButton(this, DefaultLanguage.REFRESH_CAPTCHA), captchaCST);
		
		JPanel captchaPanel = getVerticalStack(getVerticalStack(new DButton(this, DefaultLanguage.CAPTCHA), captchaBottomPanel), new JSeparator(SwingConstants.HORIZONTAL));
		
		//USER PANEL
		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new BorderLayout());
		fullPanel.add(getSpacedPanel(titleLabel, 0, 0, true, false, true, true), BorderLayout.NORTH);
		
		if(useCaptcha)
		{
			fullPanel.add(getSpacedPanel(getVerticalStack(captchaPanel, loginPanel), 1, 0, true, true, true, true), BorderLayout.CENTER);
			
		}//IF
		else
		{
			fullPanel.add(getSpacedPanel(loginPanel, 1, 0, true, true, true, true), BorderLayout.CENTER);
		
		}//ELSE
		
		return fullPanel;
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{

	}//METHOD
	
}//CLASS
