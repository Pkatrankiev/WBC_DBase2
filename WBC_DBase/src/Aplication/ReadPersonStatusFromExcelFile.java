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

	static Spisak_Prilogenia spPrNotInfo = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(546);

	
	public static List<PersonStatus> getListPersonStatusFromExcelFile(String pathFile, String firmName, String year) {
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<PersonStatus> listPerStat = new ArrayList<>();
		if (workbook.getNumberOfSheets() > 2) {
			listPerStat = getListPersonStatusFromBigExcelFile(workbook, firmName, year);

		} else {
			listPerStat = getListPersonStatusFromSmalExcelFile(workbook, firmName, year);
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
					if (EGN.contains("*"))
						EGN = EGN.substring(0, EGN.length() - 1);
					FirstName = ReadExcelFileWBC.getStringfromCell(cell1);

					person = PersonDAO.getValuePersonByEGN(EGN);
					if (person == null) {
						MessageDialog(FirstName);
					}

					cell = sheet.getRow(row).getCell(0);
					if (cell != null && cell.getCellComment() != null) {
						zab = cell.getCellComment().getString().getString();
					}

					cell = sheet.getRow(row).getCell(1);
					if (cell != null && cell.getCellComment() != null) {
						zab = zab + cell.getCellComment().getString().getString();
					}

					cell = sheet.getRow(row).getCell(2);
					if (cell != null && cell.getCellComment() != null) {
						zab = zab + cell.getCellComment().getString().getString();
					}

					cell = sheet.getRow(row).getCell(3);
					if (cell != null && cell.getCellComment() != null) {
						zab = zab + cell.getCellComment().getString().getString();
					}

					cell = sheet.getRow(row).getCell(4);
					if (cell != null && cell.getCellComment() != null) {
						zab = zab + cell.getCellComment().getString().getString();
					}

					if (PersonStatusDAO.getValuePersonStatusByPersonAndWorkplace(person, workplace).size() < 1) {

						PersonStatus personStat = new PersonStatus(person, workplace, spPr, userSet, dateSet, zab);
						listPerStat.add(personStat);
					}

				}
			}

		}
		return listPerStat;
	}

	public static List<PersonStatus> getListPersonStatusFromBigExcelFile(Workbook workbook, String firmName,
			String year) {

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
						workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName,
								listAllWorkplaceBiFirmName);
					}
				}

				if (ReadExcelFileWBC.CellNOEmpty(cell) && workplace.getOtdel() != null) {
					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					if (EGN.contains("*"))
						EGN = EGN.substring(0, EGN.length() - 1);
					FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
					
					zab = searchComent(workbook, row);
					
					person = PersonDAO.getValuePersonByEGN(EGN);
					if (person == null) {
						MessageDialog(FirstName);
					}

					int k = 7;
					cell = sheet.getRow(row).getCell(k);
					while (ReadExcelFileWBC.CellNOEmpty(cell)) {
						Spisak_Prilogenia spPr = ReadSpisak_PrilogeniaFromExcelFile.getOrCreateSisak_Prilogenie(k, row,
								sheet, startDate, endDate, formulyarName, workplace, year);

						PersonStatus personStat = new PersonStatus(person, workplace, spPr, userSet, dateSet, "");
						listPerStat.add(personStat);

						k = k + 3;
						cell = sheet.getRow(row).getCell(k);

					}
					if(listPerStat.size()>0) {
						listPerStat.get(listPerStat.size() - 1).setZabelejka(zab);
						}else {
							if(!zab.isEmpty()) {
							listPerStat.add(new PersonStatus(person, workplace, spPrNotInfo, userSet, dateSet, zab));
							}
						}
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

	public static List<PersonStatus> getListPersonStatusWithoutSpisak_Prilogenia(String egn) {
		List<PersonStatus> listPerStat = new ArrayList<>();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		List<Person> listAllPerson = new ArrayList<>();
				
		if(egn.isEmpty()) {
			listAllPerson = PersonDAO.getAllValuePerson();	
		}else {
			listAllPerson.add(PersonDAO.getValuePersonByEGN(egn));
		}
		
		Date dateSet;
		int k =0;
		for (Person person : listAllPerson) {
			System.out.println(k+" *************************************  "+person.getEgn());
			if (!person.getFirstName().isEmpty()) {
				for (int year = 2005; year < 2023; year++) {
					System.out.println(year);
					try {
						dateSet = sdfrmt.parse("31.12." + year);

						if (PersonStatusDAO.getValuePersonStatusByPersonAndDateSet(person, dateSet).size() == 0) {
							PersonStatus perStat = setNewPersonStatusWithOutSpisakPrilogenia(person, dateSet, year);
							if (perStat != null) {
								listPerStat.add(perStat);
							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				System.out.println(listPerStat.size() + " *************************************");
			}
			k++;
		}

		return listPerStat;
	}

	private static PersonStatus setNewPersonStatusWithOutSpisakPrilogenia(Person person, Date dateSet, int year) {
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_ArhivePersonalAndExternal();
		for (String pathFile : excellFiles) {
			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";
			}

			pathFile = pathFile + year + ".xls";
			Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
			return getPersonStatusFromExcelFile(workbook, firmName, year + "", person);

		}
		return null;
	}

	private static PersonStatus getPersonStatusFromExcelFile(Workbook workbook, String firmName, String year,
			Person person) {
		Spisak_Prilogenia spPr = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(546);
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date dateSet = null;
		try {
			dateSet = sdfrmt.parse("31.12." + year);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);
		List<Workplace> listAllWorkplaceBiFirmName = WorkplaceDAO.getValueWorkplaceByObject("FirmName", firmName);
		String EGN = "", zab = "";

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
						workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName,
								listAllWorkplaceBiFirmName);
					}
				}

				if (ReadExcelFileWBC.CellNOEmpty(cell) && workplace.getOtdel() != null) {
					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					if (EGN.contains("*"))
						EGN = EGN.substring(0, EGN.length() - 1);

					if (person.getEgn().equals(EGN)) {

						return new PersonStatus(person, workplace, spPr, userSet, dateSet, zab);

					}

				}
			}

		}
		return null;
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}


	public static String searchComent(Workbook workbook,  int row) {
		String zab0 = "", zab3 = "";
		
		Sheet sheet = workbook.getSheetAt(0);
		Cell cell = sheet.getRow(row).getCell(6);
		if (cell.getCellComment() != null) {
			zab0 = cell.getCellComment().getString().getString();
		}
		
		sheet = workbook.getSheetAt(3);
		cell = sheet.getRow(row).getCell(6);
		if (cell.getCellComment() != null) {
			zab3 = cell.getCellComment().getString().getString();
		}
		
		if(zab0.isEmpty()) {
			zab0 = zab3;
		}
		
		return zab0;
	}


}
