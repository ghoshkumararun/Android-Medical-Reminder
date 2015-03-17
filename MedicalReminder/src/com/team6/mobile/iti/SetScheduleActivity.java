package com.team6.mobile.iti;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.team6.mobile.iti.beans.Medicine;
import com.team6.mobile.iti.beans.TimeDto;

import MyUtility.AddMedicineUtility;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.Touch;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.*;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SetScheduleActivity extends Activity {

	// interval linear layput
	private LinearLayout llInterval;

	// duration linear layput
	private LinearLayout layoutDuration;

	// instruction linear layput
	private LinearLayout layoutInstruction;

	// start day linear layput
	private LinearLayout layoutStartDay;

	// time and dose linear layput
	private LinearLayout layoutTimeAndDose;

	// MyOnclickListener
	private MyClickListener listener;

	// Done button
	private Button btnDone;

	// interval array
	private String[] intervalArr;

	// duration array
	private String[] durationArray;

	// instruction array
	private String[] instructionArray;

	// interval choice position
	private Integer intervalChoicePos = 0;

	// duration choice position
	private Integer durationChoicePos = 0;

	// instruction choice position
	private Integer instructionChoicePos = 0;

	// start date
	private long startDate;

	// medicine bean
	private Medicine medicine;

	// interval text view
	private TextView tvInterval;

	// Time and dose text view
	private TextView tvTimeAndDose;

	// start day text view
	private TextView tvStartDay;

	// Duration text view
	private TextView tvDuration;

	// Instruction text view
	private TextView tvInstruction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_schedule);
		
		// init positions
		intervalChoicePos = new Integer(0);
		durationChoicePos = new Integer(0);
		instructionChoicePos = new Integer(0);

		// find interval text view
		tvInterval = (TextView) findViewById(R.id.tvInterval);

		// find time and dose text view
		tvTimeAndDose = (TextView) findViewById(R.id.tvTimeAndDose);

		// find start day text view
		tvStartDay = (TextView) findViewById(R.id.tvStartDay);
		
		// find duration text view
		tvDuration = (TextView) findViewById(R.id.tvDuration);

		// find instruction text view
		tvInstruction = (TextView) findViewById(R.id.tvInstruction);
		
		// get medicine object from intent
		medicine = getIntent().getParcelableExtra("medicine");

		// get medicine object from intent
		medicine = getIntent().getParcelableExtra("medicine");

		// create myclick listener
		listener = new MyClickListener();

		// button of Done
		btnDone = (Button) findViewById(R.id.btnDone);
		btnDone.setOnClickListener(listener);

		// interval layout and set its listener
		llInterval = (LinearLayout) findViewById(R.id.llInterval);
		llInterval.setOnClickListener(listener);

		// get Interval array
		intervalArr = getResources().getStringArray(R.array.medicineInterval);

		// duration layout and set its listener
		layoutDuration = (LinearLayout) findViewById(R.id.layoutDuration);
		layoutDuration.setOnClickListener(listener);

		// get Interval array
		durationArray = getResources().getStringArray(
				R.array.medicineFullDuration);

		// instruction layout and set its listener
		layoutInstruction = (LinearLayout) findViewById(R.id.layoutInstruction);
		layoutInstruction.setOnClickListener(listener);

		// get Interval array
		instructionArray = getResources().getStringArray(
				R.array.medicineInstrutions);

		// start day layout and set its listener
		layoutStartDay = (LinearLayout) findViewById(R.id.layoutStartDay);
		layoutStartDay.setOnClickListener(listener);

		// time and dose layout and set its listener
		layoutTimeAndDose = (LinearLayout) findViewById(R.id.layoutTimeAndDose);
		layoutTimeAndDose.setOnClickListener(listener);

		// set start date to date of today
		startDate = Calendar.getInstance().getTimeInMillis();
		
		// set textviews to default values
		tvInterval.setText(intervalArr[instructionChoicePos]);
		tvStartDay.setText(DateFormat.format("yyyy-MM-dd", startDate));
		tvDuration.setText(durationArray[durationChoicePos]);
		tvInstruction.setText(instructionArray[instructionChoicePos]);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1) {

			ArrayList<TimeDto> times = data
					.getParcelableArrayListExtra("times");

			// put times in medicine bean
			if (times != null){
				medicine.setTimes(times);
				
				if(times.size() >0)
					tvTimeAndDose.setText(times.size()+" times");
				else
					tvTimeAndDose.setText("None");
				
			}
		}

	}

	/**
	 * this class is for handling clicks on layouts (interval, duration, ...etc)
	 */
	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			FragmentManager manager = getFragmentManager();
			ChooseListDialog dialog;

			switch (v.getId()) {

			case R.id.llInterval:

				dialog = new ChooseListDialog(SetScheduleActivity.this,
						intervalArr, intervalChoicePos);
				dialog.show(manager, "dialog");

				break;

			case R.id.layoutDuration:

				manager = getFragmentManager();

				dialog = new ChooseListDialog(SetScheduleActivity.this,
						durationArray, durationChoicePos);
				dialog.show(manager, "dialog");

				break;

			case R.id.layoutInstruction:

				manager = getFragmentManager();

				dialog = new ChooseListDialog(SetScheduleActivity.this,
						instructionArray, instructionChoicePos);
				dialog.show(manager, "dialog");

				break;


			case R.id.btnDone:

				// set medicine bean data
				medicine.setRepetition(intervalArr[intervalChoicePos]);
				medicine.setStart_date(startDate);
				medicine.setInstruction(instructionArray[instructionChoicePos]);
				String duration = durationArray[durationChoicePos];
				medicine.setEnd_date(convertDurationToLong(duration));
				
				Log.i("testTime", ""+medicine.getTimes().get(0).getTake_time());
				
				// add medicine in db
				addMedicineInDb(medicine);
				
				// go to home actiivty
				Intent in =new Intent(SetScheduleActivity.this, HomeActivity.class);
				startActivity(in);

				break;



			case R.id.layoutStartDay:

				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(manager, "datePicker");

				break;

			case R.id.layoutTimeAndDose:

				Intent intent = new Intent(SetScheduleActivity.this,
						TimeAndDoseActivity.class);
				
				// put times on intent if its size > 0
				if(medicine.getTimes() != null && medicine.getTimes().size() > 0)
					intent.putParcelableArrayListExtra("timesList", (ArrayList<? extends Parcelable>) medicine.getTimes());
				
				startActivityForResult(intent, 1);

				break;

			default:
				break;
			}

		}
	}

	class ChooseListDialog extends DialogFragment {

		private String[] listOfChoices;
		private Integer choicePos;
		private Context context;
		private MyOnItemClickListener onItemListener;
		private ChooseListAdapter adapter;

		public ChooseListDialog(Context context, String[] arr, Integer choicePos) {

			this.listOfChoices = arr;
			this.choicePos = choicePos;
			this.context = context;

			// create custom adapter
			adapter = new ChooseListAdapter(context,
					R.layout.choose_list_single_row, listOfChoices, choicePos);

			// create onItemClick Listener
			onItemListener = new MyOnItemClickListener(this, adapter, choicePos);

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			// get dialog view
			View view = inflater.inflate(R.layout.dialog_choose_list, null,
					false);

			// get List view
			ListView chooseList = (ListView) view.findViewById(R.id.chooseList);

			// set onItemClick listener on listview
			chooseList.setOnItemClickListener(onItemListener);

			// apply adapter on list view
			chooseList.setAdapter(adapter);

			getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			return view;
		}

	}

	class ChooseListAdapter extends ArrayAdapter<String> {

		private Context context;
		private String[] strings;
		private int choicePos;

		public ChooseListAdapter(Context context, int textViewResourceId,
				String[] objects, Integer choicePos) {
			super(context, textViewResourceId, objects);
			this.context = context;
			this.strings = objects;
			this.choicePos = choicePos.intValue();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// get layout inflater service
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// inflate single row layout
			View singleRowView = inflater.inflate(
					R.layout.choose_list_single_row, parent, false);

			// get text view
			TextView tv = (TextView) singleRowView
					.findViewById(R.id.tvSingleChoose);
			tv.setText(strings[position]);

			// get check image view
			ImageView iv = (ImageView) singleRowView.findViewById(R.id.ivCheck);

			if (position == choicePos)
				iv.setVisibility(ImageView.VISIBLE);

			return singleRowView;
		}

	}

	class MyOnItemClickListener implements OnItemClickListener {

		private DialogFragment dialog;
		private ArrayAdapter<String> adapter;
		private Integer choicePos;

		public MyOnItemClickListener(DialogFragment dialog,
				ArrayAdapter<String> adapter, Integer choicePos) {

			this.dialog = dialog;
			this.adapter = adapter;
			this.choicePos = choicePos;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {

			if (choicePos == intervalChoicePos){
				intervalChoicePos = position;
				tvInterval.setText(intervalArr[position]);
			}
			else if (choicePos == durationChoicePos){
				durationChoicePos = position;
				tvDuration.setText(durationArray[durationChoicePos]);
			}
			else if (choicePos == instructionChoicePos){
				instructionChoicePos = position;
				tvInstruction.setText(instructionArray[instructionChoicePos]);
			}

			adapter.notifyDataSetChanged();
			dialog.dismiss();
		}

	}

	class DatePickerFragment extends DialogFragment implements
			OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			c.setTimeInMillis(startDate);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {

			Calendar cal = Calendar.getInstance();
			cal.set(year, month, day);
			startDate = cal.getTimeInMillis();
			tvStartDay.setText(DateFormat.format("yyyy-MM-dd", cal.getTime()));
			
		}
	}

	private Long convertDurationToLong(String duration) {

		String[] arr = duration.split(" ");
		int numOfDays = Integer.parseInt(arr[0]);

		if (arr[1].charAt(0) == 'W')
			numOfDays *= 7;
		else if (arr[1].charAt(0) == 'M')
			numOfDays *= 30;
		else if (arr[1].charAt(0) == 'Y')
			numOfDays *= 365;

		Calendar cal = Calendar.getInstance();

		cal.setTimeInMillis(startDate);
		cal.add(cal.DATE, numOfDays);

		return cal.getTimeInMillis();
	}

	private void addMedicineInDb(Medicine med) {

		AddMedicineUtility obj = new AddMedicineUtility();
		
		//Log.i("xxxxAdd",med.getName());
		//Log.i("xxxxAdd",med.getImageUrl());
	    //	DatabaseHelper helpObj=new DatabaseHelper(this);
		//DatabaseAdapter adptObj=new DatabaseAdapter(helpObj);
		//adptObj.insertMedecine(med.getName(), med.getDesc(), med.getType(),
		//	med.getImageUrl(), med.getStart_date(), med.getEnd_date(), med.getRepetition());
		obj.addMedicine(med, this);
		obj.setAlarm(med,SetScheduleActivity.this);
		Toast.makeText(SetScheduleActivity.this, "Alram Set", Toast.LENGTH_SHORT).show();

	}

}
