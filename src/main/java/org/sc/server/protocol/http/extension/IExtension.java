package org.sc.server.protocol.http.extension;

import org.sc.server.protocol.http.message.request.HttpRequest;
import org.sc.server.protocol.http.message.response.HttpResponse;

public interface IExtension {

	public void updateRequest( HttpRequest request );
	public void updateResponse( HttpResponse response );
	
}
