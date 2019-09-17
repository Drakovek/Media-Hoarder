package drakovek.hoarder.gui.artist;

import java.io.File;

/**
 * Methods for logging into a given website.
 * 
 * @author Drakovek
 * @version 2.0
 */
public interface LoginMethods 
{
	/**
	 * Attempts to log in to a given website.
	 * 
	 * @param username User's username
	 * @param password User's password
	 * @param captcha Text for the image captcha.
	 */
	public void login(final String username, final String password, final String captcha);
	
	/**
	 * Checks if the user is currently logged into a given website
	 * 
	 * @return Whether the user is logged in
	 */
	public boolean isLoggedIn();
	
	/**
	 * Saves an image captcha from online, then returns the file to which it was saved.
	 * 
	 * @return Image Captcha File
	 */
	public File getCaptcha();
	
}//METHOD
