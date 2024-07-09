package CheckErrorDataInExcellFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.RemouveDublikateFromList;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.Workplace;

public class CheckPersonStatus {

	static String curentYear = AplicationMetods.getCurentYear();

	public static void ActionListener_Btn_CheckPersonStatus(JPanel panel_AllSaerch,
			JButton btn_CheckPersonStatus, JTextArea textArea, JProgressBar progressBar) {
		btn_CheckPersonStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				
				
				new MySwingWorker(progressBar, textArea, panel_AllSaerch, "CheckPersonStatus").execute();
	
				
			}

		

		});

	}
	

	public static String  compareLists(List<Person> listPerson, List<Person> listPersonFile) {
		String str = "";
		boolean fl;
		Collection<String> listOne = new ArrayList<String>();
		for (Person person : listPerson) {
			listOne.add(person.getEgn());
		}

		Collection<String> listTwo = new ArrayList<String>();
		for (Person person : listPersonFile) {
			listTwo.add(person.getEgn());
		}

		Collection<String> similar = new HashSet<String>(listOne);
		Collection<String> different = new HashSet<String>();
		different.addAll(listOne);
		different.addAll(listTwo);

		similar.retainAll(listTwo);
		different.removeAll(similar);

		System.out.println("similar " + similar.size());
		System.out.println("different " + different.size());
		for (String egn : different) {
			fl = true;
			for (Person per : listPersonFile) {
				if(per.getEgn().equals(egn) && per.getFirstName().contains("АЕЦ")) {
				System.out.println(egn + " " +  per.getFirstName() + " " + per.getSecondName() + " " + per.getLastName());	
				fl = false;	
				}
			}
			if(fl) {
			Person person = PersonDAO.getValuePersonByEGN(egn);
			str += egn + " " +  person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName() + "\n";
			}
		}
		return str;

	}

	public static List<Person> getPerson_ListByOtdelFromExcelFile(String FILE_PATH, Workplace workplace) {

		Workbook workbook = ReadExcelFileWBC.openExcelFile(FILE_PATH);
		List<Person> person_List = new ArrayList<Person>();
		Person person = new Person();
		String EGN = "", FirstName = "";
		String otdelNameByFile;
		boolean fl = false;
		if (workbook.getNumberOfSheets() > 2) {

			Sheet sheet = workbook.getSheetAt(0);
			Cell cell, cell1;

			for (int row = 0; row < sheet.getLastRowNum(); row += 1) {

				if (sheet.getRow(row) != null && row > 0) {
					cell = sheet.getRow(row).getCell(5);
					cell1 = sheet.getRow(row).getCell(6);
					if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
						otdelNameByFile = cell1.getStringCellValue().trim();
						if (!otdelNameByFile.toUpperCase().contains("КРАЙ")
								&& (otdelNameByFile.equals(workplace.getOtdel()) || otdelNameByFile.equals(workplace.getSecondOtdelName()))) {
							fl = true;
						} else {
							fl = false;
						}
					}
					if (fl) {
						FirstName = ReadExcelFileWBC.getStringEGNfromCell(cell1);
						
							EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
							person = PersonDAO.getValuePersonByEGN(EGN);
							if (person != null) {
								if (FirstName.toUpperCase().contains("АЕЦ")) {
								FirstName = person.getFirstName() + " АЕЦ";
									person.setFirstName(FirstName);
								}
								person_List.add(person);
							}

					}
				}

			}
		}
		return person_List;
	}

	protected static List<Workplace> getWorkPleaceByFirma(String stringFirmName, List<Workplace> listWorkPleace) {
		List<Workplace> listWorkpl = new ArrayList<>();
		for (Workplace workpl : listWorkPleace) {
			if (workpl.getFirmName().equals(stringFirmName)) {
				listWorkpl.add(workpl);
			}
		}

		return listWorkpl;
	}

	public static List<Workplace> spisakAllWorkplaceByYear(String curentYear, JProgressBar progressBar) {
		Set<Integer> mySet = new HashSet<Integer>();
		List<Workplace> listWorkpl = new ArrayList<>();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		double stepForProgressBar = 30;
		List<PersonStatusNew> list = PersonStatusNewDAO.getValuePersonStatusNewByYearWithProgressBar(curentYear, progressBar, stepForProgressBar);
		System.out.println("----------------------------------------------------------");
		for (PersonStatusNew personStatusNew : list) {

			if (mySet.add(personStatusNew.getWorkplace().getId_Workplace())) {
				listWorkpl.add(personStatusNew.getWorkplace());
			}
		}

		return listWorkpl;

	}

	public static List<Person> spisakPersonFromWorkplace(Workplace workPlace, int count, int ofCounts ) {

		List<Integer> listPersonID = new ArrayList<>();
		List<Person> listPerson = new ArrayList<>();
		List<Person> listPersonNew = new ArrayList<>();

		List<PersonStatusNew> listPerStat = PersonStatusNewDAO.getValuePersonStatusNewByWorkplace_Year(workPlace,
				curentYear);
		Person person = new Person();

		for (PersonStatusNew personStat : listPerStat) {

			person = personStat.getPerson();
			PersonStatusNew perStat = PersonStatusNewDAO.getLastPersonStatusNewByPerson_YearSortByStartDate(person, curentYear);

			if (perStat != null) {
				if (perStat.getWorkplace().getOtdel().equals(workPlace.getOtdel())) {
					listPersonNew.add(person);
				}
			}
	
		}

		for (Person personn : listPersonNew) {
			listPersonID.add(personn.getId_Person());

		}

		listPersonID = RemouveDublikateFromList.removeDuplicates(new ArrayList<Integer>(listPersonID));
		for (Integer integer : listPersonID) {
			listPerson.add(PersonDAO.getValuePersonByID(integer));
		}

		return listPerson;
	}

	public static String comparePersonStatus(JProgressBar aProgressBar, JPanel panel_AllSaerch) {
		
		GeneralMethods.setWaitCursor(panel_AllSaerch);
		
		double ProgressBarSize = 0;
		aProgressBar.setValue((int) ProgressBarSize);
		String str = "";
		String diferentPerson;
//		 System.out.println("////////////////////////////////////////////////////////////////");

		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
//		 System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		List<Workplace> listWorkPleace = spisakAllWorkplaceByYear(curentYear, aProgressBar);
		System.out.println("listWorkPleace " + listWorkPleace.size());

		List<Workplace> listWorkPleaceFromAEC = getWorkPleaceByFirma("АЕЦ Козлодуй", listWorkPleace);
		System.out.println("listWorkPleaceFromAEC " + listWorkPleaceFromAEC.size());

		List<Workplace> listWorkPleaceFromVO = getWorkPleaceByFirma("Външни организации", listWorkPleace);
		System.out.println("listWorkPleaceFromVO " + listWorkPleaceFromVO.size());

		ProgressBarSize = 30;
		
		double stepForProgressBar = 70;

		
			stepForProgressBar = stepForProgressBar / listWorkPleace.size();
			
		
		for (int i = 0; i < excellFiles.length; i++) {
			List<Workplace> list = listWorkPleaceFromAEC;
			if(i==1) {
				list = listWorkPleaceFromVO;
			}
		int ofCounts = list.size();
		int count = 1;
		for (Workplace workplace : list) {
			System.out.println();
			System.out.println("*****************************************************");
			System.out.println("otdel " + workplace.getOtdel());
			List<Person> listPerson = spisakPersonFromWorkplace(workplace, count, ofCounts);
			 
			aProgressBar.setValue((int) ProgressBarSize);
			 System.out.println(ProgressBarSize+"  -------------------------------------------------------");
			ProgressBarSize += stepForProgressBar;
			System.out.println("listPerson " + listPerson.size());
			
			List<Person> listPersonFile = getPerson_ListByOtdelFromExcelFile(excellFiles[i], workplace);
			
			
			
			System.out.println("listPersonFile " + listPersonFile.size());
			diferentPerson = compareLists(listPerson, listPersonFile);
			if(!diferentPerson.isEmpty()) {
				str += workplace.getOtdel() + "\n";
				str += diferentPerson;
			}
			count++;
		}
		}
		GeneralMethods.setDefaultCursor(panel_AllSaerch);
//		round.StopWindow();
		return str;
	}




	static void setTextToArea(JTextArea textArea, String textForArea) {
		if (textForArea.isEmpty()) {
			textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
			CheckErrorDataInExcellFiles_Frame.getBtn_CheckDBaseNameKodeStat_Clear().setEnabled(false);
		} else {
			textArea.setText(textForArea);
			CheckErrorDataInExcellFiles_Frame.getBtn_CheckDBaseNameKodeStat_Clear().setEnabled(true);
		}
	}
	
}
