package WBCUsersLogin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import Aplication.ResourceLoader;
import BasiClassDAO.UsersWBCDAO;
import BasicClassAccessDbase.UsersWBC;


public class WBCUsersLogin extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JButton clearButton;
	private JButton exitButton;
	private static JTextField txt_nik_name;
	private static JPasswordField passwordField;
	private JLabel lbl_Username;
	private JLabel lbl_Password;
	private static boolean succeeded = false;
	private static UsersWBC curentUser = null;
	private List<UsersWBC> users_list = null;

	public WBCUsersLogin(Frame parent, String frameName, ActionIcone round) {
		
		super(parent, frameName, true);
		String pass = ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("LoginFrame_pass");
		users_list = UsersWBCDAO.getValueUsersWBCByActing();
//		pro.StopWindow();
		setBounds(100, 100, 272, 145);
		getContentPane().setLayout(new BorderLayout());
		// центрира рамката (центъра на текущия монитор)
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		lbl_Username = new JLabel(ReadFileBGTextVariable.getGlobalTextVariableMap()
				.get("LoginFrame_UserName"));
		lbl_Username.setBounds(10, 15, 109, 14);
		contentPanel.add(lbl_Username);
		{
			txt_nik_name = new JTextField();
			txt_nik_name.setHorizontalAlignment(SwingConstants.LEFT);
			txt_nik_name.setToolTipText("nik-name");
			txt_nik_name.setBounds(129, 12, 115, 20);
			contentPanel.add(txt_nik_name);
			txt_nik_name.setColumns(10);
			ArrayList<String> words = new ArrayList<>();
			for (UsersWBC user : users_list) {
				words.add(user.getNikName());
			}

			new AutoSuggestor(txt_nik_name, this, words, Color.WHITE.brighter(),
					Color.BLUE, Color.RED, 0.99f);

			{
				passwordField = new JPasswordField();
				passwordField.setBounds(129, 43, 115, 20);
				contentPanel.add(passwordField);
			}

			lbl_Password = new JLabel(pass);
			lbl_Password.setBounds(10, 46, 109, 14);
			contentPanel.add(lbl_Password);

			{
				JPanel buttonPane = new JPanel();
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				// buttonPane.setFocusTraversalPolicy(new
				// FocusTraversalOnArray(new
				// Component[] { okButton, cancelButton }));

				{
					okButton = new JButton("OK");
					okButton.setActionCommand("OK");
					getRootPane().setDefaultButton(okButton);

					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							okButton();

						}

					});

				}

				{
					clearButton = new JButton("Clear");
					clearButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							passwordField.setText("");
							txt_nik_name.setText("");
						}
					});

				}
				
				{
					exitButton = new JButton("Exit");
					exitButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							logOut();
							dispose();
							
						}
					});

				}
				
				buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				buttonPane.add(okButton);
				buttonPane.add(clearButton);
				buttonPane.add(exitButton);
			}
			this.setDefaultCloseOperation(WBCUsersLogin.DO_NOTHING_ON_CLOSE);
			this.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosing(java.awt.event.WindowEvent e) {
					logOut();
					e.getWindow().dispose();

				}
			});
		}
		
		round.StopWindow();
		
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	public String getUsername() {
		return txt_nik_name.getText().trim();
	}

	public static  UsersWBC getCurentUser() {
		return curentUser;
	}

	private void okButton() {

		char[] pass = passwordField.getPassword();

		String enter_pass = "";
		for (char x : pass) {
			enter_pass += x;
		}

		// String md5_encrypted_pass_userInput =
		// encrypt(final_pass); //kriptirane na string v MD5
		// format
		System.out.println(enter_pass + " "+ getUsername());
		System.out.println(users_list.size());
		for (UsersWBC user : users_list) {
			System.out.println(user.getPass() + " "+ user.getNikName());
			if (enter_pass.equals(user.getPass()) && getUsername().equals(user.getNikName())) {
				
				succeeded = true;
				curentUser = user;
			}
		}
		if (succeeded) {
			dispose();
		} else {
			JOptionPane.showMessageDialog(WBCUsersLogin.this, "Invalid username or password", "Login",
					JOptionPane.ERROR_MESSAGE);
			// reset username and password
//			passwordField.setText("");
//			txt_nik_name.setText("");

		}
	}

	public static void logOut() {
		passwordField.setText("");
		txt_nik_name.setText("");
		succeeded = false;
		curentUser = null;
	}

	public static final String encrypt(String md5) { // kriptirane na string v
														// MD5 format
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			ResourceLoader.appendToFile(e);
		}
		return null;
	}
} 
