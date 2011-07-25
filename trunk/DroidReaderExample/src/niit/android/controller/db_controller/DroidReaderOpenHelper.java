package niit.android.controller.db_controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DroidReaderOpenHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "droid_reader";
	
	private static final String DATABASE_CREATE_STATEMENTS = "" +
	"CREATE TABLE \"channel\" (" +
	"\"c_id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
	"\"c_title\" VARCHAR," +
	"\"c_url\" VARCHAR," +
	"\"c_description\" VARCHAR," +
	"\"c_pub_date\" DATETIME DEFAULT CURRENT_DATE," +
	"\"c_last_build_date\" DATETIME DEFAULT CURRENT_DATE," +
	"\"c_copyright\" VARCHAR," +
	"\"c_generator\" VARCHAR" +
	")";
	
	public DroidReaderOpenHelper(Context context,int DATABASE_VERSION) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_STATEMENTS);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}
