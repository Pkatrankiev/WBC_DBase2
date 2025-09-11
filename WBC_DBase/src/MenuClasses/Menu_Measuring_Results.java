package MenuClasses;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;
import BasicTable_Measuring_Results.Measuring_Results_Frame;
import DozeArt.DozeArtFrame;

public class Menu_Measuring_Results extends AbstractMenuAction{

	private static final long serialVersionUID = 1L;
	static String resultFromMeasuring = ReadFileBGTextVariable.getGlobalTextVariableMap().get("resultFromMeasuring");
	static String resultFromMeasuringReference_TipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("resultFromMeasuringReference_TipText");
	public Menu_Measuring_Results() {
		super(resultFromMeasuring);
		setToolTipText(resultFromMeasuringReference_TipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		ActionIcone round = new ActionIcone();
		 final Thread thread = new Thread(new Runnable() {
		     @Override
		     public void run() {
		    	new Measuring_Results_Frame(round);
		    	round.StopWindow();
		     }
		    });
		    thread.start();	
		    	     	
	
				
	}

	}