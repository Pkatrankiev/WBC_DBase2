package CheckErrorDataInExcellFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import Aplication.ReadKodeStatusFromExcelFile;

public class CheckDataBethwinExcelFilesAndMonth {

	static void ActionListener_Btn_SearchError(JPanel panel_AllSaerch, JButton btn_Search, JTextArea textArea,
			JProgressBar progressBar) {

		btn_Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");

				new MySwingWorker(progressBar, textArea, panel_AllSaerch, "SearchError").execute();

			}

		});

	}

	public static String CheckForCorrectionMeasuringInSheet0AndInMonth(JProgressBar aProgressBar,
			JPanel panel_AllSaerch) {

		GeneralMethods.setWaitCursor(panel_AllSaerch);

		double ProgressBarSize = 0;
		aProgressBar.setValue((int) ProgressBarSize);
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		String infotext = "";

		List<String> listMoveEGN = CheckErrorDataInExcellFiles_Frame.getListMoveEGN();
		if (listMoveEGN == null) {
			CheckCurentDataInExcelFilesMetod.CheckCurentDataInExcelFiles(aProgressBar, 30);
			listMoveEGN = CheckErrorDataInExcellFiles_Frame.getListMoveEGN();
		}

		String[][][] masiveStrMonth = null;
		String[][][] masiveStrMonthLab = null;
		String[][][] masiveMeasur = null;
		String[][][] masiveMeasur2 = null;
		String[][][] masiveDoze = null;

		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");

		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if (testFilesToD.equals("1")) {
			filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
			filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}

		String filePath[] = { filePathPersonel, filePathExternal };

		String filePathMonthExternal = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("filePathMonthExternal_orig");
		String filePathMonthPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("filePathMonthPersonel_orig");

		testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if (testFilesToD.equals("1")) {
			filePathMonthExternal = ReadFileBGTextVariable.getGlobalTextVariableMap()
					.get("filePathMonthExternal_orig_test");
			filePathMonthPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap()
					.get("filePathMonthPersonel_orig_test");
		}

		String filePathMont[] = { filePathMonthPersonel, filePathMonthExternal };

		Workbook workbookMont[] = { ReadExcelFileWBC.openExcelFile(filePathMont[0]),
				ReadExcelFileWBC.openExcelFile(filePathMont[1]) };

		Workbook workbook[] = { ReadExcelFileWBC.openExcelFile(filePath[0]),
				ReadExcelFileWBC.openExcelFile(filePath[1]) };

		String fileName[] = { "Personel", "External" };

		ProgressBarSize = 30;

		for (int i = 0; i < 2; i++) {

			masiveStrMonth = MasiveFromMonth(sdfrmt, workbookMont[i]);
			ProgressBarSize += 5;
			aProgressBar.setValue((int) ProgressBarSize);
			masiveStrMonthLab = MasiveFromMonthCheckLab(sdfrmt, workbookMont[i]);
			ProgressBarSize += 5;
			aProgressBar.setValue((int) ProgressBarSize);
			masiveMeasur = masiveMeasur(sdfrmt, workbook[i]);
			ProgressBarSize += 5;
			aProgressBar.setValue((int) ProgressBarSize);
			masiveMeasur2 = masiveMeasur(sdfrmt, workbook[i]);
			ProgressBarSize += 5;
			aProgressBar.setValue((int) ProgressBarSize);
			masiveDoze = masiveDoze(workbook[i]);
			ProgressBarSize += 5;
			aProgressBar.setValue((int) ProgressBarSize);
			masiveMeasur = generateCheckMasiveMeasur(masiveMeasur, masiveDoze);
			ProgressBarSize += 5;
			aProgressBar.setValue((int) ProgressBarSize);

			for (int mont = 0; mont < 12; mont++) {

				aProgressBar.setValue((int) ProgressBarSize);
				for (int m = 0; m < masiveStrMonth[mont].length; m++) {
					if (masiveStrMonth[mont][m][0] != null) {

						for (int k = 0; k < masiveMeasur2[mont].length; k++) {
							if (masiveMeasur2[mont][k][0] != null) {
								if (masiveStrMonth[mont][m][0] != null
										&& masiveStrMonth[mont][m][0].equals(masiveMeasur2[mont][k][0])
										&& masiveStrMonth[mont][m][1].equals(masiveMeasur2[mont][k][1])
										&& masiveStrMonth[mont][m][2].equals(masiveMeasur2[mont][k][2])
										&& masiveStrMonth[mont][m][3].equals(masiveMeasur2[mont][k][3])) {

									masiveStrMonth[mont][m][0] = null;
									masiveMeasur2[mont][k][0] = null;

								}
							}
						}

					}
				}
			}
			for (int mont = 0; mont < 12; mont++) {

				for (int m = 0; m < masiveStrMonthLab[mont].length; m++) {
					if (masiveStrMonthLab[mont][m][0] != null) {
						if (masiveStrMonthLab[mont][m][2].contains(masiveStrMonthLab[mont][m][3])) {
							masiveStrMonthLab[mont][m][0] = null;
						}
					}
				}

			}
			ProgressBarSize += 5;
			aProgressBar.setValue((int) ProgressBarSize);
			infotext += GenerateInfoString(fileName[i], masiveStrMonth, masiveStrMonthLab, masiveMeasur, masiveMeasur2,
					masiveDoze, listMoveEGN);
			System.out.println("*************************************" + infotext);

		}

		GeneralMethods.setDefaultCursor(panel_AllSaerch);

		return infotext;
	}

	private static String[][][] generateCheckMasiveMeasur(String[][][] masuveMeasur, String[][][] masiveDoze) {
		for (int mont = 0; mont < 12; mont++) {

			for (int d = 0; d < masiveDoze[mont].length; d++) {
				if (masiveDoze[mont][d][0] != null) {
					double sumDoze = 0.0, doubDoze = 0.0;
					String sumStrDoze = "", strDoze = "";
					boolean isString = false, fl2 = false;
					List<Integer> list = new ArrayList<>();
					for (int k = 0; k < masuveMeasur[mont].length; k++) {
						if (masuveMeasur[mont][k][0] != null) {
							if (masuveMeasur[mont][k][0].equals(masiveDoze[mont][d][0])) {
								list.add(k);
								try {
									sumDoze = +Double.parseDouble(masuveMeasur[mont][k][1]);

								} catch (Exception e) {
									sumStrDoze = masuveMeasur[mont][k][1];
								}

							}
						}
					}

					try {
						doubDoze = +Double.parseDouble(masiveDoze[mont][d][1]);
					} catch (Exception e) {
						strDoze = masiveDoze[mont][d][1];
						isString = true;
					}
					if (isString) {
						if (sumStrDoze.equals(strDoze))
							fl2 = true;
					} else {
						if (sumDoze == doubDoze)
							fl2 = true;
					}

					if (fl2) {
						masiveDoze[mont][d][0] = null;
						for (int index : list) {
							masuveMeasur[mont][index][0] = null;
						}

					}

				}
			}
		}
		return masuveMeasur;

	}

	private static String GenerateInfoString(String fileName, String[][][] masiveStrMonth,
			String[][][] masiveStrMonthLab, String[][][] masuveMeasur, String[][][] masuveMeasur2,
			String[][][] masiveDoze, List<String> listMoveEGN) {
		String ImaGoV = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_ImaGoV");
		String NoGoNiamaV = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("checkCorrectinDataInExcell_NoGoNiamaV");
		String IzmerSICH = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("checkCorrectinDataInExcell_IzmerSICH");
		String Dozi = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_DOZI");
		String ZaMesec = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_ZaMesec");
		String IzmerVSICH = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("checkCorrectinDataInExcell_IzmerVSICH");
		String MarkKato = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_MarkKato");
		String ErrorLab = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_ErrorLab");

		String infoText = "";
		String mesec = "";

		for (int m = 0; m < 12; m++) {
			String infoTextM = "";
			mesec = "\n" + "\n" + ZaMesec + " " + (m + 1);

			boolean fl = true;
			for (int j = 0; j < masiveStrMonthLab[m].length; j++) {
				if (masiveStrMonthLab[m][j][0] != null) {
					if (fl) {
						infoTextM += "\n" + ErrorLab + " Month-" + fileName + "\n";
						fl = false;
					}
					infoTextM += (j + 1) + " - " + IzmerVSICH + " " + masiveStrMonthLab[m][j][3] + " " + MarkKato + " "
							+ masiveStrMonthLab[m][j][2] + " "
							+ searchEGNInMoveList(masiveStrMonthLab[m][j][0], listMoveEGN) + "\n";
				}
			}

			fl = true;
			for (int j = 0; j < masiveStrMonth[m].length; j++) {
				if (masiveStrMonth[m][j][0] != null) {
					if (fl) {
						infoTextM += "\n" + ImaGoV + " Month-" + fileName + " " + NoGoNiamaV + " " + fileName + " "
								+ IzmerSICH + "\n";
						fl = false;
					}
					infoTextM += (j + 1) + " - " + masiveStrMonth[m][j][0] + " " + masiveStrMonth[m][j][1] + " "
							+ masiveStrMonth[m][j][2] + " " + masiveStrMonth[m][j][3] + " "
							+ searchEGNInMoveList(masiveStrMonth[m][j][0], listMoveEGN) + "\n";
				}
			}

			fl = true;

			for (int j = 0; j < masuveMeasur2[m].length; j++) {
				if (masuveMeasur2[m][j][0] != null) {
					if (fl) {
						infoTextM += "\n" + ImaGoV + " " + fileName + " " + IzmerSICH + " " + NoGoNiamaV + " Month-"
								+ fileName + "\n";
						fl = false;
					}
					infoTextM += (j + 1) + " - " + masuveMeasur2[m][j][0] + " " + masuveMeasur2[m][j][1] + " "
							+ masuveMeasur2[m][j][2] + " " + masuveMeasur2[m][j][3] + " "
							+ searchEGNInMoveList(masuveMeasur2[m][j][0], listMoveEGN) + "\n";
				}
			}

			fl = true;
			for (int j = 0; j < masuveMeasur[m].length; j++) {
				if (masuveMeasur[m][j][0] != null) {
					if (fl) {
						infoTextM += "\n" + ImaGoV + " " + fileName + " " + IzmerSICH + " " + NoGoNiamaV + " " + Dozi
								+ "\n";
						fl = false;
					}

					infoTextM += (j + 1) + " - " + masuveMeasur[m][j][0] + " " + masuveMeasur[m][j][1] + " "
							+ masuveMeasur[m][j][2] + " " + masuveMeasur[m][j][3] + " "
							+ searchEGNInMoveList(masuveMeasur[m][j][0], listMoveEGN) + "\n";
				}
			}

			fl = true;

			for (int j = 0; j < masiveDoze[m].length; j++) {
				if (masiveDoze[m][j][0] != null) {
					if (fl) {
						infoTextM += "\n" + ImaGoV + " " + fileName + " " + Dozi + " " + NoGoNiamaV + " " + IzmerSICH
								+ "\n";
						fl = false;
					}
					infoTextM += (j + 1) + " - " + masiveDoze[m][j][0] + " " + masiveDoze[m][j][1] + " "
							+ searchEGNInMoveList(masiveDoze[m][j][0], listMoveEGN) + "\n";
				}
			}
			if (!infoTextM.isEmpty()) {
				infoText += mesec + "\n" + infoTextM;
			}

		}
		return infoText;
	}

	private static String searchEGNInMoveList(String egn, List<String> listMoveEGN) {
		String coment = "";
		for (String egn2 : listMoveEGN) {
			System.out.println(egn + " <-> " + egn2);
			if (egn.equals(egn2)) {
				coment = " - преместен в АЕЦ";
			}
		}
		return coment;
	}

	private static String[][][] masiveDoze(Workbook workbook) {
		String EGN = "", doze = "";

		Cell cell_EGN, cell_Doze;

		Sheet sheet0 = workbook.getSheetAt(0);

		String[][][] masiveStr = new String[12][500][2];
		int[] maxindexMonth = new int[12];
		for (int i = 0; i < maxindexMonth.length; i++) {
			maxindexMonth[i] = 0;
		}
		for (int row = 5; row <= sheet0.getLastRowNum(); row++) {

			if (sheet0.getRow(row) != null) {

				cell_EGN = sheet0.getRow(row).getCell(5);

				if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {

					EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell_EGN);
					for (int mont = 0; mont < 12; mont++) {
						cell_Doze = sheet0.getRow(row).getCell(mont + 77);
						doze = ReadExcelFileWBC.getStringEGNfromCell(cell_Doze);
//							System.out.println(mont + " " + maxindexMonth[mont] + " " + EGN);
						if (!doze.isEmpty()) {
							masiveStr[mont][maxindexMonth[mont]][0] = EGN;
							masiveStr[mont][maxindexMonth[mont]][1] = doze;
							maxindexMonth[mont]++;
						}
					}
				}
			}
		}

		return masiveStr;
	}

	@SuppressWarnings("deprecation")
	private static String[][][] masiveMeasur(SimpleDateFormat sdfrmt, Workbook workbook) {
		Date date;
		int mont;
		String EGN = "", strDate = "", doze = "", lab = "";

		Cell cell_EGN, cell_date, cell_Doze, cell_Lab;

		String[][][] masiveStr0 = new String[12][500][4];
		Sheet sheet1 = workbook.getSheetAt(1);
		int[] maxindexMonth = new int[12];
		for (int i = 0; i < maxindexMonth.length; i++) {
			maxindexMonth[i] = 0;
		}
		for (int row0 = 5; row0 <= sheet1.getLastRowNum(); row0++) {

			if (sheet1.getRow(row0) != null) {

				cell_EGN = sheet1.getRow(row0).getCell(5);

				if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {

					EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell_EGN);
					int k = 7;
					cell_date = sheet1.getRow(row0).getCell(k);
					k++;
					cell_Lab = sheet1.getRow(row0).getCell(k);
					while (ReadExcelFileWBC.CellNOEmpty(cell_date) && ReadExcelFileWBC.CellNOEmpty(cell_Lab)) {

						date = ReadExcelFileWBC.readCellToDate(cell_date);
						strDate = sdfrmt.format(date);

						lab = ReadExcelFileWBC.getStringEGNfromCell(cell_Lab);

						mont = date.getMonth();

						k = k + 17;
						cell_Doze = sheet1.getRow(row0).getCell(k);
						doze = ReadExcelFileWBC.getStringEGNfromCell(cell_Doze);
						try {
							if (!ReadExcelFileWBC.getStringEGNfromCell(cell_date).isEmpty() && CheckCurentDataInExcelFilesMetod.checkDateInKurentYeare(new SimpleDateFormat("dd.MM.yy")
									.parse(ReadExcelFileWBC.getStringEGNfromCell(cell_date)))) {
								masiveStr0[mont][maxindexMonth[mont]][0] = EGN;
								masiveStr0[mont][maxindexMonth[mont]][1] = doze;
								masiveStr0[mont][maxindexMonth[mont]][2] = strDate;
								masiveStr0[mont][maxindexMonth[mont]][3] = lab;

								maxindexMonth[mont]++;
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
						if (k > 252) {
							k = 6;
							sheet1 = workbook.getSheetAt(2);
						}

						k++;
						cell_date = sheet1.getRow(row0).getCell(k);
						k++;
						cell_Lab = sheet1.getRow(row0).getCell(k);

					}
					sheet1 = workbook.getSheetAt(1);
				}

			}

		}

		return masiveStr0;
	}

	private static String[][][] MasiveFromMonth(SimpleDateFormat sdfrmt, Workbook workbookMont) {
		Date date;
		String EGN;
		String strDate;
		String doze;
		String lab;
		Cell cell_EGN;
		Cell cell_date;
		Cell cell_Doze;
		Cell cell_Lab;

		String[][][] masiveStrMonth = new String[12][500][4];

		for (int m = 0; m < 12; m++) {

			Sheet sheetMont = workbookMont.getSheetAt(m);
			int row = 0;
			for (int l = 6; l <= sheetMont.getLastRowNum(); l++) {

				if (sheetMont.getRow(l) != null) {
					cell_EGN = sheetMont.getRow(l).getCell(3);
					if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {
						EGN = ReadExcelFileWBC.getStringEGNfromCell(cell_EGN);
						cell_Doze = sheetMont.getRow(l).getCell(5);
						doze = ReadExcelFileWBC.getStringEGNfromCell(cell_Doze);

						cell_date = sheetMont.getRow(l).getCell(6);
						date = ReadExcelFileWBC.readCellToDate(cell_date);
						strDate = sdfrmt.format(date);

						cell_Lab = sheetMont.getRow(l).getCell(8);
						lab = ReadExcelFileWBC.getStringfromCell(cell_Lab);

						masiveStrMonth[m][row][0] = EGN;
						masiveStrMonth[m][row][1] = doze;
						masiveStrMonth[m][row][2] = strDate;
						masiveStrMonth[m][row][3] = lab;

						row++;
					}
				}
			}
		}

		return masiveStrMonth;
	}

	private static String[][][] MasiveFromMonthCheckLab(SimpleDateFormat sdfrmt, Workbook workbookMont) {
		String EGN;
		String doze;
		String lab, lab2;
		Cell cell_EGN;

		Cell cell_Doze;
		Cell cell_Lab;

		String[][][] masiveStrMonth = new String[12][500][4];

		for (int m = 0; m < 12; m++) {

			Sheet sheetMont = workbookMont.getSheetAt(m);
			int row = 0;
			for (int l = 6; l <= sheetMont.getLastRowNum(); l++) {

				if (sheetMont.getRow(l) != null) {
					cell_EGN = sheetMont.getRow(l).getCell(3);
					if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {
						EGN = ReadExcelFileWBC.getStringEGNfromCell(cell_EGN);

						cell_Doze = sheetMont.getRow(l).getCell(5);
						doze = ReadExcelFileWBC.getStringEGNfromCell(cell_Doze);

						cell_Lab = sheetMont.getRow(l).getCell(8);
						lab = ReadExcelFileWBC.getStringEGNfromCell(cell_Lab);

						cell_Lab = sheetMont.getRow(l).getCell(9);
						lab2 = "";
						if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {
							lab2 = ReadExcelFileWBC.getStringEGNfromCell(cell_Lab);
						}

						masiveStrMonth[m][row][0] = EGN;
						masiveStrMonth[m][row][1] = doze;
						masiveStrMonth[m][row][2] = lab;
						masiveStrMonth[m][row][3] = lab2;

						row++;
					}
				}
			}
		}

		return masiveStrMonth;
	}

}
