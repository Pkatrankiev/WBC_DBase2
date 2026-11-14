package Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.ReadExcelFileWBC;
import Aplication.ReadKodeStatusFromExcelFile;
import BasicClassAccessDbase.Person;

public class For_Kamburov_SetNamePersonalByEGN {

		public static void setNameByEGN() {

		String pathExcelPersonel = "d:\\11\\2024_PERSONNEL.xls";
		String pathSorseFile = "d:\\11\\Blok6.xls";
		int StartRowSorseFile = 4;

		List<Person> listPerson = readPersonEGN(pathExcelPersonel);

		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathSorseFile);
		Sheet sheet = workbook.getSheetAt(0);
		Cell cell, cell1;
		String egn;
		List<Integer> listDiferentRow = null;

		int StartRow = StartRowSorseFile;

		int row = 0;
		for (int index = StartRow; index < sheet.getLastRowNum(); index += 1) {

			if (listDiferentRow != null) {
				row = listDiferentRow.get(index);
			} else {
				row = index;
			}

			if (sheet.getRow(row) != null && row > 0) {
				cell = sheet.getRow(row).getCell(1);
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					egn = ReadExcelFileWBC.getStringEGNfromCell(cell);
					System.out.println(row + " " + egn);
					for (int i = 0; i < listPerson.size(); i++) {

						if (listPerson.get(i).getEgn().equals(egn)) {
							System.out.println(listPerson.get(i).getEgn() + " - " + egn);
							cell1 = sheet.getRow(row).getCell(2);
							cell1.setCellValue(listPerson.get(i).getFirstName() + " "
									+ listPerson.get(i).getSecondName() + " " + listPerson.get(i).getLastName());
							i = listPerson.size();
						}

					}

				}
			}
		}
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(pathSorseFile);

			workbook.write(outputStream);

			workbook.close();

			outputStream.flush();
			outputStream.close();

		} catch (OldExcelFormatException | IOException e) {
			e.printStackTrace();
		}

	}
	
		public static List<Person> readPersonEGN(String pathExcelPersonel) {

			Workbook workbook = ReadExcelFileWBC.openExcelFile(pathExcelPersonel);

			int StartRow = 5;

			String EGN = "", FirstName = "", SecondName = "", LastName = "";
			Sheet sheet = workbook.getSheetAt(0);
			Cell cell, cell1;
			List<Person> listPerson = new ArrayList<>();

			for (int row = StartRow; row < sheet.getLastRowNum(); row += 1) {

				if (sheet.getRow(row) != null && row > 0) {
					cell = sheet.getRow(row).getCell(5);
					cell1 = sheet.getRow(row).getCell(6);
					if (ReadExcelFileWBC.CellNOEmpty(cell)) {
						EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
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
			System.out.println("listPerson.size() " + listPerson.size());
			return listPerson;
		}

		

}
