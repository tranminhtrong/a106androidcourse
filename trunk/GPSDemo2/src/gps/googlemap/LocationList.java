package gps.googlemap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LocationList extends Activity{

	ListView locationList;
	DBAdapter dbAdapter;
	static final int GET_CODE = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationlist);
		locationList = (ListView) findViewById(R.id.locationlistview);
		//InsertData();
		ShowList();
		
		// sự kiện khi click chọn list item
		locationList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final long locationID = (long) locationList.getItemIdAtPosition(position);
				/*AlertDialog.Builder dialog = new AlertDialog.Builder(LocationList.this);
				dialog.setTitle("Dialog");
				dialog.setMessage("Record id = " + locationID);
				dialog.setPositiveButton("OK", null);
				dialog.show();*/
				
				final CharSequence[] items = {"Edit location item", "Delete location item", "Delete all location item"};
				
				AlertDialog.Builder menuDialog = new AlertDialog.Builder(LocationList.this);
				menuDialog.setTitle("Choose option");
				menuDialog.setItems(items, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int item) {
						Intent intent = new Intent();
							
						// edit item
						if(item == 0){
							intent = new Intent(LocationList.this, LocationDetailEdit.class);
							intent.putExtra("sendKey", locationID);
							startActivityForResult(intent, GET_CODE);
						}
						else{
							dbAdapter = new DBAdapter(LocationList.this);
							dbAdapter.Open();
							// delete item
							if(item == 1){
								dbAdapter.DeleteRecord(locationID);
								ShowList();
							} 
							// delete all
							else {
								dbAdapter.DeleteAll();
								ShowList();
							}
							dbAdapter.Close();
						}
					}
				});
				AlertDialog dialog = menuDialog.create();
				dialog.show();				
			}		
		});
	}
	
	/** Tạo menu **/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.submenu, menu);
		return true;
	}

	/** Gọi phương thức trong menu **/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {		

		case R.id.returnmap:
			Intent intent = new Intent(LocationList.this, GPSMain.class);
			startActivity(intent);
			return true;

		case R.id.exit:
			
			return true;
					
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void InsertData(){
		dbAdapter = new DBAdapter(this);
		dbAdapter.Open();
		dbAdapter.DeleteAll();
		dbAdapter.InsertRecord("Ly Thuong Kiet", "10.824032727034524", "106.68389797210693", "Lý Thường Kiệt, phường 4, Go Vap District, Ho Chi Minh City, Vietnam");
		dbAdapter.Close();
	}
	
	/*private void ShowList() {
		DBAdapter dbAdapter = new DBAdapter(this);
		dbAdapter.Open();
		Cursor cursor = dbAdapter.SelectAll();
		if (cursor != null) {
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
					R.layout.main, cursor, // Give the cursor to the list
											// adapter
					new String[] { cursor.getColumnName(1) }, // Map the column
																// in the
					// employee database to...
					new int[] { R.id.locationname }); // The view defined in the
														// XML template
			ListView locationList = (ListView) findViewById(R.id.locationlistview);
			locationList.setAdapter(adapter);
		}
		dbAdapter.Close();
	}*/
	
	private void ShowList() {
		dbAdapter = new DBAdapter(this);
		dbAdapter.Open();
		
		Cursor cursor = dbAdapter.SelectAll();
		startManagingCursor(cursor);	
		
		if (cursor != null) {
			String[] from = new String[]{cursor.getColumnName(1), cursor.getColumnName(4)}; 
			int[] to = new int[]{R.id.locationname, R.id.locationaddress};
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,	R.layout.locationrow, cursor, from, to);
					
			
			locationList.setAdapter(adapter);
		}
		else{
			EditText emptyList = (EditText)findViewById(R.id.locationname);
			emptyList.setText("Empty");
		}
		dbAdapter.Close();
	}
	
	

	}
