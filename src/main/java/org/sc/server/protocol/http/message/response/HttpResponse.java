package org.sc.server.protocol.http.message.response;

import org.sc.server.protocol.Response;
import org.sc.server.protocol.http.message.HttpVersion;
import org.sc.server.protocol.http.message.Message;

public class HttpResponse extends Message implements Response {

	private StateCode   state   = null;
	private HttpVersion version = null;
	
	private byte [] content = null;
	
	public HttpResponse( HttpVersion version, StateCode state ) {
		this.state   = state;
		this.version = version;
	}
	
	public StateCode getCode() {
		return this.state;
	}
	
	public HttpVersion getVersion() {
		return this.version;
	}
	
	public void setContent( byte [] content ) {
		this.content = content;
	}
	
	public byte [] getContent() {
		return this.content;
	}
	
	public static HttpResponse create( HttpVersion version, StateCode code ) {
		return new HttpResponse(version, code );
	}
	
	public static HttpResponse error500( HttpVersion version ) {
		return create( version, StateCode.STATE_500 );
	}
	
	public static HttpResponse error404( HttpVersion version ) {
		return create( version, StateCode.STATE_404 );
	}
	
}
