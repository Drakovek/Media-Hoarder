package drakovek.hoarder.file.language;

import java.io.File;
import java.util.ArrayList;

import drakovek.hoarder.file.DWriter;
import drakovek.hoarder.processing.ParseINI;

/**
 * Class for holding language variable names and their default English values.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DefaultLanguage 
{
	
	/**
	 * Header text for all language files.
	 */
	public static final String LANGUAGE_HEADER = "[LANGUAGE FILE]"; //$NON-NLS-1$
	
	/**
	 * Variable for the language of a particular language file.
	 */
	public static final String LANGUAGE_VARIABLE = "language_value"; //$NON-NLS-1$
	
	/**
	 * Default language name, should be "English (USA)"
	 */
	public static final String DEFAULT_LANGUAGE = "English (USA)"; //$NON-NLS-1$
	
	/**
	 * Writes default English language values to a language file.
	 * 
	 * @param languageDirectory Directory in which to save the default language file
	 */
	public static void writeDefaultLanguage(File languageDirectory)
	{
		ArrayList<String> languageFile = new ArrayList<>();
		
		//LANGUAGE
		languageFile.add(LANGUAGE_HEADER);
		languageFile.add(ParseINI.getAssignmentString(LANGUAGE_VARIABLE, DEFAULT_LANGUAGE));
		
		if(languageDirectory != null && languageDirectory.isDirectory())
		{
			File defaultFile = new File(languageDirectory, "00-ENG" + ParseINI.INI_EXTENSION); //$NON-NLS-1$
			
			languageFile.add(new String());
			languageFile.addAll(CommonValues.getValues());
			languageFile.add(new String());
			languageFile.addAll(SettingsValues.getValues());
			languageFile.add(new String());
			languageFile.addAll(CompoundValues.getValues());
			languageFile.add(new String());
			languageFile.addAll(DvkLanguageValues.getValues());
			languageFile.add(new String());
			languageFile.addAll(ModeValues.getValues());
			languageFile.add(new String());
			languageFile.addAll(ArtistValues.getValues());
			languageFile.add(new String());
			languageFile.addAll(ManagingValues.getValues());
			languageFile.add(new String());
			languageFile.addAll(EditingValues.getValues());
			languageFile.add(new String());
			languageFile.addAll(ViewerValues.getValues());
			
			DWriter.writeToFile(defaultFile, languageFile);
			
		}//IF
		
	}//METHOD
	
}//CLASS
