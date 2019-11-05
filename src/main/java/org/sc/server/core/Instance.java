package org.sc.server.core;

import org.sc.server.configuration.InstanceConfiguration;


public class Instance {

	private InstanceConfiguration configuration = null;
	
	private WorkerThread worker  = null;
	private Thread       wThread = null;
	
	private ServerThread server  = null;
	private Thread       sThread = null;
	
	public Instance( InstanceConfiguration configuration ) {
		this.configuration = configuration;
		this.init();
	}
	
	public InstanceConfiguration getConfiguration() {
		return this.configuration;
	}
	
	public WorkerThread getWorkerThread() {
		return this.worker;
	}
	
	public ServerThread getServerThread() {
		return this.server;
	}
	
	private void init() {
		
		this.worker  = new WorkerThread( this, getConfiguration().getWorker() );
		this.wThread = new Thread( this.worker );
		
		this.server  = ServerThread.create( this );
		this.sThread = new Thread( this.server );
		
	}
	
	public void start() {
		
		if ( this.server != null ) {
			this.sThread.start();
			this.wThread.start();
		}
		
	}
	

	public void terminate()  {
		this.server.terminate();
		this.worker.terminate();
	}

	public boolean isRunning() {
		return this.worker.isRunning() && this.server.isRunning();
	}
	
}
