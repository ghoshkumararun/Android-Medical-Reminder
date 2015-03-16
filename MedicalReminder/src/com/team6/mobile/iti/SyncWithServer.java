package com.team6.mobile.iti;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.team6.mobile.iti.beans.Medicine;
import com.team6.mobile.iti.connection.JSONParser;


public class SyncWithServer extends AsyncTask<Medicine, Void, Integer> {
	private static final int SYNC_SUCESS = 1;
	//private static final int INVALIED_EMAIL_OR_PASSWORD = 2;
	private static final int SYNC_FAILED = 3;
	private int syncStatus;
	//write the url of the back end servlet
	private static final String SYNC_URL = "http://10.145.239.44:8084/MedicalReminderServer/login";
	
	JSONParser jsonObj ;

	

	@Override
	protected Integer doInBackground(Medicine... params) {
		// TODO Auto-generated method stub
		Gson mGson = new Gson();
		mGson.toJson(src)
		return null;
	}
	
}
