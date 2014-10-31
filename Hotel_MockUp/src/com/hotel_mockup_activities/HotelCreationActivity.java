package com.hotel_mockup_activities;

import java.util.Calendar;

import com.hotel_mockup.R;
import com.hotel_mockup_utils.Constants;
import com.hotel_mockup_utils.DevicePreferences;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

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

	int mYear, mMonth, mDay;

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
				DevicePreferences objDevicePreferences = new DevicePreferences();
				objDevicePreferences.addKey(context, Constants.primaryEmail,
						primaryEmailEditText.getText().toString());
				objDevicePreferences.addKey(context, Constants.name, nameEditText
						.getText().toString());
				objDevicePreferences.addKey(context, Constants.zipCode,
						zipCodeEditText.getText().toString());
				objDevicePreferences.addKey(context, Constants.city, cityEditText
						.getText().toString());
				objDevicePreferences.addKey(context, Constants.address,
						addressEditText.getText().toString());
				objDevicePreferences.addKey(context, Constants.landmark1,
						landMark1EditText.getText().toString());
				objDevicePreferences.addKey(context, Constants.landmark2,
						landMark2EditText.getText().toString());
				objDevicePreferences.addKey(context, Constants.website,
						webSiteEditText.getText().toString());
				objDevicePreferences.addKey(context, Constants.totalroom,
						totalRoomEditText.getText().toString());
				objDevicePreferences.addKey(context, Constants.description,
						descriptionEditText.getText().toString());
				objDevicePreferences.addKey(context, Constants.twitterLink,
						twitterLinkEditText.getText().toString());
				objDevicePreferences.addKey(context, Constants.facebookLink,
						facebookLinkEditText.getText().toString());
				nextButton.setEnabled(true);
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
	}

	public void setValues() {
		Resources res = getResources();
		String stepsString = String.format(res.getString(R.string.steps), 1);
		stepsTextView.setText(stepsString);

		pageNameTextView.setText("Registration");
	}

	public void showDatePicker(View view) {
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		final TextView  textview = (TextView)view;
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						textview.setText((monthOfYear + 1) + "-"
						 + dayOfMonth + "-" + year);
					}
				}, mYear, mMonth, mDay);
		dpd.show();
	}

}
