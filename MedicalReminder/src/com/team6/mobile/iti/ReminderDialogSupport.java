package com.team6.mobile.iti;

import java.nio.channels.AlreadyConnectedException;
import java.sql.Date;

import com.team6.mobile.iti.beans.Medicine;
import com.team6.mobile.iti.beans.TimeDto;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class ReminderDialogSupport extends Activity {
	String medName;
	String ImageUrl;
	long startDay;
	long endDay;
	int index;
	long takeTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		
		medName = (String) bundle.get("name");
		Log.i("name in dialog", medName);
		
		//index = (Integer) bundle.get("index");
	     takeTime = (Long)bundle.get("take time");
		 startDay = (Long) bundle.get("start");
		 endDay = (Long) bundle.get("end");
		// ImageUrl = (String) bundle.get("image");
		 
		//String[] nameArray = getIntent().getStringArrayExtra("name");
		//medName = nameArray[index];	
		//Log.i("med name", medName);
		

	    // Log.i("take time dialog", new Date(takeTime).toString());
		 
		//checking if take time is between start day and end day
		if((System.currentTimeMillis()<endDay)&&(System.currentTimeMillis()>startDay)){
			
			setContentView(R.layout.activity_reminder_dialog_support);
			getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			
			//setting ringtone
			Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			MediaPlayer mediaPlayer = MediaPlayer.create(this, notification);
			mediaPlayer.start();
			
			//setting vibration
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		    vibrator.vibrate(800);
			
			ReminderDialog reminderDiag = new ReminderDialog(this);
			reminderDiag.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			reminderDiag.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
			reminderDiag.show();
			reminderDiag.txtView.setText("Please take "+medName);
			
			
		}else{
			//if not then cancel alarm
			Intent intent = new Intent(this, ReminderDialogSupport.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, index ,intent, 0);
			
			AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			alarmManager.cancel(pendingIntent);
			finish();
		}
	
	}

public String getMedecineName(){
	return medName;
}

public String getImageUrl(){
	return ImageUrl;
}

public long getTakenTime(){
	return takeTime;
}



}