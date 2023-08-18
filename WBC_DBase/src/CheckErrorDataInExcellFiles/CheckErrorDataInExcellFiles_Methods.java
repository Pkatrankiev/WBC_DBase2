package CheckErrorDataInExcellFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.GeneralMethods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;

public class CheckErrorDataInExcellFiles_Methods {

	private static JTextArea textArea = CheckErrorDataInExcellFiles_Frame.getTextArea();
	private static JButton btn_Search = CheckErrorDataInExcellFiles_Frame.getBtn_Search();
	private static JButton btn_SearchAllColumn = CheckErrorDataInExcellFiles_Frame.getBtn_Search_1();
	
	static void ActionListener_Btn_SearchError(JPanel panel_AllSaerch) {

		btn_Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				GeneralMethods.setWaitCursor(panel_AllSaerch);
				
			String textForArea = 	CheckForCorrectionMeasuringInSheet0AndInMonth();
			
			if(textForArea.isEmpty()) {
				textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
			
			}else {
				textArea.setText(textForArea);
			
			}
		
			
			
			GeneralMethods.setDefaultCursor(panel_AllSaerch);
			}

		});

	}
		
	static void ActionListener_Btn_SearchAllColumn(JPanel panel_AllSaerch) {

		btn_SearchAllColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				GeneralMethods.setWaitCursor(panel_AllSaerch);
				
			String textForArea = CheckCorrectionAllRowInSheets();
			
			if(textForArea.isEmpty()) {
				textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
			
			}else {
				textArea.setText(textForArea);
			
			}
		
			
			
			GeneralMethods.setDefaultCursor(panel_AllSaerch);
			}

		});

	}
	

	public static String CheckForCorrectionMeasuringInSheet0AndInMonth() {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		String infotext ="";
		
		String[][][] masiveStrMonth = null ;
		String[][][] masiveStrMonthLab = null ;
		String[][][] masuveMeasur = null ;
		String[][][] masuveMeasur2 = null ;
		String[][][] masiveDoze = null ;

		String filePath[] = { ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig"),
				ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig") };

		String filePathMont[] = { ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthPersonel_orig"),
				ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthExternal_orig") };

		Workbook workbookMont[] = { ReadExcelFileWBC.openExcelFile(filePathMont[0]),
				ReadExcelFileWBC.openExcelFile(filePathMont[1]) };

		Workbook workbook[] = { ReadExcelFileWBC.openExcelFile(filePath[0]),
				ReadExcelFileWBC.openExcelFile(filePath[1]) };

		String fileName[] = { "Personel", "External" };

		for (int i = 0; i < 2; i++) {

			 masiveStrMonth = MasiveFromMonth(sdfrmt, workbookMont[i]);
			 masiveStrMonthLab = MasiveFromMonthCheckLab(sdfrmt, workbookMont[i]);
			 masuveMeasur = masiveMeasur(sdfrmt, workbook[i]);
			 masuveMeasur2 =  masiveMeasur(sdfrmt, workbook[i]);
			 masiveDoze = masiveDoze(workbook[i]);
				
			
			 masuveMeasur = generateCheckMasiveMeasur(masuveMeasur, masiveDoze);
			
			for (int mont = 0; mont < 12; mont++) {
				
				for (int m = 0; m < masiveStrMonth[mont].length; m++) {
					if (masiveStrMonth[mont][m][0] != null) {
						
						for (int k = 0; k < masuveMeasur2[mont].length; k++) {
							if (masuveMeasur2[mont][k][0] != null) {
								if (masiveStrMonth[mont][m][0] != null
										&& masiveStrMonth[mont][m][0].equals(masuveMeasur2[mont][k][0])
										&& masiveStrMonth[mont][m][1].equals(masuveMeasur2[mont][k][1])
										&& masiveStrMonth[mont][m][2].equals(masuveMeasur2[mont][k][2])
										&& masiveStrMonth[mont][m][3].equals(masuveMeasur2[mont][k][3])
										) {
								
									masiveStrMonth[mont][m][0] = null;
									masuveMeasur2[mont][k][0] = null;
									
							
								}
							}
						}

					}
				}
			}
				for (int mont = 0; mont < 12; mont++) {			
			
			for (int m = 0; m < masiveStrMonthLab[mont].length; m++) {
				if (masiveStrMonthLab[mont][m][0] != null) {
							if ( masiveStrMonthLab[mont][m][2].contains(masiveStrMonthLab[mont][m][3])) {
							masiveStrMonthLab[mont][m][0] = null;
							}
						}
					}
				
			}
			infotext += GenerateInfoString(fileName[i],  masiveStrMonth, masiveStrMonthLab, masuveMeasur, masuveMeasur2, masiveDoze);	
			System.out.println("*************************************"+infotext);
			
		}
		
		return infotext;
	}
	
	private static String[][][] generateCheckMasiveMeasur(String[][][] masuveMeasur, String[][][] masiveDoze) {
			for (int mont = 0; mont < 12; mont++) {
				
				
				for (int d = 0; d < masiveDoze[mont].length; d++) {
					if (masiveDoze[mont][d][0] != null) {
				double sumDoze = 0.0, doubDoze= 0.0;
				String sumStrDoze = "", strDoze = "";
				boolean isString = false, fl2 = false;
				List<Integer> list = new ArrayList<>();
						for (int k = 0; k < masuveMeasur[mont].length; k++) {
							if (masuveMeasur[mont][k][0] != null) {
								if (masuveMeasur[mont][k][0].equals(masiveDoze[mont][d][0])){
									list.add(k);
									try {
										sumDoze =+ Double.parseDouble(masuveMeasur[mont][k][1]);
										
									} catch (Exception e) {
										sumStrDoze = masuveMeasur[mont][k][1];
									} 
									
								}
							}
						}
						
						try {
							doubDoze =+ Double.parseDouble(masiveDoze[mont][d][1]);
						} catch (Exception e) {
							strDoze = masiveDoze[mont][d][1];
							isString=true;
						} 
								if(isString) {
									if(sumStrDoze.equals(strDoze)) fl2=true;
								}else {
									if(sumDoze==doubDoze) fl2=true;	
								}
											 
								if(fl2) {
									masiveDoze[mont][d][0] = null;
									for (int index : list) {
										masuveMeasur[mont][index][0] = null;	
									}
									
								}
							
									
							}
						}
			}
			return masuveMeasur;
			
			
			
		}

		private static String GenerateInfoString(String fileName, String[][][] masiveStrMonth,
				String[][][] masiveStrMonthLab, String[][][] masuveMeasur, String[][][] masuveMeasur2,
				String[][][] masiveDoze) {
			String ImaGoV =ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_ImaGoV");
			String NoGoNiamaV =ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_NoGoNiamaV");
			String IzmerSICH =ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_IzmerSICH");
			String Dozi =ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_DOZI");
			String ZaMesec =ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_ZaMesec");
			String IzmerVSICH =ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_IzmerVSICH");
			String MarkKato =ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_MarkKato");
			String ErrorLab =ReadFileBGTextVariable.getGlobalTextVariableMap().get("checkCorrectinDataInExcell_ErrorLab");		
			
			String infoText = "";
			String mesec = "";
						
			for (int m = 0; m < 12; m++) {
				String infoTextM = "";
				mesec ="\n"+"\n"+ZaMesec+" "+ (m+1);
				
				
				
				boolean fl =true;
				for (int j = 0; j < masiveStrMonthLab[m].length; j++) {
					if (masiveStrMonthLab[m][j][0] != null) {
						if(fl) {
							infoTextM +="\n"+ ErrorLab+" Month-"+ fileName+"\n" ;
						fl = false;
						}
						infoTextM += (j+1) + " - " +IzmerVSICH+" "+ masiveStrMonthLab[m][j][3] + " " +MarkKato+" "+ masiveStrMonthLab[m][j][2]+"\n" ;
				}
			}
				
				
			 fl =true;
				for (int j = 0; j < masiveStrMonth[m].length; j++) {
					if (masiveStrMonth[m][j][0] != null) {
						if(fl) {
							infoTextM +="\n"+ImaGoV+" Month-"+ fileName+" "+NoGoNiamaV+" "+ fileName+" "+IzmerSICH+"\n" ;
						fl = false;
						}
						infoTextM += (j+1) + " - " + masiveStrMonth[m][j][0] + " " + masiveStrMonth[m][j][1] + " "
								+ masiveStrMonth[m][j][2] + " " + masiveStrMonth[m][j][3]+"\n" ;
				}
			}
				
				
			 fl =true;
					
					for (int j = 0; j < masuveMeasur2[m].length; j++) {
						if (masuveMeasur2[m][j][0] != null) {
							if(fl) {
								infoTextM +="\n"+ImaGoV+" "+ fileName+" "+IzmerSICH+" " + NoGoNiamaV+ " Month-"+ fileName+"\n" ;
								fl = false;
								}
							infoTextM += (j+1) + " - " + masuveMeasur2[m][j][0] + " " + masuveMeasur2[m][j][1] + " "
									+ masuveMeasur2[m][j][2] + " " + masuveMeasur2[m][j][3]+"\n" ;
						}
									}
				
					 fl =true;
				for (int j = 0; j < masuveMeasur[m].length; j++) {
					if (masuveMeasur[m][j][0] != null) {
						if(fl) {
							infoTextM +="\n"+ImaGoV+" "+ fileName+" "+IzmerSICH+" "+ NoGoNiamaV+" "+Dozi+"\n" ;
							fl = false;
							}
						infoTextM += (j+1) + " - " + masuveMeasur[m][j][0] + " " + masuveMeasur[m][j][1] + " "
								+ masuveMeasur[m][j][2] + " " + masuveMeasur[m][j][3]+"\n" ;
					}
			}

				 fl =true;
			
				for (int j = 0; j < masiveDoze[m].length; j++) {
					if (masiveDoze[m][j][0] != null) {
						if(fl) {
							infoTextM +="\n"+ImaGoV+" "+ fileName+" "+Dozi+" "+ NoGoNiamaV+" "+IzmerSICH+"\n" ;
							fl = false;
							}
						infoTextM += (j+1) + " - " + masiveDoze[m][j][0] + " " + masiveDoze[m][j][1]+"\n" ;
					}
			}
				if(!infoTextM.isEmpty()) {
					infoText += mesec+"\n"+infoTextM;
				}
			
			}
			return infoText;
		}

		
		private static String[][][] masiveDoze(Workbook workbook) {
			String EGN = "", doze = "";

			Cell cell_EGN, cell_Doze;

			Sheet sheet0 = workbook.getSheetAt(0);

			String[][][] masiveStr = new String[12][500][2];
			int[] maxindexMonth = new int[12];
			for (int i = 0; i < maxindexMonth.length; i++) {
				maxindexMonth[i] = 0;
			}
			for (int row = 5; row <= sheet0.getLastRowNum(); row++) {

				if (sheet0.getRow(row) != null) {

					cell_EGN = sheet0.getRow(row).getCell(5);

					if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {

						EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell_EGN);
						for (int mont = 0; mont < 12; mont++) {
							cell_Doze = sheet0.getRow(row).getCell(mont + 77);
							doze = ReadExcelFileWBC.getStringEGNfromCell(cell_Doze);
//							System.out.println(mont + " " + maxindexMonth[mont] + " " + EGN);
							if (!doze.isEmpty()) {
								masiveStr[mont][maxindexMonth[mont]][0] = EGN;
								masiveStr[mont][maxindexMonth[mont]][1] = doze;
								maxindexMonth[mont]++;
							}
						}
					}
				}
			}

			

			return masiveStr;
		}

		@SuppressWarnings("deprecation")
		private static String[][][] masiveMeasur(SimpleDateFormat sdfrmt, Workbook workbook) {
			Date date;
			int mont;
			String EGN = "", strDate = "", doze = "", lab = "";

			Cell cell_EGN, cell_date, cell_Doze, cell_Lab;

			String[][][] masiveStr0 = new String[12][500][4];
			Sheet sheet1 = workbook.getSheetAt(1);
			int[] maxindexMonth = new int[12];
			for (int i = 0; i < maxindexMonth.length; i++) {
				maxindexMonth[i] = 0;
			}
			for (int row0 = 5; row0 <= sheet1.getLastRowNum(); row0++) {

				if (sheet1.getRow(row0) != null) {

					cell_EGN = sheet1.getRow(row0).getCell(5);

					if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {

						EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell_EGN);
						int k = 7;
						cell_date = sheet1.getRow(row0).getCell(k);
						k++;
						cell_Lab = sheet1.getRow(row0).getCell(k);
						while (ReadExcelFileWBC.CellNOEmpty(cell_date) && ReadExcelFileWBC.CellNOEmpty(cell_Lab)) {

							
							date = ReadExcelFileWBC.readCellToDate(cell_date);
							strDate = sdfrmt.format(date);
							
							lab = ReadExcelFileWBC.getStringEGNfromCell(cell_Lab);

							mont = date.getMonth();

							k = k + 17;
							cell_Doze = sheet1.getRow(row0).getCell(k);
							doze = ReadExcelFileWBC.getStringEGNfromCell(cell_Doze);

							masiveStr0[mont][maxindexMonth[mont]][0] = EGN;
							masiveStr0[mont][maxindexMonth[mont]][1] = doze;
							masiveStr0[mont][maxindexMonth[mont]][2] = strDate;
							masiveStr0[mont][maxindexMonth[mont]][3] = lab;

							maxindexMonth[mont]++;

							if (k > 253) {
								k = 6;
								sheet1 = workbook.getSheetAt(2);
							}

							k++;
							cell_date = sheet1.getRow(row0).getCell(k);
							k++;
							cell_Lab = sheet1.getRow(row0).getCell(k);

						}
					}

				}

			}

		

			return masiveStr0;
		}

		private static String[][][] MasiveFromMonth(SimpleDateFormat sdfrmt, Workbook workbookMont) {
			Date date;
			String EGN;
			String strDate;
			String doze;
			String lab;
			Cell cell_EGN;
			Cell cell_date;
			Cell cell_Doze;
			Cell cell_Lab;

			String[][][] masiveStrMonth = new String[12][500][4];

			for (int m = 0; m < 12; m++) {

				Sheet sheetMont = workbookMont.getSheetAt(m);
				int row = 0;
				for (int l = 6; l <= sheetMont.getLastRowNum(); l++) {

					if (sheetMont.getRow(l) != null) {
						cell_EGN = sheetMont.getRow(l).getCell(3);
						if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {
							EGN = ReadExcelFileWBC.getStringEGNfromCell(cell_EGN);
							cell_Doze = sheetMont.getRow(l).getCell(5);
							doze = ReadExcelFileWBC.getStringEGNfromCell(cell_Doze);

							cell_date = sheetMont.getRow(l).getCell(6);
							date = ReadExcelFileWBC.readCellToDate(cell_date);
							strDate = sdfrmt.format(date);

							cell_Lab = sheetMont.getRow(l).getCell(8);
							lab = ReadExcelFileWBC.getStringfromCell(cell_Lab);

							masiveStrMonth[m][row][0] = EGN;
							masiveStrMonth[m][row][1] = doze;
							masiveStrMonth[m][row][2] = strDate;
							masiveStrMonth[m][row][3] = lab;

							row++;
						}
					}
				}
			}
		
			return masiveStrMonth;
		}

		private static String[][][] MasiveFromMonthCheckLab(SimpleDateFormat sdfrmt, Workbook workbookMont) {
			String EGN;
			String doze;
			String lab,lab2;
			Cell cell_EGN;
		
			Cell cell_Doze;
			Cell cell_Lab;

			String[][][] masiveStrMonth = new String[12][500][4];

			for (int m = 0; m < 12; m++) {

				Sheet sheetMont = workbookMont.getSheetAt(m);
				int row = 0;
				for (int l = 6; l <= sheetMont.getLastRowNum(); l++) {

					if (sheetMont.getRow(l) != null) {
						cell_EGN = sheetMont.getRow(l).getCell(3);
						if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {
							EGN = ReadExcelFileWBC.getStringEGNfromCell(cell_EGN);

							cell_Doze = sheetMont.getRow(l).getCell(5);
							doze = ReadExcelFileWBC.getStringEGNfromCell(cell_Doze);
					
							cell_Lab = sheetMont.getRow(l).getCell(8);
							lab = ReadExcelFileWBC.getStringEGNfromCell(cell_Lab);
							
							cell_Lab = sheetMont.getRow(l).getCell(9);
							lab2 ="";
							if (ReadExcelFileWBC.CellNOEmpty(cell_EGN)) {
							lab2 = ReadExcelFileWBC.getStringEGNfromCell(cell_Lab);
							}

							masiveStrMonth[m][row][0] = EGN;
							masiveStrMonth[m][row][1] = doze;
							masiveStrMonth[m][row][2] = lab;
							masiveStrMonth[m][row][3] = lab2;

							row++;
						}
					}
				}
			}
		
			return masiveStrMonth;
		}
	
		
		
		public static String CheckCorrectionAllRowInSheets() {
			String infotext ="";
			String filePath[] = { ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig"),
					ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig") };

			String fileName[] = { "Personel", "External" };

			for (int i = 0; i < filePath.length; i++) {
				Workbook workbook = ReadExcelFileWBC.openExcelFile(filePath[i]);
				String str1 = "", str2 = "", str3 = "", str4 = "";
				Sheet sheet1 = workbook.getSheetAt(0);
				Sheet sheet2 = workbook.getSheetAt(1);
				Sheet sheet3 = workbook.getSheetAt(2);
				Sheet sheet4 = workbook.getSheetAt(3);

				Cell cell1, cell2, cell3, cell4;
				for (int row = 5; row <= sheet1.getLastRowNum(); row += 1) {

					if (sheet1.getRow(row) != null) {

						for (int col = 0; col <= 6; col++) {
//						System.out.println(row);

							if (sheet1.getRow(row) != null && sheet2.getRow(row) != null && sheet3.getRow(row) != null
									&& sheet4.getRow(row) != null) {

								cell1 = sheet1.getRow(row).getCell(col);
								cell2 = sheet2.getRow(row).getCell(col);
								cell3 = sheet3.getRow(row).getCell(col);
								cell4 = sheet4.getRow(row).getCell(col);

								if (ReadExcelFileWBC.CellNOEmpty(cell1) && ReadExcelFileWBC.CellNOEmpty(cell2)
										&& ReadExcelFileWBC.CellNOEmpty(cell3) && ReadExcelFileWBC.CellNOEmpty(cell4)) {

									str1 = ReadExcelFileWBC.getStringEGNfromCell(cell1);
									str2 = ReadExcelFileWBC.getStringEGNfromCell(cell2);
									str3 = ReadExcelFileWBC.getStringEGNfromCell(cell3);
									str4 = ReadExcelFileWBC.getStringEGNfromCell(cell4);

									if (str1.equals(str2) && str2.equals(str3) && str3.equals(str4)) {
//							System.out.println(row+" OK "+str1+" "+str2+" "+str3+" "+str4);
									} else {
										
										infotext += fileName[i] + " : " + (row+1) + " NO " + str1 + " <-> " + str2 + " <-> "
												+ str3 + " <-> " + str4+"\n" ;
									}
								}
							}
						}
					}
				}
			}
			return infotext;
		
		}
	
		
		
		
	}
	
	
	
	
	
	
	

