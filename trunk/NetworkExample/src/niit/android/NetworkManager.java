package niit.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class NetworkManager {
	
	public String executeHttpGet(String uri) throws Exception { 
		BufferedReader in = null; 
		String result = "";
        try { 
            HttpClient client = new DefaultHttpClient(); 
            HttpGet request = new HttpGet(); 
            request.setURI(new URI(uri)); 
            HttpResponse response = client.execute(request); 
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent())); 
            
            StringBuffer sb = new StringBuffer(""); 
            String line = ""; 
            String NL = System.getProperty("line.separator"); 
            while ((line = in.readLine())!=null) {             
            	sb.append(line + NL); 
            } 
            
            in.close(); 
     
            result = sb.toString(); 
            } finally { 
                if (in != null) { 
                    try { 
                        in.close(); 
                    } catch (IOException e) { 
                        e.printStackTrace(); 
                    } 
                } 
            }
		return result; 
	}
	
	  public String executeHttpPost(String uri,List<NameValuePair>	postParameters) throws Exception { 
	        BufferedReader in = null;  
			String result = "";
	        try { 
	            HttpClient client = new DefaultHttpClient(); 
	            HttpPost request = new HttpPost(uri); 
	            
	            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity( 
	                    postParameters); 
	 
	            request.setEntity(formEntity); 
	            HttpResponse response = client.execute(request); 
	            in = new BufferedReader(new InputStreamReader(response.getEntity() 
	                    .getContent())); 
	 
	            StringBuffer sb = new StringBuffer(""); 
	            String line = ""; 
	            String NL = System.getProperty("line.separator"); 
	            while ((line = in.readLine()) != null) { 
	                sb.append(line + NL); 
	            } 
	            in.close();           
	            result = sb.toString(); 
	            
	        } finally { 
	            if (in != null) { 
	                try { 
	                    in.close(); 
	                } catch (IOException e) { 
	                    e.printStackTrace(); 
	                } 
	            } 
	        }
	        return result; 
	  } 
	
}
