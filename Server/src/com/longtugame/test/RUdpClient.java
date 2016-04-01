package com.longtugame.test;

import java.io.IOException;
import java.net.UnknownHostException;

import net.rudp.ReliableSocket;

public class RUdpClient
{
 
	public static void main(String[] args)
	{
		try
		{
			ReliableSocket	client = new ReliableSocket("127.0.0.1",7777);
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
