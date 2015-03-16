package com.team6.mobile.iti;

import java.util.ArrayList;

import com.team6.mobile.iti.beans.Medicine;
import com.team6.mobile.iti.beans.TimeDto;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {
	DatabaseHelper databaseHelper;
	SQLiteDatabase database;
	
	private static final String TABLE_MEDICINE = "MEDICINE";
	private static final String MEDICINE_ID_COL = "ID";
	private static final String MEDICINE_NAME_COL = "NAME";
	private static final String MEDICINE_IMAGE_URL_COL = "IMAGE_URL";
	private static final String MEDICINE_START_DATE_COL = "START_DATE";
	private static final String MEDICINE_END_DATE_COL = "END_DATE";
	private static final String MEDECINE_DESC_COL = "DESC";
	private static final String MEDECINE_TYPE_COL = "TYPE";
	private static final String MEDECINE_REPETATION_COL = "REPETATION";
	private static final String MEDECINE_New_COL = "NewMedecine";
	
	private static final String TABLE_DOSE_TIME = "DOSE_TIME";
	private static final String DOSE_MEDICINE_ID_COL = "MEDECINE_ID";
	private static final String DOSE_QUANTITY_COL = "DOSE";
	private static final String DOSE_TIME_COL = "TIME";
	private static final String DOSE_TAKEN_COL = "TAKEN";
	
	public DatabaseAdapter(DatabaseHelper h){
		databaseHelper = h;
	}
	
	public long insertMedecine(String medName,String desc,String type,String urlImage){
		database = databaseHelper.getWritableDatabase();
		long res = 0;
		String tableName = TABLE_MEDICINE;
		ContentValues medValues = new ContentValues();
		medValues.put(MEDICINE_NAME_COL, medName);
		medValues.put(MEDECINE_DESC_COL,desc);
		medValues.put(MEDECINE_TYPE_COL, type);
		medValues.put(MEDICINE_IMAGE_URL_COL, urlImage);
		
		
		
			try{
				
				database.insert(tableName,null,medValues);
				res = 0;
			}
			catch (Exception ex) {
				// TODO: handle exception
			
				res = 2;
			}
		
		
		return res;
	}
	public ArrayList<Medicine> selectAllMedecines(){
		database = databaseHelper.getReadableDatabase();
	//	String tableName = TABLE_MEDICINE;
		//long res = 0;
		ArrayList<Medicine> allMedecine = new ArrayList<Medicine>();
	//	String [] columns = {USER_NAME_COL,PASSWORD_COL};
		//String s =USER_NAME_COL+" LIKE ? AND "+PASSWORD_COL+" LIKE ? ";
	//	String [] selection = {userName,password};
		try{
			
			Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_MEDICINE+";", null);
			
			while(cursor.moveToNext() == true){
			//	res = 0;
				Medicine temp = new Medicine();
				temp.setName(cursor.getString(cursor.getColumnIndex(MEDICINE_NAME_COL)));
				temp.setType(cursor.getString(cursor.getColumnIndex(MEDECINE_TYPE_COL)));
				temp.setDesc(cursor.getString(cursor.getColumnIndex(MEDECINE_REPETATION_COL)));
				temp.setImageURL(cursor.getString(cursor.getColumnIndex(MEDICINE_IMAGE_URL_COL)));
				allMedecine.add(temp);
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
		//	res = 1;
		}
		//return res;
		return allMedecine;
	}
	public ArrayList<Medicine> selectToSync(){
		ArrayList<Medicine> selectedList ;
		database = databaseHelper.getReadableDatabase();
try{
			
			Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_MEDICINE+" WHERE "+MEDICINE_NAME_COL
					+" <> 3 ;", null);
			
			while(cursor.moveToNext() == true){
			//	res = 0;
				Medicine temp = new Medicine();
				temp.setId(cursor.getInt(cursor.getColumnIndex(MEDICINE_ID_COL)));
				temp.setName(cursor.getString(cursor.getColumnIndex(MEDICINE_NAME_COL)));
				temp.setType(cursor.getString(cursor.getColumnIndex(MEDECINE_TYPE_COL)));
				temp.setDesc(cursor.getString(cursor.getColumnIndex(MEDECINE_REPETATION_COL)));
				temp.setImageURL(cursor.getString(cursor.getColumnIndex(MEDICINE_IMAGE_URL_COL)));
				
				Cursor timeCursor = database.rawQuery("SELECT * FROM "+TABLE_DOSE_TIME+" WHERE "+DOSE_MEDICINE_ID_COL
						+" = "+temp.getId()+";", null);
				ArrayList<TimeDto> times = new ArrayList<TimeDto>();
				while(timeCursor.moveToNext()== true){
					TimeDto timeDose = new TimeDto();
					timeDose.setDose(timeCursor.getFloat(timeCursor.getColumnIndex(columnName)));
				}
				
				//temp.setTimes();
				allMedecine.add(temp);
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
		//	res = 1;
		}
		
	}
}
