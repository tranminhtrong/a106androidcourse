package gps.googlemap;

import java.util.List;
import java.util.Locale;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ZoomControls;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GPSMain extends MapActivity {
	/**
	 * =================================================== FIELDS
	 * ===================================================
	 **/
	GeoPoint currentPoint = null, dbClickPoint = null, startPoint = null,
			endPoint = null;
	Geocoder geoCoder = null;
	ProgressDialog proDialog = null;

	// sử dụng để lưu giá trị tọa độ
	// dùng cho trường hợp Save Location
	private double[] dbCoordinateSaving = null;	
	
	MenuItem saveLocationMenuItem;
	
	List<Address> addresses = null;
	private Thread searchAddressThread;

	private LocationManager locationManager;
	private LocationListener locationListener;

	private MapView mapView;
	private MapController mapController;

	private GestureDetector gestureDetector;
	
	private Intent intent;

	DirectionPathOverlay directionOverlay;
	
	
	static final int DRAW_COLOR = Color.BLUE;
	static final int STREET_VIEW_ID = 0;
	static final int SATELLITE_VIEW_ID = 1;
	static final int ZOOM_LEVEL = 17;
	static final int MAX_RESULT = 5;
	static final int GET_CODE = 0; // request code được sử dụng khi nhận kết quả
									// từ activity khác
	static boolean IS_CALL_COORDINATE = false;
	static boolean IS_CALL_DIRECTION = false;
	static boolean IS_SAVE_LOCATION = false;
	
	// trạng thái xác định tọa độ của Start Point từ bản đồ
	static boolean IS_GET_START_POINT = false; 		
	
	// trạng thái xác định tọa độ của End Point từ bản đồ
	static boolean IS_GET_END_POINT = false; 	

	static String strTapOnMapDialogTitle = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setStreetView(true);
			
		// zoom controls
		ZoomControls zoomControls = (ZoomControls) findViewById(R.id.zoom);
		zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
			// tao su kien cho zoom in
			@Override
			public void onClick(View view) {
				mapView.getController().zoomIn();
			}
		});
		zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
			// tao su kien cho zoom out
			@Override
			public void onClick(View v) {
				mapView.getController().zoomOut();
			}
		});

		mapController = mapView.getController();

		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		locationListener = new MyLocationListener();
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

		// xac dinh toa do ban dau khi hien thi ban do
		double[] dbCoordinate = { 10.781003173078687, 106.70016288757324 };
		currentPoint = ConvertDbCoordinateToGeopoint(dbCoordinate);
		mapController.animateTo(currentPoint);
		mapController.setZoom(ZOOM_LEVEL);
		mapView.invalidate();

		// sự kiện double click trên map, lấy tọa độ của điểm vừa click
		// sử dụng cho Direction
		mapView.getOverlays().add(new Overlay() {
			@Override
			public boolean onTouchEvent(MotionEvent e, MapView mapView) {
				gestureDetector.onTouchEvent(e);
				return super.onTouchEvent(e, mapView);
			}
		});

		gestureDetector = new GestureDetector(
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onDoubleTap(MotionEvent e) {
						if (IS_CALL_DIRECTION) {
							Log.d("Gesture Detector", "Double Tap event");
							dbClickPoint = mapView.getProjection().fromPixels(
									(int) e.getX(), (int) e.getY());

							AlertDialog.Builder tapOnMapDialog = new AlertDialog.Builder(
									GPSMain.this);

							// set title cho tapOnMapDialog
							if (IS_GET_START_POINT)
								strTapOnMapDialogTitle = "Do you want to choose this is a start point?";
							else if (IS_GET_END_POINT)
								strTapOnMapDialogTitle = "Do you want to choose this is a end point?";
							else
								strTapOnMapDialogTitle = "Click-point's parameter";

							tapOnMapDialog.setTitle(strTapOnMapDialogTitle);

							tapOnMapDialog.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											if (IS_GET_START_POINT) {
												startPoint = dbClickPoint;
												IS_GET_START_POINT = false;

											} else if (IS_GET_END_POINT) {
												endPoint = dbClickPoint;
												IS_GET_END_POINT = false;
											}

											// chuyển qua Activity Direction
											intent = new Intent(GPSMain.this,
													Direction.class);
											if (startPoint != null
													&& endPoint != null) {
												intent.putExtra("sendKey",
														"Tap on map start and end point");
											} else if (startPoint == null
													&& endPoint != null) {
												intent.putExtra("sendKey",
														"Tap on map end point");
											} else if (startPoint != null
													&& endPoint == null) {
												intent.putExtra("sendKey",
														"Tap on map start point");
											}
											startActivityForResult(intent,
													GET_CODE);
										}
									});
							tapOnMapDialog.setNegativeButton("Cancel", null);
							tapOnMapDialog.show();							
						} // end if						
						return true;
					} // end onDoubleTap
					
					@Override
					public boolean onDown(MotionEvent e) {
						return true;
					}
				});
		gestureDetector.setIsLongpressEnabled(true);

		// sự kiện button Search
		// tìm kiếm dựa trên địa điểm nhập vào
		Button searchButton = (Button) findViewById(R.id.buttonSearch);
		geoCoder = new Geocoder(this);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// tạo progress dialog
				proDialog = ProgressDialog.show(GPSMain.this, "Processing...",
						"Searching...", true, false);

				searchAddressThread = new Thread() {
					public void run() {
						EditText etAddress = (EditText) findViewById(R.id.address);
						String strAddress = etAddress.getText().toString();

						try {
							// truong hop test tren mobile
							try {
								addresses = geoCoder.getFromLocationName(
										strAddress, MAX_RESULT);
							}
							// truong hop test tren emulator se su dung ham
							// GetFromLocation ho tro
							catch (Exception e) {
								addresses = Geocode.GetFromLocationName(
										strAddress, MAX_RESULT);
							}
							Thread.sleep(1500);
						} catch (Exception e) {
						}
						ShowAddressResults.sendEmptyMessage(0);
					}
				};
				searchAddressThread.start();
			}
		});
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected boolean isLocationDisplayed() {
		return false;
	}

	/** Nhan gia tri tu activity khac **/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// trường hợp thực hiện tìm tọa độ trên bản đồ
		// SetCoordinateLocation
		if (IS_CALL_COORDINATE) {
			if (requestCode == GET_CODE) {
				String inputLatLng = "";
				if (data != null) {
					inputLatLng = data.getAction();
					int blankIndex = inputLatLng.indexOf(",");

					double dbLat = Double.parseDouble(inputLatLng.substring(0,
							blankIndex));
					double dbLng = Double.parseDouble(inputLatLng.substring(
							blankIndex + 1, inputLatLng.length()));
					//double[] dbCoordinate = { dbLat, dbLng };
//					dbCoordinateSaving[0] = dbLat;
//					dbCoordinateSaving[1] = dbLng;
					dbCoordinateSaving = new double[] {dbLat, dbLng};
					/*
					 * Dialog editTextNullDialog = new
					 * AlertDialog.Builder(GPSMain.this) .setIcon(0)
					 * .setTitle(getCallingActivity().toString())
					 * .setPositiveButton("OK", null) .setMessage("Error")
					 * .create(); editTextNullDialog.show();
					 */
					IS_CALL_COORDINATE = false;
					IdentifyCoordinationOnMap(dbCoordinateSaving, true);
				}
			}
		} else if (IS_CALL_DIRECTION) {
			if (requestCode == GET_CODE) {				
				String receiveKey = data.getAction();
				
				if(receiveKey.compareTo("startpointonmap") == 0) 
					IS_GET_START_POINT = true;
				
				else if(receiveKey.compareTo("endpointonmap") == 0)
					IS_GET_END_POINT = true;
				
				else if(receiveKey.compareTo("mylocation;endpointonmap") == 0)
					DrawDirection(currentPoint, endPoint);
				
				else if(receiveKey.compareTo("startpointonmap;mylocation") == 0)
					DrawDirection(startPoint, currentPoint);
				
				else if(receiveKey.compareTo("startpointonmap;endpointonmap") == 0)
					DrawDirection(startPoint, endPoint);
			}
		}
	}

	/** Tạo menu **/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	/** Gọi phương thức trong menu **/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {		

		case R.id.currentlocation:
			CurrentLocation();
			return true;

		case R.id.street:
			SettingMapView(STREET_VIEW_ID);
			return true;

		case R.id.satellite:
			SettingMapView(SATELLITE_VIEW_ID);
			return true;

		case R.id.coordinate:
			SetCoordinateLocation();
			return true;

		case R.id.direction:
			GetDirection();
			return true;
		
		case R.id.savelocation:
			SaveLocation();
			return true;
			
		case R.id.viewsavelist:
			ViewLocationList();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/** Class MyLocationListener - xác định vị trí hiện tại **/
	public class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {				
					IdentifyCoordinationOnMap(location, true);
				
					// currentPoint dùng để xác định vị trí hiện tại
					// sử dụng cho Direction
					currentPoint = ConvertLocationToGeopoint(location);
					
					// gán giá trị tọa độ cho dbCoordinateSaving
					dbCoordinateSaving = new double[] {location.getLatitude(), location.getLongitude()};					
					
				/*if (IS_CALL_DIRECTION == false)
					IdentifyCoordinationOnMap(location, true);
				else
					// 
					currentPoint = ConvertLocationToGeopoint(location);*/
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
			Toast.makeText(getApplicationContext(), "GPS Enabled",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderDisabled(String provider) {
			Toast.makeText(getApplicationContext(), "GPS Disabled",
					Toast.LENGTH_SHORT).show();
		}
	}

	/** ==================Phương thức ==================== **/
	// *************** Finish *****************
	// Setting map view
	private void SettingMapView(int type) {
		// reset dbCoordinateSaving
		ResetSaving();
		
		switch (type) {

		// Enable street view
		case STREET_VIEW_ID:
			mapView.setStreetView(true);
			mapView.setSatellite(false);
			mapView.setTraffic(false);
			break;

		// Enable satellite view
		case SATELLITE_VIEW_ID:
			mapView.setStreetView(false);
			mapView.setSatellite(true);
			mapView.setTraffic(false);
			break;
		}
	}

	// *************** Finish *****************
	// Gắn pushpin vào vị trí hiển thị tọa độ trên bản đồ
	private void SetMarkerPointing(GeoPoint point) {
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.pushpin);
		CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(
				drawable, this);

		String address = GetAddressFromCoordination(point);
		OverlayItem overlayItem = new OverlayItem(point, "Address", address);

		itemizedOverlay.addOverlay(overlayItem);
		mapOverlays.add(itemizedOverlay);

		mapController = mapView.getController();
		mapController.animateTo(point);
		mapController.setCenter(point);
		mapController.setZoom(ZOOM_LEVEL);
	}

	// *************** Finish *****************
	// xac dinh vi tri hien tai tren ban do
	private void CurrentLocation() {
		// reset dbCoordinateSaving
		ResetSaving();
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new MyLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);

		mapView = (MapView) findViewById(R.id.mapView);
		mapController = mapView.getController();
	}

	// *************** Finish *****************
	// Xac dinh toa do tren ban do
	private void IdentifyCoordinationOnMap(double[] dbCoordinate,
			boolean isSetMarkerPointing) {
		// point = new GeoPoint((int)(dbLat * 1E6), (int)(dbLng * 1E6));
		GeoPoint geoPointCoordinate = ConvertDbCoordinateToGeopoint(dbCoordinate);
		if (isSetMarkerPointing)
			SetMarkerPointing(geoPointCoordinate);
	}

	// xac dinh vi tri tren ban do bang @location
	private void IdentifyCoordinationOnMap(Location location,
			boolean isSetMarkerPointing) {
		Toast.makeText(
				getBaseContext(),
				"Location changed: \n Lat = " + location.getLatitude()
						+ " \n Lng = " + location.getLongitude(),
				Toast.LENGTH_SHORT).show();

		mapController = mapView.getController();
		// GeoPoint point = new GeoPoint((int)(location.getLatitude() * 1E6) ,
		// (int)(location.getLongitude() * 1E6));
		GeoPoint point = ConvertLocationToGeopoint(location);
		if (isSetMarkerPointing)
			SetMarkerPointing(point);
	}

	// xac dinh address tu 1 toa do vi tri
	private String GetAddressFromCoordination(GeoPoint geoPointCoordinate) {
		String result = "";
		try {

			double[] dbCoordinate = ConvertGeopointToDbCoordinate(geoPointCoordinate);
			double dbLat = dbCoordinate[0];
			double dbLng = dbCoordinate[1];

			List<Address> addresses = null;

			// truong hop test tren mobile
			try {
				Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
				addresses = geoCoder.getFromLocation(dbLat, dbLng, MAX_RESULT);
			}
			// truong hop test tren emulator se su dung ham GetFromLocation ho
			// tro
			catch (Exception e) {
				addresses = ReverseGeocode.GetFromLocation(dbLat, dbLng,
						MAX_RESULT);
			}
			StringBuilder resultAddress = new StringBuilder();
			if (addresses.size() > 0) {
				for (int i = 0; i < addresses.size(); i++) {
					Address address = addresses.get(i);
					int maxIndex = address.getMaxAddressLineIndex();

					// ten duong
					for (int x = 0; x <= maxIndex; x++) {
						resultAddress.append(address.getAddressLine(x));
						resultAddress.append(", ");
					}
					// phuong
					resultAddress.append(address.getLocality());
					resultAddress.append(", ");
					// quan
					resultAddress.append(address.getSubAdminArea());
					resultAddress.append(", ");
					// thanh pho
					resultAddress.append(address.getAdminArea());
					resultAddress.append(", ");
					// nuoc
					resultAddress.append(address.getCountryCode());
				}
			}
			result = resultAddress.toString();
			if (result == "")
				result = "Unknown";
		} catch (Exception e) {
			// e.printStackTrace();
			result = "Unknown";
		}
		return result;
	}

	// =============================Các hàm convert tọa
	// độ=======================
	/**
	 * Convert double coordinate sang geopoint
	 * 
	 * @param dbCoordinate
	 *            dbCoordinate[0] = Latitude dbCoordinate[1] = Longitude
	 */
	private GeoPoint ConvertDbCoordinateToGeopoint(double[] dbCoordinate) {
		double dbLat = dbCoordinate[0];
		double dbLng = dbCoordinate[1];
		GeoPoint geoPointCoordinate = new GeoPoint((int) (dbLat * 1E6),
				(int) (dbLng * 1E6));
		return geoPointCoordinate;
	}

	/**
	 * Convert kieu toa do dang location sang geoPoint dang int
	 * 
	 * @param location
	 * @return geoPoint kieu int
	 */
	private GeoPoint ConvertLocationToGeopoint(Location location) {
		GeoPoint geoPointCoordinate = new GeoPoint(
				(int) (location.getLatitude() * 1E6),
				(int) (location.getLongitude() * 1E6));
		return geoPointCoordinate;
	}

	/**
	 * Convert kieu toa do dang geopoint sang double
	 * 
	 * @param geoPointCoordinate
	 * @return toa do kieu double
	 */
	private double[] ConvertGeopointToDbCoordinate(GeoPoint geoPointCoordinate) {
		double dbLat = geoPointCoordinate.getLatitudeE6() / 1E6;
		double dbLng = geoPointCoordinate.getLongitudeE6() / 1E6;
		double[] dbCoordinate = { dbLat, dbLng };
		return dbCoordinate;
	}

	// =============================Menu Coordinate=======================
	// xac dinh vi tri bang cach nhap toa do
	// hien thi screen nhap toa do trong Coordination Location
	private void SetCoordinateLocation() {
		// reset dbCoordinateSaving
		ResetSaving();
		
		IS_CALL_COORDINATE = true;
		IS_CALL_DIRECTION = false;
		IS_SAVE_LOCATION = false;
		intent = new Intent(GPSMain.this, CoordinateLocation.class);
		startActivityForResult(intent, GET_CODE);
	}

	/**
	 * Xac dinh toa do tren ban do tu 1 location name
	 * 
	 * @param locationName
	 *            : ten dia diem nhap vao
	 * @method uiCallback : xu ly da luong - xem xet location name nhap vao co
	 *         ton tai hay khong
	 */
	/*
	 * private void FindLocationByLocationName(final String address){ JSONObject
	 * jsonObject = Geocode.GetFromLocationName(address); GeoPoint gp =
	 * Geocode.ConvertToGeoPoint(jsonObject); //Log.d("Location parse",
	 * string.(gp.getLatitudeE6()) + " ; " + string.parse(gp.getLongitudeE6()));
	 * 
	 * List<Address> addresses = null; // truong hop test tren mobile try {
	 * Geocoder geoCoder = new Geocoder(this, Locale.getDefault()); addresses =
	 * geoCoder.getFromLocationName(address, MAX_RESULT); } // truong hop test
	 * tren emulator se su dung ham GetFromLocation ho tro catch (Exception e) {
	 * addresses = Geocode.GetFromLocationName(address, MAX_RESULT); }
	 * 
	 * SetMarkerPointing(gp); }
	 */
	private Handler ShowAddressResults = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			proDialog.dismiss();

			// không tìm thấy kết quả phù hợp
			if (addresses.size() == 0) {
				Dialog searchingError = new AlertDialog.Builder(GPSMain.this)
						.setIcon(0).setTitle("Error")
						.setPositiveButton("OK", null)
						.setMessage("No results match your search.").create();
				searchingError.show();
			}
			// hiển thị trên map
			else {
				for (int i = 0; i < addresses.size(); i++) {
					Address address = addresses.get(i);		
					
					// gán giá trị tìm kiếm cho dbCoordinateSaving
					dbCoordinateSaving = new double[] {address.getLatitude(), address.getLongitude()};
					
					GeoPoint geoCodeCoordinate = ConvertDbCoordinateToGeopoint(dbCoordinateSaving);
					
					SetMarkerPointing(geoCodeCoordinate);
				}
			}
		}
	};

	// ve duong di

	private void GetDirection() {
		// reset dbCoordinateSaving
		ResetSaving();
		
		IS_CALL_COORDINATE = false;
		IS_SAVE_LOCATION = false;
		IS_CALL_DIRECTION = true;
		
		startPoint = null;
		endPoint = null;

		// khoi tao intent va gan strAddress cho intent
		intent = new Intent(GPSMain.this, Direction.class);
		//intent.putExtra("sendKey", strAddress);

		// load activity Direction
		startActivityForResult(intent, GET_CODE);

		/*
		 * mapView = (MapView)findViewById(R.id.mapView); // mapController =
		 * mapView.getController(); // List<Overlay> overlays =
		 * mapView.getOverlays(); // overlays.add(new
		 * DirectionPathOverlay(this));		 * 
		 * 
		 */
	}

	private void DrawDirection(GeoPoint startPoint, GeoPoint endPoint){
		//mapView.getOverlays().clear();
		DirectionDrawPath.DrawPath(startPoint, endPoint, DRAW_COLOR, mapView); 
		mapController.animateTo(endPoint);
		mapController.setZoom(ZOOM_LEVEL);
		
		IS_CALL_DIRECTION = false;		
	}
	
	
	private void SaveLocation(){			
		if(dbCoordinateSaving != null){	
			IS_SAVE_LOCATION = true;
			Intent intent = new Intent(GPSMain.this, LocationInfo.class);
			intent.putExtra("sendKey", dbCoordinateSaving);
			startActivityForResult(intent, GET_CODE);
		}
		
	}
	
	private void ResetSaving(){
		dbCoordinateSaving = null;
		//dbCoordinateSaving = new double[] {0.0, 0.0};
		/*// menu item Save location
		saveLocationMenuItem = (MenuItem)findViewById(R.id.savelocation);
		if(saveLocationMenuItem.isEnabled() == true)
			saveLocationMenuItem.setEnabled(false);*/
	}
	
	private void ViewLocationList(){
		Intent intent = new Intent(GPSMain.this, LocationList.class);
		//Intent intent = new Intent(GPSMain.this, AndroidSQLite.class);
		startActivityForResult(intent, GET_CODE);
	}
	/*private class DataTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(final String... args) {
			try{
				GPSMain.this.dbHelper.CreateDatabase();
				GPSMain.this.dbHelper.openDataBase();
				
			}catch(SQLException e){
				throw e;
			}
			return null;
		}
		
	}*/
} // end class