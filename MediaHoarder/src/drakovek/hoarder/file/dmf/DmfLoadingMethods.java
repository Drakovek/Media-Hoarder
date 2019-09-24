package drakovek.hoarder.file.dmf;

/**
 * Methods to invoke when loading DMFs
 * 
 * @author Drakovek
 * @version 2.0
 */
public interface DmfLoadingMethods
{
	/**
	 * Called when finished loading DMFs.
	 */
	public void loadingDMFsDone();
	
	/**
	 * Called when finished sorting DMFs.
	 */
	public void sortingDMFsDone();
	
	/**
	 * Called when finished filtering DMFs.
	 */
	public void filteringDMFsDone();
	
}//CLASS
