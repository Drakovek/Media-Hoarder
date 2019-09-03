package drakovek.hoarder.gui.swing.listeners;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * Deals with a hyperlink being activated.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DHyperlinkListener implements HyperlinkListener
{
	/**
	 * Event to call when hyperlink is activated
	 * 
	 * @since 2.0
	 */
	private DEvent event;
	
	/**
	 * Initializes the DHyperlinkListener class.
	 * 
	 * @param event Event to call when hyperlink is activated
	 * @since 2.0
	 */
	public DHyperlinkListener(DEvent event)
	{
		this.event = event;
		
	}//CONSTRUCTOR
	
	@Override
	public void hyperlinkUpdate(HyperlinkEvent linkEvent)
	{
		if(linkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
		{
			String myURL = new String();
			try
			{
				myURL = linkEvent.getURL().toString();
				
			}//TRY
			catch(NullPointerException e)
			{
				try
				{
					myURL = linkEvent.getDescription();
					
				}//TRY
				catch(Exception f)
				{
					myURL = new String();
					
				}//CATCH
				
			}//CATCH
			
			event.event(myURL, -1);
			
		}//IF
		
	}//METHOD

}//CLASS
