package com.longtugame.test;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import net.rudp.ReliableServerSocket;

public class RUdpServer
{
	public static void main(String[] args)
	{
		int port = 7777;
	
		try
		{
			InetAddress ip = InetAddress.getLocalHost();
			DatagramSocket dsocket = new DatagramSocket(port);
			ReliableServerSocket socket = new ReliableServerSocket(dsocket,0);
			//socket.accept();
			 
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
