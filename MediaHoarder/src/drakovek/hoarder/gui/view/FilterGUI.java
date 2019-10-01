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

import drakovek.hoarder.file.dmf.DmfLoader;
import drakovek.hoarder.file.language.CommonValues;
import drakovek.hoarder.file.language.DmfLanguageValues;
import drakovek.hoarder.file.language.ViewerValues;
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
	 * DMF loader used to filter DMFs
	 */
	private DmfLoader loader;
	
	/**
	 * Frame GUI parent of the Filter GUI
	 */
	private FrameGUI ownerGUI;
	
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
	 * DTextField for the artist filter
	 */
	private DTextField artistText;
	
	/**
	 * DButton for reseting the filter text fields
	 */
	private DButton resetButton;
	
	/**
	 * DButton for canceling the filtering operation
	 */
	private DButton cancelButton;
	
	/**
	 * DButton for finalizing filter options and starting the filtering process
	 */
	private DButton okButton;
	
	/**
	 * Initializes the FilterGUI class by creating the main GUI.
	 * 
	 * @param ownerGUI Frame GUI parent of the Filter GUI
	 * @param loader DMF loader used to filter DMFs
	 */
	public FilterGUI(FrameGUI ownerGUI, DmfLoader loader)
	{
		super(ownerGUI.getSettings(), ownerGUI.getDmfHandler(), ViewerValues.FILTER);
		this.loader = loader;
		this.ownerGUI = ownerGUI;
		
		//SET INITIAL VALUES
		caseSensitive = getDmfHandler().getFilterCaseSensitive();
		userTagText = new DTextField(this, DmfLanguageValues.USER_TAG_LABEL);
		userTagText.setText(getDmfHandler().getUserTagFilter());
		webTagText = new DTextField(this, DmfLanguageValues.WEB_TAG_LABEL);
		webTagText.setText(getDmfHandler().getWebTagFilter());
		titleText = new DTextField(this, DmfLanguageValues.TITLE_LABEL);
		titleText.setText(getDmfHandler().getTitleFilter());
		descriptionText = new DTextField(this, DmfLanguageValues.DESCRIPTION_LABEL);
		descriptionText.setText(getDmfHandler().getDescriptionFilter());
		artistText = new DTextField(this, DmfLanguageValues.ARTISTS_LABEL);
		artistText.setText(getDmfHandler().getArtistFilter());
		
		//CREATE BOTTOM PANEL
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3, getSettings().getSpaceSize(), 0));
		resetButton = new DButton(this, CommonValues.RESET);
		cancelButton = new DButton(this, CommonValues.CANCEL);
		okButton = new DButton(this, CommonValues.OK);
		buttonPanel.add(resetButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(new DCheckBox(this, caseSensitive, CommonValues.CASE_SENSITIVE), BorderLayout.WEST);
		bottomPanel.add(getHorizontalSpace(), BorderLayout.CENTER);
		bottomPanel.add(buttonPanel, BorderLayout.EAST);
		
		//CREATE CENTER PANEL
		int largestWidth = 0;
		largestWidth = getStringSizeIfLarger(resetButton, largestWidth, getSettings().getLanguageText(DmfLanguageValues.USER_TAG_LABEL));
		largestWidth = getStringSizeIfLarger(resetButton, largestWidth, getSettings().getLanguageText(DmfLanguageValues.WEB_TAG_LABEL));
		largestWidth = getStringSizeIfLarger(resetButton, largestWidth, getSettings().getLanguageText(DmfLanguageValues.TITLE_LABEL));
		largestWidth = getStringSizeIfLarger(resetButton, largestWidth, getSettings().getLanguageText(DmfLanguageValues.DESCRIPTION_LABEL));
		largestWidth = getStringSizeIfLarger(resetButton, largestWidth, getSettings().getLanguageText(DmfLanguageValues.ARTISTS_LABEL));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(5, 1, 0, getSettings().getSpaceSize()));
		centerPanel.add(getTextPanel(userTagText, DmfLanguageValues.USER_TAG_LABEL, largestWidth));
		centerPanel.add(getTextPanel(webTagText, DmfLanguageValues.WEB_TAG_LABEL, largestWidth));
		centerPanel.add(getTextPanel(titleText, DmfLanguageValues.TITLE_LABEL, largestWidth));
		centerPanel.add(getTextPanel(descriptionText, DmfLanguageValues.DESCRIPTION_LABEL, largestWidth));
		centerPanel.add(getTextPanel(artistText, DmfLanguageValues.ARTISTS_LABEL, largestWidth));
				
		//FINALIZE FRAME
		DLabel title = new DLabel(this, null, ViewerValues.FILTER);
		title.setFontLarge();
		getFrame().getContentPane().add(getSpacedPanel(getVerticalStack(title, new JSeparator(SwingConstants.HORIZONTAL)), 1, 0, true, true, true, true), BorderLayout.NORTH);
		getFrame().getContentPane().add(getSpacedPanel(centerPanel, 1, 1, false, false, true, true), BorderLayout.CENTER);
		getFrame().getContentPane().add(getSpacedPanel(getVerticalStack(new JSeparator(SwingConstants.HORIZONTAL), bottomPanel), 1, 0, true, true, true, true), BorderLayout.SOUTH);
		getFrame().interceptFrameClose(this);
		getFrame().packRestricted();
		
	}//CLASS
	
	/**
	 * Shows the filter GUI to allow user to start filtering DMFs
	 */
	public void showFilterGUI()
	{
		this.ownerGUI.getFrame().setAllowExit(false);
		enableAll();
		getFrame().setLocationRelativeTo(ownerGUI.getFrame());
		getFrame().setVisible(true);
		
	}//METHOD
	
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
		getDmfHandler().setDescriptionFilter(descriptionText.getText());
		getDmfHandler().setArtistFilter(artistText.getText());
		getDmfHandler().setUserTagFilter(userTagText.getText());
		getDmfHandler().setWebTagFilter(webTagText.getText());
		
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
		artistText.setText(new String());
		
	}//METHOD
	
	@Override
	public void enableAll()
	{
		titleText.setEnabled(true);
		descriptionText.setEnabled(true);
		artistText.setEnabled(true);
		userTagText.setEnabled(true);
		webTagText.setEnabled(true);
		resetButton.setEnabled(true);
		okButton.setEnabled(true);
		cancelButton.setEnabled(true);
		
	}//METHOD

	@Override
	public void disableAll()
	{
		titleText.setEnabled(false);
		descriptionText.setEnabled(false);
		artistText.setEnabled(false);
		userTagText.setEnabled(false);
		webTagText.setEnabled(false);
		resetButton.setEnabled(false);
		okButton.setEnabled(false);
		cancelButton.setEnabled(false);
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DmfLanguageValues.TITLE_LABEL:
			case DmfLanguageValues.DESCRIPTION_LABEL:
			case DmfLanguageValues.USER_TAG_LABEL:
			case DmfLanguageValues.WEB_TAG_LABEL:
			case DmfLanguageValues.ARTISTS_LABEL:
			case CommonValues.OK:
				getFrame().setAllowExit(false);
				addFilters();
				loader.filterDMFs();
				getFrame().setVisible(false);
				break;
			case CommonValues.CASE_SENSITIVE:
				caseSensitive = BooleanInt.getBoolean(value);
				break;
			case CommonValues.RESET:
				resetFilters();
				break;
			case CommonValues.CANCEL:
			case DCloseListener.FRAME_CLOSE_EVENT:
				ownerGUI.getFrame().setAllowExit(true);
				getFrame().setVisible(false);
				break;
			
		}//SWITCH
		
	}//METHOD

}//CLASS
