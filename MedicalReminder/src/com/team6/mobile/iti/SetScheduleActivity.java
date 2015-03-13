package com.team6.mobile.iti;

import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SetScheduleActivity extends Activity {

	// interval linear layput
	private LinearLayout llInterval;

	// MyOnclickListener
	private MyClickListener listener;

	// Set Schedule button
	private Button btnSetSchedule;

	// interval array
	String[] intervalArr;

	// interval choice position
	Integer intervalChoicePos = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_schedule);

		// create myclick listener
		listener = new MyClickListener();

		btnSetSchedule = (Button) findViewById(R.id.button1);
		btnSetSchedule.setOnClickListener(listener);

		llInterval = (LinearLayout) findViewById(R.id.llInterval);
		llInterval.setOnClickListener(listener);

		// get Interval array
		intervalArr = getResources().getStringArray(R.array.medicineInterval);

	}

	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.llInterval:

				FragmentManager manager = getFragmentManager();

				ChooseListDialog dialog = new ChooseListDialog(SetScheduleActivity.this ,intervalArr,
						intervalChoicePos);
				dialog.show(manager, "dialog");

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
		
		public ChooseListDialog(Context context ,String[] arr, Integer choicePos) {

			this.listOfChoices = arr;
			this.choicePos = choicePos;
			this.context = context;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			// get dialog view
			View view = inflater.inflate(R.layout.dialog_choose_list, null,
					false);
			
			// get List view
			ListView chooseList = (ListView) view.findViewById(R.id.chooseList);
			
			// create custom adapter
			ChooseListAdapter adapter = new ChooseListAdapter(context, R.layout.choose_list_single_row, listOfChoices, choicePos);

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
			View singleRowView = inflater.inflate(R.layout.choose_list_single_row,
					parent, false);
			
			// get text view
			TextView tv = (TextView) singleRowView.findViewById(R.id.tvSingleChoose);
			tv.setText(strings[position]);
			
			// get check image view
			ImageView iv = (ImageView) singleRowView.findViewById(R.id.ivCheck);
			
			if(position == choicePos)
				iv.setVisibility(ImageView.VISIBLE);

			return singleRowView;
		}

	}

}
