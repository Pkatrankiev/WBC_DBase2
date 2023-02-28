package Aplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.NuclideWBCDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.NuclideWBC;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.ResultsWBC;
import BasicClassAccessDbase.TypeMeasur;
import BasicClassAccessDbase.UsersWBC;

public class ReadResultsWBCFromExcelFile {

	public static List<ResultsWBC> generateListFromResultsWBCFromExcelFile(String pathFile ) {
		
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<ResultsWBC> listResultsWBC = new ArrayList<>();
		if(workbook.getNumberOfSheets()>2) {
			listResultsWBC = generateListFromResultsWBCFromBigExcelFile(workbook);
				
		}else {
			listResultsWBC = generateListFromResultsWBCFromSmalExcelFile(workbook);	
		}
		return listResultsWBC;
	}
		
	
	
	
	private static List<ResultsWBC> generateListFromResultsWBCFromSmalExcelFile(Workbook workbook) {
		DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);	
		TypeMeasur tipeM_R = TypeMeasurDAO.getValueTypeMeasurByID(1);
		String EGN = "", lab;;
		Date date;
		double[] nuclideValue = new double[16];
		double val;
		Sheet sheet = workbook.getSheetAt(1);
		Cell cell, cell1;
	
		List<ResultsWBC> listResultsWBC = new ArrayList<>();
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					if(EGN.contains("*")) EGN = EGN.substring(0, EGN.length()-1);
				Person person = PersonDAO.getValuePersonByEGN(EGN);
				if (person != null) {
					System.out.println("++++++++++++++++++++"+EGN);
					
					int k = 7;
					cell1 = sheet.getRow(row).getCell(k);
					k++;
					
					while (ReadExcelFileWBC.CellNOEmpty(cell1)) {
						int countNuclide = 0;
					date = ReadExcelFileWBC.readCellToDate(cell1);
					lab = "wbc-1";
											
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
					Measuring measur = ReadMeasuringFromExcelFile.createMeasur( person, dim, userSet, sheet,row, k, tipeM_R, date, lab);
					
					Measuring meas = MeasuringDAO.getValueMeasuringByPersonDozeDate(measur.getPerson(), measur.getDate(), measur.getDoze(), measur.getLab());
					
					
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
				
					if(k>253) {
						k=6;
						sheet = workbook.getSheetAt(2);
					}
					
					
					k++;
					cell1 = sheet.getRow(row).getCell(k);
					k++;
					
					
					
					}
				}
				}
			}

		}
		return listResultsWBC;
	}

	public static List<ResultsWBC> generateListFromResultsWBCFromBigExcelFile(Workbook workbook ) {
		DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);	
		TypeMeasur tipeM_R = TypeMeasurDAO.getValueTypeMeasurByID(1);
		String EGN = "", lab;;
		Date date;
		double[] nuclideValue = new double[16];
		double val;
		Sheet sheet = workbook.getSheetAt(1);
		Cell cell, cell1, cell2;
	
		List<ResultsWBC> listResultsWBC = new ArrayList<>();
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					if(EGN.contains("*")) EGN = EGN.substring(0, EGN.length()-1);
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
						Measuring measur = ReadMeasuringFromExcelFile.createMeasur( person, dim, userSet, sheet,row, k, tipeM_R, date, lab);
						
						Measuring meas = MeasuringDAO.getValueMeasuringByPersonDozeDate(measur.getPerson(), measur.getDate(), measur.getDoze(), measur.getLab());
						
						
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
					
						if(k>253) {
							k=6;
							sheet = workbook.getSheetAt(2);
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
