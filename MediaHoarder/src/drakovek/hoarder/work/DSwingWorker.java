package drakovek.hoarder.work;

import javax.swing.SwingWorker;

/**
 * Default SwingWorker for the program.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DSwingWorker extends SwingWorker<int[], Void>
{
	/**
	 * ID of the work
	 */
	private String id;
	
	/**
	 * Object implementing Worker interface. Called when DSwingWorker starts and ends
	 */
	private DWorker worker;
	
	/**
	 * Initializes the DSwingWorker class.
	 * 
	 * @param worker Object implementing DWorker interface
	 * @param id ID of the work
	 */
	public DSwingWorker(DWorker worker, final String id)
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
