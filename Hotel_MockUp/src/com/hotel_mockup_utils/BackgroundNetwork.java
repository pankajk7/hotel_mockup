package com.hotel_mockup_utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class BackgroundNetwork extends AsyncTask<Void, Void, Void> {

	Context context;

	public BackgroundNetwork(Context activity) {
		context = activity;
	}

//	protected void doActionOnPostExecuteBeforeProgressDismiss() {
//
//	}
//
//	protected void doActionOnPostExecuteAfterProgressDismiss() {
//
//	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
	}

	public void onPostExecuteDeveloperMethodForPublicAccess(Void result) {
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		return null;
	}

}
