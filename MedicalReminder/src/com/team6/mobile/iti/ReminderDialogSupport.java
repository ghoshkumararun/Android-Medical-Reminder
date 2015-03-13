package com.team6.mobile.iti;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ReminderDialogSupport extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder_dialog_support);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		ReminderDialog reminderDiag = new ReminderDialog(this);
		reminderDiag.show();
		Log.i("in reminderdialogsupport", "in reminderdialogsupport");
	
	}



}
