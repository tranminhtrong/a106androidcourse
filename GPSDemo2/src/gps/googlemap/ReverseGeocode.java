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
import android.util.Log;

public class ReverseGeocode {
	
	public static List<Address> GetFromLocation(double dbLat, double dbLng,
			int maxResult) {
		String strURL = "http://maps.google.com/maps/geo?q=" + dbLat + ","
				+ dbLng + "&output=json&sensor=false";
		String response = "";
		List<Address> results = new ArrayList<Address>();

		HttpClient client = new DefaultHttpClient();

		Log.d("ReverseGeocode", strURL);
		try {
			HttpResponse httpResponse = client.execute(new HttpGet(strURL));
			HttpEntity httpEntity = httpResponse.getEntity();

			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(httpEntity.getContent()));

			// đọc ghi kết quả truy xuất từ link vào response đồng thời loại bỏ \n , \r
			String buff = null;
			while ((buff = bufferReader.readLine()) != null) {
				response += buff;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} // end try catch

		JSONArray responseArray = null;
		try {
			// ghi nội dung từ response vào jsonObject			
			JSONObject jsonObject = new JSONObject(response);
			// lấy nội dung trong thẻ Placemark
			responseArray = jsonObject.getJSONArray("Placemark");
		} catch (JSONException e) {
			return results;
		} // end try catch

		Log.d("ReverseGeocode", "" + responseArray.length() + " result(s)");
		for (int i = 0; i < responseArray.length() && i < maxResult - 1; i++) {
			
			// khởi tạo dữ liệu address với các giá trị rỗng
			// sub-admin=null, locality=null, thoroughfare=null ...
			Address address = new Address(Locale.getDefault());
			
			try {
				// copy phần tử đầu tiên (giá trị trả về) vào jsObject
				JSONObject jsObject = responseArray.getJSONObject(i);

				// address có dạng "Hòa Bình, 5th Ward, District 11, Ho Chi Minh City, Vietnam"
				String addressLine = jsObject.getString("address");
				
				// Lấy ra những từ trước dấy phẩy "," đầu tiên 
				// ví dụ: Hòa Bình
				if (addressLine.contains(","))
					addressLine = addressLine.split(",")[0];

				address.setAddressLine(0, addressLine);

				// du lieu lay ra bao gom AdministrativeArea, CountryName, CountryNameCode
				jsObject = jsObject.getJSONObject("AddressDetails")
						.getJSONObject("Country");
				address.setCountryName(jsObject.getString("CountryName"));	//"VietNam"
				address.setCountryCode(jsObject.getString("CountryNameCode"));	//"VN"

				// du lieu bao gom AdministrativeAreaName, SubAdministrativeArea
				jsObject = jsObject.getJSONObject("AdministrativeArea");
				address.setAdminArea(jsObject
						.getString("AdministrativeAreaName"));		// "TpHCM"
					
				// du lieu bao gom DependentLocality
				try{
					jsObject = jsObject.getJSONObject("SubAdministrativeArea");
					address.setSubAdminArea(jsObject.getString("SubAdministrativeAreaName")); 	// "Quan"				
				}
				catch(JSONException e){
					address.setSubAdminArea(null);					
				}
				
				try{
					jsObject = jsObject.getJSONObject("DependentLocality");
					address.setLocality(jsObject.getString("DependentLocalityName"));	//"Phuong"	
				}
				catch(JSONException e){
					address.setLocality(null);
				}
				
				try{
				address.setThoroughfare(jsObject.getJSONObject("Thoroughfare")
						.getString("ThoroughfareName"));
				}
				catch(JSONException e){
					address.setThoroughfare(null);
				}
			} 
			catch (JSONException e) {
				e.printStackTrace();				
				continue;
			} // end try catch
			results.add(address);
		} // end for
		return results;
	} // end
	
	public static String GetAddressFromCoordinate(double dbLat, double dbLng){
		String result = null;
		
		String strURL = "http://maps.google.com/maps/geo?q=" + dbLat + ","
		+ dbLng + "&output=json&sensor=false";
		
		String response = "";

		HttpClient client = new DefaultHttpClient();
		
		try {
			HttpResponse httpResponse = client.execute(new HttpGet(strURL));
			HttpEntity httpEntity = httpResponse.getEntity();

			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(httpEntity.getContent()));

			// đọc ghi kết quả truy xuất từ link vào response đồng thời loại bỏ \n , \r
			String buff = null;
			while ((buff = bufferReader.readLine()) != null) {
				response += buff;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} // end try catch
		
				
		JSONObject jsonObject;
		try {
			// ghi nội dung từ response vào jsonObject	
			jsonObject = new JSONObject(response);
			result = ((JSONArray)jsonObject.getJSONArray("Placemark")).getJSONObject(0).getString("address").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
				
		return result;
	}
} // end class
