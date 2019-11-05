package org.sc.server.protocol.http.handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.sc.server.protocol.http.message.request.HttpRequest;
import org.sc.server.protocol.http.message.response.StateCode;

public class StaticHandler implements IRequestHandler {

	private Path root = null;
	
	public StaticHandler( Path root ) {
		this.root = root;
	}
	
	@Override
	public byte[] handle(HttpRequest request) throws HandlerException {
		
		String resource = request.getResource();
		if ( resource.startsWith( "/" ) ) resource = request.getResource().substring(1);
		if ( resource.trim().isEmpty() )  resource = "index.html";
		
		Path ressourcepath = this.root.resolve( resource );
		
		if ( ressourcepath.toFile().exists() ) {
			try {
				return Files.readAllBytes( ressourcepath );
			} catch (IOException ex) {
				ex.printStackTrace();
				throw new HandlerException( StateCode.STATE_500 );
			}
		}
		
		throw new HandlerException( StateCode.STATE_404 );
		
	}
	
}
