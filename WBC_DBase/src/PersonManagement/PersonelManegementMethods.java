package PersonManagement;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.RemouveDublikateFromList;
import Aplication.ReportMeasurClass;
import AutoInsertMeasuting.InsertMeasurToExcel;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.WorkplaceDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.Zone;
import PersonReference.PersonReferenceExportToExcell;
import PersonReference.PersonReferenceFrame;
import PersonReference.TextInAreaTextPanel;
import SaveToExcellFile.SaveToPersonelORExternalFile;
import SearchFreeKode.SearchFreeKodeFrame;
import SearchFreeKode.SearchFreeKodeMethods;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;

public class PersonelManegementMethods {

	static String notResults = ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults");
	static boolean multytextInTextArea;
	static String AEC = ReadFileBGTextVariable.getGlobalTextVariableMap().get("AEC");
	static String VO = ReadFileBGTextVariable.getGlobalTextVariableMap().get("VO");
	static List<String> listFirm = Arrays.asList("", AEC, VO);
	static Person selectionPerson;
	static boolean flOtdel;
	static Border defoutBorder = PersonelManegementFrame.getDefoutBorder();
	static Border redBorder = new SoftBevelBorder(BevelBorder.LOWERED, Color.RED, null, Color.RED, null);
	static String oldOtdelPerson;
	static List<String> listOtdelAll;
	static List<String> listOtdelKz;
	static List<String> listOtdelVO;
	static Object[][] dataTable;
		
	static List<String> listZvenaFromDBase = SearchFreeKodeMethods.generateListZvenaFromDBase();
	static List<List<String>> ListZvenaFromExcellFiles = SearchFreeKodeMethods.generateListZvenaFromExcellFiles();
	

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
							EGN = ReadExcelFileWBC.getStringfromCell(cell);
							if (EGN.contains("*"))
								EGN = EGN.substring(0, EGN.length() - 1);

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

	static void ActionListener_Btn_savePerson_Insert(JButton btn_savePerson_Insert, JPanel panel_AllSaerch,
			JTextArea textArea) {
		btn_savePerson_Insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				GeneralMethods.setWaitCursor(panel_AllSaerch);

				generateInfoByOnePerson(selectionPerson, textArea);

				List<Spisak_Prilogenia> list = getListSpisak_Prilogenia_FromExcelFile(oldOtdelPerson);
				PersonelManegementFrame.setListSpisak_Prilogenia(list);

				GeneralMethods.setDefaultCursor(panel_AllSaerch);
			}
		});
	}

	static void ActionListener_Btn_SearchPerson(JButton btn_SearchPerson, JPanel panel_AllSaerch, JTextArea textArea, JButton btn_savePerson_Insert) {

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
						textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson("", selectionPerson, false));

						textField_EGN.setText(selectionPerson.getEgn());
						textField_FName.setText(selectionPerson.getFirstName());
						textField_SName.setText(selectionPerson.getSecondName());
						textField_LName.setText(selectionPerson.getLastName());
					}

					if (listSelectionPerson.size() > 1) {
						multytextInTextArea = true;
						textArea.setText(addListStringSelectionPersonToTextArea(listSelectionPerson));
					}

					GeneralMethods.setDefaultCursor(panel_AllSaerch);
				}
			}

		});

	}

	public static void ActionListener_ComboBox_Firm(Choice comboBox_Firm, Choice comboBox_Otdel) {

		comboBox_Firm.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
					setitemInChoise(comboBox_Firm, comboBox_Otdel);
			}
		});

	

	}

	static void ActionListener_ComboBox_savePerson_Otdel(Choice comboBox_savePerson_Otdel) {
		comboBox_savePerson_Otdel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				PersonelManegementFrame.setListSpisak_Prilogenia(
						PersonelManegementMethods.generateListSpisPril(comboBox_savePerson_Otdel));
				JTextField fild = PersonelManegementFrame.getTextField_svePerson_KodKZ_1();
				checkKorectionSetInfoToFieldsInSavePersonPanel(fild, 1);

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
						textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson("", selectionPerson, false));

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

	static void ActionListener_Btn_Spisak(JButton btn_Spisak) {
		btn_Spisak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				List<Spisak_Prilogenia> listSpisak_Prilogenia = PersonelManegementFrame.getListSpisak_Prilogenia();
				
				listSpisak_Prilogenia = addObhodenListToListSpisak_Prilogenia(listSpisak_Prilogenia);
				
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

			private List<Spisak_Prilogenia> addObhodenListToListSpisak_Prilogenia(
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

	static void ActionListener_Btn_InsertTo(JButton btn_Insert, int zoneID) {
		btn_Insert.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String text="";
			switch (zoneID) {
			case 3: {
				text = PersonelManegementFrame.getTextField_svePerson_KodKZ_1().getText();
				PersonelManegementFrame.getTextField_svePersonKodKZ_HOG().setText("Н"+text);
			}
				break;
			case 4: {
				text = PersonelManegementFrame.getTextField_svePerson_KodKZ_1().getText();
				PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_1().setText("Т"+text);
			}
				break;
			case 5: {
				text = PersonelManegementFrame.getTextField_svePersonKodKZ_2().getText();
				PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_2().setText(text+"Т");;
			}
				break;

			}
		}

	});



}
	
	
	public static void ActionListener_Btn_SaveToExcelFile(JButton btn_SaveToExcelFile) {
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
		JTextField textField_svePersonKod_KZ_Terit_1 = PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_1();
		JTextField textField_svePersonKod_KZ_Terit_2 = PersonelManegementFrame.getTextField_svePersonKodKZ_Terit_2();
		Choice comboBox_savePerson_Firm = PersonelManegementFrame.getComboBox_savePerson_Firm();
		Choice comboBox_svePerson_Otdel = PersonelManegementFrame.getComboBox_savePerson_Otdel();
		JTextField textField_svePerson_Spisak = PersonelManegementFrame.getTextField_svePerson_Spisak();
		JTextField textField_savePerson_StartDate = PersonelManegementFrame.getTextField_savePerson_StartDate();
		JTextField textField_savePerson_EndDate = PersonelManegementFrame.getTextField_savePerson_EndDate();
		JTextField textField_svePerson_Coment = PersonelManegementFrame.getTextField_svePerson_Coment();
		JTextField textField__svePerson_Year = PersonelManegementFrame.getTextField_svePerson_Year();
		
		String egn = textField_svePerson_EGN.getText();
		Person person = PersonDAO.getValuePersonByEGN(egn);
		String comment = textField_svePerson_Coment.getText().trim();	
		if(person==null) {
			PersonDAO.setValuePerson(egn, textField_svePerson_FName.getText(), textField_svePerson_SName.getText(), textField_svePerson_LName.getText());
		}
		person = PersonDAO.getValuePersonByEGN(egn);
		String[] infoForPerson = {textField_svePerson_KodKZ_1.getText().trim(), textField_svePerson_KodKZ_2.getText().trim(), textField_svePersonKod_KZ_HOG.getText().trim(), textField_svePersonKod_KZ_Terit_1.getText().trim(),
				textField_svePersonKod_KZ_Terit_2.getText().trim(), textField_svePerson_FName.getText().trim(), textField_svePerson_SName.getText().trim(), textField_svePerson_LName.getText().trim()};
	
		
		if(checkInfoPerson(infoForPerson, person)) {
			
		
		
		Workplace workplace = WorkplaceDAO.getValueWorkplaceByObject("Otdel", comboBox_svePerson_Otdel.getSelectedItem()).get(0);
	
		
		String year = textField__svePerson_Year.getText().trim();
		try {
			Integer.parseInt(year);
		} catch (NumberFormatException e7) {
			year = curentYear;
			textField__svePerson_Year.setText(year);
		}
			
		
		Spisak_Prilogenia spisPril = null;
		String formuliarName = textField_svePerson_Spisak.getText().trim();
		if(!formuliarName.isEmpty()) {
		
		Date sDate = null, eDate = null;
		try {
			sDate = sdf.parse(textField_savePerson_StartDate.getText().trim());
			eDate = sdf.parse(textField_savePerson_EndDate.getText().trim());
		} catch (ParseException e1) {
			
			e1.printStackTrace();
		}
		
		
		Spisak_PrilogeniaDAO.setValueSpisak_Prilogenia(formuliarName, year, 
				sDate, eDate, workplace, comment);
		spisPril = Spisak_PrilogeniaDAO.getLastSaveObjectFromValueSpisak_PrilogeniaByYear_Workplace_StartDate(year, sDate, workplace.getId_Workplace());
		
		Date curentDate = Calendar.getInstance().getTime();
		
		PersonStatusDAO.setValuePersonStatus(person, workplace, spisPril, user, curentDate,"");
		
		}
		
		String kodeByFrame = infoForPerson[0];
		KodeStatus kodeStst = KodeStatusDAO.getKodeStatusByPersonZoneYear( person, 1,  year);
		KodeStatus newKodeStst = new KodeStatus (person, kodeByFrame, ZoneDAO.getValueZoneByID(1), true, year, "");
		setKodeToDBase(kodeByFrame, kodeStst, newKodeStst);
		
		
		kodeByFrame = infoForPerson[1];
		kodeStst = KodeStatusDAO.getKodeStatusByPersonZoneYear( person, 2,  year);
		newKodeStst = new KodeStatus (person, kodeByFrame, ZoneDAO.getValueZoneByID(2), true, year, "");
		setKodeToDBase(kodeByFrame, kodeStst, newKodeStst);
		
		kodeByFrame = infoForPerson[2];
		kodeStst = KodeStatusDAO.getKodeStatusByPersonZoneYear( person, 3,  year);
		newKodeStst = new KodeStatus (person, kodeByFrame, ZoneDAO.getValueZoneByID(3), true, year, "");
		setKodeToDBase(kodeByFrame, kodeStst, newKodeStst);
		
		kodeByFrame = infoForPerson[3];
		kodeStst = KodeStatusDAO.getKodeStatusByPersonZoneYear( person, 4,  year);
		newKodeStst = new KodeStatus (person, kodeByFrame, ZoneDAO.getValueZoneByID(4), true, year, "");
		setKodeToDBase(kodeByFrame, kodeStst, newKodeStst);
		
		kodeByFrame = infoForPerson[4];
		kodeStst = KodeStatusDAO.getKodeStatusByPersonZoneYear( person, 5,  year);
		newKodeStst = new KodeStatus (person, kodeByFrame, ZoneDAO.getValueZoneByID(5), true, year, "");
		setKodeToDBase(kodeByFrame, kodeStst, newKodeStst);
		
		
		
		SaveToPersonelORExternalFile.saveInfoPersonToExcelFile(person, comboBox_savePerson_Firm.getSelectedItem(), spisPril, user, comment, workplace);
			
		}
		}

			private void setKodeToDBase(String kodeByFrame, KodeStatus kodeStst, KodeStatus newKodeStst) {
				String kodByDBase;
				if(kodeByFrame.equals("н")||kodeByFrame.equals("ЕП-2")) {
					kodeByFrame = "";
				}
				kodByDBase = "";
				if(kodeStst != null)	{
					kodByDBase = kodeStst.getKode();
				}
				if(!kodByDBase.equals(kodeByFrame))	{
					if(kodeByFrame.isEmpty()) {
						KodeStatusDAO.deleteValueKodeStatus(kodeStst.getKodeStatus_ID());	
					}else {
					if(kodeStst != null)	{	
					kodeStst.setKode(kodeByFrame);
					KodeStatusDAO.updateValueKodeStatus(kodeStst, kodeStst.getKodeStatus_ID());
					}else {
						KodeStatusDAO.setObjectKodeStatusToTable(newKodeStst);
					}
				}
				}
			}

		});	
			}

	
	
	static void ActionListener_textField_svePerson_Year(JTextField textField_svePerson_Year, JButton btn_SaveToExcelFile) {
		textField_svePerson_Year.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {
				textField_svePerson_Year.setForeground(Color.BLACK);
				btn_SaveToExcelFile.setEnabled(true);
				if (!textField_svePerson_Year.getText().isEmpty()) {
					try {
						long number = Long.parseLong(textField_svePerson_Year.getText());
						if ( number >= Calendar.getInstance().get(Calendar.YEAR)) {
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

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

	}
	
	static void ActionListener_Btn_ReadFileListPerson(JButton btn_ReadFileListPerson, JTextArea textArea, 
			JPanel infoPanel,JPanel tablePane, JPanel panel_AllSaerch, JScrollPane scrollPane, JTextField textField_svePerson_Year,
			JTextField textField) {
		btn_ReadFileListPerson.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			getListPersonFromFile(textArea, infoPanel,  tablePane, panel_AllSaerch, scrollPane, textField_svePerson_Year, textField);
		}
	});
	}
	
	static void ActionListener_Btn_Exportn(JButton btn_Export, JPanel save_Panel, JPanel button_Panel) {
	btn_Export.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if ( dataTable == null) {

				PersonReferenceExportToExcell.btnExportInfoPersonToExcell(TextInAreaTextPanel.getPerson(),
						TextInAreaTextPanel.getMasivePersonStatusName(),
						TextInAreaTextPanel.getMasivePersonStatus(), TextInAreaTextPanel.getZoneNameMasive(),
						TextInAreaTextPanel.getMasiveKode(), TextInAreaTextPanel.getMasiveMeasurName(),
						TextInAreaTextPanel.getMasiveMeasur(), save_Panel);
			} else {
			PersonReferenceExportToExcell.btnExportTableToExcell(dataTable, PersonelManegementMethods.getTabHeader(), button_Panel);

			}
		}
	});
	}
	
	
	private static void checkKorectionSetInfoToFieldsInSavePersonPanel(JTextField fild, int zoneID) {
		checkIfSetKodeToEnableInsertBtn(fild, zoneID);
		String text = checkEGNByNewPerson();
		text += " "+checkDublicateKodeInNewPerson(fild, zoneID);
		text += " "+checkKorectKodeInNewPerson(fild, zoneID);
		text += " "+checInsertNewPerson();
		PersonelManegementFrame.getLbl_svePerson_Text_Check_EnterInZone().setText(text);
	}
	
	
	private static String checkEGNByNewPerson() {
		String svePersonManegement_newPerson = ReadFileBGTextVariable.getGlobalTextVariableMap().get("svePersonManegement_newPerson");
		JTextField textField_svePerson_EGN = PersonelManegementFrame.getTextField_svePerson_EGN();
		String egn = textField_svePerson_EGN.getText();
		Person person = PersonDAO.getValuePersonByEGN(egn);
		if(person==null) {
		return 	svePersonManegement_newPerson;
		}
		return "";
	}

	protected static void checkIfSetKodeToEnableInsertBtn(JTextField fild, int zoneID) {
		String kode = fild.getText();
		switch (zoneID) {
		case 1: {
			if (!kode.isEmpty() && KodKZ_1_OK) {
			PersonelManegementFrame.getBtn_InsertToHOG().setEnabled(true);
			PersonelManegementFrame.getBtn_InsertToTerit_1().setEnabled(true);
			}else {
				PersonelManegementFrame.getBtn_InsertToHOG().setEnabled(false);
				PersonelManegementFrame.getBtn_InsertToTerit_1().setEnabled(false);
			}
		}
			break;
	
		case 2: {
			if (!kode.isEmpty() && KodKZ_2_OK) {
			PersonelManegementFrame.getBtn_InsertToTerit_2().setEnabled(true);
			}else {
				PersonelManegementFrame.getBtn_InsertToTerit_2().setEnabled(false);
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
		String[] masiveKode =  getMasiveFromKodeAndWorkPlaceFromDBase(person);

		textField_svePerson_KodKZ_1.setText(masiveKode[0]);
		textField_svePersonKodKZ_2.setText(masiveKode[1]);
		textField_svePersonKodKZ_HOG.setText(masiveKode[2]);
		textField_svePersonKodKZ_Terit_1.setText(masiveKode[3]);
		textField_svePersonKodKZ_Terit_2.setText(masiveKode[4]);

		comboBox_svePerson_Firm.select(masiveKode[5]);
		setitemInChoise(comboBox_svePerson_Firm, comboBox_svePerson_Otdel);
		comboBox_svePerson_Otdel.select(masiveKode[6]);
		oldOtdelPerson = masiveKode[6];
		textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson("", person, false));
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
		listOtdelAll =  new ArrayList<>();
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

	
	
	private static boolean checkInfoPerson(String[] infoForPerson,  Person person) {
		
		String[] kode = getKodeStatusByPersonFromDBase( person);
		for (int i = 0; i < kode.length; i++) {
			if(kode[i].equals("н") || kode[i].equals("ЕП-2") ) {
				kode[i] = "";
			}
	}
		
		String errorStr = "";
		System.out.println(infoForPerson[0]+" <-> "+kode[0]);
			if(!infoForPerson[0].equals(kode[0])){
				errorStr += "KZ-1, ";
			}
		
			System.out.println(infoForPerson[1]+" <-> "+kode[1]);
			if(!infoForPerson[1].equals(kode[1])){
				errorStr += "KZ-2, ";
			}
			System.out.println(infoForPerson[2]+" <-> "+kode[2]);
			if(!infoForPerson[2].equals(kode[2])){
				errorStr += "KZ-HOG, ";
			
		}
			System.out.println(infoForPerson[3]+" <-> "+kode[3]);
			if(!infoForPerson[3].equals(kode[3])){
			errorStr += "Ter-1, ";
		}
		
			System.out.println(infoForPerson[4]+" <-> "+kode[4]);
			if(!infoForPerson[4].equals(kode[4])){
				errorStr += "Ter-2, ";
			}
		
		if(!infoForPerson[5].equals(person.getFirstName())){
			errorStr += "FName, ";
		}
		if(!infoForPerson[6].equals(person.getSecondName())){
			errorStr += "SName, ";
		}
		if(!infoForPerson[7].equals(person.getLastName())){
			errorStr += "LName, ";
		}
		
		System.out.println("errorStr "+errorStr);
		if(errorStr.length()>2)	{
			errorStr = errorStr.substring(0, errorStr.length()-2);
		if( OptionDialog(errorStr)) {
			if(errorStr.contains("Name")) {
			person.setFirstName(infoForPerson[5]);
			person.setSecondName(infoForPerson[6]);
			person.setLastName(infoForPerson[7]);
			 PersonDAO.updateValuePerson(person);
			}
			 return true;
			
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
			KodeStatus kodeStat = KodeStatusDAO.getKodeStatusByPersonZoneYear(person, i + 1, curentYear + "");
			if (kodeStat != null) {
				kode[i] = kodeStat.getKode();
			}

		}
		for (String string : kode) {
		System.out.println(string);	
		}
		
		return kode;
	}

	
	
	public static String[] getKodeByPerson(Person person) {
		String[] kode = new String[5]; 
		for (int i = 0; i < 5; i++) {
			kode[i] = "";
			KodeStatus kodeStat = KodeStatusDAO.getKodeStatusByPersonZoneYear(person, i+1, curentYear+"");
			if(kodeStat != null) {
				kode[i] = kodeStat.getKode();
				}
		
		}
		return kode;
	}
	
	
	
	public static boolean OptionDialog(String mesage) {
		String[] options = { "Skip", "Update" };
		int x = JOptionPane.showOptionDialog(null, mesage, "Info", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		if (x > 0) {
			return true;
		}
		return false;
	}
	
	static int generateSelectSpisPrilFrame(List<Spisak_Prilogenia> listSpisak_Prilogenia) {
		SelectSpisPrilFrame.setSelectedContent(-1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				
		if (listSpisak_Prilogenia != null && listSpisak_Prilogenia.size() > 0) {

			String[] masiveSpisPril = new String[listSpisak_Prilogenia.size()+1];

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
			text = text + person.getEgn() + " " + InsertMeasurToExcel.getNamePerson(person) + "\n";
		}

		return text;

	}

	public static String[] getMasiveFromKodeAndWorkPlaceFromDBase(Person person) {

		String[] masive = { "", "", "", "", "", "", "", };
		
		 List<KodeStatus> listKStat = KodeStatusDAO.getKodeStatusByPersonYear(person, curentYear);
		 if(listKStat != null) {
		 for (KodeStatus kodeStatus : listKStat) {
			    switch (kodeStatus.getZone().getId_Zone()) {
		        case 1:
		        	masive[0] = kodeStatus.getKode();
		            break;
		        case 2:
		        	masive[1] = kodeStatus.getKode();
		            break;
		        case 3:
		        	masive[2] = kodeStatus.getKode();
		            break;
		        case 4:
		        	masive[3] = kodeStatus.getKode();
		             break;
		        case 5:
		        	masive[4] = kodeStatus.getKode();
		            break;
		        default:   
		            break; 
		    }
		 }
		}
		
		 PersonStatus perStat = PersonStatusDAO. getLastValuePersonStatusByPerson(person);
		
		
		
		masive[5] = perStat.getWorkplace().getFirmName();
		masive[6] = perStat.getWorkplace().getOtdel();
		
//		int i = 0;
//		for (String string : masive) {
//			System.out.println(i + " -> " + string);
//			i++;
//		}

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
							EGN = ReadExcelFileWBC.getStringfromCell(cell);
							if (EGN.contains("*"))
								EGN = EGN.substring(0, EGN.length() - 1);

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
		List<Spisak_Prilogenia> listSpisak_Prilogenia = PersonelManegementMethods
				.getListSpisak_Prilogenia_FromExcelFile(newOtdelPerson);
		return listSpisak_Prilogenia;
	}

	public static List<Spisak_Prilogenia> getListSpisak_Prilogenia_FromExcelFile(String otdel) {

		String[] path = AplicationMetods.getDataBaseFilePat_ActualPersonalAndExternal();

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

				}
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});
	}

	public static String checInsertNewPerson() {

		String svePersonManegement_newKode = ReadFileBGTextVariable.getGlobalTextVariableMap().get("svePersonManegement_newKode");
		String svePersonManegement_IsEnteredInZone = ReadFileBGTextVariable.getGlobalTextVariableMap().get("svePersonManegement_IsEnteredInZone");
		String svePersonManegement_newOtdel = ReadFileBGTextVariable.getGlobalTextVariableMap().get("svePersonManegement_newOtdel");
		String svePersonManegement_FirstNameError = ReadFileBGTextVariable.getGlobalTextVariableMap().get("svePersonManegement_FirstNameError");
		String svePersonManegement_SekondNameError = ReadFileBGTextVariable.getGlobalTextVariableMap().get("svePersonManegement_SekondNameError");
		String svePersonManegement_LastNameError = ReadFileBGTextVariable.getGlobalTextVariableMap().get("svePersonManegement_LastNameError");
		
		
		
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
		if (person != null && !egn.isEmpty()) {

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

			String[] simpleKode = generateMasiveKodeStatus(person);

//			KodKZ_1
			KodKZ_1_OK = false;
			if (simpleKode[0].equals(textField_svePerson_KodKZ_1.getText())) {
				KodKZ_1_OK = true;
				textField_svePerson_KodKZ_1.setBorder(defoutBorder);
			} else {
				textField_svePerson_KodKZ_1.setBorder(redBorder);
			}
//			KodKZ_2
			KodKZ_2_OK = false;
			if (simpleKode[1].equals(textField_svePerson_KodKZ_2.getText())) {
				KodKZ_2_OK = true;
				textField_svePerson_KodKZ_2.setBorder(defoutBorder);
			} else {
				textField_svePerson_KodKZ_2.setBorder(redBorder);
			}

//			KZ_HOG
			KZ_HOG_OK = false;
			if (simpleKode[2].equals(textField_svePersonKod_KZ_HOG.getText())) {
				KZ_HOG_OK = true;
				textField_svePersonKod_KZ_HOG.setBorder(defoutBorder);
			} else {
				textField_svePersonKod_KZ_HOG.setBorder(redBorder);
			}
//			KodKZ_2
			KZ_Terit_1_OK = false;
			if (simpleKode[3].equals(textField_svePersonKod_KZ_Terit_1.getText())) {
				KZ_Terit_1_OK = true;
				textField_svePersonKod_KZ_Terit_1.setBorder(defoutBorder);
			} else {
				textField_svePersonKod_KZ_Terit_1.setBorder(redBorder);
			}
//			KodKZ_2
			KZ_Terit_2_OK = false;
			if (simpleKode[4].equals(textField_svePersonKod_KZ_Terit_2.getText())) {
				KZ_Terit_2_OK = true;
				textField_svePersonKod_KZ_Terit_2.setBorder(defoutBorder);
			} else {
				textField_svePersonKod_KZ_Terit_2.setBorder(redBorder);
			}

			Otdel_OK = false;
			if (oldOtdelPerson.equals(comboBox_svePerson_Otdel.getSelectedItem())) {
				Otdel_OK = true;
			}

			if (!Otdel_OK) {
				if (!textCheck.isEmpty()) {
					textCheck += ", ";
				}
				textCheck += svePersonManegement_newOtdel;
			}
			if (!KodKZ_1_OK || !KodKZ_2_OK || !KZ_HOG_OK || !KZ_Terit_1_OK || !KZ_Terit_2_OK) {
				if (!textCheck.isEmpty()) {
					textCheck += ", ";
				}
				textCheck += svePersonManegement_newKode;
			
			if (!textCheck.isEmpty()) {
				textCheck += " "+svePersonManegement_IsEnteredInZone;
			}
			}
			return textCheck;
		}
		return "";
	}

	public static String checkDublicateKodeInNewPerson(JTextField textField, int zoneID) {
		String svePersonManegement_KodeIsBusi = ReadFileBGTextVariable.getGlobalTextVariableMap().get("svePersonManegement_KodeIsBusi");
		JTextField textField_svePerson_EGN = PersonelManegementFrame.getTextField_svePerson_EGN();

		JButton btn_SaveToExcelFile = PersonelManegementFrame.getBtn_SaveToExcelFile();
		String textCheck = "";
		String egn = textField_svePerson_EGN.getText();
		String kode = textField.getText();
		if (!egn.isEmpty() && zoneID > 0 && !kode.isEmpty()) {

			List<KodeStatus> listKStat = kodeStatusFromExcelFiles.get(zoneID-1);
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
		System.out.println("1111111111111111111111111");
		for (int i = 0; i < 5; i++) {
		List<KodeStatus> listKStat = KodeStatusDAO.getKodeStatusByYearZone(curentYear, (i+1));
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
				System.out.println("11 " + str);
				strNew = SearchFreeKodeFrame.convertToUpperCyrChart(str);
				System.out.println("22 " + strNew);
				if (!strNew.isEmpty()) {
					str = kode.replace(str, strNew);
					System.out.println("33 " + str);
					textField.setText(str);
				} else {
					fl = false;
				}

				try {
					str = kode.substring(0, sizeKode - 1);
					System.out.println("44 " + str);
					Integer.parseInt(str);
				} catch (Exception e) {
					fl = false;
					System.out.println("55 " + str);
				}
			}

			if (zoneID == 2) {
				str = kode.substring(0, 1);
				System.out.println("11 " + str);
				strNew = SearchFreeKodeFrame.convertToUpperCyrChart(str);
				System.out.println("22 " + strNew);
				if (!strNew.isEmpty()) {
					str = kode.replace(str, strNew);
					System.out.println("33 " + str);
					textField.setText(str);
				} else {
					fl = false;
				}

				try {
					str = kode.substring(1, sizeKode);
					System.out.println("44 " + str);
					Integer.parseInt(str);
				} catch (Exception e) {
					fl = false;
					System.out.println("55 " + str);
				}
			}

			if (zoneID == 3) {
				str = kode.substring(0, 1);
				System.out.println("11 " + str);
				strNew = SearchFreeKodeFrame.convertToUpperCyrChart(str);
				System.out.println("22 " + strNew);
				if (!strNew.isEmpty() && strNew.equals("Н")) {
					str = kode.replace(str, strNew);
					System.out.println("33 " + str);
					textField.setText(str);
				} else {
					fl = false;
				}

				str = kode.substring(sizeKode - 1, sizeKode);
				System.out.println("11 " + str);
				strNew = SearchFreeKodeFrame.convertToUpperCyrChart(str);
				System.out.println("22 " + strNew);
				if (!strNew.isEmpty()) {
					str = kode.replace(str, strNew);
					System.out.println("33 " + str);
					textField.setText(str);
				} else {
					fl = false;
				}

				try {
					str = kode.substring(1, sizeKode - 1);
					System.out.println("44 " + str);
					Integer.parseInt(str);
				} catch (Exception e) {
					fl = false;
					System.out.println("55 " + str);
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

	private static String[] generateMasiveKodeStatus(Person person) {
		String curentYear = AplicationMetods.getCurentYear();
		List<KodeStatus> listKodeStat = KodeStatusDAO.getKodeStatusByPersonYear(person, curentYear);
		
		String[] simpleKode = generateEmptyMasive();
		if(listKodeStat != null) {
		for (KodeStatus kodeStatus : listKodeStat) {

			if (kodeStatus.getZone().getId_Zone() == 1) {
				simpleKode[0] = kodeStatus.getKode();
			}
			if (kodeStatus.getZone().getId_Zone() == 2) {
				simpleKode[1] = kodeStatus.getKode();
			}
			if (kodeStatus.getZone().getId_Zone() == 3) {
				simpleKode[2] = kodeStatus.getKode();
			}
			if (kodeStatus.getZone().getId_Zone() == 4) {
				simpleKode[3] = kodeStatus.getKode();
			}
			if (kodeStatus.getZone().getId_Zone() == 5) {
				simpleKode[4] = kodeStatus.getKode();
			}
		}
		}
		return simpleKode;
	}

	private static String[] generateEmptyMasive() {
		String[] sinpleKode = new String[5];
		for (int i = 0; i < sinpleKode.length; i++) {
			sinpleKode[i] = "";
		}
		return sinpleKode;
	}

	public static void generateListOtdels() {
		listOtdelKz =	getListKZ();
		listOtdelVO = getListVO();
		listOtdelAll = getListALL();

	}
	
	public static boolean isSetKod(String kod) {
	return (!kod.equals("ЕП-2") && !kod.trim().equals("") && !kod.equals("н")&& !inCodeNotNumber(kod));
	}




	public static List<PersonManegement> getListPersonFromFile(JTextArea textArea, JPanel infoPanel, JPanel tablePane, 
			JPanel panel_AllSaerch, JScrollPane scrollPane, JTextField textField_svePerson_Year, JTextField textField){
		
		 JFileChooser chooiser = new JFileChooser();
		 chooiser.setMultiSelectionEnabled(false);
		 chooiser.showOpenDialog(null);
		 File file = chooiser.getSelectedFile();
//		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		 List<PersonManegement> listPersonFromFile = readListPersonFromFile(file, textField);
		 
		 
			if (listPersonFromFile.size() == 0) {
				textArea.setText(notResults);
				PersonelManegementFrame.viewInfoPanel();

			}

			if (listPersonFromFile.size() == 1) {
				textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson(textField_svePerson_Year.getText(),
						listPersonFromFile.get(0).getPerson(), false));
				PersonelManegementFrame.viewInfoPanel();
			}

			if (listPersonFromFile.size() > 1) {
				System.out.println("***** " + listPersonFromFile.size());
				dataTable = addListStringSelectionPersonToComboBox(listPersonFromFile);
				panel_infoPanelTablePanel(dataTable,  textArea, panel_AllSaerch, 
						tablePane,  scrollPane, textField_svePerson_Year);
				PersonelManegementFrame.viewTablePanel();
			}
		 
		 
			
		return listPersonFromFile;
	}

	protected static Object[][] addListStringSelectionPersonToComboBox(List<PersonManegement> listSelectionPerson) {

		Object[][] dataTable = new Object[listSelectionPerson.size()][9];

//				"№", "EGN", 	"FirstName","SecondName","LastName","Otdel",
//				"Kod ",	"Kod",	"selekt"
		
		int zona = ZoneDAO.getValueZoneByNameTerritory(getZonaFromRadioButtons()).getId_Zone();
		int k = 0;
		for (PersonManegement personManegement : listSelectionPerson) {
			System.out.println("egn " + personManegement.getPerson().getEgn());
			dataTable[k][0] = (k+1);
			dataTable[k][1] = personManegement.getPerson().getEgn();
			dataTable[k][2] = personManegement.getPerson().getFirstName();
			dataTable[k][3] = personManegement.getPerson().getSecondName();
			dataTable[k][4] = personManegement.getPerson().getLastName();
			dataTable[k][5] = PersonReferenceFrame.getLastWorkplaceByPerson(personManegement.getPerson());
			dataTable[k][6] = PersonReferenceFrame.getLastKodeByPersonAndZone(personManegement.getPerson(), zona);
			dataTable[k][7] = personManegement.getKodeFromList();
			dataTable[k][8] = true;

			k++;
		}

		return dataTable;

	}

	
	

	private static List<PersonManegement> readListPersonFromFile(File file, JTextField textField) {
		textField.setText(file.getPath());
		Workbook workbook = ReadExcelFileWBC.openExcelFile(file.getPath());
		String name="", kod = "", FirstName = "", SecondName = "", LastName = "";
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
					if(listPerson.size()>0) {
					person = listPerson.get(0);
					}else {
						listPerson = PersonDAO.getValuePersonByObject("FirstName", FirstName);
					}
					if(listPerson.size()>1) {
						person = choisePerson( listPerson, name);
					}
					if(person != null) {
						listPersonManegement.add(new PersonManegement(person, kod));
					}
				}
			}

		}
		return listPersonManegement;
		
	
	}

	
	private static Person choisePerson(List<Person> listPerson, String choicePerson) {
		
		String[] listChoisePerson = generateListChoisePerson(listPerson);
		  String str = (String)JOptionPane.showInputDialog(null, choicePerson,
	                "Select Person", JOptionPane.QUESTION_MESSAGE, null, listChoisePerson, listChoisePerson[2]);
		
		 if(str != null && str.length()>0) {
		 String[] masiveStr = str.split(" ");
		return PersonDAO.getValuePersonByEGN(masiveStr[0]);
		 }
		return null;
	}



	private static String[] generateListChoisePerson(List<Person> listPerson) {

		String[] listString = new String[listPerson.size()+1];
		listString[0] = "";
		int i = 1;
		String str;
		for (Person person : listPerson) {
			PersonStatus personStat = PersonStatusDAO.getLastValuePersonStatusByPerson(person);
			str = "";
			if(personStat != null ) {
				str = personStat.getWorkplace().getOtdel();
			}
			
			listString[i] = person.getEgn()+" "+person.getFirstName()+" "+person.getSecondName()+" "+person.getLastName()+" "+str;
			i++;
		}
		
		return listString;
	}



	private static void panel_infoPanelTablePanel(Object[][] dataTable, JTextArea textArea, JPanel panel_AllSaerch, 
			JPanel tablePane, JScrollPane scrollPane, JTextField textField_svePerson_Year) {
		String[] columnNames = getTabHeader();
		int egn_code_Colum = 1;
		int newKode_code_Colum = 7;
		int choice_code_Colum = 8;
		
		DefaultTableModel dtm = new DefaultTableModel();
		final JTable table = new JTable(dtm);

		dtm = new DefaultTableModel(dataTable, columnNames) {

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
//				if(column ==  choice_code_Colum || column == newKode_code_Colum) {
					return true;
//				}
//				return false;
			}

			@Override
			public Object getValueAt(int row, int col) {
				return dataTable[row][col];
			}

			@SuppressWarnings("unused")
			public void setValueAt(String value, int row, int col) {

				if (!dataTable[row][col].equals(value)) {
					dataTable[row][col] = value;
					fireTableCellUpdated(row, col);

				}
			}
			
			


			

			public int getColumnCount() {
				return columnNames.length;
			}

			public int getRowCount() {
				return dataTable.length;
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

				if (table.getSelectedColumn() == choice_code_Colum) {
					table.rowAtPoint(e.getPoint());
					table.columnAtPoint(e.getPoint());
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();

					System.out.println(reqCodeStr);

				}	
				
				if (e.getClickCount() == 2 && getSelectedModelRow(table) == egn_code_Colum) {
					
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					String reqCodeStr = model.getValueAt(getSelectedModelRow(table), egn_code_Colum).toString();
					System.out.println(reqCodeStr);
					Person person = PersonDAO.getValuePersonByEGN(reqCodeStr);
					textArea.setText(
							TextInAreaTextPanel.createInfoPanelForPerson(textField_svePerson_Year.getText(), person, false));
					PersonelManegementFrame.viewInfoPanel();
					GeneralMethods.setDefaultCursor(panel_AllSaerch);
				}
			}
		});

		new TableFilterHeader(table, AutoChoices.ENABLED);


		
		
		
		dtm.fireTableDataChanged();
		table.setModel(dtm);
		table.setFillsViewportHeight(true);
		table.repaint();
		System.out.println("+++++++++++++ " + dataTable.length);

		tablePane.removeAll();
		tablePane.setPreferredSize(new Dimension(10, 10));
		tablePane.setMaximumSize(new Dimension(32767, 32767));
		panel_AllSaerch.add(tablePane);
		tablePane.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane(table);
		tablePane.add(scrollPane, BorderLayout.CENTER);

	}

	private static int getSelectedModelRow(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}

	static String[] getTabHeader() {
		String[] tableHeader = { " № ","EGN", "FirstName", "SecondName", "LastName", "Otdel", "Kod KZ", "new Kod KZ",
				"selekt" };
		return tableHeader;
	}

	@SuppressWarnings("rawtypes")
	public static Class[] getCulumnClass() {
		Class[] types = { Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, 
				String.class, Boolean.class};
	return types;
}
	
	public static int[] getCulumnSize() {
		int[] col = {50,100,200,200,200,500,200,200,50};
		return col;
	}
	
	private static void initColumnSizes(JTable table, int[] columnSize) {
		int cols = table.getColumnCount();
		TableColumn column = null;

		for (int i = 0; i < cols; i++) {

			column = table.getColumnModel().getColumn(i);

			column.setPreferredWidth(columnSize[i]);
			// column.sizeWidthToFit(); //or simple
		}
	}
	
	
	
}




