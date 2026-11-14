package InstanceMenagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;


public class InfoFrame_Instance extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private CustomLabel lbl_text;
	
	public InfoFrame_Instance(String[] info, String[] masiveText, String command, String tableName, String strMAX) {
				
		setTitle(tableName+" "+command);

		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel();
				topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
				
				JPanel panel = new JPanel();
				topPanel.add(panel);
				
				JPanel panelText = textPanel(masiveText);
				topPanel.add(panelText);
				
				int maxSize = getMaxWith(strMAX+" ");
				System.out.println("maxSize "+maxSize);
				
				JPanel panel_Info =	InfoPanel(info, maxSize, null);
				topPanel.add(panel_Info);
		
		
		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		topPanel.add(panel_1);
		
		setSize(120+maxSize+30, 400);
		setMinimumSize(new Dimension(120+maxSize+30, 400));
		
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public InfoFrame_Instance(String[][] masive, String[] masiveText, String command, String strMAX1,  String strMAX2, String tableName, int[] masiveDiferents) {
		setTitle(tableName+" "+command);

		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel();
				topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
				
				JPanel panel = new JPanel();
				topPanel.add(panel);
				
				JPanel panelText = textPanel(masiveText);
				topPanel.add(panelText);
				
				int maxSize1 = getMaxWith(strMAX1+" ");
				int maxSize2 = getMaxWith(strMAX2+" ");
				System.out.println("maxSize "+maxSize1+" "+maxSize2);
				
				JPanel panel_Info =	InfoPanel(masive[0], maxSize1, masiveDiferents);
				topPanel.add(panel_Info);
		
				
				JPanel panel2 = MidPanel(masive[0], 50);;
				topPanel.add(panel2);
				
				JPanel panel_Info2 =	InfoPanel(masive[1], maxSize2, masiveDiferents);
				topPanel.add(panel_Info2);
		
		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		topPanel.add(panel_1);
		
		setSize(120+maxSize1+maxSize2+80, 400);
		setMinimumSize(new Dimension(120+maxSize1+maxSize2+80, 400));
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	private JPanel MidPanel(String[] info, int maxSize) {
		
			int midRow = info.length/2;
			for (int l = 0; l < info.length; l++) {
				info[l] = "";
				if(l == midRow) {
					info[l] = " --> ";
				}
			}
			
			JPanel panel_Info = new JPanel();
			
			panel_Info.setLayout(new BoxLayout(panel_Info, BoxLayout.Y_AXIS));
			
			for (int i = 0; i < info.length; i++) {
				JLabel lblInfo = new CustomLabelInfo(info[i], maxSize);

				panel_Info.add(lblInfo);
			}
				return panel_Info;
		}

	private JPanel textPanel(String[] masiveText) {
		JPanel panelText = new JPanel();
		
		
				panelText.setLayout(new BoxLayout(panelText, BoxLayout.Y_AXIS));
		
				for (int i = 0; i < masiveText.length; i++) {
					lbl_text = new CustomLabel(masiveText[i]);
					panelText.add(lbl_text);
				}
			
				
		return panelText;
	}

	private JPanel InfoPanel(String[] info, int maxSize, int[] masDif) {
			
		JPanel panel_Info = new JPanel();
		
		panel_Info.setLayout(new BoxLayout(panel_Info, BoxLayout.Y_AXIS));
		
		for (int i = 0; i < info.length; i++) {
			JLabel lblInfo = new CustomLabelInfo(info[i], maxSize);
//			lblInfo.setForeground(Color.BLACK);
			if(masDif != null && masDif[i] > 0) {
				lblInfo.setForeground(Color.RED);
			}
			panel_Info.add(lblInfo);
		}
			return panel_Info;
	}

	private int getMaxWith(String strMAX) {
		FontMetrics fm = lbl_text.getFontMetrics(lbl_text.getFont());
		int width = fm.stringWidth(strMAX);
		return width;
	}
	
	
	public class CustomLabel extends JLabel {
		private static final long serialVersionUID = 1L;
	    public CustomLabel(String text) {
	        super(text);
	        setMinimumSize(new Dimension(120, 20));
	        setMaximumSize(new Dimension(120, 20));
			setPreferredSize(new Dimension(120, 20));
			setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
	      
	    }
	}
	
	public class CustomLabelInfo extends JLabel {

		private static final long serialVersionUID = 1L;

		public CustomLabelInfo(String text, int max) {
	        super(text);
	        setMinimumSize(new Dimension(max, 20));
	        setMaximumSize(new Dimension(max, 20));
			setPreferredSize(new Dimension(max, 20));
			setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
	      
	    }
	}	
	
}



