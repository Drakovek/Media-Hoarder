package drakovek.hoarder.gui.swing.compound;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.ExtensionFilter;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.ComponentDisabler;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DComboBox;
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFileList;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.components.DTextField;
import drakovek.hoarder.gui.swing.listeners.DEnterListener;
import drakovek.hoarder.gui.swing.listeners.DListClickListener;
import drakovek.hoarder.gui.swing.listeners.DResizeListener;
import drakovek.hoarder.processing.sort.FileSort;

/**
 * Contains methods for choosing a file to either open or save.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class DFileChooser extends BaseGUI implements ComponentDisabler
{
	/**
	 * Action ID for when enter is pressed while the file list is in focus
	 */
	private static final String LIST_ENTER_ACTION = DefaultLanguage.FILE + DEnterListener.ENTER_PRESSED;
	
	/**
	 * Ellipsis string
	 */
	private static final String ELLIPSIS = "..."; //$NON-NLS-1$
	
	/**
	 * Main file chooser dialog
	 */
	private DDialog dialog;
	
	/**
	 * Main panel for the file chooser dialog
	 */
	private JPanel panel;
	
	/**
	 * Combo Box for selecting root directories
	 */
	private DComboBox rootBox;
	
	/**
	 * Combo Box for selecting the file types to show.
	 */
	private DComboBox fileTypeBox;
	
	/**
	 * Label to show the currently selected directory
	 */
	private DLabel directoryLabel;
	
	/**
	 * Text Field for showing and entering the selected file path
	 */
	private DTextField fileNameText;
	
	/**
	 * List showing the files of the currently displayed directory
	 */
	private DFileList fileList;
	
	/**
	 * Scroll pane for holding the file list
	 */
	private DScrollPane fileScroll;
	
	/**
	 * Button used for finishing the file chooser process. Shows either "Open" or "Save"
	 */
	private DButton finishButton;
	
	/**
	 * File to return when finished with the file chooser.
	 */
	private File returnFile;
	
	/**
	 * File currently selected by the user.
	 */
	private File selectedFile;
	
	/**
	 * Whether or not the user is prompted to open a file. If false, the user is prompted to save a file.
	 */
	private boolean isOpening;
	
	/**
	 * Array of the extensions allowed to be shown when showing the contents of a directory
	 */
	private String[] extensions;
	
	/**
	 * Array of the user's root directories
	 */
	private File[] roots;
	
	/**
	 * Array of files in the current directory
	 */
	private File[] files;
	
	/**
	 * The directory currently being shown to the user.
	 */
	private File currentDirectory;
	
	/**
	 * Filter for the showing the files of the current directory
	 */
	private ExtensionFilter filter;
	
	/**
	 * Initializes the DFileChooser class by formatting the main GUI.
	 * 
	 * @param settings Program Settings
	 */
	public DFileChooser(DSettings settings)
	{
		super(settings);
		returnFile = null;
		extensions = null;
		selectedFile = null;
		filter = new ExtensionFilter(null, true);
		currentDirectory = null;
		
		//CREATE TOP PANEL
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		buttonPanel.add(new DButton(this, DefaultLanguage.PARENT));
		buttonPanel.add(new DButton(this, DefaultLanguage.NEW_DIRECTORY));
		
		rootBox = new DComboBox(this, DefaultLanguage.ROOTS);
		directoryLabel = new DLabel(this, null, new String());
		JPanel rootPanel = getHorizontalStack(new DLabel(this, rootBox, DefaultLanguage.ROOTS), 0, getHorizontalStack(rootBox, 1, buttonPanel, 0), 1);
		JPanel topPanel = getVerticalStack(getVerticalStack(rootPanel, new JSeparator(SwingConstants.HORIZONTAL)), directoryLabel);
		
		//CREATE NAME PANEL
		fileNameText = new DTextField(this, DefaultLanguage.FILE_NAME);
		fileTypeBox = new DComboBox(this, DefaultLanguage.FILE_TYPE);
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridBagLayout());
		GridBagConstraints nameCST = new GridBagConstraints();
		nameCST.gridx = 0;			nameCST.gridy = 0;
		nameCST.gridwidth = 1;		nameCST.gridheight = 1;
		nameCST.weightx = 0;		nameCST.weighty = 0;
		nameCST.fill = GridBagConstraints.BOTH;
		namePanel.add(new DLabel(this, fileNameText, DefaultLanguage.FILE_NAME), nameCST);
		nameCST.gridy = 1;
		namePanel.add(getVerticalSpace(), nameCST);
		nameCST.gridy = 2;
		namePanel.add(new DLabel(this, fileTypeBox, DefaultLanguage.FILE_TYPE), nameCST);
		nameCST.gridx = 1;
		namePanel.add(getHorizontalSpace(), nameCST);
		nameCST.gridx = 2;			nameCST.weightx = 1;
		namePanel.add(fileTypeBox, nameCST);
		nameCST.gridy = 0;
		namePanel.add(fileNameText, nameCST);
		
		//CREATE FILE PANEL
		fileList = new DFileList(this, false, DefaultLanguage.FILE);
		fileList.setLayoutOrientation(JList.VERTICAL_WRAP);
		fileList.addMouseListener(new DListClickListener(this, fileList, DListClickListener.LIST_CLICKED));
		fileScroll = new DScrollPane(settings, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, fileList);
		fileScroll.addComponentListener(new DResizeListener(this, null));
		
		//CREATE CENTER PANEL
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(getSpacedPanel(topPanel, 1, 0, true, true, true, true), BorderLayout.NORTH);
		centerPanel.add(getSpacedPanel(fileScroll, 1, 1, false, false, true, true), BorderLayout.CENTER);
		centerPanel.add(getSpacedPanel(namePanel, 1, 0, true, false, true, true), BorderLayout.SOUTH);
		
		//CREATE BOTTOM PANEL
		finishButton = new DButton(this, DefaultLanguage.SAVE);
		JPanel openPanel = new JPanel();
		openPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		openPanel.add(new DButton(this, DefaultLanguage.CANCEL));
		openPanel.add(finishButton);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(openPanel, BorderLayout.EAST);
		
		//FINALIZE PANEL
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(getSpacedPanel(bottomPanel, 1, 0, true, true, true, true), BorderLayout.SOUTH);
		
		initializeChooser(null);
		
	}//CONSTRUCTOR
	
	
	/**
	 * Opens the file chooser dialog for opening a file.
	 * 
	 * @param owner DFrame used as the file chooser's owner
	 * @param startDirectory Directory to start with when the file chooser opens
	 * @param fileExtensions Extensions to allow opening
	 * @return File chosen by the user (null if no file chosen)
	 */
	public File openDialog(DFrame owner, final File startDirectory, final String[] fileExtensions)
	{
		isOpening = true;
		extensions = fileExtensions;
		returnFile = null;
		selectedFile = null;
		owner.setAllowExit(false);
		initializeChooser(startDirectory);
		dialog = new DDialog(owner, panel ,getTitle(DefaultLanguage.OPEN_TITLE), true, getSettings().getFontSize() * 35, getSettings().getFontSize() * 25);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		dialog = null;
		owner.setAllowExit(true);
		return returnFile;
		
	}//METHOD
	
	/**
	 * Opens the file chooser dialog for opening a file.
	 * 
	 * @param owner DDialog used as the file chooser's owner
	 * @param startDirectory Directory to start with when the file chooser opens
	 * @param fileExtensions Extensions to allow opening
	 * @return File chosen by the user (null if no file chosen)
	 */
	public File openDialog(DDialog owner, final File startDirectory, final String[] fileExtensions)
	{
		isOpening = true;
		extensions = fileExtensions;
		returnFile = null;
		selectedFile = null;
		initializeChooser(startDirectory);
		dialog = new DDialog(owner, panel ,getTitle(DefaultLanguage.OPEN_TITLE), true, getSettings().getFontSize() * 35, getSettings().getFontSize() * 25);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		dialog = null;
		return returnFile;
		
	}//METHOD
	
	/**
	 * Opens the file chooser dialog for saving a file.
	 * 
	 * @param owner DFrame used as the file chooser's owner
	 * @param startDirectory Directory to start with when the file chooser opens
	 * @param fileExtensions Extensions to allow opening
	 * @return File chosen by the user (null if no file chosen)
	 */
	public File openSaveDialog(DFrame owner, final File startDirectory, final String[] fileExtensions)
	{
		isOpening = false;
		extensions = fileExtensions;
		returnFile = null;
		selectedFile = null;
		owner.setAllowExit(false);
		initializeChooser(startDirectory);
		dialog = new DDialog(owner, panel ,getTitle(DefaultLanguage.SAVE_TITLE), true, getSettings().getFontSize() * 35, getSettings().getFontSize() * 25);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		dialog = null;
		owner.setAllowExit(true);
		return returnFile;
		
	}//METHOD
	
	/**
	 * Opens the file chooser dialog for saving a file.
	 * 
	 * @param owner DDialog used as the file chooser's owner
	 * @param startDirectory Directory to start with when the file chooser opens
	 * @param fileExtensions Extensions to allow opening
	 * @return File chosen by the user (null if no file chosen)
	 */
	public File openSaveDialog(DDialog owner, final File startDirectory, final String[] fileExtensions)
	{
		isOpening = false;
		extensions = fileExtensions;
		returnFile = null;
		selectedFile = null;
		initializeChooser(startDirectory);
		dialog = new DDialog(owner, panel ,getTitle(DefaultLanguage.SAVE_TITLE), true, getSettings().getFontSize() * 35, getSettings().getFontSize() * 25);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		dialog = null;
		return returnFile;
		
	}//METHOD
	
	/**
	 * Sets the initial state of the file chooser when it is opened.
	 * 
	 * @param startDirectory Directory to start with when the file chooser opens
	 */
	private void initializeChooser(final File startDirectory)
	{
		//SET TEXT FOR FINISH BUTTON
		if(isOpening)
		{
			finishButton.setTextID(DefaultLanguage.OPEN);
			
		}//IF
		else
		{
			finishButton.setTextID(DefaultLanguage.SAVE);
			
		}//ELSE
		
		//SET ROOT DIRECTORIES
		roots = File.listRoots();
		while(roots != null && roots.length == 1)
		{
			roots = roots[0].listFiles(new ExtensionFilter(null, false));
			
		}//WHILE
		
		if(roots == null)
		{
			roots = new File[0];
			
		}//IF
		
		roots = FileSort.sortFiles(roots);
		String[] rootStrings = new String[roots.length];
		for(int i = 0; i < roots.length; i++)
		{
			rootStrings[i] = roots[i].getAbsolutePath();
			
		}//FOR
		
		rootBox.setData(rootStrings);
		
		//SET FILE TYPE BOX OPTIONS
		if(extensions == null || extensions.length == 0)
		{
			String[] fileType = new String[1];
			if(isOpening)
			{
				fileType[0] = getSettings().getLanguageText(DefaultLanguage.DIRECTORIES_ONLY);
				filter.setExtensions(null);
				filter.setAllowAll(false);
						
			}//IF
			else
			{
				fileType[0] = getSettings().getLanguageText(DefaultLanguage.ALL_FILES);
				filter.setExtensions(null);
				filter.setAllowAll(true);
						
			}//ELSE
					
			fileTypeBox.setData(fileType);

		}//IF
		else if(extensions.length == 1)
		{
			String[] fileType = {'*' + extensions[0]};
			fileTypeBox.setData(fileType);
			String[] filterExtension = {extensions[0]};
			filter.setExtensions(filterExtension);
			filter.setAllowAll(false);
			
		}//ELSE IF
		else
		{
			String[] fileTypes = new String[extensions.length + 1];
			fileTypes[0] = getSettings().getLanguageText(DefaultLanguage.ALL_ALLOWED_EXTENSIONS);
			for(int i = 0; i < extensions.length; i++)
			{
				fileTypes[i + 1] = '*' + extensions[i];
				
			}//FOR
			
			fileTypeBox.setData(fileTypes);
			filter.setExtensions(extensions);
			filter.setAllowAll(false);
			
		}//ELSE
		
		if(startDirectory != null && startDirectory.isDirectory())
		{
			setDirectory(startDirectory);
			
		}//IF
		else
		{
			rootBox.setSelectedIndex(0);
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Sets the displayed file list to show the contents of a given directory.
	 * 
	 * @param directory Given Directory
	 */
	private void setDirectory(final File directory)
	{
		if(directory != null)
		{
			if(directory.isDirectory())
			{
				currentDirectory = directory;
				selectedFile = directory;
				files = FileSort.sortFiles(currentDirectory.listFiles(filter));
				fileList.setListData(files);
				setDirectoryLabel(currentDirectory);
				setNameText(currentDirectory);
			}
			else if(!isOpening || directory.exists())
			{
				selectedFile = directory;
				setNameText(selectedFile);
				attemptFinish();
				
			}//ELSE IF
			
		}//IF
		
	}//METHOD
	
	/**
	 * Sets the directory label to show a given directory.
	 * 
	 * @param directory Given Directory
	 */
	private void setDirectoryLabel(final File directory)
	{
		if(directory != null)
		{
			String directoryString = directory.getAbsolutePath();
			directoryLabel.setToolTipText(directoryString);
			int width;
			while(true)
			{
				width = directoryLabel.getFontMetrics(getFont()).stringWidth(directoryString) + getSettings().getSpaceSize();
				if(directoryString.length() == 0 || width < fileScroll.getWidth())
				{
					break;
				
				}//IF
			
				if(directoryString.startsWith(ELLIPSIS))
				{
					directoryString = directoryString.substring(4);
					
				}//IF
				
				if(directoryString.startsWith(Character.toString('/')) || directoryString.startsWith(Character.toString('\\')))
				{
					directoryString = directoryString.substring(1);
					
				}//IF
				
				int i = directoryString.indexOf('/');
				if(i == -1)
				{
					i = directoryString.indexOf('\\');
				
				}//IF
			
				if(i == -1)
				{
					directoryString = new String();
				
				}//IF
				else
				{
					directoryString = ELLIPSIS + directoryString.substring(i);
				
				}//IF
			
			}//WHILE
		
			directoryLabel.setText(directoryString);
		
		}//IF
		
	}//METHOD
	
	/**
	 * Sets the current directory to a a directory in the file list corresponding to a given index.
	 * 
	 * @param index Index of file to use as current directory
	 */
	private void setDirectoryFromIndex(final int index)
	{
		if(index > -1 && index < files.length)
		{
			setDirectory(files[index]);
			
		}//IF
		
	}//METHOD
	
	/**
	 * Deals with a root directory being selected. Sets the current directory to the selected root.
	 */
	private void rootSelected()
	{
		int selected = rootBox.getSelectedIndex();
		if(selected != -1)
		{
			setDirectory(roots[selected]);
			
		}//IF
		
	}//METHOD
	
	/**
	 * Deals with a file name being entered. Sets the current directory to the file name entered in the file name text field.
	 */
	private void fileNameEntered()
	{
		String nameText = fileNameText.getText();
		File file = new File(nameText);
		
		if(!file.exists())
		{
			for(File curFile: files)
			{
				if(curFile.getName().equals(nameText))
				{
					file = curFile;
					break;
					
				}//IF
				
			}//FOR
			
		}//IF
		
		if(file.isDirectory())
		{
			setDirectory(file);
			
		}//IF
		else if(!isOpening)
		{
			attemptFinish();
			
		}//ELSE IF
		else
		{
			setNameText(selectedFile);
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Changes the current file filter based on the user's selection in the file type combo box.
	 */
	private void changeFilter()
	{
		if(extensions != null && extensions.length > 1)
		{
			int selected = fileTypeBox.getSelectedIndex();
			if(selected == 0)
			{
				filter.setExtensions(extensions);
				filter.setAllowAll(false);
				
			}//IF
			else if(selected > 0)
			{
				String[] extension = {extensions[selected - 1]};
				filter.setExtensions(extension);
				filter.setAllowAll(false);
				
			}//ELSE IF
			
			setDirectory(currentDirectory);
			
		}//IF
		
	}//METHOD
	
	/**
	 * Deals with a file being selected in the currently displayed directory.
	 */
	public void fileSelected()
	{
		int selected = fileList.getSelectedIndex();
		if(selected != -1)
		{
			selectedFile = files[selected];
			setNameText(selectedFile);
			
		}//IF
		
	}//METHOD
	
	/**
	 * Attempts to finish the file choosing process, attempting to set the return file.
	 */
	private void attemptFinish()
	{	
		if(isOpening)
		{
			fileNameEntered();
			
			if(extensions == null || extensions.length == 0 || !selectedFile.isDirectory())
			{
				returnFile = selectedFile;
				dialog.dispose();
				
			}//IF
			
		}//IF
		else
		{
			String nameText = fileNameText.getText();
			boolean hasExtension = false;
			if(extensions != null && extensions.length > 0)
			{
				for(String extension: extensions)
				{
					if(nameText.endsWith(extension))
					{
						hasExtension = true;
						break;
						
					}//IF
					
				}//FOR
				
			}//IF
			else
			{
				hasExtension = true;
				
			}//ELSE
			
			if(!hasExtension)
			{
				int selection = fileTypeBox.getSelectedIndex() - 1;
				if(selection > -1 && selection < extensions.length)
				{
					nameText = nameText + extensions[selection];
					
				}//IF
				else
				{
					nameText = nameText + extensions[0];
					
				}//ELSE
				
			}//IF
			
			File file = new File(nameText);
			File parent = file.getParentFile();
			if(parent == null || !parent.isDirectory())
			{
				file = new File(currentDirectory, nameText);
				
			}//IF
			
			boolean shouldSave = true;
			
			if(file.exists())
			{
				DButtonDialog buttonDialog = new DButtonDialog(getSettings());
				String[] buttonIDs = {DefaultLanguage.YES, DefaultLanguage.NO};
				shouldSave = buttonDialog.openButtonDialog(this, dialog, DefaultLanguage.FILE_EXISTS, DefaultLanguage.FILE_EXISTS_MESSAGES, buttonIDs).equals(DefaultLanguage.YES);
			
			}//IF
			
			if(shouldSave)
			{
				returnFile = file;
				dialog.dispose();
				
			}//IF
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Sets the file name text field based on a given file.
	 * 
	 * @param file Given File
	 */
	private void setNameText(final File file)
	{
		if(file != null && file.exists())
		{
			fileNameText.setText(file.getName());
			
		}//IF
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.ROOTS:
				rootSelected();
				break;
			case DefaultLanguage.FILE_TYPE:
				changeFilter();
				break;
			case DefaultLanguage.FILE_NAME:
				fileNameEntered();
				break;
			case DefaultLanguage.FILE:
				fileSelected();
				break;
			case LIST_ENTER_ACTION:
				setDirectory(selectedFile);
				break;
			case DListClickListener.LIST_CLICKED:
				setDirectoryFromIndex(value);
				break;
			case DefaultLanguage.PARENT:
				setDirectory(currentDirectory.getParentFile());
				break;
			case DResizeListener.RESIZE:
				fileList.fitRowsToSize();
				setDirectoryLabel(currentDirectory);
				break;
			case DefaultLanguage.SAVE:
				attemptFinish();
				break;
			case DefaultLanguage.CANCEL:
				dialog.dispose();
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void enableAll(){}

	@Override
	public void disableAll(){}
	
}//CLASS
