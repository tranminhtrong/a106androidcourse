package android.uiexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class main extends Activity {
	private int color = Color.RED;

	public static final int COLOR_PICKER_CODE = 100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTextView();
	}

	public void resetAllTextView() {
		TextView labelred, labelgreen, labelblue;
		labelred = (TextView)findViewById(R.id.labelRed);
		labelred.setTextColor(Color.parseColor("#666666"));
		labelgreen = (TextView)findViewById(R.id.labelGreen);
		labelgreen.setTextColor(Color.parseColor("#666666"));
		labelblue = (TextView)findViewById(R.id.labelBlue);
		labelblue.setTextColor(Color.parseColor("#666666"));
	}

	public void buttonClickHandler(View v) {
		Intent request = new Intent(this, ColorPickerActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("color", color);
		request.putExtras(bundle);
		startActivityForResult(request, COLOR_PICKER_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case COLOR_PICKER_CODE:
				if (resultCode == RESULT_OK) {
					Bundle bundle = data.getExtras();
					color = bundle.getInt("color");
					setTextView();
				}
		}
	}

	private void setTextView() {
		int tvId = 0;
		resetAllTextView();
		switch (color) {
			case Color.RED:
				tvId = R.id.labelRed;
				break;
			case Color.GREEN:
				tvId = R.id.labelGreen;
				break;
			case Color.BLUE:
				tvId = R.id.labelBlue;
				break;
			default:
				break;
		}
		TextView mycolor = (TextView)findViewById(tvId);
		mycolor.setTextColor(color);
	}
}
