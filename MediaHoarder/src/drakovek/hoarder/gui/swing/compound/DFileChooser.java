package drakovek.hoarder.gui.swing.compound;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DComboBox;
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.components.DTextField;

/**
 * Contains methods for choosing a file to either open or save.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DFileChooser extends BaseGUI
{
	/**
	 * Main file chooser dialog
	 * 
	 * @since 2.0
	 */
	private DDialog dialog;
	
	/**
	 * Main panel for the file chooser dialog
	 * 
	 * @since 2.0
	 */
	private JPanel panel;
	
	/**
	 * Combo Box for selecting root directories
	 * 
	 * @since 2.0
	 */
	private DComboBox rootBox;
	
	/**
	 * Combo Box for selecting the file types to show.
	 * 
	 * @since 2.0
	 */
	private DComboBox fileTypeBox;
	
	/**
	 * Text Field for showing and entering the selected file path
	 * 
	 * @since 2.0
	 */
	private DTextField fileNameText;
	
	/**
	 * List showing the files of the currently displayed directory
	 * 
	 * @since 2.0
	 */
	private DList fileList;
	
	/**
	 * File to return when finished with the file chooser.
	 * 
	 * @since 2.0
	 */
	private File returnFile;
	
	/**
	 * Initializes the DFileChooser class by formatting the main GUI.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public DFileChooser(DSettings settings)
	{
		super(settings);
		returnFile = null;
		
		//CREATE ROOT PANEL
		rootBox = new DComboBox(this, DefaultLanguage.ROOTS);
		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new GridBagLayout());
		GridBagConstraints rootCST = new GridBagConstraints();
		rootCST.gridx = 0;		rootCST.gridy = 0;
		rootCST.gridwidth = 1;	rootCST.gridheight = 3;
		rootCST.weightx = 0;	rootCST.weighty = 0;
		rootCST.fill = GridBagConstraints.BOTH;
		rootPanel.add(new DLabel(this, rootBox, DefaultLanguage.ROOTS), rootCST);
		rootCST.gridx = 1;
		rootPanel.add(getHorizontalSpace(), rootCST);
		rootCST.gridx = 2;		rootCST.weightx = 1;
		rootPanel.add(rootBox, rootCST);
		
		//CREATE TOP PANEL
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		buttonPanel.add(new DButton(this, DefaultLanguage.PARENT));
		buttonPanel.add(new DButton(this, DefaultLanguage.NEW_DIRECTORY));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		GridBagConstraints topCST = new GridBagConstraints();
		topCST.gridx = 1;		topCST.gridy = 0;
		topCST.gridwidth = 1;	topCST.gridheight = 3;
		topCST.weightx = 0;		topCST.weighty = 0;
		topCST.fill = GridBagConstraints.BOTH;
		topPanel.add(getHorizontalSpace(), topCST);
		topCST.gridx = 2;
		topPanel.add(buttonPanel, topCST);
		topCST.gridx = 0;		topCST.weightx = 1;
		topPanel.add(rootPanel, topCST);
		
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
		fileList = new DList(this, false, DefaultLanguage.FILE);
		DScrollPane fileScroll = new DScrollPane(settings, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, fileList);
		
		//CREATE CENTER PANEL
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(getSpacedPanel(topPanel, 1, 0, true, true, true, true), BorderLayout.NORTH);
		centerPanel.add(getSpacedPanel(fileScroll, 1, 1, false, false, true, true), BorderLayout.CENTER);
		centerPanel.add(getSpacedPanel(namePanel, 1, 0, true, false, true, true), BorderLayout.SOUTH);
		
		//CREATE BOTTOM PANEL
		JPanel openPanel = new JPanel();
		openPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		openPanel.add(new DButton(this, DefaultLanguage.CANCEL));
		openPanel.add(new DButton(this, DefaultLanguage.OPEN));
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(openPanel, BorderLayout.EAST);
		
		//FINALIZE PANEL
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(getSpacedPanel(bottomPanel, 1, 0, true, true, true, true), BorderLayout.SOUTH);
		
	}//CONSTRUCTOR
	
	/**
	 * Opens the file chooser dialog for opening a file.
	 * 
	 * @param owner DFrame used as the file chooser's owner
	 * @param startDirectory Directory to start with when the file chooser opens
	 * @param extensions Extensions to allow opening
	 * @return File chosen by the user (null if no file chosen)
	 * @since 2.0
	 */
	public File openDialog(DFrame owner, final File startDirectory, final String[] extensions)
	{
		returnFile = null;
		owner.setAllowExit(false);
		dialog = new DDialog(owner, panel ,getTitle(DefaultLanguage.OPEN_TITLE), true, getSettings().getFontSize() * 30, getSettings().getFontSize() * 20);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		dialog = null;
		owner.setAllowExit(true);
		return returnFile;
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{

	}//METHOD
	
}//CLASS
