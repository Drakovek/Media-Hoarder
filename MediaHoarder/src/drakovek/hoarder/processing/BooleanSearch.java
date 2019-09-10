package drakovek.hoarder.processing;

import java.util.ArrayList;

/**
 * Contains methods for conducting boolean searches on Strings.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class BooleanSearch
{
	/**
	 * Characters that represent the AND operator
	 * 
	 * @since 2.0
	 */
	private static final char[] AND_CHARS = {'&'};
	
	/**
	 * Characters that represent the OR operator
	 * 
	 * @since 2.0
	 */
	private static final char[] OR_CHARS = {'|', '~'};
	
	/**
	 * Characters that represent the NOT operator
	 * 
	 * @since 2.0
	 */
	private static final char[] NOT_CHARS = {'!', '-'};
	
	/**
	 * Characters that represent quotes to indicate that text within should be treated as a complete String.
	 * 
	 * @since 2.0
	 */
	private static final char[] QUOTES = {'"', '\''};
	
	/**
	 * Characters that represent empty space.
	 * 
	 * @since 2.0
	 */
	private static final char[] SPACES = {' ', '\t'};
	
	/**
	 * Left binding characters used to indicate the order to address arguments.
	 * 
	 * @since 2.0
	 */
	private static final char[] LEFT_BINDERS = {'(', '['};
	
	/**
	 * Right binding characters used to indicate the order to address arguments.
	 * 
	 * @since 2.0
	 */
	private static final char[] RIGHT_BINDERS = {')', ']'};
	
	/**
	 * Sets the current logic array based off a given user argument.
	 * 
	 * @param userArgument Given User Argument
	 * @since 2.0
	 */
	public static void createLogicArray(final String userArgument)
	{
		if(userArgument != null)
		{
			convertToParsable(userArgument);
			
		}//IF
		
	}//METHOD
	
	/**
	 * Converts an argument string given by the user into an argument that can be parsed to create a logic array.
	 * 
	 * @param argument User Argument
	 * @return Parsable Argument
	 * @since 2.0
	 */
	private static String convertToParsable(final String argument)
	{
		System.out.println();
		System.out.println(argument);
		System.out.println(separateToChunks(argument));
		return new String();
		
	}//METHOD
	
	/**
	 * Separates an argument string into chunks of search strings and operators, then returns them as an ArrayList<String> 
	 *
	 * @param argument Given argument strings
	 * @return ArrayList<String> of string and operator chunks
	 * @since 2.0
	 */
	private static ArrayList<String> separateToChunks(final String argument)
	{
		int start = -1;
		int end = -1;
		String modString = argument;
		ArrayList<String> quoteChunks = new ArrayList<>();
		
		while(StringMethods.endsWith(modString, SPACES))
		{
			modString = modString.substring(0, modString.length() - 1);
			
		}//WHILE
		
		//EXTRACT STRINGS WITH QUOTES
		while(StringMethods.indexOf(modString, QUOTES) != -1)
		{
			start = StringMethods.indexOf(modString, QUOTES);
			char quote = modString.charAt(start);
			end = modString.indexOf(quote, start + 1);
			if(end == -1)
			{
				end = modString.length();
				
			}//IF
			
			quoteChunks.add(modString.substring(0, start));
			quoteChunks.add(QUOTES[0] + modString.substring(start + 1, end) + QUOTES[0]);
			if(end < modString.length())
			{	
				modString = modString.substring(end + 1);
				
			}//IF
			else
			{
				modString = new String();
				
			}//ELSE
			
		}//METHOD
		
		if(modString.length() > 0)
		{
			quoteChunks.add(modString);
			
		}//IF
		
		//SEPARATE STRINGS FROM OPERATORS
		ArrayList<String> chunks = new ArrayList<>();
		while(quoteChunks.size() > 0)
		{
			modString = quoteChunks.get(0);
			
			//CHECK IF ENTRY IS QUOTE
			if(modString.startsWith(Character.toString(QUOTES[0])))
			{
				chunks.add(modString);
				quoteChunks.remove(0);
				continue;
				
			}//IF
			
			//CHECK IF ENTRY IS EMPTY
			while(StringMethods.startsWith(modString, SPACES))
			{
				modString = modString.substring(1);
				
			}//WHILE
			
			if(modString.length() == 0)
			{
				quoteChunks.remove(0);
				continue;
				
			}//IF
			
			//CHECK IF ENTRY IS AN OPERATOR
			if(StringMethods.startsWith(modString, AND_CHARS) ||
			   StringMethods.startsWith(modString, OR_CHARS) ||
			   StringMethods.startsWith(modString, NOT_CHARS) ||
			   StringMethods.startsWith(modString, LEFT_BINDERS) ||
			   StringMethods.startsWith(modString, RIGHT_BINDERS	))
			{
				chunks.add(Character.toString(getDefaultChar(modString.charAt(0))));
				quoteChunks.set(0, modString.substring(1));
				continue;
				
			}//IF
			
			//GET STRING
			end = StringMethods.indexOf(modString, SPACES);
			start = StringMethods.indexOf(modString, LEFT_BINDERS);
			if(start != -1 && (end == -1 || start < end))
			{
				end = start;
				
			}//IF
			start = StringMethods.indexOf(modString, RIGHT_BINDERS);
			if(start != -1 && (end == -1 || start < end))
			{
				end = start;
				
			}//IF
			
			if(end == -1)
			{
				end = modString.length();
				
			}//IF
			
			chunks.add(QUOTES[0] + modString.substring(0, end) + QUOTES[0]);
			quoteChunks.set(0, modString.substring(end));
			
		}//WHILE
		
		
		return chunks;
		
	}//METHOD
	
	/**
	 * Returns the default character for the category that the given character is a part of, if applicable.
	 * 
	 * @param inputChar Given Character
	 * @return Default Character for category
	 * @since 2.0
	 */
	private static char getDefaultChar(final char inputChar)
	{
		if(isCharInCategory(inputChar, AND_CHARS))
		{
			return AND_CHARS[0];
			
		}//IF
		
		if(isCharInCategory(inputChar, OR_CHARS))
		{
			return OR_CHARS[0];
			
		}//IF
		
		if(isCharInCategory(inputChar, NOT_CHARS))
		{
			return NOT_CHARS[0];
			
		}//IF
		
		if(isCharInCategory(inputChar, LEFT_BINDERS))
		{
			return LEFT_BINDERS[0];
			
		}//IF
		
		if(isCharInCategory(inputChar, RIGHT_BINDERS))
		{
			return RIGHT_BINDERS[0];
			
		}//IF
		
		if(isCharInCategory(inputChar, QUOTES))
		{
			return QUOTES[0];
			
		}//IF
		
		if(isCharInCategory(inputChar, SPACES))
		{
			return SPACES[0];
			
		}//IF
		
		return inputChar;
		
	}//METHOD
	
	/**
	 * Returns whether a given character is included in a character array.
	 * 
	 * @param inputChar Input Character
	 * @param charCategory Character Array
	 * @return Whether inputChar is in charCategory
	 * @since 2.0
	 */
	private static boolean isCharInCategory(final char inputChar, final char[] charCategory)
	{
		for(int i = 0; i < charCategory.length; i++)
		{
			if(inputChar == charCategory[i])
			{
				return true;
				
			}//IF
			
		}//FOR
		
		return false;
		
	}//METHOD
	
}//CLASS
