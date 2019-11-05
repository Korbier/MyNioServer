package org.sc.server.protocol.http.message.request;

import org.sc.server.protocol.http.message.Header;
import org.sc.server.protocol.http.message.HttpVersion;

public enum RequestHeader implements Header {
 
	ACCEPT(              "Accept",              HttpVersion.HTTP_1_1),
	ACCEPT_CHARSET(      "Accept-Charset",      HttpVersion.HTTP_1_1),
	ACCEPT_ENCODING(     "Accept-Encoding",     HttpVersion.HTTP_1_1),
	ACCEPT_LANGUAGE(     "Accept-Language",     HttpVersion.HTTP_1_1),
	AUTHORIZATION(       "Authorization",       HttpVersion.HTTP_1_1),
	EXPECT(              "Expect",              HttpVersion.HTTP_1_1),
	FROM(                "From",                HttpVersion.HTTP_1_1),
	HOST(                "Host",                HttpVersion.HTTP_1_1),
	IF_MATCH(            "If-Match",            HttpVersion.HTTP_1_1),
	IF_MODIFIED_SINCE(   "If-Modified-Since",   HttpVersion.HTTP_1_1),
	IF_NONE_MATCH(       "If-None-Match",       HttpVersion.HTTP_1_1),
	IF_RANGE(            "If-Range",            HttpVersion.HTTP_1_1),
	IF_UNMODIFIED_SINCE( "If-Unmodified-Since", HttpVersion.HTTP_1_1),
	MAX_FORWARDS(        "Max-Forwards",        HttpVersion.HTTP_1_1),
	PROXY_AUTHORIZATION( "Proxy-Authorization", HttpVersion.HTTP_1_1),
	RANGE(               "Range",               HttpVersion.HTTP_1_1),
	REFERER(             "Referer",             HttpVersion.HTTP_1_1),
	TE(                  "TE",                  HttpVersion.HTTP_1_1),
	USER_AGENT(          "User-Agent",          HttpVersion.HTTP_1_1);
	 
	 private String      code = null;
	 private HttpVersion version = null;
	 
	 private RequestHeader(String code, HttpVersion version) {
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
		 for ( RequestHeader header : RequestHeader.values() ) {
			 if ( header.toString().equals( code ) ) return header;
		 }
		 return null;
	 }
	 
}
