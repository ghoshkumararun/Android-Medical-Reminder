package com.team6.mobile.iti;

import com.team6.mobile.iti.R;
import com.team6.mobile.iti.broadcastReceiver.ReminderBroadcastReceiver;

import android.app.ActionBar;
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
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

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

				Intent intent = new Intent(AddMedicineActivity.this,SetScheduleActivity.class);
				startActivity(intent);
				//setAlram(v);
			}
		});

	}

	public void setAlram(View v) {

		edtMedicineName = (EditText) findViewById(R.id.edtMedicineName);
		int i = Integer.parseInt(edtMedicineName.getText().toString());
		Intent intent = new Intent(this, ReminderDialog.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (i * 1000), pendingIntent);//i seconds *1000 = Xmillisecs
		
		Toast.makeText(this, "Alarm set in " + i + " seconds",
				Toast.LENGTH_SHORT).show();

	}

}
