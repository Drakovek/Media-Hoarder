package drakovek.hoarder.processing.sort;

import java.io.File;
import java.util.ArrayList;

/**
 * Class for sorting file arrays.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class FileSort 
{
	/**
	 * Sorts an array of Files
	 * 
	 * @param inputFiles Unsorted File Array
	 * @return Sorted File Array
	 */
	public static File[] sortFiles(File[] inputFiles)
	{
		if(inputFiles == null)
		{
			return new File[0];
			
		}//IF
		
		//CONVERT TO ARRAYLIST
		ArrayList<File> fileList = new ArrayList<>();
		for(int i = 0; i < inputFiles.length; i++)
		{
			if(inputFiles[i] != null)
			{
				fileList.add(inputFiles[i]);
				
			}//IF
		
		}//FOR
		
		//SORT ARRAYLIST
		fileList = sortFiles(fileList);
		
		//CONVERT TO ARRAY
		File[] returnFiles = new File[fileList.size()];
		for(int i = 0; i < returnFiles.length; i++)
		{
			returnFiles[i] = fileList.get(i);
			
		}//FOR
			
		return returnFiles;
	
	}//METHOD
   	
	/**
	 * Sorts an ArrayList<File> alpha-numerically based on filenames.
	 * 
	 * @param inputList Starting ArrayList<File>
	 * @return Sorted ArrayList<File>
	 */
	public static ArrayList<File> sortFiles(final ArrayList<File> inputList)
	{
		if(inputList.size() < 2)
		{
			return inputList;
			
		}//IF
		
		//split initialArrayList
		int half = inputList.size() / 2;
		ArrayList<File> aList = new ArrayList<>();
		ArrayList<File> bList = new ArrayList<>();
		
		for(int i = 0; i < half; i++)
		{
			aList.add(inputList.get(i));
		
		}//FOR
		
		for(int i = half; i < inputList.size(); i++)
		{
			bList.add(inputList.get(i));
		
		}//FOR
		
		//Sort split ArrayLists
		aList = sortFiles(aList);
		bList = sortFiles(bList);
		
		//Merge split ArrayLists
		ArrayList<File> finalArrayList = new ArrayList<>();
		
		while(aList.size() > 0 && bList.size() > 0)
		{
			if(compareFiles(aList.get(0), bList.get(0)) < 0)
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
   	 * Compares two files.
   	 * 
   	 * @param aFile 1st file
   	 * @param bFile 2nd file
   	 * @return return int
   	 */
   	private static int compareFiles(File aFile, File bFile)
   	{
   		int result = 0;
   		
   		if(aFile.isDirectory() && !bFile.isDirectory())
   		{
   			result = -1;
   			
   		}//IF
   		else if(bFile.isDirectory() && !aFile.isDirectory())
   		{
   			result = 1;
   			
   		}//ELSE IF
   		
   		if(result == 0)
   		{
   			result = AlphaNumSort.compareAlpha(aFile.getName(), bFile.getName());
   			
   		}//IF
   		
   		return result;
   		
   	}//METHOD
   	
}//CLASS

