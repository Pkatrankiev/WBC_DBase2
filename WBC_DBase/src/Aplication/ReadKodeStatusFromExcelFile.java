package Aplication;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.WorkplaceDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.Workplace;

public class ReadKodeStatusFromExcelFile {

	public static List<KodeStatus> getListPersonStatusFromExcelFile(String pathFile, String firmName, String year) {
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<KodeStatus> listKodeStatus = new ArrayList<>();
		Person person;
		List<Workplace> listAllWorkplaceBiFirmName = WorkplaceDAO.getValueWorkplaceByObject("FirmName", firmName);
		String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "", kodeT1 = "", kodeT2 = "";
		String EGN = "", FirstName = "";
		String otdelName = "";
		Workplace workplace = new Workplace();
		String[] masiveWorkplace = ReadExcelFileWBC.getMasiveString(firmName);
		Sheet sheet = workbook.getSheetAt(3);
		Cell cell, cell1;
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);

				if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
					otdelName = cell1.getStringCellValue();
					if (!otdelName.contains("край")) {
						workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName,
								listAllWorkplaceBiFirmName);
					}
				}

				if (ReadExcelFileWBC.CellNOEmpty(cell) && workplace.getOtdel() != null) {
					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
					person = PersonDAO.getValuePersonByEGN(EGN);
					if (person == null) {
						ReadPersonStatusFromExcelFile.MessageDialog(FirstName);
					}

					cell = sheet.getRow(row).getCell(0);
					kodeKZ1 = cell.getStringCellValue();

					cell = sheet.getRow(row).getCell(1);
					kodeKZ2 = cell.getStringCellValue();

					cell = sheet.getRow(row).getCell(2);
					kodeHOG = cell.getStringCellValue();

					cell = sheet.getRow(row).getCell(3);
					kodeT2 = cell.getStringCellValue();

					cell = sheet.getRow(row).getCell(4);
					kodeT1 = cell.getStringCellValue();

					if (pathFile.contains("EXTERNAL")) {
						kodeT1 = kodeT2;
						kodeT2 = "";
					}

					
					if (!kodeKZ1.equals("≈ѕ-2") && !kodeKZ1.trim().equals("") && !kodeKZ1.equals("н")) {
						listKodeStatus.add(new KodeStatus(person, kodeKZ1, ZoneDAO.getValueZoneByID(1), true, year, ""));
					}
					if (!kodeKZ2.equals("н") && !kodeKZ2.trim().equals("")) {
						listKodeStatus.add(new KodeStatus(person, kodeKZ2, ZoneDAO.getValueZoneByID(2), true, year, ""));
					}
					if (!kodeHOG.equals("н") && !kodeHOG.trim().equals("")) {
						listKodeStatus.add(new KodeStatus(person, kodeHOG, ZoneDAO.getValueZoneByID(3), true, year, ""));
					}
					if (!kodeT1.equals("н") && !kodeT1.trim().equals("")) {
						listKodeStatus.add(new KodeStatus(person, kodeT1, ZoneDAO.getValueZoneByID(4), true, year, ""));
					}
					if (!kodeT2.equals("н") && !kodeT2.trim().equals("")) {
						listKodeStatus.add(new KodeStatus(person, kodeT2, ZoneDAO.getValueZoneByID(5), true, year, ""));
					}

				}
			}

		}

		return listKodeStatus;

	}

	public static void ListKodeStatus(List<KodeStatus> list) {

		for (KodeStatus kodeStatus : list) {
			System.out.println(kodeStatus.getPerson().getEgn() + " " + kodeStatus.getKode() + " "
					+ kodeStatus.getZone().getNameTerritory() + " " + kodeStatus.getisFreeKode() + " "
					+ kodeStatus.getYear());

			
		}
	}

	public static void setToDBaseListKodeStatus(List<KodeStatus> list) {
		List<KodeStatus> listDublicateKodeStatus = new ArrayList<KodeStatus>();
		for (KodeStatus kodeStatus : list) {
			if(KodeStatusDAO.setObjectKodeStatusToTable(kodeStatus)==null) {
				listDublicateKodeStatus.add(kodeStatus);
			};
		}
		if(listDublicateKodeStatus.size()>0) {
			ListKodeStatus(listDublicateKodeStatus);
		}
	}
	
	
}
