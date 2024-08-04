package DozeArt;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import DatePicker.DatePicker;

public class DozeArt_Methods {

	private static int max_scrollBarToPrePrevios;
	private static int max_scrollBarToPrevios;
	
	private static List<List<List<String>>> listData;
	
	public static List<List<List<String>>> ReadDataFromExcelFile() {

		listData = new ArrayList<>();

		Cell cell = null;
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream("DOSE_IRF_Const-24.xls");

			try (Workbook workbook = new HSSFWorkbook(inputStream)) {
				int countSheets = workbook.getNumberOfSheets();

				for (int cs = 0; cs < countSheets; cs++) {

					Sheet sheet = workbook.getSheetAt(cs);

					int countRow = sheet.getPhysicalNumberOfRows();

					Row sourceRow = sheet.getRow(0);
					int columnCount = getCountColumn(sourceRow);

					List<List<String>> listNuclideData = new ArrayList<>();
					for (int k = 0; k < columnCount; k++) {

						List<String> listDataColumn = new ArrayList<>();

						if (k == 0) {
							listDataColumn.add(sheet.getSheetName());
						} else {
							for (int j = 0; j < countRow; j++) {

								sourceRow = sheet.getRow(j);

								if (sourceRow != null) {

									cell = sourceRow.getCell(k);
									if (cell != null) {
										if (sourceRow.getRowNum() == 0) {
											listDataColumn.add(cell.getStringCellValue());
										} else {
											listDataColumn.add(cell.getNumericCellValue() + "");
										}

									}
								}

							}
						}
						listNuclideData.add(listDataColumn);
					}

					listData.add(listNuclideData);

				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
		return listData;

	}

	
	public static List<String> ReadNuclideList() {
		List<List<List<String>>> lstData = ReadDataFromExcelFile();
		List<String> listNuclide = new ArrayList<>();
		for (List<List<String>> listNuclideData : lstData) {

			listNuclide.add(listNuclideData.get(0).get(0));
				
		}
		
		return listNuclide;

	}
	
	public static Object[][] SetMasiveDadaByNuclide(String nuclideName) {
		Object[][] masive = null;
		List<List<List<String>>> lstData = ReadDataFromExcelFile();

		for (List<List<String>> listNuclideData : lstData) {

			int column = listNuclideData.size();
			int row = listNuclideData.get(1).size();

			if (listNuclideData.get(0).get(0).equals(nuclideName)) {
				masive = new Object[column - 1][row];
				int amad = 0;
				for (List<String> listColumnData : listNuclideData) {
					if (amad > 0) {
						int index = 0;
						for (String data : listColumnData) {
							if (index == 0) {
								masive[amad - 1][index] = data;
							} else {
								masive[amad - 1][index] = Double.parseDouble(data);

							}
							index++;
						}
					}
					amad++;
				}
			}
		}
		return masive;
	}
	
	public static List<String> SetListAMAD(Object[][] masive) {
	
		List<String> listAMAD = new ArrayList<>();
		for (int i = 0; i < masive.length; i++) {
			listAMAD.add((String) masive[i][0]);
		}
		return listAMAD;
	}
		
	
	public static void viewData(String nuclideName) {
		Object[][] masive = null;
		List<List<List<String>>> lstData = ReadDataFromExcelFile();

		for (List<List<String>> listNuclideData : lstData) {

			int column = listNuclideData.size();
			int row = listNuclideData.get(1).size();

			if (listNuclideData.get(0).get(0).equals(nuclideName)) {
				masive = new Object[column - 1][row];
				int amad = 0;
				for (List<String> listColumnData : listNuclideData) {
					if (amad > 0) {
						int index = 0;
						for (String data : listColumnData) {
							if (index == 0) {
								masive[amad - 1][index] = data;
							} else {
								masive[amad - 1][index] = Double.parseDouble(data);

							}
							index++;
						}
					}
					amad++;
				}
			}
		}

		for (int i = 0; i < masive.length; i++) {
			for (int j = 0; j < masive[0].length; j++) {
				System.out.println(masive[i][j]);
			}
			System.out.println("***********************************************");
		}
		
	}

	private static int getCountColumn(Row sourceRow) {
		int c = 0;
		for (int k = 0; k < 256; k++) {

			if (sourceRow.getCell(k) != null && !sourceRow.getCell(k).getStringCellValue().isEmpty()) {
				c++;
			} else {
				return c;
			}

		}
		return c;
	}

	static void ActionListenerMonitoringButtons(JRadioButton rdbtnRutinen, JRadioButton rdbtnSecialen) {
		JLayeredPane layeredPane_Postaplenie = DozeArtFrame.getLayeredPane_Postaplenie();
		JPanel panel_Rutinen = DozeArtFrame.getPanel_Rutinen();
		JPanel panel_Secialen = DozeArtFrame.getPanel_Secialen();

		JLayeredPane layeredPane_Mnogokratno = DozeArtFrame.getLayeredPane_Mnogokratno();
		JPanel panel_FreePanelMeasur = DozeArtFrame.getPanel_FreePanelMeasur();
		JPanel panel_EdnokratnoMeasur = DozeArtFrame.getPanel_EdnokratnoMeasur();
		JPanel panel_MnogokratnoMeasur = DozeArtFrame.getPanel_MnogokratnoMeasur();

		JTextField textField_Date_MeasurNaw = DozeArtFrame.getTextField_Date_MeasurNaw();
		JTextField textField_dateMeasurPrev = DozeArtFrame.getTextField_dateMeasurPrev();

		JTextField textField_dateMeasur_Start = DozeArtFrame.getTextField_dateMeasur_Start();
		JTextField textField_dateMeasur_End = DozeArtFrame.getTextField_dateMeasur_End();

		rdbtnRutinen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnRutinen.isSelected()) {
					layeredPane_Postaplenie.setLayer(panel_Rutinen, 3, 0);
					panel_Rutinen.setVisible(true);
					panel_Secialen.setVisible(false);

					checkRdbtnSelected();

				} else {
					layeredPane_Postaplenie.setLayer(panel_Rutinen, 1, 0);
					panel_Secialen.setVisible(true);
					panel_Rutinen.setVisible(false);

					layeredPane_Mnogokratno.setLayer(panel_FreePanelMeasur, 4, 0);
					panel_FreePanelMeasur.setVisible(true);
					panel_EdnokratnoMeasur.setVisible(false);
					panel_MnogokratnoMeasur.setVisible(false);

				}
			}
		});

		rdbtnSecialen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnSecialen.isSelected()) {
					layeredPane_Postaplenie.setLayer(panel_Rutinen, 1, 0);
					panel_Secialen.setVisible(true);
					panel_Rutinen.setVisible(false);

					layeredPane_Mnogokratno.setLayer(panel_FreePanelMeasur, 4, 0);
					panel_FreePanelMeasur.setVisible(true);
					panel_EdnokratnoMeasur.setVisible(false);
					panel_MnogokratnoMeasur.setVisible(false);

					textField_dateMeasur_Start.setText(textField_dateMeasurPrev.getText());
					textField_dateMeasur_End.setText(textField_Date_MeasurNaw.getText());

				} else {
					layeredPane_Postaplenie.setLayer(panel_Rutinen, 3, 0);
					panel_Rutinen.setVisible(true);
					panel_Secialen.setVisible(false);

					checkRdbtnSelected();

				}
			}
		});
	}

	static void ActionListenerPostaplenieButtons(JRadioButton rdbtnEdnokratno, JRadioButton rdbtnMnogokratno,
			JRadioButton rdbtnNeprekasnato, JRadioButton rdbtnSrTotchka) {

		rdbtnNeprekasnato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkRdbtnSelected();
			}
		});

		rdbtnSrTotchka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkRdbtnSelected();
			}
		});

		rdbtnEdnokratno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkRdbtnSelected();

			}
		});

		rdbtnMnogokratno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkRdbtnSelected();

			}
		});
	}

	private static String setDaysAndCountDays() {

		String errorStr = "";

		int max;
		JTextField textField_Date_MeasurNaw = DozeArtFrame.getTextField_Date_MeasurNaw();
		JTextField textField_dateMeasurPrev = DozeArtFrame.getTextField_dateMeasurPrev();
		JTextField textField_dateMeasurPrePrev = DozeArtFrame.getTextField_dateMeasurPrePrev();

		JLabel lbl_DateToPrevios = DozeArtFrame.getLbl_DateToPrevios();
		JLabel lbl_Count_DayToPrevios = DozeArtFrame.getLbl_Count_DayToPrevios();

		JLabel lbl_DateNawToPrevios = DozeArtFrame.getLbl_DateNawToPrevios();
		JLabel lbl_DateNawToPrePrevios = DozeArtFrame.getLbl_DateNawToPrePrevios();
		JScrollBar scrollBarToPrevios = DozeArtFrame.getScrollBarToPrevios();

		JLabel lbl_DateToPrePrevios = DozeArtFrame.getLbl_DateToPrePrevios();
		JLabel lbl_Count_DayToPrePrevios = DozeArtFrame.getLbl_Count_DayToPrePrevios();
		JScrollBar scrollBarToPrePrevios = DozeArtFrame.getScrollBarToPrePrevios();

		String count_DayToPrevios = "";
		String count_DayToPrePrevios = "";

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date date_MeasurNaw = null;
		Date dateMeasurPrev = null;
		Date dateMeasurPrePrev = null;
		String Date_MeasurNawStr = textField_Date_MeasurNaw.getText();
		String dateMeasurPrevStr = textField_dateMeasurPrev.getText();
		String dateMeasurPrePrevStr = textField_dateMeasurPrePrev.getText();

		long diff;
		long daysBetweenLong;
		if (!Date_MeasurNawStr.isEmpty() && !dateMeasurPrevStr.isEmpty()) {
			try {
				date_MeasurNaw = sdf.parse(Date_MeasurNawStr);
				dateMeasurPrev = sdf.parse(dateMeasurPrevStr);

			} catch (ParseException e) {
				e.printStackTrace();
			}

			diff = date_MeasurNaw.getTime() - dateMeasurPrev.getTime();
			daysBetweenLong = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			count_DayToPrevios = Long.toString(daysBetweenLong);

			if (daysBetweenLong > 700) {
				errorStr = count_DayToPrevios;
			}

		}

		if (!Date_MeasurNawStr.isEmpty() && !dateMeasurPrePrevStr.isEmpty()) {
			try {
				date_MeasurNaw = sdf.parse(Date_MeasurNawStr);
				dateMeasurPrePrev = sdf.parse(dateMeasurPrePrevStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			diff = date_MeasurNaw.getTime() - dateMeasurPrePrev.getTime();
			daysBetweenLong = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			count_DayToPrePrevios = Long.toString(daysBetweenLong);
			if (daysBetweenLong > 700) {
				errorStr = count_DayToPrePrevios;
			}

		}

		lbl_DateNawToPrevios.setText(Date_MeasurNawStr);
		lbl_DateNawToPrePrevios.setText(Date_MeasurNawStr);

		lbl_DateToPrevios.setText(dateMeasurPrevStr);
		lbl_Count_DayToPrevios.setText(count_DayToPrevios);
		
		if(!count_DayToPrevios.isEmpty()) {
			scrollBarToPrevios.setEnabled(true);
			 System.out.println("count_DayToPrevios "+count_DayToPrevios);	
			 max =  Integer.parseInt(count_DayToPrevios);
				max_scrollBarToPrevios = max;
		setScrollBar(scrollBarToPrevios, max);
		}else {
			scrollBarToPrevios.setEnabled(false);
		}

		
		lbl_DateToPrePrevios.setText(dateMeasurPrePrevStr);
		lbl_Count_DayToPrePrevios.setText(count_DayToPrePrevios);
		
		if(!count_DayToPrePrevios.isEmpty()) {
			scrollBarToPrePrevios.setEnabled(true);
			max =  Integer.parseInt(count_DayToPrePrevios);
			max_scrollBarToPrePrevios = max;
			setScrollBar(scrollBarToPrePrevios,max);
		}else {
			scrollBarToPrePrevios.setEnabled(false);
		}
	
		return errorStr;

	}

	static void checkRdbtnSelected() {

		JRadioButton rdbtnRutinen = DozeArtFrame.getRdbtnRutinen();

		JRadioButton rdbtnEdnokratno = DozeArtFrame.getRdbtnEdnokratno();
		JRadioButton rdbtnMnogokratno = DozeArtFrame.getRdbtnMnogokratno();
		JRadioButton rdbtnNeprekasnato = DozeArtFrame.getRdbtnNeprekasnato();
		JRadioButton rdbtnSrTotchka = DozeArtFrame.getRdbtnSrTotchka();

		JLayeredPane layeredPane_Mnogokratno = DozeArtFrame.getLayeredPane_Mnogokratno();
		JPanel panel_FreePanelMeasur = DozeArtFrame.getPanel_FreePanelMeasur();
		JPanel panel_EdnokratnoMeasur = DozeArtFrame.getPanel_EdnokratnoMeasur();
		JPanel panel_MnogokratnoMeasur = DozeArtFrame.getPanel_MnogokratnoMeasur();

		if (rdbtnRutinen.isSelected()) {
			if (rdbtnMnogokratno.isSelected()) {
				layeredPane_Mnogokratno.setLayer(panel_MnogokratnoMeasur, 4, 0);
				panel_FreePanelMeasur.setVisible(false);
				panel_EdnokratnoMeasur.setVisible(false);
				panel_MnogokratnoMeasur.setVisible(true);
			} else {
				layeredPane_Mnogokratno.setLayer(panel_MnogokratnoMeasur, 1, 0);
			}

			if (rdbtnEdnokratno.isSelected()) {
				layeredPane_Mnogokratno.setLayer(panel_EdnokratnoMeasur, 4, 0);
				panel_FreePanelMeasur.setVisible(false);
				panel_EdnokratnoMeasur.setVisible(true);
				panel_MnogokratnoMeasur.setVisible(false);
			} else {
				layeredPane_Mnogokratno.setLayer(panel_EdnokratnoMeasur, 2, 0);
			}

			if (rdbtnSrTotchka.isSelected()) {
				layeredPane_Mnogokratno.setLayer(panel_FreePanelMeasur, 4, 0);
				panel_FreePanelMeasur.setVisible(true);
				panel_EdnokratnoMeasur.setVisible(false);
				panel_MnogokratnoMeasur.setVisible(false);
			} else {
				layeredPane_Mnogokratno.setLayer(panel_FreePanelMeasur, 3, 0);
			}

			if (rdbtnNeprekasnato.isSelected()) {
				layeredPane_Mnogokratno.setLayer(panel_FreePanelMeasur, 4, 0);
				panel_FreePanelMeasur.setVisible(true);
				panel_EdnokratnoMeasur.setVisible(false);
				panel_MnogokratnoMeasur.setVisible(false);

			} else {
				layeredPane_Mnogokratno.setLayer(panel_FreePanelMeasur, 3, 0);
			}
		} else {
			layeredPane_Mnogokratno.setLayer(panel_FreePanelMeasur, 4, 0);
			panel_FreePanelMeasur.setVisible(true);
			panel_EdnokratnoMeasur.setVisible(false);
			panel_MnogokratnoMeasur.setVisible(false);
		}
	}

	static void ActionListenerSetDateByDatePicker_Spetial(JLabel lbl_Icon_StartDate2, JTextField textField_StartDate2) {

		lbl_Icon_StartDate2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e1) {
//				SimpleDateFormat sdf_New = new SimpleDateFormat("dd.MM.yyyy");

				Point pointFrame = lbl_Icon_StartDate2.getLocationOnScreen();
				final JFrame f = new JFrame();
				DatePicker dPicer = new DatePicker(f, false, textField_StartDate2.getText(), pointFrame);
				String str = dPicer.setPickedDate(false);

				textField_StartDate2.setText(str);
				checkCorectdate_Sprcial(textField_StartDate2);
			}
		});

		textField_StartDate2.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {

				checkCorectdate_Sprcial(textField_StartDate2);
			}

		});

	}

	private static void checkCorectdate_Sprcial(JTextField textField_Date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		boolean fl = false;
		JTextField textField_dateMeasur_Start = DozeArtFrame.getTextField_dateMeasur_Start();
		JTextField textField_dateMeasur_End = DozeArtFrame.getTextField_dateMeasur_End();
		if (DatePicker.correctDate(textField_Date.getText(), sdf)
				&& checkDateFieldsStartBeForeEnd(textField_dateMeasur_End, textField_dateMeasur_Start)) {

			fl = true;
		}

		editPanelsByCorectTextField_Special(textField_Date, fl);

	}

	private static void editPanelsByCorectTextField_Special(JTextField textField_StartDate2,
			boolean isCorectTextField) {
		String errorStr = "";
		JLabel lbl_DateErrorInfo_Special = DozeArtFrame.getLbl_DateErrorInfo_Special();
		if (isCorectTextField) {
			errorStr = setDaysAndCountDays_Special();
			if (!errorStr.isEmpty()) {
				errorStr = "<html>Периода от " + errorStr + " дни<br> е по голям от 700</html>";
				isCorectTextField = false;
			}

		} else {
			errorStr = "Некоректна дата";
		}

		lbl_DateErrorInfo_Special.setText(errorStr);

		if (isCorectTextField) {
			textField_StartDate2.setForeground(Color.BLACK);
		} else {
			textField_StartDate2.setForeground(Color.RED);

		}
	}

	private static String setDaysAndCountDays_Special() {

		String errorStr = "";

		JTextField textField_dateMeasur_Start = DozeArtFrame.getTextField_dateMeasur_Start();
		JTextField textField_dateMeasur_End = DozeArtFrame.getTextField_dateMeasur_End();

		String count_Days = "";

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date dateMeasur_Star = null;
		Date dateMeasur_End = null;

		String dateMeasur_StartStr = textField_dateMeasur_Start.getText();
		String dateMeasur_EndStr = textField_dateMeasur_End.getText();

		long diff;
		long daysBetweenLong;
		if (!dateMeasur_StartStr.isEmpty() && !dateMeasur_EndStr.isEmpty()) {
			try {
				dateMeasur_Star = sdf.parse(dateMeasur_StartStr);
				dateMeasur_End = sdf.parse(dateMeasur_EndStr);

			} catch (ParseException e) {
				e.printStackTrace();
			}

			diff = dateMeasur_End.getTime() - dateMeasur_Star.getTime();
			daysBetweenLong = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

			count_Days = Long.toString(daysBetweenLong);

			if (daysBetweenLong > 700) {
				errorStr = count_Days;
			}
		}
		return errorStr;

	}

	static void ActionListenerSetDateByDatePicker(JLabel lbl_Icon_StartDate2, JTextField textField_StartDate2) {

		lbl_Icon_StartDate2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e1) {
				Point pointFrame = lbl_Icon_StartDate2.getLocationOnScreen();
				final JFrame f = new JFrame();
				DatePicker dPicer = new DatePicker(f, false, textField_StartDate2.getText(), pointFrame);
				String str = dPicer.setPickedDate(false);

				textField_StartDate2.setText(str);
				checkCorectdate(textField_StartDate2);
			}
		});

		textField_StartDate2.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {

				checkCorectdate(textField_StartDate2);
			}

		});

	}

	private static void checkCorectdate(JTextField textField_Date) {
		boolean fl = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		JTextField textField_Date_MeasurNaw = DozeArtFrame.getTextField_Date_MeasurNaw();
		JTextField textField_dateMeasurPrev = DozeArtFrame.getTextField_dateMeasurPrev();
		JTextField textField_dateMeasurPrePrev = DozeArtFrame.getTextField_dateMeasurPrePrev();
		if (DatePicker.correctDate(textField_Date.getText(), sdf)
				&& checkDateFieldsStartBeForeEnd(textField_Date_MeasurNaw, textField_dateMeasurPrev)
				&& checkDateFieldsStartBeForeEnd(textField_Date_MeasurNaw, textField_dateMeasurPrePrev)
				&& checkDateFieldsStartBeForeEnd(textField_dateMeasurPrev, textField_dateMeasurPrePrev)) {
			fl = true;
		}
		editPanelsByCorectTextField(textField_Date, fl);
	}

	private static void editPanelsByCorectTextField(JTextField textField_StartDate2, boolean isCorectTextField) {
		String errorStr = "";
		JLabel lbl_DateErrorInfo = DozeArtFrame.getLbl_DateErrorInfo();
		if (isCorectTextField) {
			errorStr = setDaysAndCountDays();
			if (!errorStr.isEmpty()) {
				errorStr = "Периода от " + errorStr + " дни е по голям от 700";
				isCorectTextField = false;
			}

		} else {
			errorStr = "Некоректна дата";
		}

		editEnable_panels(textField_StartDate2, isCorectTextField);

		lbl_DateErrorInfo.setText(errorStr);

		if (isCorectTextField) {
			textField_StartDate2.setForeground(Color.BLACK);
		} else {
			textField_StartDate2.setForeground(Color.RED);

		}
	}

	private static boolean checkDateFieldsStartBeForeEnd(JTextField textField_Date_Last,
			JTextField textField_Date_Previos) {

		if (!textField_Date_Last.getText().trim().isEmpty() && !textField_Date_Previos.getText().trim().isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			Date datSt = null;
			Date datEn = null;
			String date_Previos = textField_Date_Previos.getText();
			String date_Last = textField_Date_Last.getText();
			try {
				datEn = sdf.parse(date_Previos);
				datSt = sdf.parse(date_Last);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return date_Last.equals(date_Previos) || datEn.before(datSt);
		} else {
			return true;
		}
	}

	private static void editEnable_panels(JTextField textField_StartDate2, boolean isCorectTextField) {

		DozeArtFrame.getLbl_Monitoring().setEnabled(isCorectTextField);
		JRadioButton rdbtnRutinen = DozeArtFrame.getRdbtnRutinen();
		DozeArtFrame.getRdbtnSecialen().setEnabled(isCorectTextField);
		rdbtnRutinen.setEnabled(isCorectTextField);

		JLayeredPane layeredPane_Postaplenie = DozeArtFrame.getLayeredPane_Postaplenie();
		JPanel panel_Rutinen_Enable = DozeArtFrame.getPanel_Postaplenie_Free();
		JPanel panel_Rutinen = DozeArtFrame.getPanel_Rutinen();
		JPanel panel_Secialen = DozeArtFrame.getPanel_Secialen();

		JLayeredPane layeredPane_Mnogokratno = DozeArtFrame.getLayeredPane_Mnogokratno();
		JPanel panel_FreePanelMeasur = DozeArtFrame.getPanel_FreePanelMeasur();
		JPanel panel_EdnokratnoMeasur = DozeArtFrame.getPanel_EdnokratnoMeasur();
		JPanel panel_MnogokratnoMeasur = DozeArtFrame.getPanel_MnogokratnoMeasur();

		if (isCorectTextField) {
			layeredPane_Postaplenie.setLayer(panel_Rutinen_Enable, 0, 0);

			if (rdbtnRutinen.isSelected()) {
				layeredPane_Postaplenie.setLayer(panel_Rutinen, 3, 0);
				panel_Rutinen.setVisible(true);
				panel_Secialen.setVisible(false);
			} else {
				layeredPane_Postaplenie.setLayer(panel_Rutinen, 1, 0);
				panel_Secialen.setVisible(true);
				panel_Rutinen.setVisible(false);
			}

			layeredPane_Mnogokratno.setLayer(panel_FreePanelMeasur, 0, 0);
			checkRdbtnSelected();

		} else {
			layeredPane_Postaplenie.setLayer(panel_Rutinen_Enable, 4, 0);
			panel_Rutinen.setVisible(false);
			panel_Secialen.setVisible(false);

			layeredPane_Mnogokratno.setLayer(panel_FreePanelMeasur, 4, 0);
			panel_EdnokratnoMeasur.setVisible(false);
			panel_MnogokratnoMeasur.setVisible(false);
		}

	}

	static void ActionListenerSetDateByDatePicker_MnogokratnoMeasur(JLabel lbl_Icon_StartDate2,
			JTextField textField_StartDate2, JButton btn_MnogokratnoMeasur) {

		lbl_Icon_StartDate2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e1) {
				Point pointFrame = lbl_Icon_StartDate2.getLocationOnScreen();
				final JFrame f = new JFrame();
				DatePicker dPicer = new DatePicker(f, false, textField_StartDate2.getText(), pointFrame);
				String str = dPicer.setPickedDate(false);

				textField_StartDate2.setText(str);
				checkCorectdate_MnogokratnoMeasur(textField_StartDate2, btn_MnogokratnoMeasur);
			}
		});

		textField_StartDate2.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {

				checkCorectdate_MnogokratnoMeasur(textField_StartDate2, btn_MnogokratnoMeasur);
			}

		});

	}

	private static void checkCorectdate_MnogokratnoMeasur(JTextField textField_Date, JButton btn_MnogokratnoMeasur) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		if (DatePicker.correctDate(textField_Date.getText(), sdf)) {
			textField_Date.setForeground(Color.BLACK);
			btn_MnogokratnoMeasur.setEnabled(true);
		} else {
			textField_Date.setForeground(Color.RED);
			btn_MnogokratnoMeasur.setEnabled(false);

		}

	}

	public static void ActionListener_AddDateInJList(JTextField textField_MnogokratnoMeasur,
			JButton btn_MnogokratnoMeasur, JList<String> jlist) {

		btn_MnogokratnoMeasur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCorectDateToList(textField_MnogokratnoMeasur.getText(), jlist);

			}
		});

	}

	private static void addCorectDateToList(String text, JList<String> jlist) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		List<Date> listDateNew = new ArrayList<>();
		String ErrorText = "";

		try {
			Date dat;
			for (int i = 0; i < jlist.getModel().getSize(); i++) {
				dat = sdf.parse((String) jlist.getModel().getElementAt(i));
				listDateNew.add(dat);
			}
			dat = sdf.parse(text);
			listDateNew.add(dat);

			if (listDateNew.size() > 1) {
				SortListDate(listDateNew);
				Date dateFirst = listDateNew.get(0);
				Date dateLast = listDateNew.get(listDateNew.size() - 1);

				long diff = dateLast.getTime() - dateFirst.getTime();
				long daysBetweenLong = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				ErrorText = "<html>Периода " + Long.toString(daysBetweenLong) + " дни<br> е по голям от 700</html>";

				if (daysBetweenLong <= 700) {
					ErrorText = "";
					addDataToJList(jlist, sdf, listDateNew);
				}
			} else {
				addDataToJList(jlist, sdf, listDateNew);
			}
		} catch (ParseException e1) {

		}

		JLabel lbl_ErrorInfo_MnogokratnoMeasur = DozeArtFrame.getLbl_ErrorInfo_MnogokratnoMeasur();
		lbl_ErrorInfo_MnogokratnoMeasur.setText(ErrorText);

	}

	private static void addDataToJList(JList<String> jlist, SimpleDateFormat sdf, List<Date> listDateNew) {
		String[] masive = new String[listDateNew.size()];
		int i = 0;
		for (Date date : listDateNew) {
			masive[i] = sdf.format(date);
			i++;
		}
		jlist.setListData(masive);
	}

	public static void SortListDate(List<Date> listDate) {
		Collections.sort(listDate, new Comparator<Date>() {
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});

	}

	public static void setScrollBar(JScrollBar scrollBar, int max) {
	 scrollBar.setMinimum(0);
	 System.out.println("max set "+max);
	 scrollBar.setMaximum(max); 
	 scrollBar.setValue(max); 
	}
	
	
	public static void scrollBarToPreviosListener(JScrollBar scrollBar, JLabel lbl_Date, JLabel lbl_Count_Day) {
		 scrollBar.setMinimum(0);
		 
		 scrollBar.setMaximum(max_scrollBarToPrevios); 
		int max = scrollBar.getMaximum();
		
		scrollBar.addAdjustmentListener(new AdjustmentListener() { 
    			
		public void adjustmentValueChanged(AdjustmentEvent e) {
			 int value = scrollBar.getValue();
			 System.out.println("max "+max);
			 try {
					String output = setNewDate(value);
					 lbl_Date.setText(output);
					 lbl_Count_Day.setText(value+"");
					 
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			
			
			
		}

		
    }); 

	}
	
	public static void scrollBarToPrePreviosListener(JScrollBar scrollBar, JLabel lbl_Date, JLabel lbl_Count_Day) {
		 scrollBar.setMinimum(0);
		 
		 scrollBar.setMaximum(max_scrollBarToPrePrevios); 
		int max = scrollBar.getMaximum();
		
		scrollBar.addAdjustmentListener(new AdjustmentListener() { 
   			
		public void adjustmentValueChanged(AdjustmentEvent e) {
			 int value = scrollBar.getValue();
			 System.out.println("max "+max);
			 try {
					String output = setNewDate(value);
					 lbl_Date.setText(output);
					 lbl_Count_Day.setText(value+"");
					 
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			
			
			
		}

		
   }); 

	}
	
	
	private static String setNewDate( int value)
			throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		JTextField textField_Date_MeasurNaw = DozeArtFrame.getTextField_Date_MeasurNaw();
		
		System.out.println("value "+value);
		Date date = sdf.parse(textField_Date_MeasurNaw.getText());
		System.out.println("date "+date);
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
			System.out.println("cal "+cal);
		cal.add(Calendar.DATE, -value);
		 String output = sdf.format(cal.getTime());
		 System.out.println("output "+output);
		return output;
	}


	
	public static void ActionListenerChoiceNuclide(Choice choice_NuclideName, Choice choice_AMAD) {
		
		choice_NuclideName.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				choice_AMAD.removeAll();
				Object[][] masive = SetMasiveDadaByNuclide(choice_NuclideName.getSelectedItem());
				List<String> listAMAD = SetListAMAD( masive);
				for (String string : listAMAD) {
					choice_AMAD.add(string);
				}

			}
		});
		
	} 
	
}
