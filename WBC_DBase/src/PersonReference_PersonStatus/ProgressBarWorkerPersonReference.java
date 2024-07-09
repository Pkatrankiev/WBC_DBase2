package PersonReference_PersonStatus;

import java.awt.Choice;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.text.JTextComponent;

import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.PersonStatusNew;
import BasicClassAccessDbase.Workplace;

public class ProgressBarWorkerPersonReference extends SwingWorker<List<PersonStatusNew>, Double>{
    private final JProgressBar progressBar;
	private JTextComponent textField_Year;
	private  List<PersonStatusNew> listPersonStat;
	private JPanel panel_AllSaerch;
	private JPanel tablePane;
	private JScrollPane scrollPane;
	private JCheckBox chckbx_Editing;
	
	ProgressBarWorkerPersonReference( JTextField textField_Year1, JProgressBar aProgressBar,JPanel panel_AllSaerch1, JPanel tablePane1, JScrollPane scrollPane1,
			JCheckBox chckbx_Editing1) {
		progressBar = aProgressBar;
		textField_Year = textField_Year1;
		panel_AllSaerch = panel_AllSaerch1;
		tablePane = tablePane1;
		scrollPane = scrollPane1;
		chckbx_Editing = chckbx_Editing1;

    }

    @Override
    protected List<PersonStatusNew>  doInBackground() throws Exception {
    	double stepForProgressBar = 100;
    	List<PersonStatusNew> listPersonStat2 = PersonStatusNewDAO.getValuePersonStatusNewByYearWithProgressBar(textField_Year.getText(), progressBar, stepForProgressBar);
		return listPersonStat2;
    }

    protected void process( List<Double> aDoubles ) {
      //update the percentage of the progress bar that is done
    	progressBar.setValue( ( int ) (progressBar.getMinimum() + (aDoubles.get( aDoubles.size() - 1 ))) );
    }

    @Override
    protected void  done() {
   
    	try {
    		
    		Choice choice_Firm = PersonReference_PersonStatus_Frame.getChoice_Firm();
    		listPersonStat = get();
    		List<Workplace> listWorkplace = WorkplaceDAO.getActualValueWorkplaceByFirm(choice_Firm.getSelectedItem());
			listPersonStat = PersonReference_PersonStatus_Methods.selectListPersonStatByWorkplace(listPersonStat, listWorkplace);
			PersonReference_PersonStatus_Methods.setListPersonStat(listPersonStat);
			Object[][] dataTable = PersonReference_PersonStatus_Methods.generateMasiveSelectionPerson(listPersonStat);
			PersonReference_PersonStatus_Methods.setDataTable(dataTable);
			 PersonReference_PersonStatus_Methods.panel_infoPanelTablePanel(panel_AllSaerch, tablePane, scrollPane, chckbx_Editing);
			PersonReference_PersonStatus_Frame.viewTablePanel();
    			progressBar.setValue(100);
		} catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
		}
    	
    }

	

	
  }
