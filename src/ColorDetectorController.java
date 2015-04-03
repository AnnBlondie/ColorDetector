import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ColorDetectorController implements MouseListener{
	
	private ColorDetectorModel model;
	private ColorDetector view;
	
	ColorDetectorController(ColorDetector view, ColorDetectorModel model){
		this.view=view;
		this.model=model;
		this.view.getImagePanel().addMouseListener(this);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		int pelX = e.getX();
		int pelY = e.getY();
		if(this.view.getImagePanel().getImage() != null) {
			if(pelX < this.view.getImagePanel().getImage().getWidth() && pelY < this.view.getImagePanel().getImage().getHeight()) {
				Color color = new Color(this.view.getImagePanel().getImage().getRGB(pelX, pelY));
				String message = model.getPrimaryColor(color);
				this.view.setOutput("R: " + color.getRed() + "; G: " + color.getGreen() +
						"; B: " + color.getBlue() + "\n");
				this.view.setOutput("Primary color: " + message + "\n");
				message = model.getColorName(color);
				this.view.setOutput("Secondary color is " + message + "!\n");
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (this.view.getImagePanel().getImage() != null) {
			this.view.getImagePanel().getRootPane().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.view.getImagePanel().getRootPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.view.getImagePanel().getRootPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.view.getImagePanel().getImage() != null) {
			this.view.getImagePanel().getRootPane().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}
	}
}
