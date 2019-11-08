package org.sc.server.protocol.http.message.request;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.sc.server.protocol.http.message.Command;
import org.sc.server.protocol.http.message.CustomHeader;
import org.sc.server.protocol.http.message.GenericHeader;
import org.sc.server.protocol.http.message.Header;
import org.sc.server.protocol.http.message.HttpVersion;
import org.sc.server.utils.ByteArrayConsumer;
import org.sc.server.utils.ByteArrayConsumerListener;


public class RequestUnserializer {

	public HttpRequest unserialize( SocketChannel source, byte [] data ) {
		
		ByteArrayConsumer consumer = new ByteArrayConsumer();
		
		RequestLineConsumer requestConsumer = new RequestLineConsumer();
		HeaderConsumer      headerConsumer  = new HeaderConsumer();
		BodyConsumer        bodyConsumer    = new BodyConsumer();
		
		consumer.addTrigger( (byte) ' ' );
		consumer.addTrigger( (byte) '\n' );
		consumer.addTrigger( (byte) ':' );
		
		consumer.addListener( new DataConsumer( requestConsumer, headerConsumer, bodyConsumer ) );		
		consumer.consume( data );
		
		HttpRequest request = new HttpRequest( requestConsumer.getCommand(), requestConsumer.getResource(), requestConsumer.getVersion() );
		completeRemote( source, request );
		
		
		for (Entry<String, String> entry : headerConsumer.getHeaders().entrySet() ) {
			Header header = GenericHeader.getFromCode( entry.getKey() );
			if ( header == null ) header = RequestHeader.getFromCode( entry.getKey() );
			if ( header == null ) header = new CustomHeader( entry.getKey() );
			if ( header != null ) request.setHeaderValue(header, entry.getValue());
		}
		
		return request;
		
	}
	
	private void completeRemote(SocketChannel source, HttpRequest request) {
		try {
			if ( source.getRemoteAddress() instanceof InetSocketAddress ) {
				InetSocketAddress inetSocket = (InetSocketAddress) source.getRemoteAddress();
				request.setRemoteHost( inetSocket.getHostString() );
				request.setRemotePort( inetSocket.getPort() );
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static class DataConsumer implements ByteArrayConsumerListener {

		private Byte previousTrigger = '\n';
		
		private ByteArrayConsumerListener currentConsumer = null;
		
		private ByteArrayConsumerListener requestConsumer = null;
		private ByteArrayConsumerListener headerConsumer  = null;
		private ByteArrayConsumerListener bodyConsumer    = null;
		
		public DataConsumer( ByteArrayConsumerListener request, ByteArrayConsumerListener header, ByteArrayConsumerListener body ) {
			this.requestConsumer = request;
			this.headerConsumer  = header;
			this.bodyConsumer    = body;
		}
		
		@Override
		public void onTrigger(byte trigger, byte[] buffer) {
			
			if ( currentConsumer == null )      this.currentConsumer = requestConsumer;
			if ( this.currentConsumer != null ) this.currentConsumer.onTrigger(trigger, buffer);
			
			if ( shouldGoNext( this.previousTrigger, trigger ) ) nextConsumer();	
			
			this.previousTrigger = trigger;
			
		}
		
		private boolean shouldGoNext( byte previous, byte current ) {
			if ( this.currentConsumer == this.requestConsumer && current == '\n' )                     return true;
			if ( this.currentConsumer == this.headerConsumer  && previous == '\n' && current == '\n' ) return true;
			return false;
		}
		
		private void nextConsumer() {
			     if ( this.currentConsumer == this.requestConsumer ) this.currentConsumer = this.headerConsumer;
			else if ( this.currentConsumer == this.headerConsumer )  this.currentConsumer = this.bodyConsumer;
			else if ( this.currentConsumer == this.bodyConsumer )    this.currentConsumer = null;			
		}
		
	}
	
	private static class RequestLineConsumer implements ByteArrayConsumerListener {
		
		private int pass = 0;
		
		private Command     command  = null;
		private String      resource = null;
		private HttpVersion version  = null;
		
		@Override
		public void onTrigger(byte trigger, byte[] buffer) {
			
			String sBuffer = new String( buffer ).trim();
			
			if ( pass == 0 )      this.command  = Command.getFromName( sBuffer );
			else if ( pass == 1 ) this.resource = sBuffer;
			else if ( pass == 2 ) this.version  = HttpVersion.getFromRequest( sBuffer );
			else throw new RuntimeException( "Incompatible arguments : " + sBuffer );
			
			pass++;
			
		}
		
		public Command getCommand() {
			return this.command;
		}
		
		public String getResource() {
			return this.resource;
		}
		
		public HttpVersion getVersion() {
			return this.version;
		}
		
	}
	
	private static class HeaderConsumer implements ByteArrayConsumerListener {
		
		private String key   = null;
		private String value = "";
		
		private Map<String, String> headers = new HashMap<String, String>();
		
		@Override
		public void onTrigger(byte trigger, byte[] buffer) {
			
			String sBuffer = new String( buffer );
			
			if ( key == null && trigger == ':' ) {
				String trimmedSBuffer = sBuffer.trim();
				key = trimmedSBuffer.substring(0, trimmedSBuffer.length() - 1);
			}
			else if ( trigger == '\n' && this.key != null ) {
				value += sBuffer;
				this.headers.put( key, value.trim() );
				key   = null;
				value = "";
			}
			else value += sBuffer;
			
		}
		
		public Map<String, String> getHeaders() {
			return this.headers;
		}
		
	}	
	
	private static class BodyConsumer implements ByteArrayConsumerListener {
		
		@Override
		public void onTrigger(byte trigger, byte[] buffer) {
			
			
		}
		
	}	
	
}
