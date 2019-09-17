package drakovek.hoarder.work;

/**
 * Default Runnable object for the program.
 * 
 * @author Drakovek
 * @version 2.0

 */
public class DRunnable implements Runnable
{
	/**
	 * ID of the work type
	 */
	private String id;
	
	/**
	 * Object implementing Worker interface. Called when DRunnable runs
	 */
	private DWorker worker;
	
	/**
	 * Initializes the DRunnable object.
	 * 
	 * @param worker Object implementing DWorker interface. Called when DRunnable runs
	 * @param id ID of the work type
	 */
	public DRunnable(DWorker worker, final String id)
	{
		this.id = id;
		this.worker = worker;
		
	}//CONSTRUCTOR
	
	@Override
	public void run()
	{
		worker.run(id);
		
	}//METHOD

}//CLASS
