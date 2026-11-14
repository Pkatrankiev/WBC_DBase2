package InsertMeasuting;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.Timer;

public class HoldButton extends JButton {
 
	private static final long serialVersionUID = 1L;

	private Timer timer;

    private Runnable action;

    private int initialDelay = 400;   // изчакване преди repeat
    private int repeatDelay = 120;    // начална скорост
    private int minDelay = 30;        // максимална скорост
    private int acceleration = 5;     // ускорение

    private boolean accelerating = false;

    public HoldButton(String text, Runnable action) {
        super(text);
        this.action = action;

        timer = new Timer(repeatDelay, e -> {
            action.run();

            if (accelerating) {
                repeatDelay = Math.max(minDelay, repeatDelay - acceleration);
                timer.setDelay(repeatDelay);
            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                action.run(); // първо действие веднага

                repeatDelay = 120;
                accelerating = false;

                timer.setInitialDelay(initialDelay);
                timer.setDelay(repeatDelay);

                timer.start();

                // след initial delay започва ускорение
                new Timer(initialDelay, ev -> accelerating = true) {
					
					private static final long serialVersionUID = 1L;

				{
                    setRepeats(false);
                    start();
                }};
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                stop();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                stop();
            }
        });
    }

    private void stop() {
        timer.stop();
        accelerating = false;
    }
}