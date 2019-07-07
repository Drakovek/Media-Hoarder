package drakovek.hoarder.gui.artist;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.gui.FrameGUI;
import drakovek.hoarder.gui.settings.SettingsBarGUI;
import drakovek.hoarder.gui.swing.components.DLabel;

/**
 * Creates GUI for downloading files from artist hosting websites.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public abstract class ArtistHostingGUI extends FrameGUI
{
	/**
	 * Settings Bar for the GUI
	 * 
	 * @since 2.0
	 */
	private SettingsBarGUI settingsBar;
	
	/**
	 * Initializes the ArtistHostingGUI
	 * 
	 * @param settings Program Settings
	 * @param subtitleID ID for the sub-title of the frame
	 * @since 2.0
	 */
	public ArtistHostingGUI(DSettings settings, final String subtitleID)
	{
		super(settings, subtitleID);
		
		//TITLE PANEL
		DLabel titleLabel = new DLabel(this, null, subtitleID);
		titleLabel.setFontLarge();
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new GridBagLayout());
		GridBagConstraints titleCST = new GridBagConstraints();
		titleCST.gridx = 0;			titleCST.gridy = 0;
		titleCST.gridwidth = 3;		titleCST.gridheight = 1;
		titleCST.weightx = 1;		titleCST.weighty = 0;
		titleCST.fill = GridBagConstraints.BOTH;
		titlePanel.add(titleLabel, titleCST);
		titleCST.gridy = 1;
		titlePanel.add(getVerticalSpace(), titleCST);
		titleCST.gridy = 2;
		titlePanel.add(new JSeparator(SwingConstants.HORIZONTAL), titleCST);
		
		//FINALIZE GUI
		settingsBar = new SettingsBarGUI(this);
		getFrame().getContentPane().add(this.getSpacedPanel(titlePanel, 1, 0, true, true, true, true), BorderLayout.NORTH);
		getFrame().getContentPane().add(settingsBar.getPanel(), BorderLayout.SOUTH);
		getFrame().pack();
		getFrame().setLocationRelativeTo(null);
		getFrame().setVisible(true);
		
	}//CONSTRUCTOR
	
	@Override
	public void enableAll()
	{

	}//METHOD

	@Override
	public void disableAll()
	{

	}//METHOD

	@Override
	public void event(String id, int value)
	{

	}//METHOD
	
}//CLASS
