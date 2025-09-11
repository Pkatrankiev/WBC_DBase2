package BasicTable_Measuring_Results;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReportMeasurClass;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.NuclideWBCDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.NuclideWBC;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.UsersWBC;
import DatePicker.DatePicker;
import DozeArt.DozeArt_Methods;
import InsertMeasuting.AutoInsertMeasutingMethods;
import PersonManagement.PersonelManegementMethods;
import PersonReference.PersonReferenceExportToExcell;
import PersonReference.TextInAreaTextPanel;
import WBCUsersLogin.WBCUsersLogin;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class Measuring_Results_Metods {

	private static Object[][] dataTable;
	private static JTable table;
	private static int tbl_Colum;
	private static int ID_Masur_Colum = 0;
	private static int egn_code_Colum = 3;
	private static int date_Measur_Colum = 2;
	private static UsersWBC operatorUser = null;
//	private static int measur_Id_Colum = 8;
//	private static int choice_Slekt_Colum = 9;

	public static List<Measuring> getListMeasuring(Person person, String StrStartDate, String StrEndDate,
			JProgressBar aProgressBar, double stepForProgressBar) {

		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date dateStart = null, dateEnd = null;
		List<Measuring> listMeasur = new ArrayList<>();
		try {
			dateStart = sdfrmt.parse(StrStartDate);
			dateEnd = sdfrmt.parse(StrEndDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (person != null) {
			listMeasur = MeasuringDAO.getValueMeasuringByStartdate_EndDateWithProgressBar(person.getId_Person(),
					dateStart, dateEnd, aProgressBar, stepForProgressBar);
		} else {
			listMeasur = MeasuringDAO.getValueMeasuringByStartdate_EndDateWithProgressBar(dateStart, dateEnd,
					aProgressBar, stepForProgressBar);

		}
		return listMeasur;

	}

	public static List<String> getListNuclide(List<Measuring> listMeasur, JProgressBar fProgressBar,
			double stepForProgressBar) {

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
			JPanel tablePane, JLayeredPane panel_AllSaerch, JScrollPane scrollPane, JTextField textEGN,
			JTextField textFieldStartDate, JTextField textFieldEndDate, JButton btnBackToTable,
			JProgressBar progressBar) {

		btn_ReadFileListPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("11111111111111111111111111");
				boolean fl = true;
				operatorUser =  WBCUsersLogin.getCurentUser();
				GeneralMethods.setWaitCursor(infoPanel);
				Person person = PersonDAO.getValuePersonByEGN(textEGN.getText());
				System.out.println(textEGN.getText() + " " + person);
				if (person == null && !textEGN.getText().isEmpty()) {
					System.out.println(textEGN.getText() + " <->  " + person);
					fl = OptionDialog("Не намирам служител с ЕГН:" + textEGN.getText(), "Грешно ЕГН");

				}
				if (fl) {
					GeneralMethods.setWaitCursor(infoPanel);

					textArea.setText("Търся в Базата");
					Measuring_Results_Frame.viewInfoPanel();
					System.out.println("searchFromDBase0");

					ProgressBar_Measuring_Results dd = new ProgressBar_Measuring_Results(textArea, infoPanel, tablePane,
							panel_AllSaerch, scrollPane, person, textFieldStartDate, textFieldEndDate, btnBackToTable,
							progressBar);

					dd.execute();

				} else {
					GeneralMethods.setDefaultCursor(infoPanel);
				}

			}
		});
	}

	static void setInfo(JTextArea textArea, JPanel infoPanel, JPanel tablePane, JLayeredPane panel_AllSaerch,
			JScrollPane scrollPane, JButton btnBackToTable, Object[][] dataTable, List<String> listNameNuclide) {
		if (dataTable != null) {
			panel_infoPanelTablePanel(textArea, panel_AllSaerch, tablePane, scrollPane, dataTable, listNameNuclide);
			Measuring_Results_Frame.viewTablePanel();
			btnBackToTable.setEnabled(false);
		}
	}

	public static Object[][] addListMeasur(List<Measuring> listMeasurWithDifLab, List<String> listNuclide) {

		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		tbl_Colum = 7 + listNuclide.size();
		Object[][] dataTable = new Object[listMeasurWithDifLab.size()][tbl_Colum];

//				"№", "Date", "EGN", "Name",	"Doze", "Nuclide"

		Person person;
		int k = 0;
		for (Measuring measur : listMeasurWithDifLab) {
			person = measur.getPerson();

			dataTable[k][0] = measur.getMeasuring_ID();
			dataTable[k][1] = (k + 1);
			dataTable[k][2] = sdfrmt.format(measur.getDate());
			dataTable[k][3] = person.getEgn();
			dataTable[k][4] = person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName();
			dataTable[k][5] = (Double) measur.getDoze();
			dataTable[k][6] = measur.getTypeMeasur().getNameType();
			List<ResultsWBC> listResult = ResultsWBCDAO.getValueResultsWBCByObject("Measuring_ID", measur);

			int j = 7;
			for (String nuclideName : listNuclide) {
				dataTable[k][j] = 0.0;
				for (ResultsWBC result : listResult) {
					if (result.getNuclideWBC().getSymbol_nuclide().equals(nuclideName)) {
						dataTable[k][j] = (Double) result.getGgp();
					}
				}
				j++;
			}
			k++;
		}
		System.out.println(dataTable.length);
		return dataTable;

	}

	static void panel_infoPanelTablePanel(JTextArea textArea, JLayeredPane panel_AllSaerch, JPanel tablePane,
			JScrollPane scrollPane, Object[][] dataTableIn, List<String> listNameNuclide) {

		@SuppressWarnings("rawtypes")
		Class[] types2 = getCulumnClass(listNameNuclide);
		dataTable = dataTableIn;
		JButton btnBackToTable = Measuring_Results_Frame.getBtnBackToTable();
		JButton btn_Export = Measuring_Results_Frame.getBtn_Export();
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

					System.out.println(table.getModel().getColumnClass(4).getSimpleName() + ":"
							+ model.getValueAt(getSelectedModelRow(table), 4).toString());

				}

				if (e.getClickCount() == 2 && table.getSelectedColumn() == egn_code_Colum) {

					GeneralMethods.setWaitCursor(tablePane);
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();
					System.out.println("egn " + reqCodeStr);
					btnBackToTable.setEnabled(true);
					btn_Export.setEnabled(false);
					Person person = PersonDAO.getValuePersonByEGN(reqCodeStr);
					textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson("", person, false, 0));

					Measuring_Results_Frame.viewInfoPanel();

					GeneralMethods.setDefaultCursor(tablePane);
				}

				if (e.getClickCount() == 2 && table.getSelectedColumn() == date_Measur_Colum) {

					GeneralMethods.setWaitCursor(tablePane);
					String MeasuringID = model.getValueAt(getSelectedModelRow(table), ID_Masur_Colum).toString();
					System.out.println("MeasuringID " + MeasuringID);
					Measuring measur = MeasuringDAO.getValueMeasuringByID(Integer.parseInt(MeasuringID));
					if (measur != null && operatorUser != null && operatorUser.get_isAdmin()) {
						btnBackToTable.setEnabled(true);
						btn_Export.setEnabled(false);
						String[] masiveData = getTextInfoPerson(measur);
						new Edit_Measuring_Results_Frame(new JFrame(), null, masiveData, measur);

					} else {
						GeneralMethods.setDefaultCursor(tablePane);
						if (measur == null ) {
						JOptionPane.showMessageDialog(new JFrame(), "Не намирам това измерване в базата.",
								"Г Р Е Ш К А", JOptionPane.PLAIN_MESSAGE, null);
						}else {
							JOptionPane.showMessageDialog(new JFrame(), "Нямате права за изменения",
									"В Н И М А Н И Е", JOptionPane.PLAIN_MESSAGE, null);	
						}

					}

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

					public boolean isCellEditable(int row, int column) {
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

				table.getColumnModel().getColumn(5).setCellRenderer(new DoubleCellRenderer());

				for (int i = 7; i < 7 + listNameNuclide.size(); i++) {
					table.getColumnModel().getColumn(i).setCellRenderer(new DoubleCellRenderer());
				}

				table.getColumnModel().getColumn(ID_Masur_Colum).setWidth(0);
				table.getColumnModel().getColumn(ID_Masur_Colum).setMinWidth(0);
				table.getColumnModel().getColumn(ID_Masur_Colum).setMaxWidth(0);
				table.getColumnModel().getColumn(ID_Masur_Colum).setPreferredWidth(0);

				initColumnSizes(table, dataTable);
				System.out.println("+++++++++++++ " + dataTable.length);

			}
		});

		table.getTableHeader().setReorderingAllowed(false);

		Measuring_Results_Frame.viewTablePanel();

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

		Class[] types = new Class[7 + listNameNuclide.size()];
		types[0] = Integer.class;
		types[1] = Integer.class;
		types[2] = String.class;
		types[3] = String.class;
		types[4] = String.class;
		types[5] = Double.class;
		types[6] = String.class;
		for (int i = 7; i < 7 + listNameNuclide.size(); i++) {
			types[i] = Double.class;
		}

		return types;
	}

//	"№", "Date", "EGN", "Name",	"Doze", "Nuclide"
	static String[] setTabHeader(List<String> listNameNuclide) {
		String[] tableHeader = new String[7 + listNameNuclide.size()];
		tableHeader[0] = " ID ";
		tableHeader[1] = " № ";
		tableHeader[2] = "Дата на изм.";
		tableHeader[3] = "ЕГН";
		tableHeader[4] = "Име";
		tableHeader[5] = "Доза";
		tableHeader[6] = "Тип";
		for (int i = 7; i < 7 + listNameNuclide.size(); i++) {
			tableHeader[i] = listNameNuclide.get(i - 7);
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
					PersonReferenceExportToExcell.btnExportTableToExcell(dataofTable, getTableHeader(table),
							button_Panel, "MeasuringReference");

				}
			}
		});
	}

	static void ActionListenerBtnBackToTable(JButton btnBackToTable, JTextArea textArea, JPanel tablePane,
			JLayeredPane panel_AllSaerch, JScrollPane scrollPane, JTextField textField_svePerson_Year) {
		JButton btn_Export = Measuring_Results_Frame.getBtn_Export();
		btnBackToTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dataTable != null) {
					GeneralMethods.setWaitCursor(tablePane);
					btnBackToTable.setEnabled(false);
					btn_Export.setEnabled(true);
					Measuring_Results_Frame.viewTablePanel();
					GeneralMethods.setDefaultCursor(tablePane);

				}
			}

		});

	}

	public static void ActionListenerChengePanelSizeByFrameSize(Measuring_Results_Frame measurInOderLabFrame,
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
		
		textFieldDate.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (AplicationMetods.incorrectDate(textFieldDate.getText())) {
					textFieldDate.setForeground(Color.RED);
					btn_SaveToExcelFile.setEnabled(false);
				} else {
					textFieldDate.setForeground(Color.BLACK);
					btn_SaveToExcelFile.setEnabled(true);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if (AplicationMetods.incorrectDate(textFieldDate.getText())) {
					textFieldDate.setForeground(Color.RED);
					btn_SaveToExcelFile.setEnabled(false);
				} else {
					textFieldDate.setForeground(Color.BLACK);
					btn_SaveToExcelFile.setEnabled(true);
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (AplicationMetods.incorrectDate(textFieldDate.getText())) {
					textFieldDate.setForeground(Color.RED);
					btn_SaveToExcelFile.setEnabled(false);
				} else {
					textFieldDate.setForeground(Color.BLACK);
					btn_SaveToExcelFile.setEnabled(true);
				}
			}
		});
		
	}

	public static boolean OptionDialog(String mesage, String textOptionDialogFrame) {

		String[] options = { "Назад", "Продължи" };
		JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);

		int x = JOptionPane.showOptionDialog(frame, mesage, textOptionDialogFrame, JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		System.out.println(x + " -----------------");
		if (x > 0) {
			return true;
		}
		return false;
	}

	public static List<String> ReadNuclideList() {
		List<List<List<String>>> lstData = DozeArt_Methods.ReadDataFromExcelFile();
		List<String> listNuclide = new ArrayList<>();
		for (List<List<String>> listNuclideData : lstData) {

			listNuclide.add(listNuclideData.get(0).get(0));

		}

		return listNuclide;

	}

	static String[][] readMasiveDataFromDozeArtFrame() {
		int countNuclide = Edit_Measuring_Results_Frame.getCountNuclide();
		String[][] masive = new String[countNuclide][5];

		for (int i = 0; i < countNuclide; i++) {

			masive[i][0] = Edit_Measuring_Results_Frame.getChoice_NuclideName()[i].getSelectedItem();
			masive[i][1] = Edit_Measuring_Results_Frame.getTextField_Activity()[i].getText();
			masive[i][2] = Edit_Measuring_Results_Frame.getTextField_Postaplenie()[i].getText();
			masive[i][3] = Edit_Measuring_Results_Frame.getLbl_GGPCalc()[i].getText();
			masive[i][4] = Edit_Measuring_Results_Frame.getLbl_DozaNuclide()[i].getText();

		}
		return masive;
	}

	public static void initColumnSizes(JTable table, Object[][] dataTable) {

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		TableColumn column = null;

		Component comp = null;
		int headerWidth = 0;
		int cellWidth = 0;
		Object[] longValues = getMaxLongColumn(dataTable);
		for (int i = 0; i < longValues.length; i++) {
			System.out.println(longValues[i]);
		}
		TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();

		for (int i = 0; i < table.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);

			comp = headerRenderer.getTableCellRendererComponent(null, column.getHeaderValue(), false, false, 0, 0);
			headerWidth = comp.getPreferredSize().width;

			comp = table.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(table, longValues[i],
					false, false, 0, i);
			cellWidth = comp.getPreferredSize().width;
			column.setPreferredWidth(Math.max(headerWidth, cellWidth));

		}
	}

	private static Object[] getMaxLongColumn(Object[][] masive) {
		System.out.println(masive[0].length);
		Object[] maxWith = new Object[7 + masive[0].length];
		for (int i = 0; i < 7 + masive[0].length; i++) {
			maxWith[i] = 5;
		}
		for (int i = 0; i < masive.length; i++) {
			for (int j = 0; j < masive[0].length; j++) {
				if (maxWith[j].toString().length() < masive[i][j].toString().length())
					maxWith[j] = masive[i][j];

			}
		}
		return maxWith;
	}

	public static String[] getTextInfoPerson(Measuring measur) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		DecimalFormat formatta = new DecimalFormat("#0.00");
		Person person = measur.getPerson();
		PersonStatusNew lastPersonstatusNew = PersonStatusNewDAO.getLastValuePersonStatusNewByPerson(person);
		List<ResultsWBC> listresult = ResultsWBCDAO.getValueResultsWBCByObject("Measuring_ID", measur);
		String[] masive = new String[6 + listresult.size()];

		String text = person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName() + "      ";
		text += lastPersonstatusNew.getWorkplace().getOtdel();
		masive[0] = text;
		masive[1] = sdf.format(measur.getDate());
		masive[2] = measur.getLab().getLab();
		masive[3] = measur.getTypeMeasur().getNameType();
		masive[4] = measur.getMeasurKoment();
		masive[5] = formatta.format(measur.getDoze()).replace(",", ".");

		List<ResultsWBC> listResult = ResultsWBCDAO.getValueResultsWBCByObject("Measuring_ID", measur);
		int index = 6;
		for (ResultsWBC resultsWBC : listResult) {
			System.out.println("resultsWBC.getNucl " + resultsWBC.getNuclideWBC().getSymbol_nuclide());
			masive[index] = resultsWBC.getNuclideWBC().getSymbol_nuclide() + "#";
			masive[index] += formatta.format(resultsWBC.getActivity()).replace(",", ".") + "#";
			masive[index] += formatta.format(resultsWBC.getPostaplenie()).replace(",", ".") + "#";
			masive[index] += formatta.format(resultsWBC.getGgp()).replace(",", ".") + "#";
			masive[index] += formatta.format(resultsWBC.getNuclideDoze()).replace(",", ".");

			System.out.println(index + " masive[index] " + masive[index]);
			index++;
		}
		return masive;
	}

	public static void btnSave_InsertNewMeasuting_ActionListener(JButton btnSave,
			Edit_Measuring_Results_Frame edit_Measuring_Results_Frame, Measuring measur) {
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Measuring_Results_Metods.OptionDialog("Исакте ли да добавите този запис?", "Добавяне на запис")) {

					ActionIcone round = new ActionIcone();
					final Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							if (PersonelManegementMethods.checkIsClosedPersonAndExternalFile()
									&& PersonelManegementMethods.checkIsClosedMonthPersonAndExternalFile()) {
								List<ReportMeasurClass> listReportMeasurClassToSave = generateListReportMeasurClassForSaveData(
										measur);
								AutoInsertMeasutingMethods.SaveMesuring(listReportMeasurClassToSave, round);

								edit_Measuring_Results_Frame.dispose();
							}
						}
					});
					thread.start();
				}
			}

		});

	}

	public static void btnDelete_Measuting_ActionListener(JButton btnDelete,
			Edit_Measuring_Results_Frame edit_Measuring_Results_Frame, Measuring measur) {
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Measuring_Results_Metods.OptionDialog("Исакте ли да изтриете този запис?", "Изтриване на запис")) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Изтритите данни няма да се отразят в екселските файлове.", "За Екселските файлове",
							JOptionPane.PLAIN_MESSAGE, null);
					ActionIcone round = new ActionIcone();
					final Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							if (PersonelManegementMethods.checkIsClosedPersonAndExternalFile()
									&& PersonelManegementMethods.checkIsClosedMonthPersonAndExternalFile()) {
								MeasuringDAO.deleteValueMeasuring(measur);
								for (ResultsWBC result : ResultsWBCDAO.getValueResultsWBCByObject("Measuring_ID",
										measur)) {
									ResultsWBCDAO.deleteValueResultsWBC(result.getResultsWBC_ID());
								}

								edit_Measuring_Results_Frame.dispose();
								round.StopWindow();
							}
						}
					});
					thread.start();
				}
			}

		});

	}

	public static void btnUpData_Measuting_ActionListener(JButton btnSave,
			Edit_Measuring_Results_Frame edit_Measuring_Results_Frame, Measuring measur) {
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Measuring_Results_Metods.OptionDialog("Исакте ли да промените този запис?", "Промяна на запис")) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Промените в данните няма да се отразят в екселските файлове.", "За Екселските файлове",
							JOptionPane.PLAIN_MESSAGE, null);

					ActionIcone round = new ActionIcone();
					final Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							if (PersonelManegementMethods.checkIsClosedPersonAndExternalFile()
									&& PersonelManegementMethods.checkIsClosedMonthPersonAndExternalFile()) {

								List<ReportMeasurClass> listReportMeasurClassToSave = generateListReportMeasurClassForSaveData(
										measur);

								System.out.println("listReportMeasurClassToSave.size() "+listReportMeasurClassToSave.size());
								MeasuringDAO.updateValueMeasuring(listReportMeasurClassToSave.get(0).getMeasur());
								deleteResultsWBCInMeasur(measur);
								UpDataListReportMeasurClassToDBase(round,
										listReportMeasurClassToSave, measur);

								edit_Measuring_Results_Frame.dispose();
							}
						}
					});
					thread.start();
				}
			}

		});

	}

	public static void UpDataListReportMeasurClassToDBase(ActionIcone round,
			List<ReportMeasurClass> listReportMeasurClassToSave, Measuring measur) {
		
		for (ReportMeasurClass reportMeasur : listReportMeasurClassToSave) {

			MeasuringDAO.setObjectMeasuringToTable(reportMeasur.getMeasur());
		
			if (!reportMeasur.getListNuclideData().isEmpty()) {
				for (String stringNuclideData : reportMeasur.getListNuclideData()) {

					stringNuclideData = stringNuclideData.replaceAll("##", "");
					String[] masiveStrNuclide = stringNuclideData.split(":");

					NuclideWBC nuclide = NuclideWBCDAO.getValueNuclideWBCByObject("Symbol", masiveStrNuclide[1].trim())
							.get(0);
					double actyviti = Double.parseDouble(masiveStrNuclide[2].replaceAll(",", "."));
					double postaplenie = Double
							.parseDouble(masiveStrNuclide[3].replaceAll(",", ".").replaceAll(" ", ""));
					double ggp = Double.parseDouble(masiveStrNuclide[4].replaceAll(",", "."));
					double nuclideDoze = Double.parseDouble(masiveStrNuclide[5].replaceAll(",", "."));

					ResultsWBCDAO.setValueResultsWBC(measur, nuclide, actyviti, postaplenie, ggp, nuclideDoze);

				}

			}

		}
		round.StopWindow();
	}
	
	
	
	
	public static void deleteResultsWBCInMeasur(Measuring measur) {
		List<ResultsWBC> listResult = ResultsWBCDAO.getValueResultsWBCByObject("Measuring_ID", measur);
		for (ResultsWBC resultsWBC : listResult) {
			ResultsWBCDAO.deleteValueResultsWBC(resultsWBC.getResultsWBC_ID());
		}
	}

	public static List<ReportMeasurClass> generateListReportMeasurClassForSaveData(Measuring measur) {
		List<ReportMeasurClass> listReportMeasurToData = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		ReportMeasurClass reportMeasur = new ReportMeasurClass();
		Measuring NEWmeasur = new Measuring();

		try {
			NEWmeasur.setDate(sdf.parse(Edit_Measuring_Results_Frame.getLbl_Date_Measur().getText()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		NEWmeasur.setPerson(measur.getPerson());
		NEWmeasur.setDoze(Double.valueOf(Edit_Measuring_Results_Frame.getLbl_DozeAll().getText()));
		NEWmeasur.setDoseDimension(measur.getDoseDimension());
		NEWmeasur.setLab(LaboratoryDAO
				.getValueLaboratoryByID(Edit_Measuring_Results_Frame.getChoice_Lab().getSelectedIndex() + 1));

		NEWmeasur.setUser(operatorUser );

		NEWmeasur.setTypeMeasur(TypeMeasurDAO
				.getValueTypeMeasurByObject("NameType", Edit_Measuring_Results_Frame.getChoice_Type().getSelectedItem())
				.get(0));
		NEWmeasur.setMeasurKoment(Edit_Measuring_Results_Frame.getTextKoment().getText());
		NEWmeasur.setReportFileName(measur.getReportFileName());
		NEWmeasur.setExcelPosition(measur.getExcelPosition());

		NEWmeasur.setMeasuring_ID(measur.getMeasuring_ID());
		List<String> listString = new ArrayList<>();
		String DataNuclide = "";
		int countNuclide = Edit_Measuring_Results_Frame.getCountNuclide();
		for (int i = 0; i < countNuclide; i++) {
			if (!Edit_Measuring_Results_Frame.getLbl_DozaNuclide()[i].getText().isEmpty()) {
				DataNuclide = DataNuclide + ": "
						+ Edit_Measuring_Results_Frame.getChoice_NuclideName()[i].getSelectedItem().toString();
				DataNuclide = DataNuclide + ": "
						+ ifEmptyToNull(Edit_Measuring_Results_Frame.getTextField_Activity()[i].getText());
				DataNuclide = DataNuclide + ": "
						+ ifEmptyToNull(Edit_Measuring_Results_Frame.getTextField_Postaplenie()[i].getText());
				DataNuclide = DataNuclide + ": "
						+ ifEmptyToNull(Edit_Measuring_Results_Frame.getLbl_GGPCalc()[i].getText());
				DataNuclide = DataNuclide + ": "
						+ ifEmptyToNull(Edit_Measuring_Results_Frame.getLbl_DozaNuclide()[i].getText());

			}

			if (DataNuclide.trim().length() > 0) {
				DataNuclide = "## " + DataNuclide;
				listString.add(DataNuclide);
				DataNuclide = "";
			}
		}
		reportMeasur.setMeasur(NEWmeasur);
		reportMeasur.setListNuclideData(listString);
		reportMeasur.setToExcell(Edit_Measuring_Results_Frame.getChckbxNewCheckBox().isSelected());
		reportMeasur.setKoment(Edit_Measuring_Results_Frame.getTextKoment().getText());
		reportMeasur.setReportFile(null);
		listReportMeasurToData.add(reportMeasur);

		return listReportMeasurToData;

	}

	private static String ifEmptyToNull(String text) {
		if (text.trim().length() > 0) {
			return text;
		}
		return "0";
	}

}
