package drakovek.hoarder.gui.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.Start;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.ScreenDimensions;
import drakovek.hoarder.gui.settings.SettingsBarGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DMenu;
import drakovek.hoarder.gui.swing.components.DMenuItem;
import drakovek.hoarder.gui.swing.components.DTextField;
import drakovek.hoarder.gui.swing.listeners.DResizeListener;

/**
 * Creates the browser GUI for viewing DMF Media.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class ViewBrowserGUI extends FrameGUI
{
	/**
	 * Action for when pageText event occurs.
	 * 
	 * @since 2.0
	 */
	private static final String PAGE_ACTION = "page"; //$NON-NLS-1$
	
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
	 * Initializes the ViewBrowserGUI class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public ViewBrowserGUI(DSettings settings)
	{
		super(settings, DefaultLanguage.VIEWER_TITLE);
		createPreviewPanels();
		previewWidth = 0;
		previewHeight = 0;
		
		JMenuBar menubar = new JMenuBar();
		
		//FILE MENU ITEMS
		DMenu fileMenu = new DMenu(this, DefaultLanguage.FILE);
		DMenuItem openItem = new DMenuItem(this, DefaultLanguage.OPEN);
		DMenuItem updateItem = new DMenuItem(this, DefaultLanguage.UPDATE_INDEXES);
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
		pageText = new DTextField(this, PAGE_ACTION);
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
		viewPanel.add(getSpacedPanel(previewPanel), BorderLayout.CENTER);
		viewPanel.add(getSpacedPanel(bottomPanel, 1, 1, false, false, true, true), BorderLayout.SOUTH);
		
		//FINALIZE FRAME
		SettingsBarGUI settingsBar = new SettingsBarGUI(this);
		getFrame().setJMenuBar(menubar);
		getFrame().getContentPane().add(viewPanel, BorderLayout.CENTER);
		getFrame().getContentPane().add(settingsBar.getPanel(), BorderLayout.SOUTH);
		getFrame().pack();
		getFrame().setLocationRelativeTo(null);
		getFrame().setVisible(true);
		
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
		
		for(int i = 0; i < total; i++)
		{
			previewPanels[i] = new JPanel();
			previewPanels[i].setLayout(new GridLayout(1,1));
			previewPanels[i].add(new DButton(this, Integer.toString(i)));
			
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
			
		}//IF (width != previewWidth && height != previewHeight)
		
	}//METHOD
	
	@Override
	public void enableAll() 
	{
		previousButton.setEnabled(true);
		nextButton.setEnabled(true);
		pageText.setEnabled(true);
		
	}//METHOD

	@Override
	public void disableAll()
	{
		previousButton.setEnabled(false);
		nextButton.setEnabled(false);
		pageText.setEnabled(false);
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DResizeListener.RESIZE:
			{
				previewResized();
				break;
				
			}//CASE
			case DefaultLanguage.RESTART_PROGRAM:
			{
				Start.startGUI(getSettings());
				
			}//CASE
			case DefaultLanguage.EXIT:
			{
				dispose();
				break;
			}//CASE
			
		}//SWITCH
		
	}//METHOD
	
}//CLASS
