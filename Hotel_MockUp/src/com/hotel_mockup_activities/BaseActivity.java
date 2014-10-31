package com.hotel_mockup_activities;

import com.hotel_mockup.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

public class BaseActivity extends Activity {

	public TextView stepsTextView;
	public TextView pageNameTextView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_layout);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		findViews();
		
	}
	
	public void findViews(){
		stepsTextView = (TextView) findViewById(R.id.textview_base_stepstext);
		pageNameTextView = (TextView) findViewById(R.id.textview_base_PageName);
	}
}
