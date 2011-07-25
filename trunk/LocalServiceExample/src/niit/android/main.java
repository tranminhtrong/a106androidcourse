package niit.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class main extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button bindBtn = (Button)findViewById(R.id.startBtn); 
        bindBtn.setOnClickListener(this);
        
        Button unbindBtn = (Button)findViewById(R.id.stopBtn); 
        unbindBtn.setOnClickListener(this);
 
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.startBtn:
			{
				Intent intent = new Intent(this,BackgroundService.class);
				startService(intent);
			}
				break;
			case R.id.stopBtn:
			{
				Intent intent = new Intent(this,BackgroundService.class);
				stopService(intent);
			}
				break;
			
			default:
				break;
		}
	}
}