package org.sc.server.utils;

public interface ByteArrayConsumerListener {
	public void onTrigger( byte trigger, byte [] buffer );
}
