package Test;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.ReadFileBGTextVariable;
import Aplication.RemouveDublikateFromList;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.conectToAccessDB;
import CheckErrorDataInExcellFiles.CheckErrorDataInExcellFiles_Frame;
import CheckErrorDataInExcellFiles.CheckPersonStatus;
import MenuClasses.WBC_MainWindowFrame;
import PersonReference.PersonReferenceFrame;
import PersonReference_OID.OID_Metods;
import PersonReference_OID.OID_PersonReferenceFrame;
import PersonReference_OID.OID_Person_AEC;
import PersonReference_OID.OID_Person_AECDAO;
import PersonReference_OID.OID_Person_WBC;
import PersonReference_OID.OID_Person_WBCDAO;
import PersonReference_PersonStatus.PersonReference_PersonStatus_Frame;
import PersonReference_PersonStatus.PersonReference_PersonStatus_Methods;
import TestDatePicker.DatePickerController;
import TestDatePicker.DatePickerFrame;


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
	
			;
			TestClasess.generationHCode();
			
//			TestClasess.deleteKodeStatusByMisingPerson();
//			PersonStatusNewDAO.deleteValuePersonStatusNew(PersonStatusNewDAO.getValuePersonStatusNewByID(0));
//			TestClasess.deletePersonStatusNewInYere("2024");
//			TestClasess.PresonStatusNewWithYear();
			
//			TestClasess.CheckCurentDataInExcelFiles();
			
//			 new CheckErrorDataInExcellFiles_Frame ();
			 
			 
//			 TestClasess.reformat4Sheet();
			 
			 
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
		}
	}
		}

