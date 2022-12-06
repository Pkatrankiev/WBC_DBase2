package Aplication;

import java.util.List;


import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.Spisak_Prilogenia;

public class AplicationMetods {
	static String pathFile = "D:\\EXTERNAL_2020_1.xls"; // пътя и името до ексел файла
	static String firmName = "Външни организации"; // името на фирмата за обекта списък-приложения "АЕЦ Козлодуй" "Външни организации"
	static String year = "2020"; // годината за списък-приложения

	public static void readInfoFromGodExcelFile() {

		String key ="";
//		 key = "Person";
//		 key = "Spisak_Prilogenia";
//		 key = "PersonStatus";
//		 key = "KodeStatus";
//		 key = "Measuring";
//		 key = "ResultsWBC";
		
		switch (key) {
		case "Person": {
			// read and set Person
			List<Person> listPerson = ReadPersonFromExcelFile.updatePersonFromExcelFile(pathFile);
			System.out.println("++++++++++++++++++++ "+listPerson.size());
//			ReadPersonFromExcelFile.setToDBaseListPerson(listPerson);
		}
		break;
		
		case "Spisak_Prilogenia": {
			// read and set Spisak_Prilogenia
			List<Spisak_Prilogenia> Spisak_PrilogeniaList = ReadSpisak_PrilogeniaFromExcelFile
					.getSpisak_Prilogenia_ListFromExcelFile(pathFile, firmName, year);
			ReadSpisak_PrilogeniaFromExcelFile.ListSpisak_PrilogeniaToBData(Spisak_PrilogeniaList);
//			ReadSpisak_PrilogeniaFromExcelFile.setListSpisak_PrilogeniaToBData(Spisak_PrilogeniaList);
		}
		break;
		
		case "PersonStatus": {
			// read and set PersonStatus
			List<PersonStatus> list = ReadPersonStatusFromExcelFile.getListPersonStatusFromExcelFile(pathFile, firmName,
					year);
			ReadPersonStatusFromExcelFile.ListPersonStatus(list);
			
//			ReadPersonStatusFromExcelFile.setToBDateListPersonStatus(list);
		}
		break;
		
		case "KodeStatus": {
			// read and set KodeStatus
			List<KodeStatus> listKodeStatus = ReadKodeStatusFromExcelFile.getListPersonStatusFromExcelFile(pathFile,
					firmName, year);
			ReadKodeStatusFromExcelFile.ListKodeStatus(listKodeStatus);
//			ReadKodeStatusFromExcelFile.setToDBaseListKodeStatus(listKodeStatus);

		}
		break;
		case "Measuring": {
			// read and set Measuring
			List<Measuring>  listMeasuring = ReadMeasuringFromExcelFile.generateListFromMeasuringFromExcelFile(pathFile);
			ReadMeasuringFromExcelFile.ListMeasuringToBData(listMeasuring);
//			ReadMeasuringFromExcelFile.setListMeasuringToBData(listMeasuring);


		}
		break;
		case "ResultsWBC": {
			// read and set ResultsWBC
			List<ResultsWBC>  listResultsWBC = ReadMeasuringFromExcelFile.generateListFromResultsWBCFromExcelFile(pathFile);
			ReadMeasuringFromExcelFile.ListResultsWBCToBData(listResultsWBC);
			ReadMeasuringFromExcelFile.setListResultsWBCToBData(listResultsWBC);

		}
		break;
		
		

		}
	}

}
