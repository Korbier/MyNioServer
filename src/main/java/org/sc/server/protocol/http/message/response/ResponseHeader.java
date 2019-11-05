package org.sc.server.protocol.http.message.response;

import org.sc.server.protocol.http.message.Header;
import org.sc.server.protocol.http.message.HttpVersion;

public enum ResponseHeader implements Header {
 
	ACCEPT(             "Accept-Ranges", HttpVersion.HTTP_1_1 ),
	AGE(                "Age", HttpVersion.HTTP_1_1 ),
	ETAG(               "Etag", HttpVersion.HTTP_1_1 ),
	LOCATION(           "Location", HttpVersion.HTTP_1_1 ),
	PROXY_AUTHENTICATE( "Proxy-Authenticate", HttpVersion.HTTP_1_1 ),
	RETRY_AFTER(        "Retry-After", HttpVersion.HTTP_1_1 ),
	SERVER(             "Server", HttpVersion.HTTP_1_1 ),
	VARY(               "Vary", HttpVersion.HTTP_1_1 ),
	WWW_AUTHENTICATE(   "WWW-Authenticate", HttpVersion.HTTP_1_1 );
	 
	 private String      code = null;
	 private HttpVersion version = null;
	 
	 private ResponseHeader(String code, HttpVersion version) {
		 this.code = code;
		 this.version = version;
	 }

	 public HttpVersion getMinVersion() {
		 return this.version;
	 }
	 
	 @Override
	 public String toString() {
		 return this.code;
	 }	
	 
	 public static Header getFromCode( String code ) {
		 for ( ResponseHeader header : ResponseHeader.values() ) {
			 if ( header.toString().equals( code ) ) return header;
		 }
		 return null;
	 }
	 
}
