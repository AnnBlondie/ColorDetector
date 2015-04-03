import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ColorDetector extends JFrame {
    private JMenuBar menu = new JMenuBar();
	private ImagePanel panel = new ImagePanel();
	private JScrollPane scrollPane = new JScrollPane(panel);
	private JTextArea output;

    public ColorDetector(){
		super("Color Detector");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		setLocationRelativeTo(null);

		JMenu fileMenu = new JMenu("File");
    	JMenuItem newPicture = new JMenuItem("New");
    	JMenuItem openPicture = new JMenuItem("Open Image");
    	JMenuItem exit = new JMenuItem("Exit");

		openPicture.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent ae){
				openFileDialog();
		        if(panel.image != null) {
		        	panel.setPreferredSize(new Dimension(
		        			Math.max(panel.image.getWidth(), panel.panelWidth),
		        			Math.max(panel.image.getHeight(), panel.panelHeight)));
		        } else {
		        	panel.setPreferredSize(new Dimension(panel.panelWidth, panel.panelHeight));
		        }
		        output.setText("");
		        scrollPane.revalidate();
		        scrollPane.repaint();
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
    	
    	JMenu help = new JMenu("Help");
    	JMenuItem showHelp = new JMenuItem("Help...");
    	showHelp.addActionListener(new ActionListener(){
    		ImageIcon iconHelp = createImageIcon("wink.png");
			public void actionPerformed(ActionEvent arg0) {
                String message = "<html><body width=300>" +
                		"<h1>DON'T WORRY!</h1>" +
                    "<p>We will help you!";
                JOptionPane.showMessageDialog(getContentPane(), message, "HELP", 
                		JOptionPane.PLAIN_MESSAGE, iconHelp);
			}	
    	});
    	help.add(showHelp);
    	
    	JMenuItem about = new JMenuItem("About...");
    	about.addActionListener(new ActionListener(){
    		ImageIcon iconAbout = createImageIcon("angel.png");
			public void actionPerformed(ActionEvent arg0) {
                String message = "<html><body width=300>" +
                    "<p>Let's stop talking about us! How are you?)";
                JOptionPane.showMessageDialog(getContentPane(), message, "ABOUT", 
                		JOptionPane.INFORMATION_MESSAGE, iconAbout);
			}	
    	});
    	help.add(about);
    	menu.add(help);
    	
    	output = new JTextArea(5, 40);
        output.setEditable(false);

        getContentPane().add(menu, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(panel);
      
		getContentPane().add(new JScrollPane(output), BorderLayout.SOUTH);
		
	}

	public JTextArea getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output.append(output);
	}

	void openFileDialog(){
		FileDialog openFileDialog = new FileDialog(this, "Open file");
		openFileDialog.setVisible(true);
		panel.setImage(openFileDialog.getDirectory() + "/" + openFileDialog.getFile());
		this.validate();
		this.repaint();
	}

    class ImagePanel extends JPanel{
		private BufferedImage image;
		private int panelWidth = 500;
		private int panelHeight = 450;

		public ImagePanel() {
			try {
				image = ImageIO.read(new File("color-wheel-combination-palette.jpg"));
				panelWidth = Math.max(panelWidth, image.getWidth());
				panelHeight = Math.max(panelHeight, image.getHeight());
				setPreferredSize(new Dimension(panelWidth, panelHeight));
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
		
		public BufferedImage getImage() {
			return image;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null);
		}
    }
    
    public ImagePanel getImagePanel() {
		return panel;
	}

	public void setImagePanel(ImagePanel panel) {
		this.panel = panel;
	}
	
    // Returns an ImageIcon, or null if the path was invalid.
    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ColorDetector.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            return null;
        }
    }
   
}