package SearchFreeKode;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Choice;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Button;
import java.awt.TextArea;
import java.util.List;

public class SearchFreeKodeFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	
	
	public SearchFreeKodeFrame(String zveno) {
		
	
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		List<String> masiveZvena = SearchFreeKodeMethods.getMasiveZvenaFromDBase();
		
		setMinimumSize(new Dimension(580, 300));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
			JPanel panel1 = SetPanel1(masiveZvena);
			contentPanel.add(panel1);
		
			JPanel panel = setPanel2();
			contentPanel.add(panel);
			
			TextArea textArea = new TextArea();
			contentPanel.add(textArea);
			
			setButtonPanel();
			
		
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
		
			textField = new JTextField();
			panel2.add(textField);
			textField.setColumns(10);
		
			JLabel lblStart = new JLabel("Start");
			lblStart.setHorizontalAlignment(SwingConstants.RIGHT);
			lblStart.setPreferredSize(new Dimension(50, 14));
			panel2.add(lblStart);
		
			textField_1 = new JTextField();
			panel2.add(textField_1);
			textField_1.setColumns(10);
		
			JLabel lblEnd = new JLabel("End");
			lblEnd.setPreferredSize(new Dimension(50, 14));
			lblEnd.setHorizontalAlignment(SwingConstants.RIGHT);
			panel2.add(lblEnd);
		
			textField_2 = new JTextField();
			textField_2.setColumns(10);
			panel2.add(textField_2);
		
			JLabel lbl_1 = new JLabel("");
			lbl_1.setPreferredSize(new Dimension(20, 14));
			lbl_1.setHorizontalAlignment(SwingConstants.RIGHT);
			panel2.add(lbl_1);
		
			Button btnSearch = new Button("Search");
			btnSearch.setPreferredSize(new Dimension(70, 20));
			panel2.add(btnSearch);
			return panel2;
	}

	private JPanel SetPanel1(List<String> masiveZvena) {
		
		JPanel panel1 = new JPanel();
		panel1.setMaximumSize(new Dimension(32767, 30));
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
			JLabel lblZveno = new JLabel("Zveno");
			lblZveno.setHorizontalAlignment(SwingConstants.CENTER);
			lblZveno.setPreferredSize(new Dimension(50, 14));
			panel1.add(lblZveno);
		
			Choice choiceZveno = new Choice();
			choiceZveno.setPreferredSize(new Dimension(300, 20));
			panel1.add(choiceZveno);
			
			for (String string : masiveZvena) {
				choiceZveno.add(string);
			}
			
		
			JLabel lblZona = new JLabel("Zona");
			lblZona.setHorizontalAlignment(SwingConstants.RIGHT);
			lblZona.setPreferredSize(new Dimension(60, 14));
			panel1.add(lblZona);
		
			Choice choiceZona = new Choice();
			choiceZona.setPreferredSize(new Dimension(100, 0));
			panel1.add(choiceZona);
			return panel1;
	}

}
