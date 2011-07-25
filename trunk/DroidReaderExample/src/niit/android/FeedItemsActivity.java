package niit.android;

import niit.android.array_adapter.FeedItemListArrayAdapter;
import niit.android.model.Channel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class FeedItemsActivity extends Activity {
	private ListView feedItemsListView;
	private TextView tvChannelTitle;
	private Channel channel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_items_activity);
		
		tvChannelTitle = (TextView)findViewById(R.id.tvChannelTitle);
		feedItemsListView = (ListView)findViewById(R.id.feedItemsListView);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String channelTitleStr = (String)bundle.get("c_title");
		String channelURLStr = (String)bundle.get("c_url");
		
		tvChannelTitle.setText("Channel "+channelTitleStr);
		
		channel = new Channel();
		channel.channelTitle = channelTitleStr;
		channel.url = channelURLStr;
		
		FeedItemListArrayAdapter adapter = new FeedItemListArrayAdapter(this, channel.getItems());
		
		feedItemsListView.setAdapter(adapter);
	}
}
