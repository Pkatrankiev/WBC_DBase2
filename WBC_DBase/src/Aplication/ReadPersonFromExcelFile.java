package Aplication;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import BasiClassDAO.PersonDAO;
import BasicClassAccessDbase.Person;

public class ReadPersonFromExcelFile {

	
	public static List<Person> updatePersonFromExcelFile(String pathFile) {
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		String EGN = "", FirstName = "", SecondName = "", LastName = "";
		Sheet sheet = workbook.getSheetAt(0);
		Cell cell, cell1;
		List<Person> listPerson = new ArrayList<>();
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);

				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					if(EGN.contains("*")) EGN = EGN.substring(0, EGN.length()-1);
					if (PersonDAO.getValuePersonByEGN(EGN) == null) {
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

		}
		return listPerson;
	}

	public static void setToDBaseListPerson(List<Person> listPerson) {
		for (Person person : listPerson) {
			PersonDAO.setObjectPersonToTable(person);
			}
		}
	
}
