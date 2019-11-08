package org.sc.server.protocol.http.message.request;

import org.sc.server.protocol.Request;
import org.sc.server.protocol.http.message.Command;
import org.sc.server.protocol.http.message.Entity;
import org.sc.server.protocol.http.message.Header;
import org.sc.server.protocol.http.message.HttpVersion;
import org.sc.server.protocol.http.message.Message;

public class HttpRequest extends Message implements Request {
	
	private String      remoteHost = null;	
	private int         remotePort = -1;
	
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
		
		this.setRemoteHost( request.getRemoteHost() );
		this.setRemotePort( request.getRemotePort() );
		
		for ( Header header : request.getHeaders() ) {
			this.setHeaderValue(header, request.getHeaderValue( header ) );
		}

		for ( Entity entity : request.getEntities() ) {
			this.setEntityValue(entity, request.getEntityValue( entity ) );
		}
		
	}
	
	public void setRemoteHost( String remoteHost ) {
		this.remoteHost = remoteHost;
	}
	
	public String getRemoteHost() {
		return this.remoteHost;
	}
	
	public void setRemotePort( int remotePort ) {
		this.remotePort = remotePort;
	}
	
	public int getRemotePort() {
		return this.remotePort;
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
	
	@Override
	public String toString() {
		return getRemoteHost() + ":" + getRemotePort() + " " + getCommand() + " " + getResource();
	}
	
}
