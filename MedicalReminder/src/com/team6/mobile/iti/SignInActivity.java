package com.team6.mobile.iti;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team6.mobile.iti.beans.Medicine;
import com.team6.mobile.iti.connection.JSONParser;


public class SignInActivity extends Activity {

	Button btnSignIn;
	TextView txtSignUp;
	EditText txtEmail;
	EditText txtPass;
	Intent i;
	
	private static final int LOGIN_SUCESS = 1;
	private static final int INVALIED_EMAIL_OR_PASSWORD = 2;
	private static final int LOGIN_FAILED = 3;
	private int loginStatus;
	private static final String LOGIN_URL = "http://10.145.238.152:8084/MedicalReminderServer/login";
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onStart() {
		super.onStart();
		sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
	    if(!sharedPreferences.getString("emailUser", "default").equals("default")){
	    	Intent homeIntent = new Intent(this,HomeActivity.class);
	    	startActivity(homeIntent);
	    	SignInActivity.this.finish();
	    }
		
		
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
	    if(!sharedPreferences.getString("emailUser", "default").equals("default")){
	    	Intent homeIntent = new Intent(this,HomeActivity.class);
	    	startActivity(homeIntent);
	    	SignInActivity.this.finish();
	    }
	    setContentView(R.layout.activity_sign_in);
		btnSignIn = (Button) findViewById(R.id.btnSignIn);
		txtSignUp = (TextView) findViewById(R.id.txtNewSignUp);
		txtEmail = (EditText) findViewById(R.id.editEmailSignIn);
		txtPass = (EditText) findViewById(R.id.editPassSignIn);
		
		
		txtSignUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				i = new Intent(SignInActivity.this, SignUpActivity.class);
				startActivity(i);
				

			}
		});
		// android.app.ActionBar bar = getActionBar();
		// bar.setBackgroundDrawable(new
		// ColorDrawable(Color.parseColor("#97C0E6")));

		btnSignIn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
editor = sharedPreferences.edit();
				
				editor.putString("nameUser", txtEmail.getText().toString());
				String user = txtEmail.getText().toString();
				Log.i("user",user);
				editor.putString("emailUser",user );
				editor.putString("passwordUser", txtPass.getText().toString());
				editor.commit();
				LoginTask loginTask = new LoginTask();
				loginTask.execute(txtEmail.getText().toString(), txtPass.getText().toString());
				//"m@yahoo.com", "123456"
			}
		});
	}
	
	

	class LoginTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {

			// create array list of parameters
			ArrayList<NameValuePair> requestParams = new ArrayList<NameValuePair>();

			// add email as a parameter
			requestParams.add(new BasicNameValuePair("email", params[0]));

			// add password as a parameter
			requestParams.add(new BasicNameValuePair("password", params[1]));

			// create Json Parser
			JSONParser parser = new JSONParser();

			// make a request and receive response
			JSONObject jsonResponse = parser.makeHttpRequest(LOGIN_URL, "POST",
					requestParams);

			// get login status
			int status = 0;
			String medJson;
			Medicine [] medList;
			try {
				status = jsonResponse.getInt("status");
				//medJson = jsonResponse.getString("medData");
				//Gson myGson = new Gson();
				//java.lang.reflect.Type myType = new TypeToken<Medicine[]>(){}.getType();
				//medList = myGson.fromJson("medData", myType);
				
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

			if (result == 1){
				//login successfull
				Intent intent1 = new Intent(SignInActivity.this, HomeActivity.class);
				startActivity(intent1);
				SignInActivity.this.finish();
			}
				
			else { if (result == 2)
				message = "Invalid Email/Password";
			else
				message = "Login Failed";

			Toast.makeText(SignInActivity.this, message, Toast.LENGTH_LONG)
					.show();

			}
			
		}

	}
	
}
