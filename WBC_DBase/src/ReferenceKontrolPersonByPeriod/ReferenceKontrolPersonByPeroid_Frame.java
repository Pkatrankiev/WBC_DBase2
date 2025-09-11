package ReferenceKontrolPersonByPeriod;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadPersonFromExcelFile;
import Aplication.RemouveDublikateFromList;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.Workplace;
import DeleteDataFromDBaseRemoveInCurenFromOldYear.DeleteDataFromDBaseRemoveInCurenFromOldYear_Frame;
import InsertMeasuting.SaveReportMeasurTo_PersonelORExternalExcelFile;
import PersonManagement.PersonelManegementMethods;
import PersonReference.PersonExcellClass;
import PersonReference.PersonReferenceExportToExcell;
import PersonReference.SearchFromExcellFiles;
import PersonReference.TextInAreaTextPanel;
import PersonReference_Dokument.PersonReference_Dokument_Frame;
import PersonReference_Dokument.SearchInExcellFilesByDokument;
import Reference_PersonMeasur.Reference_PersonMeasur_Frame;
import Reference_PersonMeasur.Reference_PersonMeasur_Metods;
import Reference_PersonMeasur.TextInAreaTextPanel_Reference_PersonMeasur;
import UpdateDBaseFromExcelFiles.UpdateBDataFromExcellFiles;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import javax.swing.JProgressBar;

public class ReferenceKontrolPersonByPeroid_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JPanel panel_AllSaerch;
	private static JPanel panel_Search;
	private static JPanel infoPanel;
	private static JPanel tablePane;
	private static JScrollPane scrollPane;
	
	private static Choice comboBox_Firm;
	private static Choice comboBox_Period;

	private static JTextArea textArea;
	private static JTextField txtStartDate;
	private static JTextField txtEndDate;

	private static JButton btn_SearchDBase;
	private static JButton btn_SearchFromExcel;
	private static JButton btn_Export;

	List<String> listOtdelKz;
	List<String> listOtdelVO;

//	List<String> listOtdelAll;
	static List<String> listAddPOtdel;
	List<String> listFirm;
	static Object[][] dataTable;
	
	static int curentYear = Calendar.getInstance().get(Calendar.YEAR);

	private static String referencePersonMeasur_startDate = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePersonMeasur_startDate");
	private static String labelReferenceKontrolPerson_Peroid = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("labelReferenceKontrolPerson_Peroid");
	private static String referencePersonMeasur_endDate = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePersonMeasur_endDate");
	private static String referencePersonMeasur_firm = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePersonMeasur_firm");
	private static String referencePersonMeasur_KodeZone = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePersonMeasur_KodeZone");
	
	private JProgressBar progressBar;
	private JLabel lbl_KodeZone;
	private static Choice comboBox_KodeZone;
	

	public ReferenceKontrolPersonByPeroid_Frame(ActionIcone round, String title) {
		setTitle(title);
		setMinimumSize(new Dimension(780, 900));

		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));

		ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults");
		String AEC = ReadFileBGTextVariable.getGlobalTextVariableMap().get("AEC");
		String VO = ReadFileBGTextVariable.getGlobalTextVariableMap().get("VO");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panel_Search = new JPanel();
		panel_Search.setLayout(new BoxLayout(panel_Search, BoxLayout.Y_AXIS));
		contentPane.add(panel_Search, BorderLayout.NORTH);

		panel_AllSaerch = new JPanel();
		contentPane.add(panel_AllSaerch, BorderLayout.CENTER);
		panel_AllSaerch.setLayout(new BoxLayout(panel_AllSaerch, BoxLayout.Y_AXIS));

		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(10, 10));
		infoPanel.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(infoPanel);
		infoPanel.setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		JScrollPane sp = new JScrollPane(textArea);
		infoPanel.add(sp, BorderLayout.CENTER);

		tablePane = new JPanel();
		tablePane.setPreferredSize(new Dimension(10, 0));
		tablePane.setMaximumSize(new Dimension(32767, 0));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		tablePane.add(scrollPane, BorderLayout.CENTER);

		listOtdelKz = getListStringOtdel(WorkplaceDAO.getValueWorkplaceByObject("FirmName", AEC));
		Collections.sort(listOtdelKz);

		listOtdelVO = getListStringOtdel(WorkplaceDAO.getValueWorkplaceByObject("FirmName", VO));
		Collections.sort(listOtdelVO);

//		listOtdelAll = getListStringOtdel(WorkplaceDAO.getAllValueWorkplace());
//		listOtdelAll.add("");
//		Collections.sort(listOtdelAll);

		listAddPOtdel = new ArrayList<>();
		listFirm = new ArrayList<>();
		listFirm.add("");
		listFirm.add(AEC);
		listFirm.add(VO);
		panel_2();
		panel_2A();
		
		progressBar = new JProgressBar(0,100);
		 progressBar.setValue(0);
	     progressBar.setStringPainted(true);
		panel_Search.add(progressBar);
		
		getRootPane().setDefaultButton(btn_SearchDBase);

		panel_Button();

		PersonelManegementMethods.ActionListenerSetDateByDatePicker(txtStartDate, btn_SearchDBase);
		PersonelManegementMethods.ActionListenerSetDateByDatePicker(txtEndDate, btn_SearchDBase);
		
		
		setSize(780, 900);
		setLocationRelativeTo(null);
		setVisible(true);
		GeneralMethods.setDefaultCursor(panel_AllSaerch);
		round.StopWindow();
	}

	private JPanel panel_2() {

		String referencePerson_SearchFromDBase = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("referencePerson_SearchFromDBase");

		JPanel panel2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(2);
		panel_Search.add(panel2);

		JLabel lbl_Period = new JLabel(labelReferenceKontrolPerson_Peroid);
		lbl_Period.setSize(new Dimension(120, 20));
		lbl_Period.setPreferredSize(new Dimension(102, 15));
		lbl_Period.setMinimumSize(new Dimension(120, 20));
		lbl_Period.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Period.setBorder(null);
		lbl_Period.setAlignmentX(0.5f);
		panel2.add(lbl_Period);

		JLabel lbl_StartDate = new JLabel(referencePersonMeasur_startDate);
		lbl_StartDate.setSize(new Dimension(80, 20));
		lbl_StartDate.setPreferredSize(new Dimension(70, 15));
		lbl_StartDate.setMinimumSize(new Dimension(80, 20));
		lbl_StartDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_StartDate.setBorder(null);
		lbl_StartDate.setAlignmentX(0.5f);
		panel2.add(lbl_StartDate);

		JLabel lbl_EndDate = new JLabel(referencePersonMeasur_endDate);
		lbl_EndDate.setSize(new Dimension(80, 20));
		lbl_EndDate.setPreferredSize(new Dimension(70, 15));
		lbl_EndDate.setMinimumSize(new Dimension(80, 20));
		lbl_EndDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_EndDate.setBorder(null);
		lbl_EndDate.setAlignmentX(0.5f);
		panel2.add(lbl_EndDate);
		
		lbl_KodeZone = new JLabel(referencePersonMeasur_KodeZone);
		lbl_KodeZone.setSize(new Dimension(120, 20));
		lbl_KodeZone.setPreferredSize(new Dimension(70, 15));
		lbl_KodeZone.setMinimumSize(new Dimension(120, 20));
		lbl_KodeZone.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodeZone.setBorder(null);
		lbl_KodeZone.setAlignmentX(0.5f);
		panel2.add(lbl_KodeZone);

		JLabel lbl_L_Firm = new JLabel(referencePersonMeasur_firm);
		lbl_L_Firm.setSize(new Dimension(70, 20));
		lbl_L_Firm.setPreferredSize(new Dimension(120, 15));
		lbl_L_Firm.setMinimumSize(new Dimension(70, 20));
		lbl_L_Firm.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_Firm.setBorder(null);
		lbl_L_Firm.setAlignmentX(1.0f);
		panel2.add(lbl_L_Firm);

		JLabel lblNewLabel_1_1 = new JLabel("");
		panel2.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setPreferredSize(new Dimension(150, 14));

		btn_SearchDBase = new JButton(referencePerson_SearchFromDBase);
		btn_SearchDBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel2.add(btn_SearchDBase);
		btn_SearchDBase.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchDBase.setIconTextGap(1);
		btn_SearchDBase.setPreferredSize(new Dimension(125, 23));
		btn_SearchDBase.setEnabled(false);

		ActionListenerbBtn_SearchDBase();
		return panel2;
	}

	private JPanel panel_2A() {

		String referencePerson_SearchFromExcel = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("referencePerson_SearchFromExcel");

		JPanel panel2A = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel2A.getLayout();
		flowLayout_2.setVgap(2);
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel2A.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(panel2A);
		
		comboBox_Period = new Choice();
		comboBox_Period.setPreferredSize(new Dimension(105, 20));
		comboBox_Period.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel2A.add(comboBox_Period);

		addItemPeriod();
		ActionListenerComboBox_Period();
		
		txtStartDate = new JTextField("01.01."+curentYear);
		txtStartDate.setPreferredSize(new Dimension(8, 20));
		txtStartDate.setMinimumSize(new Dimension(5, 20));
		txtStartDate.setColumns(8);
		panel2A.add(txtStartDate);

		txtEndDate = new JTextField("31.03."+curentYear);
		txtEndDate.setPreferredSize(new Dimension(5, 20));
		txtEndDate.setMinimumSize(new Dimension(5, 20));
		txtEndDate.setColumns(8);
		panel2A.add(txtEndDate);
		
		comboBox_KodeZone = new Choice();
		comboBox_KodeZone.setPreferredSize(new Dimension(70, 20));
		comboBox_KodeZone.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel2A.add(comboBox_KodeZone);

		addItemKodeZone();
		
		comboBox_Firm = new Choice();
		comboBox_Firm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_Firm.setPreferredSize(new Dimension(120, 20));
		panel2A.add(comboBox_Firm);

		ActionListenerComboBox_Firm();

		addItem(comboBox_Firm, listFirm);

		JLabel lblNewLabel_1_1 = new JLabel();
		lblNewLabel_1_1.setPreferredSize(new Dimension(147, 14));
		panel2A.add(lblNewLabel_1_1);

		btn_SearchFromExcel = new JButton(referencePerson_SearchFromExcel);
		btn_SearchFromExcel.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchFromExcel.setPreferredSize(new Dimension(125, 23));
		panel2A.add(btn_SearchFromExcel);

		btn_SearchFromExcel.setEnabled(false);
		ActionListenerbBtn_SearchFromExcel();

		checkorektDate(txtStartDate);
		checkorektDate(txtEndDate);
		return panel2A;
	}

	public static void addListStringSelectionPersonToComboBox(List<PersonExcellClass> listSelectionPerson,
			Choice comboBox_Results) {
		comboBox_Results.removeAll();
		List<String> list = new ArrayList<>();
		for (PersonExcellClass person : listSelectionPerson) {
			list.add(person.getPerson().getEgn() + " "
					+ SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person.getPerson()));
		}
		Collections.sort(list);
		for (String str : list) {
			comboBox_Results.add(str);
		}

	}

	public static void addListStringSelectionPersonToComboBoxPerson(List<Person> listSelectionPerson,
			Choice comboBox_Results) {
		comboBox_Results.removeAll();
		List<String> list = new ArrayList<>();
		for (Person person : listSelectionPerson) {
			list.add(person.getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person));
		}
		Collections.sort(list);
		for (String str : list) {
			comboBox_Results.add(str);
		}

	}

	public static void checkorektDate(JTextField textFieldDate) {
		textFieldDate.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {

				if (AplicationMetods.incorrectDate(textFieldDate.getText())) {
					textFieldDate.setForeground(Color.RED);
					btn_SearchDBase.setEnabled(false);
					btn_SearchFromExcel.setEnabled(false);

				} else {
					textFieldDate.setForeground(Color.BLACK);
					btn_SearchDBase.setEnabled(true);
					btn_SearchFromExcel.setEnabled(true);
				}
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});
	}

	

	public static String getLastWorkplaceByPerson(Person person) {

		PersonStatusNew per = PersonStatusNewDAO.getLastValuePersonStatusNewByPerson(person);

		return per == null ? "" : per.getWorkplace().getOtdel();
	}

	public static String getLastKodeByPersonAndZone(Person person, int zonaID) {
		List<KodeStatus> list = KodeStatusDAO.getKodeStatusByPersonZone(person, zonaID);
		if (list != null) {
			List<KodeStatus> sortedReversKodeStatList = list.stream()
					.sorted(Comparator.comparing(KodeStatus::getKodeStatus_ID).reversed()).collect(Collectors.toList());
			return sortedReversKodeStatList.get(0).getKode();
		}
		return "-";
	}

	protected String createStringToInfoPanel(List<Person> listSelectionPerson) {
		String str = "";
		for (Person person : listSelectionPerson) {
			str = str + person.getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person)
					+ "\n";
		}
		return str;
	}

	private void ActionListenerComboBox_Firm() {

		comboBox_Firm.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("/////////////////////////");
					setitemInChoise();
				}
			}
		});

	}

	private void ActionListenerbBtn_SearchDBase() {
		btn_SearchDBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneralMethods.setWaitCursor(panel_AllSaerch);
				textArea.setText("Търся в Базата");
				viewInfoPanel();
				System.out.println("searchFromDBase0");
				
				ProgresBarByReferenceKontrolPersonByPeriod dd = new ProgresBarByReferenceKontrolPersonByPeriod(listAddPOtdel, textArea, progressBar, "searchFromDBase");
								
			dd.execute();
				
			}
		});

	}
	
	
	public static void setTextToArea(JTextArea textArea, Object[][] dataTable_pb) {
		dataTable = convertStringToObject(dataTable_pb);
		if (dataTable == null) {
			textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
			btn_Export.setEnabled(false);
		} else {
			
			panel_infoPanelTablePanel(dataTable);
			viewTablePanel();
			GeneralMethods.setDefaultCursor(panel_AllSaerch);
			btn_Export.setEnabled(true);
		}
	}
	
	
	private static Object[][] convertStringToObject(Object[][] dataTable_pb) {
		
		for (int k = 0; k < dataTable_pb.length; k++) {
			for (int i = 0; i < dataTable_pb[0].length; i++) {
				if(dataTable_pb[k][i].toString().isEmpty()) {
					dataTable_pb[k][i] = 0;
				}
					try {
							
				switch (i) {
			
				case 0:
				case 2:
				case 3:
				case 4:{
					if(dataTable_pb[k][i] instanceof Double) {
					dataTable_pb[k][i] = ((Double)dataTable_pb[k][i]).intValue();
					}
					if(dataTable_pb[k][i] instanceof String) {
						dataTable_pb[k][i] = Integer.parseInt((String)dataTable_pb[k][i]);
						}
					}
					break;
				case 5:
				case 6:
				case 7:{
					if(dataTable_pb[k][i] instanceof Double) {
					dataTable_pb[k][i] = Double.parseDouble(String.format("%.2f", (Double)dataTable_pb[k][i]).replace(",", "."));
					}
					}
					break;
				}
					}catch (Exception e) {
						System.out.println(dataTable_pb[k][i]);
						
						e.printStackTrace();
					}
			}
			
		}
		for (int k = 0; k < dataTable_pb.length; k++) {
			for (int i = 0; i < dataTable_pb[0].length; i++) {
		System.out.print(dataTable_pb[k][i].getClass()+" ");
			}
			System.out.println();
		}
		return dataTable_pb;
	}

	private void ActionListenerbBtn_SearchFromExcel() {

		btn_SearchFromExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					
					dataTable = null;
					textArea.setText("Търся в Ексел Файла");
					viewInfoPanel();
					ProgresBarByReferenceKontrolPersonByPeriod dd = new ProgresBarByReferenceKontrolPersonByPeriod(listAddPOtdel, textArea, progressBar, "searchFromExcelFile");
					
					dd.execute();

//					GeneralMethods.setDefaultCursor(panel_AllSaerch);

				
			}
		});

	}
	

	
	private JPanel panel_Button() {

		String referencePerson_Export = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Export");

		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) buttonPanel.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		btn_Export = new JButton(referencePerson_Export);
		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dataTable != null) {
					String[] header = Metods_ReferenceKontrolPersonByPeriod.refornatTableHeader(getTabHeader());
					PersonReferenceExportToExcell.btnExportTableToExcell(dataTable, header, buttonPanel, "PersonReference");
	}
			}
		});
		buttonPanel.add(btn_Export);

		return buttonPanel;
	}

	static void viewTablePanel() {
		infoPanel.setPreferredSize(new Dimension(10, 0));
		infoPanel.setMaximumSize(new Dimension(32767, 0));

		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
//		btnBackToTable.setEnabled(false);

		contentPane.repaint();
		contentPane.revalidate();
	}

	void viewInfoPanel() {
		infoPanel.setPreferredSize(new Dimension(10, 10));
		infoPanel.setMaximumSize(new Dimension(32767, 32767));

		tablePane.setPreferredSize(new Dimension(10, 0));
		tablePane.setMaximumSize(new Dimension(32767, 0));
		if (dataTable != null) {
//			btnBackToTable.setEnabled(true);
		}
		repaint();
		revalidate();
	}

	static void panel_infoPanelTablePanel(Object[][] dataTable) {
		String[] columnNames = getTabHeader();
		int egn_code_Colum = 0;

		DefaultTableModel dtm = new DefaultTableModel();
		final JTable table = new JTable(dtm);

		dtm = new DefaultTableModel(dataTable, columnNames) {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("rawtypes")
			private Class[] types2 = getColumnClassTypes();
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return this.types2[columnIndex];
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Object getValueAt(int row, int col) {
//				return col > 1 ? col > 4 ? String.format("%.2f", dataTable[row][col]).replace(",", "."): String.format("%.0f", dataTable[row][col]).replace(",", "."):dataTable[row][col];
				return dataTable[row][col];
			}

			@SuppressWarnings("unused")
			public void setValueAt(String value, int row, int col) {

				if (!dataTable[row][col].equals(value)) {
					dataTable[row][col] = value;
					fireTableCellUpdated(row, col);

				}
			}

			public int getColumnCount() {
				return columnNames.length;
			}

			public int getRowCount() {
				return dataTable.length;
			}

		};

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				if (table.getSelectedColumn() == egn_code_Colum) {
					table.rowAtPoint(e.getPoint());
					table.columnAtPoint(e.getPoint());
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();

					System.out.println(reqCodeStr);

				}

				if (e.getClickCount() == 2 && getSelectedModelRow(table) > 0) {
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					int selectRow = getSelectedModelRow(table);
					Object otdel = table.getModel().getValueAt(selectRow, 1);
//					Object otdel = table.getValueAt(selectRow, 1);
					System.out.println(selectRow+" "+otdel);
					List<List<String>> listPersonFromOtdel = Metods_ReferenceKontrolPersonByPeriod.getListPersonFromOtdel();
					System.out.println(listPersonFromOtdel.size());
					if(listPersonFromOtdel.size() > 0) {
						if(selectRow > 0) {
							List<String> listPersoninfo = listPersonFromOtdel.get(selectRow-1);
							System.out.println(listPersoninfo.size());
							if(listPersoninfo.size() > 0) {
							new ListPersonInfoFrame(listPersoninfo, otdel);
							}
						
						GeneralMethods.setDefaultCursor(panel_AllSaerch);
						
					}
					}
				}
			}
		});

		new TableFilterHeader(table, AutoChoices.ENABLED);

		dtm.fireTableDataChanged();
		table.setModel(dtm);
		table.setFillsViewportHeight(true);
		
		Metods_ReferenceKontrolPersonByPeriod.setUp_Double_Column(table, 5);
		Metods_ReferenceKontrolPersonByPeriod.setUp_Double_Column(table, 6);
		Metods_ReferenceKontrolPersonByPeriod.setUp_Double_Column(table, 7);
		Metods_ReferenceKontrolPersonByPeriod.initColumnSizes(table, dataTable);
		
		
		table.repaint();
		System.out.println("+++++++++++++ " + dataTable.length);

		tablePane.removeAll();
		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane(table);
		tablePane.add(scrollPane, BorderLayout.CENTER);

	}

	private static int getSelectedModelRow(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}

	
	@SuppressWarnings("rawtypes")
	private static Class[] getColumnClassTypes() {

		Class[] types = { Integer.class, 
				String.class, 
				Integer.class, 
				Integer.class, 
				Integer.class,
				Double.class,  
				Double.class, 
				Double.class};

		return types;
	}	
	
	
	
	private static String[] getTabHeader() {
		String[] tableHeader = { "<html><center> <br>№<br>'</html>", 
				"Подразделение", 
				"<html><center>Брой<br>контролирани<br>лица</html>",
				"<html><center>Брой<br>измервания</html>",
				"<html><center>Брой хора<br>с активност<br>над МДА</html>", 
				"<html><center>Колективна<br>доза<br>[man.mSv]</html>",
				"<html><center>Макс.<br>доза<br>[mSv]</html>",
				"<html><center>Средна<br>доза<br>[mSv]</html>"};
		return tableHeader;
	}

	public static ArrayList<String> getListStringOtdel(List<Workplace> valueWorkplaceByObject) {
		ArrayList<String> list = new ArrayList<String>();
		for (Workplace workplace : valueWorkplaceByObject) {
			if(workplace.getActual()) {
			list.add(workplace.getOtdel());
			}
		}
		return list;
	}

	private void setitemInChoise() {
		listAddPOtdel = listOtdelVO;
		if (((String) comboBox_Firm.getSelectedItem()).trim().isEmpty()) {
//			listAdd = listOtdelAll;
			btn_SearchDBase.setEnabled(false);
			btn_SearchFromExcel.setEnabled(false);
		} else {
			btn_SearchDBase.setEnabled(true);
			btn_SearchFromExcel.setEnabled(true);
			if (((String) comboBox_Firm.getSelectedItem()).trim().equals("АЕЦ Козлодуй")) {
				listAddPOtdel = listOtdelKz;
			}
		}
//		addItem(comboBox_Firm, listAdd);
	}

	private void addItem(Choice comboBox, List<String> list) {
		comboBox.removeAll();
		for (String otdel : list) {
			comboBox.add(otdel);
		}
	}

	private void addItemKodeZone() {
		comboBox_KodeZone.add("КЗ-1");
		comboBox_KodeZone.add("КЗ-2");
		comboBox_KodeZone.add("ХОГ");
		
	}
	
	private void ActionListenerComboBox_Period() {

		comboBox_Period.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String key = comboBox_Period.getSelectedItem().trim();
					switch (key) {
					case "1-во трим": {
						txtStartDate.setText("01.01."+curentYear);
						txtEndDate.setText("31.03."+curentYear);
					}
						break;
					case "2-ро трим": {
						txtStartDate.setText("01.04."+curentYear);
						txtEndDate.setText("30.06."+curentYear);
					}
						break;
					case "3-то трим": {
						txtStartDate.setText("01.07."+curentYear);
						txtEndDate.setText("30.09."+curentYear);
					}
						break;
					case "4-то трим": {
						txtStartDate.setText("01.10."+curentYear);
						txtEndDate.setText("31.12."+curentYear);
					}
						break;
					case "Година": {
						txtStartDate.setText("01.01."+curentYear);
						txtEndDate.setText("31.12."+curentYear);
					}
						break;
				}
				}
			}
		});

	}
	
	
	private void addItemPeriod() {
		comboBox_Period.add("1-во трим");
		comboBox_Period.add("2-ро трим");
		comboBox_Period.add("3-то трим");
		comboBox_Period.add("4-то трим");
		comboBox_Period.add("Година");
		
		
	}
	
	public static JTextField getTextField_StartDate() {
		return txtStartDate;
	}

	public static JTextField getTextField_EndDate() {
		return txtEndDate;
	}

	public static JTextArea getTextArea() {
		return textArea;
	}

	public static void setTextArea(JTextArea textArea) {
		ReferenceKontrolPersonByPeroid_Frame.textArea = textArea;
	}

	public static JButton getBtn_SearchFromExcel() {
		return btn_SearchFromExcel;
	}

	public static Choice getComboBox_Firm() {
		return comboBox_Firm;
	}

	public static Choice getComboBox_Period() {
		return comboBox_Period;
	}

	public static Choice getComboBox_KodeZone() {
		return comboBox_KodeZone;
	}

}