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
	 * Object array for storing boolean search logic information for a search
	 * 
	 * [0] - boolean		- Arg1 Nested?
	 * [1] - boolean		- Arg1 Inverted?
	 * [2] - String/Object	- Arg1
	 * [3] - boolean		- Arg2 Nested?
	 * [4] - boolean		- Arg2 Inverted?
	 * [5] - String/Object	- Arg2
	 * [6] - Character		- Operator (NULL, &, |)
	 */
	private Object[] searchLogic;
	
	/**
	 * Initializes BooleanSearch by creating a default logic array
	 */
	public BooleanSearch()
	{
		resetSearchLogic();
		
	}//CONSTRUCTOR
	
	/**
	 * Resets the current search logic to be empty.
	 * 
	 * @since 2.0
	 */
	public void resetSearchLogic()
	{
		searchLogic = new Object[7];
		searchLogic[0] = Boolean.valueOf(false);
		searchLogic[1] = Boolean.valueOf(false);
		searchLogic[2] = null;
		searchLogic[3] = Boolean.valueOf(false);
		searchLogic[4] = Boolean.valueOf(false);
		searchLogic[5] = null;
		searchLogic[6] = null;
		
	}//METHOD
	
	/**
	 * Sets the current search logic based off a given user argument.
	 * 
	 * @param userArgument Given User Argument
	 */
	public void createSearchLogic(final String userArgument)
	{
		if(userArgument != null)
		{
			searchLogic = getSearchLogic(separateToChunks(convertToParsable(userArgument)));
			
		}//IF
		
	}//METHOD
	
	/**
	 * Conducts a boolean search on given text with the currently set logic.
	 * 
	 * @param text Text to search
	 * @param caseSensitive Whether the search should be case sensitive
	 * @param exactMatch Whether the text should be an exact match with search logic strings (If false, text only has to contain search strings)
	 * @return Whether the given text matches the search logic
	 */
	public boolean searchText(final String text, final boolean caseSensitive, final boolean exactMatch)
	{
		String[] textArray = {text};
		return searchText(textArray, caseSensitive, exactMatch);
		
	}//METHOD
	
	/**
	 * Conducts a boolean search on given text with the currently set logic.
	 * 
	 * @param text Text to search
	 * @param caseSensitive Whether the search should be case sensitive
	 * @param exactMatch Whether the text should be an exact match with search logic strings (If false, text only has to contain search strings)
	 * @return Whether the given text matches the search logic
	 */
	public boolean searchText(final String[] text, final boolean caseSensitive, final boolean exactMatch)
	{
		if(caseSensitive)
		{
			return searchText(searchLogic, text, caseSensitive, exactMatch);
		
		}//IF
		
		String[] lowerText = new String[text.length];
		for(int i = 0; i < lowerText.length; i++)
		{
			lowerText[i] = text[i].toLowerCase();
			
		}//FOR
		
		return searchText(searchLogic, lowerText, caseSensitive, exactMatch);
	
	}//METHOD
	
	/**
	 * Conducts a boolean search on given text with given search logic.
	 * 
	 * @param text Text to search
	 * @param logic Given Search Logic
	 * @param caseSensitive Whether the search should be case sensitive
	 * @param exactMatch Whether the text should be an exact match with search logic strings (If false, text only has to contain search strings)
	 * @return Whether the given text matches the search logic
	 */
	private boolean searchText(final Object[] logic, final String[] text, final boolean caseSensitive, final boolean exactMatch)
	{
		boolean arg1;
		boolean arg2;
		
		//GET ARGUMENT VALUES
		if(((Boolean)logic[0]).booleanValue())
		{
			arg1 = searchText((Object[])logic[2], text, caseSensitive, exactMatch);
			
		}//IF
		else
		{
			arg1 = checkTextIncluded(text, (String)logic[2], caseSensitive, exactMatch);
		
		}//ELSE
		
		if(((Boolean)logic[3]).booleanValue())
		{
			arg2 = searchText((Object[])logic[5], text, caseSensitive, exactMatch);
			
		}//IF
		else
		{
			arg2 = checkTextIncluded(text, (String)logic[5], caseSensitive, exactMatch);
		
		}//ELSE
		
		//INVERT IF NECESSARY
		if(((Boolean)logic[1]).booleanValue())
		{
			arg1 = !arg1;
			
		}//IF
		
		if(((Boolean)logic[4]).booleanValue())
		{
			arg2 = !arg2;
			
		}//IF
		
		//RETURN ARGUMENT EVALUATION
		if(logic[6] != null)
		{
			if(((Character)logic[6]).equals(Character.valueOf(AND)))
			{
				return arg1 && arg2;
				
			}//IF
			
			if(((Character)logic[6]).equals(Character.valueOf(OR)))
			{
				return arg1 || arg2;
				
			}//IF
			
		}//IF
		
		return arg1;
		
	}//METHOD
	
	/**
	 * Checks if given search text is equal to or within a given main text.
	 * 
	 * @param mainTextArray Main text to search within
	 * @param searchText Text to search for
	 * @param caseSensitive Whether search should be case sensitive
	 * @param exactMatch Whether the search text should be an exact match with main text (If false, main text only has to contain search string)
	 * @return Whether the search text is included in the main text
	 */
	private static boolean checkTextIncluded(final String[] mainTextArray, final String searchText, final boolean caseSensitive, final boolean exactMatch)
	{	
		if(searchText == null)
		{
			return true;
			
		}//IF
		
		String thisSearchText;
		if(caseSensitive)
		{
			thisSearchText = searchText;
			
		}//ELSE
		else
		{
			thisSearchText = searchText.toLowerCase();
			
		}//ELSE
		
		for(int i = 0; i < mainTextArray.length; i++)
		{
			if(exactMatch )
			{
				if(mainTextArray[i].equals(thisSearchText))
				{
					return true;
					
				}//IF
				
			}//IF
			else if(mainTextArray[i].contains(thisSearchText))
			{
				return true;
				
			}//IF
			
			
		}//FOR
		
		return false;
		
	}//IF
	
	/**
	 * Returns an Object array of search logic from a separated argument String.
	 * 
	 * @param inputChunks Separated Argument String
	 * @return Search logic Object array
	 */
	private Object[] getSearchLogic(final ArrayList<String> inputChunks)
	{
		int numLeft;
		int numRight;
		Object[] logic = new Object[7];
		ArrayList<String> chunks = inputChunks;
		ArrayList<String> tempChunks;
		
		//IS 1ST ARGUMENT INVERTED
		logic[1] = Boolean.valueOf(chunks.get(0).charAt(0) == NOT);
		if(((Boolean)logic[1]).booleanValue())
		{
			chunks.remove(0);
			
		}//IF
		
		//GET ARGUMENT 1
		if(chunks.get(0).charAt(0) == QUOTE)
		{
			logic[0] = Boolean.valueOf(false);
			logic[2] = chunks.get(0).substring(1, chunks.get(0).length() - 1);
			chunks.remove(0);
			
		}//IF
		else
		{
			//GET INTERNAL ARGUMENT 1
			logic[0] = Boolean.valueOf(true);
			chunks.remove(0);
			tempChunks = new ArrayList<>();
			numLeft = 1;
			numRight = 0;
			
			while(numLeft != numRight)
			{
				if(chunks.get(0).charAt(0) == LEFT_BINDER)
				{
					numLeft++;
					
				}//IF
				else if(chunks.get(0).charAt(0) == RIGHT_BINDER)
				{
					numRight++;
					
				}//ELSE IF
				
				tempChunks.add(chunks.get(0));
				chunks.remove(0);
				
			}//WHILE
			
			tempChunks.remove(tempChunks.size() - 1);
			logic[2] = getSearchLogic(tempChunks);
			
		}//ELSE
		
		//GET OPERATOR
		if(chunks.size() == 0)
		{
			logic[3] = Boolean.valueOf(false);
			logic[4] = Boolean.valueOf(false);
			logic[5] = null;
			logic[6] = null;
			
		}//IF
		else if(chunks.get(0).charAt(0) == AND)
		{
			logic[6] = Character.valueOf(AND);
			chunks.remove(0);
			
		}//ELSE IF
		else if(chunks.get(0).charAt(0) == OR)
		{
			logic[6] = Character.valueOf(OR);
			chunks.remove(0);
			
		}//ELSE IF
		else
		{
			logic[3] = Boolean.valueOf(false);
			logic[4] = Boolean.valueOf(false);
			logic[5] = null;
			logic[6] = null;
			
		}//ELSE
		
		if(chunks.size() > 0)
		{
			//IS 2ND ARGUMENT INVERTED
			logic[4] = Boolean.valueOf(chunks.get(0).charAt(0) == NOT);
			if(((Boolean)logic[4]).booleanValue())
			{
				chunks.remove(0);
				
			}//IF
			
			//GET ARGUMENT 2
			if(chunks.get(0).charAt(0) == QUOTE)
			{
				logic[3] = Boolean.valueOf(false);
				logic[5] = chunks.get(0).substring(1, chunks.get(0).length() - 1);
				chunks.remove(0);
				
			}//IF
			else
			{
				//GET INTERNAL ARGUMENT 2
				logic[3] = Boolean.valueOf(true);
				chunks.remove(0);
				tempChunks = new ArrayList<>();
				numLeft = 1;
				numRight = 0;
				
				while(numLeft != numRight)
				{
					if(chunks.get(0).charAt(0) == LEFT_BINDER)
					{
						numLeft++;
						
					}//IF
					else if(chunks.get(0).charAt(0) == RIGHT_BINDER)
					{
						numRight++;
						
					}//ELSE IF
					
					tempChunks.add(chunks.get(0));
					chunks.remove(0);
					
				}//WHILE
				
				tempChunks.remove(tempChunks.size() - 1);
				logic[5] = getSearchLogic(tempChunks);
				
			}//ELSE
			
		}//IF
		
		return logic;
		
	}//METHOD
	
	/**
	 * Converts an argument string given by the user into an argument that can be parsed to create a logic array.
	 * 
	 * @param argument User Argument
	 * @return Parsable Argument
	 */
	private static String convertToParsable(final String argument)
	{
		return addBinders(fixLogic(separateToChunks(argument)));
		
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
	 * Fixes any logic errors in a given boolean expression.
	 * 
	 * @param inputChunks Boolean search input split into chunks of strings and operators.
	 * @return ArrayList of string and operator chunks with fixed logic
	 */
	private static ArrayList<String> fixLogic(final ArrayList<String> inputChunks)
	{
		ArrayList<String> chunks = new ArrayList<>();
		chunks.addAll(inputChunks);
		
		//REMOVE LEADING OPERATORS
		while(chunks.size() > 0 && (chunks.get(0).charAt(0) == AND || chunks.get(0).charAt(0) == OR))
		{
			chunks.remove(0);
			
		}//WHILE
		
		//REMOVE TRAILING OPERATORS
		while(chunks.size() > 0 && (chunks.get(chunks.size() - 1).charAt(0) == AND || chunks.get(chunks.size() - 1).charAt(0) == OR || chunks.get(chunks.size() - 1).charAt(0) == NOT))
		{
			chunks.remove(chunks.size() - 1);
			
		}//WHILE
		
		//COMPLETE HANGING LEFT BINDERS
		for(int pStart = 0; pStart < chunks.size(); pStart++)
		{
			if(chunks.get(pStart).charAt(0) == LEFT_BINDER)
			{
				int numLeft = 1;
				int numRight = 0;
				
				for(int pEnd = pStart + 1; numLeft != numRight && pEnd < chunks.size(); pEnd++)
				{
					if(chunks.get(pEnd).charAt(0) == LEFT_BINDER)
					{
						numLeft++;
						
					}//IF
					else if(chunks.get(pEnd).charAt(0) == RIGHT_BINDER)
					{
						numRight++;
						
					}//ELSE IF
					
				}//FOR
				
				if(numLeft != numRight)
				{
					chunks.add(Character.toString(RIGHT_BINDER));
					pStart--;
					
				}//IF
				
			}//IF
			
		}//FOR
		
		//COMPLETE HANGING RIGHT BINDERS
		for(int pEnd = chunks.size() - 1; pEnd > -1; pEnd--)
		{
			if(chunks.get(pEnd).charAt(0) == RIGHT_BINDER)
			{
				int numLeft = 0;
				int numRight = 1;
				
				for(int pStart = pEnd -1; numLeft != numRight && pStart > -1; pStart--)
				{
					if(chunks.get(pStart).charAt(0) == LEFT_BINDER)
					{
						numLeft++;
						
					}//IF
					else if (chunks.get(pStart).charAt(0) == RIGHT_BINDER)
					{
						numRight++;
						
					}//ELSE IF
					
				}//FOR
				
				if(numLeft != numRight)
				{
					chunks.add(0, Character.toString(LEFT_BINDER));
					pEnd += 2;
					
				}//IF
				
			}//IF
			
		}//FOR
		
		//ADD AND OPERATOR TO UNLINKED LOGIC STATEMENTS
		for(int i = 1; i < chunks.size(); i++)
		{
			if((chunks.get(i).charAt(0) == LEFT_BINDER && (chunks.get(i - 1).charAt(0) == RIGHT_BINDER || chunks.get(i - 1).charAt(0) == QUOTE)) ||
			   (chunks.get(i).charAt(0) == QUOTE && (chunks.get(i - 1).charAt(0) == RIGHT_BINDER || chunks.get(i - 1).charAt(0) == QUOTE)) ||
			   (chunks.get(i).charAt(0) == NOT && chunks.get(i - 1).charAt(0) != AND && chunks.get(i - 1).charAt(0) != OR))
					
			{
				chunks.add(i, Character.toString(AND));
				
			}//IF
			
		}//FOR
		
		//ADD BLANK STRING TO HANGING OPERATORS
		for(int i  = 1; i < chunks.size(); i++)
		{
			if(
			  (chunks.get(i).charAt(0) == RIGHT_BINDER && (chunks.get(i - 1).charAt(0) == AND || chunks.get(i - 1).charAt(0) == OR || chunks.get(i - 1).charAt(0) == NOT)) ||
			  (chunks.get(i).charAt(0) == AND && chunks.get(i - 1).charAt(0) != QUOTE && chunks.get(i - 1).charAt(0) != RIGHT_BINDER) ||
			  (chunks.get(i).charAt(0) == OR && chunks.get(i - 1).charAt(0) != QUOTE && chunks.get(i - 1).charAt(0) != RIGHT_BINDER)
			)
			{
				chunks.add(i, "\"\""); //$NON-NLS-1$
				i--;
			}//IF
			
		}//FOR
		
		return chunks;
		
	}//METHOD
	
	/**
	 * Adds binding characters to separated logic argument to clarify argument order.
	 * 
	 * @param inputChunks Argument broken into chunks of strings and operations
	 * @return Argument string with added binding characters
	 */
	private static String addBinders(final ArrayList<String> inputChunks)
	{
		ArrayList<String> chunks = inputChunks;
		ArrayList<String> modifiedChunks = new ArrayList<>();
		ArrayList<String> tempChunks;
		StringBuilder builder = new StringBuilder();
		
		//PROCESS INNER BINDERS
		while(chunks.size() > 0)
		{
			//DISREGARD INFO BEFORE THE FIRST BINDER
			while(chunks.size() > 0 && chunks.get(0).charAt(0) != LEFT_BINDER)
			{
				modifiedChunks.add(chunks.get(0));
				chunks.remove(0);
				
			}//WHILE
			
			//PROCESS INFO INSIDE BINDER, IF IT EXISTS
			if(chunks.size() > 0)
			{
				chunks.remove(0);	//REMOVE LEFT BINDER
				
				tempChunks = new ArrayList<>();
				int numLeft = 1;
				int numRight = 0;
				while(numLeft != numRight)
				{
					if(chunks.get(0).charAt(0) == LEFT_BINDER)
					{
						numLeft++;
						
					}//IF
					else if(chunks.get(0).charAt(0) == RIGHT_BINDER)
					{
						numRight++;
						
					}//ELSE
					
					tempChunks.add(chunks.get(0));
					chunks.remove(0);
					
				}//WHILE
				
				tempChunks.remove(tempChunks.size() - 1);
				modifiedChunks.add(addBinders(tempChunks));
				
			}//IF
			
		}//WHILE
		
		//ADD BINDERS
		chunks = new ArrayList<>();
		chunks.addAll(modifiedChunks);
		modifiedChunks = new ArrayList<>();
		
		while(chunks.size() > 0)
		{
			builder.insert(0, LEFT_BINDER);
			
			while(chunks.size() > 0 && (chunks.get(0).charAt(0) == NOT || chunks.get(0).charAt(0) == QUOTE || chunks.get(0).charAt(0) == LEFT_BINDER))
			{
				builder.append(chunks.get(0));
				chunks.remove(0);
				
			}//WHILE
			
			if(chunks.size() > 0)
			{
				builder.append(chunks.get(0));
				chunks.remove(0);
				
				while(chunks.size() > 0 && (chunks.get(0).charAt(0) == NOT || chunks.get(0).charAt(0) == QUOTE || chunks.get(0).charAt(0) == LEFT_BINDER))
				{
					builder.append(chunks.get(0));
					chunks.remove(0);
					
				}//WHILE
				
			}//IF
			
			builder.append(RIGHT_BINDER);
			
		}//WHILE
		
		return builder.toString();
		
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
