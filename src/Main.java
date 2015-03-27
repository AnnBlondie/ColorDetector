
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        final ColorDetector colorDet = new ColorDetector();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                colorDet.setVisible(true);
            }
        });
    }
}


