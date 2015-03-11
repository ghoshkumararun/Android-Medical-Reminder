package com.team6.mobile.iti;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

public class SignUpActivity extends Activity {
	Button btnSignUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		btnSignUp = (Button) findViewById(R.id.btnSignUp);
		
		btnSignUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(SignUpActivity.this,ViewMedActivity.class);
				//startActivity(intent);
				
			}
		});
	}



}
