package org.sc.server;

import java.nio.file.Paths;

import org.sc.server.configuration.InstanceConfiguration;
import org.sc.server.core.Server;
import org.sc.server.protocol.http.HttpProtocol;
import org.sc.server.protocol.http.handler.StaticHandler;

public class Bootstrap {

	public static void main(String[] args) {
		
		Server server = new Server();
		
		HttpProtocol protocol = new HttpProtocol();
		protocol.setHandler( new StaticHandler( Paths.get( "src/test/resources/static" ) ) );
		
		server.createInstance( new InstanceConfiguration( "localhost", 8081, protocol ) );
		server.startup();
		
	}
	
}
