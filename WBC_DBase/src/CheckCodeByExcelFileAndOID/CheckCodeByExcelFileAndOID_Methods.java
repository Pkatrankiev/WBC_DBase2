package CheckCodeByExcelFileAndOID;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import PersonReference_OID.OID_Person_AEC;
import PersonReference_OID.OID_Person_AECDAO;


public class CheckCodeByExcelFileAndOID_Methods {

	private static ProgressBarWorkerPersonOIDCode pbwpr;
	
	static void ActionListenertextField_Year(JTextField textField_Year, JButton btn_StartGenerateTable) {
		textField_Year.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {
				int minYeare = 0;
				String minYearInDbase = ReadFileBGTextVariable.getGlobalTextVariableMap().get("minYearInDbase");
				try {
					minYeare = Integer.parseInt(minYearInDbase);
				} catch (Exception e) {
					JFrame jf = new JFrame();
					jf.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jf, "Year not korekt in BGTextVariable", "Error",
							JOptionPane.PLAIN_MESSAGE, null);
					System.exit(0);
				}
				textField_Year.setForeground(Color.BLACK);
				btn_StartGenerateTable.setEnabled(true);
				if (!textField_Year.getText().isEmpty()) {
					try {
						long number = Long.parseLong(textField_Year.getText());
						if (number < minYeare || number > Calendar.getInstance().get(Calendar.YEAR)+1) {
							textField_Year.setForeground(Color.RED);
							btn_StartGenerateTable.setEnabled(false);
						}
					} catch (Exception e) {
						textField_Year.setForeground(Color.RED);
						btn_StartGenerateTable.setEnabled(false);
					}
				}
			}
		});

	}
	
	public static void ActionListener_Btn_StartGenerateTable_PersonStatus(JButton btn_StartGenerateTable,
			JPanel panel_AllSaerch, JPanel tablePane, JScrollPane scrollPane, JProgressBar progressBar) {

		btn_StartGenerateTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CheckCodeByExcelFileAndOID_Frame.viewInfoPanel();
				
				GeneralMethods.setWaitCursor(tablePane);

				JTextField textField_Year = CheckCodeByExcelFileAndOID_Frame.getTextField_Year();
				JTextField textField_EGN = CheckCodeByExcelFileAndOID_Frame.getTextEGN();
				String egn = textField_EGN.getText();
				Person person = PersonDAO.getValuePersonByEGN(egn);
				    if(person != null || egn.isEmpty() || OptionDialog("Не намирам служител с ЕГН: "+ egn)) {
				    	 if(person == null && textField_Year.getText().isEmpty()) {
				    		 JOptionPane.showMessageDialog(null, "Въведете коректна година или ЕГН", "Грешни данни", JOptionPane.ERROR_MESSAGE);
				    	 }else {
				    		 if(person == null) {
				    			 textField_EGN.setText(""); 
				    		 }
				    	pbwpr = new ProgressBarWorkerPersonOIDCode(textField_Year, person, progressBar, panel_AllSaerch, tablePane,
								scrollPane);
						pbwpr.execute();	
				    	 }
				    }else {
				    	
				    }
				

				GeneralMethods.setDefaultCursor(panel_AllSaerch);

			}

//					

		});

	}
	
	public static boolean OptionDialog(String mesage) {

				String[] options = { "Не", "Да" };
				JFrame jf = new JFrame();
				jf.setAlwaysOnTop(true);
				int x = JOptionPane.showOptionDialog(jf, mesage, "Info", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

				if (x > 0) {
					return true;
				}

				return false;
			}

	protected static Object[][] generateMasiveSelectionPerson(List<PersonStatusNew> listSelectionPerson) {

		Object[][] dataTable = new Object[listSelectionPerson.size()][11];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int k = 0;
		for (PersonStatusNew personStat : listSelectionPerson) {
			dataTable[k][0] = (k + 1);
			dataTable[k][1] = personStat.getPerson().getEgn();
			dataTable[k][2] = personStat.getPerson().getFirstName() + " " + personStat.getPerson().getSecondName() + " "
					+ personStat.getPerson().getLastName();
			dataTable[k][3] = personStat.getWorkplace().getOtdel();
			dataTable[k][4] = personStat.getFormulyarName();
			dataTable[k][5] = sdf.format(personStat.getStartDate());
			dataTable[k][6] = sdf.format(personStat.getEndDate());
			dataTable[k][7] = personStat.getYear();
			dataTable[k][8] = personStat.getUserWBC().getLastName();
			dataTable[k][9] = sdf.format(personStat.getDateSet());
			dataTable[k][10] = personStat.getZabelejka();

			k++;
		}

		return dataTable;

	}

	public static String[] getInfoFromAECByEGN(String egn) {

		String[] str = new String[6];
		OID_Person_AEC person_AEC = OID_Person_AECDAO.getOID_Person_AECByEGN(egn);
		if (person_AEC != null) {
			str[0]= person_AEC.getEgn();
			str[1]= person_AEC.getFirstName(); 
			str[2]= person_AEC.getSecondName() ;
			str[3]= person_AEC.getLastName() ;

			
		
		}
		return str;

	}
	
	


	
	
}
