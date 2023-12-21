package Aplication;

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

public class ReadResultsWBCFromExcelFile {

	public static List<ResultsWBC> generateListFromResultsWBCFromExcelFile(String pathFile , ActionIcone round,  String textIcon, List<Integer> listDiferentRow) {
		
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<ResultsWBC> listResultsWBC = new ArrayList<>();
		if(workbook.getNumberOfSheets()>2) {
			listResultsWBC = generateListFromResultsWBCFromBigExcelFile(workbook, round, textIcon, listDiferentRow);
				
		}else {
			listResultsWBC = generateListFromResultsWBCFromSmalExcelFile(workbook);	
		}
		return listResultsWBC;
	}
		
	
	
	
	private static List<ResultsWBC> generateListFromResultsWBCFromSmalExcelFile(Workbook workbook) {
		DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);	
		TypeMeasur tipeM_R = TypeMeasurDAO.getValueTypeMeasurByID(1);
		String  lab;;
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

					Person person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
				if (person != null) {
										
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






	public static List<ResultsWBC> generateListFromResultsWBCFromBigExcelFile(Workbook workbook , ActionIcone round,  String textIcon, List<Integer> listDiferentRow) {
		DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);	
		TypeMeasur tipeM_R = TypeMeasurDAO.getValueTypeMeasurByID(1);
		String lab;;
		Date date;
		double[] nuclideValue = new double[16];
		String[] simbolNuclide = new String[16];
		double val;
		Sheet sheet = workbook.getSheetAt(1);
		Cell cell, cell1, cell2;
	
		List<ResultsWBC> listResultsWBC = new ArrayList<>();
		
		int StartRow = 0;
		int endRow = 0;
		
		if(listDiferentRow != null) {
			StartRow = 0;
			endRow = listDiferentRow.size();
		}else {
			StartRow = 5;
			endRow = sheet.getLastRowNum();
		}
		int row = 0;
		for (int index = StartRow; index < endRow ; index += 1) {

			if(listDiferentRow != null) {
				row = listDiferentRow.get(index);
			}else {
				row  = index;
			}

			
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
				Person person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);
					if (person != null) {
						
						
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
							simbolNuclide[i] = sheet.getRow(3).getCell(k).getStringCellValue();
							if(val>-1) {
								countNuclide++;
							}
							k++;
						}
						
						if(countNuclide>0) {
//							System.out.println("+++++++++++++++++countNuclide "+countNuclide);
//							Measuring measur = ReadMeasuringFromExcelFile.createMeasur( person, dim, userSet, sheet,row, k, tipeM_R, date, lab);
//							System.out.println("+++++++++++++++++meas "+measur.getDoze());
							
							String excelPosition = "Excel-"+person.getEgn()+"/"+k;
							int indexLab = Integer.parseInt(lab.substring(lab.length()-1));
							Laboratory labor = LaboratoryDAO.getValueLaboratoryByID(indexLab);
							Measuring meas = MeasuringDAO.getValueMeasuringFromExcelByPerson_Date_ExcelPozition_Lab( person,  date,  labor, excelPosition);
//							 MeasuringDAO.getValueMeasuringByPersonDozeDate(measur.getPerson(), measur.getDate(), measur.getDoze(), measur.getLab());
//							System.out.println("+++++++++++++++++meas "+meas.getMeasuring_ID());
							for (int i = 0; i < 16; i++) {
								if(nuclideValue[i]>0) {
									NuclideWBC nuclideWBC = NuclideWBCDAO.getValueNuclideWBCBySymbol(simbolNuclide[i]);
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
			ActionIcone.roundWithText(round, textIcon, "Read", index, endRow);
		}
		return listResultsWBC;
	}
	
	public static void setListResultsWBCToBData(List<ResultsWBC> listResultsWBC, ActionIcone round,  String textIcon) {
		int k=0;
		int l=listResultsWBC.size();
		for (ResultsWBC res : listResultsWBC) {
					
			ResultsWBCDAO.setObjectResultsWBCToTable(res);
			ActionIcone.roundWithText(round, textIcon, "Save", k, l);
			k++;
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
