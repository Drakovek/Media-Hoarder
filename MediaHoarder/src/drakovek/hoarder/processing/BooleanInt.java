package drakovek.hoarder.processing;

/**
 * Contains methods for converting between ints and booleans.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class BooleanInt
{
	/**
	 * Converts int to boolean.
	 * 
	 * @param boolInt Input int
	 * @return Boolean representing the int
	 * @since 2.0
	 */
	public static boolean getBoolean(final int boolInt)
	{
		if(boolInt == 0)
		{
			return false;
			
		}///IF
		
		return true;
		
	}//METHOD
	
	/**
	 * Converts boolean to int.
	 * 
	 * @param bool Input boolean
	 * @return Int representing the boolean
	 * @since 2.0
	 */
	public static int getInt(final boolean bool)
	{
		if(bool)
		{
			return 1;
			
		}//IF
		
		return 0;
		
	}//METHOD
	
}//CLASS
