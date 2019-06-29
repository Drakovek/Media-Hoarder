package drakovek.hoarder.file;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import drakovek.hoarder.file.language.DLanguageHandler;
import drakovek.hoarder.processing.ParseINI;

/**
 * Contains methods for handling user settings data.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DSettings
{
	/**
	 * Name of the program's data folder.
	 * 
	 * @since 2.0
	 */
	private static final String DATA_FOLDER = "data"; //$NON-NLS-1$
	
	/**
	 * Name of the program's settings file.
	 * 
	 * @since 2.0
	 */
	private static final String SETTINGS_FILE = "settings.ini"; //$NON-NLS-1$
	
	/**
	 * Main Header for the program's settings file.
	 * 
	 * @since 2.0
	 */
	private static final String SETTINGS_HEADER = "[SETTINGS]"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the language name.
	 * 
	 * @since 2.0
	 */
	private static final String LANGUAGE_NAME = "language_name"; //$NON-NLS-1$
	
	/**
	 * Swing Header for the program's settings file.
	 * 
	 * @since 2.0
	 */
	private static final String SWING_HEADER = "[SWING]"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the space multiplier.
	 * 
	 * @since 2.0
	 */
	private static final String SPACE_MULTIPLIER = "space_multiplier"; //$NON-NLS-1$
	
	/**
	 * INI variable for the frame width multiplier.
	 * 
	 * @since 2.0
	 */
	private static final String FRAME_WIDTH = "frame_width"; //$NON-NLS-1$
	
	/**
	 * INI variable for the frame height multiplier.
	 * 
	 * @since 2.0
	 */
	private static final String FRAME_HEIGHT = "frame_height"; //$NON-NLS-1$
	
	/**
	 * INI variable for the scroll unit.
	 * 
	 * @since 2.0
	 */
	private static final String SCROLL_UNIT = "scroll_unit"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the font name.
	 * 
	 * @since 2.0
	 */
	private static final String FONT_NAME = "font_name"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the font size.
	 * 
	 * @since 2.0
	 */
	private static final String FONT_SIZE = "font_size"; //$NON-NLS-1$
	
	/**
	 * INI Variable for whether the main font is bold.
	 * 
	 * @since 2.0
	 */
	private static final String FONT_BOLD = "font_bold"; //$NON-NLS-1$
	
	/**
	 * Program's data folder located in the main program directory.
	 * 
	 * @since 2.0
	 */
	private File dataFolder;
	
	/**
	 * DLanguageHandler for getting language values.
	 * 
	 * @since 2.0
	 */
	private DLanguageHandler languageHandler;
	
	//SETTINGS VARIABLES

	/**
	 * Name of the selected language.
	 * 
	 * @since 2.0
	 */
	private String languageName;
	
	/**
	 * Multiplied by fontSize to get the default space between Swing components.
	 * 
	 * @since 2.0
	 */
	private double spaceMultiplier;
	
	/**
	 * Multiplied by fontSize to get the minimum DFrame width.
	 * 
	 * @since 2.0
	 */
	private int frameWidth;
	
	/**
	 * Multiplied by fontSize to get the minimum DFrame height.
	 * 
	 * @since 2.0
	 */
	private int frameHeight;
	
	/**
	 * Scroll unit, determines the sensitivity of scroll bars.
	 * 
	 * @since 2.0
	 */
	private int scrollUnit;
	
	/**
	 * Name of Font used for Swing components
	 * 
	 * @since 2.0
	 */
	private String fontName;
	
	/**
	 * Font Size for Swing components.
	 * 
	 * @since 2.0
	 */
	private int fontSize;
	
	/**
	 * Whether the program's main font is bold.
	 * 
	 * @since 2.0
	 */
	private boolean fontBold;
	
	/**
	 * Initializes the DSettings Class
	 * 
	 * @since 2.0
	 */
	public DSettings()
	{
		setDataFolder();
		readSettings();
		languageHandler = new DLanguageHandler(dataFolder, languageName);
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the location of the main data folder for the project.
	 * 
	 * @since 2.0
	 */
	private void setDataFolder()
	{
        try
        {
            dataFolder = new File(DSettings.class.getProtectionDomain().getCodeSource().getLocation().toURI());

            while(dataFolder != null && dataFolder.exists() && !dataFolder.isDirectory())
            {
                dataFolder = dataFolder.getParentFile();

            }//WHILE

            dataFolder = DReader.getDirectory(dataFolder, DATA_FOLDER);

        }//TRY
        catch (URISyntaxException e)
        {
            dataFolder = new File(new String());

        }//CATCH
        
	}//METHOD
	
	/**
	 * Sets all settings variables to their default settings.
	 * 
	 * @since 2.0
	 */
	private void clearSettings()
	{
		//GENERAL
		languageName = new String();
		
		//SWING
		spaceMultiplier = 0.5;
		frameWidth = 20;
		frameHeight = 15;
		scrollUnit = 15;
		fontName = new String();
		fontSize = 14;
		fontBold = false;
		
	}//METHOD
	
	/**
	 * Reads the main settings file and sets the settings variables accordingly.
	 * 
	 * @since 2.0
	 */
	private void readSettings()
	{
		clearSettings();
		
		if(dataFolder != null && dataFolder.isDirectory())
		{
			File settingsFile = new File(dataFolder, SETTINGS_FILE);
			ArrayList<String> settingsInfo = DReader.readFile(settingsFile);
			
			//GENERAL
			languageName = ParseINI.getStringValue(null, LANGUAGE_NAME, settingsInfo, languageName);
			
			//SWING
			spaceMultiplier = ParseINI.getDoubleValue(null, SPACE_MULTIPLIER, settingsInfo, spaceMultiplier);
			frameWidth = ParseINI.getIntValue(null, FRAME_WIDTH, settingsInfo, frameWidth);
			frameHeight = ParseINI.getIntValue(null, FRAME_HEIGHT, settingsInfo, frameHeight);
			scrollUnit = ParseINI.getIntValue(null, SCROLL_UNIT, settingsInfo, scrollUnit);
			fontName = ParseINI.getStringValue(null, FONT_NAME, settingsInfo, fontName);
			fontSize = ParseINI.getIntValue(null, FONT_SIZE, settingsInfo, fontSize);
			fontBold = ParseINI.getBooleanValue(null, FONT_BOLD, settingsInfo, fontBold);
			
		}//IF
		
	}//METHOD
	
	/**
	 * Writes the contents of settings variables to the main settings file.
	 * 
	 * @since 2.0
	 */
	public void writeSettings()
	{
		ArrayList<String> settingsInfo = new ArrayList<>();
		
		//GENERAL
		settingsInfo.add(SETTINGS_HEADER);
		settingsInfo.add(ParseINI.getAssignmentString(LANGUAGE_NAME, languageName));
		
		//SWING
		settingsInfo.add(new String());
		settingsInfo.add(SWING_HEADER);
		settingsInfo.add(ParseINI.getAssignmentString(SPACE_MULTIPLIER, spaceMultiplier));
		settingsInfo.add(ParseINI.getAssignmentString(FRAME_WIDTH, frameWidth));
		settingsInfo.add(ParseINI.getAssignmentString(FRAME_HEIGHT, frameHeight));
		settingsInfo.add(ParseINI.getAssignmentString(SCROLL_UNIT, scrollUnit));
		settingsInfo.add(ParseINI.getAssignmentString(FONT_NAME, fontName));
		settingsInfo.add(ParseINI.getAssignmentString(FONT_SIZE, fontSize));
		settingsInfo.add(ParseINI.getAssignmentString(FONT_BOLD, fontBold));
		
		if(dataFolder != null && dataFolder.isDirectory())
		{
			File settingsFile = new File(dataFolder, SETTINGS_FILE);
			DWriter.writeToFile(settingsFile, settingsInfo);
			
		}//IF
		
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
		return languageHandler.getLanuageText(id);
		
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
		return languageHandler.getLanguageMnemonic(id);
		
	}//METHOD
	
	/**
	 * Returns an ArrayList<String> of all the languages available through language files.
	 * 
	 * @return ArrayList<String> of all languages available
	 * @since 2.0
	 */
	public ArrayList<String> getLanguages()
	{
		return languageHandler.getLanguages();
		
	}//METHOD
	
	/**
	 * Sets the Language Name
	 * 
	 * @param languageName Language Name
	 * @since 2.0
	 */
	public void setLanguageName(final String languageName)
	{
		this.languageName = languageName;
		languageHandler.setLanguage(languageName);
		
	}//METHOD
	
	/**
	 * Gets the Language Name.
	 * 
	 * @return Language Name
	 * @since 2.0
	 */
	public String getLanguageName()
	{
		return languageName;
		
	}//METHOD
	
	/**
	 * Gets the Space Multiplier
	 * 
	 * @return Space Multiplier
	 * @since 2.0
	 */
	public double getSpaceMultiplier()
	{
		return spaceMultiplier;
		
	}//METHOD
	
	/**
	 * Gets the default space size for Swing Components
	 * 
	 * @return Default Space Size
	 * @since 2.0
	 */
	public int getSpaceSize()
	{
		return (int)(getFontSize() * getSpaceMultiplier());
	
	}//METHOD
	
	/**
	 * Gets the Frame Height Multiplier
	 * 
	 * @return Frame Height Multiplier
	 * @since 2.0
	 */
	public int getFrameHeight()
	{
		return frameHeight;
		
	}//METHOD
	
	/**
	 * Gets the Frame Width Multiplier
	 * 
	 * @return Frame Width Multiplier
	 * @since 2.0
	 */
	public int getFrameWidth()
	{
		return frameWidth;
		
	}//METHOD
	
	/**
	 * Gets the scroll bar Scroll Unit.
	 * 
	 * @return Scroll Unit
	 * @since 2.0
	 */
	public int getScrollUnit()
	{
		return scrollUnit;
		
	}//METHOD
	
	/**
	 * Sets the Font Name.
	 * 
	 * @param fontName Font Name
	 * @since 2.0
	 */
	public void setFontName(final String fontName)
	{
		this.fontName = fontName;
		
	}//METHOD
	
	/**
	 * Gets the Font Name
	 * 
	 * @return Font Name
	 * @since 2.0
	 */
	public String getFontName()
	{
		return fontName;
		
	}//METHOD
	
	/**
	 * Sets the Font Size.
	 * 
	 * @param fontSize Font Size
	 * @since 2.0
	 */
	public void setFontSize(final int fontSize)
	{
		this.fontSize = fontSize;
		
	}//METHOD
	
	/**
	 * Gets the Font Size.
	 * 
	 * @return Font Size
	 * @since 2.0
	 */
	public int getFontSize()
	{
		return fontSize;
		
	}//METHOD
	
	/**
	 * Sets whether the main font is bold.
	 * 
	 * @param fontBold Whether Font should be Bold
	 * @since 2.0
	 */
	public void setFontBold(final boolean fontBold)
	{
		this.fontBold = fontBold;
		
	}//METHOD
	
	/**
	 * Gets whether the main font is bold.
	 * 
	 * @return Whether Font is Bold
	 * @since 2.0
	 */
	public boolean getFontBold()
	{
		return fontBold;
		
	}//METHOD
	
}//CLASS
