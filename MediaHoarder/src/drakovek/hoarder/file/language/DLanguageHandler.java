package drakovek.hoarder.file.language;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.processing.ParseINI;
import drakovek.hoarder.processing.sort.FileSort;

/**
 * Contains methods for retrieving text values in a specified language from local files containing language data.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DLanguageHandler
{
	/**
	 * Name of the program's language folder.
	 * 
	 * @since 2.0
	 */
	private static final String LANGUAGE_FOLDER = "language"; //$NON-NLS-1$
	
	/**
	 * Header text for all language files.
	 * 
	 * @since 2.0
	 */
	private static final String LANGUAGE_HEADER = "[LANGUAGE FILE]"; //$NON-NLS-1$
	
	/**
	 * Variable for the language of a particular language file.
	 * 
	 * @since 2.0
	 */
	private static final String LANGUAGE_VARIABLE = "language_value"; //$NON-NLS-1$
	
	/**
	 * The program's language folder located in the program's data folder.
	 * 
	 * @since 2.0
	 */
	private File languageFolder;
	
	/**
	 * INI formatted text for the currently selected language.
	 * 
	 * @since 2.0
	 */
	private ArrayList<String> languageInfo;
	
	/**
	 * Initializes the DLanguage Class.
	 * 
	 * @param dataFolder Main Data Folder for the program
	 * @param languageName Name of the language to use for the program's text
	 * @since 2.0
	 */
	public DLanguageHandler(final File dataFolder, final String languageName)
	{
		languageFolder = DReader.getDirectory(dataFolder, LANGUAGE_FOLDER);
		
		setLanguage(languageName);
		
	}//METHOD
	
	/**
	 * Sets the current language of the project, determining which language files to read from.
	 * 
	 * @param languageName Name of the selected language
	 * @since 2.0
	 */
	public void setLanguage(final String languageName)
	{	
		ArrayList<File> languageFiles = getLanguageFiles();
		languageInfo = new ArrayList<>();
		
		if(languageName != null && languageName.length() > 0)
		{
			for(File languageFile : languageFiles)
			{
				ArrayList<String> info = DReader.readFile(languageFile);
				if(ParseINI.getStringValue(LANGUAGE_HEADER, LANGUAGE_VARIABLE, info, new String()).equals(languageName))
				{
					languageInfo.addAll(info);
					
				}//IF
			
			}//FOR
		
		}//IF
		else if(languageFiles.size() > 0)
		{
			languageFiles = FileSort.sortFiles(languageFiles);
			ArrayList<String> info = DReader.readFile(languageFiles.get(0));
			setLanguage(ParseINI.getStringValue(LANGUAGE_HEADER, LANGUAGE_VARIABLE, info, new String()));
			
		}//ELSE IF
		
	}//METHOD

	/**
	 * Returns an ArrayList<String> of all the language files within the program's language folder.
	 * 
	 * @return ArrayList<String> of language files
	 * @since 2.0
	 */
	private ArrayList<File> getLanguageFiles()
	{
		String[] extensions = {ParseINI.INI_EXTENSION};
		ArrayList<File> languageFiles = DReader.getFilesOfType(languageFolder, extensions, true);
		for(int i = 0; i < languageFiles.size(); i++)
		{
			ArrayList<String> info = DReader.readFile(languageFiles.get(i));
			if(ParseINI.getStringValue(LANGUAGE_HEADER, LANGUAGE_VARIABLE, info, new String()).length() < 1)
			{
				languageFiles.remove(i);
				i--;
				
			}//IF
			
		}//FOR
		
		return languageFiles;
		
	}//METHOD
	
	/**
	 * Gets the text for a given variable in the from the currently selected language.
	 * 
	 * @param id Language ID Variable
	 * @return Text for the given Language ID
	 * @since 2.0
	 */
	public String getLanuageText(final String id)
	{
		return ParseINI.getStringValue(null, id, languageInfo, id).replaceAll(Character.toString('\\') + Character.toString('^'), new String());
		
	}//METHOD
	
	/**
	 * Gets the mnemonic for a given variable in the from the currently selected language.
	 * 
	 * @param id Language ID Variable
	 * @return Mnemonic for the given Language ID
	 * @since 2.0
	 */
	public char getLanguageMnemonic(final String id)
	{
		char mnemonic = ' ';
		
		String value = ParseINI.getStringValue(null, id, languageInfo, id);
		int charNum;
		for(charNum = 0; charNum < value.length() && value.charAt(charNum) != '^'; charNum++);
		charNum++;
		
		if(charNum < value.length())
		{
			mnemonic = value.charAt(charNum);
			
		}//IF
		else if(value.length() > 0)
		{
			mnemonic = value.charAt(0);
			
		}//ELSE IF
		
		return mnemonic;
		
	}//METHOD
	
}//CLASS
