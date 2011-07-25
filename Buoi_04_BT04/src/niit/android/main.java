package niit.android;

import niit.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class main extends Activity implements OnClickListener {
	Button btnHienThi;
    EditText edtKetQua;
    EditText edtNhap;
    CheckBox cbxHienThi;
    int[] array = {5,10,15,20,25,30,35,40,45,50,55};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnHienThi=(Button)findViewById(R.id.btnHienThi);
        edtKetQua=(EditText)findViewById(R.id.edtKetQua);
        edtNhap=(EditText)findViewById(R.id.edtNhap);
        cbxHienThi= (CheckBox)findViewById(R.id.cbxHienThi);
        btnHienThi.setOnClickListener(this);
    }
    @Override
	public void onClick(View v) {
		int i;
		int So = Integer.valueOf(edtNhap.getText().toString());
		edtKetQua.setText("");
		for(i=0;i<So;i++)
		{
			if(cbxHienThi.isChecked()) edtKetQua.append("\n "+array[i]);
			else edtKetQua.append(array[i]+", ");
		}
		
	}
}