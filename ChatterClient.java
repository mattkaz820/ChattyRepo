package ChattyRepo;


import java.net.*;
import java.io.*;
import java.util.*;

import DateDemo.DateClient;



public class ChatterClient
{
	//dadsf
	//holds the client's nickname
	String nickname;
	Socket sock;
	
	public ChatterClient(String h, int p) throws IOException
	{
		sock = new Socket(h,p);
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
		   new ChatterClient(args[0],Integer.parseInt(args[1]));
	   }
	
	
}
