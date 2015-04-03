
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        final ColorDetector colorDet = new ColorDetector();
        ColorDetectorModel model = new ColorDetectorModel();
        ColorDetectorController controller = new ColorDetectorController(colorDet, model);
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                colorDet.setVisible(true);
            }
        });
    }
}


