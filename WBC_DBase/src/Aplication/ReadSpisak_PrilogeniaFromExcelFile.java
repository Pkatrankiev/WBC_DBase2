package Aplication;

import java.text.ParseException;
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
import UpdateDBaseFromExcelFiles.UpdateBDataFromExcellFiles;

//четене на списък-приложения от годишния ексел фаил

public class ReadSpisak_PrilogeniaFromExcelFile {

	public static List<Spisak_Prilogenia> getSpisak_Prilogenia_ListFromExcelFile(String FILE_PATH, String firmName,
			String year, ActionIcone round,  String textIcon, List<Integer> listDiferentRow, List<String> arreaOtdels) {
		

		Workbook workbook = ReadExcelFileWBC.openExcelFile(FILE_PATH);
		List<Spisak_Prilogenia> spisak_Prilogenia_List = new ArrayList<Spisak_Prilogenia>();

		if (workbook.getNumberOfSheets() > 2) {

			List<Workplace> listAllWorkplaceBiFirmName = WorkplaceDAO.getValueWorkplaceByObject("FirmName", firmName);
			String otdelName = "", formulyarName = "";
			Date startDate = null, endDate = null;
			
			Workplace workplace = new Workplace();
			String[] masiveWorkplace = ReadExcelFileWBC.getMasiveString(firmName);
			Sheet sheet = workbook.getSheetAt(3);
			Cell cell, cell1;
			
			int StartRow = 0;
			int endRow = 0;
			
			if(listDiferentRow != null) {
				StartRow = 0;
				endRow = listDiferentRow.size();
			}else {
				StartRow = 0;
				endRow = sheet.getLastRowNum();
			}
			int row = 0;
			for (int index = StartRow; index < endRow ; index += 1) {

				if(listDiferentRow != null) {
					row = listDiferentRow.get(index);
					otdelName = UpdateBDataFromExcellFiles.getOtdelNameByListArreaOtdels(arreaOtdels, row);
					workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName,
							listAllWorkplaceBiFirmName);
				}else {
					row  = index;
				}
				
				if (sheet.getRow(row) != null) {
					cell = sheet.getRow(row).getCell(5);
					cell1 = sheet.getRow(row).getCell(6);
					if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
						otdelName = cell1.getStringCellValue();
						if (!otdelName.equals("край")) {
							workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName,
									listAllWorkplaceBiFirmName);
							System.out.println(otdelName + " 1 " + workplace.getOtdel());
							int k = 7;
							cell = sheet.getRow(row).getCell(k);
							while (ReadExcelFileWBC.CellNOEmpty(cell)) {
								if(listDiferentRow != null) {
									
								}
								Spisak_Prilogenia spPr =  getOrCreateSisak_Prilogenie(k,  row,   sheet,  startDate,  endDate, formulyarName,  workplace,  year);
								
								k = k+3;
								cell = sheet.getRow(row).getCell(k);
								if(workplace.getId_Workplace()==38) {
									System.out.println("************************************"+workplace.getOtdel());
									System.out.println(spPr.getFormulyarName()+" "+spPr.getYear()+" "+spPr.getStartDate()+" "+spPr.getEndDate()+" "+workplace.getOtdel());

								}
								spisak_Prilogenia_List.add(spPr);
							}
						}

					}
				}
				ActionIcone.roundWithText(round, textIcon, "Read", index, endRow);
			}
		}
		return spisak_Prilogenia_List;
	}


	


	public static Spisak_Prilogenia getOrCreateSisak_Prilogenie(int k, int row,  Sheet sheet, Date startDate, Date endDate, String formulyarName, Workplace workplace, String year) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date endDate0 = null, startDate0 = null;
		try {
			startDate0 = sdfrmt.parse("01.01." + year);
			endDate0 = sdfrmt.parse("31.12." + year);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		Cell cell = sheet.getRow(row).getCell(k);
		
		formulyarName = cell.getStringCellValue();
		k++;
		cell = sheet.getRow(row).getCell(k);
		if (ReadExcelFileWBC.CellNOEmpty(cell)
				&& !ReadExcelFileWBC.getStringfromCell(cell).isEmpty()) {
			startDate = ReadExcelFileWBC.readCellToDate(cell);
		} else {
			startDate = startDate0;
		}
		k++;
		cell = sheet.getRow(row).getCell(k);
		if (ReadExcelFileWBC.CellNOEmpty(cell)
				&& !ReadExcelFileWBC.getStringfromCell(cell).isEmpty()) {
			endDate = ReadExcelFileWBC.readCellToDate(cell);
		} else {
			endDate = endDate0;
		}
		k++;
		cell = sheet.getRow(row).getCell(k);
		Spisak_Prilogenia spPr = new Spisak_Prilogenia(formulyarName, year, startDate, endDate, workplace, "from Arhive");
//	Spisak_Prilogenia spPr = search_Spisak_Prilogenia(formulyarName, startDate, endDate, workplace, year);
	
	return spPr;	
	
	}	
	
	@SuppressWarnings("unused")
	private static Spisak_Prilogenia search_Spisak_Prilogenia(String formulyarName, Date startDate, Date endDate,
			Workplace workplace, String year) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");
		

		List<Spisak_Prilogenia> listSpPr = Spisak_PrilogeniaDAO
				.getValueSpisak_PrilogeniaByObjectSortByColumnName("FormulyarName", formulyarName, "FormulyarName");
		
		
//		System.out.println("Br SpPr ot Db " + listSpPr.size());
		Spisak_Prilogenia spPr = null, spisPril = null;
		boolean fl_Data = true;
		
		
		for (Spisak_Prilogenia spisak_Prilogenia : listSpPr) {

//			System.out.println("ot Bdata " + spisak_Prilogenia.getFormulyarName() + " "
//					+ sdfrmt.format(spisak_Prilogenia.getStartDate()) + " "
//					+ sdfrmt.format(spisak_Prilogenia.getEndDate()) + " "
//					+ spisak_Prilogenia.getWorkplace().getOtdel());
//
//			System.out.println("ot Excel " + formulyarName + " " + sdfrmt.format(startDate) + " "
//					+ sdfrmt.format(endDate) + " " + workplace.getOtdel());

			if (sdfrmt.format(startDate).equals(sdfrmt.format(spisak_Prilogenia.getStartDate()))
					&& sdfrmt.format(endDate).equals(sdfrmt.format(spisak_Prilogenia.getEndDate()))) {
				if (workplace.getOtdel().equals(spisak_Prilogenia.getWorkplace().getOtdel()) 
//						||(spisak_Prilogenia.getWorkplace().getSecondOtdelName()!= null && workplace.getSecondOtdelName()!=null 
//						&& workplace.getSecondOtdelName().equals(spisak_Prilogenia.getWorkplace().getSecondOtdelName()))
						) {

					fl_Data = false;
					spPr = spisak_Prilogenia;
					return spisak_Prilogenia;
				}
			}
		}

		if(fl_Data) {
//		System.out.println("=========" + formulyarName + " " + sdfrmt.format(startDate) + " " + sdfrmt.format(endDate)
//				+ " " + workplace.getOtdel());
		
		spisPril = new Spisak_Prilogenia(formulyarName, year, startDate, endDate, workplace, "from Arhive");
				Spisak_PrilogeniaDAO.setObjectSpisak_PrilogeniaToTable(spisPril);
				spPr = search_Spisak_Prilogenia(formulyarName, startDate, endDate, workplace, year);
		
		}
		
		return spPr;
	}

	
	public static void ListSpisak_PrilogeniaToBData(List<Spisak_Prilogenia> Spisak_PrilogeniaList) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");
		for (Spisak_Prilogenia spPr : Spisak_PrilogeniaList) {

			System.out.println("  ->  " + spPr.getWorkplace().getOtdel() + " - " + spPr.getFormulyarName() + " - "
					+ sdfrmt.format(spPr.getStartDate()) + " - " + sdfrmt.format(spPr.getEndDate()));

		}
	}

	public static void setListSpisak_PrilogeniaToBData(List<Spisak_Prilogenia> Spisak_PrilogeniaList, ActionIcone round,  String textIcon) {
		int k=0;
		int l=Spisak_PrilogeniaList.size();
		for (Spisak_Prilogenia spPr : Spisak_PrilogeniaList) {
			Spisak_PrilogeniaDAO.setObjectSpisak_PrilogeniaToTable(spPr);
			ActionIcone.roundWithText(round, textIcon, "Save", k, l);
		}
	}

}
