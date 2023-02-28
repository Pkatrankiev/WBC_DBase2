package SearchFreeKode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Aplication.RemouveDublikateFromList;
import BasiClassDAO.KodeGenerateDAO;
import BasiClassDAO.KodeStatusDAO;
import BasicClassAccessDbase.KodeGenerate;
import BasicClassAccessDbase.KodeStatus;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;


public class SearchFreeKodeMethods {

	public static List<String> getMasiveZvenaFromDBase() {
		List<String> list = new ArrayList<>();
		List<KodeGenerate> listKG = KodeGenerateDAO.getAllValueKodeGenerate();
		for (KodeGenerate kodeGenerate : listKG) {
			list.add(kodeGenerate.getWorkplace().getOtdel());
		}
		return RemouveDublikateFromList.removeDuplicates(new ArrayList<String>(list));
	}

	
	public static String generateStringForFreeKode(String leter, int start, int end, int zone_ID) {
		
		String[] year = getHeader();
		 String[][] newMasiveFreeKode = createDataTableMasive(leter, start, end, zone_ID, year);
		
		 String str = "           "+ year[0] + "     "+ year[1] +"     "+ year[2] + "\n";
		
		
		 for (int i = 0; i < newMasiveFreeKode.length; i++) {
			for (int j = 0; j < 4; j++) {
				str = str + newMasiveFreeKode[i][j] +"     ";	
			}
			str = str +  "\n";
		}
		 
		return str;
	}


	static String[] getHeader() {
		String[] year = {textCurentYear(3), textCurentYear(4),textCurentYear(5)};
		return year;
	}


	static String[][] createDataTableMasive(String leter, int start, int end, int zone_ID, String[] year) {
		List<String> listKode = generateListKode(leter, start, end, zone_ID);
		 String [][] masiveFreeKode = creatyEmptyMasive(new String[listKode.size()][4]);
		for (int i = 1; i <= year.length; i++) {
			System.out.println(year[i-1]);
			masiveFreeKode = generateListForFreeKode( masiveFreeKode, listKode, year[i-1], leter, zone_ID, i);
		}
		String [][] newMasive = creatyEmptyMasive(new String[masiveFreeKode.length][4]) ;
		
		int k=0;
		 for (int i = 0; i < masiveFreeKode.length; i++) {
			 if(!masiveFreeKode[i][1].isEmpty()) {
				for(int m = 1; m < masiveFreeKode[0].length; m++) {
				 newMasive[k][m-1] = masiveFreeKode[i][m];
				 System.out.println(i+"  "+masiveFreeKode[i][1]);
				}
				 k++;
			 }
		 }
		 String [][] newMasiveFreeKode = creatyEmptyMasive(new String[k][4]); 
		 for (int i = 0; i < k; i++) {
			 newMasiveFreeKode[i] =  newMasive[i];
			 }
		return newMasiveFreeKode;
	}

	private static String[][] creatyEmptyMasive(String[][] strings) {
		for (int i = 0; i < strings.length; i++) {
		for (int j = 0; j < strings[i].length; j++) {
			strings[i][j] = "";	
		}	
		}
		return strings;
	}

	static JTable  panel_infoPanelTablePanel(String[][] dataTable, String[] year) {
		String[] columnNames = new String[year.length+1];
		int k =0;
		for (String string : year) {
		columnNames[k] = string;
		k++;
				}
		columnNames[k] ="";
	
	
	DefaultTableModel dtm = new DefaultTableModel();
	final JTable table = new JTable(dtm);
	
	table.setShowGrid(false);
	table.getTableHeader().setReorderingAllowed(false);
	
	table.setCellSelectionEnabled(true);
	
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
		public void mouseReleased(MouseEvent e) {}

		public void mousePressed(MouseEvent e) {
			DefaultTableModel model =(DefaultTableModel) table.getModel();

				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				String reqCodeStr = model.getValueAt(getSelectedModelRow(table), col).toString();
				copyToClipboard(reqCodeStr);
			System.out.println(reqCodeStr+" "+ row +" "+ col);	
						}
	});

	new TableFilterHeader(table, AutoChoices.ENABLED);
	
	
			
			dtm.fireTableDataChanged();
			table.setModel(dtm);
			table.setFillsViewportHeight(true);
			 table.repaint();
			 System.out.println("+++++++++++++ "+dataTable.length);
			 
			 
			 System.out.println(columnNames.length);
				for (int i = 0; i < columnNames.length; i++) {
					System.out.println(i);
					table.getColumnModel().getColumn(i).setMinWidth(50);
					table.getColumnModel().getColumn(i).setMaxWidth(50);
					table.getColumnModel().getColumn(i).setPreferredWidth(50);
				}
				System.out.println(columnNames.length-1);
				table.getColumnModel().getColumn(columnNames.length-1).setMaxWidth(Integer.MAX_VALUE);

			 return table;
	

}
	
	 

	private static int getSelectedModelRow(JTable table) {
		return  table.convertRowIndexToModel(table.getSelectedRow());
		}	
	
	public static void copyToClipboard(String text) {
	    java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
	        .setContents(new java.awt.datatransfer.StringSelection(text), null);
	}

	
	private static String[][] generateListForFreeKode(String [][] masiveFreeKode, List<String> listKode, String year, String leter, int zone_ID, int index) {
		
		 List<KodeStatus> listKodeStat = KodeStatusDAO.getKodeStatusByYearZone(year, zone_ID);
		 System.out.println(year+"   "+ zone_ID+"  "+ listKodeStat.size());
		 List<String> sublistKodeStat = getSubListByLeter(listKodeStat, leter);
		
		masiveFreeKode = chekFreeKode( masiveFreeKode, sublistKodeStat, listKode, index);
		
		return masiveFreeKode;
	}


	@SuppressWarnings("rawtypes")
	private static String [][] chekFreeKode( String [][] masiveFreeKode,  List<String> sublistKodeStat, List<String> listKode, int index) {
		
		
		boolean fl = false;
		if(sublistKodeStat != null && sublistKodeStat.size()>0) {
		int k=0;
			for (String lkode : listKode) {
				masiveFreeKode[k][0] = lkode;
			fl = false;
		for (Iterator iterator = sublistKodeStat.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if(lkode.equals(string)) {
				fl = true;
				iterator.remove();
			}
		}
		 if(!fl) {
			 masiveFreeKode[k][index] = lkode ;
		 }
		 k++;
		}
		}
		return masiveFreeKode;
	}

	private static List<String> getSubListByLeter(List<KodeStatus> listKodeStat, String leter) {
		List<String> listStringKode = new ArrayList<>();
		for (KodeStatus kodStat : listKodeStat) {
			if(kodStat.getKode().contains(leter)) {
				listStringKode.add(kodStat.getKode());
			}
		}
		return listStringKode;
	}


	private static List<String> generateListKode(String leter, int start, int end, int zone_ID) {
		List<String> listKode = new ArrayList<>();
		 switch (zone_ID) {
			case 1: {
				for (int i = start; i < end; i++) {
					listKode.add(i+leter);	
				}
			}
			break;
			case 2: {
				for (int i = start; i < end; i++) {
					listKode.add(leter+i);	
				}
			}
			break;
			case 3: {
				for (int i = start; i < end; i++) {
					listKode.add("Н"+i+leter);	
				}
			}
			break;
			case 4: {
				for (int i = start; i < end; i++) {
					listKode.add("Т"+i+leter);	
				}
			}
			break;
			case 5: {
				for (int i = start; i < end; i++) {
					listKode.add(leter+i+"Т");	
				}
			}
			break;
			}
		return listKode;
	}

	@SuppressWarnings("deprecation")
	public static String textCurentYear (int corection) {
		return  Calendar.getInstance().getTime().getYear()+1900-corection+"";
	
	}
	
}
