package ClientServer;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ConnectionThread extends Thread{ 
	
	private int id;
	private Socket clientSocket;
 
	// -- the main server (port listener) will supply the socket
	//    the thread (this class) will provide the I/O streams
	//    BufferedReader is used because it handles String objects
	//    whereas DataInputStream does not (primitive types only)
	//private BufferedReader datain;
	private DataInputStream in;
	//private DataOutputStream dataout;
	    
	    public ConnectionThread (Socket clientSocket, int id) {
	    	
	        this.clientSocket = clientSocket;
	        this.id = id;
	        try {
	            System.out.println("Client "+id+ ": Client Connected");
	            this.in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public void readMessages(){
	        String line = "";
	        while(!line.equals("##")){
	            try {
	                line = in.readUTF();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            System.out.println("Client "+id+ ": "+ line);
	        }
	        System.out.println("Client "+id+ ": Client Disconnected");
	    }

	    public void close(){
	        try{
	            clientSocket.close();
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

