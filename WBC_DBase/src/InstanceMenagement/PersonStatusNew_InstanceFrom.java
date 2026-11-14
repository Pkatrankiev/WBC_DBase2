package InstanceMenagement;

import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;


public class PersonStatusNew_InstanceFrom {

	public static String getInstanceTetxByPersonStatusNew(PersonStatusNew PersonStatusNew) {
		String instanceTetx = "";
		instanceTetx += PersonStatusNew.getPersonStatusNew_ID()+"#!";
		instanceTetx += PersonStatusNew.getPerson().getId_Person()+"#!";
		instanceTetx += PersonStatusNew.getWorkplace().getId_Workplace()+"#!";
		instanceTetx += PersonStatusNew.getFormulyarName()+"#!";
		instanceTetx += PersonStatusNewDAO.convertDate(PersonStatusNew.getStartDate())+"#!";
		instanceTetx += PersonStatusNewDAO.convertDate(PersonStatusNew.getEndDate())+"#!";
		instanceTetx += PersonStatusNew.getYear()+"#!";
		instanceTetx += PersonStatusNew.getUserWBC().getId_Users()+"#!";
		instanceTetx += PersonStatusNewDAO.convertDate(PersonStatusNew.getDateSet())+"#!";
		String zab = PersonStatusNew.getZabelejka();
		if(zab.isEmpty()) {
		instanceTetx += " #!";
		}else {
			instanceTetx += PersonStatusNew.getZabelejka()+"#!";
		}
		
		return instanceTetx;
	}
	
	public static String getInstanceTetxByLastPersonStatusNew() {
		String instanceTetx = "";
		PersonStatusNew PersonStatusNew = PersonStatusNewDAO.getLastValuePersonStatusNew();
		instanceTetx += PersonStatusNew.getPersonStatusNew_ID()+"#!";
		instanceTetx += PersonStatusNew.getPerson().getId_Person()+"#!";
		instanceTetx += PersonStatusNew.getWorkplace().getId_Workplace()+"#!";
		instanceTetx += PersonStatusNew.getFormulyarName()+"#!";
		instanceTetx += PersonStatusNewDAO.convertDate(PersonStatusNew.getStartDate())+"#!";
		instanceTetx += PersonStatusNewDAO.convertDate(PersonStatusNew.getEndDate())+"#!";
		instanceTetx += PersonStatusNew.getYear()+"#!";
		instanceTetx += PersonStatusNew.getUserWBC().getId_Users()+"#!";
		instanceTetx += PersonStatusNewDAO.convertDate(PersonStatusNew.getDateSet())+"#!";
		String zab = PersonStatusNew.getZabelejka();
		if(zab.isEmpty()) {
		instanceTetx += " #!";
		}else {
			instanceTetx += PersonStatusNew.getZabelejka()+"#!";
		}
		
		return instanceTetx;
	}
	
	
	public static String getInstanceTetxByIDPersonStatusNew(int personStatusNew_ID) {
		
			String instanceTetx = "";
			PersonStatusNew PersonStatusNew = PersonStatusNewDAO.getValuePersonStatusNewByID(personStatusNew_ID);
			instanceTetx += personStatusNew_ID+"#!";
			instanceTetx += PersonStatusNew.getPerson().getId_Person()+"#!";
			instanceTetx += PersonStatusNew.getWorkplace().getId_Workplace()+"#!";
			instanceTetx += PersonStatusNew.getFormulyarName()+"#!";
			instanceTetx += PersonStatusNewDAO.convertDate(PersonStatusNew.getStartDate())+"#!";
			instanceTetx += PersonStatusNewDAO.convertDate(PersonStatusNew.getEndDate())+"#!";
			instanceTetx += PersonStatusNew.getYear()+"#!";
			instanceTetx += PersonStatusNew.getUserWBC().getId_Users()+"#!";
			instanceTetx += PersonStatusNewDAO.convertDate(PersonStatusNew.getDateSet())+"#!";
			String zab = PersonStatusNew.getZabelejka();
			if(zab.isEmpty()) {
			instanceTetx += " #!";
			}else {
				instanceTetx += PersonStatusNew.getZabelejka()+"#!";
			}
			
			return instanceTetx;
		}

	public static void create_InfoFrame_PersonStatus(Object[] selektInfo) {
	
		String[] masiveText = {"ID","Служител","Звено","Код на формуляр","Начална дата","Крайна дата","Година","Оператор",
				"Дата на Въвеждане","Коментар"};
		
		if(selektInfo[2].equals("Edit")) {
			String strMAX1 = "";
			String strMAX2 = "";
			
			 String[] Allmas = ( (String) selektInfo[6]).split("!#!");
			 String[][] mas = new String [2][10];
			 String[][] masive = new String [2][10];
			  mas[0] = Allmas[0].split("#!");
			  mas[1] = Allmas[1].split("#!");
			 masive[0] = createMasiveInfoPersonStatus(mas[0]);
			 masive[1] = createMasiveInfoPersonStatus(mas[1]);
			int[] masiveDiferents = createMasiveDiferents(mas);
			 
			 strMAX1 = getMAX(masive[0]);
			 strMAX2 = getMAX(masive[1]);
			
				new InfoFrame_Instance(masive, masiveText, (String)selektInfo[2], strMAX1, strMAX2, (String)selektInfo[1], masiveDiferents);
			
		}else {
		
		String strMAX = "";
		
		 String[] mas = ((String)selektInfo[6]).split("#!");
		
		 String[] masive = createMasiveInfoPersonStatus(mas);
		 
		 
		 strMAX = getMAX(masive);
		
			new InfoFrame_Instance(masive, masiveText, (String)selektInfo[2], (String)selektInfo[1], strMAX);
		}
	}
	
	private static int[] createMasiveDiferents(String[][] mas) {
		int[] masDif = new int[mas[0].length];
		for (int i = 0; i < mas[0].length; i++) {
			masDif[i] = 0;
			if(!mas[0][i].equals(mas[1][i])){
				masDif[i] = 1;
			}
			
		}
		return masDif;
	}





	private static String getMAX( String[] masive) {
		int max =0;
		String strMAX = "";
		for (Object str : masive) {
			System.out.println(str);
			if(max <str.toString().length()) {
				
				max = str.toString().length();
				System.out.println(str+" - "+max);
				strMAX = str.toString();
			}
		}
		return strMAX;
	}


	private static String[] createMasiveInfoPersonStatus(String[] mas) {
		
		String[] masive = new String[10];
		masive[0] =  mas[0];
		 Person pers = PersonDAO.getValuePersonByID(Integer.parseInt(mas[1]));
		 masive[1] = pers.getFirstName()+" "+pers.getSecondName()+" "+pers.getLastName();
		 Workplace work = WorkplaceDAO.getValueWorkplaceByID(Integer.parseInt(mas[2]));
		 masive[2] =  work.getOtdel();
		 masive[3] =  mas[3];
		 masive[4] =  mas[4];
		 masive[5] =  mas[5];
		 masive[6] =  mas[6];
		 UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(Integer.parseInt(mas[7]));
		 masive[7] =  user.getLastName();
		 masive[8] =  mas[8];
		 masive[9] =  mas[9];
	return masive;
	}

	
	
}
