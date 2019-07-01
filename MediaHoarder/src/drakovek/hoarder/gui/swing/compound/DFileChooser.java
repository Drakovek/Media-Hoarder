package drakovek.hoarder.gui.swing.compound;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.BaseGUI;
import drakovek.hoarder.gui.swing.components.DButton;
import drakovek.hoarder.gui.swing.components.DDialog;
import drakovek.hoarder.gui.swing.components.DFrame;

/**
 * Creates a GUI for choosing a file to either open or save.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DFileChooser extends BaseGUI
{
	/**
	 * Main dialog for the file chooser.
	 * 
	 * @since 2.0
	 */
	private DDialog dialog;
	
	/**
	 * Panel contained within the file chooser dialog
	 * 
	 * @since 2.0
	 */
	private JPanel panel;
	
	/**
	 * Initializes the DFileChooser class.
	 * 
	 * @param settings Program Settings
	 * @since 2.0
	 */
	public DFileChooser(DSettings settings)
	{
		super(settings);
		
		//CREATE BOTTOM PANEL
		JPanel okPanel = new JPanel();
		okPanel.setLayout(new GridLayout(1, 2, settings.getSpaceSize(), 0));
		okPanel.add(new DButton(this, DefaultLanguage.CANCEL));
		okPanel.add(new DButton(this, DefaultLanguage.OK));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints bottomCST = new GridBagConstraints();
		bottomCST.gridx = 2;		bottomCST.gridy = 0;
		bottomCST.gridwidth = 1;	bottomCST.gridheight = 3;
		bottomCST.weightx = 0;		bottomCST.weighty = 0;
		bottomPanel.add(okPanel, bottomCST);
		bottomCST.gridx = 0;		bottomCST.weightx = 1;
		bottomCST.gridwidth = 2;
		bottomPanel.add(getHorizontalSpace(), bottomCST);
		
		//FINALIZE PANEL
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(this.getSpacedPanel(bottomPanel, 1, 0, false, true, false, true), BorderLayout.SOUTH);
		
	}//CONSTRUCTOR
	
	/**
	 * Creates the file chooser dialog for opening a directory.
	 * 
	 * @param owner DFrame to which the file chooser is tied
	 * @param startDirectory Directory to start within
	 * @since 2.0
	 */
	public void createOpenChooser(DFrame owner, final File startDirectory)
	{
		
		dialog = new DDialog(owner, panel ,getTitle(DefaultLanguage.OPEN_TITLE), 0, 0);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		
	}//METHOD

	@Override
	public void event(String id, int value)
	{
		switch(id)
		{
			case DefaultLanguage.CANCEL:
			{
				dialog.dispose();
				break;
				
			}//CASE
			
		}//SWITCH
		
	}//METHOD
	
}//CLASS
