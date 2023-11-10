package Aplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Timestamp;
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
import MenuClasses.WBC_MainWindowFrame;

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
	ActionIcone round = new ActionIcone();
	 final Thread thread = new Thread(new Runnable() {
		
	     @Override
	     public void run() {
	       
	    	 updataFromGodExcelFile(round);
			
	    	 
	     }
	  
	    });
	    thread.start();	
	
	}
	
	
	public static void updataFromGodExcelFile(ActionIcone round) {
		
		String MainWindowFrame_Label = ReadFileBGTextVariable.getGlobalTextVariableMap().get("MainWindowFrame_Label");
		ActualExcellFiles actualExcellFiles_LastActuals = ActualExcellFilesDAO.getValueActualExcellFilesByName("LastActuals");
		String date_LastActuals = sdfrmt.format(actualExcellFiles_LastActuals.getActualExcellFiles_Date());
		WBC_MainWindowFrame.getLblNewLabel().setText(MainWindowFrame_Label+" "+date_LastActuals);
		
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
		List<ActualExcellFiles> listActualExcellFiles = ActualExcellFilesDAO.getAllValueActualExcellFiles();
		List<String> listChengeExcellFilePath = listChengeExcellFile(round, listActualExcellFiles, excellFiles);
		
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
		round.StopWindow();
		
		if (!measege.isEmpty()) {

			if (OptionDialog(measege)) {
				ActionIcone round2 = new ActionIcone("                                                        ");
				 final Thread thread = new Thread(new Runnable() {
					
				     @Override
				     public void run() {
				       
				    	 extracted(round2, listChengeExcellFilePath);
						
				    	 
				     }
				  
				    });
				    thread.start();	
				
				
				
			}
		}else {
			WBC_MainWindowFrame.updateLastActualsDBaseFromExcelFile();
		}
		
		
	}


	private static void extracted(ActionIcone round, List<String> listChengeExcellFilePath) {
		String[] key = { 
				"Person", "Spisak_Prilogenia", "PersonStatus", "KodeStatus", "Measuring", "ResultsWBC", 
				"ObhodenList" };
		String year = AplicationMetods.getCurentYear();
		String textIcon;
		String errorText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("errorText");
		boolean save = true;
		for (int i = 0; i < key.length; i++) {
			
			
//			textIcon ="<html><center>Update " +(i+1)+ "/7 <br>" + key[i]+"</center></html>";
//			round.setTextToImage(textIcon);
			for (String pathFile : listChengeExcellFilePath) {
				String firmName = "АЕЦ Козлодуй";
				if (pathFile.contains("EXTERNAL")) {
					firmName = "Външни организации";
				}
				textIcon ="<html><center>Update " + key[i]+" ("+(i+1)+ "/7)" +"<br>"+firmName+" ";
				save = true;
				switch (key[i]) {
				case "Person": {
					// read and set Person
					List<Person> listPerson = null;
					try {
						listPerson = ReadPersonFromExcelFile.updatePersonFromExcelFile(pathFile, round, textIcon);
						System.out.println("--> " + listPerson.size());
					} catch (Exception e) {
						OptionDialog(errorText);
						save = false;
					}
					if (save) {
						ReadPersonFromExcelFile.setToDBaseListPerson(listPerson, round, textIcon);
						System.out.println("Save set Person " + firmName);
					}
				}
					break;

				case "Spisak_Prilogenia": {
					// read and set Spisak_Prilogenia
					List<Spisak_Prilogenia> Spisak_PrilogeniaList = null;
										
					try {
						Spisak_PrilogeniaList = ReadSpisak_PrilogeniaFromExcelFile
								.getSpisak_Prilogenia_ListFromExcelFile(pathFile, firmName, year, round, textIcon);

//						ReadSpisak_PrilogeniaFromExcelFile.ListSpisak_PrilogeniaToBData(Spisak_PrilogeniaList);
						System.out.println("--> " + Spisak_PrilogeniaList.size());
					} catch (Exception e) {
						e.printStackTrace();
						OptionDialog(errorText);
						save = false;
					}
					if (save) {
						ReadSpisak_PrilogeniaFromExcelFile
								.setListSpisak_PrilogeniaToBData(Spisak_PrilogeniaList, round, textIcon);
						System.out.println("Save set Spisak_Prilogenia " + firmName);
					}
				}
					break;

				case "PersonStatus": {
					// read and set PersonStatus
					List<PersonStatus> list = null;
					try {
						list = ReadPersonStatusFromExcelFile.getListPersonStatusFromExcelFile(pathFile,
								firmName, year, round, textIcon);
						System.out.println("list PersonStatus from Excell " + list.size());
//						ReadPersonStatusFromExcelFile.ListPersonStatus(list);
						System.out.println("--> " + list.size());
					} catch (Exception e) {
						OptionDialog(errorText);
						save = false;
					}
					if (save) {
						ReadPersonStatusFromExcelFile.setToBDateListPersonStatus(list, round, textIcon);
						System.out.println("Save set PersonStatus " + firmName);
					}
				}
					break;

				case "KodeStatus": {
					// read and set KodeStatus
					List<KodeStatus> listKodeStatus = null;
					try {
						listKodeStatus = ReadKodeStatusFromExcelFile.getListKodeStatusFromExcelFile(pathFile,
								firmName, year, round, textIcon);
//						ReadKodeStatusFromExcelFile.ListKodeStatus(listKodeStatus);
						System.out.println("--> " + listKodeStatus.size());
					} catch (Exception e) {
						OptionDialog(errorText);
						save = false;
					}
					if (save) {
						ReadKodeStatusFromExcelFile.setToDBaseListKodeStatus(listKodeStatus, round, textIcon);
						System.out.println("Save set KodeStatus " + firmName);
					}

				}
					break;
				case "Measuring": {
					// read and set Measuring
					List<Measuring> listMeasuring = null;
					try {
						listMeasuring = ReadMeasuringFromExcelFile
								.generateListFromMeasuringFromExcelFile(pathFile, round, textIcon);
//						ReadMeasuringFromExcelFile.ListMeasuringToBData(listMeasuring);
						System.out.println("--> " + listMeasuring.size());
					} catch (Exception e) {
						OptionDialog(errorText);
						save = false;
					}
					if (save) {
						ReadMeasuringFromExcelFile.setListMeasuringToBData(listMeasuring, round, textIcon);
						System.out.println("Save set Measuring " + firmName);
					}

				}
					break;
				case "ResultsWBC": {
					// read and set ResultsWBC
					List<ResultsWBC> listResultsWBC = null;
					try {
						listResultsWBC = ReadResultsWBCFromExcelFile
								.generateListFromResultsWBCFromExcelFile(pathFile, round, textIcon);
//						ReadResultsWBCFromExcelFile.ListResultsWBCToBData(listResultsWBC);
						System.out.println("--> " + listResultsWBC.size());
					} catch (Exception e) {
						OptionDialog(errorText);
						save = false;
					}
					if (save) {
						ReadResultsWBCFromExcelFile.setListResultsWBCToBData(listResultsWBC, round, textIcon);
						System.out.println("Save set ResultsWBC " + firmName);
					}
				}
					break;
				case "ObhodenList": {
					// read and set ObhodenList in PersonStatus
					List<PersonStatus> list = ReadPersonStatusFromExcelFile.getObhodenListPersonStatusFromExcelFile(pathFile, firmName,
							year, round, textIcon);
					ReadPersonStatusFromExcelFile.ListPersonStatus(list);
					System.out.println(year+ " --> "+list.size());
					if(save) {
					ReadPersonStatusFromExcelFile.setToBDateListPersonStatus(list, round, textIcon);
					System.out.println("Save "+firmName);
					}
				}
				}
			}

		}
		if (save) {
			for (String filePath : listChengeExcellFilePath) {
				File file = new File(filePath);
				
				ActualExcellFiles actualExcellFile = ActualExcellFilesDAO.getValueActualExcellFilesByName(file.getAbsoluteFile().getName());
				actualExcellFile.setActualExcellFiles_Date(extractedDateTimeFromFile(filePath));
				ActualExcellFilesDAO.updateValueActualExcellFiles(actualExcellFile);
				
				
				System.out.println("updateValueActualExcellFiles ");
			}
			
			WBC_MainWindowFrame.updateLastActualsDBaseFromExcelFile();
		}
		round.StopWindow();
	}

	private static List<String> listChengeExcellFile(ActionIcone round, List<ActualExcellFiles> listActualExcellFiles,
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
					System.out.println(dateActualExcellFiles+"  -  "+dateFile+"  **  "+actualExcellFiles.getActualExcellFiles_Date());
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
	
	static Timestamp extractedDateTimeFromFile(String pathFile) {
		File fis = new File(pathFile);
		long lastModifiedFile = fis.lastModified();
		Timestamp dateLastModified = new Timestamp(lastModifiedFile);
		return dateLastModified;
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
