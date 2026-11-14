package InsertMeasuting;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Demo {

    static int value = 0;
    static JLabel label;

    public static void main(String[] args) {

        JFrame frame = new JFrame("HoldButton Demo");

        label = new JLabel("0", SwingConstants.CENTER);

        HoldButton plus = new HoldButton("+", () -> {
            value++;
            label.setText(String.valueOf(value));
        });

        HoldButton minus = new HoldButton("-", () -> {
            value--;
            label.setText(String.valueOf(value));
        });

        frame.setLayout(new java.awt.BorderLayout());
        frame.add(label, "Center");
        frame.add(plus, "East");
        frame.add(minus, "West");

        frame.setSize(300,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
