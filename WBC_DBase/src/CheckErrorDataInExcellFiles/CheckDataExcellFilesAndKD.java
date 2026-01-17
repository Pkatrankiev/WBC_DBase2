package CheckErrorDataInExcellFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.chrome.ChromeDriver;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.ResourceLoader;
import BasicClassAccessDbase.Person;
import PersonReferenceInWebKD.PersonReferenceInWebKD_Methods;
import PersonReference_OID.OID_PersonReferenceFrame;

public class CheckDataExcellFilesAndKD {
	static ChromeDriver driver;
	static String curentYear = AplicationMetods.getCurentYear();

	public static void ActionListener_Btn_KodeAndNameKD(JPanel panel_AllSaerch, JButton btn_CheckPersonStatus,
			JTextArea textArea, JProgressBar progressBar) {
		btn_CheckPersonStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

							textArea.setText("");
							boolean NOTviewBrauser = false;
							if(ReadFileBGTextVariable.getGlobalTextVariableMap().get("changeKodeInKD_NOTviewBrauser").equals("1")) {
								NOTviewBrauser = true;
							}
							driver = PersonReferenceInWebKD_Methods.openChromeDriver(NOTviewBrauser);
							if (PersonReferenceInWebKD_Methods.logInToWebSheet(null, driver)) {
								System.out.println("--------------------------------");
								new MySwingWorker(progressBar, textArea, panel_AllSaerch, "CheckDataExcellFilesAndKD").execute();;
										
									}else {
										driver.quit();
									}

			}
		});

	}

	public static String checkPersonNameKodeStatusInKD(JProgressBar aProgressBar, JPanel panel_AllSaerch) {

		GeneralMethods.setWaitCursor(panel_AllSaerch);
		String[][] masive = new String[2][6];

		List<String> listMasive = new ArrayList<String>();

		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");

		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if (testFilesToD.equals("1")) {
			filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
			filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}

		double ProgressBarSize = 1;

		String filePath[] = { filePathPersonel, filePathExternal };
//		String filePath[] = { filePathExternal };

		Person person;

		String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "";
//		String kodeT1 = "", kodeT2 = "";
		String EGN = "", FirstName = "";
		String strText;
		Cell cell, cell1;
		Set<String> mySet = new HashSet<String>();
		boolean notEquals = false;
		boolean setinMasive = false;
		System.out.println("/////////////////////////////");
		for (int ii = 0; ii < filePath.length; ii++) {

			if (filePath[ii].contains("EXTERNAL")) {
				listMasive.add("IN EXTERNAL FILE");
			} else {
				listMasive.add("IN PERSONEL FILE");
			}

			double stepForProgressBar = 50;

			Workbook workbook = ReadExcelFileWBC.openExcelFile(filePath[ii]);
			Sheet sheet = workbook.getSheetAt(0);
			stepForProgressBar = stepForProgressBar / sheet.getLastRowNum();
//			System.out.println(stepForProgressBar+"*******************************************");  
//			System.out.println(sheet.getLastRowNum());
			for (int row = 240; row <= sheet.getLastRowNum(); row += 1) {
				aProgressBar.setValue((int) ProgressBarSize);
//				 System.out.println(ProgressBarSize+"  -------------------------------------------------------");
				ProgressBarSize += stepForProgressBar;
				System.out.println(row);
				notEquals = false;
				setinMasive = false;
				kodeKZ1 = "";
				kodeKZ2 = "";
				kodeHOG = "";
//				kodeT1 = "";
//				kodeT2 = "";
				if (sheet.getRow(row) != null) {
					cell = sheet.getRow(row).getCell(5);
					cell1 = sheet.getRow(row).getCell(6);

					if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {

						person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
						EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
						if (mySet.add(EGN)) {
							for (int j = 0; j < 6; j++) {
								masive[0][j] = "";
								masive[1][j] = "";
							}
							FirstName = ReadExcelFileWBC.getStringEGNfromCell(cell1);
							if (person != null) {
								masive[0][0] = EGN;
								masive[0][1] = FirstName;
								cell = sheet.getRow(row).getCell(0);
								if (cell != null)
									kodeKZ1 = cell.getStringCellValue();

								cell = sheet.getRow(row).getCell(1);
								if (cell != null)
									kodeKZ2 = cell.getStringCellValue();

								cell = sheet.getRow(row).getCell(2);
								if (cell != null)
									kodeHOG = cell.getStringCellValue();

//								cell = sheet.getRow(row).getCell(3);
//								if (cell != null)
//									kodeT2 = cell.getStringCellValue();
//
//								cell = sheet.getRow(row).getCell(4);
//								if (cell != null)
//									kodeT1 = cell.getStringCellValue();
//
//								if (filePath[ii].contains("EXTERNAL")) {
//									kodeT1 = kodeT2;
//									kodeT2 = "";
//								}

								if (!kodeKZ1.equals("ЕП-2") && !kodeKZ1.trim().equals("") && !kodeKZ1.equals("н")
										&& !ReadKodeStatusFromExcelFile.inCodeNotNumber(kodeKZ1)) {
									masive[0][2] = kodeKZ1;
								}
								if (!kodeKZ2.equals("н") && !kodeKZ2.trim().equals("")
										&& !ReadKodeStatusFromExcelFile.inCodeNotNumber(kodeKZ2)) {
									masive[0][3] = kodeKZ2;
								}
								if (!kodeHOG.equals("н") && !kodeHOG.trim().equals("")
										&& !ReadKodeStatusFromExcelFile.inCodeNotNumber(kodeHOG)) {
									masive[0][4] = kodeHOG;
								}
//								if (!kodeT1.equals("н") && !kodeT1.trim().equals("") && kodeT1.indexOf("ЯГ") < 0
//										&& !ReadKodeStatusFromExcelFile.inCodeNotNumber(kodeT1)) {
//									masive[0][5] = kodeT1;
//								}
//								if (!kodeT2.equals("н") && !kodeT2.trim().equals("") && kodeT2.indexOf("ЯГ") < 0
//										&& !ReadKodeStatusFromExcelFile.inCodeNotNumber(kodeT2)) {
//									masive[0][6] = kodeT2;
//								}
								setinMasive = true;
							}
						}
					}
				}
				System.out.println("***************************************");
				if (setinMasive) {
					System.out.println(EGN+" /////////////////////////////");
					String[] masivePerson = PersonReferenceInWebKD_Methods.extractedMasiveInfoPerson(driver, "", "",	"", EGN);
//					if (masivePerson != null) {
						if (masivePerson[0] != null) {
						System.out.println("masivePerson[0] "+masivePerson[0]);
						String[] namestr = masivePerson[0].split("&&");
						for (int i = 0; i < namestr.length; i++) {
							namestr[i] = namestr[i].trim();
							System.out.println(i+" "+namestr[i]);
						}
						masive[1][1] = namestr[0];
						if (namestr[1].isEmpty()) {
							masive[1][1] += " " + namestr[2];
						} else {
							masive[1][1] += " " + namestr[1] + " " + namestr[2];
						}
						System.out.println("masive[1][1] "+masive[1][1]);
						
						System.out.println("masivePerson[6] "+masivePerson[6]);
						
						String[] kode = masivePerson[6].split("@2#");
						for (int i = 0; i < kode.length; i++) {
							System.out.println(i+" "+kode[i]);
						}
						for (int i = 0; i < kode.length; i++) {
							String[] zoneKode = kode[i].split("@#");
							
							for (int f = 0; f < zoneKode.length; f++) {
								zoneKode[f] = zoneKode[f].trim();
								System.out.println(f+" "+zoneKode[f]);
							}
							switch (zoneKode[0]) {
							
							case "КЗ-1": {
								masive[1][2] = zoneKode[1];
							}
								break;
							case "КЗ-2": {
								masive[1][3] = zoneKode[1];
							}
								break;
							case "ХОГ": {
								masive[1][4] = zoneKode[1];
							}
								break;
					 	 	}	
							System.out.println("masive[1][2] "+masive[1][2]);
							System.out.println("masive[1][3] "+masive[1][3]);
							System.out.println("masive[1][4] "+masive[1][4]);
						}
						
					}

					for (int i = 0; i < masive[1].length; i++) {
						System.out.println(i+" - "+masive[1][i]);
					}
					
					for (int s = 1; s < 5; s++) {
						if (s == 1) {
							if (!masive[1][s].replace("*", "").contains(masive[0][s])) {
								masive[1][5] += s + ";";
//								System.out.println(masive[0][s]+" <-> "+masive[1][s]);
								notEquals = true;
							}
						} else {
							if (!masive[0][s].equals(masive[1][s].replace("*", ""))) {
								masive[1][5] += s + ";";
//							System.out.println(masive[0][s]+" <-> "+masive[1][s]);
								notEquals = true;
							}
						}
					}
					if (masive[1][5].length() > 0) {
						masive[1][5] = masive[1][5].substring(0, masive[1][5].length() - 1);
					}

					if (notEquals) {
//						System.out.println("Save in list");
						strText = extracted(masive);
//						System.out.println(strText);
						listMasive.add(strText);
						notEquals = false;
						appendToFile(strText);
					}

				}
			}

			if (listMasive.size() == 1) {
				listMasive = new ArrayList<String>();
			}
		}

		String infoStrText = "";
		if (listMasive.size() > 0) {
			String[][] ExtMasive = extractedMasive(listMasive);
			Integer[] columnWith = extractColumnWith(ExtMasive);

//		listMasiveForClear = listMasive;

			infoStrText = CheckPersonName_KodeStatus_DBseToExcelFiles.creadStringToInfoText(ExtMasive, columnWith);
		}

		GeneralMethods.setDefaultCursor(panel_AllSaerch);
		driver.quit();
		return infoStrText;
	}

	public static void appendToFile(String text) {
        try {
        	
            FileWriter New_File = new FileWriter("logKD.txt", true);
            BufferedWriter Buff_File = new BufferedWriter(New_File);
            try (PrintWriter Print_File = new PrintWriter(Buff_File, true)) {
//				Print_File.println("");
				 Print_File.println(text);
			}

        }
        catch (Exception e) {
        
            throw new RuntimeException("Cannot write the Exception to file", e);
        }
   }
	
	
	
	
	
	static String extracted(String[][] listMasive) {
		String sttr0 = "", sttr1 = "";
		for (int s = 0; s < 6; s++) {
			sttr0 += listMasive[0][s] + "&";
			sttr1 += listMasive[1][s] + "&";
		}
		if (sttr0.length() > 0) {
			sttr0 = sttr0.substring(0, sttr0.length() - 1);
		}
		if (sttr1.length() > 0) {
			sttr1 = sttr1.substring(0, sttr1.length() - 1);
		}
		return sttr0 = sttr0 + "@" + sttr1;
	}
	
	
	
	static String[][] extractedMasive(List<String> listMasive) {
		String[][] extrMasive = new String[listMasive.size() * 2][6];
		
		extrMasive[0] = new String[]{"ЕГН/IDкод", "Име(1)", "КЗ1(2)", "КЗ2(3)", "ХОГ(4)", "Опис Разлики"};
		 
		int i = 1;
		for (String str : listMasive) {
			if (str.endsWith("FILE")) {
				extrMasive[i][0] = str;
				for (int t = 1; t < 6; t++) {
					extrMasive[i][t] = "";
				}
				i++;

			} else {
				String[] masiv = str.split("@");
				extrMasive[i] = masiv[0].split("&");
				i++;
				extrMasive[i] = masiv[1].split("&");
				i++;
			}
		}
		return extrMasive;
	}
	
	public static Integer[] extractColumnWith(String[][] extMasive) {
		Integer[] columnWith = new Integer[6];
		for (int j = 0; j < columnWith.length; j++) {
			columnWith[j] = 0;
		}

		for (int i = 0; i < extMasive.length; i++) {
			for (int j = 0; j < extMasive[i].length; j++) {
				if (extMasive[i][j] != null && columnWith[j] < extMasive[i][j].length()) {
					columnWith[j] = extMasive[i][j].length();
				}
			}

		}
		return columnWith;
	}
	
	
}
