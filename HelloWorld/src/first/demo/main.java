package first.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class main extends Activity {

	EditText editText1;
	EditText editText2;
	
	Button btn;

	TextView textViewKetQua;
	Button cong2So;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		

	}

	public void congHaiSo(View v) {

		String chuoi1 = getEditText1().getText().toString();
		String chuoi2 = getEditText2().getText().toString();

		int tong = Integer.valueOf(chuoi1) + Integer.valueOf(chuoi2);

		getTextViewKetQua().setText("Tong hai so la:" + String.valueOf(tong));

	}

	private EditText getEditText1() {
		if (editText1 == null) {
			editText1 = (EditText) findViewById(R.id.editText1);
		}
		return editText1;
	}

	private EditText getEditText2() {
		if (editText2 == null) {
			editText2 = (EditText) findViewById(R.id.editText2);
		}
		return editText2;
	}

	private TextView getTextViewKetQua() {
		if (textViewKetQua == null) {
			textViewKetQua = (TextView) findViewById(R.id.ketQuaTextView);
		}
		return textViewKetQua;
	}

	private Button getButton() {
		if (cong2So == null) {
			cong2So = (Button) findViewById(R.id.button);
		}
		return cong2So;
	}

}