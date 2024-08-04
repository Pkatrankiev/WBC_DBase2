package Aplication;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import BasiClassDAO.KodeGenerateDAO;
import BasiClassDAO.WorkplaceDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeGenerate;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.Zone;

public class ReadKodeGenerateFromExcelFile {

	public static List<KodeGenerate> getKodeGenerate_ListFromExcelFile(Workbook workbook, String teritori,
			String firmName) {
		List<KodeGenerate> kodeGenerate_List = new ArrayList<KodeGenerate>();
		String textNulCell = "", letter_L = "", letter_R = "";
		int startCount = 0, endCount = 0;
		String[] masiveWorkplace = ReadExcelFileWBC.getMasiveString(firmName);
		Zone zone = ZoneDAO.getValueZoneByObject("NameTerritory", teritori).get(0);
		Workplace workplace = new Workplace();
		List<Workplace> listAllWorkplaceBiFirmName = WorkplaceDAO.getValueWorkplaceByObject("FirmName", firmName);
		Sheet sheet = workbook.getSheetAt(0);

		Cell cell;
		for (int row = 0; row <= sheet.getLastRowNum(); row += 1) {
			System.out.println(row);
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(0);
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					textNulCell = cell.getStringCellValue();
					System.out.println(row + "  -  " + textNulCell);

					if (!textNulCell.isEmpty()) {

						cell = sheet.getRow(row).getCell(0);
						String otdelName = cell.getStringCellValue();

						workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName, listAllWorkplaceBiFirmName);

						cell = sheet.getRow(row).getCell(1);
						System.out.println(cell.getStringCellValue());
						letter_L = cell.getStringCellValue();

						cell = sheet.getRow(row).getCell(2);
						System.out.println(cell.getStringCellValue());
						letter_R = cell.getStringCellValue();

						cell = sheet.getRow(row).getCell(3);
						System.out.println(cell.getNumericCellValue());
						startCount = (int) cell.getNumericCellValue();
						System.out.println(startCount);

						cell = sheet.getRow(row).getCell(4);
						System.out.println(cell.getNumericCellValue());
						endCount = (int) cell.getNumericCellValue();
						System.out.println(endCount);
					}
				}
			}

			kodeGenerate_List.add(new KodeGenerate(workplace, zone, letter_L, letter_R, startCount, endCount));
		}

		for (KodeGenerate kode : kodeGenerate_List) {
			System.out.println(kode.getZone().getNameTerritory() + " " + kode.getWorkplace().getFirmName() + " "
					+ kode.getWorkplace().getOtdel() + " " + kode.getLetter_L() + " " + kode.getLetter_R() + " "
					+ kode.getStartCount() + " " + kode.getEndCount());
		}

		return kodeGenerate_List;
	}
	
	public static void setListKodeGeneratetoBData(List<KodeGenerate> kodeGenerateList) {
		for (KodeGenerate kodeGenerate : kodeGenerateList) {
			
			KodeGenerateDAO.setObjectKodeGenerateToTable(kodeGenerate);
		}
	}

	
	
	
	
}
