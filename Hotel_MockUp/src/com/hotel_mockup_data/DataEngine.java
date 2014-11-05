package com.hotel_mockup_data;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.hotel_mockup.entities.LoginUser;
import com.hotel_mockup_utils.Constants;

import android.content.Context;


public class DataEngine {
	Context context;
	String urlString = "http://54.255.180.239/";
	
	public DataEngine(Context context) {
		this.context = context;
	}

	
	public LoginUser checkLogin(ArrayList<NameValuePair> list)
	{
		urlString = urlString + "backend/session";
		RestfulService restfulService=new RestfulService();
		DataParser dataParser= new DataParser();		
		return dataParser.parseLoginDetails(restfulService.loginPost(urlString,list));
	}
	
//	public String createHotel(ArrayList<NameValuePair> list)
	public String createHotel(JsonObject jsonObject)
	{
		urlString = urlString + "backend/hotels";
		RestfulService restfulService=new RestfulService(context);
		DataParser dataParser= new DataParser();		
		return dataParser.parseHotelCreation(restfulService.postWithDataInResponse(urlString,jsonObject));
	}
	
	public String createUpdateImages(JSONObject jsonObject)
	{
		urlString = urlString + "backend/"+Constants.image_create_update;
		RestfulService restfulService=new RestfulService(context);
//		DataParser dataParser= new DataParser();		
		return restfulService.postData(urlString,jsonObject);
	}
        
	
}
