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
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.team6.mobile.iti.ReminderDialogSupport;
import com.team6.mobile.iti.beans.Medicine;
import com.team6.mobile.iti.beans.TimeDto;

public class AddMedicineUtility {

	Date date;
	long repeatInterval;
	int flag = 0;

	//method to set time of Reminder Dialog
	public void setAlarm(Medicine med, Context context) {
		
		//extract info from bean
		String name = med.getName();
		String repeat = med.getRepetition();
		long startDay = med.getStart_date();		
		long endDay = med.getEnd_date();
		String imageUrl = med.getImageURL();
		ArrayList<TimeDto> list = (ArrayList<TimeDto>) med.getTimes();
		
		//looping on all pairs of time and dose
		for(int index=0 ; index<list.size();index++){
			
		long time = list.get(index).getTake_time();
		if((time==System.currentTimeMillis())||(time<System.currentTimeMillis())){
			flag = 1;
		}

		//setting repetition interval
			if (repeat.equals("daily")) {

				repeatInterval = 24 * 60 * 60 * 1000;
				if(flag==1){
					time = time + repeatInterval;
				}

			} else if (repeat.equals("weekly")) {

				repeatInterval = 7 * 24 * 60 * 60 * 1000;
				if(flag==1){
					time = time + repeatInterval;
				}

			} else if (repeat.equals("monthly")) {

				repeatInterval = 30 * 7 * 24 * 60 * 60 * 1000;
				if(flag==1){
					time = time + repeatInterval;
				}
			}

		//setting pending intent
		Intent intent = new Intent(context, ReminderDialogSupport.class);
		intent.putExtra("name", name);
		intent.putExtra("end", endDay);
		intent.putExtra("start", startDay);
		intent.putExtra("index", index);
		intent.putExtra("image", imageUrl);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, index,intent, 0);
		
		//setting alarm manager
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,repeatInterval,pendingIntent);

	    

		}
	}

	//method to insert medicine in SQLite database
	public void addMedicine(Medicine med) {

	}

}