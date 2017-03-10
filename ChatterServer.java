package ChattyRepo;
//ChatterServer.java

import java.net.*;
import java.util.*;
import java.io.*;

public class ChatterServer
{
	
	//this object stores the addresses of every ServerListens class
	//that gets instantiated
	LinkedList<ServerListens> chatting;
	ServerSocket sock; 
	int portNum;
	boolean stillChattin = true;
	
	public ChatterServer(String p) throws IOException
	{
		portNum = Integer.parseInt(p); 
		sock = new ServerSocket(portNum); //initializes and makes a socket
		chatting = new LinkedList<ServerListens>();
		
		//listen for connections
		while(stillChattin)
		{
			answerThePhone();
		}
	}
	
	public static void main( String[] args ) throws IOException //args[0] holds the one command line arg we require
	   {
		   	new ChatterServer(args[0]);
		   	
		   	
	   }
	
	
	//this function opens the socket and waits for the call
	public void answerThePhone()
	{
		 System.out.println("date server starting ...");
	      try
	      { 
	            Socket client = sock.accept(); // this blocks until a client calls  (waiting)     
	            System.out.println("DateServer: accepts client connection ");
	            
	            ServerListens person = new ServerListens( client ); //this will create a new ServerListens object for client
	            chatting.add(person); //adds this object to the linked list
	            
	            
	            
	      }
	      catch( Exception e ) { System.err.println("DateServer: error = "+e); }      
	      System.exit(0);
	}

	
	//subclass that waits for response from others for each client
	public class ServerListens
	{
		Socket client; //Socket to hold the client info
		
		
		
		public ServerListens(Socket c)
		{
			client = c;
			
		}
		
		
	}
	
	//function to tell other ServerListens objects what each server is doing
	//needs to be synchronized
	//must go to every client
	//opens a Writer to write to ClientListens
	public void tellOthers()
	{
		
	}
	
	//function that will direct message only one other client by nickname
	public void tellOnePerson(String name)
	{
		
	}

}

