package com.hotel_mockup_activities;

import java.util.ArrayList;
import java.util.List;

import com.hotel_mockup.R;
import com.hotel_mockup.R.id;
import com.hotel_mockup.R.layout;
import com.hotel_mockup.R.string;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class CancellationPolicyActivity extends BaseActivity {

	Spinner daysSpinner;
	Spinner conditionSpinner;
	
	FrameLayout frameLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.cancellation_policy_layout);
		frameLayout = (FrameLayout) findViewById(R.id.layout_container);
		frameLayout.removeAllViews();
		frameLayout.addView(getLayoutInflater().inflate(
				R.layout.cancellation_policy_layout, null));
		
		findViews();
		setValues();
		listeners();
	}
	
	public void findViews() {		
		stepsTextView = (TextView) findViewById(R.id.textview_base_stepstext);
		pageNameTextView = (TextView) findViewById(R.id.textview_base_PageName);
		
		daysSpinner = (Spinner) findViewById(R.id.spinner_cancelllation_policy_days);
		conditionSpinner = (Spinner) findViewById(R.id.spinner_cancelllation_policy_condition);
	}
	
	public void setValues() {
		
		//Top Headers
		Resources res = getResources();
		String stepsString = String.format(res.getString(R.string.steps), 4);
		stepsTextView.setText(stepsString);

		pageNameTextView.setText("Cancellation Policy");
		
		// Spinner Drop down elements
        List <String> days = new ArrayList<String>();
        days.add("Day of arrival (Check in time)");
        days.add("day 1");
        days.add("day 2");
        days.add("day 3");
        days.add("day 7");
        days.add("day 14");
 
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daysSpinner.setAdapter(dataAdapter);
        
	}
	
	public void listeners() {
		
		daysSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
