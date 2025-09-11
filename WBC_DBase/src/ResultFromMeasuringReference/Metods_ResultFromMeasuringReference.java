package ResultFromMeasuringReference;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.ResultsWBCDAO;

import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.ResultsWBC;
import DatePicker.DatePicker;
import PersonReference.PersonReferenceExportToExcell;
import PersonReference.TextInAreaTextPanel;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class Metods_ResultFromMeasuringReference {

	private static Object[][] dataTable;
	private static JTable table;
	private static int tbl_Colum;
	private static int egn_code_Colum = 2;
	private static int newZoneKode_Colum = 8;
	private static int choice_Slekt_Colum = 9;
	
	public static List<Measuring> getListMeasuring(String StrStartDate, String StrEndDate, JProgressBar aProgressBar, double stepForProgressBar) {

		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date dateStart = null, dateEnd = null;
		try {
			dateStart = sdfrmt.parse(StrStartDate);
			dateEnd = sdfrmt.parse(StrEndDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Measuring> listMeasur = MeasuringDAO.getValueMeasuringByStartdate_EndDateWithProgressBar(dateStart, dateEnd, aProgressBar, stepForProgressBar);

		return listMeasur;

	}

	
	public static List<String> getListNuclide(List<Measuring> listMeasur, JProgressBar fProgressBar, double stepForProgressBar) {

		Set<String> listNuclide = new HashSet<String>();
		double ProgressBarSize = 90;
		stepForProgressBar = stepForProgressBar / listMeasur.size();
		for (Measuring measur : listMeasur) {
			if (measur.getTypeMeasur().getNameType().equals("M") || measur.getDoze() > 0) {
				List<ResultsWBC> listResult = ResultsWBCDAO.getValueResultsWBCByObject("Measuring_ID", measur);
				for (ResultsWBC result : listResult) {
					listNuclide.add(result.getNuclideWBC().getSymbol_nuclide());
				}
			}
			fProgressBar.setValue((int) ProgressBarSize);
			ProgressBarSize += stepForProgressBar;
		}
					List<String> list = new ArrayList<>();
					for (String string : listNuclide) {
						list.add(string);
					}
		
		
		return list;

	}

	public static boolean checkDateFieldsStartBeForeEnd(JTextField textField_Date_Last,
			JTextField textField_Date_Previos) {

		if (!textField_Date_Last.getText().trim().isEmpty() && !textField_Date_Previos.getText().trim().isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			Date datSt = null;
			Date datEn = null;
			String date_Previos = textField_Date_Previos.getText();
			String date_Last = textField_Date_Last.getText();
			try {
				datEn = sdf.parse(date_Previos);
				datSt = sdf.parse(date_Last);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return date_Last.equals(date_Previos) || datEn.before(datSt);
		} else {
			return true;
		}
	}

	public static void getListMesurFromPeriod(JTextArea textArea, JPanel infoPanel, JPanel tablePane,
			JLayeredPane panel_AllSaerch, JScrollPane scrollPane, JTextField textFieldStartDate,
			JTextField textFieldEndDate, JButton btnBackToTable, JProgressBar progressBar) {


//		return listPersonFromFile;
	}

	static void ActionListener_Btn_ReadMeasur(JButton btn_ReadFileListPerson, JTextArea textArea, JPanel infoPanel,
			JPanel tablePane, JLayeredPane panel_AllSaerch, JScrollPane scrollPane, JTextField textFieldStartDate,
			JTextField textFieldEndDate, JButton btnBackToTable, JProgressBar progressBar) {

		btn_ReadFileListPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("11111111111111111111111111");
				
				GeneralMethods.setWaitCursor(infoPanel);
				textArea.setText("Търся в Базата");
				ResultFromMeasuringReferenceFrame.viewInfoPanel();
				System.out.println("searchFromDBase0");
				
				ProgresBarByResultFromMeasuringReference dd = new ProgresBarByResultFromMeasuringReference( textArea, infoPanel, tablePane, panel_AllSaerch, scrollPane, textFieldStartDate,
						textFieldEndDate, btnBackToTable, progressBar);
										
			dd.execute();
				
		
			}
		});
	}
	
	
	static void setInfo(JTextArea textArea, JPanel infoPanel, JPanel tablePane,
			JLayeredPane panel_AllSaerch, JScrollPane scrollPane, JButton btnBackToTable, Object[][] dataTable, List<String> listNameNuclide) {
	panel_infoPanelTablePanel(textArea, panel_AllSaerch, tablePane, scrollPane, dataTable, listNameNuclide);
	ResultFromMeasuringReferenceFrame.viewTablePanel();
	btnBackToTable.setEnabled(false);
	}
	
	

	public static Object[][] addListMeasur(List<Measuring> listMeasurWithDifLab, List<String> listNuclide) {

		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		tbl_Colum = 6+listNuclide.size();
		Object[][] dataTable = new Object[listMeasurWithDifLab.size()][tbl_Colum];

//				"№", "Date", "EGN", "Name",	"Doze", "Nuclide"

		Person person;
		int k = 0;
		for (Measuring measur : listMeasurWithDifLab) {
			person = measur.getPerson();

			dataTable[k][0] = (k + 1);
			dataTable[k][1] = sdfrmt.format(measur.getDate());
			dataTable[k][2] = person.getEgn();
			dataTable[k][3] = person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName();
			dataTable[k][4] = (Double)measur.getDoze();
			dataTable[k][5] = measur.getTypeMeasur().getNameType();
			List<ResultsWBC> listResult = ResultsWBCDAO.getValueResultsWBCByObject("Measuring_ID", measur);

			int j = 6;
			for (String nuclideName : listNuclide) {
				dataTable[k][j] = 0.0;
				for (ResultsWBC result : listResult) {
					if (result.getNuclideWBC().getSymbol_nuclide().equals(nuclideName)) {
						dataTable[k][j] = (Double)result.getGgp();
					}
				}
				j++;
			}
			k++;
		}

		return dataTable;

	}

	static void panel_infoPanelTablePanel(JTextArea textArea, JLayeredPane panel_AllSaerch, JPanel tablePane,
			JScrollPane scrollPane, Object[][] dataTableIn, List<String> listNameNuclide) {

		@SuppressWarnings("rawtypes") 
		Class[] types2 = getCulumnClass(listNameNuclide);
		dataTable = dataTableIn;
		JButton btnBackToTable = ResultFromMeasuringReferenceFrame.getBtnBackToTable();
		JButton btn_Export = ResultFromMeasuringReferenceFrame.getBtn_Export();
		btn_Export.setEnabled(true);
		String[] columnNames = setTabHeader(listNameNuclide);

		table = new JTable();

	
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();

				if (table.getSelectedColumn() == 10) {

					System.out.println(table.getModel().getColumnClass(4).getSimpleName()+":"+model.getValueAt(getSelectedModelRow(table), 4).toString());
					
				}

				if (e.getClickCount() == 2 && table.getSelectedColumn() == egn_code_Colum) {

					GeneralMethods.setWaitCursor(tablePane);
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();
					System.out.println("egn " + reqCodeStr);
					btnBackToTable.setEnabled(true);
					btn_Export.setEnabled(false);
					Person person = PersonDAO.getValuePersonByEGN(reqCodeStr);
					textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson("", person, false, 0));

					ResultFromMeasuringReferenceFrame.viewInfoPanel();

					GeneralMethods.setDefaultCursor(tablePane);
				}
			}
		});

		new TableFilterHeader(table, AutoChoices.ENABLED);

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				DefaultTableModel dtm = new DefaultTableModel(dataTable, columnNames) {

					private static final long serialVersionUID = 1L;

				

				
					@Override
					public Class<?> getColumnClass(int columnIndex) {
						return types2[columnIndex];
					}

					@Override
					public boolean isCellEditable(int row, int column) {
						if (column == choice_Slekt_Colum || column == newZoneKode_Colum) {
							return true;
						}
						return false;
					}

					@Override
					public Object getValueAt(int row, int col) {
						return dataTable[row][col];
					}

					public void setValueAt(Object value, int row, int col) {

						if (!dataTable[row][col].equals(value)) {
							dataTable[row][col] = value;
						
						}
					}

					public int getColumnCount() {
						return columnNames.length;
					}

					public int getRowCount() {
						return dataTable.length;
					}

				};
				table.setFillsViewportHeight(true);
				table.setModel(dtm);
				
				
				table.getColumnModel().getColumn(4).setCellRenderer(new DoubleCellRenderer());
				
				for (int i = 6; i < 6+listNameNuclide.size(); i++) {
					table.getColumnModel().getColumn(i).setCellRenderer(new DoubleCellRenderer());
				}
				
//				table.getColumnModel().getColumn(rsult_Id_Colum).setWidth(0);
//				table.getColumnModel().getColumn(rsult_Id_Colum).setMinWidth(0);
//				table.getColumnModel().getColumn(rsult_Id_Colum).setMaxWidth(0);
//				table.getColumnModel().getColumn(rsult_Id_Colum).setPreferredWidth(0);

//				initColumnSizes(table, listNameNuclide);
				System.out.println("+++++++++++++ " + dataTable.length);

			}
		});

		table.getTableHeader().setReorderingAllowed(false);

		ResultFromMeasuringReferenceFrame.viewTablePanel();

		tablePane.removeAll();
		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane(table);
		tablePane.add(scrollPane, BorderLayout.CENTER);
	}

	@SuppressWarnings("rawtypes")
	public static Class[] getCulumnClass(List<String> listNameNuclide) {
		
		Class[] types = new Class[6+listNameNuclide.size()];
		types[0] = Integer.class;
		types[1] = String.class;
		types[2] = String.class;
		types[3] = String.class;
		types[4] = Double.class;
		types[5] = String.class;
		for (int i = 6; i < 6+listNameNuclide.size(); i++) {
			types[i] = Double.class;
		}
		
		
		
		return types;
	}
//	"№", "Date", "EGN", "Name",	"Doze", "Nuclide"
	static String[] setTabHeader(List<String> listNameNuclide) {
		String[] tableHeader = new String[6+listNameNuclide.size()];
		tableHeader[0] = " № ";
		tableHeader[1] = "Дата на изм.";
		tableHeader[2] = "ЕГН";
		tableHeader[3] = "Име";
		tableHeader[4] = "Доза";
		tableHeader[5] = "Тип";
		for (int i = 6; i < 6+listNameNuclide.size(); i++) {
			tableHeader[i] = listNameNuclide.get(i-6);
		}
		
		return tableHeader;
	}
	
	static String[] getTableHeader(JTable table) {
		
		TableModel model = table.getModel();
		String[] tableHeader = new String[model.getColumnCount()];
	
		for (int i = 0; i < model.getColumnCount(); i++) {
			tableHeader[i] = model.getColumnName(i);
		}
		
		return tableHeader;
	}

	
	private static int getSelectedModelRow(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}

	public static void setDataTable(Object[][] dataTable1) {
		dataTable = dataTable1;
	}

	public static void ActionListener_Btn_Export(JButton btn_Export, JPanel save_Panel, JPanel button_Panel) {
		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dataTable != null) {
					Object[][] dataofTable = new Object[table.getRowCount()][table.getColumnCount()];
					for (int row = 0; row < table.getRowCount(); row++) {
						int modelRow = table.convertRowIndexToModel(row);

						System.out.println(table.getRowCount() + " " + row + " " + modelRow);

						for (int i = 0; i < table.getColumnCount(); i++) {
							dataofTable[row][i] = table.getModel().getValueAt(modelRow, i);
						}
					}
					PersonReferenceExportToExcell.btnExportTableToExcell(dataofTable, getTableHeader(table), button_Panel,
							"MeasuringReference");

				}
			}
		});
	}

	static void ActionListenerBtnBackToTable(JButton btnBackToTable, JTextArea textArea, JPanel tablePane,
			JLayeredPane panel_AllSaerch, JScrollPane scrollPane, JTextField textField_svePerson_Year) {
		JButton btn_Export = ResultFromMeasuringReferenceFrame.getBtn_Export();
		btnBackToTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dataTable != null) {
					GeneralMethods.setWaitCursor(tablePane);
					btnBackToTable.setEnabled(false);
					btn_Export.setEnabled(true);
					ResultFromMeasuringReferenceFrame.viewTablePanel();
					GeneralMethods.setDefaultCursor(tablePane);

				}
			}

		});

	}
	
	
	public static void ActionListenerChengePanelSizeByFrameSize(ResultFromMeasuringReferenceFrame measurInOderLabFrame,
			JPanel tablePane, JPanel infoPanel) {

		measurInOderLabFrame.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				int with = measurInOderLabFrame.getWidth();
				int heigth = measurInOderLabFrame.getHeight();
				tablePane.setBounds(0, 0, with - 25, heigth - 180);
				infoPanel.setBounds(0, 0, with - 25, heigth - 180);
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	public static void ActionListenerSetDateByDatePicker(JTextField textField_StartDate2, JButton btn_SaveToExcelFile) {

		textField_StartDate2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e1) {
				if (e1.getClickCount() == 2) {
				Point pointFrame = textField_StartDate2.getLocationOnScreen();
				final JFrame f = new JFrame();
				DatePicker dPicer = new DatePicker(f, false, textField_StartDate2.getText(), pointFrame);
				String str = dPicer.setPickedDate(false);

				textField_StartDate2.setText(str);
				checkorektDate(textField_StartDate2, btn_SaveToExcelFile);
				}
			}
		});

		textField_StartDate2.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {

				checkorektDate(textField_StartDate2, btn_SaveToExcelFile);
				
			}

		});

	}
	
	public static void checkorektDate(JTextField textFieldDate, JButton btn_SaveToExcelFile) {
		textFieldDate.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {

				if (AplicationMetods.incorrectDate(textFieldDate.getText())) {
					textFieldDate.setForeground(Color.RED);
					btn_SaveToExcelFile.setEnabled(false);
				} else {
					textFieldDate.setForeground(Color.BLACK);
					btn_SaveToExcelFile.setEnabled(true);
				}
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});
	}
	

	
}
