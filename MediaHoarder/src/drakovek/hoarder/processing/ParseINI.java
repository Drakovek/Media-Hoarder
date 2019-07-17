package drakovek.hoarder.processing;

import java.io.File;
import java.util.ArrayList;

/**
 * Gets Information from .ini formatted text
 *
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ParseINI
{
	/**
	 * Extension for a .ini file
	 * 
	 * @since 2.0
	 */
	public static final String INI_EXTENSION = ".ini"; //$NON-NLS-1$
	
	/**
	 * Returns a section of a .ini file based on what's under a given header
	 * 
	 * @param header INI header
	 * @param iniText INI formatted text
	 * @return Section of .ini file
	 * @since 2.0
	 */
	public static ArrayList<String> getSection(final String header, final ArrayList<String> iniText)
	{
		ArrayList<String> section = new ArrayList<>();
		int start;
		for(start = 0; start < iniText.size() && !iniText.get(start).startsWith(header); start++);
		start++;
		
		if(start < iniText.size())
		{
			int end;
			for(end = start + 1; end < iniText.size() && !iniText.get(end).startsWith(Character.toString('[')); end++);
			section = StringMethods.listToArrayList(iniText.subList(start, end));
		
		}//IF
		
		return section;
		
	}//METHOD
	
	/**
	 * Gets a String value for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @return String Value of variable
	 * @since 2.0
	 */
	private static String getValue(final String header, final String variable, final ArrayList<String> iniText)
	{
		String value = null;
		if(header != null && header.length() > 0)
		{
			value = getValue(variable, getSection(header, iniText));
			
		}//IF
		else
		{
			value = getValue(variable, iniText);
			
		}//ELSE
		
		return value;
		
	}//METHOD
	
	/**
	 * Gets a String value for a given variable from .ini formatted text.
	 * 
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @return String Value of variable
	 * @since 2.0
	 */
	private static String getValue(final String variable, final ArrayList<String> iniText)
	{
		int lineNum;
		String returnString = null;
		for(lineNum = 0; lineNum < iniText.size() && !iniText.get(lineNum).startsWith(variable + '='); lineNum++);
		
		if(lineNum < iniText.size())
		{
			int charNum = iniText.get(lineNum).indexOf('=') + 1;
			if(charNum < iniText.get(lineNum).length())
			{
				returnString = iniText.get(lineNum).substring(charNum);
			
			}//IF
			
		}//IF
		
		return returnString;
		
	}//METHOD
	
	/**
	 * Gets a String value for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return String Value of variable
	 * @since 2.0
	 */
    public static String getStringValue(final String header, final String variable, final ArrayList<String> iniText, final String defaultValue)
    {
    	String value = getValue(header, variable, iniText);
    	if(value == null)
    	{
    		return defaultValue;
    	
    	}//IF
    	
    	return value;
    	
    }//METHOD
    
	/**
	 * Gets a File value for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return String Value of variable
	 * @since 2.0
	 */
    public static File getFileValue(final String header, final String variable, final ArrayList<String> iniText, final File defaultValue)
    {
    	String value = getValue(header, variable, iniText);
    	if(value == null)
    	{
    		return defaultValue;
    	
    	}//IF
    	
    	File file = new File(value);
    	if(file.exists())
    	{
    		return file;
    		
    	}//IF
    	
    	return defaultValue;
    	
    }//METHOD
    
	/**
	 * Gets a boolean value for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return String Value of variable
	 * @since 2.0
	 */
    public static boolean getBooleanValue(final String header, final String variable, final ArrayList<String> iniText, final boolean defaultValue)
    {
    	String value = getValue(header, variable, iniText);
    	if(value == null)
    	{
    		return defaultValue;
    	
    	}//IF
    	
    	return Boolean.toString(true).toLowerCase().equals(value.toLowerCase());
    	
    }//METHOD
    
	/**
	 * Gets an ArrayList<String> value for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return ArrayList<String> Value of variable
	 * @since 2.0
	 */
    public static ArrayList<String> getStringListValue(final String header, final String variable, final ArrayList<String> iniText, final ArrayList<String> defaultValue)
    {
    	String value = getValue(variable, iniText);
    	if(value == null)
        {
    		return defaultValue;
    		
        }//IF
    	
    	ArrayList<String> returnList = new ArrayList<>();
    	int end;
    	
    	while(value.length() > 0)
    	{
    		for(end = 0; end < value.length() && value.charAt(end) != StringMethods.LIST_SEPARATOR_CHAR; end++);
    		returnList.add(value.substring(0, end));
    		
    		end++;
    		if(end < value.length())
    		{
    			value = value.substring(end, value.length());
    			
    		}//IF
    		else
    		{
    			value = new String();
    			
    		}//ELSE
    		
    	}//WHILE
    	
    	return returnList;
    	
    }//METHOD
    
	/**
	 * Gets an int value for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return int Value of variable
	 * @since 2.0
	 */
    public static int getIntValue(final String header, final String variable, final ArrayList<String> iniText, final int defaultValue)
    {
    	try
    	{
    		int value = Integer.parseInt(getValue(variable, iniText));
    		return value;
    		
    	}//TRY
    	catch(Exception e)
    	{
    		return defaultValue;
    		
    	}//CATCH
    	
    }//METHOD
    
	/**
	 * Gets a double value for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return int Value of variable
	 * @since 2.0
	 */
    public static double getDoubleValue(final String header, final String variable, final ArrayList<String> iniText, final double defaultValue)
    {
    	try
    	{
    		double value = Double.parseDouble(getValue(variable, iniText));
    		return value;
    		
    	}//TRY
    	catch(Exception e)
    	{
    		return defaultValue;
    		
    	}//CATCH
    	
    }//METHOD
    
    /**
     * Creates an .ini assignment string from a given variable and String value.
     * 
     * @param variable Name of .ini Variable
     * @param value Value of variable
     * @return .ini Assignment String
     * @since 2.0
     */
    public static String getAssignmentString(final String variable, final String value)
    {
    	return variable + Character.toString('=') + value;
   
    }//METHOD
    
    /**
     * Creates an .ini assignment string from a given variable and int value.
     * 
     * @param variable Name of .ini Variable
     * @param value Value of variable
     * @return .ini Assignment String
     * @since 2.0
     */
    public static String getAssignmentString(final String variable, final int value)
    {
    	return getAssignmentString(variable, Integer.toString(value));
   
    }//METHOD
    
    /**
     * Creates an .ini assignment string from a given variable and double value.
     * 
     * @param variable Name of .ini Variable
     * @param value Value of variable
     * @return .ini Assignment String
     * @since 2.0
     */
    public static String getAssignmentString(final String variable, final double value)
    {
    	return getAssignmentString(variable, Double.toString(value));
   
    }//METHOD

    /**
     * Creates an .ini assignment string from a given variable and boolean value.
     * 
     * @param variable Name of .ini Variable
     * @param value Value of variable
     * @return .ini Assignment String
     * @since 2.0
     */
    public static String getAssignmentString(final String variable, final boolean value)
    {
    	return getAssignmentString(variable, Boolean.toString(value).toLowerCase());
   
    }//METHOD
    
    /**
     * Creates an .ini assignment string from a given variable and String[] value.
     * 
     * @param variable Name of .ini Variable
     * @param value Value of variable
     * @return .ini Assignment String
     * @since 2.0
     */
    public static String getAssignmentString(final String variable, final String[] value)
    {
    	return getAssignmentString(variable, StringMethods.arrayToString(value));
   
    }//METHOD
    
    /**
     * Creates an .ini assignment string from a given variable and File value.
     * 
     * @param variable Name of .ini Variable
     * @param value Value of variable
     * @return .ini Assignment String
     * @since 2.0
     */
    public static String getAssignmentString(final String variable, final File value)
    {
    	if(value != null && value.exists())
    	{
    		return getAssignmentString(variable, value.getAbsolutePath());
    		
    	}//IF
    	
    	return getAssignmentString(variable, new String());
   
    }//METHOD
    
    
}//CLASS
