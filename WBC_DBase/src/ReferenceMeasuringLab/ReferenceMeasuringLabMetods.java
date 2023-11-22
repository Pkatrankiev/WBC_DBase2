package ReferenceMeasuringLab;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import Aplication.AplicationMetods;
import Aplication.GeneralMethods;
import Aplication.ReadFileBGTextVariable;

import BasiClassDAO.LaboratoryDAO;
import BasiClassDAO.MeasuringDAO;

import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;

import PersonReference.PersonReferenceExportToExcell;
import PersonReference.TextInAreaTextPanel;
import SearchFreeKode.infoFrame;


public class ReferenceMeasuringLabMetods {


	private static Object[][] dataTable;
	private static List<Laboratory> listLab = LaboratoryDAO.getAllValueLaboratory();
	private static Font fontBold;

	public static void addItemMounth(Choice comboBox_Firm) {
		String[] listMounths = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		addItem(comboBox_Firm, listMounths);
	}

	private static void addItem(Choice comboBox, String[] list) {
		comboBox.removeAll();
		for (String otdel : list) {
			comboBox.add(otdel);
		}
	}

	public static void ActionListenerbBtn_Search(JPanel panel_AllSaerch, JScrollPane scrollPane, ReferenceMeasuringLabFrame referenceMeasuringLabFrame) {

		JTextArea textArea = ReferenceMeasuringLabFrame.getTextArea();
		JButton btn_Search = ReferenceMeasuringLabFrame.getBtn_Search();
		JTextField textField_StartDate = ReferenceMeasuringLabFrame.getTextField_StartDate();
		JTextField textField_EndDate = ReferenceMeasuringLabFrame.getTextField_EndDate();
		JButton btn_Export = ReferenceMeasuringLabFrame.getBtn_Export();
		btn_Search.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				GeneralMethods.setWaitCursor(panel_AllSaerch);
				
				String startDate = textField_StartDate.getText().trim();
				String endDate = textField_EndDate.getText().trim();

				dataTable = MountlyreportMeasuring(startDate, endDate);
				int row = dataTable.length;
				System.out.println("*************" + dataTable.length);
				if (row < 2) {
					textArea.setText(ReadFileBGTextVariable.getGlobalTextVariableMap().get("notResults"));
					ReferenceMeasuringLabFrame.viewInfoPanel();
					btn_Export.setEnabled(false);
				} else {
					panel_infoPanelTablePanel(dataTable, panel_AllSaerch);
					ReferenceMeasuringLabFrame.viewTablePanel();
					int withR = 250+row*18;
					if(withR >1000) {
						withR = 1000;
					}
					referenceMeasuringLabFrame.setPreferredSize(new Dimension(900, withR));
					referenceMeasuringLabFrame.setMinimumSize(new Dimension(900, withR));
					referenceMeasuringLabFrame.repaint();
					btn_Export.setEnabled(true);
				}
				
				GeneralMethods.setDefaultCursor(panel_AllSaerch);
			}

		});

	}

	public static void ActionListenerComboBox_Mounth(Choice comboBox_Mount) {

		JTextField textField_Year = ReferenceMeasuringLabFrame.getTextField_Year();
		JTextField textField_StartDate = ReferenceMeasuringLabFrame.getTextField_StartDate();
		JTextField textField_EndDate = ReferenceMeasuringLabFrame.getTextField_EndDate();
		comboBox_Mount.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String mounth = comboBox_Mount.getSelectedItem();
					String startdate = "01." + mounth + "." + textField_Year.getText();
					textField_StartDate.setText(startdate);
					String endDate = getLastDAte(mounth + "." + textField_Year.getText());
					textField_EndDate.setText(endDate);
				}
			}
		});

	}

	public static void ActionListenerBtnExportToExcell(JPanel panel_Search) {
		JButton btn_Export = ReferenceMeasuringLabFrame.getBtn_Export();
		btn_Export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonReferenceExportToExcell.btnExportTableToExcell(dataTable,
						getTabHeaderForExcelFile(getTabHeader()), panel_Search);

			}

		});
	}

	public static String getLastDAte(String text) {

		for (int i = 25; i < 33; i++) {

			if (AplicationMetods.incorrectDate(i + "." + text)) {
				i--;
				return i + "." + text;
			}
		}
		return "";
	}

	private static void panel_infoPanelTablePanel(Object[][] dataTable, JPanel panel_AllSaerch) {

		JPanel tablePane = ReferenceMeasuringLabFrame.getTablePane();
		String[] columnNames = getTabHeader();

		DefaultTableModel model = new DefaultTableModel(dataTable, columnNames);
		JTable table = new JTable(model) {

			private static final long serialVersionUID = 1L;

			public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {

				Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
				int lastRow = getRowCount() - 1;
				if (Index_row % 2 == 0) {
					comp.setBackground(Color.lightGray);
				} else {
					comp.setBackground(Color.white);
				}

				Font font = getFont();
				fontBold = new Font(font.getFontName(), Font.BOLD, font.getSize());

				if (isCellSelected(Index_row, Index_col)) {
					comp.setBackground(Color.yellow);
				}
				comp.setForeground(Color.black);
				for (Laboratory lab : listLab) {
					int id = lab.getLab_ID();
					if (model.getColumnName(Index_col).contains(id + "") && id % 2 != 0) {
						comp.setForeground(Color.BLUE);
					}

				}

			
				
				if (Index_col == 0) {
					comp.setFont(fontBold);
				}

				if (Index_row == lastRow) {
					comp.setBackground(Color.GREEN);
					comp.setForeground(Color.black);
					comp.setFont(fontBold);
				}

				if(Index_col > 0) {
					String value = model.getValueAt(Index_row, Index_col).toString();
					if(checkIsDifferentOfDouble(value)){
						comp.setForeground(Color.RED);
					}
					}
				
				return comp;
			}
		};

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();

				int col = table.columnAtPoint(e.getPoint());
				int row = getSelectedModelRow(table);
				System.out.println(row+" ******* "+table.getRowCount());
				if (col >= 0 && row < table.getRowCount()-1 && row >= 0)  {
					String dateStr = model.getValueAt(row, col).toString();

					if (col == 1 && !dateStr.equals("0")) {
						dateStr = model.getValueAt(row, 0).toString();
					
						generateInfoPanel(1, dateStr);
						
					}

					if (col == 6 && !dateStr.equals("0")) {
						dateStr = model.getValueAt(row, 0).toString();
						
						generateInfoPanel(2, dateStr);
					}

					if (col == 11 && !dateStr.equals("0")) {
						dateStr = model.getValueAt(row, 0).toString();
						
						generateInfoPanel(3, dateStr);
					}

					if (col == 16 && !dateStr.equals("0")) {
						dateStr = model.getValueAt(row, 0).toString();
						
						generateInfoPanel(0, dateStr);
					}
				}
			}

			

		});

		JTableHeader header = table.getTableHeader();
		
		Border defaultBorder = UIManager.getBorder("TableHeader.cellBorder");
		
		final TableCellRenderer hr = table.getTableHeader().getDefaultRenderer();
		
		header.setDefaultRenderer(new TableCellRenderer() {
			private JLabel lbl;
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				
				lbl = (JLabel) hr.getTableCellRendererComponent(table, value, true, true, row, column);
				
				lbl.setUI(new VerticalLabelUI(false));
					
				lbl.setBackground(table.getBackground());
				lbl.setFont(fontBold);
				lbl.setHorizontalAlignment(SwingConstants.LEFT);
				lbl.setBorder(new CompoundBorder(defaultBorder, new EmptyBorder(3, 0, 0, 0)));
				for (Laboratory lab : listLab) {
					int id = lab.getLab_ID();
					if (value.toString().contains(id + "") && id % 2 != 0) {
						lbl = (JLabel) hr.getTableCellRendererComponent(table, value, true, true, row, column);
						lbl.setBackground(table.getBackground());
						lbl.setFont(fontBold);
						lbl.setBorder(new CompoundBorder(defaultBorder, new EmptyBorder(3, 0, 0, 0)));
						lbl.setForeground(Color.BLUE);
					}
				}

				return lbl;
			}
		});

		SwingUtilities.invokeLater(new Runnable() {

			@SuppressWarnings("serial")
			public void run() {
				DefaultTableModel dtm = new DefaultTableModel(dataTable, columnNames) {

					public boolean isCellEditable(int row, int column) {
						return false;
					}

					@Override
					public Object getValueAt(int row, int col) {
						return dataTable[row][col];
					}

					@SuppressWarnings("unused")
					public void setValueAt(String value, int row, int col) {

						if (!dataTable[row][col].equals(value)) {
							dataTable[row][col] = value;
							fireTableCellUpdated(row, col);

						}
					}

					public int getColumnCount() {
						return columnNames.length;
					}

					public int getRowCount() {
						return dataTable.length;
					}

				};

				dtm.fireTableDataChanged();
				table.setModel(dtm);

			
//		new TableFilterHeader(table, AutoChoices.ENABLED);
				header.setPreferredSize(new Dimension(100, 80));
				table.getColumnModel().getColumn(0).setPreferredWidth(150);
				table.setFillsViewportHeight(true);
				table.repaint();
				System.out.println("+++++++++++++ " + dataTable.length);
			}
		});

		table.getTableHeader().setReorderingAllowed(false);
		
		tablePane.removeAll();
		JScrollPane scrollPane = new JScrollPane(table);
		tablePane.add(scrollPane, BorderLayout.CENTER);

	}

	static boolean checkIsDifferentOfDouble(String value) {
		try {
			Integer.parseInt(value);	
		} catch (Exception e) {
			if(!value.equals("0.0"))
			return true;
		} 
		
		return false;
		
	}
	
	static String[] getTabHeader() {
		
		String brMeasur = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referenceMeasuringLab_BrIzmer");
		String brMeasurNadMDA = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referenceMeasuringLab_BrNadMDA");
		String MAXDoza = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referenceMeasuringLab_MAX_Doza");
		String MINDoza = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referenceMeasuringLab_MIN_Doza");
		String SUMDoza = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referenceMeasuringLab_SUM_Doza");
		String all = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referenceMeasuringLab_All");
		
		List<Laboratory> listLab = LaboratoryDAO.getAllValueLaboratory();
		int countlab = listLab.size();
		int index = (countlab * 5) + 6;
		String[] masive = new String[index];
		masive[0] = ReadFileBGTextVariable.getGlobalTextVariableMap().get("referencePerson_Date");
		int k = 1;
		for (int i = 0; i < listLab.size(); i++) {

			masive[k] = "<html>" + listLab.get(i).getLab() + "<br>" + brMeasur + "</html>";
			k++;
			masive[k] = "<html>" + listLab.get(i).getLab() + "<br>" + brMeasurNadMDA + "</html>";
			k++;
			masive[k] = "<html>" + listLab.get(i).getLab() + "<br>" + MAXDoza + "</html>";
			k++;
			masive[k] = "<html>" + listLab.get(i).getLab() + "<br>" + MINDoza + "</html>";
			k++;
			masive[k] = "<html>" + listLab.get(i).getLab() + "<br>" + SUMDoza + "</html>";
			k++;
		}
		masive[k] = "<html>" + all + "<br>" + brMeasur + "</html>";
		k++;
		masive[k] = "<html>" + all + "<br>" + brMeasurNadMDA + "</html>";
		k++;
		masive[k] = "<html>" + all + "<br>" + MAXDoza + "</html>";
		k++;
		masive[k] = "<html>" + all + "<br>" + MINDoza + "</html>";
		k++;
		masive[k] = "<html>" + all + "<br>" + SUMDoza + "</html>";

		return masive;
	}

	static String[] getTabHeaderForExcelFile(String[] masive) {
		for (int i = 0; i < masive.length; i++) {
			masive[i] = masive[i].replace("<html>", "").replace("<br>", "\n").replace("</html>", "");
		}

		return masive;

	}

	public static Object[][] MountlyreportMeasuring(String startDate, String endDate) {

		int countlab = listLab.size();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");

		int brMeasur = 0;
		int brNadMDA = 0;
		double doza = 0;
		double dozaMAX = 0;
		double dozaMIN = 0;
		double dozaSUM = 0;

		String type = "";
		int allBrMeasur = 0;
		int allBrNadMDA = 0;
		double allDozaMAX = 0, allDozaMIN = 0, allDozaSUM = 0;

		int globBrMeasur[] = new int[countlab];
		int globBrNadMDA[] = new int[countlab];
		double globDozaMAX[] = new double[countlab];
		;
		double globDozaMIN[] = new double[countlab];
		;
		double globDozaSUM[] = new double[countlab];

		int index = (countlab * 5) + 6;
		
		List<Object[]> listMasive = new ArrayList<Object[]>();

		Date dateStart = null, dateEnd = null, date = null;
		try {
			dateStart = sdfrmt.parse(startDate);
			dateEnd = sdfrmt.parse(endDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Date dateEnd2 = getnexDate(dateEnd);
		int indexMasive = 0;
		date = dateStart;
		do {
			Object[] masive = new Object[index];
			indexMasive = 0;
			masive[indexMasive] = sdfrmt.format(date);

			allDozaMAX = 0;
			allDozaMIN = 0;
			allDozaSUM = 0;
			allBrMeasur = 0;
			allBrNadMDA = 0;
			for (int i = 0; i < countlab; i++) {

				brNadMDA = 0;
				brMeasur = 0;
				doza = 0;
				dozaMAX = 0;
				dozaMIN = 0;
				dozaSUM = 0;

				List<Measuring> listmeasur = MeasuringDAO.getValueMeasuringByLab_Date(listLab.get(i).getLab_ID(), date);
				brMeasur = listmeasur.size();
				if (brMeasur > 0) {
					for (Measuring measuring : listmeasur) {

						doza = measuring.getDoze();
						type = measuring.getTypeMeasur().getKodeType();
						System.out.println(doza +" - "+ type);
						if (doza > 0 || type.equals("M") ) {
							brNadMDA++;
						}
						
						
						if (doza > 0) {
							dozaSUM += doza;
								allDozaSUM += doza;
								globDozaSUM[i] += doza;
							

							if (doza > dozaMAX) {
								dozaMAX = doza;
							}
							if (doza > allDozaMAX) {
								allDozaMAX = doza;
							}
							if (doza > globDozaMAX[i]) {
								globDozaMAX[i] = doza;
							}

							if (doza < dozaMIN) {
								dozaMIN = doza;
							}
							if (doza < allDozaMIN) {
								allDozaMIN = doza;
							}

							if (doza < globDozaMIN[i]) {
								globDozaMIN[i] = doza;
							}

						}
					}
				}
				allBrMeasur += brMeasur;
				globBrMeasur[i] += brMeasur;

				allBrNadMDA += brNadMDA;
				globBrNadMDA[i] += brNadMDA;

				indexMasive++;
				masive[indexMasive] = brMeasur;
				indexMasive++;
				masive[indexMasive] = brNadMDA;
				indexMasive++;
				masive[indexMasive] = dozaMAX;
				indexMasive++;
				masive[indexMasive] = dozaMIN;
				indexMasive++;
				masive[indexMasive] = dozaSUM;
			}

			if (allBrMeasur > 0) {
				indexMasive++;
				masive[indexMasive] = allBrMeasur;
				indexMasive++;
				masive[indexMasive] = allBrNadMDA;
				indexMasive++;
				masive[indexMasive] = allDozaMAX;
				indexMasive++;
				masive[indexMasive] = allDozaMIN;
				indexMasive++;
				masive[indexMasive] = allDozaSUM;

				listMasive.add(masive);
			}
			date = getnexDate(date);

		} while (date.before(dateEnd2));

		Object[] masive = new Object[index];
		indexMasive = 0;

		allBrMeasur = 0;
		allBrNadMDA = 0;
		allDozaMAX = 0;
		allDozaMIN = 0;
		allDozaSUM = 0;

		masive[0] = "Общо: ";
		for (int i = 0; i < countlab; i++) {

			indexMasive++;
			masive[indexMasive] = globBrMeasur[i];
			indexMasive++;
			masive[indexMasive] = globBrNadMDA[i];
			indexMasive++;
			masive[indexMasive] = globDozaMAX[i];
			indexMasive++;
			masive[indexMasive] = globDozaMIN[i];
			indexMasive++;
			masive[indexMasive] = globDozaSUM[i];

			allBrMeasur += globBrMeasur[i];
			allBrNadMDA += globBrNadMDA[i];
			if (globDozaSUM[i] > 0.0) {
				allDozaSUM += globDozaSUM[i];
			}
			if (globDozaMAX[i] > allDozaMAX) {
				allDozaMAX = globDozaMAX[i];
			}

			if (globDozaMIN[i] < allDozaMIN) {
				allDozaMIN = globDozaMIN[i];
			}

		}

		indexMasive++;
		masive[indexMasive] = allBrMeasur;
		indexMasive++;
		masive[indexMasive] = allBrNadMDA;
		indexMasive++;
		masive[indexMasive] = allDozaMAX;
		indexMasive++;
		masive[indexMasive] = allDozaMIN;
		indexMasive++;
		masive[indexMasive] = allDozaSUM;

		listMasive.add(masive);

		Object[][] allMasive = new Object[listMasive.size()][index];
		int k = 0;
		NumberFormat format = new DecimalFormat("#0.00");
		for (Object[] objects : listMasive) {

			for (int i = 0; i < objects.length; i++) {
				allMasive[k][i] = objects[i];
				if (objects[i].getClass().getName().equals("java.lang.Double")) {
					
					if ((double) objects[i] > 0.0 && (double) objects[i] < 0.1) {
						allMasive[k][i] = "<0.10";
					}
					if ((double) objects[i] > 0.1) {
						allMasive[k][i] =  format.format(objects[i]);
						}
				}

			}
			k++;

		}
		return allMasive;

	}

	private static Date getnexDate(Date dateEnd) {
		Calendar today = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        
        Date date = null;
		try {
			date = format.parse(format.format(dateEnd));
			today.setTime(date);
	        today.add(Calendar.DAY_OF_YEAR, 1);
	        date = format.parse(format.format(today.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        
		return date;
	}

	public static void checkorektDate(JTextField textFieldDate) {
		
		JButton btn_Search = ReferenceMeasuringLabFrame.getBtn_Search();
		textFieldDate.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {

			}

			@Override
			public void keyReleased(KeyEvent event) {

				if (AplicationMetods.incorrectDate(textFieldDate.getText())
						&& !textFieldDate.getText().trim().isEmpty()) {
					btn_Search.setEnabled(false);
					textFieldDate.setForeground(Color.RED);
				} else {
					btn_Search.setEnabled(true);
					textFieldDate.setForeground(Color.BLACK);

				}
			}

			@Override
			public void keyPressed(KeyEvent event) {

			}
		});
	}

	private static void generateInfoPanel(int i, String dateStr) {
		JFrame parent = new JFrame();
		int[] sizeInfoFrame = { 550, 300 };
		int[] Coord = AplicationMetods.getCurentKoordinates(sizeInfoFrame);
		String textForInfoFrame ="";
		if(i==0) {
			textForInfoFrame = generateTextForInfoFrame( 1, dateStr)+"\n";	
			textForInfoFrame += generateTextForInfoFrame( 2, dateStr)+"\n";
			textForInfoFrame += generateTextForInfoFrame( 3, dateStr);
		}else {
				textForInfoFrame = generateTextForInfoFrame( i, dateStr);
		}
				System.out.println(textForInfoFrame);
				new infoFrame(parent, Coord, textForInfoFrame, sizeInfoFrame, null);

		
	}
	
	private static String generateTextForInfoFrame(int i, String dateString) {
		
		 SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		 Date date;
		 List<Measuring> list = new ArrayList<>();
		try {
			date = sdf.parse(dateString);
			list = MeasuringDAO.getValueMeasuringByDateLab(date,  i);
				System.out.println(list.size());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list.size()==0) return ""; 
		String[][] masiveData = new String[list.size()][6];
		int k=0;
		for (Measuring measuring : list) {
			masiveData[k][0]= " "+(k+1); 
			masiveData[k][1]= measuring.getPerson().getEgn();
			masiveData[k][2]= measuring.getPerson().getFirstName()+" "+measuring.getPerson().getSecondName()+" "+measuring.getPerson().getLastName();
			masiveData[k][3]= measuring.getDoze()+"";
			masiveData[k][4]= measuring.getTypeMeasur().getKodeType();
			masiveData[k][5]= measuring.getLab().getLab();
			k++;
		}
		
		
		return generateTextWithSpases(masiveData);
	}
	
	private static String generateTextWithSpases(String[][] masiveData) {
		String kodeString = "";
		String[] masiveZoneName = {" N","ЕГН", "Име", "Доза[mSv]", "Тип", "Лаб."};		
		int[] max = {4, 10, 20, 11, 5, 5};
		
		int[] columnSize = TextInAreaTextPanel.getMaxSizecolumn(masiveData, max);
		
		for (int i = 0; i < masiveZoneName.length; i++) {
			kodeString += masiveZoneName[i] + TextInAreaTextPanel.getAddSpace(columnSize[i], masiveZoneName[i]);
		}
		kodeString += "\n";	
		
		for (int i = 0; i < masiveData.length; i++) {

			for (int j = 0; j < masiveZoneName.length; j++) {
			kodeString += masiveData[i][j] + TextInAreaTextPanel.getAddSpace(columnSize[j], masiveData[i][j]) ;
		}
			kodeString += "\n";

		}
		return kodeString;
	}
	
	private static int getSelectedModelRow(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}
	
}
