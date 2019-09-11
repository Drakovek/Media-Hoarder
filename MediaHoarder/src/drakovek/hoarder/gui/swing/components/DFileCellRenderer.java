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
import drakovek.hoarder.processing.StringMethods;

/**
 * Cell renderer for a DFileList for showing file icons.
 *
 * @author Drakovek
 * @version 2.0
 */
public class DFileCellRenderer extends DefaultListCellRenderer
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1774598669923495036L;

	/**
	 * FileSystemView used to get file icons.
	 */
	private FileSystemView fileSystemView;
	
	/**
	 * Cell label for showing file names.
	 */
	private DLabel label;
	
	/**
	 * Cell panel for holding cell contents.
	 */
	private JPanel panel;
	
	/**
	 * Border used when cell is focused.
	 */
	private Border focusBorder;
	
	/**
	 * Border used when cell is not focused.
	 */
	private Border noBorder;
	
	/**
	 * Default background color.
	 */
	private Color background;
	
	/**
	 * Background color to use if cell is selected.
	 */
	private Color backgroundSelected;
	
	/**
	 * Initializes the cell renderer.
	 * 
	 * @param baseGUI BaseGUI used for formatting the labels and cell spacing.
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
        label.setText(file.getName() + StringMethods.extendCharacter(' ', 4));
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
