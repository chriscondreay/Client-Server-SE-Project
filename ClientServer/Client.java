package ClientServer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	// -- port and host name of server
	private static final int PORT = 8000;
	
	/* --
	 For Windows
	 From ipconfig:
	 
	 Wireless LAN adapter Wireless Network Connection:

     Connection-specific DNS Suffix  . : clunet.edu
     Link-local IPv6 Address . . . . . : fe80::1083:3e22:f5a1:a3ec%11
     IPv4 Address. . . . . . . . . . . : 199.107.222.115 <=======This address works
     Subnet Mask . . . . . . . . . . . : 255.255.240.0
     Default Gateway . . . . . . . . . : 199.107.210.2
	 
	 For MacOS
	 System Preferences -> Network -> Advanced -> TCP/IP -> IPv4 address 192.168.1.14
	 
	-- */
    private static String HOST = "10.100.48.232";
	
	// -- socket variable for peer to peer communication
	private Socket socket;

	// -- stream variables for peer to peer communication
	//    to be opened on top of the socket
	private DataInputStream datain;
	private DataOutputStream dataout;
	private Scanner in;

	private boolean connected = false;
	  	
	public Client ()
	{
		
	}
	
	public boolean connected() {
		return connected;
	}
	
	public void connect(String IP) throws IOException, UnknownHostException{
		Client.HOST = IP;
					// -- construct the peer to peer socket
			socket = new Socket(HOST, PORT);
			// -- wrap the socket in stream I/O objects
			//datain = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			dataout = new DataOutputStream(socket.getOutputStream());
			//datain = new DataInputStream(socket.getInputStream());
			in = new Scanner(System.in);
			System.out.println("Client Connected ");
			connected = true;
			sendMessages();
		
	}
	
	private void sendMessages() {
		try {
			String line = "";
	        while(!line.equals("##")){
	            line = in.nextLine();
	            dataout.writeUTF(line);
	        }
						
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void disconnect ()
	{
    	String text = "##";
		try {
			// -- the server only receives String objects that are
			//    terminated with a newline "\n"

        	// -- send a special message to let the server know 
        	//    that this client is shutting down
			//text += "\n";
			dataout.writeUTF(text);
			dataout.close();
			in.close();

			// -- close the peer to peer socket
			socket.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		
	}

	public static void main(String[] args) {
		
		// -- instantiate a Client object
		new ClientUserInterface();
	}

}
