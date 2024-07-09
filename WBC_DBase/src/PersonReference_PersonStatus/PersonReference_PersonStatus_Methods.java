package PersonReference_PersonStatus;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import DatePicker.DatePicker;
import PersonReference.PersonReferenceExportToExcell;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class PersonReference_PersonStatus_Methods {

	static Object[][] dataTable;
	static List<String> listOtdelAll;
	static List<String> listOtdelKz;
	static List<String> listOtdelVO;

	static List<PersonStatusNew> listPersonStat;
	static PersonStatusNew selectPersonStat = null;
	static int selectRow = -1;

	static boolean multytextInTextArea;
	static String heatherDozeYearNaw;
	static String heatherDozeYearNaw_1;
	static String heatherDozeYearNaw_2;
	static String heatherDozeYearNaw_3;
	static String heatherDozeYearNaw_4;
	static String otgovRZ;

	static JTable table;
	
	static UsersWBC operatorUser;
	private static List<Integer> listChangedPersonStatId;

	private static List<Workplace> listWorkplace;

	private static ProgressBarWorkerPersonReference pbwpr;

	public static void ActionListener_Btn_StartGenerateTable_PersonStatus(JButton btn_StartGenerateTable,
			JPanel panel_AllSaerch, JPanel tablePane, JScrollPane scrollPane, UsersWBC user, JCheckBox chckbx_Editing,
			JProgressBar progressBar) {

		btn_StartGenerateTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PersonReference_PersonStatus_Frame.viewInfoPanel();
				clearTextFields();
				GeneralMethods.setWaitCursor(tablePane);

				JTextField textField_Year = PersonReference_PersonStatus_Frame.getTextField_Year();

				pbwpr = new ProgressBarWorkerPersonReference(textField_Year, progressBar, panel_AllSaerch, tablePane,
						scrollPane, chckbx_Editing);
				pbwpr.execute();

				GeneralMethods.setDefaultCursor(panel_AllSaerch);

			}

//					

		});

	}

	public static void ActionListener_Btn_Add_PersonStatus(JButton btn_Add, JPanel panel_AllSaerch, JPanel tablePane,
			JScrollPane sp, UsersWBC user) {

		btn_Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PersonStatusNew newPersonStat = createPersonalStatusByTextField(tablePane, user);

				if (newPersonStat != null) {
					JCheckBox checkBoxEditing = PersonReference_PersonStatus_Frame.getChckbx_Editing();
					String text = "Ще добавите ли този запис в базата?";
					if (OptionDialog(text)) {
						GeneralMethods.setWaitCursor(tablePane);
						if (PersonStatusNewDAO.setObjectPersonStatusNewToTable(newPersonStat)) {
							dataTable = addNewRowInTable(newPersonStat);
							PersonReference_PersonStatus_Methods.setDataTable(dataTable);
							PersonReference_PersonStatus_Methods.panel_infoPanelTablePanel(panel_AllSaerch, tablePane,
									sp, checkBoxEditing);
							PersonReference_PersonStatus_Frame.viewTablePanel();
							clearTextFields();
						} else {
							String str = "Такъв запис съществува в базата";
							JOptionPane.showMessageDialog(null, str, "Info", JOptionPane.INFORMATION_MESSAGE);
						}
						GeneralMethods.setDefaultCursor(panel_AllSaerch);
					}
				}

			}

		});

	}

	public static void ActionListener_Btn_Editing_PersonStatus(JButton btn_Edit, JPanel panel_AllSaerch,
			JPanel tablePane, JScrollPane sp, UsersWBC user) {

		btn_Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PersonStatusNew newPersonStat = createPersonalStatusByTextField(tablePane, user);

				if (newPersonStat != null) {
					String text = "Ще промените ли този запис в базата?";
					if (OptionDialog(text)) {
						GeneralMethods.setWaitCursor(tablePane);

						JCheckBox checkBoxEditing = PersonReference_PersonStatus_Frame.getChckbx_Editing();
						newPersonStat.setPersonStatusNew_ID(selectPersonStat.getPersonStatusNew_ID());
						if (PersonStatusNewDAO.updateValuePersonStatusNew(newPersonStat)) {
							dataTable = editSelectionRowInTable(newPersonStat);
							PersonReference_PersonStatus_Methods.setDataTable(dataTable);
							PersonReference_PersonStatus_Methods.panel_infoPanelTablePanel(panel_AllSaerch, tablePane,
									sp, checkBoxEditing);
							PersonReference_PersonStatus_Frame.viewTablePanel();
							clearTextFields();
						} else {
							String str = "Такъв запис съществува в базата";
							JOptionPane.showMessageDialog(null, str, "Info", JOptionPane.INFORMATION_MESSAGE);
						}
						GeneralMethods.setDefaultCursor(panel_AllSaerch);
					}

				}

			}
		});

	}

	public static void ActionListener_Btn_Delete_PersonStatus(JButton btn_Delete, JPanel panel_AllSaerch,
			JPanel tablePane, JScrollPane sp, UsersWBC user) {

		btn_Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonStatusNew newPersonStat = createPersonalStatusByTextField(tablePane, user);

				if (newPersonStat != null) {
					String text = "Ще изтриете ли този запис от базата?";
					if (OptionDialog(text)) {
						GeneralMethods.setWaitCursor(tablePane);

						JCheckBox checkBoxEditing = PersonReference_PersonStatus_Frame.getChckbx_Editing();
						if (PersonStatusNewDAO.deleteValuePersonStatusNew(selectPersonStat)) {
							dataTable = deleteSelectedRowInTable(selectRow);
							PersonReference_PersonStatus_Methods.setDataTable(dataTable);
							PersonReference_PersonStatus_Methods.panel_infoPanelTablePanel(panel_AllSaerch, tablePane,
									sp, checkBoxEditing);
							PersonReference_PersonStatus_Frame.viewTablePanel();
							clearTextFields();
						} else {
							String str = "Този запис не беше изтрит";
							JOptionPane.showMessageDialog(null, str, "Info", JOptionPane.INFORMATION_MESSAGE);
						}
						GeneralMethods.setDefaultCursor(panel_AllSaerch);
					}
				}

			}
		});

	}

	static void ActionListenerSetDateByDatePicker(JLabel lbl_Icon_StartDate2, JTextField textField_StartDate2) {

		lbl_Icon_StartDate2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e1) {
//				SimpleDateFormat sdf_New = new SimpleDateFormat("dd.MM.yyyy");

				Point pointFrame = lbl_Icon_StartDate2.getLocationOnScreen();
				final JFrame f = new JFrame();
				DatePicker dPicer = new DatePicker(f, false, textField_StartDate2.getText(), pointFrame);
				String str = dPicer.setPickedDate(false);

				textField_StartDate2.setText(str);
				checkCorectdate(textField_StartDate2);
			}
		});

	}

	static void ActionListenerCheckCorectDate(JTextField textField_StartDate2) {

		textField_StartDate2.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {

				checkCorectdate(textField_StartDate2);
			}

		});

	}

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
						if (number < minYeare || number > Calendar.getInstance().get(Calendar.YEAR)) {
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

	public static void ActionListenertextField_Year(JTextField textField_Import_Year) {
		textField_Import_Year.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {
				int minYeare = 0;
				boolean fl = false;
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
				fl = true;
				if (!textField_Import_Year.getText().isEmpty()) {
					try {
						long number = Long.parseLong(textField_Import_Year.getText());
						if (number < minYeare || number > Calendar.getInstance().get(Calendar.YEAR)) {
							fl = false;
						}
					} catch (Exception e) {
						fl = false;
					}
				}

				editButtonsByCorectTextField(textField_Import_Year, fl);
			}
		});
	}

	public static void ActionListenerGetPersonByEGN(JTextField textField_EGN, JPanel panel) {

		textField_EGN.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {
				Person person = null;
				String name = "";
				boolean fl = false;
				String EGN = textField_EGN.getText();
				System.out.println(EGN);
				if (!EGN.isEmpty() && EGN.length() > 5) {
					GeneralMethods.setWaitCursor(panel);
					person = PersonDAO.getValuePersonByEGN(EGN);
					if (person != null) {
						name = person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName();
						System.out.println(name);
						PersonReference_PersonStatus_Frame.getLbl_Input_Name().setText(name);
						fl = true;
					} else {
						PersonReference_PersonStatus_Frame.getLbl_Input_Name().setText("");
						fl = false;
					}
				}
				GeneralMethods.setDefaultCursor(panel);
				editButtonsByCorectTextField(textField_EGN, fl);
			}
		});

	}

	public static void ActionListenerIsSelectOtdel(Choice choice_Otdel) {
		choice_Otdel.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JTextField textField_StartDate2 = new JTextField();
				boolean fl = true;
				choice_Otdel.setBackground(Color.WHITE);
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (choice_Otdel.getSelectedItem().isEmpty()) {
						choice_Otdel.setBackground(Color.RED);
						fl = false;
					}
				}
				editButtonsByCorectTextField(textField_StartDate2, fl);
			}
		});

	}

	public static void ActionListener_Btn_ExportToExcel(JButton btn_Export, JPanel tablePane) {

		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Object[][] volue = extractedVolumeTable(table);
				String[] columName = getExcelTabHeader();

				PersonReferenceExportToExcell.btnExportTableToExcell(volue, columName, tablePane);

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

	@SuppressWarnings("rawtypes")
	static List<PersonStatusNew> selectListPersonStatByWorkplace(List<PersonStatusNew> listAllPersonStat,
			List<Workplace> listWork) {
		List<PersonStatusNew> listPersonStatByWork = new ArrayList<>();
		for (Iterator iterator = listAllPersonStat.iterator(); iterator.hasNext();) {
			PersonStatusNew perStat = (PersonStatusNew) iterator.next();
			for (Workplace work : listWork) {
				if (work.getId_Workplace() == perStat.getWorkplace().getId_Workplace()) {
					listPersonStatByWork.add(perStat);
					iterator.remove();
				}
			}
		}

		return listPersonStatByWork;
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
					int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());

					if (e.getClickCount() == 2 && selectedRow != -1) {

						if (chckbx_Editing.isSelected()) {

							insertDataInTextFields(selectedRow);
							selectRow = selectedRow;

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
							AddInUpdateList(row);
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
			PersonReference_PersonStatus_Frame.getBtn_Export().setEnabled(true);
		}

	}

	protected static void insertDataInTextFields(int k) {
		JTextField textField_EGN = PersonReference_PersonStatus_Frame.getTextField_EGN();
		JTextField textField_StartDate = PersonReference_PersonStatus_Frame.getTextField_StartDate();
		JTextField textField_EndDate = PersonReference_PersonStatus_Frame.getTextField_EndDate();
		JTextField textField_Import_Year = PersonReference_PersonStatus_Frame.getTextField_Import_Year();
		JTextField textField_Dokument = PersonReference_PersonStatus_Frame.getTextField_Dokument();
		JTextField textField_Zabelejka = PersonReference_PersonStatus_Frame.getTextField_Zabelejka();
		Choice choice_Otdel = PersonReference_PersonStatus_Frame.getChoice_Otdel();
		JLabel lbl_Input_Name = PersonReference_PersonStatus_Frame.getLbl_Input_Name();

		SimpleDateFormat sdf_New = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat sdf_Old = new SimpleDateFormat("yyyy-MM-dd");

		textField_EGN.setText((String) dataTable[k][1]);
		lbl_Input_Name.setText((String) dataTable[k][2]);
		choice_Otdel.select((String) dataTable[k][3]);
		textField_Dokument.setText((String) dataTable[k][4]);
		textField_StartDate.setText(RefornmatFormatStringData(sdf_New, sdf_Old, (String) dataTable[k][5]));
		textField_EndDate.setText(RefornmatFormatStringData(sdf_New, sdf_Old, (String) dataTable[k][6]));
		textField_Import_Year.setText((String) dataTable[k][7]);
		textField_Zabelejka.setText((String) dataTable[k][10]);

		selectPersonStat = listPersonStat.get(k);
		System.out.println(selectPersonStat.getPerson().getEgn() + " EGN");

		editButtonsByCorectTextField(textField_EGN, true);
		editButtonsByCorectTextField(textField_StartDate, true);
		editButtonsByCorectTextField(textField_EndDate, true);
		editButtonsByCorectTextField(textField_Import_Year, true);

//		dataTable[k][8] = personStat.getUserWBC().getLastName();
//		dataTable[k][9] = sdf.format(personStat.getDateSet());

	}

	private static String RefornmatFormatStringData(SimpleDateFormat sdf_New, SimpleDateFormat sdf_Old,
			String sorseDate) {
		String newDate = "";
		try {
			Date datte = sdf_Old.parse(sorseDate);
			newDate = sdf_New.format(datte);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	static String[] getTabHeader() {
		String[] tableHeader = { "<html><center>№<br>_</center></html>", "<html><center>ЕГН<br>_</center></html>",
				"<html><center>Име<br>_</center></html>", "<html><center>Отдел<br>_</center></html>",
				"<html><center>Документ<br>_</center></html>", "<html><center>Начална<br>Дата</center></html>",
				"<html><center>Крайна<br>Дата</center></html>", "<html><center>Година<br>_</center></html>",
				"<html><center>Въвел<br>_</center></html>", "<html><center>Дата на<br>Въвеждане</center></html>",
				"<html><center>Коментар<br>_</center></html>" };
		return tableHeader;
	}

	static String[] getExcelTabHeader() {
		String[] tableHeader = { "№", "ЕГН", "Име", "Отдел", "Документ", "Начална Дата", "Крайна Дата", "Година",
				"Въвел", "Дата на Въвеждане", "Коментар" };
		return tableHeader;
	}

	@SuppressWarnings("rawtypes")
	public static Class[] getCulumnClass() {
		Class[] types = { Integer.class, String.class, String.class, String.class, String.class, String.class,
				String.class, String.class, String.class, String.class, String.class };
		return types;
	}

	public static int[] getCulumnSize() {
		int[] col = { 25, 65, 180, 180, 65, 60, 60, 50, 125, 60, 180 };
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

	protected static Object[][] generateMasiveSelectionPerson(List<PersonStatusNew> listSelectionPerson) {

		Object[][] dataTable = new Object[listSelectionPerson.size()][11];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int k = 0;
		for (PersonStatusNew personStat : listSelectionPerson) {
			dataTable[k][0] = (k + 1);
			dataTable[k][1] = personStat.getPerson().getEgn();
			dataTable[k][2] = personStat.getPerson().getFirstName() + " " + personStat.getPerson().getSecondName() + " "
					+ personStat.getPerson().getLastName();
			dataTable[k][3] = personStat.getWorkplace().getOtdel();
			dataTable[k][4] = personStat.getFormulyarName();
			dataTable[k][5] = sdf.format(personStat.getStartDate());
			dataTable[k][6] = sdf.format(personStat.getEndDate());
			dataTable[k][7] = personStat.getYear();
			dataTable[k][8] = personStat.getUserWBC().getLastName();
			dataTable[k][9] = sdf.format(personStat.getDateSet());
			dataTable[k][10] = personStat.getZabelejka();

			k++;
		}

		return dataTable;

	}

	public static boolean isEditableTable() {
		JCheckBox chckbx_Editing = PersonReference_PersonStatus_Frame.getChckbx_Editing();

		return chckbx_Editing.isSelected() && operatorUser.get_isAdmin();
	}

	public static Boolean incorrectDate(String date, Boolean inTime) {
		Boolean corDate = false;
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		try {
			LocalDate.parse(date, sdf);

		} catch (DateTimeParseException e) {
			return corDate = true;
		}
		return corDate;
	}

	private static void AddInUpdateList(int row) {

		if (listChangedPersonStatId.isEmpty()) {
			listChangedPersonStatId.add(row);
		} else {
			if (!listChangedPersonStatId.contains(row)) {
				listChangedPersonStatId.add(row);
			}
		}
		System.out.println(row);
	}

	public static void setUp_Otdel_Column(JTable table, TableColumn Otel_Column) {

		String[] list = new String[listWorkplace.size()];
		int k = 0;
		for (Workplace work : listWorkplace) {
			list[k] = work.getOtdel();
			k++;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox comboBox = new JComboBox(list);
		Otel_Column.setCellEditor(new DefaultCellEditor(comboBox));
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Натисни за избор");
		Otel_Column.setCellRenderer(renderer);
	}

	private static void checkCorectdate(JTextField textField_StartDate2) {
		boolean fl = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		if (DatePicker.correctDate(textField_StartDate2.getText(), sdf) && checkDateFieldsStartBeForeEnd()) {
			fl = true;
		}

		editButtonsByCorectTextField(textField_StartDate2, fl);
	}

	private static boolean checkDateFieldsStartBeForeEnd() {
		JTextField textField_StartDate = PersonReference_PersonStatus_Frame.getTextField_StartDate();
		JTextField textField_EndDate = PersonReference_PersonStatus_Frame.getTextField_EndDate();
		if (!textField_StartDate.getText().trim().isEmpty() && textField_EndDate.getText().trim().isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			Date datSt = null;
			Date datEn = null;
			try {
				datEn = sdf.parse(textField_EndDate.getText());
				datSt = sdf.parse(textField_StartDate.getText());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return datSt.before(datEn);
		} else {
			return true;
		}
	}

	public static Object[][] getDataTable() {
		return dataTable;
	}

	public static void setDataTable(Object[][] dataTable1) {
		dataTable = dataTable1;
	}

	public static List<PersonStatusNew> getListPersonStat() {
		return listPersonStat;
	}

	public static void setListPersonStat(List<PersonStatusNew> listPersonStat1) {
		listPersonStat = listPersonStat1;
	}

	protected static Object[][] deleteSelectedRowInTable(int selectedRow) {
		Object[][] newDataTable;
		if (selectedRow > 0) {
			newDataTable = new Object[dataTable.length - 1][11];
			int k = 0;
			for (int i = 0; i < dataTable.length; i++) {
				if (i == selectedRow) {
					i++;
				}
				newDataTable[k] = dataTable[i];
				k++;
			}
		} else {
			newDataTable = dataTable;
		}

		return newDataTable;
	}

	protected static Object[][] editSelectionRowInTable(PersonStatusNew newPersonStat) {
		Object[][] newDataTable = new Object[dataTable.length][11];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		newDataTable = dataTable;
		if (selectRow > 0) {
			int k = selectRow;

			newDataTable[k][1] = newPersonStat.getPerson().getEgn();
			newDataTable[k][2] = newPersonStat.getPerson().getFirstName() + " "
					+ newPersonStat.getPerson().getSecondName() + " " + newPersonStat.getPerson().getLastName();
			newDataTable[k][3] = newPersonStat.getWorkplace().getOtdel();
			newDataTable[k][4] = newPersonStat.getFormulyarName();
			newDataTable[k][5] = sdf.format(newPersonStat.getStartDate());
			newDataTable[k][6] = sdf.format(newPersonStat.getEndDate());
			newDataTable[k][7] = newPersonStat.getYear();
			newDataTable[k][8] = newPersonStat.getUserWBC().getLastName();
			newDataTable[k][9] = sdf.format(newPersonStat.getDateSet());
			newDataTable[k][10] = newPersonStat.getZabelejka();
		}
		return newDataTable;
	}

	public static PersonStatusNew createPersonalStatusByTextField(JPanel tablePane, UsersWBC user) {

		GeneralMethods.setWaitCursor(tablePane);
		SimpleDateFormat sdf_New = new SimpleDateFormat("dd.MM.yyyy");
		Person person = null;
		Date dateStart = null;
		Date dateEnd = null;
		PersonStatusNew newPersonStat = null;
		Date curentDate = Calendar.getInstance().getTime();
		String EGN = PersonReference_PersonStatus_Frame.getTextField_EGN().getText().trim();
		String startDate = PersonReference_PersonStatus_Frame.getTextField_StartDate().getText().trim();
		String endDate = PersonReference_PersonStatus_Frame.getTextField_EndDate().getText().trim();
		String import_Year = PersonReference_PersonStatus_Frame.getTextField_Import_Year().getText().trim();
		String dokument = PersonReference_PersonStatus_Frame.getTextField_Dokument().getText().trim();
		String otdel = PersonReference_PersonStatus_Frame.getChoice_Otdel().getSelectedItem();
		String zabelejka = PersonReference_PersonStatus_Frame.getTextField_Zabelejka().getText().trim();

		String errorText = "";

		if (!EGN.isEmpty()) {
			person = PersonDAO.getValuePersonByEGN(EGN);
		} else {
			errorText += "Не сте въвели Служител\n";
		}

		if (otdel.isEmpty()) {
			errorText += "Не сте въвели Отдел\n";
		}

		if (import_Year.isEmpty()) {
			errorText += "Не сте въвели Година\n";
		}

		if (dokument.isEmpty()) {
			errorText += "Не сте въвели Име на документа\n";
		}
		try {
			if (!startDate.isEmpty()) {

				dateStart = sdf_New.parse(startDate);

			} else {
				errorText += "Не сте въвели Начална дата\n";
			}

			if (!endDate.isEmpty()) {
				dateEnd = sdf_New.parse(endDate);
			} else {
				errorText += "Не сте въвели Крайна дата\n";
			}
			curentDate = sdf_New.parse(sdf_New.format(curentDate));

		} catch (ParseException e1) {

			e1.printStackTrace();
		}

		if (errorText.isEmpty()) {
			newPersonStat = new PersonStatusNew();
			newPersonStat.setPerson(person);
			newPersonStat.setWorkplace(WorkplaceDAO.getActualValueWorkplaceByOtdel(otdel));
			newPersonStat.setFormulyarName(dokument);
			newPersonStat.setStartDate(dateStart);
			newPersonStat.setEndDate(dateEnd);
			newPersonStat.setYear(import_Year);
			newPersonStat.setUserWBC(user);
			newPersonStat.setDateSet(curentDate);
			newPersonStat.setZabelejka(zabelejka);
		} else {
			JOptionPane.showMessageDialog(null, errorText, "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		GeneralMethods.setDefaultCursor(tablePane);
		return newPersonStat;
	}

	protected static Object[][] addNewRowInTable(PersonStatusNew newPersonStat) {
		Object[][] newDataTable = new Object[dataTable.length + 1][11];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < dataTable.length; i++) {
			newDataTable[i] = dataTable[i];
		}
		int k = dataTable.length;
		newDataTable[k][0] = (k + 1);
		newDataTable[k][1] = newPersonStat.getPerson().getEgn();
		newDataTable[k][2] = newPersonStat.getPerson().getFirstName() + " " + newPersonStat.getPerson().getSecondName()
				+ " " + newPersonStat.getPerson().getLastName();
		newDataTable[k][3] = newPersonStat.getWorkplace().getOtdel();
		newDataTable[k][4] = newPersonStat.getFormulyarName();
		newDataTable[k][5] = sdf.format(newPersonStat.getStartDate());
		newDataTable[k][6] = sdf.format(newPersonStat.getEndDate());
		newDataTable[k][7] = newPersonStat.getYear();
		newDataTable[k][8] = newPersonStat.getUserWBC().getLastName();
		newDataTable[k][9] = sdf.format(newPersonStat.getDateSet());
		newDataTable[k][10] = newPersonStat.getZabelejka();

		return newDataTable;

	}

	protected static void clearTextFields() {
		PersonReference_PersonStatus_Frame.getTextField_EGN().setText("");
		PersonReference_PersonStatus_Frame.getTextField_StartDate().setText("");
		PersonReference_PersonStatus_Frame.getTextField_EndDate().setText("");
		PersonReference_PersonStatus_Frame.getTextField_Import_Year().setText("");
		PersonReference_PersonStatus_Frame.getTextField_Dokument().setText("");
		PersonReference_PersonStatus_Frame.getTextField_Zabelejka().setText("");
		PersonReference_PersonStatus_Frame.getChoice_Otdel().select(0);
		PersonReference_PersonStatus_Frame.getLbl_Input_Name().setText("");

		selectPersonStat = null;
		selectRow = -1;
	}

	private static void editButtonsByCorectTextField(JTextField textField_StartDate2, boolean isCorectTextField) {
		JButton btn_Edit = PersonReference_PersonStatus_Frame.getBtn_Edit();
		JButton btn_Delete = PersonReference_PersonStatus_Frame.getBtn_Delete();
		JButton btn_Add = PersonReference_PersonStatus_Frame.getBtn_Add();
		if (isCorectTextField) {
			textField_StartDate2.setForeground(Color.BLACK);
			btn_Edit.setEnabled(true);
			btn_Delete.setEnabled(true);
			btn_Add.setEnabled(true);
		} else {
			textField_StartDate2.setForeground(Color.RED);
			btn_Edit.setEnabled(false);
			btn_Delete.setEnabled(false);
			btn_Add.setEnabled(false);
		}
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

}
