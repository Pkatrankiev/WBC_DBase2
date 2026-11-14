package MenuClasses;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import Aplication.ReadFileBGTextVariable;
import QueryExecutor.QueryExecutorWindow;

public class Menu_QueryExecutor extends AbstractMenuAction{
	
	private static final long serialVersionUID = 1L;
	static String labelReferenceQueryExecutor = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelReferenceQueryExecutor");
	static String labelReferenceQueryExecutorTipText = ReadFileBGTextVariable.getGlobalTextVariableMap().get("labelReferenceQueryExecutorTipText");
	
	
	public Menu_QueryExecutor() {
		super(labelReferenceQueryExecutor);
		setToolTipText(labelReferenceQueryExecutorTipText);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(() -> {
            QueryExecutorWindow window = new QueryExecutorWindow();
            window.setVisible(true);
		 });
	}
}
