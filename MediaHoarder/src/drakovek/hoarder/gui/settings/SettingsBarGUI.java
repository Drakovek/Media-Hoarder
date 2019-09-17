package drakovek.hoarder.gui.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.swing.components.ComponentDisabler;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DLabel;

/**
 * Contains methods for creating a Settings Bar
 * 
 * @author Drakovek
 * @version 2.0
 */
public class SettingsBarGUI extends BaseGUI implements ComponentDisabler
{
	/**
	 * Main label for the Settings Bar
	 */
	private DLabel label;
	
	/**
	 * Main Settings Bar Panel for the class
	 */
	private JPanel barPanel;
	
	/**
	 * Button used to open the SettingsGUI
	 */
	private DButton settingsButton;
	
	/**
	 * FrameGUI the settings bar is contained within
	 */
	private FrameGUI ownerGUI;

	/**
	 * Initializes the SettingsBarGUI class with its default Swing Layout.
	 * 
	 * @param ownerGUI FrameGUI the settings bar is contained within
	 */
	public SettingsBarGUI(FrameGUI ownerGUI)
	{
		super(ownerGUI.getSettings());
		this.ownerGUI = ownerGUI;
		
		settingsButton = new DButton(this, DefaultLanguage.SETTINGS);
		label = new DLabel(this, null, new String());
		
		JPanel internalPNL = new JPanel();
		internalPNL.setLayout(new GridBagLayout());
		GridBagConstraints internalCST = new GridBagConstraints();
		internalCST.gridx = 2;		internalCST.gridy = 2;
		internalCST.gridwidth = 1;	internalCST.gridheight = 1;
		internalCST.weightx = 0;	internalCST.weighty = 0;
		internalCST.fill = GridBagConstraints.BOTH;
		internalPNL.add(settingsButton, internalCST);
		internalCST.gridx = 1;
		internalPNL.add(getHorizontalSpace(), internalCST);
		internalCST.gridx = 2;		internalCST.gridy = 1;
		internalPNL.add(getVerticalSpace(), internalCST);
		internalCST.gridx = 0;		internalCST.gridy = 2;
		internalCST.weightx = 1;
		internalPNL.add(label, internalCST);
		internalCST.gridy = 0;		internalCST.gridwidth = 3;
		internalPNL.add(new JSeparator(SwingConstants.HORIZONTAL), internalCST);
		
		barPanel = getSpacedPanel(internalPNL, 1, 0, true, true, true, true);
		
	}//CONSTRUCTOR
	
	/**
	 * Returns the Settings Bar Panel
	 * 
	 * @return Settings Bar Panel
	 */
	public JPanel getPanel()
	{
		return barPanel;
		
	}//METHOD
	
	/**
	 * Sets the main label for the settings bar to reflect a currently selected directory.
	 * 
	 * @param file Directory to display
	 */
	public void setLabel(final File file)
	{
		if(file != null && file.isDirectory())
		{
			label.setText(file.getAbsolutePath());
			
		}//IF
		else
		{
			label.setText(getSettings().getLanguageText(DefaultLanguage.NO_DIRECTORY));
			
		}//ELSE
		
	}//METHOD
	
	/**
	 * Sets the main label of the settings bar to show if the multiple directories have been properly loaded.
	 * 
	 * @param loaded Whether DMF directories have been properly loaded
	 */
	public void setLabelLoaded(final boolean loaded)
	{
		if(loaded)
		{
			label.setText(getSettings().getLanguageText(DefaultLanguage.DIRECTORIES_LOADED));
			
		}//IF
		else
		{
			label.setText(getSettings().getLanguageText(DefaultLanguage.NO_DIRECTORY));
			
		}//ELSE
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		new SettingsGUI(getSettings(), ownerGUI.getDmfHandler(), ownerGUI);
		
	}//METHOD

	@Override
	public void enableAll()
	{
		settingsButton.setEnabled(true);
		
	}//METHOD

	@Override
	public void disableAll()
	{
		settingsButton.setEnabled(false);
		
	}//METHOD
	
}//CLASS
