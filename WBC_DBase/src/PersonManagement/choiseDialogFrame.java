package PersonManagement;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;


public class choiseDialogFrame extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private Choice choice;
	
		
	public choiseDialogFrame(JFrame parent, String[] listChoisePerson, String choicePerson) {
		
		super(parent, "Изберете служител", true);
		
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		contentPanel = new JPanel();
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			Panel panel = new Panel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			contentPanel.add(panel);
			{
				JLabel lblNewLabel = new JLabel(choicePerson);
				lblNewLabel.setPreferredSize(new Dimension(400, 20));
				panel.add(lblNewLabel);
				lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			}
		}
		{
			choice = new Choice();
			choice.setFont(new Font("monospaced", Font.PLAIN, 12));
			choice.setPreferredSize(new Dimension(0, 20));
			contentPanel.add(choice);
			for (int i = 0; i < listChoisePerson.length; i++) {
				choice.addItem(listChoisePerson[i]);
				
			}
		}
//		{
//			
//			comboBox = new JComboBox<String>(listChoisePerson);
//			comboBox.setPreferredSize(new Dimension(0, 20));
//			contentPanel.add(comboBox);
//		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setPreferredSize(new Dimension(65, 23));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PersonelManegementMethods.setChoisePerson(choice.getSelectedItem().toString());
						dispose(); // Destroy the JFrame object
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setPreferredSize(new Dimension(65, 23));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PersonelManegementMethods.setChoisePerson("");
						dispose(); // Destroy the JFrame object
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		
		int maxStr = 10;
		for (int i = 0; i < listChoisePerson.length; i++) {
			if(maxStr < listChoisePerson[i].length()) maxStr = listChoisePerson[i].length();
		}
		
		
		setSize(maxStr*8, 160);
		setLocationRelativeTo(null);
		setVisible(true);
		
		
		
	}

	
}
