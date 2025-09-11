package MeasurInOderLabCheck;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import Aplication.GeneralMethods;
import BasiClassDAO.DiferentLabInMeasurDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.DiferentLabInMeasur;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.Zone;

import PersonReference.PersonReferenceExportToExcell;
import PersonReference.PersonReferenceFrame;
import PersonReference.TextInAreaTextPanel;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;


public class MeasurInOderLabMetods {

	private static Object[][] dataTable;
	private static JTable table;
	private static int tbl_Colum = 11;
	private static int egn_code_Colum = 2;
	private static int newZoneKode_Colum = 8;
	private static int choice_Slekt_Colum = 9;
	private static int rsult_Id_Colum = 10;
	private static Set<Integer> listWhithChangeRow ; 
	

	public static List<DiferentLabInMeasur> getListMeasuringWithDiferentLabByYear(String year){
		
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date dateStart = null,dateEnd = null;
		PersonStatusNew perStaNew;
		Workplace workpl;
		Laboratory labWorkplace;
		try {
			dateStart = sdfrmt.parse("01.01."+year);
			dateEnd = sdfrmt.parse("31.12."+year);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Measuring> listMeasur = MeasuringDAO.getValueMeasuringByStartdate_EndDate(dateStart, dateEnd);
		
		for (Measuring measuring : listMeasur) {
			perStaNew = PersonStatusNewDAO.getLastPersonStatusNewByPerson_YearSortByStartDate(measuring.getPerson(),  year);
			if(perStaNew != null) {
			workpl = perStaNew.getWorkplace();
			labWorkplace = workpl.getLab();
			
			if( labWorkplace.getLab_ID() != measuring.getLab().getLab_ID()) {
				DiferentLabInMeasur diferentLabInMeasur	= new DiferentLabInMeasur(labWorkplace, measuring, workpl, year, false);
				DiferentLabInMeasurDAO.setObjectDiferentLabInMeasur(diferentLabInMeasur);
		}
			}else {
				System.out.println("EGN PersonStatusNew is null - "+measuring.getPerson().getEgn());
			}
		}
		List<DiferentLabInMeasur> listMeasurWithDifLab = DiferentLabInMeasurDAO.getListDiferentLabInMeasurByYear(year);
		return listMeasurWithDifLab;
		
	}
	
	
	
	
	
	public static void getListPersonFromFile(JTextArea textArea, JPanel infoPanel, JPanel tablePane,
			JLayeredPane panel_AllSaerch, JScrollPane scrollPane, JTextField textFieldGodina,
			JButton btnBackToTable) {
		
		textArea.setText("");
		MeasurInOderLabFrame.viewInfoPanel();
		String godina = textFieldGodina.getText();
	
		if (!godina.isEmpty()) {
//		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		GeneralMethods.setWaitCursor(infoPanel);
		
		List<DiferentLabInMeasur> listMeasurWithDifLab = getListMeasuringWithDiferentLabByYear(godina);
		if (listMeasurWithDifLab.size() > 1) {

			int zoneID = ZoneDAO.getValueZoneByNameTerritory(getZonaFromRadioButtons()).getId_Zone();
			
	//			updateSectionPersonSave_Panel(listPersonFromFile);
			dataTable = addListMeasurWithDifLab( listMeasurWithDifLab);
			panel_infoPanelTablePanel( textArea, panel_AllSaerch, tablePane, scrollPane,
					textFieldGodina,zoneID);
			MeasurInOderLabFrame.viewTablePanel();
			btnBackToTable.setEnabled(false);
			MeasurInOderLabFrame.getBtn_SaveChangeToDBase().setEnabled(false);
			listWhithChangeRow = new HashSet<Integer>();
		}
		
		GeneralMethods.setDefaultCursor(infoPanel);
		}
//		return listPersonFromFile;
	}
	
	static void ActionListener_Btn_ReadMeasurInDifLab(JButton btn_ReadFileListPerson, JTextArea textArea,
			JPanel infoPanel, JPanel tablePane, JLayeredPane panel_AllSaerch, JScrollPane scrollPane,
			JTextField textFieldGodina, JButton btnBackToTable) {
		
		btn_ReadFileListPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				getListPersonFromFile(textArea, infoPanel, tablePane, panel_AllSaerch, scrollPane,
						textFieldGodina, btnBackToTable);
				
				
			}
		});
	}
	
	protected static String getZonaFromRadioButtons() {

		List<Zone> listZone = ZoneDAO.getAllValueZone();
		if (MeasurInOderLabFrame.getRdbtn_KodKZ1().isSelected()) {
			return listZone.get(0).getNameTerritory();
		}
		if (MeasurInOderLabFrame.getRdbtn_KodKZ2().isSelected()) {
			return listZone.get(1).getNameTerritory();
		}
		if (MeasurInOderLabFrame.getRdbtn_KodKZHOG().isSelected()) {
			return listZone.get(2).getNameTerritory();
		}
		
		return "";
	}
	
	public static Object[][] addListMeasurWithDifLab(List<DiferentLabInMeasur> listMeasurWithDifLab) {

		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Object[][] dataTable = new Object[listMeasurWithDifLab.size()][11];

//				"№", "Date", "EGN", "Name","Otdel", "Kod ",	"Doze", "LabMeas", "LabWorkpl",  "selekt"

		int zona = ZoneDAO.getValueZoneByNameTerritory(getZonaFromRadioButtons()).getId_Zone();
		Person person;
		int k = 0;
		for (DiferentLabInMeasur difLabMeas : listMeasurWithDifLab) {
			if(difLabMeas.getMeasur() != null) {
			person = difLabMeas.getMeasur().getPerson();
		
			dataTable[k][0] = (k + 1);
			dataTable[k][1] = sdfrmt.format(difLabMeas.getMeasur().getDate());
			dataTable[k][2] = person.getEgn();
			dataTable[k][3] = person.getFirstName() +" " +person.getSecondName()+" " +person.getLastName();
			dataTable[k][4] = difLabMeas.getWorkplace().getOtdel();
			dataTable[k][5] = PersonReferenceFrame.getLastKodeByPersonAndZone(person, zona);
			dataTable[k][6] = difLabMeas.getMeasur().getDoze();
			dataTable[k][7] = difLabMeas.getMeasur().getLab().getLab();
			dataTable[k][8] = difLabMeas.getLabWorkplace().getLab();
			dataTable[k][9] = difLabMeas.getCheck();
			dataTable[k][10] = difLabMeas.getDiferentLabInMeasur_ID();

			k++;
			}else {
				System.out.println("DiferentLabInMeasur_ID is null - "+difLabMeas.getDiferentLabInMeasur_ID());
			}
		}
			

		return dataTable;

	}
	
	static void panel_infoPanelTablePanel(JTextArea textArea, JLayeredPane panel_AllSaerch,
			JPanel tablePane, JScrollPane scrollPane, JTextField textField_svePerson_Year, int zoneID) {

//		final JScrollPane llPane = scrollPane;

		JButton btnBackToTable = MeasurInOderLabFrame.getBtnBackToTable();
		JButton btn_SaveChangeToDBase = MeasurInOderLabFrame.getBtn_SaveChangeToDBase();
		JButton btn_Export = MeasurInOderLabFrame.getBtn_Export();
		btn_Export.setEnabled(true);
		String[] columnNames = getTabHeader();
		

//		DefaultTableModel model = new DefaultTableModel(dataTable, columnNames);
		listWhithChangeRow = new HashSet<Integer>();
		table = new JTable();
		
//		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				
				if (table.getSelectedColumn() == choice_Slekt_Colum) {

					if ((boolean) model.getValueAt(getSelectedModelRow(table), choice_Slekt_Colum)) {
						model.setValueAt(false, getSelectedModelRow(table), choice_Slekt_Colum);
					} else {
						model.setValueAt(true, getSelectedModelRow(table), choice_Slekt_Colum);
					}
				}

				if (e.getClickCount() == 2 && table.getSelectedColumn() == egn_code_Colum) {

					GeneralMethods.setWaitCursor(tablePane);
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();
					System.out.println("egn " + reqCodeStr);
					btnBackToTable.setEnabled(true);
					btn_SaveChangeToDBase.setEnabled(false);
					btn_Export.setEnabled(false);
					Person person = PersonDAO.getValuePersonByEGN(reqCodeStr);
					textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson("", person, false, 0));

					MeasurInOderLabFrame.viewInfoPanel();

					GeneralMethods.setDefaultCursor(tablePane);
				}
			}
		});
		
		new TableFilterHeader(table, AutoChoices.ENABLED);

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				DefaultTableModel dtm = new DefaultTableModel(dataTable, columnNames) {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("rawtypes")
					private Class[] types2 = getCulumnClass();

					@SuppressWarnings({})
					@Override
					public Class<?> getColumnClass(int columnIndex) {
						return this.types2[columnIndex];
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
							fireTableCellUpdated(row, col);
							AddInUpdateList(row);
							
						}
					}

					public int getColumnCount() {
						return columnNames.length;
					}

					public int getRowCount() {
						return dataTable.length;
					}

				};

				table.setModel(dtm);
				table.setFillsViewportHeight(true);

				table.getColumnModel().getColumn(rsult_Id_Colum).setWidth(0);
				table.getColumnModel().getColumn(rsult_Id_Colum).setMinWidth(0);
				table.getColumnModel().getColumn(rsult_Id_Colum).setMaxWidth(0);
				table.getColumnModel().getColumn(rsult_Id_Colum).setPreferredWidth(0);
				
				 initColumnSizes(table);
				System.out.println("+++++++++++++ " + dataTable.length);

			}
		});

		table.getTableHeader().setReorderingAllowed(false);

		
		MeasurInOderLabFrame.viewTablePanel();
		
		tablePane.removeAll();
		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane(table);
		tablePane.add(scrollPane, BorderLayout.CENTER);
	}
	
	
		@SuppressWarnings("rawtypes")
		public static Class[] getCulumnClass() {
			Class[] types = { Integer.class, String.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, Boolean.class,Integer.class };
			return types;
		}

		static String[] getTabHeader() {
			String[] tableHeader = { " № ", "Дата на изм.", "ЕГН", "Име", "Отдел",  "ИД Код", "Доза","Лаб.Измер.", "Лаб.Картон", "Отбел.","N" };
			return tableHeader;
		}

		
		private static Object[] getlong() {
			int k=5;
			double d = 12.1232;
			Object[] types = { k, "1234567", "12345678901", "1234567890123456789012345678901234567890", "12345678901234567890", "123456",
					 d, "123456",   "123456",	 Boolean.TRUE, k};

			return types;
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
					Object[][] dataofTable = new Object[table.getRowCount()][10];
					for (int row = 0; row < table.getRowCount(); row++) {
						int modelRow = table.convertRowIndexToModel(row);
						
						System.out.println(table.getRowCount()+" "+row+" "+modelRow);
						
						for (int i = 0; i < 10; i++) {
							dataofTable[row][i] = table.getModel().getValueAt(modelRow, i);
					}
					}
					PersonReferenceExportToExcell.btnExportTableToExcell(dataofTable,
							getTabHeader(), button_Panel, "MeasuringReference");
					
				}
			}
		});
	}
	
	static void ActionListenerBtnBackToTable(JButton btnBackToTable, JTextArea textArea, JPanel tablePane,
			JLayeredPane panel_AllSaerch, JScrollPane scrollPane, JTextField textField_svePerson_Year) {
		JButton btn_SaveChangeToDBase = MeasurInOderLabFrame.getBtn_SaveChangeToDBase();
		JButton btn_Export = MeasurInOderLabFrame.getBtn_Export();
		btnBackToTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dataTable != null) {
					GeneralMethods.setWaitCursor(tablePane);
					btnBackToTable.setEnabled(false);
					btn_SaveChangeToDBase.setEnabled(true);
					btn_Export.setEnabled(true);
					MeasurInOderLabFrame.viewTablePanel();
					GeneralMethods.setDefaultCursor(tablePane);

				}
			}

		});

	}
	
	private static void AddInUpdateList(int row) {
		if (listWhithChangeRow.isEmpty()) {
			listWhithChangeRow.add(row);
		} else {
			listWhithChangeRow.add(row);
			}
		MeasurInOderLabFrame.getBtn_SaveChangeToDBase().setEnabled(true);
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

	        for (int i = 0; i < tbl_Colum; i++) {
	            column = table.getColumnModel().getColumn(i);

	            comp = headerRenderer.getTableCellRendererComponent(
	                                 null, column.getHeaderValue(),
	                                 false, false, 0, 0);
	            headerWidth = comp.getPreferredSize().width;

	            comp = table.getDefaultRenderer(model.getColumnClass(i)).
	                             getTableCellRendererComponent(
	                                 table, longValues[i],
	                                 false, false, 0, i);
	            cellWidth = comp.getPreferredSize().width;

	           

	            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
//	            column.sizeWidthToFit(); //or simple
	        }
	    }





	public static void ActionListenerBtnSaveChangeToDBase(JButton btn_SaveChangeToDBase, JPanel tablePane) {
		btn_SaveChangeToDBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dataTable != null && !listWhithChangeRow.isEmpty()) {
					GeneralMethods.setWaitCursor(tablePane);
				
					for (int changeRow : listWhithChangeRow) {
						System.out.println(changeRow+" "+rsult_Id_Colum+" "+dataTable[changeRow][rsult_Id_Colum]);
						DiferentLabInMeasur difLabInMeasur = DiferentLabInMeasurDAO.getListDiferentLabInMeasurByID((int) dataTable[changeRow][rsult_Id_Colum]);
						difLabInMeasur.setCheck((boolean) dataTable[changeRow][choice_Slekt_Colum]);
						DiferentLabInMeasurDAO.updateValueDiferentLabInMeasur(difLabInMeasur);
							}
					GeneralMethods.setDefaultCursor(tablePane);
					MeasurInOderLabFrame.getBtn_SaveChangeToDBase().setEnabled(false);
					listWhithChangeRow = new HashSet<Integer>();

				}
			}

		});

		
	}





	public static void ActionListenerChengePanelSizeByFrameSize(MeasurInOderLabFrame measurInOderLabFrame,
			JPanel tablePane, JPanel infoPanel) {
		
		measurInOderLabFrame.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				int with = measurInOderLabFrame.getWidth();
				int heigth = measurInOderLabFrame.getHeight();
				tablePane.setBounds(0, 0, with-25, heigth-180);
				infoPanel.setBounds(0, 0, with-25, heigth-180);
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	 
	
	 
	
}
