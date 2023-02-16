package PersonReference;

import java.awt.Choice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.RemouveDublikateFromList;
import AutoInsertMeasuting.InsertMeasurToExcel;
import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;


public class SearchFromExcellFiles {
	
	protected static void addListStringSelectionPersonToComboBox(List<PersonExcellClass> listSelectionPerson, Choice comboBox_Results) {
		comboBox_Results.removeAll();
		List<String> list = new ArrayList<>();
		for (PersonExcellClass person : listSelectionPerson) {
			list.add(person.getPerson().getEgn() + " " + InsertMeasurToExcel.getNamePerson(person.getPerson()));
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

	protected static String createInfoPanelForPerson(PersonExcellClass person) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		String year = PersonReferenceFrame.getTextField_Year().getText(); 
		String str = person.getPerson().getEgn() + " " + InsertMeasurToExcel.getNamePerson(person.getPerson()) + "\n";
		str = str + year + "\n";
		str = str + "Кодове: \n";
		List<KodeStatus> listK = KodeStatusDAO.getValueKodeStatusByObjectSortByColumnName("Person_ID", person, "Year");
		
			for (KodeStatus kodeStat : listK) {
				if (year.trim().isEmpty() || kodeStat.getYear().equals(year)) {
					
					str = str + kodeStat.getYear() + "  " + kodeStat.getZone().getNameTerritory() + " - " + kodeStat.getKode() + " "
							+ kodeStat.getZabelejkaKodeStatus() + "\n";
				}
			
		}
			
		str = str + "\n";
		str = str + "Заповеди \n";
		List<PersonStatus> listP = PersonStatusDAO.getValuePersonStatusByObject("Person_ID", person);
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
		List<Measuring> listM = MeasuringDAO.getValueMeasuringByObject("Person_ID", person);
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
		
		String filePathArhivePersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathArhivePersonel");
		String filePathArhiveExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathArhiveExternal");
		String[] excellFiles = {filePathArhivePersonel, filePathArhiveExternal};	
		
	
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
		
		List<PersonExcellClass> listAllPerson = getPersonFromExcelFile(excellFiles, year);
	if (!egn.trim().isEmpty()) {
			for (PersonExcellClass person : listAllPerson) {
				if (person.getPerson().getEgn().contains(egn)) {
					listSelectionPersonEGN.add(person);
				}
			}
		} else {
			listSelectionPersonEGN = listAllPerson;
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
	
	public static List<PersonExcellClass> getPersonFromExcelFile(String[] excellFiles, String year) {
		
		List<PersonExcellClass> listPerson = new ArrayList<>();
		for (String pathFile : excellFiles) {
				
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile+year+".xls");
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

					ExcellEGN = ReadExcelFileWBC.getStringfromCell(cell);
					if(ExcellEGN.contains("*")) ExcellEGN = ExcellEGN.substring(0, ExcellEGN.length()-1);
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
												
						listPerson.add(new PersonExcellClass(new Person(ExcellEGN, FirstName, SecondName, LastName), kodeKZ1, kodeKZ2, kodeHOG, otdel));
					
				}
			}

		}
	}
		return listPerson;
	}	

	
	
}
