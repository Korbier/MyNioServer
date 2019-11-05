package org.sc.server.protocol.http.message.response;

public enum StateCode {

	STATE_100( 100, "Continue" ),
	STATE_200( 200, "OK" ),
	STATE_300( 300, "Multiple Choices" ),
	STATE_400( 400, "Bad Request" ),
	STATE_404( 404, "Content not found" ),
	STATE_500( 500, "Internal Server Error" );
	
	private int    code  = -1;
	private String label = null;
	
	private StateCode(int code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getLabel() {
		return this.label;
	}
	
}
