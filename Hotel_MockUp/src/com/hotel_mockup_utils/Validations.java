package com.hotel_mockup_utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.widget.EditText;
import android.widget.TextView;

public class Validations {

	Context context;

	public Validations(Context context) {
		this.context = context;
	}

	public void showMessage(String message) {

		AlertDialog.Builder altDialog = new AlertDialog.Builder(context);
		altDialog.setMessage(message);
		altDialog.setTitle("Error");
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// ((Activity) context).finish();
			}
		});
		altDialog.show();

	}

	public Boolean validateLogin(EditText userNameEditText,
			EditText passwordEditText) {
		String email1 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
		String email2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		String emailId = userNameEditText.getText().toString().trim();

		if (!((emailId.matches(email1)) || (emailId.matches(email2)))) {
			showMessage("Please enter valid email.");
			return true;

		} else if (passwordEditText.getText().toString().trim().equals("")) {
			showMessage("Please enter Password.");
			return true;
		}

		return false;
	}

	public Boolean validateHotelCreation(EditText primaryEmailEditText,
			EditText nameEditText, EditText zipCodeEditText,
			EditText cityEditText, EditText addressEditText,
			EditText landMark1EditText, EditText landMark2EditText,
			EditText webSiteEditText, EditText sameDayEditText,
			EditText totalRoomEditText, EditText facilitiesEditText,
			EditText descriptionEditText, EditText twitterLinkEditText,
			EditText facebookLinkEditText, TextView checkInTextView,
			TextView checkOutTextView) {

		String email1 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
		String email2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		String emailId = primaryEmailEditText.getText().toString().trim();

		String name1 = "[a-zA-Z]+([ -][a-zA-Z]+)*";
		String name2 = "[A-Z][a-zA-Z]*";
		String name = nameEditText.getText().toString().trim();

		String cityPattern = "([a-zA-Z ])*";
		String city = cityEditText.getText().toString().trim();

		if (!((emailId.matches(email1)) || (emailId.matches(email2)))) {
			showMessage("Please enter valid Email.");
			return true;

		} else if (!((name.matches(name1)) || (name.matches(name2)))) {
			showMessage("Please enter Name.");
			return true;
		} else if (zipCodeEditText.getText().toString().trim().length() < 5) {
			showMessage("Please enter valid Zipcode.");
			return true;
		} else if (!city.matches(cityPattern)) {
			showMessage("Please enter valid City.");
			return true;
		} else if (addressEditText.getText().toString().trim()
				.equalsIgnoreCase("")) {
			showMessage("Address field can't be empty.");
			return true;
		} else if (landMark1EditText.getText().toString().trim()
				.equalsIgnoreCase("")) {
			showMessage("LandMark1 field can't be empty.");
			return true;
		} else if (addressEditText.getText().toString().trim()
				.equalsIgnoreCase("")) {
			showMessage("Address field can't be empty.");
			return true;
		} else if (checkInTextView.getText().toString().trim()
				.equalsIgnoreCase("")) {
			showMessage("Please select CheckIn date");
			return true;
		} else if (checkOutTextView.getText().toString().trim()
				.equalsIgnoreCase("")) {
			showMessage("Please select CheckOut date");
			return true;
		}

		return false;

	}

	public Boolean validateInformation(EditText primaryLandLineEditText,
			EditText primaryMobileEditText, EditText primaryEmail,
			EditText billingLandLineEditText, EditText billingMobileEditText,
			EditText bilingEmail, EditText ownerLandLineEditText,
			EditText ownerMobileEditText, EditText ownerEmail) {

		String email1 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
		String email2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		
		String primaryEmailString = primaryEmail.getText().toString().trim();
		String billingEmailString = bilingEmail.getText().toString().trim();
		String ownerEmailString = ownerEmail.getText().toString().trim();
		
		if(primaryLandLineEditText.getText().toString().equals("")){
			showMessage("Please enter primary landline number.");
			return true;
		}else if(primaryMobileEditText.getText().toString().equals("")){
			showMessage("Please enter primary mobile number.");
			return true;
		}else if (!((primaryEmailString.matches(email1)) || (primaryEmailString.matches(email2)))) {
			showMessage("Please enter valid Primary Email.");
			return true;
		}else if(billingLandLineEditText.getText().toString().equals("")){
			showMessage("Please enter billing landline number.");
			return true;
		}else if(billingMobileEditText.getText().toString().equals("")){
			showMessage("Please enter billing mobile number.");
			return true;
		}else if (!((billingEmailString.matches(email1)) || (billingEmailString.matches(email2)))) {
			showMessage("Please enter valid Billing Email.");
			return true;
		}else if(ownerLandLineEditText.getText().toString().equals("")){
			showMessage("Please enter owner landline number.");
			return true;
		}else if(ownerMobileEditText.getText().toString().equals("")){
			showMessage("Please enter owner mobile number.");
			return true;
		}else if (!((ownerEmailString.matches(email1)) || (ownerEmailString.matches(email2)))) {
			showMessage("Please enter valid Owner Email.");
			return true;
		}
		
		return false;

	}

}
