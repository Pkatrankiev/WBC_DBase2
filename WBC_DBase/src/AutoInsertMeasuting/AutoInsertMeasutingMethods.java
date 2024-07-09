package AutoInsertMeasuting;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadResultFromReport;
import Aplication.ReportMeasurClass;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.MeasuringDAO;
import BasiClassDAO.NuclideWBCDAO;
import BasiClassDAO.ResultsWBCDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.NuclideWBC;
import PersonManagement.PersonelManegementMethods;

public class AutoInsertMeasutingMethods {

	public static void AutoInsertMeasutingStartFrame() {

		JFileChooser chooiser = new JFileChooser();
		chooiser.setMultiSelectionEnabled(true);
		chooiser.showOpenDialog(null);
		File[] files = chooiser.getSelectedFiles();
		System.out.println(files.length);

		if(files.length>0) {
		ActionIcone round = new ActionIcone();
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				List<ReportMeasurClass> list = ReadResultFromReport.getListReadGamaFiles(files);
				String[] listSimbolNuclide = NuclideWBCDAO.getMasiveSimbolNuclide();
				String[] listLaboratory = LaboratoryDAO.getMasiveLaboratory();
				String[] listUserWBC = UsersWBCDAO.getMasiveUserWBCNames();
				String[] listTypeMeasur = TypeMeasurDAO.getMasiveTypeMeasur();
				String[] listTypeNameMeasur = TypeMeasurDAO.getMasiveNameTypeMeasur();
				new AutoInsertMeasutingFrame(round, new JFrame(), list, listSimbolNuclide, listLaboratory, listUserWBC,
						listTypeMeasur, listTypeNameMeasur, null);
			}
		});
		thread.start();

		}
	}

	public static void SaveMesuring(List<ReportMeasurClass> listReportMeasurClassToSave, ActionIcone round) {
		SaveReportMeasurTo_PersonelORExternalExcelFile
				.SaveListReportMeasurClassToExcellFile(listReportMeasurClassToSave, false);
		List<ReportMeasurClass> listReportMeasurClass = SaveReportMeasurTo_PersonelORExternalExcelFile
				.getListReportMeasurClass();
		SaveListReportMeasurClassToDBase(round, listReportMeasurClass);
	}

	protected static void SaveListReportMeasurClassToDBase(ActionIcone round,
			List<ReportMeasurClass> listReportMeasurClassToSave) {
		Measuring lastMeasur = null;
		for (ReportMeasurClass reportMeasur : listReportMeasurClassToSave) {

			MeasuringDAO.setObjectMeasuringToTable(reportMeasur.getMeasur());
			lastMeasur = MeasuringDAO.getLastMeasuring();
			if (!reportMeasur.getListNuclideData().isEmpty()) {
				for (String stringNuclideData : reportMeasur.getListNuclideData()) {

					stringNuclideData = stringNuclideData.replaceAll("##", "");
					String[] masiveStrNuclide = stringNuclideData.split(":");

					NuclideWBC nuclide = NuclideWBCDAO.getValueNuclideWBCByObject("Symbol", masiveStrNuclide[1].trim())
							.get(0);
					double actyviti = Double.parseDouble(masiveStrNuclide[2].replaceAll(",", "."));
					double postaplenie = Double.parseDouble(masiveStrNuclide[3].replaceAll(",", "."));
					double ggp = Double.parseDouble(masiveStrNuclide[4].replaceAll(",", "."));
					double nuclideDoze = Double.parseDouble(masiveStrNuclide[5].replaceAll(",", "."));

					ResultsWBCDAO.setValueResultsWBC(lastMeasur, nuclide, actyviti, postaplenie, ggp, nuclideDoze);

				}

			}

		}
		round.StopWindow();
	}

	public static void btnSave_AutoInsertMeasuting_ActionListener(AutoInsertMeasutingFrame autoInsertMeasutingFrame) {
		JButton btnSave = AutoInsertMeasutingFrame.getBtnSave();
		List<ReportMeasurClass> listReportMeasurClass = AutoInsertMeasutingFrame.getListReportMeasurClass();
		int countMeasur = AutoInsertMeasutingFrame.getCountMeasur();
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(PersonelManegementMethods.checkIsClosedPersonAndExternalFile() 
						&& PersonelManegementMethods.checkIsClosedMonthPersonAndExternalFile()) {
				
				ActionIcone round = new ActionIcone();
				final Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						if (checkEmptryPostaplenieField(countMeasur, round)) {
							if (checkEmptryDozeField(countMeasur, round)) {
								List<ReportMeasurClass> listReportMeasurClassToSave = AutoInsertMeasutingFrame
										.generateListReportMeasurClassForSaveData(countMeasur, listReportMeasurClass);
								AutoInsertMeasutingMethods.SaveMesuring(listReportMeasurClassToSave, round);

								autoInsertMeasutingFrame.dispose();
							}
						}

					}
				});
				thread.start();
				}
			}
		});

	}
	
	@SuppressWarnings("rawtypes")
	public static boolean checkEmptryDozeField(int countData, ActionIcone round) {
		String isEmptyDozeFilds = ReadFileBGTextVariable.getGlobalTextVariableMap().get("isEmptyDozeFilds");
		String isEmptyDozeNuclideFilds = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("isEmptyDozeNuclideFilds");
		String noSaveRowToBase = ReadFileBGTextVariable.getGlobalTextVariableMap().get("noSaveRowToBase");
		String rowWithoutSaveToExcellFile = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("rowWithoutSaveToExcellFile");
		JTextField[] textFieldDoza = AutoInsertMeasutingFrame.getTextFieldDoza();
		JTextField[][] textField_DozeNuclide = AutoInsertMeasutingFrame.getTextField_DozeNuclide();
		JCheckBox[] chckbxSetToExcel = AutoInsertMeasutingFrame.getChckbxSetToExcel();
		JComboBox[][] comboBox_Nuclide = AutoInsertMeasutingFrame.getComboBox_Nuclide();
		JComboBox[] comboBoxTypeMeasur = AutoInsertMeasutingFrame.getComboBoxTypeMeasur();
		
		boolean emtryDoze = false;
		boolean emtryDozeNuclide = false;
		boolean noCheckedInExcell = false;
		String mesage = "";
		for (int i = 0; i < countData; i++) {
			if (textFieldDoza[i].getText().isEmpty()) {
				textFieldDoza[i].setBackground(Color.RED);
				emtryDoze = true;
			}
			if (!chckbxSetToExcel[i].isSelected()) {
				chckbxSetToExcel[i].setBackground(Color.RED);
				noCheckedInExcell = true;
			}
			for (int k = 0; k < 20; k++) {
				if (comboBox_Nuclide[i][k] != null && !comboBoxTypeMeasur[i].getSelectedItem().toString().equals("M")) {
					if (textField_DozeNuclide[i][k].getText().isEmpty()) {
						textField_DozeNuclide[i][k].setBackground(Color.RED);
						emtryDozeNuclide = true;
					}
				}
			}
		}

		if (emtryDoze) {
			mesage = mesage + "<html>" + isEmptyDozeFilds;
		}
		if (emtryDozeNuclide) {
			if (mesage.isEmpty()) {
				mesage = "<html>";
			} else {
				mesage = mesage + "<br>";
			}
			mesage = mesage + isEmptyDozeNuclideFilds + "<br>";
		}
		if (!mesage.isEmpty()) {
			mesage = mesage + noSaveRowToBase;
		}
		if (noCheckedInExcell) {
			if (mesage.isEmpty()) {
				mesage = "<html>";
			} else {
				mesage = mesage + "<br>";
			}
			mesage = mesage + rowWithoutSaveToExcellFile;
		}
		if (!mesage.isEmpty()) {
			mesage = mesage + "</html>";
		}

		if (mesage.isEmpty()) {
			return true;
		} else {
			return OptionDialog(mesage, round);
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean checkEmptryPostaplenieField(int countData, ActionIcone round) {

		JTextField[] textFieldDoza = AutoInsertMeasutingFrame.getTextFieldDoza();
		JTextField[][] textField_DozeNuclide = AutoInsertMeasutingFrame.getTextField_DozeNuclide();
		JTextField[][] textField_Actyvity = AutoInsertMeasutingFrame.getTextField_Actyvity();
		JTextField[][] textField_Postaplenie = AutoInsertMeasutingFrame.getTextField_Postaplenie();
		JTextField[][] textField_GGP = AutoInsertMeasutingFrame.getTextField_GGP();
		JComboBox[][] comboBox_Nuclide = AutoInsertMeasutingFrame.getComboBox_Nuclide();
		JComboBox[] comboBoxTypeMeasur = AutoInsertMeasutingFrame.getComboBoxTypeMeasur();
		
		String isEmptyPostaplenieNuclideFilds = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("isEmptyPostaplenieNuclideFilds");
		String dozeStr = "";
		boolean emtryDozeNuclide = false, isnuclide = false;

		String mesage = "";
		for (int i = 0; i < countData; i++) {
			System.out.println(i+" - "+textFieldDoza[i].getText());
			dozeStr = textFieldDoza[i].getText();
			if ((!dozeStr.isEmpty() && Double.parseDouble(dozeStr.replaceAll(",", ".")) > 0)
					|| comboBoxTypeMeasur[i].getSelectedItem().toString().equals("M")) {
//				if (Double.parseDouble(dozeStr.replaceAll(",", ".")) > 0) {
//					textFieldDoza[i].setBackground(Color.RED);
//					emtryDozeNuclide = true;
//				}
				for (int k = 0; k < 20; k++) {
					if (comboBox_Nuclide[i][k] != null) {
						isnuclide = true;
						if (comboBoxTypeMeasur[i].getSelectedItem().toString().equals("M")) {

							if (textField_Actyvity[i][k].getText().isEmpty() || !(Double
									.parseDouble(textField_Actyvity[i][k].getText().replaceAll(",", ".")) > 0)) {
								textField_Actyvity[i][k].setBackground(Color.RED);
								emtryDozeNuclide = true;
							}
						} else {
							if (textField_Actyvity[i][k].getText().isEmpty() || !(Double
									.parseDouble(textField_Actyvity[i][k].getText().replaceAll(",", ".")) > 0)) {
								textField_Actyvity[i][k].setBackground(Color.RED);
								emtryDozeNuclide = true;
							}
							if (textField_DozeNuclide[i][k].getText().isEmpty() || !(Double
									.parseDouble(textField_DozeNuclide[i][k].getText().replaceAll(",", ".")) > 0)) {
								textField_DozeNuclide[i][k].setBackground(Color.RED);
								emtryDozeNuclide = true;
							}

							if (textField_Postaplenie[i][k].getText().isEmpty() || !(Double
									.parseDouble(textField_Postaplenie[i][k].getText().replaceAll(",", ".")) > 0)) {
								textField_Postaplenie[i][k].setBackground(Color.RED);
								emtryDozeNuclide = true;
							}
							if (textField_GGP[i][k].getText().isEmpty()
									|| !(Double.parseDouble(textField_GGP[i][k].getText().replaceAll(",", ".")) > 0)) {
								textField_GGP[i][k].setBackground(Color.RED);
								emtryDozeNuclide = true;

							}
						}
					}
				}
				if (!isnuclide) {
					emtryDozeNuclide = true;
				}
			}
		}
		System.out.println(isnuclide + "  " + emtryDozeNuclide);
		if (emtryDozeNuclide) {
			mesage = "<html>" + isEmptyPostaplenieNuclideFilds + "</html>";
			MessageDialog(mesage);
			round.StopWindow();
			return false;
		}

		return true;

	}

	public static void MessageDialog(String mesage) {
		Icon otherIcon = null;
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, mesage, "Info", JOptionPane.PLAIN_MESSAGE, otherIcon);

	}

	public static boolean OptionDialog(String mesage, ActionIcone round) {
		String autoInsertMeasuting_Back = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_Back");
		String autoInsertMeasuting_save = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("autoInsertMeasuting_save");
		String[] options = { autoInsertMeasuting_Back, autoInsertMeasuting_save };
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		int x = JOptionPane.showOptionDialog(jf, mesage, "Info", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		System.out.println(x);
		/**
		 * select "Back" -> 0; select "Ok" -> 1;
		 */
		if (x > 0) {
			return true;
		}
		round.StopWindow();
		
		return false;
	}
	
	
	
	
	
}
