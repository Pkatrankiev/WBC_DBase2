package CheckErrorDataInExcellFiles;

import java.awt.RenderingHints.Key;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

class MySwingWorker extends SwingWorker<String, Double>{
    private final JProgressBar fProgressBar;
	private JTextArea ftextArea;
	private JPanel fpanel_AllSaerch;
	private String fnameMethod;
   
    MySwingWorker( JProgressBar aProgressBar, JTextArea textArea, JPanel panel_AllSaerch, String nameMethod) {
      fProgressBar = aProgressBar;
      ftextArea = textArea;
      fpanel_AllSaerch = panel_AllSaerch;
      fnameMethod = nameMethod;
    }

    @Override
    protected String doInBackground() throws Exception {
    	
    	String textForArea = "";
    	switch (fnameMethod) {
		
		case "CheckPersonStatus": {
			textForArea = CheckPersonStatus.comparePersonStatus(fProgressBar, fpanel_AllSaerch);
			
		}
			break;
		case "CheckDBaseNameKodeStat": {
			textForArea = CheckPersonName_KodeStatus_DBseToExcelFiles.checkPersonNameKodeStatus(fProgressBar, fpanel_AllSaerch);
			
		}
			break;
		case "SearchError": {
			textForArea = CheckDataBethwinExcelFilesAndMonth.CheckForCorrectionMeasuringInSheet0AndInMonth(fProgressBar, fpanel_AllSaerch);
			
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
    		
			CheckPersonStatus.setTextToArea(ftextArea,  get());
			fProgressBar.setValue(100);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
  }
