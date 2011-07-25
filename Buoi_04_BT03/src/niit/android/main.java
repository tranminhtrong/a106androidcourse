package niit.android;

import niit.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class main extends Activity implements OnCheckedChangeListener {
	RadioButton rdb10;
	RadioButton rdb15;
	EditText edtKetQua;
	RadioGroup rdbgroup;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        rdb10 = (RadioButton)findViewById(R.id.rdb10);
        rdb15 = (RadioButton)findViewById(R.id.rdb15);
        rdbgroup = (RadioGroup)findViewById(R.id.rdbgroup);
        edtKetQua = (EditText)findViewById(R.id.edtKetQua);
        rdbgroup.setOnCheckedChangeListener(this);
    }
    @Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int i;
		edtKetQua.setText("");
		if(checkedId==rdb10.getId())
		{
			for(i=0;i<10;i++) 
				edtKetQua.append("NIIT Hoa Sen \n");
		}
		else 
		{
			for(i=0;i<15;i++) 
				edtKetQua.append("NIIT Hoa Sen \n");
		}
	}
}