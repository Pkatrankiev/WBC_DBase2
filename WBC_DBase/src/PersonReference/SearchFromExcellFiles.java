package PersonReference;

import java.awt.Choice;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.AplicationMetods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.ReadMeasuringFromExcelFile;
import Aplication.ReadPersonStatusFromExcelFile;
import Aplication.ReadSpisak_PrilogeniaFromExcelFile;
import Aplication.RemouveDublikateFromList;
import AutoInsertMeasuting.SaveReportMeasurTo_PersonelORExternalExcelFile;
import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.NuclideWBCDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.NuclideWBC;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.TypeMeasur;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;


public class SearchFromExcellFiles {
	
	static String[] pathToArhiveExcellFiles = AplicationMetods.getDataBaseFilePat_ArhivePersonalAndExternal();	
		
	static String[] pathToFiles_OriginalPersonalAndExternal = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
	
	static int curentYear = Calendar.getInstance().get(Calendar.YEAR);
	static Spisak_Prilogenia spPrNotInfo = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(546);

	protected static void addListStringSelectionPersonToComboBox(List<PersonExcellClass> listSelectionPerson, Choice comboBox_Results) {
		comboBox_Results.removeAll();
		List<String> list = new ArrayList<>();
		for (PersonExcellClass person : listSelectionPerson) {
			list.add(person.getPerson().getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person.getPerson()));
		}
		Collections.sort(list);
		for (String str : list) {
			comboBox_Results.add(str);
		}

	}
	
	protected static String[][] addListStringSelectionPersonExcellClassToComboBox(List<PersonExcellClass> listSelectionPerson) {
		
		String[][] dataTable = new String[listSelectionPerson.size()][8];
	
//				"EGN", 	"FirstName","SecondName","LastName","Otdel",
//				"Kod KZ-1",	"Kod KZ-2",	"Kod Hog"	
				
		int k = 0;	
		for (PersonExcellClass person : listSelectionPerson) {
			dataTable[k][0] = person.getPerson().getEgn() ;
			dataTable[k][1] = person.getPerson().getFirstName();
			dataTable[k][2] = person.getPerson().getSecondName();
			dataTable[k][3] = person.getPerson().getLastName();
			dataTable[k][4] = person.getOtdel();
			dataTable[k][5] = person.getKz1();
			dataTable[k][6] = person.getKz2();
			dataTable[k][7] = person.getHog();
			
			k++;
		}
		
		return dataTable;

	}

	protected static String createInfoPanelForPersonFromExcell(PersonExcellClass excellPerson) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		String year = PersonReferenceFrame.getTextField_Year().getText(); 
		String str = excellPerson.getPerson().getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(excellPerson.getPerson()) + "\n";
		str = str + year + "\n";
		str = str + "Кодове: \n";
		List<KodeStatus> listK = KodeStatusDAO.getValueKodeStatusByObjectSortByColumnName("Person_ID", excellPerson.getPerson(), "Year");
		
			for (KodeStatus kodeStat : listK) {
				if (year.trim().isEmpty() || kodeStat.getYear().equals(year)) {
					
					str = str + kodeStat.getYear() + "  " + kodeStat.getZone().getNameTerritory() + " - " + kodeStat.getKode() + " "
							+ kodeStat.getZabelejkaKodeStatus() + "\n";
				}
			
		}
			
		str = str + "\n";
		str = str + "Заповеди \n";
		List<PersonStatus> listP = PersonStatusDAO.getValuePersonStatusByObject("Person_ID", excellPerson.getPerson());
		if(!year.trim().isEmpty()) {
			for (PersonStatus perStat : listP) {
				List<Spisak_Prilogenia> listS =	Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByObject("Year", year);
				for (Spisak_Prilogenia spPr : listS) {
				if (perStat.getSpisak_prilogenia().getSpisak_Prilogenia_ID() == spPr.getSpisak_Prilogenia_ID()) {
					str = str +TextInAreaTextPanel.generateRowByMasive( perStat);
				}
			}
				}
		}else {
			for (PersonStatus perStat : listP) {
				str = str +TextInAreaTextPanel.generateRowByMasive( perStat);
				}
			}
				
		str = str + "\n";
		str = str + "Измервания СИЧ \n";
		List<Measuring> listM = MeasuringDAO.getValueMeasuringByObject("Person_ID", excellPerson.getPerson());
		String data;
		
			for (Measuring measur : listM) {
				List<ResultsWBC> listR = ResultsWBCDAO.getValueResultsWBCByObject("Measuring_ID", measur);
				data = sdf.format(measur.getDate()).substring(6);
				System.out.println(data);
				if (year.trim().isEmpty() || data.equals(year)) {
					str = str + data + "  " + sdf.format(measur.getDate()) +" "+ measur.getDoze() 
							+" "+ measur.getDoseDimension().getDimensionName() 
							+" "+ measur.getLab().getLab()+ "\n";
					for (ResultsWBC result : listR) {
						str = str + result.getNuclideWBC().getSymbol_nuclide() 
						+" "+ result.getActivity() +" "+  DimensionWBCDAO.getValueDimensionWBCByID(4).getDimensionName()
						+" "+ result.getPostaplenie()  +" "+  DimensionWBCDAO.getValueDimensionWBCByID(4).getDimensionName() 
						+" "+ result.getGgp()   +" "+  DimensionWBCDAO.getValueDimensionWBCByID(7).getDimensionName()
						+" "+ result.getNuclideDoze()  +" "+  DimensionWBCDAO.getValueDimensionWBCByID(2).getDimensionName()
						+ "\n";	
					}
				
			}
		}
		
		
		return str;
	}

	protected static List<PersonExcellClass> getListSearchingPerson() {
		
			
		
	
		List<PersonExcellClass> listSelectionPersonEGN = new ArrayList<>();
		List<PersonExcellClass> listSelectionPersonFName = new ArrayList<>();
		List<PersonExcellClass> listSelectionPersonSName = new ArrayList<>();
		List<PersonExcellClass> listSelectionPersonLName = new ArrayList<>();
		List<PersonExcellClass> listSelectionPersonKZ1 = new ArrayList<>();
		List<PersonExcellClass> listSelectionPersonKZ2 = new ArrayList<>();
		List<PersonExcellClass> listSelectionPersonKZHog = new ArrayList<>();
		List<PersonExcellClass> listSelectionPersonOtdel = new ArrayList<>();

		String egn = PersonReferenceFrame.getTextField_EGN().getText();
		String firstName = PersonReferenceFrame.getTextField_FName().getText();
		String secontName = PersonReferenceFrame.getTextField_SName().getText();
		String lastName = PersonReferenceFrame.getTextField_LName().getText();
		String kz1 = PersonReferenceFrame.getTextField_KZ1().getText();
		String kz2 = PersonReferenceFrame.getTextField_KZ2().getText();
		String kzHog = PersonReferenceFrame.getTextField_KZHOG().getText();
		String year = PersonReferenceFrame.getTextField_Year().getText();
		String otdel = PersonReferenceFrame.getComboBox_Otdel().getSelectedItem();
		
		List<PersonExcellClass> listAllExcellPerson = getPersonFromExcelFile(year);
	if (!egn.trim().isEmpty()) {
			for (PersonExcellClass excellPerson : listAllExcellPerson) {
				if (excellPerson.getPerson().getEgn().contains(egn)) {
					listSelectionPersonEGN.add(excellPerson);
				}
			}
		} else {
			listSelectionPersonEGN = listAllExcellPerson;
		}

		if (!firstName.trim().isEmpty()) {
			for (PersonExcellClass person : listSelectionPersonEGN) {
				if (checkContainsStrings(person.getPerson().getFirstName(),firstName)) {
					listSelectionPersonFName.add(person);
				}
			}
		} else {
			listSelectionPersonFName = listSelectionPersonEGN;
		}

		if (!secontName.trim().isEmpty()) {
			for (PersonExcellClass person : listSelectionPersonFName) {
				if (checkContainsStrings(person.getPerson().getSecondName(),secontName)) {
					listSelectionPersonSName.add(person);
				}
			}
		} else {
			listSelectionPersonSName = listSelectionPersonFName;
		}

		if (!lastName.trim().isEmpty()) {
			for (PersonExcellClass person : listSelectionPersonSName) {
				if (checkContainsStrings(person.getPerson().getLastName(),lastName)) {
					listSelectionPersonLName.add(person);
				}
			}
		} else {
			listSelectionPersonLName = listSelectionPersonSName;
		}

		
		if (!kz1.trim().isEmpty()) {
			for (PersonExcellClass person : listSelectionPersonLName) {
				if (person.getKz1().contains(kz1)) {
					listSelectionPersonKZ1.add(person);
				}
			}
		} else {
			listSelectionPersonKZ1 = listSelectionPersonLName;
		}

		if (!kz2.trim().isEmpty()) {
			for (PersonExcellClass person : listSelectionPersonKZ1) {
				if (person.getKz2().contains(kz2)) {
					listSelectionPersonKZ2.add(person);
				}
			}
		} else {
			listSelectionPersonKZ2 = listSelectionPersonKZ1;
		}

		if (!kzHog.trim().isEmpty()) {
			for (PersonExcellClass person : listSelectionPersonKZ2) {
				if (person.getHog().contains(kzHog)) {
					listSelectionPersonKZHog.add(person);
				}
			}
		} else {
			listSelectionPersonKZHog = listSelectionPersonKZ2;
		}
		
		
		if (otdel != null && !otdel.trim().isEmpty()) {
			for (PersonExcellClass person : listSelectionPersonKZHog) {
				if (person.getOtdel().contains(otdel)) {
					listSelectionPersonOtdel.add(person);
				}
			}

		} else {
			listSelectionPersonOtdel = listSelectionPersonKZHog;
		}

		return RemouveDublikateFromList.removeDuplicates(new ArrayList<PersonExcellClass>(listSelectionPersonOtdel));
	}

	static boolean checkContainsStrings(String str1, String str2) {
		str1 = str1.toLowerCase().trim();
		str2 = str2.toLowerCase().trim();
	return str1.contains(str2);
	}
	
	public static List<PersonExcellClass> getPersonFromExcelFile(String year) {
		
		List<PersonExcellClass> listExcellPerson = new ArrayList<>();
		int insertYear = Integer.parseInt(year);
		String[] path = pathToArhiveExcellFiles;
		if(insertYear==curentYear) {
			path = pathToFiles_OriginalPersonalAndExternal	;
		}
		
		for (String pathFile : path) {
			
			if(insertYear!=curentYear) {
				pathFile = pathFile+year+".xls";
			}		
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		if(workbook != null) {
		String ExcellEGN = "", FirstName = "", SecondName = "", LastName = "";
		String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "";
		String otdel = "";
		
		Sheet sheet = workbook.getSheetAt(0);
		Cell cell, cell1;
	
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {
			 kodeKZ1 = "";
			 kodeKZ2 = "";
			 kodeHOG = "";
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);

				if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
					otdel = cell1.getStringCellValue();
				}
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

					ExcellEGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
						FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
						String[] names = ReadExcelFileWBC.splitAllName(FirstName);
						FirstName = names[0];
						SecondName = names[1];
						LastName = names[2];
						
						cell = sheet.getRow(row).getCell(0);
						if(cell!=null)
						kodeKZ1 = cell.getStringCellValue();

						cell = sheet.getRow(row).getCell(1);
						if(cell!=null)
						kodeKZ2 = cell.getStringCellValue();

						cell = sheet.getRow(row).getCell(2);
						if(cell!=null)
						kodeHOG = cell.getStringCellValue();
									

						
						if (kodeKZ1.equals("ЕП-2") || kodeKZ1.trim().equals("") || kodeKZ1.equals("н")) {
							 kodeKZ1="H";
						}
						if ( kodeKZ2.equals("н") || kodeKZ2.trim().equals("")) {
							 kodeKZ2 = "H";
						}
						if (kodeHOG.equals("н") || kodeHOG.trim().equals("")) {
							kodeHOG = "H";
						}
						
						listExcellPerson = addNewExcellPerson(listExcellPerson, new PersonExcellClass(new Person(ExcellEGN, FirstName, SecondName, LastName), kodeKZ1, kodeKZ2, kodeHOG, otdel));						
				
				}
			}

		}
	}
		}
		return listExcellPerson;
	}	

	private static List<PersonExcellClass> addNewExcellPerson(List<PersonExcellClass> listExcellPerson,
			PersonExcellClass newPersonExcellClass) {
		
		for (PersonExcellClass personExcellClass : listExcellPerson) {
			if(newPersonExcellClass.getPerson().getEgn().equals(personExcellClass.getPerson().getEgn())) {
				return listExcellPerson;
			}
		}
		
		listExcellPerson.add(newPersonExcellClass);
		return listExcellPerson;
	}

	public static List<KodeStatus> getListKodeStatusFromExcelFile( String year, Person person) {
		List<KodeStatus> listKodeStatus = new ArrayList<>();
		int insertYear = Integer.parseInt(year);
		String[] path = pathToArhiveExcellFiles;
		if(insertYear==curentYear) {
			path = pathToFiles_OriginalPersonalAndExternal	;
		}
		
		for (String pathFile : path) {
			
			if(insertYear!=curentYear) {
				pathFile = pathFile+year+".xls";
			}		
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		String zab = "From Arhive";
		String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "", kodeT1 = "", kodeT2 = "";
		String EGN = "";
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
					EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					
					if(person.getEgn().equals(EGN)) {
					
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
					return listKodeStatus;
				}
			}
			}

		}
	}
		return listKodeStatus;
	}

	static boolean inCodeNotNumber(String kode) {
		return kode.replaceAll("\\d*", "").length() == kode.length();
	}		
	
	public static List<PersonStatus> getListPersonStatusFromExcelFile( String year, Person person) {
		List<PersonStatus> listPerStat = new ArrayList<>();
		int insertYear = Integer.parseInt(year);
		String[] path = pathToArhiveExcellFiles;
		if(insertYear==curentYear) {
			path = pathToFiles_OriginalPersonalAndExternal	;
		}
		
		for (String pathFile : path) {
			
			if(insertYear!=curentYear) {
				pathFile = pathFile+year+".xls";
			}	
			System.out.println("pathFile: "+pathFile);
			
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
			
			
			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";	
			}	
			
		if(workbook.getNumberOfSheets()>2) {
			for (PersonStatus perSt : getListPersonStatusFromBigExcelFile(workbook, firmName, year, person)) {
			listPerStat.add(perSt);
			}
				
		}else {
			for (PersonStatus perSt :getListPersonStatusFromSmalExcelFile(workbook,firmName,  year, person)) {
			listPerStat.add(perSt);
		}	
		}
		}
		return listPerStat;
	}
		
	private static List<PersonStatus> getListPersonStatusFromSmalExcelFile(Workbook workbook, String firmName,
			String year, Person person) {
		Spisak_Prilogenia spPr = spPrNotInfo;
		List<PersonStatus> listPerStat = new ArrayList<>();
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
						workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName, listAllWorkplaceBiFirmName);
					}
				}

				if (ReadExcelFileWBC.CellNOEmpty(cell) && workplace.getOtdel() != null) {
					EGN =  ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					if(person.getEgn().equals(EGN)) {
			
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

		}
		return listPerStat;
	}

	public static List<PersonStatus> getListPersonStatusFromBigExcelFile(Workbook workbook, String firmName, String year, Person person) {
		 
		List<PersonStatus> listPerStat = new ArrayList<>();
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
					EGN =  ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					if(person.getEgn().equals(EGN)) {
						zab = ReadPersonStatusFromExcelFile.searchComent(workbook, row);

					int k = 7;
					cell = sheet.getRow(row).getCell(k);
					while (ReadExcelFileWBC.CellNOEmpty(cell)) {
						Spisak_Prilogenia spPr =  ReadSpisak_PrilogeniaFromExcelFile.getOrCreateSisak_Prilogenie(k,  row,   sheet,  startDate,  endDate, formulyarName,  workplace,  year);
						
						PersonStatus personStat = new PersonStatus(person, workplace, spPr, userSet, dateSet, "");
						listPerStat.add(personStat);
						k = k+3;
						cell = sheet.getRow(row).getCell(k);
					
					}
					if(listPerStat.size()>0) {
					listPerStat.get(listPerStat.size() - 1).setZabelejka(zab);
					}else {
						if(!zab.isEmpty()) {
						listPerStat.add(new PersonStatus(person, workplace, spPrNotInfo, userSet, dateSet, zab));
						}
					}
					row = sheet.getLastRowNum();
				}
			}
			}

		}

		return listPerStat;

	}
	
	public static  String[][] generateMasiveMeasurFromExcelFile( String year, Person person) {
		
		String[][] masiveMeasur = new String[500][10];
		int k=0;
		int insertYear = Integer.parseInt(year);
		String[] path = pathToArhiveExcellFiles;
		if(insertYear==curentYear) {
			path = pathToFiles_OriginalPersonalAndExternal	;
		}
		
		for (String pathFile : path) {
			
			if(insertYear!=curentYear) {
				pathFile = pathFile+year+".xls";
			}		
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		
		if(workbook.getNumberOfSheets()>2) {
			String[][] measur = generateListFromResultsWBCFromBigExcelFile(workbook, person, year);
			for (String[] personMeasur : measur) {
				masiveMeasur[k] = personMeasur;
				k++;
			}	
				
		}else {
			String[][] measur = generateListFromResultsWBCFromSmalExcelFile(workbook, person, year);	
			for (String[] personMeasur : measur) {
				masiveMeasur[k] = personMeasur;
				k++;
			}	
		}
		}
		String[][] newMasiveMeasur = new String[k][10];
		for (int j = 0; j < newMasiveMeasur.length; j++) {
			newMasiveMeasur[j] = masiveMeasur[j];
			
		}
		
		return newMasiveMeasur;
	}
	
	private static String[][] generateListFromResultsWBCFromSmalExcelFile(Workbook workbook, Person person, String year) {
		String[][] masiveMeasur = new String[500][9];
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);	
		TypeMeasur tipeM_R = TypeMeasurDAO.getValueTypeMeasurByID(1);
		String EGN = "", lab;;
		Date date;
		int index = 0;
		boolean fl;
		double[] nuclideValue = new double[16];
		double val;
		Sheet sheet = workbook.getSheetAt(1);
		Cell cell, cell1;
	
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

					EGN =  ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					if(person.getEgn().equals(EGN)) {
					
					int k = 7;
					cell1 = sheet.getRow(row).getCell(k);
					k++;
					while (ReadExcelFileWBC.CellNOEmpty(cell1)) {
						int countNuclide = 0;
					date = ReadExcelFileWBC.readCellToDate(cell1);
					String yearData = sdf.format(date).substring(6);
					if (year.trim().isEmpty() || yearData.equals(year)) {
						fl = false;
						lab = "wbc-1";
					Measuring measur = ReadMeasuringFromExcelFile.createMeasur( person, dim, userSet, sheet,row, k, tipeM_R, date, lab);
					masiveMeasur[index][0] =  yearData;
					masiveMeasur[index][1] = sdf.format(date);
					masiveMeasur[index][2] = measur.getDoze()+""; 
					masiveMeasur[index][3] =  measur.getLab().getLab();					
					
					k++;
					
					for (int i = 0; i < 16; i++) {
						cell = sheet.getRow(row).getCell(k);
						val = ReadExcelFileWBC.getDoublefromCell(cell);
						nuclideValue[i] = val;
						if(val>-1) {
							countNuclide++;
						}
						k++;
					}
				
							
					if(countNuclide>0) {
						for (int i = 0; i < 16; i++) {
							if(nuclideValue[i]>0) {
								NuclideWBC nuclideWBC = NuclideWBCDAO.getValueNuclideWBCByID(i+1);
									
								masiveMeasur[index][4] = nuclideWBC.getSymbol_nuclide();
								masiveMeasur[index][5] = "0.0" ;
								masiveMeasur[index][6] = "0.0" ;
								masiveMeasur[index][7] = nuclideValue[i]+"" ;
								masiveMeasur[index][8] = "0.0" ;
								if(countNuclide==1) {
									masiveMeasur[index][8] =  measur.getDoze()+"";
								}
								index++;
								fl = true;
							}
							
						}
					}
						if(fl) {
							index--;
						}
						index++;
									
					
					if(k>253) {
						k=6;
						sheet = workbook.getSheetAt(2);
					}
					
					
					k++;
					cell1 = sheet.getRow(row).getCell(k);
					k++;
					
					}
					}
					}
				}
				
				
			}

		}
		String[][] newMasiveMeasur = new String[index][9];
		for (int j = 0; j < newMasiveMeasur.length; j++) {
			newMasiveMeasur[j] = masiveMeasur[j];
			
		}
		
		return newMasiveMeasur;
	}

	public static String[][]  generateListFromResultsWBCFromBigExcelFile(Workbook workbook, Person person , String year) {
		String[][] masiveMeasur = new String[500][10];
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);	
		TypeMeasur tipeM_R = TypeMeasurDAO.getValueTypeMeasurByID(1);
		String EGN = "", lab;;
		Date date;
		int index = 0;
		boolean fl;
		double[] nuclideValue = new double[16];
		String[] simbolNuclide = new String[16];
		double val;
		Sheet sheet = workbook.getSheetAt(1);
		Cell cell, cell1, cell2;
	
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

					EGN =  ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					if(person.getEgn().equals(EGN)) {
						
						int k = 7;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						cell2 = sheet.getRow(row).getCell(k);
						while (ReadExcelFileWBC.CellNOEmpty(cell1) && ReadExcelFileWBC.CellNOEmpty(cell2)) {
							int countNuclide = 0;
						date = ReadExcelFileWBC.readCellToDate(cell1);
						lab = ReadExcelFileWBC.getStringfromCell(cell2).trim();
						String yearData = sdf.format(date).substring(6);
						if (year.trim().isEmpty() || yearData.equals(year)) {
						fl = false;						
						k++;
						
						for (int i = 0; i < 16; i++) {
							cell = sheet.getRow(row).getCell(k);
							val = ReadExcelFileWBC.getDoublefromCell(cell);
							nuclideValue[i] = val;
							simbolNuclide[i] = sheet.getRow(3).getCell(k).getStringCellValue();
							
							if(val>-1) {
								countNuclide++;
							}
							k++;
						}
						Measuring measur = ReadMeasuringFromExcelFile.createMeasur( person, dim, userSet, sheet,row, k, tipeM_R, date, lab);
						masiveMeasur[index][0] =   yearData;
						masiveMeasur[index][1] = sdf.format(date);
						masiveMeasur[index][2] = measur.getDoze()+"";
						if(masiveMeasur[index][2].equals("0.05")) masiveMeasur[index][2] = "<0.10";
						masiveMeasur[index][3] =  measur.getLab().getLab();	
						masiveMeasur[index][4] =  measur.getTypeMeasur().getKodeType();	
						
						if(countNuclide>0) {
							for (int i = 0; i < 16; i++) {
								if(nuclideValue[i]>0) {
									masiveMeasur[index][5] = simbolNuclide[i];
									masiveMeasur[index][6] = "-" ;
									masiveMeasur[index][7] = "-" ;
									masiveMeasur[index][8] = nuclideValue[i]+"" ;
									masiveMeasur[index][9] = "-" ;
									
									index++;
									fl = true;
								}
								
							}
						}
							if(fl) {
								index--;
							}
							index++;
								
					
						if(k>253) {
							k=6;
							sheet = workbook.getSheetAt(2);
						}
						
						
						k++;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						cell2 = sheet.getRow(row).getCell(k);
						
						}
						}
					}
				}
			}

		}
		String[][] newMasiveMeasur = new String[index][9];
		for (int j = 0; j < newMasiveMeasur.length; j++) {
			newMasiveMeasur[j] = masiveMeasur[j];
			
		}
		
		return newMasiveMeasur;
	}

	
	
	
}
