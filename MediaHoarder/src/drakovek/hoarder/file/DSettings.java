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
	 * INI Variable for the language name.
	 * 
	 * @since 2.0
	 */
	private static final String LANGUAGE_NAME = "language_name"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to use DMF indexes.
	 * 
	 * @since 2.0
	 */
	private static final String USE_INDEXES = "use_indexes"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to update DMF indexes when they are loaded.
	 * 
	 * @since 2.0
	 */
	private static final String UPDATE_INDEXES = "update_indexes"; //$NON-NLS-1$
	
	/**
	 * INI variable for user's DMF directories
	 * 
	 * @since 2.0
	 */
	private static final String DMF_DIRECTORY = "dmf_directory"; //$NON-NLS-1$
	
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
	 * INI variable for the scroll unit
	 * 
	 * @since 2.0
	 */
	private static final String SCROLL_UNIT = "scroll_unit"; //$NON-NLS-1$
	
	/**
	 * INI variable for the program theme
	 * 
	 * @since 2.0
	 */
	private static final String THEME = "theme"; //$NON-NLS-1$
	
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
	 * INI Variable for font anti-aliasing.
	 * 
	 * @since 2.0
	 */
	private static final String FONT_AA = "font_aa"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the DeviantArt directory
	 * 
	 * @since 2.0
	 */
	private static final String DEVIANTART_DIRECTORY = "deviantart_directory"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the Fur Affinity directory
	 * 
	 * @since 2.0
	 */
	private static final String FUR_AFFINITY_DIRECTORY = "fur_affinity_directory"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the size of DMF preview thumbnails.
	 *
	 * @since 2.0
	 */
	private static final String PREVIEW_SIZE = "preview_size"; //$NON-NLS-1$
	
	/**
	 * INI variable for the scale type.
	 * 
	 * @since 2.0
	 */
	private static final String SCALE_TYPE = "scale_type"; //$NON-NLS-1$
	
	/**
	 * INI variable for the scale amount.
	 * 
	 * @since 2.0
	 */
	private static final String SCALE_AMOUNT = "scale_amount"; //$NON-NLS-1$
	
	/**
	 * INI variable for using thumbnails
	 * 
	 * @since 2.0
	 */
	private static final String USE_THUMBNAILS = "use_thumbnails"; //$NON-NLS-1$
	
	/**
	 * INI variable for the default sort type.
	 * 
	 * @since 2.0
	 */
	private static final String SORT_TYPE = "sort_type"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to group artists.
	 * 
	 * @since 2.0
	 */
	private static final String GROUP_ARTISTS = "group_artists"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to group sequences
	 * 
	 * @since 2.0
	 */
	private static final String GROUP_SEQUENCES = "group_sequences"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to group sections
	 * 
	 * @since 2.0
	 */
	private static final String GROUP_SECTIONS = "group_sections"; //$NON-NLS-1$
	
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
	 * Whether the user prefers to use index files to load DMF directories.
	 * 
	 * @since 2.0
	 */
	private boolean useIndexes;
	
	/**
	 * Whether the user prefers to update indexes when using index files to load DMF directories.
	 * 
	 * @since 2.0
	 */
	private boolean updateIndexes;
	
	/**
	 * ArrayList containing the user's selected directories for storing and loading DMFs
	 * 
	 * @since 2.0
	 */
	private ArrayList<File> dmfDirectories;
	
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
	 * Swing "Look and Feel" for the program
	 * 
	 * @since 2.0
	 */
	private String theme;
	
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
	 * Whether the font should be anti-aliased.
	 * 
	 * @since 2.0
	 */
	private boolean fontAA;
	
	/**
	 * Directory to save DMFs to when downloading from DeviantArt
	 * 
	 * @since 2.0
	 */
	private File deviantArtDirectory;
	
	/**
	 * Directory to save DMFs to when downloading from FurAffinity
	 * 
	 * @since 2.0
	 */
	private File furAffinityDirectory;
	
	/**
	 * Size of the preview thumbnails in the viewer GUI
	 * 
	 * @since 2.0
	 */
	private int previewSize;
	
	/**
	 * Int value representing the type of scaling to use on images.
	 * 
	 * @since 2.0
	 */
	private int scaleType;
	
	/**
	 * Double to multiply image size by when scaling directly.
	 * 
	 * @since 2.0
	 */
	private double scaleAmount;
	
	/**
	 * Whether to use thumbnails in the viewer GUI
	 * 
	 * @since 2.0
	 */
	private boolean useThumbnails;
	
	/**
	 * Default sort type for the program.
	 * 
	 * @since 2.0
	 */
	private int sortType;
	
	/**
	 * Whether to group artists when sorting DMFs
	 * 
	 * @since 2.0
	 */
	private boolean groupArtists;
	
	/**
	 * Whether to group sequences when sorting DMFs
	 * 
	 * @since 2.0
	 */
	private boolean groupSequences;
	
	/**
	 * Whether to group sections when sorting DMFs
	 * 
	 * @since 2.0
	 */
	private boolean groupSections;
	
	/**
	 * Initializes the DSettings Class
	 * 
	 * @since 2.0
	 */
	public DSettings()
	{
		setDataFolder();
		readSettings();
		languageHandler = new DLanguageHandler(this, languageName);
		
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
		
		//DMF
		useIndexes = true;
		updateIndexes = true;
		dmfDirectories = new ArrayList<>();
		
		//SWING
		spaceMultiplier = 0.5;
		frameWidth = 20;
		frameHeight = 15;
		scrollUnit = 15;
		theme = new String();
		fontName = new String();
		fontSize = 14;
		fontBold = false;
		fontAA = true;
		
		//DOWNLOAD
		deviantArtDirectory = null;
		furAffinityDirectory = null;
		
		//VIEWER
		previewSize = 100;
		scaleType = 0;
		scaleAmount = 1.0;
		useThumbnails = true;
		sortType = 0;
		groupArtists = false;
		groupSequences = false;
		groupSections = false;
		
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
			
			//DMF
			dmfDirectories = ParseINI.getFileValues(null, DMF_DIRECTORY, settingsInfo, new ArrayList<File>());
			useIndexes = ParseINI.getBooleanValue(null, USE_INDEXES, settingsInfo, useIndexes);
			updateIndexes = ParseINI.getBooleanValue(null, UPDATE_INDEXES, settingsInfo, updateIndexes);
			
			//SWING
			spaceMultiplier = ParseINI.getDoubleValue(null, SPACE_MULTIPLIER, settingsInfo, spaceMultiplier);
			frameWidth = ParseINI.getIntValue(null, FRAME_WIDTH, settingsInfo, frameWidth);
			frameHeight = ParseINI.getIntValue(null, FRAME_HEIGHT, settingsInfo, frameHeight);
			scrollUnit = ParseINI.getIntValue(null, SCROLL_UNIT, settingsInfo, scrollUnit);
			theme = ParseINI.getStringValue(null, THEME, settingsInfo, theme);
			fontName = ParseINI.getStringValue(null, FONT_NAME, settingsInfo, fontName);
			fontSize = ParseINI.getIntValue(null, FONT_SIZE, settingsInfo, fontSize);
			fontBold = ParseINI.getBooleanValue(null, FONT_BOLD, settingsInfo, fontBold);
			fontAA = ParseINI.getBooleanValue(null, FONT_AA, settingsInfo, fontAA);
			
			//DOWNLOAD
			deviantArtDirectory = ParseINI.getFileValue(null, DEVIANTART_DIRECTORY, settingsInfo, null);
			furAffinityDirectory = ParseINI.getFileValue(null, FUR_AFFINITY_DIRECTORY, settingsInfo, null);
			
			//VIEWER
			previewSize = ParseINI.getIntValue(null, PREVIEW_SIZE, settingsInfo, previewSize);
			scaleType = ParseINI.getIntValue(null, SCALE_TYPE, settingsInfo, scaleType);
			scaleAmount = ParseINI.getDoubleValue(null, SCALE_AMOUNT, settingsInfo, scaleAmount);
			useThumbnails = ParseINI.getBooleanValue(null, USE_THUMBNAILS, settingsInfo, useThumbnails);
			sortType = ParseINI.getIntValue(null, SORT_TYPE, settingsInfo, sortType);
			groupArtists = ParseINI.getBooleanValue(null, GROUP_ARTISTS, settingsInfo, groupArtists);
			groupSequences = ParseINI.getBooleanValue(null, GROUP_SEQUENCES, settingsInfo, groupSequences);
			groupSections = ParseINI.getBooleanValue(null, GROUP_SECTIONS, settingsInfo, groupSections);
			
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
		settingsInfo.add("[SETTINGS]"); //$NON-NLS-1$
		settingsInfo.add(ParseINI.getAssignmentString(LANGUAGE_NAME, languageName));
		
		//SWING
		settingsInfo.add(new String());
		settingsInfo.add("[SWING]"); //$NON-NLS-1$
		settingsInfo.add(ParseINI.getAssignmentString(SPACE_MULTIPLIER, spaceMultiplier));
		settingsInfo.add(ParseINI.getAssignmentString(FRAME_WIDTH, frameWidth));
		settingsInfo.add(ParseINI.getAssignmentString(FRAME_HEIGHT, frameHeight));
		settingsInfo.add(ParseINI.getAssignmentString(SCROLL_UNIT, scrollUnit));
		settingsInfo.add(ParseINI.getAssignmentString(THEME, theme));
		settingsInfo.add(ParseINI.getAssignmentString(FONT_NAME, fontName));
		settingsInfo.add(ParseINI.getAssignmentString(FONT_SIZE, fontSize));
		settingsInfo.add(ParseINI.getAssignmentString(FONT_BOLD, fontBold));
		settingsInfo.add(ParseINI.getAssignmentString(FONT_AA, fontAA));
		
		//DMF
		settingsInfo.add(new String());
		settingsInfo.add("[DMF]"); //$NON-NLS-1$
		for(File dmfDirectory: dmfDirectories)
		{
			settingsInfo.add(ParseINI.getAssignmentString(DMF_DIRECTORY, dmfDirectory));
			
		}//FOR
		settingsInfo.add(ParseINI.getAssignmentString(USE_INDEXES, useIndexes));
		settingsInfo.add(ParseINI.getAssignmentString(UPDATE_INDEXES, updateIndexes));
		
		
		//DOWNLOAD
		settingsInfo.add(new String());
		settingsInfo.add("[DOWNLOAD]"); //$NON-NLS-1$
		settingsInfo.add(ParseINI.getAssignmentString(DEVIANTART_DIRECTORY, deviantArtDirectory));
		settingsInfo.add(ParseINI.getAssignmentString(FUR_AFFINITY_DIRECTORY, furAffinityDirectory));
		
		//VIEWER
		settingsInfo.add(new String());
		settingsInfo.add("[VIEWER]"); //$NON-NLS-1$
		settingsInfo.add(ParseINI.getAssignmentString(PREVIEW_SIZE, previewSize));
		settingsInfo.add(ParseINI.getAssignmentString(SCALE_TYPE, scaleType));
		settingsInfo.add(ParseINI.getAssignmentString(SCALE_AMOUNT, scaleAmount));
		settingsInfo.add(ParseINI.getAssignmentString(USE_THUMBNAILS, useThumbnails));
		settingsInfo.add(ParseINI.getAssignmentString(SORT_TYPE, sortType));
		settingsInfo.add(ParseINI.getAssignmentString(GROUP_ARTISTS, groupArtists));
		settingsInfo.add(ParseINI.getAssignmentString(GROUP_SEQUENCES, groupSequences));
		settingsInfo.add(ParseINI.getAssignmentString(GROUP_SECTIONS, groupSections));
		
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
	public String getLanguageText(final String id)
	{
		return languageHandler.getLanuageText(id);
		
	}//METHOD
	
	/**
	 * Returns values for a mnemonic based on a language ID
	 * 
	 * @param id Language ID
	 * @return [0] int value of mnemonic keystroke, [1] character index to show as mnemonic
	 * @since 2.0
	 */
	public int[] getLanguageMnemonic(final String id)
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
	 * Returns the program's data folder located in the main program directory.
	 * 
	 * @return Data Folder
	 * @since 2.0
	 */
	public File getDataFolder()
	{
		return dataFolder;
		
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
	 * Sets value of useIndexes.
	 * 
	 * @param useIndexes useIndexes
	 * @since 2.0
	 */
	public void setUseIndexes(final boolean useIndexes)
	{
		this.useIndexes = useIndexes;
		
	}//METHOD
	
	/**
	 * Returns value of useIndexes.
	 * 
	 * @return useIndexes
	 * @since 2.0
	 */
	public boolean getUseIndexes()
	{
		return useIndexes;
		
	}//METHOD
	
	/**
	 * Sets the value of updateIndexes.
	 * 
	 * @param updateIndexes updateIndexes
	 * @since 2.0
	 */
	public void setUpdateIndexes(final boolean updateIndexes)
	{
		this.updateIndexes = updateIndexes;
		
	}//METHOD
	
	/**
	 * Returns the value of updateIndexes.
	 * 
	 * @return updateIndexes
	 * @since 2.0
	 */
	public boolean getUpdateIndexes()
	{
		return updateIndexes;
		
	}//METHOD
	
	/**
	 * Sets the DMF directories.
	 * 
	 * @param dmfDirectories DMF Directories
	 * @since 2.0
	 */
	public void setDmfDirectories(final ArrayList<File> dmfDirectories)
	{
		this.dmfDirectories = dmfDirectories;
		
	}//METHOD
	
	/**
	 * Returns list of DMF Directories.
	 * 
	 * @return DMF Directories
	 * @since 2.0
	 */
	public ArrayList<File> getDmfDirectories()
	{
		ArrayList<File> files = new ArrayList<>();
		files.addAll(dmfDirectories);
		return files;
		
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
	 * Sets the program theme.
	 * 
	 * @param theme Theme
	 * @since 2.0
	 */
	public void setTheme(final String theme)
	{
		this.theme = theme;
		
	}//METHOD
	
	/**
	 * Gets the program theme.
	 * 
	 * @return Theme
	 * @since 2.0
	 */
	public String getTheme()
	{
		return theme;
		
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
	
	/**
	 * Sets the font anti-aliasing
	 * 
	 * @param fontAA Font Anti-Aliasing
	 * @since 2.0
	 */
	public void setFontAA(final boolean fontAA)
	{
		this.fontAA = fontAA;
		
	}//METHOD
	
	/**
	 * Returns the font anti-aliasing
	 * 
	 * @return Font Anti-Aliasing
	 * @since 2.0
	 */
	public boolean getFontAA()
	{
		return fontAA;
		
	}//METHOD
	
	/**
	 * Sets the DeviantArt directory.
	 * 
	 * @param deviantArtDirectory DeviantArt Directory
	 * @since 2.0
	 */
	public void setDeviantArtDirectory(final File deviantArtDirectory)
	{
		this.deviantArtDirectory = deviantArtDirectory;
		
	}//METHOD
	
	/**
	 * Returns the DeviantArt directory.
	 * 
	 * @return DeviantArt Directory
	 * @since 2.0
	 */
	public File getDeviantArtDirectory()
	{
		return deviantArtDirectory;
		
	}//METHOD
	
	/**
	 * Sets the Fur Affinity Directory
	 * 
	 * @param furAffinityDirectory Fur Affinity Directory
	 * @since 2.0
	 */
	public void setFurAffinityDirectory(final File furAffinityDirectory)
	{
		this.furAffinityDirectory = furAffinityDirectory;
		
	}//METHOD
	
	/**
	 * Returns the Fur Affinity Directory
	 * 
	 * @return Fur Affinity Directory
	 * @since 2.0
	 */
	public File getFurAffinityDirectory()
	{
		return furAffinityDirectory;
		
	}//METHOD
	
	/**
	 * Returns the Preview Size
	 * 
	 * @return Preview Size
	 * @since 2.0
	 */
	public int getPreviewSize()
	{
		return previewSize;
		
	}//METHOD
	
	/**
	 * Sets the scale type.
	 * 
	 * @param scaleType Scale Type
	 * @since 2.0
	 */
	public void setScaleType(final int scaleType)
	{
		this.scaleType = scaleType;
		
	}//METHOD
	
	/**
	 * Returns the scale type.
	 * 
	 * @return Scale Type
	 * @since 2.0
	 */
	public int getScaleType()
	{
		return scaleType;
		
	}//METHOD
	
	/**
	 * Sets the scale amount.
	 * 
	 * @param scaleAmount Scale Amount
	 * @since 2.0
	 */
	public void setScaleAmount(final double scaleAmount)
	{
		this.scaleAmount = scaleAmount;
		
	}//METHOD
	
	/**
	 * Returns the scale amount.
	 * 
	 * @return Scale Amount
	 * @since 2.0
	 */
	public double getScaleAmount()
	{
		return scaleAmount;
		
	}//METHOD
	
	/**
	 * Sets whether to use thumbnails.
	 * 
	 * @param useThumbnails Use Thumbnails
	 * @since 2.0
	 */
	public void setUseThumbnails(final boolean useThumbnails)
	{
		this.useThumbnails = useThumbnails;
		
	}//METHOD
	
	/**
	 * Gets whether to use thumbnails.
	 * 
	 * @return Use Thumbnails
	 * @since 2.0
	 */
	public boolean getUseThumbnails()
	{
		return useThumbnails;
		
	}//METHOD
	
	/**
	 * Sets the sort type.
	 * 
	 * @param sortType Sort Type
	 * @since 2.0
	 */
	public void setSortType(final int sortType)
	{
		this.sortType = sortType;
		
	}//METHOD
	
	/**
	 * Returns the sort type.
	 * 
	 * @return Sort Type
	 * @since 2.0
	 */
	public int getSortType()
	{
		return sortType;
		
	}//METHOD
	
	/**
	 * Sets whether to group artists.
	 * 
	 * @param groupArtists Whether to group artists
	 * @since 2.0
	 */
	public void setGroupArtists(final boolean groupArtists)
	{
		this.groupArtists = groupArtists;
		
	}//METHOD
	
	/**
	 * Returns whether to group artists.
	 * 
	 * @return Whether to group artists
	 * @since 2.0
	 */
	public boolean getGroupArtists()
	{
		return groupArtists;
		
	}//METHOD
	
	/**
	 * Sets whether to group sequences.
	 * 
	 * @param groupSequences Whether to group sequences
	 * @since 2.0
	 */
	public void setGroupSequences(final boolean groupSequences)
	{
		this.groupSequences = groupSequences;
		
	}//METHOD
	
	/**
	 * Returns whether to group sequences.
	 * 
	 * @return Whether to group sequences
	 * @since 2.0
	 */
	public boolean getGroupSequences()
	{
		return groupSequences;
		
	}//METHOD
	
	/**
	 * Sets whether to group sections.
	 * 
	 * @param groupSections Whether to group sections
	 * @since 2.0
	 */
	public void setGroupSections(final boolean groupSections)
	{
		this.groupSections = groupSections;
		
	}//METHOD
	
	/**
	 * Returns whether to group sections.
	 * 
	 * @return Whether to group sections
	 * @since 2.0
	 */
	public boolean getGroupSections()
	{
		return groupSections;
		
	}//METHOD
	
}//CLASS
