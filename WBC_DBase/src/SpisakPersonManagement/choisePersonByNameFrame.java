package SpisakPersonManagement;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class choisePersonByNameFrame extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel tablePane;
	private JScrollPane scrollPane;
	private String tableSelektedEGN;
	private JButton okButton;
	
	private static String referencePerson_EGN = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_EGN");
	private static String referencePerson_FirstName = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_FirstName");
	private static String referencePerson_SecondName = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_SecondName");
	private static String referencePerson_LastName = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_LastName");
	private static String referencePerson_ID_KZ_1 = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_ID_KZ_1");
	private static String referencePerson_ID_KZ_2 = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_ID_KZ_2");
	private static String referencePerson_ID_KZ_HOG = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_ID_KZ_HOG");
	private static String referencePersonMeasur_otdel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePersonMeasur_otdel");
	
	public choisePersonByNameFrame(JFrame parent, JPanel panel_AllSaerch, String choicePerson, List<Person> listPerson) {
		
		super(parent, "Изберете служител", true);
		
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		contentPanel = new JPanel();
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			Panel panel = new Panel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			contentPanel.add(panel);
			{
				JLabel lblNewLabel = new JLabel(choicePerson);
				lblNewLabel.setPreferredSize(new Dimension(400, 20));
				panel.add(lblNewLabel);
				lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			}
		}
		


		String[][] dataTable = addListStringSelectionPersonToComboBox(listPerson);
		
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Добави");
				okButton.setPreferredSize(new Dimension(80, 23));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setEGNChoisePerson(tableSelektedEGN);
						dispose(); // Destroy the JFrame object
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.setEnabled(false);
			}
			{
				JButton cancelButton = new JButton("Пропусни");
				cancelButton.setPreferredSize(new Dimension(80, 23));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SpisakPersonelManegementMethods.setChoisePerson("");
						dispose(); // Destroy the JFrame object
					}
				});
				buttonPane.add(cancelButton);
				
				JButton crashButton = new JButton("Отказ");
				crashButton.setPreferredSize(new Dimension(80, 23));
				crashButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SpisakPersonelManegementMethods.setChoisePerson(null);
						dispose(); // Destroy the JFrame object
					}
				});
				buttonPane.add(crashButton);
				
				
				
			}
		}
		{
			tablePane = new JPanel();
			getContentPane().add(tablePane, BorderLayout.CENTER);
			tablePane.setLayout(new BorderLayout(0, 0));
			
//				scrollPane = new JScrollPane();
//				tablePane.add(scrollPane, BorderLayout.CENTER);
			
		}
		
		GeneralMethods.setDefaultCursor(panel_AllSaerch);
		panel_infoPanelTablePanel( dataTable);
		setSize(730, 440);
		setLocationRelativeTo(null);
		setVisible(true);
		
		
		
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
				
				if (getSelectedModelRow(table) != -1) {
					tableSelektedEGN = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();
					okButton.setEnabled(true);
				}else {
					okButton.setEnabled(false);
				}
				
				if (e.getClickCount() == 2 && getSelectedModelRow(table) != -1) {
					tableSelektedEGN = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();
					setEGNChoisePerson(tableSelektedEGN);
					dispose(); // Destroy the JFrame object
					
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

	private String[] getTabHeader() {
		String[] tableHeader = { referencePerson_EGN, referencePerson_FirstName, referencePerson_SecondName, referencePerson_LastName,
				referencePersonMeasur_otdel, referencePerson_ID_KZ_1, referencePerson_ID_KZ_2, referencePerson_ID_KZ_HOG };
		return tableHeader;
	}
	
	private int getSelectedModelRow(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}
	
	private void setEGNChoisePerson(String EGN) {
		if(EGN != null) {
		Person person = PersonDAO.getValuePersonByEGN(EGN);
							
		String personText = person.getEgn() +" "+person.getFirstName();
		
		SpisakPersonelManegementMethods.setChoisePerson(personText);
		}
		
	}
	
}
