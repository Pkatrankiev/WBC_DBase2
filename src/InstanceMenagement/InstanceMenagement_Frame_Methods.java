package InstanceMenagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import BasicClassAccessDbase.Instance;
import BasicClassAccessDbase.UsersWBC;
import PersonReference.PersonReferenceExportToExcell;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;



public class InstanceMenagement_Frame_Methods {

	static Object[][] dataTable;
	static JTable table;
	private static ProgrssBarWorker_InstanceMenagement pbwpr;
	
		
	
	static void ActionListenertextField_Year(JTextField textField_Year, JButton btn_StartGenerateTable) {
		textField_Year.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {
				int minYeare = 0;
				String minYearInDbase = ReadFileBGTextVariable.getGlobalTextVariableMap().get("minYearInDbase");
				try {
					minYeare = Integer.parseInt(minYearInDbase);
				} catch (Exception e) {
					JFrame jf = new JFrame();
					jf.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jf, "Year not korekt in BGTextVariable", "Error",
							JOptionPane.PLAIN_MESSAGE, null);
					System.exit(0);
				}
				textField_Year.setForeground(Color.BLACK);
				btn_StartGenerateTable.setEnabled(true);
				if (!textField_Year.getText().isEmpty()) {
					try {
						long number = Long.parseLong(textField_Year.getText());
						if (number < minYeare || number > Calendar.getInstance().get(Calendar.YEAR)+1) {
							textField_Year.setForeground(Color.RED);
							btn_StartGenerateTable.setEnabled(false);
						}
					} catch (Exception e) {
						textField_Year.setForeground(Color.RED);
						btn_StartGenerateTable.setEnabled(false);
					}
				}
			}
		});

	}
		
	public static void ActionListener_Btn_StartGenerateTable_Instance(JButton btn_StartGenerateTable,
			JPanel panel_AllSaerch, JPanel tablePane, JScrollPane scrollPane, UsersWBC user, JProgressBar progressBar) {

		btn_StartGenerateTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				InstanceMenagement_Frame.viewInfoPanel();
		
				GeneralMethods.setWaitCursor(tablePane);

				JTextField textField_Year = InstanceMenagement_Frame.getTextField_Year();
					
				    	 if(textField_Year.getText().isEmpty()) {
				    		 JOptionPane.showMessageDialog(null, "Въведете коректна година", "Грешни данни", JOptionPane.ERROR_MESSAGE);
				    	 }else {
				    	pbwpr = new ProgrssBarWorker_InstanceMenagement(textField_Year, progressBar, panel_AllSaerch, tablePane,
								scrollPane);
						pbwpr.execute();	
				    	 }
				    
				

				GeneralMethods.setDefaultCursor(panel_AllSaerch);

			}

//					

		});

	}
		
	public static void ActionListener_Btn_ExportToExcel(JButton btn_Export, JPanel tablePane) {

		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Object[][] volue = extractedVolumeTable(table);
				String[] columName = getExcelTabHeader();

				PersonReferenceExportToExcell.btnExportTableToExcell(volue, columName, tablePane, "Instance");

			}
		});

	}
	
	public static void setDataTable(Object[][] dataTable1) {
		dataTable = dataTable1;
	}

	public static void panel_infoPanelTablePanel(JPanel panel_AllSaerch, JPanel tablePane, JScrollPane scrollPane,
			JCheckBox chckbx_Editing) {

		String[] columnNames = getTabHeader();
		@SuppressWarnings("rawtypes")
		Class[] types = getCulumnClass();

		table = new JTable();

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {

				if (SwingUtilities.isLeftMouseButton(e)) {
					if (table.getSelectedRow() != -1) {
					int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());

					if (e.getClickCount() == 2 && selectedRow != -1) {

							createInfoFrame(selectedRow);
//							selectRow = selectedRow;
						
						}
					}
				}

			}
		});

		new TableFilterHeader(table, AutoChoices.ENABLED);
		System.out.println(dataTable.length);
		for (int i = 0; i < dataTable.length; i++) {
			for (int j = 0; j < dataTable[0].length; j++) {
				System.out.println(i+":"+j+" "+dataTable[i][j]);
				
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				DefaultTableModel dtm = new DefaultTableModel(dataTable, columnNames) {

					private static final long serialVersionUID = 1L;
					@SuppressWarnings("rawtypes")
					private Class[] types2 = types;

					@Override
					public Class<?> getColumnClass(int columnIndex) {
						return this.types2[columnIndex];
					}

					public Object getValueAt(int row, int col) {
						return dataTable[row][col];
					}

					@Override
					public boolean isCellEditable(int row, int column) {
//						if (operatorUser != null && operatorUser.get_isAdmin() && chckbx_Editing.isSelected()) {
//								return true;
//							} else {
						return false;
//						}
					}

					public void setValueAt(Object value, int row, int col) {

						if (!dataTable[row][col].equals(value)) {
							dataTable[row][col] = value;
//							fireTableCellUpdated(row, col);
//							AddInUpdateList(row);
						}
					}

				};
				
				table.setModel(dtm);
				setInvisibleColumn(table, 6);
				table.setFillsViewportHeight(true);
				setupTableRenderers(table);
				initColumnSizes(table);

				
			}
		});
		
		tablePane.removeAll();
		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane(table);
		tablePane.add(scrollPane, BorderLayout.CENTER);
		
		if(table != null) {
			InstanceMenagement_Frame.getBtn_Export().setEnabled(true);
		}

	}

	private static void setupTableRenderers(JTable table) {

	    // 1. Рендър за дата - центрирана, формат yyyy-MM-dd HH:mm:ss
	    DefaultTableCellRenderer dateRenderer = new DefaultTableCellRenderer() {
	        private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	        @Override
	        public void setValue(Object value) {
	            if (value instanceof java.util.Date) {
	                setText(sdf.format((java.util.Date) value));
	            } else {
	                super.setValue(value);
	            }
	        }
	    };
	    dateRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    table.setDefaultRenderer(java.util.Date.class, dateRenderer);
	}
	
	
	
	private static void initColumnSizes(JTable table) {
	   	
    	DefaultTableModel model =(DefaultTableModel) table.getModel();
        TableColumn column = null;
      
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
       Object[] longValues = getlong();
       
        TableCellRenderer headerRenderer =
            table.getTableHeader().getDefaultRenderer();

        for (int i = 0; i < dataTable[0].length; i++) {
            column = table.getColumnModel().getColumn(i);

            comp = headerRenderer.getTableCellRendererComponent(
                                 null, column.getHeaderValue(),
                                 false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width+15;
            
            if (!java.util.Date.class.isAssignableFrom(model.getColumnClass(i))) {
            comp = table.getDefaultRenderer(model.getColumnClass(i)).
                             getTableCellRendererComponent(
                                 table, longValues[i],
                                 false, false, 0, i);
            cellWidth = comp.getPreferredSize().width+15;
            }else {
            	cellWidth = longValues[i].toString().length()+15;
            }
            

           

            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
            column.sizeWidthToFit(); //or simple
        }
    }
	
	private static Object[] getlong() {
Object[] types = {"123", "12345678901234567890","1234567890", 
			"12345678901234567890","12345678901234567890", "12345678901234567890123", ""};

	return types;
}

	protected static void createInfoFrame(int k) {
	
		String table = (String) dataTable[k][1];
		Object[] selektInfo =  dataTable[k];
		 
		switch (table) {
		case "PersonStatusNew":
			PersonStatusNew_InstanceFrom.create_InfoFrame_PersonStatus(selektInfo);
			break;
		case "Person":
			Person_InstanceFrom.create_InfoFrame_Person(selektInfo);
			break;
		default:
			break;
		}

		
	}

	@SuppressWarnings("rawtypes")
	public static Class[] getCulumnClass() {
		Class[] types = { Integer.class, String.class, String.class, String.class, String.class, java.util.Date.class, String.class};
		return types;
	}
	
	static String[] getExcelTabHeader() {
		String[] tableHeader = { "№", "Таблица", "Команда", "От къде", "Въвел", "Дата на Въвеждане"};
		return tableHeader;
	}

	static String[] getTabHeader() {
		String[] tableHeader = { "<html><center>№<br>_</center></html>", "<html><center>Таблица<br>_</center></html>",
				"<html><center>Команда<br>_</center></html>", "<html><center>От къде<br>_</center></html>",
				"<html><center>Въвел<br>_</center></html>", "<html><center>Дата на<br>Въвеждане</center></html>",
				 ""};
		return tableHeader;
	}
	
	private static Object[][] extractedVolumeTable(JTable table) {
		TableColumnModel tcm = table.getColumnModel();
//		get how column with Width > 0  -> k
		int k = 0;
		for (int j = 0; j < table.getColumnCount(); j++) {
			if (tcm.getColumn(j).getWidth() > 0) {
				k++;
			}
		}
		Object[][] volue = new Object[table.getRowCount()][k];

		int colCount;
		for (int rowTable = 0; rowTable < table.getRowCount(); rowTable++) {
			colCount = 0;
			for (int j = 0; j < table.getColumnCount(); j++) {
				if (tcm.getColumn(j).getWidth() > 0) {
					volue[rowTable][colCount] = table.getModel().getValueAt(table.convertRowIndexToModel(rowTable),
							table.convertColumnIndexToModel(j));

				}
				colCount++;
			}

		}
		return volue;
	}	
	
	public static void setInvisibleColumn (JTable table, int rsult_Id_Colum){
		table.getColumnModel().getColumn(rsult_Id_Colum).setWidth(0);
		table.getColumnModel().getColumn(rsult_Id_Colum).setMinWidth(0);
		table.getColumnModel().getColumn(rsult_Id_Colum).setMaxWidth(0);
		table.getColumnModel().getColumn(rsult_Id_Colum).setPreferredWidth(0);
	}
		
	protected static Object[][] generateMasiveSelectionInstance(List<Instance> listInstance) {

		Object[][] dataTable = new Object[listInstance.size()][7];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
		int k = 0;
		for (Instance instanceObj : listInstance) {
			dataTable[k][0] = (k + 1);
			dataTable[k][1] = instanceObj.getTable();
			dataTable[k][2] = instanceObj.getCommand();
			dataTable[k][3] = instanceObj.getSource();
			dataTable[k][4] = instanceObj.getUserWBC().getLastName();
			dataTable[k][5] = instanceObj.getDateSet(); // трябва да е java.util.Date / Timestamp
			
			dataTable[k][6] = instanceObj.getTextInstance();
		k++;
		}

		return dataTable;

	}

	
	
}
