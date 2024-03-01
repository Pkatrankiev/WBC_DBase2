package UpdateDBaseFromExcelFiles;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import javax.swing.SwingConstants;

import Aplication.ActionIcone;
import Aplication.AplicationMetods;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EtchedBorder;

public class CustomUpdate extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String[] key = { 
			"Person", 
			"Spisak_Prilogenia", 
			"PersonStatus", 
			"KodeStatus", 
			"Measuring", 
			"ResultsWBC", 
			"ObhodenList"
			};
	public String[] getKey() {
		return key;
	}

	public CustomUpdate(String title) {
		setMinimumSize(new Dimension(350, 350));
		
		setTitle(title);
		setPreferredSize(new Dimension(300, 350));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		contentPane.add(scrollPane, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		setHeatPanel(panel);
		JCheckBox[] checkBox = new JCheckBox[key.length];
		for (int i = 0; i < key.length; i++) {
			checkBox[i] = new JCheckBox();
			setOblastPanel(panel, key[i], checkBox[i]);
		}

		JPanel oblastButonPanel = new JPanel();
		oblastButonPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.add(oblastButonPanel);
		oblastButonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton printButton = new JButton("UpDate");
		printButton.setHorizontalAlignment(SwingConstants.LEFT);
//		printButton.setPreferredSize(new Dimension(70, 15));
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> list = new ArrayList<>();
				System.out.println("-------------");
				for (int j = 0; j < key.length; j++) {
					System.out.println(checkBox[j].isSelected());
					if (checkBox[j].isSelected()) {
						System.out.println("key[j] "+key[j]);
						list.add(key[j]);
					}
				}
				String[] masiveOblast = new String[list.size()];
				System.out.println("*************************"+list.size());
				for (int j = 0; j < list.size(); j++) {
					masiveOblast[j] = list.get(j);
				}
				List<String> listChengeExcellFilePath = new ArrayList<>();
				String[] excellFiles = AplicationMetods.getDataBaseFilePat_OriginalPersonalAndExternal();
				for (String file : excellFiles) {
					System.out.println("file "+file);
					listChengeExcellFilePath.add(file);
				}
				ActionIcone round2 = new ActionIcone("                                "
						+ "                                              ");
				 final Thread thread = new Thread(new Runnable() {
					
				     @Override
				     public void run() {
				    	
				    	 dispose();
				    	 UpdateBDataFromExcellFiles.extracted(round2, listChengeExcellFilePath, null, masiveOblast);
						
				    	 
				     }
				  
				    });
				    thread.start();
			}
				
				

		});
		oblastButonPanel.add(printButton);
		
		
		setVisible(true);
	}

	private void setHeatPanel(JPanel panel) {
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.add(panel_1_1);
		panel_1_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblNewLabel_5 = new JLabel("Област");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setPreferredSize(new Dimension(90, 14));
		lblNewLabel_5.setMaximumSize(new Dimension(80, 14));
		lblNewLabel_5.setMinimumSize(new Dimension(80, 14));
		panel_1_1.add(lblNewLabel_5);
	}

	private void setOblastPanel(JPanel panel, String oblast, JCheckBox checkBox) {
		JPanel oblastPanel = new JPanel();
		oblastPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.add(oblastPanel);
		oblastPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel oblastLabel = new JLabel();
		
			oblastLabel = new JLabel(oblast.trim());
			oblastLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			oblastLabel.setPreferredSize(new Dimension(90, 14));
			oblastPanel.add(oblastLabel);

			checkBox.setMargin(new Insets(0, 0, 0, 3));
			checkBox.setSelected(true);
			oblastPanel.add(checkBox);
			
	}

}
