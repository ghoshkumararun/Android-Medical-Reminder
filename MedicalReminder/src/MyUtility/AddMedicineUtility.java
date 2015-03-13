package MyUtility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.team6.mobile.iti.AddMedicineActivity;
import com.team6.mobile.iti.R;
import com.team6.mobile.iti.ReminderDialogSupport;

public class AddMedicineUtility {
	
	
	public void setAlram(Medicine med) {

		edtMedicineName = (EditText) findViewById(R.id.edtMedicineName);
		//int i = Integer.parseInt(edtMedicineName.getText().toString());
	
		Intent intent = new Intent(AddMedicineActivity.this,ReminderDialogSupport.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(AddMedicineActivity.this, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (5 * 1000), pendingIntent);//i seconds *1000 = Xmillisecs
		
		Toast.makeText(AddMedicineActivity.this, "Alarm set in 5 seconds",
				Toast.LENGTH_SHORT).show();

	}
	
	public addMedicine(Medicine med){
		
	}

}
