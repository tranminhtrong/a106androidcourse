package gps.googlemap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.location.Address;

public class Geocode {
	/*
	 * public static GeoPoint GetFromLocationName(String address){ // thay
	 * khoảng trắng trong chuỗi address bằng dấu + address =
	 * address.replaceAll(" ", "+"); String strURL =
	 * "http://maps.googleapis.com/maps/api/geocode/json?address=" + address +
	 * "&sensor=false"; String response = "";
	 * 
	 * HttpClient client = new DefaultHttpClient();
	 * 
	 * try{ HttpResponse httpResponse = client.execute(new HttpGet(strURL));
	 * HttpEntity httpEntity = httpResponse.getEntity();
	 * 
	 * BufferedReader bufferReader = new BufferedReader( new
	 * InputStreamReader(httpEntity.getContent()));
	 * 
	 * String buff = null; while ((buff = bufferReader.readLine()) != null) {
	 * response += buff; } } catch(IOException e){ e.printStackTrace(); }
	 * 
	 * Double dbLng = new Double(0); Double dbLat = new Double(0);
	 * 
	 * try { // ghi nội dung từ response vào jsonObject JSONObject jsonObject =
	 * new JSONObject(response);
	 * 
	 * // lấy giá trị Latitude dbLat =
	 * ((JSONArray)jsonObject.getJSONArray("results")).getJSONObject(0)
	 * .getJSONObject("geometry").getJSONObject("location") .getDouble("lat");
	 * 
	 * // lấy giá trị Longitude dbLng =
	 * ((JSONArray)jsonObject.get("results")).getJSONObject(0)
	 * .getJSONObject("geometry").getJSONObject("location") .getDouble("lng");
	 * 
	 * return new GeoPoint((int)(dbLat * 1E6), (int)(dbLng * 1E6));
	 * 
	 * } catch (JSONException e) { e.printStackTrace(); } return null; }
	 */

	public static List<Address> GetFromLocationName(String strAddress,
			int maxResult) {
		// thay khoảng trắng trong chuỗi address bằng dấu +
		strAddress = strAddress.replaceAll(" ", "+");
		String strURL = "http://maps.googleapis.com/maps/api/geocode/json?address="
				+ strAddress + "&sensor=false";
		String response = "";
		List<Address> results = new ArrayList<Address>();

		HttpClient client = new DefaultHttpClient();

		try {
			HttpResponse httpResponse = client.execute(new HttpGet(strURL));
			HttpEntity httpEntity = httpResponse.getEntity();

			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(httpEntity.getContent()));

			String buff = null;
			while ((buff = bufferReader.readLine()) != null) {
				response += buff;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONArray responseArray = null;
		try {
			// ghi nội dung từ response vào jsonObject
			JSONObject jsonObject = new JSONObject(response);

			responseArray = jsonObject.getJSONArray("results");
		} catch (JSONException e) {
			return results;
		}

		Double dbLng = new Double(0);
		Double dbLat = new Double(0);

		for (int i = 0; i < responseArray.length() && i < maxResult - 1; i++) {
			Address address = new Address(Locale.getDefault());

			try {
				JSONObject jsObject = responseArray.getJSONObject(i);

				// lấy giá trị Latitude
				dbLat = jsObject.getJSONObject("geometry")
						.getJSONObject("location").getDouble("lat");
				address.setLatitude(dbLat);

				// lấy giá trị Longitude
				dbLng = jsObject.getJSONObject("geometry")
						.getJSONObject("location").getDouble("lng");
				address.setLongitude(dbLng);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			results.add(address);
		}
		return results;
	}
}
