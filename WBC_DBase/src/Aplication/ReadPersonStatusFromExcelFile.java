package Aplication;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;


public class ReadPersonStatusFromExcelFile {
		
	public static List<PersonStatus> getListPersonStatusFromExcelFile(String pathFile, String firmName, String year) {
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<PersonStatus> listPerStat = new ArrayList<>();
		java.sql.Date dateSet = java.sql.Date.valueOf(LocalDate.now());
		Person person;
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);
		List<Workplace> listAllWorkplaceBiFirmName = WorkplaceDAO.getValueWorkplaceByObject("FirmName", firmName);
		String EGN = "", FirstName = "", zab = "";
		Date startDate;
		Date endDate;
		String otdelName = "", formulyarName = "";
		Workplace workplace = new Workplace();
		String[] masiveWorkplace = ReadExcelFileWBC.getMasiveString(firmName);
		Sheet sheet = workbook.getSheetAt(3);
		Cell cell, cell1;
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {
			zab = "";
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);

				if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
					otdelName = cell1.getStringCellValue();
					if (!otdelName.contains("край")) {
						workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName, listAllWorkplaceBiFirmName);
					}
				}

				if (ReadExcelFileWBC.CellNOEmpty(cell) && workplace.getOtdel() != null) {
					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
					if(cell1.getCellComment()!=null) {
					zab = cell1.getCellComment().getString().getString();
					}
					person = PersonDAO.getValuePersonByEGN(EGN);
					if(person==null) {
						MessageDialog(FirstName);
					}
			
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

						Spisak_Prilogenia spPr = search_Spisak_Prilogenia(formulyarName, startDate, endDate, workplace, year);
						PersonStatus personStat = new PersonStatus(person, workplace, spPr, userSet, dateSet, "");
						listPerStat.add(personStat);
					}
					
					listPerStat.get(listPerStat.size() - 1).setZabelejka(zab);
				}
			}

		}

		return listPerStat;

	}

	public static void ListPersonStatus(List<PersonStatus> list) {

		for (PersonStatus personStatus : list) {

			System.out.println(personStatus.getPerson().getEgn() + " " + personStatus.getWorkplace().getOtdel() + " "
					+ personStatus.getSpisak_prilogenia().getFormulyarName() + " "
					+ personStatus.getUserWBC().getLastName() + " " + personStatus.getZabelejka().toString() + " "
					+ personStatus.getDateSet().toString());

		}

	}

	public static void setToBDateListPersonStatus(List<PersonStatus> list) {

		for (PersonStatus personStatus : list) {
			PersonStatusDAO.setObjectPersonStatusToTable(personStatus);
			
		}

	}
	
	private static Spisak_Prilogenia search_Spisak_Prilogenia(String formulyarName, Date startDate, Date endDate,
			Workplace workplace, String year) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");
		

		List<Spisak_Prilogenia> listSpPr = Spisak_PrilogeniaDAO
				.getValueSpisak_PrilogeniaByObjectSortByColumnName("FormulyarName", formulyarName, "FormulyarName");
		
		
		System.out.println("Br SpPr ot Db " + listSpPr.size());
		Spisak_Prilogenia spPr = null, spisPril = null;
		boolean fl_Data = true;
		
		
		for (Spisak_Prilogenia spisak_Prilogenia : listSpPr) {

			System.out.println("ot Bdata " + spisak_Prilogenia.getFormulyarName() + " "
					+ sdfrmt.format(spisak_Prilogenia.getStartDate()) + " "
					+ sdfrmt.format(spisak_Prilogenia.getEndDate()) + " "
					+ spisak_Prilogenia.getWorkplace().getOtdel());

			System.out.println("ot Excel " + formulyarName + " " + sdfrmt.format(startDate) + " "
					+ sdfrmt.format(endDate) + " " + workplace.getOtdel());

			if (sdfrmt.format(startDate).equals(sdfrmt.format(spisak_Prilogenia.getStartDate()))
					&& sdfrmt.format(endDate).equals(sdfrmt.format(spisak_Prilogenia.getEndDate()))) {
				if (workplace.getOtdel().equals(spisak_Prilogenia.getWorkplace().getOtdel()) || workplace.getSecondOtdelName().equals(spisak_Prilogenia.getWorkplace().getSecondOtdelName())) {
					System.out.println("------------------------" + spisak_Prilogenia.getFormulyarName());
					fl_Data = false;
					spPr = spisak_Prilogenia;
					return spisak_Prilogenia;
				}
			}
		}

		if(fl_Data) {
		System.out.println("=========" + formulyarName + " " + sdfrmt.format(startDate) + " " + sdfrmt.format(endDate)
				+ " " + workplace.getOtdel());
		
		spisPril = new Spisak_Prilogenia(formulyarName, year, startDate, endDate, workplace, "нов запис");
				Spisak_PrilogeniaDAO.setObjectSpisak_PrilogeniaToTable(spisPril);
				spPr = search_Spisak_Prilogenia(formulyarName, startDate, endDate, workplace, year);
		
		}
		
		return spPr;
	}

		
	
	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}
}
