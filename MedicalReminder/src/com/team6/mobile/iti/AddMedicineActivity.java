package com.team6.mobile.iti;

import com.team6.mobile.iti.R;
import com.team6.mobile.iti.beans.Medicine;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddMedicineActivity extends Activity {

	// Edit Text Medicine Name
	EditText edtMedicineName;

	// Spinner Medicine Type
	Spinner sprMedicineType;

	// Image View Medicine Image
	ImageView imgvMedicineImage;

	// Button Set Schedule
	Button btnSetSchedule;
	
	// medicine bean
	private Medicine medicine;

	// types images
	private int [] typesIcons;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_medicine);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		// set array of images for types
		int [] arr = {R.drawable.ic_capsules, R.drawable.ic_tablets, R.drawable.ic_injection, R.drawable.ic_pills
				, R.drawable.ic_sprays, R.drawable.ic_patches, R.drawable.ic_drops,  R.drawable.ic_milligrams};
		typesIcons = arr;
		
		// create medicine object
		medicine = new Medicine();

		// find medicine name view
		edtMedicineName = (EditText) findViewById(R.id.edtMedicineName);

		// find medicine type view
		sprMedicineType = (Spinner) findViewById(R.id.sprMedicineType);
		
		// get medicine types array
		final String [] types = getResources().getStringArray(R.array.medicineTypes);
		
		// create adapter for type spinner
		TypeSpinnerAdapter adapter = new TypeSpinnerAdapter(this, android.R.layout.simple_list_item_1, types);
		
		// apply adapter to spinner
		sprMedicineType.setAdapter(adapter);

		// find medicine image view
		imgvMedicineImage = (ImageView) findViewById(R.id.imgvMedicineImage);

		// find set schedule button view
		btnSetSchedule = (Button) findViewById(R.id.btnSetSchedule);

		// set on click listener on set schedule button
		btnSetSchedule.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(AddMedicineActivity.this,
						SetScheduleActivity.class);
				
				// set medicine data
				medicine.setName(edtMedicineName.getText().toString());
				String medType = types[sprMedicineType.getSelectedItemPosition()];
				medicine.setType(medType);
				
				// add medicine object on intent
				intent.putExtra("medicine", medicine);
				
				startActivity(intent);
				// setAlram(v);
			}
		});

	}

	public void setAlram(View v) {

		edtMedicineName = (EditText) findViewById(R.id.edtMedicineName);
		int i = Integer.parseInt(edtMedicineName.getText().toString());
		Intent intent = new Intent(this, ReminderDialog.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				this.getApplicationContext(), 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
				+ (i * 1000), pendingIntent);// i seconds *1000 = Xmillisecs

		Toast.makeText(this, "Alarm set in " + i + " seconds",
				Toast.LENGTH_SHORT).show();

	}

	class TypeSpinnerAdapter extends ArrayAdapter<String> {

		String[] names;
		Context context;

		public TypeSpinnerAdapter(Context ctx, int txtViewResourceId,
				String[] objects) {
			super(ctx, txtViewResourceId, objects);
			context = ctx;
			names = objects;
		}

		@Override
		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
			return getCustomView(position, cnvtView, prnt);
		}

		@Override
		public View getView(int pos, View cnvtView, ViewGroup prnt) {
			return getCustomView(pos, cnvtView, prnt);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {

			// get layout inflater service
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// inflate single row layout
			View singleRowView = inflater.inflate(R.layout.spinner_type_row,
					parent, false);

			// set medicine type
			TextView tvType = (TextView) singleRowView
					.findViewById(R.id.tvMedicineType);
			tvType.setText(names[position]);

			// set medicine type image
			ImageView img = (ImageView) singleRowView.findViewById(R.id.imgvMedicineImg);

			//set image
			img.setImageResource(typesIcons[position]);
			
			return singleRowView;
		}

	}

}
