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
 */
public class DSettings
{
	/**
	 * Name of the program's data folder.
	 */
	private static final String DATA_FOLDER = "data"; //$NON-NLS-1$
	
	/**
	 * Name of the program's settings file.
	 */
	private static final String SETTINGS_FILE = "settings" + ParseINI.INI_EXTENSION; //$NON-NLS-1$
	
	/**
	 * INI Variable for the language name.
	 */
	private static final String LANGUAGE_NAME = "language_name"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to use DVK indexes.
	 */
	private static final String USE_INDEXES = "use_indexes"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to update DVK indexes when they are loaded.
	 */
	private static final String UPDATE_INDEXES = "update_indexes"; //$NON-NLS-1$
	
	/**
	 * INI variable for user's DVK directories
	 */
	private static final String DVK_DIRECTORY = "dvk_directory"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the space multiplier.
	 */
	private static final String SPACE_MULTIPLIER = "space_multiplier"; //$NON-NLS-1$
	
	/**
	 * INI variable for the frame width multiplier.
	 */
	private static final String FRAME_WIDTH = "frame_width"; //$NON-NLS-1$
	
	/**
	 * INI variable for the frame height multiplier.
	 */
	private static final String FRAME_HEIGHT = "frame_height"; //$NON-NLS-1$
	
	/**
	 * INI variable for the scroll unit
	 */
	private static final String SCROLL_UNIT = "scroll_unit"; //$NON-NLS-1$
	
	/**
	 * INI variable for the program theme
	 */
	private static final String THEME = "theme"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the font name.
	 */
	private static final String FONT_NAME = "font_name"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the font size
	 */
	private static final String FONT_SIZE = "font_size"; //$NON-NLS-1$
	
	/**
	 * INI Variable for whether the main font is bold.
	 */
	private static final String FONT_BOLD = "font_bold"; //$NON-NLS-1$
	
	/**
	 * INI Variable for font anti-aliasing.
	 */
	private static final String FONT_AA = "font_aa"; //$NON-NLS-1$
	
	/**
	 * INI variable for the default clock format
	 */
	private static final String CLOCK_FORMAT = "clock_format"; //$NON-NLS-1$
	
	/**
	 * INI variable for the default date format
	 */
	private static final String DATE_FORMAT = "date_format"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to save journal
	 */
	private static final String SAVE_JOURNALS = "save_journals"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the DeviantArt directory
	 */
	private static final String DEVIANTART_DIRECTORY = "deviantart_directory"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the Fur Affinity directory
	 */
	private static final String FUR_AFFINITY_DIRECTORY = "fur_affinity_directory"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the Inkbunny directory
	 */
	private static final String INKBUNY_DIRECTORY = "inkbunny_directory"; //$NON-NLS-1$
	
	/**
	 * INI Variable for the size of DVK preview thumbnails.
	 */
	private static final String PREVIEW_SIZE = "preview_size"; //$NON-NLS-1$
	
	/**
	 * INI variable for the scale type.
	 */
	private static final String SCALE_TYPE = "scale_type"; //$NON-NLS-1$
	
	/**
	 * INI variable for the scale amount.
	 */
	private static final String SCALE_AMOUNT = "scale_amount"; //$NON-NLS-1$
	
	/**
	 * INI variable for the detail location
	 */
	private static final String DETAIL_LOCATION = "detail_location"; //$NON-NLS-1$
	
	/**
	 * INI variable for using thumbnails
	 */
	private static final String USE_THUMBNAILS = "use_thumbnails"; //$NON-NLS-1$
	
	/**
	 * INI variable for the default sort type.
	 */
	private static final String SORT_TYPE = "sort_type"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to group artists.
	 */
	private static final String GROUP_ARTISTS = "group_artists"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to group sequences
	 */
	private static final String GROUP_SEQUENCES = "group_sequences"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to group sections
	 */
	private static final String GROUP_SECTIONS = "group_sections"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to reverse the order of sorted DVKs
	 */
	private static final String REVERSE_ORDER = "reverse_order"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to show artists for the previews in the viewer GUI
	 */
	private static final String SHOW_ARTISTS = "show_artists"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to show ratings for the previews in the viewer GUI
	 */
	private static final String SHOW_RATINGS = "show_ratings"; //$NON-NLS-1$
	
	/**
	 * INI variable for whether to show view counts for the previews in the viewer GUI
	 */
	private static final String SHOW_VIEWS = "show_views"; //$NON-NLS-1$
	
	/**
	 * Program's data folder located in the main program directory.
	 */
	private File dataFolder;
	
	/**
	 * DLanguageHandler for getting language values.
	 */
	private DLanguageHandler languageHandler;
	
	//SETTINGS VARIABLES

	/**
	 * Name of the selected language.
	 */
	private String languageName;
	
	/**
	 * Whether the user prefers to use index files to load DVK directories.
	 */
	private boolean useIndexes;
	
	/**
	 * Whether the user prefers to update indexes when using index files to load DVK directories.
	 */
	private boolean updateIndexes;
	
	/**
	 * ArrayList containing the user's selected directories for storing and loading DVKs
	 */
	private ArrayList<File> dvkDirectories;
	
	/**
	 * Multiplied by fontSize to get the default space between Swing components.
	 */
	private double spaceMultiplier;
	
	/**
	 * Multiplied by fontSize to get the minimum DFrame width.
	 */
	private int frameWidth;
	
	/**
	 * Multiplied by fontSize to get the minimum DFrame height.
	 */
	private int frameHeight;
	
	/**
	 * Scroll unit, determines the sensitivity of scroll bars.
	 */
	private int scrollUnit;
	
	/**
	 * Swing "Look and Feel" for the program
	 */
	private String theme;
	
	/**
	 * Name of Font used for Swing components
	 */
	private String fontName;
	
	/**
	 * Font Size for Swing components.
	 */
	private int fontSize;
	
	/**
	 * Whether the program's main font is bold.
	 */
	private boolean fontBold;
	
	/**
	 * Whether the font should be anti-aliased.
	 */
	private boolean fontAA;
	
	/**
	 * Value of the default formatting for time clocks (12 or 24 hour)
	 */
	private int clockFormat;
	
	/**
	 * Value of the default formatting for dates (day-month-year, month-day-year, etc.)
	 */
	private int dateFormat;
	
	/**
	 * Whether to save journals when using the artist hosting GUI downloader
	 */
	private boolean saveJournals;
	
	/**
	 * Directory to save DVKs to when downloading from DeviantArt
	 */
	private File deviantArtDirectory;
	
	/**
	 * Directory to save DVKs to when downloading from FurAffinity
	 */
	private File furAffinityDirectory;
	
	/**
	 * Directory to save DVKs to when downloading from Inkbunny
	 */
	private File inkbunnyDirectory;
	
	/**
	 * Size of the preview thumbnails in the viewer GUI
	 */
	private int previewSize;
	
	/**
	 * Int value representing the type of scaling to use on images.
	 */
	private int scaleType;
	
	/**
	 * Double to multiply image size by when scaling directly.
	 */
	private double scaleAmount;
	
	/**
	 * Location of DVK details in the viewer GUI
	 */
	private int detailLocation;
	
	/**
	 * Whether to use thumbnails in the viewer GUI
	 */
	private boolean useThumbnails;
	
	/**
	 * Whether to show artists for the previews in the viewer GUI
	 */
	private boolean showArtists;
	
	/**
	 * Whether to show ratings for the previews in the viewer GUI
	 */
	private boolean showRatings;
	
	/**
	 * Whether to show view counts for the previews in the viewer GUI
	 */
	private boolean showViews;
	
	/**
	 * Default sort type for the program.
	 */
	private int sortType;
	
	/**
	 * Whether to group artists when sorting DVKs
	 */
	private boolean groupArtists;
	
	/**
	 * Whether to group sequences when sorting DVKs
	 */
	private boolean groupSequences;
	
	/**
	 * Whether to group sections when sorting DVKs
	 */
	private boolean groupSections;
	
	/**
	 * Whether to reverse the order of sorted DVKs
	 */
	private boolean reverseOrder;
	
	/**
	 * Initializes the DSettings Class
	 */
	public DSettings()
	{
		setDataFolder();
		readSettings();
		languageHandler = new DLanguageHandler(this, languageName);
		
	}//CONSTRUCTOR
	
	/**
	 * Sets the location of the main data folder for the project.
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
	 */
	private void clearSettings()
	{
		//GENERAL
		languageName = new String();
		
		//DVK
		useIndexes = true;
		updateIndexes = true;
		dvkDirectories = new ArrayList<>();
		
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
		
		//TIME
		clockFormat = 0;
		dateFormat = 0;
		
		//DOWNLOAD
		saveJournals = false;
		deviantArtDirectory = null;
		furAffinityDirectory = null;
		inkbunnyDirectory = null;
		
		//VIEWER
		previewSize = 100;
		scaleType = 0;
		scaleAmount = 1.0;
		detailLocation = 0;
		useThumbnails = true;
		showArtists = true;
		showRatings = false;
		showViews = false;
		sortType = 0;
		groupArtists = false;
		groupSequences = false;
		groupSections = false;
		reverseOrder = false;
		
		
	}//METHOD
	
	/**
	 * Reads the main settings file and sets the settings variables accordingly.
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
			
			//DVK
			dvkDirectories = ParseINI.getFileValues(null, DVK_DIRECTORY, settingsInfo, new ArrayList<File>());
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
			
			//time
			clockFormat = ParseINI.getIntValue(null, CLOCK_FORMAT, settingsInfo, clockFormat);
			dateFormat = ParseINI.getIntValue(null, DATE_FORMAT, settingsInfo, dateFormat);
			
			//DOWNLOAD
			saveJournals = ParseINI.getBooleanValue(null, SAVE_JOURNALS, settingsInfo, saveJournals);
			deviantArtDirectory = ParseINI.getFileValue(null, DEVIANTART_DIRECTORY, settingsInfo, null);
			furAffinityDirectory = ParseINI.getFileValue(null, FUR_AFFINITY_DIRECTORY, settingsInfo, null);
			inkbunnyDirectory = ParseINI.getFileValue(null, INKBUNY_DIRECTORY, settingsInfo, null);
			
			//VIEWER
			previewSize = ParseINI.getIntValue(null, PREVIEW_SIZE, settingsInfo, previewSize);
			scaleType = ParseINI.getIntValue(null, SCALE_TYPE, settingsInfo, scaleType);
			scaleAmount = ParseINI.getDoubleValue(null, SCALE_AMOUNT, settingsInfo, scaleAmount);
			detailLocation = ParseINI.getIntValue(null, DETAIL_LOCATION, settingsInfo, detailLocation);
			useThumbnails = ParseINI.getBooleanValue(null, USE_THUMBNAILS, settingsInfo, useThumbnails);
			showArtists = ParseINI.getBooleanValue(null, SHOW_ARTISTS, settingsInfo, showArtists);
			showRatings = ParseINI.getBooleanValue(null, SHOW_RATINGS, settingsInfo, showRatings);
			showViews = ParseINI.getBooleanValue(null, SHOW_VIEWS, settingsInfo, showViews);
			sortType = ParseINI.getIntValue(null, SORT_TYPE, settingsInfo, sortType);
			groupArtists = ParseINI.getBooleanValue(null, GROUP_ARTISTS, settingsInfo, groupArtists);
			groupSequences = ParseINI.getBooleanValue(null, GROUP_SEQUENCES, settingsInfo, groupSequences);
			groupSections = ParseINI.getBooleanValue(null, GROUP_SECTIONS, settingsInfo, groupSections);
			reverseOrder = ParseINI.getBooleanValue(null, REVERSE_ORDER, settingsInfo, reverseOrder);
			
		}//IF
		
	}//METHOD
	
	/**
	 * Writes the contents of settings variables to the main settings file.
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
		
		//TIME
		settingsInfo.add(new String());
		settingsInfo.add("[TIME]"); //$NON-NLS-1$
		settingsInfo.add(ParseINI.getAssignmentString(CLOCK_FORMAT, clockFormat));
		settingsInfo.add(ParseINI.getAssignmentString(DATE_FORMAT, dateFormat));
		
		//DVK
		settingsInfo.add(new String());
		settingsInfo.add("[DVK]"); //$NON-NLS-1$
		for(File dvkDirectory: dvkDirectories)
		{
			settingsInfo.add(ParseINI.getAssignmentString(DVK_DIRECTORY, dvkDirectory));
			
		}//FOR
		settingsInfo.add(ParseINI.getAssignmentString(USE_INDEXES, useIndexes));
		settingsInfo.add(ParseINI.getAssignmentString(UPDATE_INDEXES, updateIndexes));
		
		
		//DOWNLOAD
		settingsInfo.add(new String());
		settingsInfo.add("[DOWNLOAD]"); //$NON-NLS-1$
		settingsInfo.add(ParseINI.getAssignmentString(SAVE_JOURNALS, saveJournals));
		settingsInfo.add(ParseINI.getAssignmentString(DEVIANTART_DIRECTORY, deviantArtDirectory));
		settingsInfo.add(ParseINI.getAssignmentString(FUR_AFFINITY_DIRECTORY, furAffinityDirectory));
		settingsInfo.add(ParseINI.getAssignmentString(INKBUNY_DIRECTORY, inkbunnyDirectory));
		
		//VIEWER
		settingsInfo.add(new String());
		settingsInfo.add("[VIEWER]"); //$NON-NLS-1$
		settingsInfo.add(ParseINI.getAssignmentString(PREVIEW_SIZE, previewSize));
		settingsInfo.add(ParseINI.getAssignmentString(SCALE_TYPE, scaleType));
		settingsInfo.add(ParseINI.getAssignmentString(SCALE_AMOUNT, scaleAmount));
		settingsInfo.add(ParseINI.getAssignmentString(DETAIL_LOCATION, detailLocation));
		settingsInfo.add(ParseINI.getAssignmentString(USE_THUMBNAILS, useThumbnails));
		settingsInfo.add(ParseINI.getAssignmentString(SHOW_ARTISTS, showArtists));
		settingsInfo.add(ParseINI.getAssignmentString(SHOW_RATINGS, showRatings));
		settingsInfo.add(ParseINI.getAssignmentString(SHOW_VIEWS, showViews));
		settingsInfo.add(ParseINI.getAssignmentString(SORT_TYPE, sortType));
		settingsInfo.add(ParseINI.getAssignmentString(GROUP_ARTISTS, groupArtists));
		settingsInfo.add(ParseINI.getAssignmentString(GROUP_SEQUENCES, groupSequences));
		settingsInfo.add(ParseINI.getAssignmentString(GROUP_SECTIONS, groupSections));
		settingsInfo.add(ParseINI.getAssignmentString(REVERSE_ORDER, reverseOrder));
		
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
	 */
	public int[] getLanguageMnemonic(final String id)
	{
		return languageHandler.getLanguageMnemonic(id);
		
	}//METHOD
	
	/**
	 * Returns an ArrayList<String> of all the languages available through language files.
	 * 
	 * @return ArrayList<String> of all languages available
	 */
	public ArrayList<String> getLanguages()
	{
		return languageHandler.getLanguages();
		
	}//METHOD
	
	/**
	 * Sets the Language Name
	 * 
	 * @param languageName Language Name
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
	 */
	public File getDataFolder()
	{
		return dataFolder;
		
	}//METHOD
	
	/**
	 * Gets the Language Name.
	 * 
	 * @return Language Name
	 */
	public String getLanguageName()
	{
		return languageName;
		
	}//METHOD
	
	/**
	 * Sets value of useIndexes.
	 * 
	 * @param useIndexes useIndexes
	 */
	public void setUseIndexes(final boolean useIndexes)
	{
		this.useIndexes = useIndexes;
		
	}//METHOD
	
	/**
	 * Returns value of useIndexes.
	 * 
	 * @return useIndexes
	 */
	public boolean getUseIndexes()
	{
		return useIndexes;
		
	}//METHOD
	
	/**
	 * Sets the value of updateIndexes.
	 * 
	 * @param updateIndexes updateIndexes
	 */
	public void setUpdateIndexes(final boolean updateIndexes)
	{
		this.updateIndexes = updateIndexes;
		
	}//METHOD
	
	/**
	 * Returns the value of updateIndexes.
	 * 
	 * @return updateIndexes
	 */
	public boolean getUpdateIndexes()
	{
		return updateIndexes;
		
	}//METHOD
	
	/**
	 * Sets the DVK directories.
	 * 
	 * @param dvkDirectories DVK Directories
	 */
	public void setDvkDirectories(final ArrayList<File> dvkDirectories)
	{
		this.dvkDirectories = dvkDirectories;
		
	}//METHOD
	
	/**
	 * Returns list of DVK Directories.
	 * 
	 * @return DVK Directories
	 */
	public ArrayList<File> getDvkDirectories()
	{
		ArrayList<File> files = new ArrayList<>();
		files.addAll(dvkDirectories);
		return files;
		
	}//METHOD
	
	/**
	 * Gets the Space Multiplier
	 * 
	 * @return Space Multiplier
	 */
	public double getSpaceMultiplier()
	{
		return spaceMultiplier;
		
	}//METHOD
	
	/**
	 * Gets the default space size for Swing Components
	 * 
	 * @return Default Space Size
	 */
	public int getSpaceSize()
	{
		return (int)(getFontSize() * getSpaceMultiplier());
	
	}//METHOD
	
	/**
	 * Gets the Frame Height Multiplier
	 * 
	 * @return Frame Height Multiplier
	 */
	public int getFrameHeight()
	{
		return frameHeight;
		
	}//METHOD
	
	/**
	 * Gets the Frame Width Multiplier
	 * 
	 * @return Frame Width Multiplier
	 */
	public int getFrameWidth()
	{
		return frameWidth;
		
	}//METHOD
	
	/**
	 * Gets the scroll bar Scroll Unit.
	 * 
	 * @return Scroll Unit
	 */
	public int getScrollUnit()
	{
		return scrollUnit;
		
	}//METHOD
	
	/**
	 * Sets the program theme.
	 * 
	 * @param theme Theme
	 */
	public void setTheme(final String theme)
	{
		this.theme = theme;
		
	}//METHOD
	
	/**
	 * Gets the program theme.
	 * 
	 * @return Theme
	 */
	public String getTheme()
	{
		return theme;
		
	}//METHOD
	
	/**
	 * Sets the Font Name.
	 * 
	 * @param fontName Font Name
	 */
	public void setFontName(final String fontName)
	{
		this.fontName = fontName;
		
	}//METHOD
	
	/**
	 * Gets the Font Name
	 * 
	 * @return Font Name
	 */
	public String getFontName()
	{
		return fontName;
		
	}//METHOD
	
	/**
	 * Sets the Font Size.
	 * 
	 * @param fontSize Font Size
	 */
	public void setFontSize(final int fontSize)
	{
		this.fontSize = fontSize;
		
	}//METHOD
	
	/**
	 * Gets the Font Size.
	 * 
	 * @return Font Size
	 */
	public int getFontSize()
	{
		return fontSize;
		
	}//METHOD
	
	/**
	 * Sets whether the main font is bold.
	 * 
	 * @param fontBold Whether Font should be Bold
	 */
	public void setFontBold(final boolean fontBold)
	{
		this.fontBold = fontBold;
		
	}//METHOD
	
	/**
	 * Gets whether the main font is bold.
	 * 
	 * @return Whether Font is Bold
	 */
	public boolean getFontBold()
	{
		return fontBold;
		
	}//METHOD
	
	/**
	 * Sets the font anti-aliasing
	 * 
	 * @param fontAA Font Anti-Aliasing
	 */
	public void setFontAA(final boolean fontAA)
	{
		this.fontAA = fontAA;
		
	}//METHOD
	
	/**
	 * Returns the font anti-aliasing
	 * 
	 * @return Font Anti-Aliasing
	 */
	public boolean getFontAA()
	{
		return fontAA;
		
	}//METHOD
	
	/**
	 * Sets the default clock format.
	 * 
	 * @param clockFormat Clock Format
	 */
	public void setClockFormat(final int clockFormat)
	{
		this.clockFormat = clockFormat;
		
	}//METHOD
	
	/**
	 * Returns the default clock format.
	 * 
	 * @return Clock Format
	 */
	public int getClockFormat()
	{
		return clockFormat;
		
	}//METHOD
	
	/**
	 * Sets the default date format.
	 * 
	 * @param dateFormat Date Format
	 */
	public void setDateFormat(final int dateFormat)
	{
		this.dateFormat = dateFormat;
		
	}//METHOD
	
	/**
	 * Returns the default date format.
	 * 
	 * @return Date Format
	 */
	public int getDateFormat()
	{
		return dateFormat;
		
	}//METHOD
	
	/**
	 * Sets whether to save journals.
	 * 
	 * @param saveJournals Whether to save journals.
	 */
	public void setSaveJournals(final boolean saveJournals)
	{
		this.saveJournals = saveJournals;
		
	}//METHOD
	
	/**
	 * Returns whether to save journals.
	 * 
	 * @return Whether to save journals
	 */
	public boolean getSaveJournals()
	{
		return saveJournals;
		
	}//METHOD
	
	/**
	 * Sets the DeviantArt directory.
	 * 
	 * @param deviantArtDirectory DeviantArt Directory
	 */
	public void setDeviantArtDirectory(final File deviantArtDirectory)
	{
		this.deviantArtDirectory = deviantArtDirectory;
		
	}//METHOD
	
	/**
	 * Returns the DeviantArt directory.
	 * 
	 * @return DeviantArt Directory
	 */
	public File getDeviantArtDirectory()
	{
		return deviantArtDirectory;
		
	}//METHOD
	
	/**
	 * Sets the Fur Affinity Directory
	 * 
	 * @param furAffinityDirectory Fur Affinity Directory
	 */
	public void setFurAffinityDirectory(final File furAffinityDirectory)
	{
		this.furAffinityDirectory = furAffinityDirectory;
		
	}//METHOD
	
	/**
	 * Returns the Fur Affinity Directory
	 * 
	 * @return Fur Affinity Directory
	 */
	public File getFurAffinityDirectory()
	{
		return furAffinityDirectory;
		
	}//METHOD
	
	/**
	 * Sets the Inkbunny Directory
	 * 
	 * @param inkbunnyDirectory Inkbunny Directory
	 */
	public void setInkbunnyDirectory(final File inkbunnyDirectory)
	{
		this.inkbunnyDirectory = inkbunnyDirectory;
		
	}//METHOD
	
	/**
	 * Returns the Inkbunny Directory
	 * 
	 * @return Inkbunny Directory
	 */
	public File getInkbunnyDirectory()
	{
		return inkbunnyDirectory;
		
	}//METHOD
	
	/**
	 * Returns the Preview Size
	 * 
	 * @return Preview Size
	 */
	public int getPreviewSize()
	{
		return previewSize;
		
	}//METHOD
	
	/**
	 * Sets the scale type.
	 * 
	 * @param scaleType Scale Type
	 */
	public void setScaleType(final int scaleType)
	{
		this.scaleType = scaleType;
		
	}//METHOD
	
	/**
	 * Returns the scale type.
	 * 
	 * @return Scale Type
	 */
	public int getScaleType()
	{
		return scaleType;
		
	}//METHOD
	
	/**
	 * Sets the scale amount.
	 * 
	 * @param scaleAmount Scale Amount
	 */
	public void setScaleAmount(final double scaleAmount)
	{
		this.scaleAmount = scaleAmount;
		
	}//METHOD
	
	/**
	 * Returns the scale amount.
	 * 
	 * @return Scale Amount
	 */
	public double getScaleAmount()
	{
		return scaleAmount;
		
	}//METHOD
	
	/**
	 * Sets the location of DVK details.
	 * 
	 * @param detailLocation Location of DVK Details
	 */
	public void setDetailLocation(final int detailLocation)
	{
		this.detailLocation = detailLocation;
		
	}//METHOD
	
	/**
	 * Returns the location of DVK details.
	 * 
	 * @return Location of DVK Details
	 */
	public int getDetailLocation()
	{
		return detailLocation;
		
	}//METHOD
	
	/**
	 * Sets whether to use thumbnails.
	 * 
	 * @param useThumbnails Use Thumbnails
	 */
	public void setUseThumbnails(final boolean useThumbnails)
	{
		this.useThumbnails = useThumbnails;
		
	}//METHOD
	
	/**
	 * Gets whether to use thumbnails.
	 * 
	 * @return Use Thumbnails
	 */
	public boolean getUseThumbnails()
	{
		return useThumbnails;
		
	}//METHOD
	
	/**
	 * Sets the sort type.
	 * 
	 * @param sortType Sort Type
	 */
	public void setSortType(final int sortType)
	{
		this.sortType = sortType;
		
	}//METHOD
	
	/**
	 * Returns the sort type.
	 * 
	 * @return Sort Type
	 */
	public int getSortType()
	{
		return sortType;
		
	}//METHOD
	
	/**
	 * Sets whether to group artists.
	 * 
	 * @param groupArtists Whether to group artists
	 */
	public void setGroupArtists(final boolean groupArtists)
	{
		this.groupArtists = groupArtists;
		
	}//METHOD
	
	/**
	 * Returns whether to group artists.
	 * 
	 * @return Whether to group artists
	 */
	public boolean getGroupArtists()
	{
		return groupArtists;
		
	}//METHOD
	
	/**
	 * Sets whether to group sequences.
	 * 
	 * @param groupSequences Whether to group sequences
	 */
	public void setGroupSequences(final boolean groupSequences)
	{
		this.groupSequences = groupSequences;
		
	}//METHOD
	
	/**
	 * Returns whether to group sequences.
	 * 
	 * @return Whether to group sequences
	 */
	public boolean getGroupSequences()
	{
		return groupSequences;
		
	}//METHOD
	
	/**
	 * Sets whether to group sections.
	 * 
	 * @param groupSections Whether to group sections
	 */
	public void setGroupSections(final boolean groupSections)
	{
		this.groupSections = groupSections;
		
	}//METHOD
	
	/**
	 * Returns whether to group sections.
	 * 
	 * @return Whether to group sections
	 */
	public boolean getGroupSections()
	{
		return groupSections;
		
	}//METHOD
	
	/**
	 * Sets whether to reverse DVK sorting order.
	 * 
	 * @param reverseOrder Whether to reverse sorting order
	 */
	public void setReverseOrder(final boolean reverseOrder)
	{
		this.reverseOrder = reverseOrder;
		
	}//METHOD
	
	/**
	 * Returns whether to reverse DVK sorting order.
	 * 
	 * @return Whether to reverse sorting order
	 */
	public boolean getReverseOrder()
	{
		return reverseOrder;
		
	}//METHOD
	
	/**
	 * Sets whether to show artists.
	 * 
	 * @param showArtists Whether to show artists
	 */
	public void setShowArtists(final boolean showArtists)
	{
		this.showArtists = showArtists;
		
	}//METHOD
	
	/**
	 * Returns whether to show artists.
	 * 
	 * @return Whether to show artists
	 */
	public boolean getShowArtists()
	{
		return showArtists;
		
	}//METHOD
	
	/**
	 * Sets whether to show ratings.
	 * 
	 * @param showRatings Whether to show ratings
	 */
	public void setShowRatings(final boolean showRatings)
	{
		this.showRatings = showRatings;
		
	}//METHOD
	
	/**
	 * Returns whether to show ratings
	 * 
	 * @return Whether to show ratings
	 */
	public boolean getShowRatings()
	{
		return showRatings;
		
	}//METHOD
	
	/**
	 * Sets whether to show views.
	 * 
	 * @param showViews Whether to show views
	 */
	public void setShowViews(final boolean showViews)
	{
		this.showViews = showViews;
		
	}//METHOD
	
	/**
	 * Returns whether to show views.
	 * 
	 * @return Whether to show views
	 */
	public boolean getShowViews()
	{
		return showViews;
		
	}//METHOD
	
}//CLASS
