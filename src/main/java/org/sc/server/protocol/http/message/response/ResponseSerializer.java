package org.sc.server.protocol.http.message.response;

import org.sc.server.protocol.http.message.Entity;
import org.sc.server.protocol.http.message.Header;
import org.sc.server.protocol.http.message.HttpVersion;

public class ResponseSerializer {

	public final static String SPACE     = " ";
	public final static String LINE_FEED = "\n";
	
	public byte [] serialize( HttpResponse response ) {

		HttpVersion version = response.getVersion();
		StateCode   state   = response.getCode();
		
		byte [] bHeader = null;
		byte [] bBody   = null;
		
		if (  response.getContent() != null ) bBody = response.getContent();
		else                                  bBody = new byte[0];
		
		String sHeader = version + SPACE + Integer.toString( state.getCode() ) + SPACE + state.getLabel() + LINE_FEED;
		for ( Header header : response.getHeaders()  ) sHeader += header.toString() + ": " + response.getHeaderValue( header ) + LINE_FEED ;
		for ( Entity entity : response.getEntities() ) sHeader += entity.toString() + ": " + response.getEntityValue( entity ) + LINE_FEED ;
		sHeader += LINE_FEED;
		
		bHeader = sHeader.getBytes();
		
		byte [] bResult = new byte[bHeader.length + bBody.length];
		
		System.arraycopy(bHeader, 0, bResult, 0, bHeader.length);
		System.arraycopy(bBody, 0,   bResult, bHeader.length, bBody.length);
		
		return bResult;
		
	}
	
}
