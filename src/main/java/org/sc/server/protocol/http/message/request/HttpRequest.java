package org.sc.server.protocol.http.message.request;

import org.sc.server.protocol.Request;
import org.sc.server.protocol.http.message.Command;
import org.sc.server.protocol.http.message.Entity;
import org.sc.server.protocol.http.message.Header;
import org.sc.server.protocol.http.message.HttpVersion;
import org.sc.server.protocol.http.message.Message;

public class HttpRequest extends Message implements Request {
	
	private Command     command  = null;
	private String      resource = null;
	private HttpVersion version  = null;
	
	public HttpRequest( Command command, String resource, HttpVersion version ) {
		this.command  = command;
		this.resource = resource;
		this.version  = version;
	}
	
	public HttpRequest( HttpRequest request ) {
		
		this( request.getCommand(), request.getResource(), request.getVersion() );
		
		for ( Header header : request.getHeaders() ) {
			this.setHeaderValue(header, request.getHeaderValue( header ) );
		}

		for ( Entity entity : request.getEntities() ) {
			this.setEntityValue(entity, request.getEntityValue( entity ) );
		}
		
	}
	
	public Command getCommand() {
		return this.command;
	}
	
	public String getResource() {
		return this.resource;
	}
	
	public HttpVersion getVersion() {
		return this.version;
	}
	
	
}
