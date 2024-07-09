package CheckErrorDataInExcellFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.GeneralMethods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import BasiClassDAO.MeasuringDAO;
import BasicClassAccessDbase.Measuring;
import ReferenceMeasuringLab.ReferenceMeasuringLabMetods;

public class CheckMeasurDBaseToMounthFile {

	private static List<Measuring> listMeasuringForClear;

	static void ActionListener_Btn_CheckDBaseToMounthFile(JPanel panel_AllSaerch, JButton btn_CheckDBase,
			JTextArea textArea) {

		btn_CheckDBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				GeneralMethods.setWaitCursor(panel_AllSaerch);
				listMeasuringForClear = new ArrayList<Measuring>();
				String textForArea = CheckMontToBDate();

				if (textForArea.isEmpty()) {
					textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
					CheckErrorDataInExcellFiles_Frame.getBtn_CheckDBase_Clear().setEnabled(false);
				} else {
					textArea.setText(textForArea);
					CheckErrorDataInExcellFiles_Frame.getBtn_CheckDBase_Clear().setEnabled(true);
				}

				GeneralMethods.setDefaultCursor(panel_AllSaerch);
			}

		});

	}

	public static String CheckMontToBDate() {

		String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
		String ImaGoV = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_ImaGoV");
		String NoGoNiamaV = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("checkCorrectinDataInExcell_NoGoNiamaV");
		String ZaMesec = ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_ZaMesec");

		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		String infotext = "";
		String infotextMounth = "";
		String mesec = "";
		String mounth = "";

		boolean flendIteration = true;
		for (int mont = 0; mont < 12; mont++) {
			mesec = "\n" + "\n" + ZaMesec + " " + (mont + 1);
			infotextMounth = "";
			Date dateStart;
			mounth = "" + (mont + 1);
			if (mont < 9) {
				mounth = "0" + (mont + 1);
			}

			System.out.println("mounth " + mounth);
			try {
				dateStart = sdfrmt.parse("01." + mounth + "." + curentYear);
				Date dateEnd = sdfrmt.parse(ReferenceMeasuringLabMetods.getLastDAte(mounth + "." + curentYear));

				System.out.println(sdfrmt.format(dateStart) + " " + sdfrmt.format(dateEnd));

				String[][][] masiveStrMonth = MasiveFromMonthFile(mont);

				List<Measuring> listMeasyrByMounth = MeasuringDAO.getValueMeasuringByStartdate_EndDate(dateStart,
						dateEnd);

				System.out.println("masiveStrMonth 1-" + masiveStrMonth[0].length);
				System.out.println("1-" + listMeasyrByMounth.size());
				int ff = listMeasyrByMounth.size();
				int kk = 0;
				System.out.println("ff 1-" + ff);
				for (int i = 0; i < 2; i++) {

					for (int m = 0; m < 500; m++) {

						if (masiveStrMonth[i][m][0] != null) {
//								System.out.println(masiveStrMonth[i][m][0]);
							Iterator<Measuring> itr = listMeasyrByMounth.iterator();
							flendIteration = true;
							kk++;
							while (itr.hasNext() && flendIteration) {
								Measuring measur = itr.next();
//									System.out.println("measur "+measur.getPerson().getEgn()+" <-> " + masiveStrMonth[i][m][0]);
//									System.out.println("date "+sdfrmt.format(measur.getDate())+" <-> " + masiveStrMonth[i][m][2]);
//									System.out.println(masiveStrMonth[i][m][3]+" <-> " +measur.getLab().getLab().toLowerCase());
								String dozeString = convertDozeToString(measur);
//								System.out.println(masiveStrMonth[i][m][1] + " <-> " + dozeString);
								if (masiveStrMonth[i][m][0].equals(measur.getPerson().getEgn())
										&& masiveStrMonth[i][m][1].equals(dozeString)
										&& masiveStrMonth[i][m][2].equals(sdfrmt.format(measur.getDate()))
										&& masiveStrMonth[i][m][3].equals(measur.getLab().getLab().toLowerCase())) {
									itr.remove();

									flendIteration = false;
									ff--;
								}
							}

						}

					}
				}
				System.out.println("2-" + listMeasyrByMounth.size());
				System.out.println("ff 2-" + ff);
				System.out.println("kk 2-" + kk);
				mesec += "\n" + ImaGoV + " DBase " + NoGoNiamaV + " Month" + "\n";
				String measurText = "";
				String measurMounthText = "";

				for (Measuring measur : listMeasyrByMounth) {
					listMeasuringForClear.add(measur);
					measurMounthText = generateMounthtext(measur, masiveStrMonth);
					measurText = measur.getPerson().getEgn() + " " + sdfrmt.format(measur.getDate()) + " "
							+ convertDozeToString(measur) + " " + measur.getLab().getLab();

					infotextMounth += measurText + " <-> " + measurMounthText + "\n";
				}
				if (!infotextMounth.isEmpty()) {
					infotext += mesec + "\n" + infotextMounth;
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		return infotext;

	}

	private static String[][][] MasiveFromMonthFile(int mounth) {

		String filePathMonthExternal = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("filePathMonthExternal_orig");
		String filePathMonthPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("filePathMonthPersonel_orig");

		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if (testFilesToD.equals("1")) {
			filePathMonthExternal = ReadFileBGTextVariable.getGlobalTextVariableMap()
					.get("filePathMonthExternal_orig_test");
			filePathMonthPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap()
					.get("filePathMonthPersonel_orig_test");
		}

		String filePathMont[] = { filePathMonthPersonel, filePathMonthExternal };

		Workbook workbookMont[] = { ReadExcelFileWBC.openExcelFile(filePathMont[0]),
				ReadExcelFileWBC.openExcelFile(filePathMont[1]) };
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");

		Date date;
		String EGN;
		String strDate;
		String doze;
		String lab;
		Cell cell_EGN;
		Cell cell_date;
		Cell cell_Doze;
		Cell cell_Lab;

		String[][][] masiveStrMonth = new String[2][500][4];

		for (int i = 0; i < 2; i++) {
			int row = 0;
			Sheet sheetMont = workbookMont[i].getSheetAt(mounth);

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

						masiveStrMonth[i][row][0] = EGN;
						masiveStrMonth[i][row][1] = readDozeToString(doze);
						masiveStrMonth[i][row][2] = strDate;
						masiveStrMonth[i][row][3] = lab;

						row++;

					}
				}
			}
		}

		return masiveStrMonth;
	}

	private static String generateMounthtext(Measuring measur, String[][][] masiveStrMonth) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		String text = "";
		String fileName[] = { "Personel", "External" };
		for (int i = 0; i < 2; i++) {

			for (int m = 0; m < 500; m++) {

				if (masiveStrMonth[i][m][0] != null) {
					if (masiveStrMonth[i][m][0].equals(measur.getPerson().getEgn())
							&& masiveStrMonth[i][m][2].equals(sdfrmt.format(measur.getDate()))) {
						text = masiveStrMonth[i][m][2] + " " + masiveStrMonth[i][m][1] + " " + masiveStrMonth[i][m][3]
								+ " Month-" + fileName[i];
					}
				}
			}
		}
		return text;
	}

	private static String convertDozeToString(Measuring measur) {
		String doseString = "";

		String dozeSTR = measur.getTypeMeasur().getKodeType().replaceAll("M", "М");
		if (dozeSTR.equals("М")) {
			return doseString = "М";
		}

		double doze = measur.getDoze();
		DecimalFormat formatter = new DecimalFormat("0.00");
		doseString = formatter.format(doze);

		if (doze < 0.1 && !doseString.equals("0,00")) {
			return doseString = "<0.10";
		}

		if (doseString.equals("0,00")) {
			return doseString = "0";
		}
		return doseString;
	}

	private static String readDozeToString(String doze) {
		String doseString = "";
		try {

			DecimalFormat formatter = new DecimalFormat("0.00");
			double dozeDouble = Double.parseDouble(doze);
			doseString = formatter.format(dozeDouble);

			if (dozeDouble < 0.1 && !doseString.equals("0,00")) {
				return doseString = "<0.10";
			}

			if (doseString.equals("0,00")) {
				return doseString = "0";
			}
		} catch (Exception e) {
			doseString = doze;
			doze = doze.replaceAll("M", "М");
			if (doze.equals("М")) {
				doseString = "М";
			}
		}
		return doseString;
	}

	static void ActionListener_Btn_CheckDBase_Clear(JPanel panel_AllSaerch, JButton btn_CheckDBase_Clear,
			JTextArea textArea) {

		btn_CheckDBase_Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GeneralMethods.setWaitCursor(panel_AllSaerch);

				System.out.println(listMeasuringForClear.size());
				if (listMeasuringForClear.size() > 0) {
					for (Measuring measuring : listMeasuringForClear) {

						List<Measuring> listMeasur = MeasuringDAO.getListValueMeasuringByPersonDozeDate(
								measuring.getPerson(), measuring.getDate(), measuring.getDoze(), measuring.getLab());
						if (listMeasur.size() > 1) {
							deleteMicingMeasurig(listMeasur);
						} else {
							MeasuringDAO.deleteValueMeasuring(measuring);
						}
					}
				}
				textArea.setText("");
				btn_CheckDBase_Clear.setEnabled(false);
				GeneralMethods.setDefaultCursor(panel_AllSaerch);
			}

		});

	}

	private static void deleteMicingMeasurig(List<Measuring> listMeasur) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		boolean fl;
		for (Measuring measuring : listMeasur) {
			fl = true;
			String[][] masiveMeasurFromExcel = getMasiveMeasurFromExcelFile(measuring.getPerson().getEgn());
			for (int j = 0; j < masiveMeasurFromExcel.length; j++) {
//				System.out.println(String.format("0.0", measuring.getDoze()) + " " + sdfrmt.format(measuring.getDate())
//						+ " " + measuring.getLab().getLab().toUpperCase() + " " + measuring.getExcelPosition());
//				System.out.println(masiveMeasurFromExcel[j][1] + " " + masiveMeasurFromExcel[j][2] + " "
//						+ masiveMeasurFromExcel[j][3] + " " + masiveMeasurFromExcel[j][4]);
//				System.out.println();
				if (masiveMeasurFromExcel[j][1].equals(String.format("0.0", measuring.getDoze()))
						&& masiveMeasurFromExcel[j][2].equals(sdfrmt.format(measuring.getDate()))
						&& masiveMeasurFromExcel[j][3].equals(measuring.getLab().getLab().toUpperCase())
						&& masiveMeasurFromExcel[j][4].equals(measuring.getExcelPosition())) {
					j = masiveMeasurFromExcel.length;
					fl = false;
				}

			}
			if (fl) {
//				System.out.println("***************delite *******************************");
//				System.out.println(String.format("0.0", measuring.getDoze()) + " " + sdfrmt.format(measuring.getDate())
//						+ " " + measuring.getLab().getLab() + " " + measuring.getExcelPosition());
//				System.out.println();
				MeasuringDAO.deleteValueMeasuring(measuring);
			}
		}

	}

	private static String[][] getMasiveMeasurFromExcelFile(String EGN_insert) {

		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");

		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if (testFilesToD.equals("1")) {
			filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
			filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}

		String filePath[] = { filePathPersonel, filePathExternal };

		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");

		Workbook workbook[] = { ReadExcelFileWBC.openExcelFile(filePath[0]),
				ReadExcelFileWBC.openExcelFile(filePath[1]) };
		String[][] masiveStr0 = new String[50][5];
		int l = 0;
		for (int i = 0; i < 2; i++) {
			Date date;
			Double dDoze;
			String EGN = "", strDate = "", doze = "", lab = "", reportFile = "";

			Cell cell_EGN, cell_date, cell_Doze, cell_Lab;

			Sheet sheet1 = workbook[i].getSheetAt(1);
			for (int row0 = 5; row0 <= sheet1.getLastRowNum(); row0++) {

				if (sheet1.getRow(row0) != null) {

					cell_EGN = sheet1.getRow(row0).getCell(5);

					if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {

						EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell_EGN);

						if (EGN.equals(EGN_insert)) {

							int k = 7;
							cell_date = sheet1.getRow(row0).getCell(k);
							k++;
							cell_Lab = sheet1.getRow(row0).getCell(k);
							while (ReadExcelFileWBC.CellNOEmpty(cell_date) && ReadExcelFileWBC.CellNOEmpty(cell_Lab)) {

								date = ReadExcelFileWBC.readCellToDate(cell_date);
								strDate = sdfrmt.format(date);

								lab = ReadExcelFileWBC.getStringEGNfromCell(cell_Lab);

								k = k + 17;
								cell_Doze = sheet1.getRow(row0).getCell(k);
								doze = ReadExcelFileWBC.getStringEGNfromCell(cell_Doze);

								dDoze = Double.valueOf(doze);
								reportFile = "Excel-" + EGN + "/" + k;
								masiveStr0[l][0] = EGN;
								masiveStr0[l][1] = String.format("0.0", dDoze);
								masiveStr0[l][2] = strDate;
								masiveStr0[l][3] = lab.toUpperCase();
								masiveStr0[l][4] = reportFile;
								l++;

								if (k > 253) {
									k = 6;
									sheet1 = workbook[i].getSheetAt(2);
								}

								k++;
								cell_date = sheet1.getRow(row0).getCell(k);
								k++;
								cell_Lab = sheet1.getRow(row0).getCell(k);

							}
							row0 = sheet1.getLastRowNum();
							i = 2;

						}
					}

				}

			}
		}
		String[][] masiveStr = new String[l][5];
		for (int j = 0; j < l; j++) {
			masiveStr[j] = masiveStr0[j];
		}

		return masiveStr;
	}

}
