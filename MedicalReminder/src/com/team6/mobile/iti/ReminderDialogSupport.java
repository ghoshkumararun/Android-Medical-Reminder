package com.team6.mobile.iti;

import java.nio.channels.AlreadyConnectedException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.WindowManager.LayoutParams;

public class ReminderDialogSupport extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		long startDay = (Long) bundle.get("start");
		long endDay = (Long) bundle.get("end");
		int index = (Integer) bundle.get("index");
		
		//checking if take time is between start day and end day
		if((System.currentTimeMillis()<endDay)&&(System.currentTimeMillis()>startDay)){
			
			setContentView(R.layout.activity_reminder_dialog_support);
			getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			
			ReminderDialog reminderDiag = new ReminderDialog(this);
			reminderDiag.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			reminderDiag.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
			reminderDiag.show();
		}else{
			//if not then cancel alarm
			Intent intent = new Intent(this, ReminderDialogSupport.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, index,intent, 0);
			
			AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			alarmManager.cancel(pendingIntent);
			finish();
		}
		

		
	
	}



}