package niit.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class main extends Activity {
    
    private TextView tv = null; 
    
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main); 
 
        tv = (TextView)findViewById(R.id.text1); 
        
        String str = "Hoa Sen University";
        
        File file  = getCacheDir();
        
        String[] str2 = fileList();
        
        String cacheDir = getCacheDir().getPath();
        File downloadingMediaFile = new File(cacheDir, "downloadingMedia.dat");
        FileOutputStream out = null;
        
		try {
			out = new FileOutputStream(downloadingMediaFile);
			out.write(str.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
        finally{
        	try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
        setOptionText(); 
                
    } 
     
    @Override 
    public boolean onCreateOptionsMenu(Menu menu)  
    {        
    	MenuInflater inflater = getMenuInflater(); 
    	inflater.inflate(R.menu.mainmenu, menu); 
    	return true; 
    } 

    @Override 
    public boolean onOptionsItemSelected (MenuItem item) 
    { 
    	if (item.getItemId() == R.id.menu_prefs) 
    	{ 
    		Intent intent = new Intent().setClass(this, FlightPreferenceActivity.class); 
    		this.startActivityForResult(intent, 0); 
    	} 
    	else if (item.getItemId() == R.id.menu_quit) 
    	{ 
    		finish(); 
    	} 
    	return true; 
    } 
    
    @Override 
    public void onActivityResult(int reqCode, int resCode, Intent data) 
    { 
    	super.onActivityResult(reqCode, resCode, data); 
    	setOptionText(); 
    }
  
    private void setOptionText() 
    { 
    	SharedPreferences prefs = getSharedPreferences("niit.android_preferences", 0); 
    	String option = prefs.getString(this.getResources().getString(R.string.selected_flight_sort_option), 
    	this.getResources().getString(R.string.flight_sort_option_default_value)); 
    	String[] optionText = this.getResources().getStringArray(R.array.flight_sort_options); 

    	tv.setText("option value is " + option + " (" + optionText[Integer.parseInt(option)] + ")"); 
    }
	
}