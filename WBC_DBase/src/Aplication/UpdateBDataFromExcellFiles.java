package Aplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import BasiClassDAO.ActualExcellFilesDAO;
import BasicClassAccessDbase.ActualExcellFiles;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;

public class UpdateBDataFromExcellFiles {

//	String key = "Person";
//	String key = "Spisak_Prilogenia";
//	String key = "PersonStatus";
//	String key = "KodeStatus";
//	String key = "Measuring";
//	String key = "ResultsWBC";

//	String year = "2022";

//	boolean save = true;
//	boolean save = false;

	static SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

	public static void updataFromGodExcelFile() {
		boolean save;
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
		String[] key = { "Person", "Spisak_Prilogenia", "PersonStatus", "KodeStatus", "Measuring", "ResultsWBC" };
		String year = AplicationMetods.getCurentYear();
		List<ActualExcellFiles> listActualExcellFiles = ActualExcellFilesDAO.getAllValueActualExcellFiles();
		List<String> listChengeExcellFilePath = listChengeExcellFile(listActualExcellFiles, excellFiles);

		String measege = "";
		String preporachvaSe = ReadFileBGTextVariable.getGlobalTextVariableMap().get("preporachvaSe");
		String imaPromeniVavFayla = ReadFileBGTextVariable.getGlobalTextVariableMap().get("imaPromeniVavFayla");

		if(listChengeExcellFilePath.size()>0) {
			measege = preporachvaSe+ "\n";
		for (String filePath : listChengeExcellFilePath) {
			File file = new File(filePath);
			measege = measege + addString(imaPromeniVavFayla, file.getAbsoluteFile().getName(), 7) + " "
					+ extractedDateTimeFromFile(sdfrmt, filePath) + "\n";
			System.out.println("measege " + measege);
		}
		}
		if (!measege.isEmpty()) {

			if (OptionDialog(measege)) {
				System.out.println("UpData");

				for (int i = 0; i < key.length; i++) {

					for (String pathFile : listChengeExcellFilePath) {
						String firmName = "АЕЦ Козлодуй";
						if (pathFile.contains("EXTERNAL")) {
							firmName = "Външни организации";
						}

						save = true;
						switch (key[i]) {
						case "Person": {
							// read and set Person
							List<Person> listPerson = null;
							try {
								listPerson = ReadPersonFromExcelFile.updatePersonFromExcelFile(pathFile);
								System.out.println("--> " + listPerson.size());
							} catch (Exception e) {
								save = false;
							}
							if (save) {
								ReadPersonFromExcelFile.setToDBaseListPerson(listPerson);
								System.out.println("Save " + firmName);
							}
						}
							break;

						case "Spisak_Prilogenia": {
							// read and set Spisak_Prilogenia
							List<Spisak_Prilogenia> Spisak_PrilogeniaList = null;
							try {
								Spisak_PrilogeniaList = ReadSpisak_PrilogeniaFromExcelFile
										.getSpisak_Prilogenia_ListFromExcelFile(pathFile, firmName, year);

								ReadSpisak_PrilogeniaFromExcelFile.ListSpisak_PrilogeniaToBData(Spisak_PrilogeniaList);
								System.out.println("--> " + Spisak_PrilogeniaList.size());
							} catch (Exception e) {
								save = false;
							}
							if (save) {
								ReadSpisak_PrilogeniaFromExcelFile
										.setListSpisak_PrilogeniaToBData(Spisak_PrilogeniaList);
								System.out.println("Save " + firmName);
							}
						}
							break;

						case "PersonStatus": {
							// read and set PersonStatus
							List<PersonStatus> list = null;
							try {
								list = ReadPersonStatusFromExcelFile.getListPersonStatusFromExcelFile(pathFile,
										firmName, year);
								ReadPersonStatusFromExcelFile.ListPersonStatus(list);
								System.out.println("--> " + list.size());
							} catch (Exception e) {
								save = false;
							}
							if (save) {
								ReadPersonStatusFromExcelFile.setToBDateListPersonStatus(list);
								System.out.println("Save " + firmName);
							}
						}
							break;

						case "KodeStatus": {
							// read and set KodeStatus
							List<KodeStatus> listKodeStatus = null;
							try {
								listKodeStatus = ReadKodeStatusFromExcelFile.getListKodeStatusFromExcelFile(pathFile,
										firmName, year);
								ReadKodeStatusFromExcelFile.ListKodeStatus(listKodeStatus);
								System.out.println("--> " + listKodeStatus.size());
							} catch (Exception e) {
								save = false;
							}
							if (save) {
								ReadKodeStatusFromExcelFile.setToDBaseListKodeStatus(listKodeStatus);
								System.out.println("Save " + firmName);
							}

						}
							break;
						case "Measuring": {
							// read and set Measuring
							List<Measuring> listMeasuring = null;
							try {
								listMeasuring = ReadMeasuringFromExcelFile
										.generateListFromMeasuringFromExcelFile(pathFile);
								ReadMeasuringFromExcelFile.ListMeasuringToBData(listMeasuring);
								System.out.println("--> " + listMeasuring.size());
							} catch (Exception e) {
								save = false;
							}
							if (save) {
								ReadMeasuringFromExcelFile.setListMeasuringToBData(listMeasuring);
								System.out.println("Save " + firmName);
							}

						}
							break;
						case "ResultsWBC": {
							// read and set ResultsWBC
							List<ResultsWBC> listResultsWBC = null;
							try {
								listResultsWBC = ReadResultsWBCFromExcelFile
										.generateListFromResultsWBCFromExcelFile(pathFile);
								ReadResultsWBCFromExcelFile.ListResultsWBCToBData(listResultsWBC);
								System.out.println("--> " + listResultsWBC.size());
							} catch (Exception e) {
								save = false;
							}
							if (save) {
								ReadResultsWBCFromExcelFile.setListResultsWBCToBData(listResultsWBC);
								System.out.println("Save " + firmName);
							}
						}
							break;
						}
					}

				}
			}
		}
	}

	private static List<String> listChengeExcellFile(List<ActualExcellFiles> listActualExcellFiles,
			String[] excellFiles) {

		List<String> listChangeFilsPath = new ArrayList<>();
		for (String pathFile : excellFiles) {

			String fileName = new File(pathFile).getAbsoluteFile().getName();
			String dateFile = extractedDateTimeFromFile(sdfrmt, pathFile);
			String dateActualExcellFiles;
			boolean fl = true;
			for (ActualExcellFiles actualExcellFiles : listActualExcellFiles) {
				if (actualExcellFiles.getActualExcellFiles_Name().contains(fileName)) {
					dateActualExcellFiles = sdfrmt.format(actualExcellFiles.getActualExcellFiles_Date());
					if (dateActualExcellFiles.equals(dateFile)) {
						fl = false;
					}
				}

			}
			if (fl) {
				listChangeFilsPath.add(pathFile);

			}
		}

		return listChangeFilsPath;
	}

	private static String extractedDateTimeFromFile(SimpleDateFormat sdfrmt, String pathFile) {
		File fis = new File(pathFile);
		long lastModifiedFile = fis.lastModified();
		Date dateLastModified = new Date(lastModifiedFile);
		String dateFile = sdfrmt.format(dateLastModified);
		return dateFile;
	}

	public static String addString(String str, String ch, int position) {
		StringBuilder sb = new StringBuilder(str);
		sb.insert(position, ch);
		return sb.toString();

	}

	public static boolean OptionDialog(String mesage) {
		String[] options = { "Skip", "Update" };
		int x = JOptionPane.showOptionDialog(null, mesage, "Info", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		System.out.println(x);
		/**
		 * select "Back" -> 0; select "Ok" -> 1;
		 */
		if (x > 0) {
			return true;
		}
		return false;
	}

}
