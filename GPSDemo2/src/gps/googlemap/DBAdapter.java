package gps.googlemap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	private static final String TAG = "LocationDB";
	private SQLiteHelper dbHelper;
	private SQLiteDatabase db;
	
	/*private static final String DB_NAME = "LocationDB";
	private static final String DB_TABLE = "LocationTable";*/
	private static final String DB_NAME = "DB";
	private static final String DB_TABLE = "Location_tb";
	private static final int DB_VERSION = 3;
	
	// columns in db
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_ADDRESS = "address";
	
	private static final String DB_CREATE = 
		"create table " + DB_TABLE + " ("
		+ COLUMN_ID + " integer primary key autoincrement, "
		+ COLUMN_NAME + " text not null, "
		+ COLUMN_LATITUDE + " text not null, "
		+ COLUMN_LONGITUDE + " text not null, "
		+ COLUMN_ADDRESS + " text not null);";	
	
	private final Context mContext;
	
	public DBAdapter(Context context){
		this.mContext = context;
	}
	
	public DBAdapter Open() throws SQLException{
		Log.i(TAG, "Opening db connection");
		dbHelper = new SQLiteHelper(mContext);
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void Close(){
		dbHelper.close();
	}
	
	public long InsertRecord (String name, String latitude,
			String longitude, String address) {
		Log.i(TAG, "Inserting record...");		
		
		ContentValues values = CreateContentValues(name, latitude, longitude, address);
		
		return db.insert(DB_TABLE, null, values);
	}
	
	public boolean DeleteRecord (long locationID) {

		return db.delete(DB_TABLE, COLUMN_ID + "=" + locationID, null) > 0;
	}
	
	public void DeleteAll(){
		db.delete(DB_TABLE, null, null);
	}

	public Cursor SelectAll() {

		return db.query(DB_TABLE, new String[] {COLUMN_ID, COLUMN_NAME, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_ADDRESS}, null, null, null, null, null);
	}
	
	public Cursor SelectRecord(long locationID) throws SQLException {

		Cursor cursor = db.query(true, DB_TABLE, new String[] {COLUMN_ID, COLUMN_NAME, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_ADDRESS}, COLUMN_ID + "=" + locationID, null,
					null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	public boolean Update (long locationID, String name) {
		ContentValues value = new ContentValues();
		value.put(COLUMN_NAME, name);

		return db.update(DB_TABLE, value, COLUMN_ID + "=" + locationID, null) > 0;
	}
	
	private ContentValues CreateContentValues(String name, String latitude,
			String longitude, String address){
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_LATITUDE, latitude);
		values.put(COLUMN_LONGITUDE, longitude);
		values.put(COLUMN_ADDRESS, address);
		
		return values;
	}
	
	public class SQLiteHelper extends SQLiteOpenHelper {

		SQLiteHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i(TAG, "Creating Database: " + DB_CREATE);
			db.execSQL(DB_CREATE);
		}

		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			onCreate(db);
		}

	}
}
