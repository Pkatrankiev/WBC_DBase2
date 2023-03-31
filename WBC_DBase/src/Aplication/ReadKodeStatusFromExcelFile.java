package Aplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;

import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;

public class ReadKodeStatusFromExcelFile {

	public static List<KodeStatus> getListKodeStatusFromExcelFile(String pathFile, String firmName, String year) {
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<KodeStatus> listKodeStatus = new ArrayList<>();
		Person person;
		String zab = "From Arhive";
		String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "", kodeT1 = "", kodeT2 = "";
		String EGN = "", FirstName = "";
		Sheet sheet = workbook.getSheetAt(0);
		Cell cell, cell1;
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {
			kodeKZ1 = "";
			kodeKZ2 = "";
			kodeHOG = "";
			kodeT1 = "";
			kodeT2 = "";
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);

				if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					if (EGN.contains("*"))
						EGN = EGN.substring(0, EGN.length() - 1);
					FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
					person = PersonDAO.getValuePersonByEGN(EGN);
					if (person == null) {
						ReadPersonStatusFromExcelFile.MessageDialog(FirstName);
					}

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

					if (pathFile.contains("EXTERNAL")) {
						kodeT1 = kodeT2;
						kodeT2 = "";
					}

					if (!kodeKZ1.equals("ЕП-2") && !kodeKZ1.trim().equals("") && !kodeKZ1.equals("н")
							&& !inCodeNotNumber(kodeKZ1)) {
						listKodeStatus
								.add(new KodeStatus(person, kodeKZ1, ZoneDAO.getValueZoneByID(1), true, year, zab));
					}
					if (!kodeKZ2.equals("н") && !kodeKZ2.trim().equals("") && !inCodeNotNumber(kodeKZ2)) {
						listKodeStatus
								.add(new KodeStatus(person, kodeKZ2, ZoneDAO.getValueZoneByID(2), true, year, zab));
					}
					if (!kodeHOG.equals("н") && !kodeHOG.trim().equals("") && !inCodeNotNumber(kodeHOG)) {
						listKodeStatus
								.add(new KodeStatus(person, kodeHOG, ZoneDAO.getValueZoneByID(3), true, year, zab));
					}
					if (!kodeT1.equals("н") && !kodeT1.trim().equals("") && !inCodeNotNumber(kodeT1)) {
						listKodeStatus
								.add(new KodeStatus(person, kodeT1, ZoneDAO.getValueZoneByID(4), true, year, zab));
					}
					if (!kodeT2.equals("н") && !kodeT2.trim().equals("") && !inCodeNotNumber(kodeT2)) {
						listKodeStatus
								.add(new KodeStatus(person, kodeT2, ZoneDAO.getValueZoneByID(5), true, year, zab));
					}

				}
			}

		}
		return listKodeStatus;
	}

	public static String[] getUsedKodeFromExcelFile(int zoneID) {
		String[] excellFiles = getFilePathForPersonelAndExternal();
		List<String[]> listKodeStatus = new ArrayList<>();
		for (String pathFile : excellFiles) {
			Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);

			String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "", kodeT1 = "", kodeT2 = "";
			Sheet sheet = workbook.getSheetAt(0);
			Cell cell, cell1;
			int allRow = sheet.getLastRowNum();

			for (int row = 5; row <= allRow; row += 1) {
				String[] sinpleKode = generateEmptyMasive();
				kodeKZ1 = "";
				kodeKZ2 = "";
				kodeHOG = "";
				kodeT1 = "";
				kodeT2 = "";
				if (sheet.getRow(row) != null) {
					cell = sheet.getRow(row).getCell(5);
					cell1 = sheet.getRow(row).getCell(6);

					if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {

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

						if (pathFile.contains("EXTERNAL")) {
							kodeT1 = kodeT2;
							kodeT2 = "";
						}

						if (!kodeKZ1.equals("ЕП-2") && !kodeKZ1.trim().equals("") && !kodeKZ1.equals("н")
								&& !inCodeNotNumber(kodeKZ1)) {
							sinpleKode[0] = kodeKZ1;
						}
						if (!kodeKZ2.equals("н") && !kodeKZ2.trim().equals("") && !inCodeNotNumber(kodeKZ2)) {
							sinpleKode[1] = kodeKZ2;
						}
						if (!kodeHOG.equals("н") && !kodeHOG.trim().equals("") && !inCodeNotNumber(kodeHOG)) {
							sinpleKode[2] = kodeHOG;
						}
						if (!kodeT1.equals("н") && !kodeT1.trim().equals("") && !inCodeNotNumber(kodeT1)) {
							sinpleKode[3] = kodeT1;
						}
						if (!kodeT2.equals("н") && !kodeT2.trim().equals("") && !inCodeNotNumber(kodeT2)) {
							sinpleKode[4] = kodeT2;
						}
						listKodeStatus.add(sinpleKode);
					}
				}

			}
		}
		int sizeList = listKodeStatus.size();
		String[] listAllKode = new String[sizeList];

		for (int i = 0; i < sizeList; i++) {
			listAllKode[i] = listKodeStatus.get(i)[zoneID - 1];
		}

		return listAllKode;
	}

	public static List<String> getUsedKodeFromExcelFileByZoneAndZveno(String zveno, int zoneID) {
		
		List<String[]> listKodeStatus = generateListFromMasiveEGNandKode(zveno);

		List<String> list = new ArrayList<>();
		for (String[] kod : listKodeStatus) {
			if (!kod[zoneID - 1].isEmpty()) {
				list.add(kod[zoneID - 1]);
			}
		}

		int maxString = list.get(0).length();
		for (String kod : list) {
			if (kod.length() > maxString) {
				maxString = kod.length();
			}
		}
		System.out.println(maxString);

		List<String> kklist = new ArrayList<>();
		
		for (String kod : list) {
			
			kod = NormKode(maxString, kod);
			kklist.add(kod);
		}
		Collections.sort(kklist);

		return kklist;
	}

	public static List<String[]> generateListFromMasiveEGNandKode(String zveno) {
		boolean flOtdel = false;
		String[] excellFiles = getFilePathForPersonelAndExternal();
		List<String[]> listKodeStatus = new ArrayList<>();
		String otdelName = "";
		for (String pathFile : excellFiles) {
			Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);

			String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "", kodeT1 = "", kodeT2 = "", egn = "";
			Sheet sheet = workbook.getSheetAt(0);
			Cell cell, cell1;
			int allRow = sheet.getLastRowNum();

			for (int globalRow = 5; globalRow <= allRow; globalRow += 1) {
				if (sheet.getRow(globalRow) != null) {
					cell = sheet.getRow(globalRow).getCell(5);
					cell1 = sheet.getRow(globalRow).getCell(6);
					if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
						otdelName = cell1.getStringCellValue();
						if (otdelName.equals(zveno) || flOtdel) {
							flOtdel = true;
//*************************************************************************************		
//			for (int row = globalRow; row <= allRow; row += 1) {
							int row = globalRow;
							while (row < allRow) {
								flOtdel = false;
								System.out.println(row + "  --  " + otdelName);
								String[] simpleKode = generateEmptyMasive();
								kodeKZ1 = "";
								kodeKZ2 = "";
								kodeHOG = "";
								kodeT1 = "";
								kodeT2 = "";
								otdelName = "";
								egn = "";
								if (sheet.getRow(row) != null) {
									cell = sheet.getRow(row).getCell(5);
									cell1 = sheet.getRow(row).getCell(6);
									if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
										otdelName = cell1.getStringCellValue();
										if (otdelName.equals("край")) {
											row = allRow;
										}
									}

									if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
											egn = ReadExcelFileWBC.getStringfromCell(cell);
											if(egn.contains("*")) egn = egn.substring(0, egn.length()-1);	
										
											
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

										if (pathFile.contains("EXTERNAL")) {
											kodeT1 = kodeT2;
											kodeT2 = "";
										}

										if (!kodeKZ1.equals("ЕП-2") && !kodeKZ1.trim().equals("")
												&& !kodeKZ1.equals("н") && !inCodeNotNumber(kodeKZ1)) {
											simpleKode[0] = kodeKZ1;
										}
										if (!kodeKZ2.equals("н") && !kodeKZ2.trim().equals("")
												&& !inCodeNotNumber(kodeKZ2)) {
											simpleKode[1] = kodeKZ2;
										}
										if (!kodeHOG.equals("н") && !kodeHOG.trim().equals("")
												&& !inCodeNotNumber(kodeHOG)) {
											simpleKode[2] = kodeHOG;
										}
										if (!kodeT1.equals("н") && !kodeT1.trim().equals("")
												&& !inCodeNotNumber(kodeT1)) {
											simpleKode[3] = kodeT1;
										}
										if (!kodeT2.equals("н") && !kodeT2.trim().equals("")
												&& !inCodeNotNumber(kodeT2)) {
											simpleKode[4] = kodeT2;
										}
										simpleKode[5] = egn;
										
										listKodeStatus.add(simpleKode);
									}

								}
								row++;
							}
//	*********************************************************************************	

						}
					}
				}
			}
		}
		return listKodeStatus;
	}

	private static String NormKode(int maxString, String kod) {
		boolean fl = true;
		String subKod = "";
		String emptyString = "";
		String firstChar = "";
		System.out.println(kod.length());
		firstChar = kod.substring(0, 1);
		subKod = kod.substring(1);
		fl = firstCharIsLeter(firstChar);

		if (kod.length() < maxString) {

			for (int i = 0; i < (maxString - kod.length()); i++) {
				emptyString = emptyString + " ";
			}
			if (fl) {
				kod = firstChar + emptyString + subKod;
			} else {
				kod = emptyString + kod;
			}

		}
		return kod;
	}

	private static boolean firstCharIsLeter(String kod) {
		try {
			Integer.parseInt(kod);
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	public static String[] getFilePathForPersonelAndExternal() {
		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel");
		String[] excellFiles = { filePathPersonel, filePathExternal };
		return excellFiles;
	}

	private static String[] generateEmptyMasive() {
		String[] sinpleKode = new String[6];
		for (int i = 0; i < sinpleKode.length; i++) {
			sinpleKode[i] = "";
		}
		return sinpleKode;
	}

	static boolean inCodeNotNumber(String kode) {
		return kode.replaceAll("\\d*", "").length() == kode.length();
	}

	public static void ListKodeStatus(List<KodeStatus> list) {

		for (KodeStatus kodeStatus : list) {
			System.out.println(kodeStatus.getPerson().getEgn() + " " + kodeStatus.getKode() + " "
					+ kodeStatus.getZone().getNameTerritory() + " " + kodeStatus.getisFreeKode() + " "
					+ kodeStatus.getYear() + kodeStatus.getZabelejkaKodeStatus());

		}
	}

	public static void setToDBaseListKodeStatus(List<KodeStatus> list) {
		List<KodeStatus> listDublicateKodeStatus = new ArrayList<KodeStatus>();
		for (KodeStatus kodeStatus : list) {
			if (KodeStatusDAO.setObjectKodeStatusToTable(kodeStatus) == null) {
				listDublicateKodeStatus.add(kodeStatus);
			}
			;
		}
		if (listDublicateKodeStatus.size() > 0) {
			ListKodeStatus(listDublicateKodeStatus);
		}
	}

}
