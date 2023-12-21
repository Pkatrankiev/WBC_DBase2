package Aplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;

import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;

public class ReadKodeStatusFromExcelFile {

	static String[] pathToArhiveExcellFiles = AplicationMetods.getDataBaseFilePat_ArhivePersonalAndExternal();

	static String[] pathToFiles_ActualPersonalAndExternal = AplicationMetods
			.getDataBaseFilePat_OriginalPersonalAndExternal();

	static int curentYear = Calendar.getInstance().get(Calendar.YEAR);

	public static List<KodeStatus> getListKodeStatusFromExcelFile(String pathFile, String firmName, String year, ActionIcone round,  String textIcon, List<Integer> listDiferentRow) {
		
		Set<String> mySet = new HashSet<String>();
		int countMySet;
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<KodeStatus> listKodeStatus = new ArrayList<>();
		Person person;
		String zab = "From Arhive";
		String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "", kodeT1 = "", kodeT2 = "";
		String EGN = "", FirstName = "";
		Sheet sheet = workbook.getSheetAt(0);
		Cell cell, cell1;
		
		int StartRow = 0;
		int endRow = 0;
		
		if(listDiferentRow != null) {
			StartRow = 0;
			endRow = listDiferentRow.size();
		}else {
			StartRow = 5;
			endRow = sheet.getLastRowNum();
		}
		int row = 0;
		for (int index = StartRow; index < endRow ; index += 1) {

			if(listDiferentRow != null) {
				row = listDiferentRow.get(index);
			}else {
				row  = index;
			}
			
			kodeKZ1 = "";
			kodeKZ2 = "";
			kodeHOG = "";
			kodeT1 = "";
			kodeT2 = "";
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);

				if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
					
					person = getPersonFromEGNCell(cell);
					EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					countMySet = mySet.size();
					mySet.add(EGN);
					if( (countMySet+1) == mySet.size()) {
					FirstName = ReadExcelFileWBC.getStringEGNfromCell(cell1);
					if (person == null) {
						System.out.println(EGN+" - "+FirstName);
						ReadPersonStatusFromExcelFile.MessageDialog(EGN+" - "+FirstName);
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
			ActionIcone.roundWithText(round, textIcon, "Read", index, endRow);
		}
		return listKodeStatus;
	}

	public static String[] getUsedKodeFromExcelFile(int zoneID) {
		String[] excellFiles_ActualPersonalAndExternal = AplicationMetods
				.getDataBaseFilePat_OriginalPersonalAndExternal();
		List<String[]> listKodeStatus = new ArrayList<>();
		for (String pathFile : excellFiles_ActualPersonalAndExternal) {
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
		if (listKodeStatus.size() > 0) {
			List<String> list = new ArrayList<>();
			for (String[] kod : listKodeStatus) {
				if (!kod[zoneID - 1].isEmpty()) {
					list.add(kod[zoneID - 1]);
				}
			}
			if (list.size() > 0) {
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
		}
		return null;
	}

	public static List<String[]> generateListFromMasiveEGNandKode(String zveno) {
		boolean flOtdel = false;
		String[] excellFiles_ActualPersonalAndExternal = AplicationMetods
				.getDataBaseFilePat_OriginalPersonalAndExternal();
		List<String[]> listKodeStatus = new ArrayList<>();
		String otdelName = "";
		for (String pathFile : excellFiles_ActualPersonalAndExternal) {
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
											globalRow = allRow;
										}
									}

									if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
										egn = ReadExcelFileWBC.getStringEGNfromCell(cell);
										if (egn.contains("*"))
											egn = egn.substring(0, egn.length() - 1);

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

	public static List<String[]> generateListForAllFromMasiveEGNandKode(String year) {
		List<String[]> listKodeStatus = new ArrayList<>();
		int insertYear = Integer.parseInt(year);
		String[] path = pathToArhiveExcellFiles;
		if (insertYear == curentYear) {
			path = pathToFiles_ActualPersonalAndExternal;
		}

		for (String pathFile : path) {

			if (insertYear != curentYear) {
				pathFile = pathFile + year + ".xls";
			}
			Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);

			String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "", kodeT1 = "", kodeT2 = "", egn = "";
			Sheet sheet = workbook.getSheetAt(0);
			Cell cell, cell1;
			int allRow = sheet.getLastRowNum();
			for (int row = 5; row <= allRow; row += 1) {
				if (sheet.getRow(row) != null) {

					String[] simpleKode = generateEmptyMasive();
					kodeKZ1 = "";
					kodeKZ2 = "";
					kodeHOG = "";
					kodeT1 = "";
					kodeT2 = "";
					egn = "";

					cell = sheet.getRow(row).getCell(5);
					cell1 = sheet.getRow(row).getCell(6);

					if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
						egn = getEGNFromENGCell(cell);

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
							simpleKode[0] = kodeKZ1;
						}
						if (!kodeKZ2.equals("н") && !kodeKZ2.trim().equals("") && !inCodeNotNumber(kodeKZ2)) {
							simpleKode[1] = kodeKZ2;
						}
						if (!kodeHOG.equals("н") && !kodeHOG.trim().equals("") && !inCodeNotNumber(kodeHOG)) {
							simpleKode[2] = kodeHOG;
						}
						if (!kodeT1.equals("н") && !kodeT1.trim().equals("") && !inCodeNotNumber(kodeT1)) {
							simpleKode[3] = kodeT1;
						}
						if (!kodeT2.equals("н") && !kodeT2.trim().equals("") && !inCodeNotNumber(kodeT2)) {
							simpleKode[4] = kodeT2;
						}
						simpleKode[5] = egn;

						listKodeStatus.add(simpleKode);
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

	static Person getPersonFromEGNCell(Cell cell) {
		String EGN = getEGNFromENGCell(cell);
Person person = PersonDAO.getValuePersonByEGN(EGN);
		return person;
	}

	public static String getEGNFromENGCell(Cell cell) {
		String EGN = ReadExcelFileWBC.getStringEGNfromCell(cell);
		if(EGN.contains("*")) EGN = EGN.substring(0, EGN.length()-1);
		return EGN;
	}
	
	
	private static boolean firstCharIsLeter(String kod) {
		try {
			Integer.parseInt(kod);
		} catch (Exception e) {
			return true;
		}
		return false;
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

	public static void setToDBaseListKodeStatus(List<KodeStatus> list, ActionIcone round,  String textIcon) {
		int k=0;
		int l=list.size();
		List<KodeStatus> listDublicateKodeStatus = new ArrayList<KodeStatus>();
		for (KodeStatus kodeStatus : list) {
			if (KodeStatusDAO.setObjectKodeStatusToTable(kodeStatus) == null) {
				listDublicateKodeStatus.add(kodeStatus);
			}
			ActionIcone.roundWithText(round, textIcon, "Save", k, l);
			k++;
		}
		if (listDublicateKodeStatus.size() > 0) {
			ListKodeStatus(listDublicateKodeStatus);
		}
	}

}
