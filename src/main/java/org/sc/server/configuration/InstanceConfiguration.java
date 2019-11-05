package org.sc.server.configuration;

import org.sc.server.core.Worker;

public class InstanceConfiguration {

	private String host   = null;
	private int    port   = -1;
	private Worker worker = null;
	
	private boolean keepAlive = false;
	
	public InstanceConfiguration( String host, int port, Worker worker ) {
		this( host, port, worker, false );
	}
	
	public InstanceConfiguration( String host, int port, Worker worker, boolean keepAlive ) {
		this.host      = host;
		this.port      = port;
		this.worker    = worker;
		this.keepAlive = keepAlive;
	}
	
	public String getHost() {
		return this.host;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public Worker getWorker() {
		return this.worker;
	}
	
	public boolean getkeepAlive() {
		return this.keepAlive;
	}
	
}
