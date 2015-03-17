package com.team6.mobile.iti;


import java.lang.reflect.TypeVariable;
import java.util.Calendar;

import com.team6.mobile.iti.beans.Medicine;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ReminderDialog extends Dialog implements
		android.view.View.OnClickListener {
     Activity activity;
	 Dialog dialog;
	 Button btnOk, btnCancel;
	 ImageView imgView;
	 TextView txtView;
	 int num =0;
	 private long currentTime;
	
	public ReminderDialog(Activity a) {
		super(a);
		this.activity = a;
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		activity.finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reminder_dialog);
		btnOk = (Button) findViewById(R.id.btnOk);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		imgView=(ImageView) findViewById(R.id.imageView1);
		txtView = (TextView) findViewById(R.id.reminderTextView);
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.SECOND, 0);
		currentTime = c.getTimeInMillis();
	}


	@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnOk:
				activity.finish();
				Intent intent = new Intent(activity, ReminderListView.class);
				intent.putExtra("currentTime", currentTime);
				activity.startActivity(intent);
				break;
			case R.id.btnCancel:
				
		    activity.finish();
				
			final NotificationManager mgr=(NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
				
	        Notification note = new Notification(R.drawable.ic_launcher, "Capsulty Reminder!",System.currentTimeMillis());
	       
	        ReminderDialogSupport r = (ReminderDialogSupport) activity; 
	        String image = r.getImageUrl();
	        String medName = r.getMedecineName();
	       
	        Log.i("namey", medName);
	        long time = r.getTakenTime();
	        
	        Intent myIntent = new Intent(activity, ReminderListView.class);
	        myIntent.putExtra("image", image);
	        myIntent.putExtra("name", medName);
	        myIntent.putExtra("currentTime", currentTime);
	        
	        // This pending intent will open after notification click
	        PendingIntent i=PendingIntent.getActivity(activity, 0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
	        	       
	        note.setLatestEventInfo(activity, "Capsulty Reminder","Please take "+medName, i);
	         
	        note.flags |= Notification.FLAG_AUTO_CANCEL;
	        
	        //After uncomment this line you will see number of notification arrive
	        mgr.notify(0, note);
	        num++;
				
				break;
			default:
				break;
			}
			dismiss();

		}
	}
