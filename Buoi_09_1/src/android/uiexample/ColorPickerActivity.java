package android.uiexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class ColorPickerActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picker);
		Intent request = getIntent();
		Bundle bundle = request.getExtras();
		int color = bundle.getInt("color");
		int radioButtonId = 0;
		switch (color) {
			case Color.RED:
				radioButtonId = R.id.radioRed;
				break;
			case Color.GREEN:
				radioButtonId = R.id.radioGreen;
				break;
			case Color.BLUE:
				radioButtonId = R.id.radioBlue;
				break;
			default:
				break;
		}
		RadioButton rBtn = (RadioButton)findViewById(radioButtonId);
		rBtn.setChecked(true);
	}

	public void radioClickHandler(View v) {
		Intent answer = new Intent();
		Bundle bundle = new Bundle();
		if (v.getId() == R.id.radioBlue) {
			bundle.putInt("color", Color.BLUE);
		} else if (v.getId() == R.id.radioRed) {
			bundle.putInt("color", Color.RED);
		} else {
			bundle.putInt("color", Color.GREEN);
		}
		answer.putExtras(bundle);
		setResult(RESULT_OK, answer);
		finish();
	}
}
