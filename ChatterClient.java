package ChattyRepo;


import java.net.*;
import java.io.*;
import java.util.*;
import java.net.Socket;


public class ChatterClient
{
	InputStreamReader in = new InputStreamReader(System.in); //reads from user
	BufferedReader bin = new BufferedReader( in ); //makes new BR for it
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
			
			
			nickname = bin.readLine();
			
			 OutputStream out = sock.getOutputStream();
	         BufferedWriter bout = new BufferedWriter( new OutputStreamWriter( out ) );
	         bout.write(nickname + '\n');
	         bout.flush();	
	         while(!sock.isClosed())
	         {
	        	 bout.write(getUserInput() + '\n');
	        	 bout.flush();
	        	 System.out.println( "hey" +bin.readLine());
	         }
	         
	         
		}
		catch( IOException ioe )
		{ System.err.println(ioe); }
		
	}
	
	//waits for the user to type something and when "enter" is hit it sends
	//sends the information to a ServerListens object
	public String getUserInput() throws IOException
	{
		
		
		String input = bin.readLine();
		
		System.out.println("Input is: " + input);
		
		if(input.contains("/nick"))
		{
			setNickname(input.substring(input.indexOf(' ')));
		}
		
		
		return input;
	}
	
	
	//listens for responses from other clients
	//acts as a separate thread
	//can extend thread or implement runnable
	public class ClientListens extends Thread
	{
	
		@Override
		public void run()
		{
			try
			{		
				InputStream instream = sock.getInputStream();
				BufferedReader br = new BufferedReader( new InputStreamReader(instream) );
				String line;
				line = br.readLine();
				while( (line=br.readLine()) != null )
				{ 
					System.out.println(nickname+ ":" + " " + line);
		        }	
		         	
		         //sock.close();
		      }	
		      catch ( IOException ioe )
		      { 	
		    	  System.err.println(ioe); 
		      }	
		}
		
		
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
