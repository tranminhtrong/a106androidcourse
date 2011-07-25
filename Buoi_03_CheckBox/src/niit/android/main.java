package niit.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class main extends Activity implements OnCheckedChangeListener {
	CheckBox cb;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        cb=(CheckBox)findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener(this);
    }
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		if (arg1) {
			cb.setText("This checkbox is: checked");
	    }
	    else {
	    	cb.setText("This checkbox is: unchecked");
	    	}
		
	}
}

