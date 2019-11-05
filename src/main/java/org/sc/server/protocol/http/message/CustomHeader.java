package org.sc.server.protocol.http.message;

public class CustomHeader implements Header {
 
	 private String code = null;
	 
	 public CustomHeader( String code ) {
		 this.code = code;
	 }
	 
	 public HttpVersion getMinVersion() {
		 return HttpVersion.HTTP_1_1;
	 }
	 
	 @Override
	 public String toString() {
		 return this.code;
	 }	
	 	 
}
