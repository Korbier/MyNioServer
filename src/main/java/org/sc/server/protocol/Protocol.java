package org.sc.server.protocol;

import org.sc.server.core.Data;
import org.sc.server.core.ServerEvent;
import org.sc.server.core.Worker;

/**
 * Protocol base sur un systeme de request - response (http par exemple)
 * 
 * @author stephane
 * 
 */
public abstract class Protocol<Q extends Request, S extends Response> implements Worker {

    private Serializer<Q, S> serializer = null;

    public Protocol( Serializer<Q, S> serializer ) {
        this.serializer = serializer;
    }

    public Serializer<Q, S> getSerializer() {
        return this.serializer;
    }
    
	@Override
    public Data process( ServerEvent data ) {
       
		Q request  = getSerializer().unserialize( data.getData() );
        S response = getResponse( request );
        
        return new Data( getSerializer().serialize( response ) );

    }

    protected abstract S getResponse( Q request );

}
