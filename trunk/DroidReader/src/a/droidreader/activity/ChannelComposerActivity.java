package a.droidreader.activity;

import a.droidreader.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ChannelComposerActivity extends Activity {
	String channelId = null;

	EditText channelTitleEditText;

	EditText channelURLEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel_composer_activity);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			channelId = bundle.getString("c_id");
			String channelTitle = bundle.getString("c_title");
			String url = bundle.getString("c_url");
			if (channelTitle != null) {
				getChannelTitleEditText().setText(channelTitle);
			}
			if (url != null) {
				getChannelURLEditText().setText(url);
			}
		}
	}

	public void saveChannelBtnTouched(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("c_id", channelId);
		bundle.putString("c_title", getChannelTitleEditText().getText().toString());
		bundle.putString("c_url", getChannelURLEditText().getText().toString());
		intent.putExtras(bundle);
		setResult(RESULT_OK, intent);
		this.finish();
	}

	public void backBtnTouched(View v) {
		setResult(RESULT_CANCELED);
		this.finish();
	}

	private EditText getChannelTitleEditText() {
		if (channelTitleEditText == null) {
			channelTitleEditText = (EditText)this.findViewById(R.id.channelTitleEditText);
		}
		return channelTitleEditText;
	}

	private EditText getChannelURLEditText() {
		if (channelURLEditText == null) {
			channelURLEditText = (EditText)this.findViewById(R.id.channelURLEditText);
		}
		return channelURLEditText;
	}
}
