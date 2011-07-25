package gps.googlemap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Direction extends Activity {

	static String sendKey = null;
	EditText startPoint = null;
	EditText endPoint = null;
	Intent intent;
	
	static final int GET_CODE = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.direction);

		startPoint = (EditText) findViewById(R.id.fromcoordinate);
		endPoint = (EditText) findViewById(R.id.tocoordinate);

		
		// Start point
		Button fromretrieve = (Button) findViewById(R.id.fromretrieve);
		fromretrieve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// list items
				final CharSequence[] items = { "My current location",
						"Point on map" };
				// list dialog box
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Direction.this);
				builder.setTitle("Choose Start Point");
				// assign click listener
				builder.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int item) {
						intent = new Intent();

						// Toast.makeText(getApplicationContext(), items[item],
						// Toast.LENGTH_SHORT).show();

						// truong hop chon vi tri hien tai
						if (item == 0) {
							startPoint.setText("My location");
						}
						// truong hop chon toa do tren ban do
						else {
							startPoint.setText("Tap on map");
							/*intent.putExtra("startpointonmap", true);
							setResult(RESULT_OK, intent);*/
							sendKey = "startpointonmap";
							setResult(RESULT_OK, intent.setAction(sendKey));
							finish();
						}
					}
				});
				AlertDialog alert = builder.create();
				alert.show();

			}
		});

		// End point
		Button toretrieve = (Button) findViewById(R.id.toretrieve);
		toretrieve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// list items
				final CharSequence[] items = { "My current location",
						"Point on map" };
				// list dialog box
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Direction.this);
				builder.setTitle("Choose End Point");
				// assign click listener
				builder.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int item) {
						intent = new Intent();
						// Toast.makeText(getApplicationContext(), items[item],
						// Toast.LENGTH_SHORT).show();

						// truong hop chon vi tri hien tai
						if (item == 0) {
							endPoint.setText("My location");
						}
						// truong hop chon toa do tren ban do
						else {
							endPoint.setText("Tap on map");
							sendKey = "endpointonmap";
							setResult(RESULT_OK, intent.setAction(sendKey));
							finish();
						}
					}
				});
				AlertDialog alert = builder.create();
				alert.show();

			}
		});

		// Hủy thao tác
		// chinh lai thanh cancel
		Button buttoncancel = (Button) findViewById(R.id.buttoncancel);
		buttoncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(Direction.this, GPSMain.class);
				startActivityForResult(intent, GET_CODE);
			}
		});

		// Tìm đường đi
		Button buttondrawdirection = (Button) findViewById(R.id.buttondrawdirection);
		buttondrawdirection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String strStartPoint = startPoint.getText().toString();
				String strEndPoint = endPoint.getText().toString();				
				
				if (strStartPoint.compareTo("My location") == 0
						&& strEndPoint.compareTo("My location") ==0){
					Dialog errorDialog = new AlertDialog.Builder(Direction.this)
							.setIcon(0).setTitle("Invalid values")
							.setPositiveButton("OK", null)
							.setMessage("Please re-enter values").create();
					errorDialog.show();
				} else {
					intent = new Intent();
					
					if (strStartPoint.compareTo("My location") == 0
							&& strEndPoint.compareTo("Tap on map") == 0){
						sendKey = "mylocation" + ";" + "endpointonmap";
												
					} else if (strStartPoint.compareTo("Tap on map") == 0
							&& strEndPoint.compareTo("My location") == 0) {
						sendKey = "startpointonmap" + ";" + "mylocation";
												
					} else if (strStartPoint.compareTo("Tap on map") == 0
							&& strEndPoint.compareTo("Tap on map") == 0) {
						sendKey = "startpointonmap" + ";" + "endpointonmap";
					
					}
					setResult(RESULT_OK, intent.setAction(sendKey));
					finish();
				}
			}
		});
		
		// xet ket qua nhan duoc
		//String receiveResult = savedInstanceState != null ? savedInstanceState.getString("sendKey") : null;
		
		try{
			Bundle bundle = getIntent().getExtras();
			String strReceive = bundle.getString("sendKey");
			// trường hợp thao tác vừa thực hiện là chọn tọa độ bắt đầu Start Point
			if(strReceive.compareTo("Tap on map start point") == 0){
				startPoint.setText("Tap on map");
				
			}
			// trường hợp thao tác vừa thực hiện là chọn tọa độ kết thúc End Point
			else if(strReceive.compareTo("Tap on map end point") == 0){
				endPoint.setText("Tap on map");
			}
			else if(strReceive.compareTo("Tap on map start and end point") == 0){
				startPoint.setText("Tap on map");
				endPoint.setText("Tap on map");
			}
				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	} // end onCreate
}
