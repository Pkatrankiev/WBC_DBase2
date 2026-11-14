package DozeArt;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class HoldButtonExample {

    private static int value = 0;
    private static Timer timer;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Hold Button");
        JButton button = new JButton("Increase");

        timer = new Timer(100, e -> {
            value++;
            System.out.println(value);
        });

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                value++; // еднократно увеличение
                System.out.println(value);

                timer.start(); // започва бързо увеличаване
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                timer.stop(); // спира когато се отпусне
            }
        });

        frame.add(button);
        frame.setSize(200,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
