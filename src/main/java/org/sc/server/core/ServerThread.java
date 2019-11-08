package org.sc.server.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.sc.server.configuration.InstanceConfiguration;

public class ServerThread implements Runnable {

	private Instance instance = null; //Instance running this thread
	private boolean  running  = true;

	private ServerSocketChannel            serverChannel; // The channel on which we'll accept connections
	private Selector                       selector;     // The selector we'll be monitoring
	private ByteBuffer                     readBuffer     = ByteBuffer.allocate(8192); //The buffer into which we'll read data when it's available
	private List<ChangeRequest>            changeRequests = new LinkedList<ChangeRequest>(); // A list of ChangeRequest instances
	private Map<SocketChannel, List<Data>> pendingData    = new HashMap<SocketChannel, List<Data>>(); // Maps a SocketChannel to a list of ByteBuffer instances
	
	public static ServerThread create( Instance instance ) {
		
		try {
			
			ServerThread sthread = new ServerThread( instance );
			sthread.initSelector();
			return sthread;
			
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
	
	private ServerThread( Instance instance ) {
		this.instance = instance;
	}
	
	public void terminate() {
		this.running = false;
	}
	
	public boolean isRunning() {
		return this.running;
	}

	@Override
	public void run() {

		while ( this.running ) {
			
			try {

				// Process any pending changes
				synchronized (this.changeRequests) {
					Iterator<ChangeRequest> changes = this.changeRequests.iterator();
					while (changes.hasNext()) {
						ChangeRequest change = (ChangeRequest) changes.next();
						SelectionKey  key    = change.socket.keyFor( this.selector );
						if ( key != null && key.isValid() ) {
							switch (change.type) {
								case ChangeRequest.CHANGEOPS:
									key.interestOps(change.ops);
									break;
							}
						}
					}
					this.changeRequests.clear();
				}
		        
				// Wait for an event one of the registered channels
				this.selector.select();

				// Iterate over the set of keys for which events are available
				Iterator<SelectionKey> selectedKeys = this.selector.selectedKeys().iterator();
				while (selectedKeys.hasNext()) {
					
					SelectionKey key = selectedKeys.next();
					selectedKeys.remove();
					
					if (!key.isValid()) continue;

					// Check what event is available and deal with it
					     if (key.isAcceptable()) this.accept(key);
					else if (key.isReadable())   this.read(key);
					else if (key.isWritable())   this.write(key);
					     
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println("SThread stopped");
		
	}
	
	public void send( SocketChannel socket, Data data ) {

		synchronized (this.changeRequests) {
			
			// Indicate we want the interest ops set changed
			this.changeRequests.add(new ChangeRequest(socket, ChangeRequest.CHANGEOPS, SelectionKey.OP_WRITE));

			// And queue the data we want written
			synchronized (this.pendingData) {
				List<Data> queue = this.pendingData.get(socket);
				if (queue == null) {
					queue = new ArrayList<>();
					this.pendingData.put(socket, queue);
				}
				if ( data != null ) {
					queue.add( data );
				}
			}
			
		}

		// Finally, wake up our selecting thread so it can make the required changes
		this.selector.wakeup();
	}
	
	private void accept(SelectionKey key) throws IOException {
		
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel(); // For an accept to be pending the channel must be a server socket channel.
		serverSocketChannel.configureBlocking( false ); // Accept the connection and make it non-blocking
		
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking( false );

		// Register the new SocketChannel with our Selector, indicating
		// we'd like to be notified when there's data waiting to be read
		socketChannel.register(this.selector, SelectionKey.OP_READ);
		
	}
	
	private void read(SelectionKey key) throws IOException {
		
		SocketChannel socketChannel = (SocketChannel) key.channel();

		// Clear out our read buffer so it's ready for new data
		this.readBuffer.clear();

		// Attempt to read off the channel
		int numRead;
		try {
			numRead = socketChannel.read(this.readBuffer);
		} catch (IOException e) {
			
			e.printStackTrace();
			
			// The remote forcibly closed the connection, cancel the selection key and close the channel.
			key.cancel();
			socketChannel.close();
			return;
			
		}

		if (numRead == -1) {
			// Remote entity shut the socket down cleanly. Do the same from our end and cancel the channel.
			key.channel().close();
			key.cancel();
			return;
		}

		// Hand the data off to our worker thread
		this.instance.getWorkerThread().enqueue( socketChannel, this.readBuffer.array(), numRead );
		
	}
	
	private void write(SelectionKey key) throws IOException {
		
		SocketChannel socketChannel = (SocketChannel) key.channel();

		synchronized (this.pendingData) {

			List<Data> queue = this.pendingData.get(socketChannel);

			// Write until there's not more data ...
			while (!queue.isEmpty()) {

				Data data = queue.get(0);
				
				ByteBuffer buf       = data.getData();
				boolean    keepAlive = getConfiguration().getkeepAlive();
				
				socketChannel.write(buf);
				if (buf.remaining() > 0) {
					break;// ... or the socket's buffer fills up
				}
				
				if ( !keepAlive ) {
					
					try {
						queue.clear();
						socketChannel.close();
					} catch( Exception ex ) {
						ex.printStackTrace();
					}
					
				} else {
					queue.remove(0);
				}

			}

			if ( queue.isEmpty() && socketChannel.isOpen() ) {
				// We wrote away all data, so we're no longer interested
				// in writing on this socket. Switch back to waiting for data.
				key.interestOps(SelectionKey.OP_READ);
			}
			
		}
		
	}
	
	private InstanceConfiguration getConfiguration() {
		return this.instance.getConfiguration();
	}
	
	private void initSelector() throws IOException {

		// Create a new selector
		Selector socketSelector = SelectorProvider.provider().openSelector();

		// Create a new non-blocking server socket channel
		this.serverChannel = ServerSocketChannel.open();
		this.serverChannel.configureBlocking( false );

		// Bind the server socket to the specified address and port
		InetSocketAddress isa = new InetSocketAddress( getConfiguration().getHost(), getConfiguration().getPort() );
		this.serverChannel.socket().bind(isa);

		// Register the server socket channel, indicating an interest in accepting new connections
		this.serverChannel.register(socketSelector, SelectionKey.OP_ACCEPT);
		
		this.selector = socketSelector;

	}
}
