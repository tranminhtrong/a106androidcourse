package a.droidreader.activity;

import a.droidreader.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import a.droidreader.model.*;
import a.droidreader.array_adapter.*;

public class FeedItemsActivity extends Activity implements OnItemClickListener {
	private ListView feedItemsListView;
	private TextView tvChannelTitle;
	private Channel channel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_items_activity);
		
		tvChannelTitle = (TextView)findViewById(R.id.tvChannelTitle);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String channelTitleStr = (String)bundle.get("c_title");
		String channelURLStr = (String)bundle.get("c_url");
		channel = new Channel();
		channel.setChannelTitle(channelTitleStr);
		channel.setUrl(channelURLStr);
		
		setChannelTile();
		
		FeedItemListArrayAdapter adapter = new FeedItemListArrayAdapter(this, channel.getItems());
		getFeedItemsListView().setAdapter(adapter);
		getFeedItemsListView().setOnItemClickListener(this);
	}

	private void setChannelTile() {
		
		int count = 0;
		for(FeedItem fi: channel.getItems()){
			if(fi.isUnRead())
				count ++;
		}
		
		tvChannelTitle.setText("Kênh "+channel.getChannelTitle()+" "+count+"/"+channel.getItems().size()+"");
		
	}

	private ListView getFeedItemsListView() {
		if (feedItemsListView == null) {
			feedItemsListView = (ListView)findViewById(R.id.feedItemsListView);
		}
		return feedItemsListView;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this, FeedItemDetailsActivity.class);
		FeedItem feedItem = channel.getItems().get(arg2);
		feedItem.setUnRead(false);
		((FeedItemListArrayAdapter)feedItemsListView.getAdapter()).notifyDataSetChanged ();
		setChannelTile();
		Bundle bundle = new Bundle();
		bundle.putString("fi_title", feedItem.getTitle());
		bundle.putString("fi_pub_date", feedItem.getPubDate().toGMTString());
		bundle.putString("fi_description", feedItem.getDescription());
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
