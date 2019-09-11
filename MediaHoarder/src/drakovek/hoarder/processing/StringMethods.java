package drakovek.hoarder.processing;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods for processing String Lists and String ArrayLists
 * 
 * @author Drakovek
 * @version 2.0
 */
public class StringMethods 
{
	/**
	 * Array of HTML escape characters and their corresponding unicode characters.
	 */
	private static final String[][] ESCAPE_CHARS = {{"&quot;", "\""}, //$NON-NLS-1$ //$NON-NLS-2$
													{"&apos;", "'"}, //$NON-NLS-1$ //$NON-NLS-2$
													{"&amp;", "&"}, //$NON-NLS-1$ //$NON-NLS-2$
													{"&lt;", "<"},  //$NON-NLS-1$//$NON-NLS-2$
													{"&gt;", ">"}, //$NON-NLS-1$ //$NON-NLS-2$
													{"&nbsp;", " "}};  //$NON-NLS-1$//$NON-NLS-2$
	/**
	 * Character for separating Strings in a list.
	 */
	public static final char LIST_SEPARATOR_CHAR = ',';
	
	/**
	 * Converts a char array to a String array.
	 * 
	 * @param chars Char Array
	 * @return Equivalent String Array
	 */
	public static String[] charArrayToStringArray(final char[] chars)
	{
		String[] strings = new String[chars.length];
		for(int i = 0; i < chars.length; i++)
		{
			strings[i] = Character.toString(chars[i]);
			
		}//FOR
		
		return strings;
		
	}//METHOD
	
	/**
	 * Returns whether the given String starts with any of the given prefix characters.
	 * 
	 * @param text Given String
	 * @param prefixes Prefix characters to check
	 * @return Whether the given String starts with any of the prefix characters
	 */
	public static boolean startsWith(final String text, final char[] prefixes)
	{
		return startsWith(text, charArrayToStringArray(prefixes));
		
	}//METHOD
	
	/**
	 * Returns whether the given String starts with any of the given prefix Strings.
	 * 
	 * @param text Given String
	 * @param prefixes Prefix Strings to check
	 * @return Whether the given String starts with any of the prefix strings
	 */
	public static boolean startsWith(final String text, final String[] prefixes)
	{
		for(int i = 0; i < prefixes.length; i++)
		{
			if(text.startsWith(prefixes[i]))
			{
				return true;
				
			}//IF
			
		}//FOR
		
		return false;
		
	}//METHOD
	
	/**
	 * Returns whether the given String ends with any of the given suffix characters.
	 * 
	 * @param text Given String
	 * @param suffixes Suffix characters to check
	 * @return Whether the given String ends with any of the suffix characters
	 */
	public static boolean endsWith(final String text, final char[] suffixes)
	{
		return endsWith(text, charArrayToStringArray(suffixes));
		
	}//METHOD
	
	/**
	 * Returns whether the given String ends with any of the given suffix Strings.
	 * 
	 * @param text Given String
	 * @param suffixes Suffix Strings to check
	 * @return Whether the given String ends with any of the suffix strings
	 */
	public static boolean endsWith(final String text, final String[] suffixes)
	{
		for(int i = 0; i < suffixes.length; i++)
		{
			if(text.endsWith(suffixes[i]))
			{
				return true;
				
			}//IF
			
		}//FOR
		
		return false;
		
	}//METHOD
	
	/**
	 * Returns the index of the first instance of any of the characters in the given character array.
	 * 
	 * @param text Given text to search
	 * @param chars Given chars to search for
	 * @return First instance of any character. Returns -1 if no given character is found.
	 */
	public static int indexOf(final String text, final char[] chars)
	{
		return indexOf(text, chars, 0);
		
	}//METHOD
	
	/**
	 * Returns the index of the first instance of any of the characters in the given character array.
	 * 
	 * @param text Given text to search
	 * @param chars Given chars to search for
	 * @param fromIndex Given index to search from
	 * @return First instance of any character. Returns -1 if no given character is found.
	 */
	public static int indexOf(final String text, final char[] chars, final int fromIndex)
	{
		int lowest = -1;
		int current;
		
		for(int i = 0; i < chars.length; i++)
		{
			current = text.indexOf(chars[i], fromIndex);
			if(current != -1 && (lowest == -1 || current < lowest))
			{
				lowest = current;
				
			}//IF
			
		}//FOR
		
		return lowest;
		
	}//METHOD
	
	/**
	 * Converts a List<String> to an ArrayList<String>
	 * 
	 * @param list Input List<String>
	 * @return Output ArrayList<String>
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
	
	/**
	 * Returns given string with a given section removed.
	 * 
	 * @param text Given Text
	 * @param start Start index of section to remove (inclusive)
	 * @param end End index of section to remove (exclusive)
	 * @return Text with given section removed
	 */
	public static String removeStringSection(final String text, final int start, final int end)
	{
		StringBuilder builder = new StringBuilder();
		if(start <= text.length())
		{
			builder.append(text.substring(0, start));
			
		}//IF
		
		if(end < text.length())
		{
			builder.append(text.substring(end));
			
		}//IF
		
		return builder.toString();
		
	}//METHOD
	
	/**
	 * Returns given text modified to have all uncommon unicode characters replaced by HTML escape characters.
	 * 
	 * @param text Given unicode text
	 * @return Text with HTML escape characters
	 */
	public static String addHtmlEscapes(final String text)
	{
    	StringBuilder builder = new StringBuilder();
    	
    	if(text != null && text.length() > 0)
    	{
    		for(int i = 0; i < text.length(); i++)
    		{
    			char myChar = text.charAt(i);
    			if((myChar > 47 && myChar < 58) || (myChar > 64 && myChar < 91) || (myChar > 96 && myChar < 123) || myChar == ' ')
    			{
    				builder.append(myChar);
    			
    			}//IF
    			else
    			{
    				builder.append('&');
    				builder.append('#');
    				builder.append(Integer.toString(myChar));
    				builder.append(';');
    			
    			}//ELSE
    		
    		}//FOR
    		
    	}//IF
    	
    	return builder.toString();
		
	}//METHOD
	
	/**
	 * Replaces uncommon unicode characters in HTML formatted text with escape characters, while keeping HTML values intact.
	 * 
	 * @param htmlText HTML Formatted Text
	 * @return HTML Text with escape characters
	 */
	public static String addHtmlEscapesToHtml(final String htmlText)
	{
		StringBuilder builder = new StringBuilder();
		
		if(htmlText != null && htmlText.length() > 0)
		{
			ArrayList<String> separated = separateByEdgeCharacters(htmlText, '"', '"');
			
			for(int i = 0; i < separated.size(); i++)
			{
				String section = separated.get(i);
				if(section.startsWith(Character.toString('"')) && section.endsWith(Character.toString('"')))
				{
					builder.append(section);

				}//IF
				else
				{
					for(int k = 0; k < section.length(); k++)
			    	{
			    		char myChar = section.charAt(k);
			    		if((myChar > 31 && myChar < 127))
			    		{
			    			builder.append(myChar);
			    			
			    		}//IF
			    		else
			    		{
			    			builder.append('&');
			    			builder.append('#');
			    			builder.append(Integer.toString(myChar));
			    			builder.append(';');
			    			
			    		}//ELSE
			    		
			    	}//FOR
					
				}//ELSE
				
			}//FOR
			
		}//IF
		
		return builder.toString();
		
	}//METHOD
	
	/**
	 * Returns given text with all HTML escape characters replaced by their equivalent unicode characters.
	 * 
	 * @param text Given Text
	 * @return Unicode Text
	 */
	public static String replaceHtmlEscapeCharacters(final String text)
	{
		StringBuilder builder = new StringBuilder();
		
		if(text != null && text.length() > 0)
		{
			ArrayList<String> separated = separateByEdgeCharacters(text, '&', ';');
			
			int start;
			int end;
			for(int i = 0; i < separated.size(); i++)
			{
				String section = separated.get(i);
				if(section.startsWith(Character.toString('&')) && section.endsWith(Character.toString(';')))
				{
					end = -1;
					start = section.indexOf('#');
					if(start != -1)
					{
						start++;
						end = section.indexOf(';', start);
						
					}//IF
					
					if(end != -1)
					{
						try
						{
							char character = (char)Integer.parseInt(section.substring(start, end));
							builder.append(character);
							
						}//TRY
						catch(Exception e)
						{
							end = -1;
							
						}//CATCH
						
					}//IF
					
					if(end == -1)
					{
						for(int k = 0; k < ESCAPE_CHARS.length; k++)
						{
							if(ESCAPE_CHARS[k][0].equals(section))
							{
								builder.append(ESCAPE_CHARS[k][1]);
								end = 0;
								break;
								
							}//IF
							
						}//FOR
						
						if(end == -1)
						{
							builder.append(section);
							
						}//IF
						
					}//IF
					
				}//IF
				else
				{
					builder.append(section);
					
				}//ELSE
				
			}//FOR
			
		}//IF
		
		return builder.toString();
		
	}//METHOD
	
	/**
	 * Separates a string of text into sections based on the characters starting and ending string sections.
	 * 
	 * @param text Given Text
	 * @param startChar Character at the start of sections to separate
	 * @param endChar Character at the end of sections to separate
	 * @return ArrayList with edge character sections separated
	 */
	private static ArrayList<String> separateByEdgeCharacters(final String text, final char startChar, final char endChar)
	{
		String leftText = text;
		ArrayList<String> separated = new ArrayList<>();
		
		if(text != null && text.length() > 0)
		{
			int start;
			int end;
			while(true)
			{
				end = -1;
				start = leftText.indexOf(startChar);
				if(start != -1 && (start + 1 < leftText.length()))
				{
					end = leftText.indexOf(endChar, start + 1);
					
				}//IF
				
				if(end == -1)
				{
					break;
					
				}//IF
				
				end++;
				
				if(start > 0)
				{
					separated.add(leftText.substring(0, start));
				
				}//IF
				
				separated.add(leftText.substring(start, end));
				
				if(end < leftText.length())
				{
					leftText = leftText.substring(end);
					
				}//IF
				else
				{
					leftText = new String();
					
				}//ELSE
				
			}//WHILE
			
			if(leftText.length() > 0)
			{
				separated.add(leftText);
				
			}//IF
			
		}//IF
		
		return separated;
		
	}//METHOD
	
}//CLASS
