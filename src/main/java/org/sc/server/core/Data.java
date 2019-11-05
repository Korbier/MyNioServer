package org.sc.server.core;

import java.nio.ByteBuffer;

public class Data {

	private ByteBuffer bBuffer   = null;
	
	public Data( byte [] rawData ) {
		this.bBuffer   = ByteBuffer.wrap( rawData );
	}
	
	public ByteBuffer getData() {
		return this.bBuffer;
	}
	
}
