package android.uiexample;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewExampleActivity extends Activity{
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
 
        WebView wv = (WebView) findViewById(R.id.webview1);        
        wv.loadUrl("http://www.hoasen.edu.vn");  
    }
}
