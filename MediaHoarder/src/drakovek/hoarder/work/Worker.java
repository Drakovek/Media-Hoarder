package drakovek.hoarder.work;

/**
 * Interface to call for various working objects such as Runnable or SwingWorker
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public interface Worker
{
	/**
	 * Called when a worker starts.
	 * 
	 * @param id ID of the work.
	 * @since 2.0
	 */
	public void run(final String id);
	
	/**
	 * Called when a worker ends.
	 * 
	 * @param id ID of the work
	 * @since 2.0
	 */
	public void done(final String id);
	
}//CLASS
