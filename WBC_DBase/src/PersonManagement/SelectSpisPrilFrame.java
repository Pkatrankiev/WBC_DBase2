package PersonManagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import Aplication.ActionIcone;
import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;
import BasiClassDAO.PersonDAO;
import BasicClassAccessDbase.Person;
import PersonReference.TextInAreaTextPanel;

import javax.swing.JList;
import java.awt.event.MouseAdapter;

public class SelectSpisPrilFrame extends JDialog {

	private JPanel contentPane;
	static int selectedContent = -1;

	public SelectSpisPrilFrame(JFrame parent, int[] coord, String [] masiveSipisPril, int[] size, int maxFormulyarName, ActionIcone round) {
		super(parent, "", true);
		
//		setBounds(coord[0], coord[1], 406, 176);
		setBounds(coord[0], coord[1], size[0], size[1]);
		contentPane = new JPanel();

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JList list = new JList(masiveSipisPril);
		list.setFont(new Font("monospaced", Font.PLAIN, 12));
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					list.getSelectedValue();
					System.out.println(list.getSelectedIndex());
					setSelectedContent(list.getSelectedIndex());
					dispose();
				}
			}
		});
		scrollPane.setViewportView(list);
		
		round.StopWindow();
		setVisible(true);
	}

	public static int getSelectedContent() {
		return selectedContent;
	}

	public static void setSelectedContent(int selectedContent) {
		SelectSpisPrilFrame.selectedContent = selectedContent;
	}

}
