import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
//    private JButton pickingState = new JButton("choose point");
	private ImagePanel panel = new ImagePanel();
	private JScrollPane scrollPane = new JScrollPane(panel);
	private JTextArea output;
	private Map<String, Color> primaryColors = new HashMap<String, Color>();//will be remove to controller or model
	private Map<Color, String> allColors = new HashMap<Color, String>();

    public ColorDetector(){
		super("Color Detector");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		setLocationRelativeTo(null);
		setPrimaryColors();//will be remove to controller or model
		setAllColors();

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
//    		ImageIcon iconHelp = new ImageIcon("E:\\48x48 PNGs\\wink.png");
			@Override
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
//    		ImageIcon iconAbout = new ImageIcon("E:\\48x48 PNGs\\angel.png");
			@Override
			public void actionPerformed(ActionEvent arg0) {
                String message = "<html><body width=300>" +
                    "<p>Let's stop talking about us! How are you?)";
                JOptionPane.showMessageDialog(getContentPane(), message, "ABOUT", 
                		JOptionPane.INFORMATION_MESSAGE, iconAbout);
			}	
    	});
    	help.add(about);
    	menu.add(help);
    	
//    	pickingState.addActionListener(new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				getRootPane().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
//			}
//    	});
    	
    	output = new JTextArea(5, 40);
        output.setEditable(false);

        getContentPane().add(menu, BorderLayout.NORTH);
		//this.getContentPane().add(pickingState, BorderLayout.SOUTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(panel);
        panel.addMouseListener(panel);
        
		getContentPane().add(new JScrollPane(output), BorderLayout.SOUTH);
		
	}

	void openFileDialog(){
		FileDialog openFileDialog = new FileDialog(this, "Open file");
		openFileDialog.setVisible(true);
		panel.setImage(openFileDialog.getDirectory() + "/" + openFileDialog.getFile());

		this.validate();
		this.repaint();
	}

    class ImagePanel extends JPanel implements MouseListener{
		private BufferedImage image;
		private int panelWidth = 500;
		private int panelHeight = 450;

		public ImagePanel() {
			setPreferredSize(new Dimension(panelWidth, panelHeight));
			try {
				image = ImageIO.read(new File("color-wheel-combination-palette.jpg"));
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

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			int pelX = e.getX();
			int pelY = e.getY();
			if(this.image != null) {
				if(pelX < this.image.getWidth() && pelY < this.image.getHeight()) {
					Color color = new Color(image.getRGB(pelX, pelY));
					String message = getPrimaryColor(color);
					output.append("R: " + color.getRed() + "; G: " + color.getGreen() +
							"; B: " + color.getBlue() + "\n");
					output.append("Primary color: " + message + "\n");
					message = getColorName(color);
		            output.append("Secondary color is " + message + "!\n");
				}
			}
			//			JOptionPane.showMessageDialog(null, message, "COLOR", JOptionPane.INFORMATION_MESSAGE);			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (image != null) {
					getRootPane().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			getRootPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

		@Override
		public void mousePressed(MouseEvent e) {
			getRootPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (image != null) {
				getRootPane().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}
		}
    	
    }
    
	private void setPrimaryColors(){//will be remove to controller or model

		primaryColors.put("red",		new Color(255,   0,   0));
		primaryColors.put("green",		new Color(  0, 255,   0));
		primaryColors.put("blue",		new Color(  0,   0, 255));
		primaryColors.put("yellow",		new Color(255, 255,   0));
		primaryColors.put("white",		new Color(255, 255, 255));
		primaryColors.put("black",		new Color(  0,   0,   0));
		primaryColors.put("gray",		new Color(128, 128, 128));
		primaryColors.put("cyan",		new Color(	0, 255, 255));
		primaryColors.put("darkGray",	new Color( 64,  64,  64));
		primaryColors.put("lightGray",	new Color(192, 192, 192));
		primaryColors.put("magenta",	new Color(255,   0, 255));
		primaryColors.put("orange",		new Color(255, 200,   0));
		primaryColors.put("pink",		new Color(255, 175, 175));
	}
	
	private void setAllColors() {
			File file = createFile("allColors.txt");
			String[] line = new String[5];
			String colorNameEng = "White";
			String colorNameRus = "Белый";
			int colorRed = 255;
			int colorGreen = 255;
			int colorBlue = 255;
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(file));
				boolean next = true;
				String colorLine = "";
				
				while (next) {
					
					try {
						colorLine = reader.readLine();
					} catch (IOException e) {
						output.append("Can't find colors.txt! Check file path.");
					}
					
					if (colorLine == null) {
						next = false;
					} else {
						line = colorLine.split("\t");
						colorNameEng = line[0];
						colorNameRus = line[1];
						colorRed = Integer.parseInt(line[2]);
						colorGreen = Integer.parseInt(line[3]);
						colorBlue = Integer.parseInt(line[4]);
						allColors.put(new Color(colorRed, colorGreen, colorBlue),
								colorNameEng + " \\ " + colorNameRus);
					}
				}
			} catch (FileNotFoundException e1) {
				output.append("Can't find colors.txt! Check file path.");
			}

			
	}
	
	private String getColorName(Color color){
		String colorName = "color unknown :(";
		int distance=256*256*3;
		if (allColors.containsKey(color)) {
			colorName = allColors.get(color);
		} else {
			for (Color col : allColors.keySet()) {
				if(colorDistance(color, col) < distance){
					colorName = allColors.get(col);
					distance = colorDistance(color, col);
				}
			}
			colorName = "approximately " + colorName;
		}
		return colorName;
	}

	private String getPrimaryColor(Color color){//will be remove to controller or model
		String colorName = "color unknown :(";
		int distance=256*256*3;
		for(String col:primaryColors.keySet()){
			if(colorDistance(color, primaryColors.get(col))<distance){
				colorName=col;
				distance=colorDistance(color, primaryColors.get(col));
			}
		}
		return colorName;
	}
	
	private int colorDistance(Color first, Color second){//will be remove to controller or model
		return (first.getRed()-second.getRed())*(first.getRed()-second.getRed())
				+(first.getBlue()-second.getBlue())*(first.getBlue()-second.getBlue())
				+(first.getGreen()-second.getGreen())*(first.getGreen()-second.getGreen());
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
	
    protected File createFile(String name) {
        java.net.URL imgURL = ColorDetector.class.getResource(name);
    	File file = null;
        if (imgURL != null) {
			try {
				file = new File(imgURL.toURI());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            return file;
        } else {
            return file;
        }
    }
    
}