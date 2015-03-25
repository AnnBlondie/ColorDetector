import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ColorDetector extends JFrame {
    private JMenuBar menu = new JMenuBar();
    private JButton pickingState = new JButton("choose point");
	private ImagePanel panel = new ImagePanel();

    public ColorDetector(){
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1024, 768);
		this.setTitle("Color detector");

		JMenu fileMenu = new JMenu("file");
    	JMenuItem newPicture = new JMenuItem("make new");
    	JMenuItem openPicture = new JMenuItem("open");
    	JMenuItem exit = new JMenuItem("exit");

		openPicture.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent ae){
				openFileDialog();
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		fileMenu.add(newPicture);
    	fileMenu.add(openPicture);
    	fileMenu.add(exit);
    	menu.add(fileMenu);
    	
    	JMenu help = new JMenu("help");
    	JMenuItem showHelp = new JMenuItem("help..");
    	showHelp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
                String message = "<html><body width=300>" +
                		"<h1>DO NOT WORRY!</h1>" +
                    "<p>we will help you!";
                JOptionPane.showMessageDialog(null, message, "HELP", JOptionPane.INFORMATION_MESSAGE);
			}	
    	});
    	help.add(showHelp);
    	
    	JMenuItem about = new JMenuItem("about..");
    	about.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
                String message = "<html><body width=300>" +
                    "<p>Let stop talk about us! How are you?";
                JOptionPane.showMessageDialog(null, message, "ABOUT", JOptionPane.INFORMATION_MESSAGE);
			}	
    	});
    	help.add(about);
    	menu.add(help);
    	
    	pickingState.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getRootPane().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}
    	});


        this.add(menu, BorderLayout.NORTH);
		this.add(pickingState, BorderLayout.SOUTH);
		this.add(panel, BorderLayout.CENTER);
	}

	void openFileDialog(){
		FileDialog openFileDialog = new FileDialog(this, "Open file");
		openFileDialog.setVisible(true);
		panel.setImage(openFileDialog.getDirectory() + "/" + openFileDialog.getFile());

		this.validate();
		this.repaint();
	}

    private class ImagePanel extends JPanel implements MouseListener{
		private BufferedImage image;

		public ImagePanel() {
			try {
				image = ImageIO.read(new File("angry_beaver-1.gif"));
			} catch (IOException ignore) { /* NOP */ }
		}

		public ImagePanel(String pictureLocation){
			try {
				image = ImageIO.read(new File(pictureLocation));
			} catch (IOException ignore) { /* NOP */ }
    	}

		void setImage(String pictureLocation){
			try {
				image = ImageIO.read(new File(pictureLocation));
			} catch (IOException ignore) { /* NOP */ }
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null);
		}



		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}
    	
    }
}