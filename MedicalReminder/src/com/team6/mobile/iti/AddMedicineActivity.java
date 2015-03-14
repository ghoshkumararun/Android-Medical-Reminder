package com.team6.mobile.iti;

<<<<<<< HEAD
import com.team6.mobile.iti.R;
import com.team6.mobile.iti.beans.Medicine;

=======
>>>>>>> branch 'master' of https://mgaberali@github.com/mgaberali/Medical-Reminder.git
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_medicine);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		// create medicine object
		medicine = new Medicine();

		// find medicine name view
		edtMedicineName = (EditText) findViewById(R.id.edtMedicineName);

		// find medicine type view
		sprMedicineType = (Spinner) findViewById(R.id.sprMedicineType);
<<<<<<< HEAD
		
		// get medicine types array
		final String [] types = getResources().getStringArray(R.array.medicineTypes);
		
		// create adapter for type spinner
		TypeSpinnerAdapter adapter = new TypeSpinnerAdapter(this, android.R.layout.simple_list_item_1, types);
		
		// apply adapter to spinner
		sprMedicineType.setAdapter(adapter);
=======
>>>>>>> branch 'master' of https://mgaberali@github.com/mgaberali/Medical-Reminder.git

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
				//setAlram(v);
				
				
			}
		});

	}



}
