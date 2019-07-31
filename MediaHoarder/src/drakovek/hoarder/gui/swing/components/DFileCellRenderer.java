package drakovek.hoarder.gui.swing.components;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIDefaults;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;

import drakovek.hoarder.gui.BaseGUI;

/**
 * Cell renderer for a DFileList for showing file icons.
 *
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class DFileCellRenderer extends DefaultListCellRenderer
{	
	/**
	 * SerialVersionUID
	 * 
	 * @since 2.0
	 */
	private static final long serialVersionUID = 1774598669923495036L;

	/**
	 * FileSystemView used to get file icons.
	 * 
	 * @since 2.0
	 */
	private FileSystemView fileSystemView;
	
	/**
	 * Cell label for showing file names.
	 * 
	 * @since 2.0
	 */
	private DLabel label;
	
	/**
	 * Cell panel for holding cell contents.
	 * 
	 * @since 2.0
	 */
	private JPanel panel;
	
	/**
	 * Border used when cell is focused.
	 * 
	 * @since 2.0
	 */
	private Border focusBorder;
	
	/**
	 * Border used when cell is not focused.
	 * 
	 * @since 2.0
	 */
	private Border noBorder;
	
	/**
	 * Default background color.
	 * 
	 * @since 2.0
	 */
	private Color background;
	
	/**
	 * Background color to use if cell is selected.
	 * 
	 * @since 2.0
	 */
	private Color backgroundSelected;
	
	/**
	 * Initializes the cell renderer.
	 * 
	 * @param baseGUI BaseGUI used for formatting the labels and cell spacing.
	 * @since 2.0
	 */
	public DFileCellRenderer(BaseGUI baseGUI)
	{
		UIDefaults defaults = javax.swing.UIManager.getDefaults();
		background = defaults.getColor("List.background"); //$NON-NLS-1$
		backgroundSelected = defaults.getColor("List.selectionBackground"); //$NON-NLS-1$
		
		fileSystemView = FileSystemView.getFileSystemView();
		label = new DLabel(baseGUI, null, new String());
		panel = baseGUI.getSpacedPanel(label, 1, 1, false, false, true, true);
		focusBorder = defaults.getBorder("List.focusCellHighlightBorder"); //$NON-NLS-1$
		noBorder = BorderFactory.createLineBorder(Color.WHITE, 0);
		
	}//CONSTRUCTOR
	
	@Override
	@SuppressWarnings("rawtypes")
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean focused)
	{
        File file = (File)value;
        
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(file.getName());
        label.setToolTipText(file.getPath());
        
        if(selected)
        {
        	panel.setBackground(backgroundSelected);
        	panel.setBorder(noBorder);
        	
        }//SELECTED
        else if(focused)
        {
        	panel.setBackground(background);
        	panel.setBorder(focusBorder);
        	
        }//ELSE IF
        else
        {
        	panel.setBackground(background);
        	panel.setBorder(noBorder);
        	
        }//ELSE
        
        return panel;
        
    }//METHOD
	
}//CLASS
