package drakovek.hoarder.gui.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DLabel;

/**
 * Contains methods for creating a Settings Bar
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class SettingsBarGUI extends BaseGUI
{
	
	/**
	 * Main Settings Bar Panel for the class
	 * 
	 * @since 2.0
	 */
	JPanel barPanel;
	
	/**
	 * Initializes the SettingsBarGUI class with its default Swing Layout.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public SettingsBarGUI(DSettings settings)
	{
		super(settings);
		
		DButton settingsButton = new DButton(this, DefaultLanguage.SETTINGS);
		DLabel label = new DLabel(this, null, new String());
		
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
	 * @since 2.0
	 */
	public JPanel getPanel()
	{
		return barPanel;
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		// TODO Auto-generated method stub
		
	}//METHOD
	
}//CLASS
