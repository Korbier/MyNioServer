package org.sc.server.protocol.http.message;

public enum Command {

	GET("GET"),
	HEAD("HEAD"),
	POST("POST"),
	OPTIONS("OPTIONS"),
	CONNECT("CONNECT"),
	TRACE("TRACE"),
	PUT("PUT"),
	PATCH("PATCH"),
	DELETE("DELETE");
	
	private String name = null;
	
	private Command(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public static Command getFromName( String name ) {
		for (Command command : Command.values() ) {
			if ( command.getName().equals( name ) ) return command;
		}
		return null;
	}
	
}
