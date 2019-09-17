package drakovek.hoarder.file;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Contains methods for writing data to local disk.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DWriter 
{
	/**
	 * Main bufferedWriter for writing data.
	 */
	private static BufferedWriter bufferedWriter;
	
	/**
	 * Writes an ArrayList<String> to a text file.
	 * 
	 * @param outputFile File to be written
	 * @param fileContents Contents to be written.
	 */
	public static void writeToFile(final File outputFile, final ArrayList<String> fileContents)
	{
		boolean writeFile = true;
		
		if(outputFile.exists())
		{
			if(!outputFile.delete())
			{
				writeFile = false;
				
			}//IF
			
		}//IF
		
		if(writeFile)
		{
			bufferedWriter = null;
			
			try
			{
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8));
				
				for (int q = 0; q < fileContents.size(); q++)
				{
					if(q > 0)
					{
						bufferedWriter.append(Character.toString('\r') + Character.toString('\n'));
						
					}//IF
					
					bufferedWriter.append(fileContents.get(q));
					
				}//FOR
				
			}//TRY
			catch (IOException e)
			{
				System.out.println("Failed to write " + outputFile.getAbsolutePath() + " - Writer.writeToFile");  //$NON-NLS-1$//$NON-NLS-2$
				
			}//CATCH
			finally
			{
				try
				{
					if(bufferedWriter != null)
					{
						bufferedWriter.flush();
						bufferedWriter.close();
						
					}//IF
            	
				}//TRY
				catch(IOException f)
				{
					System.out.println("Failed to close bufferedWriter - Writer.writeToFile"); //$NON-NLS-1$
					
				}//CATCH
				
			}//FINALLY
			
		}//IF
		
	}//METHOD

	/**
	 * Writes a String to a text file.
	 * 
	 * @param outputFile File to be written
	 * @param fileContents Contents to be written.
	 */
    public static void writeToFile(final File outputFile, final String fileContents)
    {
    	ArrayList<String> contentList = new ArrayList<>();
    	contentList.add(fileContents);
    	
    	writeToFile(outputFile, contentList);

    }//METHOD
    
    /**
     * Returns a string that is acceptable for use in a file name from a given text string.
     * 
     * @param text Given String
     * @return File Friendly Name
     */
    public static String getFileFriendlyName(final String text)
    {	
    	//REMOVE UNWANTED CHARACTERS
    	StringBuilder builder = new StringBuilder();
    	for(int i = 0; i < text.length(); i++)
    	{
    		char myChar = text.charAt(i);
    		if((myChar > 47 && myChar < 58) || (myChar > 64 && myChar < 91) || (myChar > 96 && myChar < 123) || myChar == ' ')
    		{
    			builder.append(myChar);
    			
    		}//IF
    		else
    		{
    			builder.append('-');
    			
    		}//ELSE
    		
    	}//FOR
    	
    	//REMOVE FILLER FROM START AND END
    	while(builder.length() > 0 && (builder.charAt(0) == ' ' || builder.charAt(0) == '-'))
    	{
    		builder.deleteCharAt(0);
    		
    	}//WHILE
    	
    	while(builder.length() > 0 && (builder.charAt(builder.length() - 1) == ' ' || builder.charAt(builder.length() - 1) == '-'))
    	{
    		builder.deleteCharAt(builder.length() - 1);
    		
    	}//WHILE
    	
    	//REMOVE DUPLICATE SPACER
    	for(int i = 1; i < builder.length(); i++)
    	{
    		if((builder.charAt(i) == '-' && builder.charAt(i - 1) == '-') || (builder.charAt(i) == ' ' && builder.charAt(i - 1) == ' '))
    		{
    			builder.deleteCharAt(i);
    			i--;
    			
    		}//IF
    		
    	}//FOR
    	
    	//REMOVE OUT-OF-PLACE HYPHENS
    	for(int i = 1; i < (builder.length() - 1); i++)
    	{
    		if(builder.charAt(i) == '-' && ((builder.charAt(i - 1) == ' ' && builder.charAt(i + 1) != ' ') || (builder.charAt(i + 1) == ' ' && builder.charAt(i - 1) != ' ')))
    		{
    			builder.deleteCharAt(i);
    			i--;
    			
    		}//IF
    		
    	}//FOR
    	
    	return builder.toString();
    	
    }//METHOD
    
}//CLASS
