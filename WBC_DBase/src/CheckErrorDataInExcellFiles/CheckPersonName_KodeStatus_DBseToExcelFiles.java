package CheckErrorDataInExcellFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
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
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import PersonReference.TextInAreaTextPanel;

public class CheckPersonName_KodeStatus_DBseToExcelFiles {

	private static List<String> listMasiveForClear;
	static String curentYear = AplicationMetods.getCurentYear();

	public static void ActionListener_Btn_CheckDBaseNameKodeStat(JPanel panel_AllSaerch,
			JButton btn_CheckDBaseNameKodeStat, JTextArea textArea, JProgressBar progressBar) {
		btn_CheckDBaseNameKodeStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
//				GeneralMethods.setWaitCursor(panel_AllSaerch);
				listMasiveForClear = new ArrayList<>();
				CheckErrorDataInExcellFiles_Frame.getBtn_CheckDBase_Clear().setEnabled(false);
				CheckErrorDataInExcellFiles_Frame.getBtn_CheckDBaseNameKodeStat_Clear().setEnabled(false);

				new MySwingWorker(progressBar, textArea, panel_AllSaerch, "CheckDBaseNameKodeStat").execute();

//				String textForArea = checkPersonNameKodeStatus();
//
//				if (textForArea.isEmpty()) {
//					textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
//					CheckErrorDataInExcellFiles_Frame.getBtn_CheckDBaseNameKodeStat_Clear().setEnabled(false);
//				} else {
//					textArea.setText(textForArea);
//					CheckErrorDataInExcellFiles_Frame.getBtn_CheckDBaseNameKodeStat_Clear().setEnabled(true);
//				}

//				GeneralMethods.setDefaultCursor(panel_AllSaerch);
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

	public static String checkPersonNameKodeStatus(JProgressBar aProgressBar, JPanel panel_AllSaerch) {

		GeneralMethods.setWaitCursor(panel_AllSaerch);
		String[][] masive = new String[2][8];

		String ImaGoV = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_ImaGoV");
		String NoGoNiamaV = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("checkCorrectinDataInExcell_NoGoNiamaV");

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
		Set<String> mySetKode = new HashSet<String>();
		List<String> listAllKodeEGN = new ArrayList<String>();
		String checkKodeDuplikate = "";
		boolean notEquals = false;
		boolean setinMasive = false;

//		List<String> listMasiveNotInDB = new ArrayList<String>();
		List<String> listAllEgnInExcelFiles = new ArrayList<String>();
		HashSet<String> listAllPersonInCurentYear = new HashSet<String>();
//		List<Person> listMasiveNotInExcel = new ArrayList<>();
		System.out.println("*******************************************");

//		List<PersonStatusNew> listpersonStatusNew = PersonStatusNewDAO.getValuePersonStatusNewByYear(curentYear);
//---------------------------------------------------------------------------------------		
		double startProgressBar = 0;
		double progressBarSize = 20;
		List<PersonStatusNew> listpersonStatusNew = PersonStatusNewDAO.getValuePersonStatusNewByYearWithProgressBar(
				curentYear, null, aProgressBar, progressBarSize, startProgressBar);

		System.out.println("listpersonStatusNew.size() " + listpersonStatusNew.size());

		for (PersonStatusNew personStatusNew : listpersonStatusNew) {
			if(personStatusNew.getPerson() != null) {
			listAllPersonInCurentYear.add(personStatusNew.getPerson().getEgn());
			}
		}
		System.out.println("listAllPersonInCurentYear.size() " + listAllPersonInCurentYear.size());
		System.out.println("//////////////////////////////");
//---------------------------------------------------------------------------------------

		startProgressBar = aProgressBar.getValue();
		progressBarSize = 20;
		System.out.println("1 startProgressBar " + startProgressBar+"progressBarSize  "+progressBarSize);
		List<KodeStatus> listKodeStatus = KodeStatusDAO.getKodeStatusByYearWithProgressBar(curentYear, aProgressBar,
				progressBarSize, startProgressBar);

		System.out.println("listKodeStatus.size() " + listKodeStatus.size());

		for (KodeStatus kodeStat : listKodeStatus) {

			listAllPersonInCurentYear.add(kodeStat.getPerson().getEgn());
		}
		System.out.println("listAllPersonInCurentYear.size() " + listAllPersonInCurentYear.size());
		System.out.println("//////////////////////////////");

//-----------------------------------------------------------------------------------------		
		System.out.println("2 startProgressBar " + startProgressBar+"progressBarSize  "+progressBarSize);
		startProgressBar += progressBarSize;
		progressBarSize = 60;
		System.out.println("3 startProgressBar " + startProgressBar+"progressBarSize  "+progressBarSize);
		double ProgressBarVolume = startProgressBar;
		for (int ii = 0; ii < filePath.length; ii++) {

			if (filePath[ii].contains("EXTERNAL")) {
				listMasive.add("IN EXTERNAL FILE");
				checkKodeDuplikate += "IN EXTERNAL FILE \n";
			} else {
				listMasive.add("IN PERSONEL FILE");
				checkKodeDuplikate += "IN PERSONEL FILE \n";
			}

			Workbook workbook = ReadExcelFileWBC.openExcelFile(filePath[ii]);
			Sheet sheet = workbook.getSheetAt(0);
			double stepForProgressBar = progressBarSize / (2 * sheet.getLastRowNum());
//			System.out.println(stepForProgressBar+"*******************************************");  
//			System.out.println(sheet.getLastRowNum());
			for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {
				aProgressBar.setValue((int) ProgressBarVolume);
//				 System.out.println(ProgressBarSize+"  -------------------------------------------------------");
				ProgressBarVolume += stepForProgressBar;
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
						checkKodeDuplikate = checkDuplikateKode(mySetKode, sheet, row, checkKodeDuplikate, EGN,
								listAllKodeEGN);
						if (mySet.add(EGN)) {

							for (int j = 0; j < 8; j++) {
								masive[0][j] = "";
								masive[1][j] = "";
							}
							FirstName = ReadExcelFileWBC.getStringEGNfromCell(cell1);
							listAllEgnInExcelFiles.add(EGN + " - " + FirstName);
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
							List<KodeStatus> list = KodeStatusDAO.getListKodeStatusByPersonZoneYear(personBD, k,
									curentYear);
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
						if (s == 1) {
							if (!masive[1][s].replace("*", "").contains(masive[0][s])) {
								masive[1][7] += s + ";";
//								System.out.println(masive[0][s]+" <-> "+masive[1][s]);
								notEquals = true;
							}
						} else {
							if (!masive[0][s].equals(masive[1][s].replace("*", ""))) {
								masive[1][7] += s + ";";
//							System.out.println(masive[0][s]+" <-> "+masive[1][s]);
								notEquals = true;
							}
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

			if (listMasive.size() == 1) {
				listMasive = new ArrayList<String>();
			}
		}

		System.out.println("listAllPersonInCurentYear.size() " + listAllPersonInCurentYear.size());
		System.out.println("listAllEgnInExcelFiles.size() " + listAllEgnInExcelFiles.size());
		Iterator<String> itrPerson = listAllPersonInCurentYear.iterator();

		while (itrPerson.hasNext()) {
			String personEGN = itrPerson.next();

			Iterator<String> itrEGNExcel = listAllEgnInExcelFiles.iterator();
			while (itrEGNExcel.hasNext()) {
				String egn = itrEGNExcel.next();
				egn = egn.split("-")[0].trim();
				if (egn.equals(personEGN)) {
					itrPerson.remove();
					itrEGNExcel.remove();
				}
			}
		}

		System.out.println("listAllPersonInCurentYear.size() " + listAllPersonInCurentYear.size());
		System.out.println("listAllEgnInExcelFiles.size() " + listAllEgnInExcelFiles.size());

		String infoStrText = "";
		if (listMasive.size() > 0) {
			String[][] ExtMasive = extractedMasive(listMasive);
			Integer[] columnWith = extractColumnWith(ExtMasive);

			listMasiveForClear = listMasive;

			infoStrText = creadStringToInfoText(ExtMasive, columnWith);
		}
		int k = 0;
		if (listAllPersonInCurentYear.size() > 0) {
			infoStrText += "\n" + ImaGoV + " DBase " + NoGoNiamaV + " Excel Files" + "\n";
			for (String personEGN : listAllPersonInCurentYear) {
				Person person2 = PersonDAO.getValuePersonByEGN(personEGN);
				if (!PersonStatusNewDAO.getLastValuePersonStatusNewByPerson(person2).getFormulyarName()
						.equals("Обходен лист")) {
					infoStrText += k + " " + person2.getEgn() + " - " + person2.getFirstName() + " "
							+ person2.getSecondName() + " " + person2.getLastName() + "\n";
				}
				k++;
			}
		}
		if (listAllEgnInExcelFiles.size() > 0) {
			infoStrText += "\n" + ImaGoV + " Excel Files " + NoGoNiamaV + "  DBase" + "\n";
			k = 0;
			for (String egn : listAllEgnInExcelFiles) {
				infoStrText += k + " " + egn + "\n";
				k++;
			}
		}
		String str = checkKodeDuplikate.replaceAll("IN EXTERNAL FILE \n", "").replaceAll("IN PERSONEL FILE \n", "")
				.trim();
		if (!str.isEmpty()) {
			infoStrText += "\n Повтарящи се кодове: \n" + checkKodeDuplikate;
			GeneralMethods.setDefaultCursor(panel_AllSaerch);
		}
		return infoStrText;
	}

	private static String checkDuplikateKode(Set<String> mySetKode, Sheet sheet, int row, String checkKodeDuplikate,
			String egn, List<String> listAllKodeEGN) {
		Cell cell;
		String kode;
		for (int s = 0; s < 5; s++) {
			cell = sheet.getRow(row).getCell(s);
			if (cell != null) {
				kode = cell.getStringCellValue();
				if (!kode.equals("ЕП-2") && !kode.equals("н") && !kode.trim().equals("")
						&& !ReadKodeStatusFromExcelFile.inCodeNotNumber(kode)) {

					if (!mySetKode.add(kode)) {
						for (String string : listAllKodeEGN) {
							String[] masive = string.split("-");
							if (masive[0].equals(kode)) {
								if (!masive[1].equals(egn)) {
									checkKodeDuplikate += kode + " на ЕГН:" + egn + "\n";
									checkKodeDuplikate += masive[0] + " на ЕГН:" + masive[1] + "\n";
									break;
								}
							}
						}

					} else {
						listAllKodeEGN.add(kode + "-" + egn);
					}
				}
			}
		}
		return checkKodeDuplikate;
	}

	public static String creadStringToInfoText(String[][] ExtMasive, Integer[] columnWith) {
		String infoStrText = "";
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

	public static Integer[] extractColumnWith(String[][] extMasive) {
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

	static String extracted(String[][] listMasive) {
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

	static String[][] extractedMasive(List<String> listMasive) {
		String[][] extrMasive = new String[listMasive.size() * 2][8];

		extrMasive[0] = new String[] { "ЕГН/IDкод", "Име(1)", "КЗ1(2)", "КЗ2(3)", "ХОГ(4)", "Т1(5)", "Т2(6)",
				"Опис Разлики" };

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
				person.getId_Person() + " - " + person.getEgn() + " - " + zoneID + " - " + curentYear + " - " + kod);
		kodeStat = KodeStatusDAO.getKodeStatusByPersonZoneYaerAndKode(person, zoneID, curentYear, kod);
		if (kodeStat != null) {
			System.out.println(kodeStat.getPerson().getEgn() + " - " + kodeStat.getZone().getId_Zone() + " - "
					+ curentYear + " - " + kodeStat.getKode());
			KodeStatusDAO.deleteValueKodeStatus(kodeStat.getKodeStatus_ID());
		}
	}

}
