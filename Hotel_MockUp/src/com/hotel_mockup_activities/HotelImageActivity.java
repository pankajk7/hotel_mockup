package com.hotel_mockup_activities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.hotel_mockup.R;
import com.hotel_mockup.R.layout;
import com.hotel_mockup_adapters.ImageListBaseAdapter;
import com.hotel_mockup_utils.BackgroundNetwork;
import com.hotel_mockup_utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HotelImageActivity extends BaseActivity {

	Button addButton;
	Button saveButton;
	Button nextButton;
	
	ListView listView;
	
	private static final int SELECT_PICTURE = 1;
	private static final int PICK_gallery_IMAGE = 1;
	
	ImageListBaseAdapter objListBaseAdapter;
	ArrayList<Bitmap> imageList;
	FrameLayout frameLayout;
	public static ArrayList<String> publicIdArrayList;
	public static ArrayList<Boolean> isShow;
	public static int position = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.image_layout);
		frameLayout = (FrameLayout) findViewById(R.id.layout_container);
		frameLayout.removeAllViews();
		frameLayout.addView(getLayoutInflater().inflate(
				R.layout.image_layout, null));
		
		findViews();
		Listeners();
		setValues();
	}
	
	public void findViews(){
		
		stepsTextView = (TextView) findViewById(R.id.textview_base_stepstext);
		pageNameTextView = (TextView) findViewById(R.id.textview_base_PageName);
		
		saveButton = (Button) findViewById(R.id.button_image_save);
		nextButton = (Button) findViewById(R.id.button_image_next);
		addButton = (Button) findViewById(R.id.button_image_addImage);
		
		listView = (ListView) findViewById(R.id.listview_image_list);
		imageList = new ArrayList<Bitmap>();
		publicIdArrayList = new ArrayList<String>();
		isShow = new ArrayList<Boolean>();
	}
	
	public void setValues() {
		Resources res = getResources();
		String stepsString = String.format(res.getString(R.string.steps), 2);
		stepsTextView.setText(stepsString);

		pageNameTextView.setText("Hotel Images");
	}
	
	
	public void Listeners(){
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(
							Intent.createChooser(intent, "Select Picture"),
							SELECT_PICTURE);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), e.getMessage(),
							Toast.LENGTH_LONG).show();
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}
			}
		});
		
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				nextButton.setEnabled(true);
			}
		});
		
		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HotelImageActivity.this, InformationActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri selectedImageUri = null;
		String filePath = null;
		Bitmap bitmap;
		if(requestCode == PICK_gallery_IMAGE){
			if (resultCode == Activity.RESULT_OK) {
				selectedImageUri = data.getData();
				filePath = null;

				try {

//					filePath = GetPathFromUri.getPath(HotelImageActivity.this,
//							selectedImageUri);
					filePath = getRealPathFromURI(selectedImageUri);
					String[] imageNameArray = filePath.split("/");

					if (filePath != null) {
						bitmap = decodeFile(filePath);
						Log.e("Bitmap", bitmap.toString());						
						imageList.add(bitmap);
						position++;
						isShow.add(position,true);
						objListBaseAdapter = new ImageListBaseAdapter(HotelImageActivity.this, imageList);
						listView.setAdapter(objListBaseAdapter);
						uploadImage(bitmap,position);
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Internal error",
							Toast.LENGTH_LONG).show();
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}

			}
		}
	}
	
	public Bitmap decodeFile(String filePath) {
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, o);

		// The new size we want to scale to
		final int REQUIRED_SIZE = 300;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		Bitmap bitmap1 = BitmapFactory.decodeFile(filePath, o2);
		return bitmap1;

	}
	
	public String getRealPathFromURI(Uri contentUri) {
		  Cursor cursor = null;
		  try { 
		    String[] proj = { MediaStore.Images.Media.DATA };
		    cursor = getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		  } finally {
		    if (cursor != null) {
		      cursor.close();
		    }
		  }
		}
	
	private void uploadImage(final Bitmap bitmap , final int position){
		new BackgroundNetwork(HotelImageActivity.this){
			protected Void doInBackground(Void[] params) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
				bitmap.compress(CompressFormat.PNG, 0, bos); 
				byte[] bitmapdata = bos.toByteArray();
				ByteArrayInputStream inputStream = new ByteArrayInputStream(bitmapdata);
				
				Map<String, String> config = new HashMap<String, String>();
				config.put("cloud_name", Constants.cloud_name);
				config.put("api_key", Constants.api_key);
				config.put("api_secret", Constants.api_secret);
				Cloudinary cloudinary = new Cloudinary(config);
				
				try {
					String string = cloudinary.uploader().upload(inputStream, Cloudinary.emptyMap()).toString();
					JSONObject objJsonObject = new JSONObject(string);
					publicIdArrayList.add(objJsonObject.getString("public_id").toString());
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(HotelImageActivity.this, "Error Occur while uploading", Toast.LENGTH_SHORT).show();
				}
				
				return null;
				
			};
			
			protected void onPostExecute(Void result) {
				int lastIndex = isShow.size();
				isShow.set(position, false);
				listView.setAdapter(objListBaseAdapter);
				
			};
			
		}.execute();		
		
	}
	
}
