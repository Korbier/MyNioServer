package org.sc.server.protocol.http.message;

public enum GenericHeader implements Header {
 
	 CACHE_CONTROl(    "Cache-Control",     HttpVersion.HTTP_1_1),
	 CONNECTION(       "Connection",        HttpVersion.HTTP_1_1),
	 DATE(             "Date",              HttpVersion.HTTP_1_1),
	 PRAGMA(           "Pragma",            HttpVersion.HTTP_1_1),
	 TRAILER(          "Trailer",           HttpVersion.HTTP_1_1),
	 TRANSFER_ENCODING("Transfer-Encoding", HttpVersion.HTTP_1_1),
	 UPGRADE(          "Upgrade",           HttpVersion.HTTP_1_1),
	 VIA(              "Via",               HttpVersion.HTTP_1_1),
	 WARNING(          "Warning",           HttpVersion.HTTP_1_1);
	 
	 private String      code    = null;
	 private HttpVersion version = null;
	 
	 private GenericHeader(String code, HttpVersion version) {
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
		 for ( GenericHeader header : GenericHeader.values() ) {
			 if ( header.toString().equals( code ) ) return header;
		 }
		 return null;
	 }
	 
}
