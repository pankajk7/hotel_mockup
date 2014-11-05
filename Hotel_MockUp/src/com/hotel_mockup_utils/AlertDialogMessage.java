package com.hotel_mockup_utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogMessage {

	Context context;

	public AlertDialogMessage(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public void showMessage(String header,
			String message) {

		AlertDialog.Builder altDialog = new AlertDialog.Builder(context);
		altDialog.setMessage(message);
		altDialog.setTitle(header);
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
//				((Activity) context).finish();
			}
		});
		altDialog.show();

	}

}
