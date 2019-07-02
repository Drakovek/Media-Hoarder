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
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFrame;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.components.DTextField;

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
	 * Initializes the DFileChooser class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public DFileChooser(DSettings settings)
	{
		super(settings);
		
		//CREATE ROOT LIST PANEL
		DList rootList = new DList(this, false, DefaultLanguage.ROOTS);
		DScrollPane rootScroll = new DScrollPane(settings, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, rootList);
		JPanel rootListPanel = getSpacedPanel(rootScroll, 1, 1, true, true, false, false);
		
		//CREATE DIRECTORY LIST PANEL
		DList dirList = new DList(this, false, DefaultLanguage.DIRECTORIES);
		DScrollPane dirScroll = new DScrollPane(settings, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, dirList);
		JPanel dirListPanel = getSpacedPanel(dirScroll, 1, 1, true, true, false, false);
		
		//CREATE DIRECTORY PANEL
		JPanel backPanel = new JPanel();
		backPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		backPanel.add(new DButton(this, DefaultLanguage.BACK));
		backPanel.add(new DButton(this, DefaultLanguage.PARENT));
		
		JPanel directoryPanel = new JPanel();
		directoryPanel.setLayout(new BorderLayout());
		directoryPanel.add(new DLabel(this, dirList, DefaultLanguage.DIRECTORIES), BorderLayout.WEST);
		directoryPanel.add(backPanel, BorderLayout.EAST);
		
		//CREATE NAME PANEL
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout());
		DTextField nameText = new DTextField(this, DefaultLanguage.NAME);
		namePanel.add(new DLabel(this, nameText, DefaultLanguage.NAME) ,BorderLayout.EAST);
		
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
		centerPanel.add(dirListPanel, centerCST);
		centerCST.gridx = 0;		centerCST.weightx = 0;
		centerPanel.add(rootListPanel, centerCST);
		
		//CREATE BOTTOM PANEL
		JPanel okPanel = new JPanel();
		okPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		okPanel.add(new DButton(this, DefaultLanguage.CANCEL));
		okPanel.add(new DButton(this, DefaultLanguage.OK));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints bottomCST = new GridBagConstraints();
		bottomCST.gridx = 2;		bottomCST.gridy = 0;
		bottomCST.gridwidth = 1;	bottomCST.gridheight = 3;
		bottomCST.weightx = 0;		bottomCST.weighty = 0;
		bottomPanel.add(okPanel, bottomCST);
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
	 * @since 2.0
	 */
	public void createOpenChooser(DFrame owner, final File startDirectory)
	{
		
		dialog = new DDialog(owner, panel ,getTitle(DefaultLanguage.OPEN_TITLE), 0, 0);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.CANCEL:
			{
				dialog.dispose();
				break;
				
			}//CASE
			
		}//SWITCH
		
	}//METHOD
	
}//CLASS
