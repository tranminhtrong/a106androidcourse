package niit.android;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class main extends MapActivity {
	private MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mapView = (MapView)findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		Drawable marker=getResources().getDrawable(R.drawable.androidmarker);
        InterestingLocations funPlaces = new InterestingLocations(marker); 
        mapView.getOverlays().add(funPlaces); 
        
        GeoPoint pt = funPlaces.getCenter();    // get the first-ranked point 
        mapView.getController().setCenter(pt); 
        mapView.getController().setZoom(15);

        
	}

	public void myClickHandler(View target) {
		switch (target.getId()) {
			case R.id.zoomin:
				mapView.getController().zoomIn();
				break;
			case R.id.zoomout:
				mapView.getController().zoomOut();
				break;
			case R.id.sat:
				mapView.setSatellite(true);
				break;
			case R.id.street:
				mapView.setStreetView(true);
				break;
			case R.id.traffic:
				mapView.setTraffic(true);
				break;
			case R.id.normal:
				mapView.setSatellite(false);
				mapView.setStreetView(false);
				mapView.setTraffic(false);
				break;
		}
	}

	class InterestingLocations extends ItemizedOverlay {
		private List<OverlayItem> locations = new ArrayList<OverlayItem>();

		private Drawable marker;

		public InterestingLocations(Drawable marker) {
			super(marker);
			this.marker = marker;
			// create locations of interest 
			GeoPoint disneyMagicKingdom = new GeoPoint((int) (28.418971 * 1000000), (int) (-81.581436 * 1000000));
			GeoPoint disneySevenLagoon = new GeoPoint((int) (28.410067 * 1000000), (int) (-81.583699 * 1000000));
			locations.add(new OverlayItem(disneyMagicKingdom, "Magic Kingdom", "Magic Kingdom"));
			locations.add(new OverlayItem(disneySevenLagoon, "Seven Lagoon", "Seven Lagoon"));
			populate();
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			boundCenterBottom(marker);
		}

		@Override
		protected OverlayItem createItem(int i) {
			return locations.get(i);
		}

		@Override
		public int size() {
			return locations.size();
		}
	}

	@Override
	protected boolean isLocationDisplayed() {
		return false;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
