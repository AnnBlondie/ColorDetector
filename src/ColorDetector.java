import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ColorDetector{
    private JFrame panel;
    private JMenuBar menu = new JMenuBar();
    private JButton pickingState = new JButton("choose point");
    private myImage image = new myImage("angry_beaver-1.gif");

    public ColorDetector(){
    	JMenu fileMenu = new JMenu("file");
    	JMenuItem newPicture = new JMenuItem("make new");
    	JMenuItem openPicture = new JMenuItem("open");
    	JMenuItem exit = new JMenuItem("exit");
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
				// TODO Auto-generated method stub
				panel.getRootPane().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}
    	});
    	
    	
    	panel = new JFrame();
        panel.add(menu, BorderLayout.NORTH);
        panel.add(pickingState, BorderLayout.SOUTH);   	

        //this part is not nice enough
        JLabel label = new JLabel("", image, JLabel.CENTER);
        panel.add(label, BorderLayout.WEST);
        
        panel.pack();
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setVisible(true);
    	
    }
    
    private class myImage extends ImageIcon implements MouseListener{

    	public myImage(String pictureLocation){
    		super(pictureLocation);
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