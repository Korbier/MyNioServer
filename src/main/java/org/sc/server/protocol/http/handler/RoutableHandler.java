package org.sc.server.protocol.http.handler;

import org.sc.server.protocol.http.message.request.HttpRequest;
import org.sc.server.protocol.http.message.response.StateCode;

public class RoutableHandler implements IRequestHandler {
	
	public RoutableHandler() {
	}
	
	@Override
	public byte[] handle(HttpRequest request) throws HandlerException {
		
		
		throw new HandlerException( StateCode.STATE_404 );
		
	}
	
}
