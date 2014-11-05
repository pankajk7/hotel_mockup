package com.hotel_mockup_data;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.hotel_mockup_utils.Constants;
import com.hotel_mockup_utils.DevicePreferences;
import com.hotel_mockup_utils.ProcessLoaderIndication;
import com.restservice.Http;
import com.restservice.HttpFactory;
import com.restservice.HttpResponse;
import com.restservice.NetworkError;
import com.restservice.ResponseHandler;

public class RestWebservices {

	Context context;
	String baseURL;
	String urlSuffix;
	String resourceName;
	ProcessLoaderIndication process;

	// String authorizationString;

	public RestWebservices(Context context) {
		baseURL = "http://54.255.180.239";
		urlSuffix = "/backend/";
		this.context = context;
		// authorizationString = "";
		process = new ProcessLoaderIndication(this.context);
	}

	private String getServiceURL(String resourceName, String extraParameters) {
//		return baseURL + urlSuffix + resourceName + extraParameters;
		return baseURL + urlSuffix + resourceName+extraParameters;
	}

	public void onComplete() {
		process.hideDialog();
	}

	public void onFailure(NetworkError error) {

	}

	public void onError(String message, HttpResponse response) {

	}
	
	public void onSuccess(String message, String success) {

	}

	public void onSuccess(Object object, HttpResponse response) {
	Log.d("Success", "testing");
		}

	public void onSuccess(Object[] object, HttpResponse response) {
	}

	public void onSuccess(String data, HttpResponse response) {

	}

	

	public void serviceCall(String resourceName, String extraParameters) {
		String url = getServiceURL(resourceName, extraParameters);

//		if (resourceName
//				.equalsIgnoreCase(Constants)) {
//			get(url);
//		}
	}
	
	public void serviceCall(String resourceName, String extraParameters,
			Object object) {
		String url = getServiceURL(resourceName, extraParameters);

		if (resourceName.equalsIgnoreCase(Constants.createHotel)) {
			createHotel(url, object);
		} 
	}
	

	private void createHotel(String url, Object objHotel) {
		process.showDialog();
		Http http = HttpFactory.create(this.context);
		http.post(url)
		.header("Authorization", new DevicePreferences().getString(context, Constants.Authenticity_Token, ""))
		.header("Content-Type", Constants.Content_Type_Json)
		.data(objHotel).handler(new ResponseHandler<String>() {
			@Override
			public void success(String data, HttpResponse response) {
				onSuccess(data, response);
				super.success(data, response);
			}

			@Override
			public void complete() {
				onComplete();
				super.complete();
			}

			@Override
			public void failure(NetworkError error) {
				onFailure(error);
				super.failure(error);
			}

			@Override
			public void error(String message, HttpResponse response) {
				onError(message, response);
				super.error(message, response);
			}
		}).send();

	}

	public void informationPutCall(String resourceName, String extraParameters,
			Object object) {
		String url = baseURL + urlSuffix + resourceName+"/"+extraParameters;
		process.showDialog();
		Http http = HttpFactory.create(this.context);
		http.put(url)
		.header("Authorization", new DevicePreferences().getString(context, Constants.Authenticity_Token, ""))
		.data(object).handler(new ResponseHandler<String>() {
			@Override
			public void success(String data, HttpResponse response) {
				onSuccess(data, response);
				super.success(data, response);
			}

			@Override
			public void complete() {
				onComplete();
				super.complete();
			}

			@Override
			public void failure(NetworkError error) {
				onFailure(error);
				super.failure(error);
			}

			@Override
			public void error(String message, HttpResponse response) {
				onError(message, response);
				super.error(message, response);
			}
		}).send();

	}
	
	public void cancellationPolicy(String resourceName, String extraParameters,
			Object object) {
		String url = baseURL + urlSuffix + resourceName+"/"+extraParameters;
		process.showDialog();
		Http http = HttpFactory.create(this.context);
		http.put(url)
		.header("Authorization", new DevicePreferences().getString(context, Constants.Authenticity_Token, ""))
		.data(object).handler(new ResponseHandler<String>() {
			@Override
			public void success(String data, HttpResponse response) {
				onSuccess(data, response);
				super.success(data, response);
			}

			@Override
			public void complete() {
				onComplete();
				super.complete();
			}

			@Override
			public void failure(NetworkError error) {
				onFailure(error);
				super.failure(error);
			}

			@Override
			public void error(String message, HttpResponse response) {
				onError(message, response);
				super.error(message, response);
			}
		}).send();

	}
	
}
