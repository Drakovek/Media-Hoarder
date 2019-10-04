package drakovek.hoarder.gui.editing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.Start;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.dmf.DmfLoader;
import drakovek.hoarder.file.dmf.DmfLoadingMethods;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.EditingValues;
import drakovek.hoarder.file.language.ModeValues;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.settings.SettingsBarGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DList;
import drakovek.hoarder.gui.swing.components.DMenu;
import drakovek.hoarder.gui.swing.components.DMenuItem;
import drakovek.hoarder.gui.swing.components.DScrollPane;
import drakovek.hoarder.gui.swing.components.DTextField;
import drakovek.hoarder.gui.swing.compound.DButtonDialog;
import drakovek.hoarder.gui.swing.compound.DListSelection;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.gui.view.FilterGUI;
import drakovek.hoarder.media.MediaViewer;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * Creates a GUI for adding sequence data to DMFs.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class SequencingGUI extends FrameGUI implements DmfLoadingMethods, DWorker
{	
	/**
	 * DMF loader for loading DMFs
	 */
	private DmfLoader loader;
	
	/**
	 * Main settings bar for the sequencing GUI
	 */
	private SettingsBarGUI settingsBar;
	
	/**
	 * GUI for selecting DMFs that were searched for.
	 */
	private DListSelection listSelection;
	
	/**
	 * GUI for searching 
	 */
	private FilterGUI filterGUI;
	
	/**
	 * Main progress bar for showing progress in system processes
	 */
	private DProgressDialog progressDialog;

	/**
	 * Panel for showing the currently selected media
	 */
	private MediaViewer mediaViewer;
	
	/**
	 * ArrayList containing all the 
	 */
	private ArrayList<Integer> unsequenced;
	
	/**
	 * ArrayList holding the current sequence in a sequence tree format
	 */
	private ArrayList<String> sequenceTree;
	
	/**
	 * Label for name text field
	 */
	private DLabel nameLabel;
	
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
	 * Menu that shows file options
	 */
	private DMenu fileMenu;
	
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
	 * Button for adding branches
	 */
	private DButton addBranchButton;
	
	/**
	 * Button for adding copy references for sequences that loop on itself
	 */
	private DButton addCopyButton;
	
	/**
	 * Button for adding an existing sequence
	 */
	private DButton addSequenceButton;
	
	/**
	 * Button for moving selected entries upward in the sequence list
	 */
	private DButton upButton;
	
	/**
	 * Button for moving selected entries downward in the sequence list
	 */
	private DButton downButton;
	
	/**
	 * Whether to add a DMF's existing sequence when adding a DMF to the sequence
	 */
	private boolean addSequence;
	
	/**
	 * Initializes the SequencingGUI class by creating the main GUI.
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DMF Handler
	 */
	public SequencingGUI(DSettings settings, DmfHandler dmfHandler)
	{
		super(settings, dmfHandler, ModeValues.SEQUENCE_MODE);
		loader = new DmfLoader(this, this);
		progressDialog = new DProgressDialog(getSettings());
		filterGUI = new FilterGUI(this, loader);
		listSelection = new DListSelection(getSettings());
		
		//CREATE UPDATE PANEL
		JPanel updatePanel = new JPanel();
		updatePanel.setLayout(new GridLayout(1, 4, settings.getSpaceSize(), 0));
		aboveButton = new DButton(this, EditingValues.ABOVE);
		belowButton = new DButton(this, EditingValues.BELOW);
		allButton = new DButton(this, EditingValues.ALL);
		singleButton = new DButton(this, EditingValues.SINGLE);
		updatePanel.add(aboveButton);
		updatePanel.add(belowButton);
		updatePanel.add(allButton);
		updatePanel.add(singleButton);
		

		//CREATE SAVE PANEL
		JPanel savePanel = new JPanel();
		savePanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		skipButton = new DButton(this, EditingValues.SKIP);
		saveButton = new DButton(this, CommonValues.SAVE);
		savePanel.add(skipButton);
		savePanel.add(saveButton);
		
		//CREATE NAME PANEL
		nameText = new DTextField(this, EditingValues.SINGLE);
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridBagLayout());
		GridBagConstraints nameCST = new GridBagConstraints();
		nameCST.gridx = 0;		nameCST.gridy = 1;
		nameCST.gridwidth = 1;	nameCST.gridheight = 1;
		nameCST.weightx = 0;	nameCST.weighty = 0;
		nameCST.fill = GridBagConstraints.BOTH;
		nameLabel = new DLabel(this, nameText, EditingValues.SECTION_NAME);
		namePanel.add(nameLabel, nameCST);
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
		searchButton = new DButton(this, EditingValues.SEARCH);
		removeButton = new DButton(this, CommonValues.REMOVE);
		clearButton = new DButton(this, EditingValues.CLEAR);
		searchPanel.add(searchButton);
		searchPanel.add(removeButton);
		searchPanel.add(clearButton);
		
		//CREATE ADD PANEL
		JPanel addPanel = new JPanel();
		addPanel.setLayout(new GridLayout(1, 3, settings.getSpaceSize(), 0));
		addBranchButton = new DButton(this, EditingValues.ADD_BRANCH);
		addCopyButton = new DButton(this, EditingValues.ADD_COPY);
		addSequenceButton = new DButton(this, EditingValues.ADD_SEQUENCE);
		addPanel.add(addBranchButton);
		addPanel.add(addCopyButton);
		addPanel.add(addSequenceButton);
		
		//CREATE MOVEMENT PANEL
		indexText = new DTextField(this, CommonValues.OK);
		indexText.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel movementPanel = new JPanel();
		movementPanel.setLayout(new GridLayout(1, 3, settings.getSpaceSize(), 0));
		upButton = new DButton(this, EditingValues.UP);
		downButton = new DButton(this, EditingValues.DOWN);
		movementPanel.add(upButton);
		movementPanel.add(indexText);
		movementPanel.add(downButton);
		
		//CREATE LIST PANEL
		sequenceList = new DList(this, false, ModeValues.SEQUENCE_MODE);
		DScrollPane sequenceScroll = new DScrollPane(settings, sequenceList);
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		listPanel.add(getSpacedPanel(getVerticalStack(searchPanel, addPanel), 1, 0, false, true, false, false), BorderLayout.NORTH);
		listPanel.add(getSpacedPanel(movementPanel, 1, 0, true, true, false, false), BorderLayout.SOUTH);
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
		
		fileMenu = new DMenu(this, CommonValues.FILE);
		fileMenu.add(new DMenuItem(this, CommonValues.RESTART_PROGRAM));
		fileMenu.add(new DMenuItem(this, CommonValues.EXIT));
		menubar.add(fileMenu);
		
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
		loader.loadDMFs(getSettings().getUseIndexes(), getSettings().getUseIndexes(), true);
		
	}//CONSTRUCTOR
	
	/**
	 * Finds all the DMFs loaded into memory that do not have sequence data.
	 */
	private void findMissingSequences()
	{
		unsequenced = new ArrayList<>();
		int size = getDmfHandler().getDirectSize();
		for(int i = 0; i < size; i++)
		{
			
			if(getDmfHandler().getLastIDsDirect(i).length == 0 || getDmfHandler().getNextIDsDirect(i).length == 0)
			{
				unsequenced.add(Integer.valueOf(i));
				
			}//IF
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Loads the next un-sequenced DMF to create a sequence.
	 */
	private void loadNewSequence()
	{
		if(unsequenced.size() > 0)
		{
			sequenceTree = new ArrayList<>();
			sequenceTree.add(Character.toString('>') + Integer.toString(unsequenced.get(0).intValue()));
			showTree();
			
		}//IF
		else
		{
			disableAll();
			String[] buttonIDs = {CommonValues.OK};
			String[] messageIDs = {EditingValues.SEQUENCING_FINISHED};
			DButtonDialog buttonDialog = new DButtonDialog(getSettings());
			buttonDialog.openButtonDialog(getFrame(), EditingValues.SEQUENCING_FINISHED, messageIDs, buttonIDs);
			Start.startGUI(getSettings(), getDmfHandler());;
			dispose();
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Displays the current sequence as given by the sequence tree.
	 */
	private void showTree()
	{
		String[] sequenceArray = new String[sequenceTree.size()];
		int padding = Integer.toString(sequenceTree.size()).length();
		for(int i = 0; i < sequenceArray.length; i++)
		{
			sequenceArray[i] = getStringFromTreeValue(sequenceTree.get(i), StringMethods.extendNumberString(i + 1, padding));
			
		}//FOR
		
		sequenceList.setListData(sequenceArray);
		
	}//METHOD
	
	/**
	 * Returns the String value to show for a given value from a sequence tree
	 * 
	 * @param treeValue Given value from a sequence tree
	 * @param index Index string to place before string
	 * @return String to Display
	 */
	private String getStringFromTreeValue(final String treeValue, final String index)
	{
		int tabs = treeValue.lastIndexOf('>');
		int start = tabs + 1;
		String lead = "<html>" + index + " | " + StringMethods.extendCharacter('>', tabs) + Character.toString(' '); //$NON-NLS-1$ //$NON-NLS-2$
		
		if(isTreeValueBranch(treeValue))
		{
			return lead + "<b>" + treeValue.substring(start) + "</b></html>";  //$NON-NLS-1$//$NON-NLS-2$
			
		}//IF
		
		if(isTreeValueReference(treeValue))
		{
			return lead + "<i>*" + StringMethods.addHtmlEscapes(getDmfHandler().getTitleDirect(getIndexFromTreeValue(treeValue))) + "*</i></html>"; //$NON-NLS-1$ //$NON-NLS-2$
			
		}//IF
		
		return lead + StringMethods.addHtmlEscapes(getDmfHandler().getTitleDirect(getIndexFromTreeValue(treeValue))) + "</html>"; //$NON-NLS-1$
		
	}//METHOD
	
	/**
	 * Returns the DMF index referenced by a tree value
	 * 
	 * @param treeValue Tree value to search for index within
	 * @return DMF index of the tree value
	 */
	public static int getIndexFromTreeValue(final String treeValue)
	{
		if(isTreeValueBranch(treeValue))
		{
			return -1;
			
		}//IF
		
		if(isTreeValueReference(treeValue))
		{
			return Integer.parseInt(treeValue.substring(treeValue.lastIndexOf('>') + 1, treeValue.length() - 1));
			
		}//IF
		
		return Integer.parseInt(treeValue.substring(treeValue.lastIndexOf('>') + 1));
		
	}//METHOD
	
	/**
	 * Returns the name of a branch from a given tree value.
	 * 
	 * @param treeValue Given tree value
	 * @return Name of the branch
	 */
	public static String getBranchNameFromTreeValue(final String treeValue)
	{
		return StringMethods.replaceHtmlEscapeCharacters(treeValue.substring(treeValue.indexOf('(') + 1, treeValue.length() - 1));
		
	}//METHOD
	
	/**
	 * Creates an HTML string to represent a DMF.
	 * 
	 * @param title Title of the DMF
	 * @param artists Artists for the DMF
	 * @return HTML String
	 */
	public String getDmfHtmlString(final String title, final String[] artists)
	{
		return "<html>" + StringMethods.addHtmlEscapes(title) + " - <i>" + StringMethods.addHtmlEscapes(StringMethods.arrayToString(artists, true, getSettings().getLanguageText(CommonValues.NON_APPLICABLE))) + "</i></html>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	
	}//METHOD
	
	/**
	 * Adds a sequence tree section to the main sequence tree at a given index.
	 * 
	 * @param section Sequence tree section
	 * @param index Index of which to insert the sequence tree section
	 * @param addToStart Whether to add out of bounds values to the start of the sequence, if false, values are added to the end
	 */
	private void addSectionToTree(final ArrayList<String> section, final int index, final boolean addToStart)
	{
		int insertIndex = index + 1;
		int layer = 0;
		if(index > -1 && index < sequenceTree.size())
		{
			layer = sequenceTree.get(index).lastIndexOf('>');
			if(isTreeValueBranch(sequenceTree.get(index)))
			{
				layer++;
				
			}//IF
			
		}//IF
		else if(addToStart)
		{
			insertIndex = 0;
			
		}//ELSE IF
		else
		{
			insertIndex = sequenceTree.size();
			
		}//ELSE
		
		for(int i = (section.size() - 1); i > -1; i--)
		{
			sequenceTree.add(insertIndex, StringMethods.extendCharacter('>', layer) + section.get(i));
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Returns whether a given sequence tree value is a branch.
	 * 
	 * @param treeValue Given sequence tree value
	 * @return Whether tree value is a branch value
	 */
	public static boolean isTreeValueBranch(final String treeValue)
	{
		return treeValue.charAt(treeValue.lastIndexOf('>') + 1) == '(';
		
	}//METHOD
	
	/**
	 * Returns whether a given sequence tree value is a reference.
	 * 
	 * @param treeValue Given sequence tree value
	 * @return Whether tree value is a reference value
	 */
	public static boolean isTreeValueReference(final String treeValue)
	{
		return treeValue.charAt(treeValue.length() - 1) == '*';
		
	}//METHOD
	
	/**
	 * Returns whether a given sequence tree value is a DMF.
	 * 
	 * @param treeValue Given sequence tree value
	 * @return Whether tree value is a DMF value
	 */
	public static boolean isTreeValueDMF(final String treeValue)
	{
		return !isTreeValueBranch(treeValue) && !isTreeValueReference(treeValue);
		
	}//METHOD
	
	/**
	 * Adds a branch to the currently selected tree value
	 */
	private void addBranch()
	{
		int selected = sequenceList.getSelectedIndex();
		if(selected != -1 && !isTreeValueReference(sequenceTree.get(selected)))
		{
			int layer = 0;
			int baseLayer = sequenceTree.get(selected).lastIndexOf('>');
			if(isTreeValueBranch(sequenceTree.get(selected)))
			{
				for(selected--; !isTreeValueDMF(sequenceTree.get(selected)) || sequenceTree.get(selected).lastIndexOf('>') > baseLayer; selected--);

			}//IF
			
			ArrayList<String> section = new ArrayList<>();
			int branchNums = 0;
			int insertIndex;
			for(insertIndex = selected + 1; insertIndex < sequenceTree.size(); insertIndex++)
			{
				layer = sequenceTree.get(insertIndex).lastIndexOf('>');
				if(layer == baseLayer)
				{
					if(isTreeValueBranch(sequenceTree.get(insertIndex)))
					{
						branchNums++;
						
					}//IF
					else
					{
						while(insertIndex < sequenceTree.size())
						{
							layer = sequenceTree.get(insertIndex).lastIndexOf('>');
							if(layer >= baseLayer)
							{
								section.add(Character.toString('>') + sequenceTree.get(insertIndex));
								sequenceTree.remove(insertIndex);
								
							}//IF
							else
							{
								break;
								
							}//ELSE
							
						}//WHILE
						
						break;
						
					}//ELSE
					
				}//IF
				else if(layer < baseLayer)
				{
					break;
					
				}//ELSE IF
				
			}//FOR
			
			if(branchNums == 0)
			{
				sequenceTree.add(insertIndex, StringMethods.extendCharacter('>', baseLayer + 1) + "(" + Integer.toString(2) + ")");  //$NON-NLS-1$//$NON-NLS-2$
				sequenceTree.addAll(insertIndex, section);
				sequenceTree.add(insertIndex, StringMethods.extendCharacter('>', baseLayer + 1) + "(" + Integer.toString(1) + ")");  //$NON-NLS-1$//$NON-NLS-2$
				
			}//IF
			else
			{
				sequenceTree.addAll(insertIndex, section);
				sequenceTree.add(insertIndex, StringMethods.extendCharacter('>', baseLayer + 1) + "(" + Integer.toString(branchNums + 1) + ")");  //$NON-NLS-1$//$NON-NLS-2$
				
			}//ELSE
			
			selected = sequenceList.getSelectedIndex();
			showTree();
			sequenceList.setSelectedIndex(selected);
			
		}//IF
			
	}//METHOD
	
	/**
	 * Adds a reference copy of a DMF for sequences that loop on themselves.
	 */
	private void addCopy()
	{
		int selected = sequenceList.getSelectedIndex();
		if(selected != -1)
		{
			int layer = sequenceTree.get(selected).lastIndexOf('>');
			if(isTreeValueBranch(sequenceTree.get(selected)))
			{
				layer++;
				
			}//IF
			
			for(selected++; selected < sequenceTree.size() && sequenceTree.get(selected).lastIndexOf('>') >= layer; selected++);
			if(!isTreeValueReference(sequenceTree.get(selected - 1)))
			{
				ArrayList<Integer> indexes = new ArrayList<>();
				for(int i = 0; i < sequenceTree.size(); i++)
				{
					if(isTreeValueDMF(sequenceTree.get(i)))
					{
						indexes.add(Integer.valueOf(getIndexFromTreeValue(sequenceTree.get(i))));
						
					}//IF
					
				}//FOR
				
				String[] options = new String[indexes.size()];
				for(int i = 0; i < indexes.size(); i++)
				{
					options[i] = getDmfHtmlString(getDmfHandler().getTitleDirect(indexes.get(i).intValue()), getDmfHandler().getArtistsDirect(indexes.get(i).intValue()));
					
				}//FOR
				
				int[] reference = listSelection.openSingleSeletionDialog(getFrame(), EditingValues.ADD_REFERENCE_COPY, options);
				if(reference.length > 0)
				{
					sequenceTree.add(selected, StringMethods.extendCharacter('>', layer + 1) + Integer.toString(indexes.get(reference[0]).intValue()) + Character.toString('*'));
					selected = sequenceList.getSelectedIndex();
					showTree();
					sequenceList.setSelectedIndex(selected);
					
				}//IF
				
			}//IF
			
		}//IF
		
	}//METHOD
	
	/**
	 * Removes the value at a given index from the sequence tree.
	 * 
	 * @param index Given index to remove
	 * @param userRemoved Whether the user is removing the index. If false, removal is automated
	 */
	private void removeFromTree(final int index, final boolean userRemoved)
	{
		if(index > -1 && index < sequenceTree.size())
		{
			if(isTreeValueDMF(sequenceTree.get(index)) && (index + 1) < sequenceTree.size() && isTreeValueBranch(sequenceTree.get(index + 1)))
			{
				int layer = sequenceTree.get(index).lastIndexOf('>');
				sequenceTree.remove(index);
				while(index < sequenceTree.size() && sequenceTree.get(index).lastIndexOf('>') >= layer)
				{
					sequenceTree.remove(index);
					
				}//WHILE
				
			}//IF
			else if(isTreeValueBranch(sequenceTree.get(index)))
			{
				int layer = sequenceTree.get(index).lastIndexOf('>');
				sequenceTree.remove(index);
				while(index < sequenceTree.size() && sequenceTree.get(index).lastIndexOf('>') > layer)
				{
					sequenceTree.remove(index);
					
				}//WHILE
				
			}//ELSE IF
			else
			{
				sequenceTree.remove(index);
				
			}//ELSE
			
			if(userRemoved)
			{
				int select = index;
				boolean addMain = true;
				for(int i = 0; i < sequenceTree.size(); i++)
				{
					if(unsequenced.get(0).intValue() == getIndexFromTreeValue(sequenceTree.get(i)))
					{
						addMain = false;
						break;
						
					}//IF
					
				}//FOR
				
				if(addMain)
				{
					select++;
					sequenceTree.add(0, Character.toString('>') + Integer.toString(unsequenced.get(0).intValue()));
				
				}//IF
				
				showTree();
				if(select < sequenceTree.size())
				{
					sequenceList.setSelectedIndex(select);
					
				}//IF
				else
				{
					sequenceList.setSelectedIndex(sequenceTree.size() - 1);
					
				}//ELSE
				
			}//IF
			
		}//IF
		
	}//METHOD
	
	/**
	 * Deals with an item in the sequence list being selected.
	 */
	private void listSelected()
	{
		int selected = sequenceList.getSelectedIndex();
		
		if(selected != -1)
		{
			indexText.setText(Integer.toString(selected + 1));
			if(isTreeValueBranch(sequenceTree.get(selected)))
			{
				nameText.setText(getBranchNameFromTreeValue(sequenceTree.get(selected)));
				nameLabel.setTextID(EditingValues.BRANCH_NAME, true);
				
			}//IF
			else
			{
				nameLabel.setTextID(EditingValues.SECTION_NAME, true);
				
			}//ELSE

			disableAll();
			enableAll();
			
		}//IF
		else
		{
			nameLabel.setTextID(EditingValues.SECTION_NAME, true);
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Sets the name of a single value in a sequence tree.
	 * 
	 * @param index Index of value of which to set the name
	 * @param name Name to set tree value
	 */
	private void setNameSingle(final int index, final String name)
	{
		if(index > -1 && name != null && name.length() > 0)
		{
			if(isTreeValueBranch(sequenceTree.get(index)))
			{
				int layer = sequenceTree.get(index).lastIndexOf('>') + 1;
				sequenceTree.set(index, StringMethods.extendCharacter('>', layer) + Character.toString('(') + StringMethods.addHtmlEscapes(name) + Character.toString(')'));
			
			}//IF
			
		}//IF
		
	}//METHOD
	
	/**
	 * Deals with an index being entered by moving the selected value to the given index.
	 */
	private void indexEntered()
	{
		try
		{
			int index = Integer.parseInt(indexText.getText()) - 1;
			if(index < 0)
			{
				index = 0;
			}//IF
			
			if(index >= sequenceTree.size())
			{
				index = sequenceTree.size() - 1;
				
			}//IF
			
			int selected = sequenceList.getSelectedIndex();
			
			if(selected != -1)
			{
				moveValue(selected, index);
				
			}//IF
			
		}//TRY
		catch(NumberFormatException e){}
		
	}//METHOD
	
	/**
	 * Moves the value from the start index to a given index.
	 * 
	 * @param start Index of the value to move
	 * @param index Index to move value to
	 */
	private void moveValue(final int start, final int index)
	{
		int baseLayer = sequenceTree.get(start).lastIndexOf('>');
		int end = start;
		ArrayList<String> section = new ArrayList<>();
		if(isTreeValueBranch(sequenceTree.get(start)))
		{
			section.add(sequenceTree.get(start).substring(baseLayer));
			for(int i = start + 1; i < sequenceTree.size() && sequenceTree.get(i).lastIndexOf('>') > baseLayer; i++)
			{
				section.add(sequenceTree.get(i).substring(baseLayer));
				end = i;
				
			}//FOR
			
		}//IF
		else if(isTreeValueBranch(sequenceTree.get(start) + 1) && sequenceTree.get(start + 1).lastIndexOf('>') > baseLayer)
		{
			for(int i = start; i < sequenceTree.size() && sequenceTree.get(i).lastIndexOf('>') >= baseLayer; i++)
			{
				section.add(sequenceTree.get(i).substring(baseLayer));
				end = i;
				
			}//FOR
			
		}//ELSE IF
		else
		{
			section.add(sequenceTree.get(start).substring(baseLayer));
			
		}//ELSE
		
		if(index < start || index > end)
		{
			int deleteNum = start;
			int insertNum = index;
			if(index < start)
			{
				deleteNum = start + section.size();
				insertNum--;
				
			}//IF
			
			addSectionToTree(section, insertNum, true);
			
			for(int i = 0; i < section.size(); i++)
			{
				sequenceTree.remove(deleteNum);
				
			}//FOR
			
			showTree();
			
		}//IF
		
	}//METHOD
	
	@Override
	public void dispose()
	{
		filterGUI.dispose();
		filterGUI = null;
		getFrame().dispose();
		
	}//METHOD
	
	@Override
	public void enableAll()
	{
		int selected = sequenceList.getSelectedIndex();
		if(selected == -1 || isTreeValueDMF(sequenceTree.get(selected)))
		{
			aboveButton.setEnabled(true);
			belowButton.setEnabled(true);
			allButton.setEnabled(true);
			
		}//IF
		
		if(selected != -1)
		{
			if(!isTreeValueReference(sequenceTree.get(selected)))
			{
				upButton.setEnabled(true);
				downButton.setEnabled(true);
				indexText.setEnabled(true);
				
			}//IF
			
			if(isTreeValueReference(sequenceTree.get(selected)) || unsequenced.get(0).intValue() != getIndexFromTreeValue(sequenceTree.get(selected)))
			{
				removeButton.setEnabled(true);
				
			}//IF
			
			int layer = sequenceTree.get(selected).lastIndexOf('>');
			if(isTreeValueBranch(sequenceTree.get(selected)))
			{
				layer++;
				
			}//IF
			for(selected++; selected < sequenceTree.size() && sequenceTree.get(selected).lastIndexOf('>') == layer; selected++);
			
			if(!isTreeValueReference(sequenceTree.get(selected - 1)) && (selected >= sequenceTree.size() || layer > sequenceTree.get(selected).lastIndexOf('>')))
			{
				addCopyButton.setEnabled(true);
				
			}//IF
			
		}//IF
		
		singleButton.setEnabled(true);
		skipButton.setEnabled(true);
		saveButton.setEnabled(true);
		searchButton.setEnabled(true);
		clearButton.setEnabled(true);
		addBranchButton.setEnabled(true);
		addSequenceButton.setEnabled(true);
		nameText.setEnabled(true);
		
		fileMenu.setEnabled(true);
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
		addBranchButton.setEnabled(false);
		addCopyButton.setEnabled(false);
		addSequenceButton.setEnabled(false);
		upButton.setEnabled(false);
		downButton.setEnabled(false);
		indexText.setEnabled(false);
		nameText.setEnabled(false);
		
		fileMenu.setEnabled(false);
		mediaViewer.getScaleMenu().setEnabled(false);
		mediaViewer.getDetailMenu().setEnabled(false);
		mediaViewer.getViewMenu().setEnabled(false);
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case EditingValues.ADD_BRANCH:
				addBranch();
				break;
			case EditingValues.ADD_COPY:
				addCopy();
				break;
			case CommonValues.REMOVE:
				removeFromTree(sequenceList.getSelectedIndex(), true);
				break;
			case EditingValues.SEARCH:
				addSequence = false;
				filterGUI.showFilterGUI();
				break;
			case CommonValues.OK:
				indexEntered();
				break;
			case EditingValues.ADD_SEQUENCE:
				addSequence = true;
				filterGUI.showFilterGUI();
				break;
			case EditingValues.CLEAR:
				loadNewSequence();
				break;
			case EditingValues.SKIP:
				unsequenced.remove(0);
				loadNewSequence();
				break;
			case EditingValues.SINGLE:
				int selected = sequenceList.getSelectedIndex();
				setNameSingle(selected, nameText.getText());
				showTree();
				sequenceList.setSelectedIndex(selected);
				break;
			case ModeValues.SEQUENCE_MODE:
				listSelected();
				break;
			case CommonValues.RESTART_PROGRAM:
				Start.startGUI(getSettings(), getDmfHandler());
				dispose();
				break;
			case CommonValues.EXIT:
				dispose();
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void loadingDMFsDone()
	{
		loader.sortDMFs(DmfHandler.SORT_TIME, true, false, false, false);
	
	}//METHOD

	@Override
	public void sortingDMFsDone()
	{
		settingsBar.setLabelLoaded(getDmfHandler().isLoaded());
		getFrame().setProcessRunning(true);
		progressDialog.setCancelled(false);
		progressDialog.startProgressDialog(getFrame(), EditingValues.FINDING_UNSEQUENCED_TITLE);
		progressDialog.setProcessLabel(EditingValues.FINDING_UNSEQUENCED_MESSAGE);
		progressDialog.setDetailLabel(CommonValues.RUNNING, true);
		progressDialog.setProgressBar(true, false, 0, 0);
		(new DSwingWorker(this, EditingValues.FINDING_UNSEQUENCED_TITLE)).execute();
		
	}//METHOD

	@Override
	public void filteringDMFsDone()
	{	
		int index = sequenceList.getSelectedIndex();
		String[] filterList = new String[getDmfHandler().getFilteredSize()];
		for(int i = 0; i < filterList.length; i++)
		{
			filterList[i] = getDmfHtmlString(getDmfHandler().getTitleFiltered(i), getDmfHandler().getArtistsFiltered(i));
		
		}//FOR
		
		ArrayList<String> section = new ArrayList<>();
		if(addSequence)
		{
			int[] selected = listSelection.openSingleSeletionDialog(getFrame(), EditingValues.ADD_SEQUENCE, filterList);
			if(selected.length > 0)
			{
				section = getDmfHandler().getSequenceTree(getDmfHandler().getDirectIndex(selected[0]));
				
			}//IF
			
		}//IF
		else
		{
			int[] selected = listSelection.openMultipleSeletionDialog(getFrame(), EditingValues.ADD_DMFS, filterList);
			for(int i = 0; i < selected.length; i++)
			{
				section.add(Character.toString('>') + Integer.toString(getDmfHandler().getDirectIndex(selected[i])));
				
			}//FOR
			
		}//ELSE
		
		//REMOVE DUPLICATES
		for(int i = 0; i < section.size(); i++)
		{
			int dmfValue = getIndexFromTreeValue(section.get(i));
			if(dmfValue != -1)
			{
				for(int k = 0; k < sequenceTree.size(); k++)
				{
					if(getIndexFromTreeValue(sequenceTree.get(k)) == dmfValue)
					{
						removeFromTree(k, false);
						k--;
						
						if(k < index)
						{
							index--;
							
						}//IF
						
					}//IF
				
				}//FOR
				
			}//IF
			
		}//FOR
		
		addSectionToTree(section, index, true);
		showTree();
		sequenceList.setSelectedIndex(index);
		
	}//METHOD

	@Override
	public void run(String id)
	{
		switch(id)
		{
			case EditingValues.FINDING_UNSEQUENCED_TITLE:
				findMissingSequences();
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
			case EditingValues.FINDING_UNSEQUENCED_TITLE:
				loadNewSequence();
				break;
				
		}//SWITCH
		
	}//METHOD

}//CLASS
