package PersonReference_Dokument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.AplicationMetods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.RemouveDublikateFromList;
import BasicClassAccessDbase.Person;
import PersonReference.PersonExcellClass;


public class SearchInExcellFilesByDokument {
		
	static String[] pathToFiles_OriginalPersonalAndExternal = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
	
	
		
	public static List<PersonExcellClass> getListSearchingPerson() {
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
		
		
		List<PersonDokumentExcellClass> listSelectionPersonDokument = new ArrayList<>();
		List<PersonDokumentExcellClass> listSelectionPersonStartDate = new ArrayList<>();
		List<PersonDokumentExcellClass> listSelectionPersonEndDate = new ArrayList<>();
		List<PersonDokumentExcellClass> listSelectionPersonOtdel = new ArrayList<>();

		List<PersonExcellClass> listSelectionPerson = new ArrayList<>();
	
		String dokument = PersonReference_Dokument_Frame.getTextField_Dokument().getText();
		String startDate = PersonReference_Dokument_Frame.getTextField_StartDate().getText();
		String endDate = PersonReference_Dokument_Frame.getTextField_EndDate().getText();
		Date stDate = null, enDate = null;
		try {
			stDate = sdf2.parse(startDate);
			enDate = sdf2.parse(endDate);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		String otdel = PersonReference_Dokument_Frame.getComboBox_Otdel().getSelectedItem();
		
		
		List<PersonDokumentExcellClass> listAllExcellPerson = getListPersonStatusNewFromBigExcelFile();
		System.out.println("listAllExcellPerson " + listAllExcellPerson.size());
		
	if (!dokument.trim().isEmpty()) {
			for (PersonDokumentExcellClass excellPerson : listAllExcellPerson) {
				System.out.println(excellPerson.getDokument()+" <-> "+(dokument));
				if (excellPerson.getDokument().contains(dokument)) {
					listSelectionPersonDokument.add(excellPerson);
				}
			}
		} else {
			listSelectionPersonDokument = listAllExcellPerson;
		}
	System.out.println("listSelectionPersonDokument " + listSelectionPersonDokument.size());
		if (!startDate.trim().isEmpty()) {
			for (PersonDokumentExcellClass excellPerson : listSelectionPersonDokument) {
				if (excellPerson.getStartDate().equals(stDate)) {
					listSelectionPersonStartDate.add(excellPerson);
				}
			}
		} else {
			listSelectionPersonStartDate = listSelectionPersonDokument;
		}
		System.out.println("listSelectionPersonStartDate " + listSelectionPersonStartDate.size());
		if (!endDate.trim().isEmpty()) {
			for (PersonDokumentExcellClass excellPerson : listSelectionPersonStartDate) {
				if (excellPerson.getEndDate().equals(enDate)) {
					listSelectionPersonEndDate.add(excellPerson);
				}
			}
		} else {
			listSelectionPersonEndDate = listSelectionPersonStartDate;
		}

		System.out.println("listSelectionPersonEndDate " + listSelectionPersonEndDate.size());
		if (otdel != null && !otdel.trim().isEmpty()) {
			for (PersonDokumentExcellClass person : listSelectionPersonEndDate) {
				if (person.getOtdel().contains(otdel)) {
					listSelectionPersonOtdel.add(person);
				}
			}

		} else {
			listSelectionPersonOtdel = listSelectionPersonEndDate;
		}
		System.out.println("listSelectionPersonOtdel " + listSelectionPersonOtdel.size());
		for (PersonDokumentExcellClass personDokumentExcellClass : listSelectionPersonOtdel) {
			listSelectionPerson.add(new PersonExcellClass(personDokumentExcellClass.getPerson(), personDokumentExcellClass.getKz1(), personDokumentExcellClass.getKz2(),
					personDokumentExcellClass.getHog(), personDokumentExcellClass.getOtdel()));
		}
		
		return RemouveDublikateFromList.removeDuplicates(new ArrayList<PersonExcellClass>(listSelectionPerson));
	}
	

	
	public static List<PersonDokumentExcellClass> getListPersonStatusNewFromBigExcelFile() {

	
		List<PersonDokumentExcellClass> listExcellPerson = new ArrayList<>();
		
		String[] path = pathToFiles_OriginalPersonalAndExternal	;
	
		
		for (String pathFile : path) {
				
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		
		if(workbook != null) {
		String ExcellEGN = "", FirstName = "", SecondName = "", LastName = "";
		String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "";
		String otdel = "", formulyarName = "";
		
		Sheet sheet = workbook.getSheetAt(3);
		Cell cell, cell1;

		
		Date startDate = null;
		Date endDate = null;
		
		
		
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
						
						int k = 7;
						cell = sheet.getRow(row).getCell(k);
					
						while (ReadExcelFileWBC.CellNOEmpty(cell)) {
							formulyarName = ReadExcelFileWBC.getStringEGNfromCell(cell);
							k++;
							cell = sheet.getRow(row).getCell(k);
							if (ReadExcelFileWBC.CellNOEmpty(cell)
									&& !ReadExcelFileWBC.getStringfromCell(cell).isEmpty()) {
								startDate = ReadExcelFileWBC.readCellToDate(cell);
							} 
					
							k++;
							cell = sheet.getRow(row).getCell(k);
							if (ReadExcelFileWBC.CellNOEmpty(cell)
									&& !ReadExcelFileWBC.getStringfromCell(cell).isEmpty()) {
								endDate = ReadExcelFileWBC.readCellToDate(cell);
							} 
							k++;
							cell = sheet.getRow(row).getCell(k);
							
							PersonDokumentExcellClass perr = new PersonDokumentExcellClass();
							perr.setPerson(new Person(ExcellEGN, FirstName, SecondName, LastName));
							perr.setKz1(kodeKZ1);
							perr.setKz2(kodeKZ2);
							perr.setHog(kodeHOG);
							perr.setOtdel(otdel);
							perr.setStartDate(startDate);
							perr.setEndDate(endDate);
							perr.setDokument(formulyarName);
							
							listExcellPerson.add(perr);		
						}
						
										
				
				}
			}


			
		
		}
		
		}
		}
		
		return listExcellPerson;
	}


	
	
	public static List<PersonDokumentExcellClass> addNewPersonDokumentExcellClass(List<PersonDokumentExcellClass> listExcellPerson,
			PersonDokumentExcellClass newPersonExcellClass) {
		
		for (PersonDokumentExcellClass personExcellClass : listExcellPerson) {
			if(newPersonExcellClass.getPerson().getEgn().equals(personExcellClass.getPerson().getEgn())) {
				return listExcellPerson;
			}
		}
		
		listExcellPerson.add(newPersonExcellClass);
		return listExcellPerson;
	}
	
	
	
}
