package Test;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadMeasuringFromExcelFile;
import Aplication.ReadResultsWBCFromExcelFile;
import Aplication.ResourceLoader;
import BasiClassDAO.ActualExcellFilesDAO;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.ActualExcellFiles;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.conectToAccessDB;
import PersonManagement.PersonelManegementFrame;
import PersonReference.TextInAreaTextPanel;
import ReferenceMeasuringLab.ReferenceMeasuringLabFrame;
import Reference_PersonMeasur.Reference_PersonMeasur_Frame;
import UpdateDBaseFromExcelFiles.UpdateBDataFromExcellFiles;

public class TestMainClass {

	public static void main(String[] args) throws ParseException {
		if(ReadFileBGTextVariable.CreadMasiveFromReadFile()) {
//			String pathOld = "d:\\!!000\\PERSONNEL-09-00.xls";
//			String pathNew = "d:\\!!000\\PERSONNEL-09-10.xls";
//			TestClasess.checkTwoExcelFiles(pathOld, pathNew);
	
			;
//			TestClasess.PersonStatusNewCheckDublicate();
			
//			TestClasess.deleteKodeStatusByMisingPerson();
//			PersonStatusNewDAO.deleteValuePersonStatusNew(PersonStatusNewDAO.getValuePersonStatusNewByID(0));
//			TestClasess.deleteDublikatePersonStatusWithWorkpliceInYere("2023");
//			TestClasess.PresonStatusNewWithYear();
			TestClasess.CheckCurentDataInExcelFile();
		}
	}
		}

