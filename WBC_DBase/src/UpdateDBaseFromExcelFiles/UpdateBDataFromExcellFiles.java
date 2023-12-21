package UpdateDBaseFromExcelFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.ReadMeasuringFromExcelFile;
import Aplication.ReadPersonFromExcelFile;
import Aplication.ReadPersonStatusFromExcelFile;
import Aplication.ReadResultsWBCFromExcelFile;
import Aplication.ReadSpisak_PrilogeniaFromExcelFile;
import BasiClassDAO.ActualExcellFilesDAO;
import BasicClassAccessDbase.ActualExcellFiles;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;
import MenuClasses.WBC_MainWindowFrame;
import PersonManagement.PersonelManegementMethods;
import SearchFreeKode.infoFrame;

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
		
		String destinationPathArhiveNow = ReadFileBGTextVariable.getGlobalTextVariableMap().get("destinationPathArhiveNow");
		String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
		List<ActualExcellFiles> listActualExcellFiles = ActualExcellFilesDAO.getAllValueActualExcellFiles();
		List<String> listChengeExcellFilePath = listChengeExcellFile(round, listActualExcellFiles, excellFiles);
		
		List<String> listPathOldFileFromArhive = getListAllExcelFileFromArhive(new File(destinationPathArhiveNow));
		
		List<List<List<String>>> listStringForDiferentRow = checkTwoExcelFiles(listPathOldFileFromArhive, listChengeExcellFilePath);
		
		String textForInfoFrame = generateInfoDiferentRow(listStringForDiferentRow);
		System.out.println(textForInfoFrame);
		
		
		
		String measege = "";
		String preporachvaSe = ReadFileBGTextVariable.getGlobalTextVariableMap().get("preporachvaSe");
		String imaPromeniVavFayla = ReadFileBGTextVariable.getGlobalTextVariableMap().get("imaPromeniVavFayla");

		if(listChengeExcellFilePath.size() > 0 && listPathOldFileFromArhive.size() > 0) {
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
			int opcion = choiceOptionDialog(measege);
			while (opcion==3) {
				generateInfoPanel(textForInfoFrame);
				opcion = choiceOptionDialog(measege);
			}
			
			if (opcion > 0) {
				if(opcion==2) {
				listStringForDiferentRow= null;
				}
				startUpdateFromExcelFile(listChengeExcellFilePath, listStringForDiferentRow);	
			
			}
		}else {
			boolean afterExcelUpdate = true;
			WBC_MainWindowFrame.updateLastActualsDBaseFromExcelFile(afterExcelUpdate);
		}
		
		
	}


	private static void startUpdateFromExcelFile(List<String> listChengeExcellFilePath,
			List<List<List<String>>> listStringForDiferentRow) {
		ActionIcone round2 = new ActionIcone("                                "
				+ "                                              ");
		 final Thread thread = new Thread(new Runnable() {
			
		     @Override
		     public void run() {
		    	
		    	 extracted(round2, listChengeExcellFilePath, listStringForDiferentRow);
				
		    	 
		     }
		  
		    });
		    thread.start();
	}

	public static List<String> getListAllExcelFileFromArhive(File file) {
		
		List<File> listAllExcellFile = new ArrayList<>();
		File[] list = file.listFiles();
		if (list != null) {
			for (File fil : list) {
				listAllExcellFile.add(fil);
				if (fil.isDirectory()) {
					getListAllExcelFileFromArhive( fil);
				}

			}
		} else {
			JOptionPane.showMessageDialog(null, "Недостигам до директория:" + "");

		}
		
		sortExcellFileFromArhiveByDate(listAllExcellFile);
		String name="";
		List<String> listExcellFile = new ArrayList<>();
		List<String> fileExtr = new ArrayList<>();
		List<String> filePers = new ArrayList<>();
		for (File excelFile : listAllExcellFile) {
			name = excelFile.getName();
		if (name.contains("EXTERNAL")) {
			fileExtr.add(excelFile.getPath());
			}else {
				filePers.add(excelFile.getPath());
			}
		}
		if(filePers.size()>0) listExcellFile.add(filePers.get(0));
		
		if(fileExtr.size()>0) listExcellFile.add(fileExtr.get(0));
				
		return listExcellFile;
	}
	
	 public static void sortListInt( List<Integer> excelFileArhive)
	    {	    		    	

		Collections.sort(excelFileArhive, new Comparator<Integer>() {
		 
		@Override
		public int compare(Integer o1, Integer o2) {
			 return o1.compareTo(o2);
		}
		});
	
	    }
	
	
	 public static void sortExcellFileFromArhiveByDate( List<File> excelFileArhive)
	    {	    		    	

		Collections.sort(excelFileArhive, new Comparator<File>() {
		 
		@Override
		public int compare(File o1, File o2) {
			 return extractedDateTimeFromFile(o2.getPath()).compareTo(extractedDateTimeFromFile(o1.getPath()));
		}
		});
	
	    }
	
	
	static  List<List<List<String>>> checkTwoExcelFiles(List<String> ListPathToOldFile, List<String> excellFiles) {

		List<List<List<String>>> listExcelFile = new ArrayList<>();

		
		FileInputStream inputOldStream;
		FileInputStream inputNewStream;
		
		String PathToOldFile = ListPathToOldFile.get(0);
		for (String PathToNewFile : excellFiles) {
			String excelFileName = "Per";
			if (PathToNewFile.contains("EXTERNAL")) {
				PathToOldFile = ListPathToOldFile.get(1);
				excelFileName = "Ext";
			}
			System.out.println(PathToOldFile);
			List<List<String>> listAllRowString = new ArrayList<>();
		try {
			inputOldStream = new FileInputStream(PathToOldFile);
			try (Workbook workbookOld = new HSSFWorkbook(inputOldStream)) {
				inputNewStream = new FileInputStream(PathToNewFile);
				try (Workbook workbooknew = new HSSFWorkbook(inputNewStream)) {
				
					int lastRowNew = workbooknew.getSheetAt(0).getPhysicalNumberOfRows();
					int lastRowOld = workbookOld.getSheetAt(0).getPhysicalNumberOfRows();
					
				
					int countRow = lastRowOld;
					int indexOldRow;
					Sheet worksheetOld;
					Sheet worksheetNew;

					Row sourceRowOld;
					Row sourceRowNew;
					String strOld = "";
					String strNew = "";
					String str = "";
					
					String egnOld = "";
					String egnNew = "";

						for (int i = 0; i < 4; i++) {
							List<String> listRowStr = new ArrayList<>();

							worksheetOld = workbookOld.getSheetAt(i);
							worksheetNew = workbooknew.getSheetAt(i);

							indexOldRow = 0;
							for (int j = 0; j < countRow; j++) {

								sourceRowOld = worksheetOld.getRow(indexOldRow);
								sourceRowNew = worksheetNew.getRow(j);

								if (sourceRowOld != null && sourceRowNew != null) {

									
									
									for (int k = 0; k < 256; k++) {

										
										
										str = ReadExcelFileWBC.getStringEGNfromCell(sourceRowOld.getCell(k));
										if (!str.isEmpty()) {
											strOld += str + " | ";
										}

										str = ReadExcelFileWBC.getStringEGNfromCell(sourceRowNew.getCell(k));
										if (!str.isEmpty()) {
											strNew += str + " | ";
										}
									}
									
									egnOld = ReadExcelFileWBC.getStringEGNfromCell(sourceRowOld.getCell(5));
									egnNew = ReadExcelFileWBC.getStringEGNfromCell(sourceRowNew.getCell(5));
									
									if (lastRowOld < lastRowNew) {
										if(!egnOld.equals(egnNew)){
											strOld = "";
											indexOldRow--;
										}
												
									}
//									else {
//										if(!egnOld.equals(egnNew)){
//											strNew = "";
//											j--;
//										}	
//									}
									
									
									if (!strOld.equals(strNew)) {
										listRowStr.add(excelFileName + " :"+ j + ": 1 - " + strOld + ": 2 - " + strNew);
									
									}
									strOld = "";
									strNew = "";
								}
								indexOldRow++;
							}
							listAllRowString.add(listRowStr);

						}
						
						
				
				}
			}

		} catch (IOException e) {
			
			e.printStackTrace();
		}
		listExcelFile.add(listAllRowString);
	}
		return listExcelFile;

	}


	private static String generateInfoDiferentRow(List<List<List<String>>> listExcelFile) {
		String text = "";
		
		for (List<List<String>> listAllRowString : listExcelFile) {
			int l = 1;
			
		for (List<String> listInt : listAllRowString) {
			if (listInt.size() > 0) {
				text += "Razliki w Sheet " + l + "\n";
//				System.out.println("Razliki w Sheet " + l);
				for (String integer : listInt) {
					String[] rowStr = integer.split(":");
					text += rowStr[0]+" - row " + rowStr[1] + "\n";
//					System.out.println(rowStr[0]+" - row " + rowStr[1]);
					text += "In Old fail " + rowStr[2] + "\n";
//					System.out.println("In Old fail " + rowStr[2]);
					text += "In new fail " + rowStr[3] + "\n";
//					System.out.println("In new fail " + rowStr[3]);

				}
			}
			l++;
		}
		text +=  "\n*************************************\n";
		}
		return text;
	}

	
	private static void generateInfoPanel(String textForInfoFrame) {

		JFrame parent = new JFrame();
		int[] sizeInfoFrame = { 800, 400 };
	
				new infoFrame(parent, null, textForInfoFrame, sizeInfoFrame, null);

	}
	


	private static void extracted(ActionIcone round, List<String> listChengeExcellFilePath, List<List<List<String>>> listStringForDiferentRow) {
		String[] key = { 
				"Person", 
				"Spisak_Prilogenia", 
				"PersonStatus", 
				"KodeStatus", 
				"Measuring", 
				"ResultsWBC", 
				"ObhodenList"
				};
		String year = AplicationMetods.getCurentYear();
		String textIcon;
		String errorText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("errorText");
		boolean save = true;
		boolean sameDiferentRow = false;
		List<Integer> listRowByExt = new ArrayList<>();
		List<Integer> listRowByPer = new ArrayList<>();
		if(listStringForDiferentRow != null) {
			sameDiferentRow = true;
			for (List<List<String>> list1 : listStringForDiferentRow) {
				for (List<String> list2 : list1) {
					for (String str : list2) {
						String[] rowStr = str.split(":");
						if(rowStr[0].contains("Ext")) {
							listRowByExt.add(Integer.parseInt(rowStr[1]));
							
						}else {
							listRowByPer.add(Integer.parseInt(rowStr[1]));
						}
						
					}
				}
			}
		}
		listRowByPer = revoveAndSortList(listRowByPer);
		listRowByExt = revoveAndSortList(listRowByExt);
		sortListInt(listRowByPer);
		sortListInt(listRowByExt);
		
		System.out.println("listRowByExt "+	listRowByExt.size()+",  listRowByPer "+listRowByPer);
		
		for (int i = 0; i < key.length; i++) {
			
			
//			textIcon ="<html><center>Update " +(i+1)+ "/7 <br>" + key[i]+"</center></html>";
//			round.setTextToImage(textIcon);
			for (String pathFile : listChengeExcellFilePath) {
				List<Integer> listDiferentRow = new ArrayList<>();
				
				String firmName = "АЕЦ Козлодуй";
				if (pathFile.contains("EXTERNAL")) {
					firmName = "Външни организации";
				}
				
				if(sameDiferentRow) {
					listDiferentRow = listRowByPer;
					if (pathFile.contains("EXTERNAL")) {
						listDiferentRow = listRowByExt;
					}
					}else {
							listDiferentRow = null;
						}
				
				textIcon ="<html><center>Update " + key[i]+" ("+(i+1)+ "/7)" +"<br>"+firmName+" ";
				save = true;
				switch (key[i]) {
				case "Person": {
					// read and set Person
					List<Person> listPerson = null;
					try {
						listPerson = ReadPersonFromExcelFile.updatePersonFromExcelFile(pathFile, round, textIcon, listDiferentRow);
						System.out.println("--> " + listPerson.size());
					} catch (Exception e) {
						e.printStackTrace();
						save =OptionDialog(errorText);
						
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
								.getSpisak_Prilogenia_ListFromExcelFile(pathFile, firmName, year, round, textIcon, listDiferentRow);

//						ReadSpisak_PrilogeniaFromExcelFile.ListSpisak_PrilogeniaToBData(Spisak_PrilogeniaList);
						System.out.println("--> " + Spisak_PrilogeniaList.size());
					} catch (Exception e) {
						e.printStackTrace();
						save = OptionDialog(errorText);
						
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
								firmName, year, round, textIcon, listDiferentRow);
						System.out.println("list PersonStatus from Excell " + list.size());
//						ReadPersonStatusFromExcelFile.ListPersonStatus(list);
						System.out.println("--> " + list.size());
					} catch (Exception e) {
						save = OptionDialog(errorText);
						
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
								firmName, year, round, textIcon, listDiferentRow);
//						ReadKodeStatusFromExcelFile.ListKodeStatus(listKodeStatus);
						System.out.println("--> " + listKodeStatus.size());
					} catch (Exception e) {
						save = OptionDialog(errorText);
						
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
								.generateListFromMeasuringFromExcelFile(pathFile, round, textIcon, listDiferentRow);
//						ReadMeasuringFromExcelFile.ListMeasuringToBData(listMeasuring);
						System.out.println("--> " + listMeasuring.size());
					} catch (Exception e) {
						save = OptionDialog(errorText);
					
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
								.generateListFromResultsWBCFromExcelFile(pathFile, round, textIcon, listDiferentRow);
//						ReadResultsWBCFromExcelFile.ListResultsWBCToBData(listResultsWBC);
						System.out.println("--> " + listResultsWBC.size());
					} catch (Exception e) {
						save = OptionDialog(errorText);
						
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
			String destinationPathArhiveNow = ReadFileBGTextVariable.getGlobalTextVariableMap().get("destinationPathArhiveNow");
			for (String filePath : listChengeExcellFilePath) {
				File file = new File(filePath);
				
				ActualExcellFiles actualExcellFile = ActualExcellFilesDAO.getValueActualExcellFilesByName(file.getAbsoluteFile().getName());
				actualExcellFile.setActualExcellFiles_Date(extractedDateTimeFromFile(filePath));
				ActualExcellFilesDAO.updateValueActualExcellFiles(actualExcellFile);
				PersonelManegementMethods.copyExcelFileToDestDir(filePath, destinationPathArhiveNow);
				System.out.println(filePath);
				System.out.println("updateValueActualExcellFiles ");
			}
			
			boolean afterExcelUpdate = true;
			WBC_MainWindowFrame.updateLastActualsDBaseFromExcelFile(afterExcelUpdate);
			
		}
		round.StopWindow();
	}

	private static List<Integer> revoveAndSortList(List<Integer> list) {
		List<Integer> deDupStringList = new ArrayList<>(new HashSet<>(list));
		
		return deDupStringList;
		
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
	
	public static Timestamp extractedDateTimeFromFile(String pathFile) {
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
		String[] options = { "Skip", "Update"};
		int x = JOptionPane.showOptionDialog(null, mesage, "Info", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		System.out.println(x);
		/**
		 * select "Back" -> 0; select "Ok" -> 1;
		 */
		if (x ==1) {
			return true;
		}
		return false;
	}

	public static int choiceOptionDialog(String mesage) {
		String[] options = { "Skip", "Update", "Full Update", "Vew Info" };
		int x = JOptionPane.showOptionDialog(null, mesage, "Info", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		System.out.println(x);
		/**
		 * select "Skip" -> 0; select "Update" -> 1; select "Full Update" -> 2; select "Vew Info" -> 3;
		 */
		
		return x;
	}
	
	
	
	
}
