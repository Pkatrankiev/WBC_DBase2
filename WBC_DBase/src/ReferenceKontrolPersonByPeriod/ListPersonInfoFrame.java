package ReferenceKontrolPersonByPeriod;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import Aplication.ActionIcone;
import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Person;
import PersonReference.PersonReferenceExportToExcell;
import PersonReference.TextInAreaTextPanel;
import UpdateDBaseFromExcelFiles.UpdateBDataFromExcellFiles;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class ListPersonInfoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JPanel panel_AllSaerch;
		private static JPanel tablePane;
	private static JScrollPane scrollPane;
	
	private static JButton btn_Export;
	

	
	
public  ListPersonInfoFrame ( List<String> listPersoninfo, Object otdel) {
	String ListPersonInfoFrame_Title = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("ListPersonInfoFrame_Title");
	setTitle(ListPersonInfoFrame_Title+" от "+otdel);
	setMinimumSize(new Dimension(780, 900));

	String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("main_Icon");
	setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconn)));

	ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults");
	
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	setContentPane(contentPane);
	contentPane.setLayout(new BorderLayout(0, 0));

	panel_AllSaerch = new JPanel();
	contentPane.add(panel_AllSaerch, BorderLayout.CENTER);
	panel_AllSaerch.setLayout(new BoxLayout(panel_AllSaerch, BoxLayout.Y_AXIS));


	tablePane = new JPanel();
	tablePane.setPreferredSize(new Dimension(10, 0));
	tablePane.setMaximumSize(new Dimension(32767, 0));
	panel_AllSaerch.add(tablePane);
	tablePane.setLayout(new BorderLayout(0, 0));

	scrollPane = new JScrollPane();
	tablePane.add(scrollPane, BorderLayout.CENTER);

	
	Object[][] masivePeson = new Object [listPersoninfo.size()][8];
	int k =0;
	String[] splString = new String[8];
	for (String string : listPersoninfo) {
		splString = string.split("#");
		splString[0] = k+1+"";
	
		for (int i = 0; i < 8; i++) {
			
			switch (i) {
		
			case 0:
			case 4:
			case 5:{
				masivePeson[k][i] = Integer.parseInt(splString[i]);
				}
				break;
			case 1:
			case 2:
			case 3: {
				masivePeson[k][i] = splString[i];
				
			}
				break;
	    	
			case 6:
			case 7:{
			masivePeson[k][i] = Double.parseDouble(splString[i]);
			
		}
			break;
			}
		}
		k++;
	}
	
//	for (int i = 0; i < masivePeson.length; i++) {
//		for (int j = 0; j < masivePeson[0].length; j++) {
//			System.out.print(masivePeson[i][j]+" ");
//		}
//		System.out.println();
//	}
	
	panel_infoPanelTablePanel(masivePeson);
	GeneralMethods.setDefaultCursor(panel_AllSaerch);
	

	

		String referencePerson_Export = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Export");

		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) buttonPanel.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		btn_Export = new JButton(referencePerson_Export);
		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listPersoninfo != null) {
					String[] header = Metods_ReferenceKontrolPersonByPeriod.refornatTableHeader(getTabHeaderName());
					PersonReferenceExportToExcell.btnExportTableToExcell(masivePeson, header, buttonPanel, "PersonReference");
	}
			}
		});
		buttonPanel.add(btn_Export);

	

	setSize(780, 900);
	setLocationRelativeTo(null);
	setVisible(true);
	GeneralMethods.setDefaultCursor(panel_AllSaerch);
	
}



	static void panel_infoPanelTablePanel(Object[][] dataTable) {
	String[] columnNames = getTabHeaderName();
	

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
			return dataTable[row][col];
		}

		@Override
		public void setValueAt(Object value, int row, int col) {

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
			if (e.getClickCount() == 2 && getSelectedModelRow(table) != -1) {
				GeneralMethods.setWaitCursor(panel_AllSaerch);
				String reqCodeStr = model.getValueAt(getSelectedModelRow(table), 2).toString();
				Person person = PersonDAO.getValuePersonByEGN(reqCodeStr);
				UpdateBDataFromExcellFiles.generateInfoPanel(TextInAreaTextPanel.createInfoPanelForPerson("", person, false, 0));

				GeneralMethods.setDefaultCursor(panel_AllSaerch);
			}
					
					
				
				
			
		}
	});

	new TableFilterHeader(table, AutoChoices.ENABLED);

	dtm.fireTableDataChanged();
	table.setModel(dtm);
	table.setFillsViewportHeight(true);
	
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
	


	private static String[] getTabHeaderName() {
//		име # ЕГН # код # Бр.Измервания # Бр.Измервания над МДА # СумаДоза # МаксДоза
		String zona = ReferenceKontrolPersonByPeroid_Frame.getComboBox_KodeZone().getSelectedItem();
	String[] tableHeader = { "<html><center> <br>№<br>'</html>", 
			"<html><center> <br>Име<br>'</html>",
			"<html><center> <br>ЕГН<br>'</html>",
			"<html><center>Код за<br>"+zona+"</html>",
			"<html><center>Брой<br>измервания</html>",
			"<html><center>Брой<br>измервания<br>над МДА</html>",
			"<html><center>Сума<br>доза<br>[mSv]</html>",
			"<html><center>Макс.<br>доза<br>[mSv]</html>",
			 };
	
	
	return tableHeader;
}

	@SuppressWarnings("rawtypes")
	private static Class[] getColumnClassTypes() {

		Class[] types = { Integer.class, 
				String.class, 
				String.class, 
				String.class, 
				Integer.class,
				Integer.class, 
				Double.class, 
				Double.class};

		return types;
	}	
	
	
	
	
  
    
	
}