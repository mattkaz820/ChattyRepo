package ChattyRepo;


import java.net.*;
import java.io.*;
import java.util.*;



public class ChatterClient
{
	
	//holds the client's nickname
	String nickname;
	Socket sock;
	
	public void setNickname(String n ) {nickname = n;}
	
	
	public ChatterClient(String h, int p) throws IOException
	{
		System.out.println(h+ "   "+ p);
		
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
	public String getUserInput()
	{
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine(); 
		
		String cmd = input.substring(0, input.indexOf(' ')-1);  
		if(cmd.equals("/nick"))
		{
			setNickname(input.substring(input.indexOf(' ')));
		}
		
		scan.close();
		
		return input;
	}
	
	
	//listens for responses from other clients
	//acts as a separate thread
	//can extend thread or implement runnable
	public class ClientListens extends Thread
	{
		try
		{		
			InputStream in = sock.getInputStream();
			BufferedReader bin = new BufferedReader( new InputStreamReader(in) );
			String line;
			line = bin.readLine();
			while( (line=bin.readLine()) != null )
			{ 
				System.out.println(nickname+ ":" + " " + line);
	        }	
	         	
	         sock.close();
	      }	
	      catch ( IOException ioe )
	      { 	
	    	  System.err.println(ioe); 
	      }	
	}	
	
	public static void main( String[] args ) throws RuntimeException, IOException // throws IOException
	   {
		   new ChatterClient(args[0],Integer.parseInt(args[1]));
		   //first argument is hostname of server, second is port number
		   
	   }
	
		
}
