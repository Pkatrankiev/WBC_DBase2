package Aplication;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Workplace;

public class ReadExcelFileWBC {

	public static Workbook openExcelFile(String FILE_PATH) {
		Workbook workbook = null;
		try {
			FileInputStream fis = new FileInputStream(FILE_PATH);
			workbook = new HSSFWorkbook(fis);

		} catch (FileNotFoundException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Не намирам excel файл:\n"+FILE_PATH, "Грешни данни", JOptionPane.ERROR_MESSAGE);
		
		} catch (OldExcelFormatException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Excel файлът трябва да е версия 97/2000/XP/2003", "Грешни данни",
					JOptionPane.ERROR_MESSAGE);

		} catch (IOException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Не е избран excel файл", "Грешни данни", JOptionPane.ERROR_MESSAGE);
		}

		return workbook;
	}

	public static Date readCellToDate(Cell cell) {
		Date date = new Date();
		if (cell != null) {
			String type = cell.getCellType().toString();
			switch (type) {
			case "STRING": {
				date = isLegalDate(cell.getStringCellValue(), cell);
			}
				break;
			case "DATA":
			case "NUMERIC": {
				date = cell.getDateCellValue();
			}
				break;

			}
		}
		return date;
	}

	public static Cell formatCellToDate(Cell cell, Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.MM.yy"));

		cell.setCellValue(new Date());
		cell.setCellStyle(cellStyle);
		return cell;

	}

	public static boolean CellNOEmpty(Cell cell) {
		
		return cell != null && cell.getCellType() != CellType.BLANK && !getStringfromCell(cell).trim().isEmpty();
	}
	
	public static String getStringfromCell(Cell cell) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.M.yyyy");
		String str = "";
		Date beforDat = null;
		try {
			beforDat = sdfrmt.parse("01.01.1950");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if(cell!= null) {
		try {
		String type = cell.getCellType().toString();
//		System.out.println("type "+type);
		switch (type) {
		
		case "STRING": {
			str = cell.getStringCellValue();
			
		}
			break;
		case "BLANG": {
			str = "";
		}
			break;
		
		case "NUMERIC": {
			
				double doub = cell.getNumericCellValue();
				str = new DecimalFormat("0.00").format(doub);
	
		}
			break;
		case "DATA":
		{
			
		Date dat = cell.getDateCellValue();
			if(dat.after(beforDat)) {
			str = sdfrmt.format(dat);
			}
			
		}
			break;
		}
		
	} catch (Exception e) {
		
		String cellSring = "Cell = " + CellReference.convertNumToColString(cell.getColumnIndex())
				+ (cell.getRowIndex() + 1) + ", val = " + str;
		String cel = CellReference.convertNumToColString(cell.getColumnIndex()) + (cell.getRowIndex() + 1);
		JOptionPane.showInputDialog(null, cellSring, cel);
		
	}
//		System.out.println("str "+str);
		if(str.equals("31.12.1899")) {
			str = "";
		}
		
		}
		return str;
	}
	
	@SuppressWarnings("deprecation")
	public static String getStringEGNfromCell(Cell cell) {
//		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.M.yyyy");
		String str = "";
		if(cell!= null) {
		try {
		String type = cell.getCellType().toString();
		switch (type) {
		case "STRING": {
			str = cell.getStringCellValue();
		}
			break;
		case "BLANG": {
			str = "";
		}
			break;
		case "DATA":
		{
			cell.setCellType(CellType.STRING);
			str = cell.getStringCellValue();
		}
		case "NUMERIC": {
			cell.setCellType(CellType.STRING);
			str = cell.getStringCellValue();
			

		}
			break;
		}
		
	} catch (Exception e) {
		
		String cellSring = "Cell = " + CellReference.convertNumToColString(cell.getColumnIndex())
				+ (cell.getRowIndex() + 1) + ", val = " + str;
		String cel = CellReference.convertNumToColString(cell.getColumnIndex()) + (cell.getRowIndex() + 1);
		JOptionPane.showInputDialog(null, cellSring, cel);
		
	}
		}
		return str;
	}

	public static double getDoublefromCell(Cell cell) {
		double num = -1;
		if(cell!=null) {
		String type = cell.getCellType().toString();
		switch (type) {
		case "STRING": {
			try {
				num = Double.parseDouble(cell.getStringCellValue());
			} catch (Exception e) {
				num = -1;
			}
		}
			break;
		case "NUMERIC": {
			num = cell.getNumericCellValue();
		}
			break;
		}
		}
		return num;
	}
	
	public static Date isLegalDate(String strDate, Cell cell) {
		
		Date javaDate = null;

		if (strDate.trim().equals("")) {

		} else {
			strDate = strDate.replaceAll("г.", "");
			strDate = strDate.replaceAll("г", "");
			strDate = strDate.trim();
			SimpleDateFormat sdfrmt = AplicationMetods.extractedSimleDateFormatFromDate(strDate);
			try {
				sdfrmt.setLenient(false);
				javaDate = sdfrmt.parse(strDate);
			} catch (Exception e) {
				
				String cellSring = "Cell = " + CellReference.convertNumToColString(cell.getColumnIndex())
						+ (cell.getRowIndex() + 1) + ", val = " + strDate;
				String cel = CellReference.convertNumToColString(cell.getColumnIndex()) + (cell.getRowIndex() + 1);
				String m = JOptionPane.showInputDialog(null, cellSring, cel);
				javaDate = isLegalDate(m, cell);
			}

		}

		return javaDate;
	}

	public static String[] splitAllName(String firstName) {
		String[] names = new String[3];

		String[] names1 = splitName(firstName);
		int countNames = names1.length;
		
		for (int i = 0; i < countNames; i++) {
			names1[i] = names1[i].trim();
//			System.out.println(names1[i]);
			if(!names1[i].isEmpty())
			names1[i] =names1[i].substring(0, 1).toUpperCase() + names1[i].substring(1).toLowerCase();
		}

		if (names1.length == 1) {
			names[0] = names1[0];
			names[1] = "";
			names[2] = "";
		}

		if (names1.length == 2) {
			names[0] = names1[0];
			names[1] = "";
			names[2] = names1[1];
		}

		if (names1.length == 3) {
			names[0] = names1[0];
			names[1] = names1[1];
			names[2] = names1[2];
		}

		if (names1.length > 3) {
			names[0] = names1[0];
			names[1] = names1[1];
			names[2] = names1[2];
			for (int i = 3; i < names1.length; i++) {
				names[2] = names[2] + " " + names1[i];
			}
		}
		return names;

	}

	private static String[] splitName(String firstName) {
		List<String> list = new ArrayList<>();
		int index = firstName.indexOf(" ");
		while (index>0) {
			list.add(firstName.substring(0, index));
			firstName = firstName.substring(index).trim();
			index = firstName.indexOf(" ");
		} 
		list.add(firstName.trim());
		String[] masive = new String[list.size()];
		int i =0;
		for (String string : list) {
			masive[i] = string;	
//			System.out.println(masive[i]);
			i++;
		}
		return masive;
	}

	public static String[] getMasiveString(String firmName) {
		List<Workplace> list = WorkplaceDAO.getValueWorkplaceByObjectSortByColumnName("FirmName", firmName, "Otdel");
		String[] str = new String[list.size()];
		int i = 0;
		for (Workplace workplace : list) {
			str[i] = workplace.getOtdel();
			i++;
		}
		return str;
	}

	public static Workplace selectWorkplace(String firmName, String[] masiveWorkplace, String otdelName,
			List<Workplace> listAllWorkplaceByFirmName) {
		Workplace workplace;
		workplace = new Workplace();
		boolean fl = true;
		for (Workplace worl : listAllWorkplaceByFirmName) {
//			System.out.println("----->>"+worl.getOtdel());
			if (worl.getOtdel().equals(otdelName) || (worl.getSecondOtdelName()!= null && worl.getSecondOtdelName().equals(otdelName))) {
				fl = false;
				workplace = worl;
			}

		}
		if (fl) {
			String selectOtdelName = InputDialog(masiveWorkplace, otdelName);
			if (selectOtdelName == null) {
				String inputNewOtdel = JOptionPane.showInputDialog(null, "Въведете нов обект", otdelName);
				if(inputNewOtdel==null) {
					workplace = selectWorkplace(firmName, masiveWorkplace,  otdelName, listAllWorkplaceByFirmName);
				}
				workplace = new Workplace(firmName, inputNewOtdel, "",true,"",null);
				WorkplaceDAO.setObjectWorkplaceToTable(workplace);
				workplace = WorkplaceDAO.getValueWorkplaceByObject("Otdel", inputNewOtdel).get(0);

			} else {
				String secondOtdelName = JOptionPane.showInputDialog(null, selectOtdelName, otdelName);
				workplace = WorkplaceDAO.getValueWorkplaceByObject("Otdel", selectOtdelName).get(0);
				workplace.setSecondOtdelName(secondOtdelName);
				System.out.println(
						workplace.getFirmName()+" "+
				workplace.getOtdel()+" "+
				workplace.getSecondOtdelName()+" "+
				workplace.getActual()+" "+
				workplace.getNapOtdelSector()+" "+
				workplace.getLab().getLab_ID()
				);
				WorkplaceDAO.updateValueWorkplace(workplace);
				
			}
		}
		return workplace;
	}
	
	public static String InputDialog(String[] options, String input) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		ImageIcon icon = new ImageIcon("src/images/turtle32.png");
		String n = (String) JOptionPane.showInputDialog(null, input, "Изберете отдел към който ще се добави второ име", JOptionPane.QUESTION_MESSAGE,
				icon, options, options[2]);
		System.out.println(n);
		return n;
	}

}
