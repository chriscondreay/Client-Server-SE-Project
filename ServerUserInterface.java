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

public class ServerUserInterface extends JFrame {
	
	private final int WIDTH = 600;
    private final int HEIGHT = 600;

    private ControlPanelInner controlPanel;
    private DisplayPanelInner displayPanel;
    
    public ServerUserInterface() {
    	
    	// -- construct the base JFrame first
        super();

        // -- set the application title
        this.setTitle("Server");

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
        
        // -- construct a JPanel
        displayPanel = new DisplayPanelInner();
        this.add(displayPanel, BorderLayout.CENTER);
        
        // -- can make it so the user cannot resize the frame
        this.setResizable(false);

        // -- show the frame on the screen
        this.setVisible(true);
        
    }
    
    public class ControlPanelInner extends JPanel {
    	
    	// -- push buttons
        private JButton refresh;
        private JButton shutdown;
        
        // -- fixed labels, can be changed by the program but not the user
        private JLabel numOfConnections = new JLabel("  Number of Connections   ");
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
        	this.add(numOfConnections);
        	this.add(connections);
        	this.add(numOfloggedInUsers);
        	this.add(users);
            this.add(refresh); 
            this.add(shutdown);
            
        }
        
        // -- construct JButtons and add event handlers
        private void prepareButtonHandlers() {
        	
        	refresh = new JButton("Refresh");
        	//disconnect
        	refresh.addActionListener(
        		new ActionListener() {
                    	
        			public void actionPerformed(ActionEvent arg0) {
                        	
        				// -- do not do anything that is time consuming
        				//    doing so will make the GUI non-responsive to the user
        				//String textFieldString = textField.getText();
                        
        			}
                }
            );
        	
        	shutdown= new JButton("Shutdown");
        }
        
        // -- sets the size of the JPanel
        public Dimension getPreferredSize()
        {
            return new Dimension(150, 500);
        }
        
    }
   
    public class DisplayPanelInner extends JPanel{
    	
    	// -- fixed labels, can be changed by the program but not the user
    	private JLabel loggedInUserEmail = new JLabel("  Logged In User Emails   ");
    	
    	private JTextArea textArea;
    	private JScrollPane emailScrollPane;

    	public DisplayPanelInner() {
  
    		super();
    		
    		setLayout(new FlowLayout());
    		
    		textArea = new JTextArea(30,75);
    		this.add(textArea);
    		
    		this.add(loggedInUserEmail);
    	}
    	
    	// -- sets the size of the JPanel
        public Dimension getPreferredSize()
        {
            return new Dimension(100, 100);
        }
    	
    }
    
    public static void main(String[] args) {
	      
	     new ServerUserInterface();
	
	     // -- this line demonstrates that the Swing JFrame runs in
	     //    its own thread
	     System.out.println("Main thread terminating");
		
	}
}
