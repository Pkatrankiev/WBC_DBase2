package Aplication;

import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Workplace;
import Reference_PersonMeasur.Reference_PersonMeasur_Frame;

public class TestMainClass {

	public static void main(String[] args) {
		if(ReadFileBGTextVariable.CreadMasiveFromReadFile()) {
			ActionIcone round = new ActionIcone();
			 final Thread thread = new Thread(new Runnable() {
			     @Override
			     public void run() {
			    	 
			    	 new Reference_PersonMeasur_Frame(round, "");
			    	     	
			     }
			    });
			    thread.start();
		
		
		
		
		}
	}

}
