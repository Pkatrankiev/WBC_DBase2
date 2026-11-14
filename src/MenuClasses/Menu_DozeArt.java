package MenuClasses;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import Aplication.ReadFileBGTextVariable;
import DozeArt.DozeArtFrame;


public class Menu_DozeArt extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String dozeCalculate = ReadFileBGTextVariable.getGlobalTextVariableMap().get("dozeCalculate");
	static String dozeCalculateTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("dozeCalculateTipText");
	public Menu_DozeArt() {
		super(dozeCalculate);
		setToolTipText(dozeCalculateTipText);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		final JFrame ff = new JFrame();
		 new DozeArtFrame(ff,null, null);
		    	     	
	
				
	}

	}

