package org.sc.server.protocol.http.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Message {
	
	private Map<Header, String> header = new HashMap<Header, String>();
	private Map<Entity, String> entity = new HashMap<Entity, String>();
	
	public Set<Header> getHeaders() {
		return this.header.keySet();
	}
	
	public String getHeaderValue( Header key ) {
		return this.header.get( key );
	}
	
	public void setHeaderValue( Header header, String value ) {
		this.header.put( header, value);
	}
	
	public Set<Entity> getEntities() {
		return this.entity.keySet();
	}
	
	public String getEntityValue( Entity key ) {
		return this.entity.get( key );
	}
	
	public void setEntityValue( Entity entity, String value ) {
		this.entity.put( entity, value);
	}
}
