package PersonManagement;

import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.GeneralMethods;
import Aplication.ReadExcelFileWBC;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import Aplication.ReadPersonStatusFromExcelFile;
import AutoInsertMeasuting.InsertMeasurToExcel;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.WorkplaceDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.Workplace;
import PersonReference.PersonReferenceFrame;
import PersonReference.TextInAreaTextPanel;

public class PersonelManegementMethods {

	static String notResults = ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults");
	static boolean multytextInTextArea;
	static String AEC = ReadFileBGTextVariable.getGlobalTextVariableMap().get("AEC");
	static String VO = ReadFileBGTextVariable.getGlobalTextVariableMap().get("VO");
	static Person selectionPerson ; 
	
	static void ActionListenerbBtn_Clear(JButton btn_savePerson_Insert, JButton btn_Clear_1,  JTextArea textArea,
			JTextField textField_EGN, JTextField textField_FName, JTextField textField_SName,
			JTextField textField_LName, Choice comboBox_Firm, Choice comboBox_Otdel) {
	btn_Clear_1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			btn_savePerson_Insert.setEnabled(false);
			textArea.setText("");
			textField_EGN.setText("");
			 textField_FName.setText("");
			  textField_SName.setText("");
			textField_LName.setText("");
			comboBox_Firm.select("");
			setitemInChoise( comboBox_Firm,  comboBox_Otdel);
			comboBox_Otdel.select(""); 
			
			
		}
	});
	}

	static void ActionListenerBtn_savePerson_Insert(JButton btn_savePerson_Insert, JPanel panel_AllSaerch, JTextArea textArea,
			JTextField textField_EGN, JTextField textField_FName, JTextField textField_SName,
			JTextField textField_LName, JTextField textField_svePerson_Name, JTextField textField_svePerson_EGN,
			JTextField textField_svePerson_KodKZ_1, JTextField textField_svePersonKodKZ_2,
			JTextField textField_svePersonKodKZ_HOG, JTextField textField_svePersonKodKZ_Terit_1,
			JTextField textField_svePersonKodKZ_Terit_2, Choice comboBox_svePerson_Firm, Choice comboBox_svePerson_Otdel) {
	btn_savePerson_Insert.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			GeneralMethods.setWaitCursor(panel_AllSaerch);
			
			generateInfoByOnePerson(selectionPerson, textArea, textField_EGN, textField_FName,
					textField_SName, textField_LName, textField_svePerson_Name, textField_svePerson_EGN,
					textField_svePerson_KodKZ_1, textField_svePersonKodKZ_2, textField_svePersonKodKZ_HOG,
					textField_svePersonKodKZ_Terit_1, textField_svePersonKodKZ_Terit_2, comboBox_svePerson_Firm, comboBox_svePerson_Otdel);	
			
			GeneralMethods.setDefaultCursor(panel_AllSaerch);
		}
	});
	}
	
	
	static void ActionListenerbBtn_SearchPerson(JButton btn_SearchPerson, JPanel panel_AllSaerch, JTextArea textArea,
			JTextField textField_EGN, JTextField textField_FName, JTextField textField_SName,
			JTextField textField_LName, JTextField textField_svePerson_Name, JTextField textField_svePerson_EGN,
			JTextField textField_svePerson_KodKZ_1, JTextField textField_svePersonKodKZ_2,
			JTextField textField_svePersonKodKZ_HOG, JTextField textField_svePersonKodKZ_Terit_1,
			JTextField textField_svePersonKodKZ_Terit_2, Choice comboBox_svePerson_Firm, Choice comboBox_svePerson_Otdel, JButton btn_savePerson_Insert) {

		btn_SearchPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

//				textField_svePerson_Name.setText("");
//				textField_svePerson_EGN.setText("");
//				textField_svePerson_KodKZ_1.setText("");
//				textField_svePersonKodKZ_2.setText("");
//				textField_svePersonKodKZ_HOG.setText("");
//				textField_svePersonKodKZ_Terit_1.setText("");
//				textField_svePersonKodKZ_Terit_2.setText("");
				
//				comboBox_svePerson_Firm.select("");
//				setitemInChoise( comboBox_svePerson_Firm,  comboBox_svePerson_Otdel);
//				comboBox_svePerson_Otdel.select("");
				btn_savePerson_Insert.setEnabled(false);
				
				if (!allFieldsEmnty(textField_EGN, textField_FName, textField_SName, textField_LName)) {
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					multytextInTextArea = false;
					textArea.setText("");

					String egn = textField_EGN.getText();
					String firstName = textField_FName.getText();
					String secontName = textField_SName.getText();
					String lastName = textField_LName.getText();

					List<Person>listSelectionPerson = PersonReferenceFrame.getListSearchingPerson(egn, firstName,
							secontName, lastName, "", "", "", "", "");

					if (listSelectionPerson.size() == 0) {
						textArea.setText(notResults);

					}

					if (listSelectionPerson.size() == 1) {
						btn_savePerson_Insert.setEnabled(true);
						selectionPerson = listSelectionPerson.get(0);
						textArea.setText(
								TextInAreaTextPanel.createInfoPanelForPerson("", selectionPerson, false));

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

	static void textAreaActionListener(JButton btn_savePerson_Insert, JTextArea textArea, JPanel panel_AllSaerch, JTextField textField_EGN,
			JTextField textField_FName, JTextField textField_SName, JTextField textField_LName,
			JTextField textField_svePerson_Name, JTextField textField_svePerson_EGN,
			JTextField textField_svePerson_KodKZ_1, JTextField textField_svePersonKodKZ_2,
			JTextField textField_svePersonKodKZ_HOG, JTextField textField_svePersonKodKZ_Terit_1,
			JTextField textField_svePersonKodKZ_Terit_2, Choice comboBox_svePerson_Firm, Choice comboBox_svePerson_Otdel) {
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
						textArea.setText(
								TextInAreaTextPanel.createInfoPanelForPerson("", selectionPerson, false));
						
//						generateInfoByOnePerson(PersonDAO.getValuePersonByEGN(egn), textArea, textField_EGN,
//								textField_FName, textField_SName, textField_LName, textField_svePerson_Name,
//								textField_svePerson_EGN, textField_svePerson_KodKZ_1, textField_svePersonKodKZ_2,
//								textField_svePersonKodKZ_HOG, textField_svePersonKodKZ_Terit_1,
//								textField_svePersonKodKZ_Terit_2, comboBox_svePerson_Firm, comboBox_svePerson_Otdel);

					}
					GeneralMethods.setDefaultCursor(panel_AllSaerch);
				}

			}

		});
	}

	static void generateInfoByOnePerson(Person person, JTextArea textArea, JTextField textField_EGN,
			JTextField textField_FName, JTextField textField_SName, JTextField textField_LName,
			JTextField textField_svePerson_Name, JTextField textField_svePerson_EGN,
			JTextField textField_svePerson_KodKZ_1, JTextField textField_svePersonKodKZ_2,
			JTextField textField_svePersonKodKZ_HOG, JTextField textField_svePersonKodKZ_Terit_1,
			JTextField textField_svePersonKodKZ_Terit_2, Choice comboBox_svePerson_Firm, Choice comboBox_svePerson_Otdel) {

		textField_EGN.setText(person.getEgn());

		textField_FName.setText(person.getFirstName());
		textField_SName.setText(person.getSecondName());
		textField_LName.setText(person.getLastName());

		String name = person.getFirstName() + " " + person.getSecondName() + " " + person.getLastName();
		textField_svePerson_Name.setText(name);
		textField_svePerson_EGN.setText(person.getEgn());
		String[] masiveKode = getMasiveFromKodeAndWorkPlaceFromExcelFile(person.getEgn());

		textField_svePerson_KodKZ_1.setText(masiveKode[0]);
		textField_svePersonKodKZ_2.setText(masiveKode[1]);
		textField_svePersonKodKZ_HOG.setText(masiveKode[2]);
		textField_svePersonKodKZ_Terit_1.setText(masiveKode[3]);
		textField_svePersonKodKZ_Terit_2.setText(masiveKode[4]);
		
		comboBox_svePerson_Firm.select(masiveKode[5]);
		setitemInChoise( comboBox_svePerson_Firm,  comboBox_svePerson_Otdel);
		comboBox_svePerson_Otdel.select(masiveKode[6]);
		
		textArea.setText(TextInAreaTextPanel.createInfoPanelForPerson("", person, false));
	}

	static void setitemInChoise(Choice comboBox_Firm, Choice comboBox_Otdel) {
		
		List<String> listAdd = new ArrayList<>();

		List<String> listOtdelKz = PersonReferenceFrame
				.getListStringOtdel(WorkplaceDAO.getValueWorkplaceByObject("FirmName", AEC));
		listOtdelKz.add("");
		Collections.sort(listOtdelKz);

		List<String> listOtdelVO = PersonReferenceFrame
				.getListStringOtdel(WorkplaceDAO.getValueWorkplaceByObject("FirmName", VO));
		listOtdelVO.add("");
		Collections.sort(listOtdelVO);
		List<String> listOtdelAll = PersonReferenceFrame.getListStringOtdel(WorkplaceDAO.getAllValueWorkplace());
		listOtdelAll.add("");
		Collections.sort(listOtdelAll);
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

	static void addItem(Choice comboBox, List<String> list) {
		comboBox.removeAll();
		for (String otdel : list) {
			comboBox.add(otdel);
		}
	}
	
	static void  addItemFirm(Choice comboBox_Firm){
		List<String> listFirm = new ArrayList<>();
	listFirm.add("");
	listFirm.add(AEC);
	listFirm.add(VO);
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
		String[] excellFiles = ReadKodeStatusFromExcelFile.getFilePathForPersonelAndExternal();

		for (int i = 0; i < excellFiles.length; i++) {
			String pathFile = excellFiles[i];
			String firmName = "АЕЦ Козлодуй";
			if (pathFile.contains("EXTERNAL")) {
				firmName = "Външни организации";
			}
			Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
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
							row = sheet.getLastRowNum()+1;
							i = excellFiles.length;
						}

					}
				}
			}

		}
		int i =0;
		for (String string : masive) {
			System.out.println(i+" - "+string);
			i++;
		}
		
		return masive;
	}

	static boolean inCodeNotNumber(String kode) {
		return kode.replaceAll("\\d*", "").length() == kode.length();
	}

	static void ActionListenerComboBox_Firm(Choice comboBox_Firm, Choice comboBox_Otdel) {

		comboBox_Firm.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					setitemInChoise(comboBox_Firm, comboBox_Otdel);
				}
			}
		});

	}
	
}
