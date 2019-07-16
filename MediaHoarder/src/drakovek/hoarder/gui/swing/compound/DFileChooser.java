package drakovek.hoarder.gui.swing.compound;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.ExtensionFilter;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.components.DTextField;
import drakovek.hoarder.gui.swing.listeners.DEnterListener;
import drakovek.hoarder.gui.swing.listeners.DListClickListener;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.processing.sort.FileSort;

/**
 * Creates a GUI for choosing a file to either open or save.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DFileChooser extends BaseGUI
{
	/**
	 * Action ID for when enter is pressed while the root list is focused.
	 * 
	 * @since 2.0
	 */
	private static final String ROOT_ENTER_ACTION = DefaultLanguage.ROOTS + DEnterListener.ENTER_PRESSED;
	
	/**
	 * Action ID for when enter is pressed while the file list is focused.
	 * 
	 * @since 2.0
	 */
	private static final String FILE_ENTER_ACTION = DefaultLanguage.FILES + DEnterListener.ENTER_PRESSED;
	
	/**
	 * Action ID for when an item in the file list has been double/triple clicked
	 * 
	 * @since 2.0
	 */
	private static final String FILE_CLICK_ACTION = "file_clicked"; //$NON-NLS-1$
	
	/**
	 * File selected by the user
	 * 
	 * @since 2.0
	 */
	private File returnFile;

	
	/**
	 * Main dialog for the file chooser.
	 * 
	 * @since 2.0
	 */
	private DDialog dialog;
	
	/**
	 * Panel contained within the file chooser dialog
	 * 
	 * @since 2.0
	 */
	private JPanel panel;
	
	/**
	 * Filter for listing files in the file chooser
	 * 
	 * @since 2.0
	 */
	private ExtensionFilter filter;
	
	/**
	 * List that shows the root directories of the user's computer
	 * 
	 * @since 2.0
	 */
	private DList rootList;
	
	/**
	 * List that shows the files for the current selected directory
	 * 
	 * @since 2.0
	 */
	private DList fileList;
	
	/**
	 * Text that shows the currently selected directory
	 * 
	 * @since 2.0
	 */
	private DTextField nameText;
	
	/**
	 * Root directories of the user's computer
	 * 
	 * @since 2.0
	 */
	private File[] roots;
	
	/**
	 * Files in the currently selected directory
	 * 
	 * @since 2.0
	 */
	private File[] files;
	
	/**
	 * ArrayList<File> containing the history of the user's file navigation in the current session.
	 * 
	 * @since 2.0
	 */
	private ArrayList<File> fileHistory;
	
	/**
	 * Initializes the DFileChooser class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public DFileChooser(DSettings settings)
	{
		super(settings);
		
		//CREATE ROOT LIST PANEL
		rootList = new DList(this, false, DefaultLanguage.ROOTS);
		DScrollPane rootScroll = new DScrollPane(settings, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, rootList);
		JPanel rootListPanel = getSpacedPanel(rootScroll, 1, 1, true, true, false, false);
		
		//CREATE FILE LIST PANEL
		fileList = new DList(this, false, DefaultLanguage.FILES);
		fileList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		fileList.addMouseListener(new DListClickListener(this, fileList, FILE_CLICK_ACTION));
		DScrollPane fileScroll = new DScrollPane(settings, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, fileList);
		JPanel fileListPanel = getSpacedPanel(fileScroll, 1, 1, true, true, false, false);
		
		//CREATE DIRECTORY PANEL
		JPanel backPanel = new JPanel();
		backPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		backPanel.add(new DButton(this, DefaultLanguage.BACK));
		backPanel.add(new DButton(this, DefaultLanguage.PARENT));
		
		JPanel directoryPanel = new JPanel();
		directoryPanel.setLayout(new BorderLayout());
		directoryPanel.add(new DLabel(this, fileList, DefaultLanguage.FILES), BorderLayout.WEST);
		directoryPanel.add(backPanel, BorderLayout.EAST);
		
		//CREATE NAME PANEL
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout());
		nameText = new DTextField(this, DefaultLanguage.NAME);
		namePanel.add(new DLabel(this, nameText, DefaultLanguage.NAME), BorderLayout.EAST);
		namePanel.add(Box.createRigidArea(new Dimension(getSettings().getFontSize() * 5, 1)), BorderLayout.WEST);
		
		//CREATE CENTER PANEL
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints centerCST = new GridBagConstraints();
		centerCST.gridx = 0;		centerCST.gridy = 0;
		centerCST.gridwidth = 1;	centerCST.gridheight = 1;
		centerCST.weightx = 0;		centerCST.weighty = 0;
		centerCST.fill = GridBagConstraints.BOTH;
		centerPanel.add(new DLabel(this, rootList, DefaultLanguage.ROOTS), centerCST);
		centerCST.gridy = 2;
		centerPanel.add(namePanel, centerCST);
		centerCST.gridx = 1;
		centerPanel.add(getHorizontalSpace(), centerCST);
		centerCST.gridx = 2;		centerCST.weightx = 1;
		centerPanel.add(nameText, centerCST);
		centerCST.gridy = 0;
		centerPanel.add(directoryPanel, centerCST);
		centerCST.gridy = 1;		centerCST.weighty = 1;
		centerPanel.add(fileListPanel, centerCST);
		centerCST.gridx = 0;		centerCST.weightx = 0;
		centerPanel.add(rootListPanel, centerCST);
		
		//CREATE BOTTOM PANEL
		JPanel openPanel = new JPanel();
		openPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		openPanel.add(new DButton(this, DefaultLanguage.CANCEL));
		openPanel.add(new DButton(this, DefaultLanguage.OPEN));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints bottomCST = new GridBagConstraints();
		bottomCST.gridx = 2;		bottomCST.gridy = 0;
		bottomCST.gridwidth = 1;	bottomCST.gridheight = 3;
		bottomCST.weightx = 0;		bottomCST.weighty = 0;
		bottomPanel.add(openPanel, bottomCST);
		bottomCST.gridx = 0;		bottomCST.weightx = 1;
		bottomCST.gridwidth = 2;
		bottomPanel.add(getHorizontalSpace(), bottomCST);
		
		//FINALIZE PANEL
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(getSpacedPanel(centerPanel));
		panel.add(getSpacedPanel(bottomPanel, 1, 0, false, true, false, true), BorderLayout.SOUTH);
		
	}//CONSTRUCTOR
	
	/**
	 * Creates the file chooser dialog for opening a directory.
	 * 
	 * @param owner DFrame to which the file chooser is tied
	 * @param startDirectory Directory to start within
	 * @return Directory selected by the user
	 * @since 2.0
	 */
	public File getFileOpen(DFrame owner, final File startDirectory)
	{
		owner.setAllowExit(false);
		returnFile = null;
		initializeChooser(new String[0], startDirectory);
		dialog = new DDialog(owner, panel ,getTitle(DefaultLanguage.OPEN_TITLE), true, getSettings().getFontSize() * 30, getSettings().getFontSize() * 20);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		if(startDirectory != null && startDirectory.isDirectory())
		{
			fileList.requestFocusInWindow();
			
		}//IF
		else
		{
			rootList.setSelectedIndex(0);
			rootList.requestFocusInWindow();
			
		}//ELSE

		dialog.setVisible(true);
		dialog = null;
		owner.setAllowExit(true);
		return returnFile;
		
	}//METHOD
	
	/**
	 * Initializes the file chooser starting directory
	 * 
	 * @param extensions Extensions to allow in the list of files.
	 * @param startDirectory Directory for the file chooser to start from
	 * @since 2.0
	 */
	private void initializeChooser(final String[] extensions, final File startDirectory)
	{
		fileHistory = new ArrayList<>();
		filter = new ExtensionFilter(extensions);
		roots = File.listRoots();
		while(roots != null && roots.length == 1)
        {
            roots = roots[0].listFiles(filter);

        }//WHILE

        if(roots == null)
        {
            roots= new File[0];

        }//IF
        
        roots = FileSort.sortFiles(roots);
        String[] rootStrings = new String[roots.length];
		for(int i = 0; i < roots.length; i++)
		{
			rootStrings[i] = roots[i].getName() + StringMethods.extendCharacter(' ', 6);
			
		}//FOR
        rootList.setListData(rootStrings);
		
        setDirectory(startDirectory);
        
	}//METHOD
	
	/**
	 * Sets the current directory to show to the user.
	 * 
	 * @param directory Directory to show
	 * @since 2.0
	 */
	private void setDirectory(final File directory)
	{
		if(directory != null && directory.isDirectory())
		{
			if(fileHistory.size() == 0 || !fileHistory.get(fileHistory.size() - 1).equals(directory))
			{
				fileHistory.add(directory);
				
			}//IF
			
			files = directory.listFiles(filter);
			if(files == null)
			{
				files = new File[0];
				
			}//IF
			
			files = FileSort.sortFiles(files); 
			String[] directoryStrings = new String[files.length];
			for(int i = 0; i < files.length; i++)
			{
				directoryStrings[i] = files[i].getName() + StringMethods.extendCharacter(' ', 6);
				
			}//FOR
			fileList.setListData(directoryStrings);
			
			nameText.setText(directory.getAbsolutePath());
			
		}//IF
		
	}//METHOD
	
	/**
	 * Sets the name text field to the selected file.
	 * 
	 * @since 2.0
	 */
	private void setNameFromFile()
	{
		int selected = fileList.getSelectedIndex();
		if(selected != -1)
		{
			nameText.setText(files[selected].getAbsolutePath());
			
		}//IF
		
	}//METHOD
	
	/**
	 * Sets the displayed directory to the selected root directory.
	 * 
	 * @since 2.0
	 */
	private void setDirectoryFromRoot()
	{
		int selected = rootList.getSelectedIndex();
		if(selected != -1)
		{
			setDirectory(roots[selected]);
			
		}//IF
		
	}//METHOD

	/**
	 * Sets the displayed directory to the currently selected file.
	 * 
	 * @since 2.0
	 */
	private void setDirectoryFromFile()
	{
		int selected = fileList.getSelectedIndex();
		if(selected != -1)
		{
			setDirectory(files[selected]);
			
		}//IF
		
	}//METHOD
	
	/**
	 * Sets the displayed directory to the file at the given index value.
	 * 
	 * @param index Index Value of Directory to be displayed
	 * @since 2.0
	 */
	private void setDirectoryFromFileIndex(final int index)
	{
		if(index != -1 && index < files.length)
		{
			setDirectory(files[index]);
			
		}//IF
		
	}//METHOD
	
	/**
	 * Sets the displayed directory to the directory last selected.
	 * 
	 * @since 2.0
	 */
	private void setDirectoryToPrevious()
	{
		if(fileHistory.size() > 1)
		{
			fileHistory.remove(fileHistory.size() - 1);
			setDirectory(fileHistory.get(fileHistory.size() - 1));
			
		}//IF
		
	}//METHOD
	
	/**
	 * Sets the displayed directory to the parent of the currently displayed directory.
	 * 
	 * @since 2.0
	 */
	private void setDirectoryToParent()
	{
		if(fileHistory.size() > 0)
		{
			setDirectory(fileHistory.get(fileHistory.size() - 1).getParentFile());
			
		}//IF
		
	}//METHOD
	
	/**
	 * Finalizes the open process by setting the returnFile to the selected directory and then closes the dialog.
	 * 
	 * @since 2.0
	 */
	private void open()
	{
		returnFile = new File(nameText.getText());
		if(returnFile == null || !returnFile.isDirectory())
		{
			if(fileHistory.size() > 0)
			{
				returnFile = fileHistory.get(fileHistory.size() - 1);
			}
			else
			{
				returnFile = null;
				
			}//ELSE
					
		}//IF
	
		dialog.dispose();
		
	}//METHOD
	
	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.FILES:
				setNameFromFile();
				break;
			case DefaultLanguage.ROOTS:
				setDirectoryFromRoot();
				break;
			case ROOT_ENTER_ACTION:
				fileList.requestFocusInWindow();
				break;
			case FILE_ENTER_ACTION:
				setDirectoryFromFile();
				break;
			case FILE_CLICK_ACTION:
				setDirectoryFromFileIndex(value);
				break;
			case DefaultLanguage.NAME:
				setDirectory(new File(nameText.getText()));
				break;
			case DefaultLanguage.BACK:
				setDirectoryToPrevious();
				break;
			case DefaultLanguage.PARENT:
				setDirectoryToParent();
				break;
			case DefaultLanguage.OPEN:
				open();
				break;
			case DefaultLanguage.CANCEL:
				dialog.dispose();
				break;
			
		}//SWITCH
		
	}//METHOD
	
}//CLASS
