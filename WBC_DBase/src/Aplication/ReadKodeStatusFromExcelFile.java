package Aplication;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

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
					if(EGN.contains("*")) EGN = EGN.substring(0, EGN.length()-1);
					FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
					person = PersonDAO.getValuePersonByEGN(EGN);
					if (person == null) {
						ReadPersonStatusFromExcelFile.MessageDialog(FirstName);
					}
					
						
					
					cell = sheet.getRow(row).getCell(0);
					if(cell!=null)
					kodeKZ1 = cell.getStringCellValue();

					cell = sheet.getRow(row).getCell(1);
					if(cell!=null)
					kodeKZ2 = cell.getStringCellValue();

					cell = sheet.getRow(row).getCell(2);
					if(cell!=null)
					kodeHOG = cell.getStringCellValue();

					cell = sheet.getRow(row).getCell(3);
					if(cell!=null)
					kodeT2 = cell.getStringCellValue();

					cell = sheet.getRow(row).getCell(4);
					if(cell!=null)
					kodeT1 = cell.getStringCellValue();
				
					if (pathFile.contains("EXTERNAL")) {
						kodeT1 = kodeT2;
						kodeT2 = "";
					}

					
					if (!kodeKZ1.equals("ЕП-2") && !kodeKZ1.trim().equals("") && !kodeKZ1.equals("н") && !inCodeNotNumber(kodeKZ1)) {
						listKodeStatus.add(new KodeStatus(person, kodeKZ1, ZoneDAO.getValueZoneByID(1), true, year, zab));
					}
					if (!kodeKZ2.equals("н") && !kodeKZ2.trim().equals("") && !inCodeNotNumber(kodeKZ2)) {
						listKodeStatus.add(new KodeStatus(person, kodeKZ2, ZoneDAO.getValueZoneByID(2), true, year, zab));
					}
					if (!kodeHOG.equals("н") && !kodeHOG.trim().equals("") && !inCodeNotNumber(kodeHOG)) {
						listKodeStatus.add(new KodeStatus(person, kodeHOG, ZoneDAO.getValueZoneByID(3), true, year, zab));
					}
					if (!kodeT1.equals("н") && !kodeT1.trim().equals("") && !inCodeNotNumber(kodeT1)) {
						listKodeStatus.add(new KodeStatus(person, kodeT1, ZoneDAO.getValueZoneByID(4), true, year, zab));
					}
					if (!kodeT2.equals("н") && !kodeT2.trim().equals("") && !inCodeNotNumber(kodeT2)) {
						listKodeStatus.add(new KodeStatus(person, kodeT2, ZoneDAO.getValueZoneByID(5), true, year, zab));
					}

				}
			}

		}
		return listKodeStatus;
	}

	
	static boolean inCodeNotNumber(String kode) {
		return kode.replaceAll("\\d*", "").length() == kode.length();
	}

	public static void ListKodeStatus(List<KodeStatus> list) {

		for (KodeStatus kodeStatus : list) {
			System.out.println(kodeStatus.getPerson().getEgn() + " " + kodeStatus.getKode() + " "
					+ kodeStatus.getZone().getNameTerritory() + " " + kodeStatus.getisFreeKode() + " "
					+ kodeStatus.getYear()+ kodeStatus.getZabelejkaKodeStatus());

			
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
