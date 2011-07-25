package niit.android;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class Demo extends Activity{

	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences sharedPreferences = getSharedPreferences("main", MODE_PRIVATE);
	    String tmp = sharedPreferences.getString("selected_flight_sort_option", "");
	}

	
	
}
