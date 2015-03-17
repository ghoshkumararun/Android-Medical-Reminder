package com.team6.mobile.iti;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "mydbSS";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_MEDICINE = "MEDICINE";
	private static final String MEDICINE_ID_COL = "ID";
	private static final String MEDICINE_NAME_COL = "NAME";
	private static final String MEDICINE_IMAGE_URL_COL = "IMAGE_URL";
	private static final String MEDICINE_START_DATE_COL = "START_DATE";
	private static final String MEDICINE_END_DATE_COL = "END_DATE";
	private static final String MEDECINE_DESC_COL = "DESC";
	private static final String MEDECINE_TYPE_COL = "TYPE";
	private static final String MEDECINE_REPETATION_COL = "REPETATION";
	private static final String MEDECINE_INSTRUCTION_COL = "INSTRUCTION";
	private static final String MEDECINE_New_COL = "NewMedecine";


	
	private static final String TABLE_DOSE_TIME = "DOSE_TIME";
	private static final String DOSE_MEDICINE_ID_COL = "MEDECINE_ID";
	private static final String DOSE_QUANTITY_COL = "DOSE";
	private static final String DOSE_TIME_COL = "TIME";
	private static final String DOSE_TAKEN_COL = "TAKEN";


	public DatabaseHelper(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.i("print", "in database constructor");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	//	String [] args = {TABLE_NAME,USER_NAME_COL,PASSWORD_COL}; 

		String s = "CREATE TABLE "+ TABLE_MEDICINE+
				" ( "+MEDICINE_ID_COL+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+MEDICINE_NAME_COL+" VARCHAR(50), "+
				MEDICINE_IMAGE_URL_COL+" VARCHAR(150), "
				+MEDICINE_START_DATE_COL+" INTEGER, "+MEDICINE_END_DATE_COL+" INTEGER, "
				+MEDECINE_DESC_COL+" VARCHAR(100), "+MEDECINE_TYPE_COL+" VARCHAR(50), "+
				MEDECINE_REPETATION_COL+" VARCHAR(100),"+MEDECINE_INSTRUCTION_COL+" VARCHAR(50),"+MEDECINE_New_COL+" INTEGER DEFAULT 0 );";
		Log.i("print",s);
		db.execSQL(s);
		
		db.execSQL("CREATE TABLE "+TABLE_DOSE_TIME+
				" ( "+DOSE_MEDICINE_ID_COL+" INTEGER, "
		+DOSE_QUANTITY_COL+" VARCHAR(5), "+
				DOSE_TIME_COL+" INTEGER,"+DOSE_TAKEN_COL+" INTEGER);");
		String ss="CREATE TABLE "+TABLE_DOSE_TIME+
				" ( "+DOSE_MEDICINE_ID_COL+" INTEGER, "
		+DOSE_QUANTITY_COL+" VARCHAR(5), "+
				DOSE_TIME_COL+" INTEGER,"+DOSE_TAKEN_COL+" INTEGER);";
		Log.i("xxxxDose",s);
		Log.i("xxxxDose", "Dose is created ");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
}
