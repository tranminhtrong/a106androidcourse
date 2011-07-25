package gps.googlemap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LocationInfo extends Activity{
	EditText lat = null;
	EditText lng = null;
	EditText address = null;
	EditText name = null;
	
	String strAddress = null, strName = null;
	String strLat = null, strLng = null;
	
	DBAdapter dbAdapter;
	Intent intent;
	static final int GET_CODE = 0;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
				
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.savecoordinate);
		
		lat = (EditText)findViewById(R.id.savelatitude);
		lng = (EditText)findViewById(R.id.savelongitude);
		address = (EditText)findViewById(R.id.saveaddress);
		name = (EditText)findViewById(R.id.savename);
		
		// nhận giá trị gửi từ GPSMain		
		final Bundle bundle = getIntent().getExtras();
		double[] dbReceive = bundle.getDoubleArray("sendKey");
		
		strLat = Double.toString(dbReceive[0]);
		strLng = Double.toString(dbReceive[1]);
		strAddress = ReverseGeocode.GetAddressFromCoordinate(dbReceive[0], dbReceive[1]);	
		
		// gán giá trị cho các edit text: latitude, longtitude, address
		lat.setText(new String(strLat));
		lng.setText(new String(strLng));			
		address.setText(new String(strAddress));
		name.setText(new String(strAddress));
		
		// button Cancel
		Button buttoncancel = (Button) findViewById(R.id.savecancel);
		buttoncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(LocationInfo.this, GPSMain.class);
				startActivityForResult(intent, GET_CODE);
			}
		}); // end button cancel
		
		// button save
		Button buttonsave = (Button)findViewById(R.id.saveaccept);
		buttonsave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {						
				strName = name.getText().toString();				
				InsertData(strName, strLat, strLng, strAddress);								
				intent = new Intent(LocationInfo.this, GPSMain.class);
				startActivityForResult(intent, GET_CODE);
			}
		}); // end button save
	} // end onCreate	
	
	private void InsertData(String name, String latitude, String longitude, String address){
		
		dbAdapter = new DBAdapter(this);
		dbAdapter.Open();
		//dbAdapter.DeleteAll();
		long rowID = dbAdapter.InsertRecord(name, latitude, longitude, address);
		dbAdapter.Close();
	}
}
