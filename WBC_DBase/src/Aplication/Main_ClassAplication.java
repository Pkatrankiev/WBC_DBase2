package Aplication;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.conectToAccessDB;
import MenuClasses.MainWindowFrame;
import PersonManagement.PersonelManegementMethods;
import SearchFreeKode.SearchFreeKodeFrame;


public class Main_ClassAplication {
 
public static void main(String[] args) {
	
	if(ReadFileBGTextVariable.CreadMasiveFromReadFile()) {
		UpdateBDataFromExcellFiles.updataFromGodExcelFile();
		new MainWindowFrame();
		GeneralMethods.cerateDestinationDir();
	
		}

}

}
