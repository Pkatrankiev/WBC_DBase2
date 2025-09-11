package MenuClasses;

import java.awt.event.ActionEvent;
import java.util.List;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadPersonFromExcelFile;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;


public class Menu_DeleteDublicateShortEGN extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String deleteDublicateShortEGN = ReadFileBGTextVariable.getGlobalTextVariableMap().get("deleteDublicateShortEGN");
	static String deleteDublicateShortEGNTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("deleteDublicateShortEGNTipText");
	public Menu_DeleteDublicateShortEGN() {
		super(deleteDublicateShortEGN);
		setToolTipText(deleteDublicateShortEGNTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		getDublicateShortEGN();
		    	     	
	
				
	}

public static void  getDublicateShortEGN() {
		
		List<Person> listPerson = PersonDAO.getAllValuePerson();
		for (Person personShort : listPerson) {
			if(personShort.getEgn().length() < 9) {
				for (Person personBig : PersonDAO.getListValuePersonByEGN(personShort.getEgn())) {
					if(personShort.getFirstName().equals(personBig.getFirstName())
						&& personShort.getSecondName().equals(personBig.getSecondName())
						&& personShort.getLastName().equals(personBig.getLastName())
						&& !personShort.getEgn().equals(personBig.getEgn())) {
						String personShortEGN =personShort.getEgn() +" " + personShort.getFirstName() + " " + personShort.getSecondName() + " " + personShort.getLastName();
						String personBigEGN =personBig.getEgn() +" " + personBig.getFirstName() + " " + personBig.getSecondName() + " " + personBig.getLastName();
						String dialogString = "<html>"+ personBigEGN + "<br>" +" да замени <br>" + personShortEGN;
						System.out.println(personShortEGN);
						System.out.println(personBigEGN);
						if(ReadPersonFromExcelFile.OptionDialog(dialogString + "</html>", "Смяна на ЕГН")) {
							replaceDataInDBaseNewPerson(personShort, personBig);
							String bigEGN = personBig.getEgn();
							PersonDAO.deleteValuePerson(personShort);
							personShort.setEgn(bigEGN);
							PersonDAO.updateValuePerson(personShort);
							
						}
					}
					
				}
				
			}
		}

	}
	
private static void replaceDataInDBaseNewPerson(Person personShort, Person personBig) {
	for (KodeStatus kodeStat : KodeStatusDAO.getValueKodeStatusByObject("Person_ID", personShort)) {
		kodeStat.setPerson(personBig);
		KodeStatusDAO.updateValueKodeStatus(kodeStat);	
	}
	
	for (PersonStatusNew personStat : PersonStatusNewDAO.getValuePersonStatusNewByPerson(personShort)) {
		personStat.setPerson(personBig);
		PersonStatusNewDAO.updateValuePersonStatusNew(personStat);
	}
	
	for (Measuring measur : MeasuringDAO.getValueMeasuringByObject("Person_ID", personShort)) {
		measur.setPerson(personBig);
		MeasuringDAO.updateValueMeasuring(measur);
	}
}
	
	
	
	}
