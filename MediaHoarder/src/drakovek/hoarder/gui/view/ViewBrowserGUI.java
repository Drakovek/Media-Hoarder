package drakovek.hoarder.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DMenu;
import drakovek.hoarder.gui.swing.components.DMenuItem;
import drakovek.hoarder.gui.swing.components.DTextField;
import drakovek.hoarder.gui.swing.compound.DFileChooser;
import drakovek.hoarder.gui.swing.compound.DProgressDialog;
import drakovek.hoarder.gui.swing.listeners.DResizeListener;
import drakovek.hoarder.processing.StringMethods;
import drakovek.hoarder.work.DSwingWorker;
import drakovek.hoarder.work.Worker;

/**
 * Creates the browser GUI for viewing DMF Media.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ViewBrowserGUI extends FrameGUI implements Worker
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
	 * File Chooser for choosing a directory from which to search for DMFs
	 * 
	 * @since 2.0
	 */
	private DFileChooser fileChooser;
	
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
	private DButton[] previewButtons;
	
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
		fileChooser = new DFileChooser(settings);
		progressDialog = new DProgressDialog(settings);
		previewWidth = 0;
		previewHeight = 0;
		offset = 0;
		pageText = new DTextField(this, PAGE_ACTION);
		pageText.setHorizontalAlignment(SwingConstants.CENTER);
		createPreviewPanels();
		
		JMenuBar menubar = new JMenuBar();
		
		//FILE MENU ITEMS
		DMenu fileMenu = new DMenu(this, DefaultLanguage.FILE);
		DMenuItem openItem = new DMenuItem(this, DefaultLanguage.OPEN);
		DMenuItem updateItem = new DMenuItem(this, DefaultLanguage.OPEN_WITHOUT_INDEXES);
		DMenuItem resetItem = new DMenuItem(this, DefaultLanguage.RESTART_PROGRAM);
		DMenuItem exitItem = new DMenuItem(this, DefaultLanguage.EXIT);
		fileMenu.add(openItem);
		fileMenu.add(updateItem);
		fileMenu.addSeparator();
		fileMenu.add(resetItem);
		fileMenu.add(exitItem);
		menubar.add(fileMenu);
		
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
		
		settingsBar.setLabel(settings.getViewerDirectory());
		loadDirectory(settings.getViewerDirectory(), true);
		
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
		previewButtons = new DButton[total];
		previewLabels = new DLabel[total];
		previewValues = new int[total];
		Dimension previewSpace = new Dimension(getSettings().getPreviewSize() + (getSettings().getSpaceSize() * 2), 1);
		
		for(int i = 0; i < total; i++)
		{
			previewLabels[i] = new DLabel(this, null, new String());
			previewLabels[i].setHorizontalAlignment(SwingConstants.LEFT);
			previewLabels[i].setVerticalAlignment(SwingConstants.TOP);
			previewButtons[i] = new DButton(this, Integer.toString(i));
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
	 * @param directory Directory to load DMFs from
	 * @param useIndexSettings Whether to use the user's settings for reading DMF indexes. If false, DmfHanlder will load DMFs without using index files.
	 * @since 2.0
	 */
	private void loadDirectory(final File directory, final boolean useIndexSettings)
	{
		if(!getFrame().isProcessRunning() && directory != null && directory.isDirectory() && (!useIndexSettings || getDmfHandler().getDirectory() == null || !getDmfHandler().getDirectory().equals(directory)))
		{
			getSettings().setViewerDirectory(directory);
			getFrame().setProcessRunning(true);
			progressDialog.setCancelled(false);
			progressDialog.startProgressDialog(getFrame(), DefaultLanguage.LOADING_DMFS_TITLE);
			
			if(useIndexSettings)
			{
				(new DSwingWorker(this, DefaultLanguage.LOADING_DMFS)).execute();
				
			}//IF
			else
			{
				(new DSwingWorker(this, DefaultLanguage.OPEN_WITHOUT_INDEXES)).execute();
				
			}//ELSE
			
		}//IF
		
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
				text = text + DefaultLanguage.OFFSET + (offset - ((int)Math.floor((double)offset / (double)total) * total));
			
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
				progressDialog.setDetailLabel(DefaultLanguage.RUNNING);
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
					
				}//IF
				else
				{
					previewValues[i] = -1;
					previewLabels[i].setText(new String());
					
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
		
		for(int i = 0; i < previewButtons.length; i++)
		{
			previewButtons[i].setEnabled(false);
			
		}//FOR
		
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
			case DefaultLanguage.OPEN:
				loadDirectory(fileChooser.getFileOpen(getFrame(), getSettings().getViewerDirectory()), true);
				break;
			case DefaultLanguage.OPEN_WITHOUT_INDEXES:
				loadDirectory(fileChooser.getFileOpen(getFrame(), getSettings().getViewerDirectory()), false);
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
				this.getDmfHandler().loadDMFs(getSettings().getViewerDirectory(), progressDialog, getSettings().getUseIndexes(), getSettings().getUseIndexes(), getSettings().getUpdateIndexes());
				break;
			case DefaultLanguage.OPEN_WITHOUT_INDEXES:
				this.getDmfHandler().loadDMFs(getSettings().getViewerDirectory(), progressDialog, false, getSettings().getUseIndexes(), false);
				break;
			case DefaultLanguage.LOADING_PREVIEWS:
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
		
		switch(id)
		{
			case DefaultLanguage.OPEN_WITHOUT_INDEXES:
			case DefaultLanguage.LOADING_DMFS:
				settingsBar.setLabel(getDmfHandler().getDirectory());
				launchPreviewUpdate();
				break;
				
		}//SWITCH
		
	}//METHOD
	
}//CLASS
