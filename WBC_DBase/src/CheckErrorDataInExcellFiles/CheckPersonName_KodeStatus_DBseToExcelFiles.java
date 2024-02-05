package CheckErrorDataInExcellFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import PersonReference.TextInAreaTextPanel;

public class CheckPersonName_KodeStatus_DBseToExcelFiles {

	private static List<String> listMasiveForClear;
	static String curentYear = AplicationMetods.getCurentYear();
	
	public static void ActionListener_Btn_CheckDBaseNameKodeStat(JPanel panel_AllSaerch,
			JButton btn_CheckDBaseNameKodeStat, JTextArea textArea) {
		btn_CheckDBaseNameKodeStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				GeneralMethods.setWaitCursor(panel_AllSaerch);
				listMasiveForClear = new ArrayList<>();
				String textForArea = checkPersonNameKodeStatus();

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

	public static void ActionListener_Btn_CheckDBaseNameKodeStat_Clear(JPanel panel_AllSaerch,
			JButton btn_CheckDBaseNameKodeStat_Clear, JTextArea textArea) {
		btn_CheckDBaseNameKodeStat_Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GeneralMethods.setWaitCursor(panel_AllSaerch);

				System.out.println(listMasiveForClear.size());
				if (listMasiveForClear.size() > 0) {
					btn_CheckDBaseNameKodeStat_Clear(listMasiveForClear);
				}
				textArea.setText("");
				btn_CheckDBaseNameKodeStat_Clear.setEnabled(false);
				GeneralMethods.setDefaultCursor(panel_AllSaerch);
			}

		});
	}
	
	
	
	
	
	public static String checkPersonNameKodeStatus() {

		String[][] masive = new String[2][8];

		List<String> listMasive = new ArrayList<String>();

		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");

		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if (testFilesToD.equals("1")) {
			filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
			filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}

		String filePath[] = { filePathPersonel, filePathExternal };

		Person person;

		String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "", kodeT1 = "", kodeT2 = "";
		String EGN = "", FirstName = "";
		String strKods, strId, strText;
		Cell cell, cell1;
		Set<String> mySet = new HashSet<String>();
		int countMySet;
		boolean notEquals = false;
		boolean setinMasive = false;

		String infoStrText = "";

		for (int ii = 0; ii < filePath.length; ii++) {

			if (filePath[ii].contains("EXTERNAL")) {
				listMasive.add("IN EXTERNAL FILE");
			} else {
				listMasive.add("IN PERSONEL FILE");
			}

			Workbook workbook = ReadExcelFileWBC.openExcelFile(filePath[ii]);
			Sheet sheet = workbook.getSheetAt(0);

			System.out.println(sheet.getLastRowNum());
			for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {
//				System.out.println(row);
				notEquals = false;
				setinMasive = false;
				kodeKZ1 = "";
				kodeKZ2 = "";
				kodeHOG = "";
				kodeT1 = "";
				kodeT2 = "";
				if (sheet.getRow(row) != null) {
					cell = sheet.getRow(row).getCell(5);
					cell1 = sheet.getRow(row).getCell(6);

					if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {

						person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
						EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
						countMySet = mySet.size();
						mySet.add(EGN);
						if ((countMySet + 1) == mySet.size()) {
							for (int j = 0; j < 8; j++) {
								masive[0][j] = "";
								masive[1][j] = "";
							}
							FirstName = ReadExcelFileWBC.getStringEGNfromCell(cell1);
							if (person == null) {
//							System.out.println(EGN+" - "+FirstName);
//							ReadPersonStatusFromExcelFile.MessageDialog(EGN+" - "+FirstName);
							}
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

							cell = sheet.getRow(row).getCell(3);
							if (cell != null)
								kodeT2 = cell.getStringCellValue();

							cell = sheet.getRow(row).getCell(4);
							if (cell != null)
								kodeT1 = cell.getStringCellValue();

							if (filePath[ii].contains("EXTERNAL")) {
								kodeT1 = kodeT2;
								kodeT2 = "";
							}

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
							if (!kodeT1.equals("н") && !kodeT1.trim().equals("") && kodeT1.indexOf("ЯГ") < 0
									&& !ReadKodeStatusFromExcelFile.inCodeNotNumber(kodeT1)) {
								masive[0][5] = kodeT1;
							}
							if (!kodeT2.equals("н") && !kodeT2.trim().equals("") && kodeT2.indexOf("ЯГ") < 0
									&& !ReadKodeStatusFromExcelFile.inCodeNotNumber(kodeT2)) {
								masive[0][6] = kodeT2;
							}
							setinMasive = true;
						}
					}
				}

				if (setinMasive) {
					Person personBD = PersonDAO.getValuePersonByEGN(EGN);
					if (personBD != null) {
						masive[1][1] = personBD.getFirstName();
						if (personBD.getSecondName().isEmpty()) {
							masive[1][1] += " " + personBD.getLastName();
						} else {
							masive[1][1] += " " + personBD.getSecondName() + " " + personBD.getLastName();
						}
						strId = "";
						for (int k = 1; k < 6; k++) {
							strKods = "";
							List<KodeStatus> list = KodeStatusDAO.getListKodeStatusByPersonZoneYear(personBD, k, curentYear);
							for (int l = 0; l < list.size(); l++) {
								if (list.get(l).getKode().indexOf("ЯГ") < 0) {
									if (!list.get(l).getisFreeKode()) {
										strKods += list.get(l).getKode() + "*" + ";";
									} else {
										strKods += list.get(l).getKode() + ";";
									}
									strId += list.get(l).getKodeStatus_ID() + ";";
								}
							}
							if (strKods.length() > 0) {
								masive[1][k + 1] = strKods.substring(0, strKods.length() - 1);
							}
							if (strId.length() > 0) {
								masive[1][0] = strId.substring(0, strId.length() - 1);
							}
						}
					}

					for (int s = 1; s < 7; s++) {
						if (!masive[0][s].equals(masive[1][s].replace("*", ""))) {
							masive[1][7] += s + ";";
//							System.out.println(masive[0][s]+" <-> "+masive[1][s]);
							notEquals = true;
						}
					}
					if (masive[1][7].length() > 0) {
						masive[1][7] = masive[1][7].substring(0, masive[1][7].length() - 1);
					}
					
					if (notEquals) {
//						System.out.println("Save in list");
						strText = extracted(masive);
//						System.out.println(strText);
						listMasive.add(strText);
						notEquals = false;

					}

				}
			}

		}

		String[][] ExtMasive = extractedMasive(listMasive);
		Integer[] columnWith = extractColumnWith(ExtMasive);

		listMasiveForClear = listMasive;
		
		int i;
		for (String[] masiv : ExtMasive) {
			i = 0;
			for (String strings : masiv) {
				if (strings != null) {
					infoStrText = infoStrText + TextInAreaTextPanel.getAddSpace(columnWith[i], strings) + strings;
					i++;
				}
			}
			infoStrText = infoStrText + "\n";

		}

		return infoStrText;
	}

	private static Integer[] extractColumnWith(String[][] extMasive) {
		Integer[] columnWith = new Integer[8];
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

	private static String extracted(String[][] listMasive) {
		String sttr0 = "", sttr1 = "";
		for (int s = 0; s < 8; s++) {
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

	private static String[][] extractedMasive(List<String> listMasive) {
		String[][] extrMasive = new String[listMasive.size() * 2][8];
		
		extrMasive[0] = new String[]{"ЕГН/IDкод", "Име(1)", "КЗ1(2)", "КЗ2(3)", "ХОГ(4)", "Т1(5)", "Т2(6)", "Опис Разлики"};
		 
		int i = 1;
		for (String str : listMasive) {
			if (str.endsWith("FILE")) {
				extrMasive[i][0] = str;
				for (int t = 1; t < 8; t++) {
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

	private static void btn_CheckDBaseNameKodeStat_Clear(List<String> listMasive) {
		String[][] delMasive = new String[2][8];
		String[][] mm = new String[2][8];
		String kod = "";
		Person person;

		for (String str : listMasive) {

			if (!str.endsWith("FILE")) {
				String[] masiv = str.split("@");
				mm[0] = masiv[0].split("&");
				mm[1] = masiv[1].split("&");

				for (int i = 0; i < 8; i++) {
					if (i < mm[0].length) {
						delMasive[0][i] = mm[0][i];
					} else {
						delMasive[0][i] = "";
					}
					if (i < delMasive[1].length) {
						delMasive[1][i] = mm[1][i];
					} else {
						delMasive[1][i] = "";
					}
				}

				person = PersonDAO.getValuePersonByEGN(delMasive[0][0]);
				if (person != null) {

					if (delMasive[1][7].contains("2")) {
						kod = delMasive[1][2].replace(delMasive[0][2], "").replace(";", "");
						deleteKode(kod, person, 1);
					}
					if (delMasive[1][7].contains("3")) {
						kod = delMasive[1][3].replace(delMasive[0][3], "").replace(";", "");
						deleteKode(kod, person, 2);
					}
					if (delMasive[1][7].contains("4")) {
						kod = delMasive[1][4].replace(delMasive[0][4], "").replace(";", "");
						deleteKode(kod, person, 3);
					}

				}
			}
		}
	}

	private static void deleteKode(String kod, Person person, int zoneID) {
		KodeStatus kodeStat;
		System.out.println(
				person.getId_Person() + " - " + person.getEgn() + " - " + zoneID + " - " + "2024" + " - " + kod);
		kodeStat = KodeStatusDAO.getKodeStatusByPersonZoneYaerAndKode(person, zoneID, "2024", kod);
		if (kodeStat != null) {
			System.out.println(kodeStat.getPerson().getEgn() + " - " + kodeStat.getZone().getId_Zone() + " - " + "2024"
					+ " - " + kodeStat.getKode());
			KodeStatusDAO.deleteValueKodeStatus(kodeStat.getKodeStatus_ID());
		}
	}

}
