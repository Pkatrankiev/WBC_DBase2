package SearchFreeKode;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import Aplication.ReadKodeStatusFromExcelFile;
import BasiClassDAO.KodeGenerateDAO;
import BasiClassDAO.WorkplaceDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeGenerate;
import BasicClassAccessDbase.Zone;
import PersonReference.PersonReferenceFrame;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Button;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class SearchFreeKodeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textField_Leter;
	private JTextField textField_Start;
	private JTextField textField_End;
	private JScrollPane scrollPane;
	
	private Choice choiceZona;
	private Choice choiceWorkplace;
	private JTable table;
	private JLabel lblComent;
	
	
	public SearchFreeKodeFrame(ActionIcone round) {
		
	
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	
//		List<String> masiveZvenaExcell = SearchFreeKodeMethods.getMasiveZvenaFromExcellFiles();
		List<String> masiveZvena = SearchFreeKodeMethods.generateListZvena();
		List<Zone> masiveZone = ZoneDAO.getAllValueZone();
		
		setMinimumSize(new Dimension(580, 300));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
			JPanel panel1 = SetPanel1(masiveZvena, masiveZone);
			contentPanel.add(panel1);
		
			JPanel panel = setPanel2();
			contentPanel.add(panel);
			
			JPanel panel3 = new JPanel();
			panel3.setMaximumSize(new Dimension(32767, 30));
			FlowLayout fl_panel3 = (FlowLayout) panel3.getLayout();
			fl_panel3.setAlignment(FlowLayout.LEFT);
			contentPanel.add(panel3);
			
			lblComent = new JLabel();
			panel3.add(lblComent);
			
			
			scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			
			table = new JTable();
			scrollPane.setViewportView(table);
			
			setButtonPanel();
			setLocationRelativeTo(null);
			setVisible(true);
			
			changeTextFild(choiceWorkplace, choiceZona);
			
			round.StopWindow();
			
		
	}

	private void setButtonPanel() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
			JButton okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
	}

	private JPanel setPanel2() {
		
		JPanel panel2 = new JPanel();
		panel2.setMaximumSize(new Dimension(32767, 30));
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
			JLabel lblLeter = new JLabel("Leter");
			lblLeter.setPreferredSize(new Dimension(40, 14));
			panel2.add(lblLeter);
		
			textField_Leter = new JTextField();
			panel2.add(textField_Leter);
			textField_Leter.setColumns(10);
			ActionListenerLeterTextField(textField_Leter);
		
			JLabel lblStart = new JLabel("Start");
			lblStart.setHorizontalAlignment(SwingConstants.RIGHT);
			lblStart.setPreferredSize(new Dimension(50, 14));
			panel2.add(lblStart);
		
			textField_Start = new JTextField();
			panel2.add(textField_Start);
			textField_Start.setColumns(10);
			PersonReferenceFrame.TextFieldJustNumbers(textField_Start);
			ActionListenerTextField(textField_Start);
		
			JLabel lblEnd = new JLabel("End");
			lblEnd.setPreferredSize(new Dimension(50, 14));
			lblEnd.setHorizontalAlignment(SwingConstants.RIGHT);
			panel2.add(lblEnd);
		
			textField_End = new JTextField();
			textField_End.setColumns(10);
			PersonReferenceFrame.TextFieldJustNumbers(textField_End);
			ActionListenerTextField(textField_End);
			panel2.add(textField_End);
		
			JLabel lbl_1 = new JLabel("");
			lbl_1.setPreferredSize(new Dimension(20, 14));
			lbl_1.setHorizontalAlignment(SwingConstants.RIGHT);
			panel2.add(lbl_1);
		
			Button btnSearch = new Button("Search");
			btnSearch.setPreferredSize(new Dimension(70, 20));
			panel2.add(btnSearch);
			
			actionListenerBtnSearch( btnSearch);
			
			return panel2;
	}

	private JPanel SetPanel1(List<String> masiveWorkplace, List<Zone> masiveZone) {
		
		JPanel panel1 = new JPanel();
		panel1.setMaximumSize(new Dimension(32767, 30));
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
			JLabel lblZveno = new JLabel("Zveno");
			lblZveno.setHorizontalAlignment(SwingConstants.CENTER);
			lblZveno.setPreferredSize(new Dimension(50, 14));
			panel1.add(lblZveno);
		
			choiceWorkplace = new Choice();
			choiceWorkplace.setPreferredSize(new Dimension(300, 20));
			panel1.add(choiceWorkplace);
			
			
			
			for (String string : masiveWorkplace) {
				choiceWorkplace.add(string);
			}
			
		
			JLabel lblZona = new JLabel("Zona");
			lblZona.setHorizontalAlignment(SwingConstants.RIGHT);
			lblZona.setPreferredSize(new Dimension(60, 14));
			panel1.add(lblZona);
		
			choiceZona = new Choice();
			choiceZona.setPreferredSize(new Dimension(100, 0));
			panel1.add(choiceZona);
			
			for (Zone zone : masiveZone) {
				choiceZona.add(zone.getNameTerritory());
			}
			
			
			actionListenerChoiceWorkplace( choiceWorkplace, choiceZona);
			
			
			
			return panel1;
		}
	
	private void actionListenerChoiceWorkplace(Choice choiceWorkplace, Choice choiceZona) {
		choiceWorkplace.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("/////////////////////////");
				changeTextFild(choiceWorkplace, choiceZona);
				}
			}

			
		});
		
		choiceZona.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("/////////////////////////");
				changeTextFild(choiceWorkplace, choiceZona);
				}
			}

			
		});

	}
	
	private void actionListenerBtnSearch(Button btnSearch) {
		btnSearch.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(checkEmptyFields()) {
				ActionIcone round = new ActionIcone();
				 final Thread thread = new Thread(new Runnable() {
				     @Override
				     public void run() {
				    	 int zone_ID = choiceZona.getSelectedIndex()+1;
				    	 ReadKodeStatusFromExcelFile.getUsedKodeFromExcelFileByZoneAndZveno(choiceWorkplace.getSelectedItem(), zone_ID);
				    	  	String leter = textField_Leter.getText();
							int start = Integer.parseInt(textField_Start.getText());
							int end = Integer.parseInt(textField_End.getText())+1;
							 
							String[] year = SearchFreeKodeMethods.getHeader();
							
							String[][] dataTable = SearchFreeKodeMethods.createDataTableMasive(leter, start, end, zone_ID,  year);
							table = SearchFreeKodeMethods.panel_infoPanelTablePanel(dataTable, year);
							scrollPane.setViewportView(table);
							String freeKodeComent = ReadFileBGTextVariable.getGlobalTextVariableMap().get("freeKodeComent");
							lblComent.setText(freeKodeComent );
							round.StopWindow();
							table.repaint();
				    	     	
				     }
				    });
				    thread.start();	
				}
			}

		
		});

	}
	
	private void ActionListenerLeterTextField(JTextField field) {
		field.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				field.setBackground(Color.WHITE);
				String str = textField_Leter.getText().trim();
				if(str.length()>1) {
					str = str.substring(0, 1);
					field.setText(str);
				}
				field.setText(convertToUpperCyrChart(str));
	        }
});
		
		field.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				field.setBackground(Color.WHITE);
				
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
	
	private void ActionListenerTextField(JTextField field) {
		field.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				field.setBackground(Color.WHITE);
				
	        }
	    });
		
		field.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				field.setBackground(Color.WHITE);
				
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
	
	private String convertToUpperCyrChart(String str) {
		if(!str.isEmpty()) {
			char c = str.charAt(0);
			int ascii = (int)c;
			
			if(ascii>=1040 && ascii<=1103) {
				return str.toUpperCase(); 
				}
		}
		return "";
	}
	
	private boolean checkEmptyFields() {
		boolean fl = true;
		if(textField_Leter.getText().isEmpty()) {
			fl = false;
			textField_Leter.setBackground(Color.RED);
		}
		if(textField_Start.getText().isEmpty()) {
			fl = false;
			textField_Start.setBackground(Color.RED);
		}
		if(textField_End.getText().isEmpty()) {
			fl = false;
			textField_End.setBackground(Color.RED);
		}
		
		return fl;
	}
	
	private void changeTextFild(Choice choiceWorkplace, Choice choiceZona) {
		textField_Leter.setText("");
		textField_Start.setText("");
		textField_End.setText("");
		int Work_ID = WorkplaceDAO.getValueWorkplaceByObject("Otdel", choiceWorkplace.getSelectedItem()).get(0).getId_Workplace();	
		int zone_ID = choiceZona.getSelectedIndex()+1;
		KodeGenerate kodeGen = KodeGenerateDAO.getValueKodeGenerateByWorkplaceAndZone(Work_ID, zone_ID);
		System.out.println(Work_ID+" "+zone_ID);
		if(kodeGen!=null) {
			String let = kodeGen.getLetter_R();
			if(zone_ID == 2 || zone_ID == 5) {
				let = kodeGen.getLetter_L();
			}
		textField_Leter.setText(let);
		textField_Leter.setBackground(Color.WHITE);
		
		textField_Start.setText(kodeGen.getStartCount()+"");
		textField_Start.setBackground(Color.WHITE);
		
		textField_End.setText(kodeGen.getEndCount()+"");
		textField_End.setBackground(Color.WHITE);
		}
	}	
}
