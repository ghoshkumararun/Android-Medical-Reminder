package com.team6.mobile.iti;



import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ReminderListView extends Activity {
	ListView medicineList;
	String[] titles;
	int[] images={
			R.drawable.sarah,
			R.drawable.sarah2,
			R.drawable.sarah3,
			R.drawable.sarah5,
			R.drawable.sarah7,
			R.drawable.sarah,
			R.drawable.sarah2,
			R.drawable.sarah3,
			R.drawable.sarah,
			R.drawable.sarah2,
			R.drawable.sarah3,
			R.drawable.sarah5,
			R.drawable.sarah7,
			R.drawable.ddddddddddd};
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list_view);
        Resources resources=getResources();
        titles = resources.getStringArray(R.array.medicines);
        medicineList=(ListView) findViewById(R.id.medicineList);
        customAdapter adapter=new customAdapter(this, titles, images);
        medicineList.setAdapter(adapter);
        //adding actions to a list 
    /*    medicineList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//TextView t = (TextView) view;
				//Toast.makeText(getApplicationContext(), t.getText(), 1000).show();
				Log.i("XXXXXXXXXXXXXXXXXXXXXX","from on click ");
				CustomDialogClass dialog = new CustomDialogClass(ReminderListView.this);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();

			}
		});

	}   */             
        
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	 MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.action_bar, menu);
  
         return super.onCreateOptionsMenu(menu);
    }
class MyViewHolder{
	TextView txtView;
	ImageView imgView;
	CheckBox check;
	MyViewHolder(View v){
		 imgView=(ImageView) v.findViewById(R.id.imageView1);
		 txtView=(TextView) v.findViewById(R.id.txtMedicineName);
		 check=(CheckBox) v.findViewById(R.id.checkBox1);
			}
	
}

class customAdapter extends ArrayAdapter<String> implements OnClickListener{
Context context;
int [] img;
String [] titleArray;
View row;


	  customAdapter(Context c,String [] titles,int [] images){
		  super(c,R.layout.single_row,R.id.txtMedicineName,titles);
		  this.img=images;
		  this.context=c;
		  this.titleArray=titles;
	  }
	  @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		   row=convertView; //to use it in recyclying
		   MyViewHolder holder=null;
		   if(row==null){
		      LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		       row=inflator.inflate(R.layout.single_row, parent,false);
		       holder=new MyViewHolder(row);
		       row.setTag(holder);
		       Log.i("XXXXXXXXXXXXXx", "creating new holder");
		  }
		  else {
			  holder=(MyViewHolder) row.getTag();
		       Log.i("XXXXXXXXXXXXXx", "Recycling ");

		  }
		  row.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("XXXXXXXXXXXXXXXXXXXXXX","from on click ");
				CustomDialogClass dialog = new CustomDialogClass(ReminderListView.this);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();	
			}
		});
		  

			//ImageView image = (ImageView) view.findViewById(R.id.imgview);
			//image.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.id.imgview, 50, 50));
			//image.setImageResource(LastListview.this.image[position]);
		  
		holder.imgView.setImageResource(img[position]);
		holder.txtView.setText(titleArray[position]);
		
		 return row;
	}
	@Override
	public void onClick(View v) {
		Log.i("XXXXXXXXXXXXXXXXXXXXXX","from on click ");
		CustomDialogClass dialog = new CustomDialogClass(ReminderListView.this);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.show();	
	}
  
	  /*public int calculateInSampleSize(BitmapFactory.Options options,
		int reqWidth, int reqHeight) {
	// Raw height and width of image
	final int height = options.outHeight;
	final int width = options.outWidth;
	int inSampleSize = 1;

	if (height > reqHeight || width > reqWidth) {

		final int halfHeight = height / 2;
		final int halfWidth = width / 2;

		while ((halfHeight / inSampleSize) > reqHeight
				&& (halfWidth / inSampleSize) > reqWidth) {
			inSampleSize *= 2;
		}
	}

	return inSampleSize;
}

public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
		int reqWidth, int reqHeight) {

	final BitmapFactory.Options options = new BitmapFactory.Options();
	options.inJustDecodeBounds = true;
	BitmapFactory.decodeResource(res, resId, options);

	options.inSampleSize = calculateInSampleSize(options, reqWidth,
			reqHeight);

	options.inJustDecodeBounds = false;
	return BitmapFactory.decodeResource(res, resId, options);

}*/  
  
  }


  }
