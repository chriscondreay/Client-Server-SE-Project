package ClientServer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientUserInterface extends JFrame {
	
	private final int WIDTH = 220;
    private final int HEIGHT = 180;

    private ControlPanelInner controlPanel;
    private DisplayPanelInner displayPanel;
    
    public ClientUserInterface() {
    	
    	// -- construct the base JFrame first
        super();

        // -- set the application title
        this.setTitle("Client");

        // -- initial size of the frame: width, height
        this.setSize(WIDTH, HEIGHT);

        // -- center the frame on the screen
        this.setLocationRelativeTo(null);

        // -- shut down the entire application when the frame is closed
        //    if you don't include this the application will continue to
        //    run in the background and you'll have to kill it by pressing
        //    the red square in eclipse
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // -- set the layout manager and add items
        //    5, 5 is the border around the edges of the areas
        setLayout(new BorderLayout(5, 5));
       
        // -- construct a JPanel
        controlPanel = new ControlPanelInner();
        this.add(controlPanel, BorderLayout.WEST);
        
        // -- can make it so the user cannot resize the frame
        this.setResizable(false);

        // -- show the frame on the screen
        this.setVisible(true);
        
    }
    
    public class ControlPanelInner extends JPanel {
    	
    	// -- push buttons
        private JButton login;
        private JButton register;
        private JButton pwdRecov;
        private JButton shutdown;
        
        
        // -- fixed labels, can be changed by the program but not the user
        private JLabel errorMesages = new JLabel("  *any error messages here*   ");
        private JLabel numOfloggedInUsers = new JLabel("  Users Logged In   ");
        
        
        // -- field to hold 1 line of text
        private JTextField connections;
        private JTextField users;
        
        public ControlPanelInner() {
        	
        	prepareButtonHandlers();
        	
        	setLayout(new FlowLayout());
        	
        	// -- construct the JTextField, 15 characters wide
        	connections = new JTextField(" ", 15);
        	connections.setEditable(false);
        	users = new JTextField(" ", 15);
        	users.setEditable(false);
        	
            // -- add items to the JPanel in order
            this.add(login); 
            this.add(register); 
            this.add(pwdRecov); 
            this.add(shutdown); 
            this.add(errorMesages);
            
        }
        
        // -- construct JButtons and add event handlers
        private void prepareButtonHandlers() {
        	
        	login = new JButton("Login");
        	//disconnect
        	login.addActionListener(
        		new ActionListener() {
                    	
        			public void actionPerformed(ActionEvent arg0) {
                        	
        				// -- do not do anything that is time consuming
        				//    doing so will make the GUI non-responsive to the user
        				//String textFieldString = textField.getText();
                        
        			}
                }
            ); 
        	register = new JButton("Register");
        	pwdRecov = new JButton("Recovery Password");
        	shutdown = new JButton("Shutdown");
        }
        
        // -- sets the size of the JPanel
        public Dimension getPreferredSize()
        {
            return new Dimension(200, 500);
        }
        
    }
    
    public static void main(String[] args) {
	      
	     new ClientUserInterface();
	
	     // -- this line demonstrates that the Swing JFrame runs in
	     //    its own thread
	     System.out.println("Main thread terminating");
		
	}
}

