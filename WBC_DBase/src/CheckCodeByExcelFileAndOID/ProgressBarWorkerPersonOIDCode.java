package CheckCodeByExcelFileAndOID;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import PersonReference_OID.OID_Person_WBC;
import PersonReference_OID.OID_Person_WBCDAO;


public class ProgressBarWorkerPersonOIDCode extends SwingWorker<Object[][], Double>{
    private final JProgressBar progressBar;
	private  Object[][] masiveData;
	private JPanel panel_AllSaerch;
	private JPanel tablePane;
	private JScrollPane scrollPane;
    private JTextArea ftextArea;

	
	ProgressBarWorkerPersonOIDCode(JTextArea textArea, JProgressBar aProgressBar,JPanel panel_AllSaerch1, JPanel tablePane1, JScrollPane scrollPane1
			) {
		progressBar = aProgressBar;
		panel_AllSaerch = panel_AllSaerch1;
		tablePane = tablePane1;
		scrollPane = scrollPane1;
		 ftextArea = textArea;

    }

    @Override
    protected Object[][]  doInBackground() throws Exception {
    	
    	String curentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
    	List<KodeStatus> listAllKodeStat = KodeStatusDAO.getKodeStatusByYearWithProgressBar(curentYear, progressBar, 20, 0);
    	Set<String> listPersonKodeStat = new HashSet<>();
    	for (KodeStatus kodstat : listAllKodeStat) {
    		listPersonKodeStat.add(kodstat.getPerson().getEgn());
    	}
    	List<OID_Person_WBC> listWBCPerson = OID_Person_WBCDAO.getlist_All_OID_Person_WBC();
     	double progressBarVolume = 20;
    	double  ProgressBarSize = 80;
    	
    	Object[][] dataTable = new Object[listWBCPerson.size()][8];
    	int k=0;
    	String KZ1,KZ2;
    	double stepForProgressBar = ProgressBarSize / listWBCPerson.size();
    	for (OID_Person_WBC oid_Person_WBC : listWBCPerson) {
    	     		
    		KZ1="";
    		KZ2="";
    		  if(oid_Person_WBC.getZsr1() != null && !oid_Person_WBC.getZsr1().isEmpty()) {	
    			  KZ1 = oid_Person_WBC.getZsr1();  
    		  }
    		  if(oid_Person_WBC.getZsr2() != null && !oid_Person_WBC.getZsr2().isEmpty()) {	
    			  KZ2 = oid_Person_WBC.getZsr2();  
    		  }
    		  if(!KZ1.isEmpty() || !KZ2.isEmpty()) {	  
    		  
    		dataTable[k][0] = oid_Person_WBC.getEgn();
    		dataTable[k][1]= oid_Person_WBC.getFirstName(); 
    		dataTable[k][2]= oid_Person_WBC.getSecondName() ;
    		dataTable[k][3]= oid_Person_WBC.getLastName() ;
    		dataTable[k][4]= KZ1 ;
    		dataTable[k][5]= "";
    		dataTable[k][6]= KZ2 ;
    		dataTable[k][7]= "";
    		
    		Person pers = PersonDAO.getValuePersonByEGN(oid_Person_WBC.getEgn());
			if(pers != null) {
				for (Iterator<String> iterator = listPersonKodeStat.iterator(); iterator.hasNext();) {
					String kdst = (String) iterator.next();
					if(oid_Person_WBC.getEgn().equals(kdst)) {
						iterator.remove();
					}
				}
				
			List<KodeStatus> listKdst = KodeStatusDAO.getKodeStatusByPersonYear(pers, curentYear);
			if(listKdst != null) {
			for (KodeStatus kdst : listKdst) {
				if(kdst.getZone().getId_Zone()==1) {
					dataTable[k][5]= kdst.getKode();
				}
				if(kdst.getZone().getId_Zone()==2) {
					dataTable[k][7]= kdst.getKode();
				}
			}
			}
			}
			progressBar.setValue((int) progressBarVolume);
			progressBarVolume += stepForProgressBar;
    	if(!dataTable[k][4].equals(dataTable[k][5]) || !dataTable[k][6].equals(dataTable[k][7])){
    		  System.out.println(k+" "+dataTable[k][0]+" "+dataTable[k][1]+" "+dataTable[k][2]+" "+dataTable[k][3]+" "+dataTable[k][4]+" "+dataTable[k][5]+" "+dataTable[k][6]+" "+dataTable[k][7]);
    		  k++; 
    	}
    		  }
    		}
    	ProgressBarSize = 20;
    	stepForProgressBar = ProgressBarSize / listAllKodeStat.size();
    	for (String egnPerson : listPersonKodeStat) {
    		Person person = PersonDAO.getValuePersonByEGN(egnPerson);
    		 	  
    		dataTable[k][0] = egnPerson;
    		dataTable[k][1]= person.getFirstName(); 
    		dataTable[k][2]= person.getSecondName() ;
    		dataTable[k][3]= person.getLastName() ;
    		dataTable[k][4]= "" ;
    		dataTable[k][5]= "" ;
    		dataTable[k][6]= "" ;
    		dataTable[k][7]= "" ;
    		List<KodeStatus> listKdst = KodeStatusDAO.getKodeStatusByPersonYear(person, curentYear);
			for (KodeStatus kdst : listKdst) {
				if(kdst.getZone().getId_Zone()==1) {
					dataTable[k][5]= kdst.getKode();
				}
				if(kdst.getZone().getId_Zone()==2) {
					dataTable[k][7]= kdst.getKode();
				}
			}
			 if(!((String) dataTable[k][5]).isEmpty() || !((String) dataTable[k][7]).isEmpty()) {
			progressBar.setValue((int) progressBarVolume);
			progressBarVolume += stepForProgressBar;
			k++;
    	}
		}	
    	Object[][] newDataTable = new Object[k][9];
    	for (int i = 0; i < k; i++) {
    		newDataTable[i][0] = i;
    		newDataTable[i][1] = dataTable[i][0];
    		newDataTable[i][2] = dataTable[i][1];
    		newDataTable[i][3] = dataTable[i][2];
    		newDataTable[i][4] = dataTable[i][3];
    		newDataTable[i][5] = dataTable[i][4];
    		newDataTable[i][6] = dataTable[i][5];
    		newDataTable[i][7] = dataTable[i][6];
    		newDataTable[i][8] = dataTable[i][7];
    	}
    	
    	
    	progressBar.setValue(100);
    	return newDataTable;
    }

    protected void process( List<Double> aDoubles ) {
      //update the percentage of the progress bar that is done
    	progressBar.setValue( ( int ) (progressBar.getMinimum() + (aDoubles.get( aDoubles.size() - 1 ))) );
    }

    @Override
    protected void  done() {
   
    	try {
    
    		masiveData = get();
    		CheckCodeByExcelFileAndOID_Methods.setDataTable(masiveData);
    		CheckCodeByExcelFileAndOID_Methods.panel_infoPanelTablePanel(ftextArea, panel_AllSaerch, tablePane, scrollPane);
			CheckCodeByExcelFileAndOID_Frame.viewTablePanel();
    			progressBar.setValue(100);
		} catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
		}
    	
    }

	

	
  }