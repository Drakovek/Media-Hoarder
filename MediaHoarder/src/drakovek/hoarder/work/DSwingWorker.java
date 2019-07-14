package drakovek.hoarder.work;

import javax.swing.SwingWorker;

/**
 * Default SwingWorker for the program.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DSwingWorker extends SwingWorker<int[], Void>
{
	/**
	 * ID of the work
	 * 
	 * @since 2.0
	 */
	private String id;
	
	/**
	 * Object implementing Worker interface. Called when DSwingWorker starts and ends
	 * 
	 * @since 2.0
	 */
	private Worker worker;
	
	/**
	 * Initializes the DSwingWorker class.
	 * 
	 * @param worker Object implementing Worker interface
	 * @param id ID of the work
	 * @since 2.0
	 */
	public DSwingWorker(Worker worker, final String id)
	{
		this.worker = worker;
		this.id = id;
		
	}//CONSTRUCTOR

	@Override
	protected int[] doInBackground() throws Exception
	{
		worker.run(id);
		
		return null;
		
	}//METHOD
	
	@Override
	protected void done()
	{
		//REPORT ERRORS
		try
		{
			this.get();
		
		}//TRY
		catch(Exception e)
		{
			e.printStackTrace();
			
		}//CATCH (Exception e)
		
		worker.done(id);
	
	}//METHOD

}//CLASS
