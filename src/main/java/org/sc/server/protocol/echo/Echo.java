package org.sc.server.protocol.echo;

import org.sc.server.protocol.Request;
import org.sc.server.protocol.Response;

public class Echo implements Request, Response {

	private byte [] value = null;
	
	public Echo( byte [] value ) {
		this.value = value;
	}
	
	public byte [] getValue() {
		return this.value;
	}
}
