package com.longtugame.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.rudp.ReliableSocketProfile;
import net.rudp.impl.SYNSegment;
import net.rudp.impl.Segment;

public class UDPServerSegmentInHandler extends ChannelInboundHandlerAdapter
{
	private byte[] buffer = new byte[2048];
	private List<String> unAckList = null;
	private AttributeKey<List<String>> unAckAttr = AttributeKey
			.valueOf(UDPServerSegmentInHandler.class.getName() + ".UnAckList");
	private ReliableSocketProfile _profile = new ReliableSocketProfile();

	private static final int CLOSED = 0; /*
										 * There is not an active or pending
										 * connection
										 */
	private static final int SYN_RCVD = 1; /*
											 * Request to connect received,
											 * waiting ACK
											 */
	private static final int SYN_SENT = 2; /* Request to connect sent */
	private static final int ESTABLISHED = 3; /* Data transfer state */
	private static final int CLOSE_WAIT = 4; /* Request to close the connection */
	private int _state = CLOSED;

	private int _sendQueueSize = 32; /* Maximum number of received segments */
	private int _recvQueueSize = 32; /* Maximum number of sent segments */

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object o)
			throws UnsupportedEncodingException
	{

		System.out.println("UDPServerSegmentInHandler");
		DatagramPacket msg = (DatagramPacket) o;
		ByteBuf buf = (ByteBuf) msg.content();
		int length = buf.readableBytes();
		buf.readBytes(buffer, 0, length);

		DatagramPacket backMsg = new DatagramPacket(
				Unpooled.wrappedBuffer("SB接收".getBytes("UTF-8")), msg.sender());
		ctx.writeAndFlush(backMsg);

		Segment segment = Segment.parse(buffer, 0, length);
		if (segment instanceof SYNSegment)
		{
			if (!ctx.hasAttr(unAckAttr))
			{
				unAckList = new ArrayList<>();
				ctx.attr(unAckAttr);

			}
		}
	}
	/**
	 * private void handleSYNSegment(SYNSegment segment) { try { switch (_state)
	 * { case CLOSED: //_counters.setLastInSequence(segment.seq()); _state =
	 * SYN_RCVD;
	 * 
	 * Random rand = new Random(System.currentTimeMillis()); _profile = new
	 * ReliableSocketProfile(_sendQueueSize, _recvQueueSize,
	 * segment.getMaxSegmentSize(), segment.getMaxOutstandingSegments(),
	 * segment.getMaxRetransmissions(), segment.getMaxCumulativeAcks(),
	 * segment.getMaxOutOfSequence(), segment.getMaxAutoReset(),
	 * segment.getNulSegmentTimeout(), segment.getRetransmissionTimeout(),
	 * segment.getCummulativeAckTimeout());
	 * 
	 * Segment syn = new SYNSegment(_counters.setSequenceNumber(rand
	 * .nextInt(UDPServer.MAX_SEQUENCE_NUMBER)), _profile.maxOutstandingSegs(),
	 * _profile.maxSegmentSize(), _profile.retransmissionTimeout(),
	 * _profile.cumulativeAckTimeout(), _profile.nullSegmentTimeout(),
	 * _profile.maxRetrans(), _profile.maxCumulativeAcks(),
	 * _profile.maxOutOfSequence(), _profile.maxAutoReset());
	 * 
	 * syn.setAck(segment.seq()); //sendAndQueueSegment(syn); break; case
	 * SYN_SENT: //_counters.setLastInSequence(segment.seq()); _state =
	 * ESTABLISHED;
	 * 
	 * //sendAck(); //connectionOpened(); break; } } catch (IOException xcp) {
	 * xcp.printStackTrace(); } }
	 **/
}
