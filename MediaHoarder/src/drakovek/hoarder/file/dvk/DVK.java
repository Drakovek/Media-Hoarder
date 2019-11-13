package drakovek.hoarder.file.dvk;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.apache.tika.Tika;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.processing.ExtensionMethods;
import drakovek.hoarder.processing.StringMethods;

/**
 * Contains methods for reading writing and handling a single Drakovek Media File (DVK)
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DVK
{
	/**
	 * Empty ID variable used identify when there are no DVKs either following or preceding the current DVK in a sequence.
	 */
	public static final String EMPTY_ID = "XX"; //$NON-NLS-1$
	
	/**
	 * Empty section variable to identify when a section of a sequence has no section title
	 */
	public static final String EMPTY_SECTION = ":"; //$NON-NLS-1$
	
	/**
	 * Extension used for DVK files
	 */
	public static final String DVK_EXTENSION = ".dvk"; //$NON-NLS-1$
	
	/**
	 * Array of file types and their corresponding extensions
	 */
	private static final String[][] FILE_TYPES = {{"image/jpeg", ".jpg"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"image/png", ".png"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"image/gif", ".gif"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"image/vnd.adobe.photoshop", ".psd"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"image/bmp", ".bmp"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"text/html", ".html"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"text/plain", ".txt"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"application/pdf", ".pdf"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"application/rtf", ".rtf"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"application/msword", ".doc"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"application/x-shockwave-flash", ".swf"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"video/quicktime", ".mov"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"video/x-ms-wmv", ".wmv"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"video/x-msvideo", ".avi"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"video/mp4", ".mp4"}, //$NON-NLS-1$ //$NON-NLS-2$
												  {"video/webm", ".webm"},  //$NON-NLS-1$//$NON-NLS-2$
												  {"audio/mpeg", ".mp3"}};  //$NON-NLS-1$//$NON-NLS-2$
	
	
	//Dvk
	
	/**
	 * File for the currently selected DVK
	 */
	private File dvkFile;
	
	/**
	 * Unique ID for the currently selected DVK
	 */
	private String id;
	
	/**
	 * Title of the DVK media
	 */
	private String title;
	
	/**
	 * Artist(s) of the DVK media
	 */
	private String[] artists;
	
	/**
	 * Time of publishing for the DVK media (Structured YYYYMMDDhhmm. Example: April 22, 2001 @ 4:30PM = 200104211630)
	 */
	private long time;
	
	/**
	 * Original web tags for the DVK
	 */
	private String[] webTags;
	
	/**
	 * Description of the DVK media
	 */
	private String description;
	
	/**
	 * The URL for the page that the DVK originates from
	 */
	private String pageURL;
	
	/**
	 * The URL for the direct media download URL that the DVK originates from
	 */
	private String directURL;
	
	/**
	 * URL for the direct media download URL for the secondary media file
	 */
	private String secondaryURL;
	
	/**
	 * Media File linked to the DVK
	 */
	private File mediaFile;
	
	/**
	 * Media File linked to the DVK for use as a secondary media file
	 */
	private File secondaryFile;
	
	/**
	 * Initializes DVK to represent an empty DVK file.
	 */
	public DVK()
	{
		dvkFile = null;
		clearDVK();
		
	}//CONSTRUCTOR
	
	/**
	 * Initializes DVK to represent given DVK file.
	 * 
	 * @param dvkFile DVK File to load
	 */
	public DVK(final File dvkFile)
	{
		this.dvkFile = dvkFile;
		loadDVK();
		
	}//CONSTRUCTOR
	
	/**
	 * Clears all DVK variables so this DVK object represents an empty DVK File.
	 */
	private void clearDVK()
	{
		//DVK
		id = null;
		
		//INFO
		title = null;
		artists = null;
		time = 0L;
		webTags = null;
		description = null;
		
		//WEB
		pageURL = null;
		directURL = null;
		secondaryURL = null;
		
		//FILE
		mediaFile = null;
		secondaryFile = null;
		
	}//METHOD
	
	/**
	 * Loads DVK info from DVK File so the DVK object represents a given DVK File.
	 */
	public void loadDVK()
	{
		clearDVK();
		if(dvkFile != null && dvkFile.getAbsolutePath().endsWith(DVK_EXTENSION))
		{
			StringBuilder dvkString = new StringBuilder();
			ArrayList<String> json = DReader.readFile(dvkFile);
			for(int i = 0; i < json.size(); i++)
			{
				dvkString.append(json.get(i));
				
			}//FOR
				
			JSONObject dvk = new JSONObject(dvkString.toString());
			try
			{
				setID(dvk.getString("id")); //$NON-NLS-1$
				//IF ID TAG EXISTS AND IS PROPER DVK FILE
				if(getID() != null && getID().length() > 0 && dvk.getString("file_type").equals("dvk"))  //$NON-NLS-1$//$NON-NLS-2$
				{
					JSONObject info = dvk.getJSONObject("info"); //$NON-NLS-1$
					//INFO
					try
					{
						setTitle(info.getString("title")); //$NON-NLS-1$
					}//TRY
					catch(JSONException e)
					{
						setTitle(""); //$NON-NLS-1$
						
					}//CATCH
					
					JSONArray jsonArray = info.getJSONArray("artists"); //$NON-NLS-1$
					ArrayList<String> artistList = new ArrayList<>();
					for(int i = 0; i < jsonArray.length(); i++)
					{
						artistList.add(jsonArray.getString(i));
						
					}//FOR
					setArtists(artistList);
					
					try
					{
						setTime(info.getString("time")); //$NON-NLS-1$
						
					}//TRY
					catch(JSONException e)
					{
						setTime(0L);
						
					}//CATCH
					
					try
					{
						jsonArray = info.getJSONArray("web_tags"); //$NON-NLS-1$
						ArrayList<String> tags = new ArrayList<>();
						for(int i = 0; i < jsonArray.length(); i++)
						{
							tags.add(jsonArray.getString(i));
						
						}//FOR
						setWebTags(tags);
					}//TRY
					catch(JSONException e)
					{
						setWebTags(null);
						
					}//CATCH
					
					try
					{
						setDescription(info.getString("description")); //$NON-NLS-1$
						
					}//TRY
					catch(JSONException e)
					{
						setDescription(null);
						
					}//CATCH
					
					//WEB
					JSONObject web = dvk.getJSONObject("web"); //$NON-NLS-1$
					setPageURL(web.getString("page_url")); //$NON-NLS-1$
					
					try
					{
						setDirectURL(web.getString("direct_url")); //$NON-NLS-1$
						
					}//TRY
					catch(JSONException e)
					{
						setDirectURL(null);
						
					}//CATCH
					
					try
					{
						setSecondaryURL(web.getString("secondary_url")); //$NON-NLS-1$
					
					}//TRY
					catch(JSONException e)
					{
						setSecondaryURL(null);
						
					}//CATCH
					
					//FILE
					JSONObject file = dvk.getJSONObject("file"); //$NON-NLS-1$
					setMediaFile(file.getString("media_file")); //$NON-NLS-1$
					
					try
					{
						setSecondaryFile(file.getString("secondary_file"));	 //$NON-NLS-1$
						
					}//TRY
					catch(JSONException e)
					{
						secondaryFile = null;
						
					}//CATCH
					
					
				}//IF
				
			}//TRY
			catch(JSONException e)
			{
				clearDVK();
				
			}//CATCH
			
		}//IF
		
	}//METHOD
	
	/**
	 * Writes a DVK file to dvkFile
	 *
	 * @return Whether the file was successfully written
	 */
	public boolean writeDVK()
	{
		return writeDVK(true);
		
	}//METHOD
	
	/**
	 * Writes a DVK file to dvkFile
	 *
	 * @param checkFileType Whether to check the file file type of linked media files and change extensions accordingly if necessary
	 * @return Whether the file was successfully written
	 */
	public boolean writeDVK(final boolean checkFileType)
	{
		if(isValidWrite())
		{
			JSONObject dvk = new JSONObject();
			dvk.put("file_type", "dvk"); //$NON-NLS-1$ //$NON-NLS-2$
			dvk.put("id", getID()); //$NON-NLS-1$
			
			//INFO
			JSONObject info = new JSONObject();
			
			if(title != null)
			{
				info.put("title", getTitle()); //$NON-NLS-1$
			
			}//IF
			else
			{
				info.put("title", ""); //$NON-NLS-1$ //$NON-NLS-2$
				
			}//ELSE
			
			if(getArtists() != null && getArtists().length > 0)
			{
				info.put("artists", new JSONArray(getArtists())); //$NON-NLS-1$
				
			}//IF
			
			if(getTime() != 0L)
			{
				info.put("time", getTimeString()); //$NON-NLS-1$
				
			}//IF
			
			if(getWebTags() != null && getWebTags().length > 0)
			{
				info.put("web_tags", new JSONArray(getWebTags())); //$NON-NLS-1$
				
			}//IF
			
			if(getDescription() != null && getDescription().length() > 0)
			{
				info.put("description", getDescription()); //$NON-NLS-1$
				
			}//IF
			
			dvk.put("info", info); //$NON-NLS-1$
			JSONObject web = new JSONObject();
			
			//WEB
			if(getPageURL() != null && getPageURL().length() > 0)
			{
				web.put("page_url", getPageURL()); //$NON-NLS-1$
				
			}//IF
			
			if(getDirectURL() != null && getDirectURL().length() > 0)
			{
				web.put("direct_url", getDirectURL()); //$NON-NLS-1$
				
			}//IF
			
			if(getSecondaryURL() != null && getSecondaryURL().length() > 0)
			{
				web.put("secondary_url", getSecondaryURL()); //$NON-NLS-1$
				
			}//IF
			
			dvk.put("web", web); //$NON-NLS-1$
			
			//FILE
			JSONObject file = new JSONObject();
			file.put("media_file", getMediaFile().getName()); //$NON-NLS-1$
			
			if(getSecondaryFile() != null)
			{
				file.put("secondary_file", getSecondaryFile().getName()); //$NON-NLS-1$
			
			}//IF
			
			dvk.put("file", file); //$NON-NLS-1$
			
			//WRITE TO FILE
			DWriter.writeToFile(dvkFile, dvk.toString());
			if(dvkFile.exists())
			{	
				if(checkFileType)
				{
					Tika tika = new Tika();
					String extension = ExtensionMethods.getExtension(getMediaFile());
					String secondaryExtension = ExtensionMethods.getExtension(getSecondaryFile());
					
					try
					{
						String response = tika.detect(getMediaFile());
						for(int i = 0; i < FILE_TYPES.length; i++)
						{
							if(FILE_TYPES[i][0].equals(response))
							{
								extension = FILE_TYPES[i][1];
								break;
								
							}//IF
							
						}//FOR
						
						if(getSecondaryFile() != null)
						{
							response = tika.detect(getSecondaryFile());
							for(int i = 0; i < FILE_TYPES.length; i++)
							{
								if(FILE_TYPES[i][0].equals(response))
								{
									secondaryExtension = FILE_TYPES[i][1];
									break;
									
								}//IF
								
							}//FOR
							
						}//IF
						
					}catch (IOException e){}
					
					rename(ExtensionMethods.removeExtension(getDvkFile()), extension, secondaryExtension);
					
				}//IF
				
				return true;
				
			}//IF
			
		}//IF
		
		return false;
		
	}//METHOD
	
	/**
	 * Checks if the current state of the DVK object can be written to make a valid DVK. Must have a valid name for a file, and a non-empty ID and media file.
	 *
	 * @return Whether the current DVK is valid to write
	 */
	public boolean isValidWrite()
	{
		boolean valid = true;
		
		if(dvkFile == null || dvkFile.isDirectory() || !dvkFile.getName().endsWith(DVK_EXTENSION))
		{
			valid = false;
			
		}//IF
		
		if(getID() == null || getID().length() == 0)
		{
			valid = false;
			
		}//IF
		
		if(getMediaFile() == null)
		{
			valid = false;
			
		}//IF
		
		return valid;
		
	}//METHOD
	
	/**
	 * Checks whether the current DVK file is valid to read.
	 * 
	 * @return Whether the current DVK file is valid to read
	 */
	public boolean isValidRead()
	{
		boolean valid = true;
		
		if(dvkFile == null || dvkFile.isDirectory() || !dvkFile.getName().endsWith(DVK_EXTENSION))
		{
			valid = false;
			
		}//IF
		
		return valid;
		
	}//METHOD
	
	/**
	 * Renames the DVK and its linked media.
	 * 
	 * @param filename Main Filename Body
	 * @param mediaExtension Extension to use for the media file. If null, uses the media file's current extension.
	 * @param secondaryExtension Extension to use for the secondary media file. If null, uses the secondary media file's current extension.
	 */
	public void rename(final String filename, final String mediaExtension, final String secondaryExtension)
	{
		if(isValidWrite())
		{
			File currentFolder = getDvkFile().getParentFile();
			String extension = mediaExtension;
			if(extension == null || extension.length() == 0)
			{
				extension = ExtensionMethods.getExtension(getMediaFile());
				
			}//IF
			
			File outFile = new File(currentFolder, filename + extension);
			File tempFile = new File(currentFolder, "xxxTEMPxxx" + getID()); //$NON-NLS-1$
			try
			{
				Files.move(getMediaFile().toPath(), tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
				Files.move(tempFile.toPath(), outFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
				setMediaFile(outFile);
				
				if(getSecondaryFile() != null)
				{
					extension = secondaryExtension;
					if(extension == null || extension.length() == 0)
					{
						extension = ExtensionMethods.getExtension(getSecondaryFile());
						
					}//IF
					
					outFile = new File(currentFolder, filename + extension);
					Files.move(getSecondaryFile().toPath(), tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
					Files.move(tempFile.toPath(), outFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
					setSecondaryFile(outFile);
					
				}//IF
				
				if(outFile.exists())
				{
					getDvkFile().delete();
					setDvkFile(new File(currentFolder, filename + DVK_EXTENSION));
					writeDVK(false);
					
				}//IF
				
			}//TRY
			catch(IOException e)
			{
				System.out.println("Failed to remame - DVK.rename()"); //$NON-NLS-1$
			
			}//CATCH
			
		}//IF
		
	}//METHOD
	
	//GETTERS AND SETTERS
	
	/**
	 * Returns dvkFile
	 * 
	 * @return dvkFile
	 */
	public File getDvkFile()
	{
		return dvkFile;
		
	}//METHOD
	
	/**
	 * Sets dvkFile.
	 * 
	 * @param dvkFile dvkFile
	 */
	public void setDvkFile(final File dvkFile)
	{
		this.dvkFile = dvkFile;
		
	}//METHOD
	
	/**
	 * Returns the DVK ID
	 * 
	 * @return DVK ID
	 */
	public String getID()
	{
		if(id != null)
		{
			return id.toUpperCase();
			
		}//IF
		
		return null;
		
	}//METHOD
	
	/**
	 * Sets the DVK ID
	 * 
	 * @param id DVK ID
	 */
	public void setID(final String id)
	{
		if(id != null)
		{
			this.id = id.toUpperCase();
			
		}//IF
		else
		{
			this.id = null;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns the default base filename based on DVK info (TITLE_ID)
	 * 
	 * @return Default Filename
	 */
	public String getDefaultFileName()
	{
		String currentTitle = getTitle();
		if(currentTitle == null)
		{
			currentTitle = new String();
			
		}//IF
		
		currentTitle = DWriter.getFileFriendlyName(currentTitle);
		
		String currentID = getID();
		if(currentID == null)
		{
			currentID = new String();
			
		}//IF
		
		return currentTitle + '_' + currentID;
		
	}//METHOD
	
	/**
	 * Returns the DVK Title
	 * 
	 * @return DVK Title
	 */
	public String getTitle()
	{
		if(title == null)
		{
			return null;
			
		}//IF
		
		return StringMethods.replaceHtmlEscapeCharacters(title);
		
	}//METHOD
	
	/**
	 * Sets the DVK Title
	 * 
	 * @param title DVK Title
	 */
	public void setTitle(final String title)
	{
		this.title = title;
		
	}//METHOD
	
	/**
	 * Gets the DVK Artist(s)
	 * 
	 * @return DVK Artists
	 */
	public String[] getArtists()
	{
		return artists;
		
	}//METHOD
	
	/**
	 * Sets the DVK Artists(s)
	 * 
	 * @param artists DVK Artists
	 */
	public void setArtists(final ArrayList<String> artists)
	{	
		if(artists != null && artists.size() > 0)
		{
			this.artists = StringMethods.arrayListToArray(artists);
			
		}//IF
		else
		{
			this.artists = null;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Sets the DVK Artist variable from a single artist String
	 * 
	 * @param artist DVK Artist
	 */
	public void setArtist(final String artist)
	{
		if(artist != null && artist.length() > 0)
		{
			artists = new String[1];
			artists[0] = artist;
			
		}//IF
		else
		{
			artists = null;
		
		}//ELSE
		
	}//METHOD
	
	/**
	 * Sets the DVK Time
	 * 
	 * @param time DVK Time
	 */
	public void setTime(final long time)
	{
		this.time = time;
		
	}//METHOD
	
	/**
	 * Sets the DVK Time from a DVK String
	 * 
	 * @param timeString DVK Time String
	 */
	private void setTime(final String timeString)
	{
		
		try
		{
			time = Long.parseLong(timeString);
			
		}//TRY
		catch(NumberFormatException e)
		{
			if(timeString.length() == 16)
			{
				setTime(timeString.substring(0, 4), timeString.substring(5, 7), timeString.substring(8, 10), timeString.substring(11, 13), timeString.substring(14));
				
			}//IF
			
		}//CATCH
		
		if(time < 101010000L)
		{
			time = 0L;
			
		}//IF

	}//METHOD
	
	/**
	 * Sets the DVK Time from String values.
	 * 
	 * @param yearString Year String
	 * @param monthString Month String
	 * @param dayString Day String
	 * @param hourString Hour String
	 * @param minuteString Minute String
	 */
	public void setTime(final String yearString, final String monthString, final String dayString, final String hourString, final String minuteString)
	{
		long returnTime = 0;
		boolean isValid = true;
		try
		{
			int year = Integer.parseInt(yearString);
			int month = Integer.parseInt(monthString);
			int day = Integer.parseInt(dayString);
			int hour = Integer.parseInt(hourString);
			int minute = Integer.parseInt(minuteString);
			
			if(
					(year < 1) ||
					(month < 1 || month > 12) ||
					(day < 1 || day > 31) ||
					(hour < 0 || hour > 23) ||
					(minute < 0 || minute > 59)
			)
			{
				isValid = false;
			}//IF
			
			returnTime = Long.parseLong(StringMethods.extendNumberString(year, 4) + StringMethods.extendNumberString(month, 2) + StringMethods.extendNumberString(day, 2) + StringMethods.extendNumberString(hour, 2) + StringMethods.extendNumberString(minute, 2));
			
		}//TRY
		catch(NumberFormatException e)
		{
			isValid = false;
		
		}//CATCH
		
		if(!isValid)
		{
			returnTime = 0;
			
		}//IF
		
		time = returnTime;
	
	}//METHOD
	
	/**
	 * Returns the DVK Time
	 * 
	 * @return DVK Time
	 */
	public long getTime()
	{
		return time;
		
	}//METHOD
	
	/**
	 * Gets DVK Time as a String
	 * 
	 * @return String representation of DVK Time
	 */
	public String getTimeString()
	{
		String longTimeString = StringMethods.extendNumberString(Long.toString(time), 12);
		
		return longTimeString.substring(0, 4) + Character.toString('/') + longTimeString.substring(4, 6) + Character.toString('/') + longTimeString.substring(6, 8) + Character.toString('|') + longTimeString.substring(8, 10) + Character.toString(':') + longTimeString.substring(10);
	
	}//METHOD
	
	/**
	 * Sets the DVK's web tags
	 * 
	 * @param webTags DVK Web Tags
	 */
	public void setWebTags(final ArrayList<String> webTags)
	{
		if(webTags != null && webTags.size() > 0)
		{
			this.webTags = StringMethods.arrayListToArray(webTags);
		
		}//IF
		else
		{
			this.webTags = null;
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Gets the DVK's web tags
	 * 
	 * @return DVK Web Tags
	 */
	public String[] getWebTags()
	{
		return webTags;
		
	}//METHOD
	
	/**
	 * Sets the description for the current DVK
	 * 
	 * @param description DVK Description
	 */
	public void setDescription(final String description)
	{
		this.description = description;
		
	}//METHOD
	
	/**
	 * Gets the description from the current DVK
	 * 
	 * @return DVK Description
	 */
	public String getDescription()
	{
		if(description == null)
		{
			return null;
			
		}//IF
		
		return StringMethods.addHtmlEscapesToHtml(description);
		
	}//METHOD
	
	/**
	 * Sets the DVK's Page URL
	 * 
	 * @param pageURL Page URL
	 */
	public void setPageURL(final String pageURL)
	{
		this.pageURL = pageURL;
		
	}//METHOD
	
	/**
	 * Returns the DVK's Page URL
	 * 
	 * @return Page URL
	 */
	public String getPageURL()
	{
		return pageURL;
		
	}//METHOD
	
	/**
	 * Sets the direct URL.
	 * 
	 * @param directURL Direct URL
	 */
	public void setDirectURL(final String directURL)
	{
		this.directURL = directURL;
		
	}//METHOD
	
	/**
	 * Returns the direct URL.
	 * 
	 * @return Direct URL
	 */
	public String getDirectURL()
	{
		return directURL;
		
	}//METHOD
	
	/**
	 * Sets the secondary URL.
	 * 
	 * @param secondaryURL Secondary URL
	 */
	public void setSecondaryURL(final String secondaryURL)
	{
		this.secondaryURL = secondaryURL;
		
	}//METHOD
	
	/**
	 * Returns the secondary URL.
	 * 
	 * @return Secondary URL
	 */
	public String getSecondaryURL()
	{
		return secondaryURL;
		
	}//METHOD
	
	/**
	 * Sets the linked media file.
	 * 
	 * @param mediaFile Media File
	 */
	public void setMediaFile(final File mediaFile)
	{
		this.mediaFile = mediaFile;
		
	}//METHOD
	
	/**
	 * Sets the linked media file based on a filename. Uses the same directory as the DVK File.
	 * 
	 * @param filename Name of the Media File
	 */
	public void setMediaFile(final String filename)
	{
		boolean failed = true;
		if(dvkFile != null && filename!= null && filename.length() > 0)
		{
			File parent = dvkFile.getParentFile();
			if(parent != null && parent.isDirectory())
			{
				mediaFile = new File(parent, filename);
				failed = false;
				
			}//IF
			
		}//IF
		
		if(failed)
		{
			mediaFile = null;
		
		}//ELSE
		
	}//METHOD
	
	/**
	 * Gets the linked media file.
	 * 
	 * @return Media File
	 */
	public File getMediaFile()
	{
		return mediaFile;
		
	}//METHOD
	
	/**
	 * Sets the secondary File.
	 * 
	 * @param secondaryFile Secondary File
	 */
	public void setSecondaryFile(final File secondaryFile)
	{
		this.secondaryFile = secondaryFile;
		
	}//METHOD
	

	/**
	 * Sets the linked secondary media file based on a filename. Uses the same directory as the DVK File.
	 * 
	 * @param filename Name of the Secondary Media File
	 */
	public void setSecondaryFile(final String filename)
	{
		boolean failed = true;
		if(dvkFile != null && filename!= null && filename.length() > 0)
		{
			File parent = dvkFile.getParentFile();
			if(parent != null && parent.isDirectory())
			{
				secondaryFile = new File(parent, filename);
				failed = false;
				
			}//IF
			
		}//IF
		
		if(failed)
		{
			secondaryFile = null;
		
		}//ELSE
		
	}//METHOD
	
	/**
	 * Returns the secondary file.
	 * 
	 * @return Secondary File
	 */
	public File getSecondaryFile()
	{
		return secondaryFile;
		
	}//METHOD
	
}//CLASS
