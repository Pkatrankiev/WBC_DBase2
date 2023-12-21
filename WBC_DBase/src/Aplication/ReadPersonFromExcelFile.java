package Aplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import BasiClassDAO.PersonDAO;
import BasicClassAccessDbase.Person;


public class ReadPersonFromExcelFile {

	
	public static List<Person> updatePersonFromExcelFile(String pathFile, ActionIcone round,  String textIcon, List<Integer> listDiferentRow) {
		Set<String> mySet = new HashSet<String>();
		int countMySet;
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		String EGN = "", FirstName = "", SecondName = "", LastName = "";
		Sheet sheet = workbook.getSheetAt(0);
		Cell cell, cell1;
		List<Person> listPerson = new ArrayList<>();
		
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
			System.out.println(row+" ++++++++++");
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);
				System.out.println(cell1+" *************");
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					countMySet = mySet.size();
					mySet.add(EGN);
					System.out.println(EGN+" eeeeeeeeeeee "+countMySet+" - "+ mySet.size());
					if (ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell) == null && (countMySet+1) == mySet.size()) {
						System.out.println("++++++++++++++++++++"+EGN);
						FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
						String[] names = ReadExcelFileWBC.splitAllName(FirstName);
						FirstName = names[0];
						SecondName = names[1];
						LastName = names[2];
						System.out.println("1 " + FirstName + " 2 " + SecondName + " 3 " + LastName);
						listPerson.add(new Person(EGN, FirstName, SecondName, LastName));
					}
				}
			}
			ActionIcone.roundWithText(round, textIcon, "Read", index, endRow);
		}
		System.out.println("sssssssssssssssss "+listPerson.size());
		return listPerson;
	}

	

	public static void setToDBaseListPerson(List<Person> listPerson, ActionIcone round,  String textIcon) {
		int k=0;
		int l=listPerson.size();
		for (Person person : listPerson) {
			PersonDAO.setObjectPersonToTable(person);
			ActionIcone.roundWithText(round, textIcon, "Save", k, l);
			k++;
			}
		}
	
}
