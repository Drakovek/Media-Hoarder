package drakovek.hoarder.processing;

import java.util.ArrayList;

/**
 * Contains methods for conducting boolean searches on Strings.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class BooleanSearch
{
	/**
	 * Default AND operator character
	 */
	private static final char AND = '&';
	
	/**
	 * Characters that represent the AND operator
	 */
	private static final char[] AND_CHARS = {AND};
	
	/**
	 * Default OR operator character
	 */
	private static final char OR = '|';
	
	/**
	 * Characters that represent the OR operator
	 */
	private static final char[] OR_CHARS = {OR, '~'};
	
	/**
	 * Default NOT operator character
	 */
	private static final char NOT = '!';
	
	/**
	 * Characters that represent the NOT operator
	 */
	private static final char[] NOT_CHARS = {NOT, '-'};
	
	/**
	 * Default quote character
	 */
	private static final char QUOTE = '"';
	
	/**
	 * Characters that represent quotes to indicate that text within should be treated as a complete String.
	 */
	private static final char[] QUOTE_CHARS = {QUOTE, '\''};
	
	/**
	 * Characters that represent empty space.
	 */
	private static final char[] SPACE_CHARS = {' ', '\t'};
	
	/**
	 * Default left binding character
	 */
	private static final char LEFT_BINDER = '(';
	
	/**
	 * Left binding characters used to indicate the order to address arguments.
	 */
	private static final char[] LEFT_BINDING_CHARS = {LEFT_BINDER, '['};
	
	/**
	 * Default right binding character
	 */
	private static final char RIGHT_BINDER = ')';
	
	/**
	 * Right binding characters used to indicate the order to address arguments.
	 */
	private static final char[] RIGHT_BINDING_CHARS = {RIGHT_BINDER, ']'};
	
	/**
	 * Sets the current logic array based off a given user argument.
	 * 
	 * @param userArgument Given User Argument
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
	 */
	private static ArrayList<String> separateToChunks(final String argument)
	{
		int start = -1;
		int end = -1;
		String modString = argument;
		ArrayList<String> quoteChunks = new ArrayList<>();
		
		while(StringMethods.endsWith(modString, SPACE_CHARS))
		{
			modString = modString.substring(0, modString.length() - 1);
			
		}//WHILE
		
		//EXTRACT STRINGS WITH QUOTES
		while(StringMethods.indexOf(modString, QUOTE_CHARS) != -1)
		{
			start = StringMethods.indexOf(modString, QUOTE_CHARS);
			char quote = modString.charAt(start);
			end = modString.indexOf(quote, start + 1);
			if(end == -1)
			{
				end = modString.length();
				
			}//IF
			
			quoteChunks.add(modString.substring(0, start));
			quoteChunks.add(QUOTE + modString.substring(start + 1, end) + QUOTE);
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
			if(modString.startsWith(Character.toString(QUOTE)))
			{
				chunks.add(modString);
				quoteChunks.remove(0);
				continue;
				
			}//IF
			
			//CHECK IF ENTRY IS EMPTY
			while(StringMethods.startsWith(modString, SPACE_CHARS))
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
			   StringMethods.startsWith(modString, LEFT_BINDING_CHARS) ||
			   StringMethods.startsWith(modString, RIGHT_BINDING_CHARS))
			{
				chunks.add(Character.toString(getDefaultChar(modString.charAt(0))));
				quoteChunks.set(0, modString.substring(1));
				continue;
				
			}//IF
			
			//GET STRING
			end = StringMethods.indexOf(modString, SPACE_CHARS);
			start = StringMethods.indexOf(modString, LEFT_BINDING_CHARS);
			if(start != -1 && (end == -1 || start < end))
			{
				end = start;
				
			}//IF
			start = StringMethods.indexOf(modString, RIGHT_BINDING_CHARS);
			if(start != -1 && (end == -1 || start < end))
			{
				end = start;
				
			}//IF
			
			if(end == -1)
			{
				end = modString.length();
				
			}//IF
			
			chunks.add(QUOTE + modString.substring(0, end) + QUOTE);
			quoteChunks.set(0, modString.substring(end));
			
		}//WHILE
		
		
		return chunks;
		
	}//METHOD
	
	/**
	 * Returns the default character for the category that the given character is a part of, if applicable.
	 * 
	 * @param inputChar Given Character
	 * @return Default Character for category
	 */
	private static char getDefaultChar(final char inputChar)
	{
		if(isCharInCategory(inputChar, AND_CHARS))
		{
			return AND;
			
		}//IF
		
		if(isCharInCategory(inputChar, OR_CHARS))
		{
			return OR;
			
		}//IF
		
		if(isCharInCategory(inputChar, NOT_CHARS))
		{
			return NOT;
			
		}//IF
		
		if(isCharInCategory(inputChar, LEFT_BINDING_CHARS))
		{
			return LEFT_BINDER;
			
		}//IF
		
		if(isCharInCategory(inputChar, RIGHT_BINDING_CHARS))
		{
			return RIGHT_BINDER;
			
		}//IF
		
		if(isCharInCategory(inputChar, QUOTE_CHARS))
		{
			return QUOTE;
			
		}//IF
		
		return inputChar;
		
	}//METHOD
	
	/**
	 * Returns whether a given character is included in a character array.
	 * 
	 * @param inputChar Input Character
	 * @param charCategory Character Array
	 * @return Whether inputChar is in charCategory
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
