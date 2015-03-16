package com.team6.mobile.iti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.team6.mobile.iti.beans.*;

public class TimeAndDoseActivity extends Activity {

	// add time button
	private Button btnAddTime;

	// Times List View
	private ListView lvTimes;

	// list of times
	private List<TimeDto> medicineTimes;

	// list adapter
	private TimeListAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_and_dose);
		
		// init medicine times list
		medicineTimes = new ArrayList<TimeDto>();

		// add arraylist of medicine times on intent to go back to add medicine
		// activity
		Intent returnIntent = new Intent();
		returnIntent.putParcelableArrayListExtra("times",
				(ArrayList<? extends Parcelable>) medicineTimes);
		setResult(1, returnIntent);

		// get add time button
		btnAddTime = (Button) findViewById(R.id.btnAddTime);

		// add onclick listener
		btnAddTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DialogFragment newFragment = new TimePickerFragment();
				newFragment.show(getFragmentManager(), "timePicker");

			}
		});

		// get times listview
		lvTimes = (ListView) findViewById(R.id.lvTimeList);

		// create custom adapter
		adapter = new TimeListAdapter(this,
				android.R.layout.simple_expandable_list_item_1, medicineTimes);

		// set listview adapter
		lvTimes.setAdapter(adapter);

	}

	class TimePickerFragment extends DialogFragment implements
			OnTimeSetListener {

		private boolean timeSetted = false;

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it

			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			if (!timeSetted) {
				TimeDto time = new TimeDto();
				Calendar cal = Calendar.getInstance();
				
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				
				cal.set(year, month, day, hourOfDay, minute);
				cal.set(Calendar.SECOND, 00);
				
				time.setTake_time(cal.getTimeInMillis());
				
				time.setDose(0.25f);
				medicineTimes.add(time);
				adapter.notifyDataSetChanged();
				timeSetted = true;

			}
		}

	}

	class TimeListAdapter extends ArrayAdapter<TimeDto> {

		private Context context;

		public TimeListAdapter(Context context, int textViewResourceId,
				List<TimeDto> times) {
			super(context, textViewResourceId, times);
			this.context = context;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			// get layout inflater service
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// inflate single row layout
			View singleRowView = inflater.inflate(
					R.layout.item_time_single_row, parent, false);

			// get Textview of time
			TextView tvTime = (TextView) singleRowView
					.findViewById(R.id.tvTime);
			
			// converting time from millis to be readable
			Calendar cal =  Calendar.getInstance();
			cal.setTimeInMillis(medicineTimes.get(position).getTake_time());
			tvTime.setText(DateFormat.format("hh:mm", cal));

			// get dose text view
			TextView tvDose = (TextView) singleRowView
					.findViewById(R.id.tvDose);
			tvDose.setText("" + medicineTimes.get(position).getDose());

			// get more button
			Button btnMore = (Button) singleRowView.findViewById(R.id.btnMore);
			btnMore.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					TimeDto time = medicineTimes.get(position);
					time.setDose(time.getDose() + 0.25f);
					adapter.notifyDataSetChanged();
				}
			});

			// get less button
			Button btnLess = (Button) singleRowView.findViewById(R.id.btnLess);
			btnLess.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					TimeDto time = medicineTimes.get(position);
					if (time.getDose() > 0.25f) {
						time.setDose(time.getDose() - 0.25f);
						adapter.notifyDataSetChanged();
					}
				}
			});

			// get remove button
			Button btnRemove = (Button) singleRowView
					.findViewById(R.id.btnRemove);
			btnRemove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					medicineTimes.remove(position);
					adapter.notifyDataSetChanged();
				}
			});

			return singleRowView;
		}

	}

}
