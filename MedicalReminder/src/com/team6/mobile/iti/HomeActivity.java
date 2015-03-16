package com.team6.mobile.iti;


import java.util.ArrayList;
import java.util.List;

import com.team6.mobile.iti.beans.Medicine;





import android.app.Activity;
import android.app.Application;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.os.Bundle;
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
    	
    	Intent i = new Intent(HomeActivity.this, ViewMedActivity.class);
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
        databaseAdapter.insertMedecine("katafklam", "good medecine", "medecine", "med");
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
    }
    
 
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
	//delete code and reload the list
}

// override on back pessed
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	
}

}