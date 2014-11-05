package com.hotel_mockup_adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.hotel_mockup.R;
import com.hotel_mockup_activities.HotelImageActivity;
import com.hotel_mockup_utils.BackgroundNetwork;
import com.hotel_mockup_utils.Constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ImageListBaseAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	ArrayList<Bitmap> imageList;

	public ImageListBaseAdapter(Context context, ArrayList<Bitmap> imageList) {
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageList = imageList;
	}

	@Override
	public int getCount() {
		if (imageList != null) {
			return imageList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return imageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.image_list_items, null);
		}

		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.imageview_imageListItem_image);
		ImageButton deleteImageButton = (ImageButton) convertView
				.findViewById(R.id.imageButton_imageListItem_delete);
		deleteImageButton.setTag(position);
		ProgressBar progressBar = (ProgressBar) convertView
				.findViewById(R.id.progressBar);
		
		if (HotelImageActivity.isShow.get(position) == true) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.INVISIBLE);
		}

		imageView.setImageBitmap(imageList.get(position));

		deleteImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final int position = (Integer) v.getTag();
				HotelImageActivity.isShow.set(position, true);
				notifyDataSetChanged();

				new BackgroundNetwork(context) {

					String result = "";

					protected Void doInBackground(Void[] params) {
						try {

							Map<String, String> config = new HashMap<String, String>();
							config.put("cloud_name", Constants.cloud_name);
							config.put("api_key", Constants.api_key);
							config.put("api_secret", Constants.api_secret);
							Cloudinary cloudinary = new Cloudinary(config);
							result = cloudinary
									.uploader()
									.destroy(
											HotelImageActivity.publicIdArrayList
													.get(position), config)
									.toString();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					}

					protected void onPostExecute(Void arg) {
						if (!result.equals("")) {
							imageList.remove(position);
							HotelImageActivity.publicIdArrayList.remove(position);
							HotelImageActivity.isShow.remove(position);
							HotelImageActivity.position -= 1;
						}
						notifyDataSetChanged();
					};
				}.execute();
			}
		});

		return convertView;
	}

}
