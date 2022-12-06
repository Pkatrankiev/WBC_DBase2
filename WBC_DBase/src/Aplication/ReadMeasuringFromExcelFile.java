package Aplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.NuclideWBCDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.NuclideWBC;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.ResultsWBC;

import BasicClassAccessDbase.TypeMeasur;
import BasicClassAccessDbase.UsersWBC;

public class ReadMeasuringFromExcelFile {

	public static List<Measuring> generateListFromMeasuringFromExcelFile(String pathFile ) {
				
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		String EGN = "", lab;
		Date date;
		TypeMeasur tipeM;
		DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);
		TypeMeasur tipeM_R = TypeMeasurDAO.getValueTypeMeasurByID(1);
		double doze = 0;
		Sheet sheet = workbook.getSheetAt(1);
		Cell cell, cell1, cell2;
		List<Measuring> listMeasuring = new ArrayList<>();
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

					EGN = ReadExcelFileWBC.getStringfromCell(cell);
				Person person = PersonDAO.getValuePersonByEGN(EGN);
					if (person != null) {
						System.out.println("++++++++++++++++++++"+EGN);
						int k = 7;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						cell2 = sheet.getRow(row).getCell(k);
						while (ReadExcelFileWBC.CellNOEmpty(cell1) && ReadExcelFileWBC.CellNOEmpty(cell2)) {
						date = ReadExcelFileWBC.readCellToDate(cell1);
						lab = ReadExcelFileWBC.getStringfromCell(cell2).trim();
						int indexLab = Integer.parseInt(lab.substring(lab.length()-1));
						Laboratory labor = LaboratoryDAO.getValueLaboratoryByID(indexLab);
						k = k+17;
						System.out.println(k);
						tipeM = tipeM_R;
						cell = sheet.getRow(row).getCell(k);
						doze = 0.0;
							if(cell!=null) {				
						String type = cell.getCellType().toString();
						switch (type) {
						case "STRING": {
							
							String str = cell.getStringCellValue();
							System.out.println(str);
							if(str.contains("<")) {
								doze = 0.05;
							}else {
							
							if( TypeMeasurDAO.getValueTypeMeasurByObject("KodeType", str).size()>0) {
								doze = 0.0;
							}
							
							try {
								doze = Double.parseDouble(str);
							} catch (Exception e) {
								doze = -1;
							}
							
							if(doze==-1) {
								str = cell.getStringCellValue();
								tipeM = TypeMeasurDAO.getValueTypeMeasurByObject("KodeType", str).get(0);
								doze = 0.0;
							}
							}
						}
							break;
						case "NUMERIC": {
							doze = cell.getNumericCellValue();
						}
							break;
						}
						}
						
						Measuring meas = new Measuring(person, date, doze, dim, labor, userSet, tipeM, "");
						listMeasuring.add(meas);
								
						
						k++;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						cell2 = sheet.getRow(row).getCell(k);
						
						
						}
					}
				}
			}

		}
		return listMeasuring;
	}
		
	public static void ListMeasuringToBData(List<Measuring> MeasuringList) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");
		for (Measuring spPr : MeasuringList) {
			
			System.out.println( spPr.getPerson().getEgn()+ " - "
						+ sdfrmt.format(spPr.getDate()) + " - "
						 + " - "+spPr.getDoze()+" - "+spPr.getDoseDimension().getDimensionName()+
						 " - "+spPr.getLab().getLab()+" - "+spPr.getUser().getLastName()+" - "+spPr.getTypeMeasur().getKodeType());

		}
	}
	
	public static void setListMeasuringToBData(List<Measuring> MeasuringList) {
		for (Measuring spPr : MeasuringList) {
			MeasuringDAO.setObjectMeasuringToTable(spPr);
		}
	}


	public static List<ResultsWBC> generateListFromResultsWBCFromExcelFile(String pathFile ) {
		
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		String EGN = "", lab;;
		Date date;
		double[] nuclideValue = new double[16];
		double val, doze = 0;
		Sheet sheet = workbook.getSheetAt(1);
		Cell cell, cell1, cell2;
	
		List<ResultsWBC> listResultsWBC = new ArrayList<>();
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

					EGN = ReadExcelFileWBC.getStringfromCell(cell);
				Person person = PersonDAO.getValuePersonByEGN(EGN);
					if (person != null) {
						System.out.println("++++++++++++++++++++"+EGN);
						
						int k = 7;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						cell2 = sheet.getRow(row).getCell(k);
						while (ReadExcelFileWBC.CellNOEmpty(cell1) && ReadExcelFileWBC.CellNOEmpty(cell2)) {
							int countNuclide = 0;
						date = ReadExcelFileWBC.readCellToDate(cell1);
						lab = ReadExcelFileWBC.getStringfromCell(cell2).trim();
						int indexLab = Integer.parseInt(lab.substring(lab.length()-1));
						Laboratory labor = LaboratoryDAO.getValueLaboratoryByID(indexLab);
						k++;
						
						for (int i = 0; i < 16; i++) {
							cell = sheet.getRow(row).getCell(k);
							val = ReadExcelFileWBC.getDoublefromCell(cell);
							nuclideValue[i] = val;
							if(val>-1) {
								countNuclide++;
							}
							k++;
						}
						cell = sheet.getRow(row).getCell(k);
						doze = 0.0;
						if(cell!=null) {							
						String type = cell.getCellType().toString();
						switch (type) {
						case "STRING": {
							
							String str = cell.getStringCellValue();
							System.out.println(str);
							if(str.contains("<")) {
								doze = 0.05;
							}else {
							
							if( TypeMeasurDAO.getValueTypeMeasurByObject("KodeType", str).size()>0) {
								doze = 0.0;
							}
							
							try {
								doze = Double.parseDouble(str);
							} catch (Exception e) {
								doze = -1;
							}
							
							if(doze==-1) {
								str = cell.getStringCellValue();
								doze = 0.0;
							}
							}
						}
							break;
						case "NUMERIC": {
							doze = cell.getNumericCellValue();
						}
							break;
						}
						}
						
						Measuring meas = MeasuringDAO.getValueMeasuringByPersonDozeDate(person, date, doze, labor);
						
						
						if(countNuclide>0) {
							for (int i = 0; i < 16; i++) {
								if(nuclideValue[i]>0) {
									NuclideWBC nuclideWBC = NuclideWBCDAO.getValueNuclideWBCByID(i+1);
									ResultsWBC result = new ResultsWBC (meas, nuclideWBC, 0.0,  0.0,   nuclideValue[i], 0.0);
									if(countNuclide==1) {
										result.setNuclideDoze(meas.getDoze());
									}
									listResultsWBC.add(result);
								}
								
							}
						}
					
						k++;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						cell2 = sheet.getRow(row).getCell(k);
						
						
						}
					}
				}
			}

		}
		return listResultsWBC;
	}
	
	public static void setListResultsWBCToBData(List<ResultsWBC> listResultsWBC) {
		
		for (ResultsWBC res : listResultsWBC) {
					
			ResultsWBCDAO.setObjectResultsWBCToTable(res);
		}
	}
	
	public static void ListResultsWBCToBData(List<ResultsWBC> listResultsWBC) {
		
		for (ResultsWBC res : listResultsWBC) {
			
			System.out.println( res.getMeasuring().getMeasuring_ID()+ " - "
						+res.getMeasuring().getPerson().getEgn()+ " - "
						+res.getMeasuring().getDoze()+ " - "
						+ res.getNuclideWBC().getSymbol_nuclide()+ " - "
						+res.getActivity()+" - "
						+res.getPostaplenie()+" - "
						+res.getGgp()+" - "
						+res.getNuclideDoze());
			
			
		}
	}
	
}
