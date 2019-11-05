package org.sc.server.protocol.echo;

import org.sc.server.protocol.Protocol;


public class EchoProtocol extends Protocol<Echo, Echo> {

	public EchoProtocol() {
		super( new EchoSerializer() );
	}

	@Override
	protected Echo getResponse(Echo request) {
		System.out.println("Sending response " + new String( request.getValue() ) );
		return request;
	}
	
}
