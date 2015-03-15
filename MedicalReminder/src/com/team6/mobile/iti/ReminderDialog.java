package com.team6.mobile.iti;


import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class ReminderDialog extends Dialog implements
		android.view.View.OnClickListener {

     Activity activity;
	 Dialog dialog;
	 Button btnOk, btnCancel;
	 ImageView imgView;
	

	public ReminderDialog(Activity a) {
		super(a);
		this.activity = a;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reminder_dialog);
		btnOk = (Button) findViewById(R.id.btnOk);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		imgView=(ImageView) findViewById(R.id.imageView1);
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

	}


	@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnOk:
				activity.finish();
				Intent intent = new Intent(activity, ReminderListView.class);
				activity.startActivity(intent);
				break;
			case R.id.btnCancel:
				activity.finish();
				
				
				// This pending intent will open after notification click
				PendingIntent i = PendingIntent.getActivity(activity, 0, new Intent(activity, 

	ReminderListView.class), 0);
				
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(activity).setContentTitle

	("Reminder").setContentText("his is a reminder for medecine").setContentIntent(i);

				Notification notification = mBuilder.build();
				
				NotificationManager notificationManager = (NotificationManager) activity.getSystemService(activity.NOTIFICATION_SERVICE);
				
		        notification.defaults |= Notification.DEFAULT_VIBRATE;
		        notification.defaults |= Notification.DEFAULT_SOUND;
				
				 // cancel notification after click
				notification.flags |= Notification.FLAG_AUTO_CANCEL;
				
				// show scrolling text on status bar when notification arrives
		        notification.tickerText = "Reminder" + "\n" + "remember medecine";
				
				notificationManager.notify(0, notification);

				
				break;
			default:
				break;
			}
			dismiss();
		}
	}