package com.busylee.audiobook.utils.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

/**
 * Created by busylee on 17.12.14.
 */
public class CSmartPhoneConnectionStateListener extends CPhoneStateConnectionListener {

	private final IntentFilter connectivityActionFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

	private final BroadcastReceiver connectionBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (!action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				return;
			}

			boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

			if (!noConnectivity)
				mObserver.onConnectionEstablished();
		}
	};

	public CSmartPhoneConnectionStateListener(Context context) {
		super(context);

	}

	@Override
	protected void startListening() {
		super.startListening();
		mContext.registerReceiver(connectionBroadcastReceiver, connectivityActionFilter);
	}

	@Override
	protected void stopListening() {
		super.stopListening();
		mContext.unregisterReceiver(connectionBroadcastReceiver);
	}

}
