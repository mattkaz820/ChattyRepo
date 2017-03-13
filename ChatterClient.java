package ChattyRepo;


import java.net.*;
import java.io.*;
import java.util.*;
import java.net.Socket;


public class ChatterClient
{
	//BufferedReader in;
	//PrintWriter out;
	//holds the client's nickname
	String nickname;
	Socket sock;
	
	public ChatterClient(String h, int p) throws IOException
	{
		
		try
		{
			sock = new Socket(h,p);
			
			//prompt for nickname
			System.out.println("What is your preferred nickname?");
			InputStreamReader in = new InputStreamReader(System.in); //reads from user
			BufferedReader bin = new BufferedReader( in ); //makes new BR for it
			
			
			nickname = bin.readLine();
			sock.close();
		}
		catch( IOException ioe )
		{ System.err.println(ioe); }
		
	}
	
	//waits for the user to type something and when "enter" is hit it sends
	//sends the information to a ServerListens object
	public void getUserInput()
	{
		
	}
	
	//listens for responses from other clients
	//acts as a separate thread
	//can extend thread or implement runnable
	public class ClientListens extends Thread
	{
		
	}
	
	public static void main( String[] args ) throws RuntimeException, IOException // throws IOException
	   {
		   ChatterClient client = new ChatterClient(args[0],Integer.parseInt(args[1]));
		   //client.ChatterClient();
		   //first argument is hostname of server, second is port number
		   //client is always listening (one thread)
		   //client is always ready to send (second thread)
	   }
	
	
}
