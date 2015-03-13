package com.team6.mobile.iti;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class ViewMedActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_med);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}




}
