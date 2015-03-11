package com.team6.mobile.iti.broadcastReceiver;


import com.team6.mobile.iti.ReminderDialog;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class ReminderBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {

	    Toast.makeText(arg0, "receiver working!!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
	    Log.i("inside broadcast", "inside broadcast");
	    //Intent inten = new Intent(this, ReminderDialog.class);
	   // arg1.setClass(arg0, ReminderDialog.class);

		// start SetSchedule Activity
	   // arg0.startActivity(arg1);
		
	  //  Vibrator vibrator = (Vibrator) arg0.getSystemService(Context.VIBRATOR_SERVICE);
	  //  vibrator.vibrate(2000);
	}

}
