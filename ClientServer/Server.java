package ClientServer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Server {

	// -- assign each client connection an ID. Just increment for now
	private int nextId = 0;
	
	// -- the socket that waits for client connections
	private ServerSocket serversocket;

	// -- the port number used for client communication
	private static final int PORT = 8000;
			
	// -- list of active client threads by ID number
	private Vector<ConnectionThread> clientconnections;
	
	private static int connections = 0;
	
	 private DataInputStream in;
	
	private static final String STOP_STRING = "##";
	
	public int getPort()
	{
		return PORT;
	}
	
	public Server ()
	{
		
		// -- construct the list of active client threads
		clientconnections = new Vector<ConnectionThread>();
		
		try {
			// -- open the server socket
			serversocket = new ServerSocket(getPort());
			System.out.println("Server Running...");
			while(true) inConnections();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static int getConnections() {
		return connections;
	}

	private void inConnections() throws IOException {
		Socket socket = serversocket.accept();
		connections++;
		 if(socket.isConnected())
		        new Thread(()->{
		            ConnectionThread client = new ConnectionThread(socket,connections);
		            client.readMessages();
		            client.close();
		            connections--;
		        }).start();
	}
	
	private void close() throws IOException {
        in.close();
        serversocket.close();
    }
	
	public static void main (String args[])
	{
		new ServerUserInterface();
		// -- instantiate the server anonymously
		//    no need to keep a reference to the object since it will run in its own thread
		new Server();
		
		System.out.println("Server Stopped.");
		
	}
}
