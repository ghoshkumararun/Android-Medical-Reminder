package com.team6.mobile.iti;



import android.os.Bundle;
import android.app.Activity;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;



public class ListActivity extends Activity implements OnItemClickListener {

   
         
       
                
         
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
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_list);
         
                rowItems = new ArrayList<RowItem>();
                for (int i = 0; i < titles.length; i++) {
                    RowItem item = new RowItem(images[i], titles[i], descriptions[i]);
                    rowItems.add(item);
                }
         
                listView = (ListView) findViewById(R.id.listView1);
                CustomBaseAdapter adapter = new CustomBaseAdapter(this, rowItems);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((OnItemClickListener) this);
            }
         
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Item " + (position + 1) + ": " + rowItems.get(position),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
           
}


  

