import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ColorDetector extends JFrame {
    private JMenuBar menu = new JMenuBar();
    private JButton pickingState = new JButton("choose point");
	private ImagePanel panel = new ImagePanel();
	private Map<String, Color> primaryColors = new HashMap<String, Color>();//will be remove to controller or model


    public ColorDetector(){
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setTitle("Color detector");
		setPrimaryColors();//will be remove to controller or model

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


        this.getContentPane().add(menu, BorderLayout.NORTH);
		//this.getContentPane().add(pickingState, BorderLayout.SOUTH);
        panel.addMouseListener(panel);
		this.getContentPane().add(panel, BorderLayout.CENTER);
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

		@Override
		public void mouseClicked(MouseEvent e) {
			Color color = new Color(image.getRGB((int)e.getPoint().getX(),(int)e.getPoint().getY()));
			String message = getPrimaryColor(color);
            JOptionPane.showMessageDialog(null, message, "COLOR", JOptionPane.INFORMATION_MESSAGE);			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			getRootPane().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
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
			getRootPane().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
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
}