package com.team6.mobile.iti;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.team6.mobile.iti.beans.Medicine;
import com.team6.mobile.iti.beans.TimeDto;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAdapter {
	DatabaseHelper databaseHelper;
	SQLiteDatabase database;
	private static final String TABLE_MEDICINE = "MEDICINE";
	private static final String MEDICINE_ID_COL = "ID";

	private static final String MEDICINE_NAME_COL = "NAME";// 1
	private static final String MEDICINE_IMAGE_URL_COL = "IMAGE_URL";// 2
	private static final String MEDICINE_START_DATE_COL = "START_DATE";// 3
	private static final String MEDICINE_END_DATE_COL = "END_DATE";// 4
	private static final String MEDECINE_DESC_COL = "DESC";// 5
	private static final String MEDECINE_TYPE_COL = "TYPE";// 6
	private static final String MEDECINE_REPETATION_COL = "REPETATION";// 7

	private static final String TABLE_DOSE_TIME = "DOSE_TIME";
	private static final String DOSE_MEDICINE_ID_COL = "MEDECINE_ID";
	private static final String DOSE_QUANTITY_COL = "DOSE";
	private static final String DOSE_TIME_COL = "TIME";
	private static final String DOSE_TAKEN_COL = "TAKEN";

	public DatabaseAdapter(DatabaseHelper h) {
		databaseHelper = h;
	}
	
	

	public long insertMedecine(String medName, String desc, String type,
			String urlImage, long startDate, Long endDate, String repeation) {

		database = databaseHelper.getWritableDatabase();
		long res = 0;
		String tableName = TABLE_MEDICINE;
		ContentValues medValues = new ContentValues();
		medValues.put(MEDICINE_NAME_COL, medName);// 1
		medValues.put(MEDECINE_DESC_COL, desc);// 5
		medValues.put(MEDECINE_TYPE_COL, type);// 6
		medValues.put(MEDICINE_START_DATE_COL, startDate);// 3
		medValues.put(MEDICINE_END_DATE_COL, endDate);// 4
		medValues.put(MEDECINE_REPETATION_COL, repeation);// 7
		medValues.put(MEDICINE_IMAGE_URL_COL, urlImage);// 2
		try {

			database.insert(tableName, null, medValues);
			res = 0;
		} catch (Exception ex) {
			res = 2;
		}

		// database.close();
		return res;
	}

	private int getMaxId() {
		database = databaseHelper.getReadableDatabase();

		String query = "SELECT MAX(ID) FROM MEDICINE";
		Cursor cursor = database.rawQuery(query, null);

		int id = 0;
		if (cursor.moveToFirst()) {
			do {
				id = cursor.getInt(0);
			} while (cursor.moveToNext());
		}
		return id;
	}

	public long insertMedecineIntoDoseTable(int isTaken, List<TimeDto> list,
			int id) {
		database = databaseHelper.getWritableDatabase();
		long res = 0;
		String tableName = TABLE_DOSE_TIME;
		int maxId = id;
		ContentValues medValues = new ContentValues();
		for (int i = 0; i < list.size(); i++) {
			medValues.put(DOSE_MEDICINE_ID_COL, maxId);
			medValues.put(DOSE_QUANTITY_COL, list.get(i).getDose());
			medValues.put(DOSE_TIME_COL, list.get(i).getTake_time());
			medValues.put(DOSE_TAKEN_COL, isTaken);
			try {

				database.insert(tableName, null, medValues);
				res = 0;
				Log.i("XXXXInertDose", "inserted succeffully");
			} catch (Exception ex) {
				// TODO: handle exception

				res = 2;
			}
		}

		// database.close();

		return res;
	}

	public ArrayList<Medicine> selectAllMedecines() {
		database = databaseHelper.getReadableDatabase();
		ArrayList<Medicine> allMedecine = new ArrayList<Medicine>();

		Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_MEDICINE
				+ ";", null);
		Medicine medObj = null;
		while (cursor.moveToNext() == true) {
			// res = 0;
			medObj = new Medicine();
			medObj.setId(cursor.getInt(cursor.getColumnIndex(MEDICINE_ID_COL)));
			medObj.setName(cursor.getString(cursor
					.getColumnIndex(MEDICINE_NAME_COL)));
			medObj.setType(cursor.getString(cursor
					.getColumnIndex(MEDECINE_TYPE_COL)));
			medObj.setDesc(cursor.getString(cursor
					.getColumnIndex(MEDECINE_REPETATION_COL)));
			medObj.setImageURL(cursor.getString(cursor
					.getColumnIndex(MEDICINE_IMAGE_URL_COL)));
			ArrayList<TimeDto> timeDtos = new ArrayList<TimeDto>();
			for (Long t : selectAllTimes(medObj.getId())) {
				TimeDto temp = new TimeDto();
				temp.setTake_time(t);
				timeDtos.add(temp);

			}
			medObj.setTimes(timeDtos);
			if (medObj != null)
				allMedecine.add(medObj);
		}

		return allMedecine;
	}

	public ArrayList<Long> selectAllTimes(int tempId) {
		database = databaseHelper.getReadableDatabase();
		ArrayList<Long> allTimes = new ArrayList<Long>();
		try {

			Cursor cursor = database.rawQuery("SELECT * FROM "
					+ TABLE_DOSE_TIME + ";", null);

			while (cursor.moveToNext() == true) {
				// res = 0;
				allTimes.add(cursor.getLong(cursor
						.getColumnIndex(DOSE_TIME_COL)));
			}

			// cursor.close();// close cursor

		} catch (Exception e) {
			// TODO: handle exception
			// res = 1;
		}

		return allTimes;
	}

	// sarah
	public ArrayList<Medicine> selectReminderMedecines(long currentTime) {
		database = databaseHelper.getReadableDatabase();
		ArrayList<Medicine> medReminder = new ArrayList<Medicine>(); // to add
		ArrayList<Medicine> allMedecine = selectAllMedecines(); // to add

		Medicine medObj = new Medicine();
		Log.i("testTime", ""+currentTime);
		long beginT = currentTime - 20*60*1000;
		long endT = currentTime + 20*60*1000;
		
		for(Medicine med : allMedecine ){
			
			Log.i("testTime", ""+med.getTimes().get(0).getTake_time());
			
			if(med.getTimes().get(0).getTake_time() > beginT && med.getTimes().get(0).getTake_time() < endT)
				medReminder.add(med);
			
		}
		
		
//		for (int i = 0; i < allMedecine.size(); i++) {
//			Log.i("XXXXSizeDose", "" + allMedecine.size());
//			if (allMedecine.get(i).getTimes() != null)
//				for (int j = 0; j < allMedecine.get(i).getTimes().size(); j++) // /////exception
//				{
//					long takenFromDataBase = allMedecine.get(i).getTimes()
//							.get(j).getTake_time();
//					
//					if (currentTime == takenFromDataBase) {
//						try {
//
//							Cursor cursor = database.rawQuery(
//									"SELECT * FROM MEDICINE WHERE  START_DATE BETWEEN "
//											+ beginT +" AND "+endT + " ;", null);
//
//							while (cursor.moveToNext() == true) {
//								medObj.setName(cursor.getString(cursor
//										.getColumnIndex(MEDICINE_NAME_COL)));
//								// medObj.setType(cursor.getString(cursor.getColumnIndex(MEDECINE_TYPE_COL)));
//								medObj.setDesc(cursor.getString(cursor
//										.getColumnIndex(MEDECINE_REPETATION_COL)));
//								medObj.setImageURL(cursor.getString(cursor
//										.getColumnIndex(MEDICINE_IMAGE_URL_COL)));
//								medReminder.add(medObj);
//							}
//
//						} catch (Exception e) {
//							// TODO: handle exception
//							// res = 1;
//						}
//
//					}
//
//				}
//		}
//
		// return res;
		return medReminder;

	}

	public ArrayList<Medicine> selectToSync() {
		ArrayList<Medicine> selectedList = new ArrayList<Medicine>();
		database = databaseHelper.getReadableDatabase();
		try {

			Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_MEDICINE
					+ " WHERE " + MEDICINE_NAME_COL + " <> 3 ;", null);

			while (cursor.moveToNext() == true) {
				// res = 0;
				Medicine temp = new Medicine();
				temp.setId(cursor.getInt(cursor.getColumnIndex(MEDICINE_ID_COL)));

				temp.setName(cursor.getString(cursor.getColumnIndex(MEDICINE_NAME_COL)));
				temp.setType(cursor.getString(cursor.getColumnIndex(MEDECINE_TYPE_COL)));
				temp.setDesc(cursor.getString(cursor.getColumnIndex(MEDECINE_REPETATION_COL)));
				temp.setImageURL(cursor.getString(cursor.getColumnIndex(MEDICINE_IMAGE_URL_COL)));
				temp.setStart_date(cursor.getColumnIndex(MEDICINE_START_DATE_COL));
				temp.setEnd_date(cursor.getColumnIndex(MEDICINE_END_DATE_COL));
				temp.setRepetition(MEDECINE_REPETATION_COL);
				//temp.setInstruction(cursor.getColumnIndex(Me));
				
				Cursor timeCursor = database.rawQuery("SELECT * FROM "+TABLE_DOSE_TIME+" WHERE "+DOSE_MEDICINE_ID_COL
						+" = "+temp.getId()+";", null);
				ArrayList<TimeDto> times = new ArrayList<TimeDto>();
				while (timeCursor.moveToNext() == true) {
					TimeDto timeDose = new TimeDto();
					String doseStr = timeCursor.getString(timeCursor
							.getColumnIndex(DOSE_QUANTITY_COL));
					timeDose.setDose(Float.parseFloat(doseStr));
					timeDose.setTake_time(timeCursor
							.getColumnIndex(DOSE_TIME_COL));
					timeDose.setMedicine_id(temp.getId());
					times.add(timeDose);
				}

				temp.setTimes(times);
				selectedList.add(temp);
			}

		} catch (Exception e) {
			// TODO: handle exception
			// res = 1;
			Log.i("error", "error happened");
		}
		return selectedList;
	}
	
	public int deleteMedecine(int id){
		database = databaseHelper.getWritableDatabase();
		String query = "DELETE FROM "+TABLE_MEDICINE+" WHERE "+MEDICINE_ID_COL+" = "+id;
		database.delete(TABLE_MEDICINE, MEDICINE_ID_COL+" = "+id, null);
		return 0;
		
	}
}
