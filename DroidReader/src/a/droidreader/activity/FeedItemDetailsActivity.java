package a.droidreader.activity;

import a.droidreader.R;
import a.droidreader.controller.FeedImageGetter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class FeedItemDetailsActivity extends Activity{

	private TextView feedItemTitleLabel;
	private TextView feedItemPubDateLabel;
	private TextView feedItemDescriptionLabel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_item_details);
		
		Intent intent = intent = getIntent();
		
		Bundle bundle = intent.getExtras();
		
		feedItemTitleLabel = (TextView)findViewById(R.id.feedItemTitleLabel);
		feedItemPubDateLabel = (TextView)findViewById(R.id.feedItemPudDateLabel);
		feedItemDescriptionLabel = (TextView)findViewById(R.id.feedItemDescriptionLabel);
	
		feedItemTitleLabel.setText(bundle.getString("fi_title"));
		feedItemPubDateLabel.setText(bundle.getString("fi_pub_date"));
		feedItemDescriptionLabel.setText(Html.fromHtml(bundle.getString("fi_description"), new FeedImageGetter(), null));
		
	}
	
	
	
}
