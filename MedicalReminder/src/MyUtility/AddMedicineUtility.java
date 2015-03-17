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
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
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
	int flag = 0;
	Intent intent;
	//static int medIndex = 0;

	// method to set time of Reminder Dialog
	public void setAlarm(Medicine med, Context context) {

		// extract info from bean
		String name = med.getName();
		String repeat = med.getRepetition();
		Log.i("repeat time", repeat);
		long startDay = med.getStart_date();
		long endDay = med.getEnd_date();

		
		
		//setting pending intent
		intent = new Intent(context, ReminderDialogSupport.class);
		intent.putExtra("name", name);
		Log.i("name in utility", name);
		

		intent.putExtra("end", endDay);
		intent.putExtra("start", startDay);
		//intent.putExtra("image", imageUrl);

		
		ArrayList<TimeDto> list = (ArrayList<TimeDto>) med.getTimes();
		//looping on all pairs of time and dose
		for(int index=0 ; index<list.size();index++){
			
		long time = list.get(index).getTake_time();
		if((time==System.currentTimeMillis())||(time<System.currentTimeMillis())){
			flag = 1;
		}

		// looping on all pairs of time and dose
		for ( index = 0; index < list.size(); index++) {

			 time = list.get(index).getTake_time();
			if ((time == System.currentTimeMillis())
					|| (time < System.currentTimeMillis())) {
				flag = 1;
			}

			// setting repetition interval
			if (repeat.equals("Daily")) {

				repeatInterval = 24 * 60 * 60 * 1000;
				if (flag == 1) {
					time = time + repeatInterval;
				}

			} else if (repeat.equals("Weekly")) {

				repeatInterval = 7 * 24 * 60 * 60 * 1000;
				if (flag == 1) {
					time = time + repeatInterval;
				}

			} else if (repeat.equals("Monthly")) {

				repeatInterval = 30 * 7 * 24 * 60 * 60 * 1000;
				if (flag == 1) {
					time = time + repeatInterval;
				}
			}


	   // intent.putExtra("index", index);
		intent.putExtra("take time", time);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 ,intent,PendingIntent.FLAG_UPDATE_CURRENT);
		Log.i("time of alram", new Date(time).toString());
		//setting alarm manager
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,repeatInterval,pendingIntent);
		}
		}
		//medIndex++;
	}

	// method to insert medicine in SQLite database
	public void addMedicine(Medicine med, Context con) {

		DatabaseHelper dbHelper = new DatabaseHelper(con);
		DatabaseAdapter dbAdapter = new DatabaseAdapter(dbHelper);
		// Log.i("xxxxUtility", med.getImageUrl());

		dbAdapter.insertMedecine(med.getName(), med.getDesc(), med.getType(),
				med.getImageUrl(), med.getStart_date(), med.getEnd_date(),
				med.getRepetition());

		dbAdapter.insertMedecineIntoDoseTable(med.getIsTaken(), med.getTimes(),med.getId());
	
		List<TimeDto> times = med.getTimes();
		Log.i("XXXXTimes", "" + times.get(0).getTake_time());

		List<Medicine> meds = dbAdapter.selectAllMedecines();

		//Log.i("newTag", "" + meds.size());

		//for (Medicine m : meds)
			//Log.i("newTag", m.getName());

	}

}
