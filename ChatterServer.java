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
		System.out.println("Server starting ...");
		//listen for connections
		while(stillChattin)
		{
			answerThePhone();
			//Add client obj to LL
		}
		sock.close();
	}
	
	public static void main( String[] args ) throws IOException //args[0] holds the one command line arg we require
	   {
		   	new ChatterServer(args[0]);
		   	
		   	
	   }
	
	
	//this function opens the socket and waits for the call
	public void answerThePhone()
	{
	      try
	      { 
	            Socket client = sock.accept(); // this blocks until a client calls  (waiting)     
	            System.out.println("DateServer: accepts client connection ");
	            
	            ServerListens current = new ServerListens( client );
	            
	            
	            
	      }
	      catch( Exception e ) { System.err.println("DateServer: error = "+e); }      
	      System.exit(0);
	}

	
	//subclass that waits for response from others for each client
	public class ServerListens extends Thread 
	{
		Socket client; //Socket to hold the client info
		String nick; //Name of client
		Boolean clientOnline = true;
		
		
		public ServerListens(Socket c)
		{
			client = c;
			
			try{
			
				while(clientOnline)
				{
				
				}
			c.close();
			
			}
			catch( IOException e )
			{
				System.out.println("Problem in ServerListens Constructor: " + e);
			}
			
		}
		
		@Override
		public void run()
		{
			//everytime this runs, that object.start
			
		}
		
	}
	
	//function to tell other ServerListens objects what each server is doing
	//needs to be synchronized
	//must go to every client
	//opens a Writer to write to ClientListens
	
	public synchronized void tellOthers()
	{
		
	}
	
	//function that will direct message only one other client by nickname
	public synchronized void tellOnePerson(String name)
	{
		
	}

}

