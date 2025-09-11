package ReferenceKontrolPersonByPeriod;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;


public class ProgresBarByReferenceKontrolPersonByPeriod extends SwingWorker<Object[][], Double>{
    private final JProgressBar fProgressBar;

    private JTextArea ftextArea;
	private String fnameMethod;

	private List<String> listAdd;
   
	ProgresBarByReferenceKontrolPersonByPeriod(List<String> listAdd_a, JTextArea textArea, JProgressBar aProgressBar, String nameMethod) {
      fProgressBar = aProgressBar;
      fnameMethod = nameMethod;
      ftextArea = textArea;
      listAdd = listAdd_a;
      System.out.println("searchFromDBase11");
    }


	   @Override
    protected Object[][] doInBackground() throws Exception {
		   Object[][] dataTable = null;;
		   try {
    	System.out.println("searchFromDBase1");
    	
    	switch (fnameMethod) {
		
		case "searchFromDBase": {
			System.out.println("searchFromDBase2");
			dataTable = Metods_ReferenceKontrolPersonByPeriod.searchFromDBase(fProgressBar, listAdd);
		}
			break;
		case "searchFromExcelFile": {
			dataTable = Metods_ReferenceKontrolPersonByPeriod.searchFromExcelFile(fProgressBar);
			
		}
			break;
    	}	
		   } catch (Exception e) {
				e.printStackTrace();
			// TODO: handle exception
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
    		ReferenceKontrolPersonByPeroid_Frame.setTextToArea(ftextArea, get());
    				
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
       
    
  }
