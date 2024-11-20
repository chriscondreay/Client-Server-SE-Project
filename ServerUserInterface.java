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
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerUserInterface extends JFrame {
	
	private final int WIDTH = 600;
    private final int HEIGHT = 600;

    private MainPanelInner mainPanel;
    
    public ServerUserInterface() {
    	
    	// -- construct the base JFrame first
        super();

        // -- set the application title
        this.setTitle("Server UI");

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
       
        // -- construct a JPanel for graphics
        mainPanel = new MainPanelInner();
        this.add(mainPanel, BorderLayout.CENTER);
        
        // -- can make it so the user cannot resize the frame
        this.setResizable(false);

        // -- show the frame on the screen
        this.setVisible(true);
        
    }
    
    public class MainPanelInner extends JPanel {
    	
    	// -- push buttons
        private JButton disconnect;
        
     // -- field to hold 1 line of text
        private JTextField textField;
        
        public MainPanelInner() {
        	
        	prepareButtonHandlers();
        	
        	setLayout(new GridBagLayout());
        	
        	// -- construct the JTextField, 15 characters wide
        	textField = new JTextField("Server On", 15);
        	textField.setEditable(false);
            // -- add items to the JPanel in order
            this.add(disconnect, new GridBagConstraints()); 
            //this.add(textField);
            
        }
        
        // -- construct JButtons and add event handlers
        private void prepareButtonHandlers() {
        	
          disconnect = new JButton("Disconnect");
        	//disconnect
        	disconnect.addActionListener(
        	new ActionListener() {
                    	
        			public void actionPerformed(ActionEvent arg0) {
                        	
        				// -- do not do anything that is time consuming
        				//    doing so will make the GUI non-responsive to the user
        				String textFieldString = textField.getText();
                        
                }
            }
        ); 
    }
}
   
    public static void main(String[] args) {
	      
	     new ServerUserInterface();
	
	     // -- this line demonstrates that the Swing JFrame runs in
	     //    its own thread
	     System.out.println("Main thread terminating");
		
	}
}

