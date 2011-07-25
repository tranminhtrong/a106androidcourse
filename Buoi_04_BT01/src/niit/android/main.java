package niit.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class main extends Activity implements OnClickListener {
	Button btnChanLe;
    EditText edtNhap;
    TextView tvKetQua;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnChanLe = (Button)findViewById(R.id.btnChanLe);
        edtNhap = (EditText)findViewById(R.id.edtNhap);
        tvKetQua = (TextView)findViewById(R.id.tvKetQua);
        btnChanLe.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {
		int So;
		So = Integer.valueOf(edtNhap.getText().toString());
		if(So%2==0) tvKetQua.setText("Số "+So+" là số chẳn");
		else tvKetQua.setText("Số "+So+" là số lẻ");
	}
}
