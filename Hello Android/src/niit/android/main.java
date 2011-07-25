package niit.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class main extends Activity {
	TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        tv = (TextView) findViewById(R.id.tvHello);
        tv.setText("Chào mừng bạn đến với thế giới Android!");

        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		int id = bundle.getInt("id");
    }
    
}
