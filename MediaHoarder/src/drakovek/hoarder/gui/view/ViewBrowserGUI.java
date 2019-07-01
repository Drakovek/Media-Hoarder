package drakovek.hoarder.gui.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.settings.SettingsBarGUI;
import drakovek.hoarder.gui.swing.components.DButton;
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
	 * Initializes the ViewBrowserGUI class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public ViewBrowserGUI(DSettings settings)
	{
		super(settings, DefaultLanguage.VIEWER_TITLE);
		
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
		viewPanel.add(previewPanel, BorderLayout.CENTER);
		viewPanel.add(getSpacedPanel(bottomPanel, 1, 1, false, false, true, true), BorderLayout.SOUTH);
		
		//FINALIZE FRAME
		SettingsBarGUI settingsBar = new SettingsBarGUI(this);
		getFrame().getContentPane().add(viewPanel, BorderLayout.CENTER);
		getFrame().getContentPane().add(settingsBar.getPanel(), BorderLayout.SOUTH);
		getFrame().pack();
		getFrame().setLocationRelativeTo(null);
		getFrame().setVisible(true);
		
	}//CONSTRUCTOR

	/**
	 * Runs when sectionPanel is resized, and sets a new grid panel for previews.
	 * 
	 * @since 1.0
	 */
	private void previewResized()
	{
		System.out.println("test-resized");
		
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
				previewResized();
				break;	
			
		}//SWITCH
		
	}//METHOD
	
}//CLASS
