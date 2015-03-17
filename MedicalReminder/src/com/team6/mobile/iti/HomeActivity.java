package com.team6.mobile.iti;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team6.mobile.iti.beans.Medicine;
import com.team6.mobile.iti.connection.JSONParser;

import android.app.Activity;
import android.app.Application;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeActivity extends Activity implements  OnItemClickListener{

    
    
    public static final String[] titles = new String[] { "Strawberry",
            "Banana", "Orange", "Mixed" };
 
    public static final String[] descriptions = new String[] {
            "It is an aggregate accessory fruit",
            "It is the largest herbaceous flowering plant", "Citrus Fruit",
            "Mixed Fruits" };
 
    public static final Integer[] images = { R.drawable.antivirus,
    	R.drawable.antivirus, R.drawable.antivirus, R.drawable.antivirus};
           // R.drawable.more
        //    ,R.drawable.park,R.drawable.pink,R.drawable.red};
 
    ListView listView;
    List<RowItem> rowItems;
 
    /** Called when the activity is first created. */
   
 
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
    	int selectedMedId = rowItems.get(position).getMedId();
    	Intent i = new Intent(HomeActivity.this, EditMedicineActivity.class);
    	i.putExtra("medID", selectedMedId);
		startActivity(i);
       // Toast toast = Toast.makeText(getApplicationContext(),
              //  "Item " + (position + 1) + ": " + rowItems.get(position),
              //  Toast.LENGTH_SHORT);
       // toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
       // toast.show();
    	
    }
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DatabaseHelper databaseHelper = new  DatabaseHelper(this);
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(databaseHelper);
   //     databaseAdapter.insertMedecine("katafklam", "good medecine", "medecine", "med");
        ArrayList<Medicine> allMedecines = databaseAdapter.selectAllMedecines();
        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < allMedecines.size(); i++) {
        	 RowItem item = new RowItem(R.drawable.antivirus, allMedecines.get(i).getName(), allMedecines.get(i).getType());

        //	 item.setMedId(allMedecines.get();
             rowItems.add(item);
        }
 
        listView = (ListView) findViewById(R.id.listView1);
        CustomBaseAdapter adapter = new CustomBaseAdapter(this, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((OnItemClickListener) this);
      //  listView.setOnItemLongClickListener((OnItemLongClickListener)this);
        registerForContextMenu(listView);
    }
    
/* @Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	 DatabaseHelper databaseHelper = new  DatabaseHelper(this);
     DatabaseAdapter databaseAdapter = new DatabaseAdapter(databaseHelper);
     databaseAdapter.insertMedecine("katafklam", "good medecine", "medecine", "med", 0, null, null);
     ArrayList<Medicine> allMedecines = databaseAdapter.selectAllMedecines();
     rowItems = new ArrayList<RowItem>();
     for (int i = 0; i < allMedecines.size(); i++) {
     	 RowItem item = new RowItem(R.drawable.antivirus, allMedecines.get(i).getName(), allMedecines.get(i).getType());
          rowItems.add(item);
     }

     listView = (ListView) findViewById(R.id.listView1);
     CustomBaseAdapter adapter = new CustomBaseAdapter(this, rowItems);
     listView.setAdapter(adapter);
     listView.setOnItemClickListener((OnItemClickListener) this);
   //  listView.setOnItemLongClickListener((OnItemLongClickListener)this);
     registerForContextMenu(listView);
	
}*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
    //used in actions of buttons in action bar
   
    // long press on list item
	
	
   



@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Take appropriate action for each action item click
    switch (item.getItemId()) {
    case R.id.action_search:
        // search action
        return true;
    case R.id.action_location_found:
        // location found
    	
    	Intent i = new Intent(HomeActivity.this, AddMedicineActivity.class);
		startActivity(i);
        return true;
    case R.id.action_refresh:
        // refresh
    	syncMethod();
        return true;
   
    default:
        return super.onOptionsItemSelected(item);
    }
}
public void syncMethod(){
	Toast.makeText(getApplicationContext(), "Synch button", Toast.LENGTH_SHORT).show();
	DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
	DatabaseAdapter databaseAdapter = new DatabaseAdapter(databaseHelper);
	SyncWithServer syncServer = new SyncWithServer();
	ArrayList<Medicine> medecines = databaseAdapter.selectToSync();
	Medicine [] med = medecines.toArray(new Medicine[medecines.size()]);
	syncServer.execute(med);
}


//create context menu
@Override
public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
super.onCreateContextMenu(menu, v, menuInfo);
	menu.setHeaderTitle("Options");
	menu.add(0, v.getId(), 0, "Edit");
	menu.add(0, v.getId(), 0, "Delete");
}

@Override
public boolean onContextItemSelected(MenuItem item) {
	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
  
 	if(item.getTitle()=="Edit"){function1(item.getItemId());}
	else if(item.getTitle()=="Delete"){function2(item.getItemId());}
	else {return false;}
return true;
}

public void function1(int id){
	Toast.makeText(this, "function 1 called", 1000).show();
	Intent i = new Intent(HomeActivity.this, AddMedicineActivity.class);
	i.putExtra("postition", id);
	startActivity(i);
}
public void function2(int id){
	Toast.makeText(this, "function 2 called", 1000).show();
	DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
	DatabaseAdapter adapter = new  DatabaseAdapter(helper);
	adapter.deleteMedecine(rowItems.get(id).getMedId());
	rowItems.remove(id);
	//delete code and reload the list
}

public class SyncWithServer extends AsyncTask<Medicine, Void, Integer> {
	private static final int SYNC_SUCESS = 1;
	//private static final int INVALIED_EMAIL_OR_PASSWORD = 2;
	private static final int SYNC_FAILED = 3;
	private int syncStatus;
	//write the url of the back end servlet
	private static final String SYNC_URL = "http://10.145.238.152:8084/MedicalReminderServer/newmedecines";
	
	JSONParser jsonObj ;

	

	@Override
	protected Integer doInBackground(Medicine... params) {
		// TODO Auto-generated method stub
		
		
		Gson myGson = new Gson();
		java.lang.reflect.Type myType = new TypeToken<Medicine[]>(){}.getType();
		String jsonString = myGson.toJson(params,myType);
		Log.i("json",jsonString);
		
		JSONParser parser = new JSONParser();
		BasicNameValuePair requestParam = new BasicNameValuePair("data", jsonString);
		SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
		String userEmail = sharedPreferences.getString("emailUser", "default");
		BasicNameValuePair emailParam = new BasicNameValuePair("email", userEmail);
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(requestParam);
		parameters.add(emailParam);
		JSONObject jsonResponse = parser.makeHttpRequest(SYNC_URL, "POST",
				parameters);
		int status = 0;
	/*	try {
			status = jsonResponse.getInt("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		return status;
	}
	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		String message = null;

		if (result == 0){//login successfull
			Intent intent1 = new Intent(HomeActivity.this, HomeActivity.class);
			startActivity(intent1);
			message = "Synced successfully";
		}
			
		
		else
			message = "Login Failed";

		Toast.makeText(HomeActivity.this, message, Toast.LENGTH_LONG)
				.show();

	}
}

}
