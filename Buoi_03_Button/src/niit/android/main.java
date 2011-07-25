package niit.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class main extends Activity implements OnClickListener {
	
	TextView textView;
	EditText	editText;
	Button	button;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }
    
	@Override
	public void onClick(View v) {
		String chuoiTen =	editText.getText().toString();
		textView.setText("Xin chào " + chuoiTen);
	}
}

