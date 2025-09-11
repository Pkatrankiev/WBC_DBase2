package InsertMeasuting;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Aplication.ReadFileBGTextVariable;
import Aplication.ReportMeasurClass;
import BasicClassAccessDbase.Person;
import PersonReference.PersonReferenceFrame;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AddNewMeasurFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static JTextField textField_Date;
	private static JLabel lbl_Icon;
	String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("calendarIcon");
	private ImageIcon pic = new ImageIcon(getClass().getClassLoader().getResource(iconn));
	private JTextField textField_Person_EGN;
	static String selectedContent = "";
 
	
	public AddNewMeasurFrame( JFrame parent, List<Person> listAllPerson, String date) {
		
		super(parent, "text", true);
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			contentPanel.add(panel, BorderLayout.NORTH);
				
				JLabel lblNewLabel_1 = new JLabel("Дата");
				lblNewLabel_1.setPreferredSize(new Dimension(35, 14));
				panel.add(lblNewLabel_1);
				
				
				
				
				textField_Date = new JTextField(date);
//				textField_Date.add(textField_Date_MeasurNaw);
				textField_Date.setColumns(8);
				panel.add(textField_Date);

				lbl_Icon = new JLabel(pic);
				lbl_Icon.setPreferredSize(new Dimension(20, 20));
				lbl_Icon.setHorizontalAlignment(SwingConstants.CENTER);
				lbl_Icon.setBorder(null);
				lbl_Icon.setAlignmentX(1.0f);
				panel.add(lbl_Icon);
				
				JLabel lblNewLabel = new JLabel("ЕГН");
				lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				lblNewLabel.setPreferredSize(new Dimension(30, 14));
				lblNewLabel.setMinimumSize(new Dimension(30, 14));
				panel.add(lblNewLabel);
	
				
				textField_Person_EGN = new JTextField("");
				panel.add(textField_Person_EGN);
				textField_Person_EGN.setColumns(10);
				PersonReferenceFrame.TextFieldJustNumbers(textField_Person_EGN);
				textField_Person_EGN.setFocusable(true);
				JList<String> Jlist = new JList<String>();
				Jlist.setFont(new Font("monospaced", Font.PLAIN, 12));
				contentPanel.add(Jlist, BorderLayout.CENTER);
				
				
				
				
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
				JButton okButton = new JButton("OK");
				
				okButton.setActionCommand("OK");
				okButton.setEnabled(false);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AddNewMeasurFrame.setSelectedContent(null);
						dispose();	
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			
				ManualInsertMeasutingMethods.ActionListener_JList( Jlist, textField_Date, this);
				ManualInsertMeasutingMethods.ActionListener_JTextFieldEGN(textField_Person_EGN, textField_Date, okButton,  Jlist, listAllPerson) ;
				ManualInsertMeasutingMethods.ActionListenerSetDateByDatePicker(lbl_Icon, textField_Date);
				ManualInsertMeasutingMethods.ActionListener_Btn_OK(okButton, Jlist, textField_Date, this);
				
				ManualInsertMeasutingMethods.ActionListenerAraundKey(okButton, Jlist, textField_Date, this);
				textField_Person_EGN.requestFocusInWindow();
				setLocationRelativeTo(null);
				setVisible(true);
				
				
		
	}

	public String getSelectedContent() {
		return selectedContent;
	}

	public static void setSelectedContent(String selected_Content) {
		selectedContent = selected_Content;
	}

	
	
	
}
