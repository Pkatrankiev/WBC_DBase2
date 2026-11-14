package MenuClasses;

import java.awt.event.ActionEvent;
import java.util.List;

import Aplication.ReadFileBGTextVariable;
import Aplication.ReadPersonFromExcelFile;
import BasiClassDAO.InstanceDAO;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import InstanceMenagement.PersonStatusNew_InstanceFrom;
import WBCUsersLogin.WBCUsersLogin;


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
							PersonDAO.deleteValuePerson(personShort, "DublicateShortEGN");
							personShort.setEgn(bigEGN);
							PersonDAO.updateValuePerson(personShort, "DublicateShortEGN");
							
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
		
		String textInstance = PersonStatusNew_InstanceFrom.getInstanceTetxByIDPersonStatusNew(personStat.getPersonStatusNew_ID());
		PersonStatusNewDAO.updateValuePersonStatusNew(personStat);
		textInstance += "!#!"+PersonStatusNew_InstanceFrom.getInstanceTetxByPersonStatusNew(personStat);
		System.out.println("textInstance.length() "+textInstance.length());
		InstanceDAO.setValueInstance("PersonStatusNew", "PersonReference_PersonStatus_Methods", "Edit", textInstance, WBCUsersLogin.getCurentUser());
		
	}
	
	for (Measuring measur : MeasuringDAO.getValueMeasuringByObject("Person_ID", personShort)) {
		measur.setPerson(personBig);
		MeasuringDAO.updateValueMeasuring(measur);
	}
}
	
	
	
	}
