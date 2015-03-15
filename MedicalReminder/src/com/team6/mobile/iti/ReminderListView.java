package com.team6.mobile.iti;

import java.io.File;
import java.util.ArrayList;

import com.team6.mobile.iti.beans.Medicine;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
	ArrayList<Medicine> medicines;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder_list_view);
		medicineList = (ListView) findViewById(R.id.medicineList);

		DatabaseHelper helper = new DatabaseHelper(this);
		DatabaseAdapter databaseAdapter = new DatabaseAdapter(helper);
		medicines = databaseAdapter.selectAllMedecines();
		customAdapter adapter = new customAdapter(this, R.layout.single_row,
				medicines);
		medicineList.setAdapter(adapter);

	}

	// --------------------------------------------------class
	// Adapter............................................//
	class customAdapter extends ArrayAdapter<Medicine> implements
			OnClickListener {
		Context context;
		View row;

		customAdapter(Context c, int layout, ArrayList<Medicine> medName) {
			super(c, layout, medName);
			this.context = c;
		}

		public Bitmap decodeFile(String filePath, int reqWidth, int reqHeight) {
			// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePath, options);

			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeFile(filePath, options);
		}

		private int calculateInSampleSize(BitmapFactory.Options options,
				int reqWidth, int reqHeight) {
			// Raw height and width of image
			final int height = options.outHeight;
			final int width = options.outWidth;
			int inSampleSize = 1;

			if (height > reqHeight || width > reqWidth) {

				// Calculate ratios of height and width to requested height and
				// width
				final int heightRatio = Math.round((float) height
						/ (float) reqHeight);
				final int widthRatio = Math.round((float) width
						/ (float) reqWidth);

				// Choose the smallest ratio as inSampleSize value, this will
				// guarantee
				// a final image with both dimensions larger than or equal to
				// the
				// requested height and width.
				inSampleSize = heightRatio < widthRatio ? heightRatio
						: widthRatio;
			}

			return inSampleSize;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			row = convertView; // to use it in recyclying
			MyViewHolder holder = null;
			if (row == null) {
				LayoutInflater inflator = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflator.inflate(R.layout.single_row, parent, false);
				holder = new MyViewHolder(row);
				row.setTag(holder);
				Log.i("XXXXXXXXXXXXXx", "creating new holder");
			} else {

				holder = (MyViewHolder) row.getTag();
				Log.i("XXXXXXXXXXXXXx", "Recycling ");

			}

			row.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.i("XXXXXXXXXXXXXXXXXXXXXX", "from on click ");
					CustomDialogClass dialog = new CustomDialogClass(
							ReminderListView.this);
					dialog.getWindow().setBackgroundDrawable(
							new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
				}
			});

			Bitmap myImg = decodeFile(medicines.get(position).getImageUrl(),
					50, 50);
			holder.imgView.setImageBitmap(myImg);
			holder.txtView.setText(medicines.get(position).getName());
			if (medicines.get(position).getIsTaken()== 1) {
				holder.txtView.setText("is taken");
			} else

				holder.txtView.setText("is not taken !");

			return row;
		}

		@Override
		public void onClick(View v) {
			Log.i("XXXXXXXXXXXXXXXXXXXXXX", "from on click ");
			CustomDialogClass dialog = new CustomDialogClass(
					ReminderListView.this);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));
			dialog.show();
		}

	}

	// --------------------------------------------------classHolder....................................................//
	class MyViewHolder {
		ImageView imgView;
		TextView txtView;
		TextView txtView2;
		CheckBox check;

		MyViewHolder(View v) {
			imgView = (ImageView) v.findViewById(R.id.imageView1);
			txtView = (TextView) v.findViewById(R.id.txtMedicineName);
			txtView2 = (TextView) v.findViewById(R.id.takenTxtV);
			check = (CheckBox) v.findViewById(R.id.checkBox1);
		}
	}
	// --------------------------------------------------classHolder................................................//
}
