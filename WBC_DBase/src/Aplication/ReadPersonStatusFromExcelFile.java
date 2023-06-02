package Aplication;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
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
	static Spisak_Prilogenia spPrObhodList = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(11177);
	
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

	public static List<PersonStatus> getObhodenListPersonStatusFromExcelFile(String pathFile, String firmName, String year) {
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<PersonStatus> listPerStat = new ArrayList<>();
		if (workbook.getNumberOfSheets() > 2) {
			listPerStat = getObhodenList_PersonStatusFromBigExcelFile(workbook, firmName, year);
		} 
		return listPerStat;
	}
	
	public static Workplace getWorkplaceByEGNFromExcell(Workbook workbook, String firmName, String personEGN) {
		List<Workplace> listAllWorkplaceBiFirmName = WorkplaceDAO.getValueWorkplaceByObject("FirmName", firmName);
		String[] masiveWorkplace = ReadExcelFileWBC.getMasiveString(firmName);
		Sheet sheet = workbook.getSheetAt(0);
	String otdelName = "";
	String EGN = "";
	
	Cell cell, cell1;
	Workplace workplace = new Workplace();
	for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {
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
				if (EGN.contains("*")) {
					EGN = EGN.substring(0, EGN.length() - 1);
				}
					if(EGN.equals(personEGN)) {
					return workplace;
				}
							}
		}
	}
	
	String ss = InputDialog(masiveWorkplace, otdelName);
	workplace = WorkplaceDAO.getValueWorkplaceByObject("Otdel", ss).get(0);
	return workplace;
	}
	
	public static String InputDialog(String[] options, String input) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		ImageIcon icon = new ImageIcon("src/images/turtle32.png");
		String n = (String) JOptionPane.showInputDialog(null, input, "Изберете отдел", JOptionPane.QUESTION_MESSAGE,
				icon, options, options[2]);
		System.out.println(n);
		return n;
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

	public static List<PersonStatus> getObhodenList_PersonStatusFromBigExcelFile(Workbook workbook,	String firmName, String year) {
				
		List<PersonStatus> listPerStat = new ArrayList<>();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date dateSet = null, dateObhList = null;
		

		Person person;
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);
		String EGN = "", FirstName = "", zab = "";
		Workplace workplace = new Workplace();
		Sheet sheet = workbook.getSheetAt(4);
		Cell cell, cell1;
		for (int row = 0; row <= sheet.getLastRowNum(); row += 1) {
			zab = "";
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
						MessageDialog(FirstName);
					}
					
					cell = sheet.getRow(row).getCell(7);
					String dataObhodelist = ReadExcelFileWBC.getStringfromCell(cell);
					dataObhodelist = dataObhodelist.replace("Обходен лист от", "").replace("г.", "").trim();
					
					try {
						dateObhList = sdfrmt.parse(dataObhodelist);
						dateSet = sdfrmt.parse("31.12." + year);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					System.out.println(person.getEgn()+"  "+ sdfrmt.format(dateObhList));
					
					if(!PersonStatusDAO.PersonWithObhodenList(person)) {
					
					workplace = getWorkplaceByEGNFromExcell( workbook,  firmName, person.getEgn());
					
					System.out.println(workplace.getOtdel());
					
					spPrObhodList.setStartDate(dateObhList);
					spPrObhodList.setYear(year);
					spPrObhodList.setWorkplace(workplace);
					
					Spisak_PrilogeniaDAO.setObjectSpisak_PrilogeniaToTable(spPrObhodList);
					Spisak_Prilogenia spisPril = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByYear_Workplace_StartDate(year, dateObhList, workplace.getId_Workplace());
					if(spisPril!=null) {
					listPerStat.add(new PersonStatus(person, workplace, spisPril, userSet, dateSet, zab));
					}else {
						spPrObhodList.getFormulyarName() ;
					}
					}	
				}
			}

		}

		return listPerStat;

	}
		
	public static void ListPersonStatus(List<PersonStatus> list) {
		System.out.println(list.size());
		for (PersonStatus personStatus : list) {
			System.out.println(personStatus.getPersonStatus_ID());
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
