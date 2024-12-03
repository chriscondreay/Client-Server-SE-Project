package ClientServer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ClientServer.DB.DBaseConnection;
import ClientServer.ServerUserInterface.DisplayPanelInner;

public class ClientUserInterface extends JFrame {
	
	private final int WIDTH = 220;
    private final int HEIGHT = 180;

    private ControlPanelInner controlPanel;
    private DisplayPanelInner displayPanel;
    private boolean loginVisible = false;
    
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

    // -- create a new class Login Window from JFrame
    private class LoginWindow extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton submitButton;
        private JButton cancelButton;
        private JLabel statusLabel;
        private DBaseConnection dbConnection;

        public LoginWindow() {
            super("Login");

            // -- connection for the database, change the username and password to your MySQL credentials
            dbConnection = new DBaseConnection("root", "Nita2020!CeC");

            initializeComponents();
            setupLayout();

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(300, 200);
            setLocationRelativeTo(null);
            setResizable(false);
        }

        private void initializeComponents() {
            usernameField = new JTextField(15);
            passwordField = new JPasswordField(15);
            submitButton = new JButton("Login");
            cancelButton = new JButton("Cancel");
            statusLabel = new JLabel(" ", SwingConstants.CENTER);

            submitButton.addActionListener(e -> handleLogin());
            cancelButton.addActionListener(e -> dispose());
        }

        private void setupLayout() {
            JPanel mainPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // -- username field
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 5, 5, 5);
            mainPanel.add(new JLabel("Username:"), gbc);
            
            gbc.gridx = 1;
            mainPanel.add(usernameField, gbc);
            
            // -- password field
            gbc.gridx = 0;
            gbc.gridy = 1;
            mainPanel.add(new JLabel("Password:"), gbc);
            
            gbc.gridx = 1;
            mainPanel.add(passwordField, gbc);
            
            // -- buttons
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(submitButton);
            buttonPanel.add(cancelButton);
            
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            mainPanel.add(buttonPanel, gbc);
            
            // -- set the status
            gbc.gridy = 3;
            mainPanel.add(statusLabel, gbc);
            
            // Add main panel to frame
            add(mainPanel);
        }

        private void handleLogin() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Please fill in all fields");
                return;
            }

            statusLabel.setText("Attempting to login..." + username);
            boolean loginSuccess = dbConnection.verifyUser(username, password);
            System.out.println("Login success: " + loginSuccess);

            if (loginSuccess) {
                System.out.println("Login successful!");
                dispose();
            } else {
                statusLabel.setText("Invalid username or password");
                passwordField.setText("");
            }

        }
    }
    
    public class ControlPanelInner extends JPanel {
    	
    	// -- push buttons
        private JButton login;
        private JButton register;
        private JButton pwdRecov;
        private JButton shutdown;
        
        
        // -- fixed labels, can be changed by the program but not the user
        private JLabel errorMessages = new JLabel("   *any error messages here*   ");
        private JLabel numOfLoggedInUsers = new JLabel("  Users Logged In   ");
        
        
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
            this.add(errorMessages);
            
        }
        
        // -- construct JButtons and add event handlers
        private void prepareButtonHandlers() {
        	
        	login = new JButton("Login");
        	login.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        LoginWindow loginWindow = new LoginWindow();
                        loginWindow.setVisible(true);
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

