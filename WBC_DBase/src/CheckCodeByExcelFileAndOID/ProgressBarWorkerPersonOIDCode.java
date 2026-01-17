package CheckCodeByExcelFileAndOID;
import java.awt.Choice;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.text.JTextComponent;

import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusNewDAO;
import BasiClassDAO.WorkplaceDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.Workplace;

import PersonReference_OID.OID_Person_WBC;
import PersonReference_OID.OID_Person_WBCDAO;


public class ProgressBarWorkerPersonOIDCode extends SwingWorker<Object[][], Double>{
    private final JProgressBar progressBar;
	private JTextComponent textField_Year;
	private  Object[][] listPersonStat;
	private JPanel panel_AllSaerch;
	private JPanel tablePane;
	private JScrollPane scrollPane;
	private Person person;
	
	ProgressBarWorkerPersonOIDCode( JTextField textField_Year1, Person person1, JProgressBar aProgressBar,JPanel panel_AllSaerch1, JPanel tablePane1, JScrollPane scrollPane1
			) {
		progressBar = aProgressBar;
		textField_Year = textField_Year1;
		person = person1;
		panel_AllSaerch = panel_AllSaerch1;
		tablePane = tablePane1;
		scrollPane = scrollPane1;
		

    }

    @Override
    protected Object[][]  doInBackground() throws Exception {
    	double stepForProgressBar = 100;
    	String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
    	List<KodeStatus> listAllKodeStat = KodeStatusDAO.getKodeStatusByYear(curentYear);
    	List<String> listPersonKodeStat = new ArrayList<>();
    	for (KodeStatus kodstat : listAllKodeStat) {
    		
    	}
    	List<OID_Person_WBC> listWBCPerson = OID_Person_WBCDAO.getlist_All_OID_Person_WBC();
    	Object[][] dataTable = new Object[listWBCPerson.size()][8];
    	int k=0;
    	
    	
    	for (OID_Person_WBC oid_Person_WBC : listWBCPerson) {
    		  if(!oid_Person_WBC.getZsr1().isEmpty() || !oid_Person_WBC.getZsr1().isEmpty()) {		
    		
    			
    		dataTable[k][0] = oid_Person_WBC.getEgn();
    		dataTable[k][1]= oid_Person_WBC.getFirstName(); 
    		dataTable[k][2]= oid_Person_WBC.getSecondName() ;
    		dataTable[k][3]= oid_Person_WBC.getLastName() ;
    		dataTable[k][4]= oid_Person_WBC.getZsr1() ;
    		dataTable[k][5]= "";
    		dataTable[k][6]= oid_Person_WBC.getZsr2() ;
    		dataTable[k][7]= "";
    		
    		Person pers = PersonDAO.getValuePersonByEGN(oid_Person_WBC.getEgn());
			if(pers != null) {
				for (Iterator<KodeStatus> iterator = listAllKodeStat.iterator(); iterator.hasNext();) {
					KodeStatus kdst = (KodeStatus) iterator.next();
					if(oid_Person_WBC.getEgn().equals(kdst.getPerson().getEgn())) {
						iterator.remove();
					}
				}
				
			List<KodeStatus> listKdst = KodeStatusDAO.getKodeStatusByPersonYear(pers, curentYear);
			for (KodeStatus kdst : listKdst) {
				if(kdst.getZone().getId_Zone()==1) {
					dataTable[k][5]= kdst.getKode();
				}
				if(kdst.getZone().getId_Zone()==2) {
					dataTable[k][7]= kdst.getKode();
				}
			}
			}
    		
    		k++;
    		  }
    		  
    	for (KodeStatus kodstat : listAllKodeStat) {
    		dataTable[k][0] = kodstat.getPerson().getEgn();
    		dataTable[k][1]= kodstat.getPerson().getFirstName(); 
    		dataTable[k][2]= kodstat.getPerson().getSecondName() ;
    		dataTable[k][3]= kodstat.getPerson().getLastName() ;
    		dataTable[k][4]= "" ;
//    		dataTable[k][5]= kodstat.get;
    		dataTable[k][6]= "" ;
    		dataTable[k][7]= "";
		}	  
    		  
		}
    		
		
    	return dataTable;
    }

    protected void process( List<Double> aDoubles ) {
      //update the percentage of the progress bar that is done
    	progressBar.setValue( ( int ) (progressBar.getMinimum() + (aDoubles.get( aDoubles.size() - 1 ))) );
    }

    @Override
    protected void  done() {
   
    	try {
    		
    		
    		
//    		Choice choice_Firm = PersonReference_PersonStatus_Frame.getChoice_Firm();
    		listPersonStat = get();
//    		if(!choice_Firm.getSelectedItem().isEmpty()) {
//    		List<Workplace> listWorkplace = WorkplaceDAO.getActualValueWorkplaceByFirm(choice_Firm.getSelectedItem());
//			listPersonStat = PersonReference_PersonStatus_Methods.selectListPersonStatByWorkplace(listPersonStat, listWorkplace);
//    		}
//			PersonReference_PersonStatus_Methods.setListPersonStat(listPersonStat);
    		
//			Object[][] dataTable = PersonReference_PersonStatus_Methods.generateMasiveSelectionPerson(listPersonStat);
//			PersonReference_PersonStatus_Methods.setDataTable(dataTable);
//			 PersonReference_PersonStatus_Methods.panel_infoPanelTablePanel(panel_AllSaerch, tablePane, scrollPane, chckbx_Editing);
//			PersonReference_PersonStatus_Frame.viewTablePanel();
    			progressBar.setValue(100);
		} catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
		}
    	
    }

	

	
  }