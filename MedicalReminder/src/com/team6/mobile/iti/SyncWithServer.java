package com.team6.mobile.iti;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.renderscript.Type;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team6.mobile.iti.beans.Medicine;
import com.team6.mobile.iti.connection.JSONParser;


public class SyncWithServer extends AsyncTask<Medicine, Void, Integer> {
	private static final int SYNC_SUCESS = 1;
	//private static final int INVALIED_EMAIL_OR_PASSWORD = 2;
	private static final int SYNC_FAILED = 3;
	private int syncStatus;
	//write the url of the back end servlet
	private static final String SYNC_URL = "http://10.145.239.44:8084/MedicalReminderServer/newmedecines";
	
	JSONParser jsonObj ;

	

	@Override
	protected Integer doInBackground(Medicine... params) {
		// TODO Auto-generated method stub
		
		
		Gson myGson = new Gson();
		java.lang.reflect.Type myType = new TypeToken<Medicine[]>(){}.getType();
		String jsonString = myGson.toJson(params,myType);
		Log.i("json",jsonString);
		
		JSONParser parser = new JSONParser();
		SharedPreferences sharedPref ;
		
		BasicNameValuePair requestParam = new BasicNameValuePair("data", jsonString);
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(requestParam);
		JSONObject jsonResponse = parser.makeHttpRequest(SYNC_URL, "POST",
				parameters);
		return null;
	}
	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(result == 0){
			
		}
	}
}
