package PersonReference;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InsertFulName_FrameDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	private static String names[] = new String [3];
	
	public InsertFulName_FrameDialog(JFrame parent, int[] coord) {
		super(parent, "", true);
		setBounds(coord[0], coord[1], 450, 140);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		
		
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
				JLabel lblNewLabel = new JLabel("Въведете трите имена");
				panel.add(lblNewLabel);
			
				textField = new JTextField();
				textField.setMaximumSize(new Dimension(2147483647, 20));
				panel.add(textField);
				textField.setColumns(10);
			
		
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String fullname = textField.getText();
						if(!fullname.isEmpty()) {
						String[] splitName = fullname.split(" ");
						if(splitName.length == 1) {
							names[0] = splitName[0];
							names[1] = "";
							names[2] = "";
						}
						if(splitName.length == 2) {
							names[0] = splitName[0];
							names[1] = "";
							names[2] = splitName[1];
						}
						
						if(splitName.length == 3) {
							names[0] = splitName[0];
							names[1] = splitName[1];
							names[2] = splitName[2];
						}
						if(splitName.length > 3) {
							names[0] = splitName[0];
							names[1] = splitName[1];
							for (int i = 2; i < splitName.length; i++) {
								names[2] += splitName[i]+" ";
							}
						}
						}
						for (int i = 0; i < names.length; i++) {
							  System.out.println("ii "+ names[i]);
						}
						dispose();
					}
				});
				
				buttonPane.add(okButton);
				
			
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				
				buttonPane.add(cancelButton);
			
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public static String[] getNames() {
		return names;
	}

}
