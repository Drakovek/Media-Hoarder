package drakovek.hoarder.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Contains methods for downloading and gathering data with HtmlUnit.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class Downloader
{
	/**
	 * Main WebClient for the class.
	 * 
	 * @since 2.0
	 */
	private WebClient client;
	
	/**
	 * Main HtmlPage for the class.
	 * 
	 * @since 2.0
	 */
	private HtmlPage page;

	/**
	 * InputStream for reading URL data.
	 * 
	 * @since 2.0
	 */
	private InputStream myInput;
	
	/**
	 * Initializes the Downloader class.
	 * 
	 * @param clientMethods ClientMethods object.
	 * @since 2.0
	 */
	public Downloader(ClientMethods clientMethods)
	{	
	}//CONSTRUCTOR
	
	/**
	 * Downloads file from a given URL and saves to given file.
	 * 
	 * @param url URL to download.
	 * @param outputFile File to save to
	 * @since 2.0
	 */
	public void downloadFile(final String url, final File outputFile)
	{
		UnexpectedPage myUnexpectedPage;
		myInput = null;
		OutputStream myOutput = null;
		
		try
		{
			myUnexpectedPage = client.getPage(url);
			myInput = myUnexpectedPage.getWebResponse().getContentAsStream();
			myOutput = new FileOutputStream(outputFile);
			
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = myInput.read(buffer)) != -1) 
			{
		        myOutput.write(buffer, 0, bytesRead);
		    
			}//WHILE
			
		}//TRY
		catch(Exception e)
		{
			basicDownload(url, outputFile);
			
		}//CATCH
		finally
		{
			try
			{
				if(myInput != null)
				{
					myInput.close();
				
				}//IF
				
				if(myOutput != null)
				{
					myOutput.close();
				
				}//IF
				
			}//TRY
			catch (IOException e){}
			
			
		}//FINALLY
	
	}//METHOD
	
	/**
	 * Downloads file from a given URL and saves to given file using basic Java UrlConnection.
	 * 
	 * @param url URL to download.
	 * @param outputFile File to save to
	 * @since 2.0
	 */
	public static void basicDownload(String url, File outputFile)
    {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        byte[] myByte = null;
        int data = 0;
        byte[] fullData = null;
        FileOutputStream fileOutput = null;
        
        try
        {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");  //$NON-NLS-1$//$NON-NLS-2$
            connection.connect();
                
            inputStream  = new BufferedInputStream(connection.getInputStream());
               
            outputStream = new ByteArrayOutputStream();
            myByte = new byte[1024];
            data = 0;
            while(-1 != (data = inputStream.read(myByte)))
            {
                outputStream.write(myByte, 0, data);
                
            }//WHILE
           
            fullData = outputStream.toByteArray();
                
            fileOutput = new FileOutputStream(outputFile);
            fileOutput.write(fullData);
            
        }//TRY
        catch(IOException e){}
        finally
        {
            try
            {
            	if(outputStream != null)
            	{
            		outputStream.close();
            		
            	}//IF
            	
            	if(inputStream != null)
            	{
            		inputStream.close();
            		
            	}//IF
            	
            	if(fileOutput != null)
            	{
            		fileOutput.close();
            		
            	}//IF
	            
			}//TRY
            catch(IOException e){}
            
        }//FINALLY
        
    }//METHOD
	
	/**
	 * Returns the object's main WebClient
	 * 
	 * @return Web Client
	 * @since 2.0
	 */
	public WebClient getClient()
	{
		return client;
		
	}//METHOD
	
	/**
	 * Returns the object's main HtmlPage
	 * 
	 * @return HTML Page
	 * @since 2.0
	 */
	public HtmlPage getPage()
	{
		return page;
		
	}//METHOD
	
	/**
	 * Sets the main HtmlPage to the data loaded from a given URL.
	 * 
	 * @param url Input URL
	 * @since 2.0
	 */
	public void setPage(final String url)
	{
		try
		{
			page = client.getPage(url);
			
		}//TRY
		catch(Exception e)
		{
			page = null;
			
		}//CATCH
		
	}//METHOD
	
	/**
	 * Sets the main HtmlPage to a separate given HtmlPage
	 * 
	 * @param page Input HtmlPage
	 * @since 2.0
	 */
	public void setPage(HtmlPage page)
	{
		this.page = page;
		
	}//METHOD
	
	/**
	 * Sets the main WebClient as a new WebClient object.
	 * 
	 * @since 2.0
	 */
	public void setNewClient()
	{
		client = new WebClient(BrowserVersion.BEST_SUPPORTED);
		
	}//METHOD
	
	/**
	 * Gets the string value of an DOM Attribute
	 * 
	 * @param attribute DomAttr to get value from.
	 * @return String value of DOM Attribute
	 * @since 2.0
	 */
	public static String getAttribute(final DomAttr attribute)
	{
		String value = "value="; //$NON-NLS-1$
		String xml = new String();
		if(attribute != null)
		{
			xml = attribute.asXml();
			
		}//IF
		
		String finalString = new String();
		int start = 0;
		int end = 0;
		
		for(end = xml.length() - 1; end >= 0 && xml.charAt(end) != ']'; end--);
		for(start = end - (value.length() + 1); start >= 0 && !xml.substring(start, start + value.length()).equals(value); start--);
		for(start = start + 1; xml.charAt(start) != '='; start++); start++;
		
		if(end > start && end > -1 && start > -1)
		{
			finalString = xml.substring(start, end);
			
		}//IF
			
		return finalString;
		
	}//METHOD
	
	/**
	 * Gets the interior text from a DOM element with default values.
	 * 
	 * @param element DOM Element
	 * @return Interior text of a DOM element
	 * @since 2.0
	 */
	public static String getElement(final DomElement element)
	{	
		return getElement(element, false, true);
		
	}//METHOD
	
	/**
	 * Gets the interior text from a DOM element.
	 * 
	 * @param element DOM Element
	 * @param removeAllSpaces Whether to remove all extraneous spaces from the DOM element
	 * @param removeEnds Whether to remove the HTML tags at the ends of the DOM element
	 * @return Interior text of a DOM element
	 * @since 2.0
	 */
	public static String getElement(final DomElement element, final boolean removeAllSpaces, final boolean removeEnds)
	{
		String xml = element.asXml();
		String finalString = new String();
		int start = 0;
		int end = 0;
		
		while(end < xml.length())
		{
			for(end = start; end < xml.length() && xml.charAt(end) != '\r' && xml.charAt(end) != '\n'; end++);
			
			if(removeAllSpaces == true)
			{
				for(end = end - 1; end >= start && xml.charAt(end) == ' '; end--); end++;
				
			}//IF
			
			finalString = finalString + xml.substring(start, end);
			
			for(start = end; start < xml.length() && xml.charAt(start) != '\n'; start++);
			for(start = start + 1; start < xml.length() && xml.charAt(start) == ' '; start++);
			end = start;
			
		}//WHILE
		
		if(removeEnds == true && finalString.contains(Character.toString('<')) && finalString.contains(Character.toString('>')))
		{
			for(start = 0; finalString.charAt(start) != '>'; start++); start++;
			for(end = finalString.length() - 1; finalString.charAt(end) != '<'; end--);
			if(start < end)
			{
				finalString = finalString.substring(start, end);
				
			}//IF
			else
			{
				finalString = new String();
				
			}//ELSE
		
		}//IF
		
		return finalString;
		
	}//METHOD
	
}//CLASS
