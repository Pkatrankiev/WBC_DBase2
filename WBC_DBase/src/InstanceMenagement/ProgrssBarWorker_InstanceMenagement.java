package InstanceMenagement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.text.JTextComponent;

import BasiClassDAO.InstanceDAO;
import BasicClassAccessDbase.Instance;





public class ProgrssBarWorker_InstanceMenagement extends SwingWorker<List<Instance>, Double>{
    private final JProgressBar progressBar;
	private JTextComponent textField_Year;
	private  List<Instance> listInstance;
	private JPanel panel_AllSaerch;
	private JPanel tablePane;
	private JScrollPane scrollPane;
	private JCheckBox chckbx_Editing;
	
	
	ProgrssBarWorker_InstanceMenagement( JTextField textField_Year1, JProgressBar aProgressBar,JPanel panel_AllSaerch1, JPanel tablePane1, JScrollPane scrollPane1
			) {
		progressBar = aProgressBar;
		textField_Year = textField_Year1;
		panel_AllSaerch = panel_AllSaerch1;
		tablePane = tablePane1;
		scrollPane = scrollPane1;
		

    }

    @Override
    protected List<Instance>  doInBackground() throws Exception {
    	double stepForProgressBar = 100;
    	List<Instance> listInstance2 = new ArrayList<>();
    	
    		listInstance2 = InstanceDAO.getValueInstanceByYearWithProgressBar(textField_Year.getText(), progressBar, stepForProgressBar, 0);	
    
		System.out.println("listPersonStat2.size() "+listInstance2.size());
    	return listInstance2;
    }

    protected void process( List<Double> aDoubles ) {
      //update the percentage of the progress bar that is done
    	progressBar.setValue( ( int ) (progressBar.getMinimum() + (aDoubles.get( aDoubles.size() - 1 ))) );
    }

    @Override
    protected void  done() {
   
    	try {
    		
    	
    		listInstance = get();
    		    		
			Object[][] dataTable = InstanceMenagement_Frame_Methods.generateMasiveSelectionInstance(listInstance);
			System.out.println(dataTable.length);
			InstanceMenagement_Frame_Methods.setDataTable(dataTable);
			InstanceMenagement_Frame_Methods.panel_infoPanelTablePanel(panel_AllSaerch, tablePane, scrollPane, chckbx_Editing);
			InstanceMenagement_Frame.viewTablePanel();
    			progressBar.setValue(100);
		} catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
		}
    	
    }

	

	
  }
