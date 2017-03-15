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
	            
	            int currentIndex = chatting.size();
	            ServerListens current = new ServerListens( client, currentIndex );
	            chatting.add(current);
	            System.out.println("Size of chatting: "+ chatting.size());
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
		int index; //holds the index of chatting for this
		
		
		//constructor takes the Socket
		//reads the nickname of the client
		public ServerListens(Socket c, int i)
		{
			System.out.println("in serverListens constructor");
			client = c;
			index = i;
		}
		
		@Override
		public void run()
		{
			
			//everytime this runs, that object.start
			try{
				System.out.println("in run function");
				InputStream in = client.getInputStream();
				Scanner sc = new Scanner( in );
				nick = sc.next();
				
				System.out.println("nickname is " + nick);
				
				while(clientOnline)
				{
					System.out.println("In clientonline loop");
					

					String first = sc.next();
					
					System.out.println("first is " + first);
					
					
					//System.out.println("this is first: " + first);
					
					//InputStreamReader in = new InputStreamReader(client.getInputStream()); //reads from user
					//BufferedReader bin = new BufferedReader( in ); //makes new BR for it
					
					//String first = bin.readLine();
					
					if( first.contains("/nick") )
					{
						System.out.println("In nick if");
						nick = sc.next();
					}
					else if( first.contains("/dm") )
					{
						System.out.println("In dm if");
						tellOnePerson( nick, sc.next(), sc.nextLine() );
					}
					else if( first.contains("/quit") )
					{
						System.out.println("In quit if");
						clientOnline = false; //tells to exit while loop
						chatting.remove(index); //remove this object from LL
						
						for(int j = index; j < chatting.size(); j++  )
						{
							chatting.get(j).index--; //decrease other indices by 1
						}
					}
					else
					{
						System.out.println("In tellOthers if");
						String all = first + " " + sc.nextLine();
						tellOthers( nick, all, index);
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
	public synchronized void tellOthers(String sender, String msg, int index) throws IOException
	{
		for( int i = 0; i < chatting.size(); i++ )
		{
			if(i == index)
			{
				//do not send it
			}
			else
			{
				PrintWriter pout = new PrintWriter( chatting.get(i).client.getOutputStream(), true );
				System.out.println("in for loop delivering to: " + chatting.get(i).nick);
				pout.write(sender + ": " + msg +'\n');
				pout.flush();
				//pout.println( sender + ": " + msg );
				System.out.println("test: " + sender + ": " + msg);
			}
		}
	}	
	
	/*
	OutputStream out = sock.getOutputStream();
    BufferedWriter bout = new BufferedWriter( new OutputStreamWriter( out ) );
    bout.write(nickname + '\n');
    bout.flush();	
    while(!sock.isClosed())
    {
   	 bout.write(getUserInput() + '\n');
   	 bout.flush();
    }
	
	*/
		
	//function that will direct message only one other client by nickname
	public synchronized void tellOnePerson(String sender, String name, String msg) throws IOException
	{
		for( int i = 0; i < chatting.size(); i++ )
		{
			if( chatting.get(i).nick.equals(name) )
			{
				PrintWriter pout = new PrintWriter( chatting.get(i).client.getOutputStream(), true );
				System.out.println("in for loop delivering to: " + chatting.get(i).nick);
				pout.write(sender + ": " + msg +'\n');
				pout.flush();
				//pout.println( sender + ": " + msg );
				System.out.println("test: " + sender + ": " + msg);
			}
			
		}
	}

}

