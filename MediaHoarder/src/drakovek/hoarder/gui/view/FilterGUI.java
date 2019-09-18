package drakovek.hoarder.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DCheckBox;
import drakovek.hoarder.gui.swing.components.DLabel;
import drakovek.hoarder.gui.swing.components.DTextField;
import drakovek.hoarder.gui.swing.listeners.DCloseListener;
import drakovek.hoarder.processing.BooleanInt;

/**
 * Methods for GUI that gets user input as to what media to filter out.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class FilterGUI extends FrameGUI
{
	/**
	 * Whether the case sensitive check box is checked
	 */
	private boolean caseSensitive;
	
	/**
	 * Frame GUI parent of the Filter GUI
	 */
	private ViewBrowserGUI ownerGUI;
	
	/**
	 * DTextField for the user tag filter
	 */
	private DTextField userTagText;
	
	/**
	 * DTextField for the web tag filter
	 */
	private DTextField webTagText;
	
	/**
	 * DTextField for the title filter
	 */
	private DTextField titleText;
	
	/**
	 * DTextField for the description filter
	 */
	private DTextField descriptionText;
	
	/**
	 * Initializes the FilterGUI class by creating the main GUI.
	 * 
	 * @param ownerGUI Frame GUI parent of the Filter GUI
	 */
	public FilterGUI(ViewBrowserGUI ownerGUI)
	{
		super(ownerGUI.getSettings(), ownerGUI.getDmfHandler(), DefaultLanguage.FILTER);
		this.ownerGUI = ownerGUI;
		this.ownerGUI.getFrame().setAllowExit(false);
		
		//SET INITIAL VALUES
		caseSensitive = getDmfHandler().getFilterCaseSensitive();
		userTagText = new DTextField(this, DefaultLanguage.USER_TAGS);
		webTagText = new DTextField(this, DefaultLanguage.WEB_TAGS);
		titleText = new DTextField(this, DefaultLanguage.TITLE);
		titleText.setText(getDmfHandler().getTitleFilter());
		descriptionText = new DTextField(this, DefaultLanguage.DESCRIPTION);
		
		//CREATE BOTTOM PANEL
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3, getSettings().getSpaceSize(), 0));
		DButton resetButton = new DButton(this, DefaultLanguage.RESET);
		buttonPanel.add(resetButton);
		buttonPanel.add(new DButton(this, DefaultLanguage.CANCEL));
		buttonPanel.add(new DButton(this, DefaultLanguage.OK));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(new DCheckBox(this, caseSensitive, DefaultLanguage.CASE_SENSITIVE), BorderLayout.WEST);
		bottomPanel.add(getHorizontalSpace(), BorderLayout.CENTER);
		bottomPanel.add(buttonPanel, BorderLayout.EAST);
		
		//CREATE CENTER PANEL
		int largestWidth = 0;
		largestWidth = getStringSizeIfLarger(resetButton, largestWidth, getSettings().getLanguageText(DefaultLanguage.USER_TAGS));
		largestWidth = getStringSizeIfLarger(resetButton, largestWidth, getSettings().getLanguageText(DefaultLanguage.WEB_TAGS));
		largestWidth = getStringSizeIfLarger(resetButton, largestWidth, getSettings().getLanguageText(DefaultLanguage.TITLE));
		largestWidth = getStringSizeIfLarger(resetButton, largestWidth, getSettings().getLanguageText(DefaultLanguage.DESCRIPTION));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(4, 1, 0, getSettings().getSpaceSize()));
		centerPanel.add(getTextPanel(userTagText, DefaultLanguage.USER_TAGS, largestWidth));
		centerPanel.add(getTextPanel(webTagText, DefaultLanguage.WEB_TAGS, largestWidth));
		centerPanel.add(getTextPanel(titleText, DefaultLanguage.TITLE, largestWidth));
		centerPanel.add(getTextPanel(descriptionText, DefaultLanguage.DESCRIPTION, largestWidth));
				
		//FINALIZE FRAME
		DLabel title = new DLabel(this, null, DefaultLanguage.FILTER);
		title.setFontLarge();
		getFrame().getContentPane().add(getSpacedPanel(getVerticalStack(title, new JSeparator(SwingConstants.HORIZONTAL)), 1, 0, true, true, true, true), BorderLayout.NORTH);
		getFrame().getContentPane().add(getSpacedPanel(centerPanel, 1, 1, false, false, true, true), BorderLayout.CENTER);
		getFrame().getContentPane().add(getSpacedPanel(getVerticalStack(new JSeparator(SwingConstants.HORIZONTAL), bottomPanel), 1, 0, true, true, true, true), BorderLayout.SOUTH);
		getFrame().interceptFrameClose(this);
		getFrame().packRestricted();
		getFrame().setLocationRelativeTo(ownerGUI.getFrame());
		getFrame().setVisible(true);
		
	}//CLASS
	
	/**
	 * Returns a panel with a label and a text field for a specific filter category.
	 * 
	 * @param textField Text field used in the panel.
	 * @param id ID of the Label
	 * @param labelWidth Width of the label
	 * @return Filter Category Text Panel
	 */
	private JPanel getTextPanel(DTextField textField, final String id, final int labelWidth)
	{
		Dimension spacer = new Dimension(labelWidth, 1);
		DLabel label = new DLabel(this, textField, id);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints panelCST = new GridBagConstraints();
		panelCST.gridx = 0;			panelCST.gridy = 1;
		panelCST.gridwidth = 1;		panelCST.gridheight = 1;
		panelCST.weightx = 0;		panelCST.weighty = 0;
		panelCST.fill = GridBagConstraints.BOTH;
		panel.add(label, panelCST);
		panelCST.gridx = 1;
		panel.add(getHorizontalSpace(), panelCST);
		panelCST.gridx = 0;			panelCST.gridy = 0;
		panelCST.weighty = 1;
		panel.add(Box.createRigidArea(spacer), panelCST);
		panelCST.gridy = 2;
		panel.add(Box.createRigidArea(spacer), panelCST);
		panelCST.gridx = 2;			panelCST.gridy = 0;
		panelCST.gridheight = 3;	panelCST.weightx = 1;
		panel.add(textField, panelCST);
		
		return panel;
		
	}//METHOD
	
	/**
	 * Determines the width of a string in the default font, and returns the width if it is longer than a given width, and otherwise returns the given width.
	 * 
	 * @param component JComponent from which to gather font metrics
	 * @param compareWidth Given width to compare against
	 * @param text Text to determine the width of
	 * @return Width of text if larger than the given width, otherwise returns the given width
	 */
	private int getStringSizeIfLarger(JComponent component, final int compareWidth, final String text)
	{
		int width = component.getFontMetrics(getFont()).stringWidth(text);
		if(width > compareWidth)
		{
			return width;
			
		}//IF
		
		return compareWidth;
		
	}//METHOD
	
	/**
	 * Adds filter strings to the DMF Handler
	 */
	private void addFilters()
	{
		getDmfHandler().setFilterCaseSensitive(caseSensitive);
		getDmfHandler().setTitleFilter(titleText.getText());
		
	}//METHOD
	
	/**
	 * Resets the value of all the filter Strings.
	 */
	private void resetFilters()
	{
		webTagText.setText(new String());
		userTagText.setText(new String());
		titleText.setText(new String());
		descriptionText.setText(new String());
		
	}//METHOD
	
	@Override
	public void enableAll(){}

	@Override
	public void disableAll() {}

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.CASE_SENSITIVE:
				caseSensitive = BooleanInt.getBoolean(value);
				break;
			case DefaultLanguage.OK:
				addFilters();
				dispose();
				ownerGUI.filter();
				break;
			case DefaultLanguage.RESET:
				resetFilters();
				break;
			case DefaultLanguage.CANCEL:
			case DCloseListener.FRAME_CLOSE_EVENT:
				ownerGUI.getFrame().setAllowExit(true);
				dispose();
				break;
			
		}//SWITCH
		
	}//METHOD

}//CLASS
