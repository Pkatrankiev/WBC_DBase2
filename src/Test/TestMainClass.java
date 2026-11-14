package Test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Aplication.ReadFileBGTextVariable;
import MenuClasses.WBC_MainWindowFrame;
import QueryExecutor.QueryExecutorWindow;


public class TestMainClass {

	public static void main(String[] args) throws Exception {
		if(ReadFileBGTextVariable.CreadMasiveFromReadFile()) {
			JFrame f = new JFrame();
			 if(ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD").equals("1")) {
				 WBC_MainWindowFrame.FlatLightLafSetup(f);
				}else {
					 WBC_MainWindowFrame.GetVisibleLAF(f);
				}
//			String pathOld = "d:\\!!000\\PERSONNEL-09-00.xls";
//			String pathNew = "d:\\!!000\\PERSONNEL-09-10.xls";
//			TestClasess.checkTwoExcelFiles(pathOld, pathNew);
	
			
//			 DozeArt_Methods.viewData("Zn-65");
//			 String[] masiveDataForReport = {"6902121962  Петър Велизаров Катранкиев   А942   О ОК(С) - КЦ \"Персонална дозиметрия\"  25.08.2024",""};
//			 final JFrame ff = new JFrame();
//			 new DozeArtFrame(ff,null, null);
			
					
			 SwingUtilities.invokeLater(() -> {
		            QueryExecutorWindow window = new QueryExecutorWindow();
		            window.setVisible(true);
			 });
			 
//			 ****************************************************************************
//				
//				 System.out.println("2222222222222222222222222222222");
//				ActionIcone round = new ActionIcone();
//				 final Thread thread = new Thread(new Runnable() {
//				     @Override
//				     public void run() {
//				    	 System.out.println("33333333333333333333333333333333333333");
////				    	 String labelReferenceKontrolPersonByPeroid = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelReferenceKontrolPersonByPeroid");
//				    	new CheckCodeByExcelFileAndOID_Frame("test", round);
//				    	
////				    	round.StopWindow();
//				     }
//				    });
//				    thread.start();	
////					
//		**************************************************************************************	
			 
			
//			new CheckInfoDBaseToExcelFilesFrame();
			   
//			 AutoInsertMeasutingMethods.AutoInsertMeasutingStartFrame(UsersWBCDAO.getValueUsersWBCByID(1));
			 
			 
//			 For_Kamburov_SetNamePersonalByEGN.setNameByEGN();
				    
				    
//	   **************************************************************************************						    
			
			 
			   
//		**************************************************************************************			
			 
//			 ActionIcone round = new ActionIcone();
//			 File[] files = new File[0];
//			 List<ReportMeasurClass> listReportMeasurClass = ReadResultFromReport.getListReadGamaFiles(files);
//			 String[] listSimbolNuclide = NuclideWBCDAO.getMasiveSimbolNuclide();
//				String[] listLaboratory = LaboratoryDAO.getMasiveLaboratory();
//				String[] listUserWBC = UsersWBCDAO.getMasiveUserWBCNames();
//				String[] listTypeMeasur = TypeMeasurDAO.getMasiveTypeMeasur();
//				String[] listTypeNameMeasur = TypeMeasurDAO.getMasiveNameTypeMeasur();
//				List<Person> listAllPerson = PersonDAO.getAllValuePerson();
//				new AutoInsertMeasutingFrame(round, new Frame(), listReportMeasurClass, listSimbolNuclide,
//						listLaboratory, listUserWBC, listTypeMeasur, listTypeNameMeasur, null, listAllPerson, true);
			 
//			AutoInsertMeasutingMethods.AutoInsertMeasutingStartFrame();
			 
//			DozeArt_Methods.givenEmpList_SortEmpList_thenCheckSortedList();
//			TestClasess.deleteKodeStatusByMisingPerson();
//			PersonStatusNewDAO.deleteValuePersonStatusNew(PersonStatusNewDAO.getValuePersonStatusNewByID(0));
//			TestClasess.deletePersonStatusNewInYere("2024");
//			TestClasess.PresonStatusNewWithYear();
			
//			TestClasess.CheckCurentDataInExcelFiles();
			
//			 new CheckErrorDataInExcellFiles_Frame ();
			 
			 
//			 DeleteDublicatePersonStatusNewDiferentOtdels.personStatusNewInYere("2024");
			 
			 
//			 Double dd = 66879.25;
//			String ss =  String.format("%,.0f", dd);
//			 System.out.println(ss);
//			 for (int i = 0; i < ss.length(); i++) {
//				 System.out.println(ss.substring(i,i+1));
//				 if(ss.substring(i,i+1).equals(" ")) {
//					 System.out.println("+++++++++++++++++++");
//				 }
//			}
//			 Double.parseDouble(ss);
			 
			 
//			TestClasess.submittingForm();
//			String USER = "idk2_3";
//					String	PASS = "idk2_3";
//			
////			String command = "D:\\NewDB.mdb"+" /Name " + USER + " /Password "+ PASS;
//			String command ="  D:\\NewDB.mdb /wrkgrp D:\\RZIK2.MDW /user idk2_3 /pwd qwer1234";	
////			String command ="C:\\Program Files (x86)\\Access2003\\OFFICE11\\MSACCESS.EXE /wrkgrp \\QARCdb\\users\\database\\RZIK2.MDW D:\\NewDB.mdb /user= idk2_3 /pwd=qwer1234"	;
//					
//		
//			try {
//					if (Desktop.isDesktopSupported()) {
//						Desktop.getDesktop().open(new File(command));
//					}
//					// p.waitFor();
//				} catch (IOException ioe) {
//					
//					ioe.printStackTrace();
//				}
//				
//				System.out.println(command);
//				
			
			
			
//			DatePickerFrame.datePickerr();
//		
			 
			 
			 
//	-----------------------------------------------------------------------------------------
			
//				 final Thread thread = new Thread(new Runnable() {
//				     @Override
//				     public void run() {
//				    	 ChangeKodeInKDByExcelLog. ChangeKodeInKD();
//				     }
//				    });
//				    thread.start();	
			 
			 
//			 ---------------------------------------------------------------------------------
				        
//			 String MeasuringID = "109078";
//			 String MeasuringID = "108743";
				  
//					Measuring measur = MeasuringDAO.getValueMeasuringByID(Integer.parseInt(MeasuringID));
//					
//					String[] masiveData = Measuring_Results_Metods.getTextInfoPerson( measur);
//					 new Edit_Measuring_Results_Frame(new JFrame(),null, masiveData, measur);

//					Measuring_Results_Frame.viewInfoPanel();

					
			 
//			List< Instance > listInst = InstanceDAO.getValueInstanceByYear("2026");
//			System.out.println(listInst.size());
//			System.out.println(listInst.get(9).getTextInstance());
//			 String[] masive = listInst.get(9).getTextInstance().split("#!");
//			 for (String string : masive) {
//				System.out.println(string);
//			}
//			new InfoFrame_PersonStatus(masive, "");
			 
			 
			 
//			 System.out.println("2222222222222222222222222222222");
//			 String packageName = TestMainClass.class.getPackage().getName();
//			 System.out.println(packageName);

			
//			 System.out.println(packageName);
//			ActionIcone round = new ActionIcone();
//			 final Thread thread = new Thread(new Runnable() {
//			     @Override
//			     public void run() {
//			    	 System.out.println("33333333333333333333333333333333333333");
////			    	 String labelReferenceKontrolPersonByPeroid = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelReferenceKontrolPersonByPeroid");
//			    	new InstanceMenagement_Frame("test", round);
//			    	
//			    	round.StopWindow();
//			     }
//			    });
//			    thread.start();	
			
			
			 
		}
	}
		}

