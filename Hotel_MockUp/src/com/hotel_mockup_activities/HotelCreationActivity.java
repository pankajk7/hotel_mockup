package com.hotel_mockup_activities;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.hotel_mockup.R;
import com.hotel_mockup_data.DataEngine;
import com.hotel_mockup_data.RestWebservices;
import com.hotel_mockup_utils.AlertDialogMessage;
import com.hotel_mockup_utils.BackgroundNetwork_withLoading;
import com.hotel_mockup_utils.ConnectionDetector;
import com.hotel_mockup_utils.Constants;
import com.hotel_mockup_utils.DevicePreferences;
import com.hotel_mockup_utils.Validations;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TextView.OnEditorActionListener;

public class HotelCreationActivity extends BaseActivity {

	FrameLayout frameLayout;
	Context context;

	EditText primaryEmailEditText;
	EditText nameEditText;
	EditText zipCodeEditText;
	EditText cityEditText;
	EditText addressEditText;
	EditText landMark1EditText;
	EditText landMark2EditText;
	EditText webSiteEditText;
	EditText sameDayEditText;
	EditText totalRoomEditText;
	EditText facilitiesEditText;
	EditText descriptionEditText;
	EditText twitterLinkEditText;
	EditText facebookLinkEditText;

	TextView checkInTextView;
	TextView checkOutTextView;

	Button saveButton;
	Button nextButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.home_creation_layout);
		frameLayout = (FrameLayout) findViewById(R.id.layout_container);
		frameLayout.removeAllViews();
		frameLayout.addView(getLayoutInflater().inflate(
				R.layout.hotel_creation_layout, null));

		context = HotelCreationActivity.this;

		findViews();
		Listeners();
		setValues();
	}

	public void findViews() {

		stepsTextView = (TextView) findViewById(R.id.textview_base_stepstext);
		pageNameTextView = (TextView) findViewById(R.id.textview_base_PageName);

		primaryEmailEditText = (EditText) findViewById(R.id.edittext_hotel_creation_email);
		nameEditText = (EditText) findViewById(R.id.edittext_hotel_creation_name);
		zipCodeEditText = (EditText) findViewById(R.id.edittext_hotel_creation_zipcode);
		cityEditText = (EditText) findViewById(R.id.edittext_hotel_creation_city);
		addressEditText = (EditText) findViewById(R.id.edittext_hotel_creation_address);
		landMark1EditText = (EditText) findViewById(R.id.edittext_hotel_creation_landmark1);
		landMark2EditText = (EditText) findViewById(R.id.edittext_hotel_creation_landmark2);
		webSiteEditText = (EditText) findViewById(R.id.edittext_hotel_creation_website);
		totalRoomEditText = (EditText) findViewById(R.id.edittext_hotel_creation_totalroom);
		descriptionEditText = (EditText) findViewById(R.id.edittext_hotel_creation_description);
		twitterLinkEditText = (EditText) findViewById(R.id.edittext_hotel_creation_twitter);
		facebookLinkEditText = (EditText) findViewById(R.id.edittext_hotel_creation_facebook);

		checkInTextView = (TextView) findViewById(R.id.textview_hotel_creation_checkin);
		checkOutTextView = (TextView) findViewById(R.id.textview_hotel_creation_checkout);

		saveButton = (Button) findViewById(R.id.button_hotel_cration_save);
		nextButton = (Button) findViewById(R.id.button_hotel_cration_next);
		
	}

	public void Listeners() {
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(!new ConnectionDetector(context).isConnectedToInternet()){
					new AlertDialogMessage(context).showMessage("Error", Constants.NO_Internet);
					return;
				}
				
				Validations objValidations = new Validations(
						HotelCreationActivity.this);

				if (objValidations.validateHotelCreation(primaryEmailEditText,
						nameEditText, zipCodeEditText, cityEditText,
						addressEditText, landMark1EditText, landMark2EditText,
						webSiteEditText, sameDayEditText, totalRoomEditText,
						facilitiesEditText, descriptionEditText,
						twitterLinkEditText, facebookLinkEditText,
						checkInTextView, checkOutTextView)) {
					return;
				}
				
				createHotel();
			}
		});

		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, HotelImageActivity.class);
				startActivity(intent);
				finish();
			}
		});

		checkInTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePicker(v);
			}
		});

		checkOutTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePicker(v);
			}
		});

		webSiteEditText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
//					View view = getWindow().getCurrentFocus();
					InputMethodManager imm = (InputMethodManager)getSystemService(
						      Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					showDatePicker(checkInTextView);
				}
				return false;
			}
		});

		checkInTextView.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					showDatePicker(v);
				}
				return false;
			}
		});
	}

	public void setValues() {
		Resources res = getResources();
		String stepsString = String.format(res.getString(R.string.steps), 1);
		stepsTextView.setText(stepsString);

		pageNameTextView.setText("Registration");
	}

	public void showDatePicker(View view) {
		final Calendar c = Calendar.getInstance();
		int hourOfDay, minute;
		boolean is24HourView;
		
		hourOfDay = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		is24HourView = false;

		final TextView textview = (TextView) view;
		
		TimePickerDialog timePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				textview.setText(new StringBuilder().append(checkValue(hourOfDay))
						.append(":").append(checkValue(minute)).append(":00"));
			}
		}, hourOfDay, minute, is24HourView);
		
		timePickerDialog.show();
		
	}
	
	private String checkValue(int value) {
		if (value >= 10)
			return String.valueOf(value);
		else
			return "0" + String.valueOf(value);
	}
	
	public void createHotel(){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(Constants.primaryEmail, primaryEmailEditText.getText().toString());
		jsonObject.addProperty(Constants.name, nameEditText.getText().toString());
		jsonObject.addProperty(Constants.zipCode, zipCodeEditText.getText().toString());
		jsonObject.addProperty(Constants.city, cityEditText.getText().toString());
		jsonObject.addProperty(Constants.address, addressEditText.getText().toString());
		jsonObject.addProperty(Constants.landmark1, landMark1EditText.getText().toString());
		jsonObject.addProperty(Constants.totalroom, totalRoomEditText.getText().toString());
		jsonObject.addProperty(Constants.description, descriptionEditText.getText().toString());
		
		//below fields are static now
		jsonObject.addProperty(Constants.checkInTime, "57600");
		jsonObject.addProperty(Constants.checkOutTime, "43200");
		jsonObject.addProperty(Constants.cutOffTime, "43200");
		jsonObject.addProperty(Constants.facility, "9");
		jsonObject.addProperty(Constants.latitude, "24.558");
		jsonObject.addProperty(Constants.longitude, "73.7024");
		
		
		Object object = jsonObject;
		
		new RestWebservices(HotelCreationActivity.this){
			public void onSuccess(String data, com.restservice.HttpResponse response) {
				try {
					JSONObject jsonObject = new JSONObject(data);
						
					String idString = jsonObject.getString("id");
					new DevicePreferences().addKey(HotelCreationActivity.this, Constants.Hotel_Id, idString);
					new AlertDialogMessage(HotelCreationActivity.this).showMessage("Success", "Data saved successfully");
				} catch (Exception e) {
					e.printStackTrace();
					new AlertDialogMessage(HotelCreationActivity.this).showMessage("Error", "Some error occured while saving");
				}
			};
			
			public void onError(String message, com.restservice.HttpResponse response) {
				new AlertDialogMessage(HotelCreationActivity.this).showMessage("Error", message);
			};
		}.serviceCall(Constants.createHotel, "", object);
	}

}
