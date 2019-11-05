package org.sc.server.protocol.http.extension;

import org.sc.server.protocol.http.message.request.HttpRequest;
import org.sc.server.protocol.http.message.response.HttpResponse;

public class DefaultExtension implements IExtension {

	@Override
	public void updateRequest(HttpRequest request) {}

	@Override
	public void updateResponse(HttpResponse response) {}


}
