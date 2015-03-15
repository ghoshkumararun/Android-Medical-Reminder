package com.team6.mobile.iti;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager.LayoutParams;

public class ReminderDialogSupport extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder_dialog_support);
		
		//getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		ReminderDialog reminderDiag = new ReminderDialog(this);
		//reminderDiag.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		//reminderDiag.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
		reminderDiag.show();
		
		Log.i("dialog support", "dialog support");
		
	
	}



}