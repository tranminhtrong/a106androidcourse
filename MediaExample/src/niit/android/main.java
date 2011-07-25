package niit.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class main extends Activity {
    
	Button btnAudioExample;
	Button btnVideoExample;
	Button btnAudioRecordingExample;
	Button btnVideoRecordingExample;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnAudioExample = (Button)findViewById(R.id.btnAudioExample);
        btnAudioExample.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main.this,AudioExampleActivity.class);
		        startActivity(intent);
			}
		});
        
        btnVideoExample = (Button)findViewById(R.id.btnVideoExample);
        btnVideoExample.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main.this,VideoExampleActivity.class);
		        startActivity(intent);
			}
		});
        
        btnAudioRecordingExample = (Button)findViewById(R.id.btnAudioRecordingExample);
        btnAudioRecordingExample.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main.this,AudioRecorderActivity.class);
		        startActivity(intent);
			}
		});
        
        btnVideoRecordingExample = (Button)findViewById(R.id.btnVideoRecordingExample);
        btnVideoRecordingExample.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main.this,VideoRecorderActivity.class);
		        startActivity(intent);
			}
		});
        
    }
}