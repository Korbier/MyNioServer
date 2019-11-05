package org.sc.server.protocol.http.message;

public enum HttpVersion {

	HTTP_1_1( 1, 1 );
	
	private int major = -1;
	private int minor = -1;
	
	private HttpVersion( int major, int minor ) {
		this.major = major;
		this.minor = minor;
	}
	
	public int getMajorVersion() {
		return this.major;
	}
	
	public int getMinorVersion() {
		return this.minor;
	}
	
	@Override
	public String toString() {
		return "HTTP/" + this.getMajorVersion() + "." + this.getMinorVersion();
	}
		
	public static HttpVersion getFromRequest( String requestVersion ) {
		
		if ( requestVersion == null ) return null;
		
		for ( HttpVersion version : HttpVersion.values() ) {
			if ( requestVersion.equals( version.toString() ) ) return version;
		}
		
		return null;
		
	}
	
}
