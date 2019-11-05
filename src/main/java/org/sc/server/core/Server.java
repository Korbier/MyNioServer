package org.sc.server.core;

import java.util.ArrayList;
import java.util.List;

import org.sc.server.configuration.InstanceConfiguration;

public class Server {

	private List<Instance> instances = new ArrayList<>();
	
	public int createInstance( InstanceConfiguration configuration ) {
		
		int index = this.instances.size();
		this.instances.add( new Instance( configuration ) );
		
		return index;
		
	}
	
	public void startup() {
		check();
		run();
	}
	
	public void shutdown() {
		for ( Instance instance : this.instances ) {
			instance.terminate();
		}
	}
	
	private void check() {

	}
	
	private void run() {
		for ( Instance instance : this.instances ) {
			instance.start();
		}		
	}
		
}
