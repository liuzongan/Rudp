package com.longtugame.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class UDPServer
{
	public static final int MAX_SEQUENCE_NUMBER = 255;



	// 相比于TCP而言，UDP不存在客户端和服务端的实际链接，因此
	// 不需要为连接(ChannelPipeline)设置handler
	public void run(int port) throws Exception
	{
		EventLoopGroup group = new NioEventLoopGroup();
		try
		{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class)
					.option(ChannelOption.SO_BROADCAST, true)
					.handler(new LoggingHandler(LogLevel.INFO));

			NioDatagramChannel channel = (NioDatagramChannel) b.bind(port)
					.sync().channel();
			// channel.pipeline().addLast(new
			// StringEncoder(Charset.forName("utf8")));
			// channel.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
			channel.pipeline().addLast(new UDPServerSecureHandler());
			channel.pipeline().addLast(new UDPServerSegmentInHandler());
			channel.closeFuture().await();
		} finally
		{
			group.shutdownGracefully();
		}

	}

	public static void main(String[] args) throws Exception
	{
		int port = 7777;
		if (args.length > 0)
		{
			try
			{
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
		}
		new UDPServer().run(port);
	}

}
