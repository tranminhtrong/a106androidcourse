package gps.googlemap;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import android.util.Log;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

/**
 * Ve duong di tu diem A - src den diem B - dest
 * @param src : diem A
 * @param dest : diem B
 * @param color : xac dinh color cua duong chi dan
 * @param mapView : map hien tai
 */
public class DirectionDrawPath {
	//static DirectionPathOverlay directionOverlay;
	public static void DrawPath(GeoPoint src, GeoPoint dest, int color, MapView mapView){
		/*if(directionOverlay != null){
			mapView.getOverlays().remove(directionOverlay);
			mapView.invalidate();
		}	*/		
		
		// connect to map web service
		StringBuilder urlString = new StringBuilder();
		
		/** tao dia chi co dang:
		*  http://maps.google.com/maps?f=d&hl=en&saddr=25.04202,121.534761&daddr=25.05202,121.554761&ie=UTF8&0&om=0&output=kml
		**/
		
		urlString.append("http://maps.google.com/maps?f=d&hl=en");
		
		// from
		urlString.append("&saddr=");	
		urlString.append(Double.toString((double)src.getLatitudeE6()/1.0E6 ));
		urlString.append(",");
		urlString.append(Double.toString((double)src.getLongitudeE6()/1.0E6 ));
		
		// to
		urlString.append("&daddr=");	
		urlString.append(Double.toString((double)dest.getLatitudeE6()/1.0E6 ));
		urlString.append(",");
		urlString.append(Double.toString((double)dest.getLongitudeE6()/1.0E6 ));
		
		urlString.append("&ie=UTF8&0&om=0&output=kml");
		Log.d("xxx","URL="+urlString.toString());
		
		// get KML doc (XML) and parse it to get the coordinates (direction route)
		Document doc = null;
		HttpURLConnection urlConnection = null;
		URL url = null;
		try {
			url = new URL(urlString.toString());
			urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.connect();
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(urlConnection.getInputStream());
			
			if (doc.getElementsByTagName("GeometryCollection").getLength() > 0) {
				String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue();
				Log.d("xxx", "path = " + path);
				
				String[] pairs = path.split(" ");
				String[] lngLat = pairs[0].split(",");		//// lngLat[0]=longitude	 lngLat[1]=latitude 	lngLat[2]=height
				
				// src
				GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6), (int)(Double.parseDouble(lngLat[0])*1E6));
				mapView.getOverlays().add(new DirectionPathOverlay(startGP, startGP, 1));
				
				GeoPoint gp1;
				GeoPoint gp2 = startGP;
				
				// lay tung node dia diem toa do
				for(int i=1; i<pairs.length; i++){
					lngLat = pairs[i].split(",");
					gp1 = gp2;
					gp2 = new GeoPoint((int)(Double.parseDouble(lngLat[1]) * 1E6), (int)(Double.parseDouble(lngLat[0]) * 1E6));
					mapView.getOverlays().add(new DirectionPathOverlay(gp1, gp2, 2, color));
					Log.d("xxx", "pair: " + pairs[i]);					
				}
				mapView.getOverlays().add(new DirectionPathOverlay(dest, dest, 3));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e){
			e.printStackTrace();
		}
	}
}
