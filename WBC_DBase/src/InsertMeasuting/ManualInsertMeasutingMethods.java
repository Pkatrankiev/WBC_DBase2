package InsertMeasuting;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import Aplication.ActionIcone;
import Aplication.ReportMeasurClass;
import BasiClassDAO.DimensionWBCDAO;
import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.TypeMeasurDAO;
import BasiClassDAO.UsersWBCDAO;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.TypeMeasur;
import BasicClassAccessDbase.UsersWBC;
import DatePicker.DatePicker;

public class ManualInsertMeasutingMethods {
	
	private static int keyIndex;
	
	
	public static void ManualInsertMeasutingStartFrame() {

		boolean manualInsertMeasur = true;
		 ActionIcone round = new ActionIcone();
		 File[] files = new File[0];
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				AutoInsertMeasutingMethods.insertMeasur(manualInsertMeasur, files, round);
			}

		
		});
		thread.start();

		
	}
	
	
	public static void ActionListenerAraundKey(JButton okButton, JList<String> jlist, JTextField textField_Date,
			AddNewMeasurFrame addNewMeasurFrame) {
		
		jlist.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
					int selIndex = jlist.getSelectedIndex()+1;
					System.out.println("selIndex "+selIndex);
					
//					jlist.setSelectedIndex(selIndex+2);
//					System.out.println("selIndex++ "+selIndex);
//					System.out.println("selIndex++1 "+jlist.getSelectedIndex()+1);
			
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		
			
		});
	}
	
	
	static void ActionListener_JTextFieldEGN(JTextField fild, JTextField textField_Date, JButton btn_OK,
			JList<String> Jlist, List<Person> listAllPerson) {

		fild.addKeyListener(new KeyAdapter() {

			

			public void keyReleased(KeyEvent evt) {
				int maxIndex = Listenermethod(fild, textField_Date, btn_OK, Jlist, listAllPerson);
				System.out.println("evt.getKeyCode() "+evt.getKeyCode());
				System.out.println("listAllPerson.size() "+listAllPerson.size());
				
				System.out.println("Jlist.getComponentCount() "+Jlist.getComponentCount());
				
				if(evt.getKeyCode() == evt.VK_DOWN || evt.getKeyCode() == evt.VK_UP) {
					
					if(evt.getKeyCode() == evt.VK_DOWN && keyIndex < maxIndex-1) keyIndex++;
					if( evt.getKeyCode() == evt.VK_UP && keyIndex > 0) keyIndex--;

					System.out.println("keyIndex "+keyIndex);
					
					Jlist.setSelectedIndex(keyIndex);

				}else {
					keyIndex = -1;
				}
			}

		});
		fild.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				Listenermethod(fild, textField_Date, btn_OK, Jlist, listAllPerson);
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Listenermethod(fild, textField_Date, btn_OK, Jlist, listAllPerson);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

	}

	private static int Listenermethod(JTextField fild, JTextField textField_Date, JButton btn_OK, JList<String> Jlist,
			List<Person> listAllPerson) {
		Jlist.removeAll();
		List<String> listperson = creadStringPerson(fild.getText(), listAllPerson);
		addDataToJList(Jlist, listperson);
//		System.out.println(listperson.size());
		if (listperson.size() > 0 && checkCorectdate(textField_Date)) {
//			Jlist.setSelectedIndex(0);
			btn_OK.setEnabled(true);
			btn_OK.setFocusable(true);
		} else {
			btn_OK.setEnabled(false);
			btn_OK.setFocusable(false);
		}
		return listperson.size();
	}

	static void ActionListener_JList(JList<String> Jlist, JTextField textField_Date,
			AddNewMeasurFrame addNewMeasurFrame) {

		Jlist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && checkCorectdate(textField_Date)) {
					Jlist.getSelectedValue();
					System.out.println(Jlist.getSelectedValue());
					AddNewMeasurFrame.setSelectedContent(Jlist.getSelectedValue() + " - " + textField_Date.getText());
					addNewMeasurFrame.dispose();
				}
			}
		});

	}

	static void ActionListener_Btn_OK(JButton okButton, JList<String> Jlist, JTextField textField_Date,
			AddNewMeasurFrame addNewMeasurFrame) {
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddNewMeasurFrame.setSelectedContent(Jlist.getSelectedValue() + " - " + textField_Date.getText());
				addNewMeasurFrame.dispose();

			}
		});
	}

	private static List<String> creadStringPerson(String EGN, List<Person> listAllPerson) {
		List<String> list = new ArrayList<>();
		if (!EGN.isEmpty() && EGN.length() > 5) {
			for (Person person : listAllPerson) {
				if (person.getEgn().contains(EGN)) {
					list.add(person.getEgn() + " - " + person.getFirstName() + " " + person.getSecondName() + " "
							+ person.getLastName());
				}
			}
		}
		return list;

	}

	public static ReportMeasurClass generateReportMeasurClassForRepain(AddNewMeasurFrame newMeasur, List<ReportMeasurClass> listReportMeasurClassToSave) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String[] newmeasur = newMeasur.getSelectedContent().split("-");
		for (String str : newmeasur) {
			System.out.println(str.trim());
		}
		ReportMeasurClass reportMeasur = new ReportMeasurClass();
		Measuring measur = new Measuring();
		measur.setReportFileName("");
		try {
			measur.setDate(sdf.parse(newmeasur[2]));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(2);
		UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(1);
		TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(1);
		int count = listReportMeasurClassToSave.size();
		
		if(count > 0 ) {
			lab = listReportMeasurClassToSave.get(count-1).getMeasur().getLab();
			user = listReportMeasurClassToSave.get(count-1).getMeasur().getUser();
			type = listReportMeasurClassToSave.get(count-1).getMeasur().getTypeMeasur();
		}
		
		
		measur.setPerson(PersonDAO.getValuePersonByEGN(newmeasur[0]));
		measur.setLab(lab);
		measur.setUser(user);
		measur.setTypeMeasur(type);
		boolean toExcell = true;
		String koment = "";
		measur.setMeasurKoment(koment);
		try {
			measur.setDoze(0.0);
		} catch (Exception e) {
			// 999999 - absurdna doza imitirashta prazna kletka, za propuskane na tozi red
			// za zapis v bazata
			measur.setDoze(999999);
		}
		measur.setDoseDimension(DimensionWBCDAO.getValueDimensionWBCByID(2));
		List<String> listString = new ArrayList<>();

		reportMeasur.setMeasur(measur);
		reportMeasur.setListNuclideData(listString);
		reportMeasur.setToExcell(toExcell);
		reportMeasur.setKoment(koment);
		reportMeasur.setReportFile(null);

		return reportMeasur;
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

			}
		});

		textField_StartDate2.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {

				checkCorectdate(textField_StartDate2);

			}

		});

	}

	static boolean checkCorectdate(JTextField textField_Date) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		if (DatePicker.correctDate(textField_Date.getText(), sdf)) {
			textField_Date.setForeground(Color.BLACK);
			return true;
		} else {
			textField_Date.setForeground(Color.RED);
			return false;
		}

	}

	static void addDataToJList(JList<String> jlist, List<String> listStr) {
		String[] masive = new String[listStr.size()];
		int i = 0;
		for (String date : listStr) {
			masive[i] = date;
			i++;
		}
		jlist.setListData(masive);
	}

	

	

}
