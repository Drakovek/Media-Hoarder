package drakovek.hoarder.gui.artist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.ComponentDisabler;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DPasswordField;
import drakovek.hoarder.gui.swing.components.DTextField;
import drakovek.hoarder.gui.swing.compound.DButtonDialog;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.media.ImageHandler;
import drakovek.hoarder.media.ImageScrollPane;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * GUI for remotely logging into a website.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class LoginGUI extends BaseGUI implements DWorker, ComponentDisabler
{
	/**
	 * Name of the captcha file to load before any actual captcha has been loaded from online.
	 */
	private static final String TEMP_CAPTCHA_FILE = "captcha.png"; //$NON-NLS-1$
	
	/**
	 * Main dialog for showing the login GUI
	 */
	private DDialog dialog;
	
	/**
	 * Object to deal with attempting a user login.
	 */
	private LoginMethods loginMethods;
	
	/**
	 * Dialog for showing progress
	 */
	private DProgressDialog progressDialog;
	
	/**
	 * Language ID for the title for the login dialog
	 */
	private String titleID;
	
	/**
	 * Boolean that determines whether to use the captcha GUI when creating the login GUI.
	 */
	private boolean useCaptcha;
	
	/**
	 * Text field for the user to input the contents of a captcha.
	 */
	private DTextField captchaText;
	
	/**
	 * Text field for the user to input their username
	 */
	private DTextField usernameText;
	
	/**
	 * Button to login
	 */
	private DButton loginButton;
	
	/**
	 * Button for refreshing the image captcha
	 */
	private DButton captchaButton;
	
	/**
	 * Password field for the user to input their password
	 */
	private DPasswordField passwordText;
	
	/**
	 * ImageScrollPane for holding image captchas.
	 */
	private ImageScrollPane imageScroll;
	
	/**
	 * directory for holding image captchas.
	 */
	private File captchaFolder;
	
	/**
	 * Initializes the LoginGUI class
	 * 
	 * @param settings Program Settings
	 * @param titleID Language ID for the title for the login dialog
	 * @param useCaptcha Boolean that determines whether to use the captcha GUI when creating the login GUI.
	 */
	public LoginGUI(DSettings settings, final String titleID, final boolean useCaptcha)
	{
		super(settings);
		this.titleID = titleID;
		this.useCaptcha = useCaptcha;
		progressDialog = new DProgressDialog(settings);
		usernameText = new DTextField(this, DefaultLanguage.USERNAME);
		passwordText = new DPasswordField(this, DefaultLanguage.PASSWORD);
		imageScroll = new ImageScrollPane(settings);
		
		//GET CAPTCHA FOLDER AND REMOVE OLD IMAGES
		captchaFolder = DReader.getDirectory(settings.getDataFolder(), "captcha"); //$NON-NLS-1$
		if(captchaFolder != null && captchaFolder.isDirectory())
		{
			File[] captchaFiles = captchaFolder.listFiles();
			for(int i = 0; i < captchaFiles.length; i++)
			{
				if(!captchaFiles[i].getName().equals(TEMP_CAPTCHA_FILE) && !captchaFiles[i].isDirectory())
				{
					captchaFiles[i].delete();
					
				}//IF
				
			}//FOR
			
		}//IF
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the LoginMethods object for the class.
	 * 
	 * @param loginMethods LoginMethods object
	 */
	public void setLoginMethods(LoginMethods loginMethods)
	{
		this.loginMethods = loginMethods;
		
	}//METHOD
	
	/**
	 * Opens the login dialog.
	 * 
	 * @param owner DFrame linked to the dialog
	 */
	public void openLoginDialog(DFrame owner)
	{
		owner.setAllowExit(false);
		dialog = new DDialog(owner, getLoginPanel(), titleID, true, getSettings().getFontSize() * getSettings().getFrameWidth(), 0);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		owner.setAllowExit(true);
		
	}//METHOD
	
	/**
	 * Returns the main panel for the login dialog
	 * 
	 * @return Login Panel
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
		loginButton = new DButton(this, DefaultLanguage.LOGIN);
		loginPanel.add(loginButton, loginCST);
		
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
		captchaButton = new DButton(this, DefaultLanguage.REFRESH_CAPTCHA);
		captchaBottomPanel.add(captchaButton, captchaCST);
		
		imageScroll.setScale(ImageHandler.SCALE_FULL, 1);
		if(captchaFolder != null && captchaFolder.isDirectory())
		{
			imageScroll.setFile(new File(captchaFolder, TEMP_CAPTCHA_FILE));
			
		}//IF
		
		Dimension imageDimension = new Dimension(1, (int)imageScroll.getImageDimension().getHeight() + 10);
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new GridBagLayout());
		GridBagConstraints imageCST = new GridBagConstraints();
		imageCST.gridx = 0;			imageCST.gridy = 0;
		imageCST.gridwidth = 1;		imageCST.gridheight = 3;
		imageCST.weightx = 0;		imageCST.weighty = 0;
		imageCST.fill = GridBagConstraints.BOTH;
		imagePanel.add(Box.createRigidArea(imageDimension), imageCST);
		imageCST.gridx = 2;
		imagePanel.add(Box.createRigidArea(imageDimension), imageCST);
		imageCST.gridx = 1;
		imageCST.weightx = 1;		imageCST.weighty = 1;
		imagePanel.add(imageScroll, imageCST);
		
		JPanel captchaPanel = getVerticalStack(getVerticalStack(imagePanel, captchaBottomPanel), new JSeparator(SwingConstants.HORIZONTAL));
		
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
	
	/**
	 * Clears the captcha, username, and password fields.
	 */
	public void clearFields()
	{
		usernameText.setText(new String());
		passwordText.setText(new String());
		captchaText.setText(new String());
	
	}//METHOD
	
	/**
	 * Returns the directory for holding image captchas.
	 * 
	 * @return Captcha Folder
	 */
	public File getCaptchaFolder()
	{
		return captchaFolder;
		
	}//METHOD
	
	/**
	 * Starts the worker to load an image captcha
	 */
	private void loadCaptcha()
	{
		disableAll();
		captchaText.setText(new String());
		progressDialog.startProgressDialog(dialog, DefaultLanguage.LOAD_CAPTCHA_TITLE);
		progressDialog.setProcessLabel(DefaultLanguage.LOAD_CAPTCHA);
		progressDialog.setDetailLabel(DefaultLanguage.RUNNING, true);
		progressDialog.setProgressBar(true, false, 0, 0);
		(new DSwingWorker(this, DefaultLanguage.CAPTCHA)).execute();
		
	}//METHOD
	
	/**
	 * Sets the image captcha.
	 */
	private void setCaptchaImage()
	{
		File file = loginMethods.getCaptcha();
		
		if(file != null && file.exists())
		{
			imageScroll.setFile(file);
			
		}//IF
		else
		{
			imageScroll.setFile(new File(captchaFolder, TEMP_CAPTCHA_FILE));
		
		}//ELSE
		
	}//METHOD
	
	/**
	 * Starts the worker that attempts login.
	 */
	private void login()
	{
		if(usernameText.getText().length() > 0 && passwordText.getPassword().length > 0 && (!useCaptcha || captchaText.getText().length() > 0))
		{
			disableAll();
			progressDialog.startProgressDialog(dialog, DefaultLanguage.ATTEMPT_LOGIN);
			progressDialog.setProcessLabel(DefaultLanguage.LOGIN);
			progressDialog.setDetailLabel(DefaultLanguage.RUNNING, true);
			progressDialog.setProgressBar(true, false, 0, 0);
			(new DSwingWorker(this, DefaultLanguage.LOGIN)).execute();
		
		}//IF
		
	}//METHOD
	
	/**
	 * Gets the login information provided by the user, then passes it to the loginMethods object.
	 */
	private void loginWork()
	{
		char[] passwordArray = passwordText.getPassword();
		StringBuilder builder = new StringBuilder();
		for(char passwordChar: passwordArray)
		{
			builder.append(passwordChar);
			
		}//FOR
		
		loginMethods.login(usernameText.getText(), builder.toString(), captchaText.getText());
	
	}//METHOD
	
	/**
	 * Finishes the login process and checks if the login was successful, closing the dialog if it was.
	 */
	private void loginEnd()
	{
		enableAll();
		progressDialog.closeProgressDialog();
		
		if(loginMethods.isLoggedIn())
		{
			dialog.dispose();
			dialog = null;
			
		}//IF
		else
		{
			DButtonDialog buttonDialog = new DButtonDialog(getSettings());
			String[] messageIDs = {DefaultLanguage.LOGIN_FAILED};
			String[] buttonIDs = {DefaultLanguage.OK};
			buttonDialog.openButtonDialog(this, dialog, DefaultLanguage.LOGIN_FAILED, messageIDs, buttonIDs);
			loadCaptcha();
			
		}//ELSE
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.REFRESH_CAPTCHA:
				loadCaptcha();
				break;
			case DefaultLanguage.LOGIN:
				login();
				break;
			
		}//SWITCH
		
	}//METHOD

	@Override
	public void run(String id)
	{
		switch(id)
		{
			case DefaultLanguage.CAPTCHA:
				setCaptchaImage();
				break;
			case DefaultLanguage.LOGIN:
				loginWork();
				break;
			
		}//SWITCH
		
	}//METHOD

	@Override
	public void done(String id)
	{
		switch(id)
		{
			case DefaultLanguage.CAPTCHA:
				enableAll();
				progressDialog.closeProgressDialog();
				break;
			case DefaultLanguage.PASSWORD:
			case DefaultLanguage.LOGIN:
				loginEnd();
				break;
			
		}//SWITCH
		
	}//METHOD

	@Override
	public void enableAll()
	{
		usernameText.setEnabled(true);
		passwordText.setEnabled(true);
		captchaText.setEnabled(true);
		loginButton.setEnabled(true);
		captchaButton.setEnabled(true);
		
	}//METHOD

	@Override
	public void disableAll()
	{
		usernameText.setEnabled(false);
		passwordText.setEnabled(false);
		captchaText.setEnabled(false);
		loginButton.setEnabled(false);
		captchaButton.setEnabled(false);
		
	}//METHOD
	
}//CLASS
