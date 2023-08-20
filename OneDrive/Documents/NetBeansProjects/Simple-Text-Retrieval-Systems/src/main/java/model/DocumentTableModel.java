package model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/*
 * DocumentTableModel.java
 *  
 * @author Abdul Waris Sherzad
 */
 
public class DocumentTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected List<Document> list;
    
	public DocumentTableModel(List<Document> list) {
		this.list = list;
	}
	
    private final String[] columnNames = new String[] {
            "S.No.", "DATE", "TOPICS", "TITLE", "BODY"
    };
    
    @SuppressWarnings("rawtypes")
	private final Class[] columnClass = new Class[] {
        Integer.class, String.class, String.class, String.class, String.class
    };
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
 
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }
 
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
 
    @Override
    public int getRowCount() {
        return list.size();
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	
    	Document row = list.get(rowIndex);
    	if(0 == columnIndex) {
            return ++rowIndex;
        }
        if(1 == columnIndex) {
            return row.getDate();
        }
        else if(2 == columnIndex) {
            return row.getTopic();
        }
        else if(3 == columnIndex) {
            return row.getTitle();
        }
        else if(4 == columnIndex) {
            return row.getBody();
        }
        return null;
    }
}
