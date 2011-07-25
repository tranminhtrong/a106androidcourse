package niit.android;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class main extends Activity {
	
	WebView webView;
	String mimeType = "text/html";
    String encoding = "UTF-8";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);    
        webView = (WebView)findViewById(R.id.webView);
        
        String result="";
        
        NetworkManager networkManager = new NetworkManager();
        /*
        try {
			result = networkManager.executeHttpGet("http://www.google.com.vn/search?q=hoasen+university");
			System.out.print(result);
        } catch (Exception e) {
			e.printStackTrace();
        }
        */
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>(); 
        postParameters.add(new BasicNameValuePair("test", "AseDra+Le"));
        
        try {
			result = networkManager.executeHttpPost("http://www.hieuvugroup.com/getpost/getpost.php", postParameters);
			System.out.print(result);
        } catch (Exception e) {
			e.printStackTrace();
		}
        
        webView.loadDataWithBaseURL("",result, mimeType, encoding,"");
        
    }
}