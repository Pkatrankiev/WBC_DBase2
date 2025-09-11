package Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import BasiClassDAO.PersonStatusNewDAO;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;

public class DeleteDublicatePersonStatusNewDiferentOtdels {

	public static Set<String> personEGNListDeleteStatus = new HashSet<>() ;
	
	
	public static void personStatusNewInYere(String year) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date endDate = null, minDate = null;
		
		try {
			List<PersonStatusNew> listAllPersonSatus = PersonStatusNewDAO.getValuePersonStatusNewByYear(year);
			System.out.println(listAllPersonSatus.size());

			Set<Person> mySet = new HashSet<Person>();
			int k = 0, l = 0;
			for (PersonStatusNew personStat : listAllPersonSatus) {
				mySet.add(personStat.getPerson());
				if (l == 100) {
					System.out.println(k);
					l = 0;
				}
				k++;
				l++;
			}
			k = 0;
			l = 0;

			for (Person person : mySet) {
			
//			Person person = PersonDAO.getValuePersonByEGN("6402102007");
				List<PersonStatusNew> personSatusList = PersonStatusNewDAO.getValuePersonStatusNewByPerson_Year(person,
						year);
				Set<String> OtdelSet = new HashSet<String>();
				for (PersonStatusNew personSataNew : personSatusList) {
					System.out.println(personSataNew.getFormulyarName()+" "+personSataNew.getWorkplace().getOtdel()+" "+personSataNew.getStartDate());
					OtdelSet.add(personSataNew.getWorkplace().getOtdel());
					
					if (l == 100) {
						System.out.println(k);
						l = 0;
					}
					
				}
				if (OtdelSet.size() > 1) {
					endDate = sdfrmt.parse("01.01.2000");
					int index = 0;
					String[] masiveWorkplace = new String[OtdelSet.size()];
					Date[] masiveDate = new Date[OtdelSet.size()];
					
					for (String workplace : OtdelSet) {

						for (PersonStatusNew personSataNew : personSatusList) {
							if (personSataNew.getWorkplace().getOtdel().equals(workplace)) {
								if (personSataNew.getStartDate().after(endDate)) {
									endDate = personSataNew.getStartDate();
								}
							}
						}

						masiveWorkplace[index] = workplace;
						masiveDate[index] = endDate;
						index++;
					}
					minDate = masiveDate[0];
					for (int i = 0; i < masiveDate.length; i++) {
						if (minDate.after(masiveDate[i])) {
							minDate = masiveDate[i];
						}
					}
					deletePersonSatusNewBeforMinDate(endDate, minDate, personSatusList, masiveWorkplace, masiveDate);

				}
				k++;
				l++;
			}

			for (String person : personEGNListDeleteStatus) {
				System.out.println(person);
			}
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
	}

	
	private static void deletePersonSatusNewBeforMinDate(Date endDate, Date minDate,
			List<PersonStatusNew> personSatusList, String[] masiveWorkplace, Date[] masiveDate) {
		String minWorkplace;
		int index = 0;
		
		for (int i = 0; i < masiveDate.length; i++) {
			if(minDate.equals(masiveDate[i])) {
				index = i;
			}
		}
		
		minDate = masiveDate[index];
		minWorkplace = masiveWorkplace[index];
	for (PersonStatusNew personSataNew : personSatusList) {
			if (!minWorkplace.equals(personSataNew.getWorkplace().getOtdel())) {
				if (!personSataNew.getStartDate().after(minDate)) {
					PersonStatusNewDAO.deleteValuePersonStatusNew(personSataNew);
					personEGNListDeleteStatus.add(personSataNew.getPerson().getEgn());
				}
			}
		}

		if (masiveDate.length - 1 > 1) {
			String[] masiveNewWorkplace = new String[masiveDate.length - 1];
			Date[] masiveNewDate = new Date[masiveDate.length - 1];
			index = 0;
			for (int i = 0; i < masiveDate.length; i++) {
				if (!minDate.equals(masiveDate[i])) {
					masiveNewWorkplace[index] = masiveWorkplace[i];
					masiveNewDate[index] = masiveDate[i];
				}
			}
			deletePersonSatusNewBeforMinDate(endDate, minDate, personSatusList, masiveWorkplace, masiveDate);

		}
	}
	
	
	
	
	
	
}
