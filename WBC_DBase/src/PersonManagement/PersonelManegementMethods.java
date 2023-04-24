package PersonManagement;

import java.awt.Choice;
import java.awt.Color;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.text.BadLocationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import AutoInsertMeasuting.InsertMeasurToExcel;
import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.WorkplaceDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.Zone;
import PersonReference.PersonReferenceFrame;
import PersonReference.TextInAreaTextPanel;
import SearchFreeKode.SearchFreeKodeFrame;
import SearchFreeKode.SearchFreeKodeMethods;

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
	static List<String> listZvenaFromDBase = SearchFreeKodeMethods.generateListZvenaFromDBase();
	static List<List<String>> ListZvenaFromExcellFiles = SearchFreeKodeMethods.generateListZvenaFromExcellFiles();
	static List<String[]> kodeStatusFromExcelFiles = getMasiveKodeStatusFromExcelFiles();

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

	static void ActionListener_ComboBox_Firm(Choice comboBox_Firm, Choice comboBox_Otdel) {

		comboBox_Firm.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					setitemInChoise(comboBox_Firm, comboBox_Otdel);
				}
			}
		});

		comboBox_Otdel.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				checInsertNewPerson();
			}
		});

	}

	static void ActionListener_ComboBox_savePerson_Otdel(Choice comboBox_savePerson_Otdel) {
		comboBox_savePerson_Otdel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				PersonelManegementFrame.setListSpisak_Prilogenia(
						PersonelManegementMethods.generateListSpisPril(comboBox_savePerson_Otdel));

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
	
	
	static void ActionListener_JTextField(JTextField fild, int zoneID) {

		fild.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {

				checkIfSetKodeToEnableInsertBtn(fild, zoneID);
				String text = checkDublicateKodeInNewPerson(fild, zoneID);
				text += checkKorectKodeInNewPerson(fild, zoneID);
				text += checInsertNewPerson();
				PersonelManegementFrame.getLbl_svePerson_Text_Check_EnterInZone().setText(text);

			}
		});
		fild.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				checkIfSetKodeToEnableInsertBtn(fild, zoneID);
				String text = checkDublicateKodeInNewPerson(fild, zoneID);
				text += checkKorectKodeInNewPerson(fild, zoneID);
				text += checInsertNewPerson();
				PersonelManegementFrame.getLbl_svePerson_Text_Check_EnterInZone().setText(text);

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

		String[] masiveKode = getMasiveFromKodeAndWorkPlaceFromExcelFile(person.getEgn());

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

	static void setitemInChoise(Choice comboBox_Firm, Choice comboBox_Otdel) {

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
		listOtdelKz = SearchFreeKodeMethods.generateListZvenaKZ(ListZvenaFromExcellFiles, listZvenaFromDBase);
		listOtdelKz.add("");
		Collections.sort(listOtdelKz);
		return listOtdelKz;
	}

	static List<String> getListVO() {
		listOtdelVO = SearchFreeKodeMethods.generateListZvenaVO(ListZvenaFromExcellFiles, listZvenaFromDBase);
		listOtdelVO.add("");
		Collections.sort(listOtdelVO);
		return listOtdelVO;
	}

	static List<String> getListALL() {
		listOtdelAll = SearchFreeKodeMethods.generateListZvena();
		listOtdelAll.add("");
		Collections.sort(listOtdelAll);
		return listOtdelAll;
	}

	static int generateSelectSpisPrilFrame(List<Spisak_Prilogenia> listSpisak_Prilogenia) {
		SelectSpisPrilFrame.setSelectedContent(-1);

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		if (listSpisak_Prilogenia != null && listSpisak_Prilogenia.size() > 0) {

			String[] masiveSpisPril = new String[listSpisak_Prilogenia.size()];

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

	static void addItemFirm(Choice comboBox_Firm) {
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

	public static String[] getMasiveFromKodeAndWorkPlaceFromExcelFile(String insertEGN) {

		String[] masive = { "", "", "", "", "", "", "", };
		String[] excellFiles_ActualPersonalAndExternal = AplicationMetods
				.getDataBaseFilePat_ActualPersonalAndExternal();
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
			System.out.println(i + " - " + string);
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
												formulyarName, workplace, "2023");

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

	static void checkorektDate(JTextField textFieldDate) {
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
				textCheck += "FirstName not OK";
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
				textCheck += "SekondName not OK ";
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
				textCheck += "LastName not OK";
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
				textCheck += "promenen otdel, zapisa trqbwa da se premesti";
			}
			if (!KodKZ_1_OK || !KodKZ_2_OK || !KZ_HOG_OK || !KZ_Terit_1_OK || !KZ_Terit_2_OK) {
				if (!textCheck.isEmpty()) {
					textCheck += ", ";
				}
				textCheck += "promenen kod";
			}
			if (!textCheck.isEmpty()) {
				textCheck += " Ima li wlizaniq w zonata?";
			}
			return textCheck;
		}
		return "";
	}

	public static String checkDublicateKodeInNewPerson(JTextField textField, int zoneID) {

		JTextField textField_svePerson_EGN = PersonelManegementFrame.getTextField_svePerson_EGN();

		String textCheck = "";
		String egn = textField_svePerson_EGN.getText();
		String kode = textField.getText();
		if (!egn.isEmpty() && zoneID > 0 && !kode.isEmpty()) {

			for (String[] list : kodeStatusFromExcelFiles) {

				if (!egn.equals(list[0]) && kode.equals(list[zoneID])) {
					textField.setForeground(Color.BLACK);
					textField.setBackground(Color.RED);
					return "koda se izpolzva ot drug sluvitel. ";

				} else {
					textField.setBackground(Color.WHITE);
					textCheck = "";
				}

			}

		}
		return textCheck;
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
		getListKZ();
		getListVO();
		getListALL();

	}

}
