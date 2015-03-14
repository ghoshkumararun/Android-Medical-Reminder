package com.team6.mobile.iti;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
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
			break;
		case R.id.btnCancel:
			dismiss();
			
			break;
		default:
			break;
		}
		dismiss();
	}
}
