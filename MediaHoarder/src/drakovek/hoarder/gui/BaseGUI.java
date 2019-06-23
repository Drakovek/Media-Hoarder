package drakovek.hoarder.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JPanel;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.gui.swing.listeners.DEvent;

/**
 * Default GUI Object for use in other GUI Objects.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public abstract class BaseGUI implements DEvent
{
	/**
	 * Program's settings.
	 * 
	 * @since 2.0
	 */
	private DSettings settings;
	
	/**
	 * Default font for the program's Swing components.
	 * 
	 * @since 2.0
	 */
	private Font font;
	
	/**
	 * Initializes the BaseGUI Class
	 * 
	 * @param settings Program's Settings
	 * @since 2.0
	 */
	public BaseGUI(DSettings settings)
	{
		this.settings = settings;
		
		//SET THE FONT
		int fontType = Font.PLAIN;
		if(settings.getFontBold())
		{
			fontType = Font.BOLD;
			
		}//IF
		
		font = new Font(settings.getFontName(), fontType, settings.getFontSize());
		
	}//CONSTRUCTOR
	
	/**
	 * Returns the program settings.
	 * 
	 * @return Program Settings
	 * @since 2.0
	 */
	public DSettings getSettings()
	{
		return settings;
		
	}//METHOD
	
	/**
	 * Gets the default font for the project.
	 * 
	 * @return Default Font
	 * @since 2.0
	 */
	public Font getFont()
	{
		return font;
		
	}//METHOD
	
	/**
	 * Returns a JPanel with a given component surrounded by spaces as specified.
	 * 
	 * @param component Component to be centered within the panel
	 * @return Panel with component centered
	 * @since 2.0
	 */
	public JPanel getSpacedPanel(Component component)
	{
		return getSpacedPanel(component, true, true, true, true);
	
	}//METHOD
	
	/**
	 * Returns a JPanel with a given component surrounded by spaces as specified.
	 * 
	 * @param component Component to be centered within the panel
	 * @param hasTopSpace Whether to use a space on the panel's TOP
	 * @param hasBottomSpace Whether to use a space on the panel's BOTTOM
	 * @param hasLeftSpace Whether to use a space on the panel's LEFT
	 * @param hasRightSpace Whether to use a space on the panel's RIGHT
	 * @return Panel with component centered
	 * @since 2.0
	 */
	public JPanel getSpacedPanel(Component component, final boolean hasTopSpace, final boolean hasBottomSpace, final boolean hasLeftSpace, final boolean hasRightSpace)
	{
		return getSpacedPanel(component, 1, 1, hasTopSpace, hasBottomSpace, hasLeftSpace, hasRightSpace);
	
	}//METHOD
	
	/**
	 * Returns a JPanel with a given component surrounded by spaces as specified.
	 * 
	 * @param component Component to be centered within the panel
	 * @param weightx Horizontal Weight of component (1 or 0)
	 * @param weighty Vertical Weight of component (1 or 0)
	 * @param hasTopSpace Whether to use a space on the panel's TOP
	 * @param hasBottomSpace Whether to use a space on the panel's BOTTOM
	 * @param hasLeftSpace Whether to use a space on the panel's LEFT
	 * @param hasRightSpace Whether to use a space on the panel's RIGHT
	 * @return Panel with component centered
	 * @since 2.0
	 */
	public JPanel getSpacedPanel(Component component, final int weightx, final int weighty, final boolean hasTopSpace, final boolean hasBottomSpace, final boolean hasLeftSpace, final boolean hasRightSpace)
	{
		GridBagConstraints componentCST = new GridBagConstraints();
		componentCST.gridx = 0;				componentCST.gridy = 0;
		componentCST.gridwidth = 3;			componentCST.gridheight = 3;
		componentCST.weightx = weightx;		componentCST.weighty = weighty;
		componentCST.fill = GridBagConstraints.BOTH;
		
		JPanel spacedPanel = new JPanel();
		spacedPanel.setLayout(new GridBagLayout());
		GridBagConstraints spaceCST = new GridBagConstraints();
		spaceCST.gridwidth = 1;		spaceCST.gridheight = 1;
		spaceCST.weightx = 0;		spaceCST.weighty = 0;
		spaceCST.fill = GridBagConstraints.BOTH;
		
		if(hasTopSpace)
		{
			componentCST.gridy = 1;
			componentCST.gridheight--;
			spaceCST.gridx = 1;
			spaceCST.gridy = 0;
			spacedPanel.add(getVerticalSpace(), spaceCST);
		
		}//IF
		
		if(hasBottomSpace)
		{
			componentCST.gridheight--;
			spaceCST.gridx = 1;
			spaceCST.gridy = 2;
			spacedPanel.add(getVerticalSpace(), spaceCST);
		
		}//IF
		
		if(hasLeftSpace)
		{
			componentCST.gridx = 1;
			componentCST.gridwidth--;
			spaceCST.gridx = 0;
			spaceCST.gridy = 1;
			spacedPanel.add(getHorizontalSpace(), spaceCST);
		
		}//IF
		
		if(hasRightSpace)
		{
			componentCST.gridwidth--;
			spaceCST.gridx = 2;
			spaceCST.gridy = 1;
			spacedPanel.add(getHorizontalSpace(), spaceCST);
		
		}//IF
		
		spacedPanel.add(component, componentCST);
		
		return spacedPanel;
		
	}//METHOD
	
	/**
	 * Returns a rigid area component with the width of the default space for the program.
	 * 
	 * @return Rigid Area Component
	 * @since 2.0
	 */
	public Component getHorizontalSpace()
	{
		return Box.createRigidArea(new Dimension(settings.getSpaceSize(), 1));
	
	}//METHOD
	
	/**
	 * Returns a rigid area component with the height of the default space for the program.
	 * 
	 * @return Rigid Area Component
	 * @since 2.0
	 */
	public Component getVerticalSpace()
	{
		return Box.createRigidArea(new Dimension(1, settings.getSpaceSize()));
	
	}//METHOD
	
	/**
	 * Returns the default button Insets based on the default space size for the program.
	 * 
	 * @return Default Button Insets
	 * @since 2.0
	 */
	public Insets getButtonInsets()
	{
		int space = settings.getSpaceSize() / 2;
		if(space < 1)
		{
			space = 1;
			
		}//IF
		
		return new Insets(space, settings.getSpaceSize(), space, settings.getSpaceSize());
	
	}//METHOD
	
}//CLASS
