package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/*
 * TableHeaderRenderer.java
 * 
 * @author Abdul Waris Sherzad
 */

public class TableHeaderRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	public TableHeaderRenderer() {
        setFont(new Font("Times New Roman", Font.BOLD, 15));
        setBorder(BorderFactory.createEtchedBorder());
        setForeground(Color.BLUE);
        setHorizontalAlignment(JLabel.CENTER);
    }
     
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        return this;
    } 
}
