package SpisakPersonManagement;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.RemouveDublikateFromList;
import Aplication.ResourceLoader;
import BasiClassDAO.KodeGenerateDAO;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeGenerate;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.Zone;
import DatePicker.DatePicker;
import DozeArt.DozeArtFrame;
import DozeArt.DozeArt_Methods;
import InsertMeasuting.SaveReportMeasurTo_PersonelORExternalExcelFile;
import PersonManagement.OpenedExcelClass;
import PersonManagement.PersonManegement;
import PersonManagement.PersonelManegementFrame;
import PersonManagement.SelectSpisPrilFrame;
import PersonReference.PersonReferenceExportToExcell;
import PersonReference.PersonReferenceFrame;
import PersonReference.TextInAreaTextPanel;
import PersonReference_OID.OID_Person_WBC;
import PersonReference_OID.OID_Person_WBCDAO;
import SaveToExcellFile.SaveToPersonelORExternalFile;
import SearchFreeKode.SearchFreeKodeFrame;
import WBCUsersLogin.WBCUsersLogin;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class SpisakPersonelManegementMethods {

	static String notResults = ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults");
	static String svePersonManegement_ChangeOtdel = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("svePersonManegement_ChangeOtdel");
	static String svePersonManegement_NonKorektFormatKode = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("svePersonManegement_NonKorektFormatKode");
	static String svePersonManegement_WithPril = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("svePersonManegement_WithPril");
	static String svePersonManegement_kodeNotInOtdelArea = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("svePersonManegement_kodeNotInOtdelArea");
	static String svePersonManegement_InsertToListChengeKode = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("svePersonManegement_InsertToListChengeKode");
	static String svePersonManegement_CheckBoxLbIs_EnteredInZone = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("svePersonManegement_CheckBoxLbIs_EnteredInZone");
	static String svePersonManegement_textOtdelArea = ReadFileBGTextVariable.getGlobalTextVariableMap()
			.get("svePersonManegement_textOtdelArea");
	static boolean multytextInTextArea;
	static String ExcelFileBAK_Path = "";
	static String AEC = ReadFileBGTextVariable.getGlobalTextVariableMap().get("AEC");
	static String VO = ReadFileBGTextVariable.getGlobalTextVariableMap().get("VO");
	static List<String> listFirm = Arrays.asList("", AEC, VO);
	static Person selectionPerson;
	static String choisePerson;
	static boolean flOtdel;
	static Border defoutBorder = SpisakPersonelManegementFrame.getDefoutBorder();
	static Border redBorder = new SoftBevelBorder(BevelBorder.LOWERED, Color.RED, null, Color.RED, null);
	static String oldOtdelPerson;
	static String prilStartDateText = "";
	static String prilText = "";
	static String zoneArea;
	static List<String> listOtdelAll;
	static List<String> listOtdelKz;
	static List<String> listOtdelVO;
	static Object[][] dataTable;
	

	static KodeGenerate kodeGen;

//	static List<String> listZvenaFromDBase = SearchFreeKodeMethods.generateListZvenaFromDBase();
//	static List<List<String>> ListZvenaFromExcellFiles = SearchFreeKodeMethods.generateListZvenaFromExcellFiles();

	static String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
	static List<List<KodeStatus>> kodeStatusFromExcelFiles = getMasiveKodeStatusFromDBaseForCurentYear(curentYear);

	static boolean FirstNameOK;
	static boolean SekondNameOK;
	static boolean LastNameOK;
	static boolean KodKZ_1_OK;
	static boolean KodKZ_2_OK;
	static boolean KZ_HOG_OK;
	static boolean KZ_Terit_1_OK;
	static boolean KZ_Terit_2_OK;
	static boolean Otdel_OK;
	static boolean obhodenList;
	static boolean corectFormatKode = true;
	static boolean fromListPersonManegement = false;
	private static boolean crashRadfale = false;

	static void ActionListener_ComboBox_Firm(Choice comboBox_Firm, Choice comboBox_Otdel) {

		comboBox_Firm.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				setitemInChoise(comboBox_Firm, comboBox_Otdel);
			}
		});

	}

	public static void ActionListener_ComboBox_Otdel(Choice comboBox_Otdel, JButton btn_ReadFileListPerson) {
		comboBox_Otdel.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				zoneArea = "";
				if (!comboBox_Otdel.getSelectedItem().trim().isEmpty()) {
					int zoneID = ZoneDAO.getValueZoneByNameTerritory(getZonaFromRadioButtons()).getId_Zone();
					kodeGen = getKodeGenerate(zoneID);
					zoneArea = generateTextKodeArea(zoneID);
					SpisakPersonelManegementFrame.getLbl_OtdelKodeArea().setText(zoneArea);
					btn_ReadFileListPerson.setEnabled(true);
				} else {
					btn_ReadFileListPerson.setEnabled(false);
				}
			}
		});
	}

	static void ActionListener_Btn_ReadFileListPerson(JButton btn_ReadFileListPerson, JTextArea textArea,
			JPanel infoPanel, JPanel tablePane, JPanel panel_AllSaerch, JScrollPane scrollPane,
			JTextField textField_svePerson_Year, JTextField textField, JButton btnBackToTable) {
		btn_ReadFileListPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				getListPersonFromFile(textArea, infoPanel, tablePane, panel_AllSaerch, scrollPane,
						textField_svePerson_Year, textField, btnBackToTable);
			}
		});
	}

	static void ActionListenerBtnBackToTable(JButton btnBackToTable, JTextArea textArea, JPanel tablePane,
			JPanel panel_AllSaerch, JScrollPane scrollPane, JTextField textField_svePerson_Year) {

		btnBackToTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dataTable != null) {
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					btnBackToTable.setEnabled(false);
					SpisakPersonelManegementFrame.viewInfoPanel();
					int zoneID = ZoneDAO.getValueZoneByNameTerritory(getZonaFromRadioButtons()).getId_Zone();
					panel_infoPanelTablePanel(dataTable, textArea, panel_AllSaerch, tablePane, scrollPane,
							textField_svePerson_Year, zoneID);
					SpisakPersonelManegementFrame.viewTablePanel();
					GeneralMethods.setDefaultCursor(panel_AllSaerch);

				}
			}

		});

	}

	
	
	
	
	static void ActionListener_textField_svePerson_Year(JTextField textField_svePerson_Year,
			JButton btn_SaveToExcelFile) {
		textField_svePerson_Year.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {
				textField_svePerson_Year.setForeground(Color.BLACK);
				btn_SaveToExcelFile.setEnabled(true);
				if (!textField_svePerson_Year.getText().isEmpty()) {
					try {
						long number = Long.parseLong(textField_svePerson_Year.getText());
						if (number < Calendar.getInstance().get(Calendar.YEAR)) {
							textField_svePerson_Year.setForeground(Color.RED);
							btn_SaveToExcelFile.setEnabled(false);
						}
					} catch (Exception e) {
						textField_svePerson_Year.setForeground(Color.RED);
						btn_SaveToExcelFile.setEnabled(false);
					}
				}
			}
		});

	}

	public static void ActionListenerGenerateNewSpisak(JButton btn_GenerateSpisak, JPanel panel_AllSaerch, JTextField textField_svePerson_Year) {
		
		btn_GenerateSpisak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String year = textField_svePerson_Year.getText();
				int zoneID = ZoneDAO.getValueZoneByNameTerritory(getZonaFromRadioButtons()).getId_Zone();
				System.out.println("zoneID "+zoneID+"year "+year);
		new choisePersonByKodeFrame(new JFrame(), panel_AllSaerch, zoneID, year);
		
		
			}

		});
	}
	
	
	static void ActionListener_Btn_Spisak(JButton btn_Spisak) {
		btn_Spisak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
//				List<Spisak_Prilogenia> listSpisak_Prilogenia = SpisakPersonelManegementFrame.getListSpisak_Prilogenia();
//				
//				listSpisak_Prilogenia = addObhodenListToListSpisak_Prilogenia(listSpisak_Prilogenia);
				Choice comboBox_svePerson_Otdel = SpisakPersonelManegementFrame.getComboBox_Otdel();
				List<Spisak_Prilogenia> listSpisak_Prilogenia = generateListSpisPril(comboBox_svePerson_Otdel);

				sortByFormulyarName(listSpisak_Prilogenia);

				if (listSpisak_Prilogenia.size() > 0) {
					int selectedContent = SpisakPersonelManegementMethods
							.generateSelectSpisPrilFrame(listSpisak_Prilogenia);

					if (selectedContent >= 0) {
						String formuliarName = listSpisak_Prilogenia.get(selectedContent).getFormulyarName();
						String startDate = sdf.format(listSpisak_Prilogenia.get(selectedContent).getStartDate());
						String endDate = sdf.format(listSpisak_Prilogenia.get(selectedContent).getEndDate());

						JTextField textField_svePerson_Spisak = SpisakPersonelManegementFrame
								.getTextField_svePerson_Spisak();
						JTextField textField_svePerson_StartDate = SpisakPersonelManegementFrame
								.getTextField_savePerson_StartDate();
						JTextField textField_svePerson_EndDate = SpisakPersonelManegementFrame
								.getTextField_savePerson_EndDate();

						textField_svePerson_Spisak.setText(formuliarName);
						textField_svePerson_StartDate.setText(startDate);
						textField_svePerson_EndDate.setText(endDate);
					}
				}

			}

		});
	}

	public static void ActionListener_Btn_SaveToExcelFile(JPanel frame, JButton btn_SaveToExcelFile, JTextArea textArea) {
		btn_SaveToExcelFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneralMethods.setWaitCursor(frame);
				
				System.out.println("dataTable.length " + dataTable.length);
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
				boolean checkbx_EnterInZone;
				Choice comboBox_svePerson_Otdel = SpisakPersonelManegementFrame.getComboBox_Otdel();
				Choice comboBox_savePerson_Firm = SpisakPersonelManegementFrame.getComboBox_Firm();
				JTextField textField_svePerson_Spisak = SpisakPersonelManegementFrame.getTextField_svePerson_Spisak();
				JTextField textField_savePerson_StartDate = SpisakPersonelManegementFrame
						.getTextField_savePerson_StartDate();
				JTextField textField_savePerson_EndDate = SpisakPersonelManegementFrame
						.getTextField_savePerson_EndDate();
				
				JTextField textField__svePerson_Year = SpisakPersonelManegementFrame.getTextField_svePerson_Year();

				String newYear = SpisakPersonelManegementFrame.getTextField_svePerson_Year().getText();

				if (SpisakPersonelManegementMethods.checkIsClosedPersonAndExternalFile()) {

					if (checkInfoOtdel(comboBox_svePerson_Otdel)) {

						String comment = "";

						int lastrekord = dataTable.length;

						String[] infoForPersonFromFrame = new String[8];
						for (int i = 0; i < lastrekord; i++) {

//			************** set PERSON

							String egn = (String) dataTable[i][1];
							boolean saveToExcel = (boolean) dataTable[i][9];

							Person person = PersonDAO.getValuePersonByEGN(egn);

							if (person == null) {

//							!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//							!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//							!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

							}

							System.out.println("person " + person.getFirstName());

//					************** set KodeStatus				

							String[] kode = getKodeStatusByPersonFromDBase(person);
							for (int j = 0; j < kode.length; j++) {
								infoForPersonFromFrame[j] = kode[j];
							}
							checkbx_EnterInZone = true;
							int zona = ZoneDAO.getValueZoneByNameTerritory(getZonaFromRadioButtons()).getId_Zone();
							System.out.println(zona + " " + dataTable[i][8]);

							infoForPersonFromFrame[zona - 1] = (String) dataTable[i][8];
							infoForPersonFromFrame[5] = (String) dataTable[i][2];
							infoForPersonFromFrame[6] = (String) dataTable[i][3];
							infoForPersonFromFrame[7] = (String) dataTable[i][4];

							if (checkInfoPersonAndKodeStatus(infoForPersonFromFrame, person, checkbx_EnterInZone)) {

								Workplace workplace = WorkplaceDAO
										.getValueWorkplaceByObject("Otdel", comboBox_svePerson_Otdel.getSelectedItem())
										.get(0);
								System.out.println("-------------------------------- ");
								String year = textField__svePerson_Year.getText().trim();
								try {
									Integer.parseInt(year);
								} catch (NumberFormatException e0) {
									year = curentYear;
									textField__svePerson_Year.setText(year);
								}

//		************** set SPISAK_PRILOGENIA
								Spisak_Prilogenia spisPril = null;

								String formuliarName = textField_svePerson_Spisak.getText().trim();

								if (!formuliarName.isEmpty()) {

									Date sDate = null, eDate = null;
									try {
										sDate = sdf.parse(textField_savePerson_StartDate.getText().trim());
										eDate = sdf.parse(textField_savePerson_EndDate.getText().trim());
									} catch (ParseException e1) {

										e1.printStackTrace();
									}

									Spisak_PrilogeniaDAO.setValueSpisak_Prilogenia(formuliarName, newYear, sDate, eDate,
											workplace, "");
									spisPril = Spisak_PrilogeniaDAO
											.getLastSaveObjectFromValueSpisak_PrilogeniaByYear_Workplace_StartDate(
													newYear, sDate, workplace.getId_Workplace());

									SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
									Date curentDate = Calendar.getInstance().getTime();
									try {
										curentDate = sdf2.parse(sdf2.format(curentDate));
									} catch (ParseException e1) {

									}


										UsersWBC user = WBCUsersLogin.getCurentUser();
										PersonStatusNewDAO.setValuePersonStatusNew(person, workplace,
												spisPril.getFormulyarName(), spisPril.getStartDate(),
												spisPril.getEndDate(), spisPril.getYear(), user, curentDate, comment);
									
								}

//		****************** set KODESTATUS
								setKodeStatusInDBase(person, infoForPersonFromFrame, newYear);

								System.out.println(saveToExcel);

								if (saveToExcel) {
									System.out.println("**********************************");
									UsersWBC user = WBCUsersLogin.getCurentUser();
									SaveToPersonelORExternalFile.saveInfoFromPersonManegementToExcelFile(person,
											comboBox_savePerson_Firm.getSelectedItem(), spisPril, user, comment,
											workplace, oldOtdelPerson, checkbx_EnterInZone, false, obhodenList);

								}
							}

						}
					}
				}
				textArea.setText("OK");
				SpisakPersonelManegementFrame.viewInfoPanel();
				textField_svePerson_Spisak.setText("");
				textField_savePerson_StartDate.setText("");
				textField_savePerson_EndDate.setText("");
				dataTable = null;
				GeneralMethods.setDefaultCursor(frame);

			}

		});
	}

	public static void ActionListener_Btn_Export(JButton btn_Export, JPanel save_Panel, JPanel button_Panel) {
		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dataTable == null) {
					PersonReferenceExportToExcell.btnExportInfoPersonToExcell(TextInAreaTextPanel.getPerson(),
							TextInAreaTextPanel.getMasivePersonStatusName(),
							TextInAreaTextPanel.getMasivePersonStatus(), TextInAreaTextPanel.getZoneNameMasive(),
							TextInAreaTextPanel.getMasiveKode(), TextInAreaTextPanel.getMasiveMeasurName(),
							TextInAreaTextPanel.getMasiveMeasur(), save_Panel);
				} else {
					PersonReferenceExportToExcell.btnExportTableToExcell(dataTable,
							SpisakPersonelManegementMethods.getTabHeader(), button_Panel, "PersonReference");
					}
			}
		});
	}

	private static void setKodeStatusInDBase(Person person, String[] infoForPerson, String year) {
		Date setdate = Calendar.getInstance().getTime();
		UsersWBC setDataUser = UsersWBCDAO.getValueUsersWBCByID(1);
		for (int i = 0; i < 5; i++) {

			String kodeByFrame = infoForPerson[i];
			KodeStatus kodeStat = KodeStatusDAO.getKodeStatusByPersonZoneYear(person, i + 1, year);
			KodeStatus newKodeStat = new KodeStatus(person, kodeByFrame, ZoneDAO.getValueZoneByID(i + 1), true, year,
					"", setDataUser, setdate);
			setKodeToDBase(kodeByFrame, kodeStat, newKodeStat);
		}

	}

	public static List<Spisak_Prilogenia> addObhodenListToListSpisak_Prilogenia(
			List<Spisak_Prilogenia> listSpisak_Prilogenia) {
		List<Spisak_Prilogenia> newlistSpisak_Prilogenia = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Spisak_Prilogenia obhodenList_SpPr = Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(11177);
		Calendar curentdate = Calendar.getInstance();
		Date startDate = curentdate.getTime();
		Date endDate = null;
		try {
			endDate = sdf.parse("01.01.2000");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		obhodenList_SpPr.setStartDate(startDate);
		obhodenList_SpPr.setEndDate(endDate);
		newlistSpisak_Prilogenia.add(obhodenList_SpPr);
		for (Spisak_Prilogenia spisak_Prilogenia : listSpisak_Prilogenia) {
			newlistSpisak_Prilogenia.add(spisak_Prilogenia);
		}
		return newlistSpisak_Prilogenia;
	}

	public static List<String[]> getMasiveKodeStatusFromExcelFiles() {

		List<String[]> listMasive = new ArrayList<>();
		String[] excellFiles_ActualPersonalAndExternal = AplicationMetods
				.getDataBaseFilePat_OriginalPersonalAndExternal();
		for (int i = 0; i < excellFiles_ActualPersonalAndExternal.length; i++) {
			String pathFile = excellFiles_ActualPersonalAndExternal[i];
			Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
			if (workbook != null) {

				String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "", kodeT1 = "", kodeT2 = "";
				String EGN = "";
				Sheet sheet = workbook.getSheetAt(0);
				Cell cell, cell1;

				for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {
					String[] masive = { "", "", "", "", "", "", "", };
					kodeKZ1 = "";
					kodeKZ2 = "";
					kodeHOG = "";
					kodeT1 = "";
					kodeT2 = "";
					if (sheet.getRow(row) != null) {
						cell = sheet.getRow(row).getCell(5);
						cell1 = sheet.getRow(row).getCell(6);

						if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
							EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);
							masive[0] = EGN;

							cell = sheet.getRow(row).getCell(0);
							if (cell != null)
								kodeKZ1 = cell.getStringCellValue();

							cell = sheet.getRow(row).getCell(1);
							if (cell != null)
								kodeKZ2 = cell.getStringCellValue();

							cell = sheet.getRow(row).getCell(2);
							if (cell != null)
								kodeHOG = cell.getStringCellValue();

							cell = sheet.getRow(row).getCell(3);
							if (cell != null)
								kodeT2 = cell.getStringCellValue();

							cell = sheet.getRow(row).getCell(4);
							if (cell != null)
								kodeT1 = cell.getStringCellValue();

							if (pathFile.contains("EXTERNAL")) {
								kodeT1 = kodeT2;
								kodeT2 = "";
							}

							if (!kodeKZ1.equals("ЕП-2") && !kodeKZ1.trim().equals("") && !kodeKZ1.equals("н")
									&& !inCodeNotNumber(kodeKZ1)) {
								masive[1] = kodeKZ1;
							}
							if (!kodeKZ2.equals("н") && !kodeKZ2.trim().equals("") && !inCodeNotNumber(kodeKZ2)) {
								masive[2] = kodeKZ2;
							}
							if (!kodeHOG.equals("н") && !kodeHOG.trim().equals("") && !inCodeNotNumber(kodeHOG)) {
								masive[3] = kodeHOG;
							}
							if (!kodeT1.equals("н") && !kodeT1.trim().equals("") && !inCodeNotNumber(kodeT1)) {
								masive[4] = kodeT1;
							}
							if (!kodeT2.equals("н") && !kodeT2.trim().equals("") && !inCodeNotNumber(kodeT2)) {
								masive[5] = kodeT2;
							}

						}
					}
					listMasive.add(masive);

				}
			}
		}

//		for (String[] string : listMasive) {
//			for (int j = 0; j < string.length; j++) {
//			System.out.print(string[j] + " - ");
//			}
//			System.out.println();
//			
//		}

		return listMasive;

	}

	public static void getListPersonFromFile(JTextArea textArea, JPanel infoPanel, JPanel tablePane,
			JPanel panel_AllSaerch, JScrollPane scrollPane, JTextField textField_svePerson_Year, JTextField textField,
			JButton btnBackToTable) {
		crashRadfale = false;
		textArea.setText("");
		SpisakPersonelManegementFrame.viewInfoPanel();
				
		File file;
		String filePath = textField.getText();
		System.out.println("filePath "+filePath);
		if (filePath.isEmpty()) {
			JFileChooser chooiser = new JFileChooser(ExcelFileBAK_Path);
			chooiser.setMultiSelectionEnabled(false);
			chooiser.showOpenDialog(null);
			file = chooiser.getSelectedFile();
		} else {
			file = new File(filePath);
		}
		System.out.println("file "+file);
		if(file != null) {
//		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		GeneralMethods.setWaitCursor(panel_AllSaerch);
		List<PersonManegement> listPersonFromFile = readListPersonFromFile(panel_AllSaerch, file, textField);
		System.out.println("listPersonFromFile.size "+listPersonFromFile.size());
		if (listPersonFromFile.size() == 0) {
			textArea.setText(notResults);
			SpisakPersonelManegementFrame.viewInfoPanel();
			if (dataTable != null) {
				btnBackToTable.setEnabled(true);
			}

		}

		if (listPersonFromFile.size() == 1) {
			textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson(textField_svePerson_Year.getText(),
					listPersonFromFile.get(0).getPerson(), false, 0));
			SpisakPersonelManegementFrame.viewInfoPanel();
			if (dataTable != null) {
				btnBackToTable.setEnabled(true);
			}
		}

		if (listPersonFromFile.size() > 1) {

			int zoneID = ZoneDAO.getValueZoneByNameTerritory(getZonaFromRadioButtons()).getId_Zone();
			

			fromListPersonManegement = true;
			System.out.println("***** " + listPersonFromFile.size());
//			updateSectionPersonSave_Panel(listPersonFromFile);
			dataTable = addListStringSelectionPersonToComboBox(listPersonFromFile);
			panel_infoPanelTablePanel(dataTable, textArea, panel_AllSaerch, tablePane, scrollPane,
					textField_svePerson_Year,zoneID);
			SpisakPersonelManegementFrame.viewTablePanel();
			btnBackToTable.setEnabled(false);

		}
		
		GeneralMethods.setDefaultCursor(panel_AllSaerch);
		}
//		return listPersonFromFile;
	}

	private static KodeGenerate getKodeGenerate(int zoneID) {
		KodeGenerate kodeGen = null;
		String otdelPerson = SpisakPersonelManegementFrame.getComboBox_Otdel().getSelectedItem();
		Workplace workPl = WorkplaceDAO.getActualValueWorkplaceByOtdel(otdelPerson);
		if (workPl != null) {
			kodeGen = KodeGenerateDAO.getValueKodeGenerateByWorkplaceAndZone(workPl.getId_Workplace(), zoneID);
		}
		return kodeGen;
	}

	public static String getInfoFromWBCByEGN(String egn) {

		String str = "";
		List<OID_Person_WBC> list = OID_Person_WBCDAO.getlist_OID_Person_WBCByEGN(egn);
		if (list != null) {
			if (list.size() < 2) {
				OID_Person_WBC person = list.get(0);

				str = person.getEgn() + "   " + person.getFirstName() + " " + person.getSecondName() + " "
						+ person.getLastName() + "\n";
				str += "Код зона 1  " + person.getZsr1() + " Код зона 2  " + person.getZsr2() + "\n";

			}
		}
		return str;
	}

	public static void setitemInChoise(Choice comboBox_Firm, Choice comboBox_Otdel) {

		List<String> listAdd = new ArrayList<>();

		listAdd = listOtdelVO;

		if (((String) comboBox_Firm.getSelectedItem()).trim().isEmpty()) {
			listAdd = listOtdelAll;
		} else {
			if (((String) comboBox_Firm.getSelectedItem()).trim().equals("АЕЦ Козлодуй")) {
				listAdd = listOtdelKz;
			}
		}
		addItem(comboBox_Otdel, listAdd);
	}

	static List<String> getListKZ() {
		listOtdelKz = getStringListFromActualWorkplaceByFirmname("АЕЦ Козлодуй");
//		listOtdelKz = SearchFreeKodeMethods.generateListZvenaKZ(ListZvenaFromExcellFiles, listZvenaFromDBase);
		listOtdelKz.add("");
		Collections.sort(listOtdelKz);
		return listOtdelKz;
	}

	static List<String> getListVO() {
		listOtdelVO = getStringListFromActualWorkplaceByFirmname("Външни организации");
//		listOtdelVO = SearchFreeKodeMethods.generateListZvenaVO(ListZvenaFromExcellFiles, listZvenaFromDBase);
		listOtdelVO.add("");
		Collections.sort(listOtdelVO);
		return listOtdelVO;
	}

	static List<String> getListALL() {
		listOtdelAll = new ArrayList<>();
		listOtdelAll.addAll(listOtdelKz);
		listOtdelAll.addAll(listOtdelVO);
//		listOtdelAll = SearchFreeKodeMethods.generateListZvena();
//		listOtdelAll.add("");
		Collections.sort(listOtdelAll);
		return listOtdelAll;
	}

	public static List<String> getStringListFromActualWorkplaceByFirmname(String string) {
		List<Workplace> list = WorkplaceDAO.getActualValueWorkplaceByFirm(string);
		List<String> listStr = new ArrayList<>();
		for (Workplace workplace : list) {
			listStr.add(workplace.getOtdel());
		}
		return listStr;
	}

	private static boolean checkInfoOtdel(Choice comboBox_svePerson_Otdel) {

		if (comboBox_svePerson_Otdel.getSelectedItem().isEmpty()) {
			String svePersonManegement_CheckOtdelArea = ReadFileBGTextVariable.getGlobalTextVariableMap()
					.get("svePersonManegement_CheckOtdelArea");
			AplicationMetods.MessageDialog(svePersonManegement_CheckOtdelArea);
			return false;
		}
		return true;
	}

	private static boolean checkInfoPersonAndKodeStatus(String[] infoForPersonFromFrame, Person person,
			boolean checkbx_EnterInZone) {

		String svePersonManegement_chengedSomeFilds = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("svePersonManegement_chengedSomeFilds");
		String svePersonManegement_EnteredInZoneANDChangeKode = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("svePersonManegement_EnteredInZoneANDChangeKode");

		boolean fl_KodeIsChange = false;
		String[] kodeFromDBase = getKodeStatusByPersonFromDBase(person);
		for (int i = 0; i < kodeFromDBase.length; i++) {
			if (kodeFromDBase[i].equals("н") || kodeFromDBase[i].equals("ЕП-2")) {
				kodeFromDBase[i] = "";
			}
			if (infoForPersonFromFrame[i].equals("н") || infoForPersonFromFrame[i].equals("ЕП-2")) {
				infoForPersonFromFrame[i] = "";
			}

			System.out.println(i + " | " + kodeFromDBase[i] + " | " + infoForPersonFromFrame[i]);
		}

		String errorStr = "";
		if (!infoForPersonFromFrame[0].equals(kodeFromDBase[0])) {
			errorStr += "KZ-1(" + kodeFromDBase[0] + "->" + infoForPersonFromFrame[0] + "), ";
			fl_KodeIsChange = true;
		}

		if (!infoForPersonFromFrame[1].equals(kodeFromDBase[1])) {
			errorStr += "KZ-2(" + kodeFromDBase[1] + "->" + infoForPersonFromFrame[1] + "), ";
			fl_KodeIsChange = true;
		}

		if (!infoForPersonFromFrame[2].equals(kodeFromDBase[2])) {
			errorStr += "KZ-HOG(" + kodeFromDBase[2] + "->" + infoForPersonFromFrame[2] + "), ";
			fl_KodeIsChange = true;

		}

		if (!infoForPersonFromFrame[3].equals(kodeFromDBase[3])) {
			errorStr += "Ter-1(" + kodeFromDBase[3] + "->" + infoForPersonFromFrame[3] + "->" + "), ";
			fl_KodeIsChange = true;
		}

		if (!infoForPersonFromFrame[4].equals(kodeFromDBase[4])) {
			errorStr += "Ter-2(" + kodeFromDBase[4] + "->" + infoForPersonFromFrame[4] + "), ";
			fl_KodeIsChange = true;
		}

		if (!infoForPersonFromFrame[5].equals(person.getFirstName())) {
			errorStr += "FName(" + person.getFirstName() + "->" + infoForPersonFromFrame[5] + "), ";
			;
		}
		if (!infoForPersonFromFrame[6].equals(person.getSecondName())) {
			errorStr += "SName(" + person.getSecondName() + "->" + infoForPersonFromFrame[6] + "), ";
		}
		if (!infoForPersonFromFrame[7].equals(person.getLastName())) {
			errorStr += "LName(" + person.getLastName() + "->" + infoForPersonFromFrame[7] + "), ";
		}

		System.out.println("errorStr " + errorStr);
		if (errorStr.length() > 2) {
			errorStr = errorStr.substring(0, errorStr.length() - 2);

			if (OptionDialog(errorStr, svePersonManegement_chengedSomeFilds)) {
				if (errorStr.contains("Name")) {
					person.setFirstName(infoForPersonFromFrame[5]);
					person.setSecondName(infoForPersonFromFrame[6]);
					person.setLastName(infoForPersonFromFrame[7]);
					PersonDAO.updateValuePerson(person);
				}
				if (fromListPersonManegement) {
					return true;
				}
				if (fl_KodeIsChange && checkbx_EnterInZone) {
					System.out.println("**************************************");
					return OptionDialog(svePersonManegement_EnteredInZoneANDChangeKode, "В Н И М А Н И Е");

				} else {
					return true;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	public static String[] getKodeStatusByPersonFromDBase(Person person) {
		String[] kode = new String[5];
		for (int i = 0; i < 5; i++) {
			kode[i] = "н";
			if (i == 0) {
				kode[i] = "ЕП-2";
			}
			KodeStatus kodeStat = KodeStatusDAO.getKodeStatusByPersonZoneYear(person, i + 1, curentYear);
			if (kodeStat != null) {
				kode[i] = kodeStat.getKode();
			}

		}

		for (int i = 0; i < 5; i++) {
			System.out.println(i + " " + kode[i]);
		}
		return kode;
	}

	public static void setKodeToDBase(String kodeByFrame, KodeStatus kodeStst, KodeStatus newKodeStst) {
		String kodByDBase;
		Date setdate = Calendar.getInstance().getTime();
		if (kodeByFrame.equals("н") || kodeByFrame.equals("ЕП-2")) {
			kodeByFrame = "";
		}
		kodByDBase = "";
		if (kodeStst != null) {
			kodByDBase = kodeStst.getKode();
		}
		if (!kodByDBase.equals(kodeByFrame)) {
			if (kodeByFrame.isEmpty()) {
				KodeStatusDAO.deleteValueKodeStatus(kodeStst.getKodeStatus_ID());
			} else {
				if (kodeStst != null) {
					kodeStst.setKode(kodeByFrame);
					kodeStst.setDateSet(setdate);
					KodeStatusDAO.updateValueKodeStatus(kodeStst);
				} else {
					KodeStatusDAO.setObjectKodeStatusToTable(newKodeStst);
				}
			}
		}
	}
	
	public static boolean OptionDialog(String mesage, String textOptionDialogFrame) {

		String[] options = { "Back", "Continued" };
		int x = JOptionPane.showOptionDialog(null, mesage, textOptionDialogFrame, JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		System.out.println(x + " -----------------");
		if (x > 0) {
			return true;
		}
		return false;
	}

	static int generateSelectSpisPrilFrame(List<Spisak_Prilogenia> listSpisak_Prilogenia) {
		SelectSpisPrilFrame.setSelectedContent(-1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		if (listSpisak_Prilogenia != null && listSpisak_Prilogenia.size() > 0) {

			String[] masiveSpisPril = new String[listSpisak_Prilogenia.size() + 1];

			int maxFormulyarName = 0;
			for (Spisak_Prilogenia spisak_Prilogenia : listSpisak_Prilogenia) {
				if (maxFormulyarName < spisak_Prilogenia.getFormulyarName().length()) {
					maxFormulyarName = spisak_Prilogenia.getFormulyarName().length();
				}
			}

			int k = 0;
			for (Spisak_Prilogenia spisak_Prilogenia : listSpisak_Prilogenia) {
				String space = TextInAreaTextPanel.getAddSpace(maxFormulyarName + 3,
						spisak_Prilogenia.getFormulyarName());
				masiveSpisPril[k] = spisak_Prilogenia.getFormulyarName() + space
						+ sdf.format(spisak_Prilogenia.getStartDate()) + "     "
						+ sdf.format(spisak_Prilogenia.getEndDate());
				k++;
			}

			JFrame parent = new JFrame();
			int[] sizeInfoFrame = { 300, 400 };
			int[] Coord = AplicationMetods.getCurentKoordinates(sizeInfoFrame);
			ActionIcone round = new ActionIcone();
			int max = maxFormulyarName;

			new SelectSpisPrilFrame(parent, Coord, masiveSpisPril, sizeInfoFrame, max, round);
		}

		return SelectSpisPrilFrame.getSelectedContent();
	}

	static void addItem(Choice comboBox, List<String> list) {
		comboBox.removeAll();
		for (String otdel : list) {
			comboBox.add(otdel);
		}
	}

	public static void addItemFirm(Choice comboBox_Firm) {
		addItem(comboBox_Firm, listFirm);
	}

	static boolean allFieldsEmnty(JTextField textField_EGN, JTextField textField_FName, JTextField textField_SName,
			JTextField textField_LName, Choice otdel) {
		return (textField_EGN.getText().trim().isEmpty() && textField_FName.getText().trim().isEmpty()
				&& textField_SName.getText().trim().isEmpty() && textField_LName.getText().trim().isEmpty()
				&& otdel.getSelectedItem().trim().isEmpty());

	}

	static String addListStringSelectionPersonToTextArea(List<Person> listSelectionPerson) {
		String text = "";
		for (Person person : listSelectionPerson) {
			text = text + person.getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person)
					+ "\n";
		}

		return text;

	}

	public static String[] getMasiveFromKodeAndWorkPlaceFromDBase(Person person) {

		String[] masive = new String[7];
		String[] masiveKodeStatus = getKodeStatusByPersonFromDBase(person);

		List<PersonStatusNew> listPerStat = PersonStatusNewDAO.getValuePersonStatusNewByPerson(person);
		sortByStartDateNew(listPerStat);
		PersonStatusNew perStat = listPerStat.get(0);
		for (int i = 0; i < masiveKodeStatus.length; i++) {
			masive[i] = masiveKodeStatus[i];
		}

		masive[5] = perStat.getWorkplace().getFirmName();
		masive[6] = perStat.getWorkplace().getOtdel();

		return masive;
	}

	public static String[] getMasiveFromKodeAndWorkPlaceFromExcelFile(String insertEGN) {

		String[] masive = { "", "", "", "", "", "", "", };
		String[] excellFiles_ActualPersonalAndExternal = AplicationMetods
				.getDataBaseFilePat_OriginalPersonalAndExternal();
		for (int i = 0; i < excellFiles_ActualPersonalAndExternal.length; i++) {
			String pathFile = excellFiles_ActualPersonalAndExternal[i];
			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";
			}
			Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
			if (workbook != null) {
				masive[5] = firmName;
				String otdelName = "";
				String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "", kodeT1 = "", kodeT2 = "";
				String EGN = "";
				Sheet sheet = workbook.getSheetAt(0);
				Cell cell, cell1;
				for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {
					kodeKZ1 = "";
					kodeKZ2 = "";
					kodeHOG = "";
					kodeT1 = "";
					kodeT2 = "";
					if (sheet.getRow(row) != null) {
						cell = sheet.getRow(row).getCell(5);
						cell1 = sheet.getRow(row).getCell(6);
						if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
							otdelName = cell1.getStringCellValue().trim();
							if (!otdelName.contains("край") && !otdelName.contains("КРАЙ")) {
								masive[6] = otdelName;
							}
						}

						if (ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
							EGN = ReadKodeStatusFromExcelFile.getEGNFromENGCell(cell);

							if (EGN.equals(insertEGN)) {

								cell = sheet.getRow(row).getCell(0);
								if (cell != null)
									kodeKZ1 = cell.getStringCellValue();

								cell = sheet.getRow(row).getCell(1);
								if (cell != null)
									kodeKZ2 = cell.getStringCellValue();

								cell = sheet.getRow(row).getCell(2);
								if (cell != null)
									kodeHOG = cell.getStringCellValue();

								cell = sheet.getRow(row).getCell(3);
								if (cell != null)
									kodeT2 = cell.getStringCellValue();

								cell = sheet.getRow(row).getCell(4);
								if (cell != null)
									kodeT1 = cell.getStringCellValue();

								if (pathFile.contains("EXTERNAL")) {
									kodeT1 = kodeT2;
									kodeT2 = "";
								}

								if (!kodeKZ1.equals("ЕП-2") && !kodeKZ1.trim().equals("") && !kodeKZ1.equals("н")
										&& !inCodeNotNumber(kodeKZ1)) {
									masive[0] = kodeKZ1;
								}
								if (!kodeKZ2.equals("н") && !kodeKZ2.trim().equals("") && !inCodeNotNumber(kodeKZ2)) {
									masive[1] = kodeKZ2;
								}
								if (!kodeHOG.equals("н") && !kodeHOG.trim().equals("") && !inCodeNotNumber(kodeHOG)) {
									masive[2] = kodeHOG;
								}
								if (!kodeT1.equals("н") && !kodeT1.trim().equals("") && !inCodeNotNumber(kodeT1)) {
									masive[3] = kodeT1;
								}
								if (!kodeT2.equals("н") && !kodeT2.trim().equals("") && !inCodeNotNumber(kodeT2)) {
									masive[4] = kodeT2;
								}
								row = sheet.getLastRowNum() + 1;
								i = excellFiles_ActualPersonalAndExternal.length;
							}

						}
					}
				}
			}
		}
		int i = 0;
		for (String string : masive) {
			System.out.println(i + " -> " + string);
			i++;
		}

		return masive;
	}

	static boolean inCodeNotNumber(String kode) {
		return kode.replaceAll("\\d*", "").length() == kode.length();
	}

	static void startSearchFreeKodeFrame(String otdel, String zona) {
		ActionIcone round = new ActionIcone();
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {

				new SearchFreeKodeFrame(round, otdel, zona);

			}
		});
		thread.start();
	}

	public static List<Spisak_Prilogenia> generateListSpisPril(Choice comboBox_savePerson_Otdel) {
		String newOtdelPerson = comboBox_savePerson_Otdel.getSelectedItem();
		Workplace workPl = WorkplaceDAO.getActualValueWorkplaceByOtdel(newOtdelPerson);
		System.out.println(workPl.getId_Workplace());
		List<Spisak_Prilogenia> listSpisak_Prilogenia = getSisPril(curentYear, workPl);

		System.out.println(listSpisak_Prilogenia.size());
//		List<Spisak_Prilogenia> listSpisak_Prilogenia = PersonelManegementMethods
//				.getListSpisak_Prilogenia_FromExcelFile(newOtdelPerson);

		return listSpisak_Prilogenia;
	}

	private static List<Spisak_Prilogenia> getSisPril(String curentYear2, Workplace workPl) {
		List<Integer> listSpInt = new ArrayList<>();
		String PerStatNewSet = ReadFileBGTextVariable.getGlobalTextVariableMap().get("PerStatNewSet");
		if (PerStatNewSet.equals("1")) {
			List<Spisak_Prilogenia> list = Spisak_PrilogeniaDAO.getListSpisak_PrilogeniaByYear_Workplace(curentYear,
					workPl.getId_Workplace());
			for (Spisak_Prilogenia spPr : list) {
				listSpInt.add(spPr.getSpisak_Prilogenia_ID());
			}
		} else {
			List<PersonStatus> list = PersonStatusDAO.getValuePersonStatusByWorkplace_Year(workPl, curentYear);
			for (PersonStatus personStatus : list) {
				listSpInt.add(personStatus.getSpisak_prilogenia().getSpisak_Prilogenia_ID());
			}
		}
		List<Integer> listSp2 = removeDuplicatess(listSpInt);
		List<Spisak_Prilogenia> listSp = new ArrayList<>();
		for (Integer personStatusInt : listSp2) {
			listSp.add(Spisak_PrilogeniaDAO.getValueSpisak_PrilogeniaByID(personStatusInt));
		}
		return listSp;
	}

	public static List<Integer> removeDuplicatess(List<Integer> list) {
		Set<Integer> set = new LinkedHashSet<>();
		set.addAll(list);
		list.clear();
		list.addAll(set);
		return list;
	}

	public static List<Spisak_Prilogenia> getListSpisak_Prilogenia_FromExcelFile(String otdel) {

		String[] path = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();

		List<Spisak_Prilogenia> spisak_Prilogenia_List = new ArrayList<Spisak_Prilogenia>();
		for (String pathFile : path) {

			Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
			if (workbook != null) {

				String otdelName = "", formulyarName = "";
				Date startDate = null, endDate = null;

				Workplace workplace = new Workplace();
				Sheet sheet = workbook.getSheetAt(3);
				Cell cell, cell1;
				for (int row = 0; row <= sheet.getLastRowNum(); row += 1) {
					if (sheet.getRow(row) != null) {
						cell = sheet.getRow(row).getCell(5);
						cell1 = sheet.getRow(row).getCell(6);
						if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
							otdelName = cell1.getStringCellValue();
							if (!otdelName.equals("край")) {
								if (otdelName.equals(otdel)) {

									int k = 7;
									cell = sheet.getRow(row).getCell(k);
									while (ReadExcelFileWBC.CellNOEmpty(cell)) {
										Spisak_Prilogenia spPr = getSisak_Prilogenie(k, row, sheet, startDate, endDate,
												formulyarName, workplace, curentYear);

										k = k + 3;
										cell = sheet.getRow(row).getCell(k);

										spisak_Prilogenia_List.add(spPr);
									}
								}
							}
						}
					}
				}
			}
		}
		return spisak_Prilogenia_List;
	}

	@SuppressWarnings("deprecation")
	public static Spisak_Prilogenia getSisak_Prilogenie(int k, int row, Sheet sheet, Date startDate, Date endDate,
			String formulyarName, Workplace workplace, String year) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date endDate0 = null, startDate0 = null;
		try {
			startDate0 = sdfrmt.parse("01.01." + year);
			endDate0 = sdfrmt.parse("31.12." + year);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Cell cell = sheet.getRow(row).getCell(k);

		formulyarName = cell.getStringCellValue();
		k++;
		cell = sheet.getRow(row).getCell(k);
		if (ReadExcelFileWBC.CellNOEmpty(cell) && !ReadExcelFileWBC.getStringfromCell(cell).isEmpty()) {
			startDate = ReadExcelFileWBC.readCellToDate(cell);
		} else {
			startDate = startDate0;
		}
		k++;
		cell = sheet.getRow(row).getCell(k);
		if (ReadExcelFileWBC.CellNOEmpty(cell) && !ReadExcelFileWBC.getStringfromCell(cell).isEmpty()) {
			endDate = ReadExcelFileWBC.readCellToDate(cell);
		} else {
			endDate = endDate0;
		}
		k++;
		cell = sheet.getRow(row).getCell(k);
		year = (endDate0.getYear() + 1900) + "";
		Spisak_Prilogenia spPr = new Spisak_Prilogenia(formulyarName, year, startDate, endDate, workplace, "");

		return spPr;

	}

	protected static String getZonaFromRadioButtons() {

		List<Zone> listZone = ZoneDAO.getAllValueZone();
		if (SpisakPersonelManegementFrame.getRdbtn_KodKZ1().isSelected()) {
			System.out.println("1");
			return listZone.get(0).getNameTerritory();
		}
		if (SpisakPersonelManegementFrame.getRdbtn_KodKZ2().isSelected()) {
			System.out.println("2");
			return listZone.get(1).getNameTerritory();
		}
		if (SpisakPersonelManegementFrame.getRdbtn_KodKZHOG().isSelected()) {
			System.out.println("3");
			return listZone.get(2).getNameTerritory();
		}
		if (SpisakPersonelManegementFrame.getRdbtn_KodTerit_1().isSelected()) {
			System.out.println("4");
			return listZone.get(3).getNameTerritory();
		}
		if (SpisakPersonelManegementFrame.getRdbtn_KodTerit_2().isSelected()) {
			System.out.println("5");
			return listZone.get(4).getNameTerritory();
		}

		return "";
	}

	

	private static List<List<KodeStatus>> getMasiveKodeStatusFromDBaseForCurentYear(String curentYear) {
		List<List<KodeStatus>> listMasive = new ArrayList<List<KodeStatus>>();
		for (int i = 0; i < 5; i++) {
			List<KodeStatus> listKStat = KodeStatusDAO.getKodeStatusByYearZone(curentYear, (i + 1));
			listMasive.add(listKStat);
		}

		return listMasive;
	}

	public static boolean checkDublicateKodeInNewPerson(int zoneID, int zoneKode_Colum, DefaultTableModel model,
			int Index_row) {

		String egn = model.getValueAt(Index_row, 1).toString();
		String kode = model.getValueAt(Index_row, zoneKode_Colum).toString();
		if (!egn.isEmpty() && zoneID > 0 && !kode.isEmpty()) {
			List<KodeStatus> listKStat = kodeStatusFromExcelFiles.get(zoneID - 1);
			for (KodeStatus kodeStatus : listKStat) {
				if (!egn.equals(kodeStatus.getPerson().getEgn()) && kode.equals(kodeStatus.getKode())) {
					return true;
				}
			}
		}

		return false;
	}

	private static void checkKotrecAllCode(int zoneID, DefaultTableModel model) {
		int zoneKode_Colum = 7;
		int newZoneKode_Colum = 8;

		JButton btn_SaveToExcelFile = SpisakPersonelManegementFrame.getBtn_SaveToExcelFile();
		btn_SaveToExcelFile.setEnabled(true);
		String dublikateKodeText = "";
		String KodeAreaText = "";
		String ChangeKodeText = "";
		
		for (int Index_row = 0; Index_row < model.getRowCount(); Index_row++) {

//			model.getValueAt(Index_row, egn_code_Colum).toString();
			if (!checkKorectKodeArea(zoneID, zoneKode_Colum, model, Index_row)) {
				KodeAreaText = "Има кодове извън областа на отдела. ";
			}
			if (checkChangeKodeAndNewKode(zoneKode_Colum, newZoneKode_Colum, model, Index_row)) {
				ChangeKodeText = "Има кодове, които ще се променят. ";
			}
			if (checkDublicateKodeInNewPerson(zoneID, newZoneKode_Colum, model, Index_row)) {
				btn_SaveToExcelFile.setEnabled(false);
				dublikateKodeText = "Има дублиращи се кодове";
			}
		}

		SpisakPersonelManegementFrame.getLbl_OtdelKodeArea()
				.setText(zoneArea + " " + KodeAreaText + ChangeKodeText + dublikateKodeText);

	}

	public static String generateTextKodeArea(int zoneID) {
		String textToolTipText = "";
		
		
		if (kodeGen != null) {
			String leterL = kodeGen.getLetter_L();
			String leterR = kodeGen.getLetter_R();
			int startCount = kodeGen.getStartCount();
			int endCount = kodeGen.getEndCount();

			if (zoneID == 1) {

				textToolTipText = svePersonManegement_textOtdelArea + " " + startCount + "÷" + endCount + "|" + leterR;
			}

			if (zoneID == 2) {
				textToolTipText = svePersonManegement_textOtdelArea + " " + leterL + "|" + startCount + "÷" + endCount;
			}
			if (zoneID == 3) {

				textToolTipText = svePersonManegement_textOtdelArea + " " + "Н|" + startCount + "÷" + endCount + "|" + leterR;
			}
		}

		return textToolTipText;
	}

	public static boolean checkKorectKodeArea(int zoneID, int zoneKode_Colum, DefaultTableModel model, int Index_row) {
		boolean fl = true;
		if (kodeGen != null) {
			String leterL = kodeGen.getLetter_L();
			String leterR = kodeGen.getLetter_R();
			String strNum = "";
			int startCount = kodeGen.getStartCount();
			int endCount = kodeGen.getEndCount();

			String kode = model.getValueAt(Index_row, zoneKode_Colum).toString();
			int sizeKode = kode.length();
			String leter = null;
			String Fleter = null;

			if (sizeKode > 0) {
				if (zoneID == 1) {
					if (!kode.isEmpty() && !kode.equals("ЕП-2") && !kode.equals("н")) {
						leter = kode.substring(sizeKode - 1, sizeKode);
						strNum = kode.substring(0, sizeKode - 1);
						Fleter = leterR;
					}
				}

				if (zoneID == 2) {
					if (!kode.isEmpty() && !kode.equals("н")) {
						leter = kode.substring(0, 1);
						strNum = kode.substring(1, sizeKode);
						Fleter = leterL;
					}
				}
				fl = checkLeterAndAreaNumber(Fleter, startCount, endCount, leter, strNum);
			}

		}
		return fl;
	}

	private static boolean checkLeterAndAreaNumber(String leterR, int startCount, int endCount, String leter,
			String strNum) {

		boolean fl;
		try {
			int numKode = Integer.parseInt(strNum);

			if (leter.equals(leterR) && numKode >= startCount && numKode <= endCount) {
				fl = true;
			} else {
				fl = false;
			}

		} catch (Exception e) {
			fl = false;
		}
		return fl;
	}

	public static void sortByFormulyarName(List<Spisak_Prilogenia> listSpisak_Prilogenia) {

		Collections.sort(listSpisak_Prilogenia, new Comparator<Spisak_Prilogenia>() {

			@Override
			public int compare(Spisak_Prilogenia o1, Spisak_Prilogenia o2) {
				return o2.getFormulyarName().compareTo(o1.getFormulyarName());
			}
		});

	}

	public static void sortByStartDateNew(List<PersonStatusNew> perStat) {

		Collections.sort(perStat, new Comparator<PersonStatusNew>() {

			@Override
			public int compare(PersonStatusNew o1, PersonStatusNew o2) {
				return o2.getStartDate().compareTo(o1.getStartDate());
			}
		});

	}

	public static void convertToBigCyr(JTextField textField, int zoneID) {

		String kode = textField.getText();
		int sizeKode = kode.length();
		if (sizeKode > 1 && zoneID > 0) {
			kode = AplicationMetods.literate(kode);
			kode = kode.toUpperCase();
			;
			textField.setText(kode);
		}

	}

	public static void generateListOtdels() {
		listOtdelKz = getListKZ();
		listOtdelVO = getListVO();
		listOtdelAll = getListALL();

	}

	public static boolean isSetKod(String kod) {
		return (!kod.equals("ЕП-2") && !kod.trim().equals("") && !kod.equals("н") && !inCodeNotNumber(kod));
	}

	private static List<PersonManegement> readListPersonFromFile(JPanel panel_AllSaerch, File file, JTextField textField) {
		ExcelFileBAK_Path = file.getPath();
		textField.setText(ExcelFileBAK_Path);
		Collection<String> different = new HashSet<String>();
		Workbook workbook = ReadExcelFileWBC.openExcelFile(file.getPath());
		String name = "", kod = "", FirstName = "", SecondName = "", LastName = "";
		Sheet sheet = workbook.getSheetAt(0);
		System.out.println("sheet "+sheet.getLastRowNum());
		Cell cell, cell1;
		List<PersonManegement> listPersonManegement = new ArrayList<>();
		Person person = null;
		for (int row = 0; row <= sheet.getLastRowNum(); row += 1) {

			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(0);
				cell1 = sheet.getRow(row).getCell(1);

				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

					name = ReadExcelFileWBC.getStringfromCell(cell);
					kod = ReadExcelFileWBC.getStringfromCell(cell1);

					String[] names = ReadExcelFileWBC.splitAllName(name);
					FirstName = names[0];
					SecondName = names[1];
					LastName = names[2];
					List<Person> listPerson = PersonDAO.getValuePersonByAllName(FirstName, SecondName, LastName);
					System.out.println("name "+name);
					if (listPerson.size() > 0) {
						if (listPerson.size() == 1) {
							person = listPerson.get(0);
						} else {
							person = choisePerson(panel_AllSaerch, listPerson, name);
						}
					} else {
						listPerson = PersonDAO.getValuePersonByFirstAndSecondName(FirstName, SecondName);
						if (listPerson.size() > 0) {
							person = choisePerson(panel_AllSaerch, listPerson, name);
						} else {
							listPerson = PersonDAO.getValuePersonByFirstAndLastName(FirstName, LastName);
							if (listPerson.size() > 0) {
								person = choisePerson(panel_AllSaerch, listPerson, name);
							} else {
								listPerson = PersonDAO.getValuePersonBySecondAndLastName(SecondName, LastName);
								if (listPerson.size() > 0) {
									person = choisePerson(panel_AllSaerch, listPerson, name);
								} else {
									listPerson = spisakPersonByName(FirstName, SecondName, LastName);
									if (listPerson != null && listPerson.size() > 0) {
										person = choisePerson(panel_AllSaerch, listPerson, name);
									} else {
										MessageDialog("Не намирам лице с име: " + name);
									}
								}
							}
						}
					}
					if(!crashRadfale) {
					if (person != null) {
						if(different.add(person.getEgn())) {
						listPersonManegement.add(new PersonManegement(person, kod));
						}else {
							MessageDialog("Лице с ЕГН: " +person.getEgn()+" се повтаря в списъка");
						}
						person = null;
					}
					}else {
						row = sheet.getLastRowNum();
					}
				}
			}

		}
		return listPersonManegement;

	}

	public static List<Person> spisakPersonByName(String FirstName, String SecondName, String LastName) {

		List<Person> listPerson = new ArrayList<>();

		listPerson = PersonDAO.getValuePersonByObject("FirstName", FirstName);
		if (listPerson.size() > 0) {
			return listPerson;
		}
		listPerson = PersonDAO.getValuePersonByObject("SecondName", SecondName);
		if (listPerson.size() > 0) {
			return listPerson;
		}
		listPerson = PersonDAO.getValuePersonByObject("LastName", LastName);
		if (listPerson.size() > 0) {
			return listPerson;
		}
		return null;
	}

	private static Person choisePerson(JPanel panel_AllSaerch, List<Person> listPerson, String choicePerson) {
		
		crashRadfale = false;
	
		new choisePersonByNameFrame(new JFrame(), panel_AllSaerch, choicePerson, listPerson);
		GeneralMethods.setWaitCursor(panel_AllSaerch);
		String str = choisePerson;

		if(str != null) {
		if (str != null && str.length() > 0) {
			String[] masiveStr = str.split(" ");
			return PersonDAO.getValuePersonByEGN(masiveStr[0]);
		}
		}else {
			crashRadfale = true;
		}
		return null;
	}
	
	static void panel_infoPanelTablePanel(Object[][] dataTable, JTextArea textArea, JPanel panel_AllSaerch,
			JPanel tablePane, JScrollPane scrollPane, JTextField textField_svePerson_Year, int zoneID) {

		final JScrollPane llPane = scrollPane;

		JButton btnBackToTable = SpisakPersonelManegementFrame.getBtnBackToTable();
		JButton btn_SaveToExcelFile = SpisakPersonelManegementFrame.getBtn_SaveToExcelFile();
		JButton btn_Export = SpisakPersonelManegementFrame.getBtn_Export();
		btn_Export.setEnabled(true);
		String[] columnNames = getTabHeader();
		int egn_code_Colum = 1;
		int zoneKode_Colum = 7;
		int newZoneKode_Colum = 8;
		int choice_Slekt_Colum = 9;

		DefaultTableModel model = new DefaultTableModel(dataTable, columnNames);

		JTable table = new JTable(model) {

			private static final long serialVersionUID = 1L;

			public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {

				Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
				comp.setForeground(Color.black);
				comp.setBackground(Color.WHITE);

				if (Index_col == zoneKode_Colum || Index_col == newZoneKode_Colum) {

					if (!checkKorectKodeArea(zoneID, zoneKode_Colum, model, Index_row)) {
						comp.setForeground(Color.BLUE);
					}
				}

				if (Index_col == zoneKode_Colum) {

					if (checkChangeKodeAndNewKode(zoneKode_Colum, newZoneKode_Colum, model, Index_row)) {
						comp.setForeground(Color.RED);
					}
				}

				if (Index_col == newZoneKode_Colum) {

					if (checkDublicateKodeInNewPerson(zoneID, newZoneKode_Colum, model, Index_row)) {
						comp.setForeground(Color.BLACK);
						comp.setBackground(Color.RED);

					} else {
						comp.setBackground(Color.WHITE);

					}

				}
				return comp;
			}

		};
		new TableFilterHeader(table, AutoChoices.ENABLED);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				checkKotrecAllCode(zoneID, model);
			}

			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();

				checkKotrecAllCode(zoneID, model);

				if (table.getSelectedColumn() == choice_Slekt_Colum) {
					table.rowAtPoint(e.getPoint());
					table.columnAtPoint(e.getPoint());
					if ((boolean) model.getValueAt(getSelectedModelRow(table), choice_Slekt_Colum)) {
						System.out.println("1");
						model.setValueAt(false, getSelectedModelRow(table), choice_Slekt_Colum);
					} else {
						System.out.println("2");
						model.setValueAt(true, getSelectedModelRow(table), choice_Slekt_Colum);
					}
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), choice_Slekt_Colum).toString();

					System.out.println(reqCodeStr);

				}

				if (e.getClickCount() == 2 && table.getSelectedColumn() == egn_code_Colum) {

					GeneralMethods.setWaitCursor(panel_AllSaerch);
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();
					System.out.println("egn " + reqCodeStr);
					btnBackToTable.setEnabled(true);
					btn_SaveToExcelFile.setEnabled(false);
					btn_Export.setEnabled(false);
					Person person = PersonDAO.getValuePersonByEGN(reqCodeStr);
					textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson("", person, false, 0));

					SpisakPersonelManegementFrame.viewInfoPanel();

					GeneralMethods.setDefaultCursor(panel_AllSaerch);
				}
			}
		});
//
//		JTableHeader header = table.getTableHeader();
//		
//		final TableCellRenderer hr = table.getTableHeader().getDefaultRenderer();
//		
		SwingUtilities.invokeLater(new Runnable() {

//			@SuppressWarnings("serial")
			public void run() {
				DefaultTableModel dtm = new DefaultTableModel(dataTable, columnNames) {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("rawtypes")
					private Class[] types2 = getCulumnClass();

					@SuppressWarnings({})
					@Override
					public Class<?> getColumnClass(int columnIndex) {
						return this.types2[columnIndex];
					}

					@Override
					public boolean isCellEditable(int row, int column) {
						if (column == choice_Slekt_Colum || column == newZoneKode_Colum) {
							return true;
						}
						return false;
					}

					@Override
					public Object getValueAt(int row, int col) {
						return dataTable[row][col];
					}

					public void setValueAt(Object value, int row, int col) {

						if (!dataTable[row][col].equals(value)) {
							dataTable[row][col] = value;
							fireTableCellUpdated(row, col);
							panel_infoPanelTablePanel(dataTable, textArea, panel_AllSaerch, tablePane, llPane,
									textField_svePerson_Year, zoneID);
							SpisakPersonelManegementFrame.viewTablePanel();
							btnBackToTable.setEnabled(false);
							checkKotrecAllCode(zoneID, model);
						}
					}

					public int getColumnCount() {
						return columnNames.length;
					}

					public int getRowCount() {
						return dataTable.length;
					}

				};

//		new TableFilterHeader(table, AutoChoices.ENABLED);

				dtm.fireTableDataChanged();
				table.setModel(dtm);
				table.setFillsViewportHeight(true);
				table.repaint();
				System.out.println("+++++++++++++ " + dataTable.length);

			}
		});

		table.getTableHeader().setReorderingAllowed(false);

		tablePane.removeAll();
		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane(table);
		tablePane.add(scrollPane, BorderLayout.CENTER);
		checkKotrecAllCode(zoneID, model);
	}

	private static boolean checkChangeKodeAndNewKode(int zoneKode_Colum, int newZoneKode_Colum, DefaultTableModel model,
			int Index_row) {
//		System.out.println(model+" "+Index_row+" "+ zoneKode_Colum+" "+ newZoneKode_Colum);
		return (model.getValueAt(Index_row, zoneKode_Colum) != null
				&& model.getValueAt(Index_row, newZoneKode_Colum) != null
				&& !model.getValueAt(Index_row, zoneKode_Colum).toString()
				.equals(model.getValueAt(Index_row, newZoneKode_Colum).toString()));

	}

	private static int getSelectedModelRow(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}

	static String[] getTabHeader() {
		String[] tableHeader = { " № ", "ЕГН", "Име", "Презиме", "Фамилия", "Отдел", "Дата на изм.", "ИД Код",
				"нов ИД Код", "selekt" };
		return tableHeader;
	}

	@SuppressWarnings("rawtypes")
	public static Class[] getCulumnClass() {
		Class[] types = { Integer.class, String.class, String.class, String.class, String.class, String.class,
				String.class, String.class, String.class, Boolean.class };
		return types;
	}

	public static int[] getCulumnSize() {
		int[] col = { 50, 100, 200, 200, 200, 500, 200, 200, 200, 50 };
		return col;
	}

	public static String getChoisePerson() {
		return choisePerson;
	}

	public static void setChoisePerson(String NewChoisePerson) {
		choisePerson = NewChoisePerson;
	}

	public static boolean fileIsOpened(String excelFilePath) {
		File file = new File(excelFilePath);
		File sameFileName = new File(excelFilePath);
		if (file.renameTo(sameFileName)) {
			System.out.println("file is closed");
			return false;
		} else {
			System.out.println("file is opened");
			return true;
		}
	}

	public static boolean checkIsClosedPersonAndExternalFile() {

		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");

		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if (testFilesToD.equals("1")) {
			filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
			filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}

		String filePath[] = { filePathPersonel, filePathExternal };

		String fileIsOpen = ReadFileBGTextVariable.getGlobalTextVariableMap().get("fileIsOpen");
		String dialogString = "<html>";
		for (int i = 0; i < filePath.length; i++) {
			File file = new File(filePath[i]);
			File sameFileName = new File(filePath[i]);
			if (!file.renameTo(sameFileName)) {
				System.out.println("file is opened");
				dialogString += sameFileName.getName() + " " + fileIsOpen + "<br>";

			}
		}
		System.out.println(dialogString + "  " + dialogString.length());
		if (dialogString.length() > 7) {
			return OptionDialog(dialogString + "</html>", "В Н И М А Н И Е");
		}
		return true;
	}

	public static boolean checkIsClosedMonthANDPersonAndExternalFile(ActionIcone round) {

		if (checkIsClosedPersonAndExternalFile() && checkIsClosedMonthPersonAndExternalFile()) {
			round.StopWindow();
			return true;
		}
		round.StopWindow();
		return false;

	}

	public static boolean checkIsClosedMonthPersonAndExternalFile() {

		String filePathMonthExternal = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("filePathMonthExternal_orig");
		String filePathMonthPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("filePathMonthPersonel_orig");

		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if (testFilesToD.equals("1")) {
			filePathMonthExternal = ReadFileBGTextVariable.getGlobalTextVariableMap()
					.get("filePathMonthExternal_orig_test");
			filePathMonthPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap()
					.get("filePathMonthPersonel_orig_test");
		}

		String filePath[] = { filePathMonthPersonel, filePathMonthExternal };

		String fileIsOpen = ReadFileBGTextVariable.getGlobalTextVariableMap().get("fileIsOpen");
		String dialogString = "<html>";
		for (int i = 0; i < filePath.length; i++) {
			File file = new File(filePath[i]);
			File sameFileName = new File(filePath[i]);
			if (!file.renameTo(sameFileName)) {
				System.out.println("file is opened");
				dialogString += sameFileName.getName() + " " + fileIsOpen + "<br>";

			}
		}
		System.out.println(dialogString + "  " + dialogString.length());
		if (dialogString.length() > 7) {
			return OptionDialog(dialogString + "</html>", "В Н И М А Н И Е");
		}
		return true;
	}

	public static List<OpenedExcelClass> openClosedPersonAndExternalFile() {
		List<OpenedExcelClass> list = new ArrayList<>();

		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");

		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if (testFilesToD.equals("1")) {
			filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
			filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}

		String filePath[] = { filePathPersonel, filePathExternal };

		for (int i = 0; i < filePath.length; i++) {
			File file = new File(filePath[i]);
			File sameFileName = new File(filePath[i]);
			if (file.renameTo(sameFileName)) {
				System.out.println("file is closed");
				if (openJavaExcelFile(filePath[i]) != null) {
					list.add(openJavaExcelFile(filePath[i]));
				}
			}
		}
		return list;
	}

	public static void ClosedPersonAndExternalFile(Workbook[] workbook) {

		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");

		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if (testFilesToD.equals("1")) {
			filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
			filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}

		String filePath[] = { filePathPersonel, filePathExternal };

		for (int i = 0; i < filePath.length; i++) {
			FileOutputStream outputStream;
			try {
				outputStream = new FileOutputStream("C://Users//AnubhavPatel//Desktop/Testing_Code_Macro.xlsm");
				try {
					workbook[i].write(outputStream);
					outputStream.close();
				} catch (IOException e) {

					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		}
	}

	public static void copyExcelFileToDestDir(String sourceFilePath, String destFilePath) {
		if (destFilePath == null) {
			destFilePath = ReadFileBGTextVariable.getGlobalTextVariableMap().get("destinationDir");
		}
		SimpleDateFormat sdfrmt = new SimpleDateFormat("ddMMyy");
		Date date = new Date();
		String strDate = sdfrmt.format(date);

		int slashIndex = sourceFilePath.lastIndexOf("\\") + 1;
		int dotIndex = sourceFilePath.indexOf(".");
		String destFileName = sourceFilePath.substring(slashIndex, dotIndex) + "-" + strDate
				+ sourceFilePath.substring(dotIndex);
		System.out.println(destFilePath + destFileName + "---------------------------------");
		File fileSorce = new File(sourceFilePath);
		File fileDest = new File(destFilePath + destFileName);

		try {
			FileUtils.copyFile(fileSorce, fileDest);
		} catch (IOException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}

	}

	public static void openMinimizedExcelFile(String excelFilePath) {
		try {
			Runtime.getRuntime().exec(new String[] { "cmd", "/c", "start", "/min", "EXCEL.EXE", excelFilePath });
		} catch (IOException ioe) {
			ResourceLoader.appendToFile(ioe);
			ioe.printStackTrace();
		}

	}

	public static OpenedExcelClass openJavaExcelFile(String excelFilePath) {

		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(excelFilePath);
			Workbook workbook = new HSSFWorkbook(inputStream);
			return new OpenedExcelClass(excelFilePath, workbook);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;

	}

	public static void clozedJavaExcelFile(List<OpenedExcelClass> listOpenedExcelFile) {
		for (OpenedExcelClass openedExcelClass : listOpenedExcelFile) {
			try {
				FileInputStream inputStream = new FileInputStream(openedExcelClass.getFilePath());
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void clozedExcelFile() {
		try {
			Runtime.getRuntime().exec(new String[] { "cmd", "/c", "taskkill", "/f", "/im", "EXCEL.EXE" });
		} catch (IOException ioe) {
			ResourceLoader.appendToFile(ioe);
			ioe.printStackTrace();
		}

	}

	public static Object[][] addListStringSelectionPersonToComboBox(List<PersonManegement> listSelectionPerson) {

		Object[][] dataTable = new Object[listSelectionPerson.size()][10];

//				"№", "EGN", 	"FirstName","SecondName","LastName","Otdel", "Date"
//				"Kod ",	"Kod",	"selekt"

		int zona = ZoneDAO.getValueZoneByNameTerritory(getZonaFromRadioButtons()).getId_Zone();
		int k = 0;
		for (PersonManegement personManegement : listSelectionPerson) {
			System.out.println("egn " + personManegement.getPerson().getEgn());
			dataTable[k][0] = (k + 1);
			dataTable[k][1] = personManegement.getPerson().getEgn();
			dataTable[k][2] = personManegement.getPerson().getFirstName();
			dataTable[k][3] = personManegement.getPerson().getSecondName();
			dataTable[k][4] = personManegement.getPerson().getLastName();
			dataTable[k][5] = PersonReferenceFrame.getLastWorkplaceByPerson(personManegement.getPerson());
			dataTable[k][6] = getLastMeasuring(personManegement.getPerson());
			dataTable[k][7] = PersonReferenceFrame.getLastKodeByPersonAndZone(personManegement.getPerson(), zona);
			dataTable[k][8] = personManegement.getKodeFromList();
			dataTable[k][9] = true;

			k++;
		}

		return dataTable;

	}

	static String getLastMeasuring(Person person) {
		String strDate = "-";
		String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
		Date endDate0 = null, startDate0 = null;
		try {
			startDate0 = sdfrmt.parse("01.01." + curentYear);
			endDate0 = sdfrmt.parse("31.12." + curentYear);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Measuring> listMeasuring = MeasuringDAO.getValueMeasuringByPersonAndYear(person, startDate0, endDate0);
		int sizeList = listMeasuring.size();
		if (sizeList > 0) {
			strDate = sdfrmt.format(listMeasuring.get(sizeList - 1).getDate());
		}
		return strDate;
	}

	public static void MessageDialog(String name) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, name, "Грешка", JOptionPane.PLAIN_MESSAGE,
				otherIcon);

	}

	public static Object[][] getDataTable() {
		return dataTable;
	}

	public static void setDataTable(Object[][] dataTable1) {
		dataTable = dataTable1;
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
				checkorektDate(textField_StartDate2);
			
			}
		});

		textField_StartDate2.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {

				checkorektDate(textField_StartDate2);
				
			}

		});

	}
	
	public static void checkorektDate(JTextField textFieldDate) {
		textFieldDate.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {
				JButton btn_SaveToExcelFile = SpisakPersonelManegementFrame.getBtn_SaveToExcelFile();
				if (AplicationMetods.incorrectDate(textFieldDate.getText())) {
					textFieldDate.setForeground(Color.RED);
					btn_SaveToExcelFile.setEnabled(false);
				} else {
					btn_SaveToExcelFile.setEnabled(true);
					textFieldDate.setForeground(Color.BLACK);
//					setComentGetTexTFromOtdelAndStartDate();
				}
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});
	}
	


}