package Aplication;

import java.util.Date;
import java.util.HashSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;

public class ReadPersonStatusFromExcelFile {

	
	static Spisak_Prilogenia spPrObhodList = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByObject("FormulyarName", "Обходен лист").get(0);
	
	public static List<PersonStatus> getListPersonStatusFromExcelFile(String pathFile, String firmName, String year, ActionIcone round,  String textIcon, List<Integer> listDiferentRow) {
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<PersonStatus> listPerStat = new ArrayList<>();
		if (workbook.getNumberOfSheets() > 2) {
			listPerStat = getListPersonStatusFromBigExcelFile(workbook, firmName, year, round, textIcon, listDiferentRow); 

		} else {
			listPerStat = getListPersonStatusFromSmalExcelFile(workbook, firmName, year);
		}
		return listPerStat;
	}

	public static List<PersonStatusNew> getListPersonStatusNewFromExcelFile(String pathFile, String firmName, String year, ActionIcone round,  String textIcon, List<Integer> listDiferentRow) {
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<PersonStatusNew> listPerStat = new ArrayList<>();
		
			listPerStat = getListPersonStatusNewFromBigExcelFile(workbook, firmName, year, round, textIcon, listDiferentRow); 
		
		return listPerStat;
	}
	
	public static List<PersonStatus> getObhodenListPersonStatusFromExcelFile(String pathFile, String firmName, String year, ActionIcone round,  String textIcon) {
		System.out.println(pathFile+"  "+firmName+"  "+year);
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<PersonStatus> listPerStat = new ArrayList<>();
		if (workbook.getNumberOfSheets() > 2) {
			System.out.println(workbook.getNumberOfSheets());
			listPerStat = getObhodenList_PersonStatusFromBigExcelFile(pathFile, workbook, firmName, year, round, textIcon);
		} 
		return listPerStat;
	}
	
	public static List<PersonStatusNew> getObhodenListPersonStatusNewFromExcelFile(String pathFile, String firmName, String year, ActionIcone round,  String textIcon) {
		System.out.println(pathFile+"  "+firmName+"  "+year);
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<PersonStatusNew> listPerStat = new ArrayList<>();
		if (workbook.getNumberOfSheets() > 2) {
			System.out.println(workbook.getNumberOfSheets());
			listPerStat = getObhodenList_PersonStatusNewFromBigExcelFile(pathFile, workbook, firmName, year, round, textIcon);
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
				EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
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
		String FirstName = "", zab = "";

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
					person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
					FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
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
			String year, ActionIcone round,  String textIcon, List<Integer> listDiferentRow) {

		Set<String> mySet = new HashSet<String>();
		int countMySet;
		
		List<PersonStatus> listPerStat = new ArrayList<>();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date dateSet = null,nulldate= null;
		try {
			dateSet = sdfrmt.parse("31.12." + year);
			nulldate = sdfrmt.parse("01.01." + year);
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
		boolean fl ;
		
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
			

			zab = "";
			fl = false;
			PersonStatus personStatus_NotInList = null;
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

//				workplace(54) - Транспортиране на СЯГ и ОЯГ;  workplace(101) - Транспортиране СОЯГ 
				if (ReadExcelFileWBC.CellNOEmpty(cell) && workplace.getOtdel() != null && workplace.getId_Workplace()!= 54 && workplace.getId_Workplace()!= 101) {
					FirstName = ReadExcelFileWBC.getStringEGNfromCell(cell1);
					zab = searchComent(workbook, row);
					
					EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					countMySet = mySet.size();
					mySet.add(EGN);
					person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
					if (person == null && (countMySet+1) == mySet.size()) {
						MessageDialog(EGN+" - "+FirstName);
					}

					int k = 7;
					cell = sheet.getRow(row).getCell(k);
					List<PersonStatus> ListPrStat = PersonStatusDAO.getValuePersonStatusByPerson_Workplace_DateSetInYear(person, workplace, year);
					
					if(ListPrStat.size()==1 && ListPrStat.get(0).getSpisak_prilogenia().getFormulyarName().equals("NotInList")) {
						personStatus_NotInList = ListPrStat.get(0); 
					}
					
					
					
					while (ReadExcelFileWBC.CellNOEmpty(cell)) {
						Spisak_Prilogenia spPr = ReadSpisak_PrilogeniaFromExcelFile.getOrCreateSisak_Prilogenie(k, row,
								sheet, startDate, endDate, formulyarName, workplace, year);
							
						if(personStatus_NotInList != null) {
								personStatus_NotInList.setSpisak_prilogenia(spPr);
								personStatus_NotInList.setUserWBC(userSet);
								personStatus_NotInList.setDateSet(dateSet);
								PersonStatusDAO.updateValuePersonStatus(personStatus_NotInList);
								personStatus_NotInList = null;
							}else {
								if(workplace.getOtdel().equals(spPr.getWorkplace().getOtdel())) {
						PersonStatus personStat = new PersonStatus(person, workplace, spPr, userSet, dateSet, "");
						listPerStat.add(personStat);
								}
							}

						k = k + 3;
						cell = sheet.getRow(row).getCell(k);
						
						fl = true;

					}
					if(listPerStat.size()>0 && fl) {
						listPerStat.get(listPerStat.size() - 1).setZabelejka(zab);
						}else {
							Spisak_PrilogeniaDAO.setValueSpisak_Prilogenia("NotInList", year, nulldate, dateSet, workplace, "");
							Spisak_Prilogenia spPrNotInfo = Spisak_PrilogeniaDAO.getListSpisak_PrilogeniaByFormulyarName_Year_Workplace("NotInList", year, workplace.getId_Workplace());
						
							if(spPrNotInfo != null) {
							if(workplace.getOtdel().equals(spPrNotInfo.getWorkplace().getOtdel())) {
							listPerStat.add(new PersonStatus(person, workplace, spPrNotInfo, userSet, dateSet, zab));
							}
						}
				}
			}
			}
			ActionIcone.roundWithText(round, textIcon, "Read", index, endRow);
		}

		return listPerStat;

	}

	public static List<PersonStatusNew> getListPersonStatusNewFromBigExcelFile(Workbook workbook, String firmName,
			String year, ActionIcone round,  String textIcon, List<Integer> listDiferentRow) {

		Set<String> mySet = new HashSet<String>();
		int countMySet;
		
		List<PersonStatusNew> listPerStatNew = new ArrayList<>();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date dateSet = null,nulldate= null;
		try {
//			dateSet = sdfrmt.parse("31.12." + year);
			dateSet = new Date();
			nulldate = sdfrmt.parse("01.01." + year);
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
		boolean fl ;
		
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
			

			zab = "";
			fl = false;
			PersonStatusNew personStatusNew_NotInList = null;
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

//				workplace(54) - Транспортиране на СЯГ и ОЯГ;  workplace(101) - Транспортиране СОЯГ 
				if (ReadExcelFileWBC.CellNOEmpty(cell) && workplace.getOtdel() != null && workplace.getId_Workplace()!= 54 && workplace.getId_Workplace()!= 101) {
					FirstName = ReadExcelFileWBC.getStringEGNfromCell(cell1);
					zab = searchComent(workbook, row);
					
					EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					countMySet = mySet.size();
					mySet.add(EGN);
					person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
					if (person == null && (countMySet+1) == mySet.size()) {
						MessageDialog(EGN+" - "+FirstName);
					}

					int k = 7;
					cell = sheet.getRow(row).getCell(k);
					List<PersonStatusNew> ListPrStatNew = PersonStatusNewDAO.getValuePersonStatusNewByPerson_Workplace_DateSetInYear(person, workplace, year);
					
					if(ListPrStatNew.size()==1 && ListPrStatNew.get(0).getFormulyarName().equals("NotInList")) {
						personStatusNew_NotInList = ListPrStatNew.get(0); 
					}
					
					
					
					while (ReadExcelFileWBC.CellNOEmpty(cell)) {
						Spisak_Prilogenia spPr = ReadSpisak_PrilogeniaFromExcelFile.getOrCreateSisak_Prilogenie(k, row,
								sheet, startDate, endDate, formulyarName, workplace, year);
							
						if(personStatusNew_NotInList != null) {
								personStatusNew_NotInList.setFormulyarName(spPr.getFormulyarName());
								personStatusNew_NotInList.setStartDate(spPr.getStartDate());
								personStatusNew_NotInList.setEndDate(spPr.getEndDate());
								personStatusNew_NotInList.setYear(spPr.getYear());
								personStatusNew_NotInList.setUserWBC(userSet);
								personStatusNew_NotInList.setDateSet(dateSet);
								PersonStatusNewDAO.updateValuePersonStatusNew(personStatusNew_NotInList);
								personStatusNew_NotInList = null;
							}else {
								if(workplace.getOtdel().equals(spPr.getWorkplace().getOtdel())) {
						PersonStatusNew personStatNew = new PersonStatusNew(person, workplace, spPr.getFormulyarName(), spPr.getStartDate(), spPr.getEndDate(), spPr.getYear(), userSet, dateSet, "");
						listPerStatNew.add(personStatNew);
								}
							}

						k = k + 3;
						cell = sheet.getRow(row).getCell(k);
						
						fl = true;

					}
					if(listPerStatNew.size()>0 && fl) {
						listPerStatNew.get(listPerStatNew.size() - 1).setZabelejka(zab);
						}else {
							Spisak_PrilogeniaDAO.setValueSpisak_Prilogenia("NotInList", year, nulldate, dateSet, workplace, "");
							Spisak_Prilogenia spPrNotInfo = Spisak_PrilogeniaDAO.getListSpisak_PrilogeniaByFormulyarName_Year_Workplace("NotInList", year, workplace.getId_Workplace());
						
							if(spPrNotInfo != null) {
							if(workplace.getOtdel().equals(spPrNotInfo.getWorkplace().getOtdel())) {
							listPerStatNew.add(new PersonStatusNew(person, workplace, spPrNotInfo.getFormulyarName(), spPrNotInfo.getStartDate(), 
									spPrNotInfo.getEndDate(), spPrNotInfo.getYear(), userSet, dateSet, zab));
							}
						}
				}
			}
			}
			ActionIcone.roundWithText(round, textIcon, "Read", index, endRow);
		}

		return listPerStatNew;

	}

	
	
	public static List<PersonStatus> getObhodenList_PersonStatusFromBigExcelFile(String pathFile, Workbook workbook,	String firmName, String year, ActionIcone round,  String textIcon) {
				
		List<PersonStatus> listPerStat = new ArrayList<>();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date dateSet = null, dateObhList = null;
		

		Person person;
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);
		String FirstName = "", zab = "", dataObhodelistExt = "", dataObhodelist = "";
		Workplace workplace = new Workplace();
		Sheet sheet = workbook.getSheetAt(4);
		Cell cell, cell0, cell1;
		System.out.println(sheet.getLastRowNum());
		for (int row = 0; row <= sheet.getLastRowNum(); row += 1) {
			zab = "";
			if (sheet.getRow(row) != null) {
				
				cell = sheet.getRow(row).getCell(3);
				cell1 = sheet.getRow(row).getCell(4);
				
				if (pathFile.contains("EXTERNAL")) {
					cell0 = sheet.getRow(row).getCell(0);
					if (ReadExcelFileWBC.CellNOEmpty(cell0) && ReadExcelFileWBC.CellNOEmpty(cell0)) {
						dataObhodelistExt = ReadExcelFileWBC.getStringfromCell(cell0);
						
					}
					cell = sheet.getRow(row).getCell(4);
					cell1 = sheet.getRow(row).getCell(5);
					
				}
				
				

				if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
					FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
					person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
					if (person == null) {
						MessageDialog(FirstName);
					}
					
					
					
					try {
						if (pathFile.contains("EXTERNAL")) {
							cell = sheet.getRow(row).getCell(7);
							dataObhodelist = ReadExcelFileWBC.getStringfromCell(cell);
							zab = dataObhodelist;
							dateObhList = sdfrmt.parse(dataObhodelistExt);
						}else {
							cell = sheet.getRow(row).getCell(6);
							dataObhodelist = ReadExcelFileWBC.getStringfromCell(cell);
							zab = dataObhodelist;
						int index = dataObhodelist.indexOf("от")+2;
							dataObhodelist = dataObhodelist.substring(index,index+11).trim();
						dateObhList = sdfrmt.parse(dataObhodelist);
						}
						dateSet = sdfrmt.parse("31.12." + year);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					System.out.println("егн "+person.getEgn()+"  "+ sdfrmt.format(dateObhList));
					
//					if(!PersonStatusDAO.PersonWithObhodenList(person)) {
					
					workplace = PersonStatusDAO.getLastValuePersonStatusByPerson( person).getWorkplace();
					
					System.out.println(workplace.getOtdel());
					
					spPrObhodList.setStartDate(dateObhList);
					spPrObhodList.setYear(year);
					spPrObhodList.setWorkplace(workplace);
					
					Spisak_PrilogeniaDAO.setObjectSpisak_PrilogeniaToTable(spPrObhodList);
					Spisak_Prilogenia spisPril = Spisak_PrilogeniaDAO.getLastSaveObjectFromValueSpisak_PrilogeniaByYear_Workplace_StartDate(year, dateObhList, workplace.getId_Workplace());
					if(spisPril!=null) {
					listPerStat.add(new PersonStatus(person, workplace, spisPril, userSet, dateSet, zab));
					}else {
						spPrObhodList.getFormulyarName() ;
					}
//					}	
				}
			}
			ActionIcone.roundWithText(round, textIcon, "Read", row, sheet.getLastRowNum());
		}

		return listPerStat;

	}
	
	public static List<PersonStatusNew> getObhodenList_PersonStatusNewFromBigExcelFile(String pathFile, Workbook workbook,	String firmName, String year, ActionIcone round,  String textIcon) {
		
		List<PersonStatusNew> listPerStat = new ArrayList<>();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date dateSet = null, dateObhList = null;
		

		Person person;
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);
		String FirstName = "", zab = "", dataObhodelistExt = "", dataObhodelist = "";
		Workplace workplace = new Workplace();
		Sheet sheet = workbook.getSheetAt(4);
		Cell cell, cell0, cell1;
		System.out.println(sheet.getLastRowNum());
		for (int row = 0; row <= sheet.getLastRowNum(); row += 1) {
			zab = "";
			if (sheet.getRow(row) != null) {
				
				cell = sheet.getRow(row).getCell(3);
				cell1 = sheet.getRow(row).getCell(4);
				
				if (pathFile.contains("EXTERNAL")) {
					cell0 = sheet.getRow(row).getCell(0);
					if (ReadExcelFileWBC.CellNOEmpty(cell0) && ReadExcelFileWBC.CellNOEmpty(cell0)) {
						dataObhodelistExt = ReadExcelFileWBC.getStringfromCell(cell0);
						
					}
					cell = sheet.getRow(row).getCell(4);
					cell1 = sheet.getRow(row).getCell(5);
					
				}
				
				

				if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
					FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
					person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
					if (person == null) {
						MessageDialog(FirstName);
					}
					
					
					
					try {
						if (pathFile.contains("EXTERNAL")) {
							cell = sheet.getRow(row).getCell(7);
							dataObhodelist = ReadExcelFileWBC.getStringfromCell(cell);
							zab = dataObhodelist;
							dateObhList = sdfrmt.parse(dataObhodelistExt);
						}else {
							cell = sheet.getRow(row).getCell(6);
							dataObhodelist = ReadExcelFileWBC.getStringfromCell(cell);
							zab = dataObhodelist;
						int index = dataObhodelist.indexOf("от")+2;
							dataObhodelist = dataObhodelist.substring(index,index+11).trim();
						dateObhList = sdfrmt.parse(dataObhodelist);
						}
						dateSet = sdfrmt.parse("31.12." + year);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					System.out.println("егн "+person.getEgn()+"  "+ sdfrmt.format(dateObhList));
					
//					if(!PersonStatusDAO.PersonWithObhodenList(person)) {
					
					workplace = PersonStatusNewDAO.getLastValuePersonStatusNewByPerson( person).getWorkplace();
					
					System.out.println(workplace.getOtdel());
					
					spPrObhodList.setStartDate(dateObhList);
					spPrObhodList.setYear(year);
					spPrObhodList.setWorkplace(workplace);
					
					Spisak_PrilogeniaDAO.setObjectSpisak_PrilogeniaToTable(spPrObhodList);
					Spisak_Prilogenia spisPril = Spisak_PrilogeniaDAO.getLastSaveObjectFromValueSpisak_PrilogeniaByYear_Workplace_StartDate(year, dateObhList, workplace.getId_Workplace());
					if(spisPril!=null) {
					listPerStat.add(new PersonStatusNew(person, workplace, spisPril.getFormulyarName(),  spisPril.getStartDate(), spisPril.getEndDate(), spisPril.getYear(), userSet,  dateSet, zab));
					}else {
						spPrObhodList.getFormulyarName() ;
					}
//					}	
				}
			}
			ActionIcone.roundWithText(round, textIcon, "Read", row, sheet.getLastRowNum());
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

	public static void ListPersonStatusNew(List<PersonStatusNew> list) {
		System.out.println(list.size());
		for (PersonStatusNew personStatus : list) {
			System.out.println(personStatus.getPersonStatusNew_ID());
			System.out.println(personStatus.getPerson().getEgn() + " " + personStatus.getWorkplace().getOtdel() + " "
					+ personStatus.getFormulyarName() + " "
					+ personStatus.getUserWBC().getLastName() + " " + personStatus.getZabelejka().toString() + " "
					+ personStatus.getDateSet().toString());

		}

	}
	

	public static void setToBDateListPersonStatus(List<PersonStatus> list, ActionIcone round,  String textIcon) {
		int k=0;
		int l=list.size();
		for (PersonStatus personStatus : list) {
			PersonStatusDAO.setObjectPersonStatusToTable(personStatus);
			ActionIcone.roundWithText(round, textIcon, "Save", k, l);
			k++;

		}

	}
	
	public static void setToBDateListPersonStatusNew(List<PersonStatusNew> list, ActionIcone round,  String textIcon) {
		int k=0;
		int l=list.size();
		for (PersonStatusNew personStatus : list) {
			PersonStatusNewDAO.setObjectPersonStatusNewToTable(personStatus);
			ActionIcone.roundWithText(round, textIcon, "Save", k, l);
			k++;

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

	public static List<PersonStatusNew> getListPersonStatusNewWithoutSpisak_Prilogenia(String egn) {
		List<PersonStatusNew> listPerStat = new ArrayList<>();
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
							PersonStatusNew perStat = setNewPersonStatusNewWithOutSpisakPrilogenia(person, dateSet, year);
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
	
	private static PersonStatusNew setNewPersonStatusNewWithOutSpisakPrilogenia(Person person, Date dateSet, int year) {
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_ArhivePersonalAndExternal();
		for (String pathFile : excellFiles) {
			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";
			}

			pathFile = pathFile + year + ".xls";
			Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
			return getPersonStatusNewFromExcelFile(workbook, firmName, year + "", person);

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
					EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					if (person.getEgn().equals(EGN)) {

						return new PersonStatus(person, workplace, spPr, userSet, dateSet, zab);

					}

				}
			}

		}
		return null;
	}

	private static PersonStatusNew getPersonStatusNewFromExcelFile(Workbook workbook, String firmName, String year,
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
					EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					if (person.getEgn().equals(EGN)) {

						return new PersonStatusNew(person, workplace, spPr.getFormulyarName(), spPr.getStartDate(), spPr.getEndDate(), spPr.getYear(), userSet, dateSet, zab);

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
