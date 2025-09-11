package DozeArt;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import PersonManagement.SelectSpisPrilFrame;

public class DozeArt_ReportFrame extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static int selectedContent = -1;
	static String DozeArt_ReportFrame_Label = ReadFileBGTextVariable.getGlobalTextVariableMap().get("DozeArt_ReportFrame_Label");
	
	public DozeArt_ReportFrame(JFrame parent, int[] coord, String report, int[] size, ActionIcone round) {
		super(parent, DozeArt_ReportFrame_Label, true);

		setBounds(coord[0], coord[1], size[0], size[1]);
		contentPane = new JPanel();

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		JTextArea list = new JTextArea(report);
		list.setFont(new Font("monospaced", Font.PLAIN, 12));
		scrollPane.setViewportView(list);
		
		round.StopWindow();
		setVisible(true);
	}

	

	

}