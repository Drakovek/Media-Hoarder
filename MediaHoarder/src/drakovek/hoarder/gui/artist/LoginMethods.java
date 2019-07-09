package drakovek.hoarder.gui.artist;

/**
 * Methods for logging into a given website.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public interface LoginMethods 
{
	/**
	 * Attempts to log in to a given website.
	 * 
	 * @param username User's username
	 * @param password User's password
	 * @since 2.0
	 */
	public void login(final String username, final String password);
	
	/**
	 * Checks if the user is currently logged into a given website
	 * 
	 * @return Whether the user is logged in
	 */
	public boolean isLoggedIn();
	
}//METHOD
