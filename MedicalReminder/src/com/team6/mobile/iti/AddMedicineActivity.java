package com.team6.mobile.iti;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.team6.mobile.iti.R;
import com.team6.mobile.iti.beans.Medicine;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddMedicineActivity extends Activity {
	//sarah put that 
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	   String timeStamp;
	// directory name to store captured images and videos
	private static final String IMAGE_DIRECTORY_NAME = "MedicalReminder";

	public Uri fileUri; // file url to store image/video
	//end "sarah"

	// Edit Text Medicine Name
	EditText edtMedicineName;

	// Spinner Medicine Type
	Spinner sprMedicineType;

	// Image View Medicine Image
	ImageView imgvMedicineImage;

	// Button Set Schedule
	Button btnSetSchedule;
	
	// medicine bean
	private Medicine medicine;


	// types images
	private int [] typesIcons;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_medicine);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		// set array of images for types
		int [] arr = {R.drawable.ic_capsules, R.drawable.ic_tablets, R.drawable.ic_injection, R.drawable.ic_pills
				, R.drawable.ic_sprays, R.drawable.ic_patches, R.drawable.ic_drops,  R.drawable.ic_milligrams};
		typesIcons = arr;
		
		// create medicine object
		medicine = new Medicine();

		// find medicine name view
		edtMedicineName = (EditText) findViewById(R.id.edtMedicineName);

		// find medicine type view
		sprMedicineType = (Spinner) findViewById(R.id.sprMedicineType);
		
		// get medicine types array
		final String [] types = getResources().getStringArray(R.array.medicineTypes);
		
		// create adapter for type spinner
		TypeSpinnerAdapter adapter = new TypeSpinnerAdapter(this, android.R.layout.simple_list_item_1, types);
		
		// apply adapter to spinner
		sprMedicineType.setAdapter(adapter);

		// find medicine image view
		imgvMedicineImage = (ImageView) findViewById(R.id.imgvMedicineImage);

		// find set schedule button view
		btnSetSchedule = (Button) findViewById(R.id.btnSetSchedule);

		// set on click listener on set schedule button
		btnSetSchedule.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(AddMedicineActivity.this,
						SetScheduleActivity.class);
				
				// set medicine data
				medicine.setName(edtMedicineName.getText().toString());
				String medType = types[sprMedicineType.getSelectedItemPosition()];
				medicine.setType(medType);
				
				// add medicine object on intent
				intent.putExtra("medicine", medicine);
				
				startActivity(intent);
				// setAlram(v);
			}
		});
	imgvMedicineImage.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
            captureImage();
			
		}
	});	
	
	

    // Checking camera availability
    if (!isDeviceSupportCamera()) {
        Toast.makeText(getApplicationContext(),
                "Sorry! Your device doesn't support camera",
                Toast.LENGTH_LONG).show();
        // will close the app if the device does't have camera
        finish();
    }
}

/**
 * Checking device has camera hardware or not
 * */
private boolean isDeviceSupportCamera() {
    if (getApplicationContext().getPackageManager().hasSystemFeature(
            PackageManager.FEATURE_CAMERA)) {
        // this device has a camera
        return true;
    } else {
        // no camera on this device
        return false;
    }
}

/**
 * Capturing Camera Image will lauch camera app requrest image capture
 */
private void captureImage() {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
    Log.i("xxxxpath", ""+fileUri);
    Log.i("xxxxpath2", ""+fileUri.toString());

    medicine.setImageURL(fileUri.getPath());                        //try1
    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
    // start the image capture Intent
    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
}

/**
 * Here we store the file url as it will be null after returning from camera
 * app
 */
@Override
protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    // save file url in bundle as it will be null on scren orientation
    // changes
    outState.putParcelable("file_uri", fileUri);
}

@Override
protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);

    // get the file url
    fileUri = savedInstanceState.getParcelable("file_uri");
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // if the result is capturing Image
    if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            // successfully captured the image
            // display it in image view
            previewCapturedImage();
        } else if (resultCode == RESULT_CANCELED) {
            // user cancelled Image capture
            Toast.makeText(getApplicationContext(),
                    "User cancelled image capture", Toast.LENGTH_SHORT)
                    .show();
        } else {
            // failed to capture image
            Toast.makeText(getApplicationContext(),
                    "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}


private void previewCapturedImage() {
    try {


        // bimatp factory
        BitmapFactory.Options options = new BitmapFactory.Options();

        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 8;

        final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                options);

        imgvMedicineImage.setImageBitmap(bitmap);
    } catch (NullPointerException e) {
        e.printStackTrace();
    }
}

public Uri getOutputMediaFileUri(int type) {
    return Uri.fromFile(getOutputMediaFile(type));
}

private  File getOutputMediaFile(int type) {
	String directory;
	String imageName;

    // External sdcard location
    File mediaStorageDir = new File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            IMAGE_DIRECTORY_NAME);

  //  directory=Environment.getExternalStorageDirectory().getName()+""+IMAGE_DIRECTORY_NAME;
    directory=Environment.getExternalStorageDirectory().getAbsolutePath()+""+IMAGE_DIRECTORY_NAME;

             Log.i("IIIIEnv", directory);

    // Create the storage directory if it does not exist
    if (!mediaStorageDir.exists()) {
        if (!mediaStorageDir.mkdirs()) {
            Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                    + IMAGE_DIRECTORY_NAME + " directory");
            return null;
        }
    }

    // Create a media file name
    timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
            Locale.getDefault()).format(new Date());
    File mediaFile;
    if (type == MEDIA_TYPE_IMAGE) {
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
    }  else {
        return null;
    }

    imageName="IMG_" + timeStamp + ".jpg";
   // String imageUrl = returnImageFullPath( directory ,  imageName);
    
    //medicine.setImageURL(imageUrl);               ////////////////////////gaber
    
    return mediaFile;
}


/*String returnImageFullPath(String directory , String imageName){
	
	String imagePath=directory+"/Pictures/"+imageName;
	Log.i("IIIIIIIIIIIIIIIIIIIIIIII",imagePath); //false
	return imagePath;
	
}*/
	
	

	public void setAlram(View v) {

		edtMedicineName = (EditText) findViewById(R.id.edtMedicineName);
		int i = Integer.parseInt(edtMedicineName.getText().toString());
		Intent intent = new Intent(this, ReminderDialog.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				this.getApplicationContext(), 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
				+ (i * 1000), pendingIntent);// i seconds *1000 = Xmillisecs

		Toast.makeText(this, "Alarm set in " + i + " seconds",
				Toast.LENGTH_SHORT).show();

	}

	class TypeSpinnerAdapter extends ArrayAdapter<String> {

		String[] names;
		Context context;

		public TypeSpinnerAdapter(Context ctx, int txtViewResourceId,
				String[] objects) {
			super(ctx, txtViewResourceId, objects);
			context = ctx;
			names = objects;
		}

		@Override
		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
			return getCustomView(position, cnvtView, prnt);
		}

		@Override
		public View getView(int pos, View cnvtView, ViewGroup prnt) {
			return getCustomView(pos, cnvtView, prnt);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {

			// get layout inflater service
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// inflate single row layout
			View singleRowView = inflater.inflate(R.layout.spinner_type_row,
					parent, false);

			// set medicine type
			TextView tvType = (TextView) singleRowView
					.findViewById(R.id.tvMedicineType);
			tvType.setText(names[position]);

			// set medicine type image
			ImageView img = (ImageView) singleRowView.findViewById(R.id.imgvMedicineImg);

			//set image
			img.setImageResource(typesIcons[position]);
			

			return singleRowView;
		}

	}

}

