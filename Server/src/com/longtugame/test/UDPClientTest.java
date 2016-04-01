package com.longtugame.test;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClientTest extends Thread
{
	private InetAddress host;
	private int  port;
	byte[] bytes=new byte[1024];
	
	UDPClientTest(InetAddress host,int port)
	{
		this.host = host;
		this.port = port;
		try
		{
			sendSocket = new DatagramSocket();
		} catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	DatagramSocket sendSocket;

	public void run()
	{
		DatagramPacket packet=new DatagramPacket(bytes,bytes.length);
		while(true)
		{
			try
			{
				System.out.println("=====================================");
				sendSocket.receive(packet);
				byte[] data = packet.getData();
				System.out.print(new String(data,CharsetUtil.UTF_8));
				System.out.println("=====================================");
	
		
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void Send() throws IOException
	{
 
		String mes = "形如测试";

 
		byte[] buf = mes.getBytes("UTF-8");
 
		DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, host,
				port);

		sendSocket.send(sendPacket);
 
		// ByteArrayOutputStream
		//sendSocket.close();
	}
	
	public static void main(String[] args) throws IOException
	{
		int port = 7777;
		InetAddress ip = InetAddress.getLocalHost();
		UDPClientTest test = new UDPClientTest(ip,port);

		test.Send();
		test.start();
	}
}
