import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ColorDetector{
    private JFrame panel;
    private JPanel menu = new JPanel(new BorderLayout());
    private JButton pickingState = new JButton("choose point");
    private ImageIcon image = new ImageIcon("angry_beaver-1.gif");

    public ColorDetector(){
    	JButton file = new JButton("file");
    	final JPanel fileMenu = new JPanel(new BorderLayout());
    	JButton newPicture = new JButton("make new");
    	JButton openPicture = new JButton("open");
    	JButton exit = new JButton("exit");
    	fileMenu.add(newPicture);
    	fileMenu.add(openPicture);
    	fileMenu.add(exit);
    	fileMenu.setVisible(false);
    	file.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
	        	fileMenu.setVisible(true);
			}        	
        });

    	JButton help = new JButton("help");
    	help.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame helpFrame = new JFrame();
				JLabel helpLable = new JLabel(" we will hepl you! ");
				helpFrame.add(helpLable);
				helpFrame.pack();
				helpFrame.setVisible(true);
			}	
    	});
    	JButton about = new JButton("about");
    	about.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame aboutFrame = new JFrame();
				JLabel aboutLable = new JLabel(" Let stop talk about us! How are you?");
				aboutFrame.add(aboutLable);
				aboutFrame.pack();
				aboutFrame.setVisible(true);
			}	
    	});
    	menu.add(file, BorderLayout.WEST);
    	menu.add(help);
    	menu.add(about, BorderLayout.EAST);
    	
    	panel = new JFrame();
        panel.add(pickingState, BorderLayout.SOUTH);   	

        panel.add(menu, BorderLayout.NORTH);

        JLabel label = new JLabel("", image, JLabel.CENTER);

        panel.add(label, BorderLayout.WEST);
        panel.pack();
        panel.setVisible(true);
    	
    }
}