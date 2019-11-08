package org.sc.server.protocol;

import org.sc.server.core.Data;
import org.sc.server.core.ServerEvent;
import org.sc.server.core.Worker;

import com.google.common.flogger.FluentLogger;

/**
 * Protocol base sur un systeme de request - response (http par exemple)
 * 
 * @author stephane
 * 
 */
public abstract class Protocol<Q extends Request, S extends Response> implements Worker {

	private static final FluentLogger LOGGER = FluentLogger.forEnclosingClass();
	private Serializer<Q, S> serializer = null;

    public Protocol( Serializer<Q, S> serializer ) {
        this.serializer = serializer;
    }

    public Serializer<Q, S> getSerializer() {
        return this.serializer;
    }
    
	@Override
    public Data process( ServerEvent event ) {
       
		Q request  = getSerializer().unserialize( event.getSource(), event.getData() );
        S response = processRequest( request );
        
        return new Data( getSerializer().serialize( response ) );

    }

    protected abstract S getResponse( Q request );

    private S processRequest( Q request ) {
    	
    	long start = System.nanoTime();
    	
    	try {
    		return getResponse( request );
    	} finally {
			LOGGER.atFine().log( "%s %dms", request, (System.nanoTime() - start) / 1000000);
		}
    	
    }
    
}
