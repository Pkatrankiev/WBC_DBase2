package DeleteDataFromDBaseRemoveInCurenFromOldYear;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.ReadMeasuringFromExcelFile;
import Aplication.ReadPersonStatusFromExcelFile;
import Aplication.ReadResultsWBCFromExcelFile;
import Aplication.ReadSpisak_PrilogeniaFromExcelFile;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import UpdateDBaseFromExcelFiles.UpdateBDataFromExcellFiles;
import WBCUsersLogin.WBCUsersLogin;

public class getMasiveFromOriginalExcelFile {

	static SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");

	static List<Person> extracted_Person() {
		List<Person> listPerson = new ArrayList<>();
		List<Person> list = new ArrayList<>();

		ActionIcone round = new ActionIcone(
				"                                " + "                                              ");
		List<String> listChengeExcellFilePath = new ArrayList<>();
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
		for (String file : excellFiles) {
			System.out.println("file " + file);
			listChengeExcellFilePath.add(file);
		}

		for (String pathFile : listChengeExcellFilePath) {

			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";
			}
			String textIcon;
			String errorText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("errorText");

			List<Integer> listDiferentRow = null;

			textIcon = "<html><center>Update Person<br>" + firmName + " ";

			// read and set Person

			try {
				list = updatePersonFromExcelFile(pathFile, round, textIcon, listDiferentRow);

			} catch (Exception e) {
				e.printStackTrace();
				UpdateBDataFromExcellFiles.OptionDialog(errorText);

			}
			listPerson.addAll(list);
		}
		System.out.println("Person --> " + listPerson.size());
		round.StopWindow();
		return listPerson;
	}

	static List<Spisak_Prilogenia> extracted_Spisak_Prilogenia() {
		List<Spisak_Prilogenia> Spisak_PrilogeniaList = new ArrayList<>();
		List<Spisak_Prilogenia> list = new ArrayList<>();
		ActionIcone round = new ActionIcone(
				"                                " + "                                              ");
		List<String> listChengeExcellFilePath = new ArrayList<>();
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
		for (String file : excellFiles) {
			System.out.println("file " + file);
			listChengeExcellFilePath.add(file);
		}

		for (String pathFile : listChengeExcellFilePath) {
			List<String> arreaOtdels = UpdateBDataFromExcellFiles.getListArreaOtdels(pathFile);
			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";
			}

			String year = AplicationMetods.getCurentYear();
			String textIcon;
			String errorText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("errorText");

			List<Integer> listDiferentRow = null;

			textIcon = "<html><center>Update Spisak_Prilogenia<br>" + firmName + " ";

			// read and set Spisak_Prilogenia

			try {
				list = ReadSpisak_PrilogeniaFromExcelFile.getSpisak_Prilogenia_ListFromExcelFile(pathFile, firmName,
						year, round, textIcon, listDiferentRow, arreaOtdels);

			} catch (Exception e) {
				e.printStackTrace();
				UpdateBDataFromExcellFiles.OptionDialog("Spisak_Prilogenia" + errorText);

			}
			Spisak_PrilogeniaList.addAll(list);

		}
		System.out.println("Spisak_Prilogenia --> " + Spisak_PrilogeniaList.size());
		round.StopWindow();
		return Spisak_PrilogeniaList;

	}

	static List<PersonStatusNew> extracted_PersonStatusNew(String year) {
		List<PersonStatusNew> listPersonStatus = new ArrayList<>();
		List<PersonStatusNew> list = new ArrayList<>();
		String firmName = "";
		ActionIcone round = new ActionIcone(
				"                                " + "                                              ");
		List<String> listChengeExcellFilePath = new ArrayList<>();
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
		for (String file : excellFiles) {
			System.out.println("file " + file);
			listChengeExcellFilePath.add(file);
		}

		for (String pathFile : listChengeExcellFilePath) {
			List<String> arreaOtdels = UpdateBDataFromExcellFiles.getListArreaOtdels(pathFile);
			firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";
			}

			String textIcon;
			String errorText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("errorText");

			List<Integer> listDiferentRow = null;

			textIcon = "<html><center>Update PersonStatus<br>" + firmName + " ";

			// read and set PersonStatus

			try {
				list = getListPersonStatusNewFromExcelFile(pathFile, firmName, year, round, textIcon, listDiferentRow,
						arreaOtdels);

			} catch (Exception e) {
				UpdateBDataFromExcellFiles.OptionDialog("PersonStatus" + errorText);

			}
			System.out.println("PersonStatusList --> " + firmName + " " + list.size());
			listPersonStatus.addAll(list);
		}
		System.out.println("PersonStatus --> " + listPersonStatus.size());
		round.StopWindow();
		return listPersonStatus;
	}

	static List<KodeStatus> extracted_KodeStatus() {
		List<KodeStatus> listKodeStatus = new ArrayList<>();
		List<KodeStatus> list = new ArrayList<>();
		ActionIcone round = new ActionIcone(
				"                                " + "                                              ");
		List<String> listChengeExcellFilePath = new ArrayList<>();
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
		for (String file : excellFiles) {
			System.out.println("file " + file);
			listChengeExcellFilePath.add(file);
		}

		for (String pathFile : listChengeExcellFilePath) {
			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";
			}

			String year = AplicationMetods.getCurentYear();
			String textIcon;
			String errorText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("errorText");

			textIcon = "<html><center>Update KodeStatus<br>" + firmName + " ";

			// read and set KodeStatus
			List<Integer> listDiferentRow = null;
			try {
				list = ReadKodeStatusFromExcelFile.getListKodeStatusFromExcelFile(pathFile, firmName, year, round,
						textIcon, listDiferentRow);

			} catch (Exception e) {
				UpdateBDataFromExcellFiles.OptionDialog(errorText);

			}
			listKodeStatus.addAll(list);
		}
		System.out.println("KodeStatus --> " + listKodeStatus.size());
		round.StopWindow();
		return listKodeStatus;

	}

	static List<Measuring> extracted_Measuring() {
		List<Measuring> listMeasuring = new ArrayList<>();
		List<Measuring> list = new ArrayList<>();
		ActionIcone round = new ActionIcone(
				"                                " + "                                              ");
		List<String> listChengeExcellFilePath = new ArrayList<>();
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
		for (String file : excellFiles) {
			System.out.println("file " + file);
			listChengeExcellFilePath.add(file);
		}

		for (String pathFile : listChengeExcellFilePath) {
			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";
			}

			String textIcon;
			String errorText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("errorText");

			List<Integer> listDiferentRow = null;

			textIcon = "<html><center>Update Measuring<br>" + firmName + " ";

			// read and set Measuring

			try {
				list = ReadMeasuringFromExcelFile.generateListFromMeasuringFromExcelFile(pathFile, round, textIcon,
						listDiferentRow);

			} catch (Exception e) {
				UpdateBDataFromExcellFiles.OptionDialog(errorText);

			}
			listMeasuring.addAll(list);
		}
		System.out.println("Measuring --> " + listMeasuring.size());
		round.StopWindow();
		return listMeasuring;

	}

	static List<ResultsWBC> extracted_ResultsWBC() {
		List<ResultsWBC> listResultsWBC = new ArrayList<>();
		List<ResultsWBC> list = new ArrayList<>();
		ActionIcone round = new ActionIcone(
				"                                " + "                                              ");
		List<String> listChengeExcellFilePath = new ArrayList<>();
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
		for (String file : excellFiles) {
			System.out.println("file " + file);
			listChengeExcellFilePath.add(file);
		}

		for (String pathFile : listChengeExcellFilePath) {
			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";
			}

			String textIcon;
			String errorText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("errorText");

			List<Integer> listDiferentRow = null;

			textIcon = "<html><center>Update ResultsWBC<br>" + firmName + " ";

			// read and set ResultsWBC

			try {
				list = ReadResultsWBCFromExcelFile.generateListFromResultsWBCFromExcelFile(pathFile, round, textIcon,
						listDiferentRow);

			} catch (Exception e) {
				UpdateBDataFromExcellFiles.OptionDialog(errorText);

			}
			listResultsWBC.addAll(list);
		}
		System.out.println("Results --> " + listResultsWBC.size());
		round.StopWindow();
		return listResultsWBC;

	}

	static List<PersonStatusNew> extracted_ObhodenList() {
		List<PersonStatusNew> list = new ArrayList<>();
		List<PersonStatusNew> listObhodenList = new ArrayList<>();
		ActionIcone round = new ActionIcone(
				"                                " + "                                              ");
		List<String> listChengeExcellFilePath = new ArrayList<>();
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
		for (String file : excellFiles) {
			System.out.println("file " + file);
			listChengeExcellFilePath.add(file);
		}

		for (String pathFile : listChengeExcellFilePath) {
			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";
			}

			String year = AplicationMetods.getCurentYear();
			String textIcon;

			textIcon = "<html><center>Update Obhoden List<br>" + firmName + " ";

			// read and set ObhodenList in PersonStatus

			list = ReadPersonStatusFromExcelFile.getObhodenListPersonStatusNewFromExcelFile(pathFile, firmName, year,
					round, textIcon);
			ReadPersonStatusFromExcelFile.ListPersonStatusNew(list);
			listObhodenList.addAll(list);
		}
		System.out.println("ObhodenList --> " + listObhodenList.size());
		round.StopWindow();
		return list;

	}

	static String deleteSpisak_PrilogeniaFromDBaseWhichAreNotInExcelFiles(JProgressBar aProgressBar,
			JPanel panel_AllSaerch, String year) {

		GeneralMethods.setWaitCursor(panel_AllSaerch);

		double ProgressBarSize = 0;
		aProgressBar.setValue((int) ProgressBarSize);

		List<Spisak_Prilogenia> Spisak_PrilogeniaListExcel = getMasiveFromOriginalExcelFile
				.extracted_Spisak_Prilogenia();
		System.out.println("Spisak_PrilogeniaListExcel " + Spisak_PrilogeniaListExcel.size());

		List<Spisak_Prilogenia> Spisak_PrilogeniaListDBase = Spisak_PrilogeniaDAO
				.getValueSpisak_PrilogeniaByObject("Year", year);
		System.out.println("Spisak_PrilogeniaListDBase " + Spisak_PrilogeniaListDBase.size());
		int sise = Spisak_PrilogeniaListDBase.size();
		boolean fl;
		Spisak_Prilogenia spisPrExcel;

		double stepForProgressBar = 100;
		stepForProgressBar = stepForProgressBar / sise;

		int k = 0, f = 0;
		for (Spisak_Prilogenia spisPrDbase : Spisak_PrilogeniaListDBase) {
			fl = true;

			for (int i = 0; i < Spisak_PrilogeniaListExcel.size(); i++) {
				spisPrExcel = Spisak_PrilogeniaListExcel.get(i);
				if (spisPrExcel.getFormulyarName().equals(spisPrDbase.getFormulyarName())
						&& spisPrExcel.getStartDate().equals(spisPrDbase.getStartDate())
						&& spisPrExcel.getEndDate().equals(spisPrDbase.getEndDate()) && spisPrExcel.getWorkplace()
								.getId_Workplace() == spisPrDbase.getWorkplace().getId_Workplace()) {
					fl = false;
					i = Spisak_PrilogeniaListExcel.size();
				}
			}
			if (fl) {
				System.out.println(
						k + "/" + sise + " delete:" + spisPrDbase.getFormulyarName() + " " + spisPrDbase.getStartDate()
								+ " " + spisPrDbase.getEndDate() + " " + spisPrDbase.getWorkplace().getOtdel());
				Spisak_PrilogeniaDAO.deleteValueSpisak_Prilogenia(spisPrDbase.getSpisak_Prilogenia_ID());
				f++;
			}
			k++;
			aProgressBar.setValue((int) ProgressBarSize);
			ProgressBarSize += stepForProgressBar;
		}
		aProgressBar.setValue(100);
		GeneralMethods.setDefaultCursor(panel_AllSaerch);
		return "Изтрити "+f+" записа.";
	}

	public static String delete_PersonStatus_FromDBaseWhichAreNotInExcelFiles(JProgressBar aProgressBar,
			JPanel panel_AllSaerch, String year) {

		GeneralMethods.setWaitCursor(panel_AllSaerch);

		double ProgressBarSize = 0;
		aProgressBar.setValue((int) ProgressBarSize);

		List<Person> listPersonExcel = extracted_Person();
		System.out.println("listPersonExcel " + listPersonExcel.size());
		List<PersonStatusNew> PersonStatusPersonExcel = new ArrayList<>();
		List<PersonStatusNew> PersonStatusNewListExcel = extracted_PersonStatusNew(year);
		System.out.println("PersonStatusNewListExcel " + PersonStatusNewListExcel.size());
		int sise = listPersonExcel.size();

		double stepForProgressBar = 100;
		stepForProgressBar = stepForProgressBar / sise;

		int k = 0, f = 0;
		for (Person personExcel : listPersonExcel) {
			PersonStatusPersonExcel.clear();
			System.out.println();
			System.out.println("--------------------------------------");
			System.out.println("personExcel " + personExcel.getEgn());
			for (PersonStatusNew personStatusNew : PersonStatusNewListExcel) {

				if (personStatusNew.getPerson().getEgn().equals(personExcel.getEgn())) {
					PersonStatusPersonExcel.add(personStatusNew);
				}
			}
			System.out.println("PersonStatusPersonExcel " + PersonStatusPersonExcel.size());
			for (PersonStatusNew personStatusNew : PersonStatusPersonExcel) {

				System.out.println(
						personStatusNew.getFormulyarName() + " " + sdfrmt.format(personStatusNew.getStartDate()) + " "
								+ sdfrmt.format(personStatusNew.getEndDate()) + " "
								+ personStatusNew.getWorkplace().getOtdel());

			}
			Person person = PersonDAO.getValuePersonByEGN(personExcel.getEgn());
			if(person != null) {
			List<PersonStatusNew> PersonStatusNewPersonListDBase = PersonStatusNewDAO
					.getValuePersonStatusNewByPerson_Year(person, year);

			System.out.println("PersonStatusNewPersonListDBase " + PersonStatusNewPersonListDBase.size());
			for (PersonStatusNew personStatusNew : PersonStatusNewPersonListDBase) {
				System.out.println(
						personStatusNew.getFormulyarName() + " " + sdfrmt.format(personStatusNew.getStartDate()) + " "
								+ sdfrmt.format(personStatusNew.getEndDate()) + " "
								+ personStatusNew.getWorkplace().getOtdel());
			}

			boolean fl;
			PersonStatusNew personStatusExcel;

			for (PersonStatusNew personStatusDbase : PersonStatusNewPersonListDBase) {
				fl = true;
				System.out.println("PersonEGN " + personStatusDbase.getPerson().getEgn());
				for (int i = 0; i < PersonStatusPersonExcel.size(); i++) {
					personStatusExcel = PersonStatusPersonExcel.get(i);

					System.out.println(k + "/" + sise + " excell:" + personStatusExcel.getFormulyarName() + " "
							+ sdfrmt.format(personStatusExcel.getStartDate()) + " "
							+ sdfrmt.format(personStatusExcel.getEndDate()) + " "
							+ personStatusExcel.getWorkplace().getOtdel());
					if (personStatusExcel.getFormulyarName().equals(personStatusDbase.getFormulyarName())
							&& personStatusExcel.getStartDate().equals(personStatusDbase.getStartDate())
							&& personStatusExcel.getEndDate().equals(personStatusDbase.getEndDate())
							&& personStatusExcel.getWorkplace().getOtdel()
									.equals(personStatusDbase.getWorkplace().getOtdel())) {
						fl = false;

						i = PersonStatusPersonExcel.size();
					}
				}
				if (fl) {
					System.out.println(k + "/" + sise + " delete:" + personStatusDbase.getFormulyarName() + " "
							+ sdfrmt.format(personStatusDbase.getStartDate()) + " "
							+ sdfrmt.format(personStatusDbase.getEndDate()) + " "
							+ personStatusDbase.getWorkplace().getOtdel());
					PersonStatusNewDAO.deleteValuePersonStatusNew(personStatusDbase);
					f++;
				}

			}
		}
			aProgressBar.setValue((int) ProgressBarSize);
			ProgressBarSize += stepForProgressBar;
			k++;
		}
		aProgressBar.setValue(100);
		GeneralMethods.setDefaultCursor(panel_AllSaerch);
		return "Изтрити "+f+" записа.";
	}

	public static String delete_PersonWithoutExcelFile(JProgressBar aProgressBar, JPanel panel_AllSaerch, String year) {

		GeneralMethods.setWaitCursor(panel_AllSaerch);
		Set<String> mySet = new HashSet<String>();
		double ProgressBarSize = 0;
		aProgressBar.setValue((int) ProgressBarSize);

		List<Person> listPersonExcel = extracted_Person();
		System.out.println("listPersonExcel " + listPersonExcel.size());
		ActionIcone round = new ActionIcone();
		List<PersonStatusNew> listPersonStatus_DBase = PersonStatusNewDAO.getValuePersonStatusNewByYear(year);
		System.out.println("listPersonStatus_DBase " + listPersonStatus_DBase.size());
		round.StopWindow();
		int sise = listPersonStatus_DBase.size();
		Person personExcel = null;
		
		List<PersonStatusNew> listPersonStatus = new ArrayList<>();
		int k = 0;
		boolean fl;
		for (PersonStatusNew personStatusNew : listPersonStatus_DBase) {
			fl = true;
			for (int i = 0; i < listPersonExcel.size(); i++) {
				personExcel = listPersonExcel.get(i);
				if (personExcel.getEgn().equals(personStatusNew.getPerson().getEgn())) {
					fl = false;
					i = listPersonExcel.size();
				}
			}

			if (fl) {
				System.out.println(k + "/" + sise + " delete:" + personStatusNew.getPerson().getEgn() + " "
						+ personStatusNew.getFormulyarName() + " " + personStatusNew.getWorkplace().getOtdel());
//				listPersonSat.add(personStatusNew);
				listPersonStatus.add(personStatusNew);
			}

			
			k++;
		}
		sise = listPersonStatus.size();
		double stepForProgressBar = 100;
		stepForProgressBar = stepForProgressBar / sise;
		Person person;
		int f=0;
		String egn;
		for (PersonStatusNew personStat : listPersonStatus) {
			egn = personStat.getPerson().getEgn();
			System.out.println(f+" "+egn);
			if(mySet.add(egn)) {
			
			person = PersonDAO.getValuePersonByEGN(egn);
			List<KodeStatus> list = KodeStatusDAO.getKodeStatusByPersonYear(person, year);
			if (list != null) {
				for (KodeStatus kodeSat : list) {
					KodeStatusDAO.deleteValueKodeStatus(kodeSat.getKodeStatus_ID());
				}
			}
			}
			
			PersonStatusNewDAO.deleteValuePersonStatusNew(personStat);
			
			aProgressBar.setValue((int) ProgressBarSize);
			ProgressBarSize += stepForProgressBar;
			f++;
		}
		
		aProgressBar.setValue(100);
		GeneralMethods.setDefaultCursor(panel_AllSaerch);
				
		return "Изтрити "+f+" записа.";
	}
	

	public static List<Person> updatePersonFromExcelFile(String pathFile, ActionIcone round, String textIcon,
			List<Integer> listDiferentRow) {
		Set<String> mySet = new HashSet<String>();
		int countMySet;
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		String EGN = "", FirstName = "", SecondName = "", LastName = "";
		Sheet sheet = workbook.getSheetAt(0);
		Cell cell, cell1;
		List<Person> listPerson = new ArrayList<>();

		int StartRow = 0;
		int endRow = 0;

		if (listDiferentRow != null) {
			StartRow = 0;
			endRow = listDiferentRow.size();
		} else {
			StartRow = 5;
			endRow = sheet.getLastRowNum();
		}
		int row = 0;
		for (int index = StartRow; index < endRow; index += 1) {

			if (listDiferentRow != null) {
				row = listDiferentRow.get(index);
			} else {
				row = index;
			}
			System.out.println(row + " ++++++++++");
			if (sheet.getRow(row) != null && row > 0) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);
				System.out.println(cell1 + " *************");
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					countMySet = mySet.size();
					mySet.add(EGN);
					System.out.println(EGN + " eeeeeeeeeeee " + countMySet + " - " + mySet.size());
					if ((countMySet + 1) == mySet.size()) {
						System.out.println("++++++++++++++++++++" + EGN);
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
		System.out.println("sssssssssssssssss " + listPerson.size());
		return listPerson;
	}

	public static List<PersonStatusNew> getListPersonStatusNewFromExcelFile(String pathFile, String firmName,
			String year, ActionIcone round, String textIcon, List<Integer> listDiferentRow, List<String> arreaOtdels) {
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);

		Set<String> mySet = new HashSet<String>();
		int countMySet;

		List<PersonStatusNew> listPerStatNew = new ArrayList<>();
		Date dateSet = null;

		Person person;
		UsersWBC userSet = WBCUsersLogin.getCurentUser();
		List<Workplace> listAllWorkplaceBiFirmName = WorkplaceDAO.getValueWorkplaceByObject("FirmName", firmName);
		String EGN = "", FirstName = "";
		Date startDate = null;
		Date endDate = null;
		String otdelName = "", formulyarName = "";
		Workplace workplace = new Workplace();
		String[] masiveWorkplace = ReadExcelFileWBC.getMasiveString(firmName);
		Sheet sheet = workbook.getSheetAt(3);
		Cell cell, cell1;
		PersonStatusNew personStatusNew_NotInList = null;
		int StartRow = 0;
		int endRow = 0;

		if (listDiferentRow != null) {
			StartRow = 0;
			endRow = listDiferentRow.size();
		} else {
			StartRow = 5;
			endRow = sheet.getLastRowNum();
		}
		int row = 0;
		for (int index = StartRow; index < endRow; index += 1) {

			if (listDiferentRow != null) {
				row = listDiferentRow.get(index);
				otdelName = UpdateBDataFromExcellFiles.getOtdelNameByListArreaOtdels(arreaOtdels, row);
				workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName,
						listAllWorkplaceBiFirmName);
				System.out.println("workplace " + workplace.getOtdel());
			} else {
				row = index;
			}

			if (sheet.getRow(row) != null && row > 0) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);

				if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
					otdelName = cell1.getStringCellValue().trim();
					if (!otdelName.contains("край") && !otdelName.contains("КРАЙ")) {
						workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName,
								listAllWorkplaceBiFirmName);
					}
				}

				System.out.println("workplace 2 " + workplace.getOtdel());
//				workplace(54) - Транспортиране на СЯГ и ОЯГ;  workplace(101) - Транспортиране СОЯГ 
				if (ReadExcelFileWBC.CellNOEmpty(cell) && workplace.getOtdel() != null
						&& workplace.getId_Workplace() != 54 && workplace.getId_Workplace() != 101) {
					FirstName = ReadExcelFileWBC.getStringEGNfromCell(cell1);
					EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
					countMySet = mySet.size();
					mySet.add(EGN);
					person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
					if (person != null && (countMySet + 1) == mySet.size()) {

						if (!FirstName.contains("АЕЦ")) {
							int k = 7;
							cell = sheet.getRow(row).getCell(k);
							List<PersonStatusNew> ListPrStatNew = PersonStatusNewDAO
									.getValuePersonStatusNewByPerson_Workplace_DateSetInYear(person, workplace, year);

							if (ListPrStatNew.size() == 1
									&& ListPrStatNew.get(0).getFormulyarName().equals("NotInList")) {
								personStatusNew_NotInList = ListPrStatNew.get(0);
							}

//					System.out.println("////////////////////////////////////// ");

							while (ReadExcelFileWBC.CellNOEmpty(cell)) {
								Spisak_Prilogenia spPr = ReadSpisak_PrilogeniaFromExcelFile.getOrCreateSisak_Prilogenie(
										k, row, sheet, startDate, endDate, formulyarName, workplace, year);
//						System.out.println("111111111111111111111111111 ");
								if (personStatusNew_NotInList != null) {
									personStatusNew_NotInList.setFormulyarName(spPr.getFormulyarName());
									personStatusNew_NotInList.setStartDate(spPr.getStartDate());
									personStatusNew_NotInList.setEndDate(spPr.getEndDate());
									personStatusNew_NotInList.setYear(spPr.getYear());
									personStatusNew_NotInList.setUserWBC(userSet);
									personStatusNew_NotInList.setDateSet(dateSet);
//								PersonStatusNewDAO.updateValuePersonStatusNew(personStatusNew_NotInList);
									listPerStatNew.add(personStatusNew_NotInList);
									personStatusNew_NotInList = null;
								} else {
									if (workplace.getOtdel().equals(spPr.getWorkplace().getOtdel())) {
										PersonStatusNew personStatNew = new PersonStatusNew(person, workplace,
												spPr.getFormulyarName(), spPr.getStartDate(), spPr.getEndDate(),
												spPr.getYear(), userSet, dateSet, "");
										listPerStatNew.add(personStatNew);
									}
								}

								k = k + 3;
								cell = sheet.getRow(row).getCell(k);

							}

						}
					}
				}
			}
			ActionIcone.roundWithText(round, textIcon, "Read", index, endRow);
		}

		return listPerStatNew;
	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}

	public static void ActionListener_Btn_deleteSpisakPrilojenia(JPanel panel_AllSaerch, JButton btn_CheckPersonStatus,
			JTextArea textArea, JProgressBar progressBar, String year) {
		btn_CheckPersonStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");

				new ProgresBarByDeleteDataFromDBaseRemoveInCurenFromOldYear(progressBar, textArea, panel_AllSaerch,
						"deleteSpisakPrilojenia", year).execute();

			}

		});

	}

	public static void ActionListener_Btn_deletePersonStatus(JPanel panel_AllSaerch, JButton btn_CheckPersonStatus,
			JTextArea textArea, JProgressBar progressBar, String year) {
		btn_CheckPersonStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");

				new ProgresBarByDeleteDataFromDBaseRemoveInCurenFromOldYear(progressBar, textArea, panel_AllSaerch, "deletePersonStatus",
						year).execute();

			}

		});

	}

	public static void ActionListener_Btn_delete_PersonWithoutExcelFile(JPanel panel_AllSaerch,
			JButton btn_delete_PersonWithoutExcelFile, JTextArea textArea, JProgressBar progressBar, String year) {
		btn_delete_PersonWithoutExcelFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");

				new ProgresBarByDeleteDataFromDBaseRemoveInCurenFromOldYear(progressBar, textArea, panel_AllSaerch,
						"" + "delete_PersonWithoutExcelFile", year).execute();

			}

		});

	}

	static void setTextToArea(JTextArea textArea, String textForArea) {
		if (textForArea.isEmpty()) {
			textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
			DeleteDataFromDBaseRemoveInCurenFromOldYear_Frame.getBtn_Export().setEnabled(false);
		} else {
			textArea.setText(textForArea);
			DeleteDataFromDBaseRemoveInCurenFromOldYear_Frame.getBtn_Export().setEnabled(true);
		}
	}

}
