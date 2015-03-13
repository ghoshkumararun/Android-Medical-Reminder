package com.team6.mobile.iti;

import com.team6.mobile.iti.R;
import com.team6.mobile.iti.broadcastReceiver.ReminderBroadcastReceiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_medicine);

		// find medicine name view
		//edtMedicineName = (EditText) findViewById(R.id.edtMedicineName);

		// find medicine type view
		sprMedicineType = (Spinner) findViewById(R.id.sprMedicineType);

		// find medicine image view
		imgvMedicineImage = (ImageView) findViewById(R.id.imgvMedicineImage);

		// find set schedule button view
		btnSetSchedule = (Button) findViewById(R.id.btnSetSchedule);

		// set on click listener on set schedule button
		btnSetSchedule.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//Intent intent = new Intent(AddMedicineActivity.this,SetScheduleActivity.class);
				//startActivity(intent);
				//setAlram(v);
				
				
			}
		});

	}



}
