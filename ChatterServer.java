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
		//sock.close();
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
	            System.out.println("Server: accepts client connection");
	            
	            ServerListens current = new ServerListens( client );
	            current.start();
	            
	            
	            
	      }
	      catch( Exception e ) { System.err.println("AnswerThePhone: error = "+e); }      
	      
	}

	
	//subclass that waits for response from others for each client
	public class ServerListens extends Thread 
	{
		Socket client; //Socket to hold the client info
		String nick; //Name of client
		Boolean clientOnline = true; //used for while loop
		
		
		//constructor takes the Socket
		//reads the nickname of the client
		public ServerListens(Socket c)
		{
			client = c;
			
			try{
				
				InputStream in = client.getInputStream();
				Scanner sc = new Scanner( in );
				nick = sc.next();
				
				//sc.close();
				
			}
			catch( IOException e )
			{
				System.out.println("IO Problem in ServerListens Constructor: " + e);
			}
			catch ( Exception e )
			{
				System.out.println("Problem in ServerListens Constructor: " + e);
			}
			
		}
		
		@Override
		public void run()
		{
			
			//everytime this runs, that object.start
			try{
				
				while(clientOnline)
				{
					
					//InputStream in = client.getInputStream();
					//Scanner sc = new Scanner( in );
					
					//String first = sc.nextLine();
					//System.out.println("this is first: " + first);
					
					InputStreamReader in = new InputStreamReader(client.getInputStream()); //reads from user
					BufferedReader bin = new BufferedReader( in ); //makes new BR for it
					
					String first = bin.readLine();
					
					if( first.contains("/nick") )
					{
					//	nick = sc.next();
					}
					else if( first.contains("/dm") )
					{
						//tellOnePerson( nick, sc.next(), sc.nextLine() );
					}
					else if( first.contains("/quit") )
					{
						clientOnline = false;
					}
					else
					{
						tellOthers( nick, first);
					}
					
					
					
					//sc.close();
				}
				
			
			
			}
			catch( IOException e )
			{
				System.out.println("Problem in ServerListens run(): " + e);
			}
			
		}
		
	}
	
	//function to tell other ServerListens objects what each server is doing
	//needs to be synchronized
	//must go to every client
	//opens a Writer to write to ClientListens
	public synchronized void tellOthers(String sender, String msg) throws IOException
	{
		System.out.println("in tellothers");
		int size = chatting.size();
		for( int i = 0; i < size; i++ )
		{
			PrintWriter pout = new PrintWriter( chatting.get(i).client.getOutputStream(), true );
			pout.println( sender + ": " + msg );
		}
		
	}
	
	//function that will direct message only one other client by nickname
	public synchronized void tellOnePerson(String sender, String name, String msg)
	{
		
	}

}

