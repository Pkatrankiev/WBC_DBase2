package CheckCodeByExcelFileAndOID;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.PersonDAO;
import BasicClassAccessDbase.Person;
import PersonReference.PersonReferenceExportToExcell;
import PersonReference.TextInAreaTextPanel;
import PersonReference_OID.OID_Metods;
import PersonReference_OID.OID_Person_AEC;
import PersonReference_OID.OID_Person_AECDAO;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;


public class CheckCodeByExcelFileAndOID_Methods {

	static Object[][] dataTable;
	static JTable table;
	static int selectRow = -1;
	private static int egn_code_Colum = 1;
	
	private static ProgressBarWorkerPersonOIDCode pbwpr;
	
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
		
	
	public static void ActionListener_Btn_StartGenerateTable_DataOID(JTextArea textArea, JButton btn_StartGenerateTable,
			JPanel panel_AllSaerch, JPanel tablePane, JScrollPane scrollPane, JProgressBar progressBar) {

		btn_StartGenerateTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CheckCodeByExcelFileAndOID_Frame.viewInfoPanel();
				
				GeneralMethods.setWaitCursor(tablePane);

				System.out.println("4444444444444444444444444444");
				    	pbwpr = new ProgressBarWorkerPersonOIDCode(textArea, progressBar, panel_AllSaerch, tablePane,
								scrollPane);
						pbwpr.execute();	
	
				GeneralMethods.setDefaultCursor(panel_AllSaerch);

			}

//					

		});

	}
	
	public static boolean OptionDialog(String mesage) {

				String[] options = { "Не", "Да" };
				JFrame jf = new JFrame();
				jf.setAlwaysOnTop(true);
				int x = JOptionPane.showOptionDialog(jf, mesage, "Info", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

				if (x > 0) {
					return true;
				}

				return false;
			}

	public static void ActionListener_Btn_ExportToExcel(JButton btn_Export, JPanel tablePane) {

		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Object[][] volue = extractedVolumeTable(table);
				String[] columName = getExcelTabHeader();

				PersonReferenceExportToExcell.btnExportTableToExcell(volue, columName, tablePane, "PersonReference");

			}
		});

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

	
	public static String[] getInfoFromAECByEGN(String egn) {

		String[] str = new String[6];
		OID_Person_AEC person_AEC = OID_Person_AECDAO.getOID_Person_AECByEGN(egn);
		if (person_AEC != null) {
			str[0]= person_AEC.getEgn();
			str[1]= person_AEC.getFirstName(); 
			str[2]= person_AEC.getSecondName() ;
			str[3]= person_AEC.getLastName() ;

			
		
		}
		return str;

	}
	
	public static void setDataTable(Object[][] dataTable1) {
		dataTable = dataTable1;
	}

	@SuppressWarnings("unused")
	public static void panel_infoPanelTablePanel(JTextArea textArea, JPanel panel_AllSaerch, JPanel tablePane, JScrollPane scrollPane) {

		String[] columnNames = getTabHeader();
		@SuppressWarnings("rawtypes")
		Class[] types = getCulumnClass();
		JButton btnBackToTable = CheckCodeByExcelFileAndOID_Frame.getBtnBackToTable();
		JButton btn_Export = CheckCodeByExcelFileAndOID_Frame.getBtn_Export();
		String year = CheckCodeByExcelFileAndOID_Frame.getTextField_Year().getText();
		btn_Export.setEnabled(true);
		
		table = new JTable();

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (table.getSelectedRow() != -1) {
					int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());

					if (e.getClickCount() == 2 && table.getSelectedColumn() == egn_code_Colum) {

						GeneralMethods.setWaitCursor(tablePane);
						String reqCodeStr = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();
						String firstName = model.getValueAt(getSelectedModelRow(table), (egn_code_Colum+1)).toString();
						System.out.println("egn " + reqCodeStr);
						btnBackToTable.setEnabled(true);
						btn_Export.setEnabled(false);
						Person person = searchPerson(reqCodeStr,firstName);
						String fromOID = OID_Metods.getInfoByEGN(reqCodeStr);
						String fromExel = TextInAreaTextPanel.createInfoPanelForPerson(year, person, true, 0);
						textArea.setText("Информация от Ексел файла: \n \n"+fromExel+
								"\n \n Информация от ОИД базата: \n \n"+fromOID);

						CheckCodeByExcelFileAndOID_Frame.viewInfoPanel();

						GeneralMethods.setDefaultCursor(tablePane);
					}
					}
				}

			}

			
		});

		new TableFilterHeader(table, AutoChoices.ENABLED);

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
							fireTableCellUpdated(row, col);
//							AddInUpdateList(row);
						}
					}

				};

				table.setModel(dtm);
				table.setFillsViewportHeight(true);

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
			CheckCodeByExcelFileAndOID_Frame.getBtn_Export().setEnabled(true);
		}

	}
	
	private static Person searchPerson(String egn, String firstName) {
		if(egn.length()<10) {
			List<Person> listPerson = PersonDAO.getListValuePersonByEGN(egn);	
			System.out.println(listPerson.size());
			if(listPerson.size()>1) {
				for (Person person : listPerson) {
					System.out.println(person.getFirstName().toLowerCase()+" "+(firstName.toLowerCase()));
					if(person.getFirstName().toLowerCase().equals(firstName.toLowerCase())) {
					return 	person;
					}
				}
			}else {
				return listPerson.get(0);
			}
		}
		
		return PersonDAO.getValuePersonByEGN(egn);
	}
	
	
	
	
	private static int getSelectedModelRow(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}
	static String[] getTabHeader() {
		String[] tableHeader = {
				"<html><center>№<br>_</center></html>",
				"<html><center>ЕГН<br>_</center></html>",
				"<html><center>Име<br>_</center></html>", 
				"<html><center>Презиме<br>_</center></html>",
				"<html><center>Фамилия<br>_</center></html>",
				"<html><center>КЗ-1<br>ОИД</center></html>",
				"<html><center>КЗ-1<br>База</center></html>", 
				"<html><center>КЗ-2<br>ОИД</center></html>",
				"<html><center>КЗ-2<br>База</center></html>", 
				};
		return tableHeader;
	}

	static String[] getExcelTabHeader() {
		String[] tableHeader = {"№", "ЕГН", "Име", "Презиме", "Фамилия", "КЗ-1 ОИД", "КЗ-1", "КЗ-2 ОИД", "КЗ-2"};
		return tableHeader;
	}

	@SuppressWarnings("rawtypes")
	public static Class[] getCulumnClass() {
		Class[] types = {Integer.class, String.class, String.class, String.class, String.class, String.class,
				String.class, String.class, String.class};
		return types;
	}

	public static int[] getCulumnSize() {
		int[] col = {50, 60, 180, 180, 180, 65, 65, 65, 65 };
		return col;
	}

	private static void initColumnSizes(JTable table) {

		TableColumn column = null;

		int[] col = getCulumnSize();

		for (int i = 0; i < col.length; i++) {
			column = table.getColumnModel().getColumn(i);
			column.setMinWidth(col[i]);

		}
	}

	
	public static void ActionListenerBtnBackToTable(JButton btnBackToTable, JTextArea textArea, JPanel tablePane,
			JPanel panel_AllSaerch, JScrollPane scrollPane) {
		JButton btn_Export = CheckCodeByExcelFileAndOID_Frame.getBtn_Export();
		btnBackToTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dataTable != null) {
					GeneralMethods.setWaitCursor(tablePane);
					btnBackToTable.setEnabled(false);
					btn_Export.setEnabled(true);
					CheckCodeByExcelFileAndOID_Frame.viewTablePanel();
					GeneralMethods.setDefaultCursor(tablePane);

				}
			}

		});

	}
	

	
}
