package org.sc.server.core;


public interface Worker {

	public abstract Data process( ServerEvent event );
	
}
