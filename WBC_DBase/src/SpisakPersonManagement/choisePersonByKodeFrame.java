package SpisakPersonManagement;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import PersonReference.PersonReferenceFrame;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Insets;

public class choisePersonByKodeFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPanel;
	private static JPanel tablePane;
	private JPanel infoPanel;
	private static JScrollPane scrollPane;
	private String tableSelektedEGN;
	private JButton okButton;
	private JButton btn_SearchDBase;
	private static JTextArea textArea;

	private static String referencePerson_EGN = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePerson_EGN");
	private static String referencePerson_FirstName = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePerson_FirstName");
	private static String referencePerson_SecondName = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePerson_SecondName");
	private static String referencePerson_LastName = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePerson_LastName");
	private static String referencePerson_ID_KZ_1 = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePerson_ID_KZ_1");
	private static String referencePerson_ID_KZ_2 = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePerson_ID_KZ_2");
	private static String referencePerson_ID_KZ_HOG = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePerson_ID_KZ_HOG");
	private static String referencePersonMeasur_otdel = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("referencePersonMeasur_otdel");

	private static JTextField textField_Year;
	private static JTextField textField_Kode;

	public choisePersonByKodeFrame(JFrame parent, JPanel panel_AllSaerch, int IDZone, String year) {

		super(parent, "Изберете служител", true);

		String kodeName = getTabHeader()[4+IDZone];
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		contentPanel = new JPanel();
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		Panel panel = new Panel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPanel.add(panel);

		JLabel lbl_Year = new JLabel("Година");
		lbl_Year.setToolTipText("");
		lbl_Year.setSize(new Dimension(80, 20));
		lbl_Year.setPreferredSize(new Dimension(38, 15));
		lbl_Year.setMinimumSize(new Dimension(80, 20));
		lbl_Year.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Year.setBorder(null);
		lbl_Year.setAlignmentX(0.5f);
		panel.add(lbl_Year);

		textField_Year = new JTextField();
		textField_Year.setText(year);
		textField_Year.setPreferredSize(new Dimension(5, 20));
		textField_Year.setMinimumSize(new Dimension(5, 20));
		textField_Year.setColumns(4);
		panel.add(textField_Year);

		JLabel lbl_KodKZ1 = new JLabel(kodeName);
		lbl_KodKZ1.setSize(new Dimension(80, 20));
		lbl_KodKZ1.setPreferredSize(new Dimension(55, 15));
		lbl_KodKZ1.setMinimumSize(new Dimension(80, 20));
		lbl_KodKZ1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_KodKZ1.setBorder(null);
		lbl_KodKZ1.setAlignmentX(0.5f);
		panel.add(lbl_KodKZ1);

		textField_Kode = new JTextField();
		textField_Kode.setPreferredSize(new Dimension(5, 20));
		textField_Kode.setMinimumSize(new Dimension(5, 20));
		textField_Kode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_Kode.setColumns(6);
		panel.add(textField_Kode);

		JLabel lblDistance = new JLabel();
		lblDistance.setPreferredSize(new Dimension(320, 14));
		lblDistance.setMinimumSize(new Dimension(120, 14));
		lblDistance.setMaximumSize(new Dimension(35200, 14));
		panel.add(lblDistance);

		btn_SearchDBase = new JButton("Търси");
		btn_SearchDBase.setPreferredSize(new Dimension(125, 23));
		btn_SearchDBase.setMargin(new Insets(2, 5, 2, 5));
		btn_SearchDBase.setIconTextGap(1);
		panel.add(btn_SearchDBase);
		btn_SearchDBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[][] dataTable = addListStringSelectionPersonToComboBox(searchPersonByKode(IDZone));
				if (dataTable.length > 0) {
					GeneralMethods.setDefaultCursor(panel_AllSaerch);
					panel_infoPanelTablePanel(dataTable, IDZone);
					viewTablePanel();
				}else {
				textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
					viewInfoPanel();

				}
				
			}
		});
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("Добави");
		okButton.setPreferredSize(new Dimension(65, 23));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Person person = PersonDAO.getValuePersonByEGN(tableSelektedEGN);
				setNewRowInTable(person, IDZone);
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		okButton.setEnabled(false);

		JButton cancelButton = new JButton("Пропусни");
		cancelButton.setPreferredSize(new Dimension(65, 23));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Destroy the JFrame object
			}
		});
		buttonPane.add(cancelButton);
		
		
		
		JPanel panel_All = new JPanel();
		getContentPane().add(panel_All, BorderLayout.CENTER);
		panel_All.setLayout(new BoxLayout(panel_All, BoxLayout.Y_AXIS));

		
		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(10, 10));
		infoPanel.setMaximumSize(new Dimension(32767, 32767));
		panel_All.add(infoPanel);
		infoPanel.setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		JScrollPane sp = new JScrollPane(textArea);
		infoPanel.add(sp, BorderLayout.CENTER);

		tablePane = new JPanel();
		tablePane.setPreferredSize(new Dimension(10, 0));
		tablePane.setMaximumSize(new Dimension(32767, 0));
		panel_All.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		tablePane.add(scrollPane, BorderLayout.CENTER);
		
		
		
		
		
//		tablePane = new JPanel();
//		getContentPane().add(tablePane, BorderLayout.CENTER);
//		tablePane.setLayout(new BorderLayout(0, 0));

		getRootPane().setDefaultButton(btn_SearchDBase);
		setSize(730, 440);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	void viewInfoPanel() {
		infoPanel.setPreferredSize(new Dimension(10, 10));
		infoPanel.setMaximumSize(new Dimension(32767, 32767));

		tablePane.setPreferredSize(new Dimension(10, 0));
		tablePane.setMaximumSize(new Dimension(32767, 0));
		
		repaint();
		revalidate();
	}
	
	void viewTablePanel() {
		

		infoPanel.setPreferredSize(new Dimension(10, 0));
		infoPanel.setMaximumSize(new Dimension(32767, 0));

		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		
		repaint();
		revalidate();
	}
	
	
	protected static List<Person> searchPersonByKode(int IDZone) {
		List<Person> listPerson = new ArrayList<>();
		List<Integer> listIDPerson = new ArrayList<>();
		String kod = textField_Kode.getText();
		String year = textField_Year.getText();

		List<KodeStatus> listKodeStst = KodeStatusDAO.getListKodeStatusByZoneYaerAndKode(IDZone, year, kod);
		if (listKodeStst != null) {
			for (KodeStatus kodeStatus : listKodeStst) {
				if (kodeStatus != null) {
					listIDPerson.add(kodeStatus.getPerson().getId_Person());
				}
			}
		}
		for (int idPerson : SpisakPersonelManegementMethods.removeDuplicatess(listIDPerson)) {
			listPerson.add(PersonDAO.getValuePersonByID(idPerson));
		}
		return listPerson;
	}

	protected static String[][] addListStringSelectionPersonToComboBox(List<Person> listSelectionPerson) {

		String[][] dataTable = new String[listSelectionPerson.size()][8];

//				"EGN", 	"FirstName","SecondName","LastName","Otdel",
//				"Kod KZ-1",	"Kod KZ-2",	"Kod Hog"	

		int k = 0;
		for (Person person : listSelectionPerson) {
//			System.out.println("egn " + person.getEgn());
			dataTable[k][0] = person.getEgn();
			dataTable[k][1] = person.getFirstName();
			dataTable[k][2] = person.getSecondName();
			dataTable[k][3] = person.getLastName();
			dataTable[k][4] = getLastWorkplaceByPerson(person);
			dataTable[k][5] = getLastKodeByPersonAndZone(person, 1);
			dataTable[k][6] = getLastKodeByPersonAndZone(person, 2);
			dataTable[k][7] = getLastKodeByPersonAndZone(person, 3);

			k++;
		}

		return dataTable;

	}

	private void panel_infoPanelTablePanel(String[][] dataTable, int IDZone) {
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

				if (getSelectedModelRow(table) != -1) {
					tableSelektedEGN = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();
					okButton.setEnabled(true);
				} else {
					okButton.setEnabled(false);
				}

				if (e.getClickCount() == 2 && getSelectedModelRow(table) != -1) {
					tableSelektedEGN = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();
					Person person = PersonDAO.getValuePersonByEGN(tableSelektedEGN);
					setNewRowInTable(person, IDZone);
					
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
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane(table);
		tablePane.add(scrollPane, BorderLayout.CENTER);

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

	private String[] getTabHeader() {
		String[] tableHeader = { referencePerson_EGN, referencePerson_FirstName, referencePerson_SecondName,
				referencePerson_LastName, referencePersonMeasur_otdel, referencePerson_ID_KZ_1, referencePerson_ID_KZ_2,
				referencePerson_ID_KZ_HOG };
		return tableHeader;
	}

	private int getSelectedModelRow(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}
	
	
	private void setNewRowInTable(Person person, int IDZone) {
		
		if(person != null) {
		Object[] newRow = new Object[10];
		
		JPanel panel_AllSaerch  = SpisakPersonelManegementFrame.getPanel_AllSaerch();
		Object[][] dataTableNew = new Object[1][10];
		Object[][] dataTable = SpisakPersonelManegementMethods.getDataTable();
		int k = 0;
		if (dataTable != null && dataTable.length > 0) {
			
			k = dataTable.length;
			System.out.println("k="+k+" dataTable.length="+dataTable.length);
			dataTableNew = new Object[k + 1][10];
			for (int i = 0; i < dataTable.length; i++) {
				dataTableNew[i] = dataTable[i];
				
			}
			
			System.out.println("k="+k+" dataTableNew.length="+dataTableNew.length);
		}
		
			newRow[0] = (k + 1);
			newRow[1] = person.getEgn();
			newRow[2] = person.getFirstName();
			newRow[3] = person.getSecondName();
			newRow[4] = person.getLastName();
			newRow[5] = PersonReferenceFrame.getLastWorkplaceByPerson(person);
			newRow[6] = SpisakPersonelManegementMethods.getLastMeasuring(person);
			newRow[7] = PersonReferenceFrame.getLastKodeByPersonAndZone(person, IDZone);
			newRow[8] = newRow[7];
			newRow[9] = true;
			System.out.println("k2="+k+" dataTableNew.length="+dataTableNew.length);
			Collection<String> different = new HashSet<String>();
			for (int i = 0; i < dataTableNew.length; i++) {
				different.add((String) dataTableNew[i][1]);
			}
			if(different.add(person.getEgn())) {
			dataTableNew[k] = newRow;
			
			JTextArea textArea = SpisakPersonelManegementFrame.getTextArea();
			JPanel tablePane = SpisakPersonelManegementFrame.getTablePane();
			
			JScrollPane scrollPane   = SpisakPersonelManegementFrame.getScrollPane();
			JTextField textField_svePerson_Year   = SpisakPersonelManegementFrame.getTextField_svePerson_Year();
			GeneralMethods.setWaitCursor(panel_AllSaerch);
			SpisakPersonelManegementMethods.setDataTable(dataTableNew);
			SpisakPersonelManegementFrame.viewInfoPanel();
			int zoneID = ZoneDAO.getValueZoneByNameTerritory(SpisakPersonelManegementMethods.getZonaFromRadioButtons()).getId_Zone();
			SpisakPersonelManegementMethods.panel_infoPanelTablePanel(dataTableNew, textArea, panel_AllSaerch, tablePane, scrollPane,
					textField_svePerson_Year, zoneID);
			SpisakPersonelManegementFrame.viewTablePanel();
			}else{
				GeneralMethods.setDefaultCursor(panel_AllSaerch);
				SpisakPersonelManegementMethods.MessageDialog("Лице с ЕГН: " +person.getEgn()+" се повтаря в списъка");	
			}
			GeneralMethods.setDefaultCursor(panel_AllSaerch);

		
		}
		}

}
