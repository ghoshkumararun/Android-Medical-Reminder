package com.team6.mobile.iti;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.team6.mobile.iti.connection.JSONParser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {
	Button btnSignUp;
	EditText txtName;
	EditText txtEmail;
	EditText txtPassword;
	EditText txtConfPassword;
	
	private static final int LOGIN_SUCESS = 1;
	private static final int INVALIED_EMAIL_OR_PASSWORD = 2;
	private static final int LOGIN_FAILED = 3;
	private int loginStatus;
	private static final String SIGNUP_URL = "http://192.168.1.5:8084/MedicalReminderServer/signup";
	
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		txtName = (EditText) findViewById(R.id.editName);
		txtEmail = (EditText) findViewById(R.id.editEmailSignUp);
		txtPassword = (EditText) findViewById(R.id.editPassSignUp);
		txtConfPassword = (EditText) findViewById(R.id.editConfirmPass);
		btnSignUp = (Button) findViewById(R.id.btnSignUp);
	    sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
	    if(!sharedPreferences.getString("emailUser", "default").equals("default")){
	    	Intent homeIntent = new Intent(this,HomeActivity.class);
	    	startActivity(homeIntent);
	    }
		btnSignUp.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(SignUpActivity.this,ViewMedActivity.class);
				//startActivity(intent);
				if(txtConfPassword.getText().toString().equals(txtPassword.getText().toString())){
			//	if(true){
					editor = sharedPreferences.edit();
					
					editor.putString("nameUser", txtName.getText().toString());
					String user = txtEmail.getText().toString();
					Log.i("user",user);
					editor.putString("emailUser",user );
					editor.putString("passwordUser", txtPassword.getText().toString());
					editor.commit();
					//Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
					//startActivity(intent);
					SignUpTask signup = new SignUpTask();
					signup.execute(txtName.getText().toString(),txtEmail.getText().toString(),txtPassword.getText().toString());
				}
				else{
					Toast.makeText(getApplicationContext(), "Please Enter the password and cofirmation correctly", 1000);
					txtConfPassword.setText("");
					txtPassword.setText("");
				}
			}
		});
	}

	class SignUpTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {

			// create array list of parameters
			ArrayList<NameValuePair> requestParams = new ArrayList<NameValuePair>();
			
			// add name as a parameter
			   requestParams.add(new BasicNameValuePair("name", params[0]));


			// add email as a parameter
			requestParams.add(new BasicNameValuePair("email", params[1]));
			
			
			// add password as a parameter
			requestParams.add(new BasicNameValuePair("password", params[2]));

			// create Json Parser
			JSONParser parser = new JSONParser();

			// make a request and receive response
			JSONObject jsonResponse = parser.makeHttpRequest(SIGNUP_URL, "POST",
					requestParams);

			// get login status
			int status = 0;
			try {
				status = jsonResponse.getInt("status");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return status;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);

			String message = null;

			if (result == 1){//login successfull
				Intent intent1 = new Intent(SignUpActivity.this, HomeActivity.class);
				startActivity(intent1);
				message = "Signed up successfully";
			}
				
			else  if (result == 2)
				message = "Invalid Email/Password";
			else
				message = "Login Failed";

			Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG)
					.show();

			
		}

	}
	

}
