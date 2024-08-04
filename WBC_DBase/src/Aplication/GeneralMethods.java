package Aplication;

import java.awt.Cursor;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;



public class GeneralMethods {

	public static void setWaitCursor(JPanel frame) {
		if (frame != null) {
			RootPaneContainer root = (RootPaneContainer) frame.getRootPane().getTopLevelAncestor();
			root.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			root.getGlassPane().setVisible(true);
		}
	}

	public static void setDefaultCursor(JPanel frame) {
		if (frame != null) {
			RootPaneContainer root = (RootPaneContainer) frame.getRootPane().getTopLevelAncestor();
			root.getGlassPane().setCursor(Cursor.getDefaultCursor());
			root.getGlassPane().setVisible(false);
		}
	}
	
	public static void cerateDestinationDir(){
		String destinationDir = ReadFileBGTextVariable.getGlobalTextVariableMap().get("destinationDir");
		File destinationFolder = new File(destinationDir);
		if (!destinationFolder.exists()) {
			destinationFolder.mkdirs();
		}
	}
	
	
}
