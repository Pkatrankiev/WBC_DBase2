package ResultFromMeasuringReference;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import Aplication.GeneralMethods;
import BasicClassAccessDbase.Measuring;

public class ProgresBarByResultFromMeasuringReference extends SwingWorker<Object[][], Double>{
    private final JProgressBar fProgressBar;
    private JTextArea ftextArea;
	private List<String> listNameNuclide;
	private  JTextField ftextFieldStartDate;
	private  JTextField ftextFieldEndDate;
	private JPanel finfoPanel;
	private JPanel ftablePane;
		private  JLayeredPane fpanel_AllSaerch;
	private  JScrollPane fscrollPane;
	private JButton fbtnBackToTable;
   

	public ProgresBarByResultFromMeasuringReference(JTextArea textArea, JPanel infoPanel, JPanel tablePane,
			JLayeredPane panel_AllSaerch, JScrollPane scrollPane, JTextField textFieldStartDate,
			JTextField textFieldEndDate, JButton btnBackToTable, JProgressBar aProgressBar) {
		   fProgressBar = aProgressBar;
		   finfoPanel = infoPanel;
		   ftablePane =  tablePane;
		   fpanel_AllSaerch = panel_AllSaerch;
		   fscrollPane = scrollPane;
		  fbtnBackToTable = btnBackToTable;
		   ftextArea = textArea;
		   ftextFieldStartDate = textFieldStartDate;
		   ftextFieldEndDate = textFieldEndDate;
		 
		       System.out.println("searchMeasurs");
	}


	@Override
    protected Object[][] doInBackground() throws Exception {
		 Object[][] dataTable = null;;
		  
    	System.out.println("searchFromDBase1");
    	
		ResultFromMeasuringReferenceFrame.viewInfoPanel();
		String StrStartDate = ftextFieldStartDate.getText();
		String StrEndDate = ftextFieldEndDate.getText();
		System.out.println("22222222222222222222222222222");
		if (Metods_ResultFromMeasuringReference.checkDateFieldsStartBeForeEnd(ftextFieldEndDate, ftextFieldStartDate)) {
 
			GeneralMethods.setWaitCursor(finfoPanel);
			System.out.println("333333333333333333333333333333333");
			double stepForProgressBar = 90;
			List<Measuring> listMeasur = Metods_ResultFromMeasuringReference.getListMeasuring(StrStartDate, StrEndDate, fProgressBar, stepForProgressBar);
			System.out.println("listMeasur.size "+listMeasur.size());
			if (listMeasur.size() > 1) {
				stepForProgressBar = 10;
				listNameNuclide = Metods_ResultFromMeasuringReference.getListNuclide(listMeasur, fProgressBar, stepForProgressBar);
				System.out.println("listNameNuclide.size "+listNameNuclide.size());
				dataTable = Metods_ResultFromMeasuringReference.addListMeasur( listMeasur, listNameNuclide);
				
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
    		Metods_ResultFromMeasuringReference.setInfo(ftextArea, finfoPanel, ftablePane,
    				fpanel_AllSaerch,fscrollPane, fbtnBackToTable, get(), listNameNuclide);
    				
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    	
    }
    
  
    
    
}  
    
  