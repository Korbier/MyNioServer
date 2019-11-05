package org.sc.server.protocol;

/**
 * Interface permettant de determiner la facon dont les elements sont serialises (vue du cote serveur)
 * et deserialises pour la communication client/serveur
 * 
 * @author SC
 * 
 */
public interface Serializer<Q extends Request, S extends Response> {
    
	public byte[] serialize( S object );
    public Q unserialize( byte[] data );
    
}
