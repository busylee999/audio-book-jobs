package com.busylee.audiobook.utils.connection;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by busylee on 11.08.14.
 */
public class CPhoneStateConnectionListener extends PhoneStateListener implements IAPhoneStateConnectionListener {

	private static final String TAG = "CPhoneStateListener";

	enum TAState {
		EListening,
		EStopped
	}

	protected final Context mContext;
	private final IANetworkInfo mNetworkInfo;

	protected IAPhoneStateConnectionListenerObserver mObserver;
	private TAState mState = TAState.EStopped;
	private TelephonyManager mTelephonyManager;


	public CPhoneStateConnectionListener(Context context) {
		mContext = context;
		mNetworkInfo = new CNetworkInfo(context);

	}

	@Override
	public void onDataConnectionStateChanged(int state) {
		super.onDataConnectionStateChanged(state);

		Log.i(TAG, "onConnectionEstablished: state: " + state);

		if(mObserver != null && mNetworkInfo.hasNetworkConnection())
			mObserver.onConnectionEstablished();

	}

	@Override
	public void setObserver(IAPhoneStateConnectionListenerObserver observer) {
		mObserver = observer;
	}

	@Override
	public void startListenConnectionState() {

		if(mState == TAState.EListening) {
			Log.w(TAG, "startListenConnectionState() already listening. state: " + mState);
			return;
		}

		startListening();

		mState = TAState.EListening;
	}

	@Override
	public void stopListen() {

		if(mState != TAState.EListening) {
			Log.w(TAG, "stopListen() listener stopped or not started. state: " + mState);
			return;
		}

		stopListening();

		mState = TAState.EStopped;
	}

	protected void startListening() {
		getTelephonyManager().listen(this, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
	}

	protected void stopListening() {
		getTelephonyManager().listen(this, PhoneStateListener.LISTEN_NONE);
	}

	private TelephonyManager getTelephonyManager() {
		if(mTelephonyManager == null)
			mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyManager;
	}
}
