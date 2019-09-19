package drakovek.hoarder.gui.editing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.settings.SettingsBarGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.components.DTextField;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.media.MediaViewer;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * 
 * 
 * @author Drakovek
 * @version 2.0
 */
public class SequencingGUI extends FrameGUI implements DWorker
{
	/**
	 * Main progress dialog for the class
	 */
	private DProgressDialog progressDialog;
	
	/**
	 * Main settings bar for the sequencing GUI
	 */
	private SettingsBarGUI settingsBar;
	
	/**
	 * Panel for showing the currently selected media
	 */
	private MediaViewer mediaViewer;
	
	/**
	 * Text field for entering the names of sections and branches
	 */
	private DTextField nameText;
	
	/**
	 * Text field for entering an index number to move a given entry.
	 */
	private DTextField indexText;
	
	/**
	 * List that shows the sequence data
	 */
	private DList sequenceList;
	
	/**
	 * Button for adding name data to above entries
	 */
	private DButton aboveButton;
	
	/**
	 * Button for adding name data to below entries
	 */
	private DButton belowButton;
	
	/**
	 * Button for adding name data to a single entry
	 */
	private DButton singleButton;
	
	/**
	 * Button for adding name data to all entries
	 */
	private DButton allButton;
	
	/**
	 * Button for skipping entries
	 */
	private DButton skipButton;
	
	/**
	 * Button for saving the current sequence
	 */
	private DButton saveButton;
	
	/**
	 * Button used to search for DMFs
	 */
	private DButton searchButton;
	
	/**
	 * Button for removing entries from the sequence list
	 */
	private DButton removeButton;
	
	/**
	 * Button for clearing all entries from the sequence list
	 */
	private DButton clearButton;
	
	/**
	 * Button for moving selected entries upward in the sequence list
	 */
	private DButton upButton;
	
	/**
	 * Button for moving selected entries downward in the sequence list
	 */
	private DButton downButton;
	
	/**
	 * Initializes the SequencingGUI class by creating the main GUI.
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DMF Handler
	 */
	public SequencingGUI(DSettings settings, DmfHandler dmfHandler)
	{
		super(settings, dmfHandler, DefaultLanguage.SEQUENCE_MODE);
		progressDialog = new DProgressDialog(settings);
		
		//CREATE UPDATE PANEL
		JPanel updatePanel = new JPanel();
		updatePanel.setLayout(new GridLayout(1, 4, settings.getSpaceSize(), 0));
		aboveButton = new DButton(this, DefaultLanguage.ABOVE);
		belowButton = new DButton(this, DefaultLanguage.BELOW);
		singleButton = new DButton(this, DefaultLanguage.SINGLE);
		allButton = new DButton(this, DefaultLanguage.ALL);
		updatePanel.add(aboveButton);
		updatePanel.add(belowButton);
		updatePanel.add(singleButton);
		updatePanel.add(allButton);

		//CREATE SAVE PANEL
		JPanel savePanel = new JPanel();
		savePanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		skipButton = new DButton(this, DefaultLanguage.SKIP);
		saveButton = new DButton(this, DefaultLanguage.SAVE);
		savePanel.add(skipButton);
		savePanel.add(saveButton);
		
		//CREATE NAME PANEL
		nameText = new DTextField(this, DefaultLanguage.SECTION_NAME);
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridBagLayout());
		GridBagConstraints nameCST = new GridBagConstraints();
		nameCST.gridx = 0;		nameCST.gridy = 1;
		nameCST.gridwidth = 1;	nameCST.gridheight = 1;
		nameCST.weightx = 0;	nameCST.weighty = 0;
		nameCST.fill = GridBagConstraints.BOTH;
		namePanel.add(new DLabel(this, nameText, DefaultLanguage.SECTION_NAME), nameCST);
		nameCST.gridx = 1;
		namePanel.add(getHorizontalSpace(), nameCST);
		nameCST.gridx = 2;		nameCST.weightx = 1;
		namePanel.add(nameText, nameCST);
		nameCST.gridx = 0;		nameCST.gridy = 0;
		nameCST.gridwidth = 3;
		namePanel.add(getSpacedPanel(updatePanel, 1, 0, true, true, false, false), nameCST);
		nameCST.gridy = 2;
		namePanel.add(this.getSpacedPanel(savePanel, 1, 0, true, false, false, false), nameCST);
		
		//CREATE SEARCH PANEL
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new GridLayout(1, 3, settings.getSpaceSize(), 0));
		searchButton = new DButton(this, DefaultLanguage.SEARCH);
		removeButton = new DButton(this, DefaultLanguage.REMOVE);
		clearButton = new DButton(this, DefaultLanguage.CLEAR);
		searchPanel.add(searchButton);
		searchPanel.add(removeButton);
		searchPanel.add(clearButton);
		
		//CREATE MOVEMENT PANEL
		indexText = new DTextField(this, new String());
		JPanel movementPanel = new JPanel();
		movementPanel.setLayout(new GridLayout(1, 3, settings.getSpaceSize(), 0));
		upButton = new DButton(this, DefaultLanguage.UP);
		downButton = new DButton(this, DefaultLanguage.DOWN);
		movementPanel.add(upButton);
		movementPanel.add(indexText);
		movementPanel.add(downButton);
		
		//CREATE LIST PANEL
		sequenceList = new DList(this, true, new String());
		DScrollPane sequenceScroll = new DScrollPane(settings, sequenceList);
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		listPanel.add(getSpacedPanel(searchPanel, 1, 0, false, true, false, false), BorderLayout.NORTH);
		listPanel.add(this.getSpacedPanel(movementPanel, 1, 0, true, true, false, false), BorderLayout.SOUTH);
		listPanel.add(sequenceScroll, BorderLayout.CENTER);
		
		//CREATE FULL PANEL
		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new GridBagLayout());
		GridBagConstraints fullCST = new GridBagConstraints();
		fullCST.gridx = 0;		fullCST.gridy = 2;
		fullCST.gridwidth = 3;	fullCST.gridheight = 1;
		fullCST.weightx = 1;	fullCST.weighty = 0;
		fullCST.fill = GridBagConstraints.BOTH;
		fullPanel.add(namePanel, fullCST);
		fullCST.gridy = 1;
		fullPanel.add(new JSeparator(SwingConstants.HORIZONTAL), fullCST);
		fullCST.gridy = 0;		fullCST.weighty = 1;
		fullPanel.add(listPanel, fullCST);
		
		//CREATE MENU BAR
		mediaViewer = new MediaViewer(this, searchButton, false);
		JMenuBar menubar = new JMenuBar();
		menubar.add(mediaViewer.getScaleMenu());
		menubar.add(mediaViewer.getDetailMenu());
		menubar.add(mediaViewer.getViewMenu());
				
		//FINALIZE FRAME
		settingsBar = new SettingsBarGUI(this);
		getFrame().setJMenuBar(menubar);
		getFrame().getContentPane().add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getSpacedPanel(fullPanel), mediaViewer.getViewerPanel()), BorderLayout.CENTER);
		getFrame().getContentPane().add(settingsBar.getPanel(), BorderLayout.SOUTH);
		getFrame().packRestricted();
		getFrame().setMinimumSize(getFrame().getSize());
		getFrame().setLocationRelativeTo(null);
		getFrame().setVisible(true);
		
		settingsBar.setLabelLoaded(getDmfHandler().isLoaded());
		loadDirectories();
		
	}//CONSTRUCTOR
	
	/**
	 * Starts process of loading all DMFs from the default DMF directories.
	 */
	private void loadDirectories()
	{
		getFrame().setProcessRunning(true);
		progressDialog.setCancelled(false);
		progressDialog.startProgressDialog(getFrame(), DefaultLanguage.LOADING_DMFS_TITLE);
		(new DSwingWorker(this, DefaultLanguage.LOADING_DMFS)).execute();
		
	}//METHOD
	
	@Override
	public void enableAll()
	{
		aboveButton.setEnabled(true);
		belowButton.setEnabled(true);
		singleButton.setEnabled(true);
		allButton.setEnabled(true);
		skipButton.setEnabled(true);
		saveButton.setEnabled(true);
		searchButton.setEnabled(true);
		removeButton.setEnabled(true);
		clearButton.setEnabled(true);
		upButton.setEnabled(true);
		downButton.setEnabled(true);
		
		mediaViewer.getScaleMenu().setEnabled(true);
		mediaViewer.getDetailMenu().setEnabled(true);
		mediaViewer.getViewMenu().setEnabled(true);
		
	}//METHOD

	@Override
	public void disableAll()
	{
		aboveButton.setEnabled(false);
		belowButton.setEnabled(false);
		singleButton.setEnabled(false);
		allButton.setEnabled(false);
		skipButton.setEnabled(false);
		saveButton.setEnabled(false);
		searchButton.setEnabled(false);
		removeButton.setEnabled(false);
		clearButton.setEnabled(false);
		upButton.setEnabled(false);
		downButton.setEnabled(false);
		
		mediaViewer.getScaleMenu().setEnabled(false);
		mediaViewer.getDetailMenu().setEnabled(false);
		mediaViewer.getViewMenu().setEnabled(false);
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		
	}//METHOD

	@Override
	public void run(String id)
	{
		switch(id)
		{
			case DefaultLanguage.LOADING_DMFS:
				this.getDmfHandler().loadDMFs(getSettings().getDmfDirectories(), progressDialog, getSettings().getUseIndexes(), getSettings().getUseIndexes(), true);
				this.getDmfHandler().sort(DmfHandler.SORT_TIME, true, false, false, false);
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void done(String id)
	{
		progressDialog.setCancelled(false);
		progressDialog.closeProgressDialog();
		getFrame().setProcessRunning(false);
		
		switch(id)
		{
			case DefaultLanguage.LOADING_DMFS:
				settingsBar.setLabelLoaded(getDmfHandler().isLoaded());
				break;
				
		}//SWITCH
		
	}//METHOD

}//CLASS
