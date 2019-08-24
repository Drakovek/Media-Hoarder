package drakovek.hoarder.processing;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods for processing String Lists and String ArrayLists
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class StringMethods 
{
	/**
	 * Character for separating Strings in a list.
	 * 
	 * @since 2.0
	 */
	public static final char LIST_SEPARATOR_CHAR = ',';
	
	/**
	 * Converts a List<String> to an ArrayList<String>
	 * 
	 * @param list Input List<String>
	 * @return Output ArrayList<String>
	 * @since 2.0
	 */
	public static ArrayList<String> listToArrayList(final List<String> list)
	{
		ArrayList<String> arrayList = new ArrayList<>();
		for(String item : list)
		{
			arrayList.add(item);
		
		}//FOR
		
		return arrayList;
		
	}//METHOD
	
	/**
	 * Converts String[] to a String with entries separated by LIST_SEPARATOR_CHAR
	 * 
	 * @param array Input String Array
	 * @return String with entries separated by LIST_SEPARATOR_CHAR
	 * @since 2.0
	 */
	public static String arrayToString(final String[] array)
	{
		return arrayToString(array, false, new String());
		
	}//METHOD
	
	/**
	 * Converts String[] to a String with entries separated by LIST_SEPARATOR_CHAR
	 * 
	 * @param array Input String Array
	 * @param isDisplayString Whether the String is meant to be displayed. If false, array string will be separated without space in between items.
	 * @param emptyString String to return if the array is empty
	 * @return String with entries separated by LIST_SEPARATOR_CHAR
	 * @since 2.0
	 */
	public static String arrayToString(final String[] array, final boolean isDisplayString, final String emptyString)
	{
		if(array == null || array.length == 0)
		{
			return emptyString;
			
		}//IF
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < array.length; i++)
		{
			if(i > 0)
			{
				builder.append(Character.toString(LIST_SEPARATOR_CHAR));
				if(isDisplayString)
				{
					builder.append(Character.toString(' '));
					
				}//IF
			
			}//IF
			
			builder.append(array[i]);
			
		}//FOR
	
		return builder.toString();
		
	}//METHOD
	
	/**
	 * Converts an ArrayList<String> to a String[]
	 * 
	 * @param arraylist Input ArrayList<String>
	 * @return Output String[]
	 * @since 2.0
	 */
	public static String[] arrayListToArray(final ArrayList<String> arraylist)
	{
		String[] array = new String[arraylist.size()];
		
		for(int i = 0; i < arraylist.size(); i++)
		{
			array[i] = arraylist.get(i);
		
		}//FOR
		
		return array;
		
	}//METHOD
	
	/**
	 * Extends a String of number characters to a given length by adding 0s to the beginning.
	 * 
	 * @param string Number String
	 * @param length Length to extend String
	 * @return Extended Number String
	 * @since 2.0
	 */
	public static String extendNumberString(final String string, final int length)
	{
		int repetitions = length - string.length();
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < repetitions; i++)
		{
			builder.append(Character.toString('0'));
		
		}//METHOD
		
		builder.append(string);
		
		return builder.toString();
		
	}//METHOD 
	
	/**
	 * Creates a String from int, then extends to a given length by adding 0s to the beginning.
	 * 
	 * @param integer Input int
	 * @param length Length to extend String
	 * @return Extended Number String
	 * @since 2.0
	 */
	public static String extendNumberString(final int integer, final int length)
	{
		String intString = Integer.toString(integer);
		return extendNumberString(intString, length);
		
	}//METHOD
	
	/**
	 * Returns a String consisting of a single character repeated to a certain length.
	 * 
	 * @param character Character to repeat
	 * @param length Length of the returning String
	 * @return Extended String
	 * @since 2.0
	 */
	public static String extendCharacter(final char character, final int length)
	{
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < length; i++)
		{
			builder.append(Character.toString(character));
		
		}//FOR
		
		return builder.toString();
		
	}//METHOD
	
}//CLASS
