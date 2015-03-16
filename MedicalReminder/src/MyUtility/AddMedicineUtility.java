package MyUtility;

import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Toast;

import com.team6.mobile.iti.AddMedicineActivity;
import com.team6.mobile.iti.DatabaseAdapter;
import com.team6.mobile.iti.DatabaseHelper;
import com.team6.mobile.iti.ReminderDialogSupport;
import com.team6.mobile.iti.beans.Medicine;

public class AddMedicineUtility {

	public void setAlram(Medicine med) {

		// edtMedicineName = (EditText) findViewById(R.id.edtMedicineName);
		// int i = Integer.parseInt(edtMedicineName.getText().toString());

		/*
		 * Intent intent = new
		 * Intent(AddMedicineActivity.this,ReminderDialogSupport.class);
		 * PendingIntent pendingIntent =
		 * PendingIntent.getActivity(AddMedicineActivity.this, 0, intent, 0);
		 * AlarmManager alarmManager = (AlarmManager)
		 * getSystemService(ALARM_SERVICE);
		 * alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+
		 * (5 * 1000), pendingIntent);//i seconds *1000 = Xmillisecs
		 */

	}

	public void addMedicine(Medicine med, Context con) {
		DatabaseHelper dbHelper = new DatabaseHelper(con);
		DatabaseAdapter dbAdapter = new DatabaseAdapter(dbHelper);
		Log.i("xxxxUtility", med.getImageUrl());
		dbAdapter.insertMedecine(med.getName(), med.getDesc(), med.getType(),
				med.getImageUrl(), med.getStart_date(), med.getEnd_date(),
				med.getRepetition());
		dbAdapter.insertMedecineIntoDoseTable(med.getIsTaken(), med.getTimes());

		
		List<Medicine> meds = dbAdapter.selectAllMedecines();
		
		Log.i("newTag", ""+meds.size());
		
		for(Medicine m : meds)
			Log.i("newTag", m.getName());
		
	}
}