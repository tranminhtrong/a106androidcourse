package niit.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class main extends Activity implements OnClickListener {
	
	RadioGroup	radioGroup;
	
	Button	button;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }

	@Override
	public void onClick(View arg0) {
		long radioChecked = radioGroup.getCheckedRadioButtonId();
		String chuoiThongBao = "";
		if(radioChecked==R.id.radio1){
			chuoiThongBao = "Bạn đã chọn Rock";
		} else if(radioChecked==R.id.radio2){
			chuoiThongBao = "Bạn đã chọn Scissors";
		} else if(radioChecked==R.id.radio3){
			chuoiThongBao = "Bạn đã chọn Paper";
		} else {
			chuoiThongBao = "Bạn chưa check chọn";
		}
		Toast toast = Toast.makeText(this, chuoiThongBao, 3000);
		toast.show();
	}
}
