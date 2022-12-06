
package MenuClasses;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;




abstract class AbstractMenuAction extends JMenuItem implements ActionListener {

    private static final long serialVersionUID = 7907436305283586667L;

    protected ActionHandler main;

    public AbstractMenuAction(String title) {
        super(title);
        
        addActionListener(this);
    }

}
