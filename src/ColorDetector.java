import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
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
	private JMenu imageName = new JMenu("");
	private ImagePanel panel = new ImagePanel();
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
		        output.setText("");
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
    	
    	menu.add(Box.createGlue());
    	menu.add(imageName);

    	output = new JTextArea(4, 40);
        output.setEditable(false);

        getContentPane().add(menu, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
        	@Override
            public void componentResized(ComponentEvent evt) {
                formComponentResized(evt);
            }
       });

		getContentPane().add(new JScrollPane(output), BorderLayout.SOUTH);
		
	}
    
    private void formComponentResized(ComponentEvent evt) {                                      
        panel.scaleImage();
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
		imageName.setText(openFileDialog.getFile().substring(0, 
				openFileDialog.getFile().lastIndexOf(".")));
		this.validate();
		this.repaint();
	}

    class ImagePanel extends JPanel{
    	private BufferedImage beforeImage;
		private BufferedImage image;
		private Image scaledImage;
		private int imageWidth;
		private int imageHeight;
		private int panelWidth;
		private int panelHeight;

		public ImagePanel() {
			super();
			setImage("color-wheel-combination-palette.jpg");
		}

		public ImagePanel(String pictureLocation){
			setImage(pictureLocation);
    	}

		void setImage(String pictureLocation){
			try {
				beforeImage = ImageIO.read(new File(pictureLocation));
				imageWidth = beforeImage.getWidth();
				imageHeight = beforeImage.getHeight();
				scaleImage();
			} catch (IOException ignore) { /* NOP */ }
		}
		
		public BufferedImage getImage() {
			return image;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (image != null)
				g.drawImage(image, (panelWidth - image.getWidth()) / 2,
					(panelHeight - image.getHeight()) / 2, this);
		}
		
		//Converts a given Image into a BufferedImage
		private BufferedImage toBufferedImage(Image img) {
		    if (img instanceof BufferedImage)
		    {
		        return (BufferedImage) img;
		    }

		    // Create a buffered image with transparency
		    BufferedImage bimage = new BufferedImage(img.getWidth(this), img.getHeight(this),
		    		BufferedImage.TYPE_INT_ARGB);

		    // Draw the image on to the buffered image
		    Graphics2D bGr = bimage.createGraphics();
		    bGr.drawImage(img, 0, 0, null);
		    bGr.dispose();

		    // Return the buffered image
		    return bimage;
		}
		
		private void scaleImage() {
			panelWidth = this.getWidth();   //panel width
			panelHeight = this.getHeight();  //panel height
			if ( beforeImage != null ) {

		            //use floats so division below won't round
		            float iw = imageWidth;
		            float ih = imageHeight;
		            
		            if ( panelWidth < iw || panelHeight < ih ) {

		                if ( (panelWidth * 1.0f / panelHeight) > (iw / ih) ) {
		                    iw = -1;
		                    ih = panelHeight;
		                } else {
		                    iw = panelWidth;
		                    ih = -1;
		                }
		                
		                //prevent errors if panel is 0 wide or high
		                if (iw == 0) {
		                    iw = -1;
		                }
		                if (ih == 0) {
		                    ih = -1;
		                }
		                
		                scaledImage = beforeImage.getScaledInstance(
		                            (int)iw, (int)ih, Image.SCALE_SMOOTH);
		                
		            } else {
		                scaledImage = beforeImage;
		            }
		        
			image = toBufferedImage(scaledImage);
			}
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