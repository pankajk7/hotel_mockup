package com.hotel_mockup_activities;

import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.hotel_mockup.R;
import com.hotel_mockup.R.layout;
import com.hotel_mockup_data.RestWebservices;
import com.hotel_mockup_utils.AlertDialogMessage;
import com.hotel_mockup_utils.ConnectionDetector;
import com.hotel_mockup_utils.Constants;
import com.hotel_mockup_utils.DevicePreferences;
import com.hotel_mockup_utils.Validations;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class InformationActivity extends BaseActivity {

	FrameLayout frameLayout;

	EditText primaryLandLineEditText;
	EditText primaryMobileEditText;
	EditText primaryEmail;

	EditText billingLandLineEditText;
	EditText billingMobileEditText;
	EditText bilingEmail;

	EditText ownerLandLineEditText;
	EditText ownerMobileEditText;
	EditText ownerEmail;

	Button saveButton;
	Button nextButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.information_layout);
		frameLayout = (FrameLayout) findViewById(R.id.layout_container);
		frameLayout.removeAllViews();
		frameLayout.addView(getLayoutInflater().inflate(
				R.layout.information_layout, null));
		findViews();
		setValues();
		listeners();
	}

	public void findViews() {

		stepsTextView = (TextView) findViewById(R.id.textview_base_stepstext);
		pageNameTextView = (TextView) findViewById(R.id.textview_base_PageName);

		saveButton = (Button) findViewById(R.id.button_information_save);
		nextButton = (Button) findViewById(R.id.button_information_next);

		primaryLandLineEditText = (EditText) findViewById(R.id.edittext_information_primaryLandLine);
		primaryMobileEditText = (EditText) findViewById(R.id.edittext_information_primaryMobile);
		primaryEmail = (EditText) findViewById(R.id.edittext_information_primaryEmail);
		bilingEmail = (EditText) findViewById(R.id.edittext_information_billingEmail);
		billingLandLineEditText = (EditText) findViewById(R.id.edittext_information_billingLandLine);
		billingMobileEditText = (EditText) findViewById(R.id.edittext_information_billingMobile);
		ownerEmail = (EditText) findViewById(R.id.edittext_information_ownerEmail);
		ownerLandLineEditText = (EditText) findViewById(R.id.edittext_information_ownerLandLine);
		ownerMobileEditText = (EditText) findViewById(R.id.edittext_information_ownerMobile);
	}

	public void setValues() {
		Resources res = getResources();
		String stepsString = String.format(res.getString(R.string.steps), 3);
		stepsTextView.setText(stepsString);

		pageNameTextView.setText("Contact Information");
	}

	public void listeners() {

		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!new ConnectionDetector(InformationActivity.this)
						.isConnectedToInternet()) {
					new AlertDialogMessage(InformationActivity.this)
							.showMessage("Error", Constants.NO_Internet);
					return;
				}

				if (new Validations(InformationActivity.this)
						.validateInformation(primaryLandLineEditText,
								primaryMobileEditText, primaryEmail,
								billingLandLineEditText, billingMobileEditText,
								bilingEmail, ownerLandLineEditText,
								ownerMobileEditText, ownerEmail)) {
					return;
				}

				saveInformation();
				nextButton.setEnabled(true);
			}
		});

		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InformationActivity.this,
						CancellationPolicyActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	public void saveInformation() {

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("owner_landline_country_code", "");
		jsonObject.addProperty("owner_landline_area_code", "");
		jsonObject.addProperty("owner_landline_no", ownerLandLineEditText
				.getText().toString());
		jsonObject.addProperty("owner_mobile_country_code", "");
		jsonObject.addProperty("owner_mobile_area_code", "");
		jsonObject.addProperty("owner_mobile_no", ownerMobileEditText.getText()
				.toString());
		jsonObject.addProperty("owner_email", ownerEmail.getText().toString());
		jsonObject.addProperty("primary_landline_country_code", "");
		jsonObject.addProperty("primary_landline_area_code", "");
		jsonObject.addProperty("primary_landline_no", primaryLandLineEditText
				.getText().toString());
		jsonObject.addProperty("primary_mobile_country_code", "");
		jsonObject.addProperty("primary_mobile_no", primaryMobileEditText
				.getText().toString());
		jsonObject.addProperty("primary_email", primaryEmail.getText()
				.toString());
		jsonObject.addProperty("billing_landline_country_code", "");
		jsonObject.addProperty("billing_landline_no", billingLandLineEditText
				.getText().toString());
		jsonObject.addProperty("billing_mobile_country_code", "");
		jsonObject.addProperty("billing_mobile_no", billingMobileEditText
				.getText().toString());
		jsonObject.addProperty("billing_email", bilingEmail.getText()
				.toString());

		Object object = jsonObject;

		String extraParameter = new DevicePreferences().getString(
				InformationActivity.this, Constants.Hotel_Id, "0");

		new RestWebservices(InformationActivity.this) {
			public void onSuccess(String data,
					com.restservice.HttpResponse response) {
				try {
					JSONObject jsonObject = new JSONObject(data);

					String idString = jsonObject.getString("id");
					new DevicePreferences().addKey(InformationActivity.this,
							Constants.Hotel_Id, idString);
					new AlertDialogMessage(InformationActivity.this)
							.showMessage("Success", "Data saved successfully");
				} catch (Exception e) {
					e.printStackTrace();
					new AlertDialogMessage(InformationActivity.this)
							.showMessage("Error",
									"Some error occured while saving");
				}
			};

			public void onError(String message,
					com.restservice.HttpResponse response) {
				new AlertDialogMessage(InformationActivity.this).showMessage(
						"Error", message);
			};
		}.informationPutCall(Constants.createHotel, extraParameter, object);
	}
}
