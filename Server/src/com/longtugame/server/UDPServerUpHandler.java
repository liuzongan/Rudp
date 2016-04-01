package com.longtugame.server;

import java.net.DatagramPacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class UDPServerUpHandler extends
		SimpleChannelInboundHandler<DatagramPacket>
{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{

		ctx.fireChannelActive();

	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg)
			throws Exception
	{
		// TODO Auto-generated method stub

	}

}