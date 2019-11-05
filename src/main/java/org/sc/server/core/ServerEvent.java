package org.sc.server.core;

import java.nio.channels.SocketChannel;

public class ServerEvent {

	private byte []       data   = null;
	private SocketChannel source = null;
	
	public ServerEvent( SocketChannel source, byte[] data ) {
		this.source = source;
		this.data   = data;		
	}
	
	public byte [] getData() {
		return this.data;
	}
	
	public SocketChannel getSource() {
		return this.source;
	}
	
}
