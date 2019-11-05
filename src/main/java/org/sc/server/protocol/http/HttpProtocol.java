package org.sc.server.protocol.http;

import java.util.ArrayList;
import java.util.List;

import org.sc.server.protocol.Protocol;
import org.sc.server.protocol.http.extension.IExtension;
import org.sc.server.protocol.http.handler.HandlerException;
import org.sc.server.protocol.http.handler.IRequestHandler;
import org.sc.server.protocol.http.message.request.HttpRequest;
import org.sc.server.protocol.http.message.response.HttpResponse;
import org.sc.server.protocol.http.message.response.StateCode;

public class HttpProtocol extends Protocol<HttpRequest, HttpResponse> {
	
	private IRequestHandler  handler    = null;
	private List<IExtension> extensions = new ArrayList<>(); 
	
	public HttpProtocol() {
		super( new HttpSerializer() );
	}

	public void setHandler( IRequestHandler handler ) {
		this.handler = handler;
	}
	
	@Override
	protected HttpResponse getResponse(HttpRequest request) {
		
		for ( IExtension extension : getExtensions() ) {
			extension.updateRequest( request );
		}
		
		try {
			
			byte [] responseContent = this.handler.handle( request );
			if ( responseContent == null ) {
				return HttpResponse.error500( request.getVersion() );
			}
			
			HttpResponse response = new HttpResponse(request.getVersion(), StateCode.STATE_200 );
			response.setContent( responseContent );
			
			for ( IExtension extension : getExtensions() ) {
				extension.updateResponse( response );
			}
			
			return response;
			
		} catch (HandlerException ex) {
			return new HttpResponse( request.getVersion(), ex.getStateCode() );
		}
		
	}
	
	private List<IExtension> getExtensions() {
		return this.extensions;
	}
	
}
