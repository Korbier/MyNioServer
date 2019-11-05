package org.sc.server.protocol.http.message;

public enum Entity {
 
	ALLOW(            "Allow",            HttpVersion.HTTP_1_1),
	CONTENT_ENCODING( "Content-Encoding", HttpVersion.HTTP_1_1),
	CONTENT_LANGUAGE( "Content-Language", HttpVersion.HTTP_1_1),
	CONTENT_LENGTH(   "Content-Length",   HttpVersion.HTTP_1_1),
	CONTENT_LOCATION( "Content-Location", HttpVersion.HTTP_1_1),
	CONTENT_MD5(      "Content-MD5",      HttpVersion.HTTP_1_1),
	CONTENT_RANGE(    "Content-Range",    HttpVersion.HTTP_1_1),
	CONTENT_TYPES(    "Content-Type",     HttpVersion.HTTP_1_1),
	EXPIRES(          "Expires",          HttpVersion.HTTP_1_1),
	LAST_MODIFIED(    "Last-Modified",    HttpVersion.HTTP_1_1),
	EXTENSION_HEADER( "extension-header", HttpVersion.HTTP_1_1);
    
	 private String      code    = null;
	 private HttpVersion version = null;
	 
	 private Entity(String code, HttpVersion version) {
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
	 
	 public static Entity getFromCode( String code ) {
		 for ( Entity entity : Entity.values() ) {
			 if ( entity.toString().equals( code ) ) return entity;
		 }
		 return null;
	 }
	 
}
