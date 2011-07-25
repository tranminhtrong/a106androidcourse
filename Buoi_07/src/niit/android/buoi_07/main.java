package niit.android.buoi_07;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.Toast;

public class main extends Activity implements OnCreateContextMenuListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	public void openMenuExampleActivity(View v){
		Intent intent = new Intent(this,MenuExampleActivity.class);
		startActivity(intent);
	}
	

}
