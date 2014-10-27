package com.busylee.audiobook.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by busylee on 16.09.14.
 */
public class CAAlertDialogFragment extends DialogFragment {

	protected static final String ARG_TITLE = "ARG_TITLE";
	protected static final String ARG_POSITIVE_RESOURCE_ID = "ARG_POSITIVE_RESOURCE_ID";
	protected static final String ARG_NEGATIVE_RESOURCE_ID = "ARG_NEGATIVE_RESOURCE_ID";
	protected static final String ARG_MESSAGE = "ARG_MESSAGE";

	protected String mTitle;
	protected int mPositiveButtonResourceId;
	protected int mNegativeButtonResourceId;

	private IAAlertDialogObserver mObserver;
	private String mMessage;

	public enum TButton {
		EPositiveButton,
		ENegativeButton
	}

	public static CAAlertDialogFragment newInstance(String title, String message, int positiveButtonResourceId, int negativeButtonResourceId, IAAlertDialogObserver observer) {
		CAAlertDialogFragment alertDialog = new CAAlertDialogFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putString(ARG_TITLE, title);
		args.putString(ARG_MESSAGE, message);
		args.putInt(ARG_POSITIVE_RESOURCE_ID, positiveButtonResourceId);
		args.putInt(ARG_NEGATIVE_RESOURCE_ID, negativeButtonResourceId);
		alertDialog.setArguments(args);

		alertDialog.setObserver(observer);

		return alertDialog;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Bundle arguments = getArguments();
		mTitle = arguments.getString(ARG_TITLE);
		mMessage = arguments.getString(ARG_MESSAGE);
		mPositiveButtonResourceId = arguments.getInt(ARG_POSITIVE_RESOURCE_ID);
		mNegativeButtonResourceId = arguments.getInt(ARG_NEGATIVE_RESOURCE_ID);
	}

	public void setObserver(IAAlertDialogObserver observer) {
		mObserver = observer;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setTitle(mTitle)
				.setPositiveButton(mPositiveButtonResourceId,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								mObserver.alertDialogButtonPressed(getTag(), TButton.EPositiveButton);
							}
						}
				);

		if(mNegativeButtonResourceId!=0) {
			builder.setNegativeButton(mNegativeButtonResourceId,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							mObserver.alertDialogButtonPressed(getTag(), TButton.ENegativeButton);
						}
					}
			);
		}

		if (mMessage != null)
			builder.setMessage(mMessage);
		return builder.create();
	}

}
