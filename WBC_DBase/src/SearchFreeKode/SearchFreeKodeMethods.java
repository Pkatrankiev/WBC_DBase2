package SearchFreeKode;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
 
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.RemouveDublikateFromList;
import BasiClassDAO.KodeGenerateDAO;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;
import BasicClassAccessDbase.KodeGenerate;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import PersonReference.TextInAreaTextPanel;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class SearchFreeKodeMethods {

	public static List<String> getMasiveZvenaFromDBase() {
		List<String> list = new ArrayList<>();
		List<KodeGenerate> listKG = KodeGenerateDAO.getAllValueKodeGenerate();
		for (KodeGenerate kodeGenerate : listKG) {
			list.add(kodeGenerate.getWorkplace().getOtdel() + "@" + kodeGenerate.getWorkplace().getSecondOtdelName());
		}
		List<String> newlist = RemouveDublikateFromList.removeDuplicates(new ArrayList<String>(list));
		Collections.sort(newlist);
		return newlist;
	}

	public static List<String> getMasiveZvenaFromExcellFiles() {
		List<String> list = new ArrayList<>();
		String[] filePath_ActualPersonalAndExternal = AplicationMetods.getDataBaseFilePat_ActualPersonalAndExternal();
		for (String pathFile : filePath_ActualPersonalAndExternal) {
			Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
			Sheet sheet = workbook.getSheetAt(0);
			if (sheet.getRow(0) != null) {
				int col = 0;
				Cell cell = sheet.getRow(0).getCell(col);
				while (cell != null && !cell.getStringCellValue().isEmpty()) {
					list.add(cell.getStringCellValue());
					col++;
					cell = sheet.getRow(0).getCell(col);
				}
			}
		}
		List<String> newlist = RemouveDublikateFromList.removeDuplicates(new ArrayList<String>(list));
		Collections.sort(newlist);
		System.out.println("getMasiveZvenaFromExcellFiles " + list.size());
		return newlist;
	}

	@SuppressWarnings("rawtypes")
	public static List<String> generateListZvena() {
		List<String> list = new ArrayList<>();
		List<String> listZvenaFromDBase = getMasiveZvenaFromDBase();
		List<String> listZvenaFromExcellFiles = getMasiveZvenaFromExcellFiles();
		boolean fl;
		for (String excelZveno : listZvenaFromExcellFiles) {
			fl = true;
			for (Iterator iterator = listZvenaFromDBase.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				String[] str = string.split("@", 2);
				if (excelZveno.equals(str[0]) || excelZveno.equals(str[1])) {
					list.add(excelZveno);
					iterator.remove();
					fl = false;
				}
			}
			if (fl) {
				System.out.println("-> " + excelZveno);
			}
		}

		System.out.println("generateListZvena " + list.size());
		return list;

	}

	static String[] getHeader() {
		String[] year = { textCurentYear(0), textCurentYear(1), textCurentYear(2) };
		return year;
	}

	static String[][] createDataTableMasive(String leter, int start, int end, int zone_ID, String[] year,
			List<String> masiveUsedKode) {
		List<String> listKode = generateListKode(leter, start, end, zone_ID);
		String[][] masiveFreeKode = creatyEmptyMasive(new String[listKode.size()][4]);
		for (int i = 1; i <= year.length; i++) {
			System.out.println(year[i - 1]);
			masiveFreeKode = generateListForFreeKode(masiveFreeKode, listKode, year[i - 1], leter, zone_ID, i);
		}
		String[][] newMasive = creatyEmptyMasive(new String[masiveFreeKode.length][4]);

		int k = 0;
		for (int i = 0; i < masiveFreeKode.length; i++) {
			if (!masiveFreeKode[i][1].isEmpty()) {
				for (int m = 1; m < masiveFreeKode[0].length; m++) {
					newMasive[k][m - 1] = masiveFreeKode[i][m];
					System.out.println(i + "  " + masiveFreeKode[i][1]);
				}
				k++;
			}
		}
		int maxMasiveUsedKode = masiveUsedKode.size();
		if (k < maxMasiveUsedKode) {
			k = maxMasiveUsedKode;
		}

		String[][] newMasiveFreeKode = creatyEmptyMasive(new String[k][4]);
		for (int i = 0; i < k; i++) {
			newMasiveFreeKode[i] = newMasive[i];
			if(i < maxMasiveUsedKode) {
			newMasiveFreeKode[i][3] = masiveUsedKode.get(i);
			}
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

	static JTable panel_infoPanelTablePanel(String[][] dataTable, String[] year, String zveno) {
		String[] columnNames = new String[year.length + 1];
		int k = 0;
		for (String string : year) {
			columnNames[k] = string;
			k++;
		}

		columnNames[k] = "used kode";

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
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();

				int col = table.columnAtPoint(e.getPoint());
				if(col>0) {
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), col).toString();

					String selectYear ="";
					if(reqCodeStr.isEmpty() || col==3 ) {
					if(col==3) {
						selectYear = year[0];
						
					}else {
						
					 selectYear = year[col];	
					col =0;
					}
					
					final String setYear = selectYear;	
					final int selectcol = col;
				JFrame parent = new JFrame();
				
			
				int[] sizeInfoFrame = {550, 300};
				int[] Coord = getCurentKoordinates(sizeInfoFrame);
				ActionIcone round = new ActionIcone();
				 final Thread thread = new Thread(new Runnable() {
				     @Override
				     public void run() {
				    	 
				    	 String reqCodeStr2 = model.getValueAt(getSelectedModelRow(table), selectcol).toString();
				    	 System.out.println(setYear+"------------------" +selectcol+ "++++++++++++++++++"+reqCodeStr2);
				    	 String textForInfoFrame = generateTextForInfoFrame(setYear, reqCodeStr2, zveno);
				    	 System.out.println(" --7777777777777777777777777 " +textForInfoFrame);
							new infoFrame(parent, Coord, textForInfoFrame, sizeInfoFrame, round);
				    	     	
				     }
				    });
				    thread.start();	
				
				
				}else {
					copyToClipboard(reqCodeStr);
				}
				}
			}
		});

		new TableFilterHeader(table, AutoChoices.ENABLED);

		dtm.fireTableDataChanged();
		table.setModel(dtm);
		table.setFillsViewportHeight(true);
		table.repaint();
		System.out.println("+++++++++++++ " + dataTable.length);

		TableColumn tColumn;
		Font colFond = table.getFont();
		Color backgroundColor = table.getBackground();
		
		for (int i = 0; i < columnNames.length; i++) {
			System.out.println(i);
			setColumnWidth(table, i, 50);
		}
		setColumnWidth(table, columnNames.length - 1, 80);
		
		tColumn = table.getColumnModel().getColumn(columnNames.length - 1);
		tColumn.setCellRenderer(new ColumnColorRenderer(backgroundColor, Color.red,
				new Font(colFond.getName(), Font.BOLD, colFond.getSize())));
		
//		tColumn = table.getColumnModel().getColumn(columnNames.length - 1);
//	
//		tColumn.setMaxWidth(Integer.MAX_VALUE);
		
		

		return table;

	}

	protected static String generateTextForInfoFrame(String year, String reqCodeStr, String zveno) {
		String text = reqCodeStr.replaceAll(" ", "");
		String egn = "";
		List<String[]> listKodeStatus = ReadKodeStatusFromExcelFile.generateListFromMasiveEGNandKode(zveno);
		int k = 0;
		while (k < listKodeStatus.size()  ) {
		
		String[] strings = listKodeStatus.get(k);
		System.out.println(k+ " egn "+strings[4]);
			for (int i = 0; i < 5; i++) {
			if(strings[i].equals(text))	{
				egn = strings[5];	
			}
			}
			k++;
		}
		Person person = PersonDAO.getValuePersonByEGN(egn);
		
		text = TextInAreaTextPanel.createInfoPanelForPerson(year, person, true);
		
	
		
		return text;
	}

	public static int[] getCurentKoordinates(int[] size) {
		int[] koordinates = new int[2];
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int koordWidth = gd.getDisplayMode().getWidth();
		int koorHeight = gd.getDisplayMode().getHeight();
		
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x = (int) b.getX()+10;
		int y = (int) b.getY()+10;
		
		if((x+size[0])>koordWidth)
			x=x-size[0];
		if(x<0){
			x=0;
		}
		if((y+size[1])>koorHeight)
			y=y-size[1];
		if(y<0){
			y=0;
		}
		koordinates[0] = x;
		koordinates[1] = y;
		return koordinates;
	}
		
	private static void setColumnWidth(final JTable table, int i, int cWidth) {
		table.getColumnModel().getColumn(i).setMinWidth(cWidth);
		table.getColumnModel().getColumn(i).setMaxWidth(cWidth);
		table.getColumnModel().getColumn(i).setPreferredWidth(cWidth);
	}

	private static int getSelectedModelRow(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}

	public static void copyToClipboard(String text) {
		java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(new java.awt.datatransfer.StringSelection(text), null);
	}

	private static String[][] generateListForFreeKode(String[][] masiveFreeKode, List<String> listKode, String year,
			String leter, int zone_ID, int index) {
		List<String> sublistKodeStat = new ArrayList<>();
		if (index == 1) {
			String[] listKodeFromExcell = ReadKodeStatusFromExcelFile.getUsedKodeFromExcelFile(zone_ID);
			System.out.println(year + "   " + zone_ID + "  " + listKodeFromExcell.length);
			sublistKodeStat = getSubListByLeter(listKodeFromExcell, leter);
		} else {
			List<KodeStatus> listKodeStat = KodeStatusDAO.getKodeStatusByYearZone(year, zone_ID);
			System.out.println(year + "   " + zone_ID + "  " + listKodeStat.size());
			sublistKodeStat = getSubListByLeter(listKodeStat, leter);
		}

		masiveFreeKode = chekFreeKode(masiveFreeKode, sublistKodeStat, listKode, index);

		return masiveFreeKode;
	}

	@SuppressWarnings("rawtypes")
	private static String[][] chekFreeKode(String[][] masiveFreeKode, List<String> sublistKodeStat,
			List<String> listKode, int index) {

		boolean fl = false;
		if (sublistKodeStat != null && sublistKodeStat.size() > 0) {
			int k = 0;
			for (String lkode : listKode) {
				masiveFreeKode[k][0] = lkode;
				fl = false;
				for (Iterator iterator = sublistKodeStat.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					if (lkode.equals(string)) {
						fl = true;
						iterator.remove();
					}
				}
				if (!fl) {
					masiveFreeKode[k][index] = lkode;
				}
				k++;
			}
		}
		return masiveFreeKode;
	}

	private static List<String> getSubListByLeter(List<KodeStatus> listKodeStat, String leter) {
		List<String> listStringKode = new ArrayList<>();
		for (KodeStatus kodStat : listKodeStat) {
			if (kodStat.getKode().contains(leter)) {
				listStringKode.add(kodStat.getKode());
			}
		}
		return listStringKode;
	}

	private static List<String> getSubListByLeter(String[] masiveKode, String leter) {
		List<String> listStringKode = new ArrayList<>();
		for (String kod : masiveKode) {
			if (kod.contains(leter)) {
				listStringKode.add(kod);
			}
		}
		return listStringKode;
	}

	private static List<String> generateListKode(String leter, int start, int end, int zone_ID) {
		List<String> listKode = new ArrayList<>();
		switch (zone_ID) {
		case 1: {
			for (int i = start; i < end; i++) {
				listKode.add(i + leter);
			}
		}
			break;
		case 2: {
			for (int i = start; i < end; i++) {
				listKode.add(leter + i);
			}
		}
			break;
		case 3: {
			for (int i = start; i < end; i++) {
				listKode.add("Н" + i + leter);
			}
		}
			break;
		case 4: {
			for (int i = start; i < end; i++) {
				listKode.add("Т" + i + leter);
			}
		}
			break;
		case 5: {
			for (int i = start; i < end; i++) {
				listKode.add(leter + i + "Т");
			}
		}
			break;
		}
		return listKode;
	}

	@SuppressWarnings("deprecation")
	public static String textCurentYear(int corection) {
		return Calendar.getInstance().getTime().getYear() + 1900 - corection + "";

	}

}

//Customize the code to set the background and foreground color for each column of a JTable
@SuppressWarnings("serial")
class ColumnColorRenderer extends DefaultTableCellRenderer {
	Color backgroundColor, foregroundColor;
	Font colFond;

	public ColumnColorRenderer(Color backgroundColor, Color foregroundColor, Font colFond) {
		super();
		this.backgroundColor = backgroundColor;
		this.foregroundColor = foregroundColor;
		this.colFond = colFond;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		cell.setBackground(backgroundColor);
		cell.setForeground(foregroundColor);
		cell.setFont(colFond);
		return cell;
	}
}
