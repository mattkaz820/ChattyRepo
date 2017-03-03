package ChattyRepo;

import java.net.*;
import java.util.*;
import java.io.*;
//TEST FROM KAZ
public class ChatterServer
{
	
	//this object stores the addresses of every ServerListens class
	//that gets instantiated
	LinkedList chatting;
	
	
	//this function opens the socket and waits for the call
	public void answerThePhone()
	{
		
	}

	
	//subclass that waits for response from others for each client
	public class ServerListens
	{
		
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

