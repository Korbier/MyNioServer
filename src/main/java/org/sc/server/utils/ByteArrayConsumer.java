package org.sc.server.utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ByteArrayConsumer {

	private InnerByteBuffer buffer   = null;
	private List<Byte>      triggers = new ArrayList<Byte>();
	
	private List<ByteArrayConsumerListener> listeners = new ArrayList<ByteArrayConsumerListener>();
	
	public void consume( byte [] data ) {
		
		resetBuffer();
		
		for ( byte b : data ) {
			
			getBuffer().write( b );
			
			if ( shouldTrigger( b ) ) {
				trigger(this, b, getBuffer().getBuffer());
				resetBuffer();
			}
			
		}
		
		cleanBuffer();
		
	}
	
	private boolean shouldTrigger( byte b ) {
		return triggers.contains( b );
	}
	
	private InnerByteBuffer getBuffer() {
		return this.buffer;
	}
	
	private void resetBuffer() {
		try {
			if ( this.buffer != null ) this.buffer.close();
			this.buffer = new InnerByteBuffer();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void cleanBuffer() {
		try {
			if ( this.buffer != null ) {
				this.buffer.close();
				this.buffer = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void addTrigger( Byte b ) {
		this.triggers.add( b );
	}
	
	public void removeTrigger( Byte b ) {
		this.triggers.remove( b );
	}
	
	public void addListener( ByteArrayConsumerListener listener ) {
		this.listeners.add( listener );
	}
	
	public void removeListener( ByteArrayConsumerListener listener ) {
		this.listeners.remove( listener );
	}
	
	private void trigger( ByteArrayConsumer consumer, byte trigger, byte [] buffer ) {
		for ( ByteArrayConsumerListener listener : this.listeners ) {
			listener.onTrigger( trigger, buffer );
		}
	}
		
	private static class InnerByteBuffer extends ByteArrayOutputStream {
		
		public byte [] getBuffer() {
			return this.buf;
		}
		
	}
	
}
