package MyUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;

import com.team6.mobile.iti.DatabaseAdapter;
import com.team6.mobile.iti.DatabaseHelper;
import com.team6.mobile.iti.ReminderDialogSupport;
import com.team6.mobile.iti.beans.Medicine;
import com.team6.mobile.iti.beans.TimeDto;

public class AddMedicineUtility {

	Date date;
	long repeatInterval;

	// method to set time of Reminder Dialog
	public void setAlarm(Medicine med, Context context) {

		// extract info from bean
		String repeat = med.getRepetition();
		long startDay = med.getStart_date();
		long endDay = med.getEnd_date();
		ArrayList<TimeDto> list = (ArrayList<TimeDto>) med.getTimes();

		// looping on all pairs of time and dose
		for (int index = 0; index < list.size(); index++) {

			long time = list.get(index).getTake_time();

			// setting repetition interval
			if (repeat.equals("daily")) {

				repeatInterval = 24 * 60 * 60 * 1000;

			} else if (repeat.equals("weekly")) {

				repeatInterval = 7 * 24 * 60 * 60 * 1000;

			} else if (repeat.equals("monthly")) {

				repeatInterval = 30 * 7 * 24 * 60 * 60 * 1000;
			}

			// setting intent
			Intent intent = new Intent(context, ReminderDialogSupport.class);
			intent.putExtra("start", System.currentTimeMillis());
			intent.putExtra("end", System.currentTimeMillis() + 30 * 1000);
			intent.putExtra("index", index);
			PendingIntent pendingIntent = PendingIntent.getActivity(context,
					index, intent, 0);

			// setting alarm manager
			AlarmManager alarmManager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time,
					repeatInterval, pendingIntent);

		}
	}

	// method to insert medicine in SQLite database
	public void addMedicine(Medicine med, Context con) {

		DatabaseHelper dbHelper = new DatabaseHelper(con);
		DatabaseAdapter dbAdapter = new DatabaseAdapter(dbHelper);
		Log.i("xxxxUtility", med.getImageUrl());
		dbAdapter.insertMedecine(med.getName(), med.getDesc(), med.getType(),
				med.getImageUrl(), med.getStart_date(), med.getEnd_date(),
				med.getRepetition());
		dbAdapter.insertMedecineIntoDoseTable(med.getIsTaken(), med.getTimes());

		List<Medicine> meds = dbAdapter.selectAllMedecines();

		Log.i("newTag", "" + meds.size());

		for (Medicine m : meds)
			Log.i("newTag", m.getName());

	}

}
