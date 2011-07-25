package niit.android.demoservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class main extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btnStartService = (Button)findViewById(R.id.btnStartService);
        btnStartService.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main.this,BackgroundService.class);
				main.this.startService(intent);
			}
		});
        
        Button btnStopService = (Button)findViewById(R.id.btnStopService);
        btnStopService.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main.this,BackgroundService.class);
				main.this.stopService(intent);
			}
		});
    }
}