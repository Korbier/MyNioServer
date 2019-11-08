package org.sc.server.protocol.http;

import java.nio.channels.SocketChannel;

import org.sc.server.protocol.Serializer;
import org.sc.server.protocol.http.message.request.HttpRequest;
import org.sc.server.protocol.http.message.request.RequestUnserializer;
import org.sc.server.protocol.http.message.response.HttpResponse;
import org.sc.server.protocol.http.message.response.ResponseSerializer;

public class HttpSerializer implements Serializer<HttpRequest, HttpResponse> {

	private RequestUnserializer requestUnserializer = new RequestUnserializer();
	private ResponseSerializer  responseSerializer  = new ResponseSerializer();
	
	@Override
	public byte[] serialize(HttpResponse object) {
		return responseSerializer.serialize( object );
	}

	@Override
	public HttpRequest unserialize(SocketChannel source, byte[] data) {
		return this.requestUnserializer.unserialize( source, data );
	}

}
