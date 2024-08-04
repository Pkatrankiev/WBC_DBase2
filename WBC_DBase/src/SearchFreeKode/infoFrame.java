package SearchFreeKode;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.border.LineBorder;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;

import java.awt.Color;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class infoFrame extends JDialog {

	private JPanel contentPane;

	public infoFrame(JFrame parent, int[] coord, String text, int[] size, ActionIcone round) {
		super(parent, "", true);
		setUndecorated(true);
		
		String zaDaZatvoriteRamkata = ReadFileBGTextVariable.getGlobalTextVariableMap().get("zaDaZatvoriteRamkata") + "\n\n";
		if(coord != null) {
		setBounds(coord[0], coord[1], size[0], size[1]);
		}else {
			setSize(size[0], size[1]);
			setLocationRelativeTo(null);
		}
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea(zaDaZatvoriteRamkata + text);
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		textArea.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(textArea.getCaretPosition()<50) {;
				dispose();
				}
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
		setVisible(true);
	}

}
