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
 * @since 2.0
 */
public class DWriter 
{
	/**
	 * Main bufferedWriter for writing data.
	 * 
	 * @since 2.0
	 */
	private static BufferedWriter bufferedWriter;
	
	/**
	 * Writes an ArrayList<String> to a text file.
	 * 
	 * @param outputFile File to be written
	 * @param fileContents Contents to be written.
	 * @since 2.0
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
					bufferedWriter.flush();
					bufferedWriter.close();
            	
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
	 * @since 2.0
	 */
    public static void writeToFile(final File outputFile, final String fileContents)
    {
    	ArrayList<String> contentList = new ArrayList<>();
    	contentList.add(fileContents);
    	
    	writeToFile(outputFile, fileContents);

    }//METHOD
    
}//CLASS
