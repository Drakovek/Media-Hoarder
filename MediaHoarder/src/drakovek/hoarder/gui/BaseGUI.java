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
import drakovek.hoarder.file.language.DefaultLanguage;
import drakovek.hoarder.gui.swing.listeners.DEvent;

/**
 * Default GUI Object for use in other GUI Objects.
 * 
 * @author Drakovek
 * @version 2.0
 */
public abstract class BaseGUI implements DEvent
{
	/**
	 * Program's settings.
	 */
	private DSettings settings;
	
	/**
	 * Default font for the program's Swing components.
	 */
	private Font font;
	
	/**
	 * Large font to use for the program's Swing components.
	 */
	private Font largeFont;
	
	/**
	 * Initializes the BaseGUI Class
	 * 
	 * @param settings Program's Settings
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
		largeFont = new Font(settings.getFontName(), Font.BOLD, (int)(settings.getFontSize() * ((double)4/(double)3)));
		
	}//CONSTRUCTOR
	
	/**
	 * Returns the program settings.
	 * 
	 * @return Program Settings
	 */
	public DSettings getSettings()
	{
		return settings;
		
	}//METHOD
	
	/**
	 * Gets the default font for the project.
	 * 
	 * @return Default Font
	 */
	public Font getFont()
	{
		return font;
		
	}//METHOD
	
	/**
	 * Gets the project's large font.
	 * 
	 * @return Large Font
	 */
	public Font getLargeFont()
	{
		return largeFont;
		
	}//METHOD
	
	/**
	 * Returns a JPanel with a given component surrounded by spaces as specified.
	 * 
	 * @param component Component to be centered within the panel
	 * @return Panel with component centered
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
	 * Creates a JPanel That consists of two components stacked vertically with default space in between.
	 * 
	 * @param topComponent Component to place on top
	 * @param topWeight Weight of the top component
	 * @param bottomComponent Component to place on bottom
	 * @param bottomWeight Weight of the bottom component
	 * @return Vertical Stack Panel
	 */
	public JPanel getVerticalStack(Component topComponent, final int topWeight, Component bottomComponent, final int bottomWeight)
	{
		JPanel stackPanel = new JPanel();
		stackPanel.setLayout(new GridBagLayout());
		GridBagConstraints stackCST = new GridBagConstraints();
		stackCST.gridx = 1;			stackCST.gridy = 1;
		stackCST.gridwidth = 1;		stackCST.gridheight = 1;
		stackCST.weightx = 0;		stackCST.weighty = 0;
		stackCST.fill = GridBagConstraints.BOTH;
		stackPanel.add(getVerticalSpace(), stackCST);
		stackCST.gridx = 0;			stackCST.gridy = 0;
		stackCST.gridwidth = 3;		stackCST.weightx = 1;
		stackCST.weighty = topWeight;
		stackPanel.add(topComponent, stackCST);
		stackCST.gridy = 2;			stackCST.weighty = bottomWeight;
		stackPanel.add(bottomComponent, stackCST);
		
		return stackPanel;
		
	}//METHOD
	
	/**
	 * Creates a JPanel That consists of two components stacked vertically with default space in between, with minimum default weight.
	 * 
	 * @param topComponent Component to place on top
	 * @param bottomComponent Component to place on bottom
	 * @return Vertical Stack Panel
	 */
	public JPanel getVerticalStack(Component topComponent, Component bottomComponent)
	{
		return getVerticalStack(topComponent, 0, bottomComponent, 0);
		
	}//METHOD
	
	/**
	 * Creates a JPanel That consists of two components stacked horizontally with default space in between.
	 * 
	 * @param leftComponent Component to place on the left
	 * @param leftWeight Weight of the left component
	 * @param rightComponent Component to place on the right
	 * @param rightWeight Weight of the right component
	 * @return Horizontal Stack Panel
	 */
	public JPanel getHorizontalStack(Component leftComponent, final int leftWeight, Component rightComponent, final int rightWeight)
	{
		JPanel stackPanel = new JPanel();
		stackPanel.setLayout(new GridBagLayout());
		GridBagConstraints stackCST = new GridBagConstraints();
		stackCST.gridx = 1;			stackCST.gridy = 1;
		stackCST.gridwidth = 1;		stackCST.gridheight = 1;
		stackCST.weightx = 0;		stackCST.weighty = 0;
		stackCST.fill = GridBagConstraints.BOTH;
		stackPanel.add(getHorizontalSpace(), stackCST);
		stackCST.gridx = 0;			stackCST.gridy = 0;
		stackCST.gridheight = 3;	stackCST.weighty = 1;
		stackCST.weightx = leftWeight;
		stackPanel.add(leftComponent, stackCST);
		stackCST.gridx = 2;			stackCST.weightx = rightWeight;
		stackPanel.add(rightComponent, stackCST);
		
		return stackPanel;
		
	}//METHOD
	
	/**
	 * Creates a JPanel That consists of two components stacked horizontally with default space in between, with minimum default weight.
	 * 
	 * @param leftComponent Component to place on the left
	 * @param rightComponent Component to place on the right
	 * @return Horizontal Stack Panel
	 */
	public JPanel getHorizontalStack(Component leftComponent, Component rightComponent)
	{	
		return getHorizontalStack(leftComponent, 0, rightComponent, 0);
		
	}//METHOD
	
	/**
	 * Returns a rigid area component with the width of the default space for the program.
	 * 
	 * @return Rigid Area Component
	 */
	public Component getHorizontalSpace()
	{
		return Box.createRigidArea(new Dimension(settings.getSpaceSize(), 1));
	
	}//METHOD
	
	/**
	 * Returns a rigid area component with the height of the default space for the program.
	 * 
	 * @return Rigid Area Component
	 */
	public Component getVerticalSpace()
	{
		return Box.createRigidArea(new Dimension(1, settings.getSpaceSize()));
	
	}//METHOD
	
	/**
	 * Returns the default button Insets based on the default space size for the program.
	 * 
	 * @return Default Button Insets
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
	
	/**
	 * Returns the default menu Insets based on the default space size for the program.
	 * 
	 * @return Default Menu Insets
	 */
	public Insets getMenuInsets()
	{
		int space = settings.getSpaceSize() / 2;
		if(space < 1)
		{
			space = 1;
			
		}//IF
		
		return new Insets(space, space, space, space);
		
	}//METHOD
	
	/**
	 * Gets the title for a frame, with subtitle if called for.
	 * 
	 * @param subtitleID ID for the subtitle. If null, only uses the default title.
	 * @return Title
	 */
	public String getTitle(final String subtitleID)
	{
		if(subtitleID == null)
		{
			return settings.getLanguageText(DefaultLanguage.TITLE_VALUE);
			
		}//IF
		
		return settings.getLanguageText(DefaultLanguage.TITLE_VALUE) + " - " + settings.getLanguageText(subtitleID); //$NON-NLS-1$
		
	}//METHOD
	
	
}//CLASS
