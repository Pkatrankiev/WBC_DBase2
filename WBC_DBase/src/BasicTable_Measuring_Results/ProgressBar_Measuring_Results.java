package BasicTable_Measuring_Results;

import java.awt.Choice;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.text.JTextComponent;

import Aplication.GeneralMethods;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.Workplace;




public class ProgressBar_Measuring_Results extends SwingWorker<Object[][], Double>{
    private final JProgressBar fProgressBar;
    private JTextArea ftextArea;
	private List<String> listNameNuclide;
	private  Person personn;
	private  JTextField ftextFieldStartDate;
	private  JTextField ftextFieldEndDate;
	private JPanel finfoPanel;
	private JPanel ftablePane;
		private  JLayeredPane fpanel_AllSaerch;
	private  JScrollPane fscrollPane;
	private JButton fbtnBackToTable;
   

	public ProgressBar_Measuring_Results(JTextArea textArea, JPanel infoPanel, JPanel tablePane,
			JLayeredPane panel_AllSaerch, JScrollPane scrollPane, Person  person, JTextField textFieldStartDate,
			JTextField textFieldEndDate, JButton btnBackToTable, JProgressBar aProgressBar) {
		   fProgressBar = aProgressBar;
		   finfoPanel = infoPanel;
		   ftablePane =  tablePane;
		   fpanel_AllSaerch = panel_AllSaerch;
		   fscrollPane = scrollPane;
		  fbtnBackToTable = btnBackToTable;
		   ftextArea = textArea;
		   personn = person;
		   ftextFieldStartDate = textFieldStartDate;
		   ftextFieldEndDate = textFieldEndDate;
		 
		       System.out.println("searchMeasurs");
	}


	@Override
    protected Object[][] doInBackground() throws Exception {
		 Object[][] dataTable = null;;
		  
    	System.out.println("searchFromDBase1");
    	
		Measuring_Results_Frame.viewInfoPanel();
		String StrStartDate = ftextFieldStartDate.getText();
		String StrEndDate = ftextFieldEndDate.getText();
		
		System.out.println("22222222222222222222222222222");
		if (Measuring_Results_Metods.checkDateFieldsStartBeForeEnd(ftextFieldEndDate, ftextFieldStartDate) ) {
 
			GeneralMethods.setWaitCursor(finfoPanel);
			System.out.println("333333333333333333333333333333333");
			double stepForProgressBar = 90;
			List<Measuring> listMeasur = Measuring_Results_Metods.getListMeasuring(personn, StrStartDate, StrEndDate, fProgressBar, stepForProgressBar);
			System.out.println("listMeasur.size "+listMeasur.size());
			if (listMeasur.size() > 0) {
				stepForProgressBar = 10;
				listNameNuclide = Measuring_Results_Metods.getListNuclide(listMeasur, fProgressBar, stepForProgressBar);
				System.out.println("listNameNuclide.size "+listNameNuclide.size());
				dataTable = Measuring_Results_Metods.addListMeasur( listMeasur, listNameNuclide);
				
			}

			GeneralMethods.setDefaultCursor(finfoPanel);
		}
		
		
 		
			return dataTable;
    }
	   
    @Override
    protected void process( List<Double> aDoubles ) {
      //update the percentage of the progress bar that is done
      fProgressBar.setValue( ( int ) (fProgressBar.getMinimum() + (aDoubles.get( aDoubles.size() - 1 ))) );
    }

    @Override
    protected void done() {
   
    	try {
    		fProgressBar.setValue(100);
    		Measuring_Results_Metods.setInfo(ftextArea, finfoPanel, ftablePane,
    				fpanel_AllSaerch,fscrollPane, fbtnBackToTable, get(), listNameNuclide);
    				
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    	
    }
    

	
	
}
