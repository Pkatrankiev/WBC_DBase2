package Aplication;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		if(workbook.getNumberOfSheets()>2) {
			listPerStat = getListPersonStatusFromBigExcelFile(workbook, firmName, year);
				
		}else {
			listPerStat = getListPersonStatusFromSmalExcelFile(workbook,firmName,  year);	
		}
		return listPerStat;
	}
	
	
	
	private static List<PersonStatus> getListPersonStatusFromSmalExcelFile(Workbook workbook, String firmName,
			String year) {
		Spisak_Prilogenia spPr = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(546);
		List<PersonStatus> listPerStat = new ArrayList<>();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date dateSet = null;
		try {
			dateSet = sdfrmt.parse("31.12." + year);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Person person;
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);
		List<Workplace> listAllWorkplaceBiFirmName = WorkplaceDAO.getValueWorkplaceByObject("FirmName", firmName);
		String EGN = "", FirstName = "", zab = "";
		
		String otdelName = "";
		Workplace workplace = new Workplace();
		String[] masiveWorkplace = ReadExcelFileWBC.getMasiveString(firmName);
		Sheet sheet = workbook.getSheetAt(0);
		Cell cell, cell1;
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {
			zab = "";
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);

				if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
					otdelName = cell1.getStringCellValue().trim();
					if (!otdelName.contains("край") && !otdelName.contains("КРАЙ")) {
						workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName, listAllWorkplaceBiFirmName);
					}
				}

				if (ReadExcelFileWBC.CellNOEmpty(cell) && workplace.getOtdel() != null) {
					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					if(EGN.contains("*")) EGN = EGN.substring(0, EGN.length()-1);
					FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
					
					person = PersonDAO.getValuePersonByEGN(EGN);
					if(person==null) {
						MessageDialog(FirstName);
					}
			
					cell = sheet.getRow(row).getCell(0);
					if(cell!= null && cell.getCellComment()!=null) {
						zab = cell.getCellComment().getString().getString();
						}

					cell = sheet.getRow(row).getCell(1);
					if(cell!= null && cell.getCellComment()!=null) {
						zab = zab + cell.getCellComment().getString().getString();
						}

					cell = sheet.getRow(row).getCell(2);
					if(cell!= null && cell.getCellComment()!=null) {
						zab = zab + cell.getCellComment().getString().getString();
						}

					cell = sheet.getRow(row).getCell(3);
					if(cell!= null && cell.getCellComment()!=null) {
						zab = zab + cell.getCellComment().getString().getString();
						}

					cell = sheet.getRow(row).getCell(4);
					if(cell!= null && cell.getCellComment()!=null) {
						zab = zab + cell.getCellComment().getString().getString();
						}
					
				
					
				if(PersonStatusDAO.getValuePersonStatusByPersonAndWorkplace(person, workplace).size()<1) {
					
						PersonStatus personStat = new PersonStatus(person, workplace, spPr, userSet, dateSet, zab);
						listPerStat.add(personStat);
				}
						
				}
			}

		}
		return listPerStat;
	}

	public static List<PersonStatus> getListPersonStatusFromBigExcelFile(Workbook workbook, String firmName, String year) {
		 
		List<PersonStatus> listPerStat = new ArrayList<>();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date dateSet = null;
		try {
			dateSet = sdfrmt.parse("31.12." + year);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Person person;
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);
		List<Workplace> listAllWorkplaceBiFirmName = WorkplaceDAO.getValueWorkplaceByObject("FirmName", firmName);
		String EGN = "", FirstName = "", zab = "";
		Date startDate = null;
		Date endDate = null;
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
					otdelName = cell1.getStringCellValue().trim();
					if (!otdelName.contains("край") && !otdelName.contains("КРАЙ")) {
						workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName, listAllWorkplaceBiFirmName);
					}
				}

				if (ReadExcelFileWBC.CellNOEmpty(cell) && workplace.getOtdel() != null) {
					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					if(EGN.contains("*")) EGN = EGN.substring(0, EGN.length()-1);
					FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
					if(cell1.getCellComment()!=null) {
					zab = cell1.getCellComment().getString().getString();
					}
					person = PersonDAO.getValuePersonByEGN(EGN);
					if( person==null) {
						MessageDialog(FirstName);
					}
			
					int k = 7;
					cell = sheet.getRow(row).getCell(k);
					while (ReadExcelFileWBC.CellNOEmpty(cell)) {
						Spisak_Prilogenia spPr =  ReadSpisak_PrilogeniaFromExcelFile.getOrCreateSisak_Prilogenie(k,  row,   sheet,  startDate,  endDate, formulyarName,  workplace,  year);
						
						PersonStatus personStat = new PersonStatus(person, workplace, spPr, userSet, dateSet, "");
						listPerStat.add(personStat);
						
						k = k+3;
						cell = sheet.getRow(row).getCell(k);
					
					}
					if(listPerStat.size()>1)
					listPerStat.get(listPerStat.size() - 1).setZabelejka(zab);
				}
			}

		}

		return listPerStat;

	}

	
	
	
	public static void ListPersonStatus(List<PersonStatus> list) {

		for (PersonStatus personStatus : list) {

			System.out.println(personStatus.getPerson().getEgn() + " " 
					+ personStatus.getWorkplace().getOtdel() + " "
					+ personStatus.getSpisak_prilogenia().getFormulyarName() + " "
					+ personStatus.getUserWBC().getLastName() + " "
					+ personStatus.getZabelejka().toString() + " "
					+ personStatus.getDateSet().toString());

		}

	}

	public static void setToBDateListPersonStatus(List<PersonStatus> list) {

		for (PersonStatus personStatus : list) {
			PersonStatusDAO.setObjectPersonStatusToTable(personStatus);
			
		}

	}
	

	
	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}
}
