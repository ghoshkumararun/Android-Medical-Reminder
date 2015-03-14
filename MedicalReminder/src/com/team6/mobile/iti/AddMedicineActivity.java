package com.team6.mobile.iti;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

	// directory name to store captured images and videos
	private static final String IMAGE_DIRECTORY_NAME = "MyImages";

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_medicine);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// find medicine name view
		edtMedicineName = (EditText) findViewById(R.id.edtMedicineName);

		// find medicine type view
		sprMedicineType = (Spinner) findViewById(R.id.sprMedicineType);

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
				startActivity(intent);
				//setAlram(v);
				
				
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

        //imgPreview.setVisibility(View.VISIBLE);

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

private static File getOutputMediaFile(int type) {

    // External sdcard location
    File mediaStorageDir = new File(
            Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            IMAGE_DIRECTORY_NAME);

    // Create the storage directory if it does not exist
    if (!mediaStorageDir.exists()) {
        if (!mediaStorageDir.mkdirs()) {
            Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                    + IMAGE_DIRECTORY_NAME + " directory");
            return null;
        }
    }

    // Create a media file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
            Locale.getDefault()).format(new Date());
    File mediaFile;
    if (type == MEDIA_TYPE_IMAGE) {
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
    }  else {
        return null;
    }

    return mediaFile;
}

	}
	

