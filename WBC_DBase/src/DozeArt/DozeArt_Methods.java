package DozeArt;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import DatePicker.DatePicker;

public class DozeArt_Methods {

	private static List<List<List<String>>> listData;
	private static Object[][] masiveDataForReport = null;

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
				clearCalculationData();
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
				clearCalculationData();
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
				clearCalculationData();
			}
		});

		rdbtnSrTotchka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkRdbtnSelected();
				clearCalculationData();
			}
		});

		rdbtnEdnokratno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkRdbtnSelected();
				clearCalculationData();
			}
		});

		rdbtnMnogokratno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkRdbtnSelected();
				clearCalculationData();
			}
		});
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
				clearCalculationData();
			}
		});

		textField_StartDate2.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {

				checkCorectdate_Sprcial(textField_StartDate2);
				clearCalculationData();
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

		String dateMeasur_StartStr = textField_dateMeasur_Start.getText();
		String dateMeasur_EndStr = textField_dateMeasur_End.getText();

		String count_Days = "";

		if (!dateMeasur_StartStr.isEmpty() && !dateMeasur_EndStr.isEmpty()) {

			count_Days = getCoundDays(dateMeasur_StartStr, dateMeasur_EndStr);
			if (count_Days != null) {
				long daysBetweenLong = Long.parseLong(count_Days);

				if (daysBetweenLong > 700) {
					errorStr = count_Days;
				}
			}
		}
		return errorStr;

	}

	private static String getCoundDays(String dateMeasur_StartStr, String dateMeasur_EndStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date dateMeasur_Star = null;
		Date dateMeasur_End = null;

		long diff;
		long daysBetweenLong;
		String count_Days = null;
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
		}
		return count_Days;
	}

	public static void ActionListenerSetDateByDatePicker(JLabel lbl_Icon_StartDate2, JTextField textField_StartDate2) {

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
				clearCalculationData();
			}
		});

		textField_StartDate2.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {

				checkCorectdate(textField_StartDate2);
				clearCalculationData();
			}

		});

	}

	static void checkCorectdate(JTextField textField_Date) {
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

	static void editPanelsByCorectTextField(JTextField textField_StartDate2, boolean isCorectTextField) {
		String errorStr = "";
		JLabel lbl_DateErrorInfo = DozeArtFrame.getLbl_DateErrorInfo();
		if (isCorectTextField) {
			errorStr = ScrollBar_Methods.setDaysAndCountDays();
			if (!errorStr.isEmpty()) {
				errorStr = "Периода от " + errorStr + " дни е по голям от 700";
				isCorectTextField = false;
			}

		} else {
			errorStr = "Некоректна дата";
		}

		editEnable_panels(isCorectTextField);

		lbl_DateErrorInfo.setText(errorStr);

		if (isCorectTextField) {
			textField_StartDate2.setForeground(Color.BLACK);
		} else {
			textField_StartDate2.setForeground(Color.RED);

		}
	}

	public static boolean checkDateFieldsStartBeForeEnd(JTextField textField_Date_Last,
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

	private static void editEnable_panels(boolean isCorectTextField) {

		DozeArtFrame.getLbl_Monitoring().setEnabled(isCorectTextField);
		JRadioButton rdbtnRutinen = DozeArtFrame.getRdbtnRutinen();
		DozeArtFrame.getRdbtnSpecialen().setEnabled(isCorectTextField);
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
			DozeArtFrame.getBtn_Calculation().setEnabled(true);

		} else {
			layeredPane_Postaplenie.setLayer(panel_Rutinen_Enable, 4, 0);
			panel_Rutinen.setVisible(false);
			panel_Secialen.setVisible(false);

			layeredPane_Mnogokratno.setLayer(panel_FreePanelMeasur, 4, 0);
			panel_EdnokratnoMeasur.setVisible(false);
			panel_MnogokratnoMeasur.setVisible(false);
			DozeArtFrame.getBtn_Calculation().setEnabled(false);

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
				clearCalculationData();
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
				clearCalculationData();
			}
		});

	}

	private static void addCorectDateToList(String text, JList<String> jlist) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		List<Date> listDateNew = new ArrayList<>();
		String ErrorText = "";

		try {
			listDateNew = readDataFromJLIst(jlist);

			Date dat = sdf.parse(text);
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
					addDataToJList(jlist, listDateNew);
				}
			} else {
				addDataToJList(jlist, listDateNew);
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		JLabel lbl_ErrorInfo_MnogokratnoMeasur = DozeArtFrame.getLbl_ErrorInfo_MnogokratnoMeasur();
		lbl_ErrorInfo_MnogokratnoMeasur.setText(ErrorText);

	}

	private static List<Date> readDataFromJLIst(JList<String> jlist) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		List<Date> listDateNew = new ArrayList<>();
		try {
			Date dat;
			for (int i = 0; i < jlist.getModel().getSize(); i++) {
				dat = sdf.parse((String) jlist.getModel().getElementAt(i));
				listDateNew.add(dat);
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return listDateNew;
	}

	static void addDataToJList(JList<String> jlist, List<Date> listDateNew) {
		String[] masive = new String[listDateNew.size()];
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
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

	public static void ActionListenerChoiceNuclide(Choice choice_NuclideName, Choice choice_AMAD) {

		choice_NuclideName.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String nuclideName = choice_NuclideName.getSelectedItem();
				setItemByNuclideName(nuclideName, choice_AMAD);
				clearCalculationData();
			}

		});

	}

	static void setItemByNuclideName(String nuclideName, Choice choice_AMAD) {
		choice_AMAD.removeAll();

		Object[][] masive = SetMasiveDadaByNuclide(nuclideName);
		List<String> listAMAD = SetListAMAD(masive);
		String dafoltAMD = listAMAD.get(0);
		for (String string : listAMAD) {
			if (string.contains("*")) {
				dafoltAMD = string.replace("*", "");
			}
			choice_AMAD.add(string.replace("*", ""));
		}

		choice_AMAD.select(dafoltAMD);
	}

	public static void clearCalculationData() {
		int countNuclide = DozeArtFrame.getCountNuclide();
		for (int i = 0; i < countNuclide; i++) {
			DozeArtFrame.getLbl_PostaplenieBq()[i].setText("");
			DozeArtFrame.getLbl_PostaplenieMCi()[i].setText("");
			DozeArtFrame.getLbl_GGPCalc()[i].setText("");
			DozeArtFrame.getLbl_DozaNuclide()[i].setText("");
		}
		DozeArtFrame.getLbl_DozeAll().setText("");
		DozeArtFrame.getBtn_Report().setEnabled(false);
		DozeArtFrame.getBtn_Export().setEnabled(false);
	}

	public static void ActionListenerCalculationBTN() {
		String[][] masive = readMasiveDataFromDozeArtFrame();
		System.out.println("masive " + masive.length);
		double sumDoze = 0;
		int count = 0;
		for (int i = 0; i < masive.length; i++) {
			if (!masive[i][2].isEmpty()) {
				count++;
			}
		}
		masiveDataForReport = null;
		if (count > 0) {
			masiveDataForReport = new Object[count][21];

//		Calculate Rutinen - Sr.Tochka **************************************************************
			if (DozeArtFrame.getRdbtnRutinen().isSelected() && DozeArtFrame.getRdbtnSrTotchka().isSelected()) {
				sumDoze = checkdataToCalcMiddlePoint(masive, sumDoze);
			}

//			Calculate Rutinen - Ednokratno **************************************************************
			if (DozeArtFrame.getRdbtnRutinen().isSelected() && DozeArtFrame.getRdbtnEdnokratno().isSelected()) {
				sumDoze = checkdataToCalcEdnokratno(masive, sumDoze);
			}

//			Calculate Speciale **************************************************************
			if (DozeArtFrame.getRdbtnSpecialen().isSelected()) {
				checkdataToCalcSpeciale(masive, sumDoze);
			}

		} else {
			MessageDialog("Няма измерени активности за изчисляване");
		}
	}

	private static void checkdataToCalcSpeciale(String[][] masive, double sumDoze) {
		if (!DozeArtFrame.getTextField_Date_MeasurNaw().getText().isEmpty()
				&& !DozeArtFrame.getTextField_dateMeasurPrev().getText().isEmpty()
				&& !DozeArtFrame.getTextField_dateMeasurPrePrev().getText().isEmpty()
				&& !DozeArtFrame.getTextField_dateMeasur_Start().getText().isEmpty()
				&& !DozeArtFrame.getTextField_dateMeasur_End().getText().isEmpty()) {

			int k = 0;
			for (int i = 0; i < masive.length; i++) {
				if (!masive[i][2].isEmpty()) {

					masiveDataForReport[k] = calcSpeciale(masive[i]);

					setDataToFields(k, i);

					sumDoze += (double) masiveDataForReport[k][20];
					k++;
				}

			}
			DozeArtFrame.getLbl_DozeAll().setText(String.format("%,.2f", sumDoze).replace(",", "."));
		} else {
			MessageDialog("Въведете коректно всички дати");
		}
	}

	private static double checkdataToCalcEdnokratno(String[][] masive, double sumDoze) {
		if (!DozeArtFrame.getTextField_Date_MeasurNaw().getText().isEmpty()
				&& !DozeArtFrame.getTextField_dateMeasurPrev().getText().isEmpty()
				&& !DozeArtFrame.getTextField_dateMeasurPrePrev().getText().isEmpty()) {

			int k = 0;
			for (int i = 0; i < masive.length; i++) {
				if (!masive[i][2].isEmpty()) {

					masiveDataForReport[k] = calcAcutePoint(masive[i]);

					setDataToFields(k, i);

					sumDoze += (double) masiveDataForReport[k][20];
					k++;
				}

			}
			DozeArtFrame.getLbl_DozeAll().setText(String.format("%,.2f", sumDoze).replace(",", "."));
		} else {
			MessageDialog("Въведете коректно и трите дати");
		}
		return sumDoze;
	}

	private static double checkdataToCalcMiddlePoint(String[][] masive, double sumDoze) {
		if (!DozeArtFrame.getTextField_Date_MeasurNaw().getText().isEmpty()
				&& !DozeArtFrame.getTextField_dateMeasurPrev().getText().isEmpty()
				&& !DozeArtFrame.getTextField_dateMeasurPrePrev().getText().isEmpty()) {

			int k = 0;
			for (int i = 0; i < masive.length; i++) {
				if (!masive[i][2].isEmpty()) {

					masiveDataForReport[k] = calcMiddlePoint(masive[i]);

					setDataToFields(k, i);

					sumDoze += (double) masiveDataForReport[k][20];
					k++;
				}

			}
			DozeArtFrame.getLbl_DozeAll().setText(String.format("%,.2f", sumDoze).replace(",", "."));
		} else {
			MessageDialog("Въведете коректно и трите дати");
		}
		return sumDoze;
	}

	private static void setDataToFields(int count, int i) {
		DozeArtFrame.getLbl_PostaplenieBq()[i]
				.setText(String.format("%,.0f", masiveDataForReport[count][17]).replace(",", "."));
		DozeArtFrame.getLbl_PostaplenieMCi()[i]
				.setText(String.format("%,.4f", masiveDataForReport[count][18]).replace(",", "."));
		DozeArtFrame.getLbl_GGPCalc()[i]
				.setText(String.format("%,.3f", masiveDataForReport[count][19]).replace(",", "."));
		DozeArtFrame.getLbl_DozaNuclide()[i]
				.setText(String.format("%,.3f", masiveDataForReport[count][20]).replace(",", "."));
	}

	public static void MessageDialog(String str) {

		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, str, "Грешни Дати", JOptionPane.PLAIN_MESSAGE, null);

	}

	private static Object[] calcMiddlePoint(String[] masive) {

		JTextField textField_Date_MeasurNaw = DozeArtFrame.getTextField_Date_MeasurNaw();
		JTextField textField_dateMeasurPrev = DozeArtFrame.getTextField_dateMeasurPrev();
		JTextField textField_dateMeasurPrePrev = DozeArtFrame.getTextField_dateMeasurPrePrev();
		double activity = 0;
		double previosIntake = 0;
		String act = masive[4] + "";

		Object[] CalculateData = new Object[21];

		if (masive[3].isEmpty()) {
			masive[3] = "0";
		}

		if (act.equals("Bq")) {
			activity = Double.parseDouble(masive[2]);
			previosIntake = Double.parseDouble(masive[3]);

		}
		if (act.equals("µCi")) {
			activity = Double.parseDouble(masive[2]) * 37000;
			previosIntake = Double.parseDouble(masive[3]) * 37000;
		}
		Object[][] masiveDataIRF = SetMasiveDadaByNuclide(masive[0]);
		double DCF = 0;
		double[] masiveIRF = null;
		for (int i = 0; i < masiveDataIRF.length; i++) {
			System.out.println(masiveDataIRF[i][0] + "  " + masive[1]);
			if ((((String) masiveDataIRF[i][0]).replace("*", "")).equals(masive[1])) {
				DCF = Double.parseDouble(masiveDataIRF[i][1] + "");
				int k = 0;
				masiveIRF = new double[masiveDataIRF[0].length - 2];
				for (int j = 2; j < masiveDataIRF[0].length; j++) {
					masiveIRF[k] = Double.parseDouble(masiveDataIRF[i][j] + "");
					k++;
				}
			}
		}
		String dateMeasur_StartStr = textField_dateMeasurPrev.getText();
		String dateMeasur_EndStr = textField_Date_MeasurNaw.getText();

		String count_DaysPrev = getCoundDays(dateMeasur_StartStr, dateMeasur_EndStr);
		int interval = Integer.parseInt(count_DaysPrev) / 2;

		dateMeasur_EndStr = textField_dateMeasurPrePrev.getText();
		String count_DaysPrePrev = getCoundDays(dateMeasur_EndStr, dateMeasur_StartStr);
		int previosInterval = (Integer.parseInt(count_DaysPrePrev) / 2) + Integer.parseInt(count_DaysPrev);

		double intake = (activity - (previosIntake * masiveIRF[previosInterval])) / masiveIRF[interval];

		if (intake < 0)
			intake = 0;
		double doze = intake * DCF * 1000;

		CalculateData[0] = getTypeMonitoring(); // вид мониторинг
		CalculateData[1] = "Средна точка"; // вид изчисление

		CalculateData[2] = masive[0]; // Нуклид
		CalculateData[3] = masive[1]; // АМАД
		CalculateData[4] = DCF; // Коефициент DCF

		CalculateData[5] = textField_Date_MeasurNaw.getText(); // дата на измерване
		CalculateData[6] = textField_dateMeasurPrev.getText(); // дата на предишно измерване
		CalculateData[7] = textField_dateMeasurPrePrev.getText(); // дата на измерване преди предишното

		CalculateData[8] = count_DaysPrev; // Бр. дни до предходно измерване
		CalculateData[9] = interval; // Бр. дни до средата на първия интервал
		CalculateData[10] = masiveIRF[interval]; // Коефициент IRF1 за първия интервал

		CalculateData[11] = count_DaysPrePrev; // Бр. дни от предходно измерване до измерване преди предходното
		CalculateData[12] = Integer.parseInt(count_DaysPrePrev) / 2; // Бр. дни до средата на втория интервал
		CalculateData[13] = previosInterval; // Бр. дни от средата на втория интервал до датата на измерване
		CalculateData[14] = masiveIRF[previosInterval]; // Коефициент IRF2 за втория интервал

		CalculateData[15] = activity; // Измерена активност Bq
		CalculateData[16] = previosIntake; // Предишно постъпление Bq

		CalculateData[17] = intake; // постъпление Bq
		CalculateData[18] = intake / 37000; // постъпление мCi
		CalculateData[19] = (doze / 20) * 100; // ГГП %
		CalculateData[20] = doze; // Доза mSv

		return CalculateData;
	}

	private static Object[] calcAcutePoint(String[] masive) {

		JTextField textField_Date_MeasurNaw = DozeArtFrame.getTextField_Date_MeasurNaw();
		JTextField textField_dateMeasurPrev = DozeArtFrame.getTextField_dateMeasurPrev();
		JTextField textField_dateMeasurPrePrev = DozeArtFrame.getTextField_dateMeasurPrePrev();
		JLabel lbl_DateToPrevios = DozeArtFrame.getLbl_DateToPrevios();
		JLabel lbl_DateToPrePrevios = DozeArtFrame.getLbl_DateToPrePrevios();

		double activity = 0;
		double previosIntake = 0;
		String act = masive[4] + "";

		Object[] CalculateData = new Object[21];

		if (masive[3].isEmpty()) {
			masive[3] = "0";
		}

		if (act.equals("Bq")) {
			activity = Double.parseDouble(masive[2]);
			previosIntake = Double.parseDouble(masive[3]);

		}
		if (act.equals("µCi")) {
			activity = Double.parseDouble(masive[2]) * 37000;
			previosIntake = Double.parseDouble(masive[3]) * 37000;
		}
		Object[][] masiveDataIRF = SetMasiveDadaByNuclide(masive[0]);
		double DCF = 0;
		double[] masiveIRF = null;
		for (int i = 0; i < masiveDataIRF.length; i++) {
			if ((((String) masiveDataIRF[i][0]).replace("*", "")).equals(masive[1])) {
				DCF = Double.parseDouble(masiveDataIRF[i][1] + "");
				int k = 0;
				masiveIRF = new double[masiveDataIRF[0].length - 2];
				for (int j = 2; j < masiveDataIRF[0].length; j++) {
					masiveIRF[k] = Double.parseDouble(masiveDataIRF[i][j] + "");
					k++;
				}
			}
		}
//		String dateMeasur_StartStr = textField_dateMeasurPrev.getText();
//		
//		String dateMeasur_EndStr = lbl_DateToPrevios.getText();

		String count_DaysPrev = getCoundDays(lbl_DateToPrevios.getText(), textField_Date_MeasurNaw.getText());
		int interval = Integer.parseInt(count_DaysPrev);

//		String dateMeasur_EndStr = textField_dateMeasurPrePrev.getText();
		System.out.println(lbl_DateToPrePrevios.getText() + "  " + textField_dateMeasurPrev.getText());
		String count_DaysPre = getCoundDays(lbl_DateToPrePrevios.getText(), textField_dateMeasurPrev.getText());
		String count_DaysPrePrev = getCoundDays(textField_dateMeasurPrev.getText(), textField_Date_MeasurNaw.getText());

		int previosInterval = Integer.parseInt(count_DaysPrePrev) + Integer.parseInt(count_DaysPre);

		double intake = (activity - (previosIntake * masiveIRF[previosInterval])) / masiveIRF[interval];

		if (intake < 0)
			intake = 0;
		double doze = intake * DCF * 1000;

		CalculateData[0] = getTypeMonitoring(); // вид мониторинг
		CalculateData[1] = "Средна точка"; // вид изчисление

		CalculateData[2] = masive[0]; // Нуклид
		CalculateData[3] = masive[1]; // АМАД
		CalculateData[4] = DCF; // Коефициент DCF

		CalculateData[5] = textField_Date_MeasurNaw.getText(); // дата на измерване
		CalculateData[6] = textField_dateMeasurPrev.getText(); // дата на предишно измерване
		CalculateData[7] = textField_dateMeasurPrePrev.getText(); // дата на измерване преди предишното

		CalculateData[8] = count_DaysPrev; // Бр. дни до предходно измерване
		CalculateData[9] = interval; // Бр. дни до средата на първия интервал
		CalculateData[10] = masiveIRF[interval]; // Коефициент IRF1 за първия интервал

		CalculateData[11] = count_DaysPrePrev; // Бр. дни от предходно измерване до измерване преди предходното
		CalculateData[12] = Integer.parseInt(count_DaysPrePrev) / 2; // Бр. дни до средата на втория интервал
		CalculateData[13] = previosInterval; // Бр. дни от средата на втория интервал до датата на измерване
		CalculateData[14] = masiveIRF[previosInterval]; // Коефициент IRF2 за втория интервал

		CalculateData[15] = activity; // Измерена активност Bq
		CalculateData[16] = previosIntake; // Предишно постъпление Bq

		CalculateData[17] = intake; // постъпление Bq
		CalculateData[18] = intake / 37000; // постъпление мCi
		CalculateData[19] = (doze / 20) * 100; // ГГП %
		CalculateData[20] = doze; // Доза mSv

		return CalculateData;
	}

	private static Object[] calcSpeciale(String[] masive) {

		JTextField textField_Date_MeasurNaw = DozeArtFrame.getTextField_Date_MeasurNaw();
		JTextField textField_dateMeasurPrev = DozeArtFrame.getTextField_dateMeasurPrev();
		JTextField textField_dateMeasurPrePrev = DozeArtFrame.getTextField_dateMeasurPrePrev();
		JTextField textField_dateMeasurStart = DozeArtFrame.getTextField_dateMeasur_Start();
		JTextField textField_dateMeasurEnd = DozeArtFrame.getTextField_dateMeasur_End();

		double activity = 0;
		double previosIntake = 0;
		String act = masive[4] + "";

		Object[] CalculateData = new Object[21];

		if (masive[3].isEmpty()) {
			masive[3] = "0";
		}

		if (act.equals("Bq")) {
			activity = Double.parseDouble(masive[2]);
			previosIntake = Double.parseDouble(masive[3]);

		}
		if (act.equals("µCi")) {
			activity = Double.parseDouble(masive[2]) * 37000;
			previosIntake = Double.parseDouble(masive[3]) * 37000;
		}
		Object[][] masiveDataIRF = SetMasiveDadaByNuclide(masive[0]);
		double DCF = 0;
		double[] masiveIRF = null;
		for (int i = 0; i < masiveDataIRF.length; i++) {
			if ((((String) masiveDataIRF[i][0]).replace("*", "")).equals(masive[1])) {
				DCF = Double.parseDouble(masiveDataIRF[i][1] + "");
				int k = 0;
				masiveIRF = new double[masiveDataIRF[0].length - 2];
				for (int j = 2; j < masiveDataIRF[0].length; j++) {
					masiveIRF[k] = Double.parseDouble(masiveDataIRF[i][j] + "");
					k++;
				}
			}
		}
		String dateMeasur_StartStr = textField_dateMeasurStart.getText();
		String dateMeasur_EndStr = textField_dateMeasurEnd.getText();

		String dateMeasur_Naw = textField_Date_MeasurNaw.getText();
		String dateMeasur_Prev = textField_dateMeasurPrev.getText();
		String dateMeasur_PrePrev = textField_dateMeasurPrePrev.getText();

		String count_DaysPrev = getCoundDays(dateMeasur_StartStr, dateMeasur_EndStr);
		String count_DaysNaw = getCoundDays(dateMeasur_EndStr, dateMeasur_Naw);
		int interval = (Integer.parseInt(count_DaysPrev) / 2) + Integer.parseInt(count_DaysNaw);

		String count_DaysPrePrev = getCoundDays(dateMeasur_PrePrev, dateMeasur_Prev);
		String count_DaysPrevNaw = getCoundDays(dateMeasur_Prev, dateMeasur_Naw);
		int previosInterval = (Integer.parseInt(count_DaysPrePrev) / 2) + Integer.parseInt(count_DaysPrevNaw);

		double intake = (activity - (previosIntake * masiveIRF[previosInterval])) / masiveIRF[interval];

		if (intake < 0)
			intake = 0;
		double doze = intake * DCF * 1000;

		CalculateData[0] = getTypeMonitoring(); // вид мониторинг
		CalculateData[1] = "Средна точка"; // вид изчисление

		CalculateData[2] = masive[0]; // Нуклид
		CalculateData[3] = masive[1]; // АМАД
		CalculateData[4] = DCF; // Коефициент DCF

		CalculateData[5] = textField_Date_MeasurNaw.getText(); // дата на измерване
		CalculateData[6] = textField_dateMeasurPrev.getText(); // дата на предишно измерване
		CalculateData[7] = textField_dateMeasurPrePrev.getText(); // дата на измерване преди предишното

		CalculateData[8] = count_DaysPrev; // Бр. дни до предходно измерване
		CalculateData[9] = interval; // Бр. дни до средата на първия интервал
		CalculateData[10] = masiveIRF[interval]; // Коефициент IRF1 за първия интервал

		CalculateData[11] = count_DaysPrePrev; // Бр. дни от предходно измерване до измерване преди предходното
		CalculateData[12] = Integer.parseInt(count_DaysPrePrev) / 2; // Бр. дни до средата на втория интервал
		CalculateData[13] = previosInterval; // Бр. дни от средата на втория интервал до датата на измерване
		CalculateData[14] = masiveIRF[previosInterval]; // Коефициент IRF2 за втория интервал

		CalculateData[15] = activity; // Измерена активност Bq
		CalculateData[16] = previosIntake; // Предишно постъпление Bq

		CalculateData[17] = intake; // постъпление Bq
		CalculateData[18] = intake / 37000; // постъпление мCi
		CalculateData[19] = (doze / 20) * 100; // ГГП %
		CalculateData[20] = doze; // Доза mSv

		return CalculateData;
	}

	public static String createReportDozeArt() {
		String report = DozeArtFrame.getLbl_InfoPersonMeasur().getText() + "\n";
		report += "-------------------------------------------------------------------" + "\n";
		report += "Вид мониторинг: " + masiveDataForReport[0][0] + "\n"; // вид мониторинг
		report += "Вид изчисление: " + masiveDataForReport[0][1] + "\n"; // вид изчисление

		report += "Дата на измерване: " + masiveDataForReport[0][5] + "\n"; // дата на измерване
		report += "Дата на предишно измерване: " + masiveDataForReport[0][6] + "\n"; // дата на предишно измерване
		report += "Дата на измерване преди предишното: " + masiveDataForReport[0][7] + "\n"; // дата на измерване преди
																								// предишното

		report += "Бр. дни до предходно измерване: " + masiveDataForReport[0][8] + "\n"; // Бр. дни до предходно
																							// измерване
		report += "Бр. дни до средата на първия интервал: " + masiveDataForReport[0][9] + "\n"; // Бр. дни до средата на
																								// първия интервал

		report += "Бр. дни от предходно измерване до измерване преди предходното: " + masiveDataForReport[0][11] + "\n"; // Бр.
																															// дни
																															// от
																															// предходно
																															// измерване
																															// до
																															// измерване
																															// преди
																															// предходното
		report += "Бр. дни до средата на втория интервал: " + masiveDataForReport[0][12] + "\n"; // Бр. дни до средата
																									// на втория
																									// интервал
		report += "Бр. дни от средата на втория интервал до датата на измерване: " + masiveDataForReport[0][13] + "\n"; // Бр.
																														// дни
																														// от
																														// средата
																														// на
																														// втория
																														// интервал
																														// до
																														// датата
																														// на
																														// измерване

		report += "-------------------------------------------------------------------" + "\n";

		for (int i = 0; i < masiveDataForReport.length; i++) {

			report += "Нуклид: " + masiveDataForReport[i][2] + "\n"; // Нуклид
			report += "АМАД: " + masiveDataForReport[i][3] + "\n"; // АМАД
			report += "Коефициент DCF: " + masiveDataForReport[i][4] + "\n"; // Коефициент DCF
			report += "Коефициент IRF1 за първия интервал[" + masiveDataForReport[i][9] + " дни]: "
					+ masiveDataForReport[i][10] + "\n"; // Коефициент IRF1 за първия интервал
			report += "Коефициент IRF2 за втория интервал[" + masiveDataForReport[i][13] + " дни]: "
					+ masiveDataForReport[i][14] + "\n"; // Коефициент IRF2 за втория интервал
			report += "Измерена активност: " + masiveDataForReport[i][15] + " Bq, ( "
					+ String.format("%,.4f", (double) masiveDataForReport[i][15] / 37000) + " мCi)" + "\n"; // Предишно
																											// постъпление
																											// Bq

			report += "постъпление Bq: " + masiveDataForReport[i][17] + "\n"; // постъпление Bq
			report += "постъпление мCi: " + masiveDataForReport[i][18] + "\n"; // постъпление мCi
			report += "ГГП %: " + masiveDataForReport[i][19] + "\n"; // ГГП %
			report += "Доза mSv: " + masiveDataForReport[i][20] + "\n"; // Доза mSv
			report += "-------------------------------------------------------------------" + "\n";
		}
		report += "Обща Доза mSv: " + DozeArtFrame.getLbl_DozeAll().getText();

		System.out.println(report);
		return report;
	}

	private static String getTypeMonitoring() {
		JRadioButton rdbtnRutinen = DozeArtFrame.getRdbtnRutinen();
		return rdbtnRutinen.isSelected() ? "Рутинен" : "Специален";
	}

//	  If optSpecial.Value Then
//      StartDate = CVDate(txtStart.Text)
//      EndDate = CVDate(txtEnd.Text)
//      Interval = CInt(DateDiff("d", Format(StartDate, "Short Date"), Format(EndDate, "Short Date")) / 2) + CInt(DateDiff("d", Format(EndDate, "Short Date"), Format(DateCurrent, "Short Date")))
//      PreviousInterval = CInt(DateDiff("d", Format(DateBeforePrevious, "Short Date"), Format(DatePrevious, "Short Date")) / 2) + CInt(DateDiff("d", Format(DatePrevious, "Short Date"), Format(DateCurrent, "Short Date")))
//      Intake(1) = (Activity(1) - PreviousIntake(1) * IRF(1, PreviousInterval, cboClass1.ListIndex + 1, Val(lblAmad.Caption))) / IRF(1, Interval, cboClass1.ListIndex + 1, Val(lblAmad.Caption))
//      If Intake(1) < 0 Then Intake(1) = 0
//      Dose(1) = Intake(1) * DCF(1, cboClass1.ListIndex + 1, spbAmad.Value) * 1000
//      GoTo item1
//  End If
//  

//  If optAcute.Value = True Then
//    Interval = CInt(DateDiff("d", Format(CVDate(lblIntakeDate), "Short Date"), Format(DateCurrent, "Short Date")))
//    PreviousInterval = CInt(DateDiff("d", Format(CVDate(lblPreviousIntakeDate), "Short Date"), Format(DatePrevious, "Short Date"))) + CInt(DateDiff("d", Format(DatePrevious, "Short Date"), Format(DateCurrent, "Short Date")))
//    Intake(1) = (Activity(1) - PreviousIntake(1) * IRF(1, PreviousInterval, cboClass1.ListIndex + 1, Val(lblAmad.Caption))) / IRF(1, Interval, cboClass1.ListIndex + 1, Val(lblAmad.Caption))
//    If Intake(1) < 0 Then Intake(1) = 0
//    Dose(1) = Intake(1) * DCF(1, cboClass1.ListIndex + 1, spbAmad.Value) * 1000
//  End If
//  

//  If optMultiple.Value Then
//    S = 0
//    For I = 0 To lstDates.ListCount - 1
//      Interval = CInt(DateDiff("d", Format(CVDate(lstDates.List(I)), "Short Date"), Format(DateCurrent, "Short Date")))
//      S = S + IRF(1, Interval, cboClass1.ListIndex + 1, Val(lblAmad.Caption))
//    Next I
//    S = S / lstDates.ListCount
//    PreviousInterval = CInt(DateDiff("d", Format(DateBeforePrevious, "Short Date"), Format(DatePrevious, "Short Date")) / 2) + CInt(DateDiff("d", Format(DatePrevious, "Short Date"), Format(DateCurrent, "Short Date")))
//    Intake(1) = (Activity(1) - PreviousIntake(1) * IRF(1, PreviousInterval, cboClass1.ListIndex + 1, Val(lblAmad.Caption))) / S
//    If Intake(1) < 0 Then Intake(1) = 0
//    Dose(1) = Intake(1) * DCF(1, cboClass1.ListIndex + 1, spbAmad.Value) * 1000
//  End If
//  

//  If optMiddlePoint.Value Then
//    Interval = CInt(DateDiff("d", Format(CVDate(DatePrevious), "Short Date"), Format(DateCurrent, "Short Date")) / 2)
//    PreviousInterval = CInt(DateDiff("d", Format(DateBeforePrevious, "Short Date"), Format(DatePrevious, "Short Date")) / 2) + CInt(DateDiff("d", Format(DatePrevious, "Short Date"), Format(DateCurrent, "Short Date")))
//    Intake(1) = (Activity(1) - PreviousIntake(1) * IRF(1, PreviousInterval, cboClass1.ListIndex + 1, Val(lblAmad.Caption))) / IRF(1, Interval, cboClass1.ListIndex + 1, Val(lblAmad.Caption))
//    If Intake(1) < 0 Then Intake(1) = 0
//    Dose(1) = Intake(1) * DCF(1, cboClass1.ListIndex + 1, spbAmad.Value) * 1000
//  End If
//  

//  If optContinuous.Value Then
//    S = 0
//    For I = 0 To CInt(DateDiff("d", Format(CVDate(DatePrevious), "Short Date"), Format(DateCurrent, "Short Date")))
//        S = S + IRF(1, I, cboClass1.ListIndex + 1, Val(lblAmad.Caption))
//    Next I
//    S = S / CInt(DateDiff("d", Format(CVDate(DatePrevious), "Short Date"), Format(DateCurrent, "Short Date")))
//    PreviousInterval = CInt(DateDiff("d", Format(DateBeforePrevious, "Short Date"), Format(DatePrevious, "Short Date")) / 2) + CInt(DateDiff("d", Format(DatePrevious, "Short Date"), Format(DateCurrent, "Short Date")))
//    Intake(1) = (Activity(1) - PreviousIntake(1) * IRF(1, PreviousInterval, cboClass1.ListIndex + 1, Val(lblAmad.Caption))) / S
//    If Intake(1) < 0 Then Intake(1) = 0
//    Dose(1) = Intake(1) * DCF(1, cboClass1.ListIndex + 1, spbAmad.Value) * 1000
//  End If

//	Select Case cboAunits.Value
//    Case "Bq"
//      lblA1.Caption = Format(Activity(1), "##,##0")
//    Case "microCi"
//      lblA1.Caption = Format(Activity(1) / 37000#, "0.000")
//  End Select
//  
//  Select Case cboIntakeUnit.Value
//    Case "Bq"
//        lblIntake1.Caption = Format(Intake(1), "##,##0")
//    Case "microCi"
//      lblIntake1.Caption = Format(Intake(1) / 37000#, "0.000")
//    Case "%ALI"
//      lblIntake1.Caption = Format(((Intake(1) * DCF(1, cboClass1.ListIndex + 1, spbAmad.Value) * 1000) / 20) * 100, "0.00")
//  End Select
//  lblDose1.Caption = Format(Dose(1), "0.00")

	static String[][] readMasiveDataFromDozeArtFrame() {
		int countNuclide = DozeArtFrame.getCountNuclide();
		String[][] masive = new String[countNuclide][5];

		for (int i = 0; i < countNuclide; i++) {

			masive[i][0] = DozeArtFrame.getChoice_NuclideName()[i].getSelectedItem();
			masive[i][1] = DozeArtFrame.getChoice_AMAD()[i].getSelectedItem();
			masive[i][2] = DozeArtFrame.getTextField_Activity()[i].getText();
			masive[i][3] = DozeArtFrame.getTextField_PreviosPostaplenie()[i].getText();
			masive[i][4] = (String) DozeArtFrame.getCmbBox_ActivityDimencion()[i].getSelectedItem();

		}
		return masive;
	}

	private static Object[] readMasiveDataFromDatePanel() {

		Object[] masive = new Object[22];

		JTextField textField_Date_MeasurNaw = DozeArtFrame.getTextField_Date_MeasurNaw();
		JTextField textField_dateMeasurPrev = DozeArtFrame.getTextField_dateMeasurPrev();
		JTextField textField_dateMeasurPrePrev = DozeArtFrame.getTextField_dateMeasurPrePrev();

		masive[0] = textField_Date_MeasurNaw.getText();
		masive[1] = textField_dateMeasurPrev.getText();
		masive[2] = textField_dateMeasurPrePrev.getText();

		JTextField textField_dateMeasur_Start = DozeArtFrame.getTextField_dateMeasur_Start();
		JTextField textField_dateMeasur_End = DozeArtFrame.getTextField_dateMeasur_End();

		masive[3] = textField_dateMeasur_Start.getText();
		masive[4] = textField_dateMeasur_End.getText();

		JRadioButton rdbtnRutinen = DozeArtFrame.getRdbtnRutinen();
		JRadioButton rdbtnSecialen = DozeArtFrame.getRdbtnSpecialen();
		masive[5] = rdbtnRutinen.isSelected();
		masive[6] = rdbtnSecialen.isSelected();

		JRadioButton rdbtnEdnokratno = DozeArtFrame.getRdbtnEdnokratno();
		JRadioButton rdbtnMnogokratno = DozeArtFrame.getRdbtnMnogokratno();
		JRadioButton rdbtnNeprekasnato = DozeArtFrame.getRdbtnNeprekasnato();
		JRadioButton rdbtnSrTotchka = DozeArtFrame.getRdbtnSrTotchka();

		masive[7] = rdbtnEdnokratno.isSelected();
		masive[8] = rdbtnMnogokratno.isSelected();
		masive[9] = rdbtnNeprekasnato.isSelected();
		masive[10] = rdbtnSrTotchka.isSelected();

		JLabel lbl_DateToPrevios = DozeArtFrame.getLbl_DateToPrevios();
		JLabel lbl_Count_DayToPrevios = DozeArtFrame.getLbl_Count_DayToPrevios();

		masive[11] = lbl_DateToPrevios.getText();
		masive[12] = lbl_Count_DayToPrevios.getText();

		JLabel lbl_DateNawToPrevios = DozeArtFrame.getLbl_DateNawToPrevios();
		JLabel lbl_DateNawToPrePrevios = DozeArtFrame.getLbl_DateNawToPrePrevios();
//		JScrollBar scrollBarToPrevios = DozeArtFrame.getScrollBarToPrevios();

		masive[13] = lbl_DateNawToPrevios.getText();
		masive[14] = lbl_DateNawToPrePrevios.getText();
		masive[15] = lbl_DateToPrevios.getText();

		JLabel lbl_DateToPrePrevios = DozeArtFrame.getLbl_DateToPrePrevios();
		JLabel lbl_Count_DayToPrePrevios = DozeArtFrame.getLbl_Count_DayToPrePrevios();
//		JScrollBar scrollBarToPrePrevios = DozeArtFrame.getScrollBarToPrePrevios();

		masive[16] = lbl_DateToPrePrevios.getText();
		masive[17] = lbl_Count_DayToPrePrevios.getText();
		masive[18] = lbl_DateToPrePrevios.getText();

		masive[19] = ScrollBar_Methods.getMax_scrollBarToPrevios();
		masive[20] = ScrollBar_Methods.getMax_scrollBarToPrePrevios();

		JList<String> jlist = DozeArtFrame.getJlist();

		masive[21] = readDataFromJLIst(jlist);

		for (int i = 0; i < masive.length; i++) {
			System.out.println(masive[i]);
		}

		return masive;
	}

	public static Object[][] getMasiveDataForReport() {
		return masiveDataForReport;
	}

	public static String[][] createExportData() {
		int countNuclide = DozeArtFrame.getCountNuclide();
		String[][] masive = new String[countNuclide][5];

		for (int i = 0; i < countNuclide; i++) {

			masive[i][0] = DozeArtFrame.getChoice_NuclideName()[i].getSelectedItem();
			masive[i][1] = DozeArtFrame.getTextField_Activity()[i].getText();
			masive[i][2] = DozeArtFrame.getLbl_PostaplenieBq()[i].getText();
			masive[i][3] = DozeArtFrame.getLbl_GGPCalc()[i].getText();
			masive[i][4] = DozeArtFrame.getLbl_DozaNuclide()[i].getText();
		}
		return masive;
	}

}
