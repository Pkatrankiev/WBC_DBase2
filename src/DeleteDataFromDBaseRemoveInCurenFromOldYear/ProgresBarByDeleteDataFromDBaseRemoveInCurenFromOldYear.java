package DeleteDataFromDBaseRemoveInCurenFromOldYear;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;


public class ProgresBarByDeleteDataFromDBaseRemoveInCurenFromOldYear extends SwingWorker<String, Double>{
    private final JProgressBar fProgressBar;
	private JTextArea ftextArea;
	private JPanel fpanel_AllSaerch;
	private String fnameMethod;
	private String year;
   
	ProgresBarByDeleteDataFromDBaseRemoveInCurenFromOldYear( JProgressBar aProgressBar, JTextArea textArea, JPanel panel_AllSaerch, String nameMethod, String year1) {
      fProgressBar = aProgressBar;
      ftextArea = textArea;
      fpanel_AllSaerch = panel_AllSaerch;
      fnameMethod = nameMethod;
      year = year1;
    }

    @Override
    protected String doInBackground() throws Exception {
    	
    	String textForArea = "";
    	switch (fnameMethod) {
		
		case "deleteSpisakPrilojenia": {
			
			textForArea = getMasiveFromOriginalExcelFile.deleteSpisak_PrilogeniaFromDBaseWhichAreNotInExcelFiles(fProgressBar, fpanel_AllSaerch, year);
		}
			break;
		case "deletePersonStatus": {
			textForArea = getMasiveFromOriginalExcelFile.delete_PersonStatus_FromDBaseWhichAreNotInExcelFiles(fProgressBar, fpanel_AllSaerch, year);
			
		}
			break;
		case "delete_PersonWithoutExcelFile": {
			textForArea = getMasiveFromOriginalExcelFile.delete_PersonWithoutExcelFile(fProgressBar, fpanel_AllSaerch, year);
			
		}
			break;
    	}	
			return textForArea;
    }

    protected void process( List<Double> aDoubles ) {
      //update the percentage of the progress bar that is done
      fProgressBar.setValue( ( int ) (fProgressBar.getMinimum() + (aDoubles.get( aDoubles.size() - 1 ))) );
    }

    @Override
    protected void done() {
   
    	try {
    		
    		getMasiveFromOriginalExcelFile.setTextToArea(ftextArea,  get());
			fProgressBar.setValue(100);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
  }
