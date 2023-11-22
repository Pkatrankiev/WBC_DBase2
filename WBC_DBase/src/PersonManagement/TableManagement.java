package PersonManagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import PersonReference.PersonReferenceFrame;

public class TableManagement {

	protected static Object[][] addListStringSelectionPersonToComboBox(List<PersonManegement> listSelectionPerson) {

		Object[][] dataTable = new Object[listSelectionPerson.size()][10];

//				"№", "EGN", 	"FirstName","SecondName","LastName","Otdel", "Date"
//				"Kod ",	"Kod",	"selekt"

		int zona = ZoneDAO.getValueZoneByNameTerritory(PersonelManegementMethods.getZonaFromRadioButtons()).getId_Zone();
		int k = 0;
		for (PersonManegement personManegement : listSelectionPerson) {
			System.out.println("egn " + personManegement.getPerson().getEgn());
			dataTable[k][0] = (k + 1);
			dataTable[k][1] = personManegement.getPerson().getEgn();
			dataTable[k][2] = personManegement.getPerson().getFirstName();
			dataTable[k][3] = personManegement.getPerson().getSecondName();
			dataTable[k][4] = personManegement.getPerson().getLastName();
			dataTable[k][5] = PersonReferenceFrame.getLastWorkplaceByPerson(personManegement.getPerson());
			dataTable[k][6] = getLastMeasuring(personManegement.getPerson());
			dataTable[k][7] = PersonReferenceFrame.getLastKodeByPersonAndZone(personManegement.getPerson(), zona);
			dataTable[k][8] = personManegement.getKodeFromList();
			dataTable[k][9] = true;

			k++;
		}

		return dataTable;

	}

	private static String getLastMeasuring(Person person) {
		String strDate = "";
		String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date endDate0 = null, startDate0 = null;
		try {
			startDate0 = sdfrmt.parse("01.01." + curentYear);
			endDate0 = sdfrmt.parse("31.12." + curentYear);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Measuring> listMeasuring = MeasuringDAO.getValueMeasuringByPersonAndYear(person, startDate0, endDate0);
		int sizeList = listMeasuring.size();
		if(sizeList>0) {
			strDate = sdfrmt.format(listMeasuring.get(sizeList-1).getDate());
		}
		return strDate;
	}

	
	
}
