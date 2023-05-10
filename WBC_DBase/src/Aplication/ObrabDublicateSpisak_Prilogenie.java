package Aplication;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ObrabDublicateSpisak_Prilogenie extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblPolDublObject;
	private JLabel lblDublObjectPol1;
	private JLabel lblDublObjectPol2;
	private static JTextField textFieldNewObjectPol1;
	private static JTextField textFieldNewObjectPol2;
	private JLabel lblNewObjectTxt;
	private JLabel lblDublObject;
	private JLabel lblNewObject;
	private boolean isDoublePole1 = true;
	private boolean isDoublePole2 = true;

	public ObrabDublicateSpisak_Prilogenie(Frame parent, String strDublObject, String pol1_DublObject,
			String pol2_DublObject, String strNewObject, String pol1_NewObject, String pol2_NewObject) {
		super(parent, "Дублирани Обекти", true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		try {

			setBounds(100, 100, 450, 208);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			GridBagLayout gbl_contentPanel = new GridBagLayout();
			gbl_contentPanel.columnWidths = new int[] { 10, 153, 86, 34, 85, 0 };
			gbl_contentPanel.rowHeights = new int[] { 14, 14, 14, 14, 14, 20, 0 };
			gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			contentPanel.setLayout(gbl_contentPanel);

			lblDublObject = new JLabel("Дублиран обект");
			GridBagConstraints gbc_lblDublObject = new GridBagConstraints();
			gbc_lblDublObject.anchor = GridBagConstraints.NORTH;
			gbc_lblDublObject.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDublObject.insets = new Insets(0, 0, 5, 5);
			gbc_lblDublObject.gridx = 1;
			gbc_lblDublObject.gridy = 0;
			contentPanel.add(lblDublObject, gbc_lblDublObject);

			JLabel lblDublObjectTxt = new JLabel(strDublObject);
			GridBagConstraints gbc_lblDublObjectTxt = new GridBagConstraints();
			gbc_lblDublObjectTxt.anchor = GridBagConstraints.NORTH;
			gbc_lblDublObjectTxt.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDublObjectTxt.insets = new Insets(0, 0, 5, 0);
			gbc_lblDublObjectTxt.gridwidth = 4;
			gbc_lblDublObjectTxt.gridx = 1;
			gbc_lblDublObjectTxt.gridy = 1;
			contentPanel.add(lblDublObjectTxt, gbc_lblDublObjectTxt);

			lblNewObject = new JLabel("Нов обект");
			GridBagConstraints gbc_lblNewObject = new GridBagConstraints();
			gbc_lblNewObject.anchor = GridBagConstraints.NORTH;
			gbc_lblNewObject.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblNewObject.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewObject.gridx = 1;
			gbc_lblNewObject.gridy = 2;
			contentPanel.add(lblNewObject, gbc_lblNewObject);

			lblNewObjectTxt = new JLabel(strNewObject);
			GridBagConstraints gbc_lblNewObjectTxt = new GridBagConstraints();
			gbc_lblNewObjectTxt.anchor = GridBagConstraints.NORTH;
			gbc_lblNewObjectTxt.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblNewObjectTxt.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewObjectTxt.gridwidth = 4;
			gbc_lblNewObjectTxt.gridx = 1;
			gbc_lblNewObjectTxt.gridy = 3;
			contentPanel.add(lblNewObjectTxt, gbc_lblNewObjectTxt);

			lblPolDublObject = new JLabel("Полета на дублирания обект");
			GridBagConstraints gbc_lblPolDublObject = new GridBagConstraints();
			gbc_lblPolDublObject.anchor = GridBagConstraints.NORTHWEST;
			gbc_lblPolDublObject.insets = new Insets(0, 0, 5, 5);
			gbc_lblPolDublObject.gridx = 1;
			gbc_lblPolDublObject.gridy = 4;
			contentPanel.add(lblPolDublObject, gbc_lblPolDublObject);

			lblDublObjectPol1 = new JLabel(pol1_DublObject);
			GridBagConstraints gbc_lblDublObjectPol1 = new GridBagConstraints();
			gbc_lblDublObjectPol1.anchor = GridBagConstraints.NORTH;
			gbc_lblDublObjectPol1.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDublObjectPol1.insets = new Insets(0, 0, 5, 5);
			gbc_lblDublObjectPol1.gridx = 2;
			gbc_lblDublObjectPol1.gridy = 4;
			contentPanel.add(lblDublObjectPol1, gbc_lblDublObjectPol1);

			lblDublObjectPol2 = new JLabel(pol2_DublObject);
			GridBagConstraints gbc_lblDublObjectPol2 = new GridBagConstraints();
			gbc_lblDublObjectPol2.anchor = GridBagConstraints.NORTH;
			gbc_lblDublObjectPol2.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblDublObjectPol2.insets = new Insets(0, 0, 5, 0);
			gbc_lblDublObjectPol2.gridx = 4;
			gbc_lblDublObjectPol2.gridy = 4;
			contentPanel.add(lblDublObjectPol2, gbc_lblDublObjectPol2);

			JLabel lblPolNewObject = new JLabel("Полета на нов обект");
			GridBagConstraints gbc_lblPolNewObject = new GridBagConstraints();
			gbc_lblPolNewObject.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblPolNewObject.insets = new Insets(0, 0, 0, 5);
			gbc_lblPolNewObject.gridx = 1;
			gbc_lblPolNewObject.gridy = 5;
			contentPanel.add(lblPolNewObject, gbc_lblPolNewObject);

			textFieldNewObjectPol1 = new JTextField(pol1_NewObject);
			textFieldNewObjectPol1.setColumns(10);
			GridBagConstraints gbc_textFieldNewObjectPol1 = new GridBagConstraints();
			gbc_textFieldNewObjectPol1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldNewObjectPol1.anchor = GridBagConstraints.NORTH;
			gbc_textFieldNewObjectPol1.insets = new Insets(0, 0, 0, 5);
			gbc_textFieldNewObjectPol1.gridx = 2;
			gbc_textFieldNewObjectPol1.gridy = 5;
			contentPanel.add(textFieldNewObjectPol1, gbc_textFieldNewObjectPol1);

			textFieldNewObjectPol2 = new JTextField(pol2_NewObject);
			textFieldNewObjectPol2.setColumns(10);
			GridBagConstraints gbc_textFieldNewObjectPol2 = new GridBagConstraints();
			gbc_textFieldNewObjectPol2.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldNewObjectPol2.anchor = GridBagConstraints.NORTH;
			gbc_textFieldNewObjectPol2.gridx = 4;
			gbc_textFieldNewObjectPol2.gridy = 5;
			contentPanel.add(textFieldNewObjectPol2, gbc_textFieldNewObjectPol2);

			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton btnNewObjectSave = new JButton("запиши нов");
			buttonPane.add(btnNewObjectSave);
			getRootPane().setDefaultButton(btnNewObjectSave);
			btnNewObjectSave.setEnabled(false);
			
			btnNewObjectSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
			

			JButton cancelButton = new JButton("Пропускане");
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
			
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textFieldNewObjectPol1 = null; 
					textFieldNewObjectPol2 = null;
					setVisible(false);	
				}
			});

			textFieldNewObjectPol1.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent event) {
					if (textFieldNewObjectPol1.getText().equals(lblDublObjectPol1.getText())) {
						isDoublePole1 = true;
					} else {
						isDoublePole1 = false;
					}
					System.out.println("1--" + isDoublePole1 + "2--" + isDoublePole2);
					if (isDoublePole1 && isDoublePole2) {
						System.out.println("1-------------------------");
						btnNewObjectSave.setEnabled(false);
					} else {
						System.out.println("1************************************");
						btnNewObjectSave.setEnabled(true);
					}
				}

			});

			textFieldNewObjectPol2.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent event) {
				}

				@Override
				public void keyPressed(KeyEvent event) {
				}

				@Override
				public void keyReleased(KeyEvent event) {

					if (textFieldNewObjectPol2.getText().equals(lblDublObjectPol2.getText())) {
						isDoublePole2 = true;
					} else {
						isDoublePole2 = false;
					}

					System.out.println("1--" + isDoublePole1 + "2--" + isDoublePole2);
					if (isDoublePole1 && isDoublePole2) {
						System.out.println("2-------------------------");
						btnNewObjectSave.setEnabled(false);
					} else {
						System.out.println("2************************************");
						btnNewObjectSave.setEnabled(true);
					}
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static String[] get_DublicatePole() {

		String[] str = new String[2];
		try {
			if (textFieldNewObjectPol1 != null && textFieldNewObjectPol2 != null) {

				str[0] = textFieldNewObjectPol1.getText();
				str[1] = textFieldNewObjectPol2.getText();
			}else {
				str = null;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return str;
	}
}
