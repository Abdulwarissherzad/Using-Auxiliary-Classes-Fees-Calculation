package frame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import org.apache.log4j.Logger;

import model.DocumentTableModel;
import service.DocumentsFromSGM;
import util.TableHeaderRenderer;

/**
 * STRSFrame.java
 * @author Abdul Waris Sherzad
 */

public class STRSFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableCellRenderer cellRenderer;
	private JTextField jtfSrarch;
	private JLabel searchLbl;
	private JTable table;
	JLabel jlTotal;
	@SuppressWarnings("rawtypes")
	private TableRowSorter sorter;
	private JScrollPane jsp;
	JSeparator jSep;
	JPanel jPannel;
	Logger logger = Logger.getLogger(STRSFrame.class.getName());
	
	public STRSFrame() {
		super("Sherzad's: Simple Text Retrieval System");
		initComponents();

		setSize(1100, 680);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		logger.info("GUI frame loaded successfully!");
	}

	@SuppressWarnings("unchecked")
	public void initComponents() {

            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            jPannel = new JPanel(new GridLayout(1, 1));
            jPannel.setLayout(new FlowLayout(FlowLayout.CENTER));

            jtfSrarch = new JTextField(20);
            jtfSrarch.setToolTipText("Please enter key to search");
            jtfSrarch.requestFocusInWindow();
            searchLbl = new JLabel("Search");

            DocumentTableModel model = new DocumentTableModel(DocumentsFromSGM.document);
            sorter= new TableRowSorter<>(model);		
            table = new JTable(model);
            table.getColumnModel().getColumn(0).setPreferredWidth(5);
            cellRenderer = new DefaultTableCellRenderer();
	    cellRenderer.setHorizontalAlignment(JLabel.CENTER);
	    table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
	    table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
	    table.getTableHeader().setDefaultRenderer(new TableHeaderRenderer());
            table.setRowSorter(sorter);
            table.setAutoCreateRowSorter(false);
            table.setPreferredScrollableViewportSize(new Dimension(980, 560));
            setLayout(new FlowLayout(FlowLayout.CENTER));
		
		table.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 2 && table.getSelectedRow() != -1) {
	               JTable target = (JTable)me.getSource();
	               int row = target.getSelectedRow();
	               
	               JTextArea jta = new JTextArea(30,60);
	               jta.setEditable(false);
	               jta.setLineWrap(true);
	               jta.setWrapStyleWord(true);
	               jta.setFont(jta.getFont().deriveFont(Font.BOLD, 14f));
	               JScrollPane scrollPane = new JScrollPane(jta);
	               getContentPane().add(scrollPane);
					
	               String val = 
	            		  "DATE	: " + table.getValueAt(row, 1).toString() + "\n"
	               		+ "TOPIC	: " + table.getValueAt(row, 2).toString() + "\n"
	               		+ "TITLE	: " + table.getValueAt(row, 3).toString() + "\n\n"
	               		+ "BODY	: " + table.getValueAt(row, 4).toString()+"\n";	
	               jta.setText(val);
	               
	               JOptionPane.showMessageDialog(null, scrollPane);
	            }
	         }
	     });
		
		jsp = new JScrollPane(table);

		jSep = new JSeparator();
		jSep.setOrientation(SwingConstants.HORIZONTAL);
		
		jlTotal = new JLabel("Total Count : " + String.valueOf(table.getRowCount()));
		add(jPannel);
		add(searchLbl);
		add(jtfSrarch);
		add(jSep);
		add(jsp);
		add(jlTotal);

		jtfSrarch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(jtfSrarch.getText());
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				search(jtfSrarch.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				search(jtfSrarch.getText());
			}
			public void search(String str) {
				if (str.length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter(str));
				}
			}
		});
	}
}