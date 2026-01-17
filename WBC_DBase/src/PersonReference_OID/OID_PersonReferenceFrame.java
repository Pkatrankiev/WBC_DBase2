package PersonReference_OID;

import javax.swing.JFrame;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Choice;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.KodeStatusDAO;

import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.Workplace;
import InsertMeasuting.SaveReportMeasurTo_PersonelORExternalExcelFile;
import PersonReference.InsertFulName_FrameDialog;
import PersonReference.PersonReferenceExportToExcell;
import PersonReference.SearchFromExcellFiles;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Component;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JTable;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class OID_PersonReferenceFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_AllSaerch;
	private JPanel panel_Search;
	private JPanel infoPanel;
	private JPanel tablePane;
	private JScrollPane scrollPane;

	private static JTextArea textArea;
	private static JTextField textField_EGN;
	private static JTextField textField_FName;
	private static JTextField textField_SName;
	private static JTextField textField_LName;

	private static JButton btn_SearchDBase;
	private static JButton btnBackToTable;

	private String notResults;

	ArrayList<String> listOtdelKz;
	List<String> listOtdelVO;
	List<String> listOtdelAll;
	List<String> listAdd;
	List<String> listFirm;
	String[][] dataTable;
	
	
	private static String referencePerson_EGN = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_EGN");
	private static String referencePerson_FirstName = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_FirstName");
	private static String referencePerson_SecondName = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_SecondName");
	private static String referencePerson_LastName = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_LastName");
		
	static String curentYear = AplicationMetods.getCurentYear();
	private JButton btnUpDataBD;
	public OID_PersonReferenceFrame(ActionIcone round, String title) {
		setTitle(title+ " Последна актуализация на базата:   "+getDateNewDBAccsess());
		setMinimumSize(new Dimension(740, 900));

		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));
		
		notResults = ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults");
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
		listOtdelKz.add("");
		Collections.sort(listOtdelKz);

		listOtdelVO = getListStringOtdel(WorkplaceDAO.getValueWorkplaceByObject("FirmName", VO));
		listOtdelVO.add("");
		Collections.sort(listOtdelVO);
		listOtdelAll = getListStringOtdel(WorkplaceDAO.getAllValueWorkplace());
		listOtdelAll.add("");
		Collections.sort(listOtdelAll);

		listAdd = new ArrayList<>();
		listFirm = new ArrayList<>();
		listFirm.add("");
		listFirm.add(AEC);
		listFirm.add(VO);

		panel_1();
		panel_1A();
		panel_3();

		getRootPane().setDefaultButton(btn_SearchDBase);

		panel_Button();

		setSize(740, 900);
		setLocationRelativeTo(null);
		setVisible(true);
		GeneralMethods.setDefaultCursor(panel_AllSaerch);
		round.StopWindow();
	}

	private JPanel panel_1() {
		
		
		JPanel panel1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel1.getLayout();
		flowLayout_1.setVgap(2);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_Search.add(panel1);

		JLabel lbl_EGN = new JLabel(referencePerson_EGN);
		lbl_EGN.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_EGN.setSize(new Dimension(80, 20));
		lbl_EGN.setPreferredSize(new Dimension(85, 15));
		lbl_EGN.setMinimumSize(new Dimension(80, 20));
		lbl_EGN.setBorder(null);
		lbl_EGN.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel1.add(lbl_EGN);

		JLabel distantion = new JLabel();
		distantion.setPreferredSize(new Dimension(30, 14));
		panel1.add(distantion);

		JLabel lbl_FirstName = new JLabel(referencePerson_FirstName);
		lbl_FirstName.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_FirstName.setSize(new Dimension(220, 20));
		lbl_FirstName.setPreferredSize(new Dimension(126, 15));
		lbl_FirstName.setMinimumSize(new Dimension(220, 20));
		lbl_FirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_FirstName.setBorder(null);
		panel1.add(lbl_FirstName);

		JLabel lbl_L_SecondName = new JLabel(referencePerson_SecondName);
		lbl_L_SecondName.setSize(new Dimension(70, 20));
		lbl_L_SecondName.setPreferredSize(new Dimension(126, 15));
		lbl_L_SecondName.setMinimumSize(new Dimension(70, 20));
		lbl_L_SecondName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_SecondName.setBorder(null);
		lbl_L_SecondName.setAlignmentX(1.0f);
		panel1.add(lbl_L_SecondName);

		JLabel lbl_L_LastName = new JLabel(referencePerson_LastName);
		lbl_L_LastName.setSize(new Dimension(70, 20));
		lbl_L_LastName.setPreferredSize(new Dimension(126, 15));
		lbl_L_LastName.setMinimumSize(new Dimension(70, 20));
		lbl_L_LastName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_L_LastName.setBorder(null);
		lbl_L_LastName.setAlignmentX(1.0f);
		panel1.add(lbl_L_LastName);
		return panel1;
	}

	private JPanel panel_1A() {
		
		String referencePerson_SearchFromDBase = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_SearchFromDBase");
		
		JPanel panel1A = new JPanel();
		panel1A.setPreferredSize(new Dimension(10, 30));
		FlowLayout fl_panel1A = (FlowLayout) panel1A.getLayout();
		fl_panel1A.setVgap(2);
		fl_panel1A.setAlignment(FlowLayout.LEFT);
		panel_Search.add(panel1A);

		textField_EGN = new JTextField();
		textField_EGN.setPreferredSize(new Dimension(5, 20));
		textField_EGN.setMinimumSize(new Dimension(5, 20));
		panel1A.add(textField_EGN);
		textField_EGN.setColumns(10);

		TextFieldJustNumbers(textField_EGN);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setPreferredSize(new Dimension(29, 14));
		panel1A.add(lblNewLabel_1);

		textField_FName = new JTextField();
		textField_FName.setPreferredSize(new Dimension(5, 20));
		textField_FName.setMinimumSize(new Dimension(5, 20));
		textField_FName.setColumns(15);
		panel1A.add(textField_FName);

		ActionListenerbInsertFullName();
		
		textField_SName = new JTextField();
		textField_SName.setPreferredSize(new Dimension(5, 20));
		textField_SName.setMinimumSize(new Dimension(5, 20));
		textField_SName.setColumns(15);
		panel1A.add(textField_SName);

		textField_LName = new JTextField();
		textField_LName.setPreferredSize(new Dimension(5, 20));
		textField_LName.setMinimumSize(new Dimension(5, 20));
		textField_LName.setColumns(15);
		panel1A.add(textField_LName);

		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setPreferredSize(new Dimension(50, 14));
		panel1A.add(lblNewLabel_1_1);

		btn_SearchDBase = new JButton(referencePerson_SearchFromDBase);
		btn_SearchDBase.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchDBase.setIconTextGap(1);
		btn_SearchDBase.setPreferredSize(new Dimension(125, 23));
		panel1A.add(btn_SearchDBase);

		ActionListenerbBtn_SearchDBase();

		return panel1A;
	}

	

	private JPanel panel_3() {

		String referencePerson_BackToTable = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_BackToTable");
		String referencePerson_UpDataBD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_UpDataBD");
		
		JPanel panel3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel3.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel3.setPreferredSize(new Dimension(10, 30));
		panel_Search.add(panel3);

		ActionListenerComboBox_Results();

		btnBackToTable = new JButton(referencePerson_BackToTable);
		btnBackToTable.setEnabled(false);
		btnBackToTable.setMargin(new Insets(2, 2, 2, 2));
		btnBackToTable.setPreferredSize(new Dimension(105, 20));
		panel3.add(btnBackToTable);
		
		ActionListenerBtnBackToTable();
		
		btnUpDataBD = new JButton(referencePerson_UpDataBD);
		btnUpDataBD.setPreferredSize(new Dimension(105, 20));
		btnUpDataBD.setMargin(new Insets(2, 2, 2, 2));
		panel3.add(btnUpDataBD);

		ActionListenerBtnUpDataBD();

		return panel3;
	}

	public static void addListStringSelectionPersonToComboBox(List<Person> listSelectionPerson,
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

	protected static String[][] addListStringSelectionPersonToComboBox(List<Person> listSelectionPerson) {

		String[][] dataTable = new String[listSelectionPerson.size()][4];

//				"EGN", 	"FirstName","SecondName","LastName","Otdel",
//				"Kod KZ-1",	"Kod KZ-2",	"Kod Hog"	

		int k = 0;
		for (Person person : listSelectionPerson) {
			System.out.println("egn " + person.getEgn());
			dataTable[k][0] = person.getEgn();
			dataTable[k][1] = person.getFirstName();
			dataTable[k][2] = person.getSecondName();
			dataTable[k][3] = person.getLastName();
			

			k++;
		}

		return dataTable;

	}

	public static String getLastWorkplaceByPerson(Person person) {
				
		PersonStatusNew per = PersonStatusNewDAO.getLastValuePersonStatusNewByPerson(person);
		
		return per == null? "":per.getWorkplace().getOtdel();
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
			str = str + person.getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person) + "\n";
		}
		return str;
	}

	public static List<Person> getListSearchingPerson(List<Person> listAllPerson) {
		List<Person> listSelectionPersonEGN = new ArrayList<>();
		List<Person> listSelectionPersonFName = new ArrayList<>();
		List<Person> listSelectionPersonSName = new ArrayList<>();
		List<Person> listSelectionPersonLName = new ArrayList<>();
		
		System.out.println("************ "+listAllPerson.size());
		
		String egn = "";
		String firstName = "";
		String secontName = "";
		String lastName = "";
		
		egn = OID_PersonReferenceFrame.getTextField_EGN().getText();
		firstName = OID_PersonReferenceFrame.getTextField_FName().getText();
		secontName = OID_PersonReferenceFrame.getTextField_SName().getText();
		lastName = OID_PersonReferenceFrame.getTextField_LName().getText();	
		

		if (!egn.trim().isEmpty()) {
			for (Person person : listAllPerson) {
				if (person.getEgn().contains(egn)) {
					listSelectionPersonEGN.add(person);
				}
			}
		} else {
			listSelectionPersonEGN = listAllPerson;
		}
		System.out.println("listSelectionPersonEGN = " + listSelectionPersonEGN.size());

		if (!firstName.trim().isEmpty()) {
			for (Person person : listSelectionPersonEGN) {
				if (SearchFromExcellFiles.checkContainsStrings(person.getFirstName(), firstName)) {
					listSelectionPersonFName.add(person);
				}
			}
		} else {
			listSelectionPersonFName = listSelectionPersonEGN;
		}
		System.out.println("listSelectionPersonFName = " + listSelectionPersonFName.size());
		if (!secontName.trim().isEmpty()) {
			for (Person person : listSelectionPersonFName) {
				if (SearchFromExcellFiles.checkContainsStrings(person.getSecondName(), secontName)) {
					listSelectionPersonSName.add(person);
				}
			}
		} else {
			listSelectionPersonSName = listSelectionPersonFName;
		}
		System.out.println("listSelectionPersonSName = " + listSelectionPersonSName.size());

		if (!lastName.trim().isEmpty()) {
			for (Person person : listSelectionPersonSName) {
				if (SearchFromExcellFiles.checkContainsStrings(person.getLastName(), lastName)) {
					listSelectionPersonLName.add(person);
				}
			}
		} else {
			listSelectionPersonLName = listSelectionPersonSName;
		}
		System.out.println("listSelectionPersonLName = " + listSelectionPersonLName.size());
	

		return listSelectionPersonLName;
	}

	

	
	

	private void ActionListenerbBtn_SearchDBase() {
		btn_SearchDBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!allFieldsEmnty()) {
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					dataTable = null;
					textArea.setText("");
				
					TreeSet<Object> set = new TreeSet<>();
					List<Person> listSelectionPersonAEC = getListSearchingPerson(OID_Person_AECDAO.getAllOID_Person_AEC());
					System.out.println("listSelectionPersonAEC "+listSelectionPersonAEC.size());
					List<Person> listSelectionPersonWBC = getListSearchingPerson(OID_Person_WBCDAO.getlistAllOID_Person_WBC());
					System.out.println("listSelectionPersonWBC "+listSelectionPersonWBC.size());
					List<Person> listSelectionPersonVO = getListSearchingPerson(OID_Person_VODAO.getAllOID_Person_VO());
					System.out.println("listSelectionPersonVO "+listSelectionPersonVO.size());
					
					
					List<Person>  listSelectionPersonLast = new ArrayList<>();
					for (Person person : listSelectionPersonAEC) {
						if(set.add(person.getEgn())) {
							listSelectionPersonLast.add(person);
							}
						}
					for (Person person : listSelectionPersonWBC) {
						if(set.add(person.getEgn())) {
							listSelectionPersonLast.add(person);
							}
						}
					for (Person person : listSelectionPersonVO) {
						if(set.add(person.getEgn())) {
							listSelectionPersonLast.add(person);
							}
						}
					
					
				

					if (listSelectionPersonLast.size() == 0) {
						textArea.setText(notResults);
						viewInfoPanel();

					}

					if (listSelectionPersonLast.size() == 1) {
						textArea.setText(OID_Metods.getInfoByEGN(listSelectionPersonLast.get(0).getEgn()));
						viewInfoPanel();
					}

					if (listSelectionPersonLast.size() > 1) {
						System.out.println("***** " + listSelectionPersonLast.size());
						dataTable = addListStringSelectionPersonToComboBox(listSelectionPersonLast);
						panel_infoPanelTablePanel(dataTable);
						viewTablePanel();
					}

					GeneralMethods.setDefaultCursor(panel_AllSaerch);
				}
			}

		});

	}

	

	private void ActionListenerComboBox_Results() {

	}

	

	private void ActionListenerBtnBackToTable() {

		btnBackToTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataTable!=null) {
				panel_infoPanelTablePanel(dataTable);
				viewTablePanel();
				}
			}

		});

	}

	private void ActionListenerBtnUpDataBD() {

		btnUpDataBD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String personReference_OID = ReadFileBGTextVariable.getGlobalTextVariableMap().get("personReference_OID");
				 ActionIcone round = new ActionIcone("Чета данни от ОиД", "");
				 final Thread thread = new Thread(new Runnable() {
				     @Override
				     public void run() {
				    	 ProcessBuilder pb;
				    	 try {
				 			pb = AccessRunner.runWithWorkgroup();
				 			 Process proc = pb.start();
				 			System.out.println("Access стартиран. Изчакване да приключи...");

				 	        int exitCode = proc.waitFor();  // Тук Java блокира докато Access процеса приключи
				 	        System.out.println("Access приключи с код: " + exitCode);
				 	        dispose(); // Destroy the JFrame object
				 	        new OID_PersonReferenceFrame(round, personReference_OID);
				 		} catch (IOException | InterruptedException e1) {
				 			e1.printStackTrace();
				 		}		
				     }
				    });
				    thread.start();	
			}

		});

	}
	
	private void ActionListenerbInsertFullName() {
		textField_FName.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						int[] sizeInfoFrame = { 450, 140 };
						int[] Coord = AplicationMetods.getCurentKoordinates(sizeInfoFrame);
						 new InsertFulName_FrameDialog(new JFrame(), Coord);	
						 String[] names = InsertFulName_FrameDialog.getNames();
						 for (int i = 0; i < names.length; i++) {
							  System.out.println("kk "+ names[i]);
						}
						 if(names[0] != null) {
							 textField_FName.setText(names[0]);
							 textField_SName.setText(names[1]);
							 textField_LName.setText(names[2]);
							 repaint();
							 revalidate();
						 }
					}
				
				}

			});
	}
	
	protected static boolean allFieldsEmnty() {
		return (textField_EGN.getText().trim().isEmpty() && textField_FName.getText().trim().isEmpty()
				&& textField_SName.getText().trim().isEmpty() && textField_LName.getText().trim().isEmpty());
	}

	public static void TextFieldJustNumbers(JTextField field) {
		((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
			Pattern regEx = Pattern.compile("\\d*");

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				Matcher matcher = regEx.matcher(text);
				if (!matcher.matches()) {
					return;
				}
				super.replace(fb, offset, length, text, attrs);
			}
		});
	}

	private JPanel panel_Button() {

		String referencePerson_Export = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Export");
		
		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) buttonPanel.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		JButton btn_Export = new JButton(referencePerson_Export);
		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnBackToTable.isEnabled() || dataTable == null) {

//					PersonReferenceExportToExcell.btnExportInfoPersonToExcell(TextInAreaTextPanel.getPerson(),
//							TextInAreaTextPanel.getMasivePersonStatusName(),
//							TextInAreaTextPanel.getMasivePersonStatus(), TextInAreaTextPanel.getZoneNameMasive(),
//							TextInAreaTextPanel.getMasiveKode(), TextInAreaTextPanel.getMasiveMeasurName(),
//							TextInAreaTextPanel.getMasiveMeasur(), buttonPanel);
				} else {
					PersonReferenceExportToExcell.btnExportTableToExcell(dataTable, getTabHeader(), buttonPanel, "PersonReference");

				}
			}
		});
		buttonPanel.add(btn_Export);

		return buttonPanel;
	}

	private void viewTablePanel() {
		infoPanel.setPreferredSize(new Dimension(10, 0));
		infoPanel.setMaximumSize(new Dimension(32767, 0));

		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		btnBackToTable.setEnabled(false);

		repaint();
		revalidate();
	}

	void viewInfoPanel() {
		infoPanel.setPreferredSize(new Dimension(10, 10));
		infoPanel.setMaximumSize(new Dimension(32767, 32767));

		tablePane.setPreferredSize(new Dimension(10, 0));
		tablePane.setMaximumSize(new Dimension(32767, 0));
		if (dataTable != null) {
			btnBackToTable.setEnabled(true);
		}
		repaint();
		revalidate();
	}

	private void panel_infoPanelTablePanel(String[][] dataTable) {
		String[] columnNames = getTabHeader();
		int egn_code_Colum = 0;

		DefaultTableModel dtm = new DefaultTableModel();
		final JTable table = new JTable(dtm);

		dtm = new DefaultTableModel(dataTable, columnNames) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Object getValueAt(int row, int col) {
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

				if (e.getClickCount() == 2 && getSelectedModelRow(table) != -1) {
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();
					textArea.setText(OID_Metods.getInfoByEGN(reqCodeStr));
					viewInfoPanel();
					GeneralMethods.setDefaultCursor(panel_AllSaerch);
				}
			}
		});

		new TableFilterHeader(table, AutoChoices.ENABLED);

		dtm.fireTableDataChanged();
		table.setModel(dtm);
		table.setFillsViewportHeight(true);
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

	private int getSelectedModelRow(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}

	private String[] getTabHeader() {
		String[] tableHeader = { referencePerson_EGN, referencePerson_FirstName, referencePerson_SecondName, referencePerson_LastName};
		return tableHeader;
	}

	public static ArrayList<String> getListStringOtdel(List<Workplace> valueWorkplaceByObject) {
		ArrayList<String> list = new ArrayList<String>();
		for (Workplace workplace : valueWorkplaceByObject) {
			list.add(workplace.getOtdel());
		}
		return list;
	}

	public static String getDateNewDBAccsess() {
		
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	File fis = new File("k:\\Docs\\Д-я БиК\\КЦ ПД\\Обща\\04 Поща\\Петър\\DBaseApp\\NewDB.mdb");
	long lastModifiedFile = fis.lastModified();
	String stringday = sdf.format(lastModifiedFile);
	
	return stringday;
	}

	public static void MessageDialog(String textInFrame, String textFrame) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, textInFrame, textFrame, JOptionPane.PLAIN_MESSAGE, otherIcon);

	}

	

	public static JTextField getTextField_EGN() {
		return textField_EGN;
	}

	public static JTextField getTextField_FName() {
		return textField_FName;
	}

	public static JTextField getTextField_SName() {
		return textField_SName;
	}

	public static JTextField getTextField_LName() {
		return textField_LName;
	}

	

	public static JTextArea getTextArea() {
		return textArea;
	}

	

	

	
	
	
}
	

