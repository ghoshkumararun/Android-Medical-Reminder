package MyUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.team6.mobile.iti.ReminderDialogSupport;
import com.team6.mobile.iti.beans.Medicine;
import com.team6.mobile.iti.beans.TimeDto;

public class AddMedicineUtility {

	Date date;
	long repeatInterval;

	//method to set time of Reminder Dialog
	public void setAlarm(Medicine med, Context context) {
		
		/*String repeat = med.getRepetition();
		Log.i("repeat", repeat);
		long startDay = med.getStart_date();
		Log.i("start day", Long.toString(startDay));
		long endDay = med.getEnd_date();
		Log.i("end day", Long.toString(endDay));
		ArrayList<TimeDto> list = (ArrayList<TimeDto>) med.getTimes();
		
		//looping on all pairs of time and dose
		for(int index=0 ; index<list.size();index++){	
		long time = list.get(index).getTake_time();
		long current = System.currentTimeMillis();
	    Date cur = new Date(current);
	    Date tkm = new Date(time);
		Log.i("take time", Long.toString(time));
		Log.i("take time date", tkm.toString());
		Log.i("current time", Long.toString(System.currentTimeMillis()));
		 Log.i("today", cur.toString());
		long increase = time - System.currentTimeMillis();
		Log.i("increase", Long.toString(increase));
		//checking if time in between start and end day
		//if ((time > startDay) && (time < endDay)) {

			if (repeat.equals("daily")) {

				repeatInterval = 24 * 60 * 60 * 1000;

			} else if (repeat.equals("weekly")) {

				repeatInterval = 7 * 24 * 60 * 60 * 1000;

			} else if (repeat.equals("monthly")) {

				repeatInterval = 30 * 7 * 24 * 60 * 60 * 1000;
			}
		/*}else{
			Log.i("time not between startday and end", "yes");
		}*/

		Intent intent = new Intent(context, ReminderDialogSupport.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		//alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + increase,repeatInterval,pendingIntent);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5*1000, pendingIntent);
		//alarmManager.cancel(pendingIntent);
		Log.i("here", "here");
		//}
	}

	//method to insert medicine in SQLite database
	public void addMedicine(Medicine med) {

	}

}