package niit.android;

//import gps.googlemap.GPSMain;


import java.util.List;
import java.util.Locale;

import niit.android.Geocode;
//import gps.googlemap.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Mylocation extends MapActivity implements OnClickListener {

    MapView mapView = null;
    MyLocationOverlay whereAmI = null;
    LocationManager locMgr = null;
    LocationListener locListener = null;
    MapController mapController;
    GeoPoint currentPoint = null;
    Button searchBtn;
    ProgressDialog proDialog = null;
    Thread searchAddressThread;
    List<Address> addresses = null;
    Geocoder geoCoder = null;
    EditText etAddress;
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		etAddress = (EditText) findViewById(R.id.address);
		searchBtn = (Button)findViewById(R.id.buttonSearch);
		searchBtn.setOnClickListener(this);
        mapView = (MapView)findViewById(R.id.geoMap);
        mapView.setBuiltInZoomControls(true);
        mapView.getController().setZoom(15);
        mapController = mapView.getController();
        double[] dbCoordinate = { 10.781003173078687, 106.70016288757324 };
		currentPoint = ConvertDbCoordinateToGeopoint(dbCoordinate);
		mapController.animateTo(currentPoint);
		//mapController.setZoom(ZOOM_LEVEL);
		mapView.invalidate();		
    }    
    
    //
    private void showLocation(GeoPoint geo) {
        if (geo != null)
        {                     
        	// di chuyen toi diem geo
            mapController.animateTo(geo);
            // dat diem geo tai trung tam ban do
            mapController.setCenter(geo);
        }
    }
    
    
//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// tat ban phim ao		
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etAddress.getWindowToken(), 0);
		// tao diago xoay trong khi map control tim dia diem
		proDialog = ProgressDialog.show(Mylocation.this, "Processing...",
				"Searching...", true, false);
		//Toast mytoast= new Toast(Mylocation.this);
		
		// tao thread tim dia diem bang dia chi
		searchAddressThread = new Thread() {
			public void run() {				
				
				String strAddress= etAddress.getText().toString();
				try {
					// tim dia chi bang sensor gps tren mobile
					try {
						addresses = geoCoder.getFromLocationName(
								strAddress, 5);
					}
					// truong hop test tren emulator se su dung ham
					// GetFromLocation cua Geocode de ho tro
					catch (Exception e) {
						addresses = Geocode.GetFromLocationName(
								strAddress, 5);
					}
					Thread.sleep(1500);
				} catch (Exception e) {
				}
				// sau khi thuc hien xong thi bao cho main thread biet
				ShowAddressResults.sendEmptyMessage(0);
			}
		};
		searchAddressThread.start();
	}
	// khai bao mot Handler giup nhan biet khi nao Thread tim dia diem dung lai
	private Handler ShowAddressResults = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// tat dialog
			proDialog.dismiss();

			// khong tim thay
			if (addresses.size() == 0) {
				Dialog searchingError = new AlertDialog.Builder(Mylocation.this)
						.setIcon(0).setTitle("Error")
						.setPositiveButton("OK", null)
						.setMessage("No results match your search.").create();
				searchingError.show();
			}
			//  neu tim thay
			else {
				for (int i = 0; i < addresses.size(); i++) {
					Address address = addresses.get(i);		
					
					//  dbCoordinateSaving
					double []dbCoordinateSaving = new double[] {address.getLatitude(), address.getLongitude()};
					
					GeoPoint geoCodeCoordinate = ConvertDbCoordinateToGeopoint(dbCoordinateSaving);
					// show dia diem tim duoc 
					showLocation(geoCodeCoordinate);
					// danh dau dia diem tim duoc len ban do
					SetMarkerPointing(geoCodeCoordinate,i);
				}
			}
			
			
		}
	};
    
	// Gan Co len ban do tai vi tri tim thay
	private void SetMarkerPointing(GeoPoint point, int i) {
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.pushpin);
		CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(
				drawable, this);		
		Address address = addresses.get(i);				
		//  dbCoordinateSaving
		double []dbCoordinateSaving = new double[] {address.getLatitude(), address.getLongitude()};
		
		GeoPoint geopoint = ConvertDbCoordinateToGeopoint(dbCoordinateSaving);
		OverlayItem overlayItem = new OverlayItem(geopoint, "Address", addresses.get(i).toString());
		

		itemizedOverlay.addOverlay(overlayItem);
		mapOverlays.add(itemizedOverlay);
		
	}
	// xac dinh address tu 1 toa do vi tri
	
	
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
	// covert db sang geopoint
	private GeoPoint ConvertDbCoordinateToGeopoint(double[] dbCoordinate) {
		double dbLat = dbCoordinate[0];
		double dbLng = dbCoordinate[1];
		GeoPoint geoPointCoordinate = new GeoPoint((int) (dbLat * 1E6),
				(int) (dbLng * 1E6));
		return geoPointCoordinate;
	}
}
