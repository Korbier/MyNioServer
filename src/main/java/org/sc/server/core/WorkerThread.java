package org.sc.server.core;

import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

public class WorkerThread implements Runnable {

	private Instance instance = null; //Instance running this thread
	private Worker   worker   = null;
	
	private boolean  running  = true;
	
	//Event queue
	private List<ServerEvent> events = new LinkedList<ServerEvent>();
	
	public WorkerThread( Instance instance, Worker worker ) {
		this.instance = instance;
		this.worker   = worker;
	}
	
	public Instance getInstance() {
		return this.instance;
	}
	
	public Worker getWorker() {
		return this.worker;
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	public void terminate() {
		this.running = false;
	}
	
	public void enqueue( SocketChannel socket, byte[] data, int count ) {
		
		byte[] dataCopy = new byte[count];
		System.arraycopy(data, 0, dataCopy, 0, count);
		
		synchronized ( this.events ) {
			this.events.add(new ServerEvent( socket, dataCopy ) );
			this.events.notify();
		}
		
	}
	
	public void run() {
		
		ServerEvent event = null;

		while ( isRunning() ) {
			
			// Wait for data to become available
			synchronized ( this.events ) {
				
				while ( this.events.isEmpty() ) {
					try {
						this.events.wait();
					} catch (InterruptedException e) {
						System.err.println( e.getMessage() );
						terminate();
					}
				}
				
				event = (ServerEvent) this.events.remove(0);
				
			}

			//Returning processed data to source channel
			getInstance().getServerThread().send( event.getSource(), this.worker.process( event ) );
			
		}
		
	}
	
}
