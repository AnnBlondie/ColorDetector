import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ColorDetector{
    private JFrame panel;
    private JMenuBar menu = new JMenuBar();
    private JButton pickingState = new JButton("choose point");
    private ImageIcon image = new ImageIcon("angry_beaver-1.gif");

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
    	
    	
    	panel = new JFrame();
        panel.add(pickingState, BorderLayout.SOUTH);   	

        panel.add(menu, BorderLayout.NORTH);

        JLabel label = new JLabel("", image, JLabel.CENTER);

        panel.add(label, BorderLayout.WEST);
        panel.pack();
        panel.setVisible(true);
    	
    }
}