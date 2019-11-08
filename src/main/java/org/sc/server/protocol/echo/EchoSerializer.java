package org.sc.server.protocol.echo;

import java.nio.channels.SocketChannel;

import org.sc.server.protocol.Serializer;

public class EchoSerializer implements Serializer<Echo, Echo> {

	@Override
	public byte[] serialize(Echo echo) {
		return echo.getValue();
	}

	@Override
	public Echo unserialize( SocketChannel source, byte[] data) {
		return new Echo( data );
	}

}