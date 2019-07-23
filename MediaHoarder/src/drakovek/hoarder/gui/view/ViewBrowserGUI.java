package drakovek.hoarder.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import javax.swing.SwingConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.Start;
import drakovek.hoarder.file.dmf.DmfHandler;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.ScreenDimensions;
import drakovek.hoarder.gui.settings.SettingsBarGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DCheckBoxMenuItem;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DMenu;
import drakovek.hoarder.gui.swing.components.DMenuItem;
import drakovek.hoarder.gui.swing.components.DRadioButtonMenuItem;
import drakovek.hoarder.gui.swing.components.DTextField;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.gui.swing.listeners.DResizeListener;
import drakovek.hoarder.media.PreviewButton;
import drakovek.hoarder.processing.BooleanInt;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.DWorker;

/**
 * Creates the browser GUI for viewing DMF Media.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ViewBrowserGUI extends FrameGUI implements DWorker
{
	/**
	 * Action for when pageText event occurs.
	 * 
	 * @since 2.0
	 */
	private static final String PAGE_ACTION = "page"; //$NON-NLS-1$
	
	/**
	 * Main settings bar for the class.
	 * 
	 * @since 2.0
	 */
	private SettingsBarGUI settingsBar;
	
	/**
	 * Main progress dialog for the class
	 * 
	 * @since 2.0
	 */
	private DProgressDialog progressDialog;
	
	/**
	 * Panel within which to hold image previews and titles of DMF media.
	 * 
	 * @since 2.0
	 */
	private JPanel previewPanel;
	
	/**
	 * Panels that each hold a preview for an individual DMF
	 * 
	 * @since 2.0
	 */
	private JPanel[] previewPanels;
	
	/**
	 * Array of buttons used to show previews of DMFs and open DMF media.
	 * 
	 * @since 2.0
	 */
	private PreviewButton[] previewButtons;
	
	/**
	 * Array of labels to show previews of DMFs
	 * 
	 * @since 2.0
	 */
	private DLabel[] previewLabels;
	
	/**
	 * Index values for all the previews in the preview panel
	 * 
	 * @since 2.0
	 */
	private int[] previewValues;
	
	/**
	 * File Menu for the GUI
	 * 
	 * @since 2.0
	 */
	private DMenu fileMenu;
	
	/**
	 * View Menu for the GUI
	 * 
	 * @since 2.0
	 */
	private DMenu viewMenu;
	
	/**
	 * Sort Menu for the GUI
	 * 
	 * @since 2.0
	 */
	private DMenu sortMenu;
	
	/**
	 * Button to show DMF media prior to the current page
	 * 
	 * @since 2.0
	 */
	private DButton previousButton;
	
	/**
	 * Button to show DMF media after the current page
	 * 
	 * @since 2.0
	 */
	private DButton nextButton;
	
	/**
	 * Text Field to set the current page/offset for the DMF media being shown
	 * 
	 * @since 2.0
	 */
	private DTextField pageText;
	
	/**
	 * Width of a preview section, used to determine how many previews can be fit in the preview panel.
	 * 
	 * @since 2.0
	 */
	private int sectionWidth;
	
	/**
	 * Height of a preview section, used to determine how many previews can be fit in the preview panel.
	 * 
	 * @since 2.0
	 */
	private int sectionHeight;
	
	/**
	 * Width of the preview panel based on the number of previews.
	 * 
	 * @since 2.0
	 */
	private int previewWidth;
	
	/**
	 * Height of the preview panel based on the number of previews.
	 * 
	 * @since 2.0
	 */
	private int previewHeight;
	
	/**
	 * Value showing the index value for the first preview in the preview panel.
	 * 
	 * @since 2.0
	 */
	private int offset;
	
	/**
	 * Initializes the ViewBrowserGUI class.
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHandler
	 * @since 2.0
	 */
	public ViewBrowserGUI(DSettings settings, DmfHandler dmfHandler)
	{
		super(settings, dmfHandler, DefaultLanguage.VIEWER_TITLE);
		progressDialog = new DProgressDialog(settings);
		previewWidth = 0;
		previewHeight = 0;
		offset = 0;
		pageText = new DTextField(this, PAGE_ACTION);
		pageText.setHorizontalAlignment(SwingConstants.CENTER);
		createPreviewPanels();
		
		JMenuBar menubar = new JMenuBar();
		
		//FILE MENU ITEMS
		fileMenu = new DMenu(this, DefaultLanguage.FILE);
		fileMenu.add(new DMenuItem(this, DefaultLanguage.RELOAD_DMFS));
		fileMenu.add(new DMenuItem(this, DefaultLanguage.RELOAD_WITHOUT_INDEXES));
		fileMenu.addSeparator();
		fileMenu.add(new DMenuItem(this, DefaultLanguage.RESTART_PROGRAM));
		fileMenu.add(new DMenuItem(this, DefaultLanguage.EXIT));
		menubar.add(fileMenu);
		
		//VIEW MENU ITEMS
		viewMenu = new DMenu(this, DefaultLanguage.VIEW);
		viewMenu.add(new DCheckBoxMenuItem(this, settings.getUseThumbnails(), DefaultLanguage.USE_THUMBNAILS));
		menubar.add(viewMenu);
		
		//SORT MENU ITEMS
		sortMenu = new DMenu(this, DefaultLanguage.SORT);
		ButtonGroup sortGroup = new ButtonGroup();
		DRadioButtonMenuItem alphaSort = new DRadioButtonMenuItem(this, settings.getSortType() == DmfHandler.SORT_ALPHA, DefaultLanguage.SORT_ALPHA);
		DRadioButtonMenuItem timeSort = new DRadioButtonMenuItem(this, settings.getSortType() == DmfHandler.SORT_TIME, DefaultLanguage.SORT_TIME);
		DRadioButtonMenuItem ratingSort = new DRadioButtonMenuItem(this, settings.getSortType() == DmfHandler.SORT_RATING, DefaultLanguage.SORT_RATING);
		sortGroup.add(alphaSort);
		sortGroup.add(timeSort);
		sortGroup.add(ratingSort);
		sortMenu.add(alphaSort);
		sortMenu.add(timeSort);
		sortMenu.add(ratingSort);
		sortMenu.addSeparator();
		sortMenu.add(new DCheckBoxMenuItem(this, settings.getGroupArtists(), DefaultLanguage.GROUP_ARTISTS));
		sortMenu.add(new DCheckBoxMenuItem(this, settings.getGroupSequences(), DefaultLanguage.GROUP_SEQUENCES));
		sortMenu.add(new DCheckBoxMenuItem(this, settings.getGroupSections(), DefaultLanguage.GROUP_SECTIONS));
		menubar.add(sortMenu);
		
		//CREATE BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3, settings.getSpaceSize(), 0));
		previousButton = new DButton(this, DefaultLanguage.PREVIOUS);
		nextButton = new DButton(this, DefaultLanguage.NEXT);
		bottomPanel.add(previousButton);
		bottomPanel.add(pageText);
		bottomPanel.add(nextButton);
		
		//CREATE PREVIEW PANEL
		previewPanel = new JPanel();
		previewPanel.setLayout(new GridLayout(1, 1));
		previewPanel.addComponentListener(new DResizeListener(this, null));
		
		//CREATE VIEW PANEL
		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new BorderLayout());
		
		Dimension sectionDimension = new Dimension(1, sectionHeight);
		JPanel spacedPanel = new JPanel();
		spacedPanel.setLayout(new BorderLayout());
		spacedPanel.add(Box.createRigidArea(sectionDimension), BorderLayout.WEST);
		spacedPanel.add(Box.createRigidArea(sectionDimension), BorderLayout.EAST);
		spacedPanel.add(previewPanel, BorderLayout.CENTER);
		
		viewPanel.add(getSpacedPanel(spacedPanel), BorderLayout.CENTER);
		viewPanel.add(getSpacedPanel(bottomPanel, 1, 1, false, false, true, true), BorderLayout.SOUTH);
		
		//FINALIZE FRAME
		settingsBar = new SettingsBarGUI(this);
		getFrame().setJMenuBar(menubar);
		getFrame().getContentPane().add(viewPanel, BorderLayout.CENTER);
		getFrame().getContentPane().add(settingsBar.getPanel(), BorderLayout.SOUTH);
		getFrame().packRestricted();
		getFrame().setMinimumSize(getFrame().getSize());
		getFrame().setLocationRelativeTo(null);
		getFrame().setVisible(true);
		
		settingsBar.setLabelLoaded(getDmfHandler().isLoaded());
		loadDirectory(true);
		
	}//CONSTRUCTOR

	/**
	 * Creates the preview panels for the frame.
	 * 
	 * @since 2.0
	 */
	private void createPreviewPanels()
	{
		ScreenDimensions screen = new ScreenDimensions();
		sectionWidth = getSettings().getFontSize() * 20;
		sectionHeight = getSettings().getFontSize() * 10;
		
		//CHECK IF SECTION DIMENSIONS COVER TOO MUCH OF THE SCREEN
		if(sectionWidth > (screen.getSmallScreenWidth() / 2))
		{
			sectionWidth = screen.getSmallScreenWidth() / 2;
			
		}//IF
		
		if(sectionHeight > (screen.getSmallScreenHeight() / 2))
		{
			sectionHeight = screen.getSmallScreenHeight() / 2;
			
		}//IF
		

		//CHECK IF SECTION DIMENSIONS ARE TOO SMALL TO HOLD PREVIEW IMAGE
		if(sectionHeight < (getSettings().getPreviewSize() + (getSettings().getSpaceSize() * 3)))
		{
			sectionHeight = getSettings().getPreviewSize() + (getSettings().getSpaceSize() * 3);
			
		}//IF
		
		if(sectionWidth < (getSettings().getPreviewSize() + (getSettings().getSpaceSize() * 3)))
		{
			sectionWidth = getSettings().getPreviewSize() + (getSettings().getSpaceSize() * 3);
			
		}//IF
		
		
		//CREATE PREVIEW PANELS
		int width = (int)Math.ceil((double)screen.getLargeScreenHeight() / (double)sectionWidth);
		int height = (int)Math.ceil((double)screen.getLargeScreenHeight() / (double)sectionHeight);
		
		int total = width * height;
		previewPanels = new JPanel[total];
		previewButtons = new PreviewButton[total];
		previewLabels = new DLabel[total];
		previewValues = new int[total];
		Dimension previewSpace = new Dimension(getSettings().getPreviewSize() + (getSettings().getSpaceSize() * 2), 1);
		
		for(int i = 0; i < total; i++)
		{
			previewLabels[i] = new DLabel(this, null, new String());
			previewLabels[i].setHorizontalAlignment(SwingConstants.LEFT);
			previewLabels[i].setVerticalAlignment(SwingConstants.TOP);
			previewButtons[i] = new PreviewButton(this, getSettings(), i);
			previewValues[i] = -1;
			
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			GridBagConstraints previewCST = new GridBagConstraints();
			previewCST.gridx = 0;		previewCST.gridy = 0;
			previewCST.gridwidth = 1;	previewCST.gridheight = 1;
			previewCST.weightx = 0;		previewCST.weighty = 0;
			previewCST.fill = GridBagConstraints.BOTH;
			panel.add(Box.createRigidArea(previewSpace), previewCST);
			previewCST.gridy = 2;
			panel.add(Box.createRigidArea(previewSpace), previewCST);
			previewCST.gridx = 1;		previewCST.gridy = 1;
			panel.add(getHorizontalSpace(), previewCST);
			previewCST.gridx = 0;		previewCST.weighty = 1;
			panel.add(previewButtons[i], previewCST);
			previewCST.gridx = 2;		previewCST.gridy = 0;
			previewCST.gridheight = 3;	previewCST.weightx = 1;
			panel.add(previewLabels[i], previewCST);
			
			previewPanels[i] = getSpacedPanel(panel);
			
			previewPanels[i].setBorder(BorderFactory.createLineBorder(pageText.getDisabledTextColor()));
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Runs when sectionPanel is resized, and sets a new grid panel for previews.
	 * 
	 * @since 1.0
	 */
	private void previewResized()
	{
		int width = (int)Math.floor((double)previewPanel.getWidth() / (double)sectionWidth);
		int height = (int)Math.floor((double)previewPanel.getHeight() / (double)sectionHeight);
		
		if(width < 1)
		{
			width = 1;
		
		}//IF (width < 1)
		
		if(height < 1)
		{
			height = 1;
		
		}//IF (height < 1)
		
		if(width != previewWidth || height != previewHeight)
		{
			previewWidth = width;
			previewHeight = height;
			
			int total = width * height;
			JPanel previewPNL = new JPanel();
			previewPNL.setLayout(new GridLayout(height, width, getSettings().getSpaceSize(), getSettings().getSpaceSize()));
			
			for(int i = 0; i < total && i < previewPanels.length; i++)
			{
				previewPNL.add(previewPanels[i]);
			
			}//FOR
			
			previewPanel.removeAll();
			previewPanel.add(previewPNL);
			previewPanel.revalidate();
			launchPreviewUpdate();
			
		}//IF (width != previewWidth && height != previewHeight)
		
	}//METHOD
	
	/**
	 * Starts the process for loading DMFs from a directory.
	 * 
	 * @param useIndexSettings Whether to use the user's settings for reading DMF indexes. If false, DmfHanlder will load DMFs without using index files.
	 * @since 2.0
	 */
	private void loadDirectory(final boolean useIndexSettings)
	{
		if(!getFrame().isProcessRunning() && (!useIndexSettings || !getDmfHandler().isLoaded()))
		{
			getFrame().setProcessRunning(true);
			progressDialog.setCancelled(false);
			progressDialog.startProgressDialog(getFrame(), DefaultLanguage.LOADING_DMFS_TITLE);
			
			if(useIndexSettings)
			{
				(new DSwingWorker(this, DefaultLanguage.LOADING_DMFS)).execute();
				
			}//IF
			else
			{
				(new DSwingWorker(this, DefaultLanguage.RELOAD_WITHOUT_INDEXES)).execute();
				
			}//ELSE
			
		}//IF
		
	}//METHOD
	
	/**
	 * Resets the preview values.
	 * 
	 * @since 2.0
	 */
	private void resetValues()
	{
		for(int i = 0; i < previewValues.length; i++)
		{
			previewValues[i] = -1;
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Starts the process of updating previews, if necessary.
	 * 
	 * @since 2.0
	 */
	private void launchPreviewUpdate()
	{
		if(!getFrame().isProcessRunning())
		{
			disableAll();
			int total = previewWidth * previewHeight;
			String text = ((offset / total) + 1) + Character.toString('/') + (int)Math.ceil((double)getDmfHandler().getSize() / (double)total);
			
			if(offset % total != 0)
			{
				text = text + getSettings().getLanguageText(DefaultLanguage.OFFSET) + (offset - ((int)Math.floor((double)offset / (double)total) * total));
			
			}//IF
			
			pageText.setText(text);
			
			boolean newPreviews = false;
			for(int i = 0; i < previewValues.length && i < total; i++)
			{
				if(previewValues[i] != (offset + i))
				{
					newPreviews = true;
					break;
					
				}//IF
				
			}//FOR
			
			if(newPreviews)
			{
				getFrame().setProcessRunning(true);
				progressDialog.setCancelled(false);
				progressDialog.startProgressDialog(getFrame(), DefaultLanguage.LOADING_PREVIEWS_TITLE);
				progressDialog.setProcessLabel(DefaultLanguage.LOADING_PREVIEWS);
				progressDialog.setDetailLabel(DefaultLanguage.RUNNING, true);
				(new DSwingWorker(this, DefaultLanguage.LOADING_PREVIEWS)).execute();
			
			}//IF
			else
			{
				enableAll();
				
			}//ELSE
			
		}//IF
		
	}//METHOD
	
	/**
	 * Updates the preview panel.
	 * 
	 * @since 2.0
	 */
	private void updatePreview()
	{
		int total = previewWidth * previewHeight;
		progressDialog.setProgressBar(true, false, 0, 0);
		//LOAD TEXT
		for(int i = 0; i < previewValues.length && i < total; i++)
		{
			progressDialog.setProgressBar(false, true, total, i);
			if(previewValues[i] != (offset + i))
			{
				if((offset + i) < getDmfHandler().getSize())
				{
					previewValues[i] = offset + i;
					previewLabels[i].setText("<html>" +  //$NON-NLS-1$
											getDmfHandler().getTitle(offset + i) + 
											"<br/><i>" + //$NON-NLS-1$
											StringMethods.arrayToString(getDmfHandler().getArtists(offset + i)) +
											"</i></html>"); //$NON-NLS-1$
					
					previewButtons[i].setFile(getDmfHandler().getMediaFile(offset + i), progressDialog.isCancelled() || !getSettings().getUseThumbnails());
					
					if(progressDialog.isCancelled())
					{
						previewValues[i] = -1;
						
					}//IF
					
				}//IF
				else
				{
					previewValues[i] = -1;
					previewLabels[i].setText(new String());
					previewButtons[i].setFile(null, false);
					
				}//ELSE
				
			}//IF
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Updates panel to the next page of previews.
	 * 
	 * @since 2.0
	 */
	private void nextPage()
	{
		int total = previewWidth * previewHeight;
		int newOffset;
		if(offset % total == 0)
		{
			newOffset = offset + total;
			
		}//IF
		else
		{
			newOffset = (int)Math.ceil((double)offset / (double)total) * total;
			
		}//ELSE
		
		if(newOffset < getDmfHandler().getSize())
		{
			offset = newOffset;
			launchPreviewUpdate();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Updates panel to the previous page of previews.
	 * 
	 * @since 2.0
	 */
	private void previousPage()
	{
		int total = previewWidth * previewHeight;
		int newOffset;
		if(offset % total == 0)
		{
			newOffset = offset - total;
			
		}//IF
		else
		{
			newOffset = (int)Math.floor((double)offset / (double)total) * total;
			
		}//ELSE
		
		if(newOffset > -1)
		{
			offset = newOffset;
			launchPreviewUpdate();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Deals with text being entered to move to a new page or search for a title with a given query.
	 * 
	 * @since 2.0
	 */
	private void textEntered()
	{
		String text = pageText.getText().toLowerCase();
		
		try
		{
			//GET PAGE IF NUMBER
			
			int page = Integer.parseInt(text) - 1;
			
			if(page < 0)
			{
				page = 0;
				
			}//IF
			
			int total = previewWidth * previewHeight;
			if(page > ((int)Math.ceil((double)getDmfHandler().getSize() / (double)total) - 1))
			{
				page = (int)Math.ceil((double)getDmfHandler().getSize() / (double)total) - 1;
				
			}//IF
			
			offset = page * total;
			
			
		}//TRY
		catch(NumberFormatException e)
		{
			//GET TITLE IF NOT NUMBER
			if(text.length() > 0)
			{
				int size = getDmfHandler().getSize();
				for(int i = 0; i < size; i++)
				{
					if(getDmfHandler().getTitle(i).toLowerCase().contains(text))
					{
						offset = i;
						break;
						
					}//IF
					
				}//FOR
				
			}//IF

		}//CATCH
		
		launchPreviewUpdate();
		
	}//METHOD
	
	@Override
	public void enableAll() 
	{
		if(offset > 0)
		{
			previousButton.setEnabled(true);
			
		}//IF
		
		if((offset + (previewWidth * previewHeight)) < getDmfHandler().getSize())
		{
			nextButton.setEnabled(true);
			
		}//IF
		
		settingsBar.enableAll();
		pageText.setEnabled(true);
		fileMenu.setEnabled(true);
		viewMenu.setEnabled(true);
		sortMenu.setEnabled(true);
		
		for(int i = 0; i < previewButtons.length; i++)
		{
			if(previewValues[i] != -1)
			{
				previewButtons[i].setEnabled(true);
				
			}//IF
			
		}//FOR
		
	}//METHOD

	@Override
	public void disableAll()
	{
		settingsBar.disableAll();
		previousButton.setEnabled(false);
		nextButton.setEnabled(false);
		pageText.setEnabled(false);
		fileMenu.setEnabled(false);
		viewMenu.setEnabled(false);
		sortMenu.setEnabled(false);
		
		for(int i = 0; i < previewButtons.length; i++)
		{
			previewButtons[i].setEnabled(false);
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Runs when one of the sorting radio buttons is pressed. Sets the appropriate sort type then starts sorting DMFs.
	 * 
	 * @param sortType Sort Type
	 * @param isSelected Whether the sort type was selected or deselected. If deselected, does nothing.
	 * @since 2.0
	 */
	private void sortRadioPressed(final int sortType, final boolean isSelected)
	{
		if(isSelected)
		{
			getSettings().setSortType(sortType);
			sort();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Starts the DMF sorting process.
	 * 
	 * @since 2.0
	 */
	private void sort()
	{
		getFrame().setProcessRunning(true);
		progressDialog.setCancelled(false);
		progressDialog.startProgressDialog(getFrame(), DefaultLanguage.SORTING_DMFS_TITLE);
		progressDialog.setProcessLabel(DefaultLanguage.SORTING_DMFS);
		progressDialog.setDetailLabel(DefaultLanguage.RUNNING, true);
		progressDialog.setProgressBar(true, false, 0, 0);
		(new DSwingWorker(this, DefaultLanguage.SORT)).execute();
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.NEXT:
				nextPage();
				break;
			case DefaultLanguage.PREVIOUS:
				previousPage();
				break;
			case PAGE_ACTION:
				textEntered();
				break;
			case DResizeListener.RESIZE:
				previewResized();
				break;
			case DefaultLanguage.USE_THUMBNAILS:
				getSettings().setUseThumbnails(BooleanInt.getBoolean(value));
				resetValues();
				launchPreviewUpdate();
				break;
			case DefaultLanguage.SORT_ALPHA:
				sortRadioPressed(DmfHandler.SORT_ALPHA, BooleanInt.getBoolean(value));
				break;
			case DefaultLanguage.SORT_TIME:
				sortRadioPressed(DmfHandler.SORT_TIME, BooleanInt.getBoolean(value));
				break;
			case DefaultLanguage.SORT_RATING:
				sortRadioPressed(DmfHandler.SORT_RATING, BooleanInt.getBoolean(value));
				break;
			case DefaultLanguage.GROUP_ARTISTS:
				getSettings().setGroupArtists(BooleanInt.getBoolean(value));
				sort();
				break;
			case DefaultLanguage.GROUP_SEQUENCES:
				getSettings().setGroupSequences(BooleanInt.getBoolean(value));
				sort();
				break;
			case DefaultLanguage.GROUP_SECTIONS:
				getSettings().setGroupSections(BooleanInt.getBoolean(value));
				if(!getSettings().getGroupSequences()) sort();
				break;
			case DefaultLanguage.RELOAD_DMFS:
				loadDirectory(true);
				break;
			case DefaultLanguage.RELOAD_WITHOUT_INDEXES:
				loadDirectory(false);
				break;
			case DefaultLanguage.RESTART_PROGRAM:
				Start.startGUI(getSettings(), getDmfHandler());
			case DefaultLanguage.EXIT:
				dispose();
				break;
			
		}//SWITCH
		
	}//METHOD

	@Override
	public void run(String id)
	{
		switch(id)
		{
			case DefaultLanguage.LOADING_DMFS:
				this.getDmfHandler().loadDMFs(getSettings().getDmfDirectories(), progressDialog, getSettings().getUseIndexes(), getSettings().getUseIndexes(), getSettings().getUpdateIndexes());
				break;
			case DefaultLanguage.RELOAD_WITHOUT_INDEXES:
				this.getDmfHandler().loadDMFs(getSettings().getDmfDirectories(), progressDialog, false, getSettings().getUseIndexes(), false);
				break;
			case DefaultLanguage.LOADING_PREVIEWS:
				updatePreview();
				break;
			case DefaultLanguage.SORT:
				this.getDmfHandler().sort(getSettings().getSortType(), getSettings().getGroupArtists(), getSettings().getGroupSequences(), getSettings().getGroupSections());
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
			case DefaultLanguage.RELOAD_WITHOUT_INDEXES:
			case DefaultLanguage.LOADING_DMFS:
				settingsBar.setLabelLoaded(getDmfHandler().isLoaded());
				sort();
				break;
			case DefaultLanguage.SORT:
				offset = 0;
				resetValues();
				launchPreviewUpdate();
				break;
				
		}//SWITCH
		
	}//METHOD
	
}//CLASS
