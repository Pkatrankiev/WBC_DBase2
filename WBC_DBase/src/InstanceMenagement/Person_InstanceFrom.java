package InstanceMenagement;

import BasiClassDAO.PersonDAO;
import BasicClassAccessDbase.Person;

public class Person_InstanceFrom {

	
	public static String getInstanceTetxByPerson(Person Person) {
		String instanceTetx = "";
		instanceTetx += Person.getId_Person()+"#!";
		instanceTetx += Person.getEgn()+"#!";
		instanceTetx += Person.getFirstName()+"#!";
		instanceTetx += Person.getSecondName()+"#!";
		instanceTetx += Person.getLastName()+"#!";
		
		
		return instanceTetx;
	}
	
	public static String getInstanceTetxByLastPerson() {
		String instanceTetx = "";
		Person Person = PersonDAO.getLastPerson();
		instanceTetx += Person.getId_Person()+"#!";
		instanceTetx += Person.getEgn()+"#!";
		instanceTetx += Person.getFirstName()+"#!";
		instanceTetx += Person.getSecondName()+"#!";
		instanceTetx += Person.getLastName()+"#!";
		
		return instanceTetx;
	}
	
	
	public static String getInstanceTetxByIDPerson(int personStatusNew_ID) {
		
			String instanceTetx = "";
			Person Person = PersonDAO.getValuePersonByID(personStatusNew_ID);
			instanceTetx += Person.getId_Person()+"#!";
			instanceTetx += Person.getEgn()+"#!";
			instanceTetx += Person.getFirstName()+"#!";
			instanceTetx += Person.getSecondName()+"#!";
			instanceTetx += Person.getLastName()+"#!";
			
			return instanceTetx;
		}

	public static void create_InfoFrame_Person(Object[] selektInfo) {
	
		String[] masiveText = {"ID","ЕГН","Име","През име","Фамилия"};
		
		int masivecolumn = 5;
		
		if(selektInfo[2].equals("Edit")) {
			String strMAX1 = "";
			String strMAX2 = "";
			
			 String[] Allmas = ( (String)selektInfo[6]).split("!#!");
			 String[][] mas = new String [2][masivecolumn];
			 String[][] masive = new String [2][masivecolumn];
			  mas[0] = Allmas[0].split("#!");
			  mas[1] = Allmas[1].split("#!");
			 masive[0] = createMasiveInfoPerson(mas[0]);
			 masive[1] = createMasiveInfoPerson(mas[1]);
			int[] masiveDiferents = createMasiveDiferents(mas);
			 
			 strMAX1 = getMAX(masive[0]);
			 strMAX2 = getMAX(masive[1]);
			
				new InfoFrame_Instance(masive, masiveText, (String)selektInfo[2], strMAX1, strMAX2, (String)selektInfo[1], masiveDiferents);
			
		}else {
		
		String strMAX = "";
		
		 String[] mas = ((String)selektInfo[6]).split("#!");
		
		 String[] masive = createMasiveInfoPerson(mas);
		 
		 
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


	private static String[] createMasiveInfoPerson(String[] mas) {
		int masivecolumn = 5;
		String[] masive = new String[masivecolumn];
		masive[0] =  mas[0];
		masive[1] = mas[1];
		masive[2] = mas[2];
		masive[3] =  mas[3];
		masive[4] =  mas[4];
		
	return masive;
	}

	
		
	
	
	
	
}
