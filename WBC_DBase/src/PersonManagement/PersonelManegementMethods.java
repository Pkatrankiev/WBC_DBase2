package PersonManagement;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.OldExcelFormatException;
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
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.Zone;

import PersonReference.PersonReferenceExportToExcell;
import PersonReference.PersonReferenceFrame;
import PersonReference.TextInAreaTextPanel;

import SaveToExcellFile.SaveToPersonelORExternalFile;
import SearchFreeKode.SearchFreeKodeFrame;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import AutoInsertMeasuting.AutoInsertMeasutingMethods;
import AutoInsertMeasuting.SaveReportMeasurTo_PersonelORExternalExcelFile;

public class PersonelManegementMethods {

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
	static Border defoutBorder = PersonelManegementFrame.getDefoutBorder();
	static Border redBorder = new SoftBevelBorder(BevelBorder.LOWERED, Color.RED, null, Color.RED, null);
	static String oldOtdelPerson;
	static String prilStartDateText = "";
	static String prilText = "";
	static List<String> listOtdelAll;
	static List<String> listOtdelKz;
	static List<String> listOtdelVO;
	static Object[][] dataTable;

//	static List<String> listZvenaFromDBase = SearchFreeKodeMethods.generateListZvenaFromDBase();
//	static List<List<String>> ListZvenaFromExcellFiles = SearchFreeKodeMethods.generateListZvenaFromExcellFiles();

	static String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
	static UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(1);
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

	static void ActionListener_Btn_Clear(JButton btn_savePerson_Insert, JButton btn_Clear_1, JTextArea textArea) {
		btn_Clear_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_savePerson_Insert.setEnabled(false);

				textArea.setText("");
				PersonelManegementFrame.setTextField_EGN(PersonelManegementFrame.getTextField_EGN(), "");
				PersonelManegementFrame.setTextField_FName(PersonelManegementFrame.getTextField_FName(), "");
				PersonelManegementFrame.setTextField_SName(PersonelManegementFrame.getTextField_SName(), "");
				PersonelManegementFrame.setTextField_LName(PersonelManegementFrame.getTextField_LName(), "");

				PersonelManegementFrame.setComboBox_Firm(PersonelManegementFrame.getComboBox_Firm(), "");
				setitemInChoise(PersonelManegementFrame.getComboBox_Firm(),
						PersonelManegementFrame.getComboBox_Otdel());
				PersonelManegementFrame.setComboBox_Otdel(PersonelManegementFrame.getComboBox_Otdel(), "");

			}
		});
	}

	static void ActionListener_Btn_SearchPerson(JButton btn_SearchPerson, JPanel panel_AllSaerch, JTextArea textArea,
			JButton btn_savePerson_Insert) {

		btn_SearchPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JTextField textField_EGN = PersonelManegementFrame.getTextField_EGN();
				JTextField textField_FName = PersonelManegementFrame.getTextField_FName();
				JTextField textField_SName = PersonelManegementFrame.getTextField_SName();
				JTextField textField_LName = PersonelManegementFrame.getTextField_LName();

				btn_savePerson_Insert.setEnabled(false);

				if (!allFieldsEmnty(textField_EGN, textField_FName, textField_SName, textField_LName)) {
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					multytextInTextArea = false;
					textArea.setText("");

					boolean fromManegementFrame = false;
					List<Person> listSelectionPerson = PersonReferenceFrame.getListSearchingPerson(fromManegementFrame);

					if (listSelectionPerson.size() == 0) {
						textArea.setText(notResults);

					}

					if (listSelectionPerson.size() == 1) {
						btn_savePerson_Insert.setEnabled(true);
						selectionPerson = listSelectionPerson.get(0);
						textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson("", selectionPerson, false, 0));

						textField_EGN.setText(selectionPerson.getEgn());
						textField_FName.setText(selectionPerson.getFirstName());
						textField_SName.setText(selectionPerson.getSecondName());
						textField_LName.setText(selectionPerson.getLastName());
					}

					if (listSelectionPerson.size() > 1) {
						multytextInTextArea = true;
						textArea.setText(addListStringSelectionPersonToTextArea(listSelectionPerson));
					}

					PersonelManegementFrame.viewInfoPanel();
					GeneralMethods.setDefaultCursor(panel_AllSaerch);
				}
			}

		});

	}

	static void ActionListener_Btn_SearchFreeKode(JButton btn_SearchFreeKode, Choice comboBox_Otdel) {
		btn_SearchFreeKode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String zona = getZonaFromRadioButtons();
				String otdel = comboBox_Otdel.getSelectedItem();
				System.out.println(zona + " - " + otdel);
				if (!zona.isEmpty() && !otdel.isEmpty()) {
					PersonelManegementMethods.startSearchFreeKodeFrame(otdel, zona);
				}
			}

		});
	}

	static void ActionListener_ComboBox_Firm(Choice comboBox_Firm, Choice comboBox_Otdel) {

		comboBox_Firm.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				setitemInChoise(comboBox_Firm, comboBox_Otdel);
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

	static void ActionListenerBtnBackToTable(JButton btnBackToTable,JTextArea textArea, JPanel tablePane,
			JPanel panel_AllSaerch, JScrollPane scrollPane, JTextField textField_svePerson_Year) {

		btnBackToTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataTable!=null) {
					PersonelManegementFrame.viewInfoPanel();
					panel_infoPanelTablePanel(dataTable, textArea, panel_AllSaerch, tablePane, scrollPane,
							textField_svePerson_Year, btnBackToTable);
					PersonelManegementFrame.viewTablePanel();
					btnBackToTable.setEnabled(false);
				}
			}

		});

	}

	
	static void ActionListener_Btn_savePerson_Insert(JButton btn_savePerson_Insert, JPanel panel_AllSaerch,
			JTextArea textArea) {
		btn_savePerson_Insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GeneralMethods.setWaitCursor(panel_AllSaerch);

				generateInfoByOnePerson(selectionPerson, textArea);

				fromListPersonManegement = false;
//				List<Spisak_Prilogenia> list = getListSpisak_Prilogenia_FromExcelFile(oldOtdelPerson);
//				PersonelManegementFrame.setListSpisak_Prilogenia(list);

				GeneralMethods.setDefaultCursor(panel_AllSaerch);
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

	static void ActionListener_ComboBox_savePerson_Otdel(Choice comboBox_savePerson_Otdel) {
		comboBox_savePerson_Otdel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
//				PersonelManegementFrame.setListSpisak_Prilogenia(
//						PersonelManegementMethods.generateListSpisPril(comboBox_savePerson_Otdel));
				JTextField fild = PersonelManegementFrame.getTextField_svePerson_KodKZ_1();
				checkKorectionSetInfoToFieldsInSavePersonPanel(fild, 0);

				setComentGetTexTFromOtdelAndStartDate();

			}

		});
	}

	static void ActionListener_TextArea(JButton btn_savePerson_Insert, JTextArea textArea, JPanel panel_AllSaerch) {

		JTextField textField_EGN = PersonelManegementFrame.getTextField_EGN();
		JTextField textField_FName = PersonelManegementFrame.getTextField_FName();
		JTextField textField_SName = PersonelManegementFrame.getTextField_SName();
		JTextField textField_LName = PersonelManegementFrame.getTextField_LName();

		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (multytextInTextArea) {
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					int position = textArea.getCaretPosition();
					String egn = "";

					try {
						int line = textArea.getLineOfOffset(position);
						int start = textArea.getLineStartOffset(line);
						int end = textArea.getLineEndOffset(line);
						String selectedLine = textArea.getDocument().getText(start, end - start);
						String[] selectedString = selectedLine.split(" ");
						egn = selectedString[0].trim();

					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					if (!egn.isEmpty()) {

						multytextInTextArea = false;
						btn_savePerson_Insert.setEnabled(true);
						selectionPerson = PersonDAO.getValuePersonByEGN(egn);
						textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson("", selectionPerson, false, 0));

						textField_EGN.setText(selectionPerson.getEgn());
						textField_FName.setText(selectionPerson.getFirstName());
						textField_SName.setText(selectionPerson.getSecondName());
						textField_LName.setText(selectionPerson.getLastName());
					}
					GeneralMethods.setDefaultCursor(panel_AllSaerch);
				}

			}

		});
	}

	static void ActionListener_Btn_InsertTo(JButton btn_Insert, int zoneID) {
		btn_Insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "";
				switch (zoneID) {
				case 3: {
					text = PersonelManegementFrame.getTextField_svePerson_KodKZ_1().getText();
					PersonelManegementFrame.getTextField_svePersonKodKZ_HOG().setText("Н" + text);
				}
					break;
				case 4: {
					text = PersonelManegementFrame.getTextField_svePerson_KodKZ_1().getText();
					PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_1().setText("Т" + text);
				}
					break;
				case 5: {
					text = PersonelManegementFrame.getTextField_svePersonKodKZ_2().getText();
					PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_2().setText(text + "Т");
					;
				}
					break;

				}
			}

		});

	}

	static void ActionListener_Btn_Spisak(JButton btn_Spisak) {
		btn_Spisak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
//				List<Spisak_Prilogenia> listSpisak_Prilogenia = PersonelManegementFrame.getListSpisak_Prilogenia();
//				
//				listSpisak_Prilogenia = addObhodenListToListSpisak_Prilogenia(listSpisak_Prilogenia);
				Choice comboBox_svePerson_Otdel = PersonelManegementFrame.getComboBox_savePerson_Otdel();
				List<Spisak_Prilogenia> listSpisak_Prilogenia = generateListSpisPril(comboBox_svePerson_Otdel);

				if (listSpisak_Prilogenia.size() > 0) {
					int selectedContent = PersonelManegementMethods.generateSelectSpisPrilFrame(listSpisak_Prilogenia);

					if (selectedContent >= 0) {
						String formuliarName = listSpisak_Prilogenia.get(selectedContent).getFormulyarName();
						String startDate = sdf.format(listSpisak_Prilogenia.get(selectedContent).getStartDate());
						String endDate = sdf.format(listSpisak_Prilogenia.get(selectedContent).getEndDate());

						JTextField textField_svePerson_Spisak = PersonelManegementFrame.getTextField_svePerson_Spisak();
						JTextField textField_svePerson_StartDate = PersonelManegementFrame
								.getTextField_savePerson_StartDate();
						JTextField textField_svePerson_EndDate = PersonelManegementFrame
								.getTextField_savePerson_EndDate();

						textField_svePerson_Spisak.setText(formuliarName);
						textField_svePerson_StartDate.setText(startDate);
						textField_svePerson_EndDate.setText(endDate);
					}
				}

			}

		});
	}

	static void ActionListener_JTextField(JTextField fild, int zoneID) {

		fild.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {
				checkKorectionSetInfoToFieldsInSavePersonPanel(fild, zoneID);
			}

		});
		fild.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				checkKorectionSetInfoToFieldsInSavePersonPanel(fild, zoneID);
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				checkKorectionSetInfoToFieldsInSavePersonPanel(fild, zoneID);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

	}

	static void ActionListener_JTextField_svePerson_Spisak(JTextField textField_svePerson_Spisak) {
		textField_svePerson_Spisak.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {
				setComentGetTexTFromOtdelAndStartDate();
				checkKorectionSetInfoToFieldsInSavePersonPanel(textField_svePerson_Spisak, 0);
			}

		});
		textField_svePerson_Spisak.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				setComentGetTexTFromOtdelAndStartDate();
				checkKorectionSetInfoToFieldsInSavePersonPanel(textField_svePerson_Spisak, 0);
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setComentGetTexTFromOtdelAndStartDate();
				checkKorectionSetInfoToFieldsInSavePersonPanel(textField_svePerson_Spisak, 0);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

	}
	
	static void ActionListener_Btn_SaveToExcelFile(JFrame frame, JButton btn_SaveToExcelFile) {
		btn_SaveToExcelFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");

				JTextField textField_svePerson_EGN = PersonelManegementFrame.getTextField_svePerson_EGN();
				JTextField textField_svePerson_FName = PersonelManegementFrame.getTextField_svePerson_FName();
				JTextField textField_svePerson_SName = PersonelManegementFrame.getTextField_svePerson_SName();
				JTextField textField_svePerson_LName = PersonelManegementFrame.getTextField_svePerson_LName();
				JTextField textField_svePerson_KodKZ_1 = PersonelManegementFrame.getTextField_svePerson_KodKZ_1();
				JTextField textField_svePerson_KodKZ_2 = PersonelManegementFrame.getTextField_svePersonKodKZ_2();
				JTextField textField_svePersonKod_KZ_HOG = PersonelManegementFrame.getTextField_svePersonKodKZ_HOG();
				JTextField textField_svePersonKod_KZ_Terit_1 = PersonelManegementFrame
						.getTextField_svePersonKodKZ_Terit_1();
				JTextField textField_svePersonKod_KZ_Terit_2 = PersonelManegementFrame
						.getTextField_svePersonKodKZ_Terit_2();
				Choice comboBox_savePerson_Firm = PersonelManegementFrame.getComboBox_savePerson_Firm();
				Choice comboBox_svePerson_Otdel = PersonelManegementFrame.getComboBox_savePerson_Otdel();
				JTextField textField_svePerson_Spisak = PersonelManegementFrame.getTextField_svePerson_Spisak();
				JTextField textField_savePerson_StartDate = PersonelManegementFrame.getTextField_savePerson_StartDate();
				JTextField textField_savePerson_EndDate = PersonelManegementFrame.getTextField_savePerson_EndDate();
				JTextField textField_svePerson_Coment = PersonelManegementFrame.getTextField_svePerson_Coment();
				JTextField textField__svePerson_Year = PersonelManegementFrame.getTextField_svePerson_Year();

				String newYear = PersonelManegementFrame.getTextField_svePerson_Year().getText();
				
				JCheckBox checkbx_svePerson_EnterInZone = PersonelManegementFrame.getChckbx_svePerson_EnterInZone();
				JCheckBox checkbx_svePerson_EnterInListChengeKode = PersonelManegementFrame.getChckbx_svePerson_EnterInListChengeKode();
				JCheckBox checkbx_svePerson__SaveToExcel = PersonelManegementFrame.getCheckbx_svePerson_SaveToExcel();
				
				boolean checkbx_EnterInZone = checkbx_svePerson_EnterInZone.isSelected();
				boolean checkbx_EnterInListChengeKode = checkbx_svePerson_EnterInListChengeKode.isSelected();
				boolean checkbx_SaveToExcel = checkbx_svePerson__SaveToExcel.isSelected();
			
				if(PersonelManegementMethods.checkIsClosedPersonAndExternalFile()) {
					
					
				if (checkInfoOtdel(comboBox_svePerson_Otdel)) {

	
					
					
					
					String comment = textField_svePerson_Coment.getText().trim();
					
					
					
					String[] infoForPersonFromFrame = { textField_svePerson_KodKZ_1.getText().trim(),
							textField_svePerson_KodKZ_2.getText().trim(),
							textField_svePersonKod_KZ_HOG.getText().trim(),
							textField_svePersonKod_KZ_Terit_1.getText().trim(),
							textField_svePersonKod_KZ_Terit_2.getText().trim(),
							textField_svePerson_FName.getText().trim(), 
							textField_svePerson_SName.getText().trim(),
							textField_svePerson_LName.getText().trim() };
					
					
					int lastrekord = 1;
					
					if(fromListPersonManegement) {
						lastrekord = dataTable.length;
					}
					
					for (int i = 0; i < lastrekord; i++) {
						
//			************** set PERSON
						
						String egn = textField_svePerson_EGN.getText();
						boolean saveToExcel = checkbx_SaveToExcel;
						if(fromListPersonManegement) {
							lastrekord = dataTable.length;
							egn =  (String) dataTable[i][1];
							saveToExcel = (boolean) dataTable[i][9];
						}
										
					
					
					Person person = PersonDAO.getValuePersonByEGN(egn);
					
					if (person == null) {
						PersonDAO.setValuePerson(egn, textField_svePerson_FName.getText(),
								textField_svePerson_SName.getText(), textField_svePerson_LName.getText());
					}
					System.out.println("egn "+egn);
					person = PersonDAO.getValuePersonByEGN(egn);	
					System.out.println("person "+person.getFirstName());
					
//					************** set KodeStatus				
					if(fromListPersonManegement) {
						String[] kode = getKodeStatusByPersonFromDBase(person);
						for (int j = 0; j < kode.length; j++) {
							infoForPersonFromFrame[j] = kode[j];
						}
						checkbx_EnterInZone = true;	
						int zona = ZoneDAO.getValueZoneByNameTerritory(getZonaFromRadioButtons()).getId_Zone();
						System.out.println(zona+" "+dataTable[i][8]);
						
						infoForPersonFromFrame[zona-1] = (String) dataTable[i][8];
						infoForPersonFromFrame[5] = (String)dataTable[i][2];
						infoForPersonFromFrame[6] = (String) dataTable[i][3];
						infoForPersonFromFrame[7] = (String) dataTable[i][4];
					}
					
					if (checkInfoPersonAndKodeStatus(infoForPersonFromFrame, person, checkbx_EnterInZone)) {

						Workplace workplace = WorkplaceDAO
								.getValueWorkplaceByObject("Otdel", comboBox_svePerson_Otdel.getSelectedItem()).get(0);
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

							Spisak_PrilogeniaDAO.setValueSpisak_Prilogenia(formuliarName, newYear, sDate, eDate, workplace,"");
							spisPril = Spisak_PrilogeniaDAO
									.getLastSaveObjectFromValueSpisak_PrilogeniaByYear_Workplace_StartDate(newYear, sDate,
											workplace.getId_Workplace());

							SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
							Date curentDate = Calendar.getInstance().getTime();
							try {
								curentDate = sdf2.parse(sdf2.format(curentDate));
							} catch (ParseException e1) {

							}
							
							String PerStatNewSet = ReadFileBGTextVariable.getGlobalTextVariableMap().get("PerStatNewSet");

							if(PerStatNewSet.equals("1")) {
								PersonStatusNewDAO.setValuePersonStatusNew(person, workplace, spisPril.getFormulyarName(), spisPril.getStartDate(), spisPril.getEndDate(), spisPril.getYear(),  user, curentDate,
										comment);
							}else {
							PersonStatusDAO.setValuePersonStatus(person, workplace, spisPril, user, curentDate,
									comment);
							}

						}

//		****************** set KODESTATUS
						setKodeStatusInDBase(person, infoForPersonFromFrame, newYear);

						

						System.out.println(saveToExcel);
						
						if(saveToExcel) {
							System.out.println("**********************************");
						SaveToPersonelORExternalFile.saveInfoFromPersonManegementToExcelFile(frame, person,
								comboBox_savePerson_Firm.getSelectedItem(), spisPril, user, comment, workplace, oldOtdelPerson, checkbx_EnterInZone, checkbx_EnterInListChengeKode, obhodenList);
					
					}
					}

				}
				}
				}
			
		}
		
		});
	}

	static void ActionListener_Btn_Export(JButton btn_Export, JPanel save_Panel, JPanel button_Panel) {
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
							PersonelManegementMethods.getTabHeader(), button_Panel);

				}
			}
		});
	}

	static void ActionListener_chckbx_svePerson__isEnterInZone(JCheckBox chckbx_svePerson__isEnterInZone) {
	chckbx_svePerson__isEnterInZone.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			boolean fl;
			if(chckbx_svePerson__isEnterInZone.isSelected()) {
				chckbx_svePerson__isEnterInZone.setText("yes");
				fl=false;
			}else {
				chckbx_svePerson__isEnterInZone.setText("no");
				fl= true;
			}
			
			setVizible_EnterInListChengeKode_LabelAndCheckBox(fl);
		}
	});
	}
	
	protected static String checkFormuliarNameToZoneKode() {
		JTextField textField_svePerson_Spisak = PersonelManegementFrame.getTextField_svePerson_Spisak();
		String str_KodKZ_1 = PersonelManegementFrame.getTextField_svePerson_KodKZ_1().getText();
		String str_KodKZ_2 = PersonelManegementFrame.getTextField_svePersonKodKZ_2().getText();
		String str_KZ_HOG = PersonelManegementFrame.getTextField_svePersonKodKZ_HOG().getText();
//		String LabelText = PersonelManegementFrame.getLbl_svePerson_Text_Check_EnterInZone().getText();
		boolean fl = true;
		String formuliarName = textField_svePerson_Spisak.getText();
		String numFormuliarName = "";
		String charName ="",str = "";
		for (int i = 0; i < formuliarName.length(); i++) {
			charName = formuliarName.substring(0, 1);
			formuliarName = formuliarName.substring(1);
			
			try {
				Integer.parseInt(charName);
				numFormuliarName += charName;
			} catch (NumberFormatException e0) {
				i = formuliarName.length();
			}
		}
		if(numFormuliarName.contains("1") && (str_KodKZ_1.isEmpty() || str_KodKZ_1.equals("ЕП-2") || str_KodKZ_1.equals("н") )){
			fl = false;
		}
		if(numFormuliarName.contains("2") && (str_KodKZ_2.isEmpty() || str_KodKZ_2.equals("ЕП-2") || str_KodKZ_2.equals("н") )){
			fl = false;
		}
		if(numFormuliarName.contains("3") && (str_KZ_HOG.isEmpty() || str_KZ_HOG.equals("ЕП-2") || str_KZ_HOG.equals("н") )){
			fl = false;
		}
		
		if(!fl) {
			textField_svePerson_Spisak.setBorder(redBorder);
			str = ReadFileBGTextVariable.getGlobalTextVariableMap().get("svePersonManegement_NonKorektPrefikceFormuliar");
		}else {
			textField_svePerson_Spisak.setBorder(defoutBorder);
		}
		
		return str;
		
		
	}
	
	private static void setKodeStatusInDBase(Person person, String[] infoForPerson, String year) {
		Date setdate = Calendar.getInstance().getTime();
		UsersWBC setDataUser = UsersWBCDAO.getValueUsersWBCByID(1);
		for (int i = 0; i < 5; i++) {

			String kodeByFrame = infoForPerson[i];
			KodeStatus kodeStat = KodeStatusDAO.getKodeStatusByPersonZoneYear(person, i + 1, year);
			KodeStatus newKodeStat = new KodeStatus(person, kodeByFrame, ZoneDAO.getValueZoneByID(i+1), true,
					year, "", setDataUser, setdate);
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

	private static void checkKorectionSetInfoToFieldsInSavePersonPanel(JTextField fild, int zoneID) {
		String text = "";
		convertToBigCyr(fild, zoneID);
		checkKorectFormatKode(zoneID);
		if(corectFormatKode) {
		checkIfSetKodeToEnableInsertBtn(fild, zoneID);
		text += " " + checkEGNByNewPerson();
		text += " " + checkDublicateKodeInNewPerson(fild, zoneID);
		text += " " + checInsertNewPerson();
		text += " " + checkKorectWritenKode();
		text += " " +checkFormuliarNameToZoneKode();
		
		}
		setTextToLabel_svePerson_Text_Check_EnterInZone(text);
		
	}

	private static void setTextToLabel_svePerson_Text_Check_EnterInZone(String text) {
		if(text.length()>110) {
		 	int index = text.lastIndexOf(" ", 110);
			String firstText = text.substring(0, index);
			String lastText = text.substring(index+1, text.length());
			text = "<html>"+firstText+"<br>"+lastText+"</html>";
		}
		PersonelManegementFrame.getLbl_svePerson_Text_Check_EnterInZone().setText(text);
	}

	private static String checkEGNByNewPerson() {
		String svePersonManegement_newPerson = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("svePersonManegement_newPerson");
		JTextField textField_svePerson_EGN = PersonelManegementFrame.getTextField_svePerson_EGN();
		String egn = textField_svePerson_EGN.getText();
		Person person = PersonDAO.getValuePersonByEGN(egn);
		if (person == null) {
			return svePersonManegement_newPerson;
		}
		return "";
	}

	protected static void checkIfSetKodeToEnableInsertBtn(JTextField fild, int zoneID) {
		String kode = fild.getText();
		switch (zoneID) {
		case 1: {
			if (!kode.isEmpty() && fild.getToolTipText() == null && !kode.equals("ЕП-2") && !kode.equals("н")) {
				PersonelManegementFrame.getTextField_svePersonKodKZ_HOG().setEnabled(true);
				PersonelManegementFrame.getBtn_InsertToHOG().setEnabled(true);
				PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_1().setEnabled(true);
				PersonelManegementFrame.getBtn_InsertToTerit_1().setEnabled(true);
				
			} else {
				PersonelManegementFrame.getTextField_svePersonKodKZ_HOG().setText("н");
				PersonelManegementFrame.getTextField_svePersonKodKZ_HOG().setEnabled(false);
				PersonelManegementFrame.getBtn_InsertToHOG().setEnabled(false);
				PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_1().setText("н");
				PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_1().setEnabled(false);
				PersonelManegementFrame.getBtn_InsertToTerit_1().setEnabled(false);
			}
		}
			break;

		case 2: {
			if (!kode.isEmpty() && fild.getToolTipText() == null) {
				PersonelManegementFrame.getBtn_InsertToTerit_2().setEnabled(true);
				PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_2().setEnabled(true);
			} else {
				PersonelManegementFrame.getBtn_InsertToTerit_2().setEnabled(false);
				PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_2().setText("н");
				PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_2().setEnabled(false);
			}
		}
			break;

		}

	}

	static void generateInfoByOnePerson(Person person, JTextArea textArea) {

//		JTextField textField_EGN = PersonelManegementFrame.getTextField_EGN(); 
//		JTextField textField_FName = PersonelManegementFrame.getTextField_FName(); 
//		JTextField textField_SName = PersonelManegementFrame.getTextField_SName();
//		JTextField textField_LName = PersonelManegementFrame.getTextField_LName(); 

		JTextField textField_savePerson_EGN = PersonelManegementFrame.getTextField_svePerson_EGN();
		JTextField textField_savePerson_FName = PersonelManegementFrame.getTextField_svePerson_FName();
		JTextField textField_savePerson_SName = PersonelManegementFrame.getTextField_svePerson_SName();
		JTextField textField_savePerson_LName = PersonelManegementFrame.getTextField_svePerson_LName();

		JTextField textField_svePerson_KodKZ_1 = PersonelManegementFrame.getTextField_svePerson_KodKZ_1();
		JTextField textField_svePersonKodKZ_2 = PersonelManegementFrame.getTextField_svePersonKodKZ_2();
		JTextField textField_svePersonKodKZ_HOG = PersonelManegementFrame.getTextField_svePersonKodKZ_HOG();
		JTextField textField_svePersonKodKZ_Terit_1 = PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_1();
		JTextField textField_svePersonKodKZ_Terit_2 = PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_2();
		Choice comboBox_svePerson_Firm = PersonelManegementFrame.getComboBox_savePerson_Firm();
		Choice comboBox_svePerson_Otdel = PersonelManegementFrame.getComboBox_savePerson_Otdel();

		textField_savePerson_EGN.setText(person.getEgn());
		textField_savePerson_FName.setText(person.getFirstName());
		textField_savePerson_SName.setText(person.getSecondName());
		textField_savePerson_LName.setText(person.getLastName());

//		String[] masiveKode = getMasiveFromKodeAndWorkPlaceFromExcelFile(person.getEgn());
		String[] masiveKode = getMasiveFromKodeAndWorkPlaceFromDBase(person);

		textField_svePerson_KodKZ_1.setText(masiveKode[0]);
		textField_svePersonKodKZ_2.setText(masiveKode[1]);
		textField_svePersonKodKZ_HOG.setText(masiveKode[2]);
		textField_svePersonKodKZ_Terit_1.setText(masiveKode[3]);
		textField_svePersonKodKZ_Terit_2.setText(masiveKode[4]);

		comboBox_svePerson_Firm.select(masiveKode[5]);
		setitemInChoise(comboBox_svePerson_Firm, comboBox_svePerson_Otdel);
		comboBox_svePerson_Otdel.select(masiveKode[6]);
		oldOtdelPerson = masiveKode[6];
		textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson("", person, false, 0));
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

	private static boolean checkInfoPersonAndKodeStatus(String[] infoForPersonFromFrame, Person person, boolean checkbx_EnterInZone) {

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
			
			System.out.println(i+" | "+kodeFromDBase[i]	+" | "+infoForPersonFromFrame[i]);
		}

		String errorStr = "";
		if (!infoForPersonFromFrame[0].equals(kodeFromDBase[0])) {
			errorStr += "KZ-1("+kodeFromDBase[0]+"->"+infoForPersonFromFrame[0]+"), ";
			fl_KodeIsChange = true;
		}

		if (!infoForPersonFromFrame[1].equals(kodeFromDBase[1])) {
			errorStr += "KZ-2("+kodeFromDBase[1]+"->"+infoForPersonFromFrame[1]+"), ";
			fl_KodeIsChange = true;
		}
	
		if (!infoForPersonFromFrame[2].equals(kodeFromDBase[2])) {
			errorStr += "KZ-HOG("+kodeFromDBase[2]+"->"+infoForPersonFromFrame[2]+"), ";
			fl_KodeIsChange = true;

		}
		
		if (!infoForPersonFromFrame[3].equals(kodeFromDBase[3])) {
			errorStr += "Ter-1("+kodeFromDBase[3]+"->"+infoForPersonFromFrame[3]+"->"+"), ";
			fl_KodeIsChange = true;
		}

		
		if (!infoForPersonFromFrame[4].equals(kodeFromDBase[4])) {
			errorStr += "Ter-2("+kodeFromDBase[4]+"->"+infoForPersonFromFrame[4]+"), ";
			fl_KodeIsChange = true;
		}

		if (!infoForPersonFromFrame[5].equals(person.getFirstName())) {
			errorStr += "FName("+person.getFirstName()+"->"+infoForPersonFromFrame[5]+"), ";;
		}
		if (!infoForPersonFromFrame[6].equals(person.getSecondName())) {
			errorStr += "SName("+person.getSecondName()+"->"+infoForPersonFromFrame[6]+"), ";
		}
		if (!infoForPersonFromFrame[7].equals(person.getLastName())) {
			errorStr += "LName("+person.getLastName()+"->"+infoForPersonFromFrame[7]+"), ";
		}

		System.out.println("errorStr " + errorStr);
		if (errorStr.length() > 2 ) {
			errorStr = errorStr.substring(0, errorStr.length() - 2);
			
			if (OptionDialog(errorStr, svePersonManegement_chengedSomeFilds)) {
				if (errorStr.contains("Name")) {
					person.setFirstName(infoForPersonFromFrame[5]);
					person.setSecondName(infoForPersonFromFrame[6]);
					person.setLastName(infoForPersonFromFrame[7]);
					PersonDAO.updateValuePerson(person);
				}
				if(fromListPersonManegement) {
					return true;
				}
				if ( fl_KodeIsChange && checkbx_EnterInZone) {
					System.out.println("**************************************");
					return OptionDialog(svePersonManegement_EnteredInZoneANDChangeKode,  "В Н И М А Н И Е");
			
			}else {
				return true;
			}
			}else {
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
			System.out.println(i+" "+kode[i]);
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

	private static void setComentGetTexTFromOtdelAndStartDate() {
		String text = "";
		obhodenList = false;
		prilText = PersonelManegementFrame.getTextField_svePerson_Spisak().getText();
		prilStartDateText = PersonelManegementFrame.getTextField_savePerson_StartDate().getText();
		if (!Otdel_OK) {
			
			if (prilText.toLowerCase().contains("пр.9") || prilText.toLowerCase().contains("пр-9")) {
				text = svePersonManegement_WithPril + " 9 от ";
			}
			if (prilText.toLowerCase().contains("пр.1") || prilText.toLowerCase().contains("пр-1")) {
				text = svePersonManegement_WithPril + " 1 от ";
			}
			if (!text.isEmpty()) {
				text = text + prilStartDateText + "г. " + svePersonManegement_ChangeOtdel + " " + oldOtdelPerson;
			}

		}
		if (prilText.contains("Обходен")) {
			text = prilText + " от " + prilStartDateText + "г. ";
			obhodenList = true;
		}
		PersonelManegementFrame.getTextField_svePerson_Coment().setText(text);
	}

	public static boolean OptionDialog(String mesage, String textOptionDialogFrame) {
		
		String[] options = { "Back", "Continued" };
		int x = JOptionPane.showOptionDialog(null, mesage, textOptionDialogFrame, JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		System.out.println(x+" -----------------");
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
			JTextField textField_LName) {
		return (textField_EGN.getText().trim().isEmpty() && textField_FName.getText().trim().isEmpty()
				&& textField_SName.getText().trim().isEmpty() && textField_LName.getText().trim().isEmpty());

	}

	static String addListStringSelectionPersonToTextArea(List<Person> listSelectionPerson) {
		String text = "";
		for (Person person : listSelectionPerson) {
			text = text + person.getEgn() + " " + SaveReportMeasurTo_PersonelORExternalExcelFile.getNamePerson(person) + "\n";
		}

		return text;

	}

	public static String[] getMasiveFromKodeAndWorkPlaceFromDBase(Person person) {

		String[] masive = new String[7] ;
		String[] masiveKodeStatus = getKodeStatusByPersonFromDBase(person);

		String PerStatNewSet = ReadFileBGTextVariable.getGlobalTextVariableMap().get("PerStatNewSet");

		if(PerStatNewSet.equals("1")) {
			
			List<PersonStatusNew> listPerStat = PersonStatusNewDAO.getValuePersonStatusNewByPerson(person);
			sortByStartDateNew( listPerStat);
			PersonStatusNew perStat = listPerStat.get(0);
				for (int i = 0; i < masiveKodeStatus.length; i++) {
					masive[i] = masiveKodeStatus[i];
				}
			
			masive[5] = perStat.getWorkplace().getFirmName();
			masive[6] = perStat.getWorkplace().getOtdel();
		}else {
		
		List<PersonStatus> listPerStat = PersonStatusDAO.getValuePersonStatusByPerson(person);
		sortByStartDate( listPerStat);
		PersonStatus perStat = listPerStat.get(0);
			for (int i = 0; i < masiveKodeStatus.length; i++) {
				masive[i] = masiveKodeStatus[i];
			}
		
		masive[5] = perStat.getWorkplace().getFirmName();
		masive[6] = perStat.getWorkplace().getOtdel();
		}

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
		if(PerStatNewSet.equals("1")) {
			List<Spisak_Prilogenia> list = Spisak_PrilogeniaDAO.getListSpisak_PrilogeniaByYear_Workplace(curentYear, workPl.getId_Workplace());
			for (Spisak_Prilogenia spPr : list) {
				listSpInt.add(spPr.getSpisak_Prilogenia_ID());
			}
		}else {
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
		if (PersonelManegementFrame.getRdbtn_KodKZ1().isSelected()) {
			System.out.println("1");
			return listZone.get(0).getNameTerritory();
		}
		if (PersonelManegementFrame.getRdbtn_KodKZ2().isSelected()) {
			System.out.println("2");
			return listZone.get(1).getNameTerritory();
		}
		if (PersonelManegementFrame.getRdbtn_KodKZHOG().isSelected()) {
			System.out.println("3");
			return listZone.get(2).getNameTerritory();
		}
		if (PersonelManegementFrame.getRdbtn_KodTerit_1().isSelected()) {
			System.out.println("4");
			return listZone.get(3).getNameTerritory();
		}
		if (PersonelManegementFrame.getRdbtn_KodTerit_2().isSelected()) {
			System.out.println("5");
			return listZone.get(4).getNameTerritory();
		}

		return "";
	}

	public static void checkorektDate(JTextField textFieldDate) {
		textFieldDate.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {

				if (AplicationMetods.incorrectDate(textFieldDate.getText())) {
					textFieldDate.setForeground(Color.RED);
				} else {
					textFieldDate.setForeground(Color.BLACK);
					setComentGetTexTFromOtdelAndStartDate();
				}
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});
	}

	public static String checInsertNewPerson() {

		String svePersonManegement_newKode = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("svePersonManegement_newKode");
		String svePersonManegement_IsEnteredInZone = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("svePersonManegement_IsEnteredInZone");
		String svePersonManegement_newOtdel = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("svePersonManegement_newOtdel");
		String svePersonManegement_FirstNameError = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("svePersonManegement_FirstNameError");
		String svePersonManegement_SekondNameError = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("svePersonManegement_SekondNameError");
		String svePersonManegement_LastNameError = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("svePersonManegement_LastNameError");

		JTextField textField_svePerson_EGN = PersonelManegementFrame.getTextField_svePerson_EGN();
		JTextField textField_svePerson_FName = PersonelManegementFrame.getTextField_svePerson_FName();
		JTextField textField_svePerson_SName = PersonelManegementFrame.getTextField_svePerson_SName();
		JTextField textField_svePerson_LName = PersonelManegementFrame.getTextField_svePerson_LName();
		JTextField textField_svePerson_KodKZ_1 = PersonelManegementFrame.getTextField_svePerson_KodKZ_1();
		JTextField textField_svePerson_KodKZ_2 = PersonelManegementFrame.getTextField_svePersonKodKZ_2();
		JTextField textField_svePersonKod_KZ_HOG = PersonelManegementFrame.getTextField_svePersonKodKZ_HOG();
		JTextField textField_svePersonKod_KZ_Terit_1 = PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_1();
		JTextField textField_svePersonKod_KZ_Terit_2 = PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_2();
		Choice comboBox_svePerson_Otdel = PersonelManegementFrame.getComboBox_savePerson_Otdel();
		String egn = textField_svePerson_EGN.getText();
		Person person = PersonDAO.getValuePersonByEGN(egn);
		String textCheck = "";
		if (person != null && !egn.isEmpty() && corectFormatKode) {

//			First Name
			FirstNameOK = false;
			if (person.getFirstName().equals(textField_svePerson_FName.getText())) {
				FirstNameOK = true;
				textField_svePerson_FName.setBorder(defoutBorder);
			} else {
				textField_svePerson_FName.setBorder(redBorder);
				textCheck += svePersonManegement_FirstNameError;
			}
//			Second Name
			SekondNameOK = false;
			if (person.getSecondName().equals(textField_svePerson_SName.getText())) {
				SekondNameOK = true;
				textField_svePerson_SName.setBorder(defoutBorder);
			} else {
				textField_svePerson_SName.setBorder(redBorder);
				if (!textCheck.isEmpty()) {
					textCheck += ", ";
				}
				textCheck += svePersonManegement_SekondNameError;
			}
//			Last Name
			LastNameOK = false;
			if (person.getLastName().equals(textField_svePerson_LName.getText())) {
				LastNameOK = true;
				textField_svePerson_LName.setBorder(defoutBorder);
			} else {
				textField_svePerson_LName.setBorder(redBorder);
				if (!textCheck.isEmpty()) {
					textCheck += ", ";
				}
				textCheck += svePersonManegement_LastNameError;
			}

			String[] simpleKode = getKodeStatusByPersonFromDBase(person);

//			KodKZ_1
			KodKZ_1_OK = false;
			if (simpleKode[0].equals("ЕП-2") || simpleKode[0].equals(textField_svePerson_KodKZ_1.getText())) {
				KodKZ_1_OK = true;
				textField_svePerson_KodKZ_1.setBorder(defoutBorder);
			} else {
				textField_svePerson_KodKZ_1.setBorder(redBorder);
			}
			
//			KodKZ_2
			KodKZ_2_OK = false;
			if (simpleKode[1].equals("н") || simpleKode[1].equals(textField_svePerson_KodKZ_2.getText())) {
				KodKZ_2_OK = true;
				textField_svePerson_KodKZ_2.setBorder(defoutBorder);
			} else {
				textField_svePerson_KodKZ_2.setBorder(redBorder);
			}
			
//			KZ_HOG
			KZ_HOG_OK = false;
			if (simpleKode[2].equals("н") || simpleKode[2].equals(textField_svePersonKod_KZ_HOG.getText())) {
				KZ_HOG_OK = true;
				textField_svePersonKod_KZ_HOG.setBorder(defoutBorder);
			} else {
				textField_svePersonKod_KZ_HOG.setBorder(redBorder);
			}
			
//			KZ_Terit_1
			KZ_Terit_1_OK = false;
			if (simpleKode[3].equals("н") || simpleKode[3].equals(textField_svePersonKod_KZ_Terit_1.getText())) {
				KZ_Terit_1_OK = true;
				textField_svePersonKod_KZ_Terit_1.setBorder(defoutBorder);
			} else {
				textField_svePersonKod_KZ_Terit_1.setBorder(redBorder);
			}
			
//			KZ_Terit_2
			KZ_Terit_2_OK = false;
			if (simpleKode[4].equals("н") || simpleKode[4].equals(textField_svePersonKod_KZ_Terit_2.getText())) {
				KZ_Terit_2_OK = true;
				textField_svePersonKod_KZ_Terit_2.setBorder(defoutBorder);
			} else {
				textField_svePersonKod_KZ_Terit_2.setBorder(redBorder);
			}
			
			Otdel_OK = false;
			if (oldOtdelPerson != null && oldOtdelPerson.equals(comboBox_svePerson_Otdel.getSelectedItem())) {
				Otdel_OK = true;
			}

			if (!Otdel_OK) {
				if (!textCheck.isEmpty()) {
					textCheck += ", ";
				}
				textCheck += svePersonManegement_newOtdel;
			}
			boolean fl = true;
			if (!KodKZ_1_OK || !KodKZ_2_OK || !KZ_HOG_OK || !KZ_Terit_1_OK || !KZ_Terit_2_OK) {
				if (!textCheck.isEmpty()) {
					textCheck += ", ";
				}
				textCheck += svePersonManegement_newKode;

				if (!textCheck.isEmpty()) {
					textCheck += " " + svePersonManegement_IsEnteredInZone;
					
					fl= false;
				}
			}
			
			setVizible_EnterInZone_LabelAndCheckBox(fl);
			return textCheck;
		}
		return "";
	}

	public static String checkDublicateKodeInNewPerson(JTextField textField, int zoneID) {
		String svePersonManegement_KodeIsBusi = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("svePersonManegement_KodeIsBusi");
		JTextField textField_svePerson_EGN = PersonelManegementFrame.getTextField_svePerson_EGN();

		JButton btn_SaveToExcelFile = PersonelManegementFrame.getBtn_SaveToExcelFile();
		String textCheck = "";
		String egn = textField_svePerson_EGN.getText();
		String kode = textField.getText();
		if (!egn.isEmpty() && zoneID > 0 && !kode.isEmpty()) {

			List<KodeStatus> listKStat = kodeStatusFromExcelFiles.get(zoneID - 1);
			for (KodeStatus kodeStatist : listKStat) {

				if (!egn.equals(kodeStatist.getPerson().getEgn()) && kode.equals(kodeStatist.getKode())) {
					textField.setForeground(Color.BLACK);
					textField.setBackground(Color.RED);
					btn_SaveToExcelFile.setEnabled(false);
					return svePersonManegement_KodeIsBusi;

				} else {
					textField.setBackground(Color.WHITE);
					btn_SaveToExcelFile.setEnabled(true);
					textCheck = "";
				}

			}

		}
		return textCheck;
	}

	private static List<List<KodeStatus>> getMasiveKodeStatusFromDBaseForCurentYear(String curentYear) {
		List<List<KodeStatus>> listMasive = new ArrayList<List<KodeStatus>>();
		for (int i = 0; i < 5; i++) {
			List<KodeStatus> listKStat = KodeStatusDAO.getKodeStatusByYearZone(curentYear, (i + 1));
			listMasive.add(listKStat);
		}

		return listMasive;
	}

	public static String checkKorectKodeInNewPerson(JTextField textField, int zoneID) {
		String textCheck = "";
		String kode = textField.getText();
		int sizeKode = kode.length();
		String str, strNew;
		boolean fl = true;
		if (sizeKode > 1) {
			if (zoneID == 1) {
				str = kode.substring(sizeKode - 1, sizeKode);
				strNew = SearchFreeKodeFrame.convertToUpperCyrChart(str);
				
				if (!strNew.isEmpty()) {
					str = kode.replace(str, strNew);
					textField.setText(str);
				} else {
					fl = false;
				}

				try {
					str = kode.substring(0, sizeKode - 1);
					Integer.parseInt(str);
				} catch (Exception e) {
					fl = false;
				}
			}

			if (zoneID == 2) {
				str = kode.substring(0, 1);
				strNew = SearchFreeKodeFrame.convertToUpperCyrChart(str);
				if (!strNew.isEmpty()) {
					str = kode.replace(str, strNew);
					textField.setText(str);
				} else {
					fl = false;
				}

				try {
					str = kode.substring(1, sizeKode);
					Integer.parseInt(str);
				} catch (Exception e) {
					fl = false;
				}
			}

			if (zoneID == 3) {
				str = kode.substring(0, 1);
				strNew = SearchFreeKodeFrame.convertToUpperCyrChart(str);
				if (!strNew.isEmpty() && strNew.equals("Н")) {
					str = kode.replace(str, strNew);
					textField.setText(str);
				} else {
					fl = false;
				}

				str = kode.substring(sizeKode - 1, sizeKode);
				strNew = SearchFreeKodeFrame.convertToUpperCyrChart(str);
				if (!strNew.isEmpty()) {
					str = kode.replace(str, strNew);
					textField.setText(str);
				} else {
					fl = false;
				}

				try {
					str = kode.substring(1, sizeKode - 1);
					Integer.parseInt(str);
				} catch (Exception e) {
					fl = false;
				}
			}
			if (fl) {
				textField.setBorder(defoutBorder);
			} else {
				textField.setBorder(redBorder);
			}
		}
		return textCheck;
	}

	 public static void sortByStartDate(List<PersonStatus> perStat){	    		    	

  		Collections.sort(perStat, new Comparator<PersonStatus>() {
  		 
		@Override
		public int compare(PersonStatus o1, PersonStatus o2) {
			 return o2.getSpisak_prilogenia().getStartDate().compareTo(o1.getSpisak_prilogenia().getStartDate());
		}
  		});
  	
	    }
	
	 public static void sortByStartDateNew(List<PersonStatusNew> perStat){	    		    	

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
			kode =  AplicationMetods.literate(kode);
			kode = kode.toUpperCase();;
			textField.setText(kode);
		}
	
	}

	public static String checkKorectWritenKode() {
		String otdelPerson = PersonelManegementFrame.getComboBox_savePerson_Otdel().getSelectedItem();
		Workplace workPl = WorkplaceDAO.getActualValueWorkplaceByOtdel(otdelPerson);
		String textCheck = "";
		String textToolTipText = "";
		
		JTextField[] textField =  {PersonelManegementFrame.getTextField_svePerson_KodKZ_1(),
				PersonelManegementFrame.getTextField_svePersonKodKZ_2()};
		
		if (workPl != null ) {
			
			for (int zoneID = 1; zoneID <= 2; zoneID++) {
				
				
			KodeGenerate kodeGen = KodeGenerateDAO.getValueKodeGenerateByWorkplaceAndZone(workPl.getId_Workplace(),
					zoneID);
			if ( kodeGen != null) {
			String leterL = kodeGen.getLetter_L();
			String leterR = kodeGen.getLetter_R();
			int startCount = kodeGen.getStartCount();
			int endCount = kodeGen.getEndCount();

			String kode = textField[zoneID-1].getText();
			int sizeKode = kode.length();
			String leter = null;
			boolean fl = true;
			if (sizeKode > 0 ) {
				if (zoneID == 1) {
					if (!kode.isEmpty() && !kode.equals("ЕП-2") && !kode.equals("н")) {
						leter = kode.substring(sizeKode - 1, sizeKode);
						String strNum = kode.substring(0, sizeKode - 1);
						textToolTipText = svePersonManegement_textOtdelArea+" "+startCount+"÷"+endCount+"|"+leterR;
						fl = checkLeterAndAreaNumber(leterR, startCount, endCount, leter, strNum);
					}
				}

				if (zoneID == 2) {
					if (!kode.isEmpty() && !kode.equals("н")) {
						leter = kode.substring(0, 1);
						String strNum = kode.substring(1, sizeKode);
						textToolTipText = svePersonManegement_textOtdelArea+" "+leterL+"|"+startCount+"÷"+endCount;
						fl = checkLeterAndAreaNumber(leterL, startCount, endCount, leter, strNum);
												
					}
				}
			}
			if (fl) {
				textField[zoneID-1].setBorder(defoutBorder);
				
				textField[zoneID-1].setToolTipText(null);
			} else {
				textCheck = svePersonManegement_kodeNotInOtdelArea;
				textField[zoneID-1].setToolTipText(textToolTipText);
				textField[zoneID-1].setBorder(redBorder);
			}
			
			setVizible_EnterInListChengeKode_LabelAndCheckBox(fl);
			
		}
			}
		}
		return textCheck;
	}

	private static boolean checkLeterAndAreaNumber(String leterR, int startCount, int endCount, String leter,
			String strNum) {
		
		boolean fl;
		try {
			int numKode = Integer.parseInt(strNum);
		
			if (leter.equals(leterR) && numKode >= startCount && numKode <= endCount) {
				fl = true;
			}else {
				fl = false;
			}

		} catch (Exception e) {
			fl = false;
		}
		return fl;
	}

	private static void setVizible_EnterInListChengeKode_LabelAndCheckBox(boolean fl) {
		
		if (fl && !PersonelManegementFrame.getChckbx_svePerson_EnterInZone().isSelected()) {
			PersonelManegementFrame.getLbl_svePerson_EnterInListChengeKode().setText("");
			PersonelManegementFrame.getChckbx_svePerson_EnterInListChengeKode().setVisible(false);
			PersonelManegementFrame.getChckbx_svePerson_EnterInListChengeKode().setSelected(false);
			PersonelManegementFrame.getChckbx_svePerson_EnterInListChengeKode().setText("no");
		} else {
			PersonelManegementFrame.getLbl_svePerson_EnterInListChengeKode().setText(svePersonManegement_InsertToListChengeKode);
			PersonelManegementFrame.getChckbx_svePerson_EnterInListChengeKode().setVisible(true);
		}
	}

	
	private static void setVizible_EnterInZone_LabelAndCheckBox(boolean fl) {
		if (fl) {
			System.out.println("true");
			PersonelManegementFrame.getLbl_svePerson_isEnterInZone().setText("");
			PersonelManegementFrame.getChckbx_svePerson_EnterInZone().setVisible(false);
			PersonelManegementFrame.getChckbx_svePerson_EnterInZone().setSelected(false);
			PersonelManegementFrame.getChckbx_svePerson_EnterInZone().setText("no");
		} else {
			System.out.println("false"+svePersonManegement_CheckBoxLbIs_EnteredInZone);
			PersonelManegementFrame.getLbl_svePerson_isEnterInZone().setText(svePersonManegement_CheckBoxLbIs_EnteredInZone);
			PersonelManegementFrame.getChckbx_svePerson_EnterInZone().setVisible(true);
		}
	}

	
	public static void checkKorectFormatKode(int zoneID) {
		boolean fl = true, fl_all = true;
		if(zoneID > 0) {
		JTextField[] textField = {
				PersonelManegementFrame.getTextField_svePerson_KodKZ_1(),
				PersonelManegementFrame.getTextField_svePersonKodKZ_2(),
				PersonelManegementFrame.getTextField_svePersonKodKZ_HOG(),
				PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_1(),
				PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_2()};
		
		JButton btn_SaveToExcelFile = PersonelManegementFrame.getBtn_SaveToExcelFile();
		String textCheck = "";
		
		
		String leter="";
		
		for (int i = 0; i < textField.length; i++) {
			String kode = textField[i].getText();
			int sizeKode = kode.length();
			fl = true;
		if (sizeKode > 0 ) {

			if ((i+1) == 1) {
				if (!kode.isEmpty() && !kode.equals("ЕП-2") && !kode.equals("н")) {
					leter = kode.substring(sizeKode - 1, sizeKode);
				try {
						if (!SearchFreeKodeFrame.checkIsCyrChart(leter)) {
							fl = false;
						}
						Integer.parseInt(kode.substring(0, sizeKode - 1));
					} catch (Exception e) {
						fl = false;
					}

					if (!fl) {
						textCheck = svePersonManegement_NonKorektFormatKode + " - 123A";
					}
				}
			}

			if ((i+1) == 2) {
				if (!kode.isEmpty() && !kode.equals("н")) {
					leter = kode.substring(0, 1);
					try {
						if (!SearchFreeKodeFrame.checkIsCyrChart(leter)) {
							fl = false;
						}
						Integer.parseInt(kode.substring(1, sizeKode));
					} catch (Exception e) {
						fl = false;
					}
					if (!fl) {
						textCheck = svePersonManegement_NonKorektFormatKode + " - A123";
					}
				}
			}

			if ((i+1) == 3) {
				if (!kode.isEmpty() && !kode.equals("н")) {
					String textKodKZ_1 = PersonelManegementFrame.getTextField_svePerson_KodKZ_1().getText();
					if (textKodKZ_1.isEmpty() || !("Н" + textKodKZ_1).equals(kode)) {
						textCheck = svePersonManegement_NonKorektFormatKode + " - H" + textKodKZ_1;
						fl = false;
					}

				}
			}

			if ((i+1) == 4) {
				if (!kode.isEmpty() && !kode.equals("н")) {
					String textKodKZ_1 = PersonelManegementFrame.getTextField_svePerson_KodKZ_1().getText();
					if (textKodKZ_1.isEmpty() || !("Т" + textKodKZ_1).equals(kode)) {
						textCheck = svePersonManegement_NonKorektFormatKode + " - T" + textKodKZ_1;
						fl = false;
					}
				}
			}

			if ((i+1) == 5) {
				if (!kode.isEmpty() && !kode.equals("н")) {
					String textKodKZ_2 = PersonelManegementFrame.getTextField_svePersonKodKZ_2().getText();
					if (textKodKZ_2.isEmpty() || !(textKodKZ_2 + "Т").equals(kode)) {
						textCheck = svePersonManegement_NonKorektFormatKode + " - " + textKodKZ_2 + "T";
						fl = false;
					}
				}
			}
		}
		if (fl) {
			textField[i].setForeground(Color.BLACK);
			textField[i].setBackground(Color.WHITE);
			textField[i].setBorder(defoutBorder);
			btn_SaveToExcelFile.setEnabled(true);
			textField[i].setToolTipText(null);
			
		} else {
			textField[i].setForeground(Color.RED);
			textField[i].setBackground(Color.WHITE);
			textField[i].setBorder(defoutBorder);
			btn_SaveToExcelFile.setEnabled(false);
			textField[i].setToolTipText(textCheck);
			
		}
		if(!fl) {
			fl_all=fl;
		}
		}
		corectFormatKode = fl_all;
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

	public static List<PersonManegement> getListPersonFromFile(JTextArea textArea, JPanel infoPanel, JPanel tablePane,
			JPanel panel_AllSaerch, JScrollPane scrollPane, JTextField textField_svePerson_Year, JTextField textField, JButton btnBackToTable) {
		File file;
		String filePath = textField.getText();
		if(filePath.isEmpty()) {
		JFileChooser chooiser = new JFileChooser(ExcelFileBAK_Path);
		chooiser.setMultiSelectionEnabled(false);
		chooiser.showOpenDialog(null);
		file = chooiser.getSelectedFile();
		}else {
			file = new File( filePath);
		}
		
//		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		List<PersonManegement> listPersonFromFile = readListPersonFromFile(file, textField);

		
		
		if (listPersonFromFile.size() == 0) {
			textArea.setText(notResults);
			PersonelManegementFrame.viewInfoPanel();
			if (dataTable != null) {
				btnBackToTable.setEnabled(true);
			}

		}

		if (listPersonFromFile.size() == 1) {
			textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson(textField_svePerson_Year.getText(),
					listPersonFromFile.get(0).getPerson(), false, 0));
			PersonelManegementFrame.viewInfoPanel();
			if (dataTable != null) {
				btnBackToTable.setEnabled(true);
			}
		}

		if (listPersonFromFile.size() > 1) {
			fromListPersonManegement = true;
			System.out.println("***** " + listPersonFromFile.size());
			updateSectionPersonSave_Panel(listPersonFromFile);
			dataTable = TableManagement.addListStringSelectionPersonToComboBox(listPersonFromFile);
			panel_infoPanelTablePanel(dataTable, textArea, panel_AllSaerch, tablePane, scrollPane,
					textField_svePerson_Year, btnBackToTable);
			PersonelManegementFrame.viewTablePanel();
			btnBackToTable.setEnabled(false);
			
		}

		return listPersonFromFile;
	}

	private static void updateSectionPersonSave_Panel(List<PersonManegement> listPersonFromFile) {
		String otdel = PersonReferenceFrame.getLastWorkplaceByPerson(listPersonFromFile.get(0).getPerson());
		String firm = WorkplaceDAO.getActualValueWorkplaceByOtdel(otdel).getFirmName();
		
		PersonelManegementFrame.getTextField_svePerson_EGN().setText("");
		PersonelManegementFrame.getTextField_svePerson_FName().setText("");
		PersonelManegementFrame.getTextField_svePerson_SName().setText("");
		PersonelManegementFrame.getTextField_svePerson_LName().setText("");
		PersonelManegementFrame.getTextField_svePerson_KodKZ_1().setText("");
		PersonelManegementFrame.getTextField_svePersonKodKZ_2().setText("");
		PersonelManegementFrame.getTextField_svePersonKodKZ_HOG().setText("");
		PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_1().setText("");
		PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_2().setText("");
		
		PersonelManegementFrame.getComboBox_savePerson_Firm().select(firm);
		PersonelManegementFrame.getComboBox_savePerson_Otdel().select(otdel);
		
		
	}
	
	private static List<PersonManegement> readListPersonFromFile(File file, JTextField textField) {
		ExcelFileBAK_Path = file.getPath();
		textField.setText(ExcelFileBAK_Path);
		Workbook workbook = ReadExcelFileWBC.openExcelFile(file.getPath());
		String name = "", kod = "", FirstName = "", SecondName = "", LastName = "";
		Sheet sheet = workbook.getSheetAt(0);
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
					if (listPerson.size() > 0) {
						person = listPerson.get(0);
					} else {
						listPerson = PersonDAO.getValuePersonByObject("FirstName", FirstName);
					}
					if (listPerson.size() > 1) {
						person = choisePerson(listPerson, name);
					}
					if (person == null) {
						listPerson = PersonDAO.getValuePersonByObject("SecondName", SecondName);
						if (listPerson.size() > 0) {
						person = choisePerson(listPerson, name);
						}
					}
					if (person == null) {
						listPerson = PersonDAO.getValuePersonByObject("LastName", LastName);
						if (listPerson.size() > 0) {
						person = choisePerson(listPerson, name);
						}
					}
					if (person != null) {
						listPersonManegement.add(new PersonManegement(person, kod));
					}
				}
			}

		}
		return listPersonManegement;

	}

	private static Person choisePerson(List<Person> listPerson, String choicePerson) {

		String[] listChoisePerson = generateListChoisePerson(listPerson);
		 
		
		new choiseDialogFrame(new JFrame(), listChoisePerson, choicePerson);
		 
		String str = choisePerson;

		if (str != null && str.length() > 0) {
			String[] masiveStr = str.split(" ");
			return PersonDAO.getValuePersonByEGN(masiveStr[0]);
		}
		return null;
	}
	
	private static String[] generateListChoisePerson(List<Person> listPerson) {

		
			int maxEGN = 10;
			int maxPerson = 10;
					
		String[] listStringPerson = new String[listPerson.size() ];
		String[] listStringWorkplace = new String[listPerson.size()];
		String[] listStringEGN = new String[listPerson.size() ];
		int i = 0;
		for (Person person : listPerson) {
			listStringEGN[i] ="";
			listStringPerson[i] ="";
			
			String PerStatNewSet = ReadFileBGTextVariable.getGlobalTextVariableMap().get("PerStatNewSet");
			if(PerStatNewSet.equals("1")) {
				PersonStatusNew personStat = PersonStatusNewDAO.getLastValuePersonStatusNewByPerson(person);
				if (personStat != null) {
					listStringWorkplace[i] = personStat.getWorkplace().getOtdel();
					
				}	
			}else {
			PersonStatus personStat = PersonStatusDAO.getLastValuePersonStatusByPerson(person);
			if (personStat != null) {
				listStringWorkplace[i] = personStat.getWorkplace().getOtdel();
				
			}
			}
			listStringEGN[i] = person.getEgn();
			listStringPerson[i] = person.getFirstName() + " " + person.getSecondName() + " "+ person.getLastName();
			
			if(maxPerson < listStringPerson[i].length()) {
				maxPerson = listStringPerson[i].length();
			}
			
			i++;
		}
		
		for (int j = 0; j < listStringEGN.length; j++) {
			System.out.println(j+" - "+listStringEGN[j]);
			listStringPerson[j] = listStringEGN[j]+ TextInAreaTextPanel.getAddSpace(maxEGN, listStringEGN[j]) +
					listStringPerson[j]  + TextInAreaTextPanel.getAddSpace(maxPerson+2, listStringPerson[j]) + listStringWorkplace[j];
			
		}
	
		return listStringPerson;
	}

	private static void panel_infoPanelTablePanel(Object[][] dataTable, JTextArea textArea, JPanel panel_AllSaerch,
			JPanel tablePane, JScrollPane scrollPane, JTextField textField_svePerson_Year, JButton btnBackToTable) {
		
		final JScrollPane llPane = scrollPane;
		
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
				if(Index_col == newZoneKode_Colum || Index_col == zoneKode_Colum) {
				checkAndChangeColorCels(zoneKode_Colum, newZoneKode_Colum, model, Index_row, comp);
				}
				
								
				return comp;
			}

			
		};
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
							
				if (table.getSelectedColumn() == egn_code_Colum) {
					table.rowAtPoint(e.getPoint());
					table.columnAtPoint(e.getPoint());
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();

					System.out.println(reqCodeStr);

				}

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
					System.out.println(reqCodeStr);
					Person person = PersonDAO.getValuePersonByEGN(reqCodeStr);
					textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson(textField_svePerson_Year.getText(),
							person, false, 0));
					PersonelManegementFrame.viewInfoPanel();
					if (dataTable != null) {
						btnBackToTable.setEnabled(true);
					}
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
							textField_svePerson_Year, btnBackToTable);
					PersonelManegementFrame.viewTablePanel();
					btnBackToTable.setEnabled(false);
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

	}

	private static void checkAndChangeColorCels(int zoneKode_Colum, int newZoneKode_Colum, DefaultTableModel model,
			int Index_row, Component comp) {
		if (!model.getValueAt(Index_row, zoneKode_Colum).toString().
				equals(model.getValueAt(Index_row, newZoneKode_Colum).toString())){
			comp.setForeground(Color.RED);
		}else {
			comp.setForeground(Color.black);
		}
	}
	
	private static int getSelectedModelRow(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}

	static String[] getTabHeader() {
		String[] tableHeader = { " № ", "ЕГН", "Име", "Презиме", "Фамилия", "Отдел",
				"Дата на изм.", "ИД Код", "нов ИД Код", "selekt" };
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
	    if(file.renameTo(sameFileName)){
	       System.out.println("file is closed"); 
	       return false;
	    }else{
	       System.out.println("file is opened");
	        return true;
	    }
	}
	
	public static boolean checkIsClosedPersonAndExternalFile() {
		
		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");
		
		
		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if(testFilesToD.equals("1")) {
		filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
		filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}
			
		String filePath[] = {filePathPersonel, filePathExternal};
		
		String fileIsOpen = ReadFileBGTextVariable.getGlobalTextVariableMap().get("fileIsOpen"); 
	String dialogString = "<html>";
		for (int i = 0; i < filePath.length; i++) {
		File file = new File(filePath[i]);
	    File sameFileName = new File(filePath[i]);
	    if(!file.renameTo(sameFileName)){
	    	 System.out.println("file is opened");
	    	dialogString += sameFileName.getName()+" "+fileIsOpen+"<br>";
	        
	    }
		}
		System.out.println(dialogString+"  "+dialogString.length());
		if(dialogString.length() > 7) {
			return OptionDialog(dialogString+"</html>",  "В Н И М А Н И Е");
		}
		return true;
	}
	
	public static boolean checkIsClosedMonthANDPersonAndExternalFile(ActionIcone round) {
		
		    	 if(PersonelManegementMethods.checkIsClosedPersonAndExternalFile() 
		 				&& PersonelManegementMethods.checkIsClosedMonthPersonAndExternalFile()) {
		    	 round.StopWindow();
		    	 return true;
		 		}
		    	round.StopWindow();
		    	return false;
	
	}
	
	public static boolean checkIsClosedMonthPersonAndExternalFile() {
		
		String filePathMonthExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthExternal_orig");
		String filePathMonthPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthPersonel_orig");
		
		
		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if(testFilesToD.equals("1")) {
		filePathMonthExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthExternal_orig_test");
		filePathMonthPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathMonthPersonel_orig_test");
		}
			
		String filePath[] = {filePathMonthPersonel, filePathMonthExternal};
		
		String fileIsOpen = ReadFileBGTextVariable.getGlobalTextVariableMap().get("fileIsOpen"); 
	String dialogString = "<html>";
		for (int i = 0; i < filePath.length; i++) {
		File file = new File(filePath[i]);
	    File sameFileName = new File(filePath[i]);
	    if(!file.renameTo(sameFileName)){
	    	 System.out.println("file is opened");
	    	dialogString += sameFileName.getName()+" "+fileIsOpen+"<br>";
	        
	    }
		}
		System.out.println(dialogString+"  "+dialogString.length());
		if(dialogString.length() > 7) {
			return OptionDialog(dialogString+"</html>",  "В Н И М А Н И Е");
		}
		return true;
	}
	
	public static List<OpenedExcelClass> openClosedPersonAndExternalFile() {
		List<OpenedExcelClass> list = new ArrayList<>();
		
		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");
				
		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if(testFilesToD.equals("1")) {
		filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
		filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}
			
		String filePath[] = {filePathPersonel, filePathExternal};
		
		for (int i = 0; i < filePath.length; i++) {
		 File file = new File(filePath[i]);
		    File sameFileName = new File(filePath[i]);
		    if(file.renameTo(sameFileName)){
		       System.out.println("file is closed"); 
		       if(openJavaExcelFile(filePath[i]) != null) {
		      list.add( openJavaExcelFile(filePath[i]));
		       }
		    }
		}
		return list;
	}
	
	
	public static void ClosedPersonAndExternalFile(Workbook[] workbook) {
	
		String filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig");
		String filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig");
				
		String testFilesToD = ReadFileBGTextVariable.getGlobalTextVariableMap().get("testFilesToD");
		if(testFilesToD.equals("1")) {
		filePathExternal = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathExternal_orig_test");
		filePathPersonel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("filePathPersonel_orig_test");
		}
			
		String filePath[] = {filePathPersonel, filePathExternal};
		
		for (int i = 0; i < filePath.length; i++) {
			FileOutputStream outputStream;
			try {
				outputStream = new FileOutputStream(
				        "C://Users//AnubhavPatel//Desktop/Testing_Code_Macro.xlsm");
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
		if(destFilePath == null) {
		destFilePath = ReadFileBGTextVariable.getGlobalTextVariableMap().get("destinationDir");
		}
		SimpleDateFormat sdfrmt = new SimpleDateFormat("ddMMyy");
		Date date = new Date();
		String strDate = sdfrmt.format(date);

		int slashIndex = sourceFilePath.lastIndexOf("\\")+1;
		int dotIndex = sourceFilePath.indexOf(".");
		String destFileName = sourceFilePath.substring(slashIndex, dotIndex)+"-"+strDate+sourceFilePath.substring(dotIndex);
		System.out.println(destFilePath+destFileName+"---------------------------------");
		File fileSorce = new File(sourceFilePath);
		File fileDest = new File(destFilePath+destFileName);
		
		try {
			FileUtils.copyFile(fileSorce, fileDest);
		} catch (IOException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		  
	}
	
	public static void openMinimizedExcelFile(String excelFilePath) {
		try {
		 Runtime.getRuntime().exec(new String[]{"cmd","/c", "start", "/min","EXCEL.EXE", excelFilePath});
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
		 Runtime.getRuntime().exec(new String[]{"cmd","/c", "taskkill", "/f","/im", "EXCEL.EXE"});
		} catch (IOException ioe) {
			ResourceLoader.appendToFile(ioe);
			ioe.printStackTrace();
		}

	}

	


}