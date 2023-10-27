package Reference_PersonMeasur;

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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import Aplication.RemouveDublikateFromList;
import Aplication.ResourceLoader;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.Workplace;
import PersonManagement.PersonelManegementMethods;
import PersonReference.PersonReferenceExportToExcell;

public class Reference_PersonMeasur_Metods {
	static String minYearInDbase = ReadFileBGTextVariable.getGlobalTextVariableMap().get("minYearInDbase");
	static int	minYeare = Integer.parseInt(minYearInDbase);
	static String curentYear = AplicationMetods.getCurentYear();
	static List<String> listOtdelKz;
	static List<String> listOtdelVO;
	static List<String> listOtdelAll;
	static List<String> listAdd = new ArrayList<>();
	static String AEC = ReadFileBGTextVariable.getGlobalTextVariableMap().get("AEC");
	static String VO = ReadFileBGTextVariable.getGlobalTextVariableMap().get("VO");
	static List<String> listFirm = Arrays.asList("", AEC, VO);
		
	private static Choice comboBox_Firm = Reference_PersonMeasur_Frame.getComboBox_Firm();
	private static Choice comboBox_Otdel = Reference_PersonMeasur_Frame.getComboBox_Otdel();

	private static JTextArea textArea = Reference_PersonMeasur_Frame.getTextArea();
	private static JTextField textField_StartDate = Reference_PersonMeasur_Frame.getTextField_StartDate();
	private static JTextField textField_EndDate = Reference_PersonMeasur_Frame.getTextField_EndDate();
	private static JTextField textField_Year = Reference_PersonMeasur_Frame.getTextField_Year();
	private static JButton btn_Search = Reference_PersonMeasur_Frame.getBtn_Search();
	private static JButton btn_Export = Reference_PersonMeasur_Frame.getBtn_Export();
	
	public static void btnExportToExcell(String[] zoneNameMasive, String[][] masiveKode, JPanel panel_Btn) {

		
		GeneralMethods.setWaitCursor(panel_Btn);
	
		String excelFilePath = ReadFileBGTextVariable.getGlobalTextVariableMap().get("destinationDir")
				+ "exportInfoPerson.xls";
		try {
			int size =0;
			if(masiveKode.length>0)
			size = size +masiveKode.length * masiveKode[0].length;
			
			if ( size < 4000) {

				Workbook workbook = new HSSFWorkbook();
				Sheet sheet = workbook.createSheet("PersonReference");

				CellStyle cellStyleBold = PersonReferenceExportToExcell.cellStyleBold(workbook);
				int endRow = 0;
				endRow = PersonReferenceExportToExcell.writeCells(sheet, cellStyleBold, zoneNameMasive, masiveKode, endRow, true);
				endRow++;

				FileOutputStream outFile = new FileOutputStream(new File(excelFilePath));
				workbook.write(outFile);
				outFile.close();

				PersonReferenceExportToExcell.openWordDoc(excelFilePath);
			} else {
				PersonReferenceExportToExcell.MessageDialog(ReadFileBGTextVariable.getGlobalTextVariableMap().get("cell_maximum_number_exceeded"),
						"файлова грешка");
			}

		} catch (FileNotFoundException e) {
			ResourceLoader.appendToFile(e);
			PersonReferenceExportToExcell.MessageDialog(e.toString(), "файлова грешка");
		} catch (IOException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
		}
		
		GeneralMethods.setDefaultCursor(panel_Btn);
	}
	
	public static void ActionListenerBtnExportToExcell( JPanel panel_Search) {
	btn_Export.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			Reference_PersonMeasur_Metods.btnExportToExcell(TextInAreaTextPanel_Reference_PersonMeasur.getMasiveZoneName(), TextInAreaTextPanel_Reference_PersonMeasur.getMasiveForInfoPanel() ,  panel_Search);
			} 
		
	});
	}
	
	public static void  ActionListenerComboBox_Firm() {

		comboBox_Firm.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("/////////////////////////");
					setitemInChoise();
				}
			}
		});

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
	
	public static void ActionListener_ComboBox_Firm(Choice comboBox_Firm, Choice comboBox_Otdel) {

		comboBox_Firm.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
					setitemInChoise(comboBox_Firm, comboBox_Otdel);
			}
		});

	

	}

	public static void addItemFirm(Choice comboBox_Firm) {
		addItem(comboBox_Firm, listFirm);
	}
	
	
	
	private static void addItem(Choice comboBox, List<String> list) {
		comboBox.removeAll();
		for (String otdel : list) {
			comboBox.add(otdel);
		}
	}

	public static ArrayList<String> getListStringOtdel(List<Workplace> valueWorkplaceByObject) {
		ArrayList<String> list = new ArrayList<String>();
		for (Workplace workplace : valueWorkplaceByObject) {
			list.add(workplace.getOtdel());
		}
		return list;
	}


	
	static void setitemInChoise() {

		
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

	public static void ActionListenerbBtn_Search(JPanel panel_AllSaerch, JButton btn_Search) {

		btn_Search.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String otdel = comboBox_Otdel.getSelectedItem().trim();
				String year = textField_Year.getText();
				String startDate = textField_StartDate.getText().trim();
				String endDate = textField_EndDate.getText().trim();
						
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				Date dateStart = null, dateEnd = null;
				try {
					if(!year.isEmpty()) {
					dateStart =  sdf.parse("01.01." + year);
					}else {
						dateStart =  sdf.parse("01.01." + curentYear);
					}
					if(!startDate.isEmpty()) {
						dateStart =  sdf.parse(startDate);
					}
					
					if(!year.isEmpty()) {
					dateEnd =  sdf.parse("31.12." + year);
					}else {
						dateEnd =  sdf.parse("31.12." + curentYear);
					}
					if(!endDate.isEmpty()) {
						dateEnd =  sdf.parse(endDate);
					
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				} 
				System.out.println(startDate+"  "+ endDate);
				if (!allFieldsEmnty() && !otdel.isEmpty()){
					
					GeneralMethods.setWaitCursor(panel_AllSaerch);
					Workplace workPlace = WorkplaceDAO.getValueWorkplaceByObject("Otdel", otdel).get(0);
					textArea.setText("");
				
					 List<Person>  listPerson = spisakPersonFromWorkplace(workPlace, curentYear);

						String textForArea = TextInAreaTextPanel_Reference_PersonMeasur.createInfoPanelForPerson(listPerson, textField_Year.getText(), dateStart,  dateEnd);
						if(textForArea.isEmpty()) {
							textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
							btn_Export.setEnabled(false);
						}else {
							textArea.setText(textForArea);
							btn_Export.setEnabled(true);
						}
					 
					 
					 
					GeneralMethods.setDefaultCursor(panel_AllSaerch);

				}
			}
		});

	}
	
	public static List<Person> spisakPersonFromWorkplace(Workplace workPlace, String curentYear ){
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date dateStart = null;
		Date dateEnd = null;
		try {
			dateStart = sdf.parse("01.01." + curentYear);
			dateEnd  = sdf.parse("31.12." + curentYear);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Integer> listPersonID = new ArrayList<>();
		List<Person> listPerson = new ArrayList<>();
		List<Person> listPersonNew = new ArrayList<>();
		List<PersonStatus> listPerStat = PersonStatusDAO.getValuePersonStatusByWorkplace_DateStart_DateEnd(workPlace, dateStart, dateEnd);
		System.out.println(listPerStat.size());
		
		for (PersonStatus personStatus : listPerStat) {
					listPersonID.add(personStatus.getPerson().getId_Person());	
		
		}
		
		 listPersonID = RemouveDublikateFromList.removeDuplicates(new ArrayList<Integer>(listPersonID));
		System.out.println(listPersonID.size());
		for (Integer integer : listPersonID) {
			listPerson.add(PersonDAO.getValuePersonByID(integer));	
		}

		for (Person person : listPerson) {
			PersonStatus perStat = PersonStatusDAO.getLastValuePersonStatusByPerson(person);
			String zabel = perStat.getZabelejka();
			String formuliarName = perStat.getSpisak_prilogenia().getFormulyarName();
			if(!zabel.contains("Обходен") && !zabel.contains("Списък напуснали") && !formuliarName.contains("МЗ") 
					&& !formuliarName.contains("NotInList")) {
				System.out.println(person.getEgn()+"  "+zabel+" -  "+formuliarName);
				listPersonNew.add(person);		
		}
		
		}
		return listPersonNew;
	}


	public static void  ActionListenertextField_Year(JTextField textField_Year, JButton btn_Search) {
		textField_Year.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {
				textField_Year.setForeground(Color.BLACK);
				btn_Search.setEnabled(true);
				if (!textField_Year.getText().isEmpty()) {
					try {
						long number = Long.parseLong(textField_Year.getText());
						if (number < minYeare || number > Calendar.getInstance().get(Calendar.YEAR)) {
							textField_Year.setForeground(Color.RED);
							btn_Search.setEnabled(false);
						}
					} catch (Exception e) {
						textField_Year.setForeground(Color.RED);
						btn_Search.setEnabled(false);
					}
				}
			}
		});

	}

	public static void ActionListener_ComboBox_Otdel(Choice comboBox_Otdel, JButton btn_Search) {
		comboBox_Otdel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btn_Search.setEnabled(true);
				if(comboBox_Otdel.getSelectedItem().isEmpty()){
					btn_Search.setEnabled(false);
				}
			}
		});
		comboBox_Otdel.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				btn_Search.setEnabled(true);
				if(comboBox_Otdel.getSelectedItem().isEmpty()){
					btn_Search.setEnabled(false);
				}
			}
		});
	}
	
	
	protected static boolean allFieldsEmnty() {
		return ( textField_Year.getText().trim().isEmpty()  && textField_StartDate.getText().trim().isEmpty()
				&& textField_EndDate.getText().trim().isEmpty());
	}


	public static void checkorektDate(JTextField textFieldDate) {
		textFieldDate.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {

				if (AplicationMetods.incorrectDate(textFieldDate.getText()) && !textFieldDate.getText().trim().isEmpty()) {
					btn_Search.setEnabled(false);
					textFieldDate.setForeground(Color.RED);
				} else {
					btn_Search.setEnabled(true);
					textFieldDate.setForeground(Color.BLACK);

				}
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});
	}

	static List<String> getListKZ() {
		listOtdelKz = PersonelManegementMethods.getStringListFromActualWorkplaceByFirmname("АЕЦ Козлодуй");
//		listOtdelKz = SearchFreeKodeMethods.generateListZvenaKZ(ListZvenaFromExcellFiles, listZvenaFromDBase);
		listOtdelKz.add("");
		Collections.sort(listOtdelKz);
		return listOtdelKz;
	}


	static List<String> getListVO() {
		listOtdelVO = PersonelManegementMethods.getStringListFromActualWorkplaceByFirmname("Външни организации");
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
	
	public static void generateListOtdels() {
		getListKZ();
		getListVO();
		getListALL();

	}
	
	
	public static void MessageDialog(String textInFrame, String textFrame) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		JOptionPane.showMessageDialog(jf, textInFrame, textFrame, JOptionPane.PLAIN_MESSAGE, otherIcon);

	}

	

	
	
}
