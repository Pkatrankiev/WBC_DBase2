package Test;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

public class JScrollPaneTest implements KeyListener {
    private JScrollPane view;   

    public JScrollPaneTest() { 
        create();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                 new JScrollPaneTest();
            }
        });
    }

    public void create() {
       JFrame frame = new JFrame(); //make frame
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(1000, 1000);
       frame.setLocationRelativeTo(null);
       frame.setResizable(false);

       SpringLayout layout = new SpringLayout(); 
       JPanel base = new JPanel();
       base.setPreferredSize(new Dimension(1000, 1000));
       base.setLayout(layout);
         
       JPanel map = new JPanel();  //make panel for the scrollpane
       map.setPreferredSize(new Dimension(1000, 1000));
       
       
       view = new JScrollPane(map);  //initialize scrollpane and add to base with a SpringLayout
       view.setFocusable(true);
       view.setPreferredSize(new Dimension(300, 300));
       layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, view, 0, SpringLayout.HORIZONTAL_CENTER, base);
       layout.putConstraint(SpringLayout.VERTICAL_CENTER, view, 0,  SpringLayout.VERTICAL_CENTER, base);
       base.add(view); //add scrollpane to base jpanel

       frame.add(base);
       frame.setVisible(true);
       view.addKeyListener(this);
       
       view.getHorizontalScrollBar().setMaximum(10*800); //set wanted max sizes of each scroll bar
       view.getVerticalScrollBar().setMaximum(10*800);
    }

    public void keyPressed(KeyEvent event) { // W, A, S, D keys to change values of jscrollbars
        int verticalValue = view.getVerticalScrollBar().getValue();
        int horizontalValue = view.getHorizontalScrollBar().getValue();
        
        switch (event.getKeyCode()) {
        case KeyEvent.VK_W:
            view.getVerticalScrollBar().setValue(verticalValue - 10);
            break;
        case KeyEvent.VK_S:
            view.getVerticalScrollBar().setValue(verticalValue + 10);
            break;
        case KeyEvent.VK_A:
            view.getHorizontalScrollBar().setValue(horizontalValue - 10);
            break;
        case KeyEvent.VK_D:
            view.getHorizontalScrollBar().setValue(horizontalValue + 10);
            break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
 
    
//    public void ScrollBarExample(){  
//        JFrame f= new JFrame("Scrollbar Example");  
//        final JLabel label = new JLabel();          
//        label.setHorizontalAlignment(JLabel.CENTER);    
//        label.setSize(400,100);  
//        final JScrollBar s=new JScrollBar();  
//        s.setBounds(100,100, 50,100);  
//        f.add(s); f.add(label);  
//        f.setSize(400,400);  
//       f.setLayout(null);  
//       f.setVisible(true);  
//       s.addAdjustmentListener(new AdjustmentListener() {  
//        public void adjustmentValueChanged(AdjustmentEvent e) {  
//           label.setText("Vertical Scrollbar value is:"+ s.getValue());  
//        }  
//     });  
//    }  
    
    
    
}