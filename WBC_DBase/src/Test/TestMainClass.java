package Test;

import java.io.FileNotFoundException;
import java.util.List;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Workplace;
import PersonManagement.PersonelManegementFrame;
import PersonReference.TextInAreaTextPanel;
import ReferenceMeasuringLab.ReferenceMeasuringLabFrame;
import Reference_PersonMeasur.Reference_PersonMeasur_Frame;

public class TestMainClass {

	public static void main(String[] args) {
		if(ReadFileBGTextVariable.CreadMasiveFromReadFile()) {

			
			TestClasess.RemoveWorkplace_54_101_FromPersonStatus(54,"7703211860");
		
				
		}
	}

}
