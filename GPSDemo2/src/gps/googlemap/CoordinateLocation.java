package gps.googlemap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class CoordinateLocation extends Activity{

	EditText editLat = null;
	EditText editLng = null; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// set layout coordination
		setContentView(R.layout.coordinate);
		
		// find view id 2 edit text
		editLat = (EditText)findViewById(R.id.editTextLat);
		editLng = (EditText)findViewById(R.id.editTextLong);
		
		// an ban phim khi roi focus o text edit
		InputMethodManager inputmanager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		inputmanager.hideSoftInputFromWindow(editLat.getWindowToken(), 0);
		inputmanager.hideSoftInputFromWindow(editLng.getWindowToken(), 0);
		
		Button button = (Button)findViewById(R.id.buttonshowme);
		button.setOnClickListener(showmeListener);
		
		button = (Button)findViewById(R.id.buttonclear);
		button.setOnClickListener(clearListener);
	}

private OnClickListener showmeListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String strLat = editLat.getText().toString();
			String strLng = editLng.getText().toString();
			if(strLat.length() != 0 && strLng.length() != 0){
				Intent intent = new Intent();
				String resultLatLng = strLat + "," + strLng;
				setResult(RESULT_OK, intent.setAction(resultLatLng));				
				finish();
			}
			else{
				Dialog editTextNullDialog = new AlertDialog.Builder(CoordinateLocation.this)
				.setIcon(0)
				.setTitle("Please enter Latitude and Longitude value")
				.setPositiveButton("OK", null)
				.setMessage("Error")
				.create();
				editTextNullDialog.show();
			}
		}
	};
	
	private OnClickListener clearListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			editLat.setText("");
			editLng.setText("");				
		}
	};
}
