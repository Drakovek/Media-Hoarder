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
import drakovek.hoarder.file.dmf.DmfLoader;
import drakovek.hoarder.file.dmf.DmfLoadingMethods;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.DmfLanguageValues;
import drakovek.hoarder.file.language.ViewerValues;
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
 */
public class ViewBrowserGUI extends FrameGUI implements DWorker, DmfLoadingMethods
{
	/**
	 * Action for when pageText event occurs.
	 */
	private static final String PAGE_ACTION = "page"; //$NON-NLS-1$
	
	/**
	 * DMF loader for loading, sorting, and filtering DMFs
	 */
	private DmfLoader loader;
	
	/**
	 * Main settings bar for the class.
	 */
	private SettingsBarGUI settingsBar;
	
	/**
	 * Main progress dialog for the class
	 */
	private DProgressDialog progressDialog;
	
	/**
	 * GUI for filtering DMFs
	 */
	private FilterGUI filterGUI;
	
	/**
	 * Panel within which to hold image previews and titles of DMF media.
	 */
	private JPanel previewPanel;
	
	/**
	 * Panels that each hold a preview for an individual DMF
	 */
	private JPanel[] previewPanels;
	
	/**
	 * Array of buttons used to show previews of DMFs and open DMF media.
	 */
	private PreviewButton[] previewButtons;
	
	/**
	 * Array of labels to show previews of DMFs
	 */
	private DLabel[] previewLabels;
	
	/**
	 * Index values for all the previews in the preview panel
	 */
	private int[] previewValues;
	
	/**
	 * File Menu for the GUI
	 */
	private DMenu fileMenu;
	
	/**
	 * View Menu for the GUI
	 */
	private DMenu viewMenu;
	
	/**
	 * Sort Menu for the GUI
	 */
	private DMenu sortMenu;
	
	/**
	 * Filter Menu for the GUI
	 */
	private DMenu filterMenu;
	
	/**
	 * Button to show DMF media prior to the current page
	 */
	private DButton previousButton;
	
	/**
	 * Button to show DMF media after the current page
	 */
	private DButton nextButton;
	
	/**
	 * Text Field to set the current page/offset for the DMF media being shown
	 */
	private DTextField pageText;
	
	/**
	 * Width of a preview section, used to determine how many previews can be fit in the preview panel.
	 */
	private int sectionWidth;
	
	/**
	 * Height of a preview section, used to determine how many previews can be fit in the preview panel.
	 */
	private int sectionHeight;
	
	/**
	 * Width of the preview panel based on the number of previews.
	 */
	private int previewWidth;
	
	/**
	 * Height of the preview panel based on the number of previews.
	 */
	private int previewHeight;
	
	/**
	 * Value showing the index value for the first preview in the preview panel.
	 */
	private int offset;
	
	/**
	 * Initializes the ViewBrowserGUI class.
	 * 
	 * @param settings Program Settings
	 * @param dmfHandler Program's DmfHandler
	 */
	public ViewBrowserGUI(DSettings settings, DmfHandler dmfHandler)
	{
		super(settings, dmfHandler, ViewerValues.VIEWER_TITLE);
		loader = new DmfLoader(this, this);
		filterGUI = new FilterGUI(this, loader);
		progressDialog = new DProgressDialog(settings);
		previewWidth = 0;
		previewHeight = 0;
		offset = 0;
		pageText = new DTextField(this, PAGE_ACTION);
		pageText.setHorizontalAlignment(SwingConstants.CENTER);
		createPreviewPanels();
		
		JMenuBar menubar = new JMenuBar();
		
		//FILE MENU ITEMS
		fileMenu = new DMenu(this, CommonValues.FILE);
		fileMenu.add(new DMenuItem(this, ViewerValues.RELOAD_DMFS));
		fileMenu.add(new DMenuItem(this, ViewerValues.RELOAD_WITHOUT_INDEXES));
		fileMenu.addSeparator();
		fileMenu.add(new DMenuItem(this, CommonValues.RESTART_PROGRAM));
		fileMenu.add(new DMenuItem(this, CommonValues.EXIT));
		menubar.add(fileMenu);
		
		//VIEW MENU ITEMS
		viewMenu = new DMenu(this, CommonValues.VIEW);
		viewMenu.add(new DCheckBoxMenuItem(this, settings.getUseThumbnails(), ViewerValues.USE_THUMBNAILS));
		viewMenu.add(new DCheckBoxMenuItem(this, settings.getShowArtists(), ViewerValues.SHOW_ARTISTS));
		viewMenu.add(new DCheckBoxMenuItem(this, settings.getShowViews(), ViewerValues.SHOW_VIEWS));
		viewMenu.add(new DCheckBoxMenuItem(this, settings.getShowRatings(), ViewerValues.SHOW_RATINGS));
		menubar.add(viewMenu);
		
		//SORT MENU ITEMS
		sortMenu = new DMenu(this, ViewerValues.SORT);
		ButtonGroup sortGroup = new ButtonGroup();
		DRadioButtonMenuItem alphaSort = new DRadioButtonMenuItem(this, settings.getSortType() == DmfHandler.SORT_ALPHA, ViewerValues.SORT_ALPHA);
		DRadioButtonMenuItem timeSort = new DRadioButtonMenuItem(this, settings.getSortType() == DmfHandler.SORT_TIME, ViewerValues.SORT_TIME);
		DRadioButtonMenuItem ratingSort = new DRadioButtonMenuItem(this, settings.getSortType() == DmfHandler.SORT_RATING, ViewerValues.SORT_RATING);
		DRadioButtonMenuItem viewSort = new DRadioButtonMenuItem(this, settings.getSortType() == DmfHandler.SORT_VIEWS, ViewerValues.SORT_VIEWS);
		sortGroup.add(alphaSort);
		sortGroup.add(timeSort);
		sortGroup.add(ratingSort);
		sortGroup.add(viewSort);
		sortMenu.add(alphaSort);
		sortMenu.add(timeSort);
		sortMenu.add(ratingSort);
		sortMenu.add(viewSort);
		sortMenu.addSeparator();
		sortMenu.add(new DCheckBoxMenuItem(this, settings.getGroupArtists(), ViewerValues.GROUP_ARTISTS));
		sortMenu.add(new DCheckBoxMenuItem(this, settings.getGroupSequences(), ViewerValues.GROUP_SEQUENCES));
		sortMenu.add(new DCheckBoxMenuItem(this, settings.getGroupSections(), ViewerValues.GROUP_SECTIONS));
		sortMenu.addSeparator();
		sortMenu.add(new DCheckBoxMenuItem(this, settings.getReverseOrder(), ViewerValues.REVERSE_ORDER));
		menubar.add(sortMenu);
		
		//FILTER MENU ITEMS
		filterMenu = new DMenu(this, ViewerValues.FILTER);
		filterMenu.add(new DMenuItem(this, ViewerValues.FILTER_MEDIA));
		menubar.add(filterMenu);
		
		//CREATE BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3, settings.getSpaceSize(), 0));
		previousButton = new DButton(this, ViewerValues.PREVIOUS);
		nextButton = new DButton(this, ViewerValues.NEXT);
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
			
			previewPanels[i].setBorder(BorderFactory.createLineBorder(pageText.getDisabledTextColor(), 1));
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Runs when sectionPanel is resized, and sets a new grid panel for previews.
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
	 */
	private void loadDirectory(final boolean useIndexSettings)
	{
		if(!getFrame().isProcessRunning() && (!useIndexSettings || !getDmfHandler().isLoaded()))
		{
			getFrame().setProcessRunning(true);
			progressDialog.setCancelled(false);
			progressDialog.startProgressDialog(getFrame(), DmfLanguageValues.LOADING_DMFS_TITLE);
			
			if(useIndexSettings)
			{
				loader.loadDMFs(getSettings().getUseIndexes(), getSettings().getUseIndexes(), getSettings().getUpdateIndexes());
				
			}//IF
			else
			{
				loader.loadDMFs(false, getSettings().getUseIndexes(), false);
				
			}//ELSE
			
		}//IF
		else
		{
			loadingDMFsDone();
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Resets the preview values.
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
	 */
	private void launchPreviewUpdate()
	{
		if(!getFrame().isProcessRunning())
		{
			disableAll();
			int total = previewWidth * previewHeight;
			String text = ((offset / total) + 1) + Character.toString('/') + (int)Math.ceil((double)getDmfHandler().getFilteredSize() / (double)total);
			
			if(offset % total != 0)
			{
				text = text + getSettings().getLanguageText(ViewerValues.OFFSET) + (offset - ((int)Math.floor((double)offset / (double)total) * total));
			
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
				progressDialog.startProgressDialog(getFrame(), ViewerValues.LOADING_PREVIEWS_TITLE);
				progressDialog.setProcessLabel(ViewerValues.LOADING_PREVIEWS);
				progressDialog.setDetailLabel(CommonValues.RUNNING, true);
				(new DSwingWorker(this, ViewerValues.LOADING_PREVIEWS)).execute();
			
			}//IF
			else
			{
				enableAll();
				
			}//ELSE
			
		}//IF
		
	}//METHOD
	
	/**
	 * Updates the preview panel.
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
				if((offset + i) < getDmfHandler().getFilteredSize())
				{
					previewValues[i] = offset + i;
					
					StringBuilder html = new StringBuilder();
					html.append("<html>"); //$NON-NLS-1$
					html.append(StringMethods.addHtmlEscapes(getDmfHandler().getTitleFiltered(offset + i)));
					
					if(getSettings().getShowArtists())
					{
						html.append("<br><i>"); //$NON-NLS-1$
						html.append(StringMethods.addHtmlEscapes(StringMethods.arrayToString(getDmfHandler().getArtistsFiltered(offset + i))));
						html.append("</i>"); //$NON-NLS-1$
						
					}//IF
					
					if(getSettings().getShowViews())
					{
						html.append("<br><b>"); //$NON-NLS-1$
						html.append(StringMethods.addHtmlEscapes(getSettings().getLanguageText(ViewerValues.VIEWS)));
						html.append("</b>&nbsp;"); //$NON-NLS-1$
						html.append(Integer.toString(getDmfHandler().getViewsFiltered(offset + i)));
						
					}//IF
					
					if(getSettings().getShowRatings() && (getDmfHandler().getRatingFiltered(offset + i) > 0))
					{
						html.append("<br><b>"); //$NON-NLS-1$
						html.append(StringMethods.addHtmlEscapes(getSettings().getLanguageText(ViewerValues.RATING)));
						html.append("</b>&nbsp;"); //$NON-NLS-1$
						html.append(StringMethods.addHtmlEscapes(StringMethods.extendCharacter('★', getDmfHandler().getRatingFiltered(offset + i))));
						
					}//IF
					
					html.append("</html>"); //$NON-NLS-1$
					
					previewLabels[i].setText(html.toString());
					
					previewButtons[i].setImage(getDmfHandler().getMediaFileFiltered(offset + i), getDmfHandler().getSecondaryFileFiltered(offset + i),  progressDialog.isCancelled() || !getSettings().getUseThumbnails());
					
					if(progressDialog.isCancelled())
					{
						previewValues[i] = -1;
						
					}//IF
					
				}//IF
				else
				{
					previewValues[i] = -1;
					previewLabels[i].setText(new String());
					previewButtons[i].noImage();
					
				}//ELSE
				
			}//IF
			
		}//FOR
		
	}//METHOD
	
	/**
	 * Sets the current offset value then updates media previews.
	 * 
	 * @param offset Given Offset
	 */
	public void setOffset(final int offset)
	{
		this.offset = offset;
		launchPreviewUpdate();
		
	}//METHOD
	
	/**
	 * Updates panel to the next page of previews.
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
		
		if(newOffset < getDmfHandler().getFilteredSize())
		{
			offset = newOffset;
			launchPreviewUpdate();
			
		}//IF
		
	}//METHOD
	
	/**
	 * Updates panel to the previous page of previews.
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
			if(page > ((int)Math.ceil((double)getDmfHandler().getFilteredSize() / (double)total) - 1))
			{
				page = (int)Math.ceil((double)getDmfHandler().getFilteredSize() / (double)total) - 1;
				
			}//IF
			
			offset = page * total;
			
			
		}//TRY
		catch(NumberFormatException e)
		{
			//GET TITLE IF NOT NUMBER
			if(text.length() > 0)
			{
				int size = getDmfHandler().getFilteredSize();
				for(int i = 0; i < size; i++)
				{
					if(getDmfHandler().getTitleFiltered(i).toLowerCase().contains(text))
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
	public void dispose()
	{
		filterGUI.dispose();
		getFrame().dispose();
		
	}//METHOD
	
	@Override
	public void enableAll() 
	{
		if(offset > 0)
		{
			previousButton.setEnabled(true);
			
		}//IF
		
		if((offset + (previewWidth * previewHeight)) < getDmfHandler().getFilteredSize())
		{
			nextButton.setEnabled(true);
			
		}//IF
		
		settingsBar.enableAll();
		pageText.setEnabled(true);
		fileMenu.setEnabled(true);
		viewMenu.setEnabled(true);
		sortMenu.setEnabled(true);
		filterMenu.setEnabled(true);
		
		int size = getDmfHandler().getFilteredSize();
		for(int i = 0; i < previewButtons.length; i++)
		{
			if((offset + i) < size)
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
		filterMenu.setEnabled(false);
		
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
	 */
	private void sortRadioPressed(final int sortType, final boolean isSelected)
	{
		if(isSelected)
		{
			getSettings().setSortType(sortType);
			loader.sortDMFsDefault();
			
		}//IF
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case PreviewButton.PREVIEW_EVENT:
				new ViewerGUI(this, offset + value, true);
				break;
			case ViewerValues.NEXT:
				nextPage();
				break;
			case ViewerValues.PREVIOUS:
				previousPage();
				break;
			case PAGE_ACTION:
				textEntered();
				break;
			case DResizeListener.RESIZE:
				previewResized();
				break;
			case ViewerValues.USE_THUMBNAILS:
				getSettings().setUseThumbnails(BooleanInt.getBoolean(value));
				resetValues();
				launchPreviewUpdate();
				break;
			case ViewerValues.SHOW_ARTISTS:
				getSettings().setShowArtists(BooleanInt.getBoolean(value));
				resetValues();
				launchPreviewUpdate();
				break;
			case ViewerValues.SHOW_RATINGS:
				getSettings().setShowRatings(BooleanInt.getBoolean(value));
				resetValues();
				launchPreviewUpdate();
				break;
			case ViewerValues.SHOW_VIEWS:
				getSettings().setShowViews(BooleanInt.getBoolean(value));
				resetValues();
				launchPreviewUpdate();
				break;
			case ViewerValues.SORT_ALPHA:
				sortRadioPressed(DmfHandler.SORT_ALPHA, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.SORT_TIME:
				sortRadioPressed(DmfHandler.SORT_TIME, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.SORT_RATING:
				sortRadioPressed(DmfHandler.SORT_RATING, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.SORT_VIEWS:
				sortRadioPressed(DmfHandler.SORT_VIEWS, BooleanInt.getBoolean(value));
				break;
			case ViewerValues.GROUP_ARTISTS:
				getSettings().setGroupArtists(BooleanInt.getBoolean(value));
				loader.sortDMFsDefault();
				break;
			case ViewerValues.GROUP_SEQUENCES:
				getSettings().setGroupSequences(BooleanInt.getBoolean(value));
				loader.sortDMFsDefault();
				break;
			case ViewerValues.GROUP_SECTIONS:
				getSettings().setGroupSections(BooleanInt.getBoolean(value));
				if(!getSettings().getGroupSequences())
					loader.sortDMFsDefault();
				break;
			case ViewerValues.REVERSE_ORDER:
				getSettings().setReverseOrder(BooleanInt.getBoolean(value));
				loader.sortDMFsDefault();
				break;
			case ViewerValues.FILTER_MEDIA:
				filterGUI.showFilterGUI();
				break;
			case ViewerValues.RELOAD_DMFS:
				loadDirectory(true);
				break;
			case ViewerValues.RELOAD_WITHOUT_INDEXES:
				loadDirectory(false);
				break;
			case CommonValues.RESTART_PROGRAM:
				Start.startGUI(getSettings(), getDmfHandler());
				dispose();
				break;
			case CommonValues.EXIT:
				filterGUI.dispose();
				dispose();
				break;
			
		}//SWITCH
		
	}//METHOD

	@Override
	public void run(String id)
	{
		switch(id)
		{
			case ViewerValues.LOADING_PREVIEWS:
				updatePreview();
				break;
				
		}//SWITCH
		
	}//METHOD

	@Override
	public void done(String id)
	{
		progressDialog.setCancelled(false);
		progressDialog.closeProgressDialog();
		getFrame().setProcessRunning(false);
		
	}//METHOD

	@Override
	public void loadingDMFsDone()
	{
		settingsBar.setLabelLoaded(getDmfHandler().isLoaded());
		loader.sortDMFsDefault();
		
	}//METHOD

	@Override
	public void sortingDMFsDone()
	{
		loader.filterDMFs();
		
	}//METHOD

	@Override
	public void filteringDMFsDone()
	{
		offset = 0;
		resetValues();
		launchPreviewUpdate();
		
	}//METHOD
	
}//CLASS
