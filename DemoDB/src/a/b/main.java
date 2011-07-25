package a.b;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class main extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(this, "demo", null, 0);
        SQLiteDatabase database = myDBOpenHelper.getWritableDatabase();
        
    }
}