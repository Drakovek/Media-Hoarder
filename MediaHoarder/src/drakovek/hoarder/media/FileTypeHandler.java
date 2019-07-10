package drakovek.hoarder.media;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.processing.ParseINI;

/**
 * Contains methods for determining a file's file type (audio, image, etc.)
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class FileTypeHandler 
{	
	/**
	 * Header for the audio extensions file.
	 * 
	 * @since 2.0
	 */
	public static final String AUDIO_HEADER = "[AUDIO EXTENSIONS]"; //$NON-NLS-1$
	
	/**
	 * Header for the image extensions file.
	 * 
	 * @since 2.0
	 */
	public static final String IMAGE_HEADER = "[IMAGE EXTENSIONS]"; //$NON-NLS-1$
	
	/**
	 * Header for the text extensions file.
	 * 
	 * @since 2.0
	 */
	public static final String TEXT_HEADER = "[TEXT EXTENSIONS]"; //$NON-NLS-1$
	
	/**
	 * Header for the video extensions file.
	 * 
	 * @since 2.0
	 */
	public static final String VIDEO_HEADER = "[VIDEO EXTENSIONS]"; //$NON-NLS-1$
	
	/**
	 * Embedded audio extensions.
	 * 
	 * @since 2.0
	 */
	private static final String[] EMBEDDED_AUDIO = {"flac", "mp3", "ogg", "wav"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	
	/**
	 * Embedded image extensions.
	 * 
	 * @since 2.0
	 */
	private static final String[] EMBEDDED_IMAGE = {"bmp", "gif", "jpg", "jpeg", "png"};  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	
	/**
	 * Embedded text extensions.
	 * 
	 * @since 2.0
	 */
	private static final String[] EMBEDDED_TEXT = {"doc", "docx", "pdf", "rtf", "txt"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	
	/**
	 * Embedded video extensions.
	 * 
	 * @since 2.0
	 */
	private static final String[] EMBEDDED_VIDEO = {"avi", "mkv", "mov", "mp4"};  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	
	/**
	 * Audio extensions file.
	 * 
	 * @since 2.0
	 */
	private File audioFile;
	
	/**
	 * Image extensions file.
	 * 
	 * @since 2.0
	 */
	private File imageFile;
	
	/**
	 * Text extensions file.
	 * 
	 * @since 2.0
	 */
	private File textFile;
	
	/**
	 * Video extensions file.
	 * 
	 * @since 2.0
	 */
	private File videoFile;
	
	/**
	 * Audio extensions added by the user
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> userAudio;
	
	/**
	 * Image extensions added by the user
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> userImage;
	
	/**
	 * Text extensions added by the user
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> userText;
	
	/**
	 * Video extensions added by the user
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> userVideo;
	
	/**
	 * Initializes the FileTypeHandler class by loading then reformatting extension files.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public FileTypeHandler(DSettings settings)
	{
		audioFile = null;
		imageFile = null;
		textFile = null;
		videoFile = null;
		File extensionFolder = DReader.getDirectory(settings.getDataFolder(), "extensions"); //$NON-NLS-1$
		if(extensionFolder != null && extensionFolder.isDirectory())
		{
			audioFile = new File(extensionFolder, "audio.ini"); //$NON-NLS-1$
			imageFile = new File(extensionFolder, "image.ini"); //$NON-NLS-1$
			textFile = new File(extensionFolder, "text.ini"); //$NON-NLS-1$
			videoFile = new File(extensionFolder, "video.ini"); //$NON-NLS-1$
			
		}//IF
		
		//READ EXTENSIONS
		userAudio = readExtensions(audioFile, AUDIO_HEADER, EMBEDDED_AUDIO);
		userImage = readExtensions(imageFile, IMAGE_HEADER, EMBEDDED_IMAGE);
		userText = readExtensions(textFile, TEXT_HEADER, EMBEDDED_TEXT);
		userVideo = readExtensions(videoFile, VIDEO_HEADER, EMBEDDED_VIDEO);
		
		//SAVE EXTENSIONS
		saveExtensions(audioFile, AUDIO_HEADER, EMBEDDED_AUDIO, userAudio);
		saveExtensions(imageFile, IMAGE_HEADER, EMBEDDED_IMAGE, userImage);
		saveExtensions(textFile, TEXT_HEADER, EMBEDDED_TEXT, userText);
		saveExtensions(videoFile, VIDEO_HEADER, EMBEDDED_VIDEO, userVideo);
		
	}//CONSTRUCTOR
	
	/**
	 * Returns a list of user added extensions from an extension file
	 * 
	 * @param file Extension file to check
	 * @param header Header of the extension file
	 * @param embedded Embedded extensions to omit from the returned list
	 * @return List of user added extensions
	 * @sin 2.0
	 */
	private static ArrayList<String> readExtensions(final File file, final String header, final String[] embedded)
	{
		ArrayList<String> contents = DReader.readFile(file);
		contents = ParseINI.getSection(header, contents);
		
		ArrayList<String> userExtensions = new ArrayList<>();
		for(String line: contents)
		{
			int i = line.indexOf('=') + 1;
			if(i < line.length())
			{
				userExtensions.add(line.substring(i).toLowerCase());
				
			}//IF
			
		}//FOR
		
		//REMOVE EMBEDDED TYPES
		for(int i = 0; i < embedded.length; i++)
		{
			for(int k = 0; k < userExtensions.size(); k++)
			{
				if(embedded[i].equals(userExtensions.get(k)))
				{
					userExtensions.remove(k);
					k--;
					
				}//IF
				
			}//FOR
			
		}//FOR
		
		//REMOVE DUPLICATES
		for(int i = 0; i < userExtensions.size(); i++)
		{
			for(int k = i + 1; k < userExtensions.size(); k++)
			{
				if(userExtensions.get(i).equals(userExtensions.get(k)))
				{
					userExtensions.remove(k);
					
				}//IF
				
			}//FOR
			
		}//FOR
	
		return userExtensions;
		
	}//METHOD
	
	/**
	 * Saves user and embedded extensions into a given extension file.
	 * 
	 * @param file Extension file to save
	 * @param header Header of the extension file
	 * @param embedded Embedded extensions to include in the extension file
	 * @param userExtensions User added extensions to include in the extension file
	 * @since 2.0
	 */
	private static void saveExtensions(final File file, final String header, final String[] embedded, final ArrayList<String> userExtensions)
	{
		ArrayList<String> contents = new ArrayList<>();
		contents.add(header);
		for(int i = 0; i < userExtensions.size(); i++)
		{
			contents.add(ParseINI.getAssignmentString(Integer.toString(i), userExtensions.get(i)));
			
		}//FOR
		
		contents.add(new String());
		contents.add("[EMBEDDED]"); //$NON-NLS-1$
		for(int i = 0; i < embedded.length; i++)
		{
			contents.add(ParseINI.getAssignmentString(Integer.toString(i), embedded[i]));
			
		}//FOR
		
		DWriter.writeToFile(file, contents);
		
	}//METHOD
	
	/**
	 * Returns whether the given file is an audio file
	 * 
	 * @param file Input File
	 * @return Whether the file is an audio file
	 * @since 2.0
	 */
	public boolean isAudioFile(final File file)
	{
		return isFileType(file.getAbsolutePath(), EMBEDDED_AUDIO, userAudio);
		
	}//METHOD
	
	/**
	 * Returns whether the given file is an image file
	 * 
	 * @param file Input File
	 * @return Whether the file is an image file
	 * @since 2.0
	 */
	public boolean isImageFile(final File file)
	{
		return isFileType(file.getAbsolutePath(), EMBEDDED_IMAGE, userImage);
		
	}//METHOD
	
	/**
	 * Returns whether the given file is a text file
	 * 
	 * @param file Input File
	 * @return Whether the file is a text file
	 * @since 2.0
	 */
	public boolean isTextFile(final File file)
	{
		return isFileType(file.getAbsolutePath(), EMBEDDED_TEXT, userText);
		
	}//METHOD
	
	/**
	 * Returns whether the given file is a video file
	 * 
	 * @param file Input File
	 * @return Whether the file is a video file
	 * @since 2.0
	 */
	public boolean isVideoFile(final File file)
	{
		return isFileType(file.getAbsolutePath(), EMBEDDED_VIDEO, userVideo);
		
	}//METHOD
	
	/**
	 * Determines if the filename given is a certain file type based on whether it uses one of the given extensions.
	 * 
	 * @param filename Filename to check
	 * @param embedded Embedded extensions to check against
	 * @param userExtensions User added extensions to check against
	 * @return Whether the given filename has any of the given extensions
	 * @since 2.0
	 */
	private static boolean isFileType(final String filename, final String[] embedded, final ArrayList<String> userExtensions)
	{
		boolean isType = false;
		
		for(int i = 0; i < embedded.length; i++)
		{
			if(filename.endsWith(Character.toString('.') + embedded[i]))
			{
				isType = true;
				break;
				
			}//IF
			
		}//FOR
		
		if(!isType)
		{
			for(int i = 0; i < userExtensions.size(); i++)
			{
				if(filename.endsWith(Character.toString('.') + userExtensions.get(i)))
				{
					isType = true;
					break;
					
				}//IF
				
			}//FOR
			
		}//IF
		
		return isType;
		
	}//METHOD
	
}//CLASS
