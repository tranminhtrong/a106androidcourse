package niit.android;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class FlightPreferenceActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		PreferenceManager prefMgr = getPreferenceManager();
		prefMgr.setSharedPreferencesName("main");
		prefMgr.setSharedPreferencesMode(MODE_WORLD_WRITEABLE);
		*/
		addPreferencesFromResource(R.xml.flightoptions);
	}

}
