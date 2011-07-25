package niit.android.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;

public class FeedImageGetter implements ImageGetter{

	@Override
    public Drawable getDrawable(String source) {                  
		Drawable d = null;
		try {
			InputStream src = imageFetch(source);
			d = Drawable.createFromStream(src, "src");
            if(d != null){
            	d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
            }
		} catch (MalformedURLException e) {
             e.printStackTrace(); 
		} catch (IOException e) {
             e.printStackTrace();  
		}

		return d;
   }
	
	public InputStream imageFetch(String source)	throws MalformedURLException,IOException {
		URL url = new URL(source);
		Object o = url.getContent();
		InputStream content = (InputStream)o;   
		return content;
		
	}
	

}
