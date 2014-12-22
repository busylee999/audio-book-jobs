package com.busylee.audiobook.utils.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by busylee on 13.08.14.
 */
public class CNetworkInfo implements IANetworkInfo{

	private final Context mContext;

	private ConnectivityManager mConnectivityManager;

	public CNetworkInfo(Context context) {
		mContext = context;
	}

	@Override
	public boolean hasNetworkConnection() {
		NetworkInfo netInfo = getConnectivityManager().getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}

		return false;
	}

	private ConnectivityManager getConnectivityManager() {
		if ( mConnectivityManager == null )
			mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

		return mConnectivityManager;
	}
}
