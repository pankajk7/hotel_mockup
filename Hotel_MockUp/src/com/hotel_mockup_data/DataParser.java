package com.hotel_mockup_data;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.hotel_mockup.entities.LoginUser;
import android.content.Context;
import android.util.Log;

public class DataParser {
	Context context;

	public String getStringFromInputStream(InputStream in) {

		if (in == null) {
			;
			return null;
		}

		StringBuffer out = new StringBuffer();
		byte[] b = new byte[1024];
		try {
			for (int i; (i = in.read(b)) != -1;) {
				out.append(new String(b, 0, i));
			}

			String responseString = out.toString();
			if (responseString != null) {
				;
				return out.toString();
			}

		} catch (IOException e) {
			System.out.println(">>> Exception >>> " + e + " >>> Message >>> "
					+ e.getMessage());
		}
		return null;
	}

	public LoginUser parseLoginDetails(InputStream jsonResponseInputStream) {
		return parseLoginDetails(getStringFromInputStream(jsonResponseInputStream));
	}

	public LoginUser parseLoginDetails(String jsonResponseString) {
		LoginUser loginUserObj;
		try {
			if (jsonResponseString != null) {

				loginUserObj = new LoginUser();

				// JSONArray jsonArray = new JSONArray(jsonResponseString);
				// JSONObject jsonObject = new JSONObject(jsonResponseString);

				try {
					loginUserObj = new Gson().fromJson(jsonResponseString,
							LoginUser.class);
					Log.d("login user", loginUserObj.toString());
					// loginUserObj.setConfirmation_token(confirmation_token) =
					// jsonObject.getString("UserRefNo");
				} catch (Exception exception) {
					System.out.println(">>> Exception >>> " + exception
							+ " >>> Message >>> " + exception.getMessage());
					return null;
				}

				return loginUserObj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	public String parseHotelCreation(InputStream jsonResponseInputStream) {
		return parseHotelCreation(getStringFromInputStream(jsonResponseInputStream));
	}

	public String parseHotelCreation(String jsonResponseString) {
		try {
			JSONObject jsonObject = new JSONObject(jsonResponseString);
			if (jsonObject != null) {
				try {
					if (jsonObject.getString("id") != null) {
						return jsonObject.getString("id");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (jsonObject.getString("error") != null) {
						return jsonObject.getString("id");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
