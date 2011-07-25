package gps.googlemap;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LocationDetailEdit extends Activity{
	EditText lat = null;
	EditText lng = null;
	EditText address = null;
	EditText name = null;
	
	String strAddress = null, strName = null;
	String strLat = null, strLng = null;
	
	DBAdapter dbAdapter;
	Intent intent;
	static final int GET_CODE = 0;
	
	protected void onCreate(final Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.savecoordinate);
		
		lat = (EditText)findViewById(R.id.savelatitude);
		lng = (EditText)findViewById(R.id.savelongitude);
		address = (EditText)findViewById(R.id.saveaddress);
		name = (EditText)findViewById(R.id.savename);
		
		// nhận giá trị gửi từ GPSMain		
		final Bundle bundle = getIntent().getExtras();
		final long locationID = bundle.getLong("sendKey");
		
		SelectByLocationID(locationID);	
		
		// button Cancel
		Button buttoncancel = (Button) findViewById(R.id.savecancel);
		buttoncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(LocationDetailEdit.this, LocationList.class);
				startActivityForResult(intent, GET_CODE);
			}
		}); // end button cancel
		
		// button save
		Button buttonsave = (Button)findViewById(R.id.saveaccept);
		buttonsave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {						
				UpdateByLocationID(locationID);							
				intent = new Intent(LocationDetailEdit.this, LocationList.class);
				startActivityForResult(intent, GET_CODE);
			}
		}); // end button save
	} // end onCreate	
	
	
	private void SelectByLocationID(long locationID){
		dbAdapter = new DBAdapter(this);
		dbAdapter.Open();
		
		Cursor cursor = dbAdapter.SelectRecord(locationID);
		if(cursor != null){
			strName = cursor.getString(1);
			strLat = cursor.getString(2);
			strLng = cursor.getString(3);
			strAddress = cursor.getString(4);
			
			// gán giá trị cho các edit text: latitude, longtitude, address
			lat.setText(new String(strLat));
			lng.setText(new String(strLng));			
			address.setText(new String(strAddress));
			name.setText(new String(strName));
		}
		dbAdapter.Close();
	}
	
	private void UpdateByLocationID(long locationID){
		dbAdapter = new DBAdapter(this);
		dbAdapter.Open();
		
		strName = name.getText().toString();
		dbAdapter.Update(locationID, strName);
		
		dbAdapter.Close();
	}
}
