
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                makeGui();
            }
        });
    }
    
    public static void makeGui() {
        final ColorDetector colorDet = new ColorDetector();
        ColorDetectorModel model = new ColorDetectorModel();
        ColorDetectorController controller = new ColorDetectorController(colorDet, model);
        colorDet.setVisible(true);
    }
}


