package drakovek.hoarder.web;

/**
 * Contains methods to be used by objects using HTML WebClients to avoid memory leaks.
 * 
 * @author Drakovek
 * @version 2.0
 */
public interface ClientMethods
{
	/**
	 * Sets the WebClient as a new WebClient object. Should add all the properties needed for a particular task.
	 */
	public void setNewClient();
	
	/**
	 * Sets the page loaded by the WebClient. Should remove previously held page to avoid memory leaks.
	 * 
	 * @param url URL to load
	 */
	public void setPage(final String url);
	
}//CLASS