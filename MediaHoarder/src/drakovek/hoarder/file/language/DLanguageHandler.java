package drakovek.hoarder.file.language;

import java.io.File;
import java.util.ArrayList;

import javax.swing.KeyStroke;

import drakovek.hoarder.file.DReader;
import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.processing.ParseINI;

/**
 * Contains methods for retrieving text values in a specified language from local files containing language data.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DLanguageHandler
{
	/**
	 * Name of the program's language folder.
	 */
	private static final String LANGUAGE_FOLDER = "language"; //$NON-NLS-1$
	
	/**
	 * The program's language folder located in the program's data folder.
	 */
	private File languageFolder;
	
	/**
	 * INI formatted text for the currently selected language.
	 */
	private ArrayList<String> languageInfo;
	
	/**
	 * Initializes the DLanguage Class.
	 * 
	 * @param settings Program Settings
	 * @param languageName Name of the language to use for the program's text
	 */
	public DLanguageHandler(DSettings settings, final String languageName)
	{
		languageFolder = DReader.getDirectory(settings.getDataFolder(), LANGUAGE_FOLDER);
		
		setLanguage(languageName);
		
	}//METHOD
	
	/**
	 * Sets the current language of the project, determining which language files to read from.
	 * 
	 * @param languageName Name of the selected language
	 */
	public void setLanguage(final String languageName)
	{	
		ArrayList<File> languageFiles = getLanguageFiles();
		languageInfo = new ArrayList<>();
		boolean getDefault = true;
		
		if(languageName != null && languageName.length() > 0)
		{
			for(File languageFile : languageFiles)
			{
				ArrayList<String> info = DReader.readFile(languageFile);
				if(ParseINI.getStringValue(DefaultLanguage.LANGUAGE_HEADER, DefaultLanguage.LANGUAGE_VARIABLE, info, new String()).equals(languageName))
				{
					languageInfo.addAll(info);
					getDefault = false;
					
				}//IF
			
			}//FOR
		
		}//IF
		
		if(getDefault)
		{
			DefaultLanguage.writeDefaultLanguage(languageFolder);
			setLanguage(DefaultLanguage.DEFAULT_LANGUAGE);
			
		}//ELSE
		
	}//METHOD

	/**
	 * Returns an ArrayList<String> of all the language files within the program's language folder.
	 * 
	 * @return ArrayList<String> of language files
	 */
	private ArrayList<File> getLanguageFiles()
	{
		String[] extensions = {ParseINI.INI_EXTENSION};
		ArrayList<File> languageFiles = DReader.getFilesOfType(languageFolder, extensions, true);
		for(int i = 0; i < languageFiles.size(); i++)
		{
			ArrayList<String> info = DReader.readFile(languageFiles.get(i));
			if(ParseINI.getStringValue(DefaultLanguage.LANGUAGE_HEADER, DefaultLanguage.LANGUAGE_VARIABLE, info, new String()).length() < 1)
			{
				languageFiles.remove(i);
				i--;
				
			}//IF
			
		}//FOR
		
		return languageFiles;
		
	}//METHOD
	
	/**
	 * Returns an ArrayList<String> of all the languages available through language files.
	 * 
	 * @return ArrayList<String> of all languages available
	 */
	public ArrayList<String> getLanguages()
	{
		ArrayList<File> languageFiles = getLanguageFiles();
		ArrayList<String> languages = new ArrayList<>();
		
		for(File languageFile: languageFiles)
		{
			ArrayList<String> info = DReader.readFile(languageFile);
			String language = ParseINI.getStringValue(DefaultLanguage.LANGUAGE_HEADER, DefaultLanguage.LANGUAGE_VARIABLE, info, new String());
			if(language.length() > 0 && !languages.contains(language))
			{
				languages.add(language);
				
			}//IF
			
		}//FOR
		
		return languages;
		
	}//METHOD
	
	/**
	 * Gets the text for a given variable in the from the currently selected language.
	 * 
	 * @param id Language ID Variable
	 * @return Text for the given Language ID
	 */
	public String getLanuageText(final String id)
	{
		return ParseINI.getStringValue(null, id, languageInfo, id).replaceAll(Character.toString('\\') + Character.toString('^'), new String());
		
	}//METHOD
	
	/**
	 * Returns values for a mnemonic based on a language ID
	 * 
	 * @param id Language ID
	 * @return [0] int value of mnemonic keystroke, [1] character index to show as mnemonic
	 */
	public int[] getLanguageMnemonic(final String id)
	{
		int[] mnemonic = {-1, -1};
		
		String value = ParseINI.getStringValue(null, id, languageInfo, id).toUpperCase();
		int charNum;
		for(charNum = 0; charNum < value.length() && value.charAt(charNum) != '^'; charNum++);
		charNum++;
		
		if(charNum < value.length())
		{
			mnemonic[0] = KeyStroke.getKeyStroke(value.charAt(charNum), 0).getKeyCode();
			mnemonic[1] = charNum - 1;
			
		}//IF
		
		return mnemonic;
		
	}//METHOD
	
}//CLASS
