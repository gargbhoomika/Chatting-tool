package chat;

import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Chat 
{
	private static URL read, send;
	private static URLConnection readConnection, sendConnection;
	private static InputStream inputData, respCode;
	private static Scanner sc;
	public static void main(String[] args) 
	{
		sc = new Scanner(System.in);
		System.out.println("Enter the ip of the other system: ");
		String ip = sc.next();
		Thread t = new Thread(new Runnable()
		{
			public void run()
			{
				String old = "";
				while (true)
				{
					try
					{
						read = new URL("http://"+ip+"/getMsg.php");
						readConnection = read.openConnection();
						inputData = readConnection.getInputStream();
						int ch;
						String newmsg = "";
						while ((ch = inputData.read()) != -1)
							newmsg = newmsg + (char)ch;
						if (!old.trim().equals(newmsg.trim()))
						{
							System.out.println("Received: " + newmsg.trim());
							old = newmsg.trim();
						}
					}catch(Exception e) {System.out.println(e);}
				}
			}
		});
		
		t.start();
		
		while (true)
		{
			String myMsg = sc.nextLine();
			String [] breakData = myMsg.split(" ");
			String finalMsg = "";
			for (int i = 0; i <= breakData.length - 1; i++)
				finalMsg = finalMsg + breakData[i] + "%20";
			try
			{
				send = new URL("http://"+ip+"/setMsg.php?message=" + finalMsg.trim());
				sendConnection = send.openConnection();
				sendConnection.setDoOutput(true);
				respCode = sendConnection.getInputStream();
			}catch(Exception e){System.out.println(e);}
		}
	}
}