package drakovek.hoarder.processing.sort;

import java.util.ArrayList;

/**
 * Contains methods relating to alpha-numeric sorting.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class AlphaNumSort
{
	/**
	 * Sorts a given ArrayList<String> alpha-numerically.
	 * 
	 * @param inputList Starting ArrayList<String>
	 * @return Sorted ArrayList<String>
	 */
	public static ArrayList<String> sort(final ArrayList<String> inputList)
	{
		if(inputList.size() < 2)
		{
			return inputList;
			
		}//IF
		
		//split initialArrayList
		int half = inputList.size() / 2;
		ArrayList<String> aList = new ArrayList<>();
		ArrayList<String> bList = new ArrayList<>();
		
		for(int i = 0; i < half; i++)
		{
			aList.add(inputList.get(i));
		
		}//FOR
		
		for(int i = half; i < inputList.size(); i++)
		{
			bList.add(inputList.get(i));
		
		}//FOR
		
		//Sort split ArrayLists
		aList = sort(aList);
		bList = sort(bList);
		
		//Merge split ArrayLists
		ArrayList<String> finalArrayList = new ArrayList<>();
		
		while(aList.size() > 0 && bList.size() > 0)
		{
			if(compareAlpha(aList.get(0), bList.get(0)) < 0)
			{
				finalArrayList.add(aList.get(0));
				aList.remove(0);
				
			}//IF
			else
			{
				finalArrayList.add(bList.get(0));
				bList.remove(0);
			
			}//ELSE
			
		}//WHILE
		
		finalArrayList.addAll(aList);
		finalArrayList.addAll(bList);
		
		return finalArrayList;
		
	}//METHOD
	
	/**
	 * Compares two Strings alpha-numerically.
	 *  
	 * @param aString First String
	 * @param bString Second String
	 * @return Compare int (int < 0: aString is first, int > 0 bString is first)
	 */
    public static int compareAlpha(final String aString, final String bString)
    {
        int aLength = aString.length();
        int bLength = bString.length();
        int aNum = 0;
        int bNum = 0;
        int compareValue = 0;
        String sectionA;
        String sectionB;

        while(compareValue == 0 && aNum < aLength && bNum < bLength)
        {
            sectionA = getSection(aString, aNum).toLowerCase();
            aNum = aNum + sectionA.length();
            sectionB = getSection(bString, bNum).toLowerCase();
            bNum = bNum + sectionB.length();

            if(isNumber(sectionA) && isNumber(sectionB))
            {
            	double aValue = Double.parseDouble(sectionA);
                double bValue = Double.parseDouble(sectionB);
            	
                if(aValue > bValue)
                {
                    compareValue = 1;

                }//IF
                else if(aValue < bValue)
                {
                    compareValue = -1;

                }//ELSE IF

            }//IF
            else
            {
                compareValue = sectionA.compareTo(sectionB);
                
            }//ELSE

        }//WHILE

        if(compareValue == 0)
        {
            if(aLength > bLength)
            {
                compareValue = 1;

            }//IF
            else if(bLength > aLength)
            {
                compareValue = -1;

            }//ELSE

        }//IF

        return compareValue;
        
    }//METHOD
    
    /**
     * Returns a section containing either only characters or only a number value.
     * 
     * @param inputString Input String
     * @param firstCharPos Position of the first character of the section
     * @return Section containing either only characters or only a number value
     */
    private static String getSection(final String inputString, final int firstCharPos)
    {
    	int stringLength = inputString.length();
    	
    	if(firstCharPos < stringLength)
    	{
    		String outputString = new String();
    		boolean isNumber = isNumber(inputString.charAt(firstCharPos));
    		
    		for(int charNum = firstCharPos; charNum < stringLength; charNum++)
    		{
    			if(isNumber)
    			{
    				if( (isNumber(inputString.charAt(charNum))) || (charNum < stringLength - 1 && inputString.charAt(charNum) == '.' && isNumber(inputString.charAt(charNum + 1))) )
    				{
    					outputString = outputString + inputString.charAt(charNum);
    					
    				}//IF
    				else
    				{
    					break;
    					
    				}//ELSE
    				
    			}//IF
    			else
    			{
    				if(isNumber(inputString.charAt(charNum)))
    				{
    					break;
    					
    				}//IF
    				
    				outputString = outputString + inputString.charAt(charNum);
    				
    			}//ELSE
    			
    		}//FOR
    		
    		return outputString;
    		
    	}//IF
    	
    	return new String();
    	
    }//METHOD
    
    /**
     * Determines if a given character is a number (0 - 9)
     * 
     * @param inputChar Input char
     * @return true if input char is a number, false if not
     */
    private static boolean isNumber(final char inputChar)
    {
    	return (inputChar > 47 && inputChar < 58);
    	
    }//METHOD
    
    /**
     * Determines if a given string is valid as a double value.
     * 
     * @param inputString Given String
     * @return Whether String is a number
     */
    private static boolean isNumber(final String inputString)
    {
    	try
    	{
    		Double.parseDouble(inputString);
    		return true;
    		
    	}//TRY
    	catch(Exception e){}
    	
    	return false;
    	
    }//METHOD
    
}//CLASS
