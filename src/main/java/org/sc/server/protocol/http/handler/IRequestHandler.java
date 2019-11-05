package org.sc.server.protocol.http.handler;

import org.sc.server.protocol.http.message.request.HttpRequest;

public interface IRequestHandler {
	public byte [] handle( HttpRequest request ) throws HandlerException;
}
