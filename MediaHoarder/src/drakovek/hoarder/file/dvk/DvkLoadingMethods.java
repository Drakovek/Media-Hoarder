package drakovek.hoarder.file.dvk;

/**
 * Methods to invoke when loading DVKs
 * 
 * @author Drakovek
 * @version 2.0
 */
public interface DvkLoadingMethods
{
	/**
	 * Called when finished loading DVKs.
	 */
	public void loadingDVKsDone();
	
	/**
	 * Called when finished sorting DVKs.
	 */
	public void sortingDVKsDone();
	
	/**
	 * Called when finished filtering DVKs.
	 */
	public void filteringDVKsDone();
	
}//CLASS
