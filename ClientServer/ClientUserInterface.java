package ClientServer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import org.mindrot.jbcrypt.BCrypt;

import ClientServer.DB.DBaseConnection;
import ClientServer.ServerUserInterface.DisplayPanelInner;

public class ClientUserInterface extends JFrame {
	
    private final int WIDTH = 300;
    private final int HEIGHT = 200;
    
    private ConnectWindow connectWindow;
    private ControlPanelInner controlPanel;
    private DisplayPanelInner displayPanel;
    private boolean loginVisible = false;
    private Client client;
    //private JFrame main;
    
    private int loginCount;
    
    public ClientUserInterface() {
    	
    	// -- construct the base JFrame first
        super();
        
        client = new Client();
        
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
       
        connectWindow = new ConnectWindow();
        this.add(connectWindow,BorderLayout.CENTER);
        // -- construct a JPanel
        controlPanel = new ControlPanelInner();
        
        // -- can make it so the user cannot resize the frame
        this.setResizable(false);

        // -- show the frame on the screen
        this.setVisible(true);
        
    }
    
    public void updateFrame(JPanel window) {
    	super.add(window);
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
                loginCount = 0;
                dispose();
            } else {
            	if (loginCount != 4) {
            		++loginCount;
                	int attempts = 4 - loginCount;
                    statusLabel.setText("Invalid username or password. " + attempts + " remaining.");
                    passwordField.setText("");
            	} else {
            		statusLabel.setText("Too many login attempts. Please Recover Password or Register.");
            		dispose();
            	}
                
            }

        }
    }

    private class RegisterWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton cancelButton;
    private JLabel statusLabel;
    private DBaseConnection dbConnection;

    public RegisterWindow() {
        super("Register");
        
        // Use your MySQL credentials here
        String dbUsername = "root"; // your MySQL username
        String dbPassword = "Nita2020!CeC"; // your MySQL password
        dbConnection = new DBaseConnection(dbUsername, dbPassword);
        
        initializeComponents();
        setupLayout();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 250);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initializeComponents() {
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
        statusLabel = new JLabel(" ", SwingConstants.CENTER);

        registerButton.addActionListener(e -> handleRegistration());
        cancelButton.addActionListener(e -> dispose());
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Common constraints for labels
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;

        // Username label
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Username:"), gbc);
        
        // Username field
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make the field fill horizontal space
        gbc.weightx = 1.0; // Give extra horizontal space to the field
        mainPanel.add(usernameField, gbc);
        
        // Password label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE; // Reset fill for label
        gbc.weightx = 0.0; // Reset weight for label
        mainPanel.add(new JLabel("Password:"), gbc);
        
        // Password field
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(passwordField, gbc);
        
        // Confirm Password label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        mainPanel.add(new JLabel("Confirm Password:"), gbc);
        
        // Confirm Password field
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(confirmPasswordField, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, gbc);
        
        // Status label
        gbc.gridy = 4;
        mainPanel.add(statusLabel, gbc);
        
        // Add some padding around the main panel
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        
        add(wrapperPanel);
        
        // Make the window slightly bigger
        setSize(350, 250);
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Basic validation
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            statusLabel.setText("Please fill in all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            statusLabel.setText("Passwords do not match");
            passwordField.setText("");
            confirmPasswordField.setText("");
            return;
        }

        if (dbConnection.checkUsername(username)) {
            statusLabel.setText("Username already exists");
            return;
        }
    
        if (dbConnection.registerUser(username, password)) {
            statusLabel.setText("Registration successful!");
            // Optional: Close the window after successful registration
            Timer timer = new Timer(1500, e -> dispose());
            timer.setRepeats(false);
            timer.start();
        } else {
            statusLabel.setText("Registration failed");
        }
    }
}
    
    public class ConnectWindow extends JPanel {
    	// -- push buttons
        private JButton connect;
        private JTextField ipAddress;
        private JLabel statusLabel;
        
        
        public ConnectWindow() {
        	
        	ipAddress = new JTextField(15);
        	statusLabel = new JLabel(" ", SwingConstants.CENTER);
        	
        	prepareButtonHandlers();
        	
        	setLayout(new GridBagLayout());
        	GridBagConstraints gbc = new GridBagConstraints();
        	
        	 gbc.gridx = 0;
             gbc.gridy = 0;
             gbc.anchor = GridBagConstraints.WEST;
             gbc.insets = new Insets(5, 5, 5, 5);
             this.add(new JLabel("IP Address:"), gbc);
             
             gbc.gridx = 1;
             this.add(ipAddress, gbc);
             
             JPanel buttonPanel = new JPanel(new FlowLayout());
             buttonPanel.add(connect);
             
             gbc.gridx = 0;
             gbc.gridy = 2;
             gbc.gridwidth = 2;
             gbc.fill = GridBagConstraints.HORIZONTAL;
             this.add(buttonPanel, gbc);
 
             gbc.gridy = 3;
             this.add(statusLabel, gbc);
        }
        
        	private void prepareButtonHandlers() {
            	
            	connect = new JButton("Connect");
            	connect.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                        	String ip = ipAddress.getText();
                        	
                        	if (ip.isEmpty()) {
                                statusLabel.setText("Please fill in IP field");
                                return;
                            }

                            statusLabel.setText("Attempting to connect");
                        	
                        	clientConnect(client, ip);
//                            
//                        	if (client.getConnected()) {
                        		connectWindow.setVisible(false);
                                updateFrame(controlPanel);
                                controlPanel.setVisible(true);	
//                        	} else {
//                        		statusLabel.setText("Connection Failed. Try Again.");
//                        	}
                        }
                    }
                );
            }
        	
        	private void clientConnect(Client client, String ip) {
        		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						try {
							client.connect(ip);
						} catch (IOException e){
							System.out.println(e.getStackTrace());
						}
						
						return null;
					}
        		};
        
        		worker.execute();
    
        	}
            
            // -- sets the size of the JPanel
            public Dimension getPreferredSize()
            {
                return new Dimension(200, 500);
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
            errorMessages.setVisible(false);
            
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
            register.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        RegisterWindow registerWindow = new RegisterWindow();
                        registerWindow.setVisible(true);
                    }
                }
            );

        	pwdRecov = new JButton("Recovery Password");
        	
        	shutdown = new JButton("Disconnect"); 
        	shutdown.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                             clientDisconnect(client);
                             controlPanel.setVisible(false);
                             connectWindow.setVisible(true);
                             connectWindow.ipAddress.setText("");
                             connectWindow.statusLabel.setText("Successfully Disconnected from Server");
                        }
                    }
                );
        	
        }
        
        private void clientDisconnect(Client client) {
    		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

				@Override
				protected Void doInBackground() throws Exception {
					
					client.disconnect();
					
					return null;
				}
    		};
    
    		worker.execute();
    		
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
