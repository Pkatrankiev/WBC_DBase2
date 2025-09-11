package CheckErrorDataInExcellFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.GeneralMethods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;

public class CheckEqualsForFirst5Column {

	static void ActionListener_Btn_SearchAllColumn5(JProgressBar aProgressBar, JPanel panel_AllSaerch, JButton btn_SearchAllColumn, JTextArea textArea) {

		btn_SearchAllColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				
				new MySwingWorker(aProgressBar, textArea, panel_AllSaerch, "CheckCorrectionAllRowInSheets").execute();
				
				
				
//				
//				GeneralMethods.setWaitCursor(panel_AllSaerch);
//
//				String textForArea = CheckCorrectionAllRowInSheets();
//
//				if (textForArea.isEmpty()) {
//					textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
//
//				} else {
//					textArea.setText(textForArea);
//
//				}
//
//				GeneralMethods.setDefaultCursor(panel_AllSaerch);
			}

		});

	}
	
	
	public static void ActionListener_Btn_SearchAllColumn(JProgressBar progressBar, JPanel panel_Search,
			JButton btn_SearchAllColumn, JTextArea textArea) {
		
		btn_SearchAllColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
		new MySwingWorker(progressBar, textArea, panel_Search, "CheckCorrectionAllRowInSheets").execute();
			}

		});
	}	
	
	
	public static String CheckCorrectionAllRowInSheets(JProgressBar aProgressBar, JPanel panel_AllSaerch) {
		String infotext = "";
		
		GeneralMethods.setWaitCursor(panel_AllSaerch);
		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");
		
		
		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if(testFilesToD.equals("1")) {
		filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
		filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}
		
		double ProgressBarSize = 0;
		double NewStepForProgressBar = 0;
		aProgressBar.setValue((int) ProgressBarSize);
		double stepForProgressBar = 100;
		
		String filePath[] = {filePathPersonel, filePathExternal };
		
		String fileName[] = { "Personel", "External" };
		
		NewStepForProgressBar = stepForProgressBar / filePath.length;
		
		for (int i = 0; i < filePath.length; i++) {
			Workbook workbook = ReadExcelFileWBC.openExcelFile(filePath[i]);
			String str1 = "", str2 = "", str3 = "", str4 = "";
			Sheet sheet1 = workbook.getSheetAt(0);
			Sheet sheet2 = workbook.getSheetAt(1);
			Sheet sheet3 = workbook.getSheetAt(2);
			Sheet sheet4 = workbook.getSheetAt(3);

			Cell cell1, cell2, cell3, cell4;
			
			stepForProgressBar = NewStepForProgressBar / sheet1.getLastRowNum();
			
			for (int row = 5; row <= sheet1.getLastRowNum(); row += 1) {

				if (sheet1.getRow(row) != null) {

					for (int col = 0; col <= 6; col++) {
//						System.out.println(row);

						if (sheet1.getRow(row) != null && sheet2.getRow(row) != null && sheet3.getRow(row) != null
								&& sheet4.getRow(row) != null) {

							cell1 = sheet1.getRow(row).getCell(col);
							cell2 = sheet2.getRow(row).getCell(col);
							cell3 = sheet3.getRow(row).getCell(col);
							cell4 = sheet4.getRow(row).getCell(col);

							if (ReadExcelFileWBC.CellNOEmpty(cell1) && ReadExcelFileWBC.CellNOEmpty(cell2)
									&& ReadExcelFileWBC.CellNOEmpty(cell3) && ReadExcelFileWBC.CellNOEmpty(cell4)) {

								str1 = ReadExcelFileWBC.getStringEGNfromCell(cell1);
								str2 = ReadExcelFileWBC.getStringEGNfromCell(cell2);
								str3 = ReadExcelFileWBC.getStringEGNfromCell(cell3);
								str4 = ReadExcelFileWBC.getStringEGNfromCell(cell4);

								if (str1.equals(str2) && str2.equals(str3) && str3.equals(str4)) {
//							System.out.println(row+" OK "+str1+" "+str2+" "+str3+" "+str4);
								} else {

									infotext += fileName[i] + " : " + (row + 1) + " NO " + str1 + " <-> " + str2
											+ " <-> " + str3 + " <-> " + str4 + "\n";
								}
							}
						}
					}
				}
				
				aProgressBar.setValue((int) ProgressBarSize);
				ProgressBarSize += stepForProgressBar;

				
			}
		}
		
		GeneralMethods.setDefaultCursor(panel_AllSaerch);
		
		return infotext;

	}

	

	
}
