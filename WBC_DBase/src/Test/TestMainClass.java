package Test;

import java.io.FileNotFoundException;
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
import Aplication.UpdateBDataFromExcellFiles;
import BasiClassDAO.ActualExcellFilesDAO;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.ActualExcellFiles;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Workplace;
import PersonManagement.PersonelManegementFrame;
import PersonReference.TextInAreaTextPanel;
import ReferenceMeasuringLab.ReferenceMeasuringLabFrame;
import Reference_PersonMeasur.Reference_PersonMeasur_Frame;

public class TestMainClass {

	public static void main(String[] args) throws ParseException {
		if(ReadFileBGTextVariable.CreadMasiveFromReadFile()) {

//			TestClasess.updateFromExcel();
			
//			TestClasess.CheckMontToBDate();
	
			KodeStatusDAO.deleteValueKodeStatus(168215);
		}
	}
		}

