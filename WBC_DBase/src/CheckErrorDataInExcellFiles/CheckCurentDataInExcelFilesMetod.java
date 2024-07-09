package CheckErrorDataInExcellFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;

public class CheckCurentDataInExcelFilesMetod {

	
	public static void ActionListener_Btn_CheckCurentDataInExcelFiles(JPanel panel_AllSaerch,
			JButton btn_CheckDBaseNameKodeStat, JTextArea textArea) {
		btn_CheckDBaseNameKodeStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				GeneralMethods.setWaitCursor(panel_AllSaerch);
				
				String[][] ExtMasive = CheckCurentDataInExcelFiles();
				Integer[] columnWith = CheckPersonName_KodeStatus_DBseToExcelFiles.extractColumnWith(ExtMasive);

				String textForArea = CheckPersonName_KodeStatus_DBseToExcelFiles.creadStringToInfoText(ExtMasive, columnWith);
				
				if (textForArea.isEmpty()) {
					textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
					CheckErrorDataInExcellFiles_Frame.getBtn_CheckDBaseNameKodeStat_Clear().setEnabled(false);
				} else {
					textArea.setText(textForArea);
					CheckErrorDataInExcellFiles_Frame.getBtn_CheckDBaseNameKodeStat_Clear().setEnabled(true);
				}

				GeneralMethods.setDefaultCursor(panel_AllSaerch);
			}

		});
		
	}
	
	
	
	public static String[][] CheckCurentDataInExcelFiles() {
		List<String> list = new ArrayList<>();
		List<String> listEGN = new ArrayList<>();
		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");

		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if (testFilesToD.equals("1")) {
			filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
			filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}
		TreeSet<Object> set = new TreeSet<>();
		String filePath[] = { filePathPersonel, filePathExternal };
		Cell cell, cell1;
		String EGN, FirstName;
		list.add("Ред#Колона#Текст#Грешка");
		for (int ii = 0; ii < filePath.length; ii++) {
			if(list.size() == 2) {
				list = new ArrayList<>();
				list.add("Ред#Колона#Текст#Грешка");
			}
			list.add(filePath[ii]+"# # ");
			int rowSOYAG = 50000;
		System.out.println("*****************************************");
		System.out.println();
			Workbook workbook = ReadExcelFileWBC.openExcelFile(filePath[ii]);

			Sheet sheet = workbook.getSheetAt(0);

			for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

				if (sheet.getRow(row) != null) {
					cell = sheet.getRow(row).getCell(5);
					cell1 = sheet.getRow(row).getCell(6);

					if(ReadExcelFileWBC.getStringfromCell(cell1).equals("Транспортиране на СЯГ и ОЯГ")
							|| ReadExcelFileWBC.getStringfromCell(cell1).equals("Транспорт СОЯГ")) {
						rowSOYAG = row;
					}
					if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
						
						EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
						String str;
						try {
							Long.parseLong(EGN);
							FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
							if(row < rowSOYAG && !set.add(EGN) ) {
								str = "дублиран ЕГН";
								if(FirstName.contains("АЕЦ")) {
									str += " - преместен в АЕЦ";
								}
								listEGN.add(row + "#" + 5 + "#" + EGN + "#" + str);	
							}
						} catch (Exception e) {
							str = row + "#" + 5 + "#" + EGN+ "#" + "некоректно ЕГН";
							list.add(str);
							System.out.println(str);
						}
						
						list = checkDoza(sheet, row, list);
						list = checkMeasur(workbook, row, list);
						list = checkPersonStat(workbook, row, list);

					}
				}

			}
			System.out.println("rowSOYAG "+rowSOYAG+" "+list.size());
			
			
			
		}
		
		String[][] masive = new String[list.size()+listEGN.size()][3];
		int i=0;
		for (String string : list) {
		masive[i] = string.split("#");
		i++;
		}
		
		
		for (String string : listEGN) {
			masive[i] = string.split("#");
			i++;
			System.out.println(string);
			
		}
		return masive;
	}
	

	private static List<String> checkDoza(Sheet sheet, int row, List<String> list) {
	
		String str,col;
		String doze;
		Cell cell_Doze;
		for (int mont = 0; mont < 12; mont++) {
			cell_Doze = sheet.getRow(row).getCell(mont + 77);
			col = (mont + 77)+"";
			doze = ReadExcelFileWBC.getStringEGNfromCell(cell_Doze);
 			if (!doze.isEmpty()) {
				try {
					Double.parseDouble(doze);
				} catch (Exception e) {
					if(!doze.equals("<0.10") && !doze.equals("M")) {
				str = row + "#" + col + "#" + doze + "#" + "некоректна Доза";
				list.add(str);
				System.out.println(str);
					}
				}
			}
		}
		return list;
	
	}
	
		
	private static List<String> CheckDate(int row, int k, Cell cell1, String ttext, List<String> list) {
		String str;
		if (cell1 != null) {
			String type = cell1.getCellType().toString();
			switch (type) {
			case "STRING": {
				if(isNotLegalDate(cell1.getStringCellValue(), cell1)) {
					str = row+"#"+k+"#"+ttext+cell1.getStringCellValue() + "#" + "некоректна Дата";
					list.add(str);
					System.out.println(str);
				}
			}
				break;
			case "DATA":
			case "NUMERIC": {
				try {
				cell1.getDateCellValue();
				} catch (Exception e) {
					str = row+"#"+k+"#"+ttext+cell1.getStringCellValue() + "#" + "некоректна Дата";
					list.add(str);
					System.out.println(str);
				}
			}
				break;

			}
		}
		return list;
	}
	
	private static List<String> checkPersonStat(Workbook workbook, int row, List<String> list) {
		
		
		Sheet sheet = workbook.getSheetAt(3);
		
		int k = 7;
		Cell cell = sheet.getRow(row).getCell(k);
		
				
		while (ReadExcelFileWBC.CellNOEmpty(cell)) {
			
			
			k++;
			cell = sheet.getRow(row).getCell(k);
			if (ReadExcelFileWBC.CellNOEmpty(cell)
					&& !ReadExcelFileWBC.getStringfromCell(cell).isEmpty()) {
			
						
				list = CheckDate(row,  k,  cell, " StartDatePerStat ", list);
			}
			k++;
			cell = sheet.getRow(row).getCell(k);
			if (ReadExcelFileWBC.CellNOEmpty(cell)
					&& !ReadExcelFileWBC.getStringfromCell(cell).isEmpty()) {
				list = CheckDate(row,  k,  cell, " endDatePerStat ", list);
			}
			k++;
			cell = sheet.getRow(row).getCell(k);
			
			

		}
		return list;
	
	}
	
	private static List<String> checkMeasur(Workbook workbook, int row, List<String> list) {
		
		Sheet sheet = workbook.getSheetAt(1);
		int k = 7;
		
		Cell cell;
		String str, nuclideVal = "";
		String lab = "" ;
	Cell cell1 = sheet.getRow(row).getCell(k);
	k++;
	Cell cell2 = sheet.getRow(row).getCell(k);
	while (ReadExcelFileWBC.CellNOEmpty(cell1) && ReadExcelFileWBC.CellNOEmpty(cell2)) {
		
	
		list = CheckDate(row, k, cell1, " dateMeasur ", list);
	
	try {
	lab = ReadExcelFileWBC.getStringfromCell(cell2).trim();
	int indexLab = Integer.parseInt(lab.substring(lab.length()-1));
	if(indexLab < 1 && indexLab > 3) {
		str = row+"#"+k+"#"+lab + "#" + "некоректна лаборат.";
		list.add(str);
		System.out.println(str);
	}
	} catch (Exception e) {
		str = row+"#"+k+"#"+lab + "#" + "некоректна лаборат.";
		list.add(str);
		System.out.println(str);
	}
	
	k++;
	
	for (int i = 0; i < 17; i++) {
		cell = sheet.getRow(row).getCell(k);
		if(cell!=null) {
			nuclideVal = ReadExcelFileWBC.getStringEGNfromCell(cell);
//			System.out.println(mont + " " + maxindexMonth[mont] + " " + EGN);
			if (!nuclideVal.isEmpty()) {
			
			try {
				Double.parseDouble(nuclideVal);
			} catch (Exception e) {
				if(!nuclideVal.equals("<0.10") && !nuclideVal.equals("M")) {
			
			str = row+"#"+k+"#"+nuclideVal + "#" + "некоректна стойност";
			list.add(str);
			System.out.println(str);
				}
			}
			
}else {
	if(i==16) {
		str = row+"#"+k+"#"+ "#" + "липсва стойност";
		list.add(str);
		System.out.println(str);
	}
}

			}else {
				if(i==16) {
					str = row+"#"+k+"#"+ "#" + "липсва стойност";
					list.add(str);
					System.out.println(str);
				}
			}
		
		k++;
	}
	
	if(k>253) {
		k=6;
		sheet = workbook.getSheetAt(2);
	}
	
	k++;
	cell1 = sheet.getRow(row).getCell(k);
	k++;
	cell2 = sheet.getRow(row).getCell(k);
	
	}
	return list;
	}

	
	public static boolean isNotLegalDate(String strDate, Cell cell) {
		
		Date dd;
		if (!strDate.trim().isEmpty()) {
			strDate = strDate.replaceAll("г.", "");
			strDate = strDate.replaceAll("г", "");
			strDate = strDate.trim();
			
			SimpleDateFormat sdfrmt = AplicationMetods.extractedSimleDateFormatFromDate(strDate);
			try {
				
					dd = sdfrmt.parse(strDate);
					if(!sdfrmt.format(dd).equals(strDate)) {
						return true;
					}
				
			} catch (Exception e) {
				System.out.println(strDate+" catch "+strDate.length());
			return true;
			}

		}

		return false;
	}

	
	
	
	
}
