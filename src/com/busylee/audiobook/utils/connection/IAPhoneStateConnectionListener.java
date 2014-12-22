package com.busylee.audiobook.utils.connection;

/**
 * Created by busylee on 11.08.14.
 */
public interface IAPhoneStateConnectionListener {

	void startListenConnectionState();

	void stopListen();

	void setObserver(IAPhoneStateConnectionListenerObserver observer);
}
