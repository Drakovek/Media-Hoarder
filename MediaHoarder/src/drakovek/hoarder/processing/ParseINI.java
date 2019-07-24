package drakovek.hoarder.processing;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.processing.sort.FileSort;

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
	public static final String INI_EXTENSION = ".dini"; //$NON-NLS-1$
	
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
	 * Gets String values for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @return String Values of variable
	 * @since 2.0
	 */
	private static ArrayList<String> getValues(final String header, final String variable, final ArrayList<String> iniText)
	{
		ArrayList<String> values = new ArrayList<>();
		if(header != null && header.length() > 0)
		{
			values = getValues(variable, getSection(header, iniText));
			
		}//IF
		else
		{
			values = getValues(variable, iniText);
			
		}//ELSE
		
		return values;
		
	}//METHOD
	
	/**
	 * Gets String values for a given variable from .ini formatted text.
	 * 
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @return String Values of variable
	 * @since 2.0
	 */
	private static ArrayList<String> getValues(final String variable, final ArrayList<String> iniText)
	{
		ArrayList<String> returnValues = new ArrayList<>();
		for(int i = 0; i < iniText.size(); i++)
		{
			if(iniText.get(i).startsWith(variable + '='))
			{
				int charNum = iniText.get(i).indexOf('=') + 1;
				if(charNum < iniText.get(i).length())
				{
					returnValues.add(iniText.get(i).substring(charNum));
				
				}//IF
				
			}//IF
			
		}//FOR
		
		return returnValues;
		
	}//METHOD
	
	/**
	 * Gets String values for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return String Values of variable
	 * @since 2.0
	 */
    public static ArrayList<String> getStringValues(final String header, final String variable, final ArrayList<String> iniText, final ArrayList<String> defaultValue)
    {
    	ArrayList<String> values = getValues(header, variable, iniText);
    	if(values.size() == 0)
    	{
    		return defaultValue;
    	
    	}//IF
    	
    	return values;
    	
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
    	ArrayList<String> values = getValues(header, variable, iniText);
    	if(values.size() == 0)
    	{
    		return defaultValue;
    	
    	}//IF
    	
    	return values.get(0);
    	
    }//METHOD
    
	/**
	 * Gets File values for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return File Values of variable
	 * @since 2.0
	 */
    public static ArrayList<File> getFileValues(final String header, final String variable, final ArrayList<String> iniText, final ArrayList<File> defaultValue)
    {
    	ArrayList<String> values = getValues(header, variable, iniText);
    	
    	//REMOVE INVALID FILES
    	ArrayList<File> files = new ArrayList<>();
    	for(int i = 0; i < values.size(); i++)
    	{
    		File file = new File(values.get(i));
    		if(file.exists())
    		{
    			files.add(file);
    			
    		}//IF
    		
    	}//FOR
    		
    	if(files.size() == 0)
    	{
    		return defaultValue;
    	
    	}//IF
    
    	return FileSort.sortFiles(files);
    	
    }//METHOD
    
	/**
	 * Gets a File value for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return File Value of variable
	 * @since 2.0
	 */
    public static File getFileValue(final String header, final String variable, final ArrayList<String> iniText, final File defaultValue)
    {
    	ArrayList<File> files = getFileValues(header, variable, iniText, new ArrayList<File>());
    	
    	if(files.size() == 0)
    	{
    		return defaultValue;
    		
    	}//IF
    	
    	return files.get(0);
    	
    }//METHOD
    
	/**
	 * Gets boolean values for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return Boolean Values of variable
	 * @since 2.0
	 */
    public static ArrayList<Boolean> getBooleanValues(final String header, final String variable, final ArrayList<String> iniText, final ArrayList<Boolean> defaultValue)
    {
    	ArrayList<String> values = getValues(header, variable, iniText);
    	ArrayList<Boolean> booleans = new ArrayList<>();
    	for(String value: values)
    	{
    		booleans.add(Boolean.valueOf(Boolean.toString(true).toLowerCase().equals(value.toLowerCase())));
    		
    	}//FOR
    	
    	if(booleans.size() == 0)
    	{
    		return defaultValue;
    	
    	}//IF
    	
    	return booleans;
    	
    }//METHOD
    
	/**
	 * Gets a boolean value for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return Boolean Value of variable
	 * @since 2.0
	 */
    public static boolean getBooleanValue(final String header, final String variable, final ArrayList<String> iniText, final boolean defaultValue)
    {
    	ArrayList<Boolean> values = getBooleanValues(header, variable, iniText, new ArrayList<Boolean>());
    	
    	if(values.size() == 0)
    	{
    		return defaultValue;
    		
    	}//IF
    	
    	return values.get(0).booleanValue();
    	
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
    	ArrayList<String> values = getValues(header, variable, iniText);
    	if(values.size() == 0)
        {
    		return defaultValue;
    		
        }//IF
    	
    	String value = values.get(0);
    	
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
	 * Gets int values for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return int Values of variable
	 * @since 2.0
	 */
    public static ArrayList<Integer> getIntValues(final String header, final String variable, final ArrayList<String> iniText, final ArrayList<Integer> defaultValue)
    {
    	ArrayList<String> values = getValues(header, variable, iniText);
    	ArrayList<Integer> ints = new ArrayList<>();
    	
    	for(String value: values)
    	{
    		try
    		{
    			int intValue = Integer.parseInt(value);
    			ints.add(Integer.valueOf(intValue));
    			
    		}//TRY
    		catch(Exception e){}
    		
    	}//FOR
    	
    	if(ints.size() == 0)
    	{
    		return defaultValue;
    		
    	}//IF
    	
    	return ints;
    	
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
    	ArrayList<Integer> ints = getIntValues(header, variable, iniText, new ArrayList<Integer>());
    	
    	if(ints.size() == 0)
    	{
    		return defaultValue;
    		
    	}//IF
    	
    	return ints.get(0).intValue();
    	
    }//METHOD
    
	/**
	 * Gets double values for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return Double Values of variable
	 * @since 2.0
	 */
    public static ArrayList<Double> getDoubleValues(final String header, final String variable, final ArrayList<String> iniText, final ArrayList<Double> defaultValue)
    {
    	ArrayList<String> values = getValues(header, variable, iniText);
    	ArrayList<Double> doubles = new ArrayList<>();
    	
    	for(String value: values)
    	{
    		try
    		{
    			double doubleValue = Double.parseDouble(value);
    			doubles.add(Double.valueOf(doubleValue));
    			
    		}//TRY
    		catch(Exception e){}
    		
    	}//FOR
    	
    	if(doubles.size() == 0)
    	{
    		return defaultValue;
    		
    	}//IF
    	
    	return doubles;
    	
    }//METHOD
    
	/**
	 * Gets a double value for a given variable under a given header from .ini formatted text.
	 * 
	 * @param header INI Header
	 * @param variable INI Variable
	 * @param iniText INI Formatted Text
	 * @param defaultValue Value returned if no relevant value is found
	 * @return Doubel Value of variable
	 * @since 2.0
	 */
    public static double getDoubleValue(final String header, final String variable, final ArrayList<String> iniText, final double defaultValue)
    {
    	ArrayList<Double> doubles = getDoubleValues(header, variable, iniText, new ArrayList<Double>());
    	
    	if(doubles.size() == 0)
    	{
    		return defaultValue;
    		
    	}//IF
    	
    	return doubles.get(0).doubleValue();
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
