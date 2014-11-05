package com.hotel_mockup_activities;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.hotel_mockup.R;
import com.hotel_mockup.R.layout;
import com.hotel_mockup.entities.LoginUser;
import com.hotel_mockup_data.DataEngine;
import com.hotel_mockup_utils.AlertDialogMessage;
import com.hotel_mockup_utils.BackgroundNetwork_withLoading;
import com.hotel_mockup_utils.ConnectionDetector;
import com.hotel_mockup_utils.Constants;
import com.hotel_mockup_utils.DevicePreferences;
import com.hotel_mockup_utils.Validations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	EditText userNameEditText;
	EditText passwordEditText;
	
	Button signInButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		
		findViews();
		listeners();
	}
	
	public void findViews(){
		userNameEditText = (EditText) findViewById(R.id.edittext_login_email);
		passwordEditText = (EditText) findViewById(R.id.edittext_login_password);
		
		signInButton = (Button) findViewById(R.id.button_login_signIn);
		
		userNameEditText.setText("admin@reshotel.in");
		passwordEditText.setText("admin");
	}
	
	public void listeners(){
		signInButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!new ConnectionDetector(LoginActivity.this).isConnectedToInternet()){
					new AlertDialogMessage(LoginActivity.this).showMessage("Error", Constants.NO_Internet);
					return;
				}
				
				if(new Validations(LoginActivity.this).validateLogin(userNameEditText, passwordEditText)){
					return;
				}
				
				
				new BackgroundNetwork_withLoading(LoginActivity.this){
					LoginUser objLoginUser;
					
					protected Void doInBackground(Void[] params) {
						DataEngine dataEngineObj = new DataEngine(
								LoginActivity.this);
						ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
						list.add(new BasicNameValuePair("email", userNameEditText.getText().toString().trim()));
						list.add(new BasicNameValuePair("password", passwordEditText.getText().toString().trim()));
//						objLoginUser = dataEngineObj.checkLogin(userNameEditText.getText().toString().trim(),passwordEditText.getText().toString().trim());
						objLoginUser = dataEngineObj.checkLogin(list);
						return null;
					};
					
					protected void onPostExecute(Void result) {
						
						if(objLoginUser != null){							
							DevicePreferences objDevicePreferences = new DevicePreferences();
							objDevicePreferences.addKey(LoginActivity.this, Constants.Authenticity_Token,
									objLoginUser.getRemember_token());
							
							Intent intent = new Intent(LoginActivity.this, HotelCreationActivity.class);
							startActivity(intent);
							finish();
						}
						super.onPostExecute(result);
					};
					
				
				}.execute();
			}
		});
	}
}
