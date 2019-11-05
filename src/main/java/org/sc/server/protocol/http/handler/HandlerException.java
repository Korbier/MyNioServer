package org.sc.server.protocol.http.handler;

import org.sc.server.protocol.http.message.response.StateCode;

public class HandlerException extends Exception {

	private static final long serialVersionUID = 4491673269638475423L;
	
	private StateCode code = null;
	
	public HandlerException( StateCode code ) {
		super( code.getCode() + " " + code.getLabel() );
		this.code = code;
	}
	
	public StateCode getStateCode() {
		return this.code;
	}
	
}
