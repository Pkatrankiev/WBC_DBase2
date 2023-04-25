package Aplication;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;



public class ActionIcone {

	private JWindow frame;
	private JLabel lab ;
	public ActionIcone() {
		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("destinationIcons");
		frame = new JWindow();
				frame.setVisible(true);
				frame.setAlwaysOnTop(true);
				frame.setBackground(new Color(0, 0, 0, 0));
				JPanel pan = new JPanel();
				pan.setOpaque(false);
				frame.setContentPane(pan);
				ImageIcon pic = new ImageIcon(getClass().getClassLoader().getResource(iconn));
				frame.getContentPane().add(new JLabel(pic));
				frame.pack();
				frame.setLocationRelativeTo(null);

			}

	public ActionIcone(String str) {
		String iconn = ReadFileBGTextVariable.getGlobalTextVariableMap().get("destinationIcons");
		frame = new JWindow();
//		frame.setMinimumSize(new Dimension(100, 100));
		frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
				frame.setVisible(true);
				frame.setAlwaysOnTop(true);
				frame.setBackground(new Color(0, 0, 0, 0));
				JPanel pan = new JPanel();
				pan.setOpaque(false);
				frame.setContentPane(pan);
				ImageIcon pic = new ImageIcon(getClass().getClassLoader().getResource(iconn));
				lab = new JLabel();
				lab.setFont(new Font("Verdana", Font.BOLD, 13));
				lab.setText(str);
				lab.setIcon(pic);
				
				lab.setHorizontalTextPosition(JLabel.CENTER);
			    lab.setVerticalTextPosition(JLabel.CENTER);
				frame.getContentPane().add(lab);
				frame.pack();
				frame.setLocationRelativeTo(null);

			}
	
	
	public JLabel getLab() {
		return lab;
	}

	public void setLab(JLabel lab) {
		this.lab = lab;
	}

	public void StopWindow() {
		SwingUtilities.getWindowAncestor(frame).dispose();
	
		}
	
	public void setTextToImage(String str) {
		getLab().setText(str);
	
		}
	
}
