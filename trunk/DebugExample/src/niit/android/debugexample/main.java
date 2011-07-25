package niit.android.debugexample;

import android.app.Activity;
import android.os.Bundle;

public class main extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        int amount = 0;
        for(int i=0;i<10;i++){
        	int tmp = i*100;
        	amount+=tmp;
        }
        
    }
}