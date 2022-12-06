package Aplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.Workplace;

//четене на списък-приложения от годишния ексел фаил

public class ReadSpisak_PrilogeniaFromExcelFile {
	
	public static List<Spisak_Prilogenia> getSpisak_Prilogenia_ListFromExcelFile(String FILE_PATH, String firmName, String year) {

		Workbook workbook = ReadExcelFileWBC.openExcelFile(FILE_PATH);
		List<Spisak_Prilogenia> kodeGenerate_List = new ArrayList<Spisak_Prilogenia>();
		List<Workplace> listAllWorkplaceBiFirmName = WorkplaceDAO.getValueWorkplaceByObject("FirmName", firmName);
		String otdelName = "", formulyarName = "";
		Date startDate , endDate ;
		Workplace workplace = new Workplace();
		String[] masiveWorkplace = ReadExcelFileWBC.getMasiveString(firmName);
		Sheet sheet = workbook.getSheetAt(3);
		Cell cell, cell1;
		for (int row = 0; row <= sheet.getLastRowNum(); row += 1) {
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);
				if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
					otdelName = cell1.getStringCellValue();
					if (!otdelName.equals("край")) {
						workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName, listAllWorkplaceBiFirmName);
						System.out.println(otdelName+" 1 "+workplace.getOtdel());
						int k = 7;
						cell = sheet.getRow(row).getCell(k);
						while (ReadExcelFileWBC.CellNOEmpty(cell)) {
							
							formulyarName = cell.getStringCellValue();
							k++;
							cell = sheet.getRow(row).getCell(k);
							startDate = ReadExcelFileWBC.readCellToDate(cell);
							k++;
							cell = sheet.getRow(row).getCell(k);
							endDate = ReadExcelFileWBC.readCellToDate(cell);
							k++;
							cell = sheet.getRow(row).getCell(k);
							
							Spisak_Prilogenia spPr = new Spisak_Prilogenia(formulyarName, year, startDate, endDate, workplace, "");
													
							kodeGenerate_List.add(spPr);
						}
					}

				}
			}
		}
		return kodeGenerate_List;
	}

	public static void ListSpisak_PrilogeniaToBData(List<Spisak_Prilogenia> Spisak_PrilogeniaList) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");
		for (Spisak_Prilogenia spPr : Spisak_PrilogeniaList) {
			
			System.out.println("  ->  " + spPr.getWorkplace().getOtdel() + " - " + spPr.getFormulyarName() + " - "
					+ sdfrmt.format(spPr.getStartDate()) + " - " + sdfrmt.format(spPr.getEndDate()));
			
		}
	}

	public static void setListSpisak_PrilogeniaToBData(List<Spisak_Prilogenia> Spisak_PrilogeniaList) {
		for (Spisak_Prilogenia spPr : Spisak_PrilogeniaList) {
			Spisak_PrilogeniaDAO.setObjectSpisak_PrilogeniaToTable(spPr);
		}
	}	
	
}
