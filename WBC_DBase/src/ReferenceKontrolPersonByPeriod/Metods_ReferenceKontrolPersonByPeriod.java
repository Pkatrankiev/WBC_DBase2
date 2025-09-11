package ReferenceKontrolPersonByPeriod;

import java.awt.Choice;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.ReadMeasuringFromExcelFile;
import Aplication.ReadPersonStatusFromExcelFile;
import Aplication.RemouveDublikateFromList;
import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.DimensionWBC;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.TypeMeasur;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import DeleteDataFromDBaseRemoveInCurenFromOldYear.DeleteDataFromDBaseRemoveInCurenFromOldYear_Frame;

public class Metods_ReferenceKontrolPersonByPeriod {

	static List<List<String>> listPersonFromOtdel;

	static int curentYear = Calendar.getInstance().get(Calendar.YEAR);

	static Object[][] searchFromDBase(JProgressBar aProgressBar, List<String> listAdd) {

		double ProgressBarSize = 0;
		aProgressBar.setValue((int) ProgressBarSize);

		String startDate = ReferenceKontrolPersonByPeroid_Frame.getTextField_StartDate().getText().trim();
		String endDate = ReferenceKontrolPersonByPeroid_Frame.getTextField_EndDate().getText().trim();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date dateStart = null, dateEnd = null;
//		JTextArea textArea_PersonMeasur = Reference_PersonMeasur_Frame.getTextArea();
//		JButton btn_Export_PersonMeasur = Reference_PersonMeasur_Frame.getBtn_Export();

		try {
			dateStart = sdf.parse(startDate);
			dateEnd = sdf.parse(endDate);

		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		String year = startDate.substring(6);

		double[][] data = new double[listAdd.size()][6];
		String[] dataTableOtdel = new String[listAdd.size()];
		double[] dataTableAll = new double[6];

		listPersonFromOtdel = new ArrayList<List<String>>();

		String personInfo = "";
		int index = 0;
		int personCount = 0;
		int measurCount = 0;
		int measurOverMDA = 0;
		double measurSum = 0;
		double measurMAX = 0;

		int personMeasurCount = 0;
		int personMeasurOverMDA = 0;
		double personMeasurSum = 0;
		double personMeasurMAX = 0;

		double stepForProgressBar = 100;
		stepForProgressBar = stepForProgressBar / listAdd.size();

		for (String otdel : listAdd) {
//			if(otdel.equals("Българи (КЗ-2/ХОГ) по заявка/заповед")){

			personCount = 0;
			measurCount = 0;
			measurOverMDA = 0;
			measurSum = 0;
			measurMAX = 0;

			Workplace workPlace = WorkplaceDAO.getValueWorkplaceByObject("Otdel", otdel).get(0);

			List<Person> listPerson = spisakPersonFromWorkplace(workPlace, year);
			System.out.println(year + " " + listPerson.size());

			if (listPerson.size() > 0) {
				List<String> listPersonInfo = new ArrayList<>();

				for (Person person : listPerson) {

					List<Measuring> listMeasur = MeasuringDAO.getValueMeasuringByPersonAndYear(person, dateStart,
							dateEnd);

					if (listMeasur.size() > 0) {

						personCount++;
						personInfo = "";
						personMeasurCount = 0;
						personMeasurOverMDA = 0;
						personMeasurSum = 0;
						personMeasurMAX = 0;

						System.out.println(personCount + " " + person.getEgn() + " " + person.getFirstName() + " "
								+ person.getLastName() + " " + listMeasur.size());

						for (Measuring meas : listMeasur) {
							personMeasurCount++;
							measurCount++;
							if (meas.getDoze() > 0) {
								personMeasurOverMDA++;

								if (meas.getDoze() >= 0.1) {
									measurSum += meas.getDoze();
									personMeasurSum += meas.getDoze();

									if (personMeasurMAX < meas.getDoze()) {
										personMeasurMAX = meas.getDoze();
									}
								}

							}

						}

//						System.out.println(endDate.substring(6));
						String kode = "-";
						KodeStatus kk = KodeStatusDAO.getKodeStatusByPersonZoneYear(person, 2, endDate.substring(6));
						if (kk != null) {
							kode = kk.getKode();
						}
//						име # ЕГН # код # Бр.Измервания # Бр.Измервания над МДА # СумаДоза # МаксДоза
						personInfo = "№" + "#" + person.getFirstName() + " " + person.getSecondName() + " "
								+ person.getLastName() + "#" + person.getEgn() + "#" + kode + "#" + personMeasurCount
								+ "#" + personMeasurOverMDA + "#" + personMeasurSum + "#" + personMeasurMAX;

						listPersonInfo.add(personInfo);

						if (personMeasurOverMDA > 0) {
							measurOverMDA++;
						}
					}
					if (measurMAX < personMeasurSum) {
						measurMAX = personMeasurSum;
					}

				}

				data[index][0] = personCount;
				data[index][1] = measurCount;
				data[index][2] = measurOverMDA;
				data[index][3] = measurSum;
				data[index][4] = measurMAX;
				data[index][5] = 0;
				if (measurCount > 0 && measurSum / measurCount >= 0.1) {
					data[index][5] = measurSum / measurCount;
				}
				dataTableOtdel[index] = otdel;
				listPersonFromOtdel.add(listPersonInfo);

				index++;
			}
//		}
			aProgressBar.setValue((int) ProgressBarSize);
			ProgressBarSize += stepForProgressBar;

		}
		dataTableAll[4] = 0;
		measurSum = 0;
		for (int i = 0; i < data.length; i++) {
			dataTableAll[0] += data[i][0];
			dataTableAll[1] += data[i][1];
			dataTableAll[2] += data[i][2];
			dataTableAll[3] += data[i][3];
			if (dataTableAll[4] < data[i][4]) {
				dataTableAll[4] = data[i][4];
			}
		}
		dataTableAll[5] = 0;
		if (dataTableAll[1] > 0 && dataTableAll[3] / dataTableAll[1] >= 0.1) {
			dataTableAll[5] = dataTableAll[3] / dataTableAll[1];
		}

		Object[][] dataTableInfo = new Object[index + 1][8];

		dataTableInfo[0][0] = "";
		dataTableInfo[0][1] = "Общо";
		dataTableInfo[0][2] = dataTableAll[0];
		dataTableInfo[0][3] = dataTableAll[1];
		dataTableInfo[0][4] = dataTableAll[2];
		dataTableInfo[0][5] = dataTableAll[3];
		dataTableInfo[0][6] = dataTableAll[4];
		dataTableInfo[0][7] = dataTableAll[5];

		for (int i = 1; i < index + 1; i++) {
			dataTableInfo[i][0] = i;
			dataTableInfo[i][1] = dataTableOtdel[i - 1];
			dataTableInfo[i][2] = data[i - 1][0];
			dataTableInfo[i][3] = data[i - 1][1];
			dataTableInfo[i][4] = data[i - 1][2];
			dataTableInfo[i][5] = data[i - 1][3];
			dataTableInfo[i][6] = data[i - 1][4];
			dataTableInfo[i][7] = data[i - 1][5];

		}

		return dataTableInfo;
	}

	public static Object[][] searchFromExcelFile(JProgressBar aProgressBar) {

		String[] pathToArhiveExcellFiles = AplicationMetods.getDataBaseFilePat_ArhivePersonalAndExternal();

		String[] pathToFiles_OriginalPersonalAndExternal = AplicationMetods
				.getDataBaseFilePat_OriginalPersonalAndExternal();
		int curentYear = Calendar.getInstance().get(Calendar.YEAR);

		double ProgressBarSize = 0;
		aProgressBar.setValue((int) ProgressBarSize);

		String startDate = ReferenceKontrolPersonByPeroid_Frame.getTextField_StartDate().getText().trim();
		String endDate = ReferenceKontrolPersonByPeroid_Frame.getTextField_EndDate().getText().trim();
		int selectKode = ReferenceKontrolPersonByPeroid_Frame.getComboBox_KodeZone().getSelectedIndex();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date dateStart = null, dateEnd = null;

		try {
			dateStart = sdf.parse(startDate);
			dateEnd = sdf.parse(endDate);

		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		String year = startDate.substring(6);
		int insertYear = Integer.parseInt(year);

		String[] path = pathToArhiveExcellFiles;

		if (insertYear == curentYear) {
			path = pathToFiles_OriginalPersonalAndExternal;
		}
		String pathFile = path[1];
		if (((String) ReferenceKontrolPersonByPeroid_Frame.getComboBox_Firm().getSelectedItem()).trim()
				.equals("АЕЦ Козлодуй")) {
			pathFile = path[0];
		}

		if (insertYear != curentYear) {
			pathFile = pathFile + year + ".xls";
		}
		System.out.println(pathFile);

		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);

		List<Measuring> listMeasuring = generateListFromMeasuringFromBigExcelFile(workbook);
		List<String> listAdd = getListArreaOtdels(workbook);
		List<String> listOtdel_EGN_Name_Kode = getListOtdel_EGN_Name_Kode(workbook, selectKode);
for (int i = 0; i < listOtdel_EGN_Name_Kode.size(); i++) {
	

		System.out.println(listOtdel_EGN_Name_Kode.get(i));
}	
		
		double[][] data = new double[listAdd.size()][6];
		String[] dataTableOtdel = new String[listAdd.size()];
		double[] dataTableAll = new double[6];

		listPersonFromOtdel = new ArrayList<List<String>>();

		String personInfo = "";
		int index = 0;
		int personCount = 0;
		int measurCount = 0;
		int measurOverMDA = 0;
		double measurSum = 0;
		double measurMAX = 0;

		int personMeasurCount = 0;
		int personMeasurOverMDA = 0;
		double personMeasurSum = 0;
		double personMeasurMAX = 0;

		double stepForProgressBar = 100;
		stepForProgressBar = stepForProgressBar / listAdd.size();

		for (String otdel : listAdd) {

			personCount = 0;
			measurCount = 0;
			measurOverMDA = 0;
			measurSum = 0;
			measurMAX = 0;

			List<String> listPersonByOtdel = getListPersonByOtdel(listOtdel_EGN_Name_Kode, otdel);

			System.out.println("listPersonByOtdel.size() " + listPersonByOtdel.size());

			if (listPersonByOtdel.size() > 0) {
				List<String> listPersonInfo = new ArrayList<>();

				for (String personOtdel_EGN_Name_kode : listPersonByOtdel) {

					List<Measuring> listMeasur = getMeasuringPerson(listMeasuring, personOtdel_EGN_Name_kode, dateStart,
							dateEnd);
					System.out.println("listMeasur.size() " + listMeasur.size());

					if (listMeasur.size() > 0) {

						personCount++;
						personInfo = "";
						personMeasurCount = 0;
						personMeasurOverMDA = 0;
						personMeasurSum = 0;
						personMeasurMAX = 0;
						String[] perInf = personOtdel_EGN_Name_kode.split("#");
						System.out.println(personCount + " " + perInf[1] + " " + perInf[2] + " " + listMeasur.size());

						for (Measuring meas : listMeasur) {
							personMeasurCount++;
							measurCount++;
							if (meas.getDoze() > 0) {
								personMeasurOverMDA++;

								if (meas.getDoze() >= 0.1) {
									measurSum += meas.getDoze();
									personMeasurSum += meas.getDoze();

									if (personMeasurMAX < meas.getDoze()) {
										personMeasurMAX = meas.getDoze();
									}
								}

							}

						}

						System.out.println(endDate.substring(6));

//						име # ЕГН # код # Бр.Измервания # Бр.Измервания над МДА # СумаДоза # МаксДоза
						personInfo = "№" + "#" + perInf[2] + "#" + perInf[1] + "#" + perInf[3] + "#" + personMeasurCount
								+ "#" + personMeasurOverMDA + "#" + personMeasurSum + "#" + personMeasurMAX;

						listPersonInfo.add(personInfo);

						if (personMeasurOverMDA > 0) {
							measurOverMDA++;
						}
					}
					if (measurMAX < personMeasurSum) {
						measurMAX = personMeasurSum;
					}

				}

				data[index][0] = personCount;
				data[index][1] = measurCount;
				data[index][2] = measurOverMDA;
				data[index][3] = measurSum;
				data[index][4] = measurMAX;
				data[index][5] = 0;
				if (measurCount > 0 && measurSum / measurCount >= 0.1) {
					data[index][5] = measurSum / measurCount;
				}
				dataTableOtdel[index] = otdel;
				listPersonFromOtdel.add(listPersonInfo);

				index++;
			}

			aProgressBar.setValue((int) ProgressBarSize);
			ProgressBarSize += stepForProgressBar;
		}
		dataTableAll[4] = 0;
		measurSum = 0;
		for (int i = 0; i < data.length; i++) {
			dataTableAll[0] += data[i][0];
			dataTableAll[1] += data[i][1];
			dataTableAll[2] += data[i][2];
			dataTableAll[3] += data[i][3];
			if (dataTableAll[4] < data[i][4]) {
				dataTableAll[4] = data[i][4];
			}
		}
		dataTableAll[5] = 0;
		if (dataTableAll[1] > 0 && dataTableAll[3] / dataTableAll[1] >= 0.1) {
			dataTableAll[5] = dataTableAll[3] / dataTableAll[1];
		}

		Object[][] dataTableInfo = new Object[index + 1][8];

		dataTableInfo[0][0] = "";
		dataTableInfo[0][1] = "Общо";
		dataTableInfo[0][2] = dataTableAll[0];
		dataTableInfo[0][3] = dataTableAll[1];
		dataTableInfo[0][4] = dataTableAll[2];
		dataTableInfo[0][5] = dataTableAll[3];
		dataTableInfo[0][6] = dataTableAll[4];
		dataTableInfo[0][7] = dataTableAll[5];

		for (int i = 1; i < index + 1; i++) {
			dataTableInfo[i][0] = i;
			dataTableInfo[i][1] = dataTableOtdel[i - 1];
			dataTableInfo[i][2] = data[i - 1][0];
			dataTableInfo[i][3] = data[i - 1][1];
			dataTableInfo[i][4] = data[i - 1][2];
			dataTableInfo[i][5] = data[i - 1][3];
			dataTableInfo[i][6] = data[i - 1][4];
			dataTableInfo[i][7] = data[i - 1][5];

		}

		return dataTableInfo;
	}

	private static List<Measuring> getMeasuringPerson(List<Measuring> listAllMeasuring, String personOtdel_EGN_Name,
			Date dateStart, Date dateEnd) {
		List<Measuring> listMeasuring = new ArrayList<>();
		for (Measuring measuring : listAllMeasuring) {
			if (measuring.getPerson().getEgn().equals(personOtdel_EGN_Name.split("#")[1])&&
					!measuring.getDate().before(dateStart) && !measuring.getDate().after(dateEnd)				
					) {
				listMeasuring.add(measuring);
			}
		}
		return listMeasuring;
	}

	private static List<String> getListPersonByOtdel(List<String> listOtdel_EGN_Name, String otdel) {
		List<String> listPersonInfo = new ArrayList<>();
		for (String string : listOtdel_EGN_Name) {
			if (string.split("#")[0].equals(otdel)) {
				listPersonInfo.add(string);
			}
		}
		return listPersonInfo;
	}

	public static List<Measuring> generateListFromMeasuringFromBigExcelFile(Workbook workbook) {
		String lab, FirstName;
		Date date;

		DimensionWBC dim = DimensionWBCDAO.getValueDimensionWBCByID(2);
		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);
		TypeMeasur tipeM_R = TypeMeasurDAO.getValueTypeMeasurByID(1);

		Sheet sheet = workbook.getSheetAt(1);
		Cell cell, cell1, cell2;
		List<Measuring> listMeasuring = new ArrayList<>();

		int StartRow = 5;
		int endRow = sheet.getLastRowNum();

		for (int row = StartRow; row < endRow; row += 1) {

			if (sheet.getRow(row) != null && row > 0) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

					Person person = ReadKodeStatusFromExcelFile.getPersonFromEGNCell(cell);

					FirstName = ReadExcelFileWBC.getStringEGNfromCell(cell1);
					if (person != null && !FirstName.contains("АЕЦ")) {

						int k = 7;
						cell1 = sheet.getRow(row).getCell(k);
						k++;
						cell2 = sheet.getRow(row).getCell(k);
						while (ReadExcelFileWBC.CellNOEmpty(cell1) && ReadExcelFileWBC.CellNOEmpty(cell2)) {
							date = ReadExcelFileWBC.readCellToDate(cell1);
							lab = ReadExcelFileWBC.getStringfromCell(cell2).trim();
							if (true) {

							}
							k = k + 17;
							Measuring meas = ReadMeasuringFromExcelFile.createMeasur(person, dim, userSet, sheet, row,
									k, tipeM_R, date, lab);

							listMeasuring.add(meas);
							if (k > 252) {
								k = 6;
								sheet = workbook.getSheetAt(2);
							}

							k++;
							cell1 = sheet.getRow(row).getCell(k);
							k++;
							cell2 = sheet.getRow(row).getCell(k);

						}
						sheet = workbook.getSheetAt(1);
					}
				}
			}

		}
		return listMeasuring;
	}

	public static List<String> getListArreaOtdels(Workbook workbook) {

		List<String> list = new ArrayList<>();
		if (workbook.getNumberOfSheets() > 2) {
			String otdelName = "";

			Sheet sheet = workbook.getSheetAt(0);
			Cell cell, cell1;

			for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

				if (sheet.getRow(row) != null) {
					cell = sheet.getRow(row).getCell(5);
					cell1 = sheet.getRow(row).getCell(6);
					if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {

						if (cell1.getStringCellValue().equals("край")) {
							list.add(otdelName);

						} else {
							otdelName = cell1.getStringCellValue();
						}

					}
				}

			}
		}
		return list;
	}

	public static List<String> getListOtdel_EGN_Name_Kode(Workbook workbook, int selectKode) {

		List<String> list = new ArrayList<>();
		if (workbook.getNumberOfSheets() > 2) {
			String otdelName = "", EGN = "", name = "", kode = "";

			Sheet sheet = workbook.getSheetAt(0);
			Cell cell, cell1;
			boolean otdelTrue = false;
			for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {

				if (sheet.getRow(row) != null) {
					cell = sheet.getRow(row).getCell(5);
					cell1 = sheet.getRow(row).getCell(6);

					if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
						otdelName = cell1.getStringCellValue().trim();
						otdelTrue = true;

						if (otdelName.contains("край") || otdelName.contains("КРАЙ")) {
							otdelTrue = false;
						}
					}

					if (ReadExcelFileWBC.CellNOEmpty(cell) && otdelTrue) {
						EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
						name = cell1.getStringCellValue().trim();
						
						cell = sheet.getRow(row).getCell(selectKode);
						kode = cell.getStringCellValue().trim();
						if(kode.trim().isEmpty()||kode.equals("н")) {;
						kode = "-";
						}
						list.add(otdelName + "#" + EGN + "#" + name + "#" + kode);
					}
				}
			}

		}
		return list;
	}

	static void setTextToArea(JTextArea textArea, String textForArea) {
		if (textForArea.isEmpty()) {
			textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
			DeleteDataFromDBaseRemoveInCurenFromOldYear_Frame.getBtn_Export().setEnabled(false);
		} else {
			textArea.setText(textForArea);
			DeleteDataFromDBaseRemoveInCurenFromOldYear_Frame.getBtn_Export().setEnabled(true);
		}
	}

	public static String[] refornatTableHeader(String[] header) {
		for (int i = 0; i < header.length; i++) {
			header[i] = header[i].replace("<html>", " ").replace("</html>", " ").replace("<br>", " ")
					.replace("<center>", "").replace("</center>", "").replace("'", "");
		}
		return header;

	}

	public static List<Person> spisakPersonFromWorkplace(Workplace workPlace, String curentYear) {

		List<Integer> listPersonID = new ArrayList<>();
		List<Person> listPerson = new ArrayList<>();
		List<Person> listPersonNew = new ArrayList<>();

		List<PersonStatusNew> listPerStat = PersonStatusNewDAO.getValuePersonStatusNewByWorkplace_Year(workPlace,
				curentYear);
		Person person = new Person();

		for (PersonStatusNew personStat : listPerStat) {

			person = personStat.getPerson();
			PersonStatusNew perStat = PersonStatusNewDAO.getLastPersonStatusNewByPerson_YearSortByStartDate(person,
					curentYear);

			if (perStat != null) {
				if (perStat.getWorkplace().getOtdel().equals(workPlace.getOtdel())) {
					listPersonNew.add(person);
				}
			}

		}

		for (Person personn : listPersonNew) {
			listPersonID.add(personn.getId_Person());

		}

		listPersonID = RemouveDublikateFromList.removeDuplicates(new ArrayList<Integer>(listPersonID));
		for (Integer integer : listPersonID) {
			listPerson.add(PersonDAO.getValuePersonByID(integer));
		}

		return listPerson;
	}

	public static void MessageDialog(String textInFrame, String textFrame) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, textInFrame, textFrame, JOptionPane.PLAIN_MESSAGE, otherIcon);

	}

	public static List<List<String>> getListPersonFromOtdel() {
		return listPersonFromOtdel;
	}

	
	static void setUp_Double_Column(JTable table, int NumColumn) {
		DecimalFormat format = new DecimalFormat();
		@SuppressWarnings("serial")
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
				
//		renderer.setHorizontalAlignment()
		@SuppressWarnings("unused")
		protected void setValueAt(Object value) {

		if (value instanceof Double) {
			setText(format.format(value));

		}else {
			super.setValue(value);
		}
	}
		
	};
	renderer.setHorizontalAlignment(SwingConstants.RIGHT);
	table.getColumnModel().getColumn(NumColumn).setCellRenderer(renderer);
	}
	
    public static void initColumnSizes(JTable table, Object[][] dataTable) {
	   	
    	DefaultTableModel model =(DefaultTableModel) table.getModel();
        TableColumn column = null;
      
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
       Object[] longValues = getMaxLongColumn(dataTable);
       for (int i = 0; i < longValues.length; i++) {
    	   System.out.println(longValues[i]);
       }
        TableCellRenderer headerRenderer =
            table.getTableHeader().getDefaultRenderer();

        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);

            comp = headerRenderer.getTableCellRendererComponent(
                                 null, column.getHeaderValue(),
                                 false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;

            comp = table.getDefaultRenderer(model.getColumnClass(i)).
                             getTableCellRendererComponent(
                                 table, longValues[i],
                                 false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
            
        }
    }
	
	
    private static Object[] getMaxLongColumn(Object[][] masivePeson) {
		Object[] maxWith = { 50, 100, 200, 200, 200, 400, 300, 200};
		for (int i = 0; i < masivePeson.length; i++) {
			for (int j = 0; j < masivePeson[0].length; j++) {
				if(maxWith[j].toString().length() < masivePeson[i][j].toString().length())
				maxWith[j] = masivePeson[i][j];
				
			}
		}
		return maxWith;
	}
	
	
	
}
