package niit.android;

import niit.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class main extends Activity implements OnClickListener {
	Button btnDoc;
    EditText edtKetQua;
    EditText edtNhap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnDoc =(Button)findViewById(R.id.btnDoc);
        edtKetQua =(EditText)findViewById(R.id.edtKetQua);
        edtNhap =(EditText)findViewById(R.id.edtNhap);
        btnDoc.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {
		int So = Integer.valueOf(edtNhap.getText().toString());
		switch (So) {
		case 0: edtKetQua.setText("Số không");
				break;
		case 1: edtKetQua.setText("Số một");
				break;
		case 2: edtKetQua.setText("Số hai");
				break;
		case 3: edtKetQua.setText("Số ba");
				break;
		case 4: edtKetQua.setText("Số bốn");
				break;
		case 5: edtKetQua.setText("Số năm");
				break;
		default: edtKetQua.setText("Không rõ !!!");
			break;
		}
		
	}
}


